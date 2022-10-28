package com.vst.core.service;

import java.io.Serializable;

/**
 * @author joslyn
 * @date 2017年11月24日 下午12:07:53
 * @description
 * @version 1.0
 */
public interface OthersMgrService extends Serializable{

	/**
	 * 初始化缓存
	 */
	void initCache();
	
	/**
	 * 刷新缓存
	 */
	void flushCache();
}
