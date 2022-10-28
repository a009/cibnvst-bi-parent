package com.vst.api.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vst.api.cache.CacheTopicConfig;
import com.vst.api.dao.VstSysTopicDao;
import com.vst.api.service.TopicMgrService;

/**
 * @author weiwei(joslyn)
 * @date 2017-9-21 下午12:17:53
 * @decription
 * @version 
 */
@Service
public class TopicMgrServiceImpl implements TopicMgrService {
	
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(TopicMgrServiceImpl.class);

	/**
	 * 系统topic配置dao层
	 */
	@Autowired
	private VstSysTopicDao _vstSysTopicDao;
	
	/**
	 * 初始化缓存
	 */
	@Override
	public void initCache() {
		try {
			// ============================================================================================
			// 加载系统配置
			List<Map<String, Object>> list = _vstSysTopicDao.query(null);
			CacheTopicConfig.getInstance().initCache(list);
		} catch (Exception e) {
			logger.error("initCache error. ERROR:", e);
		}
	}
	
	/**
	 * 刷新缓存
	 */
	@Override
	public void flushCache() {
		try {
			// ============================================================================================
			// 加载系统配置
			List<Map<String, Object>> list = _vstSysTopicDao.query(null);
			CacheTopicConfig.getInstance().flushCache(list);
		} catch (Exception e) {
			logger.error("flushCache error. ERROR:", e);
		}
	}
}
