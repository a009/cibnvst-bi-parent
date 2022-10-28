package com.vst.defend.communal.listener;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vst.defend.service.other.OthersMgrService;

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
			// 初始化缓存数据
			OthersMgrService othersMgrService = app.getBean(OthersMgrService.class);
			othersMgrService.initCache();
			
//			// 定时发送邮件
//			Timer sendEmailTimer = new Timer();
//			String path = arg0.getServletContext().getRealPath("/pub/images");
//	        sendEmailTimer.schedule(new SendEmailTimer(app.getBean(IndexService.class), path), 60000, 5000);
			
//			// 每天下午15:00发送邮件（微视听日报）
//			Timer sendEmailTimer = new Timer();
//			TimeZone zone = TimeZone.getTimeZone("Asia/Shanghai");
//			Calendar todayStart = Calendar.getInstance(zone);
//	        todayStart.set(Calendar.HOUR_OF_DAY, 15);
//	        todayStart.set(Calendar.MINUTE, 0);
//	        todayStart.set(Calendar.SECOND, 0);
//	        todayStart.set(Calendar.MILLISECOND, 0);
//	        String path = arg0.getServletContext().getRealPath("/pub/images");
//	        if(todayStart.getTimeInMillis() < System.currentTimeMillis()){
//	        	sendEmailTimer.schedule(new SendEmailTimer(app.getBean(IndexService.class), path), new Date(todayStart.getTimeInMillis() + 24*60*60*1000), 24*60*60*1000);
//	        }else{
//	        	sendEmailTimer.schedule(new SendEmailTimer(app.getBean(IndexService.class), path), todayStart.getTime(), 24*60*60*1000);
//	        }
//	        logger.info("===>>> start send CIBN daily Email thread finish.");
		} catch (Exception e) {
			logger.error("init error. ERROR:", e);
		} 
	}
}
