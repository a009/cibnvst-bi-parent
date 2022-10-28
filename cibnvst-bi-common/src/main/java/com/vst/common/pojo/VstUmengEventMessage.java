package com.vst.common.pojo;

/**
 * 友盟-事件消息统计
 * @author lhp
 * @date 2018-3-15 上午11:46:55
 * @version
 */
public class VstUmengEventMessage {
	/**
	 * 主键，10位随机字符串
	 */
	private String vst_uem_id;
	
	/**
	 * 统计日期
	 */
	private Integer vst_uem_date;
	
	/**
	 * 事件名称
	 */
	private String vst_uem_event_name;
	
	/**
	 * 参数类型
	 */
	private String vst_uem_param_type;
	
	/**
	 * 参数值
	 */
	private String vst_uem_param_value;
	
	/**
	 * 消息数量
	 */
	private Long vst_uem_message_num;
	
	/**
	 * 占比
	 */
	private String vst_uem_radio;
	
	/**
	 * 新增时间，13位毫秒数
	 */
	private Long vst_uem_addtime;
	
	/**
	 * 创建人
	 */
	private String vst_uem_creator;
	
	/**
	 * 修改时间，13位毫秒数
	 */
	private Long vst_uem_uptime;
	
	/**
	 * 修改人
	 */
	private String vst_uem_updator;
	
	/**
	 * 备注
	 */
	private String vst_uem_summary;

	public String getVst_uem_id() {
		return vst_uem_id;
	}

	public void setVst_uem_id(String vstUemId) {
		vst_uem_id = vstUemId;
	}

	public Integer getVst_uem_date() {
		return vst_uem_date;
	}

	public void setVst_uem_date(Integer vstUemDate) {
		vst_uem_date = vstUemDate;
	}

	public String getVst_uem_event_name() {
		return vst_uem_event_name;
	}

	public void setVst_uem_event_name(String vstUemEventName) {
		vst_uem_event_name = vstUemEventName;
	}

	public String getVst_uem_param_type() {
		return vst_uem_param_type;
	}

	public void setVst_uem_param_type(String vstUemParamType) {
		vst_uem_param_type = vstUemParamType;
	}

	public String getVst_uem_param_value() {
		return vst_uem_param_value;
	}

	public void setVst_uem_param_value(String vstUemParamValue) {
		vst_uem_param_value = vstUemParamValue;
	}

	public Long getVst_uem_message_num() {
		return vst_uem_message_num;
	}

	public void setVst_uem_message_num(Long vstUemMessageNum) {
		vst_uem_message_num = vstUemMessageNum;
	}

	public String getVst_uem_radio() {
		return vst_uem_radio;
	}

	public void setVst_uem_radio(String vstUemRadio) {
		vst_uem_radio = vstUemRadio;
	}

	public Long getVst_uem_addtime() {
		return vst_uem_addtime;
	}

	public void setVst_uem_addtime(Long vstUemAddtime) {
		vst_uem_addtime = vstUemAddtime;
	}

	public String getVst_uem_creator() {
		return vst_uem_creator;
	}

	public void setVst_uem_creator(String vstUemCreator) {
		vst_uem_creator = vstUemCreator;
	}

	public Long getVst_uem_uptime() {
		return vst_uem_uptime;
	}

	public void setVst_uem_uptime(Long vstUemUptime) {
		vst_uem_uptime = vstUemUptime;
	}

	public String getVst_uem_updator() {
		return vst_uem_updator;
	}

	public void setVst_uem_updator(String vstUemUpdator) {
		vst_uem_updator = vstUemUpdator;
	}

	public String getVst_uem_summary() {
		return vst_uem_summary;
	}

	public void setVst_uem_summary(String vstUemSummary) {
		vst_uem_summary = vstUemSummary;
	}

	@Override
	public String toString() {
		return "VstUmengEventMessage [vst_uem_addtime=" + vst_uem_addtime
				+ ", vst_uem_creator=" + vst_uem_creator + ", vst_uem_date="
				+ vst_uem_date + ", vst_uem_event_name=" + vst_uem_event_name
				+ ", vst_uem_id=" + vst_uem_id + ", vst_uem_message_num="
				+ vst_uem_message_num + ", vst_uem_param_type="
				+ vst_uem_param_type + ", vst_uem_param_value="
				+ vst_uem_param_value + ", vst_uem_radio=" + vst_uem_radio
				+ ", vst_uem_summary=" + vst_uem_summary + ", vst_uem_updator="
				+ vst_uem_updator + ", vst_uem_uptime=" + vst_uem_uptime + "]";
	}
}
