package com.vst.defend.communal.bean;

/**
 * @author weiwei
 * @date 2014-10-27 上午11:32:37
 * @description
 * @version
 */
public class LoginInfo {

	/**
	 * 登录名
	 */
	private String loginName;

	/**
	 * 登录密码
	 */
	private String loginPassword;

	/**
	 * 登录ip
	 */
	private String loginIp;
	
	/**
	 * 登录方式，1帐号密码登录，2QQ互联登录
	 */
	private Integer loginType;

	/**
	 * 默认构造器
	 */
	public LoginInfo() {

	}

	/**
	 * 带参构造器
	 * 
	 * @param wayCode
	 * @param loginName
	 * @param loginPassword
	 * @param loginIp
	 * @param loginType 1帐号密码登录，2QQ互联登录
	 */
	public LoginInfo(String loginName, String loginPassword, String loginIp, Integer loginType) {
		this.loginName = loginName;
		this.loginPassword = loginPassword;
		this.loginIp = loginIp;
		this.loginType = loginType;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Integer getLoginType() {
		return loginType;
	}

	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}

	@Override
	public String toString() {
		return "LoginInfo [loginIp=" + loginIp + ", loginName=" + loginName + ", loginPassword=" + loginPassword + ", loginType=" + loginType + "]";
	}

}
