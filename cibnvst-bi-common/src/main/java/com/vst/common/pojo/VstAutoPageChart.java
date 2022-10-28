package com.vst.common.pojo;

/**
 * 自动化页面-统计图
 * @author lhp
 * @date 2017-9-25 下午05:55:52
 * @version
 */
public class VstAutoPageChart {
	/**
	 * 主键，自增长
	 */
	private Integer vst_chart_pk;
	
	/**
	 * 主键，8位随机字符串
	 */
	private String vst_chart_id;
	
	/**
	 * 代码编号
	 */
	private String vst_code;
	
	/**
	 * 图表类型：line、bar、pie、map
	 */
	private String vst_chart_type;
	
	/**
	 * hichart
	 */
	private String vst_chart_json;
	
	/**
	 * 数据接口
	 */
	private String vst_chart_api;
	
	/**
	 * 排序
	 */
	private Integer vst_chart_index;
	
	/**
	 * 状态：1-正常，2-禁用
	 */
	private Integer vst_chart_state;
	
	/**
	 * 新增时间，毫秒数
	 */
	private Long vst_chart_addtime;
	
	/**
	 * 创建人
	 */
	private String vst_chart_creator;
	
	/**
	 * 修改时间，毫秒数
	 */
	private Long vst_chart_uptime;
	
	/**
	 * 修改人
	 */
	private String vst_chart_updator;
	
	/**
	 * 备注
	 */
	private String vst_chart_summary;

	public Integer getVst_chart_pk() {
		return vst_chart_pk;
	}

	public void setVst_chart_pk(Integer vstChartPk) {
		vst_chart_pk = vstChartPk;
	}

	public String getVst_chart_id() {
		return vst_chart_id;
	}

	public void setVst_chart_id(String vstChartId) {
		vst_chart_id = vstChartId;
	}

	public String getVst_code() {
		return vst_code;
	}

	public void setVst_code(String vstCode) {
		vst_code = vstCode;
	}

	public String getVst_chart_type() {
		return vst_chart_type;
	}

	public void setVst_chart_type(String vstChartType) {
		vst_chart_type = vstChartType;
	}

	public String getVst_chart_json() {
		return vst_chart_json;
	}

	public void setVst_chart_json(String vstChartJson) {
		vst_chart_json = vstChartJson;
	}

	public String getVst_chart_api() {
		return vst_chart_api;
	}

	public void setVst_chart_api(String vstChartApi) {
		vst_chart_api = vstChartApi;
	}

	public Integer getVst_chart_index() {
		return vst_chart_index;
	}

	public void setVst_chart_index(Integer vstChartIndex) {
		vst_chart_index = vstChartIndex;
	}

	public Integer getVst_chart_state() {
		return vst_chart_state;
	}

	public void setVst_chart_state(Integer vstChartState) {
		vst_chart_state = vstChartState;
	}

	public Long getVst_chart_addtime() {
		return vst_chart_addtime;
	}

	public void setVst_chart_addtime(Long vstChartAddtime) {
		vst_chart_addtime = vstChartAddtime;
	}

	public String getVst_chart_creator() {
		return vst_chart_creator;
	}

	public void setVst_chart_creator(String vstChartCreator) {
		vst_chart_creator = vstChartCreator;
	}

	public Long getVst_chart_uptime() {
		return vst_chart_uptime;
	}

	public void setVst_chart_uptime(Long vstChartUptime) {
		vst_chart_uptime = vstChartUptime;
	}

	public String getVst_chart_updator() {
		return vst_chart_updator;
	}

	public void setVst_chart_updator(String vstChartUpdator) {
		vst_chart_updator = vstChartUpdator;
	}

	public String getVst_chart_summary() {
		return vst_chart_summary;
	}

	public void setVst_chart_summary(String vstChartSummary) {
		vst_chart_summary = vstChartSummary;
	}

	@Override
	public String toString() {
		return "VstAutoPageChart [vst_chart_addtime=" + vst_chart_addtime
				+ ", vst_chart_api=" + vst_chart_api + ", vst_chart_creator="
				+ vst_chart_creator + ", vst_chart_id=" + vst_chart_id
				+ ", vst_chart_index=" + vst_chart_index + ", vst_chart_json="
				+ vst_chart_json + ", vst_chart_pk=" + vst_chart_pk
				+ ", vst_chart_state=" + vst_chart_state
				+ ", vst_chart_summary=" + vst_chart_summary
				+ ", vst_chart_type=" + vst_chart_type + ", vst_chart_updator="
				+ vst_chart_updator + ", vst_chart_uptime=" + vst_chart_uptime
				+ ", vst_code=" + vst_code + "]";
	}
}
