package com.vst.common.pojo;

/**
 * @author weiwei
 * @date 2014-11-3 上午10:38:43
 * @description
 * @version
 */
public class VstSysLoginLog {

	/**
	 * 主键id
	 */
	private Integer vst_pk_id;

	/**
	 * 日志id
	 */
	private String vst_log_id;

	/**
	 * 管理员id
	 */
	private String vst_sys_id;

	/**
	 * 登陆名称
	 */
	private String vst_sys_username;

	/**
	 * 登陆时间
	 */
	private Long vst_log_login_time;

	/**
	 * 操作ip
	 */
	private String vst_log_ip;

	public Integer getVst_pk_id() {
		return vst_pk_id;
	}

	public void setVst_pk_id(Integer vstPkId) {
		vst_pk_id = vstPkId;
	}

	public String getVst_log_id() {
		return vst_log_id;
	}

	public void setVst_log_id(String vstLogId) {
		vst_log_id = vstLogId;
	}

	public String getVst_sys_id() {
		return vst_sys_id;
	}

	public void setVst_sys_id(String vstSysId) {
		vst_sys_id = vstSysId;
	}

	public String getVst_sys_username() {
		return vst_sys_username;
	}

	public void setVst_sys_username(String vstSysUsername) {
		vst_sys_username = vstSysUsername;
	}

	public Long getVst_log_login_time() {
		return vst_log_login_time;
	}

	public void setVst_log_login_time(Long vstLogLoginTime) {
		vst_log_login_time = vstLogLoginTime;
	}

	public String getVst_log_ip() {
		return vst_log_ip;
	}

	public void setVst_log_ip(String vstLogIp) {
		vst_log_ip = vstLogIp;
	}

	@Override
	public String toString() {
		return "VstSysLoginLog [vst_log_id=" + vst_log_id + ", vst_log_ip="
				+ vst_log_ip + ", vst_log_login_time=" + vst_log_login_time
				+ ", vst_pk_id=" + vst_pk_id + ", vst_sys_id=" + vst_sys_id
				+ ", vst_sys_username=" + vst_sys_username + "]";
	}

}
