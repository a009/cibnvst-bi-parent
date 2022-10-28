package com.vst.defend.controller.topic;

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
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.communal.util.VstTools;
import com.vst.defend.service.topic.VstTopicClickService;

/**
 * 专题点击
 * @author lhp
 * @date 2017-11-28 下午07:41:41
 * @version
 */
@Scope("session")
@Controller
@RequestMapping("/topicClick")
public class VstTopicClickController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = VstTopicClickController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 专题点击Service接口
	 */
	@Resource
	private VstTopicClickService _vstTopicClickService;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询专题点击统计
	 * @return
	 */
	@RequestMapping("/findTopicClick.action")
	public String findTopicClick(){
		try {
			// 初始化
			this.initializeAction(_className);
			
			Object obj = session.getAttribute("cutPage_topicClick");
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
			
			// 获取专区类型
			Map<String, Object> params = new HashMap<String, Object>(4);
			params.put("vst_pkg", recordPkg);
			params.put("vst_table_name", "vst_topic");
			params.put("vst_column_name", "specialType");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("specialTypes", _baseService.getDictionaryForMap(params));
			// 获取分类
			params.put("vst_table_name", "vst_topic");
			params.put("vst_column_name", "classify");
			request.setAttribute("classifys", _baseService.getDictionaryForMap(params));
			// 获取模版类型
			params.put("vst_table_name", "vst_topic");
			params.put("vst_column_name", "template");
			request.setAttribute("templates", _baseService.getDictionaryForMap(params));
			
			// 获取表字段
			Map<String, String> columns = new LinkedHashMap<String, String>();
			columns.put("vst_tc_date", "日期");
			columns.put("vst_tc_pkg", "包名");
			columns.put("vst_tc_special_type", "专区类型");
			columns.put("vst_tc_cid", "分类");
			columns.put("vst_tc_template", "模版类型");
			columns.put("vst_tc_topicId", "专题ID");
			columns.put("vst_tc_name", "专题名称");
			columns.put("vst_tc_amount", "点击量");
			columns.put("vst_tc_amount_dod", "点击量天环比");
			columns.put("vst_tc_amount_wow", "点击量周环比");
			columns.put("vst_tc_uv", "独立用户");
			columns.put("vst_tc_uv_dod", "独立用户天环比");
			columns.put("vst_tc_uv_wow", "独立用户周环比");
			columns.put("vst_tc_avg", "人均点击量");
			request.setAttribute("columns", columns);
			// 获取表描述
			request.setAttribute("tableDesc", _baseService.getTableDesc("vst_topic_click"));
		} catch (Exception e) {
			logger.error("findTopicClick error." + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_topicClick");
		}
		
		return "topic/topicClick_list";
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
			queryParam.put("vst_tc_special_type", ToolsString.checkEmpty(request.getParameter("vst_tc_special_type")));
			queryParam.put("vst_tc_cid", ToolsString.checkEmpty(request.getParameter("vst_tc_cid")));
			queryParam.put("vst_tc_template", ToolsString.checkEmpty(request.getParameter("vst_tc_template")));
			queryParam.put("vst_tc_topicId", ToolsString.checkEmpty(request.getParameter("vst_tc_topicId")));
			queryParam.put("vst_tc_name", ToolsString.checkEmpty(request.getParameter("vst_tc_name")));
			queryParam.put("export_column", ToolsString.checkEmpty(request.getParameter("export_column")));
			// 编码转码
			VstTools.decodeToUTF_8(queryParam);
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			ReportBean bean = _vstTopicClickService.getExportData(queryParam, getUserSession(moduleId));
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
