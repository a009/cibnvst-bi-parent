package com.vst.defend.dao2.user;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author lhp
 * @date 2019-3-12 下午03:29:44
 * @version
 */
@Repository
public interface VstUserActiveVersionBdmodelDao {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExport(Map<String,Object> params);
	
	/**
	 * 按设备统计
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryReportByBdmodel(Map<String,Object> params);
}
