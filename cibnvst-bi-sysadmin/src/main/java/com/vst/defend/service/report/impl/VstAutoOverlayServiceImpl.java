package com.vst.defend.service.report.impl;

import com.vst.common.pojo.VstAutoOverlay;
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
import com.vst.defend.dao.report.VstAutoOverlayDao;
import com.vst.defend.service.report.VstAutoOverlayService;
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
 * @date 2017-9-12 下午04:05:12
 * @version
 */
@Service
public class VstAutoOverlayServiceImpl implements VstAutoOverlayService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstAutoOverlayServiceImpl.class);
	
	/**
	 * 自动化(sql续加)Dao接口
	 */
	@Resource
	private VstAutoOverlayDao _vstAutoOverlayDao;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询自动化(sql续加)列表
	 */
	@Override
	public CutPage getAutoOverlayList(CutPage cutPage, UserSession user) throws SystemException {
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
			
			int count = _vstAutoOverlayDao.queryCount(params);
			
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
				
				List<Map<String, Object>> list = _vstAutoOverlayDao.queryForList(params);
				List<Object> dataList = new ArrayList<Object>();
				
				for(Map<String, Object> map : list){
					//替换时间格式
					if(!ToolsString.isEmpty(map.get("vst_overlay_addtime")+"")){
						map.put("vst_overlay_addtime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, Long.valueOf(map.get("vst_overlay_addtime").toString())));
					}
					if(!ToolsString.isEmpty(map.get("vst_overlay_uptime")+"")){
						map.put("vst_overlay_uptime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, Long.valueOf(map.get("vst_overlay_uptime").toString())));
					}
					
					dataList.add(map);
				}
				result.set_dataList(dataList);
			}
		} catch (Exception e) {
			logger.error("查询自动化(sql续加)列表失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		// 参数带上
		result.set_queryParam(cutPage.get_queryParam());
		return result;
	}
	
	/**
	 * 新增自动化(sql续加)
	 */
	@Override
	public void addAutoOverlay(Map<String, Object> param, UserSession user)
			throws SystemException {
		try {
			VstAutoOverlay bean = new VstAutoOverlay();
			bean = (VstAutoOverlay) BeanUtils.mapToBean(param, bean.getClass());
			// 设置主键
			bean.setVst_overlay_id(ToolsRandom.getRandom(8));
			// 设置状态
			bean.setVst_overlay_state(VstConstants.STATE_AVALIABLE);
			// 设置添加时间
			bean.setVst_overlay_addtime(System.currentTimeMillis());
			// 设置创建人
			bean.setVst_overlay_creator(user.getLoginInfo().getLoginName());
			// 新增
			int n = _vstAutoOverlayDao.insert(bean);
			
			if(n > 0){
				// 刷新缓存
				flushCache();
				
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_ADD, 
						LogUtils.logFormat(VstConstants.OPERATE_TYPE_ADD, null, bean)));
			}
			
		} catch (Exception e) {
			logger.error("新增自动化(sql续加)失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
	}
	
	/**
	 * 根据ID，获取自动化(sql续加)信息
	 */
	@Override
	public Map<String, Object> getAutoOverlayById(String id) throws SystemException {
		Map<String, Object> result = null;
		try {
			result = _vstAutoOverlayDao.queryById(id);
		} catch (Exception e) {
			logger.error("根据id获取自动化(sql续加)信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改自动化(sql续加)
	 */
	@Override
	public int updateAutoOverlay(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException {
		// 返回结果
		int result = 0;
		try {
			VstAutoOverlay bean = new VstAutoOverlay();
			bean = (VstAutoOverlay) BeanUtils.mapToBean(param, bean.getClass());
			// 设置更新时间
			bean.setVst_overlay_uptime(System.currentTimeMillis());
			// 设置修改人
			bean.setVst_overlay_updator(user.getLoginInfo().getLoginName());
			// 更新操作
			result = _vstAutoOverlayDao.update(bean);
			
			if(result == 1){
				// 刷新缓存
				flushCache();
				
				VstAutoOverlay oldBean = null;
				if(oldParam != null){
					oldBean = new VstAutoOverlay();
					oldBean = (VstAutoOverlay) BeanUtils.mapToBean(oldParam, oldBean.getClass());
				}
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), 
										VstConstants.OPERATE_TYPE_UPDATE, 
										LogUtils.logFormat(VstConstants.OPERATE_TYPE_UPDATE, oldBean, bean)));
			}
		} catch (Exception e) {
			logger.error("更新自动化(sql续加)信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 批量删除自动化(sql续加)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int deleteAutoOverlay(String ids, UserSession user) {
		int result = 0;
		try {
			Map<String, Object> param = new HashMap<String, Object>(1);
			param.put("list_ids", Arrays.asList(ids.split(",")));
			result = _vstAutoOverlayDao.delete(param);
			
			if(result > 0){
				// 刷新缓存
				flushCache();
				
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("批量删除自动化(sql续加), id=").append(ids);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_DEL, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("批量删除自动化(sql续加)失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改自动化(sql续加)状态
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int modifyAutoOverlayState(String ids, int state, UserSession user) throws SystemException {
		int result = 0;
		try {
			Map<String, Object> params = new HashMap<String, Object>(1);
			params.put("list_ids", Arrays.asList(ids.split(",")));
			params.put("vst_overlay_state", state);
			params.put("vst_overlay_uptime", System.currentTimeMillis());
			params.put("vst_overlay_updator", user.getLoginInfo().getLoginName());
			result = _vstAutoOverlayDao.modifyState(params);
			
			if(result > 0){
				// 刷新缓存
				flushCache();
				
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append(state == 1 ? "启用自动化(sql续加)，自动化(sql续加)id=" : "禁用自动化(sql续加)，自动化(sql续加)id=").append(ids)
				.append("，操作人=").append(user.getLoginInfo().getLoginName());
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_UPDATE, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("修改自动化(sql续加)状态失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改自动化(sql续加)排序
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public void modifyAutoOverlayIndexs(String ids, String indexs, UserSession user) {
		int result = 0;
		try {
			List<VstAutoOverlay> list = new ArrayList<VstAutoOverlay>();			
			String[] id = ids.split(",");
			String[] index = indexs.split(",");
			long now = System.currentTimeMillis();
			VstAutoOverlay bean = null;
			for(int i = 0; i < id.length; i++){
				bean = new VstAutoOverlay();
				bean.setVst_overlay_id(id[i]);
				bean.setVst_overlay_index(ToolsNumber.parseInt(index[i]));
				bean.setVst_overlay_uptime(now);
				bean.setVst_overlay_updator(user.getLoginInfo().getLoginName());
				list.add(bean);
			}
			result = _vstAutoOverlayDao.batchUpdate(list);
			
			if(result > 0){
				// 刷新缓存
				flushCache();
				
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_UPDATE, "批量修改自动化(sql续加)排序，记录id=" + ids + ",排序值=" + indexs));
			}
		} catch (Exception e) {
			logger.error("批量修改自动化(sql续加)排序失败: " + SystemException.getExceptionInfo(e));
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
			List<Map<String, Object>> dataList = _vstAutoOverlayDao.queryForList(params);
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
	 * 复制续加
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int copyOverlay(String ids, String targetCode, UserSession user){
		int result = 0;
		try {
			Long now = System.currentTimeMillis();
			Map<String, Object> params = new HashMap<String, Object>(1);
			params.put("list_ids", Arrays.asList(ids.split(",")));
			List<Map<String, Object>> data = _vstAutoOverlayDao.queryForList(params);
			List<VstAutoOverlay> list = new ArrayList<VstAutoOverlay>();
			
			for(Map<String, Object> map : data){
				VstAutoOverlay bean = new VstAutoOverlay();
				bean = (VstAutoOverlay) BeanUtils.mapToBean(map, bean.getClass());
				// 设置主键
				bean.setVst_overlay_id(ToolsRandom.getRandom(8));
				// 设置新增时间
				bean.setVst_overlay_addtime(now);
				// 设置创建人
				bean.setVst_overlay_creator(user.getLoginInfo().getLoginName());
				
				// 设置代码编号
				bean.setVst_code(targetCode);
				
				list.add(bean);
			}
			
			// 批量添加
			result = _vstAutoOverlayDao.batchInsert(list);
			
			if(result > 0){
				// 刷新缓存
				flushCache();
				
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("复制续加信息, id=").append(ids).append(",目标编号=").append(targetCode);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_ADD, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("复制续加失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 替换信息
	 */
	@Override
	public int replaceOverlay(String ids, int replace_type, String replace_before, String replace_after, UserSession user){
		int result = 0;
		try{
			Map<String, Object> params = new HashMap<String, Object>();
			if(!ToolsString.isEmpty(ids)){
				params.put("list_ids", Arrays.asList(ids.split(",")));
			}
			
			Long now = System.currentTimeMillis();
			List<VstAutoOverlay> listBean = new ArrayList<VstAutoOverlay>();
			
			List<Map<String, Object>> listMap = _vstAutoOverlayDao.queryForList(params);
			
			for(Map<String, Object> map : listMap){
				VstAutoOverlay bean = new VstAutoOverlay();
				bean.setVst_overlay_id(map.get("vst_overlay_id")+"");
				bean.setVst_overlay_uptime(now);
				bean.setVst_overlay_updator(user.getLoginInfo().getLoginName());
				
				if(replace_type == 1){	//1代码编号
					String vst_code = (map.get("vst_code")+"").replace(replace_before, replace_after);
					bean.setVst_code(vst_code);
				}else if(replace_type == 2){	//2脚本
					String vst_overlay_script = (map.get("vst_overlay_script")+"").replace(replace_before, replace_after);
					bean.setVst_overlay_script(vst_overlay_script);
				}
				
				listBean.add(bean);
			}
			
			// 批量修改
			result = _vstAutoOverlayDao.batchUpdate(listBean);//批量修改集数
			
			if(result > 0){
				// 刷新缓存
				flushCache();
				
				// 写操作日志
				StringBuilder sb = new StringBuilder("替换信息，");
				sb.append("ids=").append(ids);
				sb.append("，replace_type=").append(replace_type);
				sb.append("，replace_before=").append(replace_before);
				sb.append("，replace_after=").append(replace_after);
				sb.append("，操作人=").append(user.getLoginInfo().getLoginName());
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_UPDATE, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("替换信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 刷新缓存
	 */
	private void flushCache(){
		CacheSysConfig cache = CacheSysConfig.getInstance();
		cache.putDataMap("autoOverlay", _vstAutoOverlayDao.queryForList(null));
	}
}
