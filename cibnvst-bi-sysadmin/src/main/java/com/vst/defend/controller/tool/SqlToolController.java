package com.vst.defend.controller.tool;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.controller.BaseController;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.service.tool.VstSqlToolService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * SQL工具
 * @author lhp
 * @date 2018-4-10 下午06:25:31
 * @version
 */
@Controller
@RequestMapping("/sqlTool")
public class SqlToolController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = SqlToolController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * SQL工具Service接口
	 */
	@Resource
	private VstSqlToolService _vstSqlToolService;
	
	/**
	 * 跳转到页面
	 * @return
	 */
	@RequestMapping("/goToPage.action")
	public String goToPage(){
		try{
			// 初始化
			this.initializeAction(_className);
			
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			// 转发当前模块ID
			request.setAttribute("moduleId", moduleId);
		} catch (Exception e) {
			logger.error("goToPage error." + SystemException.getExceptionInfo(e));
		}
		return "tool/sqlTool_list";
	}
	
	/**
	 * 动态查询
	 * @return
	 */
	@RequestMapping("/ajaxQuery.action")
	public String ajaxQuery(){
		JSONObject json = new JSONObject();
		try {
			// 初始化Action类
			this.initializeAction(_className);
			
			int currPage = ToolsNumber.parseInt(request.getParameter("sql_currPage"));
			int singleCount = ToolsNumber.parseInt(request.getParameter("sql_singleCount"));
			String sql = ToolsString.checkEmpty(request.getParameter("sql_script"));
			
			JSONObject result = _vstSqlToolService.queryBySql(sql, currPage, singleCount);
			if(result.get("data") == null){
				json.put("data", Collections.EMPTY_LIST);
				json.put("code", 200);
				json.put("msg", "查询数据为空！");
			}else{
				json.put("data", result.get("data"));
				json.put("time", result.get("time"));
				json.put("code", 100);
			}
		} catch (Exception e) {
			json.put("code", 300);
			json.put("msg", "系统异常，请检查SQL是否出错！");
			logger.error("ajaxQuery error. ERROR:" + e.getMessage());
		}
		printJson(json.toJSONString());
		return null;
	}
}
