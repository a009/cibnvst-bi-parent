package com.vst.defend.dao2.movie;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author lhp
 * @date 2017-11-27 下午12:28:28
 * @version
 */
@Repository
public interface VstMoviePlayDao {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExport(Map<String,Object> params);
	
	/**
	 * 导出总量
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExportSum(Map<String,Object> params);
	
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
	 * 获取列表
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryList(Map<String,Object> params);
	
	/**
	 * 查询记录数
	 * @param params
	 * @return
	 */
	int queryCount(Map<String,Object> params);
	
	/**
	 * 查询影片播放汇总排行
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryTopList(Map<String,Object> params);
	
	/**
	 * 查询影片播放汇总记录数
	 * @param params
	 * @return
	 */
	int queryTopCount(Map<String,Object> params);
	
	/**
	 * 导出影片播放汇总数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryTopExport(Map<String,Object> params);
}
