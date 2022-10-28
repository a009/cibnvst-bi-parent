package com.vst.core.communal.type;

/**
 * sql类型枚举器
 * @author weiwei(joslyn)
 * @date 2017-10-17 下午03:49:32
 * @decription
 * @version 
 */
public enum SqlType{
	/**
	 * 父任务类型：实时计算任务
	 */
	P_ONLINE(1), 
	
	/**
	 * 父任务类型：离线计算任务
	 */
	P_OFFLINE(2), 
	
	/**
	 * 父任务类型：补充数据任务
	 */
	P_COMPLEMENT(3),
	
	/**
	 * 父任务类型：拉取数据任务
	 */
	P_PULLDATA(4),
	
	/**
	 * 子任务类型：累加任务
	 */
	C_SUM(5),
	
	/**
	 * 子任务类型：环比任务
	 */
	C_RATIO(6);
	
	/**
	 * 类型
	 */
	private int type;
	
	/**
	 * 构造器私有化
	 * @param type
	 */
	private SqlType(int type){
		this.type = type;
	}

	public int getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return String.valueOf(type);
	}
}
