package com.vst.defend.dao2.realtime;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author lhp
 * @date 2018-3-16 上午10:12:53
 * @version
 */
@Repository
public interface VstRealMovieClassifyPlayDao {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExport(Map<String,Object> params);
	
	/**
	 * 实时统计
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryReportLine(Map<String,Object> params);
}
