package com.vst.defend.dao2.user;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author lhp
 * @date 2018-2-28 上午09:53:56
 * @version
 */
@Repository
public interface VstUserPlayDao {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExport(Map<String,Object> params);
	
	/**
	 * 根据类型统计
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryReportByType(Map<String,Object> params);
}
