package com.vst.defend.service.config.impl;

import com.vst.common.pojo.VstSql;
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
import com.vst.defend.communal.util.LogUtils;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.communal.util.VstTools;
import com.vst.defend.dao.config.VstSqlDao;
import com.vst.defend.service.config.VstSqlService;
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
 * @date 2017-11-20 下午03:40:56
 * @version
 */
@Service
public class VstSqlServiceImpl implements VstSqlService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstSqlServiceImpl.class);
	
	/**
	 * spark任务Dao接口
	 */
	@Resource
	private VstSqlDao _vstSqlDao;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询spark任务列表
	 */
	@Override
	public CutPage getSqlList(CutPage cutPage, UserSession user) throws SystemException {
		// 返回结果
		CutPage result = new CutPage();
		try {
			Map<String, Object> params = cutPage.get_castQueryParam();
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				// 参数特殊化处理
				if(!ToolsString.isEmpty(params.get("vst_sql_name")+"")){
					VstTools.paramFormat("vst_sql_name", params.get("vst_sql_name")+"", params);
				}
			}
			
			int count = _vstSqlDao.queryCount(params);
			
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
				
				List<Map<String, Object>> list = _vstSqlDao.queryForList(params);
				List<Object> dataList = new ArrayList<Object>();
				
				// 当前时间
				long now = System.currentTimeMillis();
				// spark任务的警告阀值
				long sparkThreshold = ToolsNumber.parseLong(_baseService.getOptionByKey("spark_task_threshold"));
				
				for(Map<String, Object> map : list){
					// 判断任务是否正常执行
					int vst_sql_interval = ToolsNumber.parseInt(map.get("vst_sql_interval")+"");
					long vst_sql_runtime = ToolsNumber.parseLong(map.get("vst_sql_runtime")+"");
					if((now - vst_sql_runtime) > (vst_sql_interval + sparkThreshold) * 1000){
						map.put("isWarn", true);
					}
					
					if(!ToolsString.isEmpty(map.get("vst_sql_addtime")+"")){
						map.put("vst_sql_addtime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, ToolsNumber.parseLong(map.get("vst_sql_addtime")+"")));
					}
					if(!ToolsString.isEmpty(map.get("vst_sql_uptime")+"")){
						map.put("vst_sql_uptime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, ToolsNumber.parseLong(map.get("vst_sql_uptime")+"")));
					}
					if(!ToolsString.isEmpty(map.get("vst_sql_runtime")+"")){
						map.put("vst_sql_runtime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, ToolsNumber.parseLong(map.get("vst_sql_runtime")+"")));
					}
					dataList.add(map);
				}
				result.set_dataList(dataList);
			}
		} catch (Exception e) {
			logger.error("查询spark任务列表失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		// 参数带上
		result.set_queryParam(cutPage.get_queryParam());
		return result;
	}
	
	/**
	 * 新增spark任务
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public void addSql(Map<String, Object> param, UserSession user)
			throws SystemException {
		try {
			// 转换时间格式
			if(ToolsString.isEmpty(param.get("vst_sql_runtime"))){
				param.put("vst_sql_runtime", 0);
			}else{
				param.put("vst_sql_runtime", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm_ss, param.get("vst_sql_runtime")+""));
			}
			
			VstSql bean = new VstSql();
			bean = (VstSql) BeanUtils.mapToBean(param, bean.getClass());
			// 设置id
			bean.setVst_sql_id(ToolsRandom.getRandom(5));
			// 设置新增时间
			bean.setVst_sql_addtime(System.currentTimeMillis());
			// 设置新增人
			bean.setVst_sql_creator(user.getLoginInfo().getLoginName());
			
			// 新增
			int n = _vstSqlDao.insert(bean);
			if(n > 0){
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(),
										VstConstants.OPERATE_TYPE_ADD, 
										LogUtils.logFormat(VstConstants.OPERATE_TYPE_ADD, null, bean)));
			}
		} catch (Exception e) {
			logger.error("新增spark任务失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
	}
	
	/**
	 * 根据ID，获取spark任务信息
	 */
	@Override
	public Map<String, Object> getSqlById(String id) throws SystemException {
		Map<String, Object> result = null;
		try {
			result = _vstSqlDao.queryById(id);
			if(result != null){
				if(!ToolsString.isEmpty(result.get("vst_sql_runtime"))){
					if("0".equals(result.get("vst_sql_runtime")+"")){
						result.put("vst_sql_runtime", "");
					}else{
						result.put("vst_sql_runtime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, result.get("vst_sql_runtime")+""));
					}
				}
			}
		} catch (Exception e) {
			logger.error("根据id获取spark任务信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改spark任务
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int updateSql(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException {
		// 返回结果
		int result = 0;
		try {
			// 转换时间格式
			if(ToolsString.isEmpty(param.get("vst_sql_runtime"))){
				param.put("vst_sql_runtime", 0);
			}else{
				param.put("vst_sql_runtime", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm_ss, param.get("vst_sql_runtime")+""));
			}
			
			VstSql bean = new VstSql();
			bean = (VstSql) BeanUtils.mapToBean(param, bean.getClass());
			// 设置修改时间
			bean.setVst_sql_uptime(System.currentTimeMillis());
			// 设置修改人
			bean.setVst_sql_updator(user.getLoginInfo().getLoginName());
			// 更新操作
			result = _vstSqlDao.update(bean);
			
			if(result == 1){
				VstSql oldBean = null;
				if(oldParam != null){
					if(ToolsString.isEmpty(oldParam.get("vst_sql_runtime"))){
						oldParam.put("vst_sql_runtime", 0);
					}else{
						oldParam.put("vst_sql_runtime", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm_ss, oldParam.get("vst_sql_runtime")+""));
					}
					oldBean = new VstSql();
					oldBean = (VstSql) BeanUtils.mapToBean(oldParam, oldBean.getClass());
				}
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), 
										VstConstants.OPERATE_TYPE_UPDATE, 
										LogUtils.logFormat(VstConstants.OPERATE_TYPE_UPDATE, oldBean, bean)));
			}
		} catch (Exception e) {
			logger.error("更新spark任务信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 批量删除spark任务
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int deleteSql(String ids, UserSession user) {
		int result = 0;
		try {
			Map<String, Object> param = new HashMap<String, Object>(1);
			param.put("list_ids", Arrays.asList(ids.split(",")));
			result = _vstSqlDao.delete(param);
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("批量删除spark任务, id=").append(ids);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(),
										VstConstants.OPERATE_TYPE_DEL,
										sb.toString()));
			}
		} catch (Exception e) {
			logger.error("批量删除spark任务失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改spark任务状态
	 */
	public int modifySqlState(String ids, int state, UserSession user) throws SystemException {
		int result = 0;
		try {
			Map<String, Object> params = new HashMap<String, Object>(1);
			params.put("list_ids", Arrays.asList(ids.split(",")));
			params.put("vst_sql_state", state);
			params.put("vst_sql_uptime", System.currentTimeMillis());
			params.put("vst_sql_updator", user.getLoginInfo().getLoginName());
			result = _vstSqlDao.modifyState(params);
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append(state == 1 ? "启用spark任务, id=" : "禁用spark任务, id=").append(ids);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(),
										VstConstants.OPERATE_TYPE_UPDATE,
										sb.toString()));
			}
		} catch (Exception e) {
			logger.error("修改spark任务状态失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改spark任务排序
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public void modifySqlIndexs(String ids, String indexs, UserSession user) {
		int result = 0;
		try {
			List<VstSql> list = new ArrayList<VstSql>();			
			String[] id = ids.split(",");
			String[] index = indexs.split(",");
			long now = System.currentTimeMillis();
			VstSql bean = null;
			for(int i = 0; i < id.length; i++){
				bean = new VstSql();
				bean.setVst_sql_id(id[i]);
				bean.setVst_sql_index(Integer.parseInt(index[i]));
				bean.setVst_sql_uptime(now);
				bean.setVst_sql_updator(user.getLoginInfo().getLoginName());
				list.add(bean);
			}
			result = _vstSqlDao.batchUpdate(list);
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("批量修改spark任务排序, id=").append(ids).append(", 排序值=").append(indexs);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(),
										VstConstants.OPERATE_TYPE_UPDATE,
										sb.toString()));
			}
		} catch (Exception e) {
			logger.error("批量修改spark任务排序失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
	}
	
	/**
	 * 修改spark任务优先级
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public void modifySqlPrioritys(String ids, String prioritys, UserSession user) {
		int result = 0;
		try {
			List<VstSql> list = new ArrayList<VstSql>();			
			String[] id = ids.split(",");
			String[] priority = prioritys.split(",");
			long now = System.currentTimeMillis();
			VstSql bean = null;
			for(int i = 0; i < id.length; i++){
				bean = new VstSql();
				bean.setVst_sql_id(id[i]);
				bean.setVst_sql_priority(Integer.parseInt(priority[i]));
				bean.setVst_sql_uptime(now);
				bean.setVst_sql_updator(user.getLoginInfo().getLoginName());
				list.add(bean);
			}
			result = _vstSqlDao.batchUpdate(list);
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("批量修改spark任务优先级, id=").append(ids).append(", 优先级=").append(prioritys);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(),
										VstConstants.OPERATE_TYPE_UPDATE,
										sb.toString()));
			}
		} catch (Exception e) {
			logger.error("批量修改spark任务优先级失败: " + SystemException.getExceptionInfo(e));
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
			List<Map<String, Object>> dataList = _vstSqlDao.queryForList(params);
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
	 * 查询key-value列表
	 */
	@Override
	public Map<String, Object> getSqlMap(Map<String, Object> params) throws SystemException {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		try {
			List<Map<String, Object>> dataList = _vstSqlDao.queryForList(params);
			for(Map<String, Object> map : dataList){
				if("2".equals(map.get("vst_sql_state")+"")){
					result.put(map.get("vst_sql_id")+"", map.get("vst_sql_name")+"(禁用)");
				}else{
					result.put(map.get("vst_sql_id")+"", map.get("vst_sql_name"));
				}
			}
		} catch (Exception e) {
			logger.error("查询key-value列表失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
}
