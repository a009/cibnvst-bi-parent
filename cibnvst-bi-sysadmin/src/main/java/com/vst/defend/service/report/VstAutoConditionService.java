package com.vst.defend.service.report;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;

/**
 * 
 * @author lhp
 * @date 2017-9-12 下午03:39:39
 * @version
 */
@Service
public interface VstAutoConditionService {
	/**
	 * 查询自动化(查询条件)列表
	 * @param cutPage
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	CutPage getAutoConditionList(CutPage cutPage, UserSession user) throws SystemException;
	
	/**
	 * 新增自动化(查询条件)
	 * @param param
	 * @param user
	 * @throws SystemException
	 */
	void addAutoCondition(Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 根据ID，获取自动化(查询条件)信息
	 * @param id
	 * @return
	 * @throws SystemException
	 */
	Map<String, Object> getAutoConditionById(String id) throws SystemException;
	
	/**
	 * 修改自动化(查询条件)
	 * @param oldParam
	 * @param param
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int updateAutoCondition(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 删除自动化(查询条件)
	 * @param ids
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int deleteAutoCondition(String ids, UserSession user) throws SystemException;
	
	/**
	 * 修改自动化(查询条件)状态
	 * @param ids
	 * @param state
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int modifyAutoConditionState(String ids, int state, UserSession user) throws SystemException;
	
	/**
	 * 修改自动化(查询条件)排序
	 * @param ids
	 * @param indexs
	 * @param user
	 * @return
	 */
	void modifyAutoConditionIndexs(String ids, String indexs, UserSession user) throws SystemException;
	
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
	 * 根据代码编号获取查询条件
	 * @param code
	 * @return
	 * @throws SystemException
	 */
	List<String> getConditionByCode(String code) throws SystemException;
	
	/**
	 * 复制条件
	 * @param ids 复制对象的ID，多个用英文逗号分割
	 * @param targetCode 目标代码编号
	 * @param user 操作用户
	 * @return
	 */
	int copyCondition(String ids, String targetCode, UserSession user);
	
	/**
	 * 替换条件
	 * @param ids
	 * @param replace_type
	 * @param replace_before
	 * @param replace_after
	 * @param user
	 * @return
	 */
	int replaceCondition(String ids, int replace_type, String replace_before, String replace_after, UserSession user);
}
