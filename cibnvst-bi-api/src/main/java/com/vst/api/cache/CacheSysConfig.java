package com.vst.api.cache;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;

/**
 * @author weiwei
 * @date 2015-9-8 下午05:22:55
 * @description 
 * @version	
 */
public class CacheSysConfig {

	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(CacheSysConfig.class);
	
	/**
	 * 单例类
	 */
	private static final CacheSysConfig _cache = new CacheSysConfig();
	
	/**
	 * 系统配置属性
	 */
	private Map<String, String> _configMap = new HashMap<String, String>();
	
	/**
	 * 构造器私有化
	 */
	private CacheSysConfig(){
		
	}
	
	/**
	 * 获取单例类
	 * @return
	 */
	public static CacheSysConfig getInstance(){
		return _cache;
	}
	
	/**
	 * 加载系统配置
	 * @param list
	 */
	public void loadSysConfig(List<Map<String, Object>> list){
		if(list == null || list.isEmpty()){
			logger.debug("There is no sysConfig in database! So we will not cache anything for sysConfig!");
		}else{
			wrapData(list, _configMap);
		}
	}
	
	/**
	 * 封装数据
	 * @param list
	 * @param configMap
	 */
	private void wrapData(List<Map<String, Object>> list, Map<String, String> configMap){
		for(Map<String, Object> map : list){
			configMap.put(ToolsString.checkEmpty(map.get("vst_option_key")), ToolsString.checkEmpty(map.get("vst_option_value")));
		}
		try {
			configMap.put("serverHostName", InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
		}
	}
	
	/**
	 * 获取value，字符串类型的
	 * @param key
	 * @return
	 */
	public String getStringValue(String key){
		return ToolsString.checkEmpty(_configMap.get(key));
	}
	
	/**
	 * 获取value，int类型的
	 * @param key
	 * @return
	 */
	public int getIntValue(String key){
		return ToolsNumber.parseInt(_configMap.get(key));
	}
	
	/**
	 * 获取value，long类型的
	 * @param key
	 * @return
	 */
	public long getLongValue(String key){
		return ToolsNumber.parseLong(_configMap.get(key));
	}
	
	/**
	 * 刷新缓存
	 * @param list
	 */
	public void reloadSysConfig(List<Map<String, Object>> list){
		Map<String, String> configMap = new HashMap<String, String>();
		wrapData(list, configMap);
		_configMap = configMap;
	}
}
