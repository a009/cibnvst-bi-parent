package com.vst.defend.communal.dao;

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
public interface BaseDao<T>{
	
	/**
	 * 查询分页数据
	 * @param params
	 * @return 返回list集合
	 */
	List<Map<String,Object>> queryForList(Map<String,Object> params);
	
	/**
	 * 查询记录数
	 * @param params
	 * @return 返回int
	 */
	int queryCount(Map<String,Object> params);
	
	/**
	 * 查询Bean对象集合
	 * @param params
	 * @return 返回list集合
	 */
	List<T> queryForListAsBean(Map<String,Object> params);
	
	/**
	 * 根据条件查询某条记录
	 * @param param
	 * @return 返回bean数据
	 */
	T queryForObject(Map<String, Object> param);
	
	/**
	 * 根据ID查询数据
	 * @param id
	 * @return 返回Map
	 */
	Map<String,Object> queryById(String id);
	
	/**
	 * 查询指定字段信息
	 * @param params
	 * @return 返回list集合
	 */
	List<Map<String,Object>> queryForMap(Map<String, Object> params);
	
	/**
	 * 根据条件查询一个map
	 * @param params
	 * @return
	 */
	Map<String,Object> queryForMap2(Map<String,Object> params);
	
	/**
	 * 插入数据
	 * @param bean
	 * @return 返回int
	 */
	int insert(T bean);
	
	/**
	 * 批量插入数据
	 * @param bean
	 * @return 返回int
	 */
	int batchInsert(List<T> list);
	
	/**
	 * 修改数据
	 * @param bean
	 * @return 返回int
	 */
	int update(T bean);
	
	/**
	 * 批量修改数据
	 * @param list
	 * @return 返回int
	 */
	int batchUpdate(List<T> list);
	
	/**
	 * 修改状态
	 * @param params
	 * @return 返回int
	 */
	int modifyState(Map<String,Object> params);
	
	/**
	 * 删除数据
	 * @param params
	 * @return 返回int
	 */
	int delete(Map<String,Object> params);
	
	/**
	 * 批量删除数据
	 * @param params
	 * @return 返回int
	 */
	int batchDelete(List<T> list);

	/**
	 * 批量修改
	 * @param ids
	 * @param state
	 * @return
	 */
	int batchUpdate(Map<String, Object> param);
}