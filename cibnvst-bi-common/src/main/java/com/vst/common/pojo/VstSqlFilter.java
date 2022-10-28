package com.vst.common.pojo;

/**
 * sql筛选配置
 * @author lhp
 * @date 2017-11-30 下午05:16:55
 * @version
 */
public class VstSqlFilter {
	/**
	 * 主键id，随机6位数，唯一
	 */
	private String vst_filter_id;
	
	/**
	 * sql配置id
	 */
	private String vst_sql_id;
	
	/**
	 * 筛选key
	 */
	private String vst_filter_key;
	
	/**
	 * 筛选value值
	 */
	private String vst_filter_value;
	
	/**
	 * 适用包名，默认为0，表示全部。若有多个，用英文逗号隔开
	 */
	private String vst_filter_pkg;
	
	/**
	 * 屏蔽包名，默认0，表示不屏蔽。若有多个，用英文逗号隔开
	 */
	private String vst_filter_pkg_block;
	
	/**
	 * 匹配类型，1：匹配（适用于字符串），2：等于，3：不等于，4：大于等于，5：大于，6：小于等于，7：小于
	 */
	private Integer vst_filter_match_type;
	
	/**
	 * 筛选匹配上后处理类型，1：通过，2：不通过
	 */
	private Integer vst_filter_yes_type;
	
	/**
	 * 筛选未匹配上后处理类型，1：通过，2：不通过
	 */
	private Integer vst_filter_no_type;
	
	/**
	 * 操作类型，0：没有任何动作，1：在开头拼接字符串，2：在结尾拼接字符串，3：移除字符串，4：加，5：减，6：乘，7：除，8：取模，9：取模算整，10：播放时长合理矫正
	 */
	private Integer vst_filter_action_type;
	
	/**
	 * 动作key
	 */
	private String vst_filter_action_key;
	
	/**
	 * 动作value值
	 */
	private String vst_filter_action_value;
	
	/**
	 * 排序值，越大越靠前，默认0
	 */
	private Integer vst_filter_index;
	
	/**
	 * 状态，1：正常，2：禁用
	 */
	private Integer vst_filter_state;
	
	/**
	 * 新增时间，13位毫秒数
	 */
	private Long vst_filter_addtime;
	
	/**
	 * 创建人
	 */
	private String vst_filter_creator;
	
	/**
	 * 修改时间，13位毫秒数
	 */
	private Long vst_filter_uptime;
	
	/**
	 * 修改人
	 */
	private String vst_filter_updator;
	
	/**
	 * 备注
	 */
	private String vst_filter_summary;

	public String getVst_filter_id() {
		return vst_filter_id;
	}

	public void setVst_filter_id(String vstFilterId) {
		vst_filter_id = vstFilterId;
	}

	public String getVst_sql_id() {
		return vst_sql_id;
	}

	public void setVst_sql_id(String vstSqlId) {
		vst_sql_id = vstSqlId;
	}

	public String getVst_filter_key() {
		return vst_filter_key;
	}

	public void setVst_filter_key(String vstFilterKey) {
		vst_filter_key = vstFilterKey;
	}

	public String getVst_filter_value() {
		return vst_filter_value;
	}

	public void setVst_filter_value(String vstFilterValue) {
		vst_filter_value = vstFilterValue;
	}

	public String getVst_filter_pkg() {
		return vst_filter_pkg;
	}

	public void setVst_filter_pkg(String vstFilterPkg) {
		vst_filter_pkg = vstFilterPkg;
	}

	public String getVst_filter_pkg_block() {
		return vst_filter_pkg_block;
	}

	public void setVst_filter_pkg_block(String vstFilterPkgBlock) {
		vst_filter_pkg_block = vstFilterPkgBlock;
	}

	public Integer getVst_filter_match_type() {
		return vst_filter_match_type;
	}

	public void setVst_filter_match_type(Integer vstFilterMatchType) {
		vst_filter_match_type = vstFilterMatchType;
	}

	public Integer getVst_filter_yes_type() {
		return vst_filter_yes_type;
	}

	public void setVst_filter_yes_type(Integer vstFilterYesType) {
		vst_filter_yes_type = vstFilterYesType;
	}

	public Integer getVst_filter_no_type() {
		return vst_filter_no_type;
	}

	public void setVst_filter_no_type(Integer vstFilterNoType) {
		vst_filter_no_type = vstFilterNoType;
	}

	public Integer getVst_filter_action_type() {
		return vst_filter_action_type;
	}

	public void setVst_filter_action_type(Integer vstFilterActionType) {
		vst_filter_action_type = vstFilterActionType;
	}

	public String getVst_filter_action_key() {
		return vst_filter_action_key;
	}

	public void setVst_filter_action_key(String vstFilterActionKey) {
		vst_filter_action_key = vstFilterActionKey;
	}

	public String getVst_filter_action_value() {
		return vst_filter_action_value;
	}

	public void setVst_filter_action_value(String vstFilterActionValue) {
		vst_filter_action_value = vstFilterActionValue;
	}

	public Integer getVst_filter_index() {
		return vst_filter_index;
	}

	public void setVst_filter_index(Integer vstFilterIndex) {
		vst_filter_index = vstFilterIndex;
	}

	public Integer getVst_filter_state() {
		return vst_filter_state;
	}

	public void setVst_filter_state(Integer vstFilterState) {
		vst_filter_state = vstFilterState;
	}

	public Long getVst_filter_addtime() {
		return vst_filter_addtime;
	}

	public void setVst_filter_addtime(Long vstFilterAddtime) {
		vst_filter_addtime = vstFilterAddtime;
	}

	public String getVst_filter_creator() {
		return vst_filter_creator;
	}

	public void setVst_filter_creator(String vstFilterCreator) {
		vst_filter_creator = vstFilterCreator;
	}

	public Long getVst_filter_uptime() {
		return vst_filter_uptime;
	}

	public void setVst_filter_uptime(Long vstFilterUptime) {
		vst_filter_uptime = vstFilterUptime;
	}

	public String getVst_filter_updator() {
		return vst_filter_updator;
	}

	public void setVst_filter_updator(String vstFilterUpdator) {
		vst_filter_updator = vstFilterUpdator;
	}

	public String getVst_filter_summary() {
		return vst_filter_summary;
	}

	public void setVst_filter_summary(String vstFilterSummary) {
		vst_filter_summary = vstFilterSummary;
	}

	@Override
	public String toString() {
		return "VstSqlFilter [vst_filter_action_key=" + vst_filter_action_key
				+ ", vst_filter_action_type=" + vst_filter_action_type
				+ ", vst_filter_action_value=" + vst_filter_action_value
				+ ", vst_filter_addtime=" + vst_filter_addtime
				+ ", vst_filter_creator=" + vst_filter_creator
				+ ", vst_filter_id=" + vst_filter_id + ", vst_filter_index="
				+ vst_filter_index + ", vst_filter_key=" + vst_filter_key
				+ ", vst_filter_match_type=" + vst_filter_match_type
				+ ", vst_filter_no_type=" + vst_filter_no_type
				+ ", vst_filter_pkg=" + vst_filter_pkg
				+ ", vst_filter_pkg_block=" + vst_filter_pkg_block
				+ ", vst_filter_state=" + vst_filter_state
				+ ", vst_filter_summary=" + vst_filter_summary
				+ ", vst_filter_updator=" + vst_filter_updator
				+ ", vst_filter_uptime=" + vst_filter_uptime
				+ ", vst_filter_value=" + vst_filter_value
				+ ", vst_filter_yes_type=" + vst_filter_yes_type
				+ ", vst_sql_id=" + vst_sql_id + "]";
	}
}
