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
 * @date 2018-1-4 下午07:40:46
 * @version
 */
@Service
public interface VstUserActiveChannelService {
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
}
