package com.vst.core.cache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;

import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.core.communal.bean.Video;
import com.vst.core.communal.bean.VideoTopic;

/**
 * @author weiwei(joslyn)
 * @date 2017-12-6 上午11:08:34
 * @decription
 * @version 
 */
public class CacheType implements Serializable{
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 5292828650149324552L;

	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(CacheType.class);

	/**
	 * 单例类
	 */
	private static final CacheType _cache = new CacheType();

	/**
	 * 缓存所有的影片信息
	 */
	private Map<String, Video> _cacheVideos;
	
	/**
	 * 缓存所有的topic信息
	 */
	private Map<String, VideoTopic> _cahceTopics;
	
	/**
	 * 构造器私有化
	 */
	private CacheType(){
		_cacheVideos = new HashMap<String, Video>();
		_cahceTopics = new HashMap<String, VideoTopic>();
	}
	
	/**
	 * 获得单例对象
	 * @return
	 */
	public static CacheType getInstance(){
		return _cache;
	}
	
	/**
	 * 初始化缓存
	 * @param videoList
	 * @param topicList
	 */
	public void initCache(List<JSONObject> videoList, List<JSONObject> topicList){
		if(videoList == null || videoList.isEmpty()){
			logger.info("===>>> videoList is null.");
		}else{
			wrapVideoData(videoList, _cacheVideos);
		}
		
		if(topicList == null || topicList.isEmpty()){
			logger.info("===>>> topicList is null.");
		}else{
			wrapTopicData(topicList, _cahceTopics);
		}
	}
	
	private void wrapVideoData(List<JSONObject> videoList, Map<String, Video> cacheVideos){
		for(JSONObject json : videoList){
			String uuid = ToolsString.checkEmpty(json.get("uuid"));
			Video video = new Video();
			video.setUuid(uuid);
			video.setCid(ToolsNumber.parseInt(String.valueOf(json.get("cid"))));
			video.setSpecialType(ToolsNumber.parseInt(String.valueOf(json.get("specialType"))));
			video.setTitle(ToolsString.checkEmpty(json.get("title")));
			video.setCat(ToolsString.checkEmpty(json.get("cat")));
			video.setActor(ToolsString.checkEmpty(json.get("actor")));
			video.setArea(ToolsString.checkEmpty(json.get("area")));
			video.setDirector(ToolsString.checkEmpty(json.get("director")));
			video.setMark(ToolsString.checkEmpty(json.get("mark")));
			video.setYear(ToolsNumber.parseInt(String.valueOf(json.get("year"))));
			cacheVideos.put(uuid, video);
		}
	}
	
	private void wrapTopicData(List<JSONObject> topicList, Map<String, VideoTopic> cahceTopics){
		for(JSONObject json : topicList){
			String topicId = ToolsString.checkEmpty(json.get("topicId"));
			VideoTopic topic = new VideoTopic();
			topic.setTopicId(topicId);
			topic.setCid(ToolsNumber.parseInt(String.valueOf(json.get("cid"))));
			topic.setSpecialType(ToolsNumber.parseInt(String.valueOf(json.get("specialType"))));
			topic.setTitle(ToolsString.checkEmpty(json.get("title")));
			topic.setTemplateType(ToolsNumber.parseInt(String.valueOf(json.get("templateType"))));
			cahceTopics.put(topicId, topic);
		}
	}
	
	public Video getVideo(String uuid){
		return _cacheVideos.get(uuid);
	}
	
	public VideoTopic getVideoTopic(String uuid){
		return _cahceTopics.get(uuid);
	}
	
	/**
	 * 刷新缓存
	 * @param videoList
	 * @param topicList
	 */
	public void flushCache(List<JSONObject> videoList, List<JSONObject> topicList){
		Map<String, Video> cacheVideos = new HashMap<String, Video>();
		wrapVideoData(videoList, cacheVideos);
		this._cacheVideos = cacheVideos;
		Map<String, VideoTopic> cahceTopics = new HashMap<String, VideoTopic>();
		wrapTopicData(topicList, cahceTopics);
		this._cahceTopics = cahceTopics;
		
	}
}
