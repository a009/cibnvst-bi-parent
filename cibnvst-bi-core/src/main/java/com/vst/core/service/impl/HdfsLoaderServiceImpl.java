package com.vst.core.service.impl;

import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructField;
import org.json.simple.JSONObject;

import com.vst.common.tools.date.ToolsDate;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.core.cache.CacheSparkSqls;
import com.vst.core.cache.CacheType;
import com.vst.core.communal.bean.Video;
import com.vst.core.communal.bean.VideoTopic;
import com.vst.core.communal.sql.SqlFactory;
import com.vst.core.service.AbstractLoaderService;

/**
 * @author joslyn
 * @date 2017年12月19日 下午5:09:58
 * @description
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class HdfsLoaderServiceImpl extends AbstractLoaderService implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -466203606499179300L;
	
	/**
	 * 写日志
	 */
	private static final Logger logger = Logger.getLogger(HdfsLoaderServiceImpl.class);
	
	/**
	 * 加载数据的抽象方法
	 * @param sparkSession spark会话管理类
	 * @param path 数据加载的path，其中日期已经替换
	 * @param sqlFactory sql的工厂类
	 * @param cacheTypeBC 类型缓存
	 * @return
	 */
	@Override
	public JavaRDD<JSONObject> load(SparkSession sparkSession, String path, 
			final SqlFactory sqlFactory, final Broadcast<CacheType> cacheTypeBC) {
		path = path.replace("hdfs:", "");
		logger.info("===>>> start loading sql data from hdfs. path = [" + path + "]");
		Dataset<Row> ds = null;
		try {
			ds = sparkSession.read().parquet(path.split(";"));
		} catch (Exception e) {
			return null;// 如果加载失败，表示当前时间的数据可能不完整，等待补充完整
		}
		JavaRDD<JSONObject> rdd = ds.toJavaRDD().map(new Function<Row, JSONObject>() {
			private static final long serialVersionUID = -946516510900428544L;
			@Override
			public JSONObject call(Row row) throws Exception {
				StructField[] fields = row.schema().fields();
				JSONObject json = new JSONObject();
				for (int i = 0; i < fields.length; i++) {
	                String colName = fields[i].name();
	                Object colValue = row.get(i);
	                json.put(colName, colValue);
				}
				
				if(sqlFactory.isSqlIsFormat()){
					String key = ToolsString.checkEmpty(json.get("nameId"));
					Video video = cacheTypeBC.value().getVideo(key);
					if(video != null){
						json.put("cid", Long.valueOf(video.getCid()));
						json.put("specId", Long.valueOf(video.getSpecialType()));
						json.put("name", video.getTitle());
						json.put("actor", video.getActor());
						json.put("area", video.getArea());
						json.put("cat", video.getCat());
						json.put("director", video.getDirector());
						json.put("year", Long.valueOf(video.getYear()));
						json.put("mark", video.getMark());
					}
					
					key = ToolsString.checkEmpty(json.get("topicId"));
					VideoTopic topic =  cacheTypeBC.value().getVideoTopic(key);
					if(topic != null){
						json.put("topicCid", String.valueOf(topic.getCid()));
						json.put("specId", Long.valueOf(topic.getSpecialType()));
						json.put("topic", topic.getTitle());
						json.put("topicType", topic.getTemplate());
					}
				}
				return json;
			}
		});
		return rdd;
	}
	
	@Override
	public JSONObject processLoadPath(SqlFactory sqlFactory, boolean allowDeleteData){
		JSONObject result = new JSONObject();
		String path = sqlFactory.getSqlDataPath();
		String[] pos = sqlFactory.getSqlRunPosition();
		String runPosDate = pos.length > 0 ? pos[0] : null;
		String runPosHH = pos.length > 1 ? pos[1] : null;
		String runPosMM = pos.length > 2 ? pos[2] : null;
		Date date = null;
		if(pos.length == 3){
			date = ToolsDate.parseDate("yyyyMMdd|HH|mm", runPosDate + "|" + runPosHH + "|" + ToolsNumber.parseInt(runPosMM)*10);
		}else if(pos.length == 2){
			date = ToolsDate.parseDate("yyyyMMdd|HH", runPosDate + "|" + runPosHH);
		}else if(pos.length == 1){
			date = ToolsDate.parseDate(ToolsDate.yyyy_MM_dd4, runPosDate);
		}
		if(date == null) return null;
		
		long runPositionTime = date.getTime();
		boolean isDel = false;
		String makeupKey = "0".equals(sqlFactory.getSqlPid()) ? sqlFactory.getSqlId() : sqlFactory.getSqlPid();
		String runPos = null;
		if(pos.length == 1){// 这种情况是按天清理
			// 如果是上一次计算位置大于等于当前时间，就以当前时间天为准；如果是小于当前天时间，就说明需要补数据
			if(runPositionTime >= ToolsDate.getCurrStartTime()){
				runPos = ToolsDate.getCurrDate(ToolsDate.yyyy_MM_dd4);
				if(allowDeleteData) isDel = true;
				CacheSparkSqls.getInstance().setMakeUp(makeupKey, false);// 给非补充数据标识
			}else{
				// 如果是刚过凌晨或者任务终止过，还需要再跑一次昨天或者终止点的数据，才能保证完整
				long distince = ToolsDate.getCurrStartTime() - sqlFactory.getSqlRuntime();
				if(distince > 0){
					runPos = ToolsDate.formatDate(ToolsDate.yyyy_MM_dd4, runPositionTime);
					if(allowDeleteData) isDel = true;
				}else{
					runPos = ToolsDate.formatDate(ToolsDate.yyyy_MM_dd4, runPositionTime + 24*60*60*1000L);
				}
				CacheSparkSqls.getInstance().setMakeUp(makeupKey, true);// 给正在补充数据标识
			}
			path = path.replace("{date}", runPos);
		}else if(pos.length == 2){// 这种情况不需要清理，并且计算间隔是小时为单位的
			long distince = System.currentTimeMillis() - runPositionTime;
			if(distince > 60*60*1000L && distince < 2*60*60*1000L){
				runPos = ToolsDate.formatDate("yyyyMMdd|HH", runPositionTime + 60*60*1000L);
				CacheSparkSqls.getInstance().setMakeUp(makeupKey, false);// 给非补充数据标识
			}else if(distince >= 2*60*60*1000L){// 如果间隔超过俩小时，说明是在补充数据
				runPos = ToolsDate.formatDate("yyyyMMdd|HH", runPositionTime + 60*60*1000L);
				CacheSparkSqls.getInstance().setMakeUp(makeupKey, true);// 给正在补充数据标识
			}else{
				return null;
			}
			String[] values = runPos.split("[|]");
			path = path.replace("{date}", values[0]).replace("{hh}", values[1]);
		}else if(pos.length == 3){// 这种情况不需要清理，并且计算间隔是10分钟
			long distince = System.currentTimeMillis() - runPositionTime;
			if(distince > 10*60*1000L && distince < 30*60*1000L){
				runPos = ToolsDate.formatDate("yyyyMMdd|HH|mm", runPositionTime + 10*60*1000L);
				CacheSparkSqls.getInstance().setMakeUp(makeupKey, false);// 给非补充数据标识
			}else if(distince >= 30*60*1000L){// 如果间隔超过20分钟，说明是在补充数据
				runPos = ToolsDate.formatDate("yyyyMMdd|HH|mm", runPositionTime + 10*60*1000L);
				CacheSparkSqls.getInstance().setMakeUp(makeupKey, true);// 给正在补充数据标识
			}else{
				return null;
			}
			String[] values = runPos.split("[|]");
			String mm = String.valueOf(ToolsNumber.parseInt(values[2]) / 10);
			path = path.replace("{date}", values[0]).replace("{hh}", values[1]).replace("{mm}", mm);
			runPos = values[0] + "|" + values[1] + "|" + mm;// 重组runPos
		}
		result.put("isDel", isDel);
		result.put("runPos", runPos);
		result.put("path", path);
		
		// 如果路径配置的是区间形式的
		String[] values = path.split("/");
		String dateConfig = values.length > 4 ? values[4] : null;
		// 暂时只支持[{startDate30},{endDate}]这种区间格式
		if(!ToolsString.isEmpty(dateConfig) && dateConfig.startsWith("[") && dateConfig.endsWith("]")){
			String[] times = dateConfig.replace("[", "").replace("]", "").split(",");
			if(times.length == 2){
				long start = -1L, end = -1L;
				long now = ToolsDate.parseDate(ToolsDate.yyyy_MM_dd4, runPos).getTime();
				String matchResult = ToolsString.matcher(times[0], "\\{startDate(\\d{0,})\\}", 0);
				if(!ToolsString.isEmpty(matchResult)){
					matchResult = ToolsString.matcher(times[0], "\\{startDate(\\d{0,})\\}");
					int startDistinceDay = ToolsNumber.parseInt(matchResult, 0);
					start = now - startDistinceDay*24*60*60*1000L;
				}
				
				matchResult = ToolsString.matcher(times[1], "\\{endDate(\\d{0,})\\}", 0);
				if(!ToolsString.isEmpty(matchResult)){
					matchResult = ToolsString.matcher(times[1], "\\{endDate(\\d{0,})\\}");
					int endDistinceDay = ToolsNumber.parseInt(matchResult, 0);
					end = now - endDistinceDay*24*60*60*1000L;
				}
				
				StringBuilder sb = new StringBuilder("{");
				for(long i = start; i <= end; i+=24*60*60*1000L){
					String temp = ToolsDate.formatDate(ToolsDate.yyyy_MM_dd4, i);
					sb.append(temp).append(",");
				}
				sb.deleteCharAt(sb.lastIndexOf(",")).append("}");
				result.put("path", path.replace(dateConfig, sb.toString()));
			}
		}
		return result;
	}
}
