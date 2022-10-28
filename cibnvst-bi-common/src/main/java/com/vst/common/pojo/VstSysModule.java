package com.vst.common.pojo;

/**
 * @author weiwei
 * @date 2014-10-23 上午11:41:21
 * @description
 * @version
 */
public class VstSysModule {

	/**
	 * 主键id
	 */
	private Integer vst_pk_id;

	/**
	 * 模块id
	 */
	private String vst_module_id;

	/**
	 * 模块名称
	 */
	private String vst_module_name;

	/**
	 * 父模块id
	 */
	private String vst_module_parent;

	/**
	 * 模块链接
	 */
	private String vst_module_url;
	
	/**
	 * 模块图标
	 */
	private String vst_module_icon;

	/**
	 * 新增时间
	 */
	private Long vst_module_addtime;

	/**
	 * 修改时间
	 */
	private Long vst_module_uptime;

	/**
	 * 操作人
	 */
	private String vst_module_operator;

	/**
	 * 状态
	 */
	private Integer vst_module_state;

	/**
	 * 排序
	 */
	private Integer vst_module_index;

	/**
	 * 备注
	 */
	private String vst_module_summary;

	public Integer getVst_pk_id() {
		return vst_pk_id;
	}

	public void setVst_pk_id(Integer vstPkId) {
		vst_pk_id = vstPkId;
	}

	public String getVst_module_id() {
		return vst_module_id;
	}

	public void setVst_module_id(String vstModuleId) {
		vst_module_id = vstModuleId;
	}

	public String getVst_module_name() {
		return vst_module_name;
	}

	public void setVst_module_name(String vstModuleName) {
		vst_module_name = vstModuleName;
	}

	public String getVst_module_parent() {
		return vst_module_parent;
	}

	public void setVst_module_parent(String vstModuleParent) {
		vst_module_parent = vstModuleParent;
	}

	public String getVst_module_url() {
		return vst_module_url;
	}

	public void setVst_module_url(String vstModuleUrl) {
		vst_module_url = vstModuleUrl;
	}

	public String getVst_module_icon() {
		return vst_module_icon;
	}

	public void setVst_module_icon(String vstModuleIcon) {
		vst_module_icon = vstModuleIcon;
	}

	public Long getVst_module_addtime() {
		return vst_module_addtime;
	}

	public void setVst_module_addtime(Long vstModuleAddtime) {
		vst_module_addtime = vstModuleAddtime;
	}

	public Long getVst_module_uptime() {
		return vst_module_uptime;
	}

	public void setVst_module_uptime(Long vstModuleUptime) {
		vst_module_uptime = vstModuleUptime;
	}

	public String getVst_module_operator() {
		return vst_module_operator;
	}

	public void setVst_module_operator(String vstModuleOperator) {
		vst_module_operator = vstModuleOperator;
	}

	public Integer getVst_module_state() {
		return vst_module_state;
	}

	public void setVst_module_state(Integer vstModuleState) {
		vst_module_state = vstModuleState;
	}

	public Integer getVst_module_index() {
		return vst_module_index;
	}

	public void setVst_module_index(Integer vstModuleIndex) {
		vst_module_index = vstModuleIndex;
	}

	public String getVst_module_summary() {
		return vst_module_summary;
	}

	public void setVst_module_summary(String vstModuleSummary) {
		vst_module_summary = vstModuleSummary;
	}

	@Override
	public String toString() {
		return "VstSysModule [vst_module_addtime=" + vst_module_addtime
				+ ", vst_module_icon=" + vst_module_icon + ", vst_module_id="
				+ vst_module_id + ", vst_module_index=" + vst_module_index
				+ ", vst_module_name=" + vst_module_name
				+ ", vst_module_operator=" + vst_module_operator
				+ ", vst_module_parent=" + vst_module_parent
				+ ", vst_module_state=" + vst_module_state
				+ ", vst_module_summary=" + vst_module_summary
				+ ", vst_module_uptime=" + vst_module_uptime
				+ ", vst_module_url=" + vst_module_url + ", vst_pk_id="
				+ vst_pk_id + "]";
	}

}
