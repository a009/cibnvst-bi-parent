package com.vst.common.pojo;

/**
 * 新增渠道用户统计-对外
 * @author lhp
 * @date 2018-1-18 下午12:25:56
 * @version
 */
public class OuterVstUserAddChannel {
	/**
	 * 主键，10位随机字符串
	 */
	private String vst_uac_id;
	
	/**
	 * 统计日期
	 */
	private Integer vst_uac_date;
	
	/**
	 * 包名
	 */
	private String vst_uac_pkg;
	
	/**
	 * 渠道
	 */
	private String vst_uac_channel;
	
	/**
	 * 新增用户数
	 */
	private Long vst_uac_amount;
	
	/**
	 * 天环比
	 */
	private String vst_uac_dod;
	
	/**
	 * 周环比
	 */
	private String vst_uac_wow;
	
	/**
	 * 月环比
	 */
	private String vst_uac_mom;
	
	/**
	 * 调整系数，默认0.35
	 */
	private String vst_uac_modulus;
	
	/**
	 * 审核状态，1：未审核，2：已审核
	 */
	private Integer vst_uac_state;
	
	/**
	 * 同步时间
	 */
	private Long vst_uac_synctime;
	
	/**
	 * 新增时间，13位毫秒数
	 */
	private Long vst_uac_addtime;
	
	/**
	 * 创建人
	 */
	private String vst_uac_creator;
	
	/**
	 * 修改时间，13位毫秒数
	 */
	private Long vst_uac_uptime;
	
	/**
	 * 修改人
	 */
	private String vst_uac_updator;
	
	/**
	 * 备注
	 */
	private String vst_uac_summary;

	public String getVst_uac_id() {
		return vst_uac_id;
	}

	public void setVst_uac_id(String vstUacId) {
		vst_uac_id = vstUacId;
	}

	public Integer getVst_uac_date() {
		return vst_uac_date;
	}

	public void setVst_uac_date(Integer vstUacDate) {
		vst_uac_date = vstUacDate;
	}

	public String getVst_uac_pkg() {
		return vst_uac_pkg;
	}

	public void setVst_uac_pkg(String vstUacPkg) {
		vst_uac_pkg = vstUacPkg;
	}

	public String getVst_uac_channel() {
		return vst_uac_channel;
	}

	public void setVst_uac_channel(String vstUacChannel) {
		vst_uac_channel = vstUacChannel;
	}

	public Long getVst_uac_amount() {
		return vst_uac_amount;
	}

	public void setVst_uac_amount(Long vstUacAmount) {
		vst_uac_amount = vstUacAmount;
	}

	public String getVst_uac_dod() {
		return vst_uac_dod;
	}

	public void setVst_uac_dod(String vstUacDod) {
		vst_uac_dod = vstUacDod;
	}

	public String getVst_uac_wow() {
		return vst_uac_wow;
	}

	public void setVst_uac_wow(String vstUacWow) {
		vst_uac_wow = vstUacWow;
	}

	public String getVst_uac_mom() {
		return vst_uac_mom;
	}

	public void setVst_uac_mom(String vstUacMom) {
		vst_uac_mom = vstUacMom;
	}

	public String getVst_uac_modulus() {
		return vst_uac_modulus;
	}

	public void setVst_uac_modulus(String vstUacModulus) {
		vst_uac_modulus = vstUacModulus;
	}

	public Integer getVst_uac_state() {
		return vst_uac_state;
	}

	public void setVst_uac_state(Integer vstUacState) {
		vst_uac_state = vstUacState;
	}

	public Long getVst_uac_synctime() {
		return vst_uac_synctime;
	}

	public void setVst_uac_synctime(Long vstUacSynctime) {
		vst_uac_synctime = vstUacSynctime;
	}

	public Long getVst_uac_addtime() {
		return vst_uac_addtime;
	}

	public void setVst_uac_addtime(Long vstUacAddtime) {
		vst_uac_addtime = vstUacAddtime;
	}

	public String getVst_uac_creator() {
		return vst_uac_creator;
	}

	public void setVst_uac_creator(String vstUacCreator) {
		vst_uac_creator = vstUacCreator;
	}

	public Long getVst_uac_uptime() {
		return vst_uac_uptime;
	}

	public void setVst_uac_uptime(Long vstUacUptime) {
		vst_uac_uptime = vstUacUptime;
	}

	public String getVst_uac_updator() {
		return vst_uac_updator;
	}

	public void setVst_uac_updator(String vstUacUpdator) {
		vst_uac_updator = vstUacUpdator;
	}

	public String getVst_uac_summary() {
		return vst_uac_summary;
	}

	public void setVst_uac_summary(String vstUacSummary) {
		vst_uac_summary = vstUacSummary;
	}

	@Override
	public String toString() {
		return "OuterVstUserAddChannel [vst_uac_addtime=" + vst_uac_addtime
				+ ", vst_uac_amount=" + vst_uac_amount + ", vst_uac_channel="
				+ vst_uac_channel + ", vst_uac_creator=" + vst_uac_creator
				+ ", vst_uac_date=" + vst_uac_date + ", vst_uac_dod="
				+ vst_uac_dod + ", vst_uac_id=" + vst_uac_id
				+ ", vst_uac_modulus=" + vst_uac_modulus + ", vst_uac_mom="
				+ vst_uac_mom + ", vst_uac_pkg=" + vst_uac_pkg
				+ ", vst_uac_state=" + vst_uac_state + ", vst_uac_summary="
				+ vst_uac_summary + ", vst_uac_synctime=" + vst_uac_synctime
				+ ", vst_uac_updator=" + vst_uac_updator + ", vst_uac_uptime="
				+ vst_uac_uptime + ", vst_uac_wow=" + vst_uac_wow + "]";
	}
}
