package com.vst.defend.controller.sys;

import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.tools.ftp.ToolsFTp;
import com.vst.common.tools.io.ToolsIO;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.controller.BaseController;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.ErrorInfo;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;
import com.vst.defend.communal.util.PropertiesReader;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.service.sys.VstOptionsService;

/**
 * 控制面板
 * @author lhp
 * @date 2017-5-9 上午11:17:24
 * @description
 * @version
 */
@Scope("session")
@Controller
@RequestMapping("/options")
public class VstOptionsController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = VstOptionsController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 数据常量Service接口
	 */
	@Resource
	private VstOptionsService _vstOptionsService;
	
	/**
	 * 查询数据常量
	 * @return
	 */
	@RequestMapping("/findOptions.action")
	public String findOptions(){
		try {
			// 初始化
			this.initializeAction(_className);

			if("1".equals(request.getParameter("flag"))) {
				cutPage = new CutPage();
			}else {
				cutPage = new CutPage();
				// 查询条件
				Map<String, String> queryParam = new HashMap<String, String>();
				queryParam.put("vst_option_key", ToolsString.checkEmpty(request.getParameter("vst_option_key")));
				queryParam.put("vst_option_value", ToolsString.checkEmpty(request.getParameter("vst_option_value")));
				queryParam.put("vst_option_desc", ToolsString.checkEmpty(request.getParameter("vst_option_desc")));
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
				cutPage = _vstOptionsService.getOptionsList(cutPage, getUserSession(moduleId));
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
			logger.error("findOptions error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_options");
		}
		return "sys/options_list";
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

			Object obj = session.getAttribute("cutPage_options");
			if(obj != null){
				cutPage = (CutPage) obj;
			}else{
				cutPage = new CutPage();
				// 查询条件
				Map<String, String> queryParam = new HashMap<String, String>();
				queryParam.put("vst_option_key", ToolsString.checkEmpty(request.getParameter("vst_option_key")));
				queryParam.put("vst_option_value", ToolsString.checkEmpty(request.getParameter("vst_option_value")));
				queryParam.put("vst_option_desc", ToolsString.checkEmpty(request.getParameter("vst_option_desc")));
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
				cutPage = _vstOptionsService.getOptionsList(cutPage, getUserSession(moduleId));
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
			session.removeAttribute("cutPage_options");
		}
		return "sys/options_list";
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
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "options/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_options", cutPage);
		}
		return "sys/options_add";
	}
	
	/**
	 * 新增数据常量
	 * @return
	 */
	@RequestMapping("/addOptions.action")
	public String addOptions(@RequestParam Map<String, Object> params){
		try {
			// 初始化Action类
			this.initializeAction(_className);

			String moduleId = String.valueOf(params.get("moduleId"));
			_vstOptionsService.addOptions(params, getUserSession(moduleId));
		} catch (Exception e) {
			logger.error("addOptions error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "添加数据常量失败！", toFind());
		}
		return this.moveToPage(VstConstants.OPERATE_SUCCESS, "添加数据常量成功！", toFind());
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
			formMap = _vstOptionsService.getOptionsById(recordId);
			
			// 把老数据放入到session中
			session.setAttribute("oldData", formMap);
			request.setAttribute("formMap", formMap);
			// 转发当前模块ID
			request.setAttribute("moduleId", ToolsString.checkEmpty(request.getParameter("moduleId")));
		} catch (Exception e) {
			logger.error("toEdit error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "options/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_options", cutPage);
		}
		return "sys/options_edit";
	}
	
	/**
	 * 修改数据常量
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/editOptions.action")
	public String editOptions(@RequestParam Map<String, Object> params){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			
			Object obj = session.getAttribute("oldData");
			Map<String, Object> oldParam = null;
			if(obj != null){
				oldParam = (Map<String, Object>) obj;
			}
			
			String moduleId = String.valueOf(params.get("moduleId"));
			int result = _vstOptionsService.updateOptions(oldParam, params, getUserSession(moduleId));
			// 转向结果页面
			return this.moveToPage(result == 1 ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR, "修改数据常量"+ (result == 1 ? "成功！" : "失败！"), toFind());
		} catch (Exception e) {
			logger.error("editOptions error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "修改数据常量失败！", toFind());
		} finally {
			// 移除老数据
			session.removeAttribute("oldData");
		}
	}
	
	/**
	 * 删除数据常量
	 * @return
	 */
	@RequestMapping("/deleteOptions.action")
	public String deleteOptions(){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String ids = request.getParameter("recordId");
			if(ToolsString.isEmpty(ids)){
				// 转向失败页面
				return this.moveToPage(VstConstants.OPERATE_INFO, "您至少选中一条记录进行删除操作！", toFind());
			}
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _vstOptionsService.deleteOptions(ids, getUserSession(moduleId));
			
			// 转向成功页面
			return this.moveToPage(result == ids.split(",").length ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR,(result > 0 ? "批量删除数据常量成功！" : "批量删除数据常量失败！"), toFind());
		} catch (Exception e) {
			logger.error("deleteOptions error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "批量删除数据常量失败！", toFind());
		}
	}
	
	/**
	 * 校验key值唯一
	 * @return
	 */
	@RequestMapping("/ajaxCheckKey.action")
	public String ajaxCheckKey(){
		// 输出流
		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			// 初始化
			this.initializeAction(_className); 
			out = response.getWriter();
			String opKey = request.getParameter("opKey");
			int flag = _vstOptionsService.ajaxCheckKey(opKey);
			if(flag == 0){
				json.put("code", 0);
				out.print(json);
				out.flush();
			}else{
				json.put("code", 1);
				out.print(json);
				out.flush();
			}
		} catch (Exception e) {
			json.put("code", 2);
			out.print(json);
			logger.error("ajaxCheckKey error. ERROR:" + SystemException.getExceptionInfo(e));
		} finally {
			// 关流操作
			ToolsIO.closeStream(out);
		}
		return null;
	}
	
	/**
	 * 上传图片
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/ajaxUploadPicFile.action")
    public String ajaxUploadPicFile(@RequestParam("upload") CommonsMultipartFile myfile) {
		// 输出流
		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			// 初始化
			this.initializeAction(_className); 
			out = response.getWriter();
			if(myfile != null){
				// 上传图片不能大于350KB=358400K
				int pic_max_size = ToolsNumber.parseInt(PropertiesReader.getProperty("pic_max_size"));
				if(myfile.getSize() > pic_max_size){
					json.put("code", 5);
					json.put("maxSize", pic_max_size/1024+"KB");
				}else{
					// 根据新增类型来获取目标文件夹
					String destDir = null;
					int type = Integer.parseInt(request.getParameter("type"));
					if(type == 1){ //配置内容
						destDir = PropertiesReader.getProperty("options_pic_dir");
					}else{// 默认
						destDir = PropertiesReader.getProperty("options_pic_dir");
					}
					// 远程ftp服务器host
					String remoteHost = PropertiesReader.getProperty("[img]");
					// 创建ftp客户端链接
					ToolsFTp ftp = new ToolsFTp();
					String fileName =  myfile.getOriginalFilename();
					InputStream fileStream = myfile.getInputStream();
					
					String url = ftp.uploadFile(destDir, remoteHost, fileStream, fileName.substring(fileName.lastIndexOf(".")));
					
					if(url != null){
						json.put("url", "[img]" + url);
						json.put("code", 0);
					}else{
						// 返回失败信息
						json.put("code", 1);
					}
				}
			}else{
				json.put("code", 3);
			}
			out.print(json);
			out.flush();
		} catch (Exception e) {
			json.put("code", 2);
			out.print(json);
			logger.error("ajaxUploadPicFile error. ERROR:" + SystemException.getExceptionInfo(e));
		} finally {
			// 关流操作
			ToolsIO.closeStream(out);
		}
		return null;
    }
	
	/**
	 * 动态修改数据常量
	 * @return
	 */
	@RequestMapping("/ajaxUpdateOption.action")
	public String ajaxUpdateOption(){
		JSONObject json = new JSONObject();
		try {
			// 初始化
			this.initializeAction(_className);
			
			String key = ToolsString.checkEmpty(request.getParameter("key"));
			String value = ToolsString.checkEmpty(request.getParameter("value"));
			
			Map<String, Object> param = new HashMap<String, Object>(2);
			param.put("vst_option_key", key);
			param.put("vst_option_value", value);
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _vstOptionsService.updateOptions(null, param, getUserSession(moduleId));
			if(result > 0){
				json.put("code", 1);
			}else{
				json.put("code", 0);
			}
		} catch (Exception e) {
			json.put("code", 2);
			logger.error("ajaxUpdateOption error. ERROR:" + SystemException.getExceptionInfo(e));
		}
		printJson(json.toJSONString());
		return null;
	}
}
