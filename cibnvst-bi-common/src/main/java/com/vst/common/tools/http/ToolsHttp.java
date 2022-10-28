package com.vst.common.tools.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.tools.file.ToolsFile;
import com.vst.common.tools.io.ToolsIO;
import com.vst.common.tools.string.ToolsString;

/**
 * @author weiwei
 * @date 2015-7-22 下午02:13:59
 * @description 
 * @version	
 */
public class ToolsHttp {

	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(ToolsHttp.class);
	
	/**
	 * 空数组
	 */
	private static String[] EMPTY_ARRAY = new String[0];
	
	/**
	 * 下载压缩文件
	 * @param url 下载地址
	 * @param filePath 生成文件地址
	 * @return true:下载成功，false：下载失败
	 */
	public static boolean downloadGzipFile(String url, String filePath){
		GZIPInputStream gzipInput = null;
		ByteArrayOutputStream out = null;
		try {
			if(ToolsString.isEmpty(url) || ToolsString.isEmpty(filePath)){
				return false;
			}
			
			URL requestUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) requestUrl.openConnection();
				
			gzipInput = new GZIPInputStream(conn.getInputStream());
			out = new ByteArrayOutputStream();
			byte[] buffer = new byte[4*1024];
			int offset = -1;
			while ((offset = gzipInput.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
				out.flush();
			}
			
			File file = new File(filePath);
			if(!file.exists()){
				file.getParentFile().mkdirs();
			}
			
			return ToolsFile.writeBytesToFile(file, out.toByteArray());
		} catch (Exception e) {
			logger.error("downloadFile error. ERROR:" + e);
		} finally {
			ToolsIO.closeStream(out, gzipInput);
		}
		return false;
	}
	
	/**
	 *  获取string数据
	 * @param requestUrl 请求地址
	 * @param isRegexFilters 是否是正则表达式过滤
	 * @param filters 过滤表达式
	 * @return
	 */
	public static String getString(Request request, boolean isRegexFilters, String... filters){
		if(request == null || ToolsString.isEmpty(request.getUrl())) return null;
		
		StringBuilder result = new StringBuilder();
		BufferedReader br = null;
		try {
			URL url = new URL(request.getUrl());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 1分钟读取超时
			conn.setReadTimeout(60000);
			// 30秒连接超时
			conn.setConnectTimeout(10000);
			
			if(!ToolsString.isEmpty(request.getReferer())){
				conn.setRequestProperty("Referer", request.getReferer());
			}
			if(!ToolsString.isEmpty(request.getDomain())){
				conn.setRequestProperty("Host", request.getDomain());
			}
			if(!ToolsString.isEmpty(request.getUserAgent())){
				conn.setRequestProperty("User-Agent", request.getUserAgent());
			}
			if(!ToolsString.isEmpty(request.getxRequestWith())){
				conn.setRequestProperty("X-Requested-With", request.getxRequestWith());
			}
			if(!ToolsString.isEmpty(request.getMethod())){
				conn.setRequestMethod(request.getMethod());
			}
			if(!ToolsString.isEmpty(request.getAccept())){
				conn.setRequestProperty("Accept", request.getAccept());
			}
			if(!ToolsString.isEmpty(request.getCookie())){
				conn.setRequestProperty("Cookie", request.getCookie());
			}
			conn.connect();
			
			if(conn.getResponseCode() > 300){
				return null;
			}
			
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			
			String line = null;
			while((line = br.readLine()) != null){
				if(filters != null){
					for(String filter : filters){
						if(isRegexFilters) {
							line = line.replaceAll(filter, "");
						}else{
							line = line.replace(filter, "");
						}
					}
				}
				
				result.append(line).append("\r\n");
			}
			conn.disconnect();
		} catch (Exception e) {
			result = null;// 清空
			logger.error("request error. request = [" + request + "], ERROR:" + e);
		} finally {
			ToolsIO.closeStream(br);// 关流
		}
		return result == null ? "" : result.toString();
	}
	
	/**
	 *  获取string数据
	 * @param requestUrl 请求地址
	 * @param isRegexFilters 是否是正则表达式过滤
	 * @param filters 过滤表达式
	 * @return
	 */
	public static String getString(String requestUrl, boolean isRegexFilters, String... filters){
		return getString(new Request(requestUrl), isRegexFilters, filters);
	}
	
	/**
	 * 获取String数据
	 * @param requestUrl 请求地址
	 * @param tryCount 失败后，尝试获取的次数
	 * @param sleeptime 请求失败后的睡眠时间
	 * @param isRegexFilters 是否是正则过滤
	 * @param filters 过滤表达式
	 * @return
	 */
	public static String tryGetString(String requestUrl, int tryCount, long sleeptime, boolean isRegexFilters, String... filters){
		String result = null;
		
		long defaultSleepTime = sleeptime == -1l ? 10 : sleeptime;
		int index = 0;
		while(index < tryCount && result == null){
			result = getString(requestUrl, isRegexFilters, filters);
			// 如果不为空，跳出
			if(result != null) break;
			try {
				Thread.sleep(defaultSleepTime);
			} catch (InterruptedException e) {
			}
			index++;
		}
		
		return result;
	}
	
	/**
	 * 获取String数据
	 * @param requestUrl 请求地址
	 * @param tryCount 失败后，尝试获取的次数
	 * @param sleeptime 请求失败后的睡眠时间
	 * @param isRegexFilters 是否是正则过滤
	 * @param filters 过滤表达式
	 * @return
	 */
	public static String tryGetString(Request request, int tryCount, long sleeptime, boolean isRegexFilters, String... filters){
		String result = null;
		
		long defaultSleepTime = sleeptime == -1l ? 10 : sleeptime;
		int index = 0;
		while(index < tryCount && result == null){
			result = getString(request, isRegexFilters, filters);
			// 如果不为空，跳出
			if(result != null) break;
			try {
				Thread.sleep(defaultSleepTime);
			} catch (InterruptedException e) {
			}
			index++;
		}
		
		return result;
	}
	
	/**
	 * 获取网络请求数据
	 * @param requestUrl
	 * @return
	 */
	public static String getString(String requestUrl){
		return getString(requestUrl, false, EMPTY_ARRAY);
	}
	
	/**
	 * 获取网络请求数据
	 * @param requestUrl
	 * @return
	 */
	public static String getString(Request requestUrl){
		return getString(requestUrl, false, EMPTY_ARRAY);
	}
	
	/**
	 * 获取json数据
	 * @param requestUrl 请求地址
	 * @param tryCount 失败后，尝试获取的次数
	 * @param sleeptime 请求失败后的睡眠时间
	 * @param isRegexFilters 是否是正则过滤
	 * @param filters 过滤表达式
	 * @return
	 */
	public static JSONObject tryGetJson(String requestUrl, int tryCount, long sleeptime, boolean isRegexFilters, String... filters){
		JSONObject json = null;
		
		long defaultSleepTime = sleeptime == -1l ? 10 : sleeptime;
		int index = 0;
		while(index < tryCount && json == null){
			json = getJson(requestUrl, null, isRegexFilters, filters);
			// 如果不为空，跳出
			if(json != null) break;
			try {
				Thread.sleep(defaultSleepTime);
			} catch (InterruptedException e) {
			}
			index++;
		}
		
		return json;
	}
	
	/**
	 * 获取json数据
	 * @param requestUrl 请求地址
	 * @param tryCount 失败后，尝试获取的次数
	 * @param sleeptime 请求失败后的睡眠时间
	 * @param isRegexFilters 是否是正则过滤
	 * @param filters 过滤表达式
	 * @return
	 */
	public static JSONObject tryGetJson(Request request, int tryCount, long sleeptime, boolean isRegexFilters, String... filters){
		JSONObject json = null;
		
		long defaultSleepTime = sleeptime == -1l ? 10 : sleeptime;
		int index = 0;
		while(index < tryCount && json == null){
			json = getJson(request, null, isRegexFilters, filters);
			// 如果不为空，跳出
			if(json != null) break;
			try {
				Thread.sleep(defaultSleepTime);
			} catch (InterruptedException e) {
			}
			index++;
		}
		
		return json;
	}
	
	/**
	 * 获取json数据
	 * @param requestUrl 请求地址
	 * @param tryCount 失败后，尝试获取的次数
	 * @param sleeptime 请求失败后的睡眠时间
	 * @param charset 编码
	 * @param isRegexFilters 是否是正则过滤
	 * @param filters 过滤表达式
	 * @return
	 */
	public static JSONObject tryGetJson(String requestUrl, int tryCount, long sleeptime, String charset, boolean isRegexFilters, String... filters){
		JSONObject json = null;
		
		long defaultSleepTime = sleeptime == -1l ? 10 : sleeptime;
		int index = 0;
		while(index < tryCount && json == null){
			json = getJson(requestUrl, charset, isRegexFilters, filters);
			// 如果不为空，跳出
			if(json != null) break;
			try {
				Thread.sleep(defaultSleepTime);
			} catch (InterruptedException e) {
			}
			index++;
		}
		
		return json;
	}
	
	/**
	 *  获取json数据
	 * @param requestUrl 请求地址
	 * @param charset 编码
	 * @param isRegexFilters 是否是正则表达式过滤
	 * @param filters 过滤表达式
	 * @return
	 */
	public static JSONObject getJson(Request request, String charset, boolean isRegexFilters, String... filters){
		JSONObject json = null;
		BufferedReader br = null;
		try {
			URL url = new URL(request.getUrl());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 2min读取超时
			conn.setReadTimeout(1000 * 60 * 2);
			// 30秒连接超时
			conn.setConnectTimeout(1000 * 30);
			
			if(!ToolsString.isEmpty(request.getReferer())){
				conn.setRequestProperty("Referer", request.getReferer());
			}
			if(!ToolsString.isEmpty(request.getDomain())){
				conn.setRequestProperty("Host", request.getDomain());
			}
			if(!ToolsString.isEmpty(request.getUserAgent())){
				conn.setRequestProperty("User-Agent", request.getUserAgent());
			}
			if(!ToolsString.isEmpty(request.getxRequestWith())){
				conn.setRequestProperty("X-Requested-With", request.getxRequestWith());
			}
			if(!ToolsString.isEmpty(request.getMethod())){
				conn.setRequestMethod(request.getMethod());
			}
			if(!ToolsString.isEmpty(request.getAccept())){
				conn.setRequestProperty("Accept", request.getAccept());
			}
			if(!ToolsString.isEmpty(request.getCookie())){
				conn.setRequestProperty("Cookie", request.getCookie());
			}
			conn.connect();
			
			if(conn.getResponseCode() > 300){
				return null;
			}
			
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), ToolsString.isEmpty(charset) ? "utf-8" : charset));
			
			String line = null;
			while((line = br.readLine()) != null){
				if(filters != null){
					for(String filter : filters){
						if(isRegexFilters) {
							line = line.replaceAll(filter, "");
						}else{
							line = line.replace(filter, "");
						}
					}
				}
				json = JSONObject.parseObject(line.trim());
			}
			conn.disconnect();
		} catch (Exception e) {
			json = null;
			logger.error("request error. requestUrl = [" + request.getUrl() + "], ERROR:" + e);
		} finally {
			ToolsIO.closeStream(br);// 关流
		}
		return json;
	}
	
	/**
	 *  获取json数据
	 * @param requestUrl 请求地址
	 * @param charset 编码
	 * @param isRegexFilters 是否是正则表达式过滤
	 * @param filters 过滤表达式
	 * @return
	 */
	public static JSONObject getJson(String requestUrl, String charset, boolean isRegexFilters, String... filters){
		return getJson(new Request(requestUrl), charset, isRegexFilters, filters);
	}
	
	/**
	 * 获取json数据
	 * @param requestUrl
	 * @return
	 */
	public static JSONObject getJson(String requestUrl){
		return getJson(requestUrl, null, false, EMPTY_ARRAY);
	}
	
	/**
	 * 获取json数据
	 * @param requestUrl
	 * @return
	 */
	public static JSONObject getJson(Request request){
		return getJson(request, null, false, EMPTY_ARRAY);
	}
	
	/**
	 * post提交数据
	 * @param requestUrl
	 * @param header
	 * @param postBody
	 * @param isRegexFilters
	 * @param filters
	 * @return
	 */
	public static String httpPost(String requestUrl, Map<String, String> header, String postBody, boolean isRegexFilters, String... filters){
		StringBuilder result = new StringBuilder();
		BufferedReader br = null;
		DataOutputStream out = null;
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 10秒超时
			conn.setReadTimeout(30000);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			
			if(header != null){
				for(String key : header.keySet()){
					conn.setRequestProperty(key, header.get(key));
				}
			}
			conn.connect();
			
			out = new DataOutputStream(conn.getOutputStream()); 
			out.write(postBody.getBytes("utf-8")); 
			out.flush(); 
			
			InputStream is = null;
			if(conn.getResponseCode() >= 400){
				is = conn.getErrorStream();
			}else{
				is = conn.getInputStream();
			}
			
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			
			String line = null;
			while((line = br.readLine()) != null){
				if(filters != null){
					for(String filter : filters){
						if(isRegexFilters) {
							line = line.replaceAll(filter, "");
						}else{
							line = line.replace(filter, "");
						}
					}
				}
				result.append(line).append("\r\n");
			}
			
			conn.disconnect();
		} catch (Exception e) {
			logger.error("request error. requestUrl = [" + requestUrl + "], ERROR:", e);
		} finally {
			ToolsIO.closeStream(out, br);// 关流
		}
		return result.toString();
	}
	
	/**
	 * post提交数据
	 * @param requestUrl
	 * @param header
	 * @param postBody
	 * @param isRegexFilters
	 * @param filters
	 * @return
	 */
	public static String httpPost(String requestUrl, Map<String, String> header, byte[] postBody, boolean isRegexFilters, String... filters){
		StringBuilder result = new StringBuilder();
		BufferedReader br = null;
		DataOutputStream out = null;
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 10秒超时
			conn.setReadTimeout(30000);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			
			if(header != null){
				for(String key : header.keySet()){
					conn.setRequestProperty(key, header.get(key));
				}
			}
			conn.connect();
			
			out = new DataOutputStream(conn.getOutputStream()); 
			out.write(postBody);
			out.flush(); 
			
			InputStream is = null;
			if(conn.getResponseCode() >= 400){
				is = conn.getErrorStream();
			}else{
				is = conn.getInputStream();
			}
			
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			
			String line = null;
			while((line = br.readLine()) != null){
				if(filters != null){
					for(String filter : filters){
						if(isRegexFilters) {
							line = line.replaceAll(filter, "");
						}else{
							line = line.replace(filter, "");
						}
					}
				}
				result.append(line).append("\r\n");
			}
			
			conn.disconnect();
		} catch (Exception e) {
			logger.error("request error. requestUrl = [" + requestUrl + "], ERROR:", e);
		} finally {
			ToolsIO.closeStream(out, br);// 关流
		}
		return result.toString();
	}
	
	/**
	 * post提交数据
	 * @param requestUrl
	 * @param header
	 * @param postBody
	 * @return
	 */
	public static String httpPost(String requestUrl, Map<String, String> header, String postBody){
		return httpPost(requestUrl, header, postBody, false);
	}
	
	/**
	 * post提交数据
	 * @param requestUrl
	 * @param header
	 * @param postBody
	 * @return
	 */
	public static String httpPost(String requestUrl, Map<String, String> header, byte[] postBody){
		return httpPost(requestUrl, header, postBody, false);
	}
	
	/**
	 * 
	 * @param target 目标地址
	 * @param needContent 是否需要获得url内容
	 * @param encoding 编码
	 * @return
	 */
	public static Map<String, String> requestURL(String target, boolean needContent, String encoding){
		Map<String, String> result = new HashMap<String, String>();
		try {
			URL url = new URL(target);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 30秒读取超时
			conn.setReadTimeout(1000 * 30);
			// 30秒连接超时
			conn.setConnectTimeout(1000 * 30);
			
			int code = conn.getResponseCode();
			String msg = conn.getResponseMessage();
			result.put("code", code+"");
			result.put("msg", msg);
			StringBuilder builder = null;
			if(code == 200 && needContent){
				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
				String line = null;
				builder = new StringBuilder();
				while((line = reader.readLine()) != null){
					builder.append(line);
				}
				reader.close();
			}
			
			if(null != builder && builder.length()>0){
				result.put("content", builder.toString());
			}
			conn.disconnect();
		} catch (Exception e) {
			logger.error(" === >>> request url = ["+target+"] error = ["+e.toString()+"]");
		}
		return result;
	}
	
	
	public static Document getDom(String requestUrl) throws DocumentException{
		return DocumentHelper.parseText(getString(requestUrl));
	}
	
	/**
	 * 获取URL中，请求头的Cookie
	 * @param urlAddress
	 * @return
	 */
	public static List<String> getHeadCookies(String urlAddress){
		List<String> cookies = new ArrayList<String>();
		try{
			CookieManager manager=new CookieManager();
			manager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
			CookieHandler.setDefault(manager);
			URL	url=new URL(urlAddress);
			HttpURLConnection conn= (HttpURLConnection) url.openConnection();
			conn.getHeaderFields();
			CookieStore store = manager.getCookieStore();
			List<HttpCookie> lCookies=store.getCookies();
			for(HttpCookie cookie: lCookies){
				cookies.add(cookie.toString());
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return cookies;
	}
}
