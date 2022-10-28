package com.vst.defend.service.config;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;

/**
 * 
 * @author lhp
 * @date 2017-11-20 下午07:42:03
 * @version
 */
@Service
public interface VstSqlGroupService {
	/**
	 * 查询sql分组配置列表
	 * @param cutPage
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	CutPage getSqlGroupList(CutPage cutPage, UserSession user) throws SystemException;
	
	/**
	 * 新增sql分组配置
	 * @param param
	 * @param user
	 * @throws SystemException
	 */
	void addSqlGroup(Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 根据ID，获取sql分组配置信息
	 * @param id
	 * @return
	 * @throws SystemException
	 */
	Map<String, Object> getSqlGroupById(String id) throws SystemException;
	
	/**
	 * 修改sql分组配置
	 * @param oldParam
	 * @param param
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int updateSqlGroup(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 删除sql分组配置
	 * @param ids
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int deleteSqlGroup(String ids, UserSession user) throws SystemException;
	
	/**
	 * 修改sql分组配置状态
	 * @param ids
	 * @param state
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int modifySqlGroupState(String ids, int state, UserSession user) throws SystemException;
	
	/**
	 * 修改sql分组配置排序
	 * @param ids
	 * @param indexs
	 * @param user
	 * @return
	 */
	void modifySqlGroupIndexs(String ids, String indexs, UserSession user) throws SystemException;
	
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
	 * 复制新增sql分组配置
	 * @param param
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int copyAddSqlGroup(Map<String, Object> param, UserSession user) throws SystemException;
}
