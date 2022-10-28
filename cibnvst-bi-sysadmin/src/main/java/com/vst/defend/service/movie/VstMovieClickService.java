package com.vst.defend.service.movie;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;

/**
 * 
 * @author lhp
 * @date 2017-11-27 下午12:24:19
 * @version
 */
@Service
public interface VstMovieClickService {
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
}
