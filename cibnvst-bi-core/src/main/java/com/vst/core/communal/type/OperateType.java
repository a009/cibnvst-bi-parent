package com.vst.core.communal.type;

/**
 * @author weiwei(joslyn)
 * @date 2017-10-16 下午02:51:26
 * @decription 元素数据类型
 * @version 
 */
public enum OperateType{
	/**
	 * 单纯列值
	 */
	SIMPLE(1),
	
	/**
	 * 求和
	 */
	SUM(2),
	
	/**
	 * distinct+count操作
	 */
	DISTINCT_COUNT(3),
	
	/**
	 * 去重列
	 */
	FIRST(4),
	
	/**
	 * 自定义列
	 */
	CUSTOM(5),
	
	/**
	 * count列
	 */
	COUNT(6);
	
	/**
	 * 当前类型
	 */
	private int type;
	
	private OperateType(int type){
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public String toString(){
		return "operateType = " + type;
	}
}
