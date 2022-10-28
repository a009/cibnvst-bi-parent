package com.vst.defend.controller.event;

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
import com.vst.defend.service.event.VstCustomEventService;

/**
 * 自定义事件
 * @author lhp
 * @date 2018-4-16 上午10:38:08
 * @version
 */
@Scope("session")
@Controller
@RequestMapping("/customEvent")
public class VstCustomEventController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = VstCustomEventController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 自定义事件Service接口
	 */
	@Resource
	private VstCustomEventService _vstCustomEventService;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询自定义事件统计
	 * @return
	 */
	@RequestMapping("/findCustomEvent.action")
	public String findCustomEvent(){
		try {
			// 初始化
			this.initializeAction(_className);
			
			Object obj = session.getAttribute("cutPage_customEvent");
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
			columns.put("vst_ce_date", "日期");
			columns.put("vst_ce_pkg", "包名");
			columns.put("vst_ce_version", "版本");
			columns.put("vst_ce_event_name", "事件名称");
			columns.put("vst_ce_event_type", "事件类型");
			columns.put("vst_ce_event_key", "事件Key");
			columns.put("vst_ce_event_value", "事件Value");
			columns.put("vst_ce_vv", "消息数");
			columns.put("vst_ce_vv_dod", "消息数天环比");
			columns.put("vst_ce_vv_wow", "消息数周环比");
			columns.put("vst_ce_uv", "独立用户");
			columns.put("vst_ce_uv_dod", "独立用户天环比");
			columns.put("vst_ce_uv_wow", "独立用户周环比");
			columns.put("vst_ce_avg", "人均消息数");
			request.setAttribute("columns", columns);
			// 获取表描述
			request.setAttribute("tableDesc", _baseService.getTableDesc("vst_custom_event"));
		} catch (Exception e) {
			logger.error("findCustomEvent error." + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_customEvent");
		}
		
		return "event/customEvent_list";
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
			queryParam.put("vst_ce_version", ToolsString.checkEmpty(request.getParameter("vst_ce_version")));
			queryParam.put("vst_ce_event_type", ToolsString.checkEmpty(request.getParameter("vst_ce_event_type")));
			queryParam.put("vst_ce_event_name", ToolsString.checkEmpty(request.getParameter("vst_ce_event_name")));
			queryParam.put("vst_ce_event_key", ToolsString.checkEmpty(request.getParameter("vst_ce_event_key")));
			queryParam.put("vst_ce_event_value", ToolsString.checkEmpty(request.getParameter("vst_ce_event_value")));
			queryParam.put("export_column", ToolsString.checkEmpty(request.getParameter("export_column")));
			// 编码转码
			VstTools.decodeToUTF_8(queryParam);
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			ReportBean bean = _vstCustomEventService.getExportData(queryParam, getUserSession(moduleId));
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
}
