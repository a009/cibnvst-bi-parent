package com.vst.core.cache;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;

import com.vst.common.tools.file.ToolsFile;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.core.communal.sql.Column;
import com.vst.core.communal.sql.GroupBy;
import com.vst.core.communal.sql.OrderBy;
import com.vst.core.communal.sql.SaveSqlInfo;
import com.vst.core.communal.sql.SqlFactory;
import com.vst.core.communal.sql.sqlFilter.Action;
import com.vst.core.communal.sql.sqlFilter.Filter;
import com.vst.core.communal.sql.sqlFilter.FilterChain;
import com.vst.core.communal.type.SqlType;
import com.vst.core.communal.util.PropertiesReader;

/**
 * @author weiwei(joslyn)
 * @date 2017-9-29 下午03:25:01
 * @decription
 * @version 
 */
public class CacheSparkSqls {

	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(CacheSparkSqls.class);
	
	/**
	 * 单例类
	 */
	private static final CacheSparkSqls _cache = new CacheSparkSqls();
	
	/**
	 * sparkSql配置map
	 */
	private Map<String, SqlFactory> _sparkSqlConfig;
	
	/**
	 * 是否停止拉取数据任务
	 */
	private boolean isStopPullTasks = false;
	
	/**
	 * 是否在补充数据任务
	 */
	private Map<String, Boolean> isMakeUp;
	
	/**
	 * lock
	 */
	private byte[] lock = new byte[0];
	
	/**
	 * 构造器私有化
	 */
	private CacheSparkSqls(){
		_sparkSqlConfig = new LinkedHashMap<String, SqlFactory>();
		isMakeUp = new HashMap<String, Boolean>();
		
		// 启动一个刷新内存中的数据写入到文件的线程
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(10*1000);// 每10s加载一次
					} catch (Exception e2) {
					}
					
					try {
						String rootDir = PropertiesReader.getInstance().getProperty("sql_config_path");
						File file = new File(rootDir + "/pull.dat");
						if(file.exists()){
							// 是否停止拉取数据线程任务 true：是，false：否
							isStopPullTasks = Boolean.valueOf(ToolsFile.readFileToString(file, "utf-8").replace("\r", "").replace("\n", ""));
						}
					} catch (Exception e) {
						logger.error("flush pull file error.", e);
					} 
				}
			}
		}).start();
	}
	
	/**
	 * 获取单例类
	 * @return
	 */
	public static CacheSparkSqls getInstance(){
		return _cache;
	}
	
	/**
	 * 加载sql配置
	 * @param sqlList
	 * @param columnList
	 * @param groupByList
	 * @param orderByList
	 */
	public void loadSparkSqlConfig(List<JSONObject> sqlList, List<JSONObject> columnList, 
			List<JSONObject> groupByList, List<JSONObject> orderByList, List<JSONObject> filterList, List<JSONObject> saveSqlInfoList){
		if(sqlList == null || sqlList.isEmpty()){
			logger.debug("===>>> spark sql config is null.");
		}else{
			wrapData(sqlList, columnList, groupByList, orderByList, filterList, saveSqlInfoList, _sparkSqlConfig);
			logger.debug("===>>> loading spark sql config finish.");
		}
	}
	
	/**
	 * 封装数据
	 * @param sqlList
	 * @param columnList
	 * @param groupByList
	 * @param orderByList
	 * @param sparkSqlConfig
	 */
	private void wrapData(List<JSONObject> sqlList, List<JSONObject> columnList, List<JSONObject> groupByList, 
			List<JSONObject> orderByList, List<JSONObject> filterList, List<JSONObject> saveSqlInfoList, Map<String, SqlFactory> sparkSqlConfig){
		// 封装筛选条件
		Map<String, List<FilterChain>> wrapFiltersMap = new HashMap<String, List<FilterChain>>();
		List<FilterChain> filterTempList = null;
		if(filterList != null && !filterList.isEmpty()){
			for(JSONObject filterJson : filterList){
				String sqlId = ToolsString.checkEmpty(filterJson.get("vst_sql_id"));
				if(ToolsString.isEmpty(sqlId)) continue;
				filterTempList = wrapFiltersMap.get(sqlId);
				if(filterTempList == null){
					filterTempList = new ArrayList<FilterChain>();
					wrapFiltersMap.put(sqlId, filterTempList);
				}
				
				String filterKey = ToolsString.checkEmpty(filterJson.get("vst_filter_key"));
				String filterValue = ToolsString.checkEmpty(filterJson.get("vst_filter_value"));
				int matchType = ToolsNumber.parseInt(ToolsString.checkEmpty(filterJson.get("vst_filter_match_type")));
				int yesType = ToolsNumber.parseInt(ToolsString.checkEmpty(filterJson.get("vst_filter_yes_type")));
				int noType = ToolsNumber.parseInt(ToolsString.checkEmpty(filterJson.get("vst_filter_no_type")));
				String pkg = ToolsString.checkEmpty(filterJson.get("vst_filter_pkg"));
				String pkgBlock = ToolsString.checkEmpty(filterJson.get("vst_filter_pkg_block"));
				Filter filter = new Filter(filterKey, filterValue, matchType, yesType, noType, pkg, pkgBlock);
				
				int actionType = ToolsNumber.parseInt(ToolsString.checkEmpty(filterJson.get("vst_filter_action_type")));
				String actionKey = ToolsString.checkEmpty(filterJson.get("vst_filter_action_key"));
				String actionValue = ToolsString.checkEmpty(filterJson.get("vst_filter_action_value"));
				Action action = new Action(actionKey, actionValue, actionType);
				
				FilterChain chain = new FilterChain(sqlId, filter, action);
				filterTempList.add(chain);
			}
		}
		
		// 封装列元素
		Map<String, List<Column>> wrapColumnsMap = new HashMap<String, List<Column>>();
		List<Column> columnTempList = null;
		for(JSONObject columnMap : columnList){
			String sqlId = ToolsString.checkEmpty(columnMap.get("vst_sql_id"));
			if(ToolsString.isEmpty(sqlId)) continue;
			columnTempList = wrapColumnsMap.get(sqlId);
			if(columnTempList == null){
				columnTempList = new ArrayList<Column>();
				wrapColumnsMap.put(sqlId, columnTempList);
			}
			
			String name = ToolsString.checkEmpty(columnMap.get("vst_column_name"));
			String alias = ToolsString.checkEmpty(columnMap.get("vst_column_alias"));
			int dataType = ToolsNumber.parseInt(String.valueOf(columnMap.get("vst_column_dataType")), 1);
			int operateType = ToolsNumber.parseInt(String.valueOf(columnMap.get("vst_column_operateType")), 1);
			Column column = new Column();
			column.setName(name);
			column.setAlias(alias);
			column.setDataType(dataType);
			column.setOperateType(operateType);
			columnTempList.add(column);
		}
		
		// 封装分组元素
		Map<String, List<GroupBy>> wrapGroupBysMap = new HashMap<String, List<GroupBy>>();
		List<GroupBy> groupByTempList = null;
		for(JSONObject groupByMap : groupByList){
			String sqlId = ToolsString.checkEmpty(groupByMap.get("vst_sql_id"));
			if(ToolsString.isEmpty(sqlId)) continue;
			groupByTempList = wrapGroupBysMap.get(sqlId);
			if(groupByTempList == null){
				groupByTempList = new ArrayList<GroupBy>();
				wrapGroupBysMap.put(sqlId, groupByTempList);
			}
			
			String name = ToolsString.checkEmpty(groupByMap.get("vst_group_name"));
			String alias = ToolsString.checkEmpty(groupByMap.get("vst_group_alias"));
			String desc = ToolsString.checkEmpty(groupByMap.get("vst_group_desc"));
			GroupBy groupBy = new GroupBy();
			groupBy.setName(name);
			groupBy.setAlias(alias);
			groupBy.setDesc(desc);
			groupByTempList.add(groupBy);
		}
		
		// 封装排序元素
		Map<String, List<OrderBy>> wrapOrderBysMap = new HashMap<String, List<OrderBy>>();
		List<OrderBy> orderByTempList = null;
		for(JSONObject orderByMap : orderByList){
			String sqlId = ToolsString.checkEmpty(orderByMap.get("vst_sql_id"));
			if(ToolsString.isEmpty(sqlId)) continue;
			orderByTempList = wrapOrderBysMap.get(sqlId);
			if(orderByTempList == null){
				orderByTempList = new ArrayList<OrderBy>();
				wrapOrderBysMap.put(sqlId, orderByTempList);
			}
			
			String name = ToolsString.checkEmpty(orderByMap.get("vst_order_name"));
			String sort = ToolsString.checkEmpty(orderByMap.get("vst_order_sort"));
			String desc = ToolsString.checkEmpty(orderByMap.get("vst_order_desc"));
			OrderBy orderBy = new OrderBy();
			orderBy.setName(name);
			orderBy.setDesc(desc);
			orderBy.setSort(sort);
			orderByTempList.add(orderBy);
		}
		
		// 封装保存sql元素
		Map<String, List<JSONObject>> wrapSaveSqlInfoMap = new HashMap<String, List<JSONObject>>();
		List<JSONObject> saveSqlInfoTempList = null;
		if(saveSqlInfoList != null){
			for(JSONObject saveSqlInfoMap : saveSqlInfoList){
				String tableName = ToolsString.checkEmpty(saveSqlInfoMap.get("vst_save_table"));
				if(ToolsString.isEmpty(tableName)) continue;
				
				saveSqlInfoTempList = wrapSaveSqlInfoMap.get(tableName);
				if(saveSqlInfoTempList == null){
					saveSqlInfoTempList = new ArrayList<JSONObject>();
					wrapSaveSqlInfoMap.put(tableName, saveSqlInfoTempList);
				}
				saveSqlInfoTempList.add(saveSqlInfoMap);
			}
		}
		
		// 封装sql
		for(JSONObject map : sqlList){
			if(map == null) continue;
			String sqlId = ToolsString.checkEmpty(map.get("vst_sql_id"));
			if(ToolsString.isEmpty(sqlId)) continue;
			String sqlPid = ToolsString.checkEmpty(map.get("vst_sql_pid")).trim();
			
			SqlFactory sql = new SqlFactory();
			sql.setSqlId(sqlId);
			sql.setSqlPid(sqlPid);
			sql.setSqlName(ToolsString.checkEmpty(map.get("vst_sql_name")));
			sql.setSqlType(ToolsNumber.parseInt(ToolsString.checkEmpty(map.get("vst_sql_type"))));
			sql.setSqlInterval(ToolsNumber.parseInt(ToolsString.checkEmpty(map.get("vst_sql_interval"))));
			sql.setSqlTopic(ToolsString.checkEmpty(map.get("vst_sql_topic")));
			sql.setSqlDataPath(ToolsString.checkEmpty(map.get("vst_sql_data_path")));
			sql.setSqlDesc(ToolsString.checkEmpty(map.get("vst_sql_desc")));
			sql.setSqlTempTableName(ToolsString.checkEmpty(map.get("vst_sql_temp_table")));
			sql.setSqlTableName(ToolsString.checkEmpty(map.get("vst_sql_table")));
			sql.setSqlDB(ToolsNumber.parseInt(ToolsString.checkEmpty(map.get("vst_sql_db"))));
			sql.setSqlRowKeyColumn(ToolsString.checkEmpty(map.get("vst_sql_rowkeyColumn")).split(","));
			sql.setSqlRuntime(ToolsNumber.parseLong(ToolsString.checkEmpty(map.get("vst_sql_runtime"))));
			sql.setSqlRunPosition(ToolsString.checkEmpty(map.get("vst_sql_run_position")).split("[|]"));
			sql.setSqlIsFormat(ToolsNumber.parseInt(String.valueOf(map.get("vst_sql_is_format"))) == 1);
			sql.setSqlRunModel(ToolsNumber.parseInt(String.valueOf(map.get("vst_sql_run_model"))));
			sql.setSqlJoinKeys(ToolsString.checkEmpty(map.get("vst_sql_joinKeys")));
			sql.setPriority(ToolsNumber.parseInt(String.valueOf(map.get("vst_sql_priority"))));

			// 如果是父级sql
			if(ToolsString.isEmpty(sqlPid) || "0".equals(sqlPid)){
				sparkSqlConfig.put(sqlId, sql);
			}else{
				SqlFactory sqlFactory = sparkSqlConfig.get(sqlPid);
				if(sqlFactory == null) continue;
				sqlFactory.addSqlFactory(sql);
			}
			
			// 过滤元素
			filterTempList = wrapFiltersMap.get(sqlId);
			if(filterTempList != null && !filterTempList.isEmpty()){
				sql.setFilterList(filterTempList);
			}
			
			// 填充列元素
			columnTempList = wrapColumnsMap.get(sqlId);
			if(columnTempList != null && !columnTempList.isEmpty()){
				sql.setColumns(columnTempList);
			}
			
			// 填充分组元素
			groupByTempList = wrapGroupBysMap.get(sqlId);
			if(groupByTempList != null && !groupByTempList.isEmpty()){
				sql.setGroupBys(groupByTempList);
			}
			
			// 填充排序元素
			orderByTempList = wrapOrderBysMap.get(sqlId);
			if(orderByTempList != null && !orderByTempList.isEmpty()){
				sql.setOrderBys(orderByTempList);
			}
			
			// 封装保存sql元素
			saveSqlInfoTempList = wrapSaveSqlInfoMap.get(sql.getSqlTableName());
			if(saveSqlInfoTempList != null && !saveSqlInfoTempList.isEmpty()){
				sql.setSaveSqlInfo(new SaveSqlInfo(sql.getSqlTableName(), saveSqlInfoTempList));
			}
		}
	}
	
	/**
	 * 是否停止拉取数据线程任务
	 * @return
	 */
	public boolean isStopPullTasks() {
		return isStopPullTasks;
	}
	
	/**
	 * 设置拉取数据线程任务启动开关
	 * @return
	 */
	public void setStopPullTasks(boolean flag){
		isStopPullTasks = flag;
	}
	
	/**
	 * 是否补充数据标识，true：是，false：否
	 * @return
	 */
	public boolean isMakeUp(String sqlId) {
		Boolean flag = isMakeUp.get(sqlId);
		return flag == null ? false : flag;
	}

	/**
	 * 设置是否在补充数据
	 * @param isMakeUp
	 */
	public void setMakeUp(String sqlId, boolean flag) {
		isMakeUp.put(sqlId, flag);
	}

	/**
	 * 获取sqlId
	 * @param sqlId
	 * @return
	 */
	public SqlFactory getSql(String sqlId){
		return _sparkSqlConfig.get(sqlId);
	}
	
	/**
	 * 获取所有的sql
	 * @return
	 */
	public List<SqlFactory> getAllSqlsByState(SqlType type){
		List<SqlFactory> result = new ArrayList<SqlFactory>();
		for(String sqlId : _sparkSqlConfig.keySet()){
			SqlFactory sql = _sparkSqlConfig.get(sqlId);
			if(sql.getSqlType() == type.getType()){
				result.add(sql);
			}
		}
		return result;
	}
	
	/**
	 * 获取所有的sql
	 * @return
	 */
	public SqlFactory getPullDataSql(String topic){
		for(String sqlId : _sparkSqlConfig.keySet()){
			SqlFactory sql = _sparkSqlConfig.get(sqlId);
			if(sql.getSqlType() == SqlType.P_PULLDATA.getType() && sql.getSqlTopic().equals(topic)){
				return sql;
			}
		}
		return null;
	}
	
	public byte[] getLock() {
		return lock;
	}
	
	/**
	 * 刷新缓存
	 * @param sqlList
	 * @param columnList
	 * @param groupByList
	 * @param orderByList
	 */
	public void reloadSparkSqlConfig(List<JSONObject> sqlList,List<JSONObject> columnList, 
			List<JSONObject> groupByList, List<JSONObject> orderByList, List<JSONObject> filterList, List<JSONObject> saveSqlInfoList){
		Map<String, SqlFactory> sparkSqlConfig = new LinkedHashMap<String, SqlFactory>();
		wrapData(sqlList, columnList, groupByList, orderByList, filterList, saveSqlInfoList, sparkSqlConfig);
		_sparkSqlConfig = sparkSqlConfig;
	}
	
}
