package com.vst.defend.controller.outer;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.tools.http.ToolsHttp;
import com.vst.common.tools.io.ToolsIO;
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
import com.vst.defend.service.outer.InnerUserAddChannelService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 新增渠道用户(对内)
 * @author lhp
 * @date 2018-1-19 下午03:53:51
 * @version
 */
@Scope("prototype")
@Controller
@RequestMapping("/innerUserAddChannel")
public class InnerUserAddChannelController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = InnerUserAddChannelController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 新增渠道用户Service接口
	 */
	@Resource
	private InnerUserAddChannelService _innerUserAddChannelService;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询新增渠道用户统计
	 * @return
	 */
	@RequestMapping("/findInnerUserAddChannel.action")
	public String findInnerUserAddChannel(){
		try {
			// 初始化
			this.initializeAction(_className);
			
			Object obj = session.getAttribute("cutPage_innerUserAddChannel");
			if(obj != null){
				cutPage = (CutPage) obj;
			}else{
				cutPage = new CutPage();
			}
			
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			// 获取包名
			String recordPkg = ToolsString.checkEmpty(request.getParameter("recordPkg"));
			if(ToolsString.isEmpty(recordPkg)){
				if(session.getAttribute("pkgName") != null){
					recordPkg = session.getAttribute("pkgName")+"";
				}else{
					recordPkg = "net.myvst.v2";
				}
			}
			
			// 获取页面按钮
			cutPage.set_buttonList(getPageButtons(moduleId));
			// 转发查询到的数据到页面
			request.setAttribute("cutPage", cutPage);
			// 转发当前模块ID
			request.setAttribute("moduleId", moduleId);
			// 转发包名
			request.setAttribute("recordPkg", recordPkg);
			
			// 获取表描述
			request.setAttribute("tableDesc", _baseService.getTableDesc("outer_vst_user_add_channel"));
		} catch (Exception e) {
			logger.error("findInnerUserAddChannel error." + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_innerUserAddChannel");
		}
		
		return "outer/innerUserAddChannel_list";
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
			queryParam.put("pkgName", ToolsString.checkEmpty(request.getParameter("pkgName")));
			queryParam.put("vst_uac_channel", ToolsString.checkEmpty(request.getParameter("vst_uac_channel")));
			queryParam.put("vst_uac_state", ToolsString.checkEmpty(request.getParameter("vst_uac_state")));
			queryParam.put("vst_uac_updator", ToolsString.checkEmpty(request.getParameter("vst_uac_updator")));
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
			
			ReportBean bean = _innerUserAddChannelService.getCutPageData(cutPage);
			List<Map<String, Object>> dataList = bean.getMapData();
			JSONObject json = new JSONObject();
			if(dataList != null && !dataList.isEmpty()){
				json.put("result", "success");
				json.put("data", dataList);
				json.put("singleSize", bean.getSingleSize());
				json.put("totalSize", bean.getTotalSize());
				json.put("currPage", currPage);
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
			queryParam.put("pkgName", ToolsString.checkEmpty(request.getParameter("pkgName")));
			queryParam.put("vst_uac_channel", ToolsString.checkEmpty(request.getParameter("vst_uac_channel")));
			queryParam.put("vst_uac_state", ToolsString.checkEmpty(request.getParameter("vst_uac_state")));
			queryParam.put("vst_uac_updator", ToolsString.checkEmpty(request.getParameter("vst_uac_updator")));
			
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			ReportBean bean = _innerUserAddChannelService.getExportData(queryParam, getUserSession(moduleId));
			List<Map<String, Object>> data = bean.getMapData();
			File file = ExportUtil.ExportExcel(data, PropertiesReader.getProperty("export_dir"), bean.getTitle().trim());
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
			queryParam.put("startDay", ToolsString.checkEmpty(request.getParameter("startDay")));
			queryParam.put("endDay", ToolsString.checkEmpty(request.getParameter("endDay")));
			queryParam.put("pkgName", ToolsString.checkEmpty(request.getParameter("pkgName")));
			queryParam.put("vst_uac_channel", ToolsString.checkEmpty(request.getParameter("vst_uac_channel")));
			queryParam.put("vst_uac_state", ToolsString.checkEmpty(request.getParameter("vst_uac_state")));
			queryParam.put("vst_uac_updator", ToolsString.checkEmpty(request.getParameter("vst_uac_updator")));
			
			ReportBean result = _innerUserAddChannelService.getReportByDate(queryParam);
			List<Map<String, Object>> data = result.getMapData();
			
			JSONObject json = new JSONObject();
			if(data != null && !data.isEmpty()){
				json.put("result", "success");
				json.put("data", data);
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
	 * 审核数据
	 * @return
	 */
	@RequestMapping("/ajaxAuditData.action")
	public String ajaxAuditData(){
		JSONObject json = new JSONObject();
		try{
			this.initializeAction(_className);
			
			String recordId = request.getParameter("recordId");
			String recordState = request.getParameter("recordState");
			int state = Integer.parseInt(recordState);
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _innerUserAddChannelService.auditData(recordId, state, getUserSession(moduleId));
			
			if(result > 0){
				json.put("code", 100);
			}else{
				json.put("code", 200);
			}
		}catch(Exception e){
			json.put("code", 300);
			logger.error("ajaxAuditData error." + SystemException.getExceptionInfo(e));
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");// 转向失败页面
		}
		printJson(json.toJSONString());
		return null;
	}
	
	/**
	 * 批量审核
	 * @return
	 */
	@RequestMapping("/ajaxBatchAuditData.action")
	public String ajaxBatchAuditData(){
		JSONObject json = new JSONObject();
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String startDay = ToolsString.checkEmpty(request.getParameter("startDay"));
			String endDay = ToolsString.checkEmpty(request.getParameter("endDay"));
			if(ToolsString.isEmpty(startDay) || ToolsString.isEmpty(endDay)){
				// 转向失败页面
				return this.moveToPage(VstConstants.OPERATE_INFO, "必选选择日期才能审核！", findInnerUserAddChannel());
			}
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("startDay", startDay);
			params.put("endDay", endDay);
			params.put("vst_uac_channel", ToolsString.checkEmpty(request.getParameter("vst_uac_channel")));
			params.put("vst_uac_state", ToolsString.checkEmpty(request.getParameter("vst_uac_state")));
			params.put("vst_uac_updator", ToolsString.checkEmpty(request.getParameter("vst_uac_updator")));
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _innerUserAddChannelService.batchAuditData(params, getUserSession(moduleId));
			if(result > 0){
				json.put("code", 100);
				json.put("result", result);
			}else{
				json.put("code", 200);
			}
		} catch (Exception e) {
			json.put("code", 300);
			logger.error("ajaxBatchAuditData error. ERROR:" + SystemException.getExceptionInfo(e));
		}
		printJson(json.toJSONString());
		return null;
	}
	
	/**
	 * 批量修改调整系数
	 * @return
	 */
	@RequestMapping("/ajaxModifyModulus.action")
	public String ajaxModifyModulus(){
		// 输出流
		PrintWriter out = null;
		String ids = null;
		String modulus = null;
		try {
			// 初始化
			this.initializeAction(_className);
			out = response.getWriter();
			ids = request.getParameter("ids");
			modulus = request.getParameter("modulus");
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			_innerUserAddChannelService.modifyModulus(ids, modulus, getUserSession(moduleId));
			out.print(true);
			out.flush();
		} catch (Exception e) {
			logger.error("ajaxModifyModulus error. ids = " + ids + ", modulus = " + modulus + ", ERROR:" + SystemException.getExceptionInfo(e));
			out.print(false);
		} finally {
			// 关流操作
			ToolsIO.closeStream(out);
		}
		return null;
	}
	
	/**
	 * 批量修改
	 * @return
	 */
	@RequestMapping("/ajaxBatchUpdate.action")
	public String ajaxBatchUpdate(){
		JSONObject json = new JSONObject();
		try {
			// 初始化
			this.initializeAction(_className);
			String ids = request.getParameter("recordId");
			String modulus = request.getParameter("modulus");
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _innerUserAddChannelService.modifyModulus(ids, modulus, getUserSession(moduleId));
			if(result > 0){
				json.put("code", 100);
			}else{
				json.put("code", 200);
			}
		} catch (Exception e) {
			json.put("code", 300);
			logger.error("ajaxBatchUpdate error. ERROR:" + SystemException.getExceptionInfo(e));
		}
		printJson(json.toJSONString());
		return null;
	}
	
	/**
	 * 导入数据
	 * @return
	 */
	@RequestMapping("/ajaxImportData.action")
	public String ajaxImportData(){
		JSONObject json = new JSONObject();
		try {
			// 初始化
			this.initializeAction(_className);
			String date = request.getParameter("date");
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _innerUserAddChannelService.importData(date, getUserSession(moduleId));
			if(result > 0){
				json.put("code", 100);
				json.put("result", result);
			}else{
				json.put("code", 200);
			}
		} catch (Exception e) {
			json.put("code", 300);
			logger.error("ajaxImportData error. ERROR:" + SystemException.getExceptionInfo(e));
		}
		printJson(json.toJSONString());
		return null;
	}
	
	/**
	 * 删除数据
	 * @return
	 */
	@RequestMapping("/ajaxDeleteData.action")
	public String ajaxDeleteData(){
		JSONObject json = new JSONObject();
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String startDay = ToolsString.checkEmpty(request.getParameter("startDay"));
			String endDay = ToolsString.checkEmpty(request.getParameter("endDay"));
			if(ToolsString.isEmpty(startDay) || ToolsString.isEmpty(endDay)){
				// 转向失败页面
				return this.moveToPage(VstConstants.OPERATE_INFO, "必选选择日期才能删除！", findInnerUserAddChannel());
			}
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("startDay", startDay);
			params.put("endDay", endDay);
			params.put("vst_uac_channel", ToolsString.checkEmpty(request.getParameter("vst_uac_channel")));
			params.put("vst_uac_state", ToolsString.checkEmpty(request.getParameter("vst_uac_state")));
			params.put("vst_uac_updator", ToolsString.checkEmpty(request.getParameter("vst_uac_updator")));
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _innerUserAddChannelService.deleteData(params, getUserSession(moduleId));
			if(result > 0){
				json.put("code", 100);
				json.put("result", result);
			}else{
				json.put("code", 200);
			}
		} catch (Exception e) {
			json.put("code", 300);
			logger.error("ajaxDeleteData error. ERROR:" + SystemException.getExceptionInfo(e));
		}
		printJson(json.toJSONString());
		return null;
	}
	
	/**
	 * 同步数据
	 * @return
	 */
	@RequestMapping("/ajaxSyncData.action")
	public String ajaxSyncData(){
		JSONObject json = new JSONObject();
		try {
			// 初始化
			this.initializeAction(_className);
			int startDate = VstTools.parseInt(request.getParameter("startDate"));
			int endDate = VstTools.parseInt(request.getParameter("endDate"));
			String url = "http://bi.cibnvst.com:8081/cibnvst-bi-quartz/push/user/add/channel?startDate={startDate}&endDate={endDate}"
					.replace("{startDate}", startDate+"")
					.replace("{endDate}", endDate+"");
			json = ToolsHttp.getJson(url);
		} catch (Exception e) {
			json.put("code", 300);
			logger.error("ajaxSyncData error. ERROR:" + SystemException.getExceptionInfo(e));
		}
		printJson(json.toJSONString());
		return null;
	}

	/**
	 * 修改数据
	 * @return
	 */
	@RequestMapping("/ajaxEditData.action")
	public String ajaxEditData(){
		JSONObject json = new JSONObject();
		try {
			// 初始化
			this.initializeAction(_className);

			String recordId = ToolsString.checkEmpty(request.getParameter("recordId"));
			if(ToolsString.isEmpty(recordId)){
				json.put("code", 201);
			}

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("vst_uac_id", recordId);
			params.put("vst_uac_amount", ToolsString.checkEmpty(request.getParameter("amount")));
			params.put("vst_uac_modulus", ToolsString.checkEmpty(request.getParameter("modulus")));

			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _innerUserAddChannelService.updateData(params, getUserSession(moduleId));
			if(result > 0){
				json.put("code", 100);
			}else{
				json.put("code", 200);
			}
		} catch (Exception e) {
			json.put("code", 300);
			logger.error("ajaxEditData error. ERROR:" + SystemException.getExceptionInfo(e));
		}
		printJson(json.toJSONString());
		return null;
	}

	/**
	 * 根据维度统计
	 * @return
	 */
	@RequestMapping("/ajaxGetReportByDim.action")
	public String ajaxGetReportByDim(){
		try{
			this.initializeAction(_className);

			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("startDay", ToolsString.checkEmpty(request.getParameter("startDay")));
			queryParam.put("endDay", ToolsString.checkEmpty(request.getParameter("endDay")));
			queryParam.put("pkgName", ToolsString.checkEmpty(request.getParameter("pkgName")));
			queryParam.put("vst_uac_channel", ToolsString.checkEmpty(request.getParameter("vst_uac_channel")));
			queryParam.put("vst_uac_state", ToolsString.checkEmpty(request.getParameter("vst_uac_state")));
			queryParam.put("vst_uac_updator", ToolsString.checkEmpty(request.getParameter("vst_uac_updator")));

			ReportBean result = _innerUserAddChannelService.getReportByDim(queryParam);
			List<Map<String, Object>> data = result.getMapData();

			JSONObject json = new JSONObject();
			if(data != null && !data.isEmpty()){
				json.put("result", "success");
				json.put("data", data);
			}else{
				json.put("result", "empty");
			}
			printJson(json.toString());
		}catch(Exception e){
			logger.error("ajaxGetReportByDim error. ERROR:" + e.getMessage());
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		}
		return null;
	}
}
