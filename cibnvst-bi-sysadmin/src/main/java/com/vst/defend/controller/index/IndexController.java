package com.vst.defend.controller.index;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.controller.BaseController;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.ErrorInfo;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.VstTools;
import com.vst.defend.service.index.IndexService;

/**
 * 首页数据
 * @author lhp
 * @date 2017-12-5 下午05:35:01
 * @version
 */
@Controller
@RequestMapping("/index")
public class IndexController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = IndexController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 首页Service接口
	 */
	@Resource
	private IndexService _indexService;
	
	/**
	 * 获取首页数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/ajaxGetIndexData.action")
	public JSONObject ajaxGetIndexData(){
		JSONObject json = new JSONObject();
		try{
			this.initializeAction(_className);
			
			Map<String, Object> params = new HashMap<String, Object>(2);
//			params.put("date", "2017-11-27");
//			params.put("pkgName", "net.myvst.v2");
			String date = ToolsString.checkEmpty(request.getParameter("date"));
			String pkgName = ToolsString.checkEmpty(request.getParameter("pkgName"));
			if(ToolsString.isEmpty(date)){
				json.put("code", 200);
				json.put("msg", "日期不能为空！");
				return json;
			}
			if(ToolsString.isEmpty(pkgName)){
				json.put("code", 200);
				json.put("msg", "包名不能为空！");
				return json;
			}
			
			params.put("date", date);
			params.put("pkgName", pkgName);
			params.put("isCache", ToolsString.checkEmpty(request.getParameter("isCache")));
			
			JSONObject data = _indexService.getIndexData(params);
			
			if(data == null || data.isEmpty()){
				json.put("code", 202);
				json.put("msg", "没有结果！");
			}else{
				json.put("data", data);
				json.put("code", 100);
				json.put("msg", "调用成功！");
			}
		}catch(Exception e){
			json.put("code", 301);
			json.put("msg", "服务器异常！");
			logger.error("ajaxGetIndexData error." + SystemException.getExceptionInfo(e));
		}
		return json;
	}
	
	/**
	 * 导出数据
	 * @return
	 */
	@RequestMapping("/exportData.action")
	public String exportData(){
		try{
			this.initializeAction(_className);
			
			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("date", ToolsString.checkEmpty(request.getParameter("date")));
			queryParam.put("pkgName", ToolsString.checkEmpty(request.getParameter("pkgName")));
			queryParam.put("export_column", ToolsString.checkEmpty(request.getParameter("export_column")));
			// 编码转码
			VstTools.decodeToUTF_8(queryParam);
			File file = _indexService.exportIndexFile(queryParam);
			if(file.exists()){
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
