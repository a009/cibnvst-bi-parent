package com.vst.common.tools.string;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author weiwei(joslyn)
 * @date 2016-8-5 下午08:49:57
 * @decription
 * @version 
 */
public class ToolsString {
	/**
	 *  null
	 */
	public static final String _NULL = null;
	
	/**
	 * 空字符串
	 */
	public static final String _EMPTY = "";
	
	/**
	 * 九宫格
	 */
	private static final Map<String, String> keynumMap = new HashMap<String, String>(9);
	
	/**
	 * 九宫格转字母
	 * @param keynum
	 * @return
	 */
	public static String getLetterByKeynum(String keynum){
		if(keynumMap.size() == 0){
			keynumMap.put("1", "1");
			keynumMap.put("2", "a,b,c");
			keynumMap.put("3", "d,e,f");
			keynumMap.put("4", "g,h,i");
			keynumMap.put("5", "j,k,l");
			keynumMap.put("6", "m,n,o");
			keynumMap.put("7", "p,q,r,s");
			keynumMap.put("8", "t,u,v");
			keynumMap.put("9", "w,x,y,z");
		}
		return keynumMap.get(keynum);
	}
	
	/**
	 * 校验字符串是否为null
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str){
		return str == _NULL;
	}
	
	/**
	 * 校验对象是否为空
	 * @param str null or 空字符串都归类为空
	 * @return true:为空，false：不为空
	 */
	public static boolean isEmpty(Object obj){
		if (obj == null) {
			return true;
		}
		boolean flag = false;
		
		if(obj instanceof String){
			flag = isEmpty(obj.toString());
		}else if(obj instanceof Integer){
			flag = obj == null;
		}else if(obj instanceof Long){
			flag = obj == null;
		}else if(obj instanceof Boolean){
			flag = (obj == null || (!obj.toString().trim().equalsIgnoreCase("true") && !obj.toString().trim().equalsIgnoreCase("false")));
		}
		return flag;
	}
	
	/**
	 * 校验字符串是否为空
	 * @param str null or 空字符串都归类为空
	 * @return true:为空，false：不为空
	 */
	public static boolean isEmpty(String str){
		if (str == null) {
			return true;
		}
		boolean flag = false;
		if (str.length() == 0 || str.trim().equals("")
				|| str.trim().equals("null")) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 判断该str对象是否为空,并且返回空字符串或者不为空的结果
	 * @param str
	 * @return 返回字符串
	 */
	public static String checkEmpty(String str) {
		return checkEmpty(str, "");
	}
	
	/**
	 * 判断该str对象是否为空
	 * @param str
	 * @param defaultStr
	 * @return
	 */
	public static String checkEmpty(String str, String defaultStr) {
		String result = defaultStr;
		if (str != null && str.length() != 0 && !str.trim().equals("")
				&& !str.trim().equals("null")) {
			result = str;
		}
		return result;
	}

	/**
	 * 判断该str对象是否为空,并且返回空字符串或者不为空的结果
	 * @param str
	 * @return 返回字符串
	 */
	public static String checkEmpty(Object obj) {
		return checkEmpty(String.valueOf(obj));
	}
	
	/**
	 * 判断该str对象是否为空
	 * @param obj
	 * @param defaultStr
	 * @return
	 */
	public static String checkEmpty(Object obj, String defaultStr) {
		return checkEmpty(String.valueOf(obj), defaultStr);
	}
	
	/**
	 * 正则匹配
	 * @param str
	 * @param regx
	 * @return
	 */
	public static String matcher(String str, String regx){
		if(isEmpty(str) || isEmpty(regx)){
			return "";
		}
		
		Matcher m = Pattern.compile(regx).matcher(str);
		if(m.find()){
			return m.group(1);
		}
		return "";
	}
	
	/**
	 * 正则匹配
	 * @param str
	 * @param regx
	 * @return
	 */
	public static String matcher(String str, String regx, int i){
		if(isEmpty(str) || isEmpty(regx)){
			return "";
		}
		
		Matcher m = Pattern.compile(regx).matcher(str);
		while(m.find()){
			return m.group(i);
		}
		return "";
	}
	
	/**
	 * 将带有中括号的字符串类似   [《爱情转移》][《十年》][《浮夸》][《K歌之王》][《好久不见》]   替换成按照replacement分割的字符串
	 * <br>如果字符串不符合这种规格，或者替换的过程中出错，将返回源字符串
	 * @param str 源字符串
	 * @param replacement 分隔符
	 * */
	public static String replaceStr(String str,String replacement){
		String temp = "";
		String result = "";
		try {
			temp = checkEmpty(str);
			if(temp.indexOf("[") != -1 && temp.indexOf("]") != -1){
				String string = temp.replaceAll("\\]\\[", replacement);
				result  = string.substring(str.indexOf("[")+1, string.lastIndexOf("]"));
			}
		} catch (Exception e) {
			result = str;
		}
		return result;
	}
	
	/**
	 * 根据清晰度值来获取清晰度名称
	 * @param value
	 * @return
	 */
	public static String qxd(int value){
		String result = "";
		switch (value) {
		case 1:
			result = "标清";
			break;
		case 2:
			result = "高清";
			break;
		case 3:
			result = "超清";
			break;
		case 4:
			result = "蓝光";
			break;
		case 5:
			result = "3D";
			break;
		case 6:
			result = "原画";
			break;
		default:
			break;
		}
		return result;
	}
	
    /**
     * 数组转字符串
     *
     * @param delimiter 界定符
     * @return str
     */
    public static String mkString(Object[] array, String delimiter) {
        String tmpDelimiter = delimiter;
        if (isEmpty(tmpDelimiter)) tmpDelimiter = "";

        StringBuilder sb = new StringBuilder();
        if (array != null && array.length > 0) {
            for (Object o : array) {
                if (!isEmpty(o)) sb.append(tmpDelimiter).append(ToolsString.checkEmpty(o));
            }

            if (sb.length() > tmpDelimiter.length()) sb.delete(0,tmpDelimiter.length());
        }
        return sb.toString();
    }
	
    /**
     * @param str       源字符串
     * @param delimiter 界定符
     */
    public static List<String> strToList(String str, String delimiter) {
        if (isEmpty(str)) return Collections.emptyList();
        return Arrays.asList(str.split(delimiter));
    }
	
    /**
     * 字符串转Map
     *
     * @param str       源字符串
     * @param separator 分隔符
     * @param delimiter 界定符
     */
    public static Map<String, String> strToMap(String str, String separator, String delimiter) {
        HashMap<String, String> map = null;
        if (isEmpty(str)) return null;

        String tmpSeparator = ",";
        if (!isEmpty(separator)) tmpSeparator = separator;

        String tmpDelimiter = ":";
        if (!isEmpty(delimiter)) tmpDelimiter = delimiter;

        String[] strs = str.split(tmpSeparator);

        for (String tmpStr : strs) {
            String[] keyValue = tmpStr.split(tmpDelimiter);
            if (keyValue.length != 2) continue;

            if (map == null) map = new HashMap<String, String>();

            map.put(keyValue[0], keyValue[1]);
        }
        return map;
    }

    /**
     * @param map       数据源
     * @param separator 分隔符
     * @param delimiter 界定符
     */
    public static String mapToStr(Map<String, String> map, String separator, String delimiter) {
        if (map == null || map.isEmpty()) return null;

        String tmpSeparator = ",";
        if (!isEmpty(separator)) tmpSeparator = separator;

        String tmpDelimiter = ":";
        if (!isEmpty(delimiter)) tmpDelimiter = delimiter;

        String[] tmpStrs = new String[map.size()];
        int index = 0;
        for (Map.Entry<String, String> keyValue : map.entrySet()) {
            tmpStrs[index] = keyValue.getKey() + tmpDelimiter + keyValue.getValue();
            index++;
        }
        return mkString(tmpStrs, tmpSeparator);
    }
}
