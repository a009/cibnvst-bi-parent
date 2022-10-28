package com.vst.defend.service.config;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;

/**
 * 
 * @author lhp
 * @date 2017-11-20 下午08:09:38
 * @version
 */
@Service
public interface VstSqlOrderService {
	/**
	 * 查询sql排序配置列表
	 * @param cutPage
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	CutPage getSqlOrderList(CutPage cutPage, UserSession user) throws SystemException;
	
	/**
	 * 新增sql排序配置
	 * @param param
	 * @param user
	 * @throws SystemException
	 */
	void addSqlOrder(Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 根据ID，获取sql排序配置信息
	 * @param id
	 * @return
	 * @throws SystemException
	 */
	Map<String, Object> getSqlOrderById(String id) throws SystemException;
	
	/**
	 * 修改sql排序配置
	 * @param oldParam
	 * @param param
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int updateSqlOrder(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 删除sql排序配置
	 * @param ids
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int deleteSqlOrder(String ids, UserSession user) throws SystemException;
	
	/**
	 * 修改sql排序配置状态
	 * @param ids
	 * @param state
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int modifySqlOrderState(String ids, int state, UserSession user) throws SystemException;
	
	/**
	 * 修改sql排序配置排序
	 * @param ids
	 * @param indexs
	 * @param user
	 * @return
	 */
	void modifySqlOrderIndexs(String ids, String indexs, UserSession user) throws SystemException;
	
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
	 * 复制新增sql排序配置
	 * @param param
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int copyAddSqlOrder(Map<String, Object> param, UserSession user) throws SystemException;
}
