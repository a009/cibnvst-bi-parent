package com.vst.common.pojo;

/**
 * 友盟-事件统计
 * @author lhp
 * @date 2018-3-15 上午11:43:47
 * @version
 */
public class VstUmengEvent {
	/**
	 * 主键，10位随机字符串
	 */
	private String vst_ue_id;
	
	/**
	 * 统计日期
	 */
	private Integer vst_ue_date;
	
	/**
	 * 事件名称
	 */
	private String vst_ue_event_name;
	
	/**
	 * 独立用户数
	 */
	private Integer vst_ue_uv;
	
	/**
	 * 新增时间，13位毫秒数
	 */
	private Long vst_ue_addtime;
	
	/**
	 * 创建人
	 */
	private String vst_ue_creator;
	
	/**
	 * 修改时间，13位毫秒数
	 */
	private Long vst_ue_uptime;
	
	/**
	 * 修改人
	 */
	private String vst_ue_updator;
	
	/**
	 * 备注
	 */
	private String vst_ue_summary;

	public String getVst_ue_id() {
		return vst_ue_id;
	}

	public void setVst_ue_id(String vstUeId) {
		vst_ue_id = vstUeId;
	}

	public Integer getVst_ue_date() {
		return vst_ue_date;
	}

	public void setVst_ue_date(Integer vstUeDate) {
		vst_ue_date = vstUeDate;
	}

	public String getVst_ue_event_name() {
		return vst_ue_event_name;
	}

	public void setVst_ue_event_name(String vstUeEventName) {
		vst_ue_event_name = vstUeEventName;
	}

	public Integer getVst_ue_uv() {
		return vst_ue_uv;
	}

	public void setVst_ue_uv(Integer vstUeUv) {
		vst_ue_uv = vstUeUv;
	}

	public Long getVst_ue_addtime() {
		return vst_ue_addtime;
	}

	public void setVst_ue_addtime(Long vstUeAddtime) {
		vst_ue_addtime = vstUeAddtime;
	}

	public String getVst_ue_creator() {
		return vst_ue_creator;
	}

	public void setVst_ue_creator(String vstUeCreator) {
		vst_ue_creator = vstUeCreator;
	}

	public Long getVst_ue_uptime() {
		return vst_ue_uptime;
	}

	public void setVst_ue_uptime(Long vstUeUptime) {
		vst_ue_uptime = vstUeUptime;
	}

	public String getVst_ue_updator() {
		return vst_ue_updator;
	}

	public void setVst_ue_updator(String vstUeUpdator) {
		vst_ue_updator = vstUeUpdator;
	}

	public String getVst_ue_summary() {
		return vst_ue_summary;
	}

	public void setVst_ue_summary(String vstUeSummary) {
		vst_ue_summary = vstUeSummary;
	}

	@Override
	public String toString() {
		return "VstUmengEvent [vst_ue_addtime=" + vst_ue_addtime
				+ ", vst_ue_creator=" + vst_ue_creator + ", vst_ue_date="
				+ vst_ue_date + ", vst_ue_event_name=" + vst_ue_event_name
				+ ", vst_ue_id=" + vst_ue_id + ", vst_ue_summary="
				+ vst_ue_summary + ", vst_ue_updator=" + vst_ue_updator
				+ ", vst_ue_uptime=" + vst_ue_uptime + ", vst_ue_uv="
				+ vst_ue_uv + "]";
	}
}
