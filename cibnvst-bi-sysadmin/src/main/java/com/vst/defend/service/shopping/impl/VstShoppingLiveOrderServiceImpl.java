package com.vst.defend.service.shopping.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vst.common.pojo.VstShoppingLiveOrder;
import com.vst.common.pojo.VstSysOperateLog;
import com.vst.common.tools.date.ToolsDate;
import com.vst.common.tools.http.Request;
import com.vst.common.tools.http.ToolsHttp;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.CutPage;
import com.vst.defend.communal.util.ExportUtil;
import com.vst.defend.communal.util.Html;
import com.vst.defend.communal.util.SslUtils;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.dao3.shopping.VstShoppingLiveOrderDao;
import com.vst.defend.service.shopping.VstShoppingLiveOrderService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author lhp
 * @date 2017-11-28 下午06:17:44
 * @version
 */
@Service
public class VstShoppingLiveOrderServiceImpl implements VstShoppingLiveOrderService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstShoppingLiveOrderServiceImpl.class);
	
	/**
	 * 直播订单Dao接口
	 */
	@Resource
	private VstShoppingLiveOrderDao _vstShoppingLiveOrderDao;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 环球抓取接口
	 */
	private static String GLOBALBUY_URL = "http://distributor.cps.ghs.net/order?s={startDay}&e={endDay}&payment_confirmed=&sid=&page={page}";
	
	/**
	 * UGO抓取接口
	 */
	private static String UGO_URL = "http://scm.huimai365.com/biz/partner/chargemng/getOrdIdList.do";
	
	/**
	 * 海莱抓取接口
	 */
	private static String HAILAI_URL = "http://www.yunshigo.com:9003/getb2brep";
	
	/**
	 * 时尚抓取接口
	 */
	private static String FASHION_URL = "http://scm.huimai365.com/biz/partner/chargemng/getOrdIdList.do";
	
	/**
	 * 多乐播抓取接口
	 */
	private static String DUOLEBO_URL = "http://tv.duolebo.com:8091/manage/billingSplit/query.do?_dc={now}&billingDateStart={startDay}&billingDateEnd={endDay}&keywords=&channelIds=&checked=&billingStatus=&deliverStatus=&settlementDateStart=&settlementDateEnd=&returnDateStart=&returnDateEnd=&provider=&province=&city=&statusLock=false&page={page}&start={start}&limit=100";
	
	/**
	 * 快乐购抓取接口
	 */
	private static String HAPPY_URL = "https://hbp2.happigo.com/order/orderDetail/getOrderDetailInfo?limit=100&offset={offset}&fr_date={startDay}&to_date={endDay}";
	
	/**
	 * 查询分页数据
	 */
	@Override
	public ReportBean getLiveOrderCutPage(CutPage cutPage) throws SystemException {
		ReportBean bean = new ReportBean();
		try{
			Map<String, Object> params = cutPage.get_castQueryParam();
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				if(!ToolsString.isEmpty(params.get("startDate")+"")){
					params.put("startDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("startDate")+""));
				}
				if(!ToolsString.isEmpty(params.get("endDate")+"")){
					params.put("endDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("endDate")+""));
				}
			}
			
			int count = _vstShoppingLiveOrderDao.queryCount(params);
			
			if(count != 0){
				int start = (cutPage.get_currPage()-1) * cutPage.get_singleCount();
				
				params.put("offset", start);
				params.put("limit", cutPage.get_singleCount());
				
				List<Map<String, Object>> list = _vstShoppingLiveOrderDao.queryForList(params);
				
				for(Map<String, Object> map : list){
					// 转换日期格式
					if(!ToolsString.isEmpty(map.get("vst_shopping_live_order_create_time")+"")){
						if("0".equals(map.get("vst_shopping_live_order_create_time")+"")){
							map.put("vst_shopping_live_order_create_time", "");
						}else{
							map.put("vst_shopping_live_order_create_time", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm, map.get("vst_shopping_live_order_create_time")+""));
						}
					}
					if(!ToolsString.isEmpty(map.get("vst_shopping_live_order_balance_time")+"")){
						if("0".equals(map.get("vst_shopping_live_order_balance_time")+"")){
							map.put("vst_shopping_live_order_balance_time", "");
						}else{
							map.put("vst_shopping_live_order_balance_time", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm, map.get("vst_shopping_live_order_balance_time")+""));
						}
					}
					map.put("vst_shopping_live_order_date", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm, map.get("vst_shopping_live_order_date")+""));
					map.put("vst_shopping_live_order_addtime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm, map.get("vst_shopping_live_order_addtime")+""));
					map.put("vst_shopping_live_order_uptime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm, map.get("vst_shopping_live_order_uptime")+""));
				}
				
				bean.setTotalSize(count); //设置总行数
				bean.setSingleSize(cutPage.get_singleCount());//设置单页显示行数
				bean.setMapData(list);
			}
		}catch(Exception e){
			logger.error("查询直播订单分页数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
	
	/**
	 * 导出数据
	 */
	@Override
	public ReportBean getExportData(Map<String, Object> params, UserSession user) throws SystemException {
		ReportBean bean = new ReportBean();
		try{
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				if(!ToolsString.isEmpty(params.get("startDate")+"")){
					params.put("startDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("startDate")+""));
				}
				if(!ToolsString.isEmpty(params.get("endDate")+"")){
					params.put("endDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("endDate")+""));
				}
			}
			
			List<Map<String, Object>> data = _vstShoppingLiveOrderDao.queryExport(params);
			if(data != null && data.size() > 0){
				
				Map<String, Object> temp = new HashMap<String, Object>(3);
				// 获取商品渠道
				temp.put("vst_table_name", "vst_shopping_live_order");
				temp.put("vst_column_name", "channel");
				temp.put("vst_state", VstConstants.STATE_AVALIABLE);
				Map<String, String> channelMap = _baseService.getDictionaryForMap(temp);
				
				// 导出字段内容
				String export_column = ToolsString.checkEmpty(params.get("export_column"));
				List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
				
				for(Map<String, Object> map : data){
					//更改日期格式
					if(!ToolsString.isEmpty(map.get("日期")+"")){
						map.put("日期", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm, map.get("日期")+""));
					}
					if(!ToolsString.isEmpty(map.get("创建时间")+"")){
						if("0".equals(map.get("创建时间")+"")){
							map.put("创建时间", "");
						}else{
							map.put("创建时间", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm, map.get("创建时间")+""));
						}
						
					}
					if(!ToolsString.isEmpty(map.get("对账时间")+"")){
						if("0".equals(map.get("对账时间")+"")){
							map.put("对账时间", "");
						}else{
							map.put("对账时间", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm, map.get("对账时间")+""));
						}
					}
					if(channelMap.containsKey(map.get("渠道")+"")){
						map.put("渠道", channelMap.get(map.get("渠道")+""));
					}
					// 只保留关键字段
					Map<String, Object> tmp = new LinkedHashMap<String, Object>();
					for(String key : map.keySet()){
						if(export_column.indexOf(key) >= 0){
							tmp.put(key, map.get(key));
						}
					}
					dataList.add(tmp);
				}
				bean.setMapData(dataList);
			}
			bean.setTitle("vst_shopping_live_order_data");
			
			// 写操作日志
			StringBuilder sb = new StringBuilder();
			sb.append("导出数据，查询条件：").append(params);
			_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_EXPORT, sb.toString()));
		}catch(Exception e){
			logger.error("导出直播订单数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
	
	/**
	 * 查询报表数据(按天统计)
	 */
	@Override
	public ReportBean getLiveOrderReportByDay(Map<String, Object> params) throws SystemException {
		ReportBean bean = new ReportBean();
		try {
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				if(!ToolsString.isEmpty(params.get("startDate")+"")){
					params.put("startDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("startDate")+""));
				}
				if(!ToolsString.isEmpty(params.get("endDate")+"")){
					params.put("endDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("endDate")+""));
				}
			}
			
			List<Map<String, Object>> list = _vstShoppingLiveOrderDao.queryReportByDate(params);
			if(list != null && list.size() > 0){
				List<JSONObject> data = new ArrayList<JSONObject>();
				for(Map<String, Object> map : list){
					String date = ToolsDate.formatDate(ToolsDate.yyyy_MM_dd4, map.get("date")+"");
					map.put("date", date);
					boolean flag = true;
					for(JSONObject json2 : data){
						String date2 = json2.getString("date");
						if(date2.equals(date)){	//如果date中已经有了这一天数据，直接去修改这一天的数据
							int qty = ToolsNumber.parseInt(map.get("qty")+"");
							double salePrice = ToolsNumber.parseDouble(map.get("salePrice")+"");
							json2.put("num", json2.getIntValue("num") + 1);
							json2.put("qty", qty + json2.getIntValue("qty"));
							json2.put("salePrice", salePrice + json2.getDoubleValue("salePrice"));
							flag = false;
							break;
						}
					}
					if(flag){	//如果date中没有这一天数据，那就添加进来
						JSONObject json = new JSONObject(map);
						json.put("num", 1);
						data.add(json);
					}
				}
				bean.setData(data);
			}
		} catch (Exception e) {
			logger.error("查询直播订单(按天统计)失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}

	/**
	 * 查询报表数据(按小时统计)
	 */
	@Override
	public ReportBean getLiveOrderReportByHour(Map<String, Object> params) throws SystemException {
		ReportBean bean = new ReportBean();
		List<JSONObject> data = new ArrayList<JSONObject>(24);
		for(int i=0; i<24; i++){
			JSONObject json = new JSONObject();
			json.put("date", i);
			json.put("num", 0);
			json.put("qty", 0);
			json.put("salePrice", 0);
			data.add(json);
		}
		
		try {
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				if(!ToolsString.isEmpty(params.get("startDate")+"")){
					params.put("startDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("startDate")+""));
				}
				if(!ToolsString.isEmpty(params.get("endDate")+"")){
					params.put("endDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("endDate")+""));
				}
			}
			List<Map<String, Object>> list = _vstShoppingLiveOrderDao.queryReportByDate(params);
			if(list != null && list.size() > 0){
				for(Map<String, Object> map : list){
					String date = ToolsDate.formatDate(ToolsDate.HH, map.get("date")+"");
					int hour = ToolsNumber.parseInt(date);
					JSONObject json2 = data.get(hour);
					int qty = ToolsNumber.parseInt(map.get("qty")+"");
					double salePrice = ToolsNumber.parseDouble(map.get("salePrice")+"");
					json2.put("num", json2.getIntValue("num") + 1);
					json2.put("qty", qty + json2.getIntValue("qty"));
					json2.put("salePrice", salePrice + json2.getDoubleValue("salePrice"));
				}
			}
			bean.setData(data);
		} catch (Exception e) {
			logger.error("查询直播订单(按小时统计)失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}

	/**
	 * 统计总量(根据商品ID、名称)
	 */
	@Override
	public ReportBean getLiveOrderReportByGood(CutPage cutPage) throws SystemException {
		ReportBean bean = new ReportBean();
		try {
			Map<String, Object> params = cutPage.get_castQueryParam();
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				if(!ToolsString.isEmpty(params.get("startDate")+"")){
					params.put("startDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("startDate")+""));
				}
				if(!ToolsString.isEmpty(params.get("endDate")+"")){
					params.put("endDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("endDate")+""));
				}
			}
			
			int count = _vstShoppingLiveOrderDao.queryCountByGood(params);
			if(count != 0){
				int start = (cutPage.get_currPage()-1) * cutPage.get_singleCount();
				
				params.put("offset", start);
				params.put("limit", cutPage.get_singleCount());
				
				List<Map<String, Object>> list = _vstShoppingLiveOrderDao.queryListByGood(params);
				
				bean.setTotalSize(count); //设置总行数
				bean.setSingleSize(cutPage.get_singleCount());//设置单页显示行数
				bean.setMapData(list);
			}
		} catch (Exception e) {
			logger.error("查询商品总销售量(直播订单)失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
	
	/**
	 * 导出总量(根据商品ID、名称)
	 */
	@Override
	public ReportBean getLiveOrderExportByGood(Map<String, Object> params, UserSession user) throws SystemException {
		ReportBean bean = new ReportBean();
		try {
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				if(!ToolsString.isEmpty(params.get("startDate")+"")){
					params.put("startDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("startDate")+""));
				}
				if(!ToolsString.isEmpty(params.get("endDate")+"")){
					params.put("endDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("endDate")+""));
				}
			}
			
			List<Map<String, Object>> data = _vstShoppingLiveOrderDao.queryExportByGood(params);
			
			bean.setMapData(data);
			bean.setTitle("vst_shopping_live_order_good");
			
			// 写操作日志
			StringBuilder sb = new StringBuilder();
			sb.append("导出总量，查询条件：").append(params);
			_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_EXPORT, sb.toString()));
		} catch (Exception e) {
			logger.error("导出商品总销售量(直播订单)失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
	
	/**
	 * 查询表格数据(按月统计汇总)
	 */
	@Override
	public ReportBean getLiveOrderSumCutPage(Map<String, Object> params) throws SystemException {
		ReportBean bean = new ReportBean();
		try {
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				if(!ToolsString.isEmpty(params.get("startDate")+"")){
					params.put("startDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("startDate")+""));
				}
				if(!ToolsString.isEmpty(params.get("endDate")+"")){
					params.put("endDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("endDate")+""));
				}
			}
			
			// 格式：date->{nums,salePrices}
			Map<String, Map<String, Number>> dataMap = new LinkedHashMap<String, Map<String, Number>>();
			
			List<Map<String, Object>> list = _vstShoppingLiveOrderDao.queryReportByDate(params);
			for(Map<String, Object> map : list){
				String date = ToolsDate.formatDate("yyyyMM", map.get("date")+"");
				
				if(dataMap.containsKey(date)){
					Map<String, Number> temp = dataMap.get(date);
					temp.put("nums", temp.get("nums").intValue() + 1);
					temp.put("qtys", temp.get("qtys").intValue() + ToolsNumber.parseInt(map.get("qty")+""));
					temp.put("salePrices", temp.get("salePrices").doubleValue() + ToolsNumber.parseDouble(map.get("salePrice")+""));
				}else{
					Map<String, Number> temp = new HashMap<String, Number>();
					temp.put("nums", 1);
					temp.put("qtys", ToolsNumber.parseInt(map.get("qty")+""));
					temp.put("salePrices", ToolsNumber.parseDouble(map.get("salePrice")+""));
					dataMap.put(date, temp);
				}
			}
			
			// 将dataMap数据转到list中
			List<JSONObject> data = new ArrayList<JSONObject>();
			for(String key : dataMap.keySet()){
				Map<String, Number> value = dataMap.get(key);
				JSONObject json = new JSONObject();
				json.put("date", key);
				json.put("nums", value.get("nums"));
				json.put("qtys", value.get("qtys"));
				json.put("salePrices", value.get("salePrices"));
				data.add(json);
			}
			bean.setData(data);
		} catch (Exception e) {
			logger.error("查询直播订单(按月统计汇总)失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}

	/**
	 * 统计占比(根据商品分类)
	 */
	@Override
	public ReportBean getLiveOrderReportByCategory(Map<String, Object> params) throws SystemException {
		ReportBean bean = new ReportBean();
		try {
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				if(!ToolsString.isEmpty(params.get("startDate")+"")){
					params.put("startDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("startDate")+""));
				}
				if(!ToolsString.isEmpty(params.get("endDate")+"")){
					params.put("endDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("endDate")+""));
				}
			}
			
			List<Map<String, Object>> data = _vstShoppingLiveOrderDao.queryReportByCategory(params);
			bean.setMapData(data);
		} catch (Exception e) {
			logger.error("查询直播订单(商品分类占比)失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}

	/**
	 * 统计总量(根据来源)
	 */
	@Override
	public ReportBean getLiveOrderReportBySource(Map<String, Object> params) throws SystemException {
		ReportBean bean = new ReportBean();
		try {
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				if(!ToolsString.isEmpty(params.get("startDate")+"")){
					params.put("startDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("startDate")+""));
				}
				if(!ToolsString.isEmpty(params.get("endDate")+"")){
					params.put("endDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("endDate")+""));
				}
			}
			
			List<Map<String, Object>> data = _vstShoppingLiveOrderDao.queryReportBySource(params);
			bean.setMapData(data);
		} catch (Exception e) {
			logger.error("查询直播订单(来源总量)失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
	
	/**
	 * 统计总量(根据省份)
	 */
	@Override
	public ReportBean getLiveOrderReportByProvince(CutPage cutPage) throws SystemException {
		ReportBean bean = new ReportBean();
		try {
			Map<String, Object> params = cutPage.get_castQueryParam();
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				if(!ToolsString.isEmpty(params.get("startDate")+"")){
					params.put("startDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("startDate")+""));
				}
				if(!ToolsString.isEmpty(params.get("endDate")+"")){
					params.put("endDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("endDate")+""));
				}
			}
			
			int count = _vstShoppingLiveOrderDao.queryCountByProvince(params);
			if(count != 0){
				int start = (cutPage.get_currPage()-1) * cutPage.get_singleCount();
				
				params.put("offset", start);
				params.put("limit", cutPage.get_singleCount());
				
				List<Map<String, Object>> list = _vstShoppingLiveOrderDao.queryListByProvince(params);
				
				bean.setTotalSize(count); //设置总行数
				bean.setSingleSize(cutPage.get_singleCount());//设置单页显示行数
				bean.setMapData(list);
			}
		} catch (Exception e) {
			logger.error("查询直播订单(省份总量)失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
	
	/**
	 * 导出总量(根据省份)
	 */
	@Override
	public ReportBean getLiveOrderExportByProvince(Map<String, Object> params, UserSession user) throws SystemException {
		ReportBean bean = new ReportBean();
		try {
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				if(!ToolsString.isEmpty(params.get("startDate")+"")){
					params.put("startDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("startDate")+""));
				}
				if(!ToolsString.isEmpty(params.get("endDate")+"")){
					params.put("endDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("endDate")+""));
				}
			}
			
			List<Map<String, Object>> data = _vstShoppingLiveOrderDao.queryExportByProvince(params);
			
			bean.setMapData(data);
			bean.setTitle("vst_shopping_live_order_province");
			
			// 写操作日志
			StringBuilder sb = new StringBuilder();
			sb.append("导出总量(按省份)，查询条件：").append(params);
			_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_EXPORT, sb.toString()));
		} catch (Exception e) {
			logger.error("导出直播订单(省份总量)失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
	
	/**
	 * 统计总量(根据地址)
	 */
	@Override
	public ReportBean getLiveOrderReportByAddress(CutPage cutPage) throws SystemException {
		ReportBean bean = new ReportBean();
		try {
			Map<String, Object> params = cutPage.get_castQueryParam();
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				if(!ToolsString.isEmpty(params.get("startDate")+"")){
					params.put("startDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("startDate")+""));
				}
				if(!ToolsString.isEmpty(params.get("endDate")+"")){
					params.put("endDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("endDate")+""));
				}
			}
			
			int count = _vstShoppingLiveOrderDao.queryCountByAddress(params);
			if(count != 0){
				int start = (cutPage.get_currPage()-1) * cutPage.get_singleCount();
				
				params.put("offset", start);
				params.put("limit", cutPage.get_singleCount());
				
				List<Map<String, Object>> list = _vstShoppingLiveOrderDao.queryListByAddress(params);
				
				bean.setTotalSize(count); //设置总行数
				bean.setSingleSize(cutPage.get_singleCount());//设置单页显示行数
				bean.setMapData(list);
			}
		} catch (Exception e) {
			logger.error("查询直播订单(地址总量)失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
	
	/**
	 * 导出总量(根据地址)
	 */
	@Override
	public ReportBean getLiveOrderExportByAddress(Map<String, Object> params, UserSession user) throws SystemException {
		ReportBean bean = new ReportBean();
		try {
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				if(!ToolsString.isEmpty(params.get("startDate")+"")){
					params.put("startDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("startDate")+""));
				}
				if(!ToolsString.isEmpty(params.get("endDate")+"")){
					params.put("endDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("endDate")+""));
				}
			}
			
			List<Map<String, Object>> data = _vstShoppingLiveOrderDao.queryExportByAddress(params);
			
			bean.setMapData(data);
			bean.setTitle("vst_shopping_live_order_address");
			
			// 写操作日志
			StringBuilder sb = new StringBuilder();
			sb.append("导出总量(按地址)，查询条件：").append(params);
			_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_EXPORT, sb.toString()));
		} catch (Exception e) {
			logger.error("导出直播订单(地址总量)失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
	
	/**
	 * 抓取环球购物数据
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int graspGlobalbuyData(Map<String, Object> params, UserSession user) {
		int result = 0;
		try{
			String startDay = ToolsString.checkEmpty(params.get("startDay"));
			String endDay = ToolsString.checkEmpty(params.get("endDay"));
			String cookie = ToolsString.checkEmpty(params.get("cookie"));
			
			int page = 1;
			while(true){
				String url = GLOBALBUY_URL.replace("{startDay}", startDay).replace("{endDay}", endDay).replace("{page}", String.valueOf(page));
				Request request = new Request(url);
				request.setCookie(cookie);
				if(ToolsHttp.getString(request) != null){
					Html html = Html.create(ToolsHttp.getString(request));
					Document document = html.getDocument();
					Elements responsives = document.getElementsByClass("order-table-responsive");
					if(responsives != null && responsives.size() > 0){
						Element responsive = responsives.get(0);
						Elements tables = responsive.getElementsByTag("table");
						if(tables != null && tables.size() > 0){
							Element table = tables.get(0);
							Elements tbodys = table.getElementsByTag("tbody");
							if(tbodys != null && tbodys.size() > 0){
								Element tbody = tbodys.get(0);
								Elements trs = tbody.getElementsByTag("tr");
								
								List<VstShoppingLiveOrder> orderList = new ArrayList<VstShoppingLiveOrder>();
								Long now = System.currentTimeMillis();
								//遍历每行数据
								for(Element tr : trs){
									Elements tds = tr.getElementsByTag("td");
									
									VstShoppingLiveOrder bean = new VstShoppingLiveOrder();
									//日期
									bean.setVst_shopping_live_order_date(ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, tds.get(5).text()));
									//商品
									bean.setVst_shopping_live_order_number(tds.get(0).text());
									bean.setVst_shopping_live_order_goods_number(tds.get(3).text());
									bean.setVst_shopping_live_order_goods_category(tds.get(4).text());
									bean.setVst_shopping_live_order_goods_name(tds.get(1).text());
									//价格
									bean.setVst_shopping_live_order_goods_price(ToolsNumber.parseDouble(tds.get(8).text()));
									bean.setVst_shopping_live_order_qty(ToolsNumber.parseInt(tds.get(2).text()));
									bean.setVst_shopping_live_order_sale_price(ToolsNumber.parseDouble(tds.get(9).text()));
									//地址
									bean.setVst_shopping_live_order_address(tds.get(7).text());
									bean.setVst_shopping_live_order_province(ExportUtil.getProvinceByAddress(tds.get(7).text()));
									//联系人
									bean.setVst_shopping_live_order_recipient("");
									bean.setVst_shopping_live_order_phone("");
									//其他
									bean.setVst_shopping_live_order_source(tds.get(22).text());//来源
									bean.setVst_shopping_live_order_balance_status(tds.get(18).text());//对账状态
									bean.setVst_shopping_live_order_logistics_status(tds.get(21).text());//物流状态
									if(!ToolsString.isEmpty(tds.get(16).text())){//创建时间
										bean.setVst_shopping_live_order_create_time(ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, tds.get(16).text()));
									}else{
										bean.setVst_shopping_live_order_create_time(0L);
									}
									if(!ToolsString.isEmpty(tds.get(19).text())){//对账时间
										bean.setVst_shopping_live_order_balance_time(ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd, tds.get(19).text()));
									}else{
										bean.setVst_shopping_live_order_balance_time(0L);
									}
									bean.setVst_shopping_live_order_channel("globalbuy");
									bean.setVst_shopping_live_order_addtime(now);
									bean.setVst_shopping_live_order_uptime(now);
									bean.setVst_shopping_live_order_operator(user.getLoginInfo().getLoginName());
									
									orderList.add(bean);
								}
								
								if(orderList.size() > 0){
									int count = _vstShoppingLiveOrderDao.batchInsert(orderList);
									System.out.println("插入环球直播数据：" + count);
									result += count;
								}
								
								page++;
							}else{
								return result;
							}
						}else{
							return result;
						}
					}else{
						return result;
					}
				}else{
					return result;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 抓取UGO购物(优品购)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int graspUgoData(Map<String, Object> params, UserSession user){
		int result = 0;
		try{
			String startDay = ToolsString.checkEmpty(params.get("startDay"));
			String endDay = ToolsString.checkEmpty(params.get("endDay"));
			String cookie = ToolsString.checkEmpty(params.get("cookie"));
			
			Map<String, String> header = new HashMap<String, String>();
			header.put("Accept", "application/json, text/javascript, */*; q=0.01");
			header.put("Accept-Encoding", "gzip, deflate");
			header.put("Accept-Language", "zh-CN,zh;q=0.8");
			header.put("Connection", "keep-alive");
			header.put("Content-Length", "193");
			header.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			header.put("Cookie", cookie);
			header.put("Host", "scm.huimai365.com");
			header.put("Origin", "http://scm.huimai365.com");
			header.put("Referer", "http://scm.huimai365.com/biz/partner/chargemng/queryOrdList.do");
			header.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 SE 2.X MetaSr 1.0");
			header.put("X-Requested-With", "XMLHttpRequest");
			
			Long now = System.currentTimeMillis();
			
			String postBody = "ordId=&startDtm={startDay}&endDtm={endDay}&cstId=&prdId=&ordTpCd=&ordSts=&rjtRtnYn=&startDtmCnfm=&endDtmCnfm=&ChnlDtlId=&jgrid_listSize=100&jgrid_currentNumber=1&jgrid_formulaInfo=[]&currentMenuId=15ddead98e15".replace("{startDay}", startDay).replace("{endDay}", endDay);
			
			String jsonUrl = ToolsHttp.httpPost(UGO_URL, header, postBody);
			if(!ToolsString.isEmpty(jsonUrl)){
				JSONObject urlJson = JSONObject.parseObject(jsonUrl);
				if(urlJson != null){
					JSONObject dataJson = urlJson.getJSONObject("data");
					if(dataJson != null && dataJson.containsKey("jgrid_list")){
						int totalCount = dataJson.getIntValue("jgrid_totalCount");
						int totalPage = (totalCount%100 == 0) ? totalCount/100 : totalCount/100+1;
						for(int page = 1; page<=totalPage; page++){
							String postBody2 = "ordId=&startDtm={startDay}&endDtm={endDay}&cstId=&prdId=&ordTpCd=&ordSts=&rjtRtnYn=&startDtmCnfm=&endDtmCnfm=&ChnlDtlId=&jgrid_listSize=100&jgrid_currentNumber={page}&jgrid_formulaInfo=[]&currentMenuId=15ddead98e15"
									.replace("{startDay}", startDay)
									.replace("{endDay}", endDay)
									.replace("{page}", page+"");
							
							String jsonUrl2 = ToolsHttp.httpPost(UGO_URL, header, postBody2);
							JSONObject urlJson2 = JSONObject.parseObject(jsonUrl2);
							if(urlJson2 != null){
								JSONObject dataJson2 = urlJson2.getJSONObject("data");
								if(dataJson2 != null){
									JSONArray rows = dataJson2.getJSONArray("jgrid_list");
									if(rows != null && rows.size() > 0){
										List<VstShoppingLiveOrder> orderList = new ArrayList<VstShoppingLiveOrder>();
										for(int i=0; i<rows.size(); i++){
											JSONObject order = rows.getJSONObject(i);
											
											VstShoppingLiveOrder bean = new VstShoppingLiveOrder();
											//日期
											bean.setVst_shopping_live_order_date(ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm_ss, order.getString("INST_DTM")));
											//商品
											bean.setVst_shopping_live_order_number(order.getString("ORD_ID"));
											bean.setVst_shopping_live_order_goods_number(order.getString("PRD_ID"));
											bean.setVst_shopping_live_order_goods_category(order.getString("PRD_CODE_NM"));
											bean.setVst_shopping_live_order_goods_name(order.getString("PRD_NM"));
											//价格
											bean.setVst_shopping_live_order_goods_price(order.getDouble("SL_PC"));
											bean.setVst_shopping_live_order_qty(order.getInteger("ORD_QTY"));
											bean.setVst_shopping_live_order_sale_price(order.getDouble("RL_SLS_AMT"));
											//地址
											bean.setVst_shopping_live_order_address(order.getString("ADDR"));
											bean.setVst_shopping_live_order_province(ExportUtil.getProvinceByAddress(order.getString("ADDR")));
											//联系人
											bean.setVst_shopping_live_order_recipient(order.getString("ADDR_NM"));
											bean.setVst_shopping_live_order_phone(order.getString("ADDR_PHONE"));
											//其他
											bean.setVst_shopping_live_order_source(order.getString("CHANNEL_NAME"));//来源
											bean.setVst_shopping_live_order_balance_status("");//对账状态
											bean.setVst_shopping_live_order_logistics_status(order.getString("ORD_STS_CD"));//物流状态
											bean.setVst_shopping_live_order_create_time(0L);//创建时间
											if(!ToolsString.isEmpty(order.getString("ORD_ACP_DTM"))){//对账时间
												bean.setVst_shopping_live_order_balance_time(ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd, order.getString("ORD_ACP_DTM")));
											}else{
												bean.setVst_shopping_live_order_balance_time(0L);
											}
											bean.setVst_shopping_live_order_channel("ugo");
											bean.setVst_shopping_live_order_addtime(now);
											bean.setVst_shopping_live_order_uptime(now);
											bean.setVst_shopping_live_order_operator(user.getLoginInfo().getLoginName());
											
											orderList.add(bean);
										}
										
										if(orderList.size() > 0){
											int count = _vstShoppingLiveOrderDao.batchInsert(orderList);
											System.out.println("插入UGO优购物数据：" + count);
											result += count;
										}
									}
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 抓取海莱购物(新)数据
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int graspHailaiData(Map<String, Object> params, UserSession user){
		int result = 0;
		try{
			String startDay = ToolsString.checkEmpty(params.get("startDay"));
			String endDay = ToolsString.checkEmpty(params.get("endDay"));
			String cookie = ToolsString.checkEmpty(params.get("cookie"));
			
			Map<String, String> header = new HashMap<String, String>();
			header.put("Host", "www.yunshigo.com:9003");
			header.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0");
			header.put("Accept", "application/json, text/javascript, */*; q=0.01");
			header.put("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
			header.put("Accept-Encoding", "gzip, deflate");
			header.put("Referer", "http://www.yunshigo.com:9003/b2brep");
			header.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			header.put("Content-Length", "XMLHttpRequest");
			header.put("X-Requested-With", "114");
			header.put("Cookie", cookie);
			header.put("Connection", "keep-alive");
			
			String postBody = "p_order_date_s={startDay}&p_order_date_e={endDay}&pssid=hpid".replace("{startDay}", startDay).replace("{endDay}", endDay);
		
			String jsonUrl = ToolsHttp.httpPost(HAILAI_URL, header, postBody);
			if(!ToolsString.isEmpty(jsonUrl)){
				JSONObject urlJson = JSONObject.parseObject(jsonUrl);
				JSONArray orders = urlJson.getJSONArray("orders");
				if(orders != null){
					List<VstShoppingLiveOrder> orderList = new ArrayList<VstShoppingLiveOrder>();
					Long now = System.currentTimeMillis();
					//遍历查看订单页面数据
					for(int i=0; i<orders.size(); i++){
						JSONArray order = orders.getJSONArray(i);
						
						VstShoppingLiveOrder bean = new VstShoppingLiveOrder();
						//日期
						bean.setVst_shopping_live_order_date(ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm_ss, order.getString(1)));
						//格式：商品ID商品名称*数量，多个用/分隔
						String shopLine = order.getString(11);
						//截取 商品ID、商品名称
						int index = 0;
						for(int j=0; j<shopLine.length(); j++){
							if(shopLine.charAt(j) < '0' || shopLine.charAt(j) > '9'){
								index = j;
								break;
							}
						}
						//商品
						bean.setVst_shopping_live_order_number(order.getString(2));
						bean.setVst_shopping_live_order_goods_number(shopLine.substring(0, index));
						bean.setVst_shopping_live_order_goods_category(order.getString(12));
						bean.setVst_shopping_live_order_goods_name(shopLine);
						//价格
						bean.setVst_shopping_live_order_goods_price(order.getDouble(4));
						bean.setVst_shopping_live_order_qty(1);
						bean.setVst_shopping_live_order_sale_price(order.getDouble(4));
						//地址
						bean.setVst_shopping_live_order_address(order.getString(7));
						bean.setVst_shopping_live_order_province(ExportUtil.getProvinceByAddress(order.getString(7)));
						//联系人
						bean.setVst_shopping_live_order_recipient(order.getString(5));
						bean.setVst_shopping_live_order_phone(order.getString(6));
						//其他
						bean.setVst_shopping_live_order_source("");//来源
						bean.setVst_shopping_live_order_balance_status(order.getString(13));//对账状态
						bean.setVst_shopping_live_order_logistics_status(order.getString(10));//物流状态
						bean.setVst_shopping_live_order_create_time(0L);
						bean.setVst_shopping_live_order_balance_time(0L);
						bean.setVst_shopping_live_order_channel("hailai");
						bean.setVst_shopping_live_order_addtime(now);
						bean.setVst_shopping_live_order_uptime(now);
						bean.setVst_shopping_live_order_operator(user.getLoginInfo().getLoginName());
						
						orderList.add(bean);
					}
					
					if(orderList.size() > 0){
						int count = _vstShoppingLiveOrderDao.batchInsert(orderList);
						System.out.println("插入海莱直播数据：" + count);
						result += count;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 抓取时尚购物数据
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int graspFashionData(Map<String, Object> params, UserSession user){
		int result = 0;
		try{
			String startDay = ToolsString.checkEmpty(params.get("startDay"));
			String endDay = ToolsString.checkEmpty(params.get("endDay"));
			String cookie = ToolsString.checkEmpty(params.get("cookie"));
			
			Map<String, String> header = new HashMap<String, String>();
			header.put("Accept", "application/json, text/javascript, */*; q=0.01");
			header.put("Accept-Encoding", "gzip, deflate");
			header.put("Accept-Language", "zh-CN,zh;q=0.8");
			header.put("Connection", "keep-alive");
			header.put("Content-Length", "193");
			header.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			header.put("Cookie", cookie);
			header.put("Host", "scm.huimai365.com");
			header.put("Origin", "http://scm.huimai365.com");
			header.put("Referer", "http://scm.huimai365.com/biz/partner/chargemng/queryOrdList.do");
			header.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 SE 2.X MetaSr 1.0");
			header.put("X-Requested-With", "XMLHttpRequest");
			
			Long now = System.currentTimeMillis();
			
			String postBody = "ordId=&startDtm={startDay}&endDtm={endDay}&cstId=&prdId=&ordTpCd=&ordSts=&rjtRtnYn=&startDtmCnfm=&endDtmCnfm=&ChnlDtlId=&jgrid_listSize=100&jgrid_currentNumber=1&jgrid_formulaInfo=[]&currentMenuId=15ddead98e15".replace("{startDay}", startDay).replace("{endDay}", endDay);
			
			String jsonUrl = ToolsHttp.httpPost(FASHION_URL, header, postBody);
			if(!ToolsString.isEmpty(jsonUrl)){
				JSONObject urlJson = JSONObject.parseObject(jsonUrl);
				if(urlJson != null){
					JSONObject dataJson = urlJson.getJSONObject("data");
					if(dataJson != null && dataJson.containsKey("jgrid_list")){
						int totalCount = dataJson.getIntValue("jgrid_totalCount");
						int totalPage = (totalCount%100 == 0) ? totalCount/100 : totalCount/100+1;
						for(int page = 1; page<=totalPage; page++){
							String postBody2 = "ordId=&startDtm={startDay}&endDtm={endDay}&cstId=&prdId=&ordTpCd=&ordSts=&rjtRtnYn=&startDtmCnfm=&endDtmCnfm=&ChnlDtlId=&jgrid_listSize=100&jgrid_currentNumber={page}&jgrid_formulaInfo=[]&currentMenuId=15ddead98e15"
									.replace("{startDay}", startDay)
									.replace("{endDay}", endDay)
									.replace("{page}", page+"");
							
							String jsonUrl2 = ToolsHttp.httpPost(FASHION_URL, header, postBody2);
							JSONObject urlJson2 = JSONObject.parseObject(jsonUrl2);
							if(urlJson2 != null){
								JSONObject dataJson2 = urlJson2.getJSONObject("data");
								if(dataJson2 != null){
									JSONArray rows = dataJson2.getJSONArray("jgrid_list");
									if(rows != null && rows.size() > 0){
										List<VstShoppingLiveOrder> orderList = new ArrayList<VstShoppingLiveOrder>();
										for(int i=0; i<rows.size(); i++){
											JSONObject order = rows.getJSONObject(i);
											
											VstShoppingLiveOrder bean = new VstShoppingLiveOrder();
											//日期
											bean.setVst_shopping_live_order_date(ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm_ss, order.getString("INST_DTM")));
											//商品
											bean.setVst_shopping_live_order_number(order.getString("ORD_ID"));
											bean.setVst_shopping_live_order_goods_number(order.getString("PRD_ID"));
											bean.setVst_shopping_live_order_goods_category(order.getString("PRD_CODE_NM"));
											bean.setVst_shopping_live_order_goods_name(order.getString("PRD_NM"));
											//价格
											bean.setVst_shopping_live_order_goods_price(order.getDouble("SL_PC"));
											bean.setVst_shopping_live_order_qty(order.getInteger("ORD_QTY"));
											bean.setVst_shopping_live_order_sale_price(order.getDouble("RL_SLS_AMT"));
											//地址
											bean.setVst_shopping_live_order_address(order.getString("ADDR"));
											bean.setVst_shopping_live_order_province(ExportUtil.getProvinceByAddress(order.getString("ADDR")));
											//联系人
											bean.setVst_shopping_live_order_recipient(order.getString("ADDR_NM"));
											bean.setVst_shopping_live_order_phone(order.getString("ADDR_PHONE"));
											//其他
											bean.setVst_shopping_live_order_source(order.getString("CHANNEL_NAME"));//来源
											bean.setVst_shopping_live_order_balance_status("");//对账状态
											bean.setVst_shopping_live_order_logistics_status(order.getString("ORD_STS_CD"));//物流状态
											bean.setVst_shopping_live_order_create_time(0L);//创建时间
											if(!ToolsString.isEmpty(order.getString("ORD_ACP_DTM"))){//对账时间
												bean.setVst_shopping_live_order_balance_time(ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd, order.getString("ORD_ACP_DTM")));
											}else{
												bean.setVst_shopping_live_order_balance_time(0L);
											}
											bean.setVst_shopping_live_order_channel("fashion");
											bean.setVst_shopping_live_order_addtime(now);
											bean.setVst_shopping_live_order_uptime(now);
											bean.setVst_shopping_live_order_operator(user.getLoginInfo().getLoginName());
											
											orderList.add(bean);
										}
										
										if(orderList.size() > 0){
											int count = _vstShoppingLiveOrderDao.batchInsert(orderList);
											System.out.println("插入时尚购物数据：" + count);
											result += count;
										}
									}
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 抓取多乐播购物数据
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int graspDuoleboData(Map<String, Object> params, UserSession user) {
		int result = 0;
		try{
			String startDay = ToolsString.checkEmpty(params.get("startDay"));
			String endDay = ToolsString.checkEmpty(params.get("endDay"));
			String cookie = ToolsString.checkEmpty(params.get("cookie"));
			long now = System.currentTimeMillis();
			
			String url = DUOLEBO_URL.replace("{now}", now+"")
						.replace("{startDay}", startDay+"%2000%3A00%3A00")
						.replace("{endDay}", endDay+"%2023%3A59%3A59")
						.replace("{page}", "1")
						.replace("{start}", "0");
			
			Request request = new Request(url);
			request.setCookie(cookie);
			JSONObject json = ToolsHttp.getJson(request);
			
			if(json != null && json.containsKey("total")){
				int total = json.getIntValue("total");
				// 计算最大页数
				int pageMax = 1;
				if(total > 100){
					if(total % 100 == 0){
						pageMax = total / 100;
					}else{
						pageMax = total / 100 + 1;
					}
				}
				
				for(int p=1; p<=pageMax; p++){
					String url2 = DUOLEBO_URL.replace("{now}", now+"")
										.replace("{startDay}", startDay+"%2000%3A00%3A00")
										.replace("{endDay}", endDay+"%2023%3A59%3A59")
										.replace("{page}", p+"")
										.replace("{start}", (p-1)*100+"");
					
					Request request2 = new Request(url2);
					request2.setCookie(cookie);
					JSONObject json2 = ToolsHttp.getJson(request2);
					
					if(json2 != null){
						JSONArray datas = json2.getJSONArray("data");
						if(datas != null && datas.size() > 0){
							List<VstShoppingLiveOrder> orderList = new ArrayList<VstShoppingLiveOrder>();
							
							for(int i=0; i<datas.size(); i++){
								JSONObject data = datas.getJSONObject(i);
								
								VstShoppingLiveOrder bean = new VstShoppingLiveOrder();
								//日期
								bean.setVst_shopping_live_order_date(ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm_ss, data.getString("billingDate")));
								//商品
								bean.setVst_shopping_live_order_number(data.getString("billingNumber"));
								bean.setVst_shopping_live_order_goods_number(data.getString("goodId"));
								bean.setVst_shopping_live_order_goods_category(data.getString("newCategoryName"));
								bean.setVst_shopping_live_order_goods_name(data.getString("goodInfo"));
								//价格
								bean.setVst_shopping_live_order_goods_price(data.getDouble("goodPrice"));
								bean.setVst_shopping_live_order_qty(data.getInteger("goodCount"));
								bean.setVst_shopping_live_order_sale_price(data.getDouble("moneyPaid"));
								//地址
								bean.setVst_shopping_live_order_address(data.getString("deliveryAddr"));
								bean.setVst_shopping_live_order_province(data.getString("province"));
								//联系人
								bean.setVst_shopping_live_order_recipient(data.getString("customerName"));
								bean.setVst_shopping_live_order_phone(data.getString("phoneNumber"));
								//其他
								bean.setVst_shopping_live_order_source("");//来源
								bean.setVst_shopping_live_order_balance_status("");//对账状态
								bean.setVst_shopping_live_order_logistics_status(data.getString("deliverStatus"));//物流状态
								if(!ToolsString.isEmpty(data.getString("createDate"))){//创建时间
									bean.setVst_shopping_live_order_create_time(ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm_ss, data.getString("createDate")));
								}else{
									bean.setVst_shopping_live_order_create_time(0L);
								}
								if(!ToolsString.isEmpty(data.getString("settlementDate"))){//对账时间
									bean.setVst_shopping_live_order_balance_time(ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm_ss, data.getString("settlementDate")));
								}else{
									bean.setVst_shopping_live_order_balance_time(0L);
								}
								bean.setVst_shopping_live_order_channel("duolebo");
								bean.setVst_shopping_live_order_addtime(now);
								bean.setVst_shopping_live_order_uptime(now);
								bean.setVst_shopping_live_order_operator(user.getLoginInfo().getLoginName());
								
								orderList.add(bean);
							}
							
							if(orderList.size() > 0){
								int count = _vstShoppingLiveOrderDao.batchInsert(orderList);
								System.out.println("插入多乐播购物数据：" + count);
								result += count;
							}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 抓取快乐购物数据
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int graspHappyData(Map<String, Object> params, UserSession user) {
		int result = 0;
		try{
			String startDay = ToolsString.checkEmpty(params.get("startDay"));
			String endDay = ToolsDate.formatDate(ToolsDate.yyyy_MM_dd, ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd, params.get("endDay")+"") + 24*60*60*1000);
			String cookie = ToolsString.checkEmpty(params.get("cookie"));
			long now = System.currentTimeMillis();
			
			String url = HAPPY_URL.replace("{offset}", "1")
						.replace("{startDay}", startDay)
						.replace("{endDay}", endDay);
			// 忽略SSL
			SslUtils.ignoreSsl();
			Request request = new Request(url);
			request.setCookie(cookie);
			JSONObject json = ToolsHttp.getJson(request);
			
			if(json != null && json.containsKey("total")){
				int total = json.getIntValue("total");
				// 计算最大页数
				int pageMax = 1;
				if(total > 100){
					if(total % 100 == 0){
						pageMax = total / 100;
					}else{
						pageMax = total / 100 + 1;
					}
				}
				
				for(int p=0; p<pageMax; p++){
					String url2 = HAPPY_URL.replace("{offset}", (p*100+1)+"")
								.replace("{startDay}", startDay)
								.replace("{endDay}", endDay);
					
					Request request2 = new Request(url2);
					request2.setCookie(cookie);
					JSONObject json2 = ToolsHttp.getJson(request2);
					
					if(json2 != null){
						JSONArray rows = json2.getJSONArray("rows");
						if(rows != null && rows.size() > 0){
							List<VstShoppingLiveOrder> orderList = new ArrayList<VstShoppingLiveOrder>();
							
							for(int i=0; i<rows.size(); i++){
								JSONObject row = rows.getJSONObject(i);
								
								VstShoppingLiveOrder bean = new VstShoppingLiveOrder();
								//日期
								bean.setVst_shopping_live_order_date(ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm_ss, row.getString("order_date")));
								//商品
								bean.setVst_shopping_live_order_number(row.getString("order_no"));
								bean.setVst_shopping_live_order_goods_number(row.getString("item_code"));
								bean.setVst_shopping_live_order_goods_category("");
								bean.setVst_shopping_live_order_goods_name(row.getString("item_name"));
								//价格
								bean.setVst_shopping_live_order_goods_price(row.getDouble("order_amount"));
								bean.setVst_shopping_live_order_qty(row.getInteger("order_qty"));
								bean.setVst_shopping_live_order_sale_price(row.getDouble("sale_price"));
								//地址
								bean.setVst_shopping_live_order_address(row.getString("provincename")+row.getString("cityname")+row.getString("townname"));
								bean.setVst_shopping_live_order_province(row.getString("provincename"));
								//联系人
								bean.setVst_shopping_live_order_recipient("");
								bean.setVst_shopping_live_order_phone("");
								//其他
								bean.setVst_shopping_live_order_source("");//来源
								bean.setVst_shopping_live_order_balance_status("");//对账状态
								bean.setVst_shopping_live_order_logistics_status(row.getString("order_state"));//物流状态
								bean.setVst_shopping_live_order_create_time(0L);//创建时间
								bean.setVst_shopping_live_order_balance_time(0L);//对账时间
								bean.setVst_shopping_live_order_channel("happy");
								bean.setVst_shopping_live_order_addtime(now);
								bean.setVst_shopping_live_order_uptime(now);
								bean.setVst_shopping_live_order_operator(user.getLoginInfo().getLoginName());
								
								orderList.add(bean);
							}
							
							if(orderList.size() > 0){
								int count = _vstShoppingLiveOrderDao.batchInsert(orderList);
								System.out.println("插入快乐购物数据：" + count);
								result += count;
							}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 删除直播订单
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int deleteLiveOrder(Map<String, Object> params, UserSession user) throws SystemException {
		int result = 0;
		try {
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				if(!ToolsString.isEmpty(params.get("startDate")+"")){
					params.put("startDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("startDate")+""));
				}
				if(!ToolsString.isEmpty(params.get("endDate")+"")){
					params.put("endDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("endDate")+""));
				}
			}
			
			List<Map<String, Object>> data = _vstShoppingLiveOrderDao.queryForList(params);
			if(data != null && data.size() > 0){
				//需要删除的ID
				List<Integer> ids = new ArrayList<Integer>();
				for(Map<String, Object> map : data){
					ids.add(ToolsNumber.parseInt(map.get("vst_pk_id")+""));
				}
				
				Map<String, Object> param = new HashMap<String, Object>(1);
				param.put("list_ids", ids);
				result = _vstShoppingLiveOrderDao.delete(param);
			}
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("删除直播订单, 条件：").append(params);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_DEL, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("删除字典失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}

	/**
	 * 查询报表数据(按渠道统计)
	 */
	@Override
	public Map<String, Object> getLiveOrderReportByChannel(Map<String, Object> params) throws SystemException {
		try {
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				if(!ToolsString.isEmpty(params.get("startDate")+"")){
					params.put("startDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("startDate")+""));
				}
				if(!ToolsString.isEmpty(params.get("endDate")+"")){
					params.put("endDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, params.get("endDate")+""));
				}
			}
			
			List<Map<String, Object>> list = _vstShoppingLiveOrderDao.queryReportByDate(params);
			if(list != null && list.size() > 0){
				Map<String, List<JSONObject>> dataMap = new HashMap<String, List<JSONObject>>();
				
				for(Map<String, Object> map : list){
					String channel = map.get("channel")+"";
					if(dataMap.containsKey(channel)){
						String date = ToolsDate.formatDate(ToolsDate.HH, map.get("date")+"");
						int hour = ToolsNumber.parseInt(date);
						double salePrice = ToolsNumber.parseDouble(map.get("salePrice")+"");
						// 封装小时数据
						List<JSONObject> hours = dataMap.get(channel);
						JSONObject temp = hours.get(hour);
						temp.put("num", temp.getIntValue("num") + 1);
						temp.put("price", salePrice + temp.getDoubleValue("price"));
					}else{
						String date = ToolsDate.formatDate(ToolsDate.HH, map.get("date")+"");
						int hour = ToolsNumber.parseInt(date);
						double salePrice = ToolsNumber.parseDouble(map.get("salePrice")+"");
						// 封装小时数据
						List<JSONObject> hours = new ArrayList<JSONObject>(24);
						for(int i=0; i<24; i++){
							JSONObject temp = new JSONObject();
							temp.put("date", i);
							if(hour == i){
								temp.put("num", 1);
								temp.put("price", salePrice);
							}else{
								temp.put("num", 0);
								temp.put("price", 0);
							}
							hours.add(temp);
						}
						dataMap.put(channel, hours);
					}
				}
				
				if(!dataMap.isEmpty()){
					// 获取商品渠道
					Map<String, Object> temp = new HashMap<String, Object>(3);
					temp.put("vst_table_name", "vst_shopping_live_order");
					temp.put("vst_column_name", "channel");
					temp.put("vst_state", VstConstants.STATE_AVALIABLE);
					Map<String, String> channelMap = _baseService.getDictionaryForMap(temp);
					
					Map<String, Object> data = new LinkedHashMap<String, Object>();
					for(String channel : channelMap.keySet()){
						if(dataMap.containsKey(channel)){
							data.put(channelMap.get(channel), dataMap.get(channel));
						}
					}
					return data;
				}
			}
		} catch (Exception e) {
			logger.error("查询直播订单(按渠道统计)失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return null;
	}
	
}
