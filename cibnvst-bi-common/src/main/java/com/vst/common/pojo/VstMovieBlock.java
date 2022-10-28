package com.vst.common.pojo;

/**
 * 影片区块播放量统计
 * @author lhp
 * @date 2018-10-18 下午06:02:11
 * @version
 */
public class VstMovieBlock {
	/**
	 * 主键，10位随机字符串
	 */
	private String vst_mb_id;
	
	/**
	 * 统计日期
	 */
	private Integer vst_mb_date;
	
	/**
	 * 包名（客户端名）
	 */
	private String vst_mb_pkg;
	
	/**
	 * 统计来源（2精选）
	 */
	private Integer vst_mb_type;
	
	/**
	 * 区块ID
	 */
	private String vst_mb_block;
	
	/**
	 * 区块位置编号，从0开始
	 */
	private Integer vst_mb_index;
	
	/**
	 * 影片播放量
	 */
	private Long vst_mb_vv;
	
	/**
	 * 影片播放量天环比
	 */
	private String vst_mb_vv_dod;
	
	/**
	 * 影片播放量周环比
	 */
	private String vst_mb_vv_wow;
	
	/**
	 * 影片播放用户数
	 */
	private Integer vst_mb_uv;
	
	/**
	 * 影片播放用户数天环比
	 */
	private String vst_mb_uv_dod;
	
	/**
	 * 影片播放用户数周环比
	 */
	private String vst_mb_uv_wow;
	
	/**
	 * 人均播放量
	 */
	private String vst_mb_avg;
	
	/**
	 * 播放时长，单位秒
	 */
	private Long vst_mb_playtime;
	
	/**
	 * 人均播放时长，单位秒
	 */
	private Long vst_mb_playtime_avg;
	
	/**
	 * 新增时间，13位毫秒数
	 */
	private Long vst_mb_addtime;
	
	/**
	 * 创建人
	 */
	private String vst_mb_creator;
	
	/**
	 * 修改时间，13位毫秒数
	 */
	private Long vst_mb_uptime;
	
	/**
	 * 修改人
	 */
	private String vst_mb_updator;
	
	/**
	 * 备注
	 */
	private String vst_mb_summary;

	public String getVst_mb_id() {
		return vst_mb_id;
	}

	public void setVst_mb_id(String vstMbId) {
		vst_mb_id = vstMbId;
	}

	public Integer getVst_mb_date() {
		return vst_mb_date;
	}

	public void setVst_mb_date(Integer vstMbDate) {
		vst_mb_date = vstMbDate;
	}

	public String getVst_mb_pkg() {
		return vst_mb_pkg;
	}

	public void setVst_mb_pkg(String vstMbPkg) {
		vst_mb_pkg = vstMbPkg;
	}

	public Integer getVst_mb_type() {
		return vst_mb_type;
	}

	public void setVst_mb_type(Integer vstMbType) {
		vst_mb_type = vstMbType;
	}

	public String getVst_mb_block() {
		return vst_mb_block;
	}

	public void setVst_mb_block(String vstMbBlock) {
		vst_mb_block = vstMbBlock;
	}

	public Integer getVst_mb_index() {
		return vst_mb_index;
	}

	public void setVst_mb_index(Integer vstMbIndex) {
		vst_mb_index = vstMbIndex;
	}

	public Long getVst_mb_vv() {
		return vst_mb_vv;
	}

	public void setVst_mb_vv(Long vstMbVv) {
		vst_mb_vv = vstMbVv;
	}

	public String getVst_mb_vv_dod() {
		return vst_mb_vv_dod;
	}

	public void setVst_mb_vv_dod(String vstMbVvDod) {
		vst_mb_vv_dod = vstMbVvDod;
	}

	public String getVst_mb_vv_wow() {
		return vst_mb_vv_wow;
	}

	public void setVst_mb_vv_wow(String vstMbVvWow) {
		vst_mb_vv_wow = vstMbVvWow;
	}

	public Integer getVst_mb_uv() {
		return vst_mb_uv;
	}

	public void setVst_mb_uv(Integer vstMbUv) {
		vst_mb_uv = vstMbUv;
	}

	public String getVst_mb_uv_dod() {
		return vst_mb_uv_dod;
	}

	public void setVst_mb_uv_dod(String vstMbUvDod) {
		vst_mb_uv_dod = vstMbUvDod;
	}

	public String getVst_mb_uv_wow() {
		return vst_mb_uv_wow;
	}

	public void setVst_mb_uv_wow(String vstMbUvWow) {
		vst_mb_uv_wow = vstMbUvWow;
	}

	public String getVst_mb_avg() {
		return vst_mb_avg;
	}

	public void setVst_mb_avg(String vstMbAvg) {
		vst_mb_avg = vstMbAvg;
	}

	public Long getVst_mb_playtime() {
		return vst_mb_playtime;
	}

	public void setVst_mb_playtime(Long vstMbPlaytime) {
		vst_mb_playtime = vstMbPlaytime;
	}

	public Long getVst_mb_playtime_avg() {
		return vst_mb_playtime_avg;
	}

	public void setVst_mb_playtime_avg(Long vstMbPlaytimeAvg) {
		vst_mb_playtime_avg = vstMbPlaytimeAvg;
	}

	public Long getVst_mb_addtime() {
		return vst_mb_addtime;
	}

	public void setVst_mb_addtime(Long vstMbAddtime) {
		vst_mb_addtime = vstMbAddtime;
	}

	public String getVst_mb_creator() {
		return vst_mb_creator;
	}

	public void setVst_mb_creator(String vstMbCreator) {
		vst_mb_creator = vstMbCreator;
	}

	public Long getVst_mb_uptime() {
		return vst_mb_uptime;
	}

	public void setVst_mb_uptime(Long vstMbUptime) {
		vst_mb_uptime = vstMbUptime;
	}

	public String getVst_mb_updator() {
		return vst_mb_updator;
	}

	public void setVst_mb_updator(String vstMbUpdator) {
		vst_mb_updator = vstMbUpdator;
	}

	public String getVst_mb_summary() {
		return vst_mb_summary;
	}

	public void setVst_mb_summary(String vstMbSummary) {
		vst_mb_summary = vstMbSummary;
	}

	@Override
	public String toString() {
		return "VstMovieBlock [vst_mb_addtime=" + vst_mb_addtime
				+ ", vst_mb_avg=" + vst_mb_avg + ", vst_mb_block="
				+ vst_mb_block + ", vst_mb_creator=" + vst_mb_creator
				+ ", vst_mb_date=" + vst_mb_date + ", vst_mb_id=" + vst_mb_id
				+ ", vst_mb_index=" + vst_mb_index + ", vst_mb_pkg="
				+ vst_mb_pkg + ", vst_mb_playtime=" + vst_mb_playtime
				+ ", vst_mb_playtime_avg=" + vst_mb_playtime_avg
				+ ", vst_mb_summary=" + vst_mb_summary + ", vst_mb_type="
				+ vst_mb_type + ", vst_mb_updator=" + vst_mb_updator
				+ ", vst_mb_uptime=" + vst_mb_uptime + ", vst_mb_uv="
				+ vst_mb_uv + ", vst_mb_uv_dod=" + vst_mb_uv_dod
				+ ", vst_mb_uv_wow=" + vst_mb_uv_wow + ", vst_mb_vv="
				+ vst_mb_vv + ", vst_mb_vv_dod=" + vst_mb_vv_dod
				+ ", vst_mb_vv_wow=" + vst_mb_vv_wow + "]";
	}
}
