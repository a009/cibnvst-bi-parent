package com.vst.defend.controller.config;

import com.vst.common.tools.io.ToolsIO;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.controller.BaseController;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.ErrorInfo;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.CutPage;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.service.config.VstSqlGroupService;
import com.vst.defend.service.config.VstSqlService;
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
 * sql分组配置
 * @author lhp
 * @date 2017-11-20 下午07:45:39
 * @version
 */
@Scope("session")
@Controller
@RequestMapping("/sqlGroup")
public class VstSqlGroupController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = VstSqlGroupController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * sql分组配置Service接口
	 */
	@Resource
	private VstSqlGroupService _vstSqlGroupService;
	
	/**
	 * spark任务Service接口
	 */
	@Resource
	private VstSqlService _vstSqlService;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询sql分组配置列表
	 * @return
	 */
	@RequestMapping("/findSqlGroups.action")
	public String findSqlGroups(){
		try {
			// 初始化
			this.initializeAction(_className);

			if("1".equals(request.getParameter("flag"))) {
				cutPage = new CutPage();
			}else {
				cutPage = new CutPage();
				// 查询条件
				Map<String, String> queryParam = new HashMap<String, String>();
				queryParam.put("vst_group_id", ToolsString.checkEmpty(request.getParameter("vst_group_id")));
				queryParam.put("vst_sql_id", ToolsString.checkEmpty(request.getParameter("vst_sql_id")));
				queryParam.put("vst_group_name", ToolsString.checkEmpty(request.getParameter("vst_group_name")));
				queryParam.put("vst_group_alias", ToolsString.checkEmpty(request.getParameter("vst_group_alias")));
				queryParam.put("vst_group_desc", ToolsString.checkEmpty(request.getParameter("vst_group_desc")));
				queryParam.put("vst_group_state", ToolsString.checkEmpty(request.getParameter("vst_group_state")));
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
				cutPage = _vstSqlGroupService.getSqlGroupList(cutPage, getUserSession(moduleId));
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
			
			// 获取任务类型
			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("vst_table_name", "vst_sql");
			params.put("vst_column_name", "vst_sql_type_parent");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("sqlTypes", _baseService.getDictionaryForMap(params));
			// 获取spark任务
			request.setAttribute("sqls", _vstSqlService.queryForMap(null, "vst_sql_id", null));
		} catch (Exception e) {
			logger.error("findSqlGroups error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_sqlGroup");
		}
		return "config/sqlGroup_list";
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

			Object obj = session.getAttribute("cutPage_sqlGroup");
			if(obj != null){
				cutPage = (CutPage) obj;
			}else{
				cutPage = new CutPage();
				// 查询条件
				Map<String, String> queryParam = new HashMap<String, String>();
				queryParam.put("vst_group_id", ToolsString.checkEmpty(request.getParameter("vst_group_id")));
				queryParam.put("vst_sql_id", ToolsString.checkEmpty(request.getParameter("vst_sql_id")));
				queryParam.put("vst_group_name", ToolsString.checkEmpty(request.getParameter("vst_group_name")));
				queryParam.put("vst_group_alias", ToolsString.checkEmpty(request.getParameter("vst_group_alias")));
				queryParam.put("vst_group_desc", ToolsString.checkEmpty(request.getParameter("vst_group_desc")));
				queryParam.put("vst_group_state", ToolsString.checkEmpty(request.getParameter("vst_group_state")));
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
				cutPage = _vstSqlGroupService.getSqlGroupList(cutPage, getUserSession(moduleId));
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

			// 获取任务类型
			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("vst_table_name", "vst_sql");
			params.put("vst_column_name", "vst_sql_type_parent");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("sqlTypes", _baseService.getDictionaryForMap(params));
			// 获取spark任务
			request.setAttribute("sqls", _vstSqlService.queryForMap(null, "vst_sql_id", null));
		} catch (Exception e) {
			logger.error("toFind error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_sqlGroup");
		}
		return "config/sqlGroup_list";
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
			request.setAttribute("moduleId", ToolsString.checkEmpty(request.getParameter("moduleId")));
			
			// 获取任务类型
			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("vst_table_name", "vst_sql");
			params.put("vst_column_name", "vst_sql_type_parent");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("sqlTypes", _baseService.getDictionaryForMap(params));
			// 获取spark任务
			request.setAttribute("sqls", _vstSqlService.queryForMap(null, "vst_sql_id", null));
		} catch (Exception e) {
			logger.error("toAdd error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sqlGroup/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_sqlGroup", cutPage);
		}
		return "config/sqlGroup_add";
	}
	
	/**
	 * 新增sql分组配置
	 * @return
	 */
	@RequestMapping("/addSqlGroup.action")
	public String addSqlGroup(@RequestParam Map<String, Object> params){
		try {
			// 初始化Action类
			this.initializeAction(_className);

			String moduleId = String.valueOf(params.get("moduleId"));
			_vstSqlGroupService.addSqlGroup(params, getUserSession(moduleId));
		} catch (Exception e) {
			logger.error("addSqlGroup error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "添加sql分组配置失败！", toFind());
		}
		return this.moveToPage(VstConstants.OPERATE_SUCCESS, "添加sql分组配置成功！", toFind());
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
			formMap = _vstSqlGroupService.getSqlGroupById(recordId);
			
			// 把老数据放入到session中
			session.setAttribute("oldData", formMap);
			request.setAttribute("formMap", formMap);
			// 转发当前模块ID
			request.setAttribute("moduleId", ToolsString.checkEmpty(request.getParameter("moduleId")));
			
			// 获取任务类型
			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("vst_table_name", "vst_sql");
			params.put("vst_column_name", "vst_sql_type_parent");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("sqlTypes", _baseService.getDictionaryForMap(params));
			// 获取spark任务
			request.setAttribute("sqls", _vstSqlService.queryForMap(null, "vst_sql_id", null));
		} catch (Exception e) {
			logger.error("toEdit error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sqlGroup/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_sqlGroup", cutPage);
		}
		return "config/sqlGroup_edit";
	}
	
	/**
	 * 修改sql分组配置
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/editSqlGroup.action")
	public String editSqlGroup(@RequestParam Map<String, Object> params){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			
			Object obj = session.getAttribute("oldData");
			Map<String, Object> oldParam = null;
			if(obj != null){
				oldParam = (Map<String, Object>) obj;
			}
			
			String moduleId = String.valueOf(params.get("moduleId"));
			int result = _vstSqlGroupService.updateSqlGroup(oldParam, params, getUserSession(moduleId));
			// 转向结果页面
			return this.moveToPage(result == 1 ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR, "修改sql分组配置"+ (result == 1 ? "成功！" : "失败！"), toFind());
		} catch (Exception e) {
			logger.error("editSqlGroup error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "修改sql分组配置失败！", toFind());
		} finally {
			// 移除老数据
			session.removeAttribute("oldData");
		}
	}
	
	/**
	 * 删除sql分组配置
	 * @return
	 */
	@RequestMapping("/deleteSqlGroup.action")
	public String deleteSqlGroup(){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String ids = request.getParameter("recordId");
			if(ToolsString.isEmpty(ids)){
				// 转向失败页面
				return this.moveToPage(VstConstants.OPERATE_INFO, "您至少选中一条记录进行删除操作！", toFind());
			}
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _vstSqlGroupService.deleteSqlGroup(ids, getUserSession(moduleId));
			
			// 转向成功页面
			return this.moveToPage(result == ids.split(",").length ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR,(result > 0 ? "批量删除sql分组配置成功！" : "批量删除sql分组配置失败！"), toFind());
		} catch (Exception e) {
			logger.error("deleteSqlGroup error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "批量删除sql分组配置失败！", toFind());
		}
	}
	
	/**
	 * 修改sql分组配置状态
	 * @return
	 */
	@RequestMapping("/modifySqlGroupState.action")
	public String modifySqlGroupState(){
		// 状态
		int state = -1;
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String recordId = request.getParameter("recordId");
			String recordState = request.getParameter("recordState");
			
			state = Integer.parseInt(recordState);
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _vstSqlGroupService.modifySqlGroupState(recordId, state, getUserSession(moduleId));
			
			// 转向成功页面
			return this.moveToPage(result > 0 ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR, 
							(state == 1 ? "启用sql分组配置" : "禁用sql分组配置") + (result > 0 ? "成功！" : "失败！"), toFind());
 		} catch (Exception e) {
			logger.error("modifySqlGroupState error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, (state == 1 ? "启用sql分组配置" : "禁用sql分组配置") + "失败！", toFind());
		}
	}
	
	/**
	 * 批量修改sql分组配置排序
	 * @return
	 */
	@RequestMapping("/modifySqlGroupIndexs.action")
	public String modifySqlGroupIndexs(){
		// 输出流
		PrintWriter out = null;
		String ids = null;
		String indexs = null;
		try {
			// 初始化
			this.initializeAction(_className); 
			out = response.getWriter();
			ids = request.getParameter("ids");
			indexs = request.getParameter("indexs");
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			_vstSqlGroupService.modifySqlGroupIndexs(ids, indexs, getUserSession(moduleId));
			out.print(true);
			out.flush();
		} catch (Exception e) {
			logger.error("modifySqlGroupIndexs error. ids = " + ids + ", indexs = " + indexs + ", ERROR:" + SystemException.getExceptionInfo(e));
			out.print(false);
		} finally {
			// 关流操作
			ToolsIO.closeStream(out);
		}
		return null;
	}
	
	/**
	 * 跳转到复制新增页面
	 * @return
	 */
	@RequestMapping("/toCopyAdd.action")
	public String toCopyAdd(){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String recordId = request.getParameter("recordId");
			formMap = _vstSqlGroupService.getSqlGroupById(recordId);
			
			// 把老数据放入到session中
			session.setAttribute("oldData", formMap);
			request.setAttribute("formMap", formMap);
			// 转发当前模块ID
			request.setAttribute("moduleId", ToolsString.checkEmpty(request.getParameter("moduleId")));
			
			// 获取任务类型
			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("vst_table_name", "vst_sql");
			params.put("vst_column_name", "vst_sql_type_parent");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("sqlTypes", _baseService.getDictionaryForMap(params));
			// 获取spark任务
			request.setAttribute("sqls", _vstSqlService.queryForMap(null, "vst_sql_id", null));
		} catch (Exception e) {
			logger.error("toCopyAdd error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sqlGroup/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_sqlGroup", cutPage);
		}
		return "config/sqlGroup_copyAdd";
	}
	
	/**
	 * 复制新增sql分组配置
	 * @return
	 */
	@RequestMapping("/copyAddSqlGroup.action")
	public String copyAddSqlGroup(@RequestParam Map<String, Object> params){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			
			String moduleId = String.valueOf(params.get("moduleId"));
			_vstSqlGroupService.copyAddSqlGroup(params, getUserSession(moduleId));
		} catch (Exception e) {
			logger.error("copyAddSqlGroup error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "复制新增sql分组配置失败！", toFind());
		} finally {
			// 移除老数据
			session.removeAttribute("oldData");
		}
		return this.moveToPage(VstConstants.OPERATE_SUCCESS, "复制新增sql分组配置成功！", toFind());
	}
}
