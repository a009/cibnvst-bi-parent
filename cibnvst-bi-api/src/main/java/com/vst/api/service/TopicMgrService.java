package com.vst.api.service;

import org.springframework.stereotype.Service;

/**
 * @author weiwei(joslyn)
 * @date 2017-9-20 上午11:11:08
 * @decription
 * @version 
 */
@Service
public interface TopicMgrService {

	/**
	 * 初始化缓存
	 */
	void initCache();
	
	/**
	 * 刷新缓存
	 */
	void flushCache();
	
}
