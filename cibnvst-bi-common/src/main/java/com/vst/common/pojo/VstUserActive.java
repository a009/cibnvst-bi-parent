package com.vst.common.pojo;

/**
 * 活跃用户
 * @author lhp
 * @date 2017-11-10 上午11:07:08
 * @version
 */
public class VstUserActive {
	/**
	 * 主键，自增长
	 */
	private Long vst_pk_id;
	
	/**
	 * 统计日期
	 */
	private Integer vst_user_active_date;
	
	/**
	 * 包名（客户端名）
	 */
	private String vst_user_active_pkg;
	
	/**
	 * 维度名1
	 */
	private String vst_user_active_dim_name1;
	
	/**
	 * 维度值1
	 */
	private String vst_user_active_dim_value1;
	
	/**
	 * 维度名2
	 */
	private String vst_user_active_dim_name2;
	
	/**
	 * 维度值2
	 */
	private String vst_user_active_dim_value2;
	
	/**
	 * 日活跃用户数
	 */
	private Integer vst_user_active_dau;
	
	/**
	 * 日活跃用户数天环比
	 */
	private String vst_user_active_dau_dod;
	
	/**
	 * 日活跃用户数周环比
	 */
	private String vst_user_active_dau_wow;
	
	/**
	 * 周活跃用户数
	 */
	private Integer vst_user_active_wau;
	
	/**
	 * 周活跃用户数天环比
	 */
	private String vst_user_active_wau_dod;
	
	/**
	 * 周活跃用户数周环比
	 */
	private String vst_user_active_wau_wow;
	
	/**
	 * 月活跃用户数
	 */
	private Integer vst_user_active_mau;
	
	/**
	 * 月活跃用户数天环比
	 */
	private String vst_user_active_mau_dod;
	
	/**
	 * 月活跃用户数周环比
	 */
	private String vst_user_active_mau_wow;
	
	/**
	 * 季活跃用户数
	 */
	private Integer vst_user_active_sau;
	
	/**
	 * 季活跃用户数天环比
	 */
	private String vst_user_active_sau_dod;
	
	/**
	 * 季活跃用户数周环比
	 */
	private String vst_user_active_sau_wow;
	
	/**
	 * 修改时间
	 */
	private Long vst_user_active_uptime;
	
	/**
	 * 修改人（普通文本）
	 */
	private String vst_user_active_updator;

	public Long getVst_pk_id() {
		return vst_pk_id;
	}

	public void setVst_pk_id(Long vstPkId) {
		vst_pk_id = vstPkId;
	}

	public Integer getVst_user_active_date() {
		return vst_user_active_date;
	}

	public void setVst_user_active_date(Integer vstUserActiveDate) {
		vst_user_active_date = vstUserActiveDate;
	}

	public String getVst_user_active_pkg() {
		return vst_user_active_pkg;
	}

	public void setVst_user_active_pkg(String vstUserActivePkg) {
		vst_user_active_pkg = vstUserActivePkg;
	}

	public String getVst_user_active_dim_name1() {
		return vst_user_active_dim_name1;
	}

	public void setVst_user_active_dim_name1(String vstUserActiveDimName1) {
		vst_user_active_dim_name1 = vstUserActiveDimName1;
	}

	public String getVst_user_active_dim_value1() {
		return vst_user_active_dim_value1;
	}

	public void setVst_user_active_dim_value1(String vstUserActiveDimValue1) {
		vst_user_active_dim_value1 = vstUserActiveDimValue1;
	}

	public String getVst_user_active_dim_name2() {
		return vst_user_active_dim_name2;
	}

	public void setVst_user_active_dim_name2(String vstUserActiveDimName2) {
		vst_user_active_dim_name2 = vstUserActiveDimName2;
	}

	public String getVst_user_active_dim_value2() {
		return vst_user_active_dim_value2;
	}

	public void setVst_user_active_dim_value2(String vstUserActiveDimValue2) {
		vst_user_active_dim_value2 = vstUserActiveDimValue2;
	}

	public Integer getVst_user_active_dau() {
		return vst_user_active_dau;
	}

	public void setVst_user_active_dau(Integer vstUserActiveDau) {
		vst_user_active_dau = vstUserActiveDau;
	}

	public String getVst_user_active_dau_dod() {
		return vst_user_active_dau_dod;
	}

	public void setVst_user_active_dau_dod(String vstUserActiveDauDod) {
		vst_user_active_dau_dod = vstUserActiveDauDod;
	}

	public String getVst_user_active_dau_wow() {
		return vst_user_active_dau_wow;
	}

	public void setVst_user_active_dau_wow(String vstUserActiveDauWow) {
		vst_user_active_dau_wow = vstUserActiveDauWow;
	}

	public Integer getVst_user_active_wau() {
		return vst_user_active_wau;
	}

	public void setVst_user_active_wau(Integer vstUserActiveWau) {
		vst_user_active_wau = vstUserActiveWau;
	}

	public String getVst_user_active_wau_dod() {
		return vst_user_active_wau_dod;
	}

	public void setVst_user_active_wau_dod(String vstUserActiveWauDod) {
		vst_user_active_wau_dod = vstUserActiveWauDod;
	}

	public String getVst_user_active_wau_wow() {
		return vst_user_active_wau_wow;
	}

	public void setVst_user_active_wau_wow(String vstUserActiveWauWow) {
		vst_user_active_wau_wow = vstUserActiveWauWow;
	}

	public Integer getVst_user_active_mau() {
		return vst_user_active_mau;
	}

	public void setVst_user_active_mau(Integer vstUserActiveMau) {
		vst_user_active_mau = vstUserActiveMau;
	}

	public String getVst_user_active_mau_dod() {
		return vst_user_active_mau_dod;
	}

	public void setVst_user_active_mau_dod(String vstUserActiveMauDod) {
		vst_user_active_mau_dod = vstUserActiveMauDod;
	}

	public String getVst_user_active_mau_wow() {
		return vst_user_active_mau_wow;
	}

	public void setVst_user_active_mau_wow(String vstUserActiveMauWow) {
		vst_user_active_mau_wow = vstUserActiveMauWow;
	}

	public Integer getVst_user_active_sau() {
		return vst_user_active_sau;
	}

	public void setVst_user_active_sau(Integer vstUserActiveSau) {
		vst_user_active_sau = vstUserActiveSau;
	}

	public String getVst_user_active_sau_dod() {
		return vst_user_active_sau_dod;
	}

	public void setVst_user_active_sau_dod(String vstUserActiveSauDod) {
		vst_user_active_sau_dod = vstUserActiveSauDod;
	}

	public String getVst_user_active_sau_wow() {
		return vst_user_active_sau_wow;
	}

	public void setVst_user_active_sau_wow(String vstUserActiveSauWow) {
		vst_user_active_sau_wow = vstUserActiveSauWow;
	}

	public Long getVst_user_active_uptime() {
		return vst_user_active_uptime;
	}

	public void setVst_user_active_uptime(Long vstUserActiveUptime) {
		vst_user_active_uptime = vstUserActiveUptime;
	}

	public String getVst_user_active_updator() {
		return vst_user_active_updator;
	}

	public void setVst_user_active_updator(String vstUserActiveUpdator) {
		vst_user_active_updator = vstUserActiveUpdator;
	}

	@Override
	public String toString() {
		return "VstUserActive [vst_pk_id=" + vst_pk_id
				+ ", vst_user_active_date=" + vst_user_active_date
				+ ", vst_user_active_dau=" + vst_user_active_dau
				+ ", vst_user_active_dau_dod=" + vst_user_active_dau_dod
				+ ", vst_user_active_dau_wow=" + vst_user_active_dau_wow
				+ ", vst_user_active_dim_name1=" + vst_user_active_dim_name1
				+ ", vst_user_active_dim_name2=" + vst_user_active_dim_name2
				+ ", vst_user_active_dim_value1=" + vst_user_active_dim_value1
				+ ", vst_user_active_dim_value2=" + vst_user_active_dim_value2
				+ ", vst_user_active_mau=" + vst_user_active_mau
				+ ", vst_user_active_mau_dod=" + vst_user_active_mau_dod
				+ ", vst_user_active_mau_wow=" + vst_user_active_mau_wow
				+ ", vst_user_active_pkg=" + vst_user_active_pkg
				+ ", vst_user_active_sau=" + vst_user_active_sau
				+ ", vst_user_active_sau_dod=" + vst_user_active_sau_dod
				+ ", vst_user_active_sau_wow=" + vst_user_active_sau_wow
				+ ", vst_user_active_updator=" + vst_user_active_updator
				+ ", vst_user_active_uptime=" + vst_user_active_uptime
				+ ", vst_user_active_wau=" + vst_user_active_wau
				+ ", vst_user_active_wau_dod=" + vst_user_active_wau_dod
				+ ", vst_user_active_wau_wow=" + vst_user_active_wau_wow + "]";
	}
}
