package com.vst.defend.communal.bean;

/**
 * 网址类
 * @author lhp
 * @date 2017-6-21 下午03:49:47
 * @description
 * @version
 */
public class VstUrl {
	/**
	 * 网址名称
	 */
	private String vst_url_name;
	
	/**
	 * 网址地址
	 */
	private String vst_url_address;
	
	/**
	 * 网址图片
	 */
	private String vst_url_img;
	
	
	public VstUrl(){
		
	}
	
	public VstUrl(String vst_url_name, String vst_url_address, String vst_url_img){
		this.vst_url_name = vst_url_name;
		this.vst_url_address = vst_url_address;
		this.vst_url_img = vst_url_img;
	}

	public String getVst_url_name() {
		return vst_url_name;
	}

	public void setVst_url_name(String vstUrlName) {
		vst_url_name = vstUrlName;
	}

	public String getVst_url_address() {
		return vst_url_address;
	}

	public void setVst_url_address(String vstUrlAddress) {
		vst_url_address = vstUrlAddress;
	}

	public String getVst_url_img() {
		return vst_url_img;
	}

	public void setVst_url_img(String vstUrlImg) {
		vst_url_img = vstUrlImg;
	}

	@Override
	public String toString() {
		return "VstUrl [vst_url_address=" + vst_url_address + ", vst_url_img="
				+ vst_url_img + ", vst_url_name=" + vst_url_name + "]";
	}
}
