package com.vst.defend.service.sys;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;

/**
 * 
 * @author lhp
 * @date 2017-5-9 上午11:06:49
 * @description
 * @version
 */
@Service
public interface VstOptionsService {
	/**
	 * 查询数据常量列表
	 * @param cutPage
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	CutPage getOptionsList(CutPage cutPage, UserSession user) throws SystemException;
	
	/**
	 * 校验key值唯一
	 * @param opKey
	 * @return
	 */
	int ajaxCheckKey(String opKey);
	
	/**
	 * 新增数据常量
	 * @param param
	 * @param user
	 * @throws SystemException
	 */
	void addOptions(Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 根据ID，获取数据常量信息
	 * @param id
	 * @return
	 * @throws SystemException
	 */
	Map<String, Object> getOptionsById(String id) throws SystemException;
	
	/**
	 * 修改数据常量
	 * @param oldParam
	 * @param param
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int updateOptions(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 删除数据常量
	 * @param ids
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int deleteOptions(String ids, UserSession user) throws SystemException;
	
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
