package com.vst.defend.service.report.impl;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.pojo.VstAutoMain;
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
import com.vst.defend.dao.report.VstAutoMainDao;
import com.vst.defend.dao.report.VstAutoOverlayDao;
import com.vst.defend.service.report.VstAutoMainService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
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
 * @date 2017-9-12 下午04:05:12
 * @version
 */
@Service
public class VstAutoMainServiceImpl implements VstAutoMainService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstAutoMainServiceImpl.class);
	
	/**
	 * 自动化(主表)Dao接口
	 */
	@Resource
	private VstAutoMainDao _vstAutoMainDao;
	
	/**
	 * 自动化(条件)Dao接口
	 */
	@Resource
	private VstAutoConditionDao _vstAutoConditionDao;
	
	/**
	 * 自动化(续加)Dao接口
	 */
	@Resource
	private VstAutoOverlayDao _vstAutoOverlayDao;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询自动化(主表)列表
	 */
	@Override
	public CutPage getAutoMainList(CutPage cutPage, UserSession user) throws SystemException {
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
			}
			
			int count = _vstAutoMainDao.queryCount(params);
			
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
				
				List<Map<String, Object>> list = _vstAutoMainDao.queryForList(params);
				List<Object> dataList = new ArrayList<Object>();
				
				for(Map<String, Object> map : list){
					//替换时间格式
					if(!ToolsString.isEmpty(map.get("vst_main_addtime")+"")){
						map.put("vst_main_addtime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, Long.valueOf(map.get("vst_main_addtime").toString())));
					}
					if(!ToolsString.isEmpty(map.get("vst_main_uptime")+"")){
						map.put("vst_main_uptime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, Long.valueOf(map.get("vst_main_uptime").toString())));
					}
					
					dataList.add(map);
				}
				result.set_dataList(dataList);
			}
		} catch (Exception e) {
			logger.error("查询自动化(主表)列表失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		// 参数带上
		result.set_queryParam(cutPage.get_queryParam());
		return result;
	}
	
	/**
	 * 新增自动化(主表)
	 */
	@Override
	public void addAutoMain(Map<String, Object> param, UserSession user)
			throws SystemException {
		try {
			VstAutoMain bean = new VstAutoMain();
			bean = (VstAutoMain) BeanUtils.mapToBean(param, bean.getClass());
			// 设置主键
			bean.setVst_main_id(ToolsRandom.getRandom(6));
			// 设置状态
			bean.setVst_main_state(VstConstants.STATE_AVALIABLE);
			// 设置添加时间
			bean.setVst_main_addtime(System.currentTimeMillis());
			// 设置创建人
			bean.setVst_main_creator(user.getLoginInfo().getLoginName());
			// 新增
			int n = _vstAutoMainDao.insert(bean);
			
			if(n > 0){
				// 刷新缓存
				flushCache();
				
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_ADD, 
						LogUtils.logFormat(VstConstants.OPERATE_TYPE_ADD, null, bean)));
			}
			
		} catch (Exception e) {
			logger.error("新增自动化(主表)失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
	}
	
	/**
	 * 根据ID，获取自动化(主表)信息
	 */
	@Override
	public Map<String, Object> getAutoMainById(String id) throws SystemException {
		Map<String, Object> result = null;
		try {
			result = _vstAutoMainDao.queryById(id);
		} catch (Exception e) {
			logger.error("根据id获取自动化(主表)信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改自动化(主表)
	 */
	@Override
	public int updateAutoMain(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException {
		// 返回结果
		int result = 0;
		try {
			VstAutoMain bean = new VstAutoMain();
			bean = (VstAutoMain) BeanUtils.mapToBean(param, bean.getClass());
			// 设置更新时间
			bean.setVst_main_uptime(System.currentTimeMillis());
			// 设置修改人
			bean.setVst_main_updator(user.getLoginInfo().getLoginName());
			// 更新操作
			result = _vstAutoMainDao.update(bean);
			
			if(result == 1){
				// 刷新缓存
				flushCache();
				
				VstAutoMain oldBean = null;
				if(oldParam != null){
					oldBean = new VstAutoMain();
					oldBean = (VstAutoMain) BeanUtils.mapToBean(oldParam, oldBean.getClass());
				}
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), 
										VstConstants.OPERATE_TYPE_UPDATE, 
										LogUtils.logFormat(VstConstants.OPERATE_TYPE_UPDATE, oldBean, bean)));
			}
		} catch (Exception e) {
			logger.error("更新自动化(主表)信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 批量删除自动化(主表)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int deleteAutoMain(String ids, UserSession user) {
		int result = 0;
		try {
			Map<String, Object> param = new HashMap<String, Object>(1);
			param.put("list_ids", Arrays.asList(ids.split(",")));
			result = _vstAutoMainDao.delete(param);
			
			if(result > 0){
				// 刷新缓存
				flushCache();
				
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("批量删除自动化(主表), id=").append(ids);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_DEL, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("批量删除自动化(主表)失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改自动化(主表)状态
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int modifyAutoMainState(String ids, int state, UserSession user) throws SystemException {
		int result = 0;
		try {
			Map<String, Object> params = new HashMap<String, Object>(1);
			params.put("list_ids", Arrays.asList(ids.split(",")));
			params.put("vst_main_state", state);
			params.put("vst_main_uptime", System.currentTimeMillis());
			params.put("vst_main_updator", user.getLoginInfo().getLoginName());
			result = _vstAutoMainDao.modifyState(params);
			
			if(result > 0){
				// 刷新缓存
				flushCache();
				
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append(state == 1 ? "启用自动化(主表)，自动化(主表)id=" : "禁用自动化(主表)，自动化(主表)id=").append(ids)
				.append("，操作人=").append(user.getLoginInfo().getLoginName());
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_UPDATE, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("修改自动化(主表)状态失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改自动化(主表)排序
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public void modifyAutoMainIndexs(String ids, String indexs, UserSession user) {
		int result = 0;
		try {
			List<VstAutoMain> list = new ArrayList<VstAutoMain>();			
			String[] id = ids.split(",");
			String[] index = indexs.split(",");
			long now = System.currentTimeMillis();
			VstAutoMain bean = null;
			for(int i = 0; i < id.length; i++){
				bean = new VstAutoMain();
				bean.setVst_main_id(id[i]);
				bean.setVst_main_index(ToolsNumber.parseInt(index[i]));
				bean.setVst_main_uptime(now);
				bean.setVst_main_updator(user.getLoginInfo().getLoginName());
				list.add(bean);
			}
			result = _vstAutoMainDao.batchUpdate(list);
			
			if(result > 0){
				// 刷新缓存
				flushCache();
				
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_UPDATE, "批量修改自动化(主表)排序，记录id=" + ids + ",排序值=" + indexs));
			}
		} catch (Exception e) {
			logger.error("批量修改自动化(主表)排序失败: " + SystemException.getExceptionInfo(e));
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
			List<Map<String, Object>> dataList = _vstAutoMainDao.queryForList(params);
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
	 * 查看树形结构
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getTreeByCode(String vst_code){
		JSONObject result = new JSONObject();
		try {
			// 获取缓存对象
			CacheSysConfig cache = CacheSysConfig.getInstance();
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("vst_main_state", VstConstants.STATE_AVALIABLE);
			params.put("vst_code", vst_code);
			List<Map<String, Object>> mainList = cache.getDataMap("autoMain", params);
			
			params.put("vst_condition_state", VstConstants.STATE_AVALIABLE);
			List<Map<String, Object>> conditionList = cache.getDataMap("autoCondition", params);
			
			params.put("vst_overlay_state", VstConstants.STATE_AVALIABLE);
			List<Map<String, Object>> overlayList = cache.getDataMap("autoOverlay", params);
			
			JSONArray mains = new JSONArray();
			for(Map<String, Object> map : mainList){
				mains.add((map.get("vst_main_script")+"").replace("'", "&apos;"));
			}
			
			JSONArray conditions = new JSONArray();
			for(Map<String, Object> map : conditionList){
				//conditions.add((map.get("vst_condition_script")+"").replace("'", "&apos;"));
				JSONObject bean = new JSONObject();
				bean.put("vst_condition_script", (map.get("vst_condition_script")+"").replace("'", "&apos;"));
				bean.put("vst_condition_arg", map.get("vst_condition_arg"));
				bean.put("vst_condition_need", map.get("vst_condition_need"));
				bean.put("vst_condition_type", map.get("vst_condition_type"));
				conditions.add(bean);
			}
			
			JSONArray overlays = new JSONArray();
			for(Map<String, Object> map : overlayList){
				overlays.add((map.get("vst_overlay_script")+"").replace("'", "&apos;"));
			}
			
			result.put("mains", mains);
			result.put("conditions", conditions);
			result.put("overlays", overlays);
		} catch (Exception e) {
			logger.error("查看树形结构失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 根据代码编号彻底删除
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int realDeleteByCode(String vst_code, UserSession user) {
		int result = 0;
		try {
			Map<String, Object> params = new HashMap<String, Object>(1);
			params.put("vst_code", vst_code);
			result = _vstAutoMainDao.delete(params);
			
			if(result > 0){
				// 删除相关联的代码编号数据
				_vstAutoConditionDao.delete(params);
				_vstAutoOverlayDao.delete(params);
				
				// 刷新缓存
				CacheSysConfig cache = CacheSysConfig.getInstance();
				cache.putDataMap("autoMain", _vstAutoMainDao.queryForList(null));
				cache.putDataMap("autoCondition", _vstAutoConditionDao.queryForList(null));
				cache.putDataMap("autoOverlay", _vstAutoOverlayDao.queryForList(null));
				
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("根据代码编号彻底删除, vst_code=").append(vst_code);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_DEL, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("根据代码编号彻底删除失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 刷新自动化缓存
	 */
	@Override
	public boolean flushAutoCache(UserSession user) throws SystemException {
		boolean result = true;
		try {
			CacheSysConfig cache = CacheSysConfig.getInstance();
			cache.putDataMap("autoMain", _vstAutoMainDao.queryForList(null));
			cache.putDataMap("autoCondition", _vstAutoConditionDao.queryForList(null));
			cache.putDataMap("autoOverlay", _vstAutoOverlayDao.queryForList(null));
			// 写操作日志
			_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_UPDATE, "刷新自动化缓存"));
		} catch (Exception e) {
			result = false;
			logger.error("刷新自动化缓存失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 刷新缓存
	 */
	private void flushCache(){
		CacheSysConfig cache = CacheSysConfig.getInstance();
		cache.putDataMap("autoMain", _vstAutoMainDao.queryForList(null));
	}
}
