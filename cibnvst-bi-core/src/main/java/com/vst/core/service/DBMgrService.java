package com.vst.core.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vst.common.tools.encrypt.ToolsEncrypt;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.spark.sql.Row;
import org.json.simple.JSONObject;

import com.vst.common.tools.date.ToolsDate;
import com.vst.common.tools.string.ToolsString;
import com.vst.core.communal.sql.Save;
import com.vst.core.communal.sql.SqlFactory;
import com.vst.core.communal.type.DBType;
import com.vst.core.service.impl.HbaseServiceImpl;
import com.vst.core.service.impl.HbaseUserServiceImpl;
import com.vst.core.service.impl.LocalFileServiceImpl;
import com.vst.core.service.impl.MySQLServiceImpl;
import com.vst.core.service.impl.HbaseUserFeatureServiceImpl;

/**
 * @author weiwei(joslyn)
 * @date 2017-10-24 下午11:23:56
 * @decription
 * @version
 */
@SuppressWarnings("unchecked")
public abstract class DBMgrService {

	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(DBMgrService.class);

	/**
	 * 根据数据库类型创建db会话
	 * @param type
	 * @return
	 */
	public static DBMgrService createDBSession(DBType type, SqlFactory sqlFactory){
		DBMgrService dbMgrService = null;
		switch (type) {
			case HBASE:
				if("vst_user".equals(sqlFactory.getSqlTableName())
						|| "home_user".equals(sqlFactory.getSqlTableName())){
					dbMgrService = HbaseUserServiceImpl.getInstance();
				} else if ("vst_user_features".equals(sqlFactory.getSqlTableName())) {
					dbMgrService = HbaseUserFeatureServiceImpl.getInstance();
				} else {
					dbMgrService = HbaseServiceImpl.getInstance();
				}
				break;
			case MYSQL:
				dbMgrService = MySQLServiceImpl.getInstance();
				break;
			case LOCAL_FILE:
				dbMgrService = LocalFileServiceImpl.getInstance();
				break;
			case HDFS:
				break;

			default:
				break;
		}
		return dbMgrService;
	}

	/**
	 * 根据数据库类型创建db会话
	 * @param sqlFactory
	 * @return
	 */
	public static DBMgrService createDBSession(SqlFactory sqlFactory){
		int type = sqlFactory.getSqlDB();
		DBType dbType = null;
		if(type == 1){
			dbType = DBType.HBASE;
		}else if(type == 2){
			dbType = DBType.MYSQL;
		}else if(type == 3){
			dbType = DBType.LOCAL_FILE;
		}else if(type == 4){
			dbType = DBType.HDFS;
		}else{
			return null;
		}
		return createDBSession(dbType, sqlFactory);
	}

	/**
	 * 保存数据
	 * @param rows
	 * @param date
	 * @param sqlFactory
	 * @return
	 * @throws Exception
	 */
	public boolean save(List<Row> rows, int date, SqlFactory sqlFactory) throws Exception{
		boolean flag = false;
		if(rows != null && !rows.isEmpty()){
			Date currDate = ToolsDate.parseDate(ToolsDate.yyyy_MM_dd4, String.valueOf(date));
			if(currDate == null) {
				logger.error("date format is error. sqlName = [" + sqlFactory.getSqlName() + "], date = [" + date + "]" );
				return flag;
			}

			long start = System.currentTimeMillis();
			// 查询昨天的数据，计算天环比
			Map<String, Map<String, String>> yesterdayData = null;
			if(sqlFactory.getSaveSqlInfo().containsRatio(8)){
				String yesterdayDate = ToolsDate.formatDate(ToolsDate.yyyy_MM_dd4, currDate.getTime() - 24*60*60*1000L);
				yesterdayData = queryHistoryData(sqlFactory, yesterdayDate);
			}
			// 查询上周的今天的数据，计算周环比
			Map<String, Map<String, String>> weekData = null;
			if(sqlFactory.getSaveSqlInfo().containsRatio(9)){
				String weekDate = ToolsDate.formatDate(ToolsDate.yyyy_MM_dd4, currDate.getTime() - 7*24*60*60*1000L);
				weekData = queryHistoryData(sqlFactory, weekDate);
			}
			// 月环比属性
			Map<String, Map<String, String>> monthData = null;
			if(sqlFactory.getSaveSqlInfo().containsRatio(10)){
				String monthDate = ToolsDate.formatDate(ToolsDate.yyyy_MM_dd4, currDate.getTime() - 30*24*60*60*1000L);
				monthData = queryHistoryData(sqlFactory, monthDate);
			}
			// 数据合并
			Map<String, Map<String, String>> mergeData = null;
			if (sqlFactory.getSaveSqlInfo().containsRatio(13)) {
				logger.info("===>>> query start data ["+rows.size()+"]");
				mergeData = queryHistoryByRowKey(sqlFactory, rows);
			}

			long takes = (System.currentTimeMillis() - start);
			logger.info("===>>> query history data takes (" + takes + ") ms");

			start = System.currentTimeMillis();
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>(rows.size());
			Map<String, Object> map = null;
			// 遍历每一行
			for(Row row : rows){
				if(row == null || row.size() == 0) continue;
				map = new HashMap<String, Object>();
				// 遍历每一列
				for(int i = 0, l = row.length(); i < l; i++){
					String name = row.schema().apply(i).name();
					Object value = row.get(i);
					map.put(name, value);
				}

				sqlFactory.getSaveSqlInfo().format(map, date, yesterdayData, weekData, monthData, mergeData);
				list.add(map);
			}

			takes = (System.currentTimeMillis() - start);
			logger.info("===>>> format data takes (" + takes + ") ms");
			start = System.currentTimeMillis();
			int affectRows = save(sqlFactory, date, list);
			takes = (System.currentTimeMillis() - start);
			logger.info("===>>> insert into table[" + sqlFactory.getSqlTableName() + "][" + arrayToString(sqlFactory.getSqlRunPosition(), "|") + "] records(" + list.size() + "), affectRows(" + affectRows + "), takes(" + takes + ") ms");
			flag = true;
		}
		return flag;
	}

	private String arrayToString(String[] array, String split){
		if(array != null && array.length > 0){
			if(array.length == 1){
				return array[0];
			}else{
				String result = "";
				for(int i = 0; i < array.length - 1; i++){
					result += array[i] + split;
				}
				result += array[array.length - 1];
				return result;
			}
		}
		return "";
	}

	/**
	 * 数据库插入方法
	 * @param sqlFactory
	 * @param list
	 * @return
	 */
	public abstract int save(SqlFactory sqlFactory, int date, List<Map<String, Object>> list) throws Exception;

	/**
	 * 删除数据
	 * @param sqlFactory
	 * @param date
	 * @return
	 */
	public abstract boolean delete(SqlFactory sqlFactory, String date);

	/**
	 * 查询历史数据，计算环比
	 * @param sqlFactory
	 * @param date
	 * @return
	 */
	protected Map<String, Map<String, String>> queryHistoryData(SqlFactory sqlFactory, String date){
		List<JSONObject> list = queryFromDB(sqlFactory, date);
		if(list != null && !list.isEmpty()){
			Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
			List<Save> keys = sqlFactory.getSaveSqlInfo().toQueryHistorySQLKeys();
			List<Save> values = sqlFactory.getSaveSqlInfo().toQueryHistorySQLValues();
			for(JSONObject json : list){
				String currKey = "";
				// 组装keys
				for(Save save : keys){
					currKey += ToolsString.checkEmpty(json.get(save.getName())) + "[@!@]";
				}
				currKey = currKey.substring(0, currKey.lastIndexOf("[@!@]"));

				// 组装values
				Map<String, String> valuesMap = new HashMap<String, String>();
				for(Save save : values){
					valuesMap.put(save.getName(), ToolsString.checkEmpty(json.get(save.getName())));
				}
				result.put(currKey, valuesMap);
			}
			return result;
		}
		return Collections.EMPTY_MAP;
	}
	/**
	 * 查询历史数据，数据合并
	 *
	 * @param sqlFactory SQl配置类
	 * @param rows       多行RowKey数据
	 * @return Map 返回Map
	 */
	private Map<String, Map<String, String>> queryHistoryByRowKey(SqlFactory sqlFactory, List<Row> rows) {
		if (rows.isEmpty()) return Collections.EMPTY_MAP;

		String[] keyColumns = sqlFactory.getSqlRowKeyColumn(); //获取RowKeyColumns
		if(keyColumns == null || keyColumns.length == 0) return Collections.EMPTY_MAP;

		List<String> rowValueList = new ArrayList<>();
		for (Row row : rows) {
			StringBuilder keyValueBuffLine = new StringBuilder();
			for (String keyColumn : keyColumns) {
				keyValueBuffLine.append("[@!@]").append(row.getAs(keyColumn));
			}
			if (keyValueBuffLine.length() > 0) keyValueBuffLine.delete(0, 5);
			rowValueList.add(ToolsEncrypt.getMD5Encrypt(keyValueBuffLine.toString()));
		}

		logger.info("===>>> queryHistoryByRowKey [" + rowValueList.size() + "]");
		List<JSONObject> list = queryFromDB(sqlFactory, rowValueList);

		if (list == null || list.isEmpty()) return Collections.EMPTY_MAP;

		Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
		List<Save> keys = sqlFactory.getSaveSqlInfo().toQueryHistorySQLKeys();
		List<Save> values = sqlFactory.getSaveSqlInfo().toQueryHistorySQLValues();
		for (JSONObject json : list) {
			StringBuilder currKey = new StringBuilder();
			// 组装keys
			for (Save save : keys) {
				currKey.append("[@!@]").append(ToolsString.checkEmpty(json.get(save.getName())));
			}
			if(currKey.length() > 0) currKey.delete(0,5);

			// 组装values
			Map<String, String> valuesMap = new HashMap<String, String>();
			for (Save save : values) {
				valuesMap.put(save.getName(), ToolsString.checkEmpty(json.get(save.getName())));
			}
			result.put(currKey.toString(), valuesMap);
		}
		return result;
	}
	/**
	 * 从数据库加载数据
	 * @param sql
	 * @return
	 */
	public abstract List<JSONObject> queryFromDB(SqlFactory sqlFactory, String date);
	/**
	 * 从数据库加载数据
	 *
	 * @param sqlFactory SQL配置
	 * @param data       查询所需参数
	 * @return list
	 */
	public List<JSONObject> queryFromDB(SqlFactory sqlFactory, List<String> data) {
		return null;
	};
}
