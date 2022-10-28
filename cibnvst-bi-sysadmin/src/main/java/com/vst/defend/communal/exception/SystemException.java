package com.vst.defend.communal.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.vst.common.tools.io.ToolsIO;

/**
 * @author weiwei
 * @date 2014-10-27 下午08:18:23
 * @description 系统异常类
 * @version
 */
public class SystemException extends RuntimeException {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -6685101121315052715L;

	/**
	 * 错误码
	 */
	private int errCode;

	/**
	 * 错误信息
	 */
	private String errMsg;

	/**
	 * 默认构造器
	 */
	public SystemException() {
		super();
	}

	/**
	 * 带参构造器
	 * @param e
	 */
	public SystemException(Exception e) {
		super(e);
	}

	/**
	 * 带失败信息构造器
	 * @param errMsg
	 */
	public SystemException(String errMsg) {
		super(errMsg);
		this.errMsg = errMsg;
	}

	/**
	 * 带异常码的构造器
	 * @param errCode
	 * @param e
	 */
	public SystemException(int errCode, Exception e) {
		super(ErrorInfo.getErrorInfo(errCode), e);
		this.errCode = errCode;
		this.errMsg = ErrorInfo.getErrorInfo(errCode);
	}

	/**
	 * @Description: 获取错误code
	 * @param @return 设定文件
	 * @return int 返回类型
	 */
	public int getErrCode() {
		return errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}
	
	/**
	 * 获取异常跟踪信息
	 * @param e
	 * @return
	 */
	public static String getExceptionInfo(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter out = null;
		try {
			out = new PrintWriter(sw);
			e.printStackTrace(out);
		} catch (Exception e2) {
		} finally {
			// 关流
			ToolsIO.closeStream(out);
		}
		return sw.toString();
	}
}
