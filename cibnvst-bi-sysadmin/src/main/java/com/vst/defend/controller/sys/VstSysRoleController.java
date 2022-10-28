package com.vst.defend.controller.sys;

import com.vst.common.tools.io.ToolsIO;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.controller.BaseController;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.ErrorInfo;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.CutPage;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.service.sys.VstSysRoleService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 角色管理
 * @author lhp
 * @date 2017-4-12 下午07:30:49
 * @description
 * @version
 */
@Scope("session")
@Controller
@RequestMapping("/sysRole")
public class VstSysRoleController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = VstSysRoleController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 角色Service接口
	 */
	@Resource
	private VstSysRoleService _vstSysRoleService;
	
	/**
	 * 基本Service接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询角色列表
	 * @return
	 */
	@RequestMapping("/findRoles.action")
	public String findRoles(){
		try {
			// 初始化
			this.initializeAction(_className);

			if("1".equals(request.getParameter("flag"))) {
				cutPage = new CutPage();
			}else {
				cutPage = new CutPage();
				// 查询条件
				Map<String, String> queryParam = new HashMap<String, String>();
				queryParam.put("vst_role_id", ToolsString.checkEmpty(request.getParameter("vst_role_id")));
				queryParam.put("vst_role_name", ToolsString.checkEmpty(request.getParameter("vst_role_name")));
				queryParam.put("vst_role_state", ToolsString.checkEmpty(request.getParameter("vst_role_state")));
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
				cutPage = _vstSysRoleService.getSysRoleList(cutPage, getUserSession(moduleId));
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
			logger.error("findRoles error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_role");
		}
		return "sys/sysRole_list";
	}

	/**
	 * 跳转到查询页面
	 * @return
	 */
	@RequestMapping("/toFind.action")
	public String toFind(){
		try {
			// 初始化
			this.initializeAction(_className);

			Object obj = session.getAttribute("cutPage_role");
			if(obj != null){
				cutPage = (CutPage) obj;
			}else{
				cutPage = new CutPage();
				// 查询条件
				Map<String, String> queryParam = new HashMap<String, String>();
				queryParam.put("vst_role_id", ToolsString.checkEmpty(request.getParameter("vst_role_id")));
				queryParam.put("vst_role_name", ToolsString.checkEmpty(request.getParameter("vst_role_name")));
				queryParam.put("vst_role_state", ToolsString.checkEmpty(request.getParameter("vst_role_state")));
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
				cutPage = _vstSysRoleService.getSysRoleList(cutPage, getUserSession(moduleId));
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
			logger.error("toFind error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_role");
		}
		return "sys/sysRole_list";
	}
	
	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequestMapping("/toAdd.action")
	public String toAdd(){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			// 转发当前模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			request.setAttribute("moduleId", moduleId);
			
			// 查询该用户所在渠道拥有的最大权限列表
			UserSession user = getUserSession(moduleId);
			request.setAttribute("modules", _baseService.getParentSysModules(user));
		} catch (Exception e) {
			logger.error("toAdd error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysRole/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_role", cutPage);
		}
		return "sys/sysRole_add";
	}
	
	/**
	 * ajax校验角色名称是否存在
	 * @return
	 */
	@RequestMapping("/ajaxCheckRoleName.action")
	public String ajaxCheckRoleName(){
		// 输出流
		PrintWriter out = null;
		String roleName = null;
		try {
			this.initializeAction(_className);
			out = response.getWriter();
			roleName = request.getParameter("roleName");
			boolean flag = _vstSysRoleService.checkRoleNameExist(roleName);
			out.print(flag);
			out.flush();
		} catch (Exception e) {
			logger.error("ajaxCheckRoleName error. roleName=" + roleName + ", ERROR:" + e.getMessage());
		} finally {
			// 关流操作
			ToolsIO.closeStream(out);
		}
		return null;
	}
	
	/**
	 * 新增角色
	 * @return
	 */
	@RequestMapping("/addRole.action")
	public String addRole(@RequestParam Map<String, Object> params){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			// 获取选择的权限
			String[] powerList = request.getParameterValues("powerList");
			// 获取模块ID
			String moduleId = String.valueOf(params.get("moduleId"));
			
			_vstSysRoleService.addSysRole(params, powerList, getUserSession(moduleId));
			// 新增成功后需要重新刷新角色缓存
			getUserSession(moduleId).setSysRoles(_baseService.getAllSysRolesName());
		} catch (Exception e) {
			logger.error("addRole error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "添加角色失败！", toFind());
		}
		return this.moveToPage(VstConstants.OPERATE_SUCCESS, "添加角色成功！", toFind());
	}
	
	/**
	 * 跳转到修改页面
	 * @return
	 */
	@RequestMapping("/toEdit.action")
	public String toEdit(){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String recordId = request.getParameter("recordId");
			formMap = _vstSysRoleService.getSysRoleById(recordId);
			
			// 把老数据放入到session中
			session.setAttribute("oldData", formMap);
			request.setAttribute("formMap", formMap);
			// 转发当前模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			request.setAttribute("moduleId", moduleId);
			// 查询该用户所在渠道拥有的最大权限列表
			UserSession user = getUserSession(moduleId);
			request.setAttribute("modules", _baseService.getParentSysModules(user));
		} catch (Exception e) {
			logger.error("toEdit error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysRole/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_role", cutPage);
		}
		return "sys/sysRole_edit";
	}
	
	/**
	 * 修改角色
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/editRole.action")
	public String editRole(@RequestParam Map<String, Object> params){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			
			Object obj = session.getAttribute("oldData");
			Map<String, Object> oldParam = null;
			if(obj != null){
				oldParam = (Map<String, Object>) obj;
			}
			
			// 获取选择的权限
			String[] powerList = request.getParameterValues("powerList");
			// 获取模块ID
			String moduleId = String.valueOf(params.get("moduleId"));
			int result = _vstSysRoleService.updateSysRole(oldParam, params, powerList, getUserSession(moduleId));
			
			// 修改成功后需要重新刷新角色缓存
			getUserSession(moduleId).setSysRoles(_baseService.getAllSysRolesName());
			// 转向结果页面
			return this.moveToPage(result == 1 ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR, "修改角色"+ (result == 1 ? "成功！" : "失败！"), toFind());
		} catch (Exception e) {
			logger.error("editRole error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "修改角色失败！", toFind());
		} finally {
			// 移除老数据
			session.removeAttribute("oldData");
		}
	}
	
	/**
	 * 删除角色
	 * @return
	 */
	@RequestMapping("/deleteRole.action")
	public String deleteRole(){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String ids = request.getParameter("recordId");
			if(ToolsString.isEmpty(ids)){
				// 转向失败页面
				return this.moveToPage(VstConstants.OPERATE_INFO, "您至少选中一条记录进行删除操作！", toFind());
			}
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _vstSysRoleService.deleteSysRole(ids, getUserSession(moduleId));
			
			// 转向成功页面
			return this.moveToPage(result == ids.split(",").length ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR,(result > 0 ? "批量删除角色成功！" : "批量删除角色失败！"), toFind());
		} catch (Exception e) {
			logger.error("deleteRole error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "批量删除角色失败！", toFind());
		}
	}
	
	/**
	 * 修改角色状态
	 * @return
	 */
	@RequestMapping("/modifyRoleState.action")
	public String modifyRoleState(){
		// 状态
		int state = -1;
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String recordId = request.getParameter("recordId");
			String recordState = request.getParameter("recordState");
			
			state = Integer.parseInt(recordState);
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _vstSysRoleService.modifyRoleState(recordId, state, getUserSession(moduleId));
			
			// 转向成功页面
			return this.moveToPage(result == 1 ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR, 
							(state == 1 ? "启用角色" : "禁用角色") + (result == 1 ? "成功！" : "失败！"), toFind());
 		} catch (Exception e) {
			logger.error("modifyRoleState error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, (state == 1 ? "启用角色" : "禁用角色") + "失败！", toFind());
		}
	}
	
	/**
	 * 查询角色列表
	 * @return
	 */
	@RequestMapping("/getRoleList.action")
	public String getRoleList(){
		try {
			this.initializeAction(_className);
			if(cutPage == null){
				cutPage = new CutPage();
			}else{
				// 查询条件
				Map<String, String> queryParam = new HashMap<String, String>();
				String vst_role_id = ToolsString.checkEmpty(request.getParameter("vst_role_id"));
				String vst_role_name = ToolsString.checkEmpty(request.getParameter("vst_role_name"));
				String vst_role_state = ToolsString.checkEmpty(request.getParameter("vst_role_state"));
				queryParam.put("vst_role_id", vst_role_id);
				queryParam.put("vst_role_name", vst_role_name);
				queryParam.put("vst_role_state", vst_role_state);
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
				
				// 获取模块ID
				String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
				cutPage = _vstSysRoleService.getSysRoleList(cutPage, getUserSession(moduleId));
				cutPage.set_isQuery(true);
			}
			// 获取页面按钮
			cutPage.set_buttonList(getPageButtons(moduleId));
			// 转发查询到的数据到页面
			request.setAttribute("cutPage", cutPage);
			// 转发展示/隐藏 查询条件
			request.setAttribute("hidden_search", request.getParameter("hidden_search"));
			// 转发展示/隐藏 tablelist
			request.setAttribute("hidden_tablelist", request.getParameter("hidden_tablelist"));
		} catch (Exception e) {
			logger.error("getRoleList error." + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		}
		
		return "sys/sysRole_radio_list";
	}
}
