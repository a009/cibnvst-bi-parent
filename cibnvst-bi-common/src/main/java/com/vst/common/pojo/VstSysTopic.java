package com.vst.common.pojo;

/**
 * @author weiwei(joslyn)
 * @date 2017-9-20 上午11:50:01
 * @decription
 * @version
 */
public class VstSysTopic {
	
	/**
	 * 主键id，随机6位数，唯一
	 */
	private String vst_topic_id;

	/**
	 * topic名称
	 */
	private String vst_topic_name;

	/**
	 * topic对应的值
	 */
	private String vst_topic_value;

	/**
	 * 排序值，越大越靠前，默认0
	 */
	private Integer vst_topic_index;

	/**
	 * 状态，1：正常，2：禁用
	 */
	private Integer vst_topic_state;

	/**
	 * 新增时间，13位毫秒数
	 */
	private Long vst_topic_addtime;

	/**
	 * 创建人
	 */
	private String vst_topic_creator;

	/**
	 * 修改时间，13位毫秒数
	 */
	private Long vst_topic_uptime;

	/**
	 * 修改人
	 */
	private String vst_topic_updator;

	/**
	 * 备注
	 */
	private String vst_topic_summary;

	public void setVst_topic_id(String vst_topic_id) {
		this.vst_topic_id = vst_topic_id;
	}

	public String getVst_topic_id() {
		return vst_topic_id;
	}

	public void setVst_topic_name(String vst_topic_name) {
		this.vst_topic_name = vst_topic_name;
	}

	public String getVst_topic_name() {
		return vst_topic_name;
	}

	public void setVst_topic_value(String vst_topic_value) {
		this.vst_topic_value = vst_topic_value;
	}

	public String getVst_topic_value() {
		return vst_topic_value;
	}

	public void setVst_topic_index(Integer vst_topic_index) {
		this.vst_topic_index = vst_topic_index;
	}

	public Integer getVst_topic_index() {
		return vst_topic_index;
	}

	public void setVst_topic_state(Integer vst_topic_state) {
		this.vst_topic_state = vst_topic_state;
	}

	public Integer getVst_topic_state() {
		return vst_topic_state;
	}

	public void setVst_topic_addtime(Long vst_topic_addtime) {
		this.vst_topic_addtime = vst_topic_addtime;
	}

	public Long getVst_topic_addtime() {
		return vst_topic_addtime;
	}

	public void setVst_topic_creator(String vst_topic_creator) {
		this.vst_topic_creator = vst_topic_creator;
	}

	public String getVst_topic_creator() {
		return vst_topic_creator;
	}

	public void setVst_topic_uptime(Long vst_topic_uptime) {
		this.vst_topic_uptime = vst_topic_uptime;
	}

	public Long getVst_topic_uptime() {
		return vst_topic_uptime;
	}

	public void setVst_topic_updator(String vst_topic_updator) {
		this.vst_topic_updator = vst_topic_updator;
	}

	public String getVst_topic_updator() {
		return vst_topic_updator;
	}

	public void setVst_topic_summary(String vst_topic_summary) {
		this.vst_topic_summary = vst_topic_summary;
	}

	public String getVst_topic_summary() {
		return vst_topic_summary;
	}

	@Override
	public String toString() {
		return "VstSysTopic[vst_topic_id=" + vst_topic_id + ",vst_topic_name="
				+ vst_topic_name + ",vst_topic_value=" + vst_topic_value
				+ ",vst_topic_index=" + vst_topic_index + ",vst_topic_state="
				+ vst_topic_state + ",vst_topic_addtime=" + vst_topic_addtime
				+ ",vst_topic_creator=" + vst_topic_creator
				+ ",vst_topic_uptime=" + vst_topic_uptime
				+ ",vst_topic_updator=" + vst_topic_updator
				+ ",vst_topic_summary=" + vst_topic_summary + "]";
	}
}