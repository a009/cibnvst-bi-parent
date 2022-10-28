package com.vst.defend.dao2.topic;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author lhp
 * @date 2017-12-18 上午10:11:24
 * @version
 */
@Repository
public interface VstTopicPlayDao {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExport(Map<String,Object> params);
	
	/**
	 * 查询专题播放汇总排行
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryTopList(Map<String,Object> params);
	
	/**
	 * 查询专题播放汇总记录数
	 * @param params
	 * @return
	 */
	int queryTopCount(Map<String,Object> params);
	
	/**
	 * 导出专题播放汇总数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryTopExport(Map<String,Object> params);
}
