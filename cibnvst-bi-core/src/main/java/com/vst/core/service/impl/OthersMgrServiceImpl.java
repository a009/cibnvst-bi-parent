package com.vst.core.service.impl;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;

import com.vst.common.tools.file.ToolsFile;
import com.vst.common.tools.http.ToolsHttp;
import com.vst.core.cache.CacheType;
import com.vst.core.communal.util.PropertiesReader;
import com.vst.core.service.OthersMgrService;

/**
 * @author joslyn
 * @date 2017年11月24日 下午12:08:17
 * @description
 * @version 1.0
 */
public class OthersMgrServiceImpl implements OthersMgrService, Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 7934081352874589016L;

	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(OthersMgrServiceImpl.class);


	@Override
	public void initCache() {
		String videoFilePath = PropertiesReader.getInstance().getProperty("video_data_local_path");
		String topicFilePath = PropertiesReader.getInstance().getProperty("topic_data_local_path");
		File videoFile = new File(videoFilePath);
		File topicFile = new File(topicFilePath);
		long now = System.currentTimeMillis();
		if(!videoFile.exists() || !topicFile.exists() 
				|| (now - videoFile.lastModified() >= 60*60*1000L) 
				|| (now - topicFile.lastModified() >= 60*60*1000L)){
			download();
		}
		
		if(videoFile.exists() && topicFile.exists()){
			List<JSONObject> videoList = ToolsFile.readFileToJson(videoFile, "utf-8");
			List<JSONObject> topicList = ToolsFile.readFileToJson(topicFile, "utf-8");
			CacheType.getInstance().initCache(videoList, topicList);
		}
	}
	
	private void download(){
		String url = PropertiesReader.getInstance().getProperty("sync_video_interface");
		String videoFilePath = PropertiesReader.getInstance().getProperty("video_data_local_path");
		logger.info("===>>> start downloading video file....");
		boolean flag = ToolsHttp.downloadGzipFile(url + "?filename=movie.dat", videoFilePath);
		logger.info("===>>> finished download video file, the result is " + (flag ? "success!" : "failure!"));
		String topicFilePath = PropertiesReader.getInstance().getProperty("topic_data_local_path");
		logger.info("===>>> start downloading topic file....");
		 flag = ToolsHttp.downloadGzipFile(url + "?filename=topic.dat", topicFilePath);
		logger.info("===>>> finished download topic file, the result is " + (flag ? "success!" : "failure!"));
	}
	
	@Override
	public void flushCache() {
		String videoFilePath = PropertiesReader.getInstance().getProperty("video_data_local_path");
		String topicFilePath = PropertiesReader.getInstance().getProperty("topic_data_local_path");
		File videoFile = new File(videoFilePath);
		File topicFile = new File(topicFilePath);
		if(!videoFile.exists() || !topicFile.exists()){
			download();
		}
		
		if(videoFile.exists() && topicFile.exists()){
			long now = System.currentTimeMillis();
			if((now - videoFile.lastModified() >= 60*60*1000L) || (now - topicFile.lastModified() >= 60*60*1000L)){
				download();// 重新下载
				if(videoFile.exists() && topicFile.exists()){
					List<JSONObject> videoList = ToolsFile.readFileToJson(videoFile, "utf-8");
					List<JSONObject> topicList = ToolsFile.readFileToJson(topicFile, "utf-8");
					CacheType.getInstance().flushCache(videoList, topicList);
				}
			}
		}
	}

	
	
	

}
