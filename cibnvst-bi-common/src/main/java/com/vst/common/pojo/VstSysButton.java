package com.vst.common.pojo;

import java.io.Serializable;

/**
 * @author wei.wei(joslyn)
 * @date 2014-10-26 下午02:30:50
 * @description
 */
public class VstSysButton implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1684611412465884841L;

	/**
	 * 主键id
	 */
	private Integer vst_pk_id;

	/**
	 * 按钮id
	 */
	private String vst_button_id;

	/**
	 * 按钮名称
	 */
	private String vst_button_name;

	/**
	 * 按钮图片
	 */
	private String vst_button_img;

	/**
	 * 按钮事件
	 */
	private String vst_button_onclick;

	/**
	 * 添加事件
	 */
	private Long vst_button_addtime;

	/**
	 * 更新事件
	 */
	private Long vst_button_uptime;

	/**
	 * 操作人
	 */
	private String vst_button_operator;

	/**
	 * 排序
	 */
	private Integer vst_button_index;

	/**
	 * 状态
	 */
	private Integer vst_button_state;

	/**
	 * 备注
	 */
	private String vst_button_summary;

	public Integer getVst_pk_id() {
		return vst_pk_id;
	}

	public void setVst_pk_id(Integer vstPkId) {
		vst_pk_id = vstPkId;
	}

	public String getVst_button_id() {
		return vst_button_id;
	}

	public void setVst_button_id(String vstButtonId) {
		vst_button_id = vstButtonId;
	}

	public String getVst_button_name() {
		return vst_button_name;
	}

	public void setVst_button_name(String vstButtonName) {
		vst_button_name = vstButtonName;
	}

	public String getVst_button_img() {
		return vst_button_img;
	}

	public void setVst_button_img(String vstButtonImg) {
		vst_button_img = vstButtonImg;
	}

	public String getVst_button_onclick() {
		return vst_button_onclick;
	}

	public void setVst_button_onclick(String vstButtonOnclick) {
		vst_button_onclick = vstButtonOnclick;
	}

	public Long getVst_button_addtime() {
		return vst_button_addtime;
	}

	public void setVst_button_addtime(Long vstButtonAddtime) {
		vst_button_addtime = vstButtonAddtime;
	}

	public Long getVst_button_uptime() {
		return vst_button_uptime;
	}

	public void setVst_button_uptime(Long vstButtonUptime) {
		vst_button_uptime = vstButtonUptime;
	}

	public String getVst_button_operator() {
		return vst_button_operator;
	}

	public void setVst_button_operator(String vstButtonOperator) {
		vst_button_operator = vstButtonOperator;
	}

	public Integer getVst_button_index() {
		return vst_button_index;
	}

	public void setVst_button_index(Integer vstButtonIndex) {
		vst_button_index = vstButtonIndex;
	}

	public Integer getVst_button_state() {
		return vst_button_state;
	}

	public void setVst_button_state(Integer vstButtonState) {
		vst_button_state = vstButtonState;
	}

	public String getVst_button_summary() {
		return vst_button_summary;
	}

	public void setVst_button_summary(String vstButtonSummary) {
		vst_button_summary = vstButtonSummary;
	}

	@Override
	public String toString() {
		return "VstSysButton [vst_button_addtime=" + vst_button_addtime
				+ ", vst_button_id=" + vst_button_id + ", vst_button_img="
				+ vst_button_img + ", vst_button_index=" + vst_button_index
				+ ", vst_button_name=" + vst_button_name
				+ ", vst_button_onclick=" + vst_button_onclick
				+ ", vst_button_operator=" + vst_button_operator
				+ ", vst_button_state=" + vst_button_state
				+ ", vst_button_summary=" + vst_button_summary
				+ ", vst_button_uptime=" + vst_button_uptime + ", vst_pk_id="
				+ vst_pk_id + "]";
	}

}
