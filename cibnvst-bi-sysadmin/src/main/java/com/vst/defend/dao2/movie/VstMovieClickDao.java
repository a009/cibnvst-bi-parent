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
public interface VstMovieClickDao {
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
	 * 查询影片点击汇总排行
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryTopList(Map<String,Object> params);
	
	/**
	 * 查询影片点击汇总记录数
	 * @param params
	 * @return
	 */
	int queryTopCount(Map<String,Object> params);
	
	/**
	 * 导出影片点击汇总数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryTopExport(Map<String,Object> params);
}
