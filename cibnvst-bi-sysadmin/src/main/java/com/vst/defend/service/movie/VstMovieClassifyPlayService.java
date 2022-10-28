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
 * @date 2017-12-1 下午03:13:23
 * @version
 */
@Service
public interface VstMovieClassifyPlayService {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	ReportBean getExportData(Map<String, Object> params, UserSession user) throws SystemException;
	
	/**
	 * 导出总量
	 * @param params
	 * @return
	 */
	ReportBean getExportSumData(Map<String, Object> params, UserSession user) throws SystemException;
	
	/**
	 * 按分类统计
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	JSONObject getReportByCid(Map<String, Object> params) throws SystemException;
}
