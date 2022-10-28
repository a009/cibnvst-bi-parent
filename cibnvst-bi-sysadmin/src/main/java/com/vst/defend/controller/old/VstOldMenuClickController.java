package com.vst.defend.controller.old;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vst.common.pojo.VstSysOperateLog;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.controller.BaseController;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.ErrorInfo;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.CutPage;
import com.vst.defend.communal.util.ExportUtil;
import com.vst.defend.communal.util.PropertiesReader;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.communal.util.VstTools;

/**
 * 菜单点击
 * @author lhp
 * @date 2017-11-23 下午12:24:57
 * @version
 */
@Controller
@RequestMapping("/oldMenuClick")
public class VstOldMenuClickController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = VstOldMenuClickController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询菜单点击
	 * @return
	 */
	@RequestMapping("/findMenuClick.action")
	public String findMenuClick(){
		try {
			// 初始化
			this.initializeAction(_className);
			
			Object obj = session.getAttribute("cutPage_menuClick");
			if(obj != null){
				cutPage = (CutPage) obj;
			}else{
				cutPage = new CutPage();
			}
			
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			
			// 获取页面按钮
			cutPage.set_buttonList(getPageButtons(moduleId));
			// 转发查询到的数据到页面
			request.setAttribute("cutPage", cutPage);
			// 转发当前模块ID
			request.setAttribute("moduleId", moduleId);
			
			// 获取分类
			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("vst_table_name", "vst_movie");
			params.put("vst_column_name", "classify");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("menuTypes", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("findMenuClick error." + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_menuClick");
		}
		
		return "old/menuClick_list";
	}
	
	/**
	 * 导出数据
	 * @return
	 */
	@RequestMapping("/exportData.action")
	public String exportData(){
		try{
			this.initializeAction(_className);
			
			String data = request.getParameter("data");
			JSONArray dataArray = JSONObject.parseArray(data);
			
			List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
			for(int i=0; i<dataArray.size(); i++){
				dataList.add(dataArray.getJSONObject(i));
			}
			
			File file = ExportUtil.ExportExcel(dataList, PropertiesReader.getProperty("export_dir"), "vst_menu_click_data");
			if(file.exists()){
				String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
				UserSession user = getUserSession(moduleId);
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("导出表名：vst_menu_click_data")
				.append("，起始日期=").append(request.getParameter("startDay"))
				.append("，结束日期=").append(request.getParameter("endDay"))
				.append("，操作人=").append(user.getLoginInfo().getLoginName());
				_baseService.saveLog(user, new VstSysOperateLog(moduleId, VstConstants.OPERATE_TYPE_EXPORT, sb.toString()));
				
				VstTools.fileDownLoad(response, file);
				file.delete();
			}
		}catch(Exception e){
			logger.error("exportData error." + SystemException.getExceptionInfo(e));
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");// 转向失败页面
		}
		return null;
	}
}