package com.vst.defend.service.sys;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;

/**
 * 
 * @author lhp
 * @date 2017-4-13 下午06:27:53
 * @description
 * @version
 */
@Service
public interface VstSysPermissionService {
	/**
	 * 查询所有的权限列表
	 * @param cutPage
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	CutPage getSysPermissionList(CutPage cutPage, UserSession user) throws SystemException;
	
	/**
	 * 校验该组合是否存在
	 * @param name
	 * @return
	 * @throws SystemException
	 */
	boolean checkNameExist(String name) throws SystemException;
	
	/**
	 * 添加权限映射
	 * @param param
	 * @param user
	 * @throws SystemException
	 */
	void addSysPermission(Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 批量删除权限映射
	 * @param groupIDs
	 * @param user
	 * @throws SystemException
	 */
	int batchDelSysPermissions(String groupIDs, UserSession user) throws SystemException;
}
