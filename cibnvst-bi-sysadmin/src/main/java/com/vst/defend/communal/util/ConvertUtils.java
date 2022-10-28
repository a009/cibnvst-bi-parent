package com.vst.defend.communal.util;

/**
 * 转换工具类
 * @author lhp
 * @date 2017-5-18 上午10:37:37
 * @description
 * @version
 */
public class ConvertUtils {

	/**
	 * 将字符串数组，转换成以逗号拼接的字符串
	 * @param strArr
	 * @return
	 */
	public static String convertStringArray(String[] strArr){
		String result = "";
		if(strArr != null && strArr.length > 0){
			for(String str : strArr){
				result += str + ",";
			}
			result = result.substring(0, result.length()-1);
		}
		return result;
	}
	
	/**
	 * 转换适合年龄段
	 * 格式：起始年龄~结束年龄
	 * @param vst_suit_age
	 * @return
	 */
	public static String replaceSuitAge(String vst_suit_age){
		String result = "";
		try{
			String[] ages = vst_suit_age.split("~");
			int vst_suit_age_start = Integer.parseInt(ages[0]);
			int vst_suit_age_end = Integer.parseInt(ages[1]);
			result = vst_suit_age_start + "岁";
			if(vst_suit_age_end == -1){
				result += "以上";
			}else if(vst_suit_age_end == 0){
				result = "只适合" + vst_suit_age;
			}else{
				result += "至" + vst_suit_age_end + "岁";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
}
