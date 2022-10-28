package com.vst.defend.service.movie;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;

/**
 * 
 * @author lhp
 * @date 2017-11-29 下午07:13:39
 * @version
 */
@Service
public interface VstMovieSiteService {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	ReportBean getExportData(Map<String, Object> params, UserSession user) throws SystemException;
	
	/**
	 * 按平台统计
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	JSONObject getReportBySite(Map<String, Object> params) throws SystemException;
}
