package com.vst.defend.controller.shopping;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.controller.BaseController;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.ErrorInfo;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.CutPage;
import com.vst.defend.communal.util.ExportUtil;
import com.vst.defend.communal.util.PropertiesReader;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.communal.util.VstTools;
import com.vst.defend.service.shopping.VstShoppingLiveOrderService;

/**
 * 全球购直播订单
 * @author lhp
 * @date 2017-11-28 下午03:58:01
 * @version
 */
@Scope("prototype")
@Controller
@RequestMapping("/shoppingLiveOrder")
public class VstShoppingLiveOrderController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = VstShoppingLiveOrderController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 直播订单Service接口
	 */
	@Resource
	private VstShoppingLiveOrderService _vstShoppingLiveOrderService;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询全球购直播订单
	 * @return
	 */
	@RequestMapping("/findShoppingLiveOrder.action")
	public String findShoppingLiveOrder(){
		try {
			// 初始化
			this.initializeAction(_className);
			
			Object obj = session.getAttribute("cutPage_shoppingLiveOrder");
			if(obj != null){
				cutPage = (CutPage) obj;
			}else{
				cutPage = new CutPage();
			}
			
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			
			// 获取页面按钮
			cutPage.set_buttonList(getPageButtons(moduleId));
			// 转发查询到的数据到页面
			request.setAttribute("cutPage", cutPage);
			// 转发当前模块ID
			request.setAttribute("moduleId", moduleId);
			
			// 获取商品渠道
			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("vst_table_name", "vst_shopping_live_order");
			params.put("vst_column_name", "channel");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("channels", _baseService.getDictionaryForMap(params));
			// 获取对账状态
			params.put("vst_table_name", "vst_shopping_live_order");
			params.put("vst_column_name", "balanceStatus");
			request.setAttribute("balanceStatus", _baseService.getDictionaryForMap(params));
			// 获取物流状态
			params.put("vst_table_name", "vst_shopping_live_order");
			params.put("vst_column_name", "logisticsStatus");
			request.setAttribute("logisticsStatus", _baseService.getDictionaryForMap(params));
			
			// 获取表字段
			Map<String, String> columns = new LinkedHashMap<String, String>();
			columns.put("vst_shopping_live_order_date", "日期");
			columns.put("vst_shopping_live_order_channel", "渠道");
			columns.put("vst_shopping_live_order_number", "订单号");
			columns.put("vst_shopping_live_order_goods_number", "商品编号");
			columns.put("vst_shopping_live_order_goods_category", "商品分类");
			columns.put("vst_shopping_live_order_goods_name", "商品名称");
			columns.put("vst_shopping_live_order_goods_price", "商品价格");
			columns.put("vst_shopping_live_order_qty", "购买数量");
			columns.put("vst_shopping_live_order_sale_price", "销售金额");
			columns.put("vst_shopping_live_order_province", "省份");
			columns.put("vst_shopping_live_order_address", "收件地址");
			columns.put("vst_shopping_live_order_recipient", "收件人");
			columns.put("vst_shopping_live_order_phone", "联系电话");
			columns.put("vst_shopping_live_order_source", "商品来源");
			columns.put("vst_shopping_live_order_balance_status", "对账状态");
			columns.put("vst_shopping_live_order_logistics_status", "物流状态");
			columns.put("vst_shopping_live_order_create_time", "创建时间");
			columns.put("vst_shopping_live_order_balance_time", "对账时间");
			request.setAttribute("columns", columns);
			// 获取表描述
			request.setAttribute("tableDesc", _baseService.getTableDesc("vst_shopping_live_order"));
		} catch (Exception e) {
			logger.error("findShoppingLiveOrder error." + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_shoppingLiveOrder");
		}
		
		return "shopping/shoppingLiveOrder_list";
	}
	
	/**
	 * 查询分页数据
	 * @return
	 */
	@RequestMapping("/ajaxGetCutPage.action")
	public String ajaxGetCutPage(){
		try{
			this.initializeAction(_className);
			
			CutPage cutPage = new CutPage();
			// 查询条件
			Map<String, String> queryParam = new HashMap<String, String>();
			queryParam.put("startDate", ToolsString.checkEmpty(request.getParameter("startDate")));
			queryParam.put("endDate", ToolsString.checkEmpty(request.getParameter("endDate")));
			queryParam.put("vst_shopping_live_order_channel", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_channel")));
			queryParam.put("vst_shopping_live_order_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_number")));
			queryParam.put("vst_shopping_live_order_goods_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_number")));
			queryParam.put("vst_shopping_live_order_goods_category", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_category")));
			queryParam.put("vst_shopping_live_order_goods_name", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_name")));
			queryParam.put("vst_shopping_live_order_province", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_province")));
			queryParam.put("vst_shopping_live_order_address", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_address")));
			queryParam.put("vst_shopping_live_order_recipient", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_recipient")));
			queryParam.put("vst_shopping_live_order_phone", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_phone")));
			queryParam.put("vst_shopping_live_order_balance_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_balance_status")));
			queryParam.put("vst_shopping_live_order_logistics_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_logistics_status")));
			queryParam.put("vst_shopping_live_order_operator", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_operator")));
			// 排序信息
			String orderBy = ToolsString.checkEmpty(request.getParameter("orderBy"));
			String order = ToolsString.checkEmpty(request.getParameter("order"));
			queryParam.put("orderBy", orderBy);
			queryParam.put("order", order);
			cutPage.set_queryParam(queryParam);
			// 分页信息
			int currPage = ToolsNumber.parseInt(request.getParameter("currPage"));
			int singleCount = ToolsNumber.parseInt(request.getParameter("singleCount"));
			if(currPage != -1){
				cutPage.set_currPage(currPage);
			}
			if(singleCount != -1){
				cutPage.set_singleCount(singleCount);
			}
			
			ReportBean bean = _vstShoppingLiveOrderService.getLiveOrderCutPage(cutPage);
			List<Map<String, Object>> dataList = bean.getMapData();
			JSONObject json = new JSONObject();
			if(dataList != null && !dataList.isEmpty()){
				json.put("result", "success");
				json.put("data", dataList);
				json.put("singleSize", bean.getSingleSize());
				json.put("totalSize", bean.getTotalSize());
			}else{
				json.put("result", "empty");
			}
			printJson(json.toString());
		}catch(Exception e){
			logger.error("ajaxGetCutPage error." + SystemException.getExceptionInfo(e));
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");// 转向失败页面
		}
		return null;
	}
	
	/**
	 * 导出数据
	 * @return
	 */
	@RequestMapping("/exportData.action")
	public String exportData(){
		try{
			this.initializeAction(_className);
			
			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("startDate", ToolsString.checkEmpty(request.getParameter("startDate")));
			queryParam.put("endDate", ToolsString.checkEmpty(request.getParameter("endDate")));
			queryParam.put("vst_shopping_live_order_channel", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_channel")));
			queryParam.put("vst_shopping_live_order_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_number")));
			queryParam.put("vst_shopping_live_order_goods_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_number")));
			queryParam.put("vst_shopping_live_order_goods_category", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_category")));
			queryParam.put("vst_shopping_live_order_goods_name", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_name")));
			queryParam.put("vst_shopping_live_order_province", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_province")));
			queryParam.put("vst_shopping_live_order_address", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_address")));
			queryParam.put("vst_shopping_live_order_recipient", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_recipient")));
			queryParam.put("vst_shopping_live_order_phone", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_phone")));
			queryParam.put("vst_shopping_live_order_balance_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_balance_status")));
			queryParam.put("vst_shopping_live_order_logistics_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_logistics_status")));
			queryParam.put("vst_shopping_live_order_operator", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_operator")));
			queryParam.put("export_column", ToolsString.checkEmpty(request.getParameter("export_column")));
			// 编码转码
			VstTools.decodeToUTF_8(queryParam);
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			ReportBean bean = _vstShoppingLiveOrderService.getExportData(queryParam, getUserSession(moduleId));
			List<Map<String, Object>> data = bean.getMapData();
			File file = ExportUtil.ExportExcel(data, PropertiesReader.getProperty("export_dir"), bean.getTitle());
			if(file.exists()){
				VstTools.fileDownLoad(response, file);
				file.delete();
			}
		}catch(Exception e){
			logger.error("exportData error." + SystemException.getExceptionInfo(e));
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");// 转向失败页面
		}
		return null;
	}
	
	/**
	 * 根据日期统计
	 * @return
	 */
	@RequestMapping("/ajaxGetReportByDate.action")
	public String ajaxGetReportByDate(){
		try{
			this.initializeAction(_className);
			
			Map<String, Object> queryParam = new HashMap<String, Object>();
			String startDate = ToolsString.checkEmpty(request.getParameter("startDate"));
			String endDate = ToolsString.checkEmpty(request.getParameter("endDate"));
			queryParam.put("startDate", startDate);
			queryParam.put("endDate", endDate);
			queryParam.put("vst_shopping_live_order_channel", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_channel")));
			queryParam.put("vst_shopping_live_order_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_number")));
			queryParam.put("vst_shopping_live_order_goods_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_number")));
			queryParam.put("vst_shopping_live_order_goods_category", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_category")));
			queryParam.put("vst_shopping_live_order_goods_name", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_name")));
			queryParam.put("vst_shopping_live_order_province", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_province")));
			queryParam.put("vst_shopping_live_order_address", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_address")));
			queryParam.put("vst_shopping_live_order_recipient", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_recipient")));
			queryParam.put("vst_shopping_live_order_phone", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_phone")));
			queryParam.put("vst_shopping_live_order_balance_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_balance_status")));
			queryParam.put("vst_shopping_live_order_logistics_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_logistics_status")));
			queryParam.put("vst_shopping_live_order_operator", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_operator")));
			
			// 按天统计
			ReportBean result = _vstShoppingLiveOrderService.getLiveOrderReportByDay(queryParam);
			// 按小时统计
			queryParam.put("startDate", startDate);
			queryParam.put("endDate", endDate);
			ReportBean result2 = _vstShoppingLiveOrderService.getLiveOrderReportByHour(queryParam);
			
			List<JSONObject> data = result.getData();
			List<JSONObject> data2 = result2.getData();
			
			JSONObject json = new JSONObject();
			if(data != null && !data.isEmpty()){
				json.put("result", "success");
				json.put("data", data);
				json.put("data2", data2);
			}else{
				json.put("result", "empty");
			}
			printJson(json.toString());
		}catch(Exception e){
			logger.error("ajaxGetReportByDate error. ERROR:" + e.getMessage());
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		}
		return null;
	}
	
	/**
	 * 统计总量(根据商品ID、名称)
	 * @return
	 */
	@RequestMapping("/ajaxGetReportDataByGood.action")
	public String ajaxGetReportDataByGood(){
		try {
			this.initializeAction(_className);
			
			CutPage cutPage = new CutPage();
			// 查询条件
			Map<String, String> queryParam = new HashMap<String, String>();
			queryParam.put("startDate", ToolsString.checkEmpty(request.getParameter("startDate")));
			queryParam.put("endDate", ToolsString.checkEmpty(request.getParameter("endDate")));
			queryParam.put("vst_shopping_live_order_channel", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_channel")));
			queryParam.put("vst_shopping_live_order_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_number")));
			queryParam.put("vst_shopping_live_order_goods_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_number")));
			queryParam.put("vst_shopping_live_order_goods_category", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_category")));
			queryParam.put("vst_shopping_live_order_goods_name", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_name")));
			queryParam.put("vst_shopping_live_order_province", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_province")));
			queryParam.put("vst_shopping_live_order_address", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_address")));
			queryParam.put("vst_shopping_live_order_recipient", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_recipient")));
			queryParam.put("vst_shopping_live_order_phone", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_phone")));
			queryParam.put("vst_shopping_live_order_balance_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_balance_status")));
			queryParam.put("vst_shopping_live_order_logistics_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_logistics_status")));
			queryParam.put("vst_shopping_live_order_operator", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_operator")));
			// 排序信息
			String orderBy = ToolsString.checkEmpty(request.getParameter("orderBy"));
			String order = ToolsString.checkEmpty(request.getParameter("order"));
			queryParam.put("orderBy", orderBy);
			queryParam.put("order", order);
			cutPage.set_queryParam(queryParam);
			// 分页信息
			int currPage = ToolsNumber.parseInt(request.getParameter("currPage"));
			int singleCount = ToolsNumber.parseInt(request.getParameter("singleCount"));
			if(currPage != -1){
				cutPage.set_currPage(currPage);
			}
			if(singleCount != -1){
				cutPage.set_singleCount(singleCount);
			}
			
			ReportBean bean = _vstShoppingLiveOrderService.getLiveOrderReportByGood(cutPage);
			List<Map<String, Object>> dataList = bean.getMapData();
			JSONObject json = new JSONObject();
			if(dataList != null && !dataList.isEmpty()){
				json.put("result", "success");
				json.put("data", dataList);
				json.put("singleSize", bean.getSingleSize());
				json.put("totalSize", bean.getTotalSize());
			}else{
				json.put("result", "empty");
			}
			printJson(json.toString());
		} catch (Exception e) {
			logger.error("ajaxGetReportDataByGood error. ERROR:" + e.getMessage());
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");// 转向失败页面
		}
		return null;
	}
	
	/**
	 * 导出总量(根据商品ID、名称)
	 * @return
	 */
	@RequestMapping("/exportSumDataByGood.action")
	public String exportSumDataByGood(){
		try{
			this.initializeAction(_className);
			
			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("startDate", ToolsString.checkEmpty(request.getParameter("startDate")));
			queryParam.put("endDate", ToolsString.checkEmpty(request.getParameter("endDate")));
			queryParam.put("vst_shopping_live_order_channel", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_channel")));
			queryParam.put("vst_shopping_live_order_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_number")));
			queryParam.put("vst_shopping_live_order_goods_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_number")));
			queryParam.put("vst_shopping_live_order_goods_category", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_category")));
			queryParam.put("vst_shopping_live_order_goods_name", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_name")));
			queryParam.put("vst_shopping_live_order_province", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_province")));
			queryParam.put("vst_shopping_live_order_address", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_address")));
			queryParam.put("vst_shopping_live_order_recipient", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_recipient")));
			queryParam.put("vst_shopping_live_order_phone", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_phone")));
			queryParam.put("vst_shopping_live_order_balance_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_balance_status")));
			queryParam.put("vst_shopping_live_order_logistics_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_logistics_status")));
			queryParam.put("vst_shopping_live_order_operator", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_operator")));
			
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			ReportBean bean = _vstShoppingLiveOrderService.getLiveOrderExportByGood(queryParam, getUserSession(moduleId));
			List<Map<String, Object>> data = bean.getMapData();
			File file = ExportUtil.ExportExcel(data, PropertiesReader.getProperty("export_dir"), bean.getTitle());
			if(file.exists()){
				VstTools.fileDownLoad(response, file);
				file.delete();
			}
		}catch(Exception e){
			logger.error("exportSumDataByGood error." + SystemException.getExceptionInfo(e));
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");// 转向失败页面
		}
		return null;
	}
	
	
	/**
	 * 查询直播订单汇总
	 * @return
	 */
	@RequestMapping("/findShoppingLiveOrderSum.action")
	public String findShoppingLiveOrderSum(){
		try {
			// 初始化
			this.initializeAction(_className);
			
			Object obj = session.getAttribute("cutPage_shoppingLiveOrderSum");
			if(obj != null){
				cutPage = (CutPage) obj;
			}else{
				cutPage = new CutPage();
			}
			
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			
			// 获取页面按钮
			cutPage.set_buttonList(getPageButtons(moduleId));
			// 转发查询到的数据到页面
			request.setAttribute("cutPage", cutPage);
			// 转发当前模块ID
			request.setAttribute("moduleId", moduleId);
			
			// 获取商品渠道
			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("vst_table_name", "vst_shopping_live_order");
			params.put("vst_column_name", "channel");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("channels", _baseService.getDictionaryForMap(params));
			// 获取对账状态
			params.put("vst_table_name", "vst_shopping_live_order");
			params.put("vst_column_name", "balanceStatus");
			request.setAttribute("balanceStatus", _baseService.getDictionaryForMap(params));
			// 获取物流状态
			params.put("vst_table_name", "vst_shopping_live_order");
			params.put("vst_column_name", "logisticsStatus");
			request.setAttribute("logisticsStatus", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("findShoppingLiveOrderSum error." + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_shoppingLiveOrderSum");
		}
		
		return "shopping/shoppingLiveOrderSum_list";
	}
	
	/**
	 * 查询表格数据(按月统计汇总)
	 * @return
	 */
	@RequestMapping("/ajaxGetSumCutPage.action")
	public String ajaxGetSumCutPage(){
		try{
			this.initializeAction(_className);
			
			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("startDate", ToolsString.checkEmpty(request.getParameter("startDate")));
			queryParam.put("endDate", ToolsString.checkEmpty(request.getParameter("endDate")));
			queryParam.put("vst_shopping_live_order_channel", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_channel")));
			queryParam.put("vst_shopping_live_order_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_number")));
			queryParam.put("vst_shopping_live_order_goods_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_number")));
			queryParam.put("vst_shopping_live_order_goods_category", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_category")));
			queryParam.put("vst_shopping_live_order_goods_name", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_name")));
			queryParam.put("vst_shopping_live_order_province", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_province")));
			queryParam.put("vst_shopping_live_order_address", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_address")));
			queryParam.put("vst_shopping_live_order_recipient", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_recipient")));
			queryParam.put("vst_shopping_live_order_phone", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_phone")));
			queryParam.put("vst_shopping_live_order_balance_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_balance_status")));
			queryParam.put("vst_shopping_live_order_logistics_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_logistics_status")));
			queryParam.put("vst_shopping_live_order_operator", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_operator")));
			
			ReportBean bean = _vstShoppingLiveOrderService.getLiveOrderSumCutPage(queryParam);
			List<JSONObject> data = bean.getData();
			JSONObject json = new JSONObject();
			if(data != null && !data.isEmpty()){
				json.put("result", "success");
				json.put("data", data);
			}else{
				json.put("result", "empty");
			}
			printJson(json.toString());
		}catch(Exception e){
			logger.error("ajaxGetSumCutPage error." + SystemException.getExceptionInfo(e));
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");// 转向失败页面
		}
		return null;
	}
	
	/**
	 * 统计占比(根据商品分类)
	 * @return
	 */
	@RequestMapping("/ajaxGetReportDataByCategory.action")
	public String ajaxGetReportDataByCategory(){
		try{
			this.initializeAction(_className);
			
			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("startDate", ToolsString.checkEmpty(request.getParameter("startDate")));
			queryParam.put("endDate", ToolsString.checkEmpty(request.getParameter("endDate")));
			queryParam.put("vst_shopping_live_order_channel", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_channel")));
			queryParam.put("vst_shopping_live_order_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_number")));
			queryParam.put("vst_shopping_live_order_goods_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_number")));
			queryParam.put("vst_shopping_live_order_goods_category", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_category")));
			queryParam.put("vst_shopping_live_order_goods_name", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_name")));
			queryParam.put("vst_shopping_live_order_province", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_province")));
			queryParam.put("vst_shopping_live_order_address", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_address")));
			queryParam.put("vst_shopping_live_order_recipient", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_recipient")));
			queryParam.put("vst_shopping_live_order_phone", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_phone")));
			queryParam.put("vst_shopping_live_order_balance_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_balance_status")));
			queryParam.put("vst_shopping_live_order_logistics_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_logistics_status")));
			queryParam.put("vst_shopping_live_order_operator", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_operator")));
			
			ReportBean bean = _vstShoppingLiveOrderService.getLiveOrderReportByCategory(queryParam);
			List<Map<String, Object>> dataList = bean.getMapData();
			JSONObject json = new JSONObject();
			if(dataList != null && !dataList.isEmpty()){
				json.put("result", "success");
				json.put("data", dataList);
			}else{
				json.put("result", "empty");
			}
			printJson(json.toString());
		}catch(Exception e){
			logger.error("ajaxGetReportDataByCategory error. ERROR:" + e.getMessage());
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		}
		return null;
	}
	
	/**
	 * 统计总量(根据来源)
	 * @return
	 */
	@RequestMapping("/ajaxGetReportDataBySource.action")
	public String ajaxGetReportDataBySource(){
		try{
			this.initializeAction(_className);
			
			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("startDate", ToolsString.checkEmpty(request.getParameter("startDate")));
			queryParam.put("endDate", ToolsString.checkEmpty(request.getParameter("endDate")));
			queryParam.put("vst_shopping_live_order_channel", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_channel")));
			queryParam.put("vst_shopping_live_order_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_number")));
			queryParam.put("vst_shopping_live_order_goods_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_number")));
			queryParam.put("vst_shopping_live_order_goods_category", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_category")));
			queryParam.put("vst_shopping_live_order_goods_name", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_name")));
			queryParam.put("vst_shopping_live_order_province", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_province")));
			queryParam.put("vst_shopping_live_order_address", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_address")));
			queryParam.put("vst_shopping_live_order_recipient", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_recipient")));
			queryParam.put("vst_shopping_live_order_phone", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_phone")));
			queryParam.put("vst_shopping_live_order_balance_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_balance_status")));
			queryParam.put("vst_shopping_live_order_logistics_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_logistics_status")));
			queryParam.put("vst_shopping_live_order_operator", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_operator")));
			
			ReportBean bean = _vstShoppingLiveOrderService.getLiveOrderReportBySource(queryParam);
			List<Map<String, Object>> dataList = bean.getMapData();
			JSONObject json = new JSONObject();
			if(dataList != null && !dataList.isEmpty()){
				json.put("result", "success");
				json.put("data", dataList);
			}else{
				json.put("result", "empty");
			}
			printJson(json.toString());
		}catch(Exception e){
			logger.error("ajaxGetReportDataBySource error. ERROR:" + e.getMessage());
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		}
		return null;
	}
	
	/**
	 * 统计总量（根据省份）
	 * @return
	 */
	@RequestMapping("/ajaxGetReportDataByProvince.action")
	public String ajaxGetReportDataByProvince(){
		try{
			this.initializeAction(_className);
			
			CutPage cutPage = new CutPage();
			// 查询条件
			Map<String, String> queryParam = new HashMap<String, String>();
			queryParam.put("startDate", ToolsString.checkEmpty(request.getParameter("startDate")));
			queryParam.put("endDate", ToolsString.checkEmpty(request.getParameter("endDate")));
			queryParam.put("vst_shopping_live_order_channel", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_channel")));
			queryParam.put("vst_shopping_live_order_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_number")));
			queryParam.put("vst_shopping_live_order_goods_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_number")));
			queryParam.put("vst_shopping_live_order_goods_category", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_category")));
			queryParam.put("vst_shopping_live_order_goods_name", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_name")));
			queryParam.put("vst_shopping_live_order_province", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_province")));
			queryParam.put("vst_shopping_live_order_address", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_address")));
			queryParam.put("vst_shopping_live_order_recipient", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_recipient")));
			queryParam.put("vst_shopping_live_order_phone", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_phone")));
			queryParam.put("vst_shopping_live_order_balance_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_balance_status")));
			queryParam.put("vst_shopping_live_order_logistics_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_logistics_status")));
			queryParam.put("vst_shopping_live_order_operator", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_operator")));
			// 排序信息
			String orderBy = ToolsString.checkEmpty(request.getParameter("orderBy"));
			String order = ToolsString.checkEmpty(request.getParameter("order"));
			queryParam.put("orderBy", orderBy);
			queryParam.put("order", order);
			cutPage.set_queryParam(queryParam);
			// 分页信息
			int currPage = ToolsNumber.parseInt(request.getParameter("currPage"));
			int singleCount = ToolsNumber.parseInt(request.getParameter("singleCount"));
			if(currPage != -1){
				cutPage.set_currPage(currPage);
			}
			if(singleCount != -1){
				cutPage.set_singleCount(singleCount);
			}
			
			ReportBean bean = _vstShoppingLiveOrderService.getLiveOrderReportByProvince(cutPage);
			List<Map<String, Object>> dataList = bean.getMapData();
			JSONObject json = new JSONObject();
			if(dataList != null && !dataList.isEmpty()){
				json.put("result", "success");
				json.put("data", dataList);
				json.put("singleSize", bean.getSingleSize());
				json.put("totalSize", bean.getTotalSize());
			}else{
				json.put("result", "empty");
			}
			printJson(json.toString());
		}catch(Exception e){
			logger.error("ajaxGetReportDataByProvince error. ERROR:" + e.getMessage());
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		}
		return null;
	}
	
	/**
	 * 导出总量（根据省份）
	 * @return
	 */
	@RequestMapping("/exportSumDataByProvince.action")
	public String exportSumDataByProvince(){		
		try{
			this.initializeAction(_className);
			
			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("startDate", ToolsString.checkEmpty(request.getParameter("startDate")));
			queryParam.put("endDate", ToolsString.checkEmpty(request.getParameter("endDate")));
			queryParam.put("vst_shopping_live_order_channel", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_channel")));
			queryParam.put("vst_shopping_live_order_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_number")));
			queryParam.put("vst_shopping_live_order_goods_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_number")));
			queryParam.put("vst_shopping_live_order_goods_category", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_category")));
			queryParam.put("vst_shopping_live_order_goods_name", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_name")));
			queryParam.put("vst_shopping_live_order_province", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_province")));
			queryParam.put("vst_shopping_live_order_address", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_address")));
			queryParam.put("vst_shopping_live_order_recipient", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_recipient")));
			queryParam.put("vst_shopping_live_order_phone", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_phone")));
			queryParam.put("vst_shopping_live_order_balance_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_balance_status")));
			queryParam.put("vst_shopping_live_order_logistics_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_logistics_status")));
			queryParam.put("vst_shopping_live_order_operator", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_operator")));
			
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			ReportBean bean = _vstShoppingLiveOrderService.getLiveOrderExportByProvince(queryParam, getUserSession(moduleId));
			List<Map<String, Object>> data = bean.getMapData();
			File file = ExportUtil.ExportExcel(data, PropertiesReader.getProperty("export_dir"), bean.getTitle());
			if(file.exists()){
				VstTools.fileDownLoad(response, file);
				file.delete();
			}
		}catch(Exception e){
			logger.error("exportSumDataByProvince error." + SystemException.getExceptionInfo(e));
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");// 转向失败页面
		}
		return null;
	}
	
	/**
	 * 统计总量（根据地址）
	 * @return
	 */
	@RequestMapping("/ajaxGetReportDataByAddress.action")
	public String ajaxGetReportDataByAddress(){
		try{
			this.initializeAction(_className);
			
			CutPage cutPage = new CutPage();
			// 查询条件
			Map<String, String> queryParam = new HashMap<String, String>();
			queryParam.put("startDate", ToolsString.checkEmpty(request.getParameter("startDate")));
			queryParam.put("endDate", ToolsString.checkEmpty(request.getParameter("endDate")));
			queryParam.put("vst_shopping_live_order_channel", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_channel")));
			queryParam.put("vst_shopping_live_order_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_number")));
			queryParam.put("vst_shopping_live_order_goods_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_number")));
			queryParam.put("vst_shopping_live_order_goods_category", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_category")));
			queryParam.put("vst_shopping_live_order_goods_name", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_name")));
			queryParam.put("vst_shopping_live_order_province", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_province")));
			queryParam.put("vst_shopping_live_order_address", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_address")));
			queryParam.put("vst_shopping_live_order_recipient", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_recipient")));
			queryParam.put("vst_shopping_live_order_phone", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_phone")));
			queryParam.put("vst_shopping_live_order_balance_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_balance_status")));
			queryParam.put("vst_shopping_live_order_logistics_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_logistics_status")));
			queryParam.put("vst_shopping_live_order_operator", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_operator")));
			// 排序信息
			String orderBy = ToolsString.checkEmpty(request.getParameter("orderBy"));
			String order = ToolsString.checkEmpty(request.getParameter("order"));
			queryParam.put("orderBy", orderBy);
			queryParam.put("order", order);
			cutPage.set_queryParam(queryParam);
			// 分页信息
			int currPage = ToolsNumber.parseInt(request.getParameter("currPage"));
			int singleCount = ToolsNumber.parseInt(request.getParameter("singleCount"));
			if(currPage != -1){
				cutPage.set_currPage(currPage);
			}
			if(singleCount != -1){
				cutPage.set_singleCount(singleCount);
			}
			
			ReportBean bean = _vstShoppingLiveOrderService.getLiveOrderReportByAddress(cutPage);
			List<Map<String, Object>> dataList = bean.getMapData();
			JSONObject json = new JSONObject();
			if(dataList != null && !dataList.isEmpty()){
				json.put("result", "success");
				json.put("data", dataList);
				json.put("singleSize", bean.getSingleSize());
				json.put("totalSize", bean.getTotalSize());
			}else{
				json.put("result", "empty");
			}
			printJson(json.toString());
		}catch(Exception e){
			logger.error("ajaxGetReportDataByAddress error. ERROR:" + e.getMessage());
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		}
		return null;
	}
	
	/**
	 * 导出总量（根据地址）
	 * @return
	 */
	@RequestMapping("/exportSumDataByAddress.action")
	public String exportSumDataByAddress(){		
		try{
			this.initializeAction(_className);
			
			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("startDate", ToolsString.checkEmpty(request.getParameter("startDate")));
			queryParam.put("endDate", ToolsString.checkEmpty(request.getParameter("endDate")));
			queryParam.put("vst_shopping_live_order_channel", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_channel")));
			queryParam.put("vst_shopping_live_order_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_number")));
			queryParam.put("vst_shopping_live_order_goods_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_number")));
			queryParam.put("vst_shopping_live_order_goods_category", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_category")));
			queryParam.put("vst_shopping_live_order_goods_name", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_name")));
			queryParam.put("vst_shopping_live_order_province", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_province")));
			queryParam.put("vst_shopping_live_order_address", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_address")));
			queryParam.put("vst_shopping_live_order_recipient", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_recipient")));
			queryParam.put("vst_shopping_live_order_phone", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_phone")));
			queryParam.put("vst_shopping_live_order_balance_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_balance_status")));
			queryParam.put("vst_shopping_live_order_logistics_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_logistics_status")));
			queryParam.put("vst_shopping_live_order_operator", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_operator")));
			
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			ReportBean bean = _vstShoppingLiveOrderService.getLiveOrderExportByAddress(queryParam, getUserSession(moduleId));
			List<Map<String, Object>> data = bean.getMapData();
			File file = ExportUtil.ExportExcel(data, PropertiesReader.getProperty("export_dir"), bean.getTitle());
			if(file.exists()){
				VstTools.fileDownLoad(response, file);
				file.delete();
			}
		}catch(Exception e){
			logger.error("exportSumDataByAddress error." + SystemException.getExceptionInfo(e));
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");// 转向失败页面
		}
		return null;
	}
	
	/**
	 * 统计总量详情页面
	 * @return
	 */
	@RequestMapping("/findOrderSumDetail.action")
	public String findOrderSumDetail(){
		try {	
			this.initializeAction(_className);
			
			// 获取查询条件
			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("startDate", ToolsString.checkEmpty(request.getParameter("startDate")));
			queryParam.put("endDate", ToolsString.checkEmpty(request.getParameter("endDate")));
			queryParam.put("vst_shopping_live_order_channel", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_channel")));
			queryParam.put("vst_shopping_live_order_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_number")));
			queryParam.put("vst_shopping_live_order_goods_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_number")));
			queryParam.put("vst_shopping_live_order_goods_category", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_category")));
			queryParam.put("vst_shopping_live_order_goods_name", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_name")));
			queryParam.put("vst_shopping_live_order_province", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_province")));
			queryParam.put("vst_shopping_live_order_address", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_address")));
			queryParam.put("vst_shopping_live_order_recipient", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_recipient")));
			queryParam.put("vst_shopping_live_order_phone", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_phone")));
			queryParam.put("vst_shopping_live_order_balance_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_balance_status")));
			queryParam.put("vst_shopping_live_order_logistics_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_logistics_status")));
			queryParam.put("vst_shopping_live_order_operator", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_operator")));
			request.setAttribute("queryParam", queryParam);
			
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			request.setAttribute("moduleId", moduleId);
			
			// 获取商品渠道
			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("vst_table_name", "vst_shopping_live_order");
			params.put("vst_column_name", "channel");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("channels", _baseService.getDictionaryForMap(params));
			// 获取对账状态
			params.put("vst_table_name", "vst_shopping_live_order");
			params.put("vst_column_name", "balanceStatus");
			request.setAttribute("balanceStatus", _baseService.getDictionaryForMap(params));
			// 获取物流状态
			params.put("vst_table_name", "vst_shopping_live_order");
			params.put("vst_column_name", "logisticsStatus");
			request.setAttribute("logisticsStatus", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("findOrderSumDetail error." + SystemException.getExceptionInfo(e));
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");// 转向失败页面
		}
		return "shopping/shoppingLiveOrderSum_detail";
	}
	
	/**
	 * 跳转到抓取
	 * @return
	 */
	@RequestMapping("/toGrasp.action")
	public String toGrasp(){
		try {
			this.initializeAction(_className); // 初始化
			
			// 转发当前模块ID
			request.setAttribute("moduleId", ToolsString.checkEmpty(request.getParameter("moduleId")));
			
			// 传递页面参数
			request.setAttribute("queryParam", request.getAttribute("queryParam"));
			
			// 获取商品渠道
			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("vst_table_name", "vst_shopping_live_order");
			params.put("vst_column_name", "channel");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("channels", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("toGrasp error." + SystemException.getExceptionInfo(e));
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");// 转向失败页面
		}
		return "shopping/shoppingLiveOrder_grasp";
	}
	
	/**
	 * 抓取数据
	 * @return
	 */
	@RequestMapping("/graspData.action")
	public String graspData(){
		int result = 0;
		try {
			// 初始化Action类
			this.initializeAction(_className);
			
			// 获取页面参数
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("startDay", ToolsString.checkEmpty(request.getParameter("startDay")));
			params.put("endDay", ToolsString.checkEmpty(request.getParameter("endDay")));
			String channel = ToolsString.checkEmpty(request.getParameter("channel"));
			params.put("channel", channel);
			params.put("cookie", ToolsString.checkEmpty(request.getParameter("cookie")));
			// 传递页面参数
			request.setAttribute("queryParam", params);
			
			// 判断渠道
			if("globalbuy".equals(channel)){	//环球购物
				result = _vstShoppingLiveOrderService.graspGlobalbuyData(params, getUserSession());
			}else if("ugo".equals(channel)){	//UGO优购物
				result = _vstShoppingLiveOrderService.graspUgoData(params, getUserSession());
			}else if("hailai".equals(channel)){	//海莱购物
				result = _vstShoppingLiveOrderService.graspHailaiData(params, getUserSession());
			}else if("fashion".equals(channel)){	//时尚购物
				result = _vstShoppingLiveOrderService.graspFashionData(params, getUserSession());
			}else if("duolebo".equals(channel)){	//多乐播购物
				result = _vstShoppingLiveOrderService.graspDuoleboData(params, getUserSession());
			}else if("happy".equals(channel)){	//快乐购物
				result = _vstShoppingLiveOrderService.graspHappyData(params, getUserSession());
			}
		} catch (Exception e) {
			logger.error("graspData error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "抓取数据失败！", toGrasp());
		}
		return this.moveToPage(VstConstants.OPERATE_SUCCESS, "抓取数据成功"+result+"条！", toGrasp());
	}
	
	/**
	 * 删除直播订单
	 * @return
	 */
	@RequestMapping("/deleteLiveOrder.action")
	public String deleteLiveOrder(){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			
			// 获取查询条件
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("startDate", ToolsString.checkEmpty(request.getParameter("startDate")));
			params.put("endDate", ToolsString.checkEmpty(request.getParameter("endDate")));
			params.put("vst_shopping_live_order_channel", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_channel")));
			params.put("vst_shopping_live_order_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_number")));
			params.put("vst_shopping_live_order_goods_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_number")));
			params.put("vst_shopping_live_order_goods_category", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_category")));
			params.put("vst_shopping_live_order_goods_name", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_name")));
			params.put("vst_shopping_live_order_province", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_province")));
			params.put("vst_shopping_live_order_address", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_address")));
			params.put("vst_shopping_live_order_recipient", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_recipient")));
			params.put("vst_shopping_live_order_phone", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_phone")));
			params.put("vst_shopping_live_order_balance_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_balance_status")));
			params.put("vst_shopping_live_order_logistics_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_logistics_status")));
			params.put("vst_shopping_live_order_operator", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_operator")));
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _vstShoppingLiveOrderService.deleteLiveOrder(params, getUserSession(moduleId));
			
			// 转向成功页面
			return this.moveToPage(VstConstants.OPERATE_SUCCESS, "删除直播订单成功"+result+"条！", findShoppingLiveOrder());
		} catch (Exception e) {
			logger.error("deleteLiveOrder error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "删除直播订单失败！", findShoppingLiveOrder());
		}
	}
	
	/**
	 * 根据渠道统计
	 * @return
	 */
	@RequestMapping("/ajaxGetReportByChannel.action")
	public String ajaxGetReportByChannel(){
		try{
			this.initializeAction(_className);
			
			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("startDate", ToolsString.checkEmpty(request.getParameter("startDate")));
			queryParam.put("endDate", ToolsString.checkEmpty(request.getParameter("endDate")));
			queryParam.put("vst_shopping_live_order_channel", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_channel")));
			queryParam.put("vst_shopping_live_order_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_number")));
			queryParam.put("vst_shopping_live_order_goods_number", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_number")));
			queryParam.put("vst_shopping_live_order_goods_category", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_category")));
			queryParam.put("vst_shopping_live_order_goods_name", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_goods_name")));
			queryParam.put("vst_shopping_live_order_province", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_province")));
			queryParam.put("vst_shopping_live_order_address", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_address")));
			queryParam.put("vst_shopping_live_order_recipient", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_recipient")));
			queryParam.put("vst_shopping_live_order_phone", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_phone")));
			queryParam.put("vst_shopping_live_order_balance_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_balance_status")));
			queryParam.put("vst_shopping_live_order_logistics_status", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_logistics_status")));
			queryParam.put("vst_shopping_live_order_operator", ToolsString.checkEmpty(request.getParameter("vst_shopping_live_order_operator")));
			
			// 按天统计
			Map<String, Object> data = _vstShoppingLiveOrderService.getLiveOrderReportByChannel(queryParam);
			JSONObject json = new JSONObject();
			if(data != null && !data.isEmpty()){
				json.put("result", "success");
				json.put("data", data);
			}else{
				json.put("result", "empty");
			}
			printJson(json.toString());
		}catch(Exception e){
			logger.error("ajaxGetReportByChannel error. ERROR:" + e.getMessage());
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		}
		return null;
	}
}
