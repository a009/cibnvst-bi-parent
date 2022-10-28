package com.vst.core.communal.type;

/**
 * @author weiwei(joslyn)
 * @date 2017-11-2 下午12:03:44
 * @decription
 * @version 
 */
public enum RowKeyModel {

	/**
	 * 以用户的uuid为rowKey
	 */
	UUID(1),
	
	/**
	 * 
	 */
	TIMESTAMP(2);
	
	/**
	 * 当前类型
	 */
	private int type;
	
	private RowKeyModel(int type){
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public String toString(){
		return "RowKeyModel = " + type;
	}
	
}
