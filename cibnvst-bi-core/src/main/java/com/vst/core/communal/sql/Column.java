package com.vst.core.communal.sql;

import java.io.Serializable;

/**
 * @author weiwei(joslyn)
 * @date 2017-9-26 下午08:24:16
 * @decription sql列bean
 * @version
 */
public class Column implements Serializable{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 8837568255426029667L;

	/**
	 * 列名
	 */
	private String name;

	/**
	 * 别名
	 */
	private String alias;

	/**
	 * 该列操作类型，1：单纯列值，2：sum操作该列，3：distinct+count操作该列值
	 */
	private int operateType;

	/**
	 * 数据类型，1：字符串，2：整数，3：浮点数，4：布尔类型
	 */
	private int dataType;
	
	/**
	 * 构造器
	 */
	public Column() {
		
	}

	/**
	 * 带餐构造器
	 * @param name
	 * @param dataType
	 */
	public Column(String name, int dataType) {
		this.name = name;
		this.dataType = dataType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public int getOperateType() {
		return operateType;
	}

	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	@Override
	public String toString() {
		return "Column [name=" + name + ", alias=" + alias + ", dataType="
				+ dataType + ", operateType=" + operateType + "]";
	}
}
