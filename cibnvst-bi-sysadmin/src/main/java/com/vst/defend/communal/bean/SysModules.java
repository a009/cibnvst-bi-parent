package com.vst.defend.communal.bean;

import java.util.List;

import com.vst.common.pojo.VstSysButton;

/**
 * @author weiwei
 * @date 2014-11-6 下午07:55:12
 * @description
 * @version
 */
public class SysModules {

	/**
	 * 模块id
	 */
	private String moduleId;

	/**
	 * 模块名称
	 */
	private String moduleName;

	/**
	 * 子模块列表
	 */
	private List<SysModules> moduleList;

	/**
	 * 模块按钮列表
	 */
	private List<VstSysButton> buttonList;

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public List<SysModules> getModuleList() {
		return moduleList;
	}

	public void setModuleList(List<SysModules> moduleList) {
		this.moduleList = moduleList;
	}

	public List<VstSysButton> getButtonList() {
		return buttonList;
	}

	public void setButtonList(List<VstSysButton> buttonList) {
		this.buttonList = buttonList;
	}

	@Override
	public String toString() {
		return "SysModules [buttonList=" + buttonList + ", moduleId="
				+ moduleId + ", moduleList=" + moduleList + ", moduleName="
				+ moduleName + "]";
	}

}
