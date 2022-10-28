package com.vst.defend.service.sys;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;

/**
 * 
 * @author lhp
 * @date 2017-4-7 下午02:20:53
 * @description
 * @version
 */
@Service
public interface VstSysRoleService {
	/**
	 * 查询角色列表
	 * @param cutPage
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	CutPage getSysRoleList(CutPage cutPage, UserSession user) throws SystemException;
	
	/**
	 * 校验角色名称是否重复
	 * @param roleName
	 * @return
	 * @throws SystemException
	 */
	boolean checkRoleNameExist(String roleName) throws SystemException;
	
	/**
	 * 新增角色
	 * @param param
	 * @param user
	 * @param powerList
	 * @throws SystemException
	 */
	void addSysRole(Map<String, Object> param, String[] powerList, UserSession user) throws SystemException;
	
	/**
	 * 根据ID，获取角色信息
	 * @param id
	 * @return
	 * @throws SystemException
	 */
	Map<String, Object> getSysRoleById(String id) throws SystemException;
	
	/**
	 * 修改角色
	 * @param oldParam
	 * @param param
	 * @param powerList
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int updateSysRole(Map<String, Object> oldParam, Map<String, Object> param, String[] powerList, UserSession user) throws SystemException;
	
	/**
	 * 删除角色
	 * @param ids
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int deleteSysRole(String ids, UserSession user) throws SystemException;
	
	/**
	 * 修改角色状态
	 * @param ids
	 * @param state
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int modifyRoleState(String ids, int state, UserSession user) throws SystemException;
	
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
