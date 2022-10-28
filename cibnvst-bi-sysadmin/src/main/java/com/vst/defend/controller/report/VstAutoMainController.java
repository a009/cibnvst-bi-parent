package com.vst.defend.controller.report;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.tools.io.ToolsIO;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.controller.BaseController;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.ErrorInfo;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.service.report.VstAutoMainService;
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
 * 自动化(主表)
 * @author lhp
 * @date 2017-4-28 上午11:13:37
 * @description
 * @version
 */
@Scope("session")
@Controller
@RequestMapping("/autoMain")
public class VstAutoMainController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = VstAutoMainController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 自动化(主表)Service接口
	 */
	@Resource
	private VstAutoMainService _vstAutoMainService;
	
	/**
	 * 查询自动化(主表)列表
	 * @return
	 */
	@RequestMapping("/findAutoMains.action")
	public String findAutoMains(){
		try {
			// 初始化
			this.initializeAction(_className);

			if("1".equals(request.getParameter("flag"))) {
				cutPage = new CutPage();
			}else {
				cutPage = new CutPage();
				// 查询条件
				Map<String, String> queryParam = new HashMap<String, String>();
				queryParam.put("vst_main_id", ToolsString.checkEmpty(request.getParameter("vst_main_id")));
				queryParam.put("vst_code", ToolsString.checkEmpty(request.getParameter("vst_code")));
				queryParam.put("vst_main_script", ToolsString.checkEmpty(request.getParameter("vst_main_script")));
				queryParam.put("vst_main_isPaging", ToolsString.checkEmpty(request.getParameter("vst_main_isPaging")));
				queryParam.put("vst_main_state", ToolsString.checkEmpty(request.getParameter("vst_main_state")));
				queryParam.put("vst_main_summary", ToolsString.checkEmpty(request.getParameter("vst_main_summary")));
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
				cutPage = _vstAutoMainService.getAutoMainList(cutPage, getUserSession(moduleId));
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
			logger.error("findAutoMains error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_autoMain");
		}
		return "report/autoMain_list";
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

			Object obj = session.getAttribute("cutPage_autoMain");
			if(obj != null){
				cutPage = (CutPage) obj;
			}else{
				cutPage = new CutPage();
				// 查询条件
				Map<String, String> queryParam = new HashMap<String, String>();
				queryParam.put("vst_main_id", ToolsString.checkEmpty(request.getParameter("vst_main_id")));
				queryParam.put("vst_code", ToolsString.checkEmpty(request.getParameter("vst_code")));
				queryParam.put("vst_main_script", ToolsString.checkEmpty(request.getParameter("vst_main_script")));
				queryParam.put("vst_main_isPaging", ToolsString.checkEmpty(request.getParameter("vst_main_isPaging")));
				queryParam.put("vst_main_state", ToolsString.checkEmpty(request.getParameter("vst_main_state")));
				queryParam.put("vst_main_summary", ToolsString.checkEmpty(request.getParameter("vst_main_summary")));
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
				cutPage = _vstAutoMainService.getAutoMainList(cutPage, getUserSession(moduleId));
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
			session.removeAttribute("cutPage_autoMain");
		}
		return "report/autoMain_list";
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
		} catch (Exception e) {
			logger.error("toAdd error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "autoMain/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_autoMain", cutPage);
		}
		return "report/autoMain_add";
	}
	
	/**
	 * 新增自动化(主表)
	 * @return
	 */
	@RequestMapping("/addAutoMain.action")
	public String addAutoMain(@RequestParam Map<String, Object> params){
		try {
			// 初始化Action类
			this.initializeAction(_className);

			String moduleId = String.valueOf(params.get("moduleId"));
			_vstAutoMainService.addAutoMain(params, getUserSession(moduleId));
		} catch (Exception e) {
			logger.error("addAutoMain error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "添加自动化(主表)失败！", toFind());
		}
		return this.moveToPage(VstConstants.OPERATE_SUCCESS, "添加自动化(主表)成功！", toFind());
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
			formMap = _vstAutoMainService.getAutoMainById(recordId);
			
			// 把老数据放入到session中
			session.setAttribute("oldData", formMap);
			request.setAttribute("formMap", formMap);
			// 转发当前模块ID
			request.setAttribute("moduleId", ToolsString.checkEmpty(request.getParameter("moduleId")));
		} catch (Exception e) {
			logger.error("toEdit error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "autoMain/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_autoMain", cutPage);
		}
		return "report/autoMain_edit";
	}
	
	/**
	 * 修改自动化(主表)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/editAutoMain.action")
	public String editAutoMain(@RequestParam Map<String, Object> params){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			
			Object obj = session.getAttribute("oldData");
			Map<String, Object> oldParam = null;
			if(obj != null){
				oldParam = (Map<String, Object>) obj;
			}
			
			String moduleId = String.valueOf(params.get("moduleId"));
			int result = _vstAutoMainService.updateAutoMain(oldParam, params, getUserSession(moduleId));
			// 转向结果页面
			return this.moveToPage(result == 1 ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR, "修改自动化(主表)"+ (result == 1 ? "成功！" : "失败！"), toFind());
		} catch (Exception e) {
			logger.error("editAutoMain error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "修改自动化(主表)失败！", toFind());
		} finally {
			// 移除老数据
			session.removeAttribute("oldData");
		}
	}
	
	/**
	 * 删除自动化(主表)
	 * @return
	 */
	@RequestMapping("/deleteAutoMain.action")
	public String deleteAutoMain(){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String ids = request.getParameter("recordId");
			if(ToolsString.isEmpty(ids)){
				// 转向失败页面
				return this.moveToPage(VstConstants.OPERATE_INFO, "您至少选中一条记录进行删除操作！", toFind());
			}
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _vstAutoMainService.deleteAutoMain(ids, getUserSession(moduleId));
			
			// 转向成功页面
			return this.moveToPage(result == ids.split(",").length ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR,(result > 0 ? "批量删除自动化(主表)成功！" : "批量删除自动化(主表)失败！"), toFind());
		} catch (Exception e) {
			logger.error("deleteAutoMain error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "批量删除自动化(主表)失败！", toFind());
		}
	}
	
	/**
	 * 修改自动化(主表)状态
	 * @return
	 */
	@RequestMapping("/modifyAutoMainState.action")
	public String modifyAutoMainState(){
		// 状态
		int state = -1;
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String recordId = request.getParameter("recordId");
			String recordState = request.getParameter("recordState");
			
			state = Integer.parseInt(recordState);
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _vstAutoMainService.modifyAutoMainState(recordId, state, getUserSession(moduleId));
			
			// 转向成功页面
			return this.moveToPage(result > 0 ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR, 
							(state == 1 ? "启用自动化(主表)" : "禁用自动化(主表)") + (result > 0 ? "成功！" : "失败！"), toFind());
 		} catch (Exception e) {
			logger.error("modifyAutoMainState error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, (state == 1 ? "启用自动化(主表)" : "禁用自动化(主表)") + "失败！", toFind());
		}
	}
	
	/**
	 * 批量修改自动化(主表)排序
	 * @return
	 */
	@RequestMapping("/modifyAutoMainIndexs.action")
	public String modifyAutoMainIndexs(){
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
			_vstAutoMainService.modifyAutoMainIndexs(ids, indexs, getUserSession(moduleId));
			out.print(true);
			out.flush();
		} catch (Exception e) {
			logger.error("modifyAutoMainIndexs error. ids = " + ids + ", indexs = " + indexs + ", ERROR:" + SystemException.getExceptionInfo(e));
			out.print(false);
		} finally {
			// 关流操作
			ToolsIO.closeStream(out);
		}
		return null;
	}
	
	/**
	 * 根据代码编号彻底删除
	 * @return
	 */
	@RequestMapping("/realDeleteByCode.action")
	public String realDeleteByCode(){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String vst_code = request.getParameter("recordId");
			if(ToolsString.isEmpty(vst_code)){
				// 转向失败页面
				return this.moveToPage(VstConstants.OPERATE_INFO, "您至少选中一条记录进行删除操作！", toFind());
			}
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _vstAutoMainService.realDeleteByCode(vst_code, getUserSession(moduleId));
			
			// 转向成功页面
			return this.moveToPage(result == vst_code.split(",").length ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR,(result > 0 ? "根据代码编号彻底删除成功！" : "根据代码编号彻底删除失败！"), toFind());
		} catch (Exception e) {
			logger.error("realDeleteByCode error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "根据代码编号彻底删除失败！", toFind());
		}
	}
	
	/**
	 * 跳转到查看页面
	 * @return
	 */
	@RequestMapping("/toLook.action")
	public String toLook(){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String vst_code = request.getParameter("recordId");
			// 转发 代码编号
			request.setAttribute("vst_code", vst_code);
			
			JSONObject json = _vstAutoMainService.getTreeByCode(vst_code);
			request.setAttribute("data", json);
			
			// 转发当前模块ID
			request.setAttribute("moduleId", ToolsString.checkEmpty(request.getParameter("moduleId")));
		} catch (Exception e) {
			logger.error("toLook error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "autoMain/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_autoMain", cutPage);
		}
		return "report/autoMain_look";
	}
	
	/**
	 * 校验代码编号是否重复
	 * @return
	 */
	@RequestMapping("/ajaxCheckCode.action")
	public String ajaxCheckCode(){
		// 输出流
		PrintWriter out = null;
		String vst_code = null;
		try {
			// 初始化Action类
			this.initializeAction(_className);
			out = response.getWriter();
			
			vst_code = request.getParameter("vst_code");
			
			Map<String, Object> params = new HashMap<String, Object>(1);
			params.put("vst_code_eq", vst_code);
			Map<String, Object> data = _vstAutoMainService.queryForMap(params, "vst_code", "vst_main_state");
			
			boolean flag = false;
			if(data != null && !data.isEmpty()){
				flag = true;
			}
			
			out.write(flag + "");
			out.flush();
		} catch (Exception e) {
			logger.error("ajaxCheckCode error. vst_code=" + vst_code + ", ERROR:" + e.getMessage());
		} finally {
			// 关流操作
			ToolsIO.closeStream(out);
		}
		return null;
	}
	
	/**
	 * 刷新自动化缓存
	 * @return
	 */
	@RequestMapping("/flushAutoCache.action")
	public String flushAutoCache(){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			boolean result = _vstAutoMainService.flushAutoCache(getUserSession(moduleId));
			
			// 转向成功页面
			return this.moveToPage(result ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR, 
							"刷新自动化缓存" + (result ? "成功！" : "失败！"), toFind());
 		} catch (Exception e) {
			logger.error("flushAutoCache error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "刷新自动化缓存失败！", toFind());
		}
	}
}
