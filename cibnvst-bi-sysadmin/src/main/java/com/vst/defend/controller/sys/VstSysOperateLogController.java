package com.vst.defend.controller.sys;

import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.controller.BaseController;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.ErrorInfo;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.service.sys.VstSysOperateLogService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 操作日志管理
 * @author lhp
 * @date 2017-4-10 上午09:58:14
 * @description
 * @version
 */
@Scope("session")
@Controller
@RequestMapping("/sysOperateLog")
public class VstSysOperateLogController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = VstSysOperateLogController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 操作日志Service接口
	 */
	@Resource
	private VstSysOperateLogService _vstSysOperateLogService;
	
	/**
	 * 查询操作日志列表
	 * @return
	 */
	@RequestMapping("/findOperLogs.action")
	public String findOperLogs(){
		try {
			// 初始化
			this.initializeAction(_className);

			if("1".equals(request.getParameter("flag"))) {
				cutPage = new CutPage();
			}else {
				cutPage = new CutPage();
				// 查询条件
				Map<String, String> queryParam = new HashMap<String, String>();
				queryParam.put("vst_log_id", ToolsString.checkEmpty(request.getParameter("vst_log_id")));
				queryParam.put("vst_sys_id", ToolsString.checkEmpty(request.getParameter("vst_sys_id")));
				queryParam.put("vst_sys_username", ToolsString.checkEmpty(request.getParameter("vst_sys_username")));
				queryParam.put("vst_module_id", ToolsString.checkEmpty(request.getParameter("vst_module_id")));
				queryParam.put("vst_module_name", ToolsString.checkEmpty(request.getParameter("vst_module_name")));
				queryParam.put("vst_log_type", ToolsString.checkEmpty(request.getParameter("vst_log_type")));
				queryParam.put("vst_log_ip", ToolsString.checkEmpty(request.getParameter("vst_log_ip")));
				queryParam.put("vst_log_content", ToolsString.checkEmpty(request.getParameter("vst_log_content")));
				queryParam.put("operate_starttime", ToolsString.checkEmpty(request.getParameter("operate_starttime")));
				queryParam.put("operate_endtime", ToolsString.checkEmpty(request.getParameter("operate_endtime")));
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
			
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			if(!"1".equals(request.getParameter("flag"))){
				cutPage = _vstSysOperateLogService.getSysOperLogList(cutPage, getUserSession(moduleId));
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
		} catch (Exception e) {
			logger.error("findOperLogs error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		}
		return "sys/sysOperLog_list";
	}
	
	/**
	 * 删除系统操作日志
	 * @return
	 */
	@RequestMapping("/deleteOperLogs.action")
	public String deleteOperLogs(@RequestParam Map<String, Object> params){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			
			String moduleId = String.valueOf(params.get("moduleId"));
			_vstSysOperateLogService.deleteSysOperLogs(params, getUserSession(moduleId));
		} catch (Exception e) {
			logger.error("deleteLoginLogs error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "删除系统操作日志失败！", findOperLogs());
		}
		return this.moveToPage(VstConstants.OPERATE_SUCCESS, "删除系统操作日志成功！", findOperLogs());
	}
}
