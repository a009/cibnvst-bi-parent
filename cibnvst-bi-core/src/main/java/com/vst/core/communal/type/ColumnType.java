package com.vst.core.communal.type;

/**
 * @author weiwei(joslyn)
 * @date 2017-10-16 下午02:51:26
 * @decription 元素数据类型
 * @version 
 */
public enum ColumnType{
	/**
	 * 字符串
	 */
	String(1),
	
	/**
	 * 整型
	 */
	Int(2),
	
	/**
	 * 浮点型
	 */
	Float(3),
	
	/**
	 * 布尔类型
	 */
	BOOLEAN(4);
	
	/**
	 * 当前类型
	 */
	private int type;
	
	private ColumnType(int type){
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public String toString(){
		return "columnType = " + type;
	}
}
