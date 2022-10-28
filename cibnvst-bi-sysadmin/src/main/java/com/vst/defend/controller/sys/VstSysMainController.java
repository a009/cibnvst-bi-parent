package com.vst.defend.controller.sys;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.pojo.VstSysUser;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.bean.LoginInfo;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.controller.BaseController;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.ErrorInfo;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.PropertiesReader;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.service.sys.VstSysUserService;

/**
 * 系统登录
 * @author lhp
 * @date 2017-4-6 上午11:59:03
 * @description
 * @version
 */
@Scope("session")
@Controller
@RequestMapping("/sysMain")
public class VstSysMainController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = VstSysMainController.class.getName();
	
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
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 进入首页
	 * @return
	 */
	@RequestMapping("/index.action")
	public String index(){
		String result;
		try {
			// 初始化
			this.initializeAction(_className);
			
			// 转向操作成功页面
			request.setAttribute("home_value", "login");
			
			// 根据职位判断，显示不同的首页
			int vst_sys_job = getUserSession().getVstSysUser().getVst_sys_job();
			if(vst_sys_job >= 0){
				result = INDEX;
			}else{
				result = "index2";
			}
		} catch (Exception e) {
			logger.error("index error." + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/index.action");
		}
		return result;
	}
	
	/**
	 * 系统登录
	 * @return
	 */
	@RequestMapping("/login.action")
	public String login(){
		try {
			// 初始化Action类
			this.initializeAction(_className);
			
			String username = request.getParameter("vst_sys_username");
			String password = request.getParameter("vst_sys_password");
			
			if(!ToolsString.isEmpty(username) && !ToolsString.isEmpty(password)){
				String ip = this.getIpAddr();
				LoginInfo loginInfo = new LoginInfo(username, password, ip, 1);
				// 登录验证
				UserSession userSession = _vstSysUserService.loginValidate(loginInfo);
				
				//第一次登录
				int loginTimes = 0;
				if(session.getAttribute("longinTimes") == null){
					session.setAttribute("longinTimes", 1);
				}else{
					loginTimes = Integer.parseInt(session.getAttribute("longinTimes").toString());
					if(loginTimes >= 2){
						//就开始验证验证码是否正确
						String random = (String) session.getAttribute(VstConstants.SESSION_KEY_RANDOM);
						if (random == null) {
							request.setAttribute("errorMsg", "验证码请重新输入");
							return LOGIN;
						}
						String randomCode = request.getParameter("randomCode");
						if (!random.equals(randomCode)) {
							request.setAttribute("errorMsg", "验证码错误");
							return LOGIN;//返回登录页面
						}
					}
				}
				
				// 返回结果页面
				String result = null;
				// 返回提示信息
				String errorMsg = "";
				
				switch (userSession.getLoginResult()) {
				case ErrorCode.LOGIN_ERROR_USERNAME_OR_PASSWORD_NULL:
					// 登录失败，跳转到登录页面
					result = LOGIN;
					errorMsg = "帐号或密码为空";
					break;
					
				case ErrorCode.LOGIN_ERROR_USERNAME_OR_PASSWORD:
					//错误次数+1
					session.setAttribute("longinTimes", (loginTimes+1));
					// 登录失败，跳转到登录页面
					result = LOGIN;
					errorMsg = "帐号或密码错误";
					break;
					
				case ErrorCode.LOGIN_ERROR_USER_STOP:
					// 登录失败，跳转到登录页面
					result = LOGIN;
					errorMsg = "该帐号已停用";
					break;
					
				case ErrorCode.LOGIN_ERROR_ROLE_STATE_DISABLED:
					// 登录失败，跳转到登录页面
					result = LOGIN;
					errorMsg = "该账号角色已禁用";
					break;
					
				default:
					//只要用户登录成功了  就吧session中登录错误的计数给清除掉
					session.removeAttribute("longinTimes");
					
					VstSysUser loginUser = userSession.getVstSysUser();
					
					String vst_sys_photo = loginUser.getVst_sys_photo();
					if(!ToolsString.isEmpty(vst_sys_photo)){
						String host = PropertiesReader.getProperty("[img]")+"/";
						loginUser.setVst_sys_photo(vst_sys_photo.replace("[img]/", host).replace("[img]", host));
					}
					// 登录成功，把用户信息放入session中
					session.setAttribute(VstConstants.SESSION_KEY_USER, userSession);
					// 获取所有包名
					Map<String, Object> params = new HashMap<String, Object>(3);
					params.put("vst_table_name", "vst_sys");
					params.put("vst_column_name", "pkgName");
					params.put("vst_state", VstConstants.STATE_AVALIABLE);
					session.setAttribute("pkgNames", _baseService.getDictionaryForMap(params));
					
					// 跳转到登录成功页面
					request.setAttribute("home_value", "login");
					
					// 根据职位判断，显示不同的首页
					int vst_sys_job = loginUser.getVst_sys_job();
					if(vst_sys_job >= 0){
						result = INDEX;
					}else{
						result = "index2";
					}
					return "redirect:/sysMain/index.action";
				}
				request.setAttribute("errorMsg", errorMsg);
				return result;
			}else{
				request.setAttribute("errorMsg", "帐号或密码为空");
				return LOGIN;
			}
		} catch (Exception e) {
			logger.error("login error. ERROR:" + SystemException.getExceptionInfo(e));
			request.setAttribute("errorMsg", "登录异常");
			return LOGIN;
		}
	}
	
	/**
	 * 主页
	 * @return
	 */
	@RequestMapping("/mainPage.action")
	public String mainPage(){
		String result;
		try {
			// 初始化
			this.initializeAction(_className);
			
			// 转向操作成功页面
			request.setAttribute("home_value", "login");
			
			// 根据职位判断，显示不同的首页
			int vst_sys_job = getUserSession().getVstSysUser().getVst_sys_job();
			if(vst_sys_job >= 0){
				result = INDEX;
			}else{
				result = "index2";
			}
		} catch (Exception e) {
			logger.error("mainPage error." + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		}
		return result;
	}
	
	/**
	 * 退出登录
	 * @return
	 */
	@RequestMapping("/logout.action")
	public String logout() {
		// 初始化
		this.initializeAction(_className); 
		// 说明为正常退出,而非超时或强制下线而退出
		request.setAttribute("logoutFlag", "normalExit");
		// 清除session信息,转向登录页面
		session.invalidate();
		
		return LOGIN;
	}
	
	/**
	 * 保存包名
	 * @return
	 */
	@RequestMapping("/savePkgName.action")
	public String savePkgName(){
		try{
			this.initializeAction(_className);
			String pkgName = ToolsString.checkEmpty(request.getParameter("pkgName"));
			session.setAttribute("pkgName", pkgName);
		}catch(Exception e){
			logger.error("savePkgName error. ERROR:" + e.getMessage());
		}
		return null;
	}
	
	/**
	 * 校验用户名、密码是否正确
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkLoginUser.action")
	public JSONObject checkLoginUser(){
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject json = new JSONObject();
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			json = _vstSysUserService.checkLoginUser(username, password);
			
			// 解决跨域问题
			String callback = ToolsString.checkEmpty(request.getParameter("callback"));
			if(!ToolsString.isEmpty(callback)){
				return JSONObject.parseObject(callback + "(" + json.toJSONString() + ")");
			}
		} catch (Exception e) {
			json.put("code", 301);
			json.put("msg", "校验异常");
			logger.error("checkLoginUser error.",e);
		}
		return json;
	}
	
	/**
	 * 切换首页
	 * @return
	 */
	@RequestMapping("/changeHome.action")
	public String changeHome(){
		response.setHeader("Access-Control-Allow-Origin", "*");
		try {
			// 初始化Action类
			this.initializeAction(_className);
			
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			if(!ToolsString.isEmpty(username) && !ToolsString.isEmpty(password)){
				String ip = this.getIpAddr();
				LoginInfo loginInfo = new LoginInfo(username, password, ip, 1);
				// 登录验证
				UserSession userSession = _vstSysUserService.loginValidate(loginInfo);
				
				// 返回结果页面
				String result = null;
				// 返回提示信息
				String errorMsg = "";
				
				switch (userSession.getLoginResult()) {
				case ErrorCode.LOGIN_ERROR_USERNAME_OR_PASSWORD_NULL:
					// 登录失败，跳转到登录页面
					result = LOGIN;
					errorMsg = "帐号或密码为空";
					break;
					
				case ErrorCode.LOGIN_ERROR_USERNAME_OR_PASSWORD:
					// 登录失败，跳转到登录页面
					result = LOGIN;
					errorMsg = "帐号或密码错误";
					break;
					
				case ErrorCode.LOGIN_ERROR_USER_STOP:
					// 登录失败，跳转到登录页面
					result = LOGIN;
					errorMsg = "该帐号已停用";
					break;
					
				case ErrorCode.LOGIN_ERROR_ROLE_STATE_DISABLED:
					// 登录失败，跳转到登录页面
					result = LOGIN;
					errorMsg = "该账号角色已禁用";
					break;
					
				default:
					VstSysUser loginUser = userSession.getVstSysUser();
					
					String vst_sys_photo = loginUser.getVst_sys_photo();
					if(!ToolsString.isEmpty(vst_sys_photo)){
						String host = PropertiesReader.getProperty("[img]")+"/";
						loginUser.setVst_sys_photo(vst_sys_photo.replace("[img]/", host).replace("[img]", host));
					}
					// 登录成功，把用户信息放入session中
					session.setAttribute(VstConstants.SESSION_KEY_USER, userSession);
					// 获取所有包名
					Map<String, Object> params = new HashMap<String, Object>(3);
					params.put("vst_table_name", "vst_sys");
					params.put("vst_column_name", "pkgName");
					params.put("vst_state", VstConstants.STATE_AVALIABLE);
					session.setAttribute("pkgNames", _baseService.getDictionaryForMap(params));
					
					// 跳转到登录成功页面
					request.setAttribute("home_value", "login");
					
					// 根据职位判断，显示不同的首页
					int vst_sys_job = loginUser.getVst_sys_job();
					if(vst_sys_job >= 0){
						result = INDEX;
					}else{
						result = "index2";
					}
					return "redirect:/sysMain/index.action";
				}
				request.setAttribute("errorMsg", errorMsg);
				return result;
			}else{
				request.setAttribute("errorMsg", "帐号或密码为空");
				return LOGIN;
			}
		} catch (Exception e) {
			logger.error("changeHome error." + SystemException.getExceptionInfo(e));
			request.setAttribute("errorMsg", "登录异常");
			return LOGIN;
		}
	}
}
