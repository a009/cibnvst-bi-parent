package com.vst.defend.timer;

import org.springframework.web.context.WebApplicationContext;

import com.vst.defend.service.other.OthersMgrService;
import com.vst.defend.timer.tasks.SyncOthersCacheTimer;

/**
 * @author weiwei
 * @date 2014-12-5 下午02:35:30
 * @description 定时任务启动类
 * @version
 */
public class MyTimers extends Thread {

	/**
	 * app
	 */
	private WebApplicationContext app;
	
	/**
	 * 构造器
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
		// 更新新闻缓存
		// 该任务每3分钟执行一次，项目启动3分钟后立刻执行
		new SyncOthersCacheTimer(app.getBean(OthersMgrService.class)).start();
	}
}
