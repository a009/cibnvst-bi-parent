package com.vst.defend.controller.umeng;

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
import com.vst.defend.service.umeng.VstUmengEventMessageService;

/**
 * 友盟-事件消息统计
 * @author lhp
 * @date 2018-3-15 下午12:27:21
 * @version
 */
@Scope("prototype")
@Controller
@RequestMapping("/umengEventMessage")
public class VstUmengEventMessageController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = VstUmengEventMessageController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 事件消息Service接口
	 */
	@Resource
	private VstUmengEventMessageService _vstUmengEventMessageService;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询事件消息统计
	 * @return
	 */
	@RequestMapping("/findUmengEventMessage.action")
	public String findUmengEventMessage(){
		try {
			// 初始化
			this.initializeAction(_className);
			
			Object obj = session.getAttribute("cutPage_umengEventMessage");
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
			
			// 获取表字段
			Map<String, String> columns = new LinkedHashMap<String, String>();
			columns.put("vst_uem_date", "日期");
			columns.put("vst_uem_event_name", "事件名称");
			columns.put("vst_uem_param_type", "参数类型");
			columns.put("vst_uem_param_value", "参数值");
			columns.put("vst_uem_radio", "占比");
			columns.put("vst_uem_message_num", "消息数量");
			request.setAttribute("columns", columns);
			// 获取表描述
			request.setAttribute("tableDesc", _baseService.getTableDesc("vst_umeng_event_message"));
		} catch (Exception e) {
			logger.error("findUmengEventMessage error." + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_umengEventMessage");
		}
		
		return "umeng/umengEventMessage_list";
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
			queryParam.put("startDay", ToolsString.checkEmpty(request.getParameter("startDay")));
			queryParam.put("endDay", ToolsString.checkEmpty(request.getParameter("endDay")));
			queryParam.put("vst_uem_event_name", ToolsString.checkEmpty(request.getParameter("vst_uem_event_name")));
			queryParam.put("vst_uem_param_type", ToolsString.checkEmpty(request.getParameter("vst_uem_param_type")));
			queryParam.put("vst_uem_param_value", ToolsString.checkEmpty(request.getParameter("vst_uem_param_value")));
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
			
			ReportBean bean = _vstUmengEventMessageService.getCutPageData(cutPage);
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
			queryParam.put("startDay", ToolsString.checkEmpty(request.getParameter("startDay")));
			queryParam.put("endDay", ToolsString.checkEmpty(request.getParameter("endDay")));
			queryParam.put("vst_uem_event_name", ToolsString.checkEmpty(request.getParameter("vst_uem_event_name")));
			queryParam.put("vst_uem_param_type", ToolsString.checkEmpty(request.getParameter("vst_uem_param_type")));
			queryParam.put("vst_uem_param_value", ToolsString.checkEmpty(request.getParameter("vst_uem_param_value")));
			queryParam.put("export_column", ToolsString.checkEmpty(request.getParameter("export_column")));
			// 编码转码
			VstTools.decodeToUTF_8(queryParam);
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			ReportBean bean = _vstUmengEventMessageService.getExportData(queryParam, getUserSession(moduleId));
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
			
		} catch (Exception e) {
			logger.error("toGrasp error." + SystemException.getExceptionInfo(e));
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");// 转向失败页面
		}
		return "umeng/umengEventMessage_grasp";
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
			params.put("eventName", ToolsString.checkEmpty(request.getParameter("eventName")));
			params.put("cookie", ToolsString.checkEmpty(request.getParameter("cookie")));
			// 传递页面参数
			request.setAttribute("queryParam", params);
			
			result = _vstUmengEventMessageService.graspEventMessageData(params);
			
		} catch (Exception e) {
			logger.error("graspData error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "抓取数据失败！", toGrasp());
		}
		return this.moveToPage(VstConstants.OPERATE_SUCCESS, "抓取数据成功"+result+"条！", toGrasp());
	}
	
	/**
	 * 删除事件消息
	 * @return
	 */
	@RequestMapping("/deleteEventMessage.action")
	public String deleteEventMessage(){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			
			// 获取查询条件
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("startDay", ToolsString.checkEmpty(request.getParameter("startDay")));
			params.put("endDay", ToolsString.checkEmpty(request.getParameter("endDay")));
			params.put("vst_uem_event_name", ToolsString.checkEmpty(request.getParameter("vst_uem_event_name")));
			params.put("vst_uem_param_type", ToolsString.checkEmpty(request.getParameter("vst_uem_param_type")));
			params.put("vst_uem_param_value", ToolsString.checkEmpty(request.getParameter("vst_uem_param_value")));
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _vstUmengEventMessageService.deleteEventMessage(params, getUserSession(moduleId));
			
			// 转向成功页面
			return this.moveToPage(VstConstants.OPERATE_SUCCESS, "删除事件消息成功"+result+"条！", findUmengEventMessage());
		} catch (Exception e) {
			logger.error("deleteEventMessage error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "删除事件消息失败！", findUmengEventMessage());
		}
	}
	
	/**
	 * 查询汇总数据
	 * @return
	 */
	@RequestMapping("/ajaxGetCutPageSum.action")
	public String ajaxGetCutPageSum(){
		try{
			this.initializeAction(_className);
			
			CutPage cutPage = new CutPage();
			// 查询条件
			Map<String, String> queryParam = new HashMap<String, String>();
			queryParam.put("startDay", ToolsString.checkEmpty(request.getParameter("startDay")));
			queryParam.put("endDay", ToolsString.checkEmpty(request.getParameter("endDay")));
			queryParam.put("vst_uem_event_name", ToolsString.checkEmpty(request.getParameter("vst_uem_event_name")));
			queryParam.put("vst_uem_param_type", ToolsString.checkEmpty(request.getParameter("vst_uem_param_type")));
			queryParam.put("vst_uem_param_value", ToolsString.checkEmpty(request.getParameter("vst_uem_param_value")));
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
			
			ReportBean bean = _vstUmengEventMessageService.getCutPageDataSum(cutPage);
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
			logger.error("ajaxGetCutPageSum error." + SystemException.getExceptionInfo(e));
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");// 转向失败页面
		}
		return null;
	}
	
	/**
	 * 导出汇总数据
	 * @return
	 */
	@RequestMapping("/exportDataSum.action")
	public String exportDataSum(){
		try{
			this.initializeAction(_className);
			
			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("startDay", ToolsString.checkEmpty(request.getParameter("startDay")));
			queryParam.put("endDay", ToolsString.checkEmpty(request.getParameter("endDay")));
			queryParam.put("vst_uem_event_name", ToolsString.checkEmpty(request.getParameter("vst_uem_event_name")));
			queryParam.put("vst_uem_param_type", ToolsString.checkEmpty(request.getParameter("vst_uem_param_type")));
			queryParam.put("vst_uem_param_value", ToolsString.checkEmpty(request.getParameter("vst_uem_param_value")));
			
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			ReportBean bean = _vstUmengEventMessageService.getExportDataSum(queryParam, getUserSession(moduleId));
			List<Map<String, Object>> data = bean.getMapData();
			File file = ExportUtil.ExportExcel(data, PropertiesReader.getProperty("export_dir"), bean.getTitle());
			if(file.exists()){
				VstTools.fileDownLoad(response, file);
				file.delete();
			}
		}catch(Exception e){
			logger.error("exportDataSum error." + SystemException.getExceptionInfo(e));
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");// 转向失败页面
		}
		return null;
	}
}
