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
import com.vst.defend.service.user.VstUserRetainChannelService;

/**
 * 留存用户(渠道)
 * @author lhp
 * @date 2018-1-5 上午10:58:12
 * @version
 */
@Scope("session")
@Controller
@RequestMapping("/userRetainChannel")
public class VstUserRetainChannelController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = VstUserRetainChannelController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 留存用户(渠道)Service接口
	 */
	@Resource
	private VstUserRetainChannelService _vstUserRetainChannelService;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询留存用户(渠道)统计
	 * @return
	 */
	@RequestMapping("/findUserRetainChannel.action")
	public String findUserRetainChannel(){
		try {
			// 初始化
			this.initializeAction(_className);
			
			Object obj = session.getAttribute("cutPage_userRetainChannel");
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
			columns.put("vst_urc_date", "日期");
			columns.put("vst_urc_pkg", "包名");
			columns.put("vst_urc_channel", "渠道");
			columns.put("vst_urc_amount", "次日留存用户");
			columns.put("vst_urc_dod", "次日留存天环比");
			columns.put("vst_urc_wow", "次日留存周环比");
			columns.put("vst_urc_rate", "次日留存率");
			columns.put("vst_urc_week_amount", "周留存用户");
			columns.put("vst_urc_week_dod", "周留存天环比");
			columns.put("vst_urc_week_wow", "周留存周环比");
			columns.put("vst_urc_week_rate", "周留存率");
			columns.put("vst_urc_month_amount", "月留存用户");
			columns.put("vst_urc_month_dod", "月留存天环比");
			columns.put("vst_urc_month_wow", "月留存周环比");
			columns.put("vst_urc_month_rate", "月留存率");
			columns.put("vst_urc_season_amount", "季留存用户");
			columns.put("vst_urc_season_dod", "季留存天环比");
			columns.put("vst_urc_season_wow", "季留存周环比");
			columns.put("vst_urc_season_rate", "季留存率");
			request.setAttribute("columns", columns);
			// 获取表描述
			request.setAttribute("tableDesc", _baseService.getTableDesc("vst_user_retain_channel"));
		} catch (Exception e) {
			logger.error("findUserRetainChannel error." + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_userRetainChannel");
		}
		
		return "user/userRetainChannel_list";
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
			queryParam.put("vst_urc_channel", ToolsString.checkEmpty(request.getParameter("vst_urc_channel")));
			queryParam.put("export_column", ToolsString.checkEmpty(request.getParameter("export_column")));
			// 编码转码
			VstTools.decodeToUTF_8(queryParam);
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			ReportBean bean = _vstUserRetainChannelService.getExportData(queryParam, getUserSession(moduleId));
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
