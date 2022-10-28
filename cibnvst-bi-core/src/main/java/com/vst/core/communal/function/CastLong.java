package com.vst.core.communal.function;

import org.apache.spark.sql.api.java.UDF1;

import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;

/**
 * @author joslyn
 * @date 2017年11月27日 下午11:14:16
 * @description 自定义函数类型转换
 * @version 1.0
 */
public class CastLong implements UDF1<Object, Long>{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 3170561287768216251L;

	@Override
	public Long call(Object obj) throws Exception {
		return ToolsNumber.parseLong(ToolsString.checkEmpty(obj));
	}

}
