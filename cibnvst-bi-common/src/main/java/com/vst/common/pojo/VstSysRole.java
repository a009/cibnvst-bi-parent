package com.vst.common.pojo;

/**
 * @author weiwei
 * @date 2014-10-23 上午11:38:37
 * @description
 * @version
 */
public class VstSysRole {

	/**
	 * 主键id
	 */
	private Integer vst_pk_id;

	/**
	 * 角色id
	 */
	private String vst_role_id;

	/**
	 * 角色名称
	 */
	private String vst_role_name;

	/**
	 * 角色类型
	 */
	private Integer vst_role_type;

	/**
	 * 新增时间
	 */
	private Long vst_role_addtime;

	/**
	 * 修改时间
	 */
	private Long vst_role_uptime;

	/**
	 * 操作人
	 */
	private String vst_role_operator;

	/**
	 * 状态
	 */
	private Integer vst_role_state;

	/**
	 * 备注
	 */
	private String vst_role_summary;

	public Integer getVst_pk_id() {
		return vst_pk_id;
	}

	public void setVst_pk_id(Integer vstPkId) {
		vst_pk_id = vstPkId;
	}

	public String getVst_role_id() {
		return vst_role_id;
	}

	public void setVst_role_id(String vstRoleId) {
		vst_role_id = vstRoleId;
	}

	public String getVst_role_name() {
		return vst_role_name;
	}

	public void setVst_role_name(String vstRoleName) {
		vst_role_name = vstRoleName;
	}

	public Integer getVst_role_type() {
		return vst_role_type;
	}

	public void setVst_role_type(Integer vstRoleType) {
		vst_role_type = vstRoleType;
	}

	public Long getVst_role_addtime() {
		return vst_role_addtime;
	}

	public void setVst_role_addtime(Long vstRoleAddtime) {
		vst_role_addtime = vstRoleAddtime;
	}

	public Long getVst_role_uptime() {
		return vst_role_uptime;
	}

	public void setVst_role_uptime(Long vstRoleUptime) {
		vst_role_uptime = vstRoleUptime;
	}

	public String getVst_role_operator() {
		return vst_role_operator;
	}

	public void setVst_role_operator(String vstRoleOperator) {
		vst_role_operator = vstRoleOperator;
	}

	public Integer getVst_role_state() {
		return vst_role_state;
	}

	public void setVst_role_state(Integer vstRoleState) {
		vst_role_state = vstRoleState;
	}

	public String getVst_role_summary() {
		return vst_role_summary;
	}

	public void setVst_role_summary(String vstRoleSummary) {
		vst_role_summary = vstRoleSummary;
	}

	@Override
	public String toString() {
		return "VstSysRole [vst_pk_id=" + vst_pk_id + ", vst_role_addtime="
				+ vst_role_addtime + ", vst_role_id=" + vst_role_id
				+ ", vst_role_name=" + vst_role_name + ", vst_role_operator="
				+ vst_role_operator + ", vst_role_state=" + vst_role_state
				+ ", vst_role_summary=" + vst_role_summary + ", vst_role_type="
				+ vst_role_type + ", vst_role_uptime=" + vst_role_uptime + "]";
	}

}
