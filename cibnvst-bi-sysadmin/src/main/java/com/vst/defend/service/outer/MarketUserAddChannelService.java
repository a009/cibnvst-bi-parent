package com.vst.defend.service.outer;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;

/**
 * 
 * @author lhp
 * @date 2018-3-7 上午11:24:23
 * @version
 */
@Service
public interface MarketUserAddChannelService {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	ReportBean getExportData(Map<String, Object> params, UserSession user) throws SystemException;
}
