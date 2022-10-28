package com.vst.core.service.impl;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
import com.vst.core.communal.sql.Column;
import com.vst.core.communal.sql.SqlFactory;
import com.vst.core.communal.type.OperateType;
import com.vst.core.service.AbstractLoaderService;
import com.vst.core.service.CalculateTasksMgrService;
import com.vst.core.service.DBMgrService;
import com.vst.core.service.SparkSQLMgrService;

/**
 * @author weiwei(joslyn)
 * @date 2017-11-1 下午02:33:57
 * @decription
 * @version
 */
@SuppressWarnings("unchecked")
public class OfflineTasksServiceImpl extends CalculateTasksMgrService{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 8428776597523424682L;

	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(OfflineTasksServiceImpl.class);

	/**
	 * sparkSession管理
	 */
	private SparkSession _sparkSession;

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
	public OfflineTasksServiceImpl(SparkSession sparkSession, SparkSQLMgrService sparkSQLMgrService) {
		this._sparkSession = sparkSession;
		_sparkSQLMgrService = sparkSQLMgrService;
	}

	/**
	 * 任务分类
	 * @param sqls 任务列表
	 * @param taskLevel 任务类别
	 */
	private List<SqlFactory> sqlClassification(List<SqlFactory> sqls, int taskLevel) {
		List<SqlFactory> returnSqls = new ArrayList<>();
		for (SqlFactory sql : sqls) {
			if (taskLevel == 1 && sql.getPriority() == -1) {
				returnSqls.add(sql);
			} else if (taskLevel == 2 && sql.getPriority() > -1) {
				returnSqls.add(sql);
			}
		}

		// 如果是优先级任务需要排序，越大越靠前
		if (taskLevel == 2) {
			Collections.sort(returnSqls, new Comparator<SqlFactory>() {
				@Override
				public int compare(SqlFactory o1, SqlFactory o2) {
					if (o1.getPriority() - o2.getPriority() > 0) {
						return -1;
					} else if (o1.getPriority() - o2.getPriority() < 0) {
						return 1;
					}
					return 0;
				}
			});
		}
		return returnSqls;
	}

	/**
	 * 执行优先级任务
	 * @param now 执行时间
	 * @param sqls  优先级的任务列表
	 * @param cacheTypeBC   影片详情广播变量
	 * 比如有a、b、c、d、e任务，会分别执行：a,b,a,c,a,b,d,a,b,c,e,a,b,c,d
	 */
	private void execPriorityTask(long now, List<SqlFactory> sqls, Broadcast<CacheType> cacheTypeBC) {
		int index = 0;
		int currentIndex = -1;  //标志位，相同任务跳过执行
		while (index != sqls.size()) {
			if (index == currentIndex) {
				index += 1;
				continue;
			}
			logger.info("===>>> start priority sql = [" + sqls.get(index).getSqlName() + "][" + sqls.get(index).getSqlTypeName() + "][" + sqls.get(index).getSqlDesc() + "][" + sqls.get(index).getPriority() + "]");
			execSql(now, sqls.get(index), cacheTypeBC);
			if (index == 0 || currentIndex > index) {
				index += 1;
				continue;
			}
			currentIndex = index;
			index = 0;
		}
	}

	/**
	 * 具体执行者
	 * @param now 执行时间
	 * @param sqlFactory 任务
	 * @param cacheTypeBC 广播变量
	 */
	private boolean execSql(long now, SqlFactory sqlFactory, Broadcast<CacheType> cacheTypeBC) {
		try {
			// 判断该sql是不是到了执行时间点
			if ((!CacheSparkSqls.getInstance().isMakeUp(sqlFactory.getSqlId())
					&& (now - sqlFactory.getSqlRuntime() >= sqlFactory.getSqlInterval() * 1000L))
					|| CacheSparkSqls.getInstance().isMakeUp(sqlFactory.getSqlId())) {
				if (sqlFactory.getSqlRunModel() == 1) {// 并行任务
					// 查看有没有子任务，如果有子任务，先执行子任务
					List<SqlFactory> children = sqlFactory.getChildrenSqlList();
					if (children != null && !children.isEmpty()) {
						for (int i = 0; i < children.size(); i++) {
							SqlFactory child = children.get(i);
							if (i == 0) {
								processCalculateParallelTask(child, true, cacheTypeBC);
							} else {
								processCalculateParallelTask(child, false, cacheTypeBC);
							}
						}
						// 处理父级任务
						processCalculateParallelTask(sqlFactory, false, cacheTypeBC);
					} else {
						// 处理父级任务
						processCalculateParallelTask(sqlFactory, true, cacheTypeBC);
					}
					return true;
				} else if (sqlFactory.getSqlRunModel() == 2) {// 串行任务
					processCalculateSerialTasks(sqlFactory, cacheTypeBC);
					return true;
				}
			}
		} catch (Exception e) {
			logger.error("ERROR: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 轮询普通任务
	 *
	 * @param sqls  全部任务列表
	 */
	@Override
	public void runOfflineTasks(List<SqlFactory> sqls) {
		if (sqls != null && !sqls.isEmpty()) {
			// 获取系统当前时间
			long now = System.currentTimeMillis();
			final Broadcast<CacheType> cacheTypeBC = JavaSparkContext.fromSparkContext(_sparkSession.sparkContext()).broadcast(CacheType.getInstance());
			List<SqlFactory> generalSqls = sqlClassification(sqls, 1);     //普通任务
			List<SqlFactory> prioritySqls = sqlClassification(sqls, 2);    //优先级任务
			for (SqlFactory sqlFactory : generalSqls) {
				boolean execFlag = execSql(now, sqlFactory, cacheTypeBC);
				if(execFlag)//有效的执行普通逻辑才执行优先级任务
					execPriorityTask(now,prioritySqls,cacheTypeBC); //新增逻辑，每执行一个普通任务都要执行优先级任务
			}
			cacheTypeBC.unpersist();
		}
	}

	private void processCalculateSerialTasks(final SqlFactory sqlFactory, final Broadcast<CacheType> cacheTypeBC){
		List<SqlFactory> children = sqlFactory.getChildrenSqlList();
		if(children != null && !children.isEmpty()){
			Dataset<Row> childRow = null;
			for(int i = 0; i < children.size(); i++){
				SqlFactory child = children.get(i);
				JSONObject tempJson = processCalculateSerialTask(child, false, cacheTypeBC);
				if(tempJson != null){
					Dataset<Row> tempRow = (Dataset<Row>) tempJson.get("dataRow");
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
			}

			AbstractLoaderService loader = AbstractLoaderService.createLoader(sqlFactory.getSqlDataPath());
			if(loader == null) {
				logger.error("===>>> there is no loader according to path = [" + sqlFactory.getSqlDataPath() + "]");
				return;
			}
			// 计算本次的计算节点位置
			JSONObject pathJson = loader.processLoadPath(sqlFactory, true);
			if(pathJson == null) return;
			String runPos = ToolsString.checkEmpty(pathJson.get("runPos"));
			boolean isDel = Boolean.valueOf(ToolsString.checkEmpty(pathJson.get("isDel")));
			childRow.createOrReplaceTempView(sqlFactory.getSqlTempTableName());
			String taskName = ("0".equals(sqlFactory.getSqlPid()) || "".equals(sqlFactory.getSqlPid())) ? "父任务" : "子任务";
			String sql = parseSql(sqlFactory.toSql(), runPos);
			logger.info("===>>> start executing sql = [" + taskName + "][" + sqlFactory.getSqlTypeName() + "][" + sqlFactory.getSqlName() + "][" + sqlFactory.getSqlDesc() + "][" + runPos + "][" + sql + "]");
			List<Row> result = _sparkSession.sql(sql).collectAsList();
			int size = result != null ? result.size() : -1;
			logger.info("===>>> start insert data into table = [" + sqlFactory.getSqlTableName() + "][" + runPos + "][" + size + "]");
			if(result != null && !result.isEmpty() && isDel){
				// 这里需要删除掉当天上一次计算周期算出的结果
				processClearData(sqlFactory, runPos.split("[|]")[0]);
			}
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
	private JSONObject processCalculateSerialTask(final SqlFactory sqlFactory, boolean allowDeleteData, final Broadcast<CacheType> cacheTypeBC){
		String path = sqlFactory.getSqlDataPath();
		if(ToolsString.isEmpty(path)) return null;
		AbstractLoaderService loader = AbstractLoaderService.createLoader(path);
		if(loader == null) {
			logger.error("===>>> there is no loader according to path = [" + path + "], sqlId = [" + sqlFactory.getSqlId() + "], sqlName = [" + sqlFactory.getSqlName() + "]");
			return null;
		}

		// 计算本次的计算节点位置
		JSONObject pathJson = loader.processLoadPath(sqlFactory, allowDeleteData);
		if(pathJson == null) return null;
		path = ToolsString.checkEmpty(pathJson.get("path"));
		String runPos = ToolsString.checkEmpty(pathJson.get("runPos"));

		JavaRDD<JSONObject> jsonRDD = loader.load(_sparkSession, path, sqlFactory, cacheTypeBC);
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
		Dataset<Row> dataRow = _sparkSession.createDataFrame(rdd, sqlFactory.getSchema());
		dataRow.createOrReplaceTempView(sqlFactory.getSqlTempTableName());
		String taskName = ("0".equals(sqlFactory.getSqlPid()) || "".equals(sqlFactory.getSqlPid())) ? "父任务" : "子任务";
		String sql = parseSql(sqlFactory.toSql(), runPos);
		logger.info("===>>> start executing sql = [" + taskName + "][" + sqlFactory.getSqlTypeName() + "][" + sqlFactory.getSqlName() + "][" + sqlFactory.getSqlDesc() + "][" + runPos + "][" + sql + "]");
		pathJson.put("dataRow", _sparkSession.sql(sql));
		return pathJson;
	}

	/**
	 * 处理并行任务，即：父sql和子sql没有结果上的关联关系
	 * @param sqlFactory
	 * @param allowDeleteData
	 * @param cacheTypeBC
	 */
	private void processCalculateParallelTask(final SqlFactory sqlFactory, boolean allowDeleteData, final Broadcast<CacheType> cacheTypeBC){
		String path = sqlFactory.getSqlDataPath();
		if(ToolsString.isEmpty(path)) return;
		AbstractLoaderService loader = AbstractLoaderService.createLoader(path);
		if(loader == null) {
			logger.error("===>>> there is no loader according to path = [" + path + "]");
			return;
		}
		// 计算本次的计算节点位置
		JSONObject pathJson = loader.processLoadPath(sqlFactory, allowDeleteData);
		if(pathJson == null) return;
		path = ToolsString.checkEmpty(pathJson.get("path"));
		String runPos = ToolsString.checkEmpty(pathJson.get("runPos"));
		boolean isDel = Boolean.valueOf(ToolsString.checkEmpty(pathJson.get("isDel")));
		JavaRDD<JSONObject> jsonRDD = loader.load(_sparkSession, path, sqlFactory, cacheTypeBC);

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
		Dataset<Row> dataRow = _sparkSession.createDataFrame(rdd, sqlFactory.getSchema());
		dataRow.createOrReplaceTempView(sqlFactory.getSqlTempTableName());
		String taskName = ("0".equals(sqlFactory.getSqlPid()) || "".equals(sqlFactory.getSqlPid())) ? "父任务" : "子任务";
		String sql = parseSql(sqlFactory.toSql(), runPos);
		logger.info("===>>> start executing sql = [" + taskName + "][" + sqlFactory.getSqlTypeName() + "][" + sqlFactory.getSqlName() + "][" + sqlFactory.getSqlDesc() + "][" + runPos + "][" + sql + "]");
		List<Row> result = _sparkSession.sql(sql).collectAsList();
		int size = result != null ? result.size() : -1;
		logger.info("===>>> start insert data into table = [" + sqlFactory.getSqlTableName() + "][" + runPos + "][" + size + "]");
		if(result != null && !result.isEmpty() && isDel){
			// 这里需要删除掉当天上一次计算周期算出的结果
			processClearData(sqlFactory, runPos.split("[|]")[0]);
		}
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

	private void processClearData(SqlFactory sqlFactory, String date){
		if(sqlFactory != null){
			DBMgrService.createDBSession(sqlFactory).delete(sqlFactory, date);
		}
	}

}
