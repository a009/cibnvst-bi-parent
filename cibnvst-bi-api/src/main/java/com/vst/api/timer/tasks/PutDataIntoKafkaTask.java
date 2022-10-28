package com.vst.api.timer.tasks;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.json.simple.JSONObject;

import com.vst.api.communal.util.KafkaUtils;
import com.vst.api.communal.util.PropertiesReader;
import com.vst.common.tools.file.ToolsFile;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;

/**
 * @author joslyn
 * @date 2018年3月5日 下午2:14:15
 * @description
 * @version 1.0
 */
public class PutDataIntoKafkaTask implements Runnable{

	@Override
	public void run() {
		while(true){
			try {
				String dataFilePathDir = PropertiesReader.getProperty("data_path");
				String failureFilePathDir = PropertiesReader.getProperty("failure_file");
				// 首先处理曾经提交失败的数据
				processFile(failureFilePathDir, failureFilePathDir);
				// 其次处理未提交的数据
				processFile(dataFilePathDir, failureFilePathDir);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		}
	}
	
	private void processFile(String dataFilePathDir, String failureFilePathDir){
		File dataFileDir = new File(dataFilePathDir);
		if(dataFileDir.exists() && dataFileDir.isDirectory()){
			File[] files = dataFileDir.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".ready");
				}
			});
			// 按照文件时间排序，时间越小，越靠前
			Arrays.sort(files, new Comparator<File>() {
				@Override
				public int compare(File o1, File o2) {
					long index1 = ToolsNumber.parseLong(o1.getName().replace(".ready", ""));
					long index2 = ToolsNumber.parseLong(o2.getName().replace(".ready", ""));
					if(index1 > index2){
						return 1;
					}else if(index1 < index2){
						return -1;
					}else{
						return 0;
					}
				}
			});
			
			for(File file : files){
				if(file.exists() && file.getName().endsWith(".ready")){
					List<JSONObject> list = ToolsFile.readFileToJson(file, "utf-8");
					if(list != null && !list.isEmpty()){
						StringBuilder sb = null;
						int failureCount = 0;
						for(JSONObject json : list){
							try {
								if(json != null){
									// 筛选不规范数据
									String kafkaTopic = ToolsString.checkEmpty(json.get("kafkaTopic"));
							        KafkaUtils.sendMessage(kafkaTopic, json.toString());
								}
							} catch (Exception e) {
								failureCount++;
								if(sb == null) sb = new StringBuilder();
								sb.append(json).append("\r\n");// 找出上传失败的数据，存放到失败文件中，做下次再次上传处理
							}
						}
						
						// 如果全部都失败了，就没必要动文件类型，反之，只有部分数据提交失败或者都提交成功的时候，才需要改类型
						if(failureCount < list.size()){
							file.renameTo(new File(file.getAbsolutePath().replace(".ready", ".finished")));
							if(sb != null && sb.length() > 0){
								ToolsFile.writeBytesToFile(failureFilePathDir + "/" + System.currentTimeMillis() + ".ready", sb.toString().getBytes(), false);
							}
						}
					}
				}
			}
		}
	}
}
