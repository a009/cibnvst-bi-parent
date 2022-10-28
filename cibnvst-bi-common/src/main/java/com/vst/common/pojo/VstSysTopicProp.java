package com.vst.common.pojo;

/**
 * @author weiwei(joslyn)
 * @date 2017-9-20 上午11:50:30
 * @decription
 * @version
 */
public class VstSysTopicProp {

	/**
	 * 主键id，随机8位数，唯一
	 */
	private String vst_prop_id;

	/**
	 * topic对应的主键id
	 */
	private String vst_topic_id;

	/**
	 * 属性名称
	 */
	private String vst_prop_name;

	/**
	 * 属性对应的默认值
	 */
	private String vst_prop_value_default;

	/**
	 * 属性值类型，String、Integer、Long、Boolean、JSONObject、JSONArray
	 */
	private String vst_prop_value_type;

	/**
	 * 是否必填，1：是，2：否
	 */
	private Integer vst_prop_value_required;

	/**
	 * 属性值范围，格式说明：如果是数字类型，用[-,+]
	 * 表示负无穷大到正无穷大；如果是字符串或boolean类型，用{"a","b"}来枚举，用![0, 10]来表示本身字符串长度的限定
	 */
	private String vst_prop_value_range;

	/**
	 * 排序值，越大越靠前，默认0
	 */
	private Integer vst_prop_index;

	/**
	 * 状态，1：正常，2：禁用
	 */
	private Integer vst_prop_state;

	/**
	 * 新增时间，13位毫秒数
	 */
	private Long vst_prop_addtime;

	/**
	 * 创建人
	 */
	private String vst_prop_creator;

	/**
	 * 修改时间，13位毫秒数
	 */
	private Long vst_prop_uptime;

	/**
	 * 修改人
	 */
	private String vst_prop_updator;

	/**
	 * 备注
	 */
	private String vst_prop_summary;

	public void setVst_prop_id(String vst_prop_id) {
		this.vst_prop_id = vst_prop_id;
	}

	public String getVst_prop_id() {
		return vst_prop_id;
	}

	public void setVst_topic_id(String vst_topic_id) {
		this.vst_topic_id = vst_topic_id;
	}

	public String getVst_topic_id() {
		return vst_topic_id;
	}

	public void setVst_prop_name(String vst_prop_name) {
		this.vst_prop_name = vst_prop_name;
	}

	public String getVst_prop_name() {
		return vst_prop_name;
	}

	public void setVst_prop_value_default(String vst_prop_value_default) {
		this.vst_prop_value_default = vst_prop_value_default;
	}

	public String getVst_prop_value_default() {
		return vst_prop_value_default;
	}

	public void setVst_prop_value_type(String vst_prop_value_type) {
		this.vst_prop_value_type = vst_prop_value_type;
	}

	public String getVst_prop_value_type() {
		return vst_prop_value_type;
	}

	public void setVst_prop_value_required(Integer vst_prop_value_required) {
		this.vst_prop_value_required = vst_prop_value_required;
	}

	public Integer getVst_prop_value_required() {
		return vst_prop_value_required;
	}

	public void setVst_prop_value_range(String vst_prop_value_range) {
		this.vst_prop_value_range = vst_prop_value_range;
	}

	public String getVst_prop_value_range() {
		return vst_prop_value_range;
	}

	public void setVst_prop_index(Integer vst_prop_index) {
		this.vst_prop_index = vst_prop_index;
	}

	public Integer getVst_prop_index() {
		return vst_prop_index;
	}

	public void setVst_prop_state(Integer vst_prop_state) {
		this.vst_prop_state = vst_prop_state;
	}

	public Integer getVst_prop_state() {
		return vst_prop_state;
	}

	public void setVst_prop_addtime(Long vst_prop_addtime) {
		this.vst_prop_addtime = vst_prop_addtime;
	}

	public Long getVst_prop_addtime() {
		return vst_prop_addtime;
	}

	public void setVst_prop_creator(String vst_prop_creator) {
		this.vst_prop_creator = vst_prop_creator;
	}

	public String getVst_prop_creator() {
		return vst_prop_creator;
	}

	public void setVst_prop_uptime(Long vst_prop_uptime) {
		this.vst_prop_uptime = vst_prop_uptime;
	}

	public Long getVst_prop_uptime() {
		return vst_prop_uptime;
	}

	public void setVst_prop_updator(String vst_prop_updator) {
		this.vst_prop_updator = vst_prop_updator;
	}

	public String getVst_prop_updator() {
		return vst_prop_updator;
	}

	public void setVst_prop_summary(String vst_prop_summary) {
		this.vst_prop_summary = vst_prop_summary;
	}

	public String getVst_prop_summary() {
		return vst_prop_summary;
	}

	@Override
	public String toString() {
		return "VstSysTopicProp[vst_prop_id=" + vst_prop_id + ",vst_topic_id="
				+ vst_topic_id + ",vst_prop_name=" + vst_prop_name
				+ ",vst_prop_value_default=" + vst_prop_value_default
				+ ",vst_prop_value_type=" + vst_prop_value_type
				+ ",vst_prop_value_required=" + vst_prop_value_required
				+ ",vst_prop_value_range=" + vst_prop_value_range
				+ ",vst_prop_index=" + vst_prop_index + ",vst_prop_state="
				+ vst_prop_state + ",vst_prop_addtime=" + vst_prop_addtime
				+ ",vst_prop_creator=" + vst_prop_creator + ",vst_prop_uptime="
				+ vst_prop_uptime + ",vst_prop_updator=" + vst_prop_updator
				+ ",vst_prop_summary=" + vst_prop_summary + "]";
	}

}