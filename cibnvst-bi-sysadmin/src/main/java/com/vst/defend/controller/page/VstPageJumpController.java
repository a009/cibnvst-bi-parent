package com.vst.defend.controller.page;

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
import com.vst.defend.service.page.VstPageJumpService;

/**
 * 页面跳转
 * @author lhp
 * @date 2018-5-16 上午10:30:26
 * @version
 */
@Scope("session")
@Controller
@RequestMapping("/pageJump")
public class VstPageJumpController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = VstPageJumpController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 页面跳转Service接口
	 */
	@Resource
	private VstPageJumpService _vstPageJumpService;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询页面跳转统计
	 * @return
	 */
	@RequestMapping("/findPageJump.action")
	public String findPageJump(){
		try {
			// 初始化
			this.initializeAction(_className);
			
			Object obj = session.getAttribute("cutPage_pageJump");
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
			
			// 获取表字段
			Map<String, String> columns = new LinkedHashMap<String, String>();
			columns.put("vst_pj_date", "日期");
			columns.put("vst_pj_pkg", "包名");
			columns.put("vst_pj_curr_page", "当前页面");
			columns.put("vst_pj_curr_amount", "页面点击");
			request.setAttribute("columns", columns);
			// 获取表描述
			request.setAttribute("tableDesc", _baseService.getTableDesc("vst_page_jump"));
			
			// 获取页面描述
			Map<String, Object> params = new HashMap<String, Object>(4);
			params.put("vst_pkg", recordPkg);
			params.put("vst_table_name", "vst_page_jump");
			params.put("vst_column_name", "vst_pj_curr_page");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("pageMap", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("findPageJump error." + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_pageJump");
		}
		
		return "page/pageJump_list";
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
			queryParam.put("vst_pj_curr_page", ToolsString.checkEmpty(request.getParameter("vst_pj_curr_page")));
			queryParam.put("vst_pj_jump_page", ToolsString.checkEmpty(request.getParameter("vst_pj_jump_page")));
			queryParam.put("export_column", ToolsString.checkEmpty(request.getParameter("export_column")));
			// 编码转码
			VstTools.decodeToUTF_8(queryParam);
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			ReportBean bean = _vstPageJumpService.getExportData(queryParam, getUserSession(moduleId));
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
	 * 根据当前页面导出
	 * @return
	 */
	@RequestMapping("/exportDataByCurrPage.action")
	public String exportDataByCurrPage(){
		try{
			this.initializeAction(_className);
			
			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("startDay", ToolsString.checkEmpty(request.getParameter("startDay")));
			queryParam.put("endDay", ToolsString.checkEmpty(request.getParameter("endDay")));
			queryParam.put("pkgName", ToolsString.checkEmpty(request.getParameter("pkgName")));
			queryParam.put("vst_pj_curr_page", ToolsString.checkEmpty(request.getParameter("vst_pj_curr_page")));
			queryParam.put("export_column", ToolsString.checkEmpty(request.getParameter("export_column")));
			// 编码转码
			VstTools.decodeToUTF_8(queryParam);
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			ReportBean bean = _vstPageJumpService.getExportDataByCurrPage(queryParam, getUserSession(moduleId));
			List<Map<String, Object>> data = bean.getMapData();
			File file = ExportUtil.ExportExcel(data, PropertiesReader.getProperty("export_dir"), bean.getTitle().trim());
			if(file.exists()){
				VstTools.fileDownLoad(response, file);
				file.delete();
			}
		}catch(Exception e){
			logger.error("exportDataByCurrPage error." + SystemException.getExceptionInfo(e));
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");// 转向失败页面
		}
		return null;
	}
	
	/**
	 * 查看详情
	 * @return
	 */
	@RequestMapping("/findDetails.action")
	public String findDetails(){
		try {
			// 初始化
			this.initializeAction(_className);
			
			Object obj = session.getAttribute("cutPage_pageJump_detail");
			if(obj != null){
				cutPage = (CutPage) obj;
			}else{
				cutPage = new CutPage();
			}
			
			// 获取查询条件
			String date = ToolsString.checkEmpty(request.getParameter("date"));
			String currPage = ToolsString.checkEmpty(request.getParameter("currPage"));
			request.setAttribute("date", date);
			request.setAttribute("currPage", currPage);
			
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
			
			// 获取表字段
			Map<String, String> columns = new LinkedHashMap<String, String>();
			columns.put("vst_pj_date", "日期");
			columns.put("vst_pj_pkg", "包名");
			columns.put("vst_pj_curr_page", "当前页面");
			columns.put("vst_pj_jump_page", "跳转页面");
			columns.put("vst_pj_jump_amount", "页面跳转数");
			columns.put("vst_pj_jump_radio", "页面跳转占比");
			request.setAttribute("columns", columns);
			// 获取表描述
			request.setAttribute("tableDesc", _baseService.getTableDesc("vst_page_jump"));
			
			// 获取页面描述
			Map<String, Object> params = new HashMap<String, Object>(4);
			params.put("vst_pkg", recordPkg);
			params.put("vst_table_name", "vst_page_jump");
			params.put("vst_column_name", "vst_pj_curr_page");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("pageMap", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("findDetails error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "pageJump/findPageJump.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_pageJump_detail");
		}
		
		return "page/pageJump_detail";
	}
	
	/**
	 * 根据跳转页面统计
	 * @return
	 */
	@RequestMapping("/ajaxGetReportByJumpPage.action")
	public String ajaxGetReportByJumpPage(){
		try{
			this.initializeAction(_className);
			
			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("startDay", ToolsString.checkEmpty(request.getParameter("startDay")));
			queryParam.put("endDay", ToolsString.checkEmpty(request.getParameter("endDay")));
			queryParam.put("pkgName", ToolsString.checkEmpty(request.getParameter("pkgName")));
			queryParam.put("vst_pj_curr_page_eq", ToolsString.checkEmpty(request.getParameter("vst_pj_curr_page_eq")));
			queryParam.put("vst_pj_jump_page", ToolsString.checkEmpty(request.getParameter("vst_pj_jump_page")));
			queryParam.put("vst_pj_jump_page_eq", ToolsString.checkEmpty(request.getParameter("vst_pj_jump_page_eq")));
			queryParam.put("vst_pj_jump_page_ne", ToolsString.checkEmpty(request.getParameter("vst_pj_jump_page_ne")));
			
			// 按跳转页面统计
			JSONObject data = _vstPageJumpService.getReportByJumpPage(queryParam);
			
			JSONObject json = new JSONObject();
			if(data != null && !data.isEmpty()){
				json.put("data", data);
				json.put("code", 100);
				json.put("msg", "调用成功");
			}else{
				json.put("code", 202);
				json.put("msg", "没有结果");
			}
			printJson(json.toString());
		}catch(Exception e){
			logger.error("ajaxGetReportByJumpPage error. ERROR:" + e.getMessage());
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		}
		return null;
	}
}
