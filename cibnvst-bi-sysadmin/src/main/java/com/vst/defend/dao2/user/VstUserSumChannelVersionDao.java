package com.vst.defend.dao2.user;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author lhp
 * @date 2018-1-4 下午03:21:09
 * @version
 */
@Repository
public interface VstUserSumChannelVersionDao {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExport(Map<String,Object> params);
	
	/**
	 * 按版本统计
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryReportByVersion(Map<String,Object> params);
}
