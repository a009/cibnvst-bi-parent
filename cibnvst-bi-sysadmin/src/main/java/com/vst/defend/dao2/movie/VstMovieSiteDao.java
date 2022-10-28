package com.vst.defend.dao2.movie;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author lhp
 * @date 2017-11-29 下午07:09:55
 * @version
 */
@Repository
public interface VstMovieSiteDao {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExport(Map<String,Object> params);
	
	/**
	 * 获取列表数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryForList(Map<String,Object> params);
	
	/**
	 * 获取首页数据(有缓存)
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryHomeList(Map<String,Object> params);
	
	/**
	 * 按平台统计
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryReportBySite(Map<String,Object> params);
}
