package com.vst.defend.service.user;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;

/**
 * 
 * @author lhp
 * @date 2019-3-6 上午11:52:19
 * @version
 */
@Service
public interface VstUserActiveChannelBdmodelService {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	ReportBean getExportData(Map<String, Object> params, UserSession user) throws SystemException;
	
	/**
	 * 按设备统计
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	JSONObject getReportByBdmodel(Map<String, Object> params) throws SystemException;
}
