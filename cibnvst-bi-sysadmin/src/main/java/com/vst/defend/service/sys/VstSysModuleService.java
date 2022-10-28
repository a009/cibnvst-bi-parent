package com.vst.defend.service.sys;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;

/**
 * 
 * @author lhp
 * @date 2017-4-6 下午02:51:28
 * @description
 * @version
 */
@Service
public interface VstSysModuleService {
	/**
	 * 查询模块列表
	 * @param cutPage
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	CutPage getSysModuleList(CutPage cutPage, UserSession user) throws SystemException;
	
	/**
	 * 校验模块名称是否存在
	 * @param moduleName
	 * @return true：存在，false：不存在
	 * @throws SystemException
	 */
	boolean checkModuleNameExist(String parentId, String moduleName) throws SystemException;
	
	/**
	 * 新增模块
	 * @param param
	 * @param user
	 * @throws SystemException
	 */
	void addSysModule(Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 根据ID，获取模块信息
	 * @param id
	 * @return
	 * @throws SystemException
	 */
	Map<String, Object> getSysModuleById(String id) throws SystemException;
	
	/**
	 * 修改模块
	 * @param oldParam
	 * @param param
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int updateSysModule(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 删除模块
	 * @param ids
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int deleteSysModule(String ids, UserSession user) throws SystemException;
	
	/**
	 * 修改模块状态
	 * @param ids
	 * @param state
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int modifyModuleState(String ids, int state, UserSession user) throws SystemException;
	
	/**
	 * 修改模块排序
	 * @param ids
	 * @param indexs
	 * @param user
	 * @return
	 */
	void modifyModuleIndexs(String ids, String indexs, UserSession user) throws SystemException;
	
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
