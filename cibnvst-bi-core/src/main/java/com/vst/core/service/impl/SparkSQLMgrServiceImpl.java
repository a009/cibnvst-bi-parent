package com.vst.core.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;

import com.vst.core.cache.CacheSparkSqls;
import com.vst.core.communal.sql.SqlFactory;
import com.vst.core.dao.MySQLDao;
import com.vst.core.dao.impl.MySQLDaoImpl;
import com.vst.core.service.SparkSQLMgrService;

/**
 * @author joslyn
 * @date 2017年11月24日 下午12:08:17
 * @description
 * @version 1.0
 */
public class SparkSQLMgrServiceImpl implements SparkSQLMgrService, Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 7934081352874589016L;

	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(SparkSQLMgrServiceImpl.class);

	/**
	 * mysql管理类
	 */
	private MySQLDao _mysqlDao;

	/**
	 * 构造器
	 */
	public SparkSQLMgrServiceImpl() {
		_mysqlDao = new MySQLDaoImpl("jdbc:mysql://bj-bi-operation-sysadmin:3306/vst_config", "javadmin", "VstVst520_");
	}

	@Override
	public void initSQLCache() {
		try {
			List<JSONObject> sqlList = _mysqlDao.query("select * from vst_sql where vst_sql_state=1 order by vst_sql_index desc");
			List<JSONObject> columnList = _mysqlDao.query("select * from vst_sql_column where vst_column_state=1 order by vst_sql_id asc,vst_column_index desc");
			List<JSONObject> groupByList = _mysqlDao.query("select * from vst_sql_group where vst_group_state=1 order by vst_sql_id asc,vst_group_index desc");
			List<JSONObject> orderByList = _mysqlDao.query("select * from vst_sql_order where vst_order_state=1 order by vst_sql_id asc,vst_order_index desc");
			List<JSONObject> filterList = _mysqlDao.query("select * from vst_sql_filter where vst_filter_state=1 order by vst_sql_id asc,vst_filter_index desc");
			List<JSONObject> saveList = _mysqlDao.query("select * from vst_sql_save where vst_save_state=1 order by vst_save_table asc,vst_save_index desc");
			CacheSparkSqls.getInstance().loadSparkSqlConfig(sqlList, columnList, groupByList, orderByList, filterList, saveList);
		} catch (Exception e) {
			logger.error("initSQLCache error. ERROR:", e);
		}

	}

	@Override
	public void flushSQLCache() {
		try {
			List<JSONObject> sqlList = _mysqlDao.query("select * from vst_sql where vst_sql_state=1 order by vst_sql_index desc");
			List<JSONObject> columnList = _mysqlDao.query("select * from vst_sql_column where vst_column_state=1 order by vst_sql_id asc,vst_column_index desc");
			List<JSONObject> groupByList = _mysqlDao.query("select * from vst_sql_group where vst_group_state=1 order by vst_sql_id asc,vst_group_index desc");
			List<JSONObject> orderByList = _mysqlDao.query("select * from vst_sql_order where vst_order_state=1 order by vst_sql_id asc,vst_order_index desc");
			List<JSONObject> filterList = _mysqlDao.query("select * from vst_sql_filter where vst_filter_state=1 order by vst_sql_id asc,vst_filter_index desc");
			List<JSONObject> saveList = _mysqlDao.query("select * from vst_sql_save where vst_save_state=1 order by vst_save_table asc,vst_save_index desc");
			CacheSparkSqls.getInstance().reloadSparkSqlConfig(sqlList, columnList, groupByList, orderByList, filterList, saveList);
		} catch (Exception e) {
			logger.error("flushSQLCache error. ERROR:", e);
		}
	}

	@Override
	public void syncSQLIntoDB(SqlFactory sqlFactory) {
		if(sqlFactory != null){
			List<SqlFactory> children = sqlFactory.getChildrenSqlList();
			List<String> sqls = new ArrayList<String>();
			if(children != null && !children.isEmpty()){
				for(SqlFactory child : children){
					String sql = new StringBuilder("update vst_sql set vst_sql_runtime=")
							.append(child.getSqlRuntime()).append(",vst_sql_run_position='")
							.append(arrayToString(child.getSqlRunPosition(), "|")).append("',vst_sql_uptime=")
							.append(System.currentTimeMillis()).append(", vst_sql_updator='updator' where vst_sql_id='")
							.append(child.getSqlId()).append("'").toString();
					sqls.add(sql);
				}
			}

			String sql = new StringBuilder("update vst_sql set vst_sql_runtime=")
					.append(sqlFactory.getSqlRuntime()).append(",vst_sql_run_position='")
					.append(arrayToString(sqlFactory.getSqlRunPosition(), "|")).append("',vst_sql_uptime=")
					.append(System.currentTimeMillis()).append(", vst_sql_updator='updator' where vst_sql_id='")
					.append(sqlFactory.getSqlId()).append("'").toString();
			sqls.add(sql);
			int affectRows = _mysqlDao.save(sqls);
			logger.info("===>>> update table[vst_sql] records(" + sqls.size() + "), affectRows(" + affectRows + ")");
		}
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

}
