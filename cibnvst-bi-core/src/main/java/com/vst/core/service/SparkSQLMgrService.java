package com.vst.core.service;

import java.io.Serializable;

import com.vst.core.communal.sql.SqlFactory;

/**
 * @author joslyn
 * @date 2017年11月24日 下午12:07:53
 * @description
 * @version 1.0
 */
public interface SparkSQLMgrService extends Serializable{

	/**
	 * 初始化sql缓存
	 */
	void initSQLCache();
	
	/**
	 * 刷新sql缓存
	 */
	void flushSQLCache();
	
	/**
	 * 同步sql到db中
	 * @param sqlFactory
	 */
	void syncSQLIntoDB(SqlFactory sqlFactory);
}
