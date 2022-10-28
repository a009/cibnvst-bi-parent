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
 * @date 2018-2-28 上午10:05:04
 * @version
 */
@Service
public interface VstUserPlayService {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	ReportBean getExportData(Map<String, Object> params, UserSession user) throws SystemException;
	
	/**
	 * 按类型统计
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	JSONObject getReportByType(Map<String, Object> params) throws SystemException;
}
