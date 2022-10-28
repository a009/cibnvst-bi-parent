package com.vst.api.timer.tasks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vst.api.service.OthersMgrService;

/**
 * @author weiwei
 * @date 2015-4-8 下午05:26:13
 * @description
 * @version
 */
public class SyncOthersCacheTimer extends Thread {

	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(SyncOthersCacheTimer.class);
	
	/**
	 * 其他管理类
	 */
	private OthersMgrService _othersMgrService;
	
	/**
	 * 构造器
	 * @param mvMgrService
	 */
	public SyncOthersCacheTimer(OthersMgrService othersMgrService) {
		_othersMgrService = othersMgrService;
	}

	/**
	 * 更新缓存主线程
	 */
	@Override
	public void run() {
		while(true){
			try {
				logger.debug("*******************Start sync others cache data...**********************");
				_othersMgrService.flushCache();
				logger.debug("*******************End sync others cache data...**********************");
			} catch (Exception e) {
				logger.error("Update others cache error. ERROR:", e);
			}finally{
				try {
					Thread.sleep(90*1000);// 1.5分钟
				} catch (InterruptedException e) {
				}
			}
		}
	}

}
