package com.vst.defend.dao2.sql;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author lhp
 * @date 2017-9-19 下午05:53:04
 * @version
 */
@Repository
@Transactional
public interface VstSQLDao {

	/**
	 * 根据sql语句，查询结果
	 * @param sql
	 * @return
	 */
	List<Map<String,Object>> queryBySqlList(String sql);
	
	/**
	 * 查询记录数
	 * @param sql
	 * @return
	 */
	Integer queryBySqlCount(String sql);
	
	/**
	 * 查询分页数据
	 * @param param
	 * @return
	 */
	List<Map<String,Object>> querySqlList(Map<String, Object> param);
}
