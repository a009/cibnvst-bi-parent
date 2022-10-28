package com.vst.core.communal.sql;

import java.io.Serializable;

/**
 * @author weiwei(joslyn)
 * @date 2017-9-26 下午08:37:34
 * @decription 排序条件bean
 * @version
 */
public class OrderBy implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -9020226480040269036L;

	/**
	 * 排序列名称
	 */
	private String name;

	/**
	 * 排列顺序，升序：asc，倒序：desc
	 */
	private String sort;

	/**
	 * 备注
	 */
	private String desc;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "OrderBy [name=" + name + ", sort=" + sort + ", desc=" + desc
				+ "]";
	}
}
