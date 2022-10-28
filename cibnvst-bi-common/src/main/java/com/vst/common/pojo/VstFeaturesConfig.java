package com.vst.common.pojo;

/**
 * 用户指标特征配置表
 * @author lhp
 * @date 2018-12-13 下午05:18:31
 * @version
 */
public class VstFeaturesConfig {
	/**
	 * 主键id，随机5位数，唯一
	 */
	private String vst_features_id;
	
	/**
	 * 所属包名 net.myvst.v2、com.vst.itv52.v1
	 */
	private String vst_features_pkg;
	
	/**
	 * 所属专区 0:非专区 1专区(聚精彩) 2专区(CIBN) 3专区(4K花园) 4(全球影视) 5专区(搜狐) 6专区(爱优教育)
	 */
	private Integer vst_features_special_type;
	
	/**
	 * 所属分类 1电影 2电视剧 3动漫 4综艺 5纪录片 8少儿
	 */
	private Integer vst_features_cid;
	
	/**
	 * 特征类型  1影片类型(动作,惊悚) 2上映时间（年份） 3演员（名称）4地区（内地,欧美）
	 */
	private Integer vst_features_type;
	
	/**
	 * 特征名称  -2:全部,-1:其它,也可写全名
	 */
	private String vst_features_name;
	
	/**
	 * 特征值，配置默认值，4位精度
	 */
	private Double vst_features_value;
	
	/**
	 * 排序
	 */
	private Integer vst_features_index;
	
	/**
	 * 状态，1：正常，2：禁用
	 */
	private Integer vst_features_state;
	
	/**
	 * 新增时间，13位毫秒数
	 */
	private Long vst_features_addtime;
	
	/**
	 * 创建人
	 */
	private String vst_features_creator;
	
	/**
	 * 修改时间
	 */
	private Long vst_features_uptime;
	
	/**
	 * 修改人
	 */
	private String vst_features_updator;
	
	/**
	 * 备注
	 */
	private String vst_features_summary;

	public String getVst_features_id() {
		return vst_features_id;
	}

	public void setVst_features_id(String vstFeaturesId) {
		vst_features_id = vstFeaturesId;
	}

	public String getVst_features_pkg() {
		return vst_features_pkg;
	}

	public void setVst_features_pkg(String vstFeaturesPkg) {
		vst_features_pkg = vstFeaturesPkg;
	}

	public Integer getVst_features_special_type() {
		return vst_features_special_type;
	}

	public void setVst_features_special_type(Integer vstFeaturesSpecialType) {
		vst_features_special_type = vstFeaturesSpecialType;
	}

	public Integer getVst_features_cid() {
		return vst_features_cid;
	}

	public void setVst_features_cid(Integer vstFeaturesCid) {
		vst_features_cid = vstFeaturesCid;
	}

	public Integer getVst_features_type() {
		return vst_features_type;
	}

	public void setVst_features_type(Integer vstFeaturesType) {
		vst_features_type = vstFeaturesType;
	}

	public String getVst_features_name() {
		return vst_features_name;
	}

	public void setVst_features_name(String vstFeaturesName) {
		vst_features_name = vstFeaturesName;
	}

	public Double getVst_features_value() {
		return vst_features_value;
	}

	public void setVst_features_value(Double vstFeaturesValue) {
		vst_features_value = vstFeaturesValue;
	}

	public Integer getVst_features_index() {
		return vst_features_index;
	}

	public void setVst_features_index(Integer vstFeaturesIndex) {
		vst_features_index = vstFeaturesIndex;
	}

	public Integer getVst_features_state() {
		return vst_features_state;
	}

	public void setVst_features_state(Integer vstFeaturesState) {
		vst_features_state = vstFeaturesState;
	}

	public Long getVst_features_addtime() {
		return vst_features_addtime;
	}

	public void setVst_features_addtime(Long vstFeaturesAddtime) {
		vst_features_addtime = vstFeaturesAddtime;
	}

	public String getVst_features_creator() {
		return vst_features_creator;
	}

	public void setVst_features_creator(String vstFeaturesCreator) {
		vst_features_creator = vstFeaturesCreator;
	}

	public Long getVst_features_uptime() {
		return vst_features_uptime;
	}

	public void setVst_features_uptime(Long vstFeaturesUptime) {
		vst_features_uptime = vstFeaturesUptime;
	}

	public String getVst_features_updator() {
		return vst_features_updator;
	}

	public void setVst_features_updator(String vstFeaturesUpdator) {
		vst_features_updator = vstFeaturesUpdator;
	}

	public String getVst_features_summary() {
		return vst_features_summary;
	}

	public void setVst_features_summary(String vstFeaturesSummary) {
		vst_features_summary = vstFeaturesSummary;
	}

	@Override
	public String toString() {
		return "VstFeaturesConfig [vst_features_addtime="
				+ vst_features_addtime + ", vst_features_cid="
				+ vst_features_cid + ", vst_features_creator="
				+ vst_features_creator + ", vst_features_id=" + vst_features_id
				+ ", vst_features_index=" + vst_features_index
				+ ", vst_features_name=" + vst_features_name
				+ ", vst_features_pkg=" + vst_features_pkg
				+ ", vst_features_special_type=" + vst_features_special_type
				+ ", vst_features_state=" + vst_features_state
				+ ", vst_features_summary=" + vst_features_summary
				+ ", vst_features_type=" + vst_features_type
				+ ", vst_features_updator=" + vst_features_updator
				+ ", vst_features_uptime=" + vst_features_uptime
				+ ", vst_features_value=" + vst_features_value + "]";
	}
}
