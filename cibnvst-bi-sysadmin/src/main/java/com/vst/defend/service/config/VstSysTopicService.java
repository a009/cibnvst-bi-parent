package com.vst.defend.service.config;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;

/**
 * 
 * @author lhp
 * @date 2017-11-17 下午06:27:33
 * @version
 */
@Service
public interface VstSysTopicService {
	/**
	 * 查询topic配置列表
	 * @param cutPage
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	CutPage getSysTopicList(CutPage cutPage, UserSession user) throws SystemException;
	
	/**
	 * 新增topic配置
	 * @param param
	 * @param user
	 * @throws SystemException
	 */
	void addSysTopic(Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 根据ID，获取topic配置信息
	 * @param id
	 * @return
	 * @throws SystemException
	 */
	Map<String, Object> getSysTopicById(String id) throws SystemException;
	
	/**
	 * 修改topic配置
	 * @param oldParam
	 * @param param
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int updateSysTopic(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 删除topic配置
	 * @param ids
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int deleteSysTopic(String ids, UserSession user) throws SystemException;
	
	/**
	 * 修改topic配置状态
	 * @param ids
	 * @param state
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int modifySysTopicState(String ids, int state, UserSession user) throws SystemException;
	
	/**
	 * 修改topic配置排序
	 * @param ids
	 * @param indexs
	 * @param user
	 * @return
	 */
	void modifySysTopicIndexs(String ids, String indexs, UserSession user) throws SystemException;
	
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
