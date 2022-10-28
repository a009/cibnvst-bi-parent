package com.vst.defend.service.sys.impl;

import com.vst.common.pojo.VstSysOperateLog;
import com.vst.common.pojo.VstSysPermission;
import com.vst.common.pojo.VstSysRole;
import com.vst.common.tools.date.ToolsDate;
import com.vst.common.tools.number.ToolsRandom;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.BeanUtils;
import com.vst.defend.communal.util.CutPage;
import com.vst.defend.communal.util.LogUtils;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.dao.sys.VstSysPermissionDao;
import com.vst.defend.dao.sys.VstSysRoleDao;
import com.vst.defend.service.sys.VstSysRoleService;
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
 * @date 2017-4-7 下午02:23:28
 * @description
 * @version
 */
@Service
public class VstSysRoleServiceImpl implements VstSysRoleService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstSysRoleServiceImpl.class);
	
	/**
	 * 角色Dao接口
	 */
	@Resource
	private VstSysRoleDao _vstSysRoleDao;
	
	/**
	 * 权限Dao接口
	 */
	@Resource
	private VstSysPermissionDao _vstSysPermissionDao;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询角色列表
	 */
	@Override
	public CutPage getSysRoleList(CutPage cutPage, UserSession user) throws SystemException {
		// 返回结果
		CutPage result = new CutPage();
		try {
			Map<String, Object> params = cutPage.get_castQueryParam();
			if(params == null){
				params = new HashMap<String, Object>();
			}
			
			int count = _vstSysRoleDao.queryCount(params);
			
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
				
				List<Map<String, Object>> list = _vstSysRoleDao.queryForList(params);
				List<Object> dataList = new ArrayList<Object>();
				
				for(Map<String, Object> map : list){
					map.put("vst_role_addtime", ToolsDate.formatDate("yyyy-MM-dd HH:mm:ss", Long.valueOf(map.get("vst_role_addtime").toString())));
					map.put("vst_role_uptime", ToolsDate.formatDate("yyyy-MM-dd HH:mm:ss", Long.valueOf(map.get("vst_role_uptime").toString())));
					dataList.add(map);
				}
				result.set_dataList(dataList);
			}
		} catch (Exception e) {
			logger.error("查询角色列表失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		// 参数带上
		result.set_queryParam(cutPage.get_queryParam());
		return result;
	}
	
	/**
	 * 校验角色名称是否重复
	 */
	@Override
	public boolean checkRoleNameExist(String roleName)
			throws SystemException {
		try {
			Map<String, Object> param = new HashMap<String, Object>(1);
			param.put("vst_role_name", roleName);
			int count = _vstSysRoleDao.queryCount(param);
			
			return count > 0;
		} catch (Exception e) {
			logger.error("ajax验证角色名称是否存在失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
	}
	
	/**
	 * 新增角色
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public void addSysRole(Map<String, Object> param, String[] powerList, UserSession user)
			throws SystemException {
		try {
			VstSysRole bean = new VstSysRole();
			bean = (VstSysRole) BeanUtils.mapToBean(param, bean.getClass());
			// 设置id
			String roleId = ToolsRandom.getRandom(12);
			bean.setVst_role_id(roleId);
			// 角色类型
			bean.setVst_role_type(VstConstants.DATA_TYPE_CUSTOM);
			// 设置状态
			bean.setVst_role_state(VstConstants.STATE_AVALIABLE);
			// 设置添加时间
			bean.setVst_role_addtime(System.currentTimeMillis());
			// 设置更新时间
			bean.setVst_role_uptime(System.currentTimeMillis());
			// 设置操作人
			bean.setVst_role_operator(user.getLoginInfo().getLoginName());
			
			// 新增
			int result = _vstSysRoleDao.insert(bean);
			
			//解析权限列表
			if(powerList != null && powerList.length > 0){
				saveRolePermissions(roleId, powerList);//保存角色权限
			}
			
			if(result > 0){
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_ADD, 
						LogUtils.logFormat(VstConstants.OPERATE_TYPE_ADD, null, bean)));
			}
		} catch (Exception e) {
			logger.error("新增角色失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
	}
	
	/**
	 * 根据ID，获取角色信息
	 */
	@Override
	public Map<String, Object> getSysRoleById(String id) throws SystemException {
		Map<String, Object> result = null;
		try {
			result = _vstSysRoleDao.queryById(id);
			if(result != null){
				// 查询权限列表
				Map<String, Object> params = new HashMap<String, Object>(2);
				params.put("vst_role_id", id);
				params.put("vst_module_state", VstConstants.STATE_AVALIABLE);
				List<Map<String, Object>> list = _vstSysPermissionDao.queryPermissions(params);
				
				// 封装权限列表集合
				List<String> powerList = new ArrayList<String>();
				for(Map<String, Object> map : list){
					String parentId = map.get("vst_module_parent").toString();
					String childId = map.get("vst_module_id").toString();
					String buttonId = map.get("vst_button_id").toString();
					if("0".equals(parentId.trim())){
						powerList.add(childId);
					}else{
						powerList.add(parentId + "_" + childId + "_" + buttonId);
					}
				}
				result.put("oldPowerList", powerList);
			}
		} catch (Exception e) {
			logger.error("根据角色id获取角色信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改角色
	 */
	@Override
	public int updateSysRole(Map<String, Object> oldParam, Map<String, Object> param, String[] powerList, UserSession user) throws SystemException {
		// 返回结果
		int result = 0;
		try {
			VstSysRole bean = new VstSysRole();
			bean = (VstSysRole) BeanUtils.mapToBean(param, bean.getClass());
			// 设置更新时间
			bean.setVst_role_uptime(System.currentTimeMillis());
			// 设置操作人
			bean.setVst_role_operator(user.getLoginInfo().getLoginName());
			// 更新操作
			result = _vstSysRoleDao.update(bean);
			
			// 如果用户还修改了该角色的权限
			if(powerList != null && powerList.length > 0){
				// 先删除以前的旧权限
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("vst_role_id", bean.getVst_role_id());
				int delCount = _vstSysPermissionDao.delete(map);
				logger.info("删除角色权限(" + delCount + ")行！");
				
				// 然后再添加新的权限
				saveRolePermissions(param.get("vst_role_id")+"", powerList);
			}
			
			if(result == 1){
				VstSysRole oldBean = null;
				if(oldParam != null){
					oldBean = new VstSysRole();
					oldBean = (VstSysRole) BeanUtils.mapToBean(oldParam, oldBean.getClass());
				}
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), 
										VstConstants.OPERATE_TYPE_UPDATE, 
										LogUtils.logFormat(VstConstants.OPERATE_TYPE_UPDATE, oldBean, bean)));
			}
		} catch (Exception e) {
			logger.error("更新角色信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 批量删除角色
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int deleteSysRole(String ids, UserSession user) {
		int result = 0;
		try {
			Map<String, Object> param = new HashMap<String, Object>(1);
			param.put("list_ids", Arrays.asList(ids.split(",")));
			result = _vstSysRoleDao.delete(param);
			
			if(result > 0){
				// 同步删除系统权限
				param.clear();
				param.put("list_roleIds", Arrays.asList(ids.split(",")));
				int delCount = _vstSysPermissionDao.delete(param);
				logger.info("删除系统权限(" + delCount + ")行！");
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("批量删除角色, id=").append(ids);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_DEL, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("批量删除角色失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改角色状态
	 */
	public int modifyRoleState(String ids, int state, UserSession user) throws SystemException {
		int result = 0;
		try {
			Map<String, Object> params = new HashMap<String, Object>(1);
			params.put("list_ids", Arrays.asList(ids.split(",")));
			params.put("vst_role_state", state);
			params.put("vst_role_uptime", System.currentTimeMillis());
			params.put("vst_role_operator", user.getLoginInfo().getLoginName());
			result = _vstSysRoleDao.modifyState(params);
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append(state == 1 ? "启用角色，角色id=" : "禁用角色，角色id=").append(ids)
				.append("，操作人=").append(user.getLoginInfo().getLoginName());
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_UPDATE, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("修改角色状态失败: " + SystemException.getExceptionInfo(e));
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
			List<Map<String, Object>> dataList = _vstSysRoleDao.queryForList(params);
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
	 * 保存角色权限
	 * @param roleId
	 * @param powerList
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	private void saveRolePermissions(String roleId, String[] powerList){
		try {
			List<VstSysPermission> permList = new ArrayList<VstSysPermission>();
			for(String power : powerList){
				String[] parts = power.split("_");
				VstSysPermission bean = new VstSysPermission();
				bean.setVst_role_id(roleId);
				// 权限模块由三部分组成，第一部分是父模块id，第二部分是子模块id（如果没有，用0代替），第三部分是按钮id（如果没有，用0代替）
				if("0".equals(parts[1].trim())){ // 这种情况是父模块
					bean.setVst_module_id(parts[0]);
					bean.setVst_button_id("0");// 肯定没有按钮id
					
				}else{// 这种情况是子模块
					bean.setVst_module_id(parts[1]);
					bean.setVst_button_id(parts[2]);
				}
				permList.add(bean);
			}
			
			int result = _vstSysPermissionDao.batchInsert(permList);
			logger.info("赋权插入数据(" + result + ")行！");
			
		} catch (Exception e) {
			logger.error("保存角色权限失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
	}
}
