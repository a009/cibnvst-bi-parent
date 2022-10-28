package com.vst.defend.service.page;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;

/**
 * 
 * @author lhp
 * @date 2018-5-16 上午10:26:21
 * @version
 */
@Service
public interface VstPageJumpService {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	ReportBean getExportData(Map<String, Object> params, UserSession user) throws SystemException;
	
	/**
	 * 按当前页面导出
	 * @param params
	 * @return
	 */
	ReportBean getExportDataByCurrPage(Map<String, Object> params, UserSession user) throws SystemException;
	
	/**
	 * 按跳转页面统计
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	JSONObject getReportByJumpPage(Map<String, Object> params) throws SystemException;
}
