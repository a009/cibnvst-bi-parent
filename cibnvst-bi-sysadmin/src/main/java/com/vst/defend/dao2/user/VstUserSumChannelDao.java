package com.vst.defend.dao2.user;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author lhp
 * @date 2018-1-4 下午03:20:28
 * @version
 */
@Repository
public interface VstUserSumChannelDao {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExport(Map<String,Object> params);
	
	/**
	 * 按渠道统计
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryReportByChannel(Map<String,Object> params);
}
