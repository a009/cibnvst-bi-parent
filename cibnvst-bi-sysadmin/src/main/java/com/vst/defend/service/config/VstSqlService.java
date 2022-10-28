package com.vst.defend.service.config;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;

/**
 * 
 * @author lhp
 * @date 2017-11-20 下午03:39:45
 * @version
 */
@Service
public interface VstSqlService {
	/**
	 * 查询spark任务列表
	 * @param cutPage
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	CutPage getSqlList(CutPage cutPage, UserSession user) throws SystemException;
	
	/**
	 * 新增spark任务
	 * @param param
	 * @param user
	 * @throws SystemException
	 */
	void addSql(Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 根据ID，获取spark任务信息
	 * @param id
	 * @return
	 * @throws SystemException
	 */
	Map<String, Object> getSqlById(String id) throws SystemException;
	
	/**
	 * 修改spark任务
	 * @param oldParam
	 * @param param
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int updateSql(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 删除spark任务
	 * @param ids
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int deleteSql(String ids, UserSession user) throws SystemException;
	
	/**
	 * 修改spark任务状态
	 * @param ids
	 * @param state
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int modifySqlState(String ids, int state, UserSession user) throws SystemException;
	
	/**
	 * 修改spark任务排序
	 * @param ids
	 * @param indexs
	 * @param user
	 * @return
	 */
	void modifySqlIndexs(String ids, String indexs, UserSession user) throws SystemException;
	
	/**
	 * 修改spark任务优先级
	 * @param ids
	 * @param prioritys
	 * @param user
	 * @return
	 */
	void modifySqlPrioritys(String ids, String prioritys, UserSession user) throws SystemException;
	
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
	 * 查询key-value列表
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	Map<String, Object> getSqlMap(Map<String, Object> params) throws SystemException;
}
