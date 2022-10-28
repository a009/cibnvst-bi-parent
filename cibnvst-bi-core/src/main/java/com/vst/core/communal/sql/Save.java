package com.vst.core.communal.sql;

import java.io.Serializable;

/**
 * @author joslyn
 * @date 2017年12月2日 下午6:37:30
 * @description
 * @version 1.0
 */
public class Save implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -5049777699511455559L;

	/**
	 * sqlId
	 */
	private String tableName;
	
	/**
	 * 字段名
	 */
	private String name;
	
	/**
	 * 字段值类型，1：字符串，2：整型，3：浮点数，4：布尔类型
	 */
	private int dataType;
	
	/**
	 * 类型，1、主键插入字段，2：普通插入字段，3：算环比key字段，4：算环比value字段
	 */
	private int type;
	
	/**
	 * 该字段为空的时候默认值
	 */
	private String defaultVaule;
	
	/**
	 * 字段长度
	 */
	private int length;
	
	/**
	 * 该字段是否需要格式化
	 */
	private boolean isFormat;
	
	/**
	 * 格式化类型，1：日期，2：新增时间，3：操作人，4：均值保留2位小数，5：均值不保留小数，6：毫秒转秒，7：字符串截取，8：天环比，9：周环比，10：月环比，11：校正属性，12：均值百分比保留2位小数
	 */
	private int formatType;
	
	/**
	 * 格式化所需要关联的字段，如果用多个，用英文逗号隔开，通常用于格式化类型5和6
	 */
	private String formatUnion;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDefaultVaule() {
		return defaultVaule;
	}

	public void setDefaultVaule(String defaultVaule) {
		this.defaultVaule = defaultVaule;
	}
	
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	public boolean isFormat() {
		return isFormat;
	}

	public void setFormat(boolean isFormat) {
		this.isFormat = isFormat;
	}

	public int getFormatType() {
		return formatType;
	}

	public void setFormatType(int formatType) {
		this.formatType = formatType;
	}

	public String getFormatUnion() {
		return formatUnion;
	}

	public void setFormatUnion(String formatUnion) {
		this.formatUnion = formatUnion;
	}

	@Override
	public String toString() {
		return "Save [dataType=" + dataType + ", defaultVaule=" + defaultVaule
				+ ", formatType=" + formatType + ", formatUnion=" + formatUnion
				+ ", isFormat=" + isFormat + ", length=" + length + ", name="
				+ name + ", tableName=" + tableName + ", type=" + type + "]";
	}

}
