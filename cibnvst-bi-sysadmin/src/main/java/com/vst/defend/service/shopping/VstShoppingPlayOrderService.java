package com.vst.defend.service.shopping;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;

/**
 * 
 * @author lhp
 * @date 2017-11-28 下午05:09:48
 * @version
 */
@Service
public interface VstShoppingPlayOrderService {
	/**
	 * 查询分页数据
	 * @param cutPage
	 * @return
	 * @throws SystemException
	 */
	ReportBean getPlayOrderCutPage(CutPage cutPage) throws SystemException;
	
	/**
	 * 导出数据
	 * @param params
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	ReportBean getExportData(Map<String, Object> params, UserSession user) throws SystemException;
	
	/**
	 * 查询报表数据(按天统计)
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	ReportBean getPlayOrderReportByDay(Map<String, Object> params) throws SystemException;
	
	/**
	 * 查询报表数据(按小时统计)
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	ReportBean getPlayOrderReportByHour(Map<String, Object> params) throws SystemException;
	
	/**
	 * 统计总量(根据商品ID、名称)
	 * @param cutPage
	 * @return
	 * @throws SystemException
	 */
	ReportBean getPlayOrderReportByGood(CutPage cutPage) throws SystemException;
	
	/**
	 * 导出总量(根据商品ID、名称)
	 * @param params
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	ReportBean getPlayOrderExportByGood(Map<String, Object> params, UserSession user) throws SystemException;
	
	/**
	 * 查询表格数据(按月统计汇总)
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	ReportBean getPlayOrderSumCutPage(Map<String, Object> params) throws SystemException;
	
	/**
	 * 查询报表数据(月汇总统计图)
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	ReportBean getPlayOrderSumReport(Map<String, Object> params) throws SystemException;
	
	/**
	 * 统计总量(根据省份)
	 * @param cutPage
	 * @return
	 * @throws SystemException
	 */
	ReportBean getPlayOrderReportByProvince(CutPage cutPage) throws SystemException;
	
	/**
	 * 导出总量(根据省份)
	 * @param params
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	ReportBean getPlayOrderExportByProvince(Map<String, Object> params, UserSession user) throws SystemException;
	
	/**
	 * 统计总量(根据地址)
	 * @param cutPage
	 * @return
	 * @throws SystemException
	 */
	ReportBean getPlayOrderReportByAddress(CutPage cutPage) throws SystemException;
	
	/**
	 * 导出总量(根据地址)
	 * @param params
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	ReportBean getPlayOrderExportByAddress(Map<String, Object> params, UserSession user) throws SystemException;
	
	/**
	 * 抓取风尚购物数据
	 * @param params
	 * @param user
	 * @return
	 */
	int graspFasionData(Map<String, Object> params, UserSession user);
	
	/**
	 * 抓取环球购物数据
	 * @param params
	 * @param user
	 * @return
	 */
	int graspGlobalbuyData(Map<String, Object> params, UserSession user);
	
	/**
	 * 抓取海莱购物(新)数据
	 * @param params
	 * @param user
	 * @return
	 */
	int graspHailaiData(Map<String, Object> params, UserSession user);
	
	/**
	 * 抓取自营购物数据
	 * @param params
	 * @param user
	 * @return
	 */
	int graspZiyingData(Map<String, Object> params, UserSession user);
	
	/**
	 * 抓取拓亚购物数据
	 * @param params
	 * @param user
	 * @return
	 */
	int graspTollData(Map<String, Object> params, UserSession user);
	
	/**
	 * 抓取拓亚0403数据
	 * @param params
	 * @param user
	 * @return
	 */
	int graspTollData_0403(Map<String, Object> params, UserSession user);
	
	/**
	 * 抓取好享购物数据
	 * @param params
	 * @param user
	 * @return
	 */
	int graspEnjoyData(Map<String, Object> params, UserSession user);
	
	/**
	 * 抓取悠态购物数据
	 * @param params
	 * @param user
	 * @return
	 */
	int graspYoutaiData(Map<String, Object> params, UserSession user);
	
	/**
	 * 抓取央广购物数据
	 * @param params
	 * @param user
	 * @return
	 */
	int graspCnrMallData(Map<String, Object> params, UserSession user);
	
	/**
	 * 抓取家有购物数据
	 * @param params
	 * @param user
	 * @return
	 */
	int graspJiayouData(Map<String, Object> params, UserSession user);
	
	/**
	 * 删除点播订单
	 * @param params
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int deletePlayOrder(Map<String, Object> params, UserSession user) throws SystemException;
	
	/**
	 * 查询报表数据(按渠道统计)
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	Map<String, Object> getPlayOrderReportByChannel(Map<String, Object> params) throws SystemException;
}
