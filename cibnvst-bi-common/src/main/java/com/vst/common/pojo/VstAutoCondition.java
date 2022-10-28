package com.vst.common.pojo;

/**
 * 自动化-查询条件
 * @author lhp
 * @date 2017-9-12 下午03:09:55
 * @version
 */
public class VstAutoCondition {
	/**
	 * 主键，自增长
	 */
	private Integer vst_condition_pk;
	
	/**
	 * 主键，10位随机字符串
	 */
	private String vst_condition_id;
	
	/**
	 * 代码编号
	 */
	private String vst_code;
	
	/**
	 * 属性
	 */
	private String vst_condition_arg;
	
	/**
	 * 查询条件
	 */
	private String vst_condition_script;
	
	/**
	 * 是否必填：1-是，2-否
	 */
	private Integer vst_condition_need;
	
	/**
	 * 参数类型，string,int,double,date
	 */
	private String vst_condition_type;
	
	/**
	 * 排序
	 */
	private Integer vst_condition_index;
	
	/**
	 * 状态：1-正常，2-禁用
	 */
	private Integer vst_condition_state;
	
	/**
	 * 新增时间，毫秒数
	 */
	private Long vst_condition_addtime;
	
	/**
	 * 创建人
	 */
	private String vst_condition_creator;
	
	/**
	 * 修改时间，毫秒数
	 */
	private Long vst_condition_uptime;
	
	/**
	 * 修改人
	 */
	private String vst_condition_updator;
	
	/**
	 * 备注
	 */
	private String vst_condition_summary;

	public Integer getVst_condition_pk() {
		return vst_condition_pk;
	}

	public void setVst_condition_pk(Integer vstConditionPk) {
		vst_condition_pk = vstConditionPk;
	}

	public String getVst_condition_id() {
		return vst_condition_id;
	}

	public void setVst_condition_id(String vstConditionId) {
		vst_condition_id = vstConditionId;
	}

	public String getVst_code() {
		return vst_code;
	}

	public void setVst_code(String vstCode) {
		vst_code = vstCode;
	}

	public String getVst_condition_arg() {
		return vst_condition_arg;
	}

	public void setVst_condition_arg(String vstConditionArg) {
		vst_condition_arg = vstConditionArg;
	}

	public String getVst_condition_script() {
		return vst_condition_script;
	}

	public void setVst_condition_script(String vstConditionScript) {
		vst_condition_script = vstConditionScript;
	}

	public Integer getVst_condition_need() {
		return vst_condition_need;
	}

	public void setVst_condition_need(Integer vstConditionNeed) {
		vst_condition_need = vstConditionNeed;
	}

	public String getVst_condition_type() {
		return vst_condition_type;
	}

	public void setVst_condition_type(String vstConditionType) {
		vst_condition_type = vstConditionType;
	}

	public Integer getVst_condition_index() {
		return vst_condition_index;
	}

	public void setVst_condition_index(Integer vstConditionIndex) {
		vst_condition_index = vstConditionIndex;
	}

	public Integer getVst_condition_state() {
		return vst_condition_state;
	}

	public void setVst_condition_state(Integer vstConditionState) {
		vst_condition_state = vstConditionState;
	}

	public Long getVst_condition_addtime() {
		return vst_condition_addtime;
	}

	public void setVst_condition_addtime(Long vstConditionAddtime) {
		vst_condition_addtime = vstConditionAddtime;
	}

	public String getVst_condition_creator() {
		return vst_condition_creator;
	}

	public void setVst_condition_creator(String vstConditionCreator) {
		vst_condition_creator = vstConditionCreator;
	}

	public Long getVst_condition_uptime() {
		return vst_condition_uptime;
	}

	public void setVst_condition_uptime(Long vstConditionUptime) {
		vst_condition_uptime = vstConditionUptime;
	}

	public String getVst_condition_updator() {
		return vst_condition_updator;
	}

	public void setVst_condition_updator(String vstConditionUpdator) {
		vst_condition_updator = vstConditionUpdator;
	}

	public String getVst_condition_summary() {
		return vst_condition_summary;
	}

	public void setVst_condition_summary(String vstConditionSummary) {
		vst_condition_summary = vstConditionSummary;
	}

	@Override
	public String toString() {
		return "VstAutoCondition [vst_code=" + vst_code
				+ ", vst_condition_addtime=" + vst_condition_addtime
				+ ", vst_condition_arg=" + vst_condition_arg
				+ ", vst_condition_creator=" + vst_condition_creator
				+ ", vst_condition_id=" + vst_condition_id
				+ ", vst_condition_index=" + vst_condition_index
				+ ", vst_condition_need=" + vst_condition_need
				+ ", vst_condition_pk=" + vst_condition_pk
				+ ", vst_condition_script=" + vst_condition_script
				+ ", vst_condition_state=" + vst_condition_state
				+ ", vst_condition_summary=" + vst_condition_summary
				+ ", vst_condition_type=" + vst_condition_type
				+ ", vst_condition_updator=" + vst_condition_updator
				+ ", vst_condition_uptime=" + vst_condition_uptime + "]";
	}
}
