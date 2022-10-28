package com.vst.defend.communal.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.vst.common.pojo.VstSysButton;
import com.vst.common.tools.io.ToolsIO;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.util.CutPage;
import com.vst.defend.communal.util.VstConstants;

/**
 * 
 * @author lhp
 * @date 2017-4-6 上午10:38:45
 * @description
 * @version
 */
@Scope("prototype")
public class BaseController {
	
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(BaseController.class);
	
	/**
	 * Http请求对象
	 */
	protected HttpServletRequest request;

	/**
	 * Http响应对象
	 */
	protected HttpServletResponse response;

	/**
	 * 会话
	 */
	protected HttpSession session;
	
	/**
	 * 分页对象
	 */
	protected CutPage cutPage;
	
	/**
	 * 页面显示对象
	 */
	protected Map<String, Object> formMap;
	
	/**
	 * 操作成功页面
	 */
	protected final static String SUCCESS = "doSuccess"; 
	
	/**
	 * 操作失败页面
	 */
	protected final static String ERROR = "doError"; 
	
	/**
	 * 操作详情页面
	 */
	protected final static String INFO = "doInfo";
	
	/**
	 * 超时退出到登录页面
	 */
	protected final static String TIMEOUT = "timeout";
	
	/**
	 * 登录页面
	 */
	protected final static String LOGIN = "login";
	
	/**
	 * 登录成功页面
	 */
	protected final static String INDEX = "index";
	
	/**
	 * action类名
	 */
	private String className;
	
	/**
	 * 异常码
	 */
	private int returnCode;
	
	/**
	 * 返回信息
	 */
	private String returnInfo;
	
	/**
	 * 返回路径
	 */
	private String returnUrl;
	
	/**
	 * 模块id
	 */
	protected String moduleId;
	
	/**
	 * 初始化 Action,将 request 对象传入以简化操作
	 * @param className
	 */
	public void initializeAction(String className) {		
		this.className = className;
		logger.info("进入 [" + className + "] Action.");
	}
	
	/**
	 * 获取userSession
	 * @return
	 */
	public UserSession getUserSession(String moduleIds){
		// 从session中获取渠道代码
		UserSession user = (UserSession) session.getAttribute(VstConstants.SESSION_KEY_USER);
//		user.setCurrModuleId(moduleId);//获取不到页面的moduleId值
		if(!ToolsString.isEmpty(moduleIds) && user != null){
			user.setCurrModuleId(moduleIds);
		}
		return user;
	}
	
	/**
	 * 获取userSession
	 * @return
	 */
	public UserSession getUserSession(){
		// 从session中获取渠道代码
		UserSession user = (UserSession) session.getAttribute(VstConstants.SESSION_KEY_USER);
		return user;
	}
	
	/**
	 * 转向错误页面
	 * @param errCode 异常码
	 * @param errMsg 返回信息
	 * @param backUrl 返回路径
	 * @return
	 */
	public String moveToError(int errCode, String errMsg,String backUrl) {
		StringBuffer sb = new StringBuffer();

		this.setReturnCode(errCode);
		this.setReturnInfo(errMsg);
		this.setReturnUrl(backUrl);

		sb.append("[" + this.className + "] 执行结果 : Error \n");
		sb.append("详细信息 : " + errMsg + "\n");
		sb.append("返回页 : " + ERROR + "\n");
		sb.append("[" + this.className + "] 结束操作" + "\n");

		logger.info(sb.toString());

		request.setAttribute("returnCode", errCode);
		request.setAttribute("returnInfo", errMsg);
		request.setAttribute("returnUrl", backUrl);
		
		return ERROR;
	}
	
	/**
	 * 转向成功页面
	 * @param successInfo
	 * @param successUrl
	 * @return
	 */
	public String moveToSuccess(String successInfo,String successUrl) {
		StringBuffer sb = new StringBuffer();

		this.setReturnInfo(successInfo);
		this.setReturnUrl(successUrl);
		
		sb.append("[" + this.className + "] 执行结果 : success \n");
		sb.append("返回页 : " + SUCCESS + "\n");
		sb.append("[" + className + "] 结束操作" + "\n");

		logger.info(sb.toString());
		
		request.setAttribute("returnInfo", successInfo);
		request.setAttribute("returnUrl", successUrl);

		return SUCCESS;
	}
	
	/**
	 * 操作完成后转向目标页面，并显示操作信息
	 * @param optCode
	 * @param optMsg
	 * @param resultStr
	 * @return
	 */
	public String moveToPage(int optCode, String optMsg, String resultStr) {
		StringBuffer sb = new StringBuffer();
		
		this.setReturnCode(optCode);
		this.setReturnInfo(optMsg);
		String retmsg = (VstConstants.OPERATE_SUCCESS == optCode ? "Success" : "Error");
		sb.append("[").append(this.className).append("] 执行结果 : ").append(retmsg).append(" \n");
		sb.append("详细信息 : ").append(optMsg).append("\n");
		sb.append("[").append(this.className).append("] 结束操作" + "\n");
		
		logger.info(sb.toString());
		
		request.setAttribute("returnCode", optCode);
		request.setAttribute("returnInfo", optMsg);
		
		return resultStr;
	}
	
	/**
	 * 转向详细信息页面
	 * @param errCode 异常码
	 * @param errMsg 返回信息
	 * @param backUrl 返回路径
	 * @return
	 */
	public String moveToInfo(int errCode, String errMsg,String backUrl) {
		StringBuffer sb = new StringBuffer();

		this.setReturnCode(errCode);
		this.setReturnInfo(errMsg);
		this.setReturnUrl(backUrl);

		sb.append("[" + this.className + "] 执行结果 : Error \n");
		sb.append("详细信息 : " + errMsg + "\n");
		sb.append("返回页 : " + INFO + "\n");
		sb.append("[" + this.className + "] 结束操作" + "\n");

		logger.info(sb.toString());
		
		request.setAttribute("returnCode", errCode);
		request.setAttribute("returnInfo", errMsg);
		request.setAttribute("returnUrl", backUrl);

		return INFO;
	}
	
	/**
	 * 转向超时页面
	 * @return
	 */
	public String moveToTimeOut() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("[" + this.className + "] 执行结果 : 退出系统 \n");
		sb.append("[" + className + "] 结束操作" + "\n");

		logger.info(sb.toString());
		//清除session信息,转向登录页面
		return TIMEOUT;
	}
	
	/**
	 * 获取客户端ip地址
	 * @return
	 */
	public String getIpAddr() {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 根据模块id，获取页面按钮
	 * @param moduleId
	 * @return
	 */
	public List<VstSysButton> getPageButtons(String moduleId){
		// 获取session中的user信息
		Object obj = session.getAttribute(VstConstants.SESSION_KEY_USER);
		
		List<VstSysButton> result = null;
		if(obj != null){
			UserSession user = (UserSession) obj;
			result = user.getButtons().get(moduleId);
		}
		return result;
	}
	
	/**
	 * 输出json数据
	 * @param json
	 */
	protected void printJson(String json){
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			if(out != null){
				out.print(json);
				out.flush();
			}
		} catch (Exception e) {
			logger.error("get PrintWriter ERROR:"+e.getMessage());
		}finally{
			ToolsIO.closeStream(out);// 关流操作
		}
	}
	
	/**
	 * 设置Http对象
	 * 每个请求调用之前都会执行该方法
	 * @param request
	 * @param response
	 */
	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}

	public CutPage getCutPage() {
		return cutPage;
	}

	public void setCutPage(CutPage cutPage) {
		this.cutPage = cutPage;
	}

	public Map<String, Object> getFormMap() {
		return formMap;
	}

	public void setFormMap(Map<String, Object> formMap) {
		this.formMap = formMap;
	}

	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnInfo() {
		return returnInfo;
	}

	public void setReturnInfo(String returnInfo) {
		this.returnInfo = returnInfo;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
}
