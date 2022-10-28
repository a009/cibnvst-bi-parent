package com.vst.defend.service.config.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vst.common.pojo.VstSqlColumn;
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
import com.vst.defend.dao.config.VstSqlColumnDao;
import com.vst.defend.service.config.VstSqlColumnService;

/**
 * 
 * @author lhp
 * @date 2017-11-20 下午06:23:56
 * @version
 */
@Service
public class VstSqlColumnServiceImpl implements VstSqlColumnService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstSqlColumnServiceImpl.class);
	
	/**
	 * sql列配置Dao接口
	 */
	@Resource
	private VstSqlColumnDao _vstSqlColumnDao;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询sql列配置列表
	 */
	@Override
	public CutPage getSqlColumnList(CutPage cutPage, UserSession user) throws SystemException {
		// 返回结果
		CutPage result = new CutPage();
		try {
			Map<String, Object> params = cutPage.get_castQueryParam();
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				// 参数特殊化处理
				if(!ToolsString.isEmpty(params.get("vst_column_name")+"")){
					VstTools.paramFormat("vst_column_name", params.get("vst_column_name")+"", params);
				}
				if(!ToolsString.isEmpty(params.get("vst_column_alias")+"")){
					VstTools.paramFormat("vst_column_alias", params.get("vst_column_alias")+"", params);
				}
			}
			
			int count = _vstSqlColumnDao.queryCount(params);
			
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
				
				List<Map<String, Object>> list = _vstSqlColumnDao.queryForList(params);
				List<Object> dataList = new ArrayList<Object>();
				
				for(Map<String, Object> map : list){
					if(!ToolsString.isEmpty(map.get("vst_column_addtime")+"")){
						map.put("vst_column_addtime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, ToolsNumber.parseLong(map.get("vst_column_addtime")+"")));
					}
					if(!ToolsString.isEmpty(map.get("vst_column_uptime")+"")){
						map.put("vst_column_uptime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, ToolsNumber.parseLong(map.get("vst_column_uptime")+"")));
					}
					dataList.add(map);
				}
				result.set_dataList(dataList);
			}
		} catch (Exception e) {
			logger.error("查询sql列配置列表失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		// 参数带上
		result.set_queryParam(cutPage.get_queryParam());
		return result;
	}
	
	/**
	 * 新增sql列配置
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public void addSqlColumn(Map<String, Object> param, UserSession user)
			throws SystemException {
		try {
			VstSqlColumn bean = new VstSqlColumn();
			bean = (VstSqlColumn) BeanUtils.mapToBean(param, bean.getClass());
			// 设置id
			bean.setVst_column_id(ToolsRandom.getRandom(6));
			// 设置状态
			bean.setVst_column_state(VstConstants.STATE_AVALIABLE);
			// 设置新增时间
			bean.setVst_column_addtime(System.currentTimeMillis());
			// 设置新增人
			bean.setVst_column_creator(user.getLoginInfo().getLoginName());
			
			// 新增
			int n = _vstSqlColumnDao.insert(bean);
			if(n > 0){
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(),
										VstConstants.OPERATE_TYPE_ADD, 
										LogUtils.logFormat(VstConstants.OPERATE_TYPE_ADD, null, bean)));
			}
		} catch (Exception e) {
			logger.error("新增sql列配置失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
	}
	
	/**
	 * 根据ID，获取sql列配置信息
	 */
	@Override
	public Map<String, Object> getSqlColumnById(String id) throws SystemException {
		Map<String, Object> result = null;
		try {
			result = _vstSqlColumnDao.queryById(id);
		} catch (Exception e) {
			logger.error("根据id获取sql列配置信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改sql列配置
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int updateSqlColumn(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException {
		// 返回结果
		int result = 0;
		try {
			VstSqlColumn bean = new VstSqlColumn();
			bean = (VstSqlColumn) BeanUtils.mapToBean(param, bean.getClass());
			// 设置修改时间
			bean.setVst_column_uptime(System.currentTimeMillis());
			// 设置修改人
			bean.setVst_column_updator(user.getLoginInfo().getLoginName());
			// 更新操作
			result = _vstSqlColumnDao.update(bean);
			
			if(result == 1){
				VstSqlColumn oldBean = null;
				if(oldParam != null){
					oldBean = new VstSqlColumn();
					oldBean = (VstSqlColumn) BeanUtils.mapToBean(oldParam, oldBean.getClass());
				}
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), 
										VstConstants.OPERATE_TYPE_UPDATE, 
										LogUtils.logFormat(VstConstants.OPERATE_TYPE_UPDATE, oldBean, bean)));
			}
		} catch (Exception e) {
			logger.error("更新sql列配置信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 批量删除sql列配置
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int deleteSqlColumn(String ids, UserSession user) {
		int result = 0;
		try {
			Map<String, Object> param = new HashMap<String, Object>(1);
			param.put("list_ids", Arrays.asList(ids.split(",")));
			result = _vstSqlColumnDao.delete(param);
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("批量删除sql列配置, id=").append(ids);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(),
										VstConstants.OPERATE_TYPE_DEL,
										sb.toString()));
			}
		} catch (Exception e) {
			logger.error("批量删除sql列配置失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改sql列配置状态
	 */
	public int modifySqlColumnState(String ids, int state, UserSession user) throws SystemException {
		int result = 0;
		try {
			Map<String, Object> params = new HashMap<String, Object>(1);
			params.put("list_ids", Arrays.asList(ids.split(",")));
			params.put("vst_column_state", state);
			params.put("vst_column_uptime", System.currentTimeMillis());
			params.put("vst_column_updator", user.getLoginInfo().getLoginName());
			result = _vstSqlColumnDao.modifyState(params);
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append(state == 1 ? "启用sql列配置, id=" : "禁用sql列配置, id=").append(ids);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(),
										VstConstants.OPERATE_TYPE_UPDATE,
										sb.toString()));
			}
		} catch (Exception e) {
			logger.error("修改sql列配置状态失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改sql列配置排序
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public void modifySqlColumnIndexs(String ids, String indexs, UserSession user) {
		int result = 0;
		try {
			List<VstSqlColumn> list = new ArrayList<VstSqlColumn>();			
			String[] id = ids.split(",");
			String[] index = indexs.split(",");
			long now = System.currentTimeMillis();
			VstSqlColumn bean = null;
			for(int i = 0; i < id.length; i++){
				bean = new VstSqlColumn();
				bean.setVst_column_id(id[i]);
				bean.setVst_column_index(Integer.parseInt(index[i]));
				bean.setVst_column_uptime(now);
				bean.setVst_column_updator(user.getLoginInfo().getLoginName());
				list.add(bean);
			}
			result = _vstSqlColumnDao.batchUpdate(list);
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("批量修改sql列配置排序, id=").append(ids).append(", 排序值=").append(indexs);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(),
										VstConstants.OPERATE_TYPE_UPDATE,
										sb.toString()));
			}
		} catch (Exception e) {
			logger.error("批量sql列配置排序失败: " + SystemException.getExceptionInfo(e));
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
			List<Map<String, Object>> dataList = _vstSqlColumnDao.queryForList(params);
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
	 * 复制新增sql列配置
	 */
	@Override
	public int copyAddSqlColumn(Map<String, Object> param, UserSession user) throws SystemException {
		int result = 0;
		try {
			if(ToolsString.isEmpty(param.get("vst_column_state")+"")){
				param.put("vst_column_state", VstConstants.STATE_AVALIABLE);
			}
			
			VstSqlColumn bean = new VstSqlColumn();
			bean = (VstSqlColumn) BeanUtils.mapToBean(param, bean.getClass());
			// 设置id
			bean.setVst_column_id(ToolsRandom.getRandom(6));
			// 设置新增时间
			bean.setVst_column_addtime(System.currentTimeMillis());
			// 设置新增人
			bean.setVst_column_creator(user.getLoginInfo().getLoginName());
			
			// 新增
			result = _vstSqlColumnDao.insert(bean);
			if(result > 0){
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(),
										VstConstants.OPERATE_TYPE_ADD, 
										LogUtils.logFormat(VstConstants.OPERATE_TYPE_ADD, null, bean)));
			}
		} catch (Exception e) {
			logger.error("复制新增sql列配置失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
}
