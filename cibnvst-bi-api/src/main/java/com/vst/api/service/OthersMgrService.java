package com.vst.api.service;

import org.springframework.stereotype.Service;

/**
 * @author weiwei(joslyn)
 * @date 2017-9-15 下午03:20:51
 * @decription
 * @version 
 */
@Service
public interface OthersMgrService {

	/**
	 * 初始化缓存
	 */
	void initCache();
	
	/**
	 * 更新缓存
	 */
	void flushCache();
	
}
