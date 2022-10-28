package com.vst.defend.dao2.user;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author lhp
 * @date 2018-1-4 下午07:33:33
 * @version
 */
@Repository
public interface VstUserActiveCityDao {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExport(Map<String,Object> params);
	
	/**
	 * 根据维度统计
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryReportByDim(Map<String,Object> params);
}
