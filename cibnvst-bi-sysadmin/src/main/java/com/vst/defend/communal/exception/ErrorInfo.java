package com.vst.defend.communal.exception;

/**
 * @author weiwei
 * @date 2014-10-27 下午08:23:14
 * @description 
 * @version	
 */
public class ErrorInfo {

	/**
	 * 构造器私有化
	 */
	private ErrorInfo(){
		
	}
	
	/**
	 * 根据错误码获取失败信息
	 * @param errCode
	 * @return
	 */
	public static String getErrorInfo(int errCode){
		String info = null;
        switch (errCode) {
            case 1:
                info = "数据库访问异常";
                break;
            case 2:
                info = "数据库操作失败";
                break;
            case 3:
                info = "业务处理失败";
                break;
            case 4:
                info = "系统异常";
                break;
            case 5:
                info = "渠道代码或帐号或密码为空";
                break;
            case 6:
                info = "帐号或密码错误";
                break;
            case 7:
                info = "该帐号已停用，如有需要，请联系VST客服";
                break;
            case 8:
                info = "该帐号未激活，请联系VST客服帮您激活该帐号";
                break;
            case 9:
                info = "该帐号已过期，请联系VST客服帮您续约";
                break;
            case 10:
            	info = "验证码错误";
            	break;
            case 11:
            	info = "修改密码失败";
            	break;
            case 12:
            	info = "该账户角色已被禁用，如有需要，请联系VST客服";
            	break;
            default:
            	info = "系统异常";
                break;
        }
		return info;
	}
}
