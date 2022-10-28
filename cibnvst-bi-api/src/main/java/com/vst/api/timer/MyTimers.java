package com.vst.api.timer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;

import com.vst.api.service.OthersMgrService;
import com.vst.api.service.TopicMgrService;
import com.vst.api.timer.tasks.DeleteLocalFileTask;
import com.vst.api.timer.tasks.ParseDataTask;
import com.vst.api.timer.tasks.PutDataIntoKafkaTask;
import com.vst.api.timer.tasks.SyncOthersCacheTimer;
import com.vst.api.timer.tasks.SyncTopicCacheTimer;

/**
 * @author weiwei
 * @date 2014-12-5 下午02:35:30
 * @description 定时任务启动类
 * @version
 */
public class MyTimers extends Thread {

	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(MyTimers.class);

	/**
	 * app
	 */
	private WebApplicationContext app;

	/**
	 * 构造器
	 * 
	 * @param mvMgrService
	 * @param tvMgrService
	 * @param healthMgrService
	 */
	public MyTimers(WebApplicationContext app) {
		this.app = app;
	}

	/**
	 * 启动定时任务线程
	 */
	@Override
	public void run() {
		// ===========================================================================================
		// 定时任务1
		// 更新其他缓存
		// 该任务每3分钟执行一次，项目启动3分钟后立刻执行
		new SyncOthersCacheTimer(app.getBean(OthersMgrService.class)).start();
		logger.info("===>>> start sync other cache thread.");

		// ===========================================================================================
		// 定时任务2
		// 更新专题缓存
		// 该任务每1.5分钟执行一次，项目启动3分钟后立刻执行
		new SyncTopicCacheTimer(app.getBean(TopicMgrService.class)).start();
		logger.info("===>>> start sync topic cache thread.");

		// ===========================================================================================
		// 定时任务3
		// 启动解析数据任务
		// 该任务每1秒执行一次，项目启动后立刻执行
		Thread parseThread = new Thread(new ParseDataTask(), "parse-data-thread");
		parseThread.start();
		logger.info("===>>> start parse data thread.");

		// ===========================================================================================
		// 定时任务4
		// 启动存放数据任务
		// 该任务每3秒执行一次，项目启动后立刻执行
		Thread saveDataThread = new Thread(new PutDataIntoKafkaTask(), "save-data-thread");
		saveDataThread.start();
		logger.info("===>>> start save data thread.");
		
		// ===========================================================================================
		// 定时任务5
		// 启动删除已经处理的文件任务
		// 该任务每60秒执行一次，项目启动后立刻执行
		Thread deleteThread = new Thread(new DeleteLocalFileTask(), "delete-file-thread");
		deleteThread.start();
		logger.info("===>>> start delete file thread.");
	}
}
