package com.vst.defend.service.umeng;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;

/**
 * 
 * @author lhp
 * @date 2018-3-15 下午12:11:53
 * @version
 */
@Service
public interface VstUmengEventMessageService {
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
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	ReportBean getExportData(Map<String, Object> params, UserSession user) throws SystemException;
	
	/**
	 * 抓取事件消息
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	int graspEventMessageData(Map<String, Object> params) throws SystemException;
	
	/**
	 * 删除事件消息
	 * @param params
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int deleteEventMessage(Map<String, Object> params, UserSession user) throws SystemException;
	
	/**
	 * 查询汇总数据
	 * @param cutPage
	 * @return
	 * @throws SystemException
	 */
	ReportBean getCutPageDataSum(CutPage cutPage) throws SystemException;
	
	/**
	 * 导出汇总数据
	 * @param params
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	ReportBean getExportDataSum(Map<String, Object> params, UserSession user) throws SystemException;
}
