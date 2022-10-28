package com.vst.api.communal.bean;

/**
 * @author weiwei(joslyn)
 * @date 2017-9-20 下午12:32:17
 * @decription
 * @version 
 */
public class TopicPropBean {

	/**
	 * 属性名称
	 */
	private String propName;

	/**
	 * 属性对应的默认值
	 */
	private String propValueDefault;

	/**
	 * 属性值类型，String、Integer、Long、Boolean、JSONObject、JSONArray
	 */
	private String propValueType;

	/**
	 * 是否必填，1：是，2：否
	 */
	private Integer propValueRequired;

	/**
	 * 属性值范围，格式说明：如果是数字类型，用[-,+]
	 * 表示负无穷大到正无穷大；如果是字符串或boolean类型，用{"a","b"}来枚举，用![0, 10]来表示本身字符串长度的限定
	 */
	private String propValueRange;

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public String getPropValueDefault() {
		return propValueDefault;
	}

	public void setPropValueDefault(String propValueDefault) {
		this.propValueDefault = propValueDefault;
	}

	public String getPropValueType() {
		return propValueType;
	}

	public void setPropValueType(String propValueType) {
		this.propValueType = propValueType;
	}

	public Integer getPropValueRequired() {
		return propValueRequired;
	}

	public void setPropValueRequired(Integer propValueRequired) {
		this.propValueRequired = propValueRequired;
	}

	public String getPropValueRange() {
		return propValueRange;
	}

	public void setPropValueRange(String propValueRange) {
		this.propValueRange = propValueRange;
	}

	@Override
	public String toString() {
		return "TopicPropBean [propName=" + propName + ", propValueDefault="
				+ propValueDefault + ", propValueRange=" + propValueRange
				+ ", propValueRequired=" + propValueRequired
				+ ", propValueType=" + propValueType + "]";
	}
}
