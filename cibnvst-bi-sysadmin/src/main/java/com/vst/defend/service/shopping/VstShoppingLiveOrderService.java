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
 * @date 2017-11-28 下午06:17:06
 * @version
 */
@Service
public interface VstShoppingLiveOrderService {
	/**
	 * 查询分页数据
	 * @param cutPage
	 * @return
	 * @throws SystemException
	 */
	ReportBean getLiveOrderCutPage(CutPage cutPage) throws SystemException;
	
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
	ReportBean getLiveOrderReportByDay(Map<String, Object> params) throws SystemException;
	
	/**
	 * 查询报表数据(按小时统计)
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	ReportBean getLiveOrderReportByHour(Map<String, Object> params) throws SystemException;
	
	/**
	 * 统计总量(根据商品ID、名称)
	 * @param cutPage
	 * @return
	 * @throws SystemException
	 */
	ReportBean getLiveOrderReportByGood(CutPage cutPage) throws SystemException;
	
	/**
	 * 导出总量(根据商品ID、名称)
	 * @param params
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	ReportBean getLiveOrderExportByGood(Map<String, Object> params, UserSession user) throws SystemException;
	
	/**
	 * 查询表格数据(按月统计汇总)
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	ReportBean getLiveOrderSumCutPage(Map<String, Object> params) throws SystemException;
	
	/**
	 * 统计占比(根据商品分类)
	 * @param params
	 * @return
	 */
	ReportBean getLiveOrderReportByCategory(Map<String, Object> params) throws SystemException;
	
	/**
	 * 统计总量(根据来源)
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	ReportBean getLiveOrderReportBySource(Map<String, Object> params) throws SystemException;

	/**
	 * 统计总量(根据省份)
	 * @param cutPage
	 * @return
	 * @throws SystemException
	 */
	ReportBean getLiveOrderReportByProvince(CutPage cutPage) throws SystemException;
	
	/**
	 * 导出总量(根据省份)
	 * @param params
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	ReportBean getLiveOrderExportByProvince(Map<String, Object> params, UserSession user) throws SystemException;
	
	/**
	 * 统计总量(根据地址)
	 * @param cutPage
	 * @return
	 * @throws SystemException
	 */
	ReportBean getLiveOrderReportByAddress(CutPage cutPage) throws SystemException;
	
	/**
	 * 导出总量(根据地址)
	 * @param params
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	ReportBean getLiveOrderExportByAddress(Map<String, Object> params, UserSession user) throws SystemException;
	
	/**
	 * 抓取环球购物数据
	 * @param params
	 * @param user
	 */
	int graspGlobalbuyData(Map<String, Object> params, UserSession user);
	
	/**
	 * 抓取UGO购物(优品购)
	 * @param params
	 * @param user
	 * @return
	 */
	int graspUgoData(Map<String, Object> params, UserSession user);
	
	/**
	 * 抓取海莱购物(新)数据
	 * @param params
	 * @param user
	 * @return
	 */
	int graspHailaiData(Map<String, Object> params, UserSession user);
	
	/**
	 * 抓取时尚购物数据
	 * @param params
	 * @param user
	 * @return
	 */
	int graspFashionData(Map<String, Object> params, UserSession user);
	
	/**
	 * 抓取多乐播购物数据
	 * @param params
	 * @param user
	 * @return
	 */
	int graspDuoleboData(Map<String, Object> params, UserSession user);
	
	/**
	 * 抓取快乐购物数据
	 * @param params
	 * @param user
	 * @return
	 */
	int graspHappyData(Map<String, Object> params, UserSession user);
	
	/**
	 * 删除直播订单
	 * @param params
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int deleteLiveOrder(Map<String, Object> params, UserSession user) throws SystemException;
	
	/**
	 * 查询报表数据(按渠道统计)
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	Map<String, Object> getLiveOrderReportByChannel(Map<String, Object> params) throws SystemException;
}
