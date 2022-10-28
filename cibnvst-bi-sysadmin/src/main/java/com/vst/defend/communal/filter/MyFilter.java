package com.vst.defend.communal.filter;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.util.VstConstants;

/**
 * @author weiwei(joslyn)
 * @date 2017-7-20 下午05:57:23
 * @decription
 * @version 
 */
public class MyFilter implements HandlerInterceptor{

	private static final Log logger = LogFactory.getLog(MyFilter.class);

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		//最后执行
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		//可以用来修改信息，跳转等
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj) throws Exception {
		//todo 在此处添加要操作code 
		String url = request.getRequestURL().toString();
		int index = url.lastIndexOf('/');
		logger.info("请求的路径: " + url.substring(0, url.length()));
		
		String action = url.substring(index+1, url.length());
		logger.info("请求的action: " + action);
		
		//获取用户信息session
		UserSession user = (UserSession) request.getSession().getAttribute(VstConstants.SESSION_KEY_USER);
		if(action.equals("login.action") || user != null
				// 校验登录
				|| action.equals("checkLoginUser.action") || action.equals("changeHome.action")
				|| action.equals("getMoviePlay.action")
				|| action.equals("getMovieSearch.action")
				|| action.equals("getMoviePlayTop.action")
				|| action.equals("getMovieClickTop.action")
				|| action.equals("getMovieBlock.action")
				|| action.equals("getShoppingPlay.action")
				|| action.equals("getShoppingLive.action")
				|| action.equals("json")){
			return true;//继续执行action,不拦截登录的方法。
		}else{
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.print("<script type='text/javascript'>alert('您登录超时,或被强制下线!');location='../sysMain/login.action';</script>");
			return false;//此处为false时，请求不会到达control层
		}
	}

}
