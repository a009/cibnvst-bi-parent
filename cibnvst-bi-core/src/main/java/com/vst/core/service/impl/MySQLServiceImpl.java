package com.vst.core.service.impl;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;

import com.vst.common.tools.string.ToolsString;
import com.vst.core.communal.sql.SqlFactory;
import com.vst.core.dao.MySQLDao;
import com.vst.core.dao.impl.MySQLDaoImpl;
import com.vst.core.service.DBMgrService;

/**
 * @author joslyn
 * @date 2017年11月22日 下午12:00:27
 * @description mysql入库实现类
 * @version 1.0
 */
public class MySQLServiceImpl extends DBMgrService{

	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(MySQLServiceImpl.class);

	/**
	 * 单例类
	 */
	private static final MySQLServiceImpl _cache = new MySQLServiceImpl();

	/**
	 * mysql管理类
	 */
	private MySQLDao _mysqlDao;

	/**
	 * 构造器
	 */
	private MySQLServiceImpl() {
		_mysqlDao = new MySQLDaoImpl();
	}

	/**
	 * 获取单例对象
	 * @return
	 */
	public static MySQLServiceImpl getInstance(){
		return _cache;
	}

	/**
	 * 删除数据
	 * @param sqlFactory
	 * @param date
	 * @return
	 */
	@Override
	public boolean delete(SqlFactory sqlFactory, String date) {
		boolean flag = false;
		if(!ToolsString.isEmpty(date)){
			String sql = sqlFactory.getSaveSqlInfo().toDeleteSQL(date);
			int affectRows = _mysqlDao.delete(sql);
			logger.info("===>>> delete from table[" + sqlFactory.getSqlTableName() + "][" + date + "]  affectRows(" + affectRows + ")");
			flag = true;
		}
		return flag;
	}

	/**
	 * 数据库插入方法
	 * @param sqlFactory
	 * @param list
	 * @return
	 */
	@Override
	public int save(SqlFactory sqlFactory, int date,
					List<Map<String, Object>> list) throws Exception {
		return _mysqlDao.save(sqlFactory.getSaveSqlInfo().toInsertSQLValues(list));
	}

	/**
	 * 从数据库加载数据
	 * @param sql
	 * @param date
	 * @return
	 */
	@Override
	public List<JSONObject> queryFromDB(SqlFactory sqlFactory, String date) {
		String sql = sqlFactory.getSaveSqlInfo().toQueryHistorySQL(date);
		return _mysqlDao.query(sql);
	}
}
