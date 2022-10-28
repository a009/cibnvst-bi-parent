package com.vst.defend.service.config;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;

/**
 * 
 * @author lhp
 * @date 2018-12-13 下午05:41:31
 * @version
 */
@Service
public interface VstFeaturesConfigService {
	/**
	 * 查询用户指标特征配置列表
	 * @param cutPage
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	CutPage getFeaturesConfigList(CutPage cutPage, UserSession user) throws SystemException;
	
	/**
	 * 新增用户指标特征配置
	 * @param param
	 * @param user
	 * @throws SystemException
	 */
	void addFeaturesConfig(Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 根据ID，获取用户指标特征配置信息
	 * @param id
	 * @return
	 * @throws SystemException
	 */
	Map<String, Object> getFeaturesConfigById(String id) throws SystemException;
	
	/**
	 * 修改用户指标特征配置
	 * @param oldParam
	 * @param param
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int updateFeaturesConfig(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 删除用户指标特征配置
	 * @param ids
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int deleteFeaturesConfig(String ids, UserSession user) throws SystemException;
	
	/**
	 * 修改用户指标特征配置状态
	 * @param ids
	 * @param state
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int modifyFeaturesConfigState(String ids, int state, UserSession user) throws SystemException;
	
	/**
	 * 修改用户指标特征配置排序
	 * @param ids
	 * @param indexs
	 * @param user
	 * @return
	 */
	void modifyFeaturesConfigIndexs(String ids, String indexs, UserSession user) throws SystemException;
	
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
	 * 批量添加用户指标特征配置
	 * @param params
	 * @param user
	 * @throws SystemException
	 */
	int batchAddFeaturesConfig(Map<String, Object> params, UserSession user) throws SystemException;
}
