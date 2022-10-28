package com.vst.api.timer.tasks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vst.api.service.TopicMgrService;

/**
 * @author weiwei
 * @date 2015-4-8 下午05:26:13
 * @description
 * @version
 */
public class SyncTopicCacheTimer extends Thread {

	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(SyncTopicCacheTimer.class);
	
	/**
	 * 其他管理类
	 */
	private TopicMgrService _topicMgrService;
	
	/**
	 * 构造器
	 * @param mvMgrService
	 */
	public SyncTopicCacheTimer(TopicMgrService topicMgrService) {
		_topicMgrService = topicMgrService;
	}

	/**
	 * 更新缓存主线程
	 */
	@Override
	public void run() {
		while(true){
			try {
				logger.debug("*******************Start sync topic cache data...**********************");
				_topicMgrService.flushCache();
				logger.debug("*******************End sync topic cache data...**********************");
			} catch (Exception e) {
				logger.error("Update topic cache error. ERROR:", e);
			}finally{
				try {
					Thread.sleep(90*1000);// 1.5分钟
				} catch (InterruptedException e) {
				}
			}
		}
	}

}
