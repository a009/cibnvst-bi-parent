package com.vst.defend.controller.task;

import java.io.File;
import java.util.HashMap;
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
import com.vst.defend.communal.controller.BaseController;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.ErrorInfo;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.CutPage;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.communal.util.VstTools;
import com.vst.defend.service.sys.VstSysModuleService;
import com.vst.defend.service.task.VstExportTaskService;

/**
 * 导出任务
 * @author lhp
 * @date 2018-4-13 上午11:38:44
 * @version
 */
@Scope("session")
@Controller
@RequestMapping("/exportTask")
public class VstExportTaskController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = VstExportTaskController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 导出任务Service接口
	 */
	@Resource
	private VstExportTaskService _vstExportTaskService;
	
	/**
	 * 系统模块Service接口
	 */
	@Resource
	private VstSysModuleService _vstSysModuleService;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询导出任务列表
	 * @return
	 */
	@RequestMapping("/findExportTasks.action")
	public String findExportTasks(){
		try {
			// 初始化
			this.initializeAction(_className);
			
			// 获取查看所有导出任务的用户
			String export_look_all = _baseService.getOptionByKey("export_look_all");
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			// 登录用户名
			String userName = getUserSession(moduleId).getLoginInfo().getLoginName();
			// 是否是管理员
			boolean isAdmin = false;
			if(!ToolsString.isEmpty(export_look_all)){
				String[] userArr = export_look_all.split(",");
				for(String name : userArr){
					if(name.equals(userName)){
						isAdmin = true;
						break;
					}
				}
				request.setAttribute("isAdmin", isAdmin);
			}
			
			Object obj = session.getAttribute("cutPage_exportTask");
			if(obj != null){
				cutPage = (CutPage) obj;
			}else{
				cutPage = new CutPage();
				// 查询条件
				Map<String, String> queryParam = new HashMap<String, String>();
				queryParam.put("vst_task_id", ToolsString.checkEmpty(request.getParameter("vst_task_id")));
				queryParam.put("vst_module_id", ToolsString.checkEmpty(request.getParameter("vst_module_id")));
				queryParam.put("vst_task_table", ToolsString.checkEmpty(request.getParameter("vst_task_table")));
				queryParam.put("vst_task_columns", ToolsString.checkEmpty(request.getParameter("vst_task_columns")));
				queryParam.put("vst_task_condition", ToolsString.checkEmpty(request.getParameter("vst_task_condition")));
				queryParam.put("vst_task_file_path", ToolsString.checkEmpty(request.getParameter("vst_task_file_path")));
				queryParam.put("vst_task_file_name", ToolsString.checkEmpty(request.getParameter("vst_task_file_name")));
				queryParam.put("vst_task_state", ToolsString.checkEmpty(request.getParameter("vst_task_state")));
				if(isAdmin){
					queryParam.put("vst_task_creator", ToolsString.checkEmpty(request.getParameter("vst_task_creator")));
				}else{
					queryParam.put("vst_task_creator", userName);
				}
				
				// 排序信息
				String orderBy = ToolsString.checkEmpty(request.getParameter("orderBy"));
				String order = ToolsString.checkEmpty(request.getParameter("order"));
				queryParam.put("orderBy", orderBy);
				queryParam.put("order", order);
				cutPage.set_queryParam(queryParam);
				
				// 分页信息
				int currPage = ToolsNumber.parseInt(request.getParameter("_currPage"));
				int singleCount = ToolsNumber.parseInt(request.getParameter("_singleCount"));
				int totalPages = ToolsNumber.parseInt(request.getParameter("_totalPages"));
				if(currPage != -1){
					cutPage.set_currPage(currPage);
				}
				if(singleCount != -1){
					cutPage.set_singleCount(singleCount);
				}
				if(totalPages != -1){
					cutPage.set_totalPages(totalPages);
				}
			}
			
			if(!"1".equals(request.getParameter("flag"))){
				cutPage = _vstExportTaskService.getExportTaskList(cutPage, getUserSession(moduleId));
				cutPage.set_isQuery(true);
				// 转发展示/隐藏 tablelist
				request.setAttribute("hidden_tablelist", request.getParameter("hidden_tablelist"));
			}
			
			// 获取页面按钮
			cutPage.set_buttonList(getPageButtons(moduleId));
			// 转发查询到的数据到页面
			request.setAttribute("cutPage", cutPage);
			// 转发当前模块ID
			request.setAttribute("moduleId", moduleId);
			// 转发展示/隐藏 查询条件
			request.setAttribute("hidden_search", request.getParameter("hidden_search"));
			
			// 获取所有模块
			Map<String, Object> params = new HashMap<String, Object>(1);
			params.put("vst_module_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("moduleList", _vstSysModuleService.queryForMap(params, "vst_module_id", "vst_module_name"));
		} catch (Exception e) {
			logger.error("findExportTasks error." + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_exportTask");
		}
		
		return "task/exportTask_list";
	}
	
	/**
	 * 轮询查询消息
	 * @return
	 */
	@RequestMapping("/getMessage.action")
	public String getMessage(){
		JSONObject json = new JSONObject();
		try{
			this.initializeAction(_className);
			
			String receiverId = getUserSession().getVstSysUser().getVst_sys_id();
			String receiverName = getUserSession().getVstSysUser().getVst_sys_username();
			int second = 0;
			while(true){
				// 如果有已经完成的任务
				if(_vstExportTaskService.isUnreadMessageNumberChanged(receiverId)){
					json.put("code", 100);
					json.put("msg", "有消息");
					//返回下载地址
					Map<String, Object> params = new HashMap<String, Object>(2);
					params.put("vst_task_creator", receiverName);
					params.put("vst_task_state", 1);
					Map<String, Object> taskSuccess = _vstExportTaskService.queryForMap(params, "vst_task_id", null);
					json.put("taskSuccess", taskSuccess);
					params.put("vst_task_state", 2);
					Map<String, Object> taskError = _vstExportTaskService.queryForMap(params, "vst_task_id", null);
					json.put("taskError", taskError);
					break;
				}
				// 睡眠1秒
				Thread.sleep(1000);
				second++;
				// 29秒结束，还是没新的消息
				if(second >= 29){
					json.put("code", 200);
					json.put("msg", "没有消息");
					break;
				}
			}
		}catch(Exception e){
			json.put("code", 301);
			json.put("msg", "服务器异常");
			logger.error("getMessage error." + SystemException.getExceptionInfo(e));
		}
		printJson(json.toJSONString());
		return null;
	}
	
	/**
	 * 下载文件
	 * @return
	 */
	@RequestMapping("/downloadFile.action")
	public String downloadFile(){
		try{
			this.initializeAction(_className);
			
			String url = ToolsString.checkEmpty(request.getParameter("url"));
			File file = new File(url);
			if(file.exists()){
				VstTools.fileDownLoad(response, file);
			}
		}catch(Exception e){
			logger.error("exportData error." + SystemException.getExceptionInfo(e));
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");// 转向失败页面
		}
		return null;
	}
}
