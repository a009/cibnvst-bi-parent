package com.vst.defend.service.config;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;

/**
 * 
 * @author lhp
 * @date 2017-11-17 下午06:35:21
 * @version
 */
@Service
public interface VstSysTopicPropService {
	/**
	 * 查询topic配置属性列表
	 * @param cutPage
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	CutPage getSysTopicPropList(CutPage cutPage, UserSession user) throws SystemException;
	
	/**
	 * 新增topic配置属性
	 * @param param
	 * @param user
	 * @throws SystemException
	 */
	void addSysTopicProp(Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 根据ID，获取topic配置属性信息
	 * @param id
	 * @return
	 * @throws SystemException
	 */
	Map<String, Object> getSysTopicPropById(String id) throws SystemException;
	
	/**
	 * 修改topic配置属性
	 * @param oldParam
	 * @param param
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int updateSysTopicProp(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 删除topic配置属性
	 * @param ids
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int deleteSysTopicProp(String ids, UserSession user) throws SystemException;
	
	/**
	 * 修改topic配置属性状态
	 * @param ids
	 * @param state
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int modifySysTopicPropState(String ids, int state, UserSession user) throws SystemException;
	
	/**
	 * 修改topic配置属性排序
	 * @param ids
	 * @param indexs
	 * @param user
	 * @return
	 */
	void modifySysTopicPropIndexs(String ids, String indexs, UserSession user) throws SystemException;
	
	/**
	 * 获取键值对
	 * @param params
	 * @param key
	 * @param value
	 * @return
	 * @throws SystemException
	 */
	Map<String, Object> queryForMap(Map<String, Object> params, String key, String value) throws SystemException;
}
