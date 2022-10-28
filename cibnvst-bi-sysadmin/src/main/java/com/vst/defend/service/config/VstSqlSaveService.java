package com.vst.defend.service.config;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;

/**
 * 
 * @author lhp
 * @date 2017-12-4 下午05:17:23
 * @version
 */
@Service
public interface VstSqlSaveService {
	/**
	 * 查询sql数据保存配置列表
	 * @param cutPage
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	CutPage getSqlSaveList(CutPage cutPage, UserSession user) throws SystemException;
	
	/**
	 * 新增sql数据保存配置
	 * @param param
	 * @param user
	 * @throws SystemException
	 */
	void addSqlSave(Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 根据ID，获取sql数据保存配置信息
	 * @param id
	 * @return
	 * @throws SystemException
	 */
	Map<String, Object> getSqlSaveById(String id) throws SystemException;
	
	/**
	 * 修改sql数据保存配置
	 * @param oldParam
	 * @param param
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int updateSqlSave(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 删除sql数据保存配置
	 * @param ids
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int deleteSqlSave(String ids, UserSession user) throws SystemException;
	
	/**
	 * 修改sql数据保存配置状态
	 * @param ids
	 * @param state
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int modifySqlSaveState(String ids, int state, UserSession user) throws SystemException;
	
	/**
	 * 修改sql数据保存配置排序
	 * @param ids
	 * @param indexs
	 * @param user
	 * @return
	 */
	void modifySqlSaveIndexs(String ids, String indexs, UserSession user) throws SystemException;
	
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
	 * 复制新增sql数据保存配置
	 * @param param
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int copyAddSqlSave(Map<String, Object> param, UserSession user) throws SystemException;
}
