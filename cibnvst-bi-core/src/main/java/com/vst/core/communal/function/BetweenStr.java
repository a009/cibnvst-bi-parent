package com.vst.core.communal.function;

import org.apache.spark.sql.api.java.UDF3;

import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;

/**
 * @author weiwei(joslyn)
 * @date 2018-3-22 下午03:21:31
 * @decription
 * @version 
 */
public class BetweenStr implements UDF3<String, Integer, Integer, Long>{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -2787539595240903763L;

	@Override
	public Long call(String str, Integer startDate, Integer endDate) throws Exception {
		if(!ToolsString.isEmpty(str)){
			String[] compareDates = str.split(",");
			for(String compareDate : compareDates){
				int currentDate = ToolsNumber.parseInt(compareDate);
				if(startDate <= currentDate && endDate >= currentDate){
					return 1L;
				}
			}
		}
		return 0L;
	}

}
