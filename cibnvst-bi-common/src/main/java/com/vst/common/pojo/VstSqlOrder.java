package com.vst.common.pojo;

/**
 * sql排序配置
 * @author lhp
 * @date 2017-11-20 上午11:58:23
 * @version
 */
public class VstSqlOrder {
	/**
	 * 主键id，随机6位数，唯一
	 */
	private String vst_order_id;
	
	/**
	 * sql配置id
	 */
	private String vst_sql_id;
	
	/**
	 * 排序字段名称
	 */
	private String vst_order_name;
	
	/**
	 * 排序顺序，asc升序，desc降序
	 */
	private String vst_order_sort;
	
	/**
	 * 排序值，越大越靠前，默认0
	 */
	private Integer vst_order_index;
	
	/**
	 * 状态，1：正常，2：禁用
	 */
	private Integer vst_order_state;
	
	/**
	 * 新增时间，13位毫秒数
	 */
	private Long vst_order_addtime;
	
	/**
	 * 创建人
	 */
	private String vst_order_creator;
	
	/**
	 * 修改时间，13位毫秒数
	 */
	private Long vst_order_uptime;
	
	/**
	 * 修改人
	 */
	private String vst_order_updator;
	
	/**
	 * 备注
	 */
	private String vst_order_summary;

	public String getVst_order_id() {
		return vst_order_id;
	}

	public void setVst_order_id(String vstOrderId) {
		vst_order_id = vstOrderId;
	}

	public String getVst_sql_id() {
		return vst_sql_id;
	}

	public void setVst_sql_id(String vstSqlId) {
		vst_sql_id = vstSqlId;
	}

	public String getVst_order_name() {
		return vst_order_name;
	}

	public void setVst_order_name(String vstOrderName) {
		vst_order_name = vstOrderName;
	}

	public String getVst_order_sort() {
		return vst_order_sort;
	}

	public void setVst_order_sort(String vstOrderSort) {
		vst_order_sort = vstOrderSort;
	}

	public Integer getVst_order_index() {
		return vst_order_index;
	}

	public void setVst_order_index(Integer vstOrderIndex) {
		vst_order_index = vstOrderIndex;
	}

	public Integer getVst_order_state() {
		return vst_order_state;
	}

	public void setVst_order_state(Integer vstOrderState) {
		vst_order_state = vstOrderState;
	}

	public Long getVst_order_addtime() {
		return vst_order_addtime;
	}

	public void setVst_order_addtime(Long vstOrderAddtime) {
		vst_order_addtime = vstOrderAddtime;
	}

	public String getVst_order_creator() {
		return vst_order_creator;
	}

	public void setVst_order_creator(String vstOrderCreator) {
		vst_order_creator = vstOrderCreator;
	}

	public Long getVst_order_uptime() {
		return vst_order_uptime;
	}

	public void setVst_order_uptime(Long vstOrderUptime) {
		vst_order_uptime = vstOrderUptime;
	}

	public String getVst_order_updator() {
		return vst_order_updator;
	}

	public void setVst_order_updator(String vstOrderUpdator) {
		vst_order_updator = vstOrderUpdator;
	}

	public String getVst_order_summary() {
		return vst_order_summary;
	}

	public void setVst_order_summary(String vstOrderSummary) {
		vst_order_summary = vstOrderSummary;
	}

	@Override
	public String toString() {
		return "VstSqlOrder [vst_order_addtime=" + vst_order_addtime
				+ ", vst_order_creator=" + vst_order_creator
				+ ", vst_order_id=" + vst_order_id + ", vst_order_index="
				+ vst_order_index + ", vst_order_name=" + vst_order_name
				+ ", vst_order_sort=" + vst_order_sort + ", vst_order_state="
				+ vst_order_state + ", vst_order_summary=" + vst_order_summary
				+ ", vst_order_updator=" + vst_order_updator
				+ ", vst_order_uptime=" + vst_order_uptime + ", vst_sql_id="
				+ vst_sql_id + "]";
	}
}
