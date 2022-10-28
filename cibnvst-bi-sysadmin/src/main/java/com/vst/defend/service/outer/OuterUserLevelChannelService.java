package com.vst.defend.service.outer;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;

/**
 * 
 * @author lhp
 * @date 2018-1-16 下午06:54:44
 * @version
 */
@Service
public interface OuterUserLevelChannelService {
	/**
	 * 查询分页数据
	 * @param cutPage
	 * @return
	 * @throws SystemException
	 */
	ReportBean getCutPageData(CutPage cutPage) throws SystemException;
	
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	ReportBean getExportData(Map<String, Object> params, UserSession user) throws SystemException;

	/**
	 * 查询报表数据(按日期统计)
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	ReportBean getReportByDate(Map<String, Object> params) throws SystemException;
	
	/**
	 * 查询报表数据(按渠道统计)
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	ReportBean getReportByChannel(Map<String, Object> params) throws SystemException;
}
