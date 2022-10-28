package com.vst.common.pojo;

/**
 * 用户二次活跃渠道统计-对外
 * @author liukai
 * @date 2019-11-28 下午11:35:56
 * @version
 */
public class OuterVstUserSecondaryActiveChannel {
	/**
	 * 主键，10位随机字符串
	 */
	private String vst_usac_id;
	
	/**
	 * 统计日期
	 */
	private Integer vst_usac_date;
	
	/**
	 * 包名
	 */
	private String vst_usac_pkg;
	
	/**
	 * 渠道
	 */
	private String vst_usac_channel;
	
	/**
	 * 二次用户活跃渠道数量
	 */
	private Long vst_usac_amount;
	
	/**
	 * 天环比
	 */
	private String vst_usac_dod;
	
	/**
	 * 周环比
	 */
	private String vst_usac_wow;
	
	/**
	 * 月环比
	 */
	private String vst_usac_mom;
	
	/**
	 * 调整系数，默认0.35
	 */
	private String vst_usac_modulus;
	
	/**
	 * 审核状态，1：未审核，2：已审核
	 */
	private Integer vst_usac_state;
	
	/**
	 * 同步时间
	 */
	private Long vst_usac_synctime;
	
	/**
	 * 新增时间，13位毫秒数
	 */
	private Long vst_usac_addtime;
	
	/**
	 * 创建人
	 */
	private String vst_usac_creator;
	
	/**
	 * 修改时间，13位毫秒数
	 */
	private Long vst_usac_uptime;
	
	/**
	 * 修改人
	 */
	private String vst_usac_updator;
	
	/**
	 * 备注
	 */
	private String vst_usac_summary;

	public String getVst_usac_id() {
		return vst_usac_id;
	}

	public void setVst_usac_id(String vstusacId) {
		vst_usac_id = vstusacId;
	}

	public Integer getVst_usac_date() {
		return vst_usac_date;
	}

	public void setVst_usac_date(Integer vstusacDate) {
		vst_usac_date = vstusacDate;
	}

	public String getVst_usac_pkg() {
		return vst_usac_pkg;
	}

	public void setVst_usac_pkg(String vstusacPkg) {
		vst_usac_pkg = vstusacPkg;
	}

	public String getVst_usac_channel() {
		return vst_usac_channel;
	}

	public void setVst_usac_channel(String vstusacChannel) {
		vst_usac_channel = vstusacChannel;
	}

	public Long getVst_usac_amount() {
		return vst_usac_amount;
	}

	public void setVst_usac_amount(Long vstusacAmount) {
		vst_usac_amount = vstusacAmount;
	}

	public String getVst_usac_dod() {
		return vst_usac_dod;
	}

	public void setVst_usac_dod(String vstusacDod) {
		vst_usac_dod = vstusacDod;
	}

	public String getVst_usac_wow() {
		return vst_usac_wow;
	}

	public void setVst_usac_wow(String vstusacWow) {
		vst_usac_wow = vstusacWow;
	}

	public String getVst_usac_mom() {
		return vst_usac_mom;
	}

	public void setVst_usac_mom(String vstusacMom) {
		vst_usac_mom = vstusacMom;
	}

	public String getVst_usac_modulus() {
		return vst_usac_modulus;
	}

	public void setVst_usac_modulus(String vstusacModulus) {
		vst_usac_modulus = vstusacModulus;
	}

	public Integer getVst_usac_state() {
		return vst_usac_state;
	}

	public void setVst_usac_state(Integer vstusacState) {
		vst_usac_state = vstusacState;
	}

	public Long getVst_usac_synctime() {
		return vst_usac_synctime;
	}

	public void setVst_usac_synctime(Long vstusacSynctime) {
		vst_usac_synctime = vstusacSynctime;
	}

	public Long getVst_usac_addtime() {
		return vst_usac_addtime;
	}

	public void setVst_usac_addtime(Long vstusacAddtime) {
		vst_usac_addtime = vstusacAddtime;
	}

	public String getVst_usac_creator() {
		return vst_usac_creator;
	}

	public void setVst_usac_creator(String vstusacCreator) {
		vst_usac_creator = vstusacCreator;
	}

	public Long getVst_usac_uptime() {
		return vst_usac_uptime;
	}

	public void setVst_usac_uptime(Long vstusacUptime) {
		vst_usac_uptime = vstusacUptime;
	}

	public String getVst_usac_updator() {
		return vst_usac_updator;
	}

	public void setVst_usac_updator(String vstusacUpdator) {
		vst_usac_updator = vstusacUpdator;
	}

	public String getVst_usac_summary() {
		return vst_usac_summary;
	}

	public void setVst_usac_summary(String vstusacSummary) {
		vst_usac_summary = vstusacSummary;
	}

	@Override
	public String toString() {
		return "OuterVstUserAddChannel [vst_usac_addtime=" + vst_usac_addtime
				+ ", vst_usac_amount=" + vst_usac_amount + ", vst_usac_channel="
				+ vst_usac_channel + ", vst_usac_creator=" + vst_usac_creator
				+ ", vst_usac_date=" + vst_usac_date + ", vst_usac_dod="
				+ vst_usac_dod + ", vst_usac_id=" + vst_usac_id
				+ ", vst_usac_modulus=" + vst_usac_modulus + ", vst_usac_mom="
				+ vst_usac_mom + ", vst_usac_pkg=" + vst_usac_pkg
				+ ", vst_usac_state=" + vst_usac_state + ", vst_usac_summary="
				+ vst_usac_summary + ", vst_usac_synctime=" + vst_usac_synctime
				+ ", vst_usac_updator=" + vst_usac_updator + ", vst_usac_uptime="
				+ vst_usac_uptime + ", vst_usac_wow=" + vst_usac_wow + "]";
	}
}
