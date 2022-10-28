package com.vst.defend.service.sys;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.vst.defend.communal.bean.LoginInfo;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;

/**
 * 
 * @author lhp
 * @date 2017-4-6 下午03:13:51
 * @description
 * @version
 */
@Service
public interface VstSysUserService {
	/**
	 * 验证登录
	 * @param loginInfo
	 * @return
	 * @throws SystemException
	 */
	UserSession loginValidate(LoginInfo loginInfo) throws SystemException;
	
	/**
	 * 查询用户列表
	 * @param cutPage
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	CutPage getSysUserList(CutPage cutPage, UserSession user) throws SystemException;
	
	/**
	 * 验证该用户名是否存在
	 * @param userName
	 * @return
	 * @throws SystemException
	 */
	boolean checkUserNameExist(String userName) throws SystemException;
	
	/**
	 * 新增用户
	 * @param param
	 * @param user
	 * @throws SystemException
	 */
	void addSysUser(Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 根据ID，获取用户信息
	 * @param id
	 * @return
	 * @throws SystemException
	 */
	Map<String, Object> getSysUserById(String id, UserSession user) throws SystemException;
	
	/**
	 * 修改用户
	 * @param oldParam
	 * @param param
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int updateSysUser(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException;

	/**
	 * 删除用户
	 * @param ids
	 * @param userSession
	 * @return
	 */
	int deleteSysUser(String ids, UserSession userSession) throws SystemException;
	
	/**
	 * 修改用户状态
	 * @param ids
	 * @param state
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int modifyUserState(String ids, int state, UserSession user) throws SystemException;
	
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
	 * 修改头像
	 * @param id
	 * @param photo
	 * @param user
	 * @return
	 */
	int updateSysPhoto(String id, String photo, UserSession user);
	
	/**
	 * 校验登录用户名、密码
	 * @param username
	 * @param password
	 * @return
	 */
	JSONObject checkLoginUser(String username, String password) throws SystemException;
}
