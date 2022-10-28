package com.vst.defend.service.plugin;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;

/**
 * 
 * @author lhp
 * @date 2018-5-15 上午11:42:57
 * @version
 */
@Service
public interface VstPluginsChannelVersionService {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	ReportBean getExportData(Map<String, Object> params, UserSession user) throws SystemException;
	
	/**
	 * 按渠道统计
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	JSONObject getReportByChannel(Map<String, Object> params) throws SystemException;
	
	/**
	 * 按版本统计
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	JSONObject getReportByVersion(Map<String, Object> params) throws SystemException;
	
	/**
	 * 按插件包统计
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	JSONObject getReportByPluginPkg(Map<String, Object> params) throws SystemException;
	
	/**
	 * 按插件版本统计
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	JSONObject getReportByPluginVersion(Map<String, Object> params) throws SystemException;
}
