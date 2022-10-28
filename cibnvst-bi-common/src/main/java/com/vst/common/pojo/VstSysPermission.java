package com.vst.common.pojo;

/**
 * @author wei.wei(joslyn)
 * @date 2014-11-9 下午03:31:59
 * @description
 */
public class VstSysPermission {

	/**
	 * 角色id
	 */
	private String vst_role_id;

	/**
	 * 模块id
	 */
	private String vst_module_id;

	/**
	 * 按钮id
	 */
	private String vst_button_id;

	public String getVst_role_id() {
		return vst_role_id;
	}

	public void setVst_role_id(String vstRoleId) {
		vst_role_id = vstRoleId;
	}

	public String getVst_module_id() {
		return vst_module_id;
	}

	public void setVst_module_id(String vstModuleId) {
		vst_module_id = vstModuleId;
	}

	public String getVst_button_id() {
		return vst_button_id;
	}

	public void setVst_button_id(String vstButtonId) {
		vst_button_id = vstButtonId;
	}

	@Override
	public String toString() {
		return "VstSysPermission [vst_button_id=" + vst_button_id
				+ ", vst_module_id=" + vst_module_id + ", vst_role_id="
				+ vst_role_id + "]";
	}

}
