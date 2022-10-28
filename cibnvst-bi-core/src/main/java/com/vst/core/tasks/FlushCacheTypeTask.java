package com.vst.core.tasks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vst.core.service.OthersMgrService;

/**
 * @author joslyn
 * @date 2017年11月24日 下午12:30:53
 * @description
 * @version 1.0
 */
public class FlushCacheTypeTask implements Runnable{

	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(FlushCacheTypeTask.class);

	/**
	 * sparkSQL管理类
	 */
	private OthersMgrService _othersMgrService;
	
	/**
	 * 构造器
	 */
	public FlushCacheTypeTask(OthersMgrService othersMgrService){
		_othersMgrService = othersMgrService;
	}
	
	/**
	 *  任务线程
	 */
	@Override
	public void run() {
		while(true){
			try {
				try {
					Thread.sleep(60*60*1000L);// 60分钟执行一次
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				_othersMgrService.flushCache();
				logger.info("flush video type cache success!");
			} catch (Exception e) {
				logger.error("flush video type cache error!. ERROR:", e);
			}
		}
	}

}
