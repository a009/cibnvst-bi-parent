package com.vst.defend.controller.report;

import com.vst.common.tools.io.ToolsIO;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.controller.BaseController;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.ErrorInfo;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.service.report.VstAutoOverlayService;
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
 * 自动化(续加)
 * @author lhp
 * @date 2017-4-28 上午11:13:37
 * @description
 * @version
 */
@Scope("session")
@Controller
@RequestMapping("/autoOverlay")
public class VstAutoOverlayController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = VstAutoOverlayController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 自动化(续加)Service接口
	 */
	@Resource
	private VstAutoOverlayService _vstAutoOverlayService;
	
	/**
	 * 查询自动化(续加)列表
	 * @return
	 */
	@RequestMapping("/findAutoOverlays.action")
	public String findAutoOverlays(){
		try {
			// 初始化
			this.initializeAction(_className);

			if("1".equals(request.getParameter("flag"))) {
				cutPage = new CutPage();
			}else {
				cutPage = new CutPage();
				// 查询条件
				Map<String, String> queryParam = new HashMap<String, String>();
				queryParam.put("vst_overlay_id", ToolsString.checkEmpty(request.getParameter("vst_overlay_id")));
				queryParam.put("vst_code", ToolsString.checkEmpty(request.getParameter("vst_code")));
				queryParam.put("vst_overlay_script", ToolsString.checkEmpty(request.getParameter("vst_overlay_script")));
				queryParam.put("vst_overlay_state", ToolsString.checkEmpty(request.getParameter("vst_overlay_state")));
				queryParam.put("vst_overlay_summary", ToolsString.checkEmpty(request.getParameter("vst_overlay_summary")));
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
				cutPage = _vstAutoOverlayService.getAutoOverlayList(cutPage, getUserSession(moduleId));
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
			logger.error("findAutoOverlays error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_autoOverlay");
		}
		return "report/autoOverlay_list";
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

			Object obj = session.getAttribute("cutPage_autoOverlay");
			if(obj != null){
				cutPage = (CutPage) obj;
			}else{
				cutPage = new CutPage();
				// 查询条件
				Map<String, String> queryParam = new HashMap<String, String>();
				queryParam.put("vst_overlay_id", ToolsString.checkEmpty(request.getParameter("vst_overlay_id")));
				queryParam.put("vst_code", ToolsString.checkEmpty(request.getParameter("vst_code")));
				queryParam.put("vst_overlay_script", ToolsString.checkEmpty(request.getParameter("vst_overlay_script")));
				queryParam.put("vst_overlay_state", ToolsString.checkEmpty(request.getParameter("vst_overlay_state")));
				queryParam.put("vst_overlay_summary", ToolsString.checkEmpty(request.getParameter("vst_overlay_summary")));
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
				cutPage = _vstAutoOverlayService.getAutoOverlayList(cutPage, getUserSession(moduleId));
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
			session.removeAttribute("cutPage_autoOverlay");
		}
		return "report/autoOverlay_list";
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
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "autoOverlay/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_autoOverlay", cutPage);
		}
		return "report/autoOverlay_add";
	}
	
	/**
	 * 新增自动化(续加)
	 * @return
	 */
	@RequestMapping("/addAutoOverlay.action")
	public String addAutoOverlay(@RequestParam Map<String, Object> params){
		try {
			// 初始化Action类
			this.initializeAction(_className);

			String moduleId = String.valueOf(params.get("moduleId"));
			_vstAutoOverlayService.addAutoOverlay(params, getUserSession(moduleId));
		} catch (Exception e) {
			logger.error("addAutoOverlay error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "添加自动化(续加)失败！", toFind());
		}
		return this.moveToPage(VstConstants.OPERATE_SUCCESS, "添加自动化(续加)成功！", toFind());
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
			formMap = _vstAutoOverlayService.getAutoOverlayById(recordId);
			
			// 把老数据放入到session中
			session.setAttribute("oldData", formMap);
			request.setAttribute("formMap", formMap);
			// 转发当前模块ID
			request.setAttribute("moduleId", ToolsString.checkEmpty(request.getParameter("moduleId")));
		} catch (Exception e) {
			logger.error("toEdit error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "autoOverlay/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_autoOverlay", cutPage);
		}
		return "report/autoOverlay_edit";
	}
	
	/**
	 * 修改自动化(续加)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/editAutoOverlay.action")
	public String editAutoOverlay(@RequestParam Map<String, Object> params){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			
			Object obj = session.getAttribute("oldData");
			Map<String, Object> oldParam = null;
			if(obj != null){
				oldParam = (Map<String, Object>) obj;
			}
			
			String moduleId = String.valueOf(params.get("moduleId"));
			int result = _vstAutoOverlayService.updateAutoOverlay(oldParam, params, getUserSession(moduleId));
			// 转向结果页面
			return this.moveToPage(result == 1 ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR, "修改自动化(续加)"+ (result == 1 ? "成功！" : "失败！"), toFind());
		} catch (Exception e) {
			logger.error("editAutoOverlay error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "修改自动化(续加)失败！", toFind());
		} finally {
			// 移除老数据
			session.removeAttribute("oldData");
		}
	}
	
	/**
	 * 删除自动化(续加)
	 * @return
	 */
	@RequestMapping("/deleteAutoOverlay.action")
	public String deleteAutoOverlay(){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String ids = request.getParameter("recordId");
			if(ToolsString.isEmpty(ids)){
				// 转向失败页面
				return this.moveToPage(VstConstants.OPERATE_INFO, "您至少选中一条记录进行删除操作！", toFind());
			}
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _vstAutoOverlayService.deleteAutoOverlay(ids, getUserSession(moduleId));
			
			// 转向成功页面
			return this.moveToPage(result == ids.split(",").length ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR,(result > 0 ? "批量删除自动化(续加)成功！" : "批量删除自动化(续加)失败！"), toFind());
		} catch (Exception e) {
			logger.error("deleteAutoOverlay error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "批量删除自动化(续加)失败！", toFind());
		}
	}
	
	/**
	 * 修改自动化(续加)状态
	 * @return
	 */
	@RequestMapping("/modifyAutoOverlayState.action")
	public String modifyAutoOverlayState(){
		// 状态
		int state = -1;
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String recordId = request.getParameter("recordId");
			String recordState = request.getParameter("recordState");
			
			state = Integer.parseInt(recordState);
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _vstAutoOverlayService.modifyAutoOverlayState(recordId, state, getUserSession(moduleId));
			
			// 转向成功页面
			return this.moveToPage(result > 0 ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR, 
							(state == 1 ? "启用自动化(续加)" : "禁用自动化(续加)") + (result > 0 ? "成功！" : "失败！"), toFind());
 		} catch (Exception e) {
			logger.error("modifyAutoOverlayState error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, (state == 1 ? "启用自动化(续加)" : "禁用自动化(续加)") + "失败！", toFind());
		}
	}
	
	/**
	 * 批量修改自动化(续加)排序
	 * @return
	 */
	@RequestMapping("/modifyAutoOverlayIndexs.action")
	public String modifyAutoOverlayIndexs(){
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
			_vstAutoOverlayService.modifyAutoOverlayIndexs(ids, indexs, getUserSession(moduleId));
			out.print(true);
			out.flush();
		} catch (Exception e) {
			logger.error("modifyAutoOverlayIndexs error. ids = " + ids + ", indexs = " + indexs + ", ERROR:" + SystemException.getExceptionInfo(e));
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
			request.setAttribute("overlayIds", recordId);
		} catch (Exception e) {
			logger.error("toCopy error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "autoOverlay/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_autoOverlay", cutPage);
		}
		return "report/autoOverlay_copy";
	}
	
	/**
	 * 复制信息
	 * @param params
	 * @return
	 */
	@RequestMapping("/copyOverlay.action")
	public String copyOverlay(@RequestParam Map<String, Object> params){
		int result = 0;
		try {
			// 初始化
			this.initializeAction(_className);
			
			String moduleId = String.valueOf(params.get("moduleId"));
			
			String overlayIds = ToolsString.checkEmpty(params.get("overlayIds"));
			String targetCode = ToolsString.checkEmpty(params.get("targetCode"));
			
			if(!ToolsString.isEmpty(targetCode)){
				result = _vstAutoOverlayService.copyOverlay(overlayIds, targetCode, getUserSession(moduleId));
			}
		} catch (Exception e) {
			logger.error("copyOverlay error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "复制信息失败！", toFind());
		}
		return this.moveToPage(VstConstants.OPERATE_SUCCESS, "成功复制"+result+"条数据！", toFind());
	}
	
	/**
	 * 替换信息
	 * @return
	 */
	@RequestMapping("/ajaxReplaceOverlay.action")
	public String ajaxReplaceOverlay(){
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
			//替换信息
			int count = _vstAutoOverlayService.replaceOverlay(ids, replace_type, replace_before, replace_after, getUserSession(moduleId));
			out.print(count);
			out.flush();
		}catch (Exception e) {
			logger.error("ajaxReplaceOverlay error. ERROR:" + SystemException.getExceptionInfo(e));
		} finally {
			// 关流操作
			ToolsIO.closeStream(out);
		}
		return null;
	}
}
