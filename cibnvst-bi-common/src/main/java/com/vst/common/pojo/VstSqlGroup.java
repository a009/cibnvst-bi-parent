package com.vst.common.pojo;

/**
 * sql分组配置
 * @author lhp
 * @date 2017-11-20 上午11:54:54
 * @version
 */
public class VstSqlGroup {
	/**
	 * 主键id，随机6位数，唯一
	 */
	private String vst_group_id;
	
	/**
	 * sql配置id
	 */
	private String vst_sql_id;
	
	/**
	 * 分组字段名称
	 */
	private String vst_group_name;
	
	/**
	 * 分组字段别名
	 */
	private String vst_group_alias;
	
	/**
	 * 分组描述
	 */
	private String vst_group_desc;
	
	/**
	 * 排序值，越大越靠前，默认0
	 */
	private Integer vst_group_index;
	
	/**
	 * 状态，1：正常，2：禁用
	 */
	private Integer vst_group_state;
	
	/**
	 * 新增时间，13位毫秒数
	 */
	private Long vst_group_addtime;
	
	/**
	 * 创建人
	 */
	private String vst_group_creator;
	
	/**
	 * 修改时间，13位毫秒数
	 */
	private Long vst_group_uptime;
	
	/**
	 * 修改人
	 */
	private String vst_group_updator;
	
	/**
	 * 备注
	 */
	private String vst_group_summary;

	public String getVst_group_id() {
		return vst_group_id;
	}

	public void setVst_group_id(String vstGroupId) {
		vst_group_id = vstGroupId;
	}

	public String getVst_sql_id() {
		return vst_sql_id;
	}

	public void setVst_sql_id(String vstSqlId) {
		vst_sql_id = vstSqlId;
	}

	public String getVst_group_name() {
		return vst_group_name;
	}

	public void setVst_group_name(String vstGroupName) {
		vst_group_name = vstGroupName;
	}

	public String getVst_group_alias() {
		return vst_group_alias;
	}

	public void setVst_group_alias(String vstGroupAlias) {
		vst_group_alias = vstGroupAlias;
	}

	public String getVst_group_desc() {
		return vst_group_desc;
	}

	public void setVst_group_desc(String vstGroupDesc) {
		vst_group_desc = vstGroupDesc;
	}

	public Integer getVst_group_index() {
		return vst_group_index;
	}

	public void setVst_group_index(Integer vstGroupIndex) {
		vst_group_index = vstGroupIndex;
	}

	public Integer getVst_group_state() {
		return vst_group_state;
	}

	public void setVst_group_state(Integer vstGroupState) {
		vst_group_state = vstGroupState;
	}

	public Long getVst_group_addtime() {
		return vst_group_addtime;
	}

	public void setVst_group_addtime(Long vstGroupAddtime) {
		vst_group_addtime = vstGroupAddtime;
	}

	public String getVst_group_creator() {
		return vst_group_creator;
	}

	public void setVst_group_creator(String vstGroupCreator) {
		vst_group_creator = vstGroupCreator;
	}

	public Long getVst_group_uptime() {
		return vst_group_uptime;
	}

	public void setVst_group_uptime(Long vstGroupUptime) {
		vst_group_uptime = vstGroupUptime;
	}

	public String getVst_group_updator() {
		return vst_group_updator;
	}

	public void setVst_group_updator(String vstGroupUpdator) {
		vst_group_updator = vstGroupUpdator;
	}

	public String getVst_group_summary() {
		return vst_group_summary;
	}

	public void setVst_group_summary(String vstGroupSummary) {
		vst_group_summary = vstGroupSummary;
	}

	@Override
	public String toString() {
		return "VstSqlGroup [vst_group_addtime=" + vst_group_addtime
				+ ", vst_group_alias=" + vst_group_alias
				+ ", vst_group_creator=" + vst_group_creator
				+ ", vst_group_desc=" + vst_group_desc + ", vst_group_id="
				+ vst_group_id + ", vst_group_index=" + vst_group_index
				+ ", vst_group_name=" + vst_group_name + ", vst_group_state="
				+ vst_group_state + ", vst_group_summary=" + vst_group_summary
				+ ", vst_group_updator=" + vst_group_updator
				+ ", vst_group_uptime=" + vst_group_uptime + ", vst_sql_id="
				+ vst_sql_id + "]";
	}
}
