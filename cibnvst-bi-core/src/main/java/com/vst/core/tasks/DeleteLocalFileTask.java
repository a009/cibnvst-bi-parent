package com.vst.core.tasks;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vst.core.communal.util.PropertiesReader;

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
	 * 判断文件是否是很久前创建的文件夹的间隔时间标准，1小时
	 */
	private static final long INTERVAL_TIME = 60*60*1000L;
	
	/**
	 *  任务线程
	 */
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(10*60*1000L);// 10分钟执行一次
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			try {
				String localPath = PropertiesReader.getInstance().getProperty("hdfs_data_local_path");
				File dir = new File(localPath);
				long now = System.currentTimeMillis();
				delFiles(dir, now);
			} catch (Exception e) {
				logger.error("delete local file error. ERROR:", e);
			}
		}
	}
	
	private void delFiles(File dir, long now){
		if (dir != null && dir.exists() && dir.isDirectory()) {
			File[] list = dir.listFiles();
			if(list != null && list.length > 0){
				for (File file : list) {
					if (file.isDirectory()) {
						// 递归去删除
						delFiles(file, now);
					} else if(file.exists() && file.isFile()) {
						// 如果文件上传完毕，删除文件
						if(file.getName().endsWith(".finished")) file.delete();
					}
				}
			}else if(now - dir.lastModified() >= INTERVAL_TIME){
				// 删除文件夹
				dir.delete();
			}
		}
	}
}
