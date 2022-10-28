package com.vst.defend.dao2.movie;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author lhp
 * @date 2018-1-23 下午04:44:09
 * @version
 */
@Repository
public interface VstMovieTotalPlayDao {
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
}
