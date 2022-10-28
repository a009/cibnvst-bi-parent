package com.vst.common.tools.pinyin;

import java.util.HashMap;
import java.util.Map;

import com.vst.common.tools.string.ToolsString;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @author weiwei
 * @date 2014-9-12 上午11:54:57
 * @description
 * @version
 */
public class ToolsSpell {
	
	/**
	 * 九宫格转数字
	 * 
	 * @param keynum
	 * @return
	 */
	private static Map<String, String> keynumMap = new HashMap<String, String>();
	
	/**
	 * 获取九宫格
	 * @param keynum
	 * @return
	 */
	public static String getKeynumByLetter(String keynum) {
		if (keynumMap.size() == 0) {
			keynumMap.put("1", "1");
			keynumMap.put("a,b,c", "2");
			keynumMap.put("d,e,f", "3");
			keynumMap.put("g,h,i", "4");
			keynumMap.put("j,k,l", "5");
			keynumMap.put("m,n,o", "6");
			keynumMap.put("p,q,r,s", "7");
			keynumMap.put("t,u,v", "8");
			keynumMap.put("w,x,y,z", "9");
		}
		char letter[] = keynum.toCharArray();// "lf"
		StringBuffer num = new StringBuffer();
		for (char i : letter) {
			for (Map.Entry<String, String> entry : keynumMap.entrySet()) {
				if (entry.getKey().contains(String.valueOf(i))) {// l
					num.append(entry.getValue());
				}
			}
		}
		return num.toString();
	}
	
	/**
	 * 获取汉字拼音首字母
	 * @param str
	 * @return
	 */
	public static String spell(String str){
		if(ToolsString.isEmpty(str)){
			return "";
		}
		
		StringBuilder result = new StringBuilder();
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            // 提取汉字的首字母
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
            	result.append(pinyinArray[0].charAt(0));
            }
        }
		return result.toString().toLowerCase();
	}

	/**
	 * 获取字符串内的所有汉字的全拼
	 * 
	 * @param chinese
	 * @return
	 */
	public static String spellAll(String chinese) {
		char[] charChinese = chinese.toCharArray();
		String[] arrChinese = new String[charChinese.length];
		// 设置汉字拼音输出的格式
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 小写
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不标声调
		format.setVCharType(HanyuPinyinVCharType.WITH_V);// u:的声母替换为v
		String spell = "";
		int length = charChinese.length;
		try {
			for (int i = 0; i < length; i++) {
				// 判断能否为汉字字符
				if (Character.toString(charChinese[i]).matches(
						"[\\u4E00-\\u9FA5]+")) {
					arrChinese = PinyinHelper.toHanyuPinyinStringArray(charChinese[i], format);// 将汉字的几种全拼都存到t2数组中
					spell += arrChinese[0];// 取出该汉字全拼的第一种读音并连接到字符串t4后
				} else {
					// 如果不是汉字字符，间接取出字符并连接到字符串t4后
					spell += Character.toString(charChinese[i]);
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
		}
		return spell;
	}

}