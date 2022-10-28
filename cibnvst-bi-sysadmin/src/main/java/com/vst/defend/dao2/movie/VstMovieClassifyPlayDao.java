package com.vst.defend.dao2.movie;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author lhp
 * @date 2017-12-1 下午03:10:08
 * @version
 */
@Repository
public interface VstMovieClassifyPlayDao {
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
	 * 按分类统计
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryReportByCid(Map<String,Object> params);
}
