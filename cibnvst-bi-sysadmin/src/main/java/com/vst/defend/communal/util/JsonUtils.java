package com.vst.defend.communal.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vst.common.tools.string.ToolsString;

/**
 * @author weiwei(joslyn)
 * @date 2017-5-17 下午08:09:16
 * @decription
 * @version 
 */
public class JsonUtils {
	
	public static final JSONObject _EMPTY_JSON = new JSONObject();
	public static final JSONArray _EMPTY_JSONARRAY = new JSONArray();

	/**
	 * 构造器私有化
	 */
	private JsonUtils(){
		
	}
	
	/**
	 * 封装空json
	 * @param code
	 * @return
	 */
	public static JSONObject wrapEmptyJson(int code){
		JSONObject json = new JSONObject();
		JSONObject info = new JSONObject();
		info.put("cacheTime", System.currentTimeMillis());
		json.put("info", info);
		json.put("data", _EMPTY_JSON);
		json.put("code", code);
		return json;
	}
	
	/**
	 * 封装空json
	 * @param code
	 * @return
	 */
	public static JSONObject wrapEmptyJson(int code, String errorMsg){
		JSONObject json = new JSONObject();
		JSONObject info = new JSONObject();
		info.put("cacheTime", System.currentTimeMillis());
		info.put("errorMsg", errorMsg);
		json.put("info", info);
		json.put("data", _EMPTY_JSON);
		json.put("code", code);
		return json;
	}
	
	/**
	 * 封装空json
	 * @param code
	 * @return
	 */
	public static JSONObject wrapEmptyJsonArray(int code){
		JSONObject json = new JSONObject();
		JSONObject info = new JSONObject();
		info.put("cacheTime", System.currentTimeMillis());
		json.put("info", info);
		json.put("data", _EMPTY_JSONARRAY);
		json.put("code", code);
		return json;
	}
	
	/**
	 * 封装空json
	 * @param code
	 * @return
	 */
	public static JSONObject wrapEmptyJsonArray(int code, String errorMsg){
		JSONObject json = new JSONObject();
		JSONObject info = new JSONObject();
		info.put("cacheTime", System.currentTimeMillis());
		info.put("errorMsg", errorMsg);
		json.put("info", info);
		json.put("data", _EMPTY_JSONARRAY);
		json.put("code", code);
		return json;
	}
	
	/**
	 * 封装空json
	 * @param code
	 * @return
	 */
	public static JSONObject wrapEmptyJsonString(int code){
		JSONObject json = new JSONObject();
		JSONObject info = new JSONObject();
		info.put("cacheTime", System.currentTimeMillis());
		json.put("info", info);
		json.put("data", ToolsString._EMPTY);
		json.put("code", code);
		return json;
	}
	
	public static JSONObject wrapEmptyJsonString(int code, String errorMsg){
		JSONObject json = new JSONObject();
		JSONObject info = new JSONObject();
		info.put("cacheTime", System.currentTimeMillis());
		info.put("errorMsg", errorMsg);
		json.put("info", info);
		json.put("data", ToolsString._EMPTY);
		json.put("code", code);
		return json;
	}
}
