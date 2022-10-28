package com.vst.core.tasks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vst.core.cache.CacheSparkSqls;
import com.vst.core.service.SparkSQLMgrService;

/**
 * @author joslyn
 * @date 2017年11月24日 下午12:30:53
 * @description
 * @version 1.0
 */
public class FlushSQLTask implements Runnable{

	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(FlushSQLTask.class);

	/**
	 * sparkSQL管理类
	 */
	private SparkSQLMgrService _sparkSQLMgrService;
	
	/**
	 * 构造器
	 */
	public FlushSQLTask(SparkSQLMgrService sparkSQLMgrService){
		_sparkSQLMgrService = sparkSQLMgrService;
	}
	
	/**
	 *  任务线程
	 */
	@Override
	public void run() {
		while(true){
			try {
				try {
					Thread.sleep(2*60*1000L);// 2分钟执行一次
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// 如果没有写sql的操作，就可以从数据库中刷新sql到缓存中
				synchronized (CacheSparkSqls.getInstance().getLock()) {
					_sparkSQLMgrService.flushSQLCache();
				}
				logger.info("flush sql from db success!");
			} catch (Exception e) {
				logger.error("flush sql from db error!. ERROR:", e);
			}
		}
	}

}
