package com.vst.common.pojo;

/**
 * 字典表
 * @author lhp
 * @date 2017-6-14 上午11:46:23
 * @description
 * @version
 */
public class VstDictionary {
	/**
	 * 主键
	 */
	private Integer vst_id;
	
	/**
	 * 包名，0表示全部
	 */
	private String vst_pkg;
	
	/**
	 * 表名
	 */
	private String vst_table_name;
	
	/**
	 * 字段名
	 */
	private String vst_column_name;
	
	/**
	 * 属性键
	 */
	private String vst_property_key;
	
	/**
	 * 属性值
	 */
	private String vst_property_value;
	
	/**
	 * 排序
	 */
	private Integer vst_index;
	
	/**
	 * 状态，1：正常，2：禁用
	 */
	private Integer vst_state;
	
	/**
	 * 新增时间
	 */
	private Long vst_addtime;
	
	/**
	 * 修改时间
	 */
	private Long vst_uptime;
	
	/**
	 * 操作人
	 */
	private String vst_operator;

	public Integer getVst_id() {
		return vst_id;
	}

	public void setVst_id(Integer vstId) {
		vst_id = vstId;
	}

	public String getVst_pkg() {
		return vst_pkg;
	}

	public void setVst_pkg(String vstPkg) {
		vst_pkg = vstPkg;
	}

	public String getVst_table_name() {
		return vst_table_name;
	}

	public void setVst_table_name(String vstTableName) {
		vst_table_name = vstTableName;
	}

	public String getVst_column_name() {
		return vst_column_name;
	}

	public void setVst_column_name(String vstColumnName) {
		vst_column_name = vstColumnName;
	}

	public String getVst_property_key() {
		return vst_property_key;
	}

	public void setVst_property_key(String vstPropertyKey) {
		vst_property_key = vstPropertyKey;
	}

	public String getVst_property_value() {
		return vst_property_value;
	}

	public void setVst_property_value(String vstPropertyValue) {
		vst_property_value = vstPropertyValue;
	}

	public Integer getVst_index() {
		return vst_index;
	}

	public void setVst_index(Integer vstIndex) {
		vst_index = vstIndex;
	}

	public Integer getVst_state() {
		return vst_state;
	}

	public void setVst_state(Integer vstState) {
		vst_state = vstState;
	}

	public Long getVst_addtime() {
		return vst_addtime;
	}

	public void setVst_addtime(Long vstAddtime) {
		vst_addtime = vstAddtime;
	}

	public Long getVst_uptime() {
		return vst_uptime;
	}

	public void setVst_uptime(Long vstUptime) {
		vst_uptime = vstUptime;
	}

	public String getVst_operator() {
		return vst_operator;
	}

	public void setVst_operator(String vstOperator) {
		vst_operator = vstOperator;
	}

	@Override
	public String toString() {
		return "VstDictionary [vst_addtime=" + vst_addtime
				+ ", vst_column_name=" + vst_column_name + ", vst_id=" + vst_id
				+ ", vst_index=" + vst_index + ", vst_operator=" + vst_operator
				+ ", vst_pkg=" + vst_pkg + ", vst_property_key="
				+ vst_property_key + ", vst_property_value="
				+ vst_property_value + ", vst_state=" + vst_state
				+ ", vst_table_name=" + vst_table_name + ", vst_uptime="
				+ vst_uptime + "]";
	}
}
