package com.vst.defend.service.shopping.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vst.common.pojo.VstShoppingPlayOrder;
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
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.communal.util.VstTools;
import com.vst.defend.dao3.shopping.VstShoppingPlayOrderDao;
import com.vst.defend.service.shopping.VstShoppingPlayOrderService;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

/**
 * 
 * @author lhp
 * @date 2017-11-28 ??????05:11:15
 * @version
 */
@Service
public class VstShoppingPlayOrderServiceImpl implements VstShoppingPlayOrderService {
	/**
	 * ?????????
	 */
	private static final Log logger = LogFactory.getLog(VstShoppingPlayOrderServiceImpl.class);
	
	/**
	 * ????????????Dao??????
	 */
	@Resource
	private VstShoppingPlayOrderDao _vstShoppingPlayOrderDao;
	
	/**
	 * ??????????????????
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * ??????????????????
	 */
	private static String FASION_LOGIN = "http://report.fstvgo.com:8080/consoleUser/consoleUserAct_consoleUserLogin.do?conUserReq.userName=CIBN-VST&conUserReq.userPassword=20151026";
	
	/**
	 * ??????????????????
	 */
	private static String FASION_URL = "http://report.fstvgo.com:8080/saleDataInfo/saleInfoAct_querySaleInfo.action?conUserReq.roleGroupId=0&freDate={startDay}&endDate={endDay}&page={page}&rows=100";
	
	/**
	 * ??????????????????
	 */
	private static String GLOBALBUY_URL = "http://distributor.cps.ghs.net/order?s={startDay}&e={endDay}&payment_confirmed=&sid=&page={page}";
	
	/**
	 * ??????????????????(???)
	 */
	private static String HAILAI_URL2 = "http://www.yunshigo.com:9003/getb2brep";
	
	/**
	 * ??????????????????????????????
	 */
	private static String ZIYING_URL = "http://dianshang.ott.cibntv.net/shop/admin/order/list.jhtml?totalNum=9&exportSection=&type=&status=&invoiceStatus=&pmid=&cpId=&memberUsername=&isPendingReceive=&isPendingRefunds=&isAllocatedStock=&hasExpired=&supplierType={supplierType}&timeType=create_date&start={startDay}&end={endDay}&channelId=0&searchValue=&pageSize=100&searchProperty=&orderProperty=&orderDirection=";
	
	/**
	 * ??????????????????????????????
	 */
	private static String ZIYING_MSG_URL = "http://dianshang.ott.cibntv.net/shop/admin/order/view.jhtml?id={id}";
	
	/**
	 * ??????????????????
	 */
	private static String TOLL_LOGIN = "http://61.234.191.54:8000/{phone}/Login.aspx";
	
	/**
	 * ??????????????????
	 */
	private static String TOLL_URL = "http://61.234.191.54:8000/{phone}/Commodity.aspx";
	
	/**
	 * ?????????????????????
	 */
	private static String ENJOY_URL = "http://xscx.hao24.cn:7777/LevelCities/ottOrders/doSearchOttOrders.action";
	
	/**
	 * ??????????????????
	 */
	private static String YOUTAI_URL = "https://www.youzan.com/v2/trade/order/list.json?keyword[start_time]={startDay}+00:00:00&keyword[end_time]={endDay}+23:59:59&p=1&type=all&state=all&orderby=book_time&order_es_tag=&tuanId=&showBanner=false&order=desc&page_size=100&disable_express_type=&order_label=order_no&feedback=all&buy_way=all&express_type=all";
	
	/**
	 * ????????????????????????
	 */
	private static String CNR_URL = "http://scm.cnrmall.com/gx/liveList.jsp?cityName=&beginDate={startDay}&endDate={endDay}&msaleGb=26&pageNo={pageNo}";
	
	/**
	 * ????????????????????????
	 */
	private static String JIAYOU_URL = "http://peisong.jiayougo.cn/action/com.harbortek.jiayou.scm.action.SupplierMgmtAction?method=getPartnerPrmList";
	
	/**
	 * ??????????????????
	 */
	@Override
	public ReportBean getPlayOrderCutPage(CutPage cutPage) throws SystemException {
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
			
			int count = _vstShoppingPlayOrderDao.queryCount(params);
			
			if(count != 0){
				int start = (cutPage.get_currPage()-1) * cutPage.get_singleCount();
				
				params.put("offset", start);
				params.put("limit", cutPage.get_singleCount());
				
				List<Map<String, Object>> list = _vstShoppingPlayOrderDao.queryForList(params);
				
				for(Map<String, Object> map : list){
					// ??????????????????
					map.put("vst_shopping_play_order_date", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm, map.get("vst_shopping_play_order_date")+""));
					map.put("vst_shopping_play_order_addtime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm, map.get("vst_shopping_play_order_addtime")+""));
					map.put("vst_shopping_play_order_uptime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm, map.get("vst_shopping_play_order_uptime")+""));
				}
				
				bean.setTotalSize(count); //???????????????
				bean.setSingleSize(cutPage.get_singleCount());//????????????????????????
				bean.setMapData(list);
			}
		}catch(Exception e){
			logger.error("????????????????????????????????????: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
	
	/**
	 * ????????????
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
			
			List<Map<String, Object>> data = _vstShoppingPlayOrderDao.queryExport(params);
			if(data != null && data.size() > 0){
				
				Map<String, Object> temp = new HashMap<String, Object>(3);
				// ??????????????????
				temp.put("vst_table_name", "vst_shopping_play_order");
				temp.put("vst_column_name", "channel");
				temp.put("vst_state", VstConstants.STATE_AVALIABLE);
				Map<String, String> channelMap = _baseService.getDictionaryForMap(temp);
				// ?????????????????????
				temp.put("vst_table_name", "vst_shopping_play_order");
				temp.put("vst_column_name", "supplierType");
				Map<String, String> supplierTypeMap = _baseService.getDictionaryForMap(temp);
				
				// ??????????????????
				String export_column = ToolsString.checkEmpty(params.get("export_column"));
				List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
				
				for(Map<String, Object> map : data){
					//??????????????????
					if(!ToolsString.isEmpty(map.get("??????")+"")){
						map.put("??????", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm, map.get("??????")+""));
					}
					if(channelMap.containsKey(map.get("??????")+"")){
						map.put("??????", channelMap.get(map.get("??????")+""));
					}
					if(supplierTypeMap.containsKey(map.get("???????????????")+"")){
						map.put("???????????????", supplierTypeMap.get(map.get("???????????????")+""));
					}
					// ?????????????????????
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
			bean.setTitle("vst_shopping_play_order_data");
			
			// ???????????????
			StringBuilder sb = new StringBuilder();
			sb.append("??????????????????????????????").append(params);
			_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_EXPORT, sb.toString()));
		}catch(Exception e){
			logger.error("??????????????????????????????: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}

	/**
	 * ??????????????????(????????????)
	 */
	@Override
	public ReportBean getPlayOrderReportByDay(Map<String, Object> params) throws SystemException {
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
			
			List<Map<String, Object>> list = _vstShoppingPlayOrderDao.queryReportByDate(params);
			if(list != null && list.size() > 0){
				List<JSONObject> data = new ArrayList<JSONObject>();
				for(Map<String, Object> map : list){
					String date = ToolsDate.formatDate(ToolsDate.yyyy_MM_dd4, map.get("date")+"");
					map.put("date", date);
					boolean flag = true;
					for(JSONObject json2 : data){
						String date2 = json2.getString("date");
						if(date2.equals(date)){	//??????date??????????????????????????????????????????????????????????????????
							int qty = ToolsNumber.parseInt(map.get("qty")+"");
							double salePrice = ToolsNumber.parseDouble(map.get("salePrice")+"");
							json2.put("num", json2.getIntValue("num") + 1);
							json2.put("qty", qty + json2.getIntValue("qty"));
							json2.put("salePrice", salePrice + json2.getDoubleValue("salePrice"));
							flag = false;
							break;
						}
					}
					if(flag){	//??????date?????????????????????????????????????????????
						JSONObject json = new JSONObject(map);
						json.put("num", 1);
						data.add(json);
					}
				}
				bean.setData(data);
			}
		} catch (Exception e) {
			logger.error("??????????????????(????????????)??????: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}

	/**
	 * ??????????????????(???????????????)
	 */
	@Override
	public ReportBean getPlayOrderReportByHour(Map<String, Object> params) throws SystemException {
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
//				// ???????????????????????????
//				if(ToolsString.isEmpty(params.get("vst_shopping_play_order_channel")+"")){
//					params.put("vst_shopping_play_order_channel_ne", "cnrmall");
//				}
			}
			List<Map<String, Object>> list = _vstShoppingPlayOrderDao.queryReportByDate(params);
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
			logger.error("??????????????????(???????????????)??????: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}

	/**
	 * ????????????(????????????ID?????????)
	 */
	@Override
	public ReportBean getPlayOrderReportByGood(CutPage cutPage) throws SystemException {
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
			
			int count = _vstShoppingPlayOrderDao.queryCountByGood(params);
			if(count != 0){
				int start = (cutPage.get_currPage()-1) * cutPage.get_singleCount();
				
				params.put("offset", start);
				params.put("limit", cutPage.get_singleCount());
				
				List<Map<String, Object>> list = _vstShoppingPlayOrderDao.queryListByGood(params);
				
				bean.setTotalSize(count); //???????????????
				bean.setSingleSize(cutPage.get_singleCount());//????????????????????????
				bean.setMapData(list);
			}
		} catch (Exception e) {
			logger.error("????????????????????????(????????????)??????: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
	
	/**
	 * ????????????(????????????ID?????????)
	 */
	@Override
	public ReportBean getPlayOrderExportByGood(Map<String, Object> params, UserSession user) throws SystemException {
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
			
			List<Map<String, Object>> data = _vstShoppingPlayOrderDao.queryExportByGood(params);
			
			bean.setMapData(data);
			bean.setTitle("vst_shopping_play_order_good");
			
			// ???????????????
			StringBuilder sb = new StringBuilder();
			sb.append("????????????(?????????)??????????????????").append(params);
			_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_EXPORT, sb.toString()));
		} catch (Exception e) {
			logger.error("????????????????????????(????????????)??????: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
	
	/**
	 * ??????????????????(??????????????????)
	 */
	@Override
	public ReportBean getPlayOrderSumCutPage(Map<String, Object> params) throws SystemException {
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
			
			// ?????????date->{nums,salePrices}
			Map<String, Map<String, Number>> dataMap = new LinkedHashMap<String, Map<String, Number>>();
			
			List<Map<String, Object>> list = _vstShoppingPlayOrderDao.queryReportByDate(params);
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
			
			// ???dataMap????????????list???
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
			logger.error("??????????????????(??????????????????)??????: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}

	/**
	 * ??????????????????(??????????????????)
	 */
	@Override
	public ReportBean getPlayOrderSumReport(Map<String, Object> params) throws SystemException {
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
			
			List<Map<String, Object>> list = _vstShoppingPlayOrderDao.queryReportByChannel(params);
			
			// ?????????date->{nums1,salePrices1...}
			Map<String, Map<String, Number>> dataMap = new LinkedHashMap<String, Map<String,Number>>();
			
			for(Map<String, Object> map : list){
				String date = ToolsDate.formatDate("yyyyMM", map.get("date")+"");
				String channel = map.get("channel")+"";
				
				if(dataMap.containsKey(date)){
					Map<String, Number> temp = dataMap.get(date);
					if("fasion".equals(channel)){	//????????????
						if(temp.containsKey("nums1") && temp.containsKey("salePrices1")){
							temp.put("nums1", temp.get("nums1").intValue() + ToolsNumber.parseInt(map.get("num")+""));
							temp.put("salePrices1", temp.get("salePrices1").doubleValue() + ToolsNumber.parseDouble(map.get("salePrice")+""));
						}else{
							temp.put("nums1", ToolsNumber.parseInt(map.get("num")+""));
							temp.put("salePrices1", ToolsNumber.parseDouble(map.get("salePrice")+""));
						}
					}else if("globalbuy".equals(channel)){	//????????????
						if(temp.containsKey("nums2") && temp.containsKey("salePrices2")){
							temp.put("nums2", temp.get("nums2").intValue() + ToolsNumber.parseInt(map.get("num")+""));
							temp.put("salePrices2", temp.get("salePrices2").doubleValue() + ToolsNumber.parseDouble(map.get("salePrice")+""));
						}else{
							temp.put("nums2", ToolsNumber.parseInt(map.get("num")+""));
							temp.put("salePrices2", ToolsNumber.parseDouble(map.get("salePrice")+""));
						}
					}else if("hailai".equals(channel)){	//????????????
						if(temp.containsKey("nums3") && temp.containsKey("salePrices3")){
							temp.put("nums3", temp.get("nums3").intValue() + ToolsNumber.parseInt(map.get("num")+""));
							temp.put("salePrices3", temp.get("salePrices3").doubleValue() + ToolsNumber.parseDouble(map.get("salePrice")+""));
						}else{
							temp.put("nums3", ToolsNumber.parseInt(map.get("num")+""));
							temp.put("salePrices3", ToolsNumber.parseDouble(map.get("salePrice")+""));
						}
					}else if("ziying".equals(channel)){	//????????????
						if(temp.containsKey("nums4") && temp.containsKey("salePrices4")){
							temp.put("nums4", temp.get("nums4").intValue() + ToolsNumber.parseInt(map.get("num")+""));
							temp.put("salePrices4", temp.get("salePrices4").doubleValue() + ToolsNumber.parseDouble(map.get("salePrice")+""));
						}else{
							temp.put("nums4", ToolsNumber.parseInt(map.get("num")+""));
							temp.put("salePrices4", ToolsNumber.parseDouble(map.get("salePrice")+""));
						}
					}else if("toll".equals(channel) || "toll_0403".equals(channel)){	//????????????
						if(temp.containsKey("nums5") && temp.containsKey("salePrices5")){
							temp.put("nums5", temp.get("nums5").intValue() + ToolsNumber.parseInt(map.get("num")+""));
							temp.put("salePrices5", temp.get("salePrices5").doubleValue() + ToolsNumber.parseDouble(map.get("salePrice")+""));
						}else{
							temp.put("nums5", ToolsNumber.parseInt(map.get("num")+""));
							temp.put("salePrices5", ToolsNumber.parseDouble(map.get("salePrice")+""));
						}
					}else if("enjoy".equals(channel)){	//????????????
						if(temp.containsKey("nums6") && temp.containsKey("salePrices6")){
							temp.put("nums6", temp.get("nums6").intValue() + ToolsNumber.parseInt(map.get("num")+""));
							temp.put("salePrices6", temp.get("salePrices6").doubleValue() + ToolsNumber.parseDouble(map.get("salePrice")+""));
						}else{
							temp.put("nums6", ToolsNumber.parseInt(map.get("num")+""));
							temp.put("salePrices6", ToolsNumber.parseDouble(map.get("salePrice")+""));
						}
					}else if("youtai".equals(channel)){	//????????????
						if(temp.containsKey("nums7") && temp.containsKey("salePrices7")){
							temp.put("nums7", temp.get("nums7").intValue() + ToolsNumber.parseInt(map.get("num")+""));
							temp.put("salePrices7", temp.get("salePrices7").doubleValue() + ToolsNumber.parseDouble(map.get("salePrice")+""));
						}else{
							temp.put("nums7", ToolsNumber.parseInt(map.get("num")+""));
							temp.put("salePrices7", ToolsNumber.parseDouble(map.get("salePrice")+""));
						}
					}else if("cnrmall".equals(channel)){	//????????????
						if(temp.containsKey("nums8") && temp.containsKey("salePrices8")){
							temp.put("nums8", temp.get("nums8").intValue() + ToolsNumber.parseInt(map.get("num")+""));
							temp.put("salePrices8", temp.get("salePrices8").doubleValue() + ToolsNumber.parseDouble(map.get("salePrice")+""));
						}else{
							temp.put("nums8", ToolsNumber.parseInt(map.get("num")+""));
							temp.put("salePrices8", ToolsNumber.parseDouble(map.get("salePrice")+""));
						}
					}else if("jiayou".equals(channel)){	//????????????
						if(temp.containsKey("nums9") && temp.containsKey("salePrices9")){
							temp.put("nums9", temp.get("nums9").intValue() + ToolsNumber.parseInt(map.get("num")+""));
							temp.put("salePrices9", temp.get("salePrices9").doubleValue() + ToolsNumber.parseDouble(map.get("salePrice")+""));
						}else{
							temp.put("nums9", ToolsNumber.parseInt(map.get("num")+""));
							temp.put("salePrices9", ToolsNumber.parseDouble(map.get("salePrice")+""));
						}
					}
				}else{
					Map<String, Number> temp = new HashMap<String, Number>();
					if("fasion".equals(channel)){	//????????????
						temp.put("nums1", ToolsNumber.parseInt(map.get("num")+""));
						temp.put("salePrices1", ToolsNumber.parseDouble(map.get("salePrice")+""));
					}else if("globalbuy".equals(channel)){	//????????????
						temp.put("nums2", ToolsNumber.parseInt(map.get("num")+""));
						temp.put("salePrices2", ToolsNumber.parseDouble(map.get("salePrice")+""));
					}else if("hailai".equals(channel)){	//????????????
						temp.put("nums3", ToolsNumber.parseInt(map.get("num")+""));
						temp.put("salePrices3", ToolsNumber.parseDouble(map.get("salePrice")+""));
					}else if("ziying".equals(channel)){	//????????????
						temp.put("nums4", ToolsNumber.parseInt(map.get("num")+""));
						temp.put("salePrices4", ToolsNumber.parseDouble(map.get("salePrice")+""));
					}else if("toll".equals(channel) || "toll_0403".equals(channel)){	//????????????
						temp.put("nums5", ToolsNumber.parseInt(map.get("num")+""));
						temp.put("salePrices5", ToolsNumber.parseDouble(map.get("salePrice")+""));
					}else if("enjoy".equals(channel)){	//????????????
						temp.put("nums6", ToolsNumber.parseInt(map.get("num")+""));
						temp.put("salePrices6", ToolsNumber.parseDouble(map.get("salePrice")+""));
					}else if("youtai".equals(channel)){	//????????????
						temp.put("nums7", ToolsNumber.parseInt(map.get("num")+""));
						temp.put("salePrices7", ToolsNumber.parseDouble(map.get("salePrice")+""));
					}else if("cnrmall".equals(channel)){	//????????????
						temp.put("nums8", ToolsNumber.parseInt(map.get("num")+""));
						temp.put("salePrices8", ToolsNumber.parseDouble(map.get("salePrice")+""));
					}else if("jiayou".equals(channel)){	//????????????
						temp.put("nums9", ToolsNumber.parseInt(map.get("num")+""));
						temp.put("salePrices9", ToolsNumber.parseDouble(map.get("salePrice")+""));
					}
					dataMap.put(date, temp);
				}
			}
			
			// ???dataMap????????????list???
			List<JSONObject> data = new ArrayList<JSONObject>();
			for(String key : dataMap.keySet()){
				Map<String, Number> value = dataMap.get(key);
				JSONObject json = new JSONObject();
				json.put("date", key);
				json.put("nums1", ToolsNumber.parseInt(ToolsString.checkEmpty(value.get("nums1")), 0));
				json.put("salePrices1", ToolsNumber.parseDouble(ToolsString.checkEmpty(value.get("salePrices1")), 0));
				json.put("nums2", ToolsNumber.parseInt(ToolsString.checkEmpty(value.get("nums2")), 0));
				json.put("salePrices2", ToolsNumber.parseDouble(ToolsString.checkEmpty(value.get("salePrices2")), 0));
				json.put("nums3", ToolsNumber.parseInt(ToolsString.checkEmpty(value.get("nums3")), 0));
				json.put("salePrices3", ToolsNumber.parseDouble(ToolsString.checkEmpty(value.get("salePrices3")), 0));
				json.put("nums4", ToolsNumber.parseInt(ToolsString.checkEmpty(value.get("nums4")), 0));
				json.put("salePrices4", ToolsNumber.parseDouble(ToolsString.checkEmpty(value.get("salePrices4")), 0));
				json.put("nums5", ToolsNumber.parseInt(ToolsString.checkEmpty(value.get("nums5")), 0));
				json.put("salePrices5", ToolsNumber.parseDouble(ToolsString.checkEmpty(value.get("salePrices5")), 0));
				json.put("nums6", ToolsNumber.parseInt(ToolsString.checkEmpty(value.get("nums6")), 0));
				json.put("salePrices6", ToolsNumber.parseDouble(ToolsString.checkEmpty(value.get("salePrices6")), 0));
				json.put("nums7", ToolsNumber.parseInt(ToolsString.checkEmpty(value.get("nums7")), 0));
				json.put("salePrices7", ToolsNumber.parseDouble(ToolsString.checkEmpty(value.get("salePrices7")), 0));
				json.put("nums8", ToolsNumber.parseInt(ToolsString.checkEmpty(value.get("nums8")), 0));
				json.put("salePrices8", ToolsNumber.parseDouble(ToolsString.checkEmpty(value.get("salePrices8")), 0));
				json.put("nums9", ToolsNumber.parseInt(ToolsString.checkEmpty(value.get("nums9")), 0));
				json.put("salePrices9", ToolsNumber.parseDouble(ToolsString.checkEmpty(value.get("salePrices9")), 0));
				data.add(json);
			}
			bean.setData(data);
		} catch (Exception e) {
			logger.error("??????????????????(??????????????????)??????: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
	
	/**
	 * ????????????(????????????)
	 */
	@Override
	public ReportBean getPlayOrderReportByProvince(CutPage cutPage) throws SystemException {
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
			
			int count = _vstShoppingPlayOrderDao.queryCountByProvince(params);
			if(count != 0){
				int start = (cutPage.get_currPage()-1) * cutPage.get_singleCount();
				
				params.put("offset", start);
				params.put("limit", cutPage.get_singleCount());
				
				List<Map<String, Object>> list = _vstShoppingPlayOrderDao.queryListByProvince(params);
				
				bean.setTotalSize(count); //???????????????
				bean.setSingleSize(cutPage.get_singleCount());//????????????????????????
				bean.setMapData(list);
			}
		} catch (Exception e) {
			logger.error("??????????????????(????????????)??????: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
	
	/**
	 * ????????????(????????????)
	 */
	@Override
	public ReportBean getPlayOrderExportByProvince(Map<String, Object> params, UserSession user) throws SystemException {
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
			
			List<Map<String, Object>> data = _vstShoppingPlayOrderDao.queryExportByProvince(params);
			
			bean.setMapData(data);
			bean.setTitle("vst_shopping_play_order_province");
			
			// ???????????????
			StringBuilder sb = new StringBuilder();
			sb.append("????????????(?????????)??????????????????").append(params);
			_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_EXPORT, sb.toString()));
		} catch (Exception e) {
			logger.error("??????????????????(????????????)??????: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
	
	/**
	 * ????????????(????????????)
	 */
	@Override
	public ReportBean getPlayOrderReportByAddress(CutPage cutPage) throws SystemException {
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
			
			int count = _vstShoppingPlayOrderDao.queryCountByAddress(params);
			if(count != 0){
				int start = (cutPage.get_currPage()-1) * cutPage.get_singleCount();
				
				params.put("offset", start);
				params.put("limit", cutPage.get_singleCount());
				
				List<Map<String, Object>> list = _vstShoppingPlayOrderDao.queryListByAddress(params);
				
				bean.setTotalSize(count); //???????????????
				bean.setSingleSize(cutPage.get_singleCount());//????????????????????????
				bean.setMapData(list);
			}
		} catch (Exception e) {
			logger.error("??????????????????(????????????)??????: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
	
	/**
	 * ????????????(????????????)
	 */
	@Override
	public ReportBean getPlayOrderExportByAddress(Map<String, Object> params, UserSession user) throws SystemException {
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
			
			List<Map<String, Object>> data = _vstShoppingPlayOrderDao.queryExportByAddress(params);
			
			bean.setMapData(data);
			bean.setTitle("vst_shopping_play_order_address");
			
			// ???????????????
			StringBuilder sb = new StringBuilder();
			sb.append("????????????(?????????)??????????????????").append(params);
			_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_EXPORT, sb.toString()));
		} catch (Exception e) {
			logger.error("??????????????????(????????????)??????: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
	
	/**
	 * ????????????????????????
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int graspFasionData(Map<String, Object> params, UserSession user){
		int result = 0;
		try{
			String startDay = ToolsString.checkEmpty(params.get("startDay"));
			String endDay = ToolsString.checkEmpty(params.get("endDay"));
			
			String url = FASION_URL.replace("{startDay}", startDay).replace("{endDay}", endDay).replace("{page}", "1");
			//??????????????????Cookie???
			List<String> cookies = ToolsHttp.getHeadCookies(FASION_LOGIN);
			if(cookies.size() > 0){
				Request request = new Request(url);
				request.setCookie(cookies.get(0));
				JSONObject urlJson = ToolsHttp.getJson(request);
				if(urlJson != null){
					//???????????????
					String total = urlJson.getString("total");
					int page = 1;
					int pageNum = Integer.parseInt(total) / 100 + 1;
					
					while(page <= pageNum){
						url = FASION_URL.replace("{startDay}", startDay).replace("{endDay}", endDay).replace("{page}", String.valueOf(page));
						request = new Request(url);
						urlJson = ToolsHttp.getJson(request);
						if(urlJson != null){
							JSONArray rows = urlJson.getJSONArray("rows");
							List<VstShoppingPlayOrder> orders = new ArrayList<VstShoppingPlayOrder>();
							Long now = System.currentTimeMillis();
							//??????rows????????????
							for(int i=0; i<rows.size(); i++){
								JSONObject row = rows.getJSONObject(i);
								String date = row.getString("etrDate");
								
								VstShoppingPlayOrder bean = new VstShoppingPlayOrder();
								//??????
								bean.setVst_shopping_play_order_date(ToolsDate.toUnixTimestamp("yyyy-MM-dd HH", date));
								//??????
								bean.setVst_shopping_play_order_good_id(row.getString("goodId"));
								bean.setVst_shopping_play_order_good_name(row.getString("goodNm"));
								//??????
								bean.setVst_shopping_play_order_good_price(row.getDoubleValue("prc"));
								bean.setVst_shopping_play_order_qty(row.getIntValue("qty"));
								bean.setVst_shopping_play_order_sale_price(row.getDoubleValue("amt"));
								//??????
								bean.setVst_shopping_play_order_address(row.getString("addr"));
								bean.setVst_shopping_play_order_province(ExportUtil.getProvinceByAddress(row.getString("addr")));
								//?????????
								bean.setVst_shopping_play_order_recipient(row.getString("rcverNm"));
								bean.setVst_shopping_play_order_phone("");
								//?????????
								bean.setVst_shopping_play_order_supplier_type("");
								bean.setVst_shopping_play_order_channel("fasion");
								bean.setVst_shopping_play_order_addtime(now);
								bean.setVst_shopping_play_order_uptime(now);
								bean.setVst_shopping_play_order_operator(user.getLoginInfo().getLoginName());
								
								orders.add(bean);
							}
							
							if(orders.size() > 0){
								int count = _vstShoppingPlayOrderDao.batchInsert(orders);
								System.out.println("???????????????????????????" + count);
								result += count;
							}
						}
						page++;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * ????????????????????????
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
								
								List<VstShoppingPlayOrder> orders = new ArrayList<VstShoppingPlayOrder>();
								Long now = System.currentTimeMillis();
								
								//??????????????????
								for(Element tr : trs){
									Elements tds = tr.getElementsByTag("td");
									VstShoppingPlayOrder bean = new VstShoppingPlayOrder();
									//??????
									bean.setVst_shopping_play_order_date(ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm, tds.get(5).text()));
									//??????
									bean.setVst_shopping_play_order_good_id(tds.get(3).text());
									bean.setVst_shopping_play_order_good_name(tds.get(1).text());
									//??????
									bean.setVst_shopping_play_order_good_price(ToolsNumber.parseDouble(tds.get(8).text()));
									bean.setVst_shopping_play_order_qty(ToolsNumber.parseInt(tds.get(2).text()));
									bean.setVst_shopping_play_order_sale_price(ToolsNumber.parseDouble(tds.get(9).text()));
									//??????
									bean.setVst_shopping_play_order_address(tds.get(7).text());
									bean.setVst_shopping_play_order_province(ExportUtil.getProvinceByAddress(tds.get(7).text()));
									//?????????
									bean.setVst_shopping_play_order_recipient("");
									bean.setVst_shopping_play_order_phone("");
									//?????????
									bean.setVst_shopping_play_order_supplier_type("");
									bean.setVst_shopping_play_order_channel("globalbuy");
									bean.setVst_shopping_play_order_addtime(now);
									bean.setVst_shopping_play_order_uptime(now);
									bean.setVst_shopping_play_order_operator(user.getLoginInfo().getLoginName());
									
									orders.add(bean);
								}
								
								int count = _vstShoppingPlayOrderDao.batchInsert(orders);
								System.out.println("???????????????????????????" + count);
								result += count;
								
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
	 * ??????????????????(???)??????
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
			header.put("Host", "119.163.198.230:9003");
			header.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:51.0) Gecko/20100101 Firefox/51.0");
			header.put("Accept", "application/json, text/javascript, */*; q=0.01");
			header.put("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
			header.put("Accept-Encoding", "gzip, deflate");
			header.put("Referer", "http://119.163.198.230:9003/b2brep");
			header.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			header.put("Content-Length", "XMLHttpRequest");
			header.put("X-Requested-With", "114");
			header.put("Cookie", cookie);
			header.put("Connection", "keep-alive");
			
			String postBody = "p_order_date_s={startDay}&p_order_date_e={endDay}&p_channel_name=16&pssid=hpid".replace("{startDay}", startDay).replace("{endDay}", endDay);
		
			String jsonUrl = ToolsHttp.httpPost(HAILAI_URL2, header, postBody);
			if(!ToolsString.isEmpty(jsonUrl)){
				JSONObject urlJson = JSONObject.parseObject(jsonUrl);
				JSONArray orders = urlJson.getJSONArray("orders");
				if(orders != null){
					List<VstShoppingPlayOrder> orderList = new ArrayList<VstShoppingPlayOrder>();
					Long now = System.currentTimeMillis();
					
					//??????????????????????????????
					for(int i=0; i<orders.size(); i++){
						JSONArray order = orders.getJSONArray(i);
						VstShoppingPlayOrder bean = new VstShoppingPlayOrder();
						//??????
						bean.setVst_shopping_play_order_date(ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm_ss, order.getString(1)));
						//???????????????ID????????????*??????????????????/??????
						String shopLine = order.getString(10);
						//?????? ??????ID???????????????
						int index = 0;
						for(int j=0; j<shopLine.length(); j++){
							if(shopLine.charAt(j) < '0' || shopLine.charAt(j) > '9'){
								index = j;
								break;
							}
						}
						//??????
						bean.setVst_shopping_play_order_good_id(shopLine.substring(0, index));
						bean.setVst_shopping_play_order_good_name(shopLine);
						//??????
						bean.setVst_shopping_play_order_good_price(order.getDouble(4));
						bean.setVst_shopping_play_order_qty(1);
						bean.setVst_shopping_play_order_sale_price(order.getDouble(4));
						//??????
						bean.setVst_shopping_play_order_address(order.getString(7));
						bean.setVst_shopping_play_order_province(ExportUtil.getProvinceByAddress(order.getString(7)));
						//?????????
						bean.setVst_shopping_play_order_recipient(order.getString(5));
						bean.setVst_shopping_play_order_phone(order.getString(6));
						//?????????
						bean.setVst_shopping_play_order_supplier_type("");
						bean.setVst_shopping_play_order_channel("hailai");
						bean.setVst_shopping_play_order_addtime(now);
						bean.setVst_shopping_play_order_uptime(now);
						bean.setVst_shopping_play_order_operator(user.getLoginInfo().getLoginName());
						
						orderList.add(bean);
					}
					
					if(orders.size() > 0){
						int count = _vstShoppingPlayOrderDao.batchInsert(orderList);
						System.out.println("???????????????????????????" + count);
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
	 * ????????????????????????
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int graspZiyingData(Map<String, Object> params, UserSession user){
		int result = 0;
		try{
			String startDay = ToolsString.checkEmpty(params.get("startDay"));
			String endDay = ToolsString.checkEmpty(params.get("endDay"));
			String cookie = ToolsString.checkEmpty(params.get("cookie"));
			
			//???????????????(???????????????????????????????????????)
			String[] supplierType = new String[]{"", "self", "consignment", "shoppingTV"};
			for(String supplier : supplierType){
				String url = ZIYING_URL.replace("{startDay}", startDay).replace("{endDay}", endDay).replace("{supplierType}", supplier);
				Request request = new Request(url);
				request.setCookie(cookie);
				if(ToolsHttp.getString(request) != null){
					Html html = Html.create(ToolsHttp.getString(request));
					org.jsoup.nodes.Document document = html.getDocument();
					
					Elements pageSkip = document.getElementsByClass("pageSkip");
					if(pageSkip != null && pageSkip.size() > 0){
						String pageStr = pageSkip.text();
						if(Pattern.matches("^???[0-9]{1,}???$", pageStr)){
							int totals = ToolsNumber.parseInt(pageStr.substring(pageStr.indexOf("???")+1, pageStr.indexOf("???")));
							
							for(int total=1; total<=totals; total++){
								String url2 = url + "&pageNumber=" + total;
								Request request2 = new Request(url2);
								request2.setCookie(cookie);
								if(ToolsHttp.getString(request2) != null){
									Html html2 = Html.create(ToolsHttp.getString(request2));
									org.jsoup.nodes.Document document2 = html2.getDocument();
									Element listTable = document2.getElementById("listTable");
									if(listTable != null){
										Elements inputs = listTable.getElementsByTag("input");
										if(inputs.size() > 1){
											List<VstShoppingPlayOrder> orders = new ArrayList<VstShoppingPlayOrder>();
											Long now = System.currentTimeMillis();
											
											for(int i=1; i<inputs.size(); i++){
												Element input = inputs.get(i);
												String orderId = input.attr("value");
												String msgUrl = ZIYING_MSG_URL.replace("{id}", orderId);
												
												Request msgRequest = new Request(msgUrl);
												msgRequest.setCookie(cookie);
												if(ToolsHttp.getString(msgRequest) != null){
													Html msgHtml = Html.create(ToolsHttp.getString(msgRequest));
													Document msgDocument = msgHtml.getDocument();
													
													Elements inputTbodys = msgDocument.getElementsByTag("tbody");
													if(inputTbodys.size() > 2){
														//????????????
														Element orderTbody = inputTbodys.get(0);
														Elements orderTds = orderTbody.getElementsByTag("td");
														//????????????
														Element goodTbody = inputTbodys.get(1);
														Elements goodTds = goodTbody.getElementsByTag("td");
														
														VstShoppingPlayOrder bean = new VstShoppingPlayOrder();
														//??????
														bean.setVst_shopping_play_order_date(ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm_ss, orderTds.get(1).text()));
														//??????
														bean.setVst_shopping_play_order_good_id(goodTds.get(0).text());
														bean.setVst_shopping_play_order_good_name(goodTds.get(1).text());
														//??????
														bean.setVst_shopping_play_order_good_price(ToolsNumber.parseDouble(goodTds.get(2).text().substring(1)));
														bean.setVst_shopping_play_order_qty(ToolsNumber.parseInt(goodTds.get(3).text()));
														bean.setVst_shopping_play_order_sale_price(ToolsNumber.parseDouble(goodTds.get(4).text().substring(1)));
														//??????
														bean.setVst_shopping_play_order_address(orderTds.get(15).text());
														bean.setVst_shopping_play_order_province(ExportUtil.getProvinceByAddress(orderTds.get(15).text()));
														//?????????
														bean.setVst_shopping_play_order_recipient(orderTds.get(14).text());
														bean.setVst_shopping_play_order_phone(orderTds.get(18).text());
														//?????????
														bean.setVst_shopping_play_order_supplier_type(supplier);
														bean.setVst_shopping_play_order_channel("ziying");
														bean.setVst_shopping_play_order_addtime(now);
														bean.setVst_shopping_play_order_uptime(now);
														bean.setVst_shopping_play_order_operator(user.getLoginInfo().getLoginName());
														
														orders.add(bean);
													}
												}
											}
											
											if(orders.size() > 0){
												int count = _vstShoppingPlayOrderDao.batchInsert(orders);
												System.out.println("??????????????????("+supplier+")?????????" + count);
												result += count;
											}
										}
									}
								}
							}
						}
					}else{
						Element listTable = document.getElementById("listTable");
						if(listTable != null){
							Elements inputs = listTable.getElementsByTag("input");
							if(inputs.size() > 1){
								List<VstShoppingPlayOrder> orders = new ArrayList<VstShoppingPlayOrder>();
								Long now = System.currentTimeMillis();
								
								for(int i=1; i<inputs.size(); i++){
									Element input = inputs.get(i);
									String orderId = input.attr("value");
									String msgUrl = ZIYING_MSG_URL.replace("{id}", orderId);
									
									Request msgRequest = new Request(msgUrl);
									msgRequest.setCookie(cookie);
									if(ToolsHttp.getString(msgRequest) != null){
										Html msgHtml = Html.create(ToolsHttp.getString(msgRequest));
										org.jsoup.nodes.Document msgDocument = msgHtml.getDocument();
										
										Elements inputTbodys = msgDocument.getElementsByTag("tbody");
										if(inputTbodys.size() > 2){
											//????????????
											Element orderTbody = inputTbodys.get(0);
											Elements orderTds = orderTbody.getElementsByTag("td");
											//????????????
											Element goodTbody = inputTbodys.get(1);
											Elements goodTds = goodTbody.getElementsByTag("td");
											
											VstShoppingPlayOrder bean = new VstShoppingPlayOrder();
											//??????
											bean.setVst_shopping_play_order_date(ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm_ss, orderTds.get(1).text()));
											//??????
											bean.setVst_shopping_play_order_good_id(goodTds.get(0).text());
											bean.setVst_shopping_play_order_good_name(goodTds.get(1).text());
											//??????
											bean.setVst_shopping_play_order_good_price(ToolsNumber.parseDouble(goodTds.get(2).text().substring(1)));
											bean.setVst_shopping_play_order_qty(ToolsNumber.parseInt(goodTds.get(3).text()));
											bean.setVst_shopping_play_order_sale_price(ToolsNumber.parseDouble(goodTds.get(4).text().substring(1)));
											//??????
											bean.setVst_shopping_play_order_address(orderTds.get(15).text());
											bean.setVst_shopping_play_order_province(ExportUtil.getProvinceByAddress(orderTds.get(15).text()));
											//?????????
											bean.setVst_shopping_play_order_recipient(orderTds.get(14).text());
											bean.setVst_shopping_play_order_phone(orderTds.get(18).text());
											//?????????
											bean.setVst_shopping_play_order_supplier_type(supplier);
											bean.setVst_shopping_play_order_channel("ziying");
											bean.setVst_shopping_play_order_addtime(now);
											bean.setVst_shopping_play_order_uptime(now);
											bean.setVst_shopping_play_order_operator(user.getLoginInfo().getLoginName());
											
											orders.add(bean);
										}
									}
								}
								
								if(orders.size() > 0){
									int count = _vstShoppingPlayOrderDao.batchInsert(orders);
									System.out.println("??????????????????("+supplier+")?????????" + count);
									result += count;
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
	 * ????????????????????????
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int graspTollData(Map<String, Object> params, UserSession user){
		int result = 0;
		try{
			// ????????????
			CloseableHttpClient client = HttpClients.createDefault();
			// ??????post?????????????????????
			String loginUrl = TOLL_LOGIN.replace("{phone}", "5882");
			HttpPost postLogin = new HttpPost(loginUrl);
			Map<String, String> parameterMap = new HashMap<String, String>();
			parameterMap.put("__VIEWSTATE", "/wEPDwUJMjc0MjQwMDkwZGQnwWozUv/mWum1VwszkGzLoScsIf0b7WZJ4emnUJlcfQ==");
			parameterMap.put("__EVENTVALIDATION", "/wEWBAKU+rbXBwKl1bKzCQKv1vq2DgKPyPGACgx2WjbnuUNsg6K73I1WHRRADP0n/4K0VO8IjMsEcPE/");
			parameterMap.put("txtUserName", "5882");
			parameterMap.put("txtPaseWord", "5882");
			parameterMap.put("login", "??????");
			UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(getParam(parameterMap), "UTF-8");
			postLogin.setEntity(postEntity);
			
			try {
				// ??????post??????
				HttpResponse postLoginResponse = client.execute(postLogin);
				// ??????????????????????????????302??????????????????
				int resCode = postLoginResponse.getStatusLine().getStatusCode();
				
				if(resCode == 302){
					// ????????????GET??????????????????????????????Commodity.aspx
					String requestUrl = TOLL_URL.replace("{phone}", "5882");
					HttpGet getCommodity = new HttpGet(requestUrl);
					HttpResponse getCommodityResponse = client.execute(getCommodity);
					
					//???commodity????????????doc ???????????? __VIEWSTATE 	__EVENTVALIDATION
					Document doc = Jsoup.parse(getResponseContent(getCommodityResponse));
					String viewState = doc.getElementById("__VIEWSTATE").val();
					String eventvalidation = doc.getElementById("__EVENTVALIDATION").val();
					
					HttpPost postCommodity = new HttpPost(requestUrl);
					Map<String, String> parameterMap2 = new HashMap<String, String>();
					
					String startDay = ToolsString.checkEmpty(params.get("startDay"));
					String endDay = ToolsString.checkEmpty(params.get("endDay"));
					
					parameterMap2.put("txtOrderTime", startDay);
					parameterMap2.put("txtOrderHours", "00:00:00");
					parameterMap2.put("txtOrderTimes", endDay);
					parameterMap2.put("txtOrderHourses", "23:59:59");
					parameterMap2.put("txtCalledPhone", "56965873");
					parameterMap2.put("txtSendDetailAddress", "");
					parameterMap2.put("BtnSearch", "");
					parameterMap2.put("__VIEWSTATE", viewState);
					parameterMap2.put("__EVENTVALIDATION", eventvalidation);
					postCommodity.setEntity(new UrlEncodedFormEntity(getParam(parameterMap2), "UTF-8"));
					HttpResponse httpResponse2 = client.execute(postCommodity);
					String orderListHtml = getResponseContent(httpResponse2);
					
					if(!ToolsString.isEmpty(orderListHtml)){
						Document orderDoc = Jsoup.parse(orderListHtml);
						Elements con_tab =  orderDoc.select(".con-tab");//??????class?????????.con-tab???DIV
						Element table =  con_tab.first().child(0);//????????????DIV?????????table
						
						if(table.children() != null){
							Elements trList = table.child(0).children();//??????tbody????????????tr??????
							
							List<VstShoppingPlayOrder> orders = new ArrayList<VstShoppingPlayOrder>();
							Long now = System.currentTimeMillis();
							
							for (Element tr : trList) {
								VstShoppingPlayOrder bean = new VstShoppingPlayOrder();
								//??????
								bean.setVst_shopping_play_order_date(ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm_ss, tr.child(0).text()));
								//??????
								bean.setVst_shopping_play_order_good_id("");
								bean.setVst_shopping_play_order_good_name(tr.child(2).text());
								//??????
								bean.setVst_shopping_play_order_good_price(ToolsNumber.parseDouble(tr.child(3).text(), 0.0));
								bean.setVst_shopping_play_order_qty(ToolsNumber.parseInt(tr.child(5).text(), 0));
								bean.setVst_shopping_play_order_sale_price(ToolsNumber.parseDouble(tr.child(3).text(), 0.0));
								//??????
								bean.setVst_shopping_play_order_address(tr.child(6).text());
								bean.setVst_shopping_play_order_province(ExportUtil.getProvinceByAddress(tr.child(6).text()));
								//?????????
								bean.setVst_shopping_play_order_recipient(tr.child(1).text());
								bean.setVst_shopping_play_order_phone("");
								//?????????
								bean.setVst_shopping_play_order_supplier_type("");
								bean.setVst_shopping_play_order_channel("toll");
								bean.setVst_shopping_play_order_addtime(now);
								bean.setVst_shopping_play_order_uptime(now);
								bean.setVst_shopping_play_order_operator(user.getLoginInfo().getLoginName());
								
								orders.add(bean);
							}
							
							if(orders.size() > 0){
								int count = _vstShoppingPlayOrderDao.batchInsert(orders);
								System.out.println("???????????????????????????" + count);
								result += count;
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					// ????????????????????????
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * ????????????0403??????
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int graspTollData_0403(Map<String, Object> params, UserSession user){
		int result = 0;
		try{
			// ????????????
			CloseableHttpClient client = HttpClients.createDefault();
			// ??????post?????????????????????
			String loginUrl = TOLL_LOGIN.replace("{phone}", "0403");
			HttpPost postLogin = new HttpPost(loginUrl);
			Map<String, String> parameterMap = new HashMap<String, String>();
			parameterMap.put("__VIEWSTATE", "/wEPDwUJMjc0MjQwMDkwZGSP/XYPF2+djmOAYqobTTQYPioxCZ0gdfN9NR7VwwfGMA==");
			parameterMap.put("__EVENTVALIDATION", "/wEWBAKlxIO/AQKl1bKzCQKv1vq2DgKPyPGACmTLv3+KiZ2Q1+2Gncv0YYrKSZthpEgj45aeSfp6V8Re");
			parameterMap.put("txtUserName", "0403");
			parameterMap.put("txtPaseWord", "040340");
			parameterMap.put("login", "??????");
			UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(getParam(parameterMap), "UTF-8");
			postLogin.setEntity(postEntity);
			
			try {
				// ??????post??????
				HttpResponse postLoginResponse = client.execute(postLogin);
				// ??????????????????????????????302??????????????????
				int resCode = postLoginResponse.getStatusLine().getStatusCode();
				
				if(resCode == 302){
					// ????????????GET??????????????????????????????Commodity.aspx
					String requestUrl = TOLL_URL.replace("{phone}", "0403");
					HttpGet getCommodity = new HttpGet(requestUrl);
					HttpResponse getCommodityResponse = client.execute(getCommodity);
					
					//???commodity????????????doc ???????????? __VIEWSTATE 	__EVENTVALIDATION
					Document doc = Jsoup.parse(getResponseContent(getCommodityResponse));
					String viewState = doc.getElementById("__VIEWSTATE").val();
					String eventvalidation = doc.getElementById("__EVENTVALIDATION").val();
					
					HttpPost postCommodity = new HttpPost(requestUrl);
					Map<String, String> parameterMap2 = new HashMap<String, String>();
					
					String startDay = ToolsString.checkEmpty(params.get("startDay"));
					String endDay = ToolsString.checkEmpty(params.get("endDay"));
					
					parameterMap2.put("txtOrderTime", startDay);
					parameterMap2.put("txtOrderHours", "00:00:00");
					parameterMap2.put("txtOrderTimes", endDay);
					parameterMap2.put("txtOrderHourses", "23:59:59");
					parameterMap2.put("txtCalledPhone", "56617513");
					parameterMap2.put("txtSendDetailAddress", "");
					parameterMap2.put("BtnSearch", "");
					parameterMap2.put("__VIEWSTATE", viewState);
					parameterMap2.put("__EVENTVALIDATION", eventvalidation);
					postCommodity.setEntity(new UrlEncodedFormEntity(getParam(parameterMap2), "UTF-8"));
					HttpResponse httpResponse2 = client.execute(postCommodity);
					String orderListHtml = getResponseContent(httpResponse2);
					
					if(!ToolsString.isEmpty(orderListHtml)){
						Document orderDoc = Jsoup.parse(orderListHtml);
						Elements con_tab =  orderDoc.select(".con-tab");//??????class?????????.con-tab???DIV
						Element table =  con_tab.first().child(0);//????????????DIV?????????table
						
						if(table.children() != null){
							Elements trList = table.child(0).children();//??????tbody????????????tr??????
							
							List<VstShoppingPlayOrder> orders = new ArrayList<VstShoppingPlayOrder>();
							Long now = System.currentTimeMillis();
							
							for (Element tr : trList) {
								VstShoppingPlayOrder bean = new VstShoppingPlayOrder();
								//??????
								bean.setVst_shopping_play_order_date(ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm_ss, tr.child(0).text()));
								//??????
								bean.setVst_shopping_play_order_good_id("");
								bean.setVst_shopping_play_order_good_name(tr.child(2).text());
								//??????
								bean.setVst_shopping_play_order_good_price(ToolsNumber.parseDouble(tr.child(3).text(), 0.0));
								bean.setVst_shopping_play_order_qty(ToolsNumber.parseInt(tr.child(5).text(), 0));
								bean.setVst_shopping_play_order_sale_price(ToolsNumber.parseDouble(tr.child(3).text(), 0.0));
								//??????
								bean.setVst_shopping_play_order_address(tr.child(6).text());
								bean.setVst_shopping_play_order_province(ExportUtil.getProvinceByAddress(tr.child(6).text()));
								//?????????
								bean.setVst_shopping_play_order_recipient(tr.child(1).text());
								bean.setVst_shopping_play_order_phone("");
								//?????????
								bean.setVst_shopping_play_order_supplier_type("");
								bean.setVst_shopping_play_order_channel("toll_0403");
								bean.setVst_shopping_play_order_addtime(now);
								bean.setVst_shopping_play_order_uptime(now);
								bean.setVst_shopping_play_order_operator(user.getLoginInfo().getLoginName());
								
								orders.add(bean);
							}
							
							if(orders.size() > 0){
								int count = _vstShoppingPlayOrderDao.batchInsert(orders);
								System.out.println("????????????0403?????????" + count);
								result += count;
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					// ????????????????????????
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * ????????????????????????
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int graspEnjoyData(Map<String, Object> params, UserSession user){
		int result = 0;
		try{
			String startDay = ToolsString.checkEmpty(params.get("startDay"));
			String endDay = ToolsString.checkEmpty(params.get("endDay"));
			String cookie = ToolsString.checkEmpty(params.get("cookie"));
			Long now = System.currentTimeMillis();
			
			Map<String, String> header = new HashMap<String, String>();
			header.put("Host", "xscx.hao24.cn:7777");
			header.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0");
			header.put("Accept", "*/*");
			header.put("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
			header.put("Accept-Encoding", "gzip, deflate");
			header.put("Referer", "http://xscx.hao24.cn:7777/LevelCities/toOttOrders.action");
			header.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			header.put("Content-Length", "135");
			header.put("X-Requested-With", "XMLHttpRequest");
			header.put("Cookie", cookie);
			header.put("Connection", "keep-alive");
			
			String postBody = "_dc={_dc}&stDate={startDay}&edDate={endDay}&ordStat=ALL&rtnStat=ALL&ordType=ALL&city=??????&giftYN=ALL&mclssId=&sclssId=&dateType=1".replace("{_dc}", now+"").replace("{startDay}", startDay).replace("{endDay}", endDay);
			
			String jsonUrl = ToolsHttp.httpPost(ENJOY_URL, header, postBody);
			if(!ToolsString.isEmpty(jsonUrl)){
				JSONArray jsonArray = JSONObject.parseArray(jsonUrl);
				
				List<VstShoppingPlayOrder> orders = new ArrayList<VstShoppingPlayOrder>();
				for(int i=0; i<jsonArray.size(); i++){
					JSONObject shopJSON = jsonArray.getJSONObject(i);
					
					VstShoppingPlayOrder bean = new VstShoppingPlayOrder();
					//??????
					bean.setVst_shopping_play_order_date(ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm_ss, shopJSON.getString("etr_date")));
					//??????
					bean.setVst_shopping_play_order_good_id(shopJSON.getString("good_id"));
					bean.setVst_shopping_play_order_good_name(shopJSON.getString("good_nm"));
					//??????
					bean.setVst_shopping_play_order_good_price(shopJSON.getDouble("ord_amt"));
					bean.setVst_shopping_play_order_qty(shopJSON.getIntValue("ord_qty"));
					bean.setVst_shopping_play_order_sale_price(shopJSON.getDouble("ord_amt"));
					//??????
					bean.setVst_shopping_play_order_address(shopJSON.getString("addr"));
					bean.setVst_shopping_play_order_province(shopJSON.getString("getprovince"));
					//?????????
					bean.setVst_shopping_play_order_recipient(shopJSON.getString("cust_nm"));
					bean.setVst_shopping_play_order_phone("");
					//?????????
					bean.setVst_shopping_play_order_supplier_type("");
					bean.setVst_shopping_play_order_channel("enjoy");
					bean.setVst_shopping_play_order_addtime(now);
					bean.setVst_shopping_play_order_uptime(now);
					bean.setVst_shopping_play_order_operator(user.getLoginInfo().getLoginName());
					
					orders.add(bean);
				}
				
				if(orders.size() > 0){
					int count = _vstShoppingPlayOrderDao.batchInsert(orders);
					System.out.println("???????????????????????????" + count);
					result += count;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * ????????????????????????
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int graspYoutaiData(Map<String, Object> params, UserSession user){
		int result = 0;
		try{
			String startDay = ToolsString.checkEmpty(params.get("startDay"));
			String endDay = ToolsString.checkEmpty(params.get("endDay"));
			String cookie = ToolsString.checkEmpty(params.get("cookie"));
			Long now = System.currentTimeMillis();
			
			String url = YOUTAI_URL.replace("{startDay}", startDay).replace("{endDay}", endDay);
			Request request = new Request(url);
			request.setCookie(cookie);
			JSONObject json = ToolsHttp.getJson(request);
			
			if(json != null){
				JSONObject data = json.getJSONObject("data");
				if(data != null){
					JSONArray orderList = data.getJSONArray("list");
					if(orderList != null && orderList.size() > 0){
						List<VstShoppingPlayOrder> orders = new ArrayList<VstShoppingPlayOrder>();
						
						for(int i=0; i<orderList.size(); i++){
							JSONObject order = orderList.getJSONObject(i);
							
							// ??????????????????
							JSONArray items = order.getJSONArray("items");
							if(items != null && items.size() > 0){
								for(int j=0; j<items.size(); j++){
									JSONObject item = items.getJSONObject(j);
									
									VstShoppingPlayOrder bean = new VstShoppingPlayOrder();
									//??????
									bean.setVst_shopping_play_order_date(ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm_ss, order.getString("created_time")));
									//??????
									bean.setVst_shopping_play_order_good_id(item.getString("goods_id"));
									bean.setVst_shopping_play_order_good_name(item.getString("title"));
									//??????
									bean.setVst_shopping_play_order_good_price(item.getDouble("price"));
									bean.setVst_shopping_play_order_qty(item.getIntValue("num"));
									bean.setVst_shopping_play_order_sale_price(item.getDouble("pay_price"));
									//??????
									String address = order.getString("province")+order.getString("city")+order.getString("county")+order.getString("address_detail");
									bean.setVst_shopping_play_order_address(address);
									bean.setVst_shopping_play_order_province(order.getString("province"));
									//?????????
									bean.setVst_shopping_play_order_recipient(order.getString("user_name"));
									bean.setVst_shopping_play_order_phone(order.getString("tel"));
									//?????????
									bean.setVst_shopping_play_order_supplier_type("");
									bean.setVst_shopping_play_order_channel("youtai");
									bean.setVst_shopping_play_order_addtime(now);
									bean.setVst_shopping_play_order_uptime(now);
									bean.setVst_shopping_play_order_operator(user.getLoginInfo().getLoginName());
									
									orders.add(bean);
								}
							}
						}
						
						if(orders.size() > 0){
							int count = _vstShoppingPlayOrderDao.batchInsert(orders);
							System.out.println("???????????????????????????" + count);
							result += count;
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
	 * ????????????????????????
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int graspCnrMallData(Map<String, Object> params, UserSession user){
		int result = 0;
		try{
			String startDay = ToolsString.checkEmpty(params.get("startDay"));
			String endDay = ToolsString.checkEmpty(params.get("endDay"));
			String cookie = ToolsString.checkEmpty(params.get("cookie"));
			
			String url = CNR_URL.replace("{startDay}", VstTools.parseInt(startDay)+"").replace("{endDay}", VstTools.parseInt(endDay)+"");
			int page = 1;
			url = url.replace("{pageNo}", page+"");
			
			OkHttpClient client = new OkHttpClient();
			
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "");
            okhttp3.Request request = new okhttp3.Request.Builder()
                   .url(url)
                   .post(body)
                   .addHeader("content-type", "application/x-www-form-urlencoded")
                   .addHeader("cache-control", "no-cache")
                   .addHeader("cookie", cookie)
                   .build();
            
            Response response = client.newCall(request).execute();
            String bodyStr = response.body().string();
            
            if(bodyStr != null){
            	Html html = Html.create(bodyStr);
            	Document document = html.getDocument();
            	
            	// ??????????????????
            	int pageMax = 1;
            	Elements textCenters = document.getElementsByClass("text-center");
            	if(textCenters != null && textCenters.size() > 0){
            		Element textCenter = textCenters.get(0);
            		// Elements pageDivs = textCenter.getElementsByClass("pagination inlineblock");
            		Elements pageNums = textCenter.getElementsByTag("a");
            		if(pageNums != null && pageNums.size() > 0){
            			Element pageNum = pageNums.get(pageNums.size()-1);
            			String href = pageNum.attr("href");
            			if(href.indexOf("javascript:fGoPage") >= 0){
            				pageMax = ToolsNumber.parseInt(href.substring(19, href.length()-1));
            			}
            		}
            	}
            	
            	// ?????????
            	for(int p=1; p<=pageMax; p++){
            		String url2 = CNR_URL.replace("{startDay}", VstTools.parseInt(startDay)+"")
            				.replace("{endDay}", VstTools.parseInt(endDay)+"")
            				.replace("{pageNo}", p+"");
            		MediaType mediaType2 = MediaType.parse("application/json");
                    RequestBody body2 = RequestBody.create(mediaType2, "");
                    okhttp3.Request request2 = new okhttp3.Request.Builder()
                           .url(url2)
                           .post(body2)
                           .addHeader("content-type", "application/x-www-form-urlencoded")
                           .addHeader("cache-control", "no-cache")
                           .addHeader("cookie", cookie)
                           .build();
                    
                    Response response2 = client.newCall(request2).execute();
                    String bodyStr2 = response2.body().string();
                    
                    if(bodyStr2 != null){
                    	Html html2 = Html.create(bodyStr2);
                    	Document document2 = html2.getDocument();
            		
		            	Elements tabs = document2.getElementsByTag("table");
		            	if(tabs != null && tabs.size() > 0){
		            		Element tab = tabs.get(2);
		            		
		            		Elements trs = tab.getElementsByTag("tr");
		            		if(trs != null && trs.size() > 2){
		            			List<VstShoppingPlayOrder> orders = new ArrayList<VstShoppingPlayOrder>();
								Long now = System.currentTimeMillis();
								
								//??????????????????
								for(int i=1; i<trs.size()-1; i++){
		            				Element tr = trs.get(i);
		            				Elements tds = tr.getElementsByTag("td");
		            				
		            				VstShoppingPlayOrder bean = new VstShoppingPlayOrder();
		            				//??????
		            				String orderDate = tds.get(10).text();
		            				if(orderDate.length() >= 18){
		            					orderDate = orderDate.substring(0, 18);
		            				}
									bean.setVst_shopping_play_order_date(ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm_ss, orderDate));
									//??????
									bean.setVst_shopping_play_order_good_id(tds.get(5).text());
									bean.setVst_shopping_play_order_good_name(tds.get(7).text());
									//??????
									bean.setVst_shopping_play_order_good_price(ToolsNumber.parseDouble(tds.get(9).text()));
									bean.setVst_shopping_play_order_qty(1);
									bean.setVst_shopping_play_order_sale_price(ToolsNumber.parseDouble(tds.get(9).text()));
									//??????
									bean.setVst_shopping_play_order_address(tds.get(3).text());
									bean.setVst_shopping_play_order_province(tds.get(1).text());
									//?????????
									bean.setVst_shopping_play_order_recipient("");
									bean.setVst_shopping_play_order_phone("");
									//?????????
									bean.setVst_shopping_play_order_supplier_type("");
									bean.setVst_shopping_play_order_channel("cnrmall");
									bean.setVst_shopping_play_order_addtime(now);
									bean.setVst_shopping_play_order_uptime(now);
									bean.setVst_shopping_play_order_operator(user.getLoginInfo().getLoginName());
									
									orders.add(bean);
		            			}
								
								int count = _vstShoppingPlayOrderDao.batchInsert(orders);
								System.out.println("???????????????????????????" + count);
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
	 * ????????????????????????
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int graspJiayouData(Map<String, Object> params, UserSession user){
		int result = 0;
		try{
			String startDay = ToolsString.checkEmpty(params.get("startDay"));
			String endDay = ToolsString.checkEmpty(params.get("endDay"));
			String cookie = ToolsString.checkEmpty(params.get("cookie"));
			
			Map<String, String> header = new HashMap<String, String>();
			header.put("Host", "peisong.jiayougo.cn");
			header.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0");
			header.put("Accept", "*/*");
			header.put("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
			header.put("Accept-Encoding", "gzip, deflate");
			header.put("X-Requested-With", "XMLHttpRequest");
			header.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			header.put("Referer", "http://peisong.jiayougo.cn/lis/partner/cmp_info3.jsp");
			header.put("Content-Length", "159");
			header.put("Cookie", cookie);
			header.put("Connection", "keep-alive");

			String postBody = "startDate={startDay} 00:00:00&endDate={endDay} 23:59:59&start=0&limit=100&ORD_APPR_SURE=S&is_thq=N&time_type=order_date".replace("{startDay}", startDay).replace("{endDay}", endDay);
			
			String jsonUrl = ToolsHttp.httpPost(JIAYOU_URL, header, postBody);
			if(!ToolsString.isEmpty(jsonUrl)){
				JSONObject urlJson = JSONObject.parseObject(jsonUrl);
				int total = urlJson.getIntValue("total");
				if(total > 0) {
					JSONArray orders = urlJson.getJSONArray("data");
					if (orders != null && orders.size() > 0) {
						List<VstShoppingPlayOrder> orderList = new ArrayList<VstShoppingPlayOrder>();
						Long now = System.currentTimeMillis();

						//??????????????????????????????
						for (int i = 0; i < total; i++) {
							JSONObject order = orders.getJSONObject(i);
							VstShoppingPlayOrder bean = new VstShoppingPlayOrder();
							//??????
							bean.setVst_shopping_play_order_date(ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm_ss, order.getString("ETR_DATE")));
							//??????
							bean.setVst_shopping_play_order_good_id(order.getString("GOOD_ID"));
							bean.setVst_shopping_play_order_good_name(order.getString("GOOD_NM"));
							//??????
							bean.setVst_shopping_play_order_good_price(order.getDouble("PRC"));
							bean.setVst_shopping_play_order_qty(order.getInteger("ORD_QTY"));
							bean.setVst_shopping_play_order_sale_price(order.getDouble("SETTLE_AMT"));
							//??????
							bean.setVst_shopping_play_order_address(order.getString("ADDR_1"));
							bean.setVst_shopping_play_order_province(order.getString("LRGN_CD"));
							//?????????
							bean.setVst_shopping_play_order_recipient(order.getString("CUST_NM"));
							bean.setVst_shopping_play_order_phone("");
							//?????????
							bean.setVst_shopping_play_order_supplier_type("");
							bean.setVst_shopping_play_order_channel("jiayou");
							bean.setVst_shopping_play_order_addtime(now);
							bean.setVst_shopping_play_order_uptime(now);
							bean.setVst_shopping_play_order_operator(user.getLoginInfo().getLoginName());

							orderList.add(bean);
						}

						if (orders.size() > 0) {
							int count = _vstShoppingPlayOrderDao.batchInsert(orderList);
							System.out.println("???????????????????????????" + count);
							result += count;
						}
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * ??????????????????
	 */
	@Override
	public int deletePlayOrder(Map<String, Object> params, UserSession user) throws SystemException {
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
			
			List<Map<String, Object>> data = _vstShoppingPlayOrderDao.queryForList(params);
			if(data != null && data.size() > 0){
				//???????????????ID
				List<Integer> ids = new ArrayList<Integer>();
				for(Map<String, Object> map : data){
					ids.add(ToolsNumber.parseInt(map.get("vst_pk_id")+""));
				}
				
				Map<String, Object> param = new HashMap<String, Object>(1);
				param.put("list_ids", ids);
				result = _vstShoppingPlayOrderDao.delete(param);
			}
			
			if(result > 0){
				// ???????????????
				StringBuilder sb = new StringBuilder();
				sb.append("??????????????????, ?????????").append(params);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_DEL, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("????????????????????????: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * ??????????????????(???????????????)
	 */
	@Override
	public Map<String, Object> getPlayOrderReportByChannel(Map<String, Object> params) throws SystemException {
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
			
			List<Map<String, Object>> list = _vstShoppingPlayOrderDao.queryReportByDate(params);
			if(list != null && list.size() > 0){
				Map<String, List<JSONObject>> dataMap = new HashMap<String, List<JSONObject>>();
				
				for(Map<String, Object> map : list){
					String channel = map.get("channel")+"";
					if(dataMap.containsKey(channel)){
						String date = ToolsDate.formatDate(ToolsDate.HH, map.get("date")+"");
						int hour = ToolsNumber.parseInt(date);
						double salePrice = ToolsNumber.parseDouble(map.get("salePrice")+"");
						// ??????????????????
						List<JSONObject> hours = dataMap.get(channel);
						JSONObject temp = hours.get(hour);
						temp.put("num", temp.getIntValue("num") + 1);
						temp.put("price", salePrice + temp.getDoubleValue("price"));
					}else{
						String date = ToolsDate.formatDate(ToolsDate.HH, map.get("date")+"");
						int hour = ToolsNumber.parseInt(date);
						double salePrice = ToolsNumber.parseDouble(map.get("salePrice")+"");
						// ??????????????????
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
					// ??????????????????
					Map<String, Object> temp = new HashMap<String, Object>(3);
					temp.put("vst_table_name", "vst_shopping_play_order");
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
			logger.error("??????????????????(???????????????)??????: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return null;
	}
	
	/**
	 * ??????????????????
	 * @param httpResponse
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	private static String getResponseContent(HttpResponse httpResponse) throws ParseException, IOException {
		return EntityUtils.toString(httpResponse.getEntity());
	}
	
	/**
	 * ??????????????????
	 * @param parameterMap
	 * @return
	 */
	private static List<NameValuePair> getParam(Map<String,String> parameterMap) {
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		Iterator<Entry<String, String>> it = parameterMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> parmEntry =  it.next();
			param.add(new BasicNameValuePair((String) parmEntry.getKey(),
					(String) parmEntry.getValue()));
		}
		return param;
	}
}
