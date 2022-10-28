package com.vst.defend.service.sys.impl;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.pojo.VstSysLoginLog;
import com.vst.common.pojo.VstSysOperateLog;
import com.vst.common.pojo.VstSysUser;
import com.vst.common.tools.date.ToolsDate;
import com.vst.common.tools.encrypt.ToolsEncrypt;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.number.ToolsRandom;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.bean.LoginInfo;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.BeanUtils;
import com.vst.defend.communal.util.CutPage;
import com.vst.defend.communal.util.EncryptUtils;
import com.vst.defend.communal.util.LogUtils;
import com.vst.defend.communal.util.PropertiesReader;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.communal.util.VstTools;
import com.vst.defend.dao.sys.VstSysRoleDao;
import com.vst.defend.dao.sys.VstSysUserDao;
import com.vst.defend.service.sys.VstSysUserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author lhp
 * @date 2017-4-6 下午03:15:13
 * @description
 * @version
 */
@Service
public class VstSysUserServiceImpl implements VstSysUserService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstSysUserServiceImpl.class);
	
	/**
	 * 用户Dao接口
	 */
	@Resource
	private VstSysUserDao _vstSysUserDao;
	
	/**
	 * 系统角色Dao接口
	 */
	@Resource
	private VstSysRoleDao _vstSysRoleDao;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 验证登录
	 * @param loginInfo
	 * @return
	 * @throws SystemException
	 */
	@Override
	public UserSession loginValidate(LoginInfo loginInfo) throws SystemException {
		// 返回结果
		UserSession userSession = new UserSession();
		try {
			// 当登录bean为空，或者渠道代码或登录名或密码为空时
			if(loginInfo == null ||
					ToolsString.isEmpty(loginInfo.getLoginName()) || ToolsString.isEmpty(loginInfo.getLoginPassword())){
				userSession.setLoginResult(ErrorCode.LOGIN_ERROR_USERNAME_OR_PASSWORD_NULL);
			}else{
				Map<String, Object> param = new HashMap<String, Object>(2);
				param.put("vst_sys_username", loginInfo.getLoginName());
				if(loginInfo.getLoginType() == 2){//1帐号密码登录，2QQ互联登录
					param.put("vst_sys_password", loginInfo.getLoginPassword());
				}else{
					param.put("vst_sys_password", EncryptUtils.getSHA256Encrypt(loginInfo.getLoginPassword()));
				}
				
				List<VstSysUser> userList = _vstSysUserDao.queryForListAsBean(param);
				VstSysUser user = null;
				if(userList.size() > 0){
					user = userList.get(0);
				}
				if(user == null){
					// 用户名和密码不正确，返回登录失败信息
					userSession.setLoginResult(ErrorCode.LOGIN_ERROR_USERNAME_OR_PASSWORD);
				}else if(user.getVst_sys_state() == VstConstants.STATE_DISABLED){
					// 该帐号被停用
					userSession.setLoginResult(ErrorCode.LOGIN_ERROR_USER_STOP);
				}else{
					int roleState = 1;//用户的角色状态
					Map<String, Object> roleParam = new HashMap<String, Object>(1);
					roleParam.put("vst_role_id", user.getVst_role_id());
					List<Map<String, Object>> roleList = _vstSysRoleDao.queryForList(roleParam);
					for (Map<String, Object> roleMap : roleList) {
						roleState = ToolsNumber.parseInt(roleMap.get("vst_role_state")+"");
					}
					if(roleState == VstConstants.STATE_DISABLED){
						// 该账户角色已被禁用
						userSession.setLoginResult(ErrorCode.LOGIN_ERROR_ROLE_STATE_DISABLED);
					}else if(roleState == VstConstants.STATE_AVALIABLE){
						// 把登录信息设置到用户session中
						userSession.setLoginInfo(loginInfo);
						// 把数据库中的用户信息放到session中
						userSession.setVstSysUser(user);
						
						// 查询模块列表
						userSession.setModules(_baseService.getSysModules(Arrays.asList(user.getVst_role_id().split(","))));
						// 查询所有的按钮列表
						userSession.setButtons(_baseService.getSysButtons(Arrays.asList(user.getVst_role_id().split(","))));
						// 查询所有的角色
						userSession.setSysRoles(_baseService.getAllSysRolesName());
						// 查询当前用户所拥有的角色
						userSession.setRoles(_baseService.getAllSysRolesName(Arrays.asList(user.getVst_role_id().split(","))));
	
						// 记登录日志
						_baseService.saveLoginLog(userSession, new VstSysLoginLog());
					}
				}
			}
		} catch (Exception e) {
			logger.error("验证用户登录失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return userSession;
	}
	
	/**
	 * 查询用户列表
	 */
	@Override
	public CutPage getSysUserList(CutPage cutPage, UserSession user) throws SystemException {
		// 返回结果
		CutPage result = new CutPage();
		try {
			Map<String, Object> params = cutPage.get_castQueryParam();
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				// 参数特殊化处理
				if(!ToolsString.isEmpty(params.get("vst_sys_name")+"")){
					VstTools.paramFormat("vst_sys_name", params.get("vst_sys_name")+"", params);
				}
				if(!ToolsString.isEmpty(params.get("vst_sys_channel")+"")){
					VstTools.paramFormat("vst_sys_channel", params.get("vst_sys_channel")+"", params);
				}
			}
			
			int count = _vstSysUserDao.queryCount(params);
			
			if(count != 0){
				// 设置单页显示数量
				result.set_singleCount(cutPage.get_singleCount());
				// 设置总数
				result.set_totalResults(count);
				// 设置当前页
				result.set_currPage(cutPage.get_currPage());
				
				int start = (cutPage.get_currPage()-1) * cutPage.get_singleCount();
				
				params.put("offset", start);
				params.put("limit", cutPage.get_singleCount());
				
				List<Map<String, Object>> list = _vstSysUserDao.queryForList(params);
				List<Object> dataList = new ArrayList<Object>();
				
				String host = PropertiesReader.getProperty("[img]") + "/";
				for(Map<String, Object> map : list){
					if(!ToolsString.isEmpty(map.get("vst_sys_photo")+"")){
						map.put("vst_sys_photo", (map.get("vst_sys_photo")+"").replace("[img]/", host).replace("[img]", host));
					}
					map.put("vst_sys_addtime", ToolsDate.formatDate("yyyy-MM-dd HH:mm:ss", Long.valueOf(map.get("vst_sys_addtime").toString())));
					map.put("vst_sys_uptime", ToolsDate.formatDate("yyyy-MM-dd HH:mm:ss", Long.valueOf(map.get("vst_sys_uptime").toString())));
					dataList.add(map);
				}
				result.set_dataList(dataList);
			}
		} catch (Exception e) {
			logger.error("查询用户列表失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		// 参数带上
		result.set_queryParam(cutPage.get_queryParam());
		return result;
	}
	
	/**
	 * 验证该用户名是否存在
	 */
	@Override
	public boolean checkUserNameExist(String userName) throws SystemException {
		try {
			Map<String, Object> param = new HashMap<String, Object>(1);
			param.put("vst_sys_username", userName);
			int count = _vstSysUserDao.queryCount(param);
			
			return count > 0;
		} catch (Exception e) {
			logger.error("ajax验证用户是否存在失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
	}
	
	/**
	 * 新增用户
	 */
	@Override
	public void addSysUser(Map<String, Object> param, UserSession user)
			throws SystemException {
		try {
			VstSysUser bean = new VstSysUser();
			bean = (VstSysUser) BeanUtils.mapToBean(param, bean.getClass());
			// 设置id
			bean.setVst_sys_id(ToolsRandom.getRandom(12));
			// 设置用户类型
			bean.setVst_sys_type(VstConstants.DATA_TYPE_CUSTOM);
			// 设置状态
			bean.setVst_sys_state(VstConstants.STATE_AVALIABLE);
			// 设置添加时间
			bean.setVst_sys_addtime(System.currentTimeMillis());
			// 设置更新时间
			bean.setVst_sys_uptime(System.currentTimeMillis());
			// 设置操作人
			bean.setVst_sys_operator(user.getLoginInfo().getLoginName());
			// 密码加密
			bean.setVst_sys_password(EncryptUtils.getSHA256Encrypt(bean.getVst_sys_password()));
			
			// 新增
			_vstSysUserDao.insert(bean);
			
			// 写操作日志
			_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_ADD, 
					LogUtils.logFormat(VstConstants.OPERATE_TYPE_ADD, null, bean)));
			
		} catch (Exception e) {
			logger.error("新增用户失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
	}
	
	/**
	 * 根据ID，获取用户信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getSysUserById(String userId, UserSession user) throws SystemException {
		Map<String, Object> result = null;
		try {
			Map<String, Object> param = new HashMap<String, Object>(1);
			param.put("vst_sys_id", userId);
			Map<String, Object> bean = queryForMap(param, "vst_sys_id", null);
			
			if(bean != null){
				for(Map.Entry<String, Object> entry : bean.entrySet()){
					result = (Map<String, Object>) entry.getValue();
				}
				
				result.put("vst_role_name", user.getSysRoles(result.get("vst_role_id").toString()));
			}
		} catch (Exception e) {
			logger.error("根据用户id获取用户信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改用户
	 */
	@Override
	public int updateSysUser(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException {
		// 返回结果
		int result = 0;
		try {
			VstSysUser bean = new VstSysUser();
			bean = (VstSysUser) BeanUtils.mapToBean(param, bean.getClass());
			// 设置更新时间
			bean.setVst_sys_uptime(System.currentTimeMillis());
			// 设置操作人
			bean.setVst_sys_operator(user.getLoginInfo().getLoginName());
			// 更新操作
			result = _vstSysUserDao.update(bean);
			
			if(result == 1){
				VstSysUser oldBean = null;
				if(oldParam != null){
					oldBean = new VstSysUser();
					oldBean = (VstSysUser) BeanUtils.mapToBean(oldParam, oldBean.getClass());
				}
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), 
										VstConstants.OPERATE_TYPE_UPDATE, 
										LogUtils.logFormat(VstConstants.OPERATE_TYPE_UPDATE, oldBean, bean)));
			}
		} catch (Exception e) {
			logger.error("更新用户信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 删除用户
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int deleteSysUser(String ids, UserSession user) {
		int result = 0;
		try {
			Map<String, Object> param = new HashMap<String, Object>(1);
			param.put("list_ids", Arrays.asList(ids.split(",")));
			result = _vstSysUserDao.delete(param);
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("批量删除用户信息, 用户id=").append(ids);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_DEL, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("删除用户信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改用户状态
	 */
	public int modifyUserState(String ids, int state, UserSession user) throws SystemException {
		int result = 0;
		try {
			Map<String, Object> params = new HashMap<String, Object>(1);
			params.put("list_ids", Arrays.asList(ids.split(",")));
			params.put("vst_sys_state", state);
			params.put("vst_sys_uptime", System.currentTimeMillis());
			params.put("vst_sys_operator", user.getLoginInfo().getLoginName());
			result = _vstSysUserDao.modifyState(params);
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append(state == 1 ? "启用用户，用户id=" : "禁用用户，用户id=").append(ids)
				.append("，操作人=").append(user.getLoginInfo().getLoginName());
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_UPDATE, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("修改用户状态失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 获取键值对
	 */
	@Override
	public Map<String, Object> queryForMap(Map<String, Object> params, String key, String value){
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		try {
			List<Map<String, Object>> dataList = _vstSysUserDao.queryForList(params);
			if(value == null){	//value为空时，值代表整个对象
				for(Map<String, Object> map : dataList){
					if(!ToolsString.isEmpty(map.get(key)+"")){
						result.put(map.get(key)+"", map);
					}
				}
			}else{
				for(Map<String, Object> map : dataList){
					if(!ToolsString.isEmpty(map.get(key)+"")){
						result.put(map.get(key)+"", map.get(value));
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取键值对失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改头像
	 * @param id
	 * @param photo
	 * @param user
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int updateSysPhoto(String id, String photo, UserSession user) {
		int result = 0;
		try {
			VstSysUser bean = new VstSysUser();
			bean.setVst_sys_id(id);
			bean.setVst_sys_photo(photo);
			bean.setVst_sys_uptime(System.currentTimeMillis());
			bean.setVst_sys_operator(user.getLoginInfo().getLoginName());
			result = _vstSysUserDao.update(bean);
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("修改用户头像, 用户id=").append(id).append(", photo=").append(photo);
				_baseService.saveLog(user, new VstSysOperateLog("F44K6WXQ5U6M", VstConstants.OPERATE_TYPE_UPDATE, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("修改用户头像失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 校验登录用户名、密码
	 */
	@Override
	public JSONObject checkLoginUser(String username, String password) throws SystemException {
		JSONObject result = new JSONObject();
		try {
			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("vst_sys_username", username);
			params.put("vst_sys_password", ToolsEncrypt.getSHA256Encrypt(password));
			params.put("vst_sys_state", VstConstants.STATE_AVALIABLE);
			int count = _vstSysUserDao.queryCount(params);
			if(count > 0){
				result.put("code", 100);
				result.put("msg", "校验成功！");
			}else{
				params.remove("vst_sys_password");
				count = _vstSysUserDao.queryCount(params);
				if(count > 0){
					result.put("code", 201);
					result.put("msg", "密码错误！");
				}else{
					result.put("code", 202);
					result.put("msg", "用户无权限！");
				}
			}
		} catch (Exception e) {
			logger.error("校验登录用户名、密码失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
}
