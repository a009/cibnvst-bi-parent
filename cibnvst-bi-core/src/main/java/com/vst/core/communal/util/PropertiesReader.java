package com.vst.core.communal.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Properties;

import com.vst.common.tools.io.ToolsIO;
import com.vst.common.tools.number.ToolsNumber;


/**
 * @author weiwei(joslyn)
 * @date 2017-10-16 下午05:47:24
 * @decription
 * @version 
 */
public class PropertiesReader {
	
	/**
	 * 单例类
	 */
	private final static PropertiesReader _props = new PropertiesReader();

	/**
	 * 文件属性
	 */
	private Properties prop;
	
	/**
	 * 是否是本地文件配置模式，true：是，false：否
	 */
	private boolean localMode = false;
	
	/**
	 * 构造器私有化
	 */
	private PropertiesReader(){
		
	}
	
	/**
	 * 获取单例类
	 * @return
	 */
	public static PropertiesReader getInstance(){
		return _props;
	}
	
	/**
	 * 初始化方法
	 */
	public Properties loadConfig(){
		FileInputStream fis = null;
		try {
			InputStream is = this.getClass().getResourceAsStream(localMode ? "/local.properties" : "/config.properties");
			prop = new Properties();
			prop.load(is);
			prop.put("serverHostName", InetAddress.getLocalHost().getHostName());
		} catch (Exception e) {
		} finally {
			// 关闭流
			ToolsIO.closeStream(fis);
		}
		return prop;
	}

	public String getProperty(String key) {
		return prop.getProperty(key);
	}
	
	public int getPropertyInt(String key) {
		return ToolsNumber.parseInt(prop.getProperty(key));
	}

	public String printInfo() {
		return prop.toString();
	}
	
}
