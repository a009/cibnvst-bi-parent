package com.vst.api.timer.tasks;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vst.api.communal.util.PropertiesReader;

/**
 * @author weiwei(joslyn)
 * @date 2017-11-14 下午08:49:58
 * @decription 删除本地已经上传的kafka日志文件
 * @version 
 */
public class DeleteLocalFileTask implements Runnable{
	
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(DeleteLocalFileTask.class);
	
	/**
	 *  任务线程
	 */
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(60*1000L);// 1分钟执行一次
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			try {
				String dataFilePathDir = PropertiesReader.getProperty("data_path");
				delFiles(dataFilePathDir);
				String failureFilePathDir = PropertiesReader.getProperty("failure_file");
				delFiles(failureFilePathDir);
			} catch (Exception e) {
				logger.error("delete local file error. ERROR:", e);
			}
		}
	}
	
	private void delFiles(String dataFilePathDir){
		File dataFileDir = new File(dataFilePathDir);
		if(dataFileDir.exists() && dataFileDir.isDirectory()){
			File[] files = dataFileDir.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".finished");
				}
			});
			for (File file : files) {
				if(file.exists() && file.isFile()) {
					// 如果文件上传完毕，删除文件
					if(file.getName().endsWith(".finished")) file.delete();
				}
			}
		}
	}
}
