package com.vst.defend.communal.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.vst.common.tools.string.ToolsString;

/**
 * @author weiwei(joslyn)
 * @date 2017-6-7 下午12:11:58
 * @decription
 * @version 
 */
public class ToolsParams {

	/**
	 * 空map
	 */
	private final static Map<String, String> EMPTY_MAP = new HashMap<String, String>(0);
	
	/**
	 * 构造器私有化
	 */
	private ToolsParams(){
		
	}
	
	/**
	 * 获取参数
	 * @param request
	 * @return
	 */
	public static Map<String, String> initParam(HttpServletRequest request){
		return initParam(request.getRequestURI());
	}
	
	/**
	 * 获取参数
	 * @param request
	 * @return
	 */
	public static Map<String, String> initParam(String uri){
		if(!ToolsString.isEmpty(uri) && uri.length() > 4 && uri.indexOf(".dat") == uri.length() - 4){
			String[] params = uri.replace(".dat", "").split("/");
			Map<String, String> paramMap = new HashMap<String, String>();
			for(String p : params){
				if(p.contains("_") && p.indexOf("_") < p.length() - 1)
				paramMap.put(p.substring(0, p.indexOf("_")), p.substring(p.indexOf("_") + 1));
			}
			return paramMap;
		}
		return EMPTY_MAP;
	}
}
