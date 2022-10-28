package com.vst.common.pojo;

/**
 * 自动化报表-sql续加
 * @author lhp
 * @date 2017-9-12 下午03:12:22
 * @version
 */
public class VstAutoOverlay {
	/**
	 * 主键，自增长
	 */
	private Integer vst_overlay_pk;
	
	/**
	 * 主键，8位随机字符串
	 */
	private String vst_overlay_id;
	
	/**
	 * 代码编号
	 */
	private String vst_code;
	
	/**
	 * 续加sql
	 */
	private String vst_overlay_script;
	
	/**
	 * 排序
	 */
	private Integer vst_overlay_index;
	
	/**
	 * 状态：1-正常，2-禁用
	 */
	private Integer vst_overlay_state;
	
	/**
	 * 新增时间，毫秒数
	 */
	private Long vst_overlay_addtime;
	
	/**
	 * 创建人
	 */
	private String vst_overlay_creator;
	
	/**
	 * 修改时间，毫秒数
	 */
	private Long vst_overlay_uptime;
	
	/**
	 * 修改人
	 */
	private String vst_overlay_updator;
	
	/**
	 * 备注
	 */
	private String vst_overlay_summary;

	public Integer getVst_overlay_pk() {
		return vst_overlay_pk;
	}

	public void setVst_overlay_pk(Integer vstOverlayPk) {
		vst_overlay_pk = vstOverlayPk;
	}

	public String getVst_overlay_id() {
		return vst_overlay_id;
	}

	public void setVst_overlay_id(String vstOverlayId) {
		vst_overlay_id = vstOverlayId;
	}

	public String getVst_code() {
		return vst_code;
	}

	public void setVst_code(String vstCode) {
		vst_code = vstCode;
	}

	public String getVst_overlay_script() {
		return vst_overlay_script;
	}

	public void setVst_overlay_script(String vstOverlayScript) {
		vst_overlay_script = vstOverlayScript;
	}

	public Integer getVst_overlay_index() {
		return vst_overlay_index;
	}

	public void setVst_overlay_index(Integer vstOverlayIndex) {
		vst_overlay_index = vstOverlayIndex;
	}

	public Integer getVst_overlay_state() {
		return vst_overlay_state;
	}

	public void setVst_overlay_state(Integer vstOverlayState) {
		vst_overlay_state = vstOverlayState;
	}

	public Long getVst_overlay_addtime() {
		return vst_overlay_addtime;
	}

	public void setVst_overlay_addtime(Long vstOverlayAddtime) {
		vst_overlay_addtime = vstOverlayAddtime;
	}

	public String getVst_overlay_creator() {
		return vst_overlay_creator;
	}

	public void setVst_overlay_creator(String vstOverlayCreator) {
		vst_overlay_creator = vstOverlayCreator;
	}

	public Long getVst_overlay_uptime() {
		return vst_overlay_uptime;
	}

	public void setVst_overlay_uptime(Long vstOverlayUptime) {
		vst_overlay_uptime = vstOverlayUptime;
	}

	public String getVst_overlay_updator() {
		return vst_overlay_updator;
	}

	public void setVst_overlay_updator(String vstOverlayUpdator) {
		vst_overlay_updator = vstOverlayUpdator;
	}

	public String getVst_overlay_summary() {
		return vst_overlay_summary;
	}

	public void setVst_overlay_summary(String vstOverlaySummary) {
		vst_overlay_summary = vstOverlaySummary;
	}

	@Override
	public String toString() {
		return "VstAutoOverlay [vst_code=" + vst_code
				+ ", vst_overlay_addtime=" + vst_overlay_addtime
				+ ", vst_overlay_creator=" + vst_overlay_creator
				+ ", vst_overlay_id=" + vst_overlay_id + ", vst_overlay_index="
				+ vst_overlay_index + ", vst_overlay_pk=" + vst_overlay_pk
				+ ", vst_overlay_script=" + vst_overlay_script
				+ ", vst_overlay_state=" + vst_overlay_state
				+ ", vst_overlay_summary=" + vst_overlay_summary
				+ ", vst_overlay_updator=" + vst_overlay_updator
				+ ", vst_overlay_uptime=" + vst_overlay_uptime + "]";
	}
}
