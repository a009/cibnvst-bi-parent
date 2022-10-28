package com.vst.defend.controller.sys;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
import com.vst.defend.service.sys.VstDictionaryService;

/**
 * 字典管理
 * @author lhp
 * @date 2017-6-14 下午12:16:51
 * @description
 * @version
 */
@Scope("session")
@Controller
@RequestMapping("/dictionary")
public class VstDictionaryController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = VstDictionaryController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 字典Service接口
	 */
	@Resource
	private VstDictionaryService _vstDictionaryService;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询字典列表
	 * @return
	 */
	@RequestMapping("/findDictionarys.action")
	public String findDictionarys(){
		try {
			// 初始化
			this.initializeAction(_className);

			if("1".equals(request.getParameter("flag"))) {
				cutPage = new CutPage();
			}else {
				cutPage = new CutPage();
				// 查询条件
				Map<String, String> queryParam = new HashMap<String, String>();
				queryParam.put("vst_id", ToolsString.checkEmpty(request.getParameter("vst_id")));
				queryParam.put("vst_pkg", ToolsString.checkEmpty(request.getParameter("vst_pkg")));
				queryParam.put("vst_table_name", ToolsString.checkEmpty(request.getParameter("vst_table_name")));
				queryParam.put("vst_column_name", ToolsString.checkEmpty(request.getParameter("vst_column_name")));
				queryParam.put("vst_state", ToolsString.checkEmpty(request.getParameter("vst_state")));
				queryParam.put("vst_property_key", ToolsString.checkEmpty(request.getParameter("vst_property_key")));
				queryParam.put("vst_property_value", ToolsString.checkEmpty(request.getParameter("vst_property_value")));
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
				cutPage = _vstDictionaryService.getDictionaryList(cutPage, getUserSession(moduleId));
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
			
			// 获取所有包名
			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("vst_table_name", "vst_sys");
			params.put("vst_column_name", "pkgName");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			session.setAttribute("pkgList", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("findDictionarys error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_dictionary");
		}
		return "sys/dictionary_list";
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

			Object obj = session.getAttribute("cutPage_dictionary");
			if(obj != null){
				cutPage = (CutPage) obj;
			}else{
				cutPage = new CutPage();
				// 查询条件
				Map<String, String> queryParam = new HashMap<String, String>();
				queryParam.put("vst_id", ToolsString.checkEmpty(request.getParameter("vst_id")));
				queryParam.put("vst_pkg", ToolsString.checkEmpty(request.getParameter("vst_pkg")));
				queryParam.put("vst_table_name", ToolsString.checkEmpty(request.getParameter("vst_table_name")));
				queryParam.put("vst_column_name", ToolsString.checkEmpty(request.getParameter("vst_column_name")));
				queryParam.put("vst_state", ToolsString.checkEmpty(request.getParameter("vst_state")));
				queryParam.put("vst_property_key", ToolsString.checkEmpty(request.getParameter("vst_property_key")));
				queryParam.put("vst_property_value", ToolsString.checkEmpty(request.getParameter("vst_property_value")));
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
				cutPage = _vstDictionaryService.getDictionaryList(cutPage, getUserSession(moduleId));
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

			// 获取所有包名
			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("vst_table_name", "vst_sys");
			params.put("vst_column_name", "pkgName");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			session.setAttribute("pkgList", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("toFind error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_dictionary");
		}
		return "sys/dictionary_list";
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
			
			// 获取所有包名
			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("vst_table_name", "vst_sys");
			params.put("vst_column_name", "pkgName");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			session.setAttribute("pkgList", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("toAdd error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "dictionary/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_dictionary", cutPage);
		}
		return "sys/dictionary_add";
	}
	
	/**
	 * 新增字典
	 * @return
	 */
	@RequestMapping("/addDictionary.action")
	public String addDictionary(@RequestParam Map<String, Object> params){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			
			String moduleId = String.valueOf(params.get("moduleId"));
			_vstDictionaryService.addDictionary(params, getUserSession(moduleId));
		} catch (Exception e) {
			logger.error("addDictionary error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "添加字典失败！", toFind());
		}
		return this.moveToPage(VstConstants.OPERATE_SUCCESS, "添加字典成功！", toFind());
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
			formMap = _vstDictionaryService.getDictionaryById(recordId);
			
			// 把老数据放入到session中
			session.setAttribute("oldData", formMap);
			request.setAttribute("formMap", formMap);
			// 转发当前模块ID
			request.setAttribute("moduleId", ToolsString.checkEmpty(request.getParameter("moduleId")));
			
			// 获取所有包名
			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("vst_table_name", "vst_sys");
			params.put("vst_column_name", "pkgName");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			session.setAttribute("pkgList", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("toEdit error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "dictionary/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_dictionary", cutPage);
		}
		return "sys/dictionary_edit";
	}
	
	/**
	 * 修改字典
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/editDictionary.action")
	public String editDictionary(@RequestParam Map<String, Object> params){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			
			Object obj = session.getAttribute("oldData");
			Map<String, Object> oldParam = null;
			if(obj != null){
				oldParam = (Map<String, Object>) obj;
			}
			
			String moduleId = String.valueOf(params.get("moduleId"));
			int result = _vstDictionaryService.updateDictionary(oldParam, params, getUserSession(moduleId));
			// 转向结果页面
			return this.moveToPage(result == 1 ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR, "修改字典"+ (result == 1 ? "成功！" : "失败！"), toFind());
		} catch (Exception e) {
			logger.error("editDictionary error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "修改字典失败！", toFind());
		} finally {
			// 移除老数据
			session.removeAttribute("oldData");
		}
	}
	
	/**
	 * 删除字典
	 * @return
	 */
	@RequestMapping("/deleteDictionary.action")
	public String deleteDictionary(){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String ids = request.getParameter("recordId");
			if(ToolsString.isEmpty(ids)){
				// 转向失败页面
				return this.moveToPage(VstConstants.OPERATE_INFO, "您至少选中一条记录进行删除操作！", toFind());
			}
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _vstDictionaryService.deleteDictionary(ids, getUserSession(moduleId));
			
			// 转向成功页面
			return this.moveToPage(result == ids.split(",").length ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR,(result > 0 ? "批量删除字典成功！" : "批量删除字典失败！"), toFind());
		} catch (Exception e) {
			logger.error("deleteDictionary error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "批量删除字典失败！", toFind());
		}
	}
	
	/**
	 * 修改字典状态
	 * @return
	 */
	@RequestMapping("/modifyDictionaryState.action")
	public String modifyDictionaryState(){
		// 状态
		int state = -1;
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String recordId = request.getParameter("recordId");
			String recordState = request.getParameter("recordState");
			
			state = Integer.parseInt(recordState);
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _vstDictionaryService.modifyDictionaryState(recordId, state, getUserSession(moduleId));
			
			// 转向成功页面
			return this.moveToPage(result > 0 ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR, 
							(state == 1 ? "启用字典" : "禁用字典") + (result > 0 ? "成功！" : "失败！"), toFind());
 		} catch (Exception e) {
			logger.error("modifyDictionaryState error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, (state == 1 ? "启用字典" : "禁用字典") + "失败！", toFind());
		}
	}
	
	/**
	 * 批量修改字典排序
	 * @return
	 */
	@RequestMapping("/modifyDictionaryIndexs.action")
	public String modifyDictionaryIndexs(){
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
			_vstDictionaryService.modifyDictionaryIndexs(ids, indexs, getUserSession(moduleId));
			out.print(true);
			out.flush();
		} catch (Exception e) {
			logger.error("modifyDictionaryIndexs error. ids = " + ids + ", indexs = " + indexs + ", ERROR:" + SystemException.getExceptionInfo(e));
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
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "dictionary/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_dictionary", cutPage);
		}
		return "sys/dictionary_batchAdd";
	}
	
	/**
	 * 批量添加字典
	 * @return
	 */
	@RequestMapping("/batchAddDictionary.action")
	public String batchAddDictionary(@RequestParam Map<String, Object> params){
		int result = 0;
		try {
			// 初始化Action类
			this.initializeAction(_className);

			String moduleId = String.valueOf(params.get("moduleId"));
			result = _vstDictionaryService.batchAddDictionary(params, getUserSession(moduleId));
		} catch (Exception e) {
			logger.error("batchAddDictionary error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "批量添加字典失败！", toFind());
		}
		return this.moveToPage(VstConstants.OPERATE_SUCCESS, "批量添加字典成功"+result+"条！", toFind());
	}
	
	/**
	 * 批量修改属性键
	 * @return
	 */
	@RequestMapping("/modifyDictionaryKeys.action")
	public String modifyDictionaryKeys(){
		// 输出流
		PrintWriter out = null;
		String ids = null;
		String keys = null;
		try {
			// 初始化
			this.initializeAction(_className); 
			out = response.getWriter();
			ids = request.getParameter("ids");
			keys = request.getParameter("keys");
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			_vstDictionaryService.modifyDictionaryKeys(ids, keys, getUserSession(moduleId));
			out.print(true);
			out.flush();
		} catch (Exception e) {
			logger.error("modifyDictionaryKeys error. ids = " + ids + ", keys = " + keys + ", ERROR:" + SystemException.getExceptionInfo(e));
			out.print(false);
		} finally {
			// 关流操作
			ToolsIO.closeStream(out);
		}
		return null;
	}
	
	/**
	 * 批量修改属性值
	 * @return
	 */
	@RequestMapping("/modifyDictionaryValues.action")
	public String modifyDictionaryValues(){
		// 输出流
		PrintWriter out = null;
		String ids = null;
		String values = null;
		try {
			// 初始化
			this.initializeAction(_className); 
			out = response.getWriter();
			ids = request.getParameter("ids");
			values = request.getParameter("values");
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			_vstDictionaryService.modifyDictionaryValues(ids, values, getUserSession(moduleId));
			out.print(true);
			out.flush();
		} catch (Exception e) {
			logger.error("modifyDictionaryValues error. ids = " + ids + ", values = " + values + ", ERROR:" + SystemException.getExceptionInfo(e));
			out.print(false);
		} finally {
			// 关流操作
			ToolsIO.closeStream(out);
		}
		return null;
	}
	
	/**
	 * 跳转到复制页面
	 * @return
	 */
	@RequestMapping("/toCopy.action")
	public String toCopy(){
		try {
			// 初始化
			this.initializeAction(_className);
			// 转发当前模块ID
			request.setAttribute("moduleId", ToolsString.checkEmpty(request.getParameter("moduleId")));
			
			// 获取需要复制对象的ID
			String recordId = request.getParameter("recordId");
			request.setAttribute("ids", recordId);
		} catch (Exception e) {
			logger.error("toCopy error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "dictionary/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_dictionary", cutPage);
		}
		return "sys/dictionary_copy";
	}
	
	/**
	 * 复制字典数据
	 * @param params
	 * @return
	 */
	@RequestMapping("/copyDictionary.action")
	public String copyDictionary(@RequestParam Map<String, Object> params){
		int result = 0;
		try {
			// 初始化
			this.initializeAction(_className);
			
			String moduleId = String.valueOf(params.get("moduleId"));
			
			String ids = ToolsString.checkEmpty(params.get("ids"));
			String dataType = ToolsString.checkEmpty(params.get("dataType"));
			String dataValue = ToolsString.checkEmpty(params.get("dataValue"));
			
			if(!ToolsString.isEmpty(dataType)){
				result = _vstDictionaryService.copyDictionary(ids, dataType, dataValue, getUserSession(moduleId));
			}
		} catch (Exception e) {
			logger.error("copyDictionary error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "复制字典数据失败！", toFind());
		}
		return this.moveToPage(VstConstants.OPERATE_SUCCESS, "成功复制"+result+"条数据！", toFind());
	}
	
	/**
	 * 替换信息
	 * @return
	 */
	@RequestMapping("/ajaxReplaceDictionary.action")
	public String ajaxReplaceDictionary(){
		// 输出流
		PrintWriter out = null;
		try{
			// 初始化
			this.initializeAction(_className);
			out = response.getWriter();
			
			String ids = request.getParameter("recordId");
			if(ToolsString.isEmpty(ids)){
				// 转向失败页面
				return this.moveToPage(VstConstants.OPERATE_INFO, "您至少选中一条记录进行替换操作！", toFind());
			}
			
			int replace_type = ToolsNumber.parseInt(request.getParameter("replace_type"));
			String replace_before = request.getParameter("replace_before");
			String replace_after = request.getParameter("replace_after");
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			//替换条件
			int count = _vstDictionaryService.replaceDictionary(ids, replace_type, replace_before, replace_after, getUserSession(moduleId));
			out.print(count);
			out.flush();
		}catch (Exception e) {
			logger.error("ajaxReplaceDictionary error. ERROR:" + SystemException.getExceptionInfo(e));
		} finally {
			// 关流操作
			ToolsIO.closeStream(out);
		}
		return null;
	}
}
