package com.vst.core.service.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.protobuf.ProtobufUtil;
import org.apache.hadoop.hbase.util.Base64;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.SparkSession;
import org.json.simple.JSONObject;

import scala.Tuple2;

import com.vst.common.tools.date.ToolsDate;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.core.cache.CacheSparkSqls;
import com.vst.core.cache.CacheType;
import com.vst.core.communal.bean.Video;
import com.vst.core.communal.bean.VideoTopic;
import com.vst.core.communal.sql.Column;
import com.vst.core.communal.sql.SqlFactory;
import com.vst.core.communal.type.ColumnType;
import com.vst.core.communal.type.OperateType;
import com.vst.core.communal.util.PropertiesReader;
import com.vst.core.service.AbstractLoaderService;

/**
 * @author joslyn
 * @date 2017年12月19日 下午5:25:02
 * @description
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class HbaseLoaderServiceImpl extends AbstractLoaderService implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 3401761440997122827L;

	/**
	 * 写日志
	 */
	private static final Logger logger = Logger.getLogger(HbaseLoaderServiceImpl.class);

	/**
	 * 一天时间
	 */
	private static final long ONE_DAY = 24*60*60*1000L;

	/**
	 * 加载数据的抽象方法
	 * @param sparkSession spark会话管理类
	 * @param path 数据加载的path，其中日期已经替换
	 * 		  path格式，hbase:/{tableName}/{key}/[{date90},{date}],
	 * 		  hbase:/{tableName}/{key}/[{time90},{time}],
	 * 		  hbase:/{tableName}/{key}/[{dayStart90},{dayEnd}]
	 * @param sqlFactory sql的工厂类
	 * @param cacheTypeBC 类型缓存
	 * @return
	 */
	@Override
	public JavaRDD<JSONObject> load(SparkSession sparkSession, String path,
									final SqlFactory sqlFactory, final Broadcast<CacheType> cacheTypeBC) {
		logger.info("===>>> start loading sql data from hbase. path = [" + path + "]");
		String[] values = sqlFactory.getSqlDataPath().split("/");
		String namespace = PropertiesReader.getInstance().getProperty("hbase_default_namespace");
		final String family = PropertiesReader.getInstance().getProperty("hbase_default_family");
		String hbaseConfDir = PropertiesReader.getInstance().getProperty("hbase_conf_dir");
		Configuration conf = null;
		if(values.length == 4) {
			// 解析后的path
			String[] valuesParsed = path.split("/");
			// 解析url
			String dateConfig = values[3];
			String dateConfigParsed = valuesParsed[3];
			long startTime = -1L, endTime = -1L, dayStart = -1L, dayEnd = -1L;
			int startDate = -1, endDate = -1;
			if(dateConfig.startsWith("[") && dateConfig.endsWith("]")){
				String[] times = dateConfig.replace("[", "").replace("]", "").split(",");
				String[] timesParsed = dateConfigParsed.replace("[", "").replace("]", "").split(",");
				if(times.length == 1){// 如果只有一个时间参数，表示等于
					String matchResult = ToolsString.matcher(times[0], "\\{startTime(\\d{0,})\\}");
					if(!ToolsString.isEmpty(matchResult)){
						startTime = ToolsNumber.parseLong(timesParsed[0]);
						endTime = -2L;
					}else{
						matchResult = ToolsString.matcher(times[0], "\\{startDate(\\d{0,})\\}");
						if(!ToolsString.isEmpty(matchResult)){
							startDate = ToolsNumber.parseInt(timesParsed[0]);
							endDate = -2;
						}else{
							matchResult = ToolsString.matcher(times[0], "\\{dayStart(\\d{0,})\\}");
							dayStart = ToolsNumber.parseLong(timesParsed[0]);
							dayEnd = -2L;
						}
					}
				}else if(times.length == 2){// 如果有两个时间参数，表示时间范围
					String matchResult = ToolsString.matcher(times[0], "\\{startTime(\\d{0,})\\}");
					if(!ToolsString.isEmpty(matchResult)){
						startTime = ToolsNumber.parseLong(timesParsed[0]);
					}else{
						matchResult = ToolsString.matcher(times[0], "\\{startDate(\\d{0,})\\}");
						if(!ToolsString.isEmpty(matchResult)){
							startDate = ToolsNumber.parseInt(timesParsed[0]);
						}else{
							matchResult = ToolsString.matcher(times[0], "\\{dayStart(\\d{0,})\\}");
							dayStart = ToolsNumber.parseLong(timesParsed[0]);
						}
					}

					matchResult = ToolsString.matcher(times[1], "\\{endTime(\\d{0,})\\}");
					if(!ToolsString.isEmpty(matchResult)){
						endTime = ToolsNumber.parseLong(timesParsed[1]);
					}else{
						matchResult = ToolsString.matcher(times[1], "\\{endDate(\\d{0,})\\}");
						if(!ToolsString.isEmpty(matchResult)){
							endDate = ToolsNumber.parseInt(timesParsed[1]);
						}else{
							matchResult = ToolsString.matcher(times[1], "\\{dayEnd(\\d{0,})\\}");
							dayEnd = ToolsNumber.parseLong(timesParsed[1]);
						}
					}
				}
			}else{
				logger.error("path format is error! path = [" + path + "]");
				return null;
			}

			logger.info("===>>> time[" + startTime + "," + endTime + "], day[" + dayStart + "," + dayEnd + "], date[" + startDate + "," + endDate + "]");
			FilterList filters = new FilterList();
			// 表明格式是[{time(\\d+)}]
			if(startTime >= 0L && endTime == -2L){
				filters.addFilter(new SingleColumnValueFilter(family.getBytes(), values[2].getBytes(), CompareOp.EQUAL, Bytes.toBytes(String.valueOf(startTime))));
			}else if(startDate > 0 && endDate == -2){// 表明格式是[{date(\\d+)}]
				filters.addFilter(new SingleColumnValueFilter(family.getBytes(), values[2].getBytes(), CompareOp.EQUAL, Bytes.toBytes(String.valueOf(startDate))));
			}else if(dayStart > 0L && dayEnd == -2L){
				filters.addFilter(new SingleColumnValueFilter(family.getBytes(), values[2].getBytes(), CompareOp.EQUAL, Bytes.toBytes(String.valueOf(dayStart))));
			}else if(startTime >= 0 && endTime == -1L){// 表明格式是[{time(\\d+)}, -1]
				filters.addFilter(new SingleColumnValueFilter(family.getBytes(), values[2].getBytes(), CompareOp.GREATER_OR_EQUAL, Bytes.toBytes(String.valueOf(startTime))));
			}else if(startDate > 0 && endDate == -1){// 表明格式是[{date(\\d+)}, -1]
				filters.addFilter(new SingleColumnValueFilter(family.getBytes(), values[2].getBytes(), CompareOp.GREATER_OR_EQUAL, Bytes.toBytes(String.valueOf(startDate))));
			}else if(dayStart > 0L && dayEnd == -1L){// 表明格式是[{dayStart(\\d+)}, -1]
				filters.addFilter(new SingleColumnValueFilter(family.getBytes(), values[2].getBytes(), CompareOp.GREATER_OR_EQUAL, Bytes.toBytes(String.valueOf(dayStart))));
			}else if(startTime == -1L && endTime > 0L){// 表明格式是[-1, {endStart(\\d+)}]
				filters.addFilter(new SingleColumnValueFilter(family.getBytes(), values[2].getBytes(), CompareOp.LESS_OR_EQUAL, Bytes.toBytes(String.valueOf(endTime))));
			}else if(startDate == -1L && endDate > 0){// 表明格式是[-1, {endDate(\\d+)}]
				filters.addFilter(new SingleColumnValueFilter(family.getBytes(), values[2].getBytes(), CompareOp.LESS_OR_EQUAL, Bytes.toBytes(String.valueOf(endDate))));
			}else if(dayStart == -1L && dayEnd > 0L){// 表明格式是[-1, {dayEnd(\\d+)}]
				filters.addFilter(new SingleColumnValueFilter(family.getBytes(), values[2].getBytes(), CompareOp.LESS_OR_EQUAL, Bytes.toBytes(String.valueOf(dayEnd))));
			}else if(startTime >= 0L && endTime > 0 && startTime < endTime){
				filters.addFilter(new SingleColumnValueFilter(family.getBytes(), values[2].getBytes(), CompareOp.GREATER_OR_EQUAL, Bytes.toBytes(String.valueOf(startTime))));
				filters.addFilter(new SingleColumnValueFilter(family.getBytes(), values[2].getBytes(), CompareOp.LESS_OR_EQUAL, Bytes.toBytes(String.valueOf(endTime))));
			}else if(startDate > 0 && endDate > 0 && startDate < endDate){
				filters.addFilter(new SingleColumnValueFilter(family.getBytes(), values[2].getBytes(), CompareOp.GREATER_OR_EQUAL, Bytes.toBytes(String.valueOf(startDate))));
				filters.addFilter(new SingleColumnValueFilter(family.getBytes(), values[2].getBytes(), CompareOp.LESS_OR_EQUAL, Bytes.toBytes(String.valueOf(endDate))));
			}else if(dayStart > 0 && dayEnd > 0 && dayStart < dayEnd){
				filters.addFilter(new SingleColumnValueFilter(family.getBytes(), values[2].getBytes(), CompareOp.GREATER_OR_EQUAL, Bytes.toBytes(String.valueOf(dayStart))));
				filters.addFilter(new SingleColumnValueFilter(family.getBytes(), values[2].getBytes(), CompareOp.LESS_OR_EQUAL, Bytes.toBytes(String.valueOf(dayEnd))));
			}else{
				return null;
			}

			conf = HBaseConfiguration.create();
			conf.addResource(new Path(hbaseConfDir + "/hbase-site.xml"));
			conf.addResource(new Path(hbaseConfDir + "/core-site.xml"));
			conf.addResource(new Path(hbaseConfDir + "/hdfs-site.xml"));
			conf.set(TableInputFormat.INPUT_TABLE, namespace + ":" + values[1]);
			Scan scan = new Scan();
			scan.setFilter(filters);
			try {
				org.apache.hadoop.hbase.protobuf.generated.ClientProtos.Scan proto = ProtobufUtil.toScan(scan);
				conf.set(TableInputFormat.SCAN, Base64.encodeBytes(proto.toByteArray()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(values.length == 2){
			conf = HBaseConfiguration.create();
			conf.addResource(new Path(hbaseConfDir + "/hbase-site.xml"));
			conf.addResource(new Path(hbaseConfDir + "/core-site.xml"));
			conf.addResource(new Path(hbaseConfDir + "/hdfs-site.xml"));
			conf.set(TableInputFormat.INPUT_TABLE, namespace + ":" + values[1]);
		}else{
			logger.error("path format is error! path = [" + path + "]");
			return null;
		}

		@SuppressWarnings("resource")
		JavaSparkContext jsc = new JavaSparkContext(sparkSession.sparkContext());
		JavaPairRDD<ImmutableBytesWritable, Result> javaPairRdd = jsc.newAPIHadoopRDD(conf, TableInputFormat.class, ImmutableBytesWritable.class, Result.class);
		JavaRDD<JSONObject> rdd = javaPairRdd.map(new Function<Tuple2<ImmutableBytesWritable,Result>, JSONObject>() {
			private static final long serialVersionUID = 5784790198189817799L;
			@Override
			public JSONObject call(Tuple2<ImmutableBytesWritable, Result> t)
					throws Exception {
				JSONObject json = new JSONObject();
				for(Column c : sqlFactory.getColumns()){
					if(c.getOperateType() == OperateType.COUNT.getType()) continue;
					String name = c.getName();
					if(c.getOperateType() == OperateType.CUSTOM.getType()){
						String[] values = name.split("\\[@!@]");
						if(values.length > 1) name = values[1];
					}
					Cell cell = t._2.getColumnLatestCell(Bytes.toBytes(family), Bytes.toBytes(name));
					if(cell == null) continue;
					if(c.getDataType() == ColumnType.String.getType()){
						json.put(name, ToolsString.checkEmpty(Bytes.toString(CellUtil.cloneValue(cell))));
					}else if(c.getDataType() == ColumnType.Int.getType()){
						json.put(name, ToolsNumber.parseLong(Bytes.toString(CellUtil.cloneValue(cell))));
					}else if(c.getDataType() == ColumnType.Float.getType()){
						json.put(name, ToolsNumber.parseDouble(Bytes.toString(CellUtil.cloneValue(cell))));
					}else if(c.getDataType() == ColumnType.BOOLEAN.getType()){
						json.put(name, Boolean.valueOf(Bytes.toString(CellUtil.cloneValue(cell))));
					}
				}

				if(sqlFactory.isSqlIsFormat()){
					String key = ToolsString.checkEmpty(json.get("nameId"));
					Video video = cacheTypeBC.value().getVideo(key);
					if(video != null){
						json.put("cid", Long.valueOf(video.getCid()));
						json.put("specId", Long.valueOf(video.getSpecialType()));
						json.put("name", video.getTitle());
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
	public JSONObject processLoadPath(SqlFactory sqlFactory, boolean allowDeleteData) {
		JSONObject result = new JSONObject();
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
		// 如果是上一次计算位置大于等于当前时间，就以当前时间天为准；如果是小于当前天时间，就说明需要补数据
		if(runPositionTime >= ToolsDate.getCurrStartTime()){
			runPos = ToolsDate.getCurrDate(ToolsDate.yyyy_MM_dd4);
			if(allowDeleteData) isDel = true;
			CacheSparkSqls.getInstance().setMakeUp(makeupKey, false);// 给非补充数据标识
		}else{
			// 如果是刚过凌晨，还需要再跑一次昨天的数据，才能保证完整
			long distince = ToolsDate.getCurrStartTime() - sqlFactory.getSqlRuntime();
			if(distince > 0){
				runPos = ToolsDate.formatDate(ToolsDate.yyyy_MM_dd4, runPositionTime);
				if(allowDeleteData) isDel = true;
			}else{
				runPos = ToolsDate.formatDate(ToolsDate.yyyy_MM_dd4, runPositionTime + 24*60*60*1000L);
			}
			CacheSparkSqls.getInstance().setMakeUp(makeupKey, true);// 给正在补充数据标识
		}

		String path = sqlFactory.getSqlDataPath();
		String[] values = path.split("/");
		if(values.length == 4) {
			// 解析url
			String dateConfig = values[3];
			long now = -1L, startTime = -1L, endTime = -1L, dayStart = -1L, dayEnd = -1L;
			int startDate = -1, endDate = -1;
			date = ToolsDate.parseDate(ToolsDate.yyyy_MM_dd4, runPos);
			now = date.getTime();
			if(dateConfig.startsWith("[") && dateConfig.endsWith("]")){
				String[] times = dateConfig.replace("[", "").replace("]", "").split(",");
				if(times.length == 1){// 如果只有一个时间参数，表示等于
					String matchResult = ToolsString.matcher(times[0], "\\{startTime(\\d{0,})\\}", 0);
					if(!ToolsString.isEmpty(matchResult)){
						matchResult = ToolsString.matcher(times[0], "\\{startTime(\\d{0,})\\}");
						int startDistinceDay = ToolsNumber.parseInt(matchResult, 0);
						startTime = now - startDistinceDay*ONE_DAY;
						path = path.replace("{startTime" + matchResult + "}", String.valueOf(startTime));
					}else{
						matchResult = ToolsString.matcher(times[0], "\\{startDate(\\d{0,})\\}", 0);
						if(!ToolsString.isEmpty(matchResult)){
							matchResult = ToolsString.matcher(times[0], "\\{startDate(\\d{0,})\\}");
							int startDistinceDay = ToolsNumber.parseInt(matchResult, 0);
							startDate = ToolsNumber.parseInt(ToolsDate.formatDate(ToolsDate.yyyy_MM_dd4, now - startDistinceDay*ONE_DAY));
							path = path.replace("{startDate" + matchResult + "}", String.valueOf(startDate));
						}else{
							matchResult = ToolsString.matcher(times[0], "\\{dayStart(\\d{0,})\\}", 0);
							if(!ToolsString.isEmpty(matchResult)){
								matchResult = ToolsString.matcher(times[0], "\\{dayStart(\\d{0,})\\}");
								int distinceDayStart = ToolsNumber.parseInt(matchResult, 0);
								dayStart = ToolsDate.getCurrStartTime(now - distinceDayStart*ONE_DAY);
								path = path.replace("{dayStart" + matchResult + "}", String.valueOf(dayStart));
							}
						}
					}
				}else if(times.length == 2){// 如果有两个时间参数，表示时间范围
					String matchResult = ToolsString.matcher(times[0], "\\{startTime(\\d{0,})\\}", 0);
					if(!ToolsString.isEmpty(matchResult)){
						matchResult = ToolsString.matcher(times[0], "\\{startTime(\\d{0,})\\}");
						int startDistinceDay = ToolsNumber.parseInt(matchResult, 0);
						startTime = now - startDistinceDay*ONE_DAY;
						path = path.replace("{startTime" + matchResult + "}", String.valueOf(startTime));
					}else{
						matchResult = ToolsString.matcher(times[0], "\\{startDate(\\d{0,})\\}", 0);
						if(!ToolsString.isEmpty(matchResult)){
							matchResult = ToolsString.matcher(times[0], "\\{startDate(\\d{0,})\\}");
							int startDistinceDay = ToolsNumber.parseInt(matchResult, 0);
							startDate = ToolsNumber.parseInt(ToolsDate.formatDate(ToolsDate.yyyy_MM_dd4, now - startDistinceDay*ONE_DAY));
							path = path.replace("{startDate" + matchResult + "}", String.valueOf(startDate));
						}else{
							matchResult = ToolsString.matcher(times[0], "\\{dayStart(\\d{0,})\\}", 0);
							if(!ToolsString.isEmpty(matchResult)){
								matchResult = ToolsString.matcher(times[0], "\\{dayStart(\\d{0,})\\}");
								int distinceDayStart = ToolsNumber.parseInt(matchResult, 0);
								dayStart = ToolsDate.getCurrStartTime(now - distinceDayStart*ONE_DAY);
								path = path.replace("{dayStart" + matchResult + "}", String.valueOf(dayStart));
							}
						}
					}

					matchResult = ToolsString.matcher(times[1], "\\{endTime(\\d{0,})\\}", 0);
					if(!ToolsString.isEmpty(matchResult)){
						matchResult = ToolsString.matcher(times[1], "\\{endTime(\\d{0,})\\}");
						int endDistinceDay = ToolsNumber.parseInt(matchResult, 0);
						endTime = now - endDistinceDay*ONE_DAY;
						path = path.replace("{endTime" + matchResult + "}", String.valueOf(endTime));
					}else{
						matchResult = ToolsString.matcher(times[1], "\\{endDate(\\d{0,})\\}", 0);
						if(!ToolsString.isEmpty(matchResult)){
							matchResult = ToolsString.matcher(times[1], "\\{endDate(\\d{0,})\\}");
							int endDistinceDay = ToolsNumber.parseInt(matchResult, 0);
							endDate = ToolsNumber.parseInt(ToolsDate.formatDate(ToolsDate.yyyy_MM_dd4, now - endDistinceDay*ONE_DAY));
							path = path.replace("{endDate" + matchResult + "}", String.valueOf(endDate));
						}else{
							matchResult = ToolsString.matcher(times[1], "\\{dayEnd(\\d{0,})\\}", 0);
							if(!ToolsString.isEmpty(matchResult)){
								matchResult = ToolsString.matcher(times[1], "\\{dayEnd(\\d{0,})\\}");
								int distinceDayEnd = ToolsNumber.parseInt(matchResult, 0);
								dayEnd = ToolsDate.getCurrEndTime(now - distinceDayEnd*ONE_DAY);
								path = path.replace("{dayEnd" + matchResult + "}", String.valueOf(dayEnd));
							}
						}
					}
				}
			}else{
				logger.error("path format is error! path = [" + path + "]");
				return null;
			}
			logger.info("===>>> time[" + startTime + "," + endTime + "], day[" + dayStart + "," + dayEnd + "], date[" + startDate + "," + endDate + "]");
		}else if(values.length == 2){// 这种情况是不带时间参数的，不对path做任何操作
		}else{
			logger.error("path format is error! path = [" + path + "]");
			return null;
		}

		result.put("isDel", isDel);
		result.put("runPos", runPos);
		result.put("path", path);
		return result;
	}
}
