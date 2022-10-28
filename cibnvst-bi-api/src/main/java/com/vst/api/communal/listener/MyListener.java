package com.vst.api.communal.listener;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vst.api.service.OthersMgrService;
import com.vst.api.service.TopicMgrService;
import com.vst.api.timer.MyTimers;

/**
 * @author weiwei
 * @date 2014-7-8 下午05:41:04
 * @description
 * @version
 */
public class MyListener extends HttpServlet {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1485476537907917273L;
	
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(MyListener.class);
	
	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void init(ServletConfig arg0) throws ServletException {
		try {
			WebApplicationContext app = WebApplicationContextUtils.getRequiredWebApplicationContext(arg0.getServletContext());
			// 初始化其他数据
			OthersMgrService othersMgrService = app.getBean(OthersMgrService.class);
			othersMgrService.initCache();
			logger.debug("init others cache finish.");
			
			// 初始化专题缓存数据
			TopicMgrService topicMgrService = app.getBean(TopicMgrService.class);
			topicMgrService.initCache();
			logger.debug("init topic cache finish.");
			
			MyTimers m = new MyTimers(app);
			m.start();
		} catch (Exception e) {
			logger.error("init error. ERROR:", e);
		} 
	}
}
