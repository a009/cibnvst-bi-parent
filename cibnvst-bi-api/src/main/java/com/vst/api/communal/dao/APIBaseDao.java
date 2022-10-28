package com.vst.api.communal.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * @author weiwei(joslyn)
 * @date 2017-4-27 下午05:32:32
 * @decription
 * @version 
 */
@Repository
public interface APIBaseDao{

	/**
	 * 根据查询条件查询数据
	 * @param param 查询条件map
	 * @return
	 */
	List<Map<String, Object>> query(Map<String, Object> param);
	
	/**
	 * 根据条件查询条数
	 * @param param 查询条件map
	 * @return
	 */
	int queryCount(Map<String, Object> param);
}
