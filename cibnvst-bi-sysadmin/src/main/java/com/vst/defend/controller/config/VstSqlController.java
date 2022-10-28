package com.vst.defend.controller.config;

import com.alibaba.fastjson.JSONObject;
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
import com.vst.defend.service.config.VstSqlService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * spark任务
 * @author lhp
 * @date 2017-11-20 下午04:31:56
 * @version
 */
@Scope("session")
@Controller
@RequestMapping("/sql")
public class VstSqlController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = VstSqlController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
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
	 * 查询spark任务列表
	 * @return
	 */
	@RequestMapping("/findSqls.action")
	public String findSqls(){
		try {
			// 初始化
			this.initializeAction(_className);

			if("1".equals(request.getParameter("flag"))) {
				cutPage = new CutPage();
			}else {
				cutPage = new CutPage();
				// 查询条件
				Map<String, String> queryParam = new HashMap<String, String>();
				queryParam.put("vst_sql_id", ToolsString.checkEmpty(request.getParameter("vst_sql_id")));
				queryParam.put("vst_sql_pid", ToolsString.checkEmpty(request.getParameter("vst_sql_pid")));
				queryParam.put("vst_sql_name", ToolsString.checkEmpty(request.getParameter("vst_sql_name")));
				queryParam.put("vst_sql_type", ToolsString.checkEmpty(request.getParameter("vst_sql_type")));
				queryParam.put("vst_sql_state", ToolsString.checkEmpty(request.getParameter("vst_sql_state")));
				queryParam.put("vst_sql_db", ToolsString.checkEmpty(request.getParameter("vst_sql_db")));
				queryParam.put("vst_sql_run_model", ToolsString.checkEmpty(request.getParameter("vst_sql_run_model")));
				queryParam.put("vst_sql_is_format", ToolsString.checkEmpty(request.getParameter("vst_sql_is_format")));
				queryParam.put("vst_sql_table", ToolsString.checkEmpty(request.getParameter("vst_sql_table")));
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
				cutPage = _vstSqlService.getSqlList(cutPage, getUserSession(moduleId));
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
			
			// 获取父级任务
			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("vst_sql_pid", "0");
			request.setAttribute("parents", _vstSqlService.queryForMap(params, "vst_sql_id", null));
			// 获取任务类型
			params.clear();
			params.put("vst_table_name", "vst_sql");
			params.put("vst_column_name", "vst_sql_type_parent");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("parentTypes", _baseService.getDictionaryForMap(params));
			params.put("vst_table_name", "vst_sql");
			params.put("vst_column_name", "vst_sql_type_child");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("childTypes", _baseService.getDictionaryForMap(params));
			// 获取保存数据源
			params.put("vst_table_name", "vst_sql");
			params.put("vst_column_name", "vst_sql_db");
			request.setAttribute("dbs", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("findSqls error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_sql");
		}
		return "config/sql_list";
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

			Object obj = session.getAttribute("cutPage_sql");
			if(obj != null){
				cutPage = (CutPage) obj;
			}else{
				cutPage = new CutPage();
				// 查询条件
				Map<String, String> queryParam = new HashMap<String, String>();
				queryParam.put("vst_sql_id", ToolsString.checkEmpty(request.getParameter("vst_sql_id")));
				queryParam.put("vst_sql_pid", ToolsString.checkEmpty(request.getParameter("vst_sql_pid")));
				queryParam.put("vst_sql_name", ToolsString.checkEmpty(request.getParameter("vst_sql_name")));
				queryParam.put("vst_sql_type", ToolsString.checkEmpty(request.getParameter("vst_sql_type")));
				queryParam.put("vst_sql_state", ToolsString.checkEmpty(request.getParameter("vst_sql_state")));
				queryParam.put("vst_sql_db", ToolsString.checkEmpty(request.getParameter("vst_sql_db")));
				queryParam.put("vst_sql_run_model", ToolsString.checkEmpty(request.getParameter("vst_sql_run_model")));
				queryParam.put("vst_sql_is_format", ToolsString.checkEmpty(request.getParameter("vst_sql_is_format")));
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
				cutPage = _vstSqlService.getSqlList(cutPage, getUserSession(moduleId));
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

			// 获取父级任务
			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("vst_sql_pid", "0");
			request.setAttribute("parents", _vstSqlService.queryForMap(params, "vst_sql_id", null));
			// 获取任务类型
			params.clear();
			params.put("vst_table_name", "vst_sql");
			params.put("vst_column_name", "vst_sql_type_parent");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("parentTypes", _baseService.getDictionaryForMap(params));
			params.put("vst_table_name", "vst_sql");
			params.put("vst_column_name", "vst_sql_type_child");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("childTypes", _baseService.getDictionaryForMap(params));
			// 获取保存数据源
			params.put("vst_table_name", "vst_sql");
			params.put("vst_column_name", "vst_sql_db");
			request.setAttribute("dbs", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("toFind error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_sql");
		}
		return "config/sql_list";
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
			
			// 获取父级任务
			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("vst_sql_pid", "0");
			request.setAttribute("parents", _vstSqlService.queryForMap(params, "vst_sql_id", null));
			// 获取任务类型
			params.clear();
			params.put("vst_table_name", "vst_sql");
			params.put("vst_column_name", "vst_sql_type_parent");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("parentTypes", _baseService.getDictionaryForMap(params));
			params.put("vst_table_name", "vst_sql");
			params.put("vst_column_name", "vst_sql_type_child");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("childTypes", _baseService.getDictionaryForMap(params));
			// 获取保存数据源
			params.put("vst_table_name", "vst_sql");
			params.put("vst_column_name", "vst_sql_db");
			request.setAttribute("dbs", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("toAdd error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sql/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_sql", cutPage);
		}
		return "config/sql_add";
	}
	
	/**
	 * 新增spark任务
	 * @return
	 */
	@RequestMapping("/addSql.action")
	public String addSql(@RequestParam Map<String, Object> params){
		try {
			// 初始化Action类
			this.initializeAction(_className);

			String moduleId = String.valueOf(params.get("moduleId"));
			_vstSqlService.addSql(params, getUserSession(moduleId));
		} catch (Exception e) {
			logger.error("addSql error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "添加spark任务失败！", toFind());
		}
		return this.moveToPage(VstConstants.OPERATE_SUCCESS, "添加spark任务成功！", toFind());
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
			formMap = _vstSqlService.getSqlById(recordId);
			
			// 把老数据放入到session中
			session.setAttribute("oldData", formMap);
			request.setAttribute("formMap", formMap);
			// 转发当前模块ID
			request.setAttribute("moduleId", ToolsString.checkEmpty(request.getParameter("moduleId")));
			
			// 获取父级任务
			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("vst_sql_pid", "0");
			request.setAttribute("parents", _vstSqlService.queryForMap(params, "vst_sql_id", null));
			// 获取任务类型
			params.clear();
			params.put("vst_table_name", "vst_sql");
			params.put("vst_column_name", "vst_sql_type_parent");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("parentTypes", _baseService.getDictionaryForMap(params));
			params.put("vst_table_name", "vst_sql");
			params.put("vst_column_name", "vst_sql_type_child");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("childTypes", _baseService.getDictionaryForMap(params));
			// 获取保存数据源
			params.put("vst_table_name", "vst_sql");
			params.put("vst_column_name", "vst_sql_db");
			request.setAttribute("dbs", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("toEdit error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sql/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_sql", cutPage);
		}
		return "config/sql_edit";
	}
	
	/**
	 * 修改spark任务
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/editSql.action")
	public String editSql(@RequestParam Map<String, Object> params){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			
			Object obj = session.getAttribute("oldData");
			Map<String, Object> oldParam = null;
			if(obj != null){
				oldParam = (Map<String, Object>) obj;
			}
			
			String moduleId = String.valueOf(params.get("moduleId"));
			int result = _vstSqlService.updateSql(oldParam, params, getUserSession(moduleId));
			// 转向结果页面
			return this.moveToPage(result == 1 ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR, "修改spark任务"+ (result == 1 ? "成功！" : "失败！"), toFind());
		} catch (Exception e) {
			logger.error("editSql error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "修改spark任务失败！", toFind());
		} finally {
			// 移除老数据
			session.removeAttribute("oldData");
		}
	}
	
	/**
	 * 删除spark任务
	 * @return
	 */
	@RequestMapping("/deleteSql.action")
	public String deleteSql(){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String ids = request.getParameter("recordId");
			if(ToolsString.isEmpty(ids)){
				// 转向失败页面
				return this.moveToPage(VstConstants.OPERATE_INFO, "您至少选中一条记录进行删除操作！", toFind());
			}
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _vstSqlService.deleteSql(ids, getUserSession(moduleId));
			
			// 转向成功页面
			return this.moveToPage(result == ids.split(",").length ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR,(result > 0 ? "批量删除spark任务成功！" : "批量删除spark任务失败！"), toFind());
		} catch (Exception e) {
			logger.error("deleteSql error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "批量删除spark任务失败！", toFind());
		}
	}
	
	/**
	 * 修改spark任务状态
	 * @return
	 */
	@RequestMapping("/modifySqlState.action")
	public String modifySqlState(){
		// 状态
		int state = -1;
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String recordId = request.getParameter("recordId");
			String recordState = request.getParameter("recordState");
			
			state = Integer.parseInt(recordState);
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _vstSqlService.modifySqlState(recordId, state, getUserSession(moduleId));
			
			// 转向成功页面
			return this.moveToPage(result > 0 ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR, 
							(state == 1 ? "启用spark任务" : "禁用spark任务") + (result > 0 ? "成功！" : "失败！"), toFind());
 		} catch (Exception e) {
			logger.error("modifySqlState error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, (state == 1 ? "启用spark任务" : "禁用spark任务") + "失败！", toFind());
		}
	}
	
	/**
	 * 批量修改spark任务排序
	 * @return
	 */
	@RequestMapping("/modifySqlIndexs.action")
	public String modifySqlIndexs(){
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
			_vstSqlService.modifySqlIndexs(ids, indexs, getUserSession(moduleId));
			out.print(true);
			out.flush();
		} catch (Exception e) {
			logger.error("modifySqlIndexs error. ids = " + ids + ", indexs = " + indexs + ", ERROR:" + SystemException.getExceptionInfo(e));
			out.print(false);
		} finally {
			// 关流操作
			ToolsIO.closeStream(out);
		}
		return null;
	}
	
	/**
	 * 批量修改spark任务优先级
	 * @return
	 */
	@RequestMapping("/modifySqlPrioritys.action")
	public String modifySqlPrioritys(){
		// 输出流
		PrintWriter out = null;
		String ids = null;
		String prioritys = null;
		try {
			// 初始化
			this.initializeAction(_className);
			out = response.getWriter();
			ids = request.getParameter("ids");
			prioritys = request.getParameter("prioritys");
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			_vstSqlService.modifySqlPrioritys(ids, prioritys, getUserSession(moduleId));
			out.print(true);
			out.flush();
		} catch (Exception e) {
			logger.error("modifySqlPrioritys error. ids = " + ids + ", prioritys = " + prioritys + ", ERROR:" + SystemException.getExceptionInfo(e));
			out.print(false);
		} finally {
			// 关流操作
			ToolsIO.closeStream(out);
		}
		return null;
	}
	
	/**
	 * 动态获取spark任务
	 * @return
	 */
	@RequestMapping("/ajaxGetSql.action")
	public String ajaxGetSql(){
		JSONObject json = new JSONObject();
		try {
			// 初始化
			this.initializeAction(_className);
			
			String vst_sql_type = ToolsString.checkEmpty(request.getParameter("vst_sql_type"));
			
			Map<String, Object> params = new HashMap<String, Object>(1);
			params.put("vst_sql_type", vst_sql_type);
			Map<String, Object> sqls = _vstSqlService.getSqlMap(params);
			
			if(sqls != null && !sqls.isEmpty()){
				json.put("code", 100);
				json.put("data", sqls);
			}else{
				json.put("code", 201);
				json.put("data", Collections.EMPTY_MAP);
			}
		} catch (Exception e) {
			json.put("code", 301);
			json.put("msg", "系统异常");
			logger.error("ajaxGetSql error. ERROR:" + SystemException.getExceptionInfo(e));
		}
		printJson(json.toJSONString());
		return null;
	}
}
