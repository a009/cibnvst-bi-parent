package com.vst.defend.dao2.topic;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author lhp
 * @date 2017-11-28 下午07:26:51
 * @version
 */
@Repository
public interface VstTopicClickDao {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExport(Map<String,Object> params);
	
	/**
	 * 查询专题点击汇总排行
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryTopList(Map<String,Object> params);
	
	/**
	 * 查询专题点击汇总记录数
	 * @param params
	 * @return
	 */
	int queryTopCount(Map<String,Object> params);
	
	/**
	 * 导出专题点击汇总数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryTopExport(Map<String,Object> params);
}
