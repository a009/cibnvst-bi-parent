package com.vst.defend.controller.movie;

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
import com.vst.defend.service.movie.VstMovieSiteService;

/**
 * 平台播放
 * @author lhp
 * @date 2017-11-29 下午07:09:24
 * @version
 */
@Scope("session")
@Controller
@RequestMapping("/movieSite")
public class VstMovieSiteController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = VstMovieSiteController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 平台播放Service接口
	 */
	@Resource
	private VstMovieSiteService _vstMovieSiteService;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询平台播放统计
	 * @return
	 */
	@RequestMapping("/findMovieSite.action")
	public String findMovieSite(){
		try {
			// 初始化
			this.initializeAction(_className);
			
			Object obj = session.getAttribute("cutPage_movieSite");
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
			columns.put("vst_ms_date", "日期");
			columns.put("vst_ms_pkg", "包名");
			columns.put("vst_ms_special_type", "专区类型");
			columns.put("vst_ms_site", "平台名称");
			columns.put("vst_ms_vv", "播放量");
			columns.put("vst_ms_vv_dod", "播放量天环比");
			columns.put("vst_ms_vv_wow", "播放量周环比");
			columns.put("vst_ms_uv", "独立用户");
			columns.put("vst_ms_uv_dod", "独立用户天环比");
			columns.put("vst_ms_uv_wow", "独立用户周环比");
			columns.put("vst_ms_avg", "人均播放量");
			columns.put("vst_ms_playtime", "播放时长(H)");
			columns.put("vst_ms_playtime_avg", "人均播放时长(m)");
			request.setAttribute("columns", columns);
			// 获取表描述
			request.setAttribute("tableDesc", _baseService.getTableDesc("vst_movie_site"));
		} catch (Exception e) {
			logger.error("findMovieSite error." + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_movieSite");
		}
		
		return "movie/movieSite_list";
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
			queryParam.put("vst_ms_site", ToolsString.checkEmpty(request.getParameter("vst_ms_site")));
			queryParam.put("export_column", ToolsString.checkEmpty(request.getParameter("export_column")));
			// 编码转码
			VstTools.decodeToUTF_8(queryParam);
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			ReportBean bean = _vstMovieSiteService.getExportData(queryParam, getUserSession(moduleId));
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
	 * 根据平台统计
	 * @return
	 */
	@RequestMapping("/ajaxGetReportBySite.action")
	public String ajaxGetReportBySite(){
		try{
			this.initializeAction(_className);
			
			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("startDay", ToolsString.checkEmpty(request.getParameter("startDay")));
			queryParam.put("endDay", ToolsString.checkEmpty(request.getParameter("endDay")));
			queryParam.put("pkgName", ToolsString.checkEmpty(request.getParameter("pkgName")));
			queryParam.put("vst_ms_site", ToolsString.checkEmpty(request.getParameter("vst_ms_site")));
			
			// 按平台统计
			JSONObject data = _vstMovieSiteService.getReportBySite(queryParam);
			
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
			logger.error("ajaxGetReportBySite error. ERROR:" + e.getMessage());
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		}
		return null;
	}
}
