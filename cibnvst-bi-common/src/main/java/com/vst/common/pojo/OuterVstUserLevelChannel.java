package com.vst.common.pojo;

/**
 * 季质量用户统计-对外
 * @author lhp
 * @date 2018-1-18 下午12:30:33
 * @version
 */
public class OuterVstUserLevelChannel {
	/**
	 * 主键，10位随机字符串
	 */
	private String vst_ulc_id;
	
	/**
	 * 统计日期
	 */
	private Integer vst_ulc_date;
	
	/**
	 * 包名
	 */
	private String vst_ulc_pkg;
	
	/**
	 * 渠道
	 */
	private String vst_ulc_channel;
	
	/**
	 * 季新增用户数
	 */
	private Long vst_ulc_add_amount;
	
	/**
	 * 季质量用户数
	 */
	private Long vst_ulc_season_amount;
	
	/**
	 * 季质量用户天环比
	 */
	private String vst_ulc_season_dod;
	
	/**
	 * 季质量用户周环比
	 */
	private String vst_ulc_season_wow;
	
	/**
	 * 季质量用户占比
	 */
	private String vst_ulc_season_radio;
	
	/**
	 * 调整系数，默认0.35
	 */
	private String vst_ulc_modulus;
	
	/**
	 * 审核状态，1：未审核，2：已审核
	 */
	private Integer vst_ulc_state;
	
	/**
	 * 同步时间
	 */
	private Long vst_ulc_synctime;
	
	/**
	 * 新增时间，13位毫秒数
	 */
	private Long vst_ulc_addtime;
	
	/**
	 * 创建人
	 */
	private String vst_ulc_creator;
	
	/**
	 * 修改时间，13位毫秒数
	 */
	private Long vst_ulc_uptime;
	
	/**
	 * 修改人
	 */
	private String vst_ulc_updator;
	
	/**
	 * 备注
	 */
	private String vst_ulc_summary;

	public String getVst_ulc_id() {
		return vst_ulc_id;
	}

	public void setVst_ulc_id(String vstUlcId) {
		vst_ulc_id = vstUlcId;
	}

	public Integer getVst_ulc_date() {
		return vst_ulc_date;
	}

	public void setVst_ulc_date(Integer vstUlcDate) {
		vst_ulc_date = vstUlcDate;
	}

	public String getVst_ulc_pkg() {
		return vst_ulc_pkg;
	}

	public void setVst_ulc_pkg(String vstUlcPkg) {
		vst_ulc_pkg = vstUlcPkg;
	}

	public String getVst_ulc_channel() {
		return vst_ulc_channel;
	}

	public void setVst_ulc_channel(String vstUlcChannel) {
		vst_ulc_channel = vstUlcChannel;
	}

	public Long getVst_ulc_add_amount() {
		return vst_ulc_add_amount;
	}

	public void setVst_ulc_add_amount(Long vstUlcAddAmount) {
		vst_ulc_add_amount = vstUlcAddAmount;
	}

	public Long getVst_ulc_season_amount() {
		return vst_ulc_season_amount;
	}

	public void setVst_ulc_season_amount(Long vstUlcSeasonAmount) {
		vst_ulc_season_amount = vstUlcSeasonAmount;
	}

	public String getVst_ulc_season_dod() {
		return vst_ulc_season_dod;
	}

	public void setVst_ulc_season_dod(String vstUlcSeasonDod) {
		vst_ulc_season_dod = vstUlcSeasonDod;
	}

	public String getVst_ulc_season_wow() {
		return vst_ulc_season_wow;
	}

	public void setVst_ulc_season_wow(String vstUlcSeasonWow) {
		vst_ulc_season_wow = vstUlcSeasonWow;
	}

	public String getVst_ulc_season_radio() {
		return vst_ulc_season_radio;
	}

	public void setVst_ulc_season_radio(String vstUlcSeasonRadio) {
		vst_ulc_season_radio = vstUlcSeasonRadio;
	}

	public String getVst_ulc_modulus() {
		return vst_ulc_modulus;
	}

	public void setVst_ulc_modulus(String vstUlcModulus) {
		vst_ulc_modulus = vstUlcModulus;
	}

	public Integer getVst_ulc_state() {
		return vst_ulc_state;
	}

	public void setVst_ulc_state(Integer vstUlcState) {
		vst_ulc_state = vstUlcState;
	}

	public Long getVst_ulc_synctime() {
		return vst_ulc_synctime;
	}

	public void setVst_ulc_synctime(Long vstUlcSynctime) {
		vst_ulc_synctime = vstUlcSynctime;
	}

	public Long getVst_ulc_addtime() {
		return vst_ulc_addtime;
	}

	public void setVst_ulc_addtime(Long vstUlcAddtime) {
		vst_ulc_addtime = vstUlcAddtime;
	}

	public String getVst_ulc_creator() {
		return vst_ulc_creator;
	}

	public void setVst_ulc_creator(String vstUlcCreator) {
		vst_ulc_creator = vstUlcCreator;
	}

	public Long getVst_ulc_uptime() {
		return vst_ulc_uptime;
	}

	public void setVst_ulc_uptime(Long vstUlcUptime) {
		vst_ulc_uptime = vstUlcUptime;
	}

	public String getVst_ulc_updator() {
		return vst_ulc_updator;
	}

	public void setVst_ulc_updator(String vstUlcUpdator) {
		vst_ulc_updator = vstUlcUpdator;
	}

	public String getVst_ulc_summary() {
		return vst_ulc_summary;
	}

	public void setVst_ulc_summary(String vstUlcSummary) {
		vst_ulc_summary = vstUlcSummary;
	}

	@Override
	public String toString() {
		return "OuterVstUserLevelChannel [vst_ulc_add_amount="
				+ vst_ulc_add_amount + ", vst_ulc_addtime=" + vst_ulc_addtime
				+ ", vst_ulc_channel=" + vst_ulc_channel + ", vst_ulc_creator="
				+ vst_ulc_creator + ", vst_ulc_date=" + vst_ulc_date
				+ ", vst_ulc_id=" + vst_ulc_id + ", vst_ulc_modulus="
				+ vst_ulc_modulus + ", vst_ulc_pkg=" + vst_ulc_pkg
				+ ", vst_ulc_season_amount=" + vst_ulc_season_amount
				+ ", vst_ulc_season_dod=" + vst_ulc_season_dod
				+ ", vst_ulc_season_radio=" + vst_ulc_season_radio
				+ ", vst_ulc_season_wow=" + vst_ulc_season_wow
				+ ", vst_ulc_state=" + vst_ulc_state + ", vst_ulc_summary="
				+ vst_ulc_summary + ", vst_ulc_synctime=" + vst_ulc_synctime
				+ ", vst_ulc_updator=" + vst_ulc_updator + ", vst_ulc_uptime="
				+ vst_ulc_uptime + "]";
	}
}
