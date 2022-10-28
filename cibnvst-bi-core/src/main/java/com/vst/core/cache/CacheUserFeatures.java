package com.vst.core.cache;

import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.core.communal.sql.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author weiwei(joslyn)
 * @date 2017-9-29 下午03:25:01
 * @decription
 * @version
 */
public class CacheUserFeatures {

	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(CacheUserFeatures.class);

	/**
	 * 单例类
	 */
	private static final CacheUserFeatures _cache = new CacheUserFeatures();

	/**
	 * features配置列表
	 */
	private List<FeatureConfig> _featuresConfig;

	/**
	 * 是否停止拉取数据任务
	 */
	private boolean isStopPullTasks = false;

	/**
	 * lock
	 */
	private byte[] lock = new byte[0];

	/**
	 * 构造器私有化
	 */
	private CacheUserFeatures(){
		_featuresConfig = new ArrayList<>();
	}

	/**
	 * 获取单例类
	 * @return
	 */
	public static CacheUserFeatures getInstance(){
		return _cache;
	}

	/**
	 * 加载sql配置
	 * @param featureList
	 */
	public void loadFeatureConfig(List<JSONObject> featureList){
		if(featureList == null || featureList.isEmpty()){
			logger.debug("===>>> user feature config is null.");
		}else{
			wrapData(featureList, _featuresConfig);
			logger.debug("===>>> loading user feature config finish.");
		}
	}

	/**
	 * 封装数据
	 * @param featureList
	 */
	private void wrapData(List<JSONObject> featureList,List<FeatureConfig> featuresConfig){
		// 封装筛选条件
		if(featureList != null && !featureList.isEmpty()){
			FeatureConfig config;
			for(JSONObject featureJson : featureList){
				String pkg = ToolsString.checkEmpty(featureJson.get("vst_features_pkg"));
				int specId = ToolsNumber.parseInt(ToolsString.checkEmpty(featureJson.get("vst_features_special_type")));
				int cid = ToolsNumber.parseInt(ToolsString.checkEmpty(featureJson.get("vst_features_cid")));
				int type = ToolsNumber.parseInt(ToolsString.checkEmpty(featureJson.get("vst_features_type")));
				String name = ToolsString.checkEmpty(ToolsString.checkEmpty(featureJson.get("vst_features_name")));
				BigDecimal value = new BigDecimal(ToolsNumber.parseDouble(ToolsString.checkEmpty(featureJson.get("vst_features_value"))));

				config = new FeatureConfig(pkg,specId,cid);
				if(featuresConfig.contains(config)){
					int index = featuresConfig.indexOf(config);
					featuresConfig.get(index).setFeatureInfo(type,name,value);
				}else{
					config.setFeatureInfo(type,name,value);
					featuresConfig.add(config);
				}
			}
		}
	}

	public List<FeatureConfig> getAllFeatures(){
		return _featuresConfig;
	}

	/**
	 * 是否停止拉取数据线程任务
	 * @return
	 */
	public boolean isStopPullTasks() {
		return isStopPullTasks;
	}

	/**
	 * 设置拉取数据线程任务启动开关
	 * @return
	 */
	public void setStopPullTasks(boolean flag){
		isStopPullTasks = flag;
	}

	public byte[] getLock() {
		return lock;
	}

	/**
	 * 刷新缓存
	 * @param featureList
	 */
	public void reloadSparkSqlConfig(List<JSONObject> featureList){
		List<FeatureConfig> featuresConfig = new ArrayList<>();
		wrapData(featureList,featuresConfig);
		_featuresConfig = featuresConfig;
	}

}
