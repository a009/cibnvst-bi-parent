package com.vst.api.communal.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.vst.api.cache.CacheSysConfig;

/**
 * @author weiwei(joslyn)
 * @date 2017-7-20 下午05:57:23
 * @decription
 * @version 
 */
public class MyFilter implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		arg1.setHeader("Server-HostName", CacheSysConfig.getInstance().getStringValue("serverHostName"));
		return true;
	}

}
