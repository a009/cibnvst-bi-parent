package com.vst.common.pojo;

/**
 * @author weiwei
 * @date 2014-10-23 上午11:45:53
 * @description
 * @version
 */
public class VstSysOperateLog {

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
	 * 日志类型
	 */
	private Integer vst_log_type;

	/**
	 * 日志记录时间
	 */
	private Long vst_log_addtime;

	/**
	 * 日志内容
	 */
	private String vst_log_content;

	/**
	 * 操作模块id
	 */
	private String vst_module_id;

	/**
	 * 操作模块名称
	 */
	private String vst_module_name;

	/**
	 * 操作ip
	 */
	private String vst_log_ip;

	/**
	 * 默认构造器
	 */
	public VstSysOperateLog() {
		super();
	}

	/**
	 * 带参构造器
	 * 
	 * @param moduleName
	 * @param type
	 * @param content
	 */
	public VstSysOperateLog(String moduleId, Integer type, String content) {
		super();
		this.vst_module_id = moduleId;
		this.vst_log_type = type;
		this.vst_log_content = content;
	}

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

	public Integer getVst_log_type() {
		return vst_log_type;
	}

	public void setVst_log_type(Integer vstLogType) {
		vst_log_type = vstLogType;
	}

	public Long getVst_log_addtime() {
		return vst_log_addtime;
	}

	public void setVst_log_addtime(Long vstLogAddtime) {
		vst_log_addtime = vstLogAddtime;
	}

	public String getVst_log_content() {
		return vst_log_content;
	}

	public void setVst_log_content(String vstLogContent) {
		vst_log_content = vstLogContent;
	}

	public String getVst_module_id() {
		return vst_module_id;
	}

	public void setVst_module_id(String vstModuleId) {
		vst_module_id = vstModuleId;
	}

	public String getVst_module_name() {
		return vst_module_name;
	}

	public void setVst_module_name(String vstModuleName) {
		vst_module_name = vstModuleName;
	}

	public String getVst_log_ip() {
		return vst_log_ip;
	}

	public void setVst_log_ip(String vstLogIp) {
		vst_log_ip = vstLogIp;
	}

	@Override
	public String toString() {
		return "VstSysOperateLog [vst_log_addtime=" + vst_log_addtime
				+ ", vst_log_content=" + vst_log_content + ", vst_log_id="
				+ vst_log_id + ", vst_log_ip=" + vst_log_ip + ", vst_log_type="
				+ vst_log_type + ", vst_module_id=" + vst_module_id
				+ ", vst_module_name=" + vst_module_name + ", vst_pk_id="
				+ vst_pk_id + ", vst_sys_id=" + vst_sys_id
				+ ", vst_sys_username=" + vst_sys_username + "]";
	}
}
