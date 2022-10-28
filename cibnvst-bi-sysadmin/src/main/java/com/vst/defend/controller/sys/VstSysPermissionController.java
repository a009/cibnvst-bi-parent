package com.vst.defend.controller.sys;

import com.vst.common.tools.io.ToolsIO;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.controller.BaseController;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.ErrorInfo;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.ConvertUtils;
import com.vst.defend.communal.util.CutPage;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.service.sys.VstSysPermissionService;
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
 * 权限管理
 * @author lhp
 * @date 2017-4-13 下午05:51:54
 * @description
 * @version
 */
@Scope("session")
@Controller
@RequestMapping("/sysPermission")
public class VstSysPermissionController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = VstSysRoleController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 权限Service接口
	 */
	@Resource
	private VstSysPermissionService _vstSysPermissionService;
	
	/**
	 * 角色Service接口
	 */
	@Resource
	private VstSysRoleService _vstSysRoleService;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询权限列表
	 * @return
	 */
	@RequestMapping("/findPermissions.action")
	public String findPermissions(){
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
				queryParam.put("vst_module_name", ToolsString.checkEmpty(request.getParameter("vst_module_name")));
				queryParam.put("vst_button_name", ToolsString.checkEmpty(request.getParameter("vst_button_name")));
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
				cutPage = _vstSysPermissionService.getSysPermissionList(cutPage, getUserSession(moduleId));
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
			
			// 获取角色列表
			Map<String, Object> params = new HashMap<String, Object>(1);
			params.put("vst_role_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("roles", _vstSysRoleService.queryForMap(params, "vst_role_id", "vst_role_name"));
		} catch (Exception e) {
			logger.error("findPermissions error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_permission");
		}
		return "sys/sysPermission_list";
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

			Object obj = session.getAttribute("cutPage_permission");
			if(obj != null){
				cutPage = (CutPage) obj;
			}else{
				cutPage = new CutPage();
				// 查询条件
				Map<String, String> queryParam = new HashMap<String, String>();
				queryParam.put("vst_role_id", ToolsString.checkEmpty(request.getParameter("vst_role_id")));
				queryParam.put("vst_module_name", ToolsString.checkEmpty(request.getParameter("vst_module_name")));
				queryParam.put("vst_button_name", ToolsString.checkEmpty(request.getParameter("vst_button_name")));
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
				cutPage = _vstSysPermissionService.getSysPermissionList(cutPage, getUserSession(moduleId));
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

			// 获取角色列表
			Map<String, Object> params = new HashMap<String, Object>(1);
			params.put("vst_role_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("roles", _vstSysRoleService.queryForMap(params, "vst_role_id", "vst_role_name"));
		} catch (Exception e) {
			logger.error("toFind error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_permission");
		}
		return "sys/sysPermission_list";
	}
	
	/**
	 * 跳转到添加模块界面
	 * @return
	 */
	@RequestMapping("/toAdd.action")
	public String toAdd(){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			// 转发当前模块ID
			request.setAttribute("moduleId", ToolsString.checkEmpty(request.getParameter("moduleId")));
			// 获取所有的角色名称
//			request.setAttribute("roles", getUserSession().getSysRoles());
			request.setAttribute("roles", _baseService.getAllSysRolesName());
			// 查询所有的模块
//			request.setAttribute("modules", getUserSession().getModules());
			request.setAttribute("modules", _baseService.getAllSysModulesName());
			// 查询所有的按钮
			request.setAttribute("buttons", _baseService.getAllSysButtonsName());
		} catch (Exception e) {
			logger.error("toAdd error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysPermission/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_permission", cutPage);
		}
		return "sys/sysPermission_add";
	}
	
	/**
	 * 校验该组合是否存在
	 * @return
	 */
	@RequestMapping("/ajaxCheckName.action")
	public String ajaxCheckName(){
		// 输出流
		PrintWriter out = null;
		String name = null;
		try {
			// 初始化Action类
			this.initializeAction(_className);
			out = response.getWriter();
			name = request.getParameter("name");
			boolean flag = _vstSysPermissionService.checkNameExist(name);
			out.print(flag);
			out.flush();
		} catch (Exception e) {
			logger.error("ajaxCheckName error. name=" + name + ", ERROR:" + e.getMessage());
		} finally {
			// 关流操作
			ToolsIO.closeStream(out);
		}
		return null;
	}
	
	/**
	 * 新增权限
	 * @return
	 */
	@RequestMapping("/addPermission.action")
	public String addPermission(@RequestParam Map<String, Object> params){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			
			String moduleId = String.valueOf(params.get("moduleId"));
			String[] buttons = request.getParameterValues("vst_button_id");
			params.put("vst_button_id", ConvertUtils.convertStringArray(buttons));
			_vstSysPermissionService.addSysPermission(params, getUserSession(moduleId));
		} catch (Exception e) {
			logger.error("addPermission error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "添加权限映射失败！", toFind());
		}
		return this.moveToPage(VstConstants.OPERATE_SUCCESS, "添加权限映射成功！", toFind());
	}
	
	/**
	 * 删除权限
	 * @return
	 */
	@RequestMapping("/deletePermissions.action")
	public String deletePermissions(){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String ids = request.getParameter("recordId");
			if(ToolsString.isEmpty(ids)){
				// 转向失败页面
				return this.moveToPage(VstConstants.OPERATE_INFO, "您至少选中一条记录进行删除操作！", toFind());
			}
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _vstSysPermissionService.batchDelSysPermissions(ids, getUserSession(moduleId));
			
			// 转向成功页面
			return this.moveToPage(result == ids.split(",").length ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR, 
							(result > 0 ? "删除权限映射成功！" : "删除权限映射失败！"), toFind());
		} catch (Exception e) {
			logger.error("deletePermissions error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "删除权限映射失败！", toFind());
		}
	}
}
