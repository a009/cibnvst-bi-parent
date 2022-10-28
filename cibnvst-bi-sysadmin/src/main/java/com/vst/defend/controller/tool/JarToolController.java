package com.vst.defend.controller.tool;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.controller.BaseController;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.ErrorInfo;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.PropertiesReader;
import com.vst.defend.communal.util.VstTools;
import com.vst.defend.service.tool.VstJarToolService;

/**
 * JAR包工具
 * @author lhp
 * @date 2019-1-8 下午02:32:21
 * @version
 */
@SuppressWarnings("unused")
@Controller
@RequestMapping("/jarTool")
public class JarToolController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = SqlToolController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * JAR包工具Service接口
	 */
	@Resource
	private VstJarToolService _vstJarToolService;
	
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
		return "tool/jarTool_list";
	}
	
	/**
	 * 上传JAR包
	 * @param myfile
	 * @return
	 */
	@RequestMapping("/ajaxUploadJar.action")
	@ResponseBody
    public JSONObject ajaxUploadJar(@RequestParam("upload") CommonsMultipartFile myfile) {
		JSONObject result = new JSONObject();
		try {
			// 初始化
			this.initializeAction(_className);
			
			if(myfile != null){
				String destDir = PropertiesReader.getProperty("upload_jar_dir");
				
				String fileName =  myfile.getOriginalFilename();
				String newFileUrl = destDir + "/" + fileName;
				File newFile = new File(newFileUrl);
				if(newFile.exists()){
					newFile.delete();
				}
				FileUtils.copyInputStreamToFile(myfile.getInputStream(), newFile);
				
				result.put("code", 100);
				result.put("msg", "上传成功");
				result.put("url", newFileUrl);
			}else{
				result.put("code", 201);
				result.put("msg", "文件为空");
			}
		} catch (Exception e) {
			result.put("code", 301);
			result.put("msg", "ERROR:" + e.getMessage());
			logger.error("ajaxUploadJar error, ERROR:" + e.getMessage());
		}
		return result;
	}
	
	/**
	 * 获取Spark执行参数
	 * @param request
	 * @return
	 */
	@RequestMapping("/ajaxGetSparkExecParam.action")
	@ResponseBody
	public JSONObject ajaxGetSparkExecParam(HttpServletRequest request){
		JSONObject result = new JSONObject();
		try {
			// 初始化
			this.initializeAction(_className);
			
			String sql_type = ToolsString.checkEmpty(request.getParameter("sql_type"));
			String sql_sysParam = ToolsString.checkEmpty(request.getParameter("sql_sysParam"));
			String sql_appParam = ToolsString.checkEmpty(request.getParameter("sql_appParam"));
			String sql_content = ToolsString.checkEmpty(request.getParameter("sql_content"));
			sql_content = URLEncoder.encode(sql_content, "utf-8");
			sql_appParam = sql_appParam.replace("{sql}", sql_content).replace("{type}", sql_type);
			
	        String sparkExecParam = sql_sysParam + " " + sql_appParam;
	        result.put("code", 100);
			result.put("result", sparkExecParam);
		} catch (Exception e) {
			result.put("code", 301);
			result.put("msg", "ERROR:" + e.getMessage());
			logger.error("ajaxGetSparkExecParam error, ERROR:" + e.getMessage());
		}
		return result;
	}
	
	/**
	 * 执行SQL
	 * @return
	 */
	@RequestMapping("/ajaxExecuteSql.action")
	@ResponseBody
	public JSONObject ajaxExecuteSql(){
		JSONObject result = new JSONObject();
		try {
			// 初始化Action类
			this.initializeAction(_className);
			
			String sql_type = ToolsString.checkEmpty(request.getParameter("sql_type"));
			String sql_sysParam = ToolsString.checkEmpty(request.getParameter("sql_sysParam"));
			String sql_appParam = ToolsString.checkEmpty(request.getParameter("sql_appParam"));
			String sql_content = ToolsString.checkEmpty(request.getParameter("sql_content"));
			sql_content = URLEncoder.encode(sql_content, "utf-8");
			sql_appParam = sql_appParam.replace("{sql}", sql_content).replace("{type}", sql_type);
			
	        String sparkExecParam = sql_sysParam + " " + sql_appParam;
	        List<String> list = _vstJarToolService.executeSql(sparkExecParam);
			if(list.isEmpty()){
				result.put("code", 202);
				result.put("msg", "结果为空");
			}else{
				result.put("code", 100);
				result.put("result", list);
			}
	        
			// result = _vstJarToolService.executeSql(sql_type, sql_content);
		} catch (Exception e) {
			result.put("code", 301);
			result.put("msg", "ERROR:" + e.getMessage());
			logger.error("ajaxExecuteSql error, ERROR:" + e.getMessage());
		}
		return result;
	}
	
	/**
	 * 下载SQL文件
	 * @return
	 */
	@RequestMapping("/downloadSql.action")
	public String downloadSql(){
		try{
			this.initializeAction(_className);
			
			String fileUrl = ToolsString.checkEmpty(request.getParameter("url"));
			
			File file = new File(fileUrl);
			if(file.exists()){
				VstTools.fileDownLoad(response, file);
			}
		}catch(Exception e){
			logger.error("downloadSql error." + SystemException.getExceptionInfo(e));
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");// 转向失败页面
		}
		return null;
	}
}
