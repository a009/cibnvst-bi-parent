package com.vst.defend.service.other.impl;

import com.vst.defend.cache.CacheSysConfig;
import com.vst.defend.dao.report.VstAutoConditionDao;
import com.vst.defend.dao.report.VstAutoMainDao;
import com.vst.defend.dao.report.VstAutoOverlayDao;
import com.vst.defend.service.other.OthersMgrService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author weiwei(joslyn)
 * @date 2017-9-15 下午03:21:24
 * @decription
 * @version
 */
@Service
public class OthersMgrServiceImpl implements OthersMgrService {

//	/**
//	 * 操作接口
//	 */
//	@Resource
//	private VstOptionsDao _vstOptionsDao;
	
	/**
	 * 自动化(主表)Dao接口
	 */
	@Resource
	private VstAutoMainDao _vstAutoMainDao;
	
	/**
	 * 自动化(查询条件)Dao接口
	 */
	@Resource
	private VstAutoConditionDao _vstAutoConditionDao;
	
	/**
	 * 自动化(sql续加)Dao接口
	 */
	@Resource
	private VstAutoOverlayDao _vstAutoOverlayDao;

	/**
	 * 初始化缓存
	 */
	@Override
	public void initCache() {
		// ============================================================================================
		// 加载系统配置
//		List<Map<String, Object>> sysConfig = _vstOptionsDao.queryForList(null);
//		CacheSysConfig.getInstance().loadSysConfig(sysConfig);
		
		CacheSysConfig cache = CacheSysConfig.getInstance();
		cache.putDataMap("autoMain", _vstAutoMainDao.queryForList(null));
		cache.putDataMap("autoCondition", _vstAutoConditionDao.queryForList(null));
		cache.putDataMap("autoOverlay", _vstAutoOverlayDao.queryForList(null));
	}
	
	/**
	 * 更新缓存
	 */
	@Override
	public void flushCache() {
		// ============================================================================================
		// 刷新系统配置
//		List<Map<String, Object>> sysConfig = _vstOptionsDao.queryForList(null);
//		CacheSysConfig.getInstance().reloadSysConfig(sysConfig);
	}

}
