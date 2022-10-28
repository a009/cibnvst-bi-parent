package com.vst.common.pojo;

/**
 * @author weiwei
 * @date 2014-11-24 下午12:14:43
 * @description
 * @version
 */
public class VstSysUser {

	/**
	 * 主键
	 */
	private Integer vst_pk_id;

	/**
	 * 管理员id，12位随机数
	 */
	private String vst_sys_id;

	/**
	 * 管理员名称
	 */
	private String vst_sys_name;

	/**
	 * 管理员登录名称
	 */
	private String vst_sys_username;

	/**
	 * 管理员登录密码
	 */
	private String vst_sys_password;

	/**
	 * 管理员类型0：系统管理员，1：用户维护
	 */
	private Integer vst_sys_type;
	
	/**
	 * 角色id
	 */
	private String vst_role_id;
	
	/**
	 * 所属部门(1总经办、2行政部、3财务部、4后台、5安卓、6编辑部、7产品部、8测试部、9市场部、10商务部、11北京、0未知)
	 */
	private Integer vst_sys_division;
	
	/**
	 * 职位(1老总、2主管、3经理、4组长、5员工、0其他)
	 */
	private Integer vst_sys_job;
	
	/**
	 * 渠道号，all代表全部，多个用逗号分割
	 */
	private String vst_sys_channel;
	
	/**
	 * 用户头像
	 */
	private String vst_sys_photo;

	/**
	 * 状态
	 */
	private Integer vst_sys_state;
	
	/**
	 * 添加时间
	 */
	private Long vst_sys_addtime;

	/**
	 * 更新时间
	 */
	private Long vst_sys_uptime;

	/**
	 * 操作人
	 */
	private String vst_sys_operator;
	
	/**
	 * 备注
	 */
	private String vst_sys_summary;

	public Integer getVst_pk_id() {
		return vst_pk_id;
	}

	public void setVst_pk_id(Integer vstPkId) {
		vst_pk_id = vstPkId;
	}

	public String getVst_sys_id() {
		return vst_sys_id;
	}

	public void setVst_sys_id(String vstSysId) {
		vst_sys_id = vstSysId;
	}

	public String getVst_sys_name() {
		return vst_sys_name;
	}

	public void setVst_sys_name(String vstSysName) {
		vst_sys_name = vstSysName;
	}

	public String getVst_sys_username() {
		return vst_sys_username;
	}

	public void setVst_sys_username(String vstSysUsername) {
		vst_sys_username = vstSysUsername;
	}

	public String getVst_sys_password() {
		return vst_sys_password;
	}

	public void setVst_sys_password(String vstSysPassword) {
		vst_sys_password = vstSysPassword;
	}

	public Integer getVst_sys_type() {
		return vst_sys_type;
	}

	public void setVst_sys_type(Integer vstSysType) {
		vst_sys_type = vstSysType;
	}

	public String getVst_role_id() {
		return vst_role_id;
	}

	public void setVst_role_id(String vstRoleId) {
		vst_role_id = vstRoleId;
	}

	public Integer getVst_sys_division() {
		return vst_sys_division;
	}

	public void setVst_sys_division(Integer vstSysDivision) {
		vst_sys_division = vstSysDivision;
	}

	public Integer getVst_sys_job() {
		return vst_sys_job;
	}

	public void setVst_sys_job(Integer vstSysJob) {
		vst_sys_job = vstSysJob;
	}

	public String getVst_sys_channel() {
		return vst_sys_channel;
	}

	public void setVst_sys_channel(String vstSysChannel) {
		vst_sys_channel = vstSysChannel;
	}

	public String getVst_sys_photo() {
		return vst_sys_photo;
	}

	public void setVst_sys_photo(String vstSysPhoto) {
		vst_sys_photo = vstSysPhoto;
	}

	public Integer getVst_sys_state() {
		return vst_sys_state;
	}

	public void setVst_sys_state(Integer vstSysState) {
		vst_sys_state = vstSysState;
	}

	public Long getVst_sys_addtime() {
		return vst_sys_addtime;
	}

	public void setVst_sys_addtime(Long vstSysAddtime) {
		vst_sys_addtime = vstSysAddtime;
	}

	public Long getVst_sys_uptime() {
		return vst_sys_uptime;
	}

	public void setVst_sys_uptime(Long vstSysUptime) {
		vst_sys_uptime = vstSysUptime;
	}

	public String getVst_sys_operator() {
		return vst_sys_operator;
	}

	public void setVst_sys_operator(String vstSysOperator) {
		vst_sys_operator = vstSysOperator;
	}

	public String getVst_sys_summary() {
		return vst_sys_summary;
	}

	public void setVst_sys_summary(String vstSysSummary) {
		vst_sys_summary = vstSysSummary;
	}

	@Override
	public String toString() {
		return "VstSysUser [vst_pk_id=" + vst_pk_id + ", vst_role_id="
				+ vst_role_id + ", vst_sys_addtime=" + vst_sys_addtime
				+ ", vst_sys_channel=" + vst_sys_channel
				+ ", vst_sys_division=" + vst_sys_division + ", vst_sys_id="
				+ vst_sys_id + ", vst_sys_job=" + vst_sys_job
				+ ", vst_sys_name=" + vst_sys_name + ", vst_sys_operator="
				+ vst_sys_operator + ", vst_sys_password=" + vst_sys_password
				+ ", vst_sys_photo=" + vst_sys_photo + ", vst_sys_state="
				+ vst_sys_state + ", vst_sys_summary=" + vst_sys_summary
				+ ", vst_sys_type=" + vst_sys_type + ", vst_sys_uptime="
				+ vst_sys_uptime + ", vst_sys_username=" + vst_sys_username
				+ "]";
	}
}
