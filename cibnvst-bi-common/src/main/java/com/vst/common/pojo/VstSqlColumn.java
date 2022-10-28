package com.vst.common.pojo;

/**
 * sql列配置
 * @author lhp
 * @date 2017-11-20 上午11:51:01
 * @version
 */
public class VstSqlColumn {
	/**
	 * 主键id，随机6位数，唯一
	 */
	private String vst_column_id;
	
	/**
	 * sql配置id
	 */
	private String vst_sql_id;
	
	/**
	 * 列字段名称
	 */
	private String vst_column_name;
	
	/**
	 * 列字段别名
	 */
	private String vst_column_alias;
	
	/**
	 * 该列操作类型，1：单纯列值，2：sum操作该列，3：distinct+count操作该列值
	 */
	private Integer vst_column_operateType;
	
	/**
	 * 数据类型，1：字符串，2：整数，3：浮点数
	 */
	private Integer vst_column_dataType;
	
	/**
	 * 排序值，越大越靠前，默认0
	 */
	private Integer vst_column_index;
	
	/**
	 * 状态，1：正常，2：禁用
	 */
	private Integer vst_column_state;
	
	/**
	 * 新增时间，13位毫秒数
	 */
	private Long vst_column_addtime;
	
	/**
	 * 创建人
	 */
	private String vst_column_creator;
	
	/**
	 * 修改时间，13位毫秒数
	 */
	private Long vst_column_uptime;
	
	/**
	 * 修改人
	 */
	private String vst_column_updator;
	
	/**
	 * 备注
	 */
	private String vst_column_summary;

	public String getVst_column_id() {
		return vst_column_id;
	}

	public void setVst_column_id(String vstColumnId) {
		vst_column_id = vstColumnId;
	}

	public String getVst_sql_id() {
		return vst_sql_id;
	}

	public void setVst_sql_id(String vstSqlId) {
		vst_sql_id = vstSqlId;
	}

	public String getVst_column_name() {
		return vst_column_name;
	}

	public void setVst_column_name(String vstColumnName) {
		vst_column_name = vstColumnName;
	}

	public String getVst_column_alias() {
		return vst_column_alias;
	}

	public void setVst_column_alias(String vstColumnAlias) {
		vst_column_alias = vstColumnAlias;
	}

	public Integer getVst_column_operateType() {
		return vst_column_operateType;
	}

	public void setVst_column_operateType(Integer vstColumnOperateType) {
		vst_column_operateType = vstColumnOperateType;
	}

	public Integer getVst_column_dataType() {
		return vst_column_dataType;
	}

	public void setVst_column_dataType(Integer vstColumnDataType) {
		vst_column_dataType = vstColumnDataType;
	}

	public Integer getVst_column_index() {
		return vst_column_index;
	}

	public void setVst_column_index(Integer vstColumnIndex) {
		vst_column_index = vstColumnIndex;
	}

	public Integer getVst_column_state() {
		return vst_column_state;
	}

	public void setVst_column_state(Integer vstColumnState) {
		vst_column_state = vstColumnState;
	}

	public Long getVst_column_addtime() {
		return vst_column_addtime;
	}

	public void setVst_column_addtime(Long vstColumnAddtime) {
		vst_column_addtime = vstColumnAddtime;
	}

	public String getVst_column_creator() {
		return vst_column_creator;
	}

	public void setVst_column_creator(String vstColumnCreator) {
		vst_column_creator = vstColumnCreator;
	}

	public Long getVst_column_uptime() {
		return vst_column_uptime;
	}

	public void setVst_column_uptime(Long vstColumnUptime) {
		vst_column_uptime = vstColumnUptime;
	}

	public String getVst_column_updator() {
		return vst_column_updator;
	}

	public void setVst_column_updator(String vstColumnUpdator) {
		vst_column_updator = vstColumnUpdator;
	}

	public String getVst_column_summary() {
		return vst_column_summary;
	}

	public void setVst_column_summary(String vstColumnSummary) {
		vst_column_summary = vstColumnSummary;
	}

	@Override
	public String toString() {
		return "VstSqlColumn [vst_column_addtime=" + vst_column_addtime
				+ ", vst_column_alias=" + vst_column_alias
				+ ", vst_column_creator=" + vst_column_creator
				+ ", vst_column_dataType=" + vst_column_dataType
				+ ", vst_column_id=" + vst_column_id + ", vst_column_index="
				+ vst_column_index + ", vst_column_name=" + vst_column_name
				+ ", vst_column_operateType=" + vst_column_operateType
				+ ", vst_column_state=" + vst_column_state
				+ ", vst_column_summary=" + vst_column_summary
				+ ", vst_column_updator=" + vst_column_updator
				+ ", vst_column_uptime=" + vst_column_uptime + ", vst_sql_id="
				+ vst_sql_id + "]";
	}
}
