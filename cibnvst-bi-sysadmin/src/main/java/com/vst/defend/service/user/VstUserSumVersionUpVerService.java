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
 * @date 2018-1-4 下午03:34:54
 * @version
 */
@Service
public interface VstUserSumVersionUpVerService {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	ReportBean getExportData(Map<String, Object> params, UserSession user) throws SystemException;
	
	/**
	 * 按升级版本统计
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	JSONObject getReportByUpVer(Map<String, Object> params) throws SystemException;
}
