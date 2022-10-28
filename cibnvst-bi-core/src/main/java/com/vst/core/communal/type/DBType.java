package com.vst.core.communal.type;

/**
 * @author weiwei(joslyn)
 * @date 2017-10-16 下午02:51:26
 * @decription 数据库类型
 * @version 
 */
public enum DBType{
	/**
	 * hbase
	 */
	HBASE(1),
	
	/**
	 * mysql
	 */
	MYSQL(2),
	
	/**
	 * 本地文件
	 */
	LOCAL_FILE(3),
	
	/**
	 * hdfs
	 */
	HDFS(4);
	
	/**
	 * 当前类型
	 */
	private int type;
	
	private DBType(int type){
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public String toString(){
		return "DBType = " + type;
	}
}
