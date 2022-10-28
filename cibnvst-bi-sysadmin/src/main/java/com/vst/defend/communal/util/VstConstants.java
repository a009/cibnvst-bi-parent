package com.vst.defend.communal.util;

/**
 * @author wei.wei(joslyn)
 * @date 2014-10-26 下午04:28:11
 * @description
 */
public interface VstConstants {

	/**
	 * 验证码
	 */
	String SESSION_KEY_RANDOM = "session_key_random";
	
	/**
	 * 用户登录session
	 */
	String SESSION_KEY_USER = "session_key_user";
	
	
	//========================================================
	/**
	 * 状态：正常
	 */
	int STATE_AVALIABLE = 1;
	
	/**
	 * 状态：禁用
	 */
	int STATE_DISABLED = 2;
	
	/**
	 * 状态：打回
	 */
	int STATE_BACK = 3;
	
	/**
	 * 状态:删除
	 */
	int STATE_DEL = 4;
	
	//========================================================
	/**
	 * 用户未激活
	 */
	int USER_ACTIVATE_NO = 1;

	/**
	 * 用户已激活
	 */
	int USER_ACTIVATE_YES = 2;
	
	
	//========================================================
	/**
	 * 操作成功
	 */
	int OPERATE_SUCCESS = 1;
	
	/**
	 * 操作失败
	 */
	int OPERATE_ERROR = 2;
	
	/**
	 * 操作信息
	 */
	int OPERATE_INFO = 3;
	
	
	//========================================================
	/**
	 * 操作类型：新增
	 */
	int OPERATE_TYPE_ADD = 1;
	
	/**
	 * 操作类型：修改
	 */
	int OPERATE_TYPE_UPDATE = 2;
	
	/**
	 * 操作类型：删除
	 */
	int OPERATE_TYPE_DEL = 3;
	
	/**
	 * 操作类型：导出
	 */
	int OPERATE_TYPE_EXPORT = 4;
	
	
	//========================================================
	/**
	 * 数据类型，0：（超级管理员）系统默认
	 */
	int DATA_TYPE_DEFAULT = 0;
	
	/**
	 * 数据类型，1：(VST)用户自定义
	 */
	int DATA_TYPE_CUSTOM = 1;
	
	/**
	 * 数据类型，2：(广告)用户自定义
	 */
	int DATA_TYPE_ADVERTISER = 2;
	
	
	//========================================================
	/**
	 * 未审核
	 */
	int AUDIT_NO = 1;
	
	/**
	 * 审核通过未复核
	 */
	int AUDIT_PASS = 2;
	
	/**
	 * 审核不通过
	 */
	int AUDIT_NOT_PASS = 3;
	
	/**
	 * 复核通过
	 */
	int REAUDIT_PASS = 4;
	
	/**
	 * 复核不通过
	 */
	int REAUDIT_NOT_PASS = 5;
	
	//========================================================
	/**
	 * 所属部门（未知）
	 */
	int VST_SYS_DIVISION = 0;
	
	//========================================================
	/**
	 * 是否是经理部门（是）
	 */
	int VST_SYS_ISBOSS=1;
	
	/**
	 * 是否是经理部门（否）
	 */
	int VST_SYS_ISNOTBOSS=2;
	
	//========================================================
	/**
	 * 初始密码
	 */
	String VST_SYS_PASSWORD ="123456";
	
	
}
