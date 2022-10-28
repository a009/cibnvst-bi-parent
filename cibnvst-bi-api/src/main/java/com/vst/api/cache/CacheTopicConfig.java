package com.vst.api.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;

import com.vst.api.communal.bean.RangeBean;
import com.vst.api.communal.bean.TopicPropBean;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;

/**
 * @author weiwei(joslyn)
 * @date 2017-9-20 下午12:02:21
 * @decription
 * @version 
 */
public class CacheTopicConfig{

	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(CacheSysConfig.class);
	
	/**
	 * 单例类
	 */
	private static final CacheTopicConfig _cache = new CacheTopicConfig();
	
	/**
	 * 系统配置属性
	 */
	private Map<String, List<TopicPropBean>> _configMap;
	
	/**
	 * 构造器私有化
	 */
	private CacheTopicConfig(){
		_configMap = new HashMap<String, List<TopicPropBean>>();
	}
	
	/**
	 * 获取单例类
	 * @return
	 */
	public static CacheTopicConfig getInstance(){
		return _cache;
	}

	/**
	 * 初始化缓存
	 * @param list
	 */
	public void initCache(List<Map<String, Object>> list) {
		if(list == null || list.isEmpty()){
			logger.debug("There is no sysTopicConfig in database! So we will not cache anything for this!");
		}else{
			wrapData(list, _configMap);
		}
	}
	
	/**
	 * 封装数据
	 * @param list
	 * @param configMap
	 */
	private void wrapData(List<Map<String, Object>> list, Map<String, List<TopicPropBean>> configMap){
		List<TopicPropBean> tempList = null;
		TopicPropBean bean = null;
		for(Map<String, Object> map : list){
			// 校验topicId是否为空
			String topicId = ToolsString.checkEmpty(map.get("vst_topic_id"));
			if(ToolsString.isEmpty(topicId)) continue;
			// 校验topic名称
			String topicValue = ToolsString.checkEmpty(map.get("vst_topic_value"));
			if(ToolsString.isEmpty(topicValue)) continue;
			// 校验topic对应的属性名称
			String propName = ToolsString.checkEmpty(map.get("vst_prop_name"));
			if(ToolsString.isEmpty(propName)) continue;
			
			tempList = configMap.get(topicValue);
			if(tempList == null){
				tempList = new ArrayList<TopicPropBean>();
				configMap.put(topicValue, tempList);
			}
			
			// 封装每个topic的属性bean
			bean = new TopicPropBean();
			// 属性名称
			bean.setPropName(propName);
			// 属性默认值
			bean.setPropValueDefault(ToolsString.checkEmpty(map.get("vst_prop_value_default")));
			// 属性类型 String、Integer、Long、Boolean、JSONObject、JSONArray
			bean.setPropValueType(ToolsString.checkEmpty(map.get("vst_prop_value_type")));
			// 属性是否必填，1：是，2：否
			bean.setPropValueRequired(ToolsNumber.parseInt(String.valueOf(map.get("vst_prop_value_required")), 2));
			// 属性值范围，格式说明：如果是数字类型，用[-,+]表示负无穷大到正无穷大；如果是字符串或boolean类型，用{"a","b"}来枚举，用![0, 10]来表示本身字符串长度的限定
			bean.setPropValueRange(ToolsString.checkEmpty(map.get("vst_prop_value_range")));
			tempList.add(bean);
		}
	}
	
	/**
	 * 校验json数据
	 * @param topicValue
	 * @param json
	 * @return
	 */
	public boolean checkFormat(String topicValue, JSONObject json){
		// 校验对象不空，才有意义校验
		if(json != null && !json.isEmpty()){
			// 根据topic名称获取该topic属性值列表
			List<TopicPropBean> tempList = _configMap.get(topicValue);
			if(tempList != null && !tempList.isEmpty()){
				for(TopicPropBean bean : tempList){
					String propName = bean.getPropName();
					Object value = json.get(propName);
					// 属性是否必填，1：是，2：否
					boolean isRequired = bean.getPropValueRequired().intValue() == 1;
					// 1、如果属性值必填，数据里面就必须要有属性key和对应的value值
					if(isRequired && (!json.containsKey(propName) || ToolsString.isEmpty(value))){
						logger.error("prop is required but key[" + propName + "] or value[" + value + "] is null");
						return false;
					}
					if(value != null){
						// 2、判断属性值类型是否合法
						String type = bean.getPropValueType();
						String currType = value.getClass().getSimpleName();
						if(!ToolsString.isEmpty(type) && !ToolsString.isEmpty(currType) && !type.equalsIgnoreCase(currType)){
							logger.error("prop's type[" + currType + "] doesn't match [" + type + "] according to key[" + propName + "] and value[" + value + "]");
							return false;
						}
						// 3、检查值的范围是否合法
						String range = bean.getPropValueRange();
						RangeBean rangeBean = new RangeBean(range);
						if(!rangeBean.checkRange(value).isRight()){
							logger.error("prop's value[" + value + "] is not between [" + range + "] according to key[" + propName + "]");
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * 刷新缓存
	 * @param list
	 */
	public void flushCache(List<Map<String, Object>> list){
		Map<String, List<TopicPropBean>> configMap = new HashMap<String, List<TopicPropBean>>();
		wrapData(list, configMap);
		_configMap = configMap;
	}
}
