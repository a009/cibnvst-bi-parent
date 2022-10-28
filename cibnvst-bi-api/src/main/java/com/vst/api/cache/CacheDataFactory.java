package com.vst.api.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.json.simple.JSONObject;

/**
 * @author weiwei(joslyn)
 * @date 2017-9-21 下午03:18:26
 * @decription
 * @version 
 */
public class CacheDataFactory {

	/**
	 * 单例模式
	 */
	private static final CacheDataFactory _cache = new CacheDataFactory();
	
	/**
	 * 所有的数据队列
	 */
	private ConcurrentLinkedQueue<JSONObject> _allData = new ConcurrentLinkedQueue<JSONObject>();
	
	/**
	 * 构造器私有化
	 */
	private CacheDataFactory(){
	}
	
	/**
	 * 获取单例对象
	 * @return
	 */
	public static CacheDataFactory getInstance(){
		return _cache;
	}
	
	/**
	 * 添加任务
	 * @param json
	 */
	public void addData(JSONObject data){
		_allData.add(data);
	}
	
	/**
	 * 获取队列中的任务
	 * @return
	 */
	public JSONObject getData(){
		return _allData.poll();
	}
	
	/**
	 * 获取队列中的任务
	 * @return
	 */
	public List<JSONObject> getDatas(int count){
		if(count > 0 && !_allData.isEmpty()){
			List<JSONObject> result = new ArrayList<JSONObject>();
			while(count > 0){
				count--;
				JSONObject json = _allData.poll();
				if(json != null){
					result.add(json);
				}else{
					break;
				}
			}
			return result;
		}
		return null;
	}
	
	/**
	 * 获取集合大小
	 * @return
	 */
	public int size(){
		return _allData.size();
	}
}
