package com.vst.defend.dao2.movie;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author lhp
 * @date 2018-1-29 下午06:39:46
 * @version
 */
@Repository
public interface VstMovieSearchDao {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExport(Map<String,Object> params);
	
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
	 * 查询影片搜索汇总排行
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryTopList(Map<String,Object> params);
	
	/**
	 * 查询影片搜索汇总记录数
	 * @param params
	 * @return
	 */
	int queryTopCount(Map<String,Object> params);
	
	/**
	 * 导出影片搜索汇总数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryTopExport(Map<String,Object> params);
}
