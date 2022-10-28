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
import com.vst.defend.service.config.VstFeaturesConfigService;
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
 * 用户指标特征配置
 * @author lhp
 * @date 2018-12-13 下午05:47:07
 * @version
 */
@Scope("session")
@Controller
@RequestMapping("/featuresConfig")
public class VstFeaturesConfigController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = VstFeaturesConfigController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 用户指标特征配置Service接口
	 */
	@Resource
	private VstFeaturesConfigService _vstFeaturesConfigService;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询用户指标特征配置列表
	 * @return
	 */
	@RequestMapping("/findFeaturesConfigs.action")
	public String findFeaturesConfigs(){
		try {
			// 初始化
			this.initializeAction(_className);

			if("1".equals(request.getParameter("flag"))) {
				cutPage = new CutPage();
			}else {
				cutPage = new CutPage();
				// 查询条件
				Map<String, String> queryParam = new HashMap<String, String>();
				queryParam.put("vst_features_id", ToolsString.checkEmpty(request.getParameter("vst_features_id")));
				queryParam.put("vst_features_pkg", ToolsString.checkEmpty(request.getParameter("vst_features_pkg")));
				queryParam.put("vst_features_special_type", ToolsString.checkEmpty(request.getParameter("vst_features_special_type")));
				queryParam.put("vst_features_cid", ToolsString.checkEmpty(request.getParameter("vst_features_cid")));
				queryParam.put("vst_features_type", ToolsString.checkEmpty(request.getParameter("vst_features_type")));
				queryParam.put("vst_features_name", ToolsString.checkEmpty(request.getParameter("vst_features_name")));
				queryParam.put("vst_features_value", ToolsString.checkEmpty(request.getParameter("vst_features_value")));
				queryParam.put("vst_features_state", ToolsString.checkEmpty(request.getParameter("vst_features_state")));
				// 排序信息
				queryParam.put("orderBy", ToolsString.checkEmpty(request.getParameter("orderBy")));
				queryParam.put("order", ToolsString.checkEmpty(request.getParameter("order")));
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
				cutPage = _vstFeaturesConfigService.getFeaturesConfigList(cutPage, getUserSession(moduleId));
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
			
			// 获取所属专区
			Map<String, Object> params = new HashMap<String, Object>(4);
			params.put("vst_pkg", "0");
			params.put("vst_table_name", "vst_features_config");
			params.put("vst_column_name", "vst_features_special_type");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("specialTypes", _baseService.getDictionaryForMap(params));
			// 获取所属分类
			params.put("vst_column_name", "vst_features_cid");
			request.setAttribute("cids", _baseService.getDictionaryForMap(params));
			// 获取特征分类
			params.put("vst_column_name", "vst_features_type");
			request.setAttribute("types", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("findFeaturesConfigs error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_featuresConfig");
		}
		return "config/featuresConfig_list";
	}

	/**
	 * 查询用户指标特征配置列表
	 * @return
	 */
	@RequestMapping("/toFind.action")
	public String toFind(){
		try {
			// 初始化
			this.initializeAction(_className);

			Object obj = session.getAttribute("cutPage_featuresConfig");
			if(obj != null){
				cutPage = (CutPage) obj;
			}else{
				cutPage = new CutPage();
				// 查询条件
				Map<String, String> queryParam = new HashMap<String, String>();
				queryParam.put("vst_features_id", ToolsString.checkEmpty(request.getParameter("vst_features_id")));
				queryParam.put("vst_features_pkg", ToolsString.checkEmpty(request.getParameter("vst_features_pkg")));
				queryParam.put("vst_features_special_type", ToolsString.checkEmpty(request.getParameter("vst_features_special_type")));
				queryParam.put("vst_features_cid", ToolsString.checkEmpty(request.getParameter("vst_features_cid")));
				queryParam.put("vst_features_type", ToolsString.checkEmpty(request.getParameter("vst_features_type")));
				queryParam.put("vst_features_name", ToolsString.checkEmpty(request.getParameter("vst_features_name")));
				queryParam.put("vst_features_value", ToolsString.checkEmpty(request.getParameter("vst_features_value")));
				queryParam.put("vst_features_state", ToolsString.checkEmpty(request.getParameter("vst_features_state")));
				// 排序信息
				queryParam.put("orderBy", ToolsString.checkEmpty(request.getParameter("orderBy")));
				queryParam.put("order", ToolsString.checkEmpty(request.getParameter("order")));
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
				cutPage = _vstFeaturesConfigService.getFeaturesConfigList(cutPage, getUserSession(moduleId));
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

			// 获取所属专区
			Map<String, Object> params = new HashMap<String, Object>(4);
			params.put("vst_pkg", "0");
			params.put("vst_table_name", "vst_features_config");
			params.put("vst_column_name", "vst_features_special_type");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("specialTypes", _baseService.getDictionaryForMap(params));
			// 获取所属分类
			params.put("vst_column_name", "vst_features_cid");
			request.setAttribute("cids", _baseService.getDictionaryForMap(params));
			// 获取特征分类
			params.put("vst_column_name", "vst_features_type");
			request.setAttribute("types", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("toFind error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_featuresConfig");
		}
		return "config/featuresConfig_list";
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
			
			// 获取所属专区
			Map<String, Object> params = new HashMap<String, Object>(4);
			params.put("vst_pkg", "0");
			params.put("vst_table_name", "vst_features_config");
			params.put("vst_column_name", "vst_features_special_type");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("specialTypes", _baseService.getDictionaryForMap(params));
			// 获取所属分类
			params.put("vst_column_name", "vst_features_cid");
			request.setAttribute("cids", _baseService.getDictionaryForMap(params));
			// 获取特征分类
			params.put("vst_column_name", "vst_features_type");
			request.setAttribute("types", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("toAdd error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "featuresConfig/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_featuresConfig", cutPage);
		}
		return "config/featuresConfig_add";
	}
	
	/**
	 * 新增用户指标特征配置
	 * @return
	 */
	@RequestMapping("/addFeaturesConfig.action")
	public String addFeaturesConfig(@RequestParam Map<String, Object> params){
		try {
			// 初始化Action类
			this.initializeAction(_className);

			String moduleId = String.valueOf(params.get("moduleId"));
			_vstFeaturesConfigService.addFeaturesConfig(params, getUserSession(moduleId));
		} catch (Exception e) {
			logger.error("addFeaturesConfig error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "添加用户指标特征配置失败！", toFind());
		}
		return this.moveToPage(VstConstants.OPERATE_SUCCESS, "添加用户指标特征配置成功！", toFind());
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
			formMap = _vstFeaturesConfigService.getFeaturesConfigById(recordId);
			
			// 把老数据放入到session中
			session.setAttribute("oldData", formMap);
			request.setAttribute("formMap", formMap);
			// 转发当前模块ID
			request.setAttribute("moduleId", ToolsString.checkEmpty(request.getParameter("moduleId")));
			
			// 获取所属专区
			Map<String, Object> params = new HashMap<String, Object>(4);
			params.put("vst_pkg", "0");
			params.put("vst_table_name", "vst_features_config");
			params.put("vst_column_name", "vst_features_special_type");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("specialTypes", _baseService.getDictionaryForMap(params));
			// 获取所属分类
			params.put("vst_column_name", "vst_features_cid");
			request.setAttribute("cids", _baseService.getDictionaryForMap(params));
			// 获取特征分类
			params.put("vst_column_name", "vst_features_type");
			request.setAttribute("types", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("toEdit error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "featuresConfig/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_featuresConfig", cutPage);
		}
		return "config/featuresConfig_edit";
	}
	
	/**
	 * 修改用户指标特征配置
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/editFeaturesConfig.action")
	public String editFeaturesConfig(@RequestParam Map<String, Object> params){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			
			Object obj = session.getAttribute("oldData");
			Map<String, Object> oldParam = null;
			if(obj != null){
				oldParam = (Map<String, Object>) obj;
			}
			
			String moduleId = String.valueOf(params.get("moduleId"));
			int result = _vstFeaturesConfigService.updateFeaturesConfig(oldParam, params, getUserSession(moduleId));
			// 转向结果页面
			return this.moveToPage(result == 1 ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR, "修改用户指标特征配置"+ (result == 1 ? "成功！" : "失败！"), toFind());
		} catch (Exception e) {
			logger.error("editFeaturesConfig error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "修改用户指标特征配置失败！", toFind());
		} finally {
			// 移除老数据
			session.removeAttribute("oldData");
		}
	}
	
	/**
	 * 删除用户指标特征配置
	 * @return
	 */
	@RequestMapping("/deleteFeaturesConfig.action")
	public String deleteFeaturesConfig(){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String ids = request.getParameter("recordId");
			if(ToolsString.isEmpty(ids)){
				// 转向失败页面
				return this.moveToPage(VstConstants.OPERATE_INFO, "您至少选中一条记录进行删除操作！", toFind());
			}
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _vstFeaturesConfigService.deleteFeaturesConfig(ids, getUserSession(moduleId));
			
			// 转向成功页面
			return this.moveToPage(result == ids.split(",").length ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR,(result > 0 ? "批量删除用户指标特征配置成功！" : "批量删除用户指标特征配置失败！"), toFind());
		} catch (Exception e) {
			logger.error("deleteFeaturesConfig error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "批量删除用户指标特征配置失败！", toFind());
		}
	}
	
	/**
	 * 修改用户指标特征配置状态
	 * @return
	 */
	@RequestMapping("/modifyFeaturesConfigState.action")
	public String modifyFeaturesConfigState(){
		// 状态
		int state = -1;
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String recordId = request.getParameter("recordId");
			String recordState = request.getParameter("recordState");
			
			state = Integer.parseInt(recordState);
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _vstFeaturesConfigService.modifyFeaturesConfigState(recordId, state, getUserSession(moduleId));
			
			// 转向成功页面
			return this.moveToPage(result > 0 ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR, 
							(state == 1 ? "启用用户指标特征配置" : "禁用用户指标特征配置") + (result > 0 ? "成功！" : "失败！"), toFind());
 		} catch (Exception e) {
			logger.error("modifyFeaturesConfigState error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, (state == 1 ? "启用用户指标特征配置" : "禁用用户指标特征配置") + "失败！", toFind());
		}
	}
	
	/**
	 * 批量修改用户指标特征配置排序
	 * @return
	 */
	@RequestMapping("/modifyFeaturesConfigIndexs.action")
	public String modifyFeaturesConfigIndexs(){
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
			_vstFeaturesConfigService.modifyFeaturesConfigIndexs(ids, indexs, getUserSession(moduleId));
			out.print(true);
			out.flush();
		} catch (Exception e) {
			logger.error("modifyFeaturesConfigIndexs error. ids = " + ids + ", indexs = " + indexs + ", ERROR:" + SystemException.getExceptionInfo(e));
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
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "featuresConfig/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_featuresConfig", cutPage);
		}
		return "config/featuresConfig_batchAdd";
	}
	
	/**
	 * 批量添加用户指标特征配置
	 * @return
	 */
	@RequestMapping("/batchAddFeaturesConfig.action")
	public String batchAddFeaturesConfig(@RequestParam Map<String, Object> params){
		int result = 0;
		try {
			// 初始化Action类
			this.initializeAction(_className);

			String moduleId = String.valueOf(params.get("moduleId"));
			result = _vstFeaturesConfigService.batchAddFeaturesConfig(params, getUserSession(moduleId));
		} catch (Exception e) {
			logger.error("batchAddFeaturesConfig error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "批量添加用户指标特征配置失败！", toFind());
		}
		return this.moveToPage(VstConstants.OPERATE_SUCCESS, "批量添加用户指标特征配置成功"+result+"条！", toFind());
	}
}
