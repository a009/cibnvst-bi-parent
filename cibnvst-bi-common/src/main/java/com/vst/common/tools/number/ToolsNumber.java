package com.vst.common.tools.number;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vst.common.tools.string.ToolsString;

/**
 * @author Administrator
 *
 */
public class ToolsNumber {

	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(ToolsNumber.class);
	
	private ToolsNumber(){
		
	}
	
	/**
	 * 格式化字符串成数字
	 * @param str
	 * @return
	 */
	public static int parseInt(String str){
		// 如果字符串为空，直接返回-1
		return parseInt(str, -1);
	}
	
	/**
	 * 格式化字符串成数字
	 * @param str
	 * @return
	 */
	public static int parseInt(String str, int defaultValue){
		// 如果字符串为空，直接返回defaultValue
		if(ToolsString.isEmpty(str)){
			return defaultValue;
		}
		
		int result = defaultValue;
		try {
			if(isNumber(str) && str.trim().length() < 12){
				result = Integer.parseInt(str.trim());
			}
		} catch (NumberFormatException e) {
			logger.error("Format number error. ERROR:" + e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * 格式化字符串成数字
	 * @param str
	 * @return
	 */
	public static long parseLong(String str){
		return parseLong(str, -1L);
	}
	
	/**
	 * 格式化字符串成数字
	 * @param str
	 * @return
	 */
	public static long parseLong(String str, long defaultValue){
		// 如果字符串为空，直接返回-1
		if(ToolsString.isEmpty(str)){
			return defaultValue;
		}
		
		long result = defaultValue;
		try {
			if(isNumber(str) && str.trim().length() < 20){
				result = Long.parseLong(str.trim());
			}
		} catch (NumberFormatException e) {
			logger.error("Format number error. ERROR:" + e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * 格式化字符串成数字
	 * @param str
	 * @return
	 */
	public static double parseDouble(String str){
		return parseDouble(str, -1.0d);
	}
	
	/**
	 * 格式化字符串成数字
	 * @param str
	 * @return
	 */
	public static double parseDouble(String str, double defaultValue){
		// 如果字符串为空，直接返回-1
		if(ToolsString.isEmpty(str)){
			return defaultValue;
		}
		
		double result = defaultValue;
		try {
			if(isDecimal(str)){
				result = Double.parseDouble(str.trim());
			}
		} catch (NumberFormatException e) {
			logger.error("Format number error. ERROR:" + e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * 格式化字符串成数字
	 * @param str
	 * @return
	 */
	public static float parseFloat(String str){
		return parseFloat(str, -1L);
	}
	
	/**
	 * 格式化字符串成数字
	 * @param str
	 * @return
	 */
	public static float parseFloat(String str, float defaultValue){
		// 如果字符串为空，直接返回-1
		if(ToolsString.isEmpty(str)){
			return defaultValue;
		}
		
		float result = defaultValue;
		try {
			if(isNumber(str) && str.trim().length() < 20){
				result = Float.parseFloat(str.trim());
			}
		} catch (NumberFormatException e) {
			logger.error("Format number error. ERROR:" + e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * 判断字符串是否是数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str){
		if(ToolsString.isEmpty(str)) return false;
		if(Pattern.matches("^(-)?[0-9]+$", str.trim())){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断字符串是否是数字
	 * @param str
	 * @return
	 */
	public static boolean isDecimal(String str){
		if(ToolsString.isEmpty(str)) return false;
		if(Pattern.matches("^(-)?[0-9]+((.)[0-9]+)?$", str.trim())){
			return true;
		}
		return false;
	}
	
	public static String formatHits(long hits){
		if(hits > 10000 && hits < 100000000){
			DecimalFormat df = new DecimalFormat("#.0");
			return df.format((double)hits / 10000) + "万次";
		}
		
		if(hits > 100000000){
			DecimalFormat df = new DecimalFormat("#.0");
			return df.format((double)hits / 10000) + "亿次";
		}
		return hits + "次";
	}
	
	public static String formatHits(long hits, String cell){
		if(hits > 10000 && hits < 100000000){
			DecimalFormat df = new DecimalFormat("#.00");
			return df.format((double)hits / 10000) + "万" + cell;
		}
		
		if(hits > 100000000){
			DecimalFormat df = new DecimalFormat("#.00");
			return df.format((double)hits / 100000000) + "亿" + cell;
		}
		return hits + cell;
	}
	
	/**
	 * 两数相除算百分比
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static String divideForPercent(long num1, long num2) {
		if(num2 != 0){
			 BigDecimal b1 = new BigDecimal(num1);  
			 BigDecimal b2 = new BigDecimal(num2);  
			 return new DecimalFormat("#0.00").format(b1.divide(b2, 10, BigDecimal.ROUND_HALF_UP).doubleValue()*100) + "%";  
		}
		return "0.00%";
	}
	
	/**
	 * 两数相除
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static String divide(long num1, long num2) {  
		if(num2 != 0){
			DecimalFormat df = new DecimalFormat("#0.00");
			BigDecimal b1 = new BigDecimal(num1);
			BigDecimal b2 = new BigDecimal(num2);
			return df.format(b1.divide(b2, 10, BigDecimal.ROUND_HALF_UP).doubleValue());
		}
		return "0.00%";
	}
}
