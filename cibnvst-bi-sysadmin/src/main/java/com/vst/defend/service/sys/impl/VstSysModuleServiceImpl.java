package com.vst.defend.service.sys.impl;

import com.vst.common.pojo.VstSysModule;
import com.vst.common.pojo.VstSysOperateLog;
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
import com.vst.defend.dao.sys.VstSysModuleDao;
import com.vst.defend.dao.sys.VstSysPermissionDao;
import com.vst.defend.service.sys.VstSysModuleService;
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
 * @date 2017-4-6 下午02:53:31
 * @description
 * @version
 */
@Service
public class VstSysModuleServiceImpl implements VstSysModuleService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstSysModuleServiceImpl.class);
	
	/**
	 * 模块Dao接口
	 */
	@Resource
	private VstSysModuleDao _vstSysModuleDao;
	
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
	 * 查询模块列表
	 */
	@Override
	public CutPage getSysModuleList(CutPage cutPage, UserSession user) throws SystemException {
		// 返回结果
		CutPage result = new CutPage();
		try {
			Map<String, Object> params = cutPage.get_castQueryParam();
			if(params == null){
				params = new HashMap<String, Object>();
			}
			
			int count = _vstSysModuleDao.queryCount(params);
			
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
				
				List<Map<String, Object>> list = _vstSysModuleDao.queryForList(params);
				List<Object> dataList = new ArrayList<Object>();
				
				for(Map<String, Object> map : list){
					map.put("vst_module_addtime", ToolsDate.formatDate("yyyy-MM-dd HH:mm:ss", Long.valueOf(map.get("vst_module_addtime").toString())));
					map.put("vst_module_uptime", ToolsDate.formatDate("yyyy-MM-dd HH:mm:ss", Long.valueOf(map.get("vst_module_uptime").toString())));
					dataList.add(map);
				}
				result.set_dataList(dataList);
			}
		} catch (Exception e) {
			logger.error("查询模块列表失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		// 参数带上
		result.set_queryParam(cutPage.get_queryParam());
		return result;
	}
	
	/**
	 * 校验模块名称是否存在
	 */
	@Override
	public boolean checkModuleNameExist(String parentId, String moduleName)
			throws SystemException {
		try {
			Map<String, Object> param = new HashMap<String, Object>(2);
			param.put("equals_vst_module_parent", parentId);
			param.put("vst_module_name", moduleName);
			int count = _vstSysModuleDao.queryCount(param);
			return count > 0;
		} catch (Exception e) {
			logger.error("ajax验证模块名称是否存在失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
	}
	
	/**
	 * 新增模块
	 */
	@Override
	public void addSysModule(Map<String, Object> param, UserSession user)
			throws SystemException {
		try {
			VstSysModule bean = new VstSysModule();
			bean = (VstSysModule) BeanUtils.mapToBean(param, bean.getClass());
			// 设置id
			bean.setVst_module_id(ToolsRandom.getRandom(12));
			// 设置状态
			bean.setVst_module_state(VstConstants.STATE_AVALIABLE);
			// 设置添加时间
			bean.setVst_module_addtime(System.currentTimeMillis());
			// 设置更新时间
			bean.setVst_module_uptime(System.currentTimeMillis());
			// 设置操作人
			bean.setVst_module_operator(user.getLoginInfo().getLoginName());
			// 新增
			int n = _vstSysModuleDao.insert(bean);
			
			if(n > 0){
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_ADD, 
						LogUtils.logFormat(VstConstants.OPERATE_TYPE_ADD, null, bean)));
			}
			
		} catch (Exception e) {
			logger.error("新增模块失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
	}
	
	/**
	 * 根据ID，获取模块信息
	 */
	@Override
	public Map<String, Object> getSysModuleById(String id) throws SystemException {
		Map<String, Object> result = null;
		try {
			result = _vstSysModuleDao.queryById(id);
		} catch (Exception e) {
			logger.error("根据模块id获取模块信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改模块
	 */
	@Override
	public int updateSysModule(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException {
		// 返回结果
		int result = 0;
		try {
			VstSysModule bean = new VstSysModule();
			bean = (VstSysModule) BeanUtils.mapToBean(param, bean.getClass());
			// 设置更新时间
			bean.setVst_module_uptime(System.currentTimeMillis());
			// 设置操作人
			bean.setVst_module_operator(user.getLoginInfo().getLoginName());
			// 更新操作
			result = _vstSysModuleDao.update(bean);
			
			if(result == 1){
				VstSysModule oldBean = null;
				if(oldParam != null){
					oldBean = new VstSysModule();
					oldBean = (VstSysModule) BeanUtils.mapToBean(oldParam, oldBean.getClass());
				}
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), 
										VstConstants.OPERATE_TYPE_UPDATE, 
										LogUtils.logFormat(VstConstants.OPERATE_TYPE_UPDATE, oldBean, bean)));
			}
		} catch (Exception e) {
			logger.error("更新模块信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 批量删除模块
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int deleteSysModule(String ids, UserSession user) {
		int result = 0;
		try {
			Map<String, Object> param = new HashMap<String, Object>(1);
			param.put("list_ids", Arrays.asList(ids.split(",")));
			result = _vstSysModuleDao.delete(param);
			
			if(result > 0){
				// 同步删除系统权限
				param.clear();
				param.put("list_moduleIds", Arrays.asList(ids.split(",")));
				int delCount = _vstSysPermissionDao.delete(param);
				logger.info("删除系统权限(" + delCount + ")行！");
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("批量删除模块, vstClassifyId=").append(ids);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_DEL, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("批量删除模块失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改模块状态
	 */
	public int modifyModuleState(String ids, int state, UserSession user) throws SystemException {
		int result = 0;
		try {
			Map<String, Object> params = new HashMap<String, Object>(1);
			params.put("list_ids", Arrays.asList(ids.split(",")));
			params.put("vst_module_state", state);
			params.put("vst_module_uptime", System.currentTimeMillis());
			params.put("vst_module_operator", user.getLoginInfo().getLoginName());
			result = _vstSysModuleDao.modifyState(params);
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append(state == 1 ? "启用模块，模块id=" : "禁用模块，模块id=").append(ids)
				.append("，操作人=").append(user.getLoginInfo().getLoginName());
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_UPDATE, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("修改模块状态失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改模块排序
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public void modifyModuleIndexs(String ids, String indexs, UserSession user) {
		int result = 0;
		try {
			List<VstSysModule> list = new ArrayList<VstSysModule>();			
			String[] id = ids.split(",");
			String[] index = indexs.split(",");
			long now = System.currentTimeMillis();
			VstSysModule bean = null;
			for(int i = 0; i < id.length; i++){
				bean = new VstSysModule();
				bean.setVst_module_id(id[i]);
				bean.setVst_module_index(Integer.parseInt(index[i]));
				bean.setVst_module_uptime(now);
				bean.setVst_module_operator(user.getLoginInfo().getLoginName());
				list.add(bean);
			}
			result = _vstSysModuleDao.batchUpdate(list);
			
			if(result > 0){
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_UPDATE, "批量修改模块排序，记录id=" + ids + ",排序值=" + indexs));
			}
		} catch (Exception e) {
			logger.error("批量模块排序失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
	}
	
	/**
	 * 获取键值对
	 */
	@Override
	public Map<String, Object> queryForMap(Map<String, Object> params, String key, String value){
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		try {
			List<Map<String, Object>> dataList = _vstSysModuleDao.queryForList(params);
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
}
