package com.vst.defend.communal.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vst.common.tools.date.ToolsDate;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;

/**
 * 
 * @author lhp
 * @date 2017-5-2 下午02:23:58
 * @description
 * @version
 */
public class VstTools {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstTools.class);
	
	/**
	 * 编码
	 */
	private static final String _UNCODE = "UTF-8";//UTF-8
	
	/**
	 * 获取播放网址
	 * @param url
	 * @param map
	 * @return
	 */
	public static String getPlay(String url, Map<String, String> map) {
		String retsite = "video_0";
		for (String site : map.keySet()) {
			String absite = map.get(site);
			if (!ToolsString.isEmpty(absite)) {
				if (absite.indexOf("@") != -1) {
					String[] urls = absite.split("@");
					for (int i = 0; i < urls.length; i++) {
						if (url.indexOf(urls[i]) != -1) {
							retsite = site;
							break;
						}
					}
				} else {
					if (url.indexOf(absite) != -1) {
						retsite = site;
					}
				}
			}
		}
		return retsite;
	}
	
	/**
	 * 移除某些特殊字符
	 * @param str
	 * @return 返回list集合
	 */
	public static List<String> removeStr(String str) {
		// 返回结果
		List<String> result = new ArrayList<String>();

		if (!ToolsString.isEmpty(str)) {
			while (str.indexOf("[") != -1 && str.indexOf("]") != -1) {
				String cell = str.substring(str.indexOf("[") + 1, str
						.indexOf("]"));
				result.add(cell);

				if (str.indexOf("]") <= str.length() - 1) {
					str = str.substring(str.indexOf("]") + 1);
				} else {
					break;
				}
			}
		}

		return result;
	}

	/**
	 * 移除某些特殊字符
	 * @param str
	 * @return 返回字符串
	 */
	public static String removeStr2(String str) {
		// 返回结果
		StringBuilder result = new StringBuilder();

		if (!ToolsString.isEmpty(str) && !"0".equals(str.trim())) {
			while (str.indexOf("[") != -1 && str.indexOf("]") != -1) {
				String cell = str.substring(str.indexOf("[") + 1, str
						.indexOf("]"));
				result.append(cell).append(",");

				if (str.indexOf("]") <= str.length() - 1) {
					str = str.substring(str.indexOf("]") + 1);
				} else {
					break;
				}
			}
		} else {
			return str;
		}

		return result.length() > 0 ? result.substring(0, result.length() - 1)
				: result.toString();
	}
	
	/**
	 * 得到网站源码 指定编码 2014-12-3
	 * @param url
	 * @param encoding
	 * @return
	 * @throws IOException
	 * */
	public static String getCodeByUrl(String url, String encoding)	throws IOException {
		BufferedReader br = null;
		URL myUrl = new URL(url);
		GZIPInputStream gzipStream = null;
		StringBuilder sb = new StringBuilder();
		InputStreamReader inputStreamReader = null;
		//ByteArrayBuffer buf = new ByteArrayBuffer(4096);
		HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
		//String location = conn.getHeaderField("Location");  
	    //myUrl = new URL(location);  
		try {
		    conn = (HttpURLConnection) myUrl.openConnection();  
		    conn.setRequestMethod("GET");   
			conn.setConnectTimeout(20000);
			conn.setReadTimeout(20000);
			conn.setRequestProperty("Content-type","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36");
		    conn.connect();  
			int statusCode = conn.getResponseCode();
			if (200 == statusCode || 206 == statusCode) {
				String Encoding = conn.getContentEncoding();
				if ("gzip".equals(Encoding)) {
					int length = -1;
					char[] buffer = new char[4096];
					gzipStream = new GZIPInputStream(conn.getInputStream());
					inputStreamReader = new InputStreamReader(gzipStream, encoding);
					while ((length = inputStreamReader.read(buffer)) != -1) {
						sb.append(new String(buffer, 0, length));
					}
//					int length = -1;
//					byte[] tmp = new byte[4096];
//					gzipStream = new GZIPInputStream(conn.getInputStream());
//					while ((length = gzipStream.read(tmp)) != -1) {
//						buf.write(tmp, 0, length);
//					}
//					sb.append(new String(buf.toByteArray(), encoding));
				} else {
					String line = null;
					br = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
					while ((line = br.readLine()) != null) {
						sb.append(line);
					}
				}
			}
		} finally {
			if (inputStreamReader != null){
				inputStreamReader.close();
				inputStreamReader = null;
			}
			if (gzipStream != null) {
				gzipStream.close();
				gzipStream = null;
			}
			if (br != null) {
				br.close();
				br = null;
			}
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		return sb.toString();
	}

	/**
	 * 发送http请求
	 * @param urlStr
	 * @return
	 * @throws IOException 
	 */
	public static String httpRequest(String urlStr) throws IOException  {
		return getCodeByUrl(urlStr, _UNCODE);
	}
	
	/**
	 * 格式化字符串成数字
	 * @param str
	 * @return
	 */
	public static int parseInt(String str){
		int result = -1;
		if (!ToolsString.isEmpty(str)) {
			try {
				result = Integer.parseInt(str.replaceAll(",", "").replaceAll("-", "").trim());
			} catch (NumberFormatException e) {
				logger.error("parse error. ERROR:" + e.getMessage());
			}
		}
		return result;
	}
	
	/**
	 * 获取页面源代码
	 * @param urlString
	 * @return
	 */
	public static String getHtml(String urlString) {
		try {
			StringBuffer html = new StringBuffer();
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			conn.connect();
			InputStreamReader isr = new InputStreamReader(
					conn.getInputStream(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String temp;
			while ((temp = br.readLine()) != null) {
				html.append(temp).append("\n");
			}
			br.close();
			isr.close();
			return html.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 文件下载
	 * @param response
	 * @param file
	 * @return
	 */
	public static boolean fileDownLoad(HttpServletResponse response, File file){
		InputStream fis = null;
		OutputStream os = null;
		boolean success = false;
		try {
			fis = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
	        fis.close();
	        response.reset();
	        String fileName  = URLEncoder.encode(file.getName(),"UTF-8");
	        response.setContentType("application/octet-stream");
	        response.addHeader("Content-Disposition", "attachment;filename="+fileName);
	        response.addHeader("Content-Length", "" + file.length());
	        os = new BufferedOutputStream(response.getOutputStream());
	        os.write(buffer);
	        os.flush();
	        os.close();
	        success = true;
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}finally{
			try {
				if(os!=null){
					os.flush();
					os.close();
				}
				if(fis!=null){
					fis.close();
				}
			} catch (IOException e) {
				os=null;
				fis=null;
				logger.error(e.getMessage());
			}
		}
		return success;
	}
	
	/**
	 * 解码成UTF-8
	 */
	public static void decodeToUTF_8(Map<String, Object> map){
		if(map != null && !map.isEmpty()){
			for(Map.Entry<String, Object> entry : map.entrySet()){
				try {
					map.put(entry.getKey(), URLDecoder.decode(entry.getValue()+"", "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 获取两个日期之间的所有日期
	 * @param startDay
	 * @param endDay
	 * @return
	 */
	public static List<Integer> getDateSection(int startDay, int endDay){
		List<Integer> dateList = new ArrayList<Integer>();
		if(startDay == endDay){
			dateList.add(startDay);
		}else if(startDay < endDay){
			Date dBegin = ToolsDate.parseDate(ToolsDate.yyyy_MM_dd4, startDay+"");
			Calendar calBegin = Calendar.getInstance();
			calBegin.setTime(dBegin);
			
			Date dEnd = ToolsDate.parseDate(ToolsDate.yyyy_MM_dd4, endDay+"");
			Calendar calEnd = Calendar.getInstance();
			calEnd.setTime(dEnd);
			
			while (dEnd.after(calBegin.getTime())) {
				dateList.add(ToolsNumber.parseInt(ToolsDate.formatDate(ToolsDate.yyyy_MM_dd4, calBegin.getTime().getTime())));
				// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
				calBegin.add(Calendar.DAY_OF_MONTH, 1);
			}
			dateList.add(endDay);
		}
		return dateList;
	}
	
	/**
	 * 获取两个日期之间的所有日期
	 * @param startDay
	 * @param endDay
	 * @param parttern
	 * @return
	 */
	public static List<Integer> getDateSection(String startDay, String endDay, String parttern){
		int start = parseInt(ToolsDate.formatDate(parttern, ToolsDate.toUnixTimestamp(parttern, startDay)));
		int end = parseInt(ToolsDate.formatDate(parttern, ToolsDate.toUnixTimestamp(parttern, endDay)));
		return getDateSection(start, end);
	}
	
	/**
	 * 时间转小时，保留两位小数
	 * @param time 毫秒数
	 * @return
	 */
	public static double toHour(long time){
		double result = 0;
		try{
			result = Math.round(time*100.0/(1000*60*60))/100.0;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 时间转分钟，保留两位小数
	 * @param time 毫秒数
	 * @return
	 */
	public static double toMinute(long time){
		double result = 0;
		try{
			result = Math.round(time*100.0/(1000*60))/100.0;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 参数格式化
	 * 功能：①=开头，做等于判断；②!=开头，做不等于判断；③否则，默认
	 * @param key
	 * @param value
	 * @param params
	 */
	public static void paramFormat(String key, String value, Map<String, Object> params){
		if(params != null && !ToolsString.isEmpty(value)){
			params.remove(key);
			if(value.startsWith("=")){
				params.put(key+"_eq", value.substring(1));
			}else if(value.startsWith("!=")){
				params.put(key+"_ne", value.substring(2));
			}else{
				params.put(key, value);
			}
		}
	}
}
