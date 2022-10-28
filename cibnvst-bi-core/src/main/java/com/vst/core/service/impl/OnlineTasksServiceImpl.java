package com.vst.core.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.json.simple.JSONObject;

import com.vst.common.tools.date.ToolsDate;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.core.cache.CacheSparkSqls;
import com.vst.core.cache.CacheType;
import com.vst.core.communal.bean.Video;
import com.vst.core.communal.bean.VideoTopic;
import com.vst.core.communal.sql.Column;
import com.vst.core.communal.sql.SqlFactory;
import com.vst.core.communal.type.OperateType;
import com.vst.core.service.CalculateTasksMgrService;
import com.vst.core.service.DBMgrService;
import com.vst.core.service.SparkSQLMgrService;

/**
 * @author joslyn
 * @date 2018年3月16日 下午3:52:36
 * @description
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class OnlineTasksServiceImpl extends CalculateTasksMgrService{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -8174909969144479586L;

	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(OnlineTasksServiceImpl.class);
	
	/**
	 * sparkSQL管理类
	 */
	private SparkSQLMgrService _sparkSQLMgrService;
	
	/**
	 * 一天时间
	 */
	private static final long ONE_DAY = 24*60*60*1000L;
	
	/**
	 * 构造器
	 * @param sparkSession
	 */
	public OnlineTasksServiceImpl(SparkSQLMgrService sparkSQLMgrService) {
		_sparkSQLMgrService = sparkSQLMgrService;
	}

	/**
	 * 执行任务
	 * @param lines
	 * @param sqls
	 * @param dbMgrService
	 */
	@Override
	public void runOnlineTasks(SparkSession sparkSession, JavaRDD<JSONObject> lines, List<SqlFactory> sqls, long batchTime) {
		if(sqls != null && !sqls.isEmpty()){
			// 获取系统当前时间
			long now = System.currentTimeMillis();
			final Broadcast<CacheType> cacheTypeBC = JavaSparkContext.fromSparkContext(sparkSession.sparkContext()).broadcast(CacheType.getInstance());
			for(SqlFactory sqlFactory : sqls){
				try {
					// 判断该sql是不是到了执行时间点
					if((!CacheSparkSqls.getInstance().isMakeUp(sqlFactory.getSqlId()) 
							&& (now - sqlFactory.getSqlRuntime() >= sqlFactory.getSqlInterval()*1000L))
							|| CacheSparkSqls.getInstance().isMakeUp(sqlFactory.getSqlId())){
						if(sqlFactory.getSqlRunModel() == 1){// 并行任务
							// 查看有没有子任务，如果有子任务，先执行子任务
							List<SqlFactory> children = sqlFactory.getChildrenSqlList();
							if(children != null && !children.isEmpty()){
								for(int i = 0; i < children.size(); i++){
									SqlFactory child = children.get(i);
									if(i == 0){
										processCalculateParallelTask(sparkSession , lines, child, cacheTypeBC, batchTime);
									}else{
										processCalculateParallelTask(sparkSession, lines, child, cacheTypeBC, batchTime);
									}
								}
								// 处理父级任务
								processCalculateParallelTask(sparkSession, lines, sqlFactory, cacheTypeBC, batchTime);
							}else{
								// 处理父级任务
								processCalculateParallelTask(sparkSession, lines, sqlFactory, cacheTypeBC, batchTime);
							}
						}else if(sqlFactory.getSqlRunModel() == 2){// 串行任务
							processCalculateSerialTasks(sparkSession, lines, sqlFactory, cacheTypeBC, batchTime);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			cacheTypeBC.unpersist();
		}
	}
	
	private void processCalculateSerialTasks(SparkSession sparkSession, JavaRDD<JSONObject> lines, 
			final SqlFactory sqlFactory, final Broadcast<CacheType> cacheTypeBC, long batchTime){
		List<SqlFactory> children = sqlFactory.getChildrenSqlList();
		if(children != null && !children.isEmpty()){
			Dataset<Row> childRow = null;
			for(int i = 0; i < children.size(); i++){
				SqlFactory child = children.get(i);
				Dataset<Row> tempRow = processCalculateSerialTask(sparkSession, lines, child, false, cacheTypeBC, batchTime);
				if(tempRow != null){
					if(childRow == null){
						childRow = tempRow;
					}else{
						org.apache.spark.sql.Column column = null;
						String[] joinKeys = ToolsString.checkEmpty(child.getSqlJoinKeys()).split("[|]");
						for(String joinKey : joinKeys){
							if(column == null){
								column = childRow.col(joinKey).equalTo(tempRow.col(joinKey));
							}else{
								column = column.and(childRow.col(joinKey).equalTo(tempRow.col(joinKey)));
							}
						}
						childRow = childRow.join(tempRow, column, "left");
						// 删除重复的关联键
						for(String joinKey : joinKeys){
							childRow = childRow.drop(tempRow.col(joinKey));
						}
					}
				}
			}
			
			String runPos = ToolsDate.formatDate("yyyyMMdd|HH|mm|ss", batchTime);
			childRow.createOrReplaceTempView(sqlFactory.getSqlTempTableName());
			String taskName = ("0".equals(sqlFactory.getSqlPid()) || "".equals(sqlFactory.getSqlPid())) ? "父任务" : "子任务";
			String sql = parseSql(sqlFactory.toSql(), runPos);
			logger.info("===>>> start executing sql = [" + taskName + "][" + sqlFactory.getSqlTypeName() + "][" + sqlFactory.getSqlName() + "][" + sqlFactory.getSqlDesc() + "][" + runPos + "][" + sql + "]");
			List<Row> result = sparkSession.sql(sql).collectAsList();
			int size = result != null ? result.size() : -1;
			logger.info("===>>> start insert data into table = [" + sqlFactory.getSqlTableName() + "][" + runPos + "][" + size + "]");
			String[] oldRunPos = sqlFactory.getSqlRunPosition();
			try {
				sqlFactory.setSqlRunPosition(runPos.split("[|]"));
				for(SqlFactory child : children){
					child.setSqlRunPosition(runPos.split("[|]"));
					child.setSqlRuntime(System.currentTimeMillis());
				}
				processData(result, sqlFactory, runPos.split("[|]")[0]);
				sqlFactory.setSqlRuntime(System.currentTimeMillis());
				// 同步状态到数据库中
				_sparkSQLMgrService.syncSQLIntoDB(sqlFactory);
			} catch (Exception e) {
				sqlFactory.setSqlRunPosition(oldRunPos);
				e.printStackTrace();
			}
		}
	}
	
	private static String parseSql(String sql, String runPos){
		long now = -1L, startTime = -1L, endTime = -1L, dayStart = -1L, dayEnd = -1L;
		int startDate = -1, endDate = -1;
		String[] pos = runPos.split("[|]");
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
		now = date.getTime();
		
		// 如果是时间戳格式的参数
		String matchResult = ToolsString.matcher(sql, "\\{startTime(\\d{0,})\\}", 0);
		while(!ToolsString.isEmpty(matchResult)){
			matchResult = ToolsString.matcher(sql, "\\{startTime(\\d{0,})\\}");
			int startDistinceDay = ToolsNumber.parseInt(matchResult, 0);
			startTime = now - startDistinceDay*ONE_DAY;
			sql = sql.replace("{startTime" + matchResult + "}", String.valueOf(startTime));
			matchResult = ToolsString.matcher(sql, "\\{startTime(\\d{0,})\\}", 0);
		}
		matchResult = ToolsString.matcher(sql, "\\{endTime(\\d{0,})\\}", 0);
		while(!ToolsString.isEmpty(matchResult)){
			matchResult = ToolsString.matcher(sql, "\\{endTime(\\d{0,})\\}");
			int endDistinceDay = ToolsNumber.parseInt(matchResult, 0);
			endTime = now - endDistinceDay*ONE_DAY;
			sql = sql.replace("{endTime" + matchResult + "}", String.valueOf(endTime));
			matchResult = ToolsString.matcher(sql, "\\{endTime(\\d{0,})\\}", 0);
		}
		
		// 如果是年月日格式的参数
		matchResult = ToolsString.matcher(sql, "\\{startDate(\\d{0,})\\}", 0);
		while(!ToolsString.isEmpty(matchResult)){
			matchResult = ToolsString.matcher(sql, "\\{startDate(\\d{0,})\\}");
			int startDistinceDay = ToolsNumber.parseInt(matchResult, 0);
			startDate = ToolsNumber.parseInt(ToolsDate.formatDate(ToolsDate.yyyy_MM_dd4, now - startDistinceDay*ONE_DAY));
			sql = sql.replace("{startDate" + matchResult + "}", String.valueOf(startDate));
			matchResult = ToolsString.matcher(sql, "\\{startDate(\\d{0,})\\}", 0);
		}
		matchResult = ToolsString.matcher(sql, "\\{endDate(\\d{0,})\\}", 0);
		while(!ToolsString.isEmpty(matchResult)){
			matchResult = ToolsString.matcher(sql, "\\{endDate(\\d{0,})\\}");
			int endDistinceDay = ToolsNumber.parseInt(matchResult, 0);
			endDate = ToolsNumber.parseInt(ToolsDate.formatDate(ToolsDate.yyyy_MM_dd4, now - endDistinceDay*ONE_DAY));
			sql = sql.replace("{endDate" + matchResult + "}", String.valueOf(endDate));
			matchResult = ToolsString.matcher(sql, "\\{endDate(\\d{0,})\\}", 0);
		}
		
		// 如果是开始天时间戳和结束天时间戳格式的参数
		matchResult = ToolsString.matcher(sql, "\\{dayStart(\\d{0,})\\}", 0);
		while(!ToolsString.isEmpty(matchResult)){
			matchResult = ToolsString.matcher(sql, "\\{dayStart(\\d{0,})\\}");
			int distinceDayStart = ToolsNumber.parseInt(matchResult, 0);
			dayStart = ToolsDate.getCurrStartTime(now - distinceDayStart*ONE_DAY);
			sql = sql.replace("{dayStart" + matchResult + "}", String.valueOf(dayStart));
			matchResult = ToolsString.matcher(sql, "\\{dayStart(\\d{0,})\\}", 0);
		}
		matchResult = ToolsString.matcher(sql, "\\{dayEnd(\\d{0,})\\}", 0);
		while(!ToolsString.isEmpty(matchResult)){
			matchResult = ToolsString.matcher(sql, "\\{dayEnd(\\d{0,})\\}");
			int distinceDayEnd = ToolsNumber.parseInt(matchResult, 0);
			dayEnd = ToolsDate.getCurrEndTime(now - distinceDayEnd*ONE_DAY);
			sql = sql.replace("{dayEnd" + matchResult + "}", String.valueOf(dayEnd));
			matchResult = ToolsString.matcher(sql, "\\{dayEnd(\\d{0,})\\}", 0);
		}
		return sql.replace("{date}", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd4, now));
	}
	
	/**
	 * 处理串行sql任务，即：每一个子sql都必须和父sql有结果关联
	 * @param lines
	 * @param sqlFactory
	 * @param cacheTypeBC
	 * @return
	 */
	private Dataset<Row> processCalculateSerialTask(SparkSession sparkSession, JavaRDD<JSONObject> lines, final SqlFactory sqlFactory, 
			boolean allowDeleteData, final Broadcast<CacheType> cacheTypeBC, long batchTime){
		String path = sqlFactory.getSqlDataPath();
		if(ToolsString.isEmpty(path)) return null;
		String runPos = ToolsDate.formatDate("yyyyMMdd|HH|mm|ss", batchTime);
        JavaRDD<JSONObject> jsonRDD = lines.filter(new Function<JSONObject, Boolean>() {
			private static final long serialVersionUID = 1L;
			@Override
            public Boolean call(JSONObject json) throws Exception {
				if(sqlFactory.getSqlTopic().equals(ToolsString.checkEmpty(json.get("kafkaTopic")))){
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
					return true;
				}
                return false;
            }
        });
		
		if(jsonRDD == null){
			logger.error("===>>> loading data null according to path = [" + path + "], sqlId = [" + sqlFactory.getSqlId() + "], sqlName = [" + sqlFactory.getSqlName() + "]");
			return null;
		}
			
		JavaRDD<Row> rdd = jsonRDD.filter(new Function<JSONObject, Boolean>() {
			private static final long serialVersionUID = 6556880097318354920L;
			@Override
			public Boolean call(JSONObject json) throws Exception {
				return sqlFactory.doChain(json);
			}
		}).map(new Function<JSONObject, Row>() {
			private static final long serialVersionUID = 5418498709971408767L;
			@Override
			public Row call(JSONObject json) throws Exception {
				List<Column> cols = sqlFactory.getColumns();
				Object[] objs = new Object[cols.size()];
				int index = 0;
				for(int i = 0; i < cols.size(); i++){
					Column c = cols.get(i);
					String name = c.getName();
					if(c.getOperateType() == OperateType.CUSTOM.getType()){
						String[] values = name.split("\\[@!@]");
						if(values.length != 2) continue;
						name = values[1];
					}else if(c.getOperateType() == OperateType.COUNT.getType()){
						continue;// count列不需要
					}
					objs[index++] = json.get(name);
				}
				return RowFactory.create(objs);
			}
		});
		// 创建spark临时表，用于后面的spark查询
		Dataset<Row> dataRow = sparkSession.createDataFrame(rdd, sqlFactory.getSchema());
		dataRow.createOrReplaceTempView(sqlFactory.getSqlTempTableName());
		String taskName = ("0".equals(sqlFactory.getSqlPid()) || "".equals(sqlFactory.getSqlPid())) ? "父任务" : "子任务";
		String sql = parseSql(sqlFactory.toSql(), runPos);
		logger.info("===>>> start executing sql = [" + taskName + "][" + sqlFactory.getSqlTypeName() + "][" + sqlFactory.getSqlName() + "][" + sqlFactory.getSqlDesc() + "][" + runPos + "][" + sql + "]");
		return sparkSession.sql(sql);
	}
	
	/**
	 * 处理并行任务，即：父sql和子sql没有结果上的关联关系
	 * @param lines
	 * @param sqlFactory
	 * @param cacheTypeBC
	 */
	private void processCalculateParallelTask(SparkSession sparkSession, JavaRDD<JSONObject> lines, final SqlFactory sqlFactory, 
			final Broadcast<CacheType> cacheTypeBC, long batchTime){
		// 在线任务，lines不能为空
        if (lines == null) return;
		String path = sqlFactory.getSqlDataPath();
		if(ToolsString.isEmpty(path)) return;
        String runPos = ToolsDate.formatDate("yyyyMMdd|HH|mm|ss", batchTime);
        JavaRDD<JSONObject> jsonRDD = lines.filter(new Function<JSONObject, Boolean>() {
			private static final long serialVersionUID = -8075687961700936091L;
			@Override
            public Boolean call(JSONObject json) throws Exception {
				if(sqlFactory.getSqlTopic().equals(ToolsString.checkEmpty(json.get("kafkaTopic")))){
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
					return true;
				}
                return false;
            }
        });
		
		if(jsonRDD == null){
			logger.error("===>>> loading data null according to path = [" + path + "]");
			return;
		}
	    JavaRDD<Row> rdd = jsonRDD.filter(new Function<JSONObject, Boolean>() {
			private static final long serialVersionUID = 6556880097318354920L;
			@Override
			public Boolean call(JSONObject json) throws Exception {
				return sqlFactory.doChain(json);
			}
		}).map(new Function<JSONObject, Row>() {
			private static final long serialVersionUID = 5418498709971408767L;
			@Override
			public Row call(JSONObject json) throws Exception {
				List<Column> cols = sqlFactory.getColumns();
				Object[] objs = new Object[cols.size()];
				int index = 0;
				for(int i = 0; i < cols.size(); i++){
					Column c = cols.get(i);
					String name = c.getName();
					if(c.getOperateType() == OperateType.CUSTOM.getType()){
						String[] values = name.split("\\[@!@]");
						if(values.length != 2) continue;
						name = values[1];
					}else if(c.getOperateType() == OperateType.COUNT.getType()){
						continue;// count列不需要
					}
					objs[index++] = json.get(name);
				}
				return RowFactory.create(objs);
			}
		});
		
		// 创建spark临时表，用于后面的spark查询
		Dataset<Row> dataRow = sparkSession.createDataFrame(rdd, sqlFactory.getSchema());
		dataRow.createOrReplaceTempView(sqlFactory.getSqlTempTableName());
		String taskName = ("0".equals(sqlFactory.getSqlPid()) || "".equals(sqlFactory.getSqlPid())) ? "父任务" : "子任务";
		logger.info("===>>> start executing sql = [" + taskName + "][" + sqlFactory.getSqlTypeName() + "][" + sqlFactory.getSqlName() + "][" + sqlFactory.getSqlDesc() + "][" + runPos + "][" + sqlFactory.toSql() + "]");
		List<Row> result = sparkSession.sql(sqlFactory.toSql()).collectAsList();
		int size = result != null ? result.size() : -1;
		logger.info("===>>> start insert data into table = [" + sqlFactory.getSqlTableName() + "][" + runPos + "][" + size + "]");
		String[] oldRunPos = sqlFactory.getSqlRunPosition();
		try {
			sqlFactory.setSqlRunPosition(runPos.split("[|]"));
			processData(result, sqlFactory, runPos.split("[|]")[0]);
			sqlFactory.setSqlRuntime(System.currentTimeMillis());
			// 同步状态到数据库中
			_sparkSQLMgrService.syncSQLIntoDB(sqlFactory);
		} catch (Exception e) {
			sqlFactory.setSqlRunPosition(oldRunPos);
			e.printStackTrace();
		}
    }
	
	/**
	 * 处理数据
	 * @param result
	 * @param sqlFactory
	 */
	private void processData(List<Row> result, SqlFactory sqlFactory, String date) throws Exception{
		if(result != null && !result.isEmpty() && sqlFactory != null){
			DBMgrService dBMgrService = DBMgrService.createDBSession(sqlFactory);
			dBMgrService.save(result, ToolsNumber.parseInt(date), sqlFactory);
		}
	}
}
