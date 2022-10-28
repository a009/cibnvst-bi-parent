package com.vst.defend.controller.user;

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
import com.vst.defend.communal.util.VstTools;
import com.vst.defend.service.user.VstUserAddChannelBdmodelService;

/**
 * 新增用户(渠道、设备)
 * @author lhp
 * @date 2019-3-6 下午02:17:36
 * @version
 */
@Scope("session")
@Controller
@RequestMapping("/userAddChannelBdmodel")
public class VstUserAddChannelBdmodelController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = VstUserAddChannelBdmodelController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 新增用户(渠道、设备)Service接口
	 */
	@Resource
	private VstUserAddChannelBdmodelService _vstUserAddChannelBdmodelService;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询新增用户(渠道、设备)统计
	 * @return
	 */
	@RequestMapping("/findUserAddChannelBdmodel.action")
	public String findUserAddChannelBdmodel(){
		try {
			// 初始化
			this.initializeAction(_className);
			
			Object obj = session.getAttribute("cutPage_userAddChannelBdmodel");
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
			columns.put("vst_uacb_date", "日期");
			columns.put("vst_uacb_pkg", "包名");
			columns.put("vst_uacb_channel", "渠道");
			columns.put("vst_uacb_bdmodel", "设备名称");
			columns.put("vst_uacb_amount", "用户数量");
			columns.put("vst_uacb_rate", "用户占比");
			columns.put("vst_uacb_dod", "天环比");
			columns.put("vst_uacb_wow", "周环比");
			columns.put("vst_uacb_mom", "月环比");
			request.setAttribute("columns", columns);
			// 获取表描述
			request.setAttribute("tableDesc", _baseService.getTableDesc("vst_user_add_channel_bdmodel"));
		} catch (Exception e) {
			logger.error("findUserAddChannelBdmodel error." + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_userAddChannelBdmodel");
		}
		
		return "user/userAddChannelBdmodel_list";
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
			queryParam.put("vst_uacb_channel", ToolsString.checkEmpty(request.getParameter("vst_uacb_channel")));
			queryParam.put("vst_uacb_bdmodel", ToolsString.checkEmpty(request.getParameter("vst_uacb_bdmodel")));
			queryParam.put("orderBy", ToolsString.checkEmpty(request.getParameter("orderBy")));
			queryParam.put("order", ToolsString.checkEmpty(request.getParameter("order")));
			queryParam.put("export_column", ToolsString.checkEmpty(request.getParameter("export_column")));
			// 编码转码
			VstTools.decodeToUTF_8(queryParam);
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			ReportBean bean = _vstUserAddChannelBdmodelService.getExportData(queryParam, getUserSession(moduleId));
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
	 * 根据设备统计
	 * @return
	 */
	@RequestMapping("/ajaxGetReportByBdmodel.action")
	public String ajaxGetReportByBdmodel(){
		try{
			this.initializeAction(_className);
			
			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("startDay", ToolsString.checkEmpty(request.getParameter("startDay")));
			queryParam.put("endDay", ToolsString.checkEmpty(request.getParameter("endDay")));
			queryParam.put("pkgName", ToolsString.checkEmpty(request.getParameter("pkgName")));
			queryParam.put("vst_uacb_channel", ToolsString.checkEmpty(request.getParameter("vst_uacb_channel")));
			queryParam.put("vst_uacb_channel_eq", ToolsString.checkEmpty(request.getParameter("vst_uacb_channel_eq")));
			queryParam.put("vst_uacb_channel_ne", ToolsString.checkEmpty(request.getParameter("vst_uacb_channel_ne")));
			queryParam.put("vst_uacb_bdmodel", ToolsString.checkEmpty(request.getParameter("vst_uacb_bdmodel")));
			queryParam.put("vst_uacb_bdmodel_eq", ToolsString.checkEmpty(request.getParameter("vst_uacb_bdmodel_eq")));
			queryParam.put("vst_uacb_bdmodel_ne", ToolsString.checkEmpty(request.getParameter("vst_uacb_bdmodel_ne")));
			
			JSONObject data = _vstUserAddChannelBdmodelService.getReportByBdmodel(queryParam);
			
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
			logger.error("ajaxGetReportByBdmodel error. ERROR:" + e.getMessage());
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		}
		return null;
	}
}
