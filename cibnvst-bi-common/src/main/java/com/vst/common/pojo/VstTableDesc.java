package com.vst.common.pojo;

/**
 * 表算法描述
 * @author lhp
 * @date 2018-4-16 下午03:14:31
 * @version
 */
public class VstTableDesc {
	/**
	 * 主键，6位随机数
	 */
	private String vst_td_id;
	
	/**
	 * 表名
	 */
	private String vst_td_table;
	
	/**
	 * 描述属性
	 */
	private String vst_td_title;
	
	/**
	 * 描述内容
	 */
	private String vst_td_desc;
	
	/**
	 * 排序值，越大越靠前，默认0
	 */
	private Integer vst_td_index;
	
	/**
	 * 状态，1：正常，2：禁用
	 */
	private Integer vst_td_state;
	
	/**
	 * 新增时间，13位毫秒数
	 */
	private Long vst_td_addtime;
	
	/**
	 * 创建人
	 */
	private String vst_td_creator;
	
	/**
	 * 修改时间，13位毫秒数
	 */
	private Long vst_td_uptime;
	
	/**
	 * 修改人
	 */
	private String vst_td_updator;
	
	/**
	 * 备注
	 */
	private String vst_td_summary;

	public String getVst_td_id() {
		return vst_td_id;
	}

	public void setVst_td_id(String vstTdId) {
		vst_td_id = vstTdId;
	}

	public String getVst_td_table() {
		return vst_td_table;
	}

	public void setVst_td_table(String vstTdTable) {
		vst_td_table = vstTdTable;
	}

	public String getVst_td_title() {
		return vst_td_title;
	}

	public void setVst_td_title(String vstTdTitle) {
		vst_td_title = vstTdTitle;
	}

	public String getVst_td_desc() {
		return vst_td_desc;
	}

	public void setVst_td_desc(String vstTdDesc) {
		vst_td_desc = vstTdDesc;
	}

	public Integer getVst_td_index() {
		return vst_td_index;
	}

	public void setVst_td_index(Integer vstTdIndex) {
		vst_td_index = vstTdIndex;
	}

	public Integer getVst_td_state() {
		return vst_td_state;
	}

	public void setVst_td_state(Integer vstTdState) {
		vst_td_state = vstTdState;
	}

	public Long getVst_td_addtime() {
		return vst_td_addtime;
	}

	public void setVst_td_addtime(Long vstTdAddtime) {
		vst_td_addtime = vstTdAddtime;
	}

	public String getVst_td_creator() {
		return vst_td_creator;
	}

	public void setVst_td_creator(String vstTdCreator) {
		vst_td_creator = vstTdCreator;
	}

	public Long getVst_td_uptime() {
		return vst_td_uptime;
	}

	public void setVst_td_uptime(Long vstTdUptime) {
		vst_td_uptime = vstTdUptime;
	}

	public String getVst_td_updator() {
		return vst_td_updator;
	}

	public void setVst_td_updator(String vstTdUpdator) {
		vst_td_updator = vstTdUpdator;
	}

	public String getVst_td_summary() {
		return vst_td_summary;
	}

	public void setVst_td_summary(String vstTdSummary) {
		vst_td_summary = vstTdSummary;
	}

	@Override
	public String toString() {
		return "VstTableDesc [vst_td_addtime=" + vst_td_addtime
				+ ", vst_td_creator=" + vst_td_creator + ", vst_td_desc="
				+ vst_td_desc + ", vst_td_id=" + vst_td_id + ", vst_td_index="
				+ vst_td_index + ", vst_td_state=" + vst_td_state
				+ ", vst_td_summary=" + vst_td_summary + ", vst_td_table="
				+ vst_td_table + ", vst_td_title=" + vst_td_title
				+ ", vst_td_updator=" + vst_td_updator + ", vst_td_uptime="
				+ vst_td_uptime + "]";
	}
}
