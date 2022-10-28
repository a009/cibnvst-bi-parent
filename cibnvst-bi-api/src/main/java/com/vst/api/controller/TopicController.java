package com.vst.api.controller;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vst.api.cache.CacheDataFactory;
import com.vst.api.cache.CacheSysConfig;
import com.vst.common.tools.io.ToolsIO;
import com.vst.common.tools.string.ToolsString;

/**
 * @author weiwei(joslyn)
 * @date 2017-9-21 下午12:23:22
 * @decription
 * @version 
 */
@Controller
@SuppressWarnings("unchecked")
public class TopicController {

	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(TopicController.class);
	
	/**
	 * 统计action
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/action/**")
	@ResponseBody
	public String topic(HttpServletRequest request){
		String topic = ToolsString.matcher(request.getRequestURI(), "/action/(\\w+)");
		try {
			InputStream is = request.getInputStream();
			// 解压获取客户端发送过来的数据
			String clientData = ToolsIO.uncompressToString(ToolsIO.toArray(is), "utf-8");
			if(!ToolsString.isEmpty(clientData)){
				if(!ToolsString.isEmpty(ToolsString.matcher(clientData.trim(), "^\\{(.*)\\}$"))){
					JSONObject json = (JSONObject) JSONValue.parse(clientData.trim());
					json.put("ipaddr", getIpAddr(request));// 从服务器端角度获取客户端ip地址
					json.put("rectime", System.currentTimeMillis());// 记录接收时间
					json.put("kafkaTopic", topic);
					CacheDataFactory.getInstance().addData(json);
					return "ok";
				}else if(!ToolsString.isEmpty(ToolsString.matcher(clientData.trim(), "^\\[(.*)\\]$"))){
					JSONArray jsonArray = (JSONArray) JSONValue.parse(clientData.trim());
					if(jsonArray != null){
						for(int i = 0; i < jsonArray.size(); i++){
							JSONObject json = (JSONObject) jsonArray.get(i);
							json.put("ipaddr", getIpAddr(request));// 从服务器端角度获取客户端ip地址
							json.put("rectime", System.currentTimeMillis());// 记录接收时间
							json.put("kafkaTopic", topic);
							CacheDataFactory.getInstance().addData(json);
						}
					}
					return "ok";
				}else{// 非法格式,根据配置情况,看看是否需要记录错误数据
					if(CacheSysConfig.getInstance().getIntValue("error_log_open") == 1){
						logger.error("error bi-data ===>>>" + clientData);
					}
					return "ok";
				}
			}
		} catch (Exception e) {
			logger.error("topic error. topic = [" + topic + "], ERROR:", e);
			return "error";
		}
		return "ok";
	}
	
	/**
	 * 统计action(兼容老版本)
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/logs/**")
	@ResponseBody
	public String logs(HttpServletRequest request){
		String topic = null;
		try {
			InputStream is = request.getInputStream();
			// 解压获取客户端发送过来的数据
			String clientData = ToolsIO.uncompressToString(ToolsIO.toArray(is), "utf-8");
			if(!ToolsString.isEmpty(clientData)){
				if(!ToolsString.isEmpty(ToolsString.matcher(clientData.trim(), "^\\{(.*)\\}$"))){
					JSONObject json = (JSONObject) JSONValue.parse(clientData.trim());
					json.put("ipaddr", getIpAddr(request));// 从服务器端角度获取客户端ip地址
					json.put("rectime", System.currentTimeMillis());// 记录接收时间
					topic = ToolsString.checkEmpty(json.get("keyAction"));
					json.put("kafkaTopic", topic);
					CacheDataFactory.getInstance().addData(json);
					return "ok";
				}else if(!ToolsString.isEmpty(ToolsString.matcher(clientData.trim(), "^\\[(.*)\\]$"))){
					JSONArray jsonArray = (JSONArray) JSONValue.parse(clientData.trim());
					if(jsonArray != null){
						for(int i = 0; i < jsonArray.size(); i++){
							JSONObject json = (JSONObject) jsonArray.get(i);
							json.put("ipaddr", getIpAddr(request));// 从服务器端角度获取客户端ip地址
							json.put("rectime", System.currentTimeMillis());// 记录接收时间
							topic = ToolsString.checkEmpty(json.get("keyAction"));
							json.put("kafkaTopic", topic);
							CacheDataFactory.getInstance().addData(json);
						}
					}
					return "ok";
				}else{// 非法格式,根据配置情况,看看是否需要记录错误数据
					if(CacheSysConfig.getInstance().getIntValue("error_log_open") == 1){
						logger.error("error bi-data ===>>>" + clientData);
					}
					return "ok";
				}
			}
		} catch (Exception e) {
			logger.error("topic error. topic = [" + topic + "], ERROR:", e);
			return "error";
		}
		return "ok";
	}
	
	/**
	 * 统计action(兼容老版本)
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/status")
	@ResponseBody
	public String status(HttpServletRequest request){
		return String.valueOf(CacheDataFactory.getInstance().size());
	} 
	
	/**
	 * 获取客户端ip地址
	 * @return
	 */
	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if(!ToolsString.isEmpty(ip)){
			String[] ips = ToolsString.checkEmpty(ip).split(",");
			if(ips.length > 0) {
				ip = ips[0];
			}else{
				ip = null;// 如果没有，置空
			}
		}
		
		if(ToolsString.isEmpty(ip) ||  "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("X-Real-IP");
		}
		if (ToolsString.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ToolsString.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ToolsString.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
