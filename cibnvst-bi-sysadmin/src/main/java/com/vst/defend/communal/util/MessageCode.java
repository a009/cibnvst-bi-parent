package com.vst.defend.communal.util;

/**
 * @author weiwei
 * @date 2014-9-11 上午10:48:20
 * @description  接口状态码类
 * @version	
 */
public class MessageCode {

	/**
	 * 构造器私有化
	 */
	private MessageCode(){
		
	}
	
	// 成功
	public static final int CODE_SUCCESS = 100;
	// 参数错误
	public static final int CODE_ERROR_PARAMETER = 201;
	// 没有结果
	public static final int CODE_ERROR_NO_RESULT = 202;
	// 服务器异常
	public static final int CODE_ERROR_SERVER_BUSY = 301;
}
