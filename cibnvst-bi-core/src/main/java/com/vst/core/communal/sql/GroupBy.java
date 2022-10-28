package com.vst.core.communal.sql;

import java.io.Serializable;

/**
 * @author weiwei(joslyn)
 * @date 2017-9-26 下午08:32:15
 * @decription 分组bean
 * @version 
 */
public class GroupBy implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3048511731304352269L;

	/**
	 * 分组key名称
	 */
	private String name;
	
	/**
	 * 分组别名
	 */
	private String alias;
	
	/**
	 * key描述
	 */
	private String desc;

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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "GroupBy [alias=" + alias + ", desc=" + desc + ", name=" + name
				+ "]";
	}
}
