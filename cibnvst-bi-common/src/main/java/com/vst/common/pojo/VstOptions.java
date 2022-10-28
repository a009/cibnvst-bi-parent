package com.vst.common.pojo;
/**
 * 数据常量配置表
 * @author WuMingzhi
 *
 */
public class VstOptions {
	private String vst_option_key;
	private String vst_option_value;
	private String vst_option_desc;
	
	public String getVst_option_key() {
		return vst_option_key;
	}

	public void setVst_option_key(String vst_option_key) {
		this.vst_option_key = vst_option_key;
	}

	public String getVst_option_value() {
		return vst_option_value;
	}

	public void setVst_option_value(String vst_option_value) {
		this.vst_option_value = vst_option_value;
	}

	public String getVst_option_desc() {
		return vst_option_desc;
	}

	public void setVst_option_desc(String vst_option_desc) {
		this.vst_option_desc = vst_option_desc;
	}

	public String toString(){
		return "VstOptions [vst_option_key=" + vst_option_key +
				", vst_option_value=" + vst_option_value +
				", vst_option_desc=" + vst_option_desc + "]";
	}
}
