package com.vst.api.communal.type;

/**
 * @author weiwei(joslyn)
 * @date 2017-9-21 上午11:30:39
 * @decription
 * @version 
 */
public enum RangeEnum {

	/**
	 * A类型：[-,+]表示负无穷大到正无穷大，适用数字类型
	 * B类型：用{"a","b"}来枚举，适用字符串或boolean类型
	 * C类型：用![0, 10]来表示本身字符串长度的限定，适用字符串
	 */
	ERROR(-1), NONE(0), A(1), B(2), C(3);
	
	/**
	 * 格式类型
	 */
	private int formatType;
	
	/**
	 * 构造器私有化
	 * @param formatType
	 */
	private RangeEnum(int formatType) {
		this.formatType = formatType;
	}

	/**
	 * 获取格式类型
	 * @return
	 */
	public int getFormatType() {
		return formatType;
	}

	@Override
	public String toString() {
		return String.valueOf(formatType);
	}
	
}
