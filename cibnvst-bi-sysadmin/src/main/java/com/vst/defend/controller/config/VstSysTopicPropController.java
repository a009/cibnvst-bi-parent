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
import com.vst.defend.service.config.VstSysTopicPropService;
import com.vst.defend.service.config.VstSysTopicService;
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
 * topic配置属性
 * @author lhp
 * @date 2017-11-17 下午06:38:30
 * @version
 */
@Scope("session")
@Controller
@RequestMapping("/sysTopicProp")
public class VstSysTopicPropController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = VstSysTopicPropController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * topic配置属性Service接口
	 */
	@Resource
	private VstSysTopicPropService _vstSysTopicPropService;
	
	/**
	 * topic配置Service接口
	 */
	@Resource
	private VstSysTopicService _vstSysTopicService;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询topic配置属性列表
	 * @return
	 */
	@RequestMapping("/findSysTopicProps.action")
	public String findSysTopicProps(){
		try {
			// 初始化
			this.initializeAction(_className);

			if("1".equals(request.getParameter("flag"))) {
				cutPage = new CutPage();
			}else {
				cutPage = new CutPage();
				// 查询条件
				Map<String, String> queryParam = new HashMap<String, String>();
				queryParam.put("vst_prop_id", ToolsString.checkEmpty(request.getParameter("vst_prop_id")));
				queryParam.put("vst_topic_id", ToolsString.checkEmpty(request.getParameter("vst_topic_id")));
				queryParam.put("vst_prop_name", ToolsString.checkEmpty(request.getParameter("vst_prop_name")));
				queryParam.put("vst_prop_value_default", ToolsString.checkEmpty(request.getParameter("vst_prop_value_default")));
				queryParam.put("vst_prop_value_type", ToolsString.checkEmpty(request.getParameter("vst_prop_value_type")));
				queryParam.put("vst_prop_value_required", ToolsString.checkEmpty(request.getParameter("vst_prop_value_required")));
				queryParam.put("vst_prop_value_range", ToolsString.checkEmpty(request.getParameter("vst_prop_value_range")));
				queryParam.put("vst_prop_state", ToolsString.checkEmpty(request.getParameter("vst_prop_state")));
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
				cutPage = _vstSysTopicPropService.getSysTopicPropList(cutPage, getUserSession(moduleId));
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
			
			// 获取topic配置
			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("vst_topic_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("topics", _vstSysTopicService.queryForMap(params, "vst_topic_id", "vst_topic_name"));
			// 获取属性值类型
			params.clear();
			params.put("vst_table_name", "vst_sys_topic_prop");
			params.put("vst_column_name", "vst_prop_value_type");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("valueTypes", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("findSysTopicProps error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_sysTopicProp");
		}
		return "config/sysTopicProp_list";
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

			Object obj = session.getAttribute("cutPage_sysTopicProp");
			if(obj != null){
				cutPage = (CutPage) obj;
			}else{
				cutPage = new CutPage();
				// 查询条件
				Map<String, String> queryParam = new HashMap<String, String>();
				queryParam.put("vst_prop_id", ToolsString.checkEmpty(request.getParameter("vst_prop_id")));
				queryParam.put("vst_topic_id", ToolsString.checkEmpty(request.getParameter("vst_topic_id")));
				queryParam.put("vst_prop_name", ToolsString.checkEmpty(request.getParameter("vst_prop_name")));
				queryParam.put("vst_prop_value_default", ToolsString.checkEmpty(request.getParameter("vst_prop_value_default")));
				queryParam.put("vst_prop_value_type", ToolsString.checkEmpty(request.getParameter("vst_prop_value_type")));
				queryParam.put("vst_prop_value_required", ToolsString.checkEmpty(request.getParameter("vst_prop_value_required")));
				queryParam.put("vst_prop_value_range", ToolsString.checkEmpty(request.getParameter("vst_prop_value_range")));
				queryParam.put("vst_prop_state", ToolsString.checkEmpty(request.getParameter("vst_prop_state")));
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
				cutPage = _vstSysTopicPropService.getSysTopicPropList(cutPage, getUserSession(moduleId));
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

			// 获取topic配置
			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("vst_topic_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("topics", _vstSysTopicService.queryForMap(params, "vst_topic_id", "vst_topic_name"));
			// 获取属性值类型
			params.clear();
			params.put("vst_table_name", "vst_sys_topic_prop");
			params.put("vst_column_name", "vst_prop_value_type");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("valueTypes", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("toFind error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_sysTopicProp");
		}
		return "config/sysTopicProp_list";
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
			
			// 获取topic配置
			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("vst_topic_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("topics", _vstSysTopicService.queryForMap(params, "vst_topic_id", "vst_topic_name"));
			// 获取属性值类型
			params.clear();
			params.put("vst_table_name", "vst_sys_topic_prop");
			params.put("vst_column_name", "vst_prop_value_type");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("valueTypes", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("toAdd error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysTopicProp/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_sysTopicProp", cutPage);
		}
		return "config/sysTopicProp_add";
	}
	
	/**
	 * 新增topic配置属性
	 * @return
	 */
	@RequestMapping("/addSysTopicProp.action")
	public String addSysTopicProp(@RequestParam Map<String, Object> params){
		try {
			// 初始化Action类
			this.initializeAction(_className);

			String moduleId = String.valueOf(params.get("moduleId"));
			_vstSysTopicPropService.addSysTopicProp(params, getUserSession(moduleId));
		} catch (Exception e) {
			logger.error("addSysTopicProp error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "添加topic配置属性失败！", toFind());
		}
		return this.moveToPage(VstConstants.OPERATE_SUCCESS, "添加topic配置属性成功！", toFind());
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
			formMap = _vstSysTopicPropService.getSysTopicPropById(recordId);
			
			// 把老数据放入到session中
			session.setAttribute("oldData", formMap);
			request.setAttribute("formMap", formMap);
			// 转发当前模块ID
			request.setAttribute("moduleId", ToolsString.checkEmpty(request.getParameter("moduleId")));
			
			// 获取topic配置
			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("vst_topic_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("topics", _vstSysTopicService.queryForMap(params, "vst_topic_id", "vst_topic_name"));
			// 获取属性值类型
			params.clear();
			params.put("vst_table_name", "vst_sys_topic_prop");
			params.put("vst_column_name", "vst_prop_value_type");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("valueTypes", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("toEdit error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysTopicProp/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_sysTopicProp", cutPage);
		}
		return "config/sysTopicProp_edit";
	}
	
	/**
	 * 修改topic配置属性
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/editSysTopicProp.action")
	public String editSysTopicProp(@RequestParam Map<String, Object> params){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			
			Object obj = session.getAttribute("oldData");
			Map<String, Object> oldParam = null;
			if(obj != null){
				oldParam = (Map<String, Object>) obj;
			}
			
			String moduleId = String.valueOf(params.get("moduleId"));
			int result = _vstSysTopicPropService.updateSysTopicProp(oldParam, params, getUserSession(moduleId));
			// 转向结果页面
			return this.moveToPage(result == 1 ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR, "修改topic配置属性"+ (result == 1 ? "成功！" : "失败！"), toFind());
		} catch (Exception e) {
			logger.error("editSysTopicProp error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "修改topic配置属性失败！", toFind());
		} finally {
			// 移除老数据
			session.removeAttribute("oldData");
		}
	}
	
	/**
	 * 删除topic配置属性
	 * @return
	 */
	@RequestMapping("/deleteSysTopicProp.action")
	public String deleteSysTopicProp(){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String ids = request.getParameter("recordId");
			if(ToolsString.isEmpty(ids)){
				// 转向失败页面
				return this.moveToPage(VstConstants.OPERATE_INFO, "您至少选中一条记录进行删除操作！", toFind());
			}
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _vstSysTopicPropService.deleteSysTopicProp(ids, getUserSession(moduleId));
			
			// 转向成功页面
			return this.moveToPage(result == ids.split(",").length ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR,(result > 0 ? "批量删除topic配置属性成功！" : "批量删除topic配置属性失败！"), toFind());
		} catch (Exception e) {
			logger.error("deleteSysTopicProp error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "批量删除topic配置属性失败！", toFind());
		}
	}
	
	/**
	 * 修改topic配置属性状态
	 * @return
	 */
	@RequestMapping("/modifySysTopicPropState.action")
	public String modifySysTopicPropState(){
		// 状态
		int state = -1;
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String recordId = request.getParameter("recordId");
			String recordState = request.getParameter("recordState");
			
			state = Integer.parseInt(recordState);
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _vstSysTopicPropService.modifySysTopicPropState(recordId, state, getUserSession(moduleId));
			
			// 转向成功页面
			return this.moveToPage(result > 0 ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR, 
							(state == 1 ? "启用topic配置属性" : "禁用topic配置属性") + (result > 0 ? "成功！" : "失败！"), toFind());
 		} catch (Exception e) {
			logger.error("modifySysTopicPropState error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, (state == 1 ? "启用topic配置属性" : "禁用topic配置属性") + "失败！", toFind());
		}
	}
	
	/**
	 * 批量修改topic配置属性排序
	 * @return
	 */
	@RequestMapping("/modifySysTopicPropIndexs.action")
	public String modifySysTopicPropIndexs(){
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
			_vstSysTopicPropService.modifySysTopicPropIndexs(ids, indexs, getUserSession(moduleId));
			out.print(true);
			out.flush();
		} catch (Exception e) {
			logger.error("modifySysTopicPropIndexs error. ids = " + ids + ", indexs = " + indexs + ", ERROR:" + SystemException.getExceptionInfo(e));
			out.print(false);
		} finally {
			// 关流操作
			ToolsIO.closeStream(out);
		}
		return null;
	}
}
