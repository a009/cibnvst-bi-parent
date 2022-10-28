package com.vst.common.tools.pinyin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * 汉字字典，可以查询汉字的拼音，部首和笔画
 * 
 */
public class ToolsDict {
	/** 汉字最小unicode值 */
	public static final char HAN_MIN = '一';
	/** 汉字最大unicode值 */
	public static final char HAN_MAX = '龥';
	/** 汉字数据，从"一"到"龥" */
	public static final String[] HAN_DATA = new String[HAN_MAX - HAN_MIN + 1];
	/** 汉字数据文件 */
	private static final String HAN_DATA_FILE = "/data.txt";
	/** 汉字数据文件编码 */
	private static final Charset FILE_CHARSET = Charset.forName("utf-8");

	/** 笔画数据下标 */
	private static final int INDEX_BH = 2;

	/**
	 * 是否加载配置文件
	 */
	private static boolean flag = false;
	
	/**
	 * 获取汉字笔画，如 "大"的笔画为"134"<br>
	 * 12345 对应 "横竖撇捺折"
	 * @param str
	 *            单个汉字
	 * @return String
	 * @throws IOException 
	 */
	public static String getFullBH(String str){
		if (str == null || str.isEmpty()) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		for(char c : str.toCharArray()){
			sb.append(getFullBH(c));
		}
		return sb.toString();
	}

	/**
	 * 获取汉字笔画，如 "大"的笔画为"134"<br>
	 * 12345 对应 "横竖撇捺折"
	 * 
	 * @param ch
	 *            汉字
	 * @return String
	 * @throws IOException 
	 */
	public static String getFullBH(char ch){
		// 如果还没有加载过文件
		if(!flag){
			try {
				new ToolsDict().loadHanData();
			} catch (IOException e) {
			}
			flag = true;
		}
		
		if (isHan(ch)) {
			return HAN_DATA[ch - HAN_MIN].split("\\|")[INDEX_BH];
		}
		return "";
	}
	
	/**
	 * 获取汉字首笔画
	 * @param str
	 * @return
	 */
	public static String getFirstBH(String str){
		if (str == null || str.isEmpty()) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		for(char c : str.toCharArray()){
			sb.append(getFirstBH(c));
		}
		return sb.toString();
	}
	
	/**
	 * 获取字符首笔画
	 * @param ch
	 * @return
	 */
	public static String getFirstBH(char ch){
		// 如果还没有加载过文件
		if(!flag){
			try {
				new ToolsDict().loadHanData();
			} catch (IOException e) {
			}
			flag = true;
		}
		
		if (isHan(ch)) {
			return HAN_DATA[ch - HAN_MIN].split("\\|")[INDEX_BH].substring(0, 1);
		}
		return "";
	}
	
	/**
	 * 检查是否汉字
	 * 
	 * @param ch
	 * @return
	 */
	private static boolean isHan(char ch) {
		if (ch >= HAN_MIN && ch <= HAN_MAX) {
			return true;
		}
		return false;
	}

	private void loadHanData() throws IOException {
		InputStream fis = this.getClass().getResourceAsStream(HAN_DATA_FILE);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(fis,
					FILE_CHARSET));
			String line = null;
			int index = 0;
			while ((line = br.readLine()) != null) {
				HAN_DATA[index++] = line;
			}
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
	}
}
