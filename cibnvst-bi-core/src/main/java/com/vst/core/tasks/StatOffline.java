package com.vst.core.tasks;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.spark.sql.SparkSession;

import com.vst.core.cache.CacheSparkSqls;
import com.vst.core.communal.sql.SqlFactory;
import com.vst.core.communal.type.SqlType;
import com.vst.core.service.CalculateTasksMgrService;
import com.vst.core.service.SparkSQLMgrService;
import com.vst.core.service.impl.OfflineTasksServiceImpl;

/**
 * @author weiwei(joslyn)
 * @date 2017-9-26 下午05:48:05
 * @decription
 * @version 
 */
public class StatOffline implements Runnable{
	
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(StatOffline.class);
	
	private SparkSession _sparkSession;
	
	/**
	 * sparkSQL管理类
	 */
	private SparkSQLMgrService _sparkSQLMgrService;
	
	/**
	 * 构造器
	 * @param _sparkSession
	 * @param _sparkSQLMgrService
	 * @param _othersMgrService
	 */
	public StatOffline(SparkSession sparkSession,
			SparkSQLMgrService sparkSQLMgrService) {
		_sparkSession = sparkSession;
		_sparkSQLMgrService = sparkSQLMgrService;
	}

	@Override
	public void run(){
		CalculateTasksMgrService offlineTasks = new OfflineTasksServiceImpl(_sparkSession, _sparkSQLMgrService);
		while(true){
			try {
				synchronized (CacheSparkSqls.getInstance().getLock()) {
					// 获取所有拉取数据执行的任务
					List<SqlFactory> sqls = CacheSparkSqls.getInstance().getAllSqlsByState(SqlType.P_OFFLINE);
					logger.info("===>>> total sql size " + sqls.size());
					offlineTasks.runOfflineTasks(sqls);
				}
			} catch (Exception e) {
				logger.error("runOfflineCalculateTasks error. ERROR:", e);
			} finally {
				try {
					Thread.sleep(60*1000);// 每分钟执行一次
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
