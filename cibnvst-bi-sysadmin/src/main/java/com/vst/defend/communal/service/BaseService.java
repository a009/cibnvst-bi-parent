package com.vst.defend.communal.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.vst.common.pojo.VstSysButton;
import com.vst.common.pojo.VstSysLoginLog;
import com.vst.common.pojo.VstSysModule;
import com.vst.common.pojo.VstSysOperateLog;
import com.vst.defend.communal.bean.SysModules;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;


/**
 * @author weiwei
 * @date 2014-10-23 下午08:52:42
 * @description 
 * @version	
 */
@Service
public interface BaseService {
	
	/**
	 * 根据角色id获取用户模块
	 * @param roleIds
	 * @return
	 * @throws SystemException
	 */
	Map<VstSysModule, List<VstSysModule>> getSysModules(List<String> roleIds) throws SystemException;
	
	/**
	 * 根据角色id获取用户所拥有的按钮
	 * @param roleIds
	 * @return
	 * @throws SystemException
	 */
	Map<String, List<VstSysButton>> getSysButtons(List<String> roleIds) throws SystemException;
	
	/**
	 * 获取当前用户所对应的所有角色
	 * @param roleIds
	 * @return
	 * @throws SystemException
	 */
	Map<String, String> getAllSysRolesName(List<String> roleIds) throws SystemException;
	
	/**
	 * 获取所有VST管理员的角色
	 * @return
	 * @throws SystemException
	 */
	Map<String, String> getAllSysRolesName() throws SystemException;
	
	/**
	 * 获取所有的系统模块名称
	 * @return
	 * @throws SystemException
	 */
	Map<String, String> getAllSysModulesName() throws SystemException;
	
	/**
	 * 获取所有的系统按钮名称
	 * @return
	 * @throws SystemException
	 */
	Map<String, String> getAllSysButtonsName() throws SystemException;
	
	/**
	 * 写操作日志
	 * @param user
	 * @param log
	 * @throws SystemException
	 */
	void saveLog(UserSession user, VstSysOperateLog log) throws SystemException;
	
	/**
	 * 写登录日志
	 * @param user
	 * @param log
	 * @throws SystemException
	 */
	void saveLoginLog(UserSession user, VstSysLoginLog log) throws SystemException;
	
	/**
	 * 根据用户id获取该用户的最大权限模块
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	List<SysModules> getParentSysModules(UserSession user) throws SystemException;
	
	/**
	 * 获取字典键值对
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	Map<String, String> getDictionaryForMap(Map<String, Object> params) throws SystemException;
	
	/**
	 * 获取控制面板的配置内容
	 * @param key
	 * @return
	 */
	String getOptionByKey(String key) throws SystemException;
	
	/**
	 * 获取表的描述信息
	 * @param tableName
	 * @return
	 * @throws SystemException
	 */
	JSONArray getTableDesc(String tableName) throws SystemException;
}
