package com.vst.defend.service.sys;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;

/**
 * 
 * @author lhp
 * @date 2017-6-14 下午12:02:36
 * @description
 * @version
 */
@Service
public interface VstDictionaryService {
	/**
	 * 查询字典列表
	 * @param cutPage
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	CutPage getDictionaryList(CutPage cutPage, UserSession user) throws SystemException;
	
	/**
	 * 新增字典
	 * @param param
	 * @param user
	 * @throws SystemException
	 */
	void addDictionary(Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 根据ID，获取字典信息
	 * @param id
	 * @return
	 * @throws SystemException
	 */
	Map<String, Object> getDictionaryById(String id) throws SystemException;
	
	/**
	 * 修改字典
	 * @param oldParam
	 * @param param
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int updateDictionary(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 删除字典
	 * @param ids
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int deleteDictionary(String ids, UserSession user) throws SystemException;
	
	/**
	 * 修改字典状态
	 * @param ids
	 * @param state
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int modifyDictionaryState(String ids, int state, UserSession user) throws SystemException;
	
	/**
	 * 修改字典排序
	 * @param ids
	 * @param indexs
	 * @param user
	 * @return
	 */
	void modifyDictionaryIndexs(String ids, String indexs, UserSession user) throws SystemException;
	
	/**
	 * 批量添加字典
	 * @param params
	 * @param user
	 * @throws SystemException
	 */
	int batchAddDictionary(Map<String, Object> param, UserSession user) throws SystemException;
	
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
	 * 修改字典属性键
	 * @param ids
	 * @param keys
	 * @param user
	 * @return
	 */
	void modifyDictionaryKeys(String ids, String keys, UserSession user) throws SystemException;
	
	/**
	 * 修改字典属性值
	 * @param ids
	 * @param values
	 * @param user
	 * @return
	 */
	void modifyDictionaryValues(String ids, String values, UserSession user) throws SystemException;
	
	/**
	 * 复制字典数据
	 * @param ids 复制对象的ID，多个用英文逗号分割
	 * @param dataType 复制类型
	 * @param dataValue 修改后的值
	 * @param user 操作用户
	 * @return
	 */
	int copyDictionary(String ids, String dataType, String dataValue, UserSession user);
	
	/**
	 * 替换条件
	 * @param ids
	 * @param replace_type
	 * @param replace_before
	 * @param replace_after
	 * @param user
	 * @return
	 */
	int replaceDictionary(String ids, int replace_type, String replace_before, String replace_after, UserSession user);
}
