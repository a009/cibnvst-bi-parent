package com.vst.common.pojo;

/**
 * sql数据保存配置
 * @author lhp
 * @date 2017-12-4 下午05:00:45
 * @version
 */
public class VstSqlSave {
	/**
	 * 主键id，随机6位数，唯一
	 */
	private String vst_save_id;
	
	/**
	 * 插入表名称
	 */
	private String vst_save_table;
	
	/**
	 * 列字段名称
	 */
	private String vst_save_name;
	
	/**
	 * 字段值类型，1：字符串，2：整型，3：浮点数
	 */
	private Integer vst_save_data_type;
	
	/**
	 * 类型，1、主键插入字段，2：普通插入字段，3：算环比key字段，4：算环比value字段
	 */
	private Integer vst_save_type;
	
	/**
	 * 该字段为空的时候默认值
	 */
	private String vst_save_default;
	
	/**
	 * 字段长度范围
	 */
	private Integer vst_save_length;
	
	/**
	 * 是否需要重新格式化，1：是，2：否
	 */
	private Integer vst_save_is_format;
	
	/**
	 * 格式化类型，1：日期，2：新增时间，3：操作人，4：vv量，5：人均vv，6：人均播放时间，7：毫秒转秒，8：字符串截取，9：算vv天环比，10：算vv周环比，11：算uv天环比，12：算uv周环比
	 */
	private String vst_save_format_type;
	
	/**
	 * 格式化所需要关联的字段，如果用多个，用英文逗号隔开，通常用于格式化类型5和6
	 */
	private String vst_save_format_union;
	
	/**
	 * 排序值，越大越靠前，默认0
	 */
	private Integer vst_save_index;
	
	/**
	 * 状态，1：正常，2：禁用
	 */
	private Integer vst_save_state;
	
	/**
	 * 新增时间，13位毫秒数
	 */
	private Long vst_save_addtime;
	
	/**
	 * 创建人
	 */
	private String vst_save_creator;
	
	/**
	 * 修改时间，13位毫秒数
	 */
	private Long vst_save_uptime;
	
	/**
	 * 修改人
	 */
	private String vst_save_updator;
	
	/**
	 * 备注
	 */
	private String vst_save_summary;

	public String getVst_save_id() {
		return vst_save_id;
	}

	public void setVst_save_id(String vstSaveId) {
		vst_save_id = vstSaveId;
	}

	public String getVst_save_table() {
		return vst_save_table;
	}

	public void setVst_save_table(String vstSaveTable) {
		vst_save_table = vstSaveTable;
	}

	public String getVst_save_name() {
		return vst_save_name;
	}

	public void setVst_save_name(String vstSaveName) {
		vst_save_name = vstSaveName;
	}

	public Integer getVst_save_data_type() {
		return vst_save_data_type;
	}

	public void setVst_save_data_type(Integer vstSaveDataType) {
		vst_save_data_type = vstSaveDataType;
	}

	public Integer getVst_save_type() {
		return vst_save_type;
	}

	public void setVst_save_type(Integer vstSaveType) {
		vst_save_type = vstSaveType;
	}

	public String getVst_save_default() {
		return vst_save_default;
	}

	public void setVst_save_default(String vstSaveDefault) {
		vst_save_default = vstSaveDefault;
	}

	public Integer getVst_save_length() {
		return vst_save_length;
	}

	public void setVst_save_length(Integer vstSaveLength) {
		vst_save_length = vstSaveLength;
	}

	public Integer getVst_save_is_format() {
		return vst_save_is_format;
	}

	public void setVst_save_is_format(Integer vstSaveIsFormat) {
		vst_save_is_format = vstSaveIsFormat;
	}

	public String getVst_save_format_type() {
		return vst_save_format_type;
	}

	public void setVst_save_format_type(String vstSaveFormatType) {
		vst_save_format_type = vstSaveFormatType;
	}

	public String getVst_save_format_union() {
		return vst_save_format_union;
	}

	public void setVst_save_format_union(String vstSaveFormatUnion) {
		vst_save_format_union = vstSaveFormatUnion;
	}

	public Integer getVst_save_index() {
		return vst_save_index;
	}

	public void setVst_save_index(Integer vstSaveIndex) {
		vst_save_index = vstSaveIndex;
	}

	public Integer getVst_save_state() {
		return vst_save_state;
	}

	public void setVst_save_state(Integer vstSaveState) {
		vst_save_state = vstSaveState;
	}

	public Long getVst_save_addtime() {
		return vst_save_addtime;
	}

	public void setVst_save_addtime(Long vstSaveAddtime) {
		vst_save_addtime = vstSaveAddtime;
	}

	public String getVst_save_creator() {
		return vst_save_creator;
	}

	public void setVst_save_creator(String vstSaveCreator) {
		vst_save_creator = vstSaveCreator;
	}

	public Long getVst_save_uptime() {
		return vst_save_uptime;
	}

	public void setVst_save_uptime(Long vstSaveUptime) {
		vst_save_uptime = vstSaveUptime;
	}

	public String getVst_save_updator() {
		return vst_save_updator;
	}

	public void setVst_save_updator(String vstSaveUpdator) {
		vst_save_updator = vstSaveUpdator;
	}

	public String getVst_save_summary() {
		return vst_save_summary;
	}

	public void setVst_save_summary(String vstSaveSummary) {
		vst_save_summary = vstSaveSummary;
	}

	@Override
	public String toString() {
		return "VstSqlSave [vst_save_addtime=" + vst_save_addtime
				+ ", vst_save_creator=" + vst_save_creator
				+ ", vst_save_data_type=" + vst_save_data_type
				+ ", vst_save_default=" + vst_save_default
				+ ", vst_save_format_type=" + vst_save_format_type
				+ ", vst_save_format_union=" + vst_save_format_union
				+ ", vst_save_id=" + vst_save_id + ", vst_save_index="
				+ vst_save_index + ", vst_save_is_format=" + vst_save_is_format
				+ ", vst_save_length=" + vst_save_length + ", vst_save_name="
				+ vst_save_name + ", vst_save_state=" + vst_save_state
				+ ", vst_save_summary=" + vst_save_summary
				+ ", vst_save_table=" + vst_save_table + ", vst_save_type="
				+ vst_save_type + ", vst_save_updator=" + vst_save_updator
				+ ", vst_save_uptime=" + vst_save_uptime + "]";
	}
}
