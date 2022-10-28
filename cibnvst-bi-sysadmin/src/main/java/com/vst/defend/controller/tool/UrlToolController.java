package com.vst.defend.controller.tool;

import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.controller.BaseController;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.service.tool.VstUrlToolService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 常用网址
 * @author lhp
 * @date 2017-6-21 下午03:46:06
 * @description
 * @version
 */
@Controller
@RequestMapping("/urlTool")
public class UrlToolController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = UrlToolController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 网址工具Service接口
	 */
	@Resource
	private VstUrlToolService _vstUrlToolService;
	
	/**
	 * 查看常用网址
	 * @return
	 */
	@RequestMapping("/findUrls.action")
	public String findUrls(){
		try{
			// 初始化
			this.initializeAction(_className);
			
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			// 转发当前模块ID
			request.setAttribute("moduleId", moduleId);
			
			// 获取基本网址
			request.setAttribute("basicUrls", _vstUrlToolService.getBasicUrls());
			// 获取VST网址
			request.setAttribute("vstUrls", _vstUrlToolService.getVstUrls());
		} catch (Exception e) {
			logger.error("findUrls error." + SystemException.getExceptionInfo(e));
		}
		return "tool/urlTool_list";
	}
}
