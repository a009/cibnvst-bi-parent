package com.vst.defend.service.report.impl;

import com.vst.common.pojo.VstAutoCondition;
import com.vst.common.pojo.VstSysOperateLog;
import com.vst.common.tools.date.ToolsDate;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.number.ToolsRandom;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.cache.CacheSysConfig;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.BeanUtils;
import com.vst.defend.communal.util.CutPage;
import com.vst.defend.communal.util.LogUtils;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.communal.util.VstTools;
import com.vst.defend.dao.report.VstAutoConditionDao;
import com.vst.defend.service.report.VstAutoConditionService;
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
 * @date 2017-9-12 下午03:43:48
 * @version
 */
@Service
public class VstAutoConditionServiceImpl implements VstAutoConditionService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstAutoConditionServiceImpl.class);
	
	/**
	 * 自动化(查询条件)Dao接口
	 */
	@Resource
	private VstAutoConditionDao _vstAutoConditionDao;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询自动化(查询条件)列表
	 */
	@Override
	public CutPage getAutoConditionList(CutPage cutPage, UserSession user) throws SystemException {
		// 返回结果
		CutPage result = new CutPage();
		try {
			Map<String, Object> params = cutPage.get_castQueryParam();
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				// 参数特殊化处理
				if(!ToolsString.isEmpty(params.get("vst_code")+"")){
					VstTools.paramFormat("vst_code", params.get("vst_code")+"", params);
				}
				if(!ToolsString.isEmpty(params.get("vst_condition_arg")+"")){
					VstTools.paramFormat("vst_condition_arg", params.get("vst_condition_arg")+"", params);
				}
			}
			
			int count = _vstAutoConditionDao.queryCount(params);
			
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
				
				List<Map<String, Object>> list = _vstAutoConditionDao.queryForList(params);
				List<Object> dataList = new ArrayList<Object>();
				
				for(Map<String, Object> map : list){
					//替换时间格式
					if(!ToolsString.isEmpty(map.get("vst_condition_addtime")+"")){
						map.put("vst_condition_addtime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, Long.valueOf(map.get("vst_condition_addtime").toString())));
					}
					if(!ToolsString.isEmpty(map.get("vst_condition_uptime")+"")){
						map.put("vst_condition_uptime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, Long.valueOf(map.get("vst_condition_uptime").toString())));
					}
					
					dataList.add(map);
				}
				result.set_dataList(dataList);
			}
		} catch (Exception e) {
			logger.error("查询自动化(查询条件)列表失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		// 参数带上
		result.set_queryParam(cutPage.get_queryParam());
		return result;
	}
	
	/**
	 * 新增自动化(查询条件)
	 */
	@Override
	public void addAutoCondition(Map<String, Object> param, UserSession user)
			throws SystemException {
		try {
			VstAutoCondition bean = new VstAutoCondition();
			bean = (VstAutoCondition) BeanUtils.mapToBean(param, bean.getClass());
			// 设置主键
			bean.setVst_condition_id(ToolsRandom.getRandom(10));
			// 设置状态
			bean.setVst_condition_state(VstConstants.STATE_AVALIABLE);
			// 设置添加时间
			bean.setVst_condition_addtime(System.currentTimeMillis());
			// 设置创建人
			bean.setVst_condition_creator(user.getLoginInfo().getLoginName());
			// 新增
			int n = _vstAutoConditionDao.insert(bean);
			
			if(n > 0){
				// 刷新缓存
				flushCache();
				
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_ADD, 
						LogUtils.logFormat(VstConstants.OPERATE_TYPE_ADD, null, bean)));
			}
			
		} catch (Exception e) {
			logger.error("新增自动化(查询条件)失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
	}
	
	/**
	 * 根据ID，获取自动化(查询条件)信息
	 */
	@Override
	public Map<String, Object> getAutoConditionById(String id) throws SystemException {
		Map<String, Object> result = null;
		try {
			result = _vstAutoConditionDao.queryById(id);
		} catch (Exception e) {
			logger.error("根据id获取自动化(查询条件)信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改自动化(查询条件)
	 */
	@Override
	public int updateAutoCondition(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException {
		// 返回结果
		int result = 0;
		try {
			VstAutoCondition bean = new VstAutoCondition();
			bean = (VstAutoCondition) BeanUtils.mapToBean(param, bean.getClass());
			// 设置更新时间
			bean.setVst_condition_uptime(System.currentTimeMillis());
			// 设置修改人
			bean.setVst_condition_updator(user.getLoginInfo().getLoginName());
			// 更新操作
			result = _vstAutoConditionDao.update(bean);
			
			if(result == 1){
				// 刷新缓存
				flushCache();
				
				VstAutoCondition oldBean = null;
				if(oldParam != null){
					oldBean = new VstAutoCondition();
					oldBean = (VstAutoCondition) BeanUtils.mapToBean(oldParam, oldBean.getClass());
				}
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), 
										VstConstants.OPERATE_TYPE_UPDATE, 
										LogUtils.logFormat(VstConstants.OPERATE_TYPE_UPDATE, oldBean, bean)));
			}
		} catch (Exception e) {
			logger.error("更新自动化(查询条件)信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 批量删除自动化(查询条件)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int deleteAutoCondition(String ids, UserSession user) {
		int result = 0;
		try {
			Map<String, Object> param = new HashMap<String, Object>(1);
			param.put("list_ids", Arrays.asList(ids.split(",")));
			result = _vstAutoConditionDao.delete(param);
			
			if(result > 0){
				// 刷新缓存
				flushCache();
				
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("批量删除自动化(查询条件), id=").append(ids);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_DEL, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("批量删除自动化(查询条件)失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改自动化(查询条件)状态
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int modifyAutoConditionState(String ids, int state, UserSession user) throws SystemException {
		int result = 0;
		try {
			Map<String, Object> params = new HashMap<String, Object>(1);
			params.put("list_ids", Arrays.asList(ids.split(",")));
			params.put("vst_condition_state", state);
			params.put("vst_condition_uptime", System.currentTimeMillis());
			params.put("vst_condition_updator", user.getLoginInfo().getLoginName());
			result = _vstAutoConditionDao.modifyState(params);
			
			if(result > 0){
				// 刷新缓存
				flushCache();
				
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append(state == 1 ? "启用自动化(查询条件)，自动化(查询条件)id=" : "禁用自动化(查询条件)，自动化(查询条件)id=").append(ids)
				.append("，操作人=").append(user.getLoginInfo().getLoginName());
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_UPDATE, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("修改自动化(查询条件)状态失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改自动化(查询条件)排序
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public void modifyAutoConditionIndexs(String ids, String indexs, UserSession user) {
		int result = 0;
		try {
			List<VstAutoCondition> list = new ArrayList<VstAutoCondition>();			
			String[] id = ids.split(",");
			String[] index = indexs.split(",");
			long now = System.currentTimeMillis();
			VstAutoCondition bean = null;
			for(int i = 0; i < id.length; i++){
				bean = new VstAutoCondition();
				bean.setVst_condition_id(id[i]);
				bean.setVst_condition_index(ToolsNumber.parseInt(index[i]));
				bean.setVst_condition_uptime(now);
				bean.setVst_condition_updator(user.getLoginInfo().getLoginName());
				list.add(bean);
			}
			result = _vstAutoConditionDao.batchUpdate(list);
			
			if(result > 0){
				// 刷新缓存
				flushCache();
				
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_UPDATE, "批量修改自动化(查询条件)排序，记录id=" + ids + ",排序值=" + indexs));
			}
		} catch (Exception e) {
			logger.error("批量修改自动化(查询条件)排序失败: " + SystemException.getExceptionInfo(e));
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
			List<Map<String, Object>> dataList = _vstAutoConditionDao.queryForList(params);
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
	 * 根据代码编号获取查询条件
	 */
	@Override
	public List<String> getConditionByCode(String code) throws SystemException {
		List<String> result = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>(2);
			params.put("vst_condition_state", VstConstants.STATE_AVALIABLE);
			params.put("vst_condition_code", code);
			
			List<Map<String, Object>> data = _vstAutoConditionDao.queryForList(params);
			if(data != null && data.size() > 0){
				result = new ArrayList<String>(data.size());
				for(Map<String, Object> map : data){
					String vst_condition_arg = ToolsString.checkEmpty(map.get("vst_condition_arg"));
					result.add(vst_condition_arg);
				}
			}
		} catch (Exception e) {
			logger.error("获取查询条件失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 复制条件
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int copyCondition(String ids, String targetCode, UserSession user){
		int result = 0;
		try {
			Long now = System.currentTimeMillis();
			Map<String, Object> params = new HashMap<String, Object>(1);
			params.put("list_ids", Arrays.asList(ids.split(",")));
			List<Map<String, Object>> data = _vstAutoConditionDao.queryForList(params);
			List<VstAutoCondition> list = new ArrayList<VstAutoCondition>();
			
			for(Map<String, Object> map : data){
				VstAutoCondition bean = new VstAutoCondition();
				bean = (VstAutoCondition) BeanUtils.mapToBean(map, bean.getClass());
				// 设置主键
				bean.setVst_condition_id(ToolsRandom.getRandom(10));
				// 设置新增时间
				bean.setVst_condition_addtime(now);
				// 设置创建人
				bean.setVst_condition_creator(user.getLoginInfo().getLoginName());
				
				// 设置代码编号
				bean.setVst_code(targetCode);
				
				list.add(bean);
			}
			
			// 批量添加
			result = _vstAutoConditionDao.batchInsert(list);
			
			if(result > 0){
				// 刷新缓存
				flushCache();
				
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("复制条件信息, id=").append(ids).append(",目标编号=").append(targetCode);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_ADD, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("复制条件失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 替换条件
	 */
	@Override
	public int replaceCondition(String ids, int replace_type, String replace_before, String replace_after, UserSession user){
		int result = 0;
		try{
			Map<String, Object> params = new HashMap<String, Object>();
			if(!ToolsString.isEmpty(ids)){
				params.put("list_ids", Arrays.asList(ids.split(",")));
			}
			
			Long now = System.currentTimeMillis();
			List<VstAutoCondition> listBean = new ArrayList<VstAutoCondition>();
			
			List<Map<String, Object>> listMap = _vstAutoConditionDao.queryForList(params);
			
			for(Map<String, Object> map : listMap){
				VstAutoCondition bean = new VstAutoCondition();
				bean.setVst_condition_id(map.get("vst_condition_id")+"");
				bean.setVst_condition_uptime(now);
				bean.setVst_condition_updator(user.getLoginInfo().getLoginName());
				
				if(replace_type == 1){	//1代码编号
					String vst_code = (map.get("vst_code")+"").replace(replace_before, replace_after);
					bean.setVst_code(vst_code);
				}else if(replace_type == 2){	//2参数
					String vst_condition_arg = (map.get("vst_condition_arg")+"").replace(replace_before, replace_after);
					bean.setVst_condition_arg(vst_condition_arg);
				}else if(replace_type == 3){	//3脚本
					String vst_condition_script = (map.get("vst_condition_script")+"").replace(replace_before, replace_after);
					bean.setVst_condition_script(vst_condition_script);
				}
				
				listBean.add(bean);
			}
			
			// 批量修改
			result = _vstAutoConditionDao.batchUpdate(listBean);//批量修改集数
			
			if(result > 0){
				// 刷新缓存
				flushCache();
				
				// 写操作日志
				StringBuilder sb = new StringBuilder("替换条件，");
				sb.append("ids=").append(ids);
				sb.append("，replace_type=").append(replace_type);
				sb.append("，replace_before=").append(replace_before);
				sb.append("，replace_after=").append(replace_after);
				sb.append("，操作人=").append(user.getLoginInfo().getLoginName());
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_UPDATE, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("替换条件失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 刷新缓存
	 */
	private void flushCache(){
		CacheSysConfig cache = CacheSysConfig.getInstance();
		cache.putDataMap("autoCondition", _vstAutoConditionDao.queryForList(null));
	}
}
