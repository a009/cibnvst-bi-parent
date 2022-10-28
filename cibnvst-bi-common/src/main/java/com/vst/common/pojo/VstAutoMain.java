package com.vst.common.pojo;

/**
 * 自动化报表-主表
 * @author lhp
 * @date 2017-9-12 下午03:12:22
 * @version
 */
public class VstAutoMain {
	/**
	 * 主键，自增长
	 */
	private Integer vst_main_pk;
	
	/**
	 * 主键，6位随机字符串
	 */
	private String vst_main_id;
	
	/**
	 * 代码编号
	 */
	private String vst_code;
	
	/**
	 * 头部
	 */
	private String vst_main_script;
	
	/**
	 * 是否分页，0-否，1-是
	 */
	private Integer vst_main_isPaging;
	
	/**
	 * 查询记录数
	 */
	private String vst_main_countScript;
	
	/**
	 * 排序
	 */
	private Integer vst_main_index;
	
	/**
	 * 状态：1-正常，2-禁用
	 */
	private Integer vst_main_state;
	
	/**
	 * 新增时间，毫秒数
	 */
	private Long vst_main_addtime;
	
	/**
	 * 创建人
	 */
	private String vst_main_creator;
	
	/**
	 * 修改时间，毫秒数
	 */
	private Long vst_main_uptime;
	
	/**
	 * 修改人
	 */
	private String vst_main_updator;
	
	/**
	 * 备注
	 */
	private String vst_main_summary;

	public Integer getVst_main_pk() {
		return vst_main_pk;
	}

	public void setVst_main_pk(Integer vstMainPk) {
		vst_main_pk = vstMainPk;
	}

	public String getVst_main_id() {
		return vst_main_id;
	}

	public void setVst_main_id(String vstMainId) {
		vst_main_id = vstMainId;
	}

	public String getVst_code() {
		return vst_code;
	}

	public void setVst_code(String vstCode) {
		vst_code = vstCode;
	}

	public String getVst_main_script() {
		return vst_main_script;
	}

	public void setVst_main_script(String vstMainScript) {
		vst_main_script = vstMainScript;
	}

	public Integer getVst_main_isPaging() {
		return vst_main_isPaging;
	}

	public void setVst_main_isPaging(Integer vstMainIsPaging) {
		vst_main_isPaging = vstMainIsPaging;
	}

	public String getVst_main_countScript() {
		return vst_main_countScript;
	}

	public void setVst_main_countScript(String vstMainCountScript) {
		vst_main_countScript = vstMainCountScript;
	}

	public Integer getVst_main_index() {
		return vst_main_index;
	}

	public void setVst_main_index(Integer vstMainIndex) {
		vst_main_index = vstMainIndex;
	}

	public Integer getVst_main_state() {
		return vst_main_state;
	}

	public void setVst_main_state(Integer vstMainState) {
		vst_main_state = vstMainState;
	}

	public Long getVst_main_addtime() {
		return vst_main_addtime;
	}

	public void setVst_main_addtime(Long vstMainAddtime) {
		vst_main_addtime = vstMainAddtime;
	}

	public String getVst_main_creator() {
		return vst_main_creator;
	}

	public void setVst_main_creator(String vstMainCreator) {
		vst_main_creator = vstMainCreator;
	}

	public Long getVst_main_uptime() {
		return vst_main_uptime;
	}

	public void setVst_main_uptime(Long vstMainUptime) {
		vst_main_uptime = vstMainUptime;
	}

	public String getVst_main_updator() {
		return vst_main_updator;
	}

	public void setVst_main_updator(String vstMainUpdator) {
		vst_main_updator = vstMainUpdator;
	}

	public String getVst_main_summary() {
		return vst_main_summary;
	}

	public void setVst_main_summary(String vstMainSummary) {
		vst_main_summary = vstMainSummary;
	}
}
