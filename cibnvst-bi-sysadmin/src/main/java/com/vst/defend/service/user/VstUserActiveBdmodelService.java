package com.vst.defend.service.user;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;

/**
 * 
 * @author lhp
 * @date 2019-3-6 上午09:59:40
 * @version
 */
@Service
public interface VstUserActiveBdmodelService {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	ReportBean getExportData(Map<String, Object> params, UserSession user) throws SystemException;
}
