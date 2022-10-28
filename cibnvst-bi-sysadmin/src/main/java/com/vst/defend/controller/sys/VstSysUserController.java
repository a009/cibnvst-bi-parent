package com.vst.defend.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.tools.ftp.ToolsFTp;
import com.vst.common.tools.io.ToolsIO;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.number.ToolsRandom;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.controller.BaseController;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.ErrorInfo;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.CutPage;
import com.vst.defend.communal.util.EncryptUtils;
import com.vst.defend.communal.util.MailUtils;
import com.vst.defend.communal.util.PropertiesReader;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.service.index.IndexService;
import com.vst.defend.service.sys.VstSysRoleService;
import com.vst.defend.service.sys.VstSysUserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户管理
 * @author lhp
 * @date 2017-4-6 下午03:17:47
 * @description
 * @version
 */
@Scope("session")
@Controller
@RequestMapping("/sysUser")
public class VstSysUserController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = VstSysUserController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 用户Service接口
	 */
	@Resource
	private VstSysUserService _vstSysUserService;
	
	/**
	 * 角色Service接口
	 */
	@Resource
	private VstSysRoleService _vstSysRoleService;
	
	/**
	 * 首页Service接口
	 */
	@Resource
	private IndexService _indexService;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询用户列表
	 * @return
	 */
	@RequestMapping("/findUsers.action")
	public String findUsers(){
		try {
			// 初始化
			this.initializeAction(_className);

			if("1".equals(request.getParameter("flag"))) {
				cutPage = new CutPage();
			}else {
				cutPage = new CutPage();
				// 查询条件
				Map<String, String> queryParam = new HashMap<String, String>();
				queryParam.put("vst_sys_id", ToolsString.checkEmpty(request.getParameter("vst_sys_id")));
				queryParam.put("vst_sys_name", ToolsString.checkEmpty(request.getParameter("vst_sys_name")));
				queryParam.put("vst_sys_username", ToolsString.checkEmpty(request.getParameter("vst_sys_username")));
				queryParam.put("vst_sys_state", ToolsString.checkEmpty(request.getParameter("vst_sys_state")));
				queryParam.put("vst_role_id", ToolsString.checkEmpty(request.getParameter("vst_role_id")));
				queryParam.put("vst_sys_division", ToolsString.checkEmpty(request.getParameter("vst_sys_division")));
				queryParam.put("vst_sys_job", ToolsString.checkEmpty(request.getParameter("vst_sys_job")));
				queryParam.put("vst_sys_channel", ToolsString.checkEmpty(request.getParameter("vst_sys_channel")));
				queryParam.put("vst_sys_summary", ToolsString.checkEmpty(request.getParameter("vst_sys_summary")));
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
				cutPage = _vstSysUserService.getSysUserList(cutPage, getUserSession(moduleId));
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
			
			//获取角色列表
			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("vst_role_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("roles", _vstSysRoleService.queryForMap(params, "vst_role_id", "vst_role_name"));
			//获取所有部门
			params.clear();
			params.put("vst_table_name", "vst_sys_user");
			params.put("vst_column_name", "vst_sys_division");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("divisions", _baseService.getDictionaryForMap(params));
			//获取所有职位
			params.put("vst_table_name", "vst_sys_user");
			params.put("vst_column_name", "vst_sys_job");
			request.setAttribute("jobs", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("findUsers error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_user");
		}
		return "sys/sysUser_list";
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

			Object obj = session.getAttribute("cutPage_user");
			if(obj != null){
				cutPage = (CutPage) obj;
			}else{
				cutPage = new CutPage();
				// 查询条件
				Map<String, String> queryParam = new HashMap<String, String>();
				queryParam.put("vst_sys_id", ToolsString.checkEmpty(request.getParameter("vst_sys_id")));
				queryParam.put("vst_sys_name", ToolsString.checkEmpty(request.getParameter("vst_sys_name")));
				queryParam.put("vst_sys_username", ToolsString.checkEmpty(request.getParameter("vst_sys_username")));
				queryParam.put("vst_sys_state", ToolsString.checkEmpty(request.getParameter("vst_sys_state")));
				queryParam.put("vst_role_id", ToolsString.checkEmpty(request.getParameter("vst_role_id")));
				queryParam.put("vst_sys_division", ToolsString.checkEmpty(request.getParameter("vst_sys_division")));
				queryParam.put("vst_sys_job", ToolsString.checkEmpty(request.getParameter("vst_sys_job")));
				queryParam.put("vst_sys_channel", ToolsString.checkEmpty(request.getParameter("vst_sys_channel")));
				queryParam.put("vst_sys_summary", ToolsString.checkEmpty(request.getParameter("vst_sys_summary")));
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
				cutPage = _vstSysUserService.getSysUserList(cutPage, getUserSession(moduleId));
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

			//获取角色列表
			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("vst_role_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("roles", _vstSysRoleService.queryForMap(params, "vst_role_id", "vst_role_name"));
			//获取所有部门
			params.clear();
			params.put("vst_table_name", "vst_sys_user");
			params.put("vst_column_name", "vst_sys_division");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("divisions", _baseService.getDictionaryForMap(params));
			//获取所有职位
			params.put("vst_table_name", "vst_sys_user");
			params.put("vst_column_name", "vst_sys_job");
			request.setAttribute("jobs", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("toFind error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_user");
		}
		return "sys/sysUser_list";
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
			
			//获取所有部门
			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("vst_table_name", "vst_sys_user");
			params.put("vst_column_name", "vst_sys_division");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("divisions", _baseService.getDictionaryForMap(params));
			//获取所有职位
			params.put("vst_table_name", "vst_sys_user");
			params.put("vst_column_name", "vst_sys_job");
			request.setAttribute("jobs", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("toAdd error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysUser/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_user", cutPage);
		}
		return "sys/sysUser_add";
	}
	
	/**
	 * 校验登录帐号是否重复
	 * @return
	 */
	@RequestMapping("/ajaxCheckUsername.action")
	public String ajaxCheckUsername(){
		// 输出流
		PrintWriter out = null;
		String userName = null;
		try {
			// 初始化Action类
			this.initializeAction(_className);
			out = response.getWriter();
			userName = request.getParameter("vst_sys_username");
			boolean flag = _vstSysUserService.checkUserNameExist(userName);
			out.write(flag + "");
			out.flush();
		} catch (Exception e) {
			logger.error("ajaxcCheckUsername error. userName=" + userName + ", ERROR:" + e.getMessage());
		} finally {
			// 关流操作
			ToolsIO.closeStream(out);
		}
		return null;
	}
	
	/**
	 * 新增用户
	 * @return
	 */
	@RequestMapping("/addUser.action")
	public String addUser(@RequestParam Map<String, Object> params){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			
			String moduleId = String.valueOf(params.get("moduleId"));
			_vstSysUserService.addSysUser(params, getUserSession(moduleId));
		} catch (Exception e) {
			logger.error("addUser error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "添加用户失败！", toFind());
		}
		return this.moveToPage(VstConstants.OPERATE_SUCCESS, "添加用户成功！", toFind());
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
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			formMap = _vstSysUserService.getSysUserById(recordId, getUserSession(moduleId));
			
			// 把老数据放入到session中
			session.setAttribute("oldData", formMap);
			request.setAttribute("formMap", formMap);
			// 转发当前模块ID
			request.setAttribute("moduleId", moduleId);
			
			//获取所有部门
			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("vst_table_name", "vst_sys_user");
			params.put("vst_column_name", "vst_sys_division");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("divisions", _baseService.getDictionaryForMap(params));
			//获取所有职位
			params.put("vst_table_name", "vst_sys_user");
			params.put("vst_column_name", "vst_sys_job");
			request.setAttribute("jobs", _baseService.getDictionaryForMap(params));
		} catch (Exception e) {
			logger.error("toEdit error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysUser/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_user", cutPage);
		}
		return "sys/sysUser_edit";
	}
	
	/**
	 * 修改用户
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/editUser.action")
	public String editUser(@RequestParam Map<String, Object> params){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			
			Object obj = session.getAttribute("oldData");
			Map<String, Object> oldParam = null;
			if(obj != null){
				oldParam = (Map<String, Object>) obj;
			}
			
			String moduleId = String.valueOf(params.get("moduleId"));
			int result = _vstSysUserService.updateSysUser(oldParam, params, getUserSession(moduleId));
			// 转向结果页面
			return this.moveToPage(result == 1 ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR, "修改用户"+ (result == 1 ? "成功！" : "失败！"), toFind());
		} catch (Exception e) {
			logger.error("editUser error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "修改用户失败！", toFind());
		} finally {
			// 移除老数据
			session.removeAttribute("oldData");
		}
	}
	
	/**
	 * 删除用户
	 * @return
	 */
	@RequestMapping("/deleteUser.action")
	public String deleteUser(){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String ids = request.getParameter("recordId");
			if(ToolsString.isEmpty(ids)){
				// 转向失败页面
				return this.moveToPage(VstConstants.OPERATE_INFO, "您至少选中一条记录进行删除操作！", toFind());
			}
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _vstSysUserService.deleteSysUser(ids, getUserSession(moduleId));
			
			// 转向成功页面
			return this.moveToPage(result == ids.split(",").length ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR,(result > 0 ? "批量删除用户成功！" : "批量删除用户失败！"), toFind());
		} catch (Exception e) {
			logger.error("deleteUser error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "批量删除用户失败！", toFind());
		}
	}
	
	/**
	 * 修改用户状态
	 * @return
	 */
	@RequestMapping("/modifyUserState.action")
	public String modifyUserState(){
		// 状态
		int state = -1;
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String recordId = request.getParameter("recordId");
			String recordState = request.getParameter("recordState");
			
			state = Integer.parseInt(recordState);
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _vstSysUserService.modifyUserState(recordId, state, getUserSession(moduleId));
			
			// 转向成功页面
			return this.moveToPage(result > 0 ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR, 
							(state == 1 ? "启用用户" : "禁用用户") + (result > 0 ? "成功！" : "失败！"), toFind());
 		} catch (Exception e) {
			logger.error("modifyUserState error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, (state == 1 ? "启用用户" : "禁用用户") + "失败！", toFind());
		}
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
//				// 上传图片不能大于350KB=358400K
//				int pic_max_size = ToolsNumber.parseInt(PropertiesReader.getProperty("pic_max_size"));
//				if(myfile.getSize() > pic_max_size){
//					json.put("code", 5);
//					json.put("maxSize", pic_max_size/1024+"KB");
//				}else{
//					// 判断图片格式
//					String fileName =  myfile.getOriginalFilename();
//					String stype = fileName.substring(fileName.lastIndexOf(".")+1);
//					if(!stype.matches("(gif|jpg|jpeg|png|bmp|tiff|GIF|JPG|JPEG|PNG|BMP|TIFF)")){
//						json.put("code", 4);
//						out.print(json);
//						out.flush();
//						return null;
//					}
					// 根据新增类型来获取目标文件夹
					String destDir = null;
					int type = Integer.parseInt(request.getParameter("type"));
					if(type == 1){ //头像
						destDir = PropertiesReader.getProperty("sysAdmin_photo");
					}else{// 默认
						destDir = PropertiesReader.getProperty("sysAdmin_photo");
					}
					
					// 远程ftp服务器host
					String remoteHost = PropertiesReader.getProperty("[img]");
					// 创建ftp客户端链接
					ToolsFTp ftp = new ToolsFTp();
					InputStream fileStream = myfile.getInputStream();
					String fileName =  myfile.getOriginalFilename();
					String url = ftp.uploadFile(destDir, remoteHost, fileStream, fileName.substring(fileName.lastIndexOf(".")));
					
					if(url != null){
						if(type==0){//修改头像
							//获取用户信息session
							UserSession userSession = (UserSession) request.getSession().getAttribute(VstConstants.SESSION_KEY_USER);
							//用户ID
							String userId = userSession.getVstSysUser().getVst_sys_id();
							int result = _vstSysUserService.updateSysPhoto(userId, "[img]" + url, getUserSession("F44K6WXQ5U6M"));
							if(result > 0){
								userSession.getVstSysUser().setVst_sys_photo(remoteHost + url);
								json.put("url", remoteHost + url);
								json.put("code", 0);
							}else{
								// 返回失败信息
								json.put("code", 1);
							}
						}else{
							json.put("url", "[img]" + url);
							json.put("code", 0);
						}
					}else{
						// 返回失败信息
						json.put("code", 1);
					}
//				}
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
	 * 跳转到重置密码页面
	 * @return
	 */
	@RequestMapping("/toReset.action")
	public String toReset(){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String recordId = request.getParameter("recordId");
			String username = request.getParameter("username");
			formMap = new HashMap<String, Object>(3);
			formMap.put("vst_sys_id", recordId);
			formMap.put("vst_sys_username", username);
			formMap.put("vst_sys_password", ToolsRandom.getRandom(6));
			
			request.setAttribute("formMap", formMap);
			// 转发当前模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			request.setAttribute("moduleId", moduleId);
		} catch (Exception e) {
			logger.error("toReset error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysUser/toFind.action");
		} finally {
			// 把查询条件以及分页信息放入session中
			session.setAttribute("cutPage_user", cutPage);
		}
		return "sys/sysUser_reset";
	}
	
	/**
	 * 重置密码
	 * @return
	 */
	@RequestMapping("/resetPassword.action")
	public String resetPassword(@RequestParam Map<String, Object> params){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String password = ToolsString.checkEmpty(params.get("vst_sys_password"));
			// 加密密码入库
			params.put("vst_sys_password", EncryptUtils.getSHA256Encrypt(password));
			
			String moduleId = String.valueOf(params.get("moduleId"));
			int result = _vstSysUserService.updateSysUser(null, params, getUserSession(moduleId));
			
			return this.moveToPage(result == 1 ? VstConstants.OPERATE_SUCCESS : VstConstants.OPERATE_ERROR, 
					"重置密码" + (result == 1 ? "成功!" : "失败!"), toFind());
		} catch (Exception e) {
			logger.error("resetPassword error. ERROR:" + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToPage(VstConstants.OPERATE_ERROR, "重置密码失败！", toFind());
		}
	}
	
	/**
	 * 修改密码
	 * @return
	 */
	@RequestMapping("/editPassword.action")
	public String editPassword(){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			formMap = new HashMap<String, Object>(1);
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			formMap.put("vst_sys_id", getUserSession(moduleId).getVstSysUser().getVst_sys_id());
			
			request.setAttribute("formMap", formMap);
			// 转发当前模块ID
			request.setAttribute("moduleId", moduleId);
		} catch (Exception e) {
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysUser/toFind.action");
		}
		return "sys/sysUser_edit_password";
	}
	
	/**
	 * 保存修改的新密码
	 * @return
	 */
	@RequestMapping("/savePassword.action")
	public String savePassword(@RequestParam Map<String, Object> params){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			String password = ToolsString.checkEmpty(params.get("vst_sys_password"));
			// 加密密码入库
			params.put("vst_sys_password", EncryptUtils.getSHA256Encrypt(password));
			
			String moduleId = String.valueOf(params.get("moduleId"));
			int result = _vstSysUserService.updateSysUser(null, params, this.getUserSession(moduleId));
			
			// 如果没有修改成功，跳转到失败页面
			if(result != 1){
				return this.moveToPage(VstConstants.OPERATE_ERROR, "修改密码失败！", toFind());
			}
			
			// 转向操作成功页面
			return this.moveToPage(VstConstants.OPERATE_SUCCESS, "修改密码成功！", toFind());
		} catch (Exception e) {
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysUser/toFind.action");
		}
	}
	
	/**
	 * 验证输入的旧密码是否正确
	 * @return
	 */
	@RequestMapping("/checkOldPassword.action")
	public String checkOldPassword(@RequestParam Map<String, Object> params){
		// 输出流
		PrintWriter out = null;
		try {
			// 初始化Action类
			this.initializeAction(_className);
			
			String moduleId = String.valueOf(params.get("moduleId"));
			String oldPassword = getUserSession(moduleId).getLoginInfo().getLoginPassword();
			String inputPassword = ToolsString.checkEmpty(params.get("oldPassword"));
			boolean flag = oldPassword.equals(inputPassword);
			out = response.getWriter();
			out.print(flag);
			out.flush();
		} catch (Exception e) {
			logger.error("checkOldPassword error. ERROR:" + e.getMessage());
		} finally {
			// 关流操作
			ToolsIO.closeStream(out);
		}
		return null;
	}
	
	/**
	 * 发送邮件
	 * @return
	 */
	@RequestMapping("/ajaxSendEmail.action")
	public String ajaxSendEmail(){
		JSONObject json = new JSONObject();
		try {
			// 初始化
			this.initializeAction(_className);
			String date = request.getParameter("date");
			
			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("date", date);
			params.put("pkgName", "net.myvst.v2");
			params.put("isCache", false);
			
//			String path = "";
//			String requestUrl = request.getRequestURL().toString();
//			int index = requestUrl.indexOf("cibnvst-bi-sysadmin");
//			if(index != -1){
//				path = requestUrl.substring(0, index) + "cibnvst-bi-sysadmin/pub/images";
//			}
//			String table = _indexService.getEmailContent(params, path);
			
			
			double radio = ToolsNumber.parseDouble(_baseService.getOptionByKey("email_send_ratio"));
			String table = _indexService.getEmailByOuter(params, radio);
			
			String smtp = "smtp.exmail.qq.com";
			String from = "jie.wang@91vst.com";
			String to = _baseService.getOptionByKey("email_send_to");
			String copyto = _baseService.getOptionByKey("email_send_copyto");
			String subject = "微视听日报";
			String username = "jie.wang@91vst.com";
			String password = "Wj201230210205wJ";
			
			MailUtils.sendImageAndCc(smtp, from, to, copyto, subject, table, username, password, null);
			
			json.put("code", 100);
		} catch (Exception e) {
			json.put("code", 300);
			logger.error("ajaxSendEmail error. ERROR:" + SystemException.getExceptionInfo(e));
		}
		printJson(json.toJSONString());
		return null;
	}
}
