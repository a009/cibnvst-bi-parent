package com.vst.defend.service.report;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.vst.defend.communal.exception.SystemException;

/**
 * 
 * @author lhp
 * @date 2017-9-12 下午02:35:56
 * @version
 */
@Service
public interface VstAutoReportService {

	JSONObject getJsonByCode(Map<String, Object> object) throws SystemException;
	
	
	JSONObject getChartJson(Map<String, Object> object) throws SystemException;
}
