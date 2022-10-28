package com.vst.defend.communal.bean;

import java.util.List;
import java.util.Map;

import com.vst.common.pojo.VstSysButton;
import com.vst.common.pojo.VstSysModule;
import com.vst.common.pojo.VstSysUser;
import com.vst.common.tools.string.ToolsString;

/**
 * @author weiwei
 * @date 2014-10-27 上午11:19:14
 * @description 用户登录session
 * @version
 */
public class UserSession {

	/**
	 * 登录结果
	 */
	private int loginResult;

	/**
	 * 登录信息
	 */
	private LoginInfo loginInfo;

	/**
	 * 数据库中的用户信息
	 */
	private VstSysUser vstSysUser;

	/**
	 * 模块列表
	 */
	private Map<VstSysModule, List<VstSysModule>> modules;

	/**
	 * 所有的按钮集合，按照模块id分类
	 */
	private Map<String, List<VstSysButton>> buttons;

	/**
	 * 所有的系统角色名称
	 */
	private Map<String, String> sysRoles;

	/**
	 * 当前用户的角色
	 */
	private Map<String, String> roles;
	
	/**
	 * 当前模块id
	 */
	private String currModuleId;

	public int getLoginResult() {
		return loginResult;
	}

	public void setLoginResult(int loginResult) {
		this.loginResult = loginResult;
	}

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	public VstSysUser getVstSysUser() {
		return vstSysUser;
	}

	public void setVstSysUser(VstSysUser vstSysUser) {
		this.vstSysUser = vstSysUser;
	}

	public Map<VstSysModule, List<VstSysModule>> getModules() {
		return modules;
	}

	public void setModules(Map<VstSysModule, List<VstSysModule>> modules) {
		this.modules = modules;
	}

	public Map<String, List<VstSysButton>> getButtons() {
		return buttons;
	}

	public void setButtons(Map<String, List<VstSysButton>> buttons) {
		this.buttons = buttons;
	}

	public Map<String, String> getSysRoles() {
		return sysRoles;
	}

	public String getSysRoles(String roleIds) {
		// 返回结果
		StringBuilder result = new StringBuilder();
		if (!ToolsString.isEmpty(roleIds)) {
			for (String roleId : roleIds.split(",")) {
				result.append(sysRoles.get(roleId.trim())).append(",");
			}
		}
		return result.length() > 0 ? result.substring(0, result.length() - 1)
				: result.toString();
	}

	public void setSysRoles(Map<String, String> sysRoles) {
		this.sysRoles = sysRoles;
	}

	public String getCurrModuleId() {
		return currModuleId;
	}

	public void setCurrModuleId(String currModuleId) {
		this.currModuleId = currModuleId;
	}

	public Map<String, String> getRoles() {
		return roles;
	}

	public void setRoles(Map<String, String> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "UserSession [buttons=" + buttons + ", currModuleId="
				+ currModuleId + ", loginInfo=" + loginInfo + ", loginResult="
				+ loginResult + ", modules=" + modules + ", roles=" + roles
				+ ", sysRoles=" + sysRoles + ", vstSysUser=" + vstSysUser + "]";
	}
}
