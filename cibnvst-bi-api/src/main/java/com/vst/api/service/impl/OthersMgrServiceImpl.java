package com.vst.api.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vst.api.cache.CacheSysConfig;
import com.vst.api.dao.VstOptionsDao;
import com.vst.api.service.OthersMgrService;

/**
 * @author weiwei(joslyn)
 * @date 2017-9-15 下午03:21:24
 * @decription
 * @version
 */
@Service
public class OthersMgrServiceImpl implements OthersMgrService {

	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(OthersMgrServiceImpl.class);
	
	/**
	 * 操作接口
	 */
	@Autowired
	private VstOptionsDao _vstOptionsDao;

	/**
	 * 初始化缓存
	 */
	@Override
	public void initCache() {
		try {
			// ============================================================================================
			// 加载系统配置
			List<Map<String, Object>> sysConfig = _vstOptionsDao.query(null);
			CacheSysConfig.getInstance().loadSysConfig(sysConfig);
		} catch (Exception e) {
			logger.error("initCache error. ERROR:", e);
		}
	}
	
	/**
	 * 更新缓存
	 */
	@Override
	public void flushCache() {
		try {
			// ============================================================================================
			// 刷新系统配置
			List<Map<String, Object>> sysConfig = _vstOptionsDao.query(null);
			CacheSysConfig.getInstance().reloadSysConfig(sysConfig);
		} catch (Exception e) {
			logger.error("flushCache error. ERROR:", e);
		}
		
	}

}
