package com.vst.defend.service.realtime;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;

/**
 * 
 * @author lhp
 * @date 2018-3-16 上午10:29:34
 * @version
 */
@Service
public interface VstRealMovieClassifyPlayService {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	ReportBean getExportData(Map<String, Object> params, UserSession user) throws SystemException;
	
	/**
	 * 实时统计
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	JSONObject getReportLine(Map<String, Object> params) throws SystemException;
}
