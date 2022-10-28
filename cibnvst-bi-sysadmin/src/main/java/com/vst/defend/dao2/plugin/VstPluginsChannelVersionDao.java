package com.vst.defend.dao2.plugin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author lhp
 * @date 2018-5-15 上午11:38:48
 * @version
 */
@Repository
public interface VstPluginsChannelVersionDao {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExport(Map<String,Object> params);
	
	/**
	 * 按渠道统计
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryReportByChannel(Map<String,Object> params);
	
	/**
	 * 按版本统计
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryReportByVersion(Map<String,Object> params);
	
	/**
	 * 按插件包统计
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryReportByPluginPkg(Map<String,Object> params);
	
	/**
	 * 按插件版本统计
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryReportByPluginVersion(Map<String,Object> params);
}
