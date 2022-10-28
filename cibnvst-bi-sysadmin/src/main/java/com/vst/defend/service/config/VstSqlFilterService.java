package com.vst.defend.service.config;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;

/**
 * 
 * @author lhp
 * @date 2017-11-30 下午05:33:54
 * @version
 */
@Service
public interface VstSqlFilterService {
	/**
	 * 查询sql筛选配置列表
	 * @param cutPage
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	CutPage getSqlFilterList(CutPage cutPage, UserSession user) throws SystemException;
	
	/**
	 * 新增sql筛选配置
	 * @param param
	 * @param user
	 * @throws SystemException
	 */
	void addSqlFilter(Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 根据ID，获取sql筛选配置信息
	 * @param id
	 * @return
	 * @throws SystemException
	 */
	Map<String, Object> getSqlFilterById(String id) throws SystemException;
	
	/**
	 * 修改sql筛选配置
	 * @param oldParam
	 * @param param
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int updateSqlFilter(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 删除sql筛选配置
	 * @param ids
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int deleteSqlFilter(String ids, UserSession user) throws SystemException;
	
	/**
	 * 修改sql筛选配置状态
	 * @param ids
	 * @param state
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int modifySqlFilterState(String ids, int state, UserSession user) throws SystemException;
	
	/**
	 * 修改sql筛选配置排序
	 * @param ids
	 * @param indexs
	 * @param user
	 * @return
	 */
	void modifySqlFilterIndexs(String ids, String indexs, UserSession user) throws SystemException;
	
	/**
	 * 获取键值对
	 * @param params
	 * @param key
	 * @param value
	 * @return
	 * @throws SystemException
	 */
	Map<String, Object> queryForMap(Map<String, Object> params, String key, String value) throws SystemException;
	
	/**
	 * 批量添加sql筛选配置
	 * @param params
	 * @param user
	 * @throws SystemException
	 */
	int batchAddSqlFilter(Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 复制新增sql筛选配置
	 * @param param
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int copyAddSqlFilter(Map<String, Object> param, UserSession user) throws SystemException;
}
