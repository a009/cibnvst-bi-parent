package com.vst.defend.controller.config;

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
import com.vst.defend.service.config.VstSqlFilterService;
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
 * sql筛选配置
 * @author lhp
 * @date 2017-11-30 下午05:36:53
 * @version
 */
@Scope("session")
@Controller
@RequestMapping("/sqlFilter")
public class VstSqlFilterController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = VstSqlFilterController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * sql筛选配置Service接口
	 */
	@Resource
	private VstSqlFilterService _vstSqlFilterService;
	
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
	 * 查询sql筛选配置列表
	 * @return
	 */
	@RequestMapping("/findSqlFilters.action")
	public String findSqlFilters(){
		try {
			// 初始化
			this.initializeAction(_className);

			if("1".equals(request.getParameter("flag"))) {
				cutPage = new CutPage();
			}else {
				cutPage = new CutPage();
				// 查询条件
				Map<String, String> queryParam = new HashMap<String, String>();
				queryParam.put("vst_filter_id", ToolsString.checkEmpty(request.getParameter("vst_filter_id")));
				queryParam.put("vst_sql_id", ToolsString.checkEmpty(request.getParameter("vst_sql_id")));
				queryParam.put("vst_filter_key", ToolsString.checkEmpty(request.getParameter("vst_filter_key")));
				queryParam.put("vst_filter_value", ToolsString.checkEmpty(request.getParameter("vst_filter_value")));
				queryParam.put("vst_filter_state", ToolsString.checkEmpty(request.getParameter("vst_filter_state")));
				queryParam.put("vst_filter_match_type", ToolsString.checkEmpty(request.getParameter("vst_filter_match_type")));
				queryParam.put("vst_filter_yes_type", ToolsString.checkEmpty(request.getParameter("vst_filter_yes_type")));
				queryParam.put("vst_filter_no_type", ToolsString.checkEmpty(request.getParameter("vst_filter_no_type")));
				queryParam.put("vst_filter_action_type", ToolsString.checkEmpty(request.getParameter("vst_filter_action_type")));
				queryParam.put("vst_filter_action_key", ToolsString.checkEmpty(request.getParameter("vst_filter_action_key")));
				queryParam.put("vst_filter_action_value", ToolsString.checkEmpty(request.getParameter("vst_filter_action_value")));
				queryParam.put("vst_filter_pkg", ToolsString.checkEmpty(request.getParameter("vst_filter_pkg")));
				queryParam.put("vst_filter_pkg_block", ToolsString.checkEmpty(request.getParameter("vst_filter_pkg_block")));
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
				cutPage = _vstSqlFilterService.getSqlFilterList(cutPage, getUserSession(moduleId));
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
			// 获取匹配类型
			params.put("vst_table_name", "vst_sql_filter");
			params.put("vst_column_name", "vst_filter_match_type");
			request.setAttribute("matchTypes", _baseService.getDictionaryForMap(params));
			// 获取操作类型
			params.put("vst_table_name", "vst_sql_filter");
			params.put("vst_column_name", "vst_filter_action_type");
			request.setAttribute("actionTypes", _baseService.getDictionaryForMap(params));
			// 获取包名
			params.put("vst_table_name", "vst_sys");
			params.put("vst_column_name", "pkgName");
			request.setAttribute("pkgs", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("findSqlFilters error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_sqlFilter");
		}
		return "config/sqlFilter_list";
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

			Object obj = session.getAttribute("cutPage_sqlFilter");
			if(obj != null){
				cutPage = (CutPage) obj;
			}else{
				cutPage = new CutPage();
				// 查询条件
				Map<String, String> queryParam = new HashMap<String, String>();
				queryParam.put("vst_filter_id", ToolsString.checkEmpty(request.getParameter("vst_filter_id")));
				queryParam.put("vst_sql_id", ToolsString.checkEmpty(request.getParameter("vst_sql_id")));
				queryParam.put("vst_filter_key", ToolsString.checkEmpty(request.getParameter("vst_filter_key")));
				queryParam.put("vst_filter_value", ToolsString.checkEmpty(request.getParameter("vst_filter_value")));
				queryParam.put("vst_filter_state", ToolsString.checkEmpty(request.getParameter("vst_filter_state")));
				queryParam.put("vst_filter_match_type", ToolsString.checkEmpty(request.getParameter("vst_filter_match_type")));
				queryParam.put("vst_filter_yes_type", ToolsString.checkEmpty(request.getParameter("vst_filter_yes_type")));
				queryParam.put("vst_filter_no_type", ToolsString.checkEmpty(request.getParameter("vst_filter_no_type")));
				queryParam.put("vst_filter_action_type", ToolsString.checkEmpty(request.getParameter("vst_filter_action_type")));
				queryParam.put("vst_filter_action_key", ToolsString.checkEmpty(request.getParameter("vst_filter_action_key")));
				queryParam.put("vst_filter_action_value", ToolsString.checkEmpty(request.getParameter("vst_filter_action_value")));
				queryParam.put("vst_filter_pkg", ToolsString.checkEmpty(request.getParameter("vst_filter_pkg")));
				queryParam.put("vst_filter_pkg_block", ToolsString.checkEmpty(request.getParameter("vst_filter_pkg_block")));
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
				cutPage = _vstSqlFilterService.getSqlFilterList(cutPage, getUserSession(moduleId));
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
			// 获取匹配类型
			params.put("vst_table_name", "vst_sql_filter");
			params.put("vst_column_name", "vst_filter_match_type");
			request.setAttribute("matchTypes", _baseService.getDictionaryForMap(params));
			// 获取操作类型
			params.put("vst_table_name", "vst_sql_filter");
			params.put("vst_column_name", "vst_filter_action_type");
			request.setAttribute("actionTypes", _baseService.getDictionaryForMap(params));
			// 获取包名
			params.put("vst_table_name", "vst_sys");
			params.put("vst_column_name", "pkgName");
			request.setAttribute("pkgs", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("toFind error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_sqlFilter");
		}
		return "config/sqlFilter_list";
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
			// 获取匹配类型
			params.put("vst_table_name", "vst_sql_filter");
			params.put("vst_column_name", "vst_filter_match_type");
			request.setAttribute("matchTypes", _baseService.getDictionaryForMap(params));
			// 获取操作类型
			params.put("vst_table_name", "vst_sql_filter");
			params.put("vst_column_name", "vst_filter_action_type");
			request.setAttribute("actionTypes", _baseService.getDictionaryForMap(params));
			// 获取包名
			params.put("vst_table_name", "vst_sys");
			params.put("vst_column_name", "pkgName");
			request.setAttribute("pkgs", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("toAdd error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sqlFilter/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_sqlFilter", cutPage);
		}
		return "config/sqlFilter_add";
	}
	
	/**
	 * 新增sql筛选配置
	 * @return
	 */
	@RequestMapping("/addSqlFilter.action")
	public String addSqlFilter(@RequestParam Map<String, Object> params){
		try {
			// 初始化Action类
			this.initializeAction(_className);

			String moduleId = String.valueOf(params.get("moduleId"));
			//获取适用包名
			String[] filterPkgs = request.getParameterValues("vst_filter_pkg");
			params.put("vst_filter_pkg", ConvertUtils.convertStringArray(filterPkgs));
			//获取屏蔽包名
			String[] bolckPkgs = request.getParameterValues("vst_filter_pkg_block");
			params.put("vst_filter_pkg_block", ConvertUtils.convertStringArray(bolckPkgs));
			_vstSqlFilterService.addSqlFilter(params, getUserSession(moduleId));
		} catch (Exception e) {
			logger.error("addSqlFilter error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "添加sql筛选配置失败！", toFind());
		}
		return this.moveToPage(VstConstants.OPERATE_SUCCESS, "添加sql筛选配置成功！", toFind());
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
			formMap = _vstSqlFilterService.getSqlFilterById(recordId);
			
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
			// 获取匹配类型
			params.put("vst_table_name", "vst_sql_filter");
			params.put("vst_column_name", "vst_filter_match_type");
			request.setAttribute("matchTypes", _baseService.getDictionaryForMap(params));
			// 获取操作类型
			params.put("vst_table_name", "vst_sql_filter");
			params.put("vst_column_name", "vst_filter_action_type");
			request.setAttribute("actionTypes", _baseService.getDictionaryForMap(params));
			// 获取包名
			params.put("vst_table_name", "vst_sys");
			params.put("vst_column_name", "pkgName");
			request.setAttribute("pkgs", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("toEdit error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sqlFilter/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_sqlFilter", cutPage);
		}
		return "config/sqlFilter_edit";
	}
	
	/**
	 * 修改sql筛选配置
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/editSqlFilter.action")
	public String editSqlFilter(@RequestParam Map<String, Object> params){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			
			Object obj = session.getAttribute("oldData");
			Map<String, Object> oldParam = null;
			if(obj != null){
				oldParam = (Map<String, Object>) obj;
			}
			
			String moduleId = String.valueOf(params.get("moduleId"));
			//获取适用包名
			String[] filterPkgs = request.getParameterValues("vst_filter_pkg");
			params.put("vst_filter_pkg", ConvertUtils.convertStringArray(filterPkgs));
			//获取屏蔽包名
			String[] bolckPkgs = request.getParameterValues("vst_filter_pkg_block");
			params.put("vst_filter_pkg_block", ConvertUtils.convertStringArray(bolckPkgs));
			int result = _vstSqlFilterService.updateSqlFilter(oldParam, params, getUserSession(moduleId));
			// 转向结果页面
			return this.moveToPage(result == 1 ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR, "修改sql筛选配置"+ (result == 1 ? "成功！" : "失败！"), toFind());
		} catch (Exception e) {
			logger.error("editSqlFilter error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "修改sql筛选配置失败！", toFind());
		} finally {
			// 移除老数据
			session.removeAttribute("oldData");
		}
	}
	
	/**
	 * 删除sql筛选配置
	 * @return
	 */
	@RequestMapping("/deleteSqlFilter.action")
	public String deleteSqlFilter(){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String ids = request.getParameter("recordId");
			if(ToolsString.isEmpty(ids)){
				// 转向失败页面
				return this.moveToPage(VstConstants.OPERATE_INFO, "您至少选中一条记录进行删除操作！", toFind());
			}
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _vstSqlFilterService.deleteSqlFilter(ids, getUserSession(moduleId));
			
			// 转向成功页面
			return this.moveToPage(result == ids.split(",").length ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR,(result > 0 ? "批量删除sql筛选配置成功！" : "批量删除sql筛选配置失败！"), toFind());
		} catch (Exception e) {
			logger.error("deleteSqlFilter error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "批量删除sql筛选配置失败！", toFind());
		}
	}
	
	/**
	 * 修改sql筛选配置状态
	 * @return
	 */
	@RequestMapping("/modifySqlFilterState.action")
	public String modifySqlFilterState(){
		// 状态
		int state = -1;
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String recordId = request.getParameter("recordId");
			String recordState = request.getParameter("recordState");
			
			state = Integer.parseInt(recordState);
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _vstSqlFilterService.modifySqlFilterState(recordId, state, getUserSession(moduleId));
			
			// 转向成功页面
			return this.moveToPage(result > 0 ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR, 
							(state == 1 ? "启用sql筛选配置" : "禁用sql筛选配置") + (result > 0 ? "成功！" : "失败！"), toFind());
 		} catch (Exception e) {
			logger.error("modifySqlFilterState error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, (state == 1 ? "启用sql筛选配置" : "禁用sql筛选配置") + "失败！", toFind());
		}
	}
	
	/**
	 * 批量修改sql筛选配置排序
	 * @return
	 */
	@RequestMapping("/modifySqlFilterIndexs.action")
	public String modifySqlFilterIndexs(){
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
			_vstSqlFilterService.modifySqlFilterIndexs(ids, indexs, getUserSession(moduleId));
			out.print(true);
			out.flush();
		} catch (Exception e) {
			logger.error("modifySqlFilterIndexs error. ids = " + ids + ", indexs = " + indexs + ", ERROR:" + SystemException.getExceptionInfo(e));
			out.print(false);
		} finally {
			// 关流操作
			ToolsIO.closeStream(out);
		}
		return null;
	}
	
	/**
	 * 跳转到批量添加页面
	 * @return
	 */
	@RequestMapping("/toBatchAdd.action")
	public String toBatchAdd(){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			// 转发当前模块ID
			request.setAttribute("moduleId", ToolsString.checkEmpty(request.getParameter("moduleId")));
		} catch (Exception e) {
			logger.error("toBatchAdd error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sqlFilter/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_sqlFilter", cutPage);
		}
		return "config/sqlFilter_batchAdd";
	}
	
	/**
	 * 批量添加sql筛选配置
	 * @return
	 */
	@RequestMapping("/batchAddSqlFilter.action")
	public String batchAddSqlFilter(@RequestParam Map<String, Object> params){
		int result = 0;
		try {
			// 初始化Action类
			this.initializeAction(_className);

			String moduleId = String.valueOf(params.get("moduleId"));
			result = _vstSqlFilterService.batchAddSqlFilter(params, getUserSession(moduleId));
		} catch (Exception e) {
			logger.error("batchAddSqlFilter error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "批量添加sql筛选配置失败！", toFind());
		}
		return this.moveToPage(VstConstants.OPERATE_SUCCESS, "批量添加sql筛选配置成功"+result+"条！", toFind());
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
			formMap = _vstSqlFilterService.getSqlFilterById(recordId);
			
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
			// 获取匹配类型
			params.put("vst_table_name", "vst_sql_filter");
			params.put("vst_column_name", "vst_filter_match_type");
			request.setAttribute("matchTypes", _baseService.getDictionaryForMap(params));
			// 获取操作类型
			params.put("vst_table_name", "vst_sql_filter");
			params.put("vst_column_name", "vst_filter_action_type");
			request.setAttribute("actionTypes", _baseService.getDictionaryForMap(params));
			// 获取包名
			params.put("vst_table_name", "vst_sys");
			params.put("vst_column_name", "pkgName");
			request.setAttribute("pkgs", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("toCopyAdd error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sqlFilter/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_sqlFilter", cutPage);
		}
		return "config/sqlFilter_copyAdd";
	}
	
	/**
	 * 复制新增sql筛选配置
	 * @return
	 */
	@RequestMapping("/copyAddSqlFilter.action")
	public String copyAddSqlFilter(@RequestParam Map<String, Object> params){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			
			String moduleId = String.valueOf(params.get("moduleId"));
			_vstSqlFilterService.copyAddSqlFilter(params, getUserSession(moduleId));
		} catch (Exception e) {
			logger.error("copyAddSqlFilter error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "复制新增sql筛选配置失败！", toFind());
		} finally {
			// 移除老数据
			session.removeAttribute("oldData");
		}
		return this.moveToPage(VstConstants.OPERATE_SUCCESS, "复制新增sql筛选配置成功！", toFind());
	}
}
