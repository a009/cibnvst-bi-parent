package com.vst.defend.service.task.impl;

import com.vst.common.pojo.VstExportTask;
import com.vst.common.pojo.VstSysOperateLog;
import com.vst.common.tools.date.ToolsDate;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.number.ToolsRandom;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.BeanUtils;
import com.vst.defend.communal.util.CutPage;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.communal.util.VstTools;
import com.vst.defend.dao.task.VstExportTaskDao;
import com.vst.defend.service.task.VstExportTaskService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author lhp
 * @date 2018-4-13 上午10:50:38
 * @version
 */
@Service
public class VstExportTaskServiceImpl implements VstExportTaskService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstExportTaskServiceImpl.class);
	
	// cache
	private static Map<String, Map<String, Object>> cache_map_kReceiverId_vCacheInfo = new HashMap<String, Map<String, Object>>(); // 键是消息接收人内码，值是缓存信息
	
	/**
	 * 导出任务Dao接口
	 */
	@Resource
	private VstExportTaskDao _vstExportTaskDao;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询导出任务列表
	 */
	@Override
	public CutPage getExportTaskList(CutPage cutPage, UserSession user) throws SystemException {
		// 返回结果
		CutPage result = new CutPage();
		try {
			Map<String, Object> params = cutPage.get_castQueryParam();
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				// 参数特殊化处理
				if(!ToolsString.isEmpty(params.get("vst_task_table")+"")){
					VstTools.paramFormat("vst_task_table", params.get("vst_task_table")+"", params);
				}
			}
			
			int count = _vstExportTaskDao.queryCount(params);
			
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
				
				List<Map<String, Object>> list = _vstExportTaskDao.queryForList(params);
				List<Object> dataList = new ArrayList<Object>();
				
				for(Map<String, Object> map : list){
					// 单位转换
					if(!ToolsString.isEmpty(map.get("vst_task_file_size")+"")){
						long vst_task_file_size = ToolsNumber.parseLong(map.get("vst_task_file_size")+"");
						map.put("vst_task_file_size_kb", Math.round(vst_task_file_size/1024.0*100)/100.0);
					}
					// 时间转换
					map.put("vst_task_addtime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, Long.valueOf(map.get("vst_task_addtime").toString())));
					map.put("vst_task_uptime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, Long.valueOf(map.get("vst_task_uptime").toString())));
					dataList.add(map);
				}
				result.set_dataList(dataList);
			}
		} catch (Exception e) {
			logger.error("查询导出任务列表失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		// 参数带上
		result.set_queryParam(cutPage.get_queryParam());
		return result;
	}
	
	/**
	 * 新增导出任务
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public void addExportTask(Map<String, Object> param, UserSession user)
			throws SystemException {
		try {
			VstExportTask bean = new VstExportTask();
			bean = (VstExportTask) BeanUtils.mapToBean(param, bean.getClass());
			// 设置id
			if(ToolsString.isEmpty(param.get("vst_task_id")+"")){
				bean.setVst_task_id(ToolsRandom.getRandom(10));
			}
			// 设置状态
			if(ToolsString.isEmpty(param.get("vst_task_state")+"")){
				bean.setVst_task_state(0);
			}
			// 设置添加时间
			bean.setVst_task_addtime(System.currentTimeMillis());
			// 设置创建人
			bean.setVst_task_creator(user.getLoginInfo().getLoginName());
			
			// 新增
			_vstExportTaskDao.insert(bean);
		} catch (Exception e) {
			logger.error("新增导出任务失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
	}
	
	/**
	 * 根据ID，获取导出任务信息
	 */
	@Override
	public Map<String, Object> getExportTaskById(String id) throws SystemException {
		Map<String, Object> result = null;
		try {
			result = _vstExportTaskDao.queryById(id);
		} catch (Exception e) {
			logger.error("根据导出任务id获取导出任务信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改导出任务
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int updateExportTask(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException {
		// 返回结果
		int result = 0;
		try {
			VstExportTask bean = new VstExportTask();
			bean = (VstExportTask) BeanUtils.mapToBean(param, bean.getClass());
			// 设置修改时间
			bean.setVst_task_uptime(System.currentTimeMillis());
			// 设置修改人
			bean.setVst_task_updator(user.getLoginInfo().getLoginName());
			
			// 更新操作
			result = _vstExportTaskDao.update(bean);
		} catch (Exception e) {
			logger.error("更新导出任务信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 批量删除导出任务
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int deleteExportTask(String ids, UserSession user) {
		int result = 0;
		try {
			Map<String, Object> param = new HashMap<String, Object>(1);
			param.put("list_ids", Arrays.asList(ids.split(",")));
			result = _vstExportTaskDao.delete(param);
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("批量删除导出任务, id=").append(ids);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_DEL, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("批量删除导出任务失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改导出任务状态
	 */
	public int modifyExportTaskState(String ids, int state, UserSession user) throws SystemException {
		int result = 0;
		try {
			Map<String, Object> params = new HashMap<String, Object>(1);
			params.put("list_ids", Arrays.asList(ids.split(",")));
			params.put("vst_task_state", state);
			params.put("vst_task_uptime", System.currentTimeMillis());
			params.put("vst_task_updator", user.getLoginInfo().getLoginName());
			result = _vstExportTaskDao.modifyState(params);
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append(state == 1 ? "启用导出任务，导出任务id=" : "禁用导出任务，导出任务id=").append(ids)
				.append("，操作人=").append(user.getLoginInfo().getLoginName());
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_UPDATE, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("修改导出任务状态失败: " + SystemException.getExceptionInfo(e));
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
			List<Map<String, Object>> dataList = _vstExportTaskDao.queryForList(params);
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
	 * 未读消息数量是否发生变化
	 */
	@Override
	public boolean isUnreadMessageNumberChanged(String receiverId) {
		if (cache_map_kReceiverId_vCacheInfo.get(receiverId) == null) {
			return false;
		} else {
			cache_map_kReceiverId_vCacheInfo.remove(receiverId);
			return true;
		}
	}

	/**
	 * 写缓存(用于通知接收人未读消息发生变更)
	 */
	@Override
	public void writeCache(String receiverId) {
		// 创建缓存
		Map<String, Object> cacheInfo = new HashMap<String, Object>();
		cacheInfo.put("timestamp", new Date().getTime());
		// 写缓存
		cache_map_kReceiverId_vCacheInfo.put(receiverId, cacheInfo);
	}
}
