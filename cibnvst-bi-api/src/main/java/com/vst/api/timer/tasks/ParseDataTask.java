package com.vst.api.timer.tasks;

import java.io.File;
import java.util.List;

import org.json.simple.JSONObject;

import com.vst.api.cache.CacheDataFactory;
import com.vst.api.cache.CacheTopicConfig;
import com.vst.api.communal.util.PropertiesReader;
import com.vst.common.tools.file.ToolsFile;
import com.vst.common.tools.string.ToolsString;

/**
 * @author weiwei
 * @date 2016-4-13 下午05:25:11
 * @description 
 * @version	
 */
public class ParseDataTask implements Runnable {
	
	/**
	 * 10M文件大小
	 */
	private static final long MAX_FILE_SIZE = 3*1024*1024L;
	
	@Override
	public void run() {
		// 启动的时候先把上次写的文件处理掉
		String dataFilePath = PropertiesReader.getProperty("data_path") + "/index.dat";
		File file = new File(dataFilePath);
		if(file.exists()){
			long now = System.currentTimeMillis();
			file.renameTo(new File(file.getAbsolutePath().replace("index.dat", now + ".ready")));
		}
		
		while(true){
			try {
				List<JSONObject> datas = CacheDataFactory.getInstance().getDatas(100);
				if(datas != null && !datas.isEmpty()){
					StringBuilder sb = new StringBuilder();
					for(JSONObject json : datas){
						// 筛选不规范数据
						String kafkaTopic = ToolsString.checkEmpty(json.get("kafkaTopic"));
						if(ToolsString.isEmpty(kafkaTopic)) continue;
						boolean isRight = CacheTopicConfig.getInstance().checkFormat(kafkaTopic, json);
						if(!isRight) continue;
						sb.append(json).append("\r\n");
					}
					// kafka报错的时候，优先写入本地文件
					dataFilePath = PropertiesReader.getProperty("data_path") + "/index.dat";
					file = new File(dataFilePath);
					if(file.exists()){
						long now = System.currentTimeMillis();
						if(file.length() >= MAX_FILE_SIZE || now - file.lastModified() >= 5000){
							file.renameTo(new File(file.getAbsolutePath().replace("index.dat", now + ".ready")));
						}
					}
					ToolsFile.writeBytesToFile(file, sb.toString().getBytes(), true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
				}
			}
		
//		while(true){
//			try {
//				JSONObject json = CacheDataFactory.getInstance().getData();
//				if(json != null){
//					// 筛选不规范数据
//					String kafkaTopic = ToolsString.checkEmpty(json.get("kafkaTopic"));
//					if(ToolsString.isEmpty(kafkaTopic)) continue;
//					boolean isRight = CacheTopicConfig.getInstance().checkFormat(kafkaTopic, json);
//					if(!isRight) continue;
//					try {
//						KafkaUtils.sendMessage(kafkaTopic, json.toString());
//					} catch (Exception e) {
//						// kafka报错的时候，优先写入本地文件
//						dataFilePath = PropertiesReader.getProperty("data_path") + "/index.dat";
//						file = new File(dataFilePath);
//						if(file.exists()){
//							long now = System.currentTimeMillis();
//							if(file.length() >= MAX_FILE_SIZE || now - file.lastModified() >= 20000){
//								file.renameTo(new File(file.getAbsolutePath().replace("index.dat", now + ".ready")));
//							}
//						}
//						ToolsFile.writeBytesToFile(file, json.toString().concat("\r\n").getBytes(), true);
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//				try {
//					Thread.sleep(1);
//				} catch (InterruptedException e) {
//				}
//			}
		}
	}
}
