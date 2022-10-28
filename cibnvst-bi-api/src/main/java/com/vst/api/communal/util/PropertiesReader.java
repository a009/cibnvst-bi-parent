package com.vst.api.communal.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

import com.vst.common.tools.io.ToolsIO;

/**
 * @author weiwei
 * @date 2014-7-3 上午09:49:41
 * @description
 * @version
 */
public class PropertiesReader {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(PropertiesReader.class);
	
	/**
	 * 文件属性
	 */
	private static Properties prop;

	/**
	 * 资源获取接口
	 */
	private Resource resource;
	
	/**
	 * 初始化方法
	 * 
	 * @throws IOException
	 */
	public void initial() throws IOException {
		prop = new Properties();
		prop.load(this.resource.getInputStream());
		
		// 启动文件刷新线程，进行定时刷新文件配置信息
		FlushThread flush = new FlushThread();
		flush.start();
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	} 

	public static String getProperty(String key) {
		return prop.getProperty(key);
	}
	
	public static void setProperty(String key, String value){
		prop.setProperty(key, value);
	}
	
	/**
	 * 更新配置线程
	 * @author weiwei
	 * @date 2014-9-5 下午02:29:28
	 * @description 
	 * @version	
	 */
	private class FlushThread extends Thread{
		
		@Override
		public void run(){
			try {
				Thread.sleep(3 * 60 * 1000);
			} catch (InterruptedException e1) {
				logger.error("Thread sleep error. ERROR:" + e1.getMessage());
			}
			
			while(true){
				FileInputStream fis = null;
				try {
					String path = Thread.currentThread().getContextClassLoader().getResource("config.properties").getPath();
					File file = new File(path);
					
					if(file.exists()){
						fis = new FileInputStream(file);
						Properties p = new Properties();
						p.load(fis);
						prop.putAll(p);
					}
				} catch (Exception e) {
					logger.error("Update config.properties thread error. ERROR:" + e.getMessage());
				} finally {
					// 关闭流
					ToolsIO.closeStream(fis);
					
					try {
						// 每两分钟读取一次
						Thread.sleep(2 * 60 * 1000);
					} catch (InterruptedException e) {
						logger.error("Thread sleep error. ERROR:" + e.getMessage());
					}
				}
			}
		}
		
	}
}
