package com.vst.defend.communal.exception;

/**
 * @author weiwei
 * @date 2014-10-27 上午11:22:22
 * @description 
 * @version	
 */
public interface ErrorCode {

	/**
	 *  数据库访问异常
	 */
	int DB_ACCESS_ERROR = 1;

	/**
	 *  数据库操作失败
	 */
	int DB_OPERATOR_FAILURE = 2;
	
	/**
	 *  业务层操作失败
	 */
	int SERVICE_OPERATOR_FAILURE = 3;
	
	/**
	 *  系统异常
	 */
	int SYS_OPERATOR_FAILURE = 4;
	
	/**
	 *  渠道代码或帐号或密码为空
	 */
	int LOGIN_ERROR_USERNAME_OR_PASSWORD_NULL = 5;
	
	/**
	 *  帐号或密码错误
	 */
	int LOGIN_ERROR_USERNAME_OR_PASSWORD = 6;
	
	/**
	 *  该帐号已停用
	 */
	int LOGIN_ERROR_USER_STOP = 7;
	
	/**
	 *  该帐号未激活
	 */
	int LOGIN_ERROR_USER_NOT_ACTIVATE = 8;
	
	/**
	 *  该帐号已过期
	 */
	int LOGIN_ERROR_USER_NOT_VALIDITY = 9;
	
	/**
	 * 验证码错误
	 */
	int LOGIN_ERROR_RANDOM_CODE = 10;
	
	/**
	 * 修改密码失败
	 */
	int UPDATE_PASSWORD_FAILURE = 11;
	
	/**
	 * 该账户角色已被禁用
	 */
	int LOGIN_ERROR_ROLE_STATE_DISABLED = 12;
	
}
