package com.vst.defend.dao2.movie;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author lhp
 * @date 2018-7-12 下午03:23:12
 * @version
 */
@Repository
public interface VstMoviePlayRateDao {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExport(Map<String,Object> params);
	
	/**
	 * 按分类统计
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryReportByCid(Map<String,Object> params);
}
