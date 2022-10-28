package com.vst.defend.service.report.impl;

import com.vst.common.pojo.VstAutoPageChart;
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
import com.vst.defend.dao.report.VstAutoPageChartDao;
import com.vst.defend.service.report.VstAutoPageChartService;
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
public class VstAutoPageChartServiceImpl implements VstAutoPageChartService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstAutoPageChartServiceImpl.class);
	
	/**
	 * 自动化页面(统计图)Dao接口
	 */
	@Resource
	private VstAutoPageChartDao _vstAutoPageChartDao;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询自动化页面(统计图)列表
	 */
	@Override
	public CutPage getAutoPageChartList(CutPage cutPage, UserSession user) throws SystemException {
		// 返回结果
		CutPage result = new CutPage();
		try {
			Map<String, Object> params = cutPage.get_castQueryParam();
			if(params == null){
				params = new HashMap<String, Object>();
			}
			
			int count = _vstAutoPageChartDao.queryCount(params);
			
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
				
				List<Map<String, Object>> list = _vstAutoPageChartDao.queryForList(params);
				List<Object> dataList = new ArrayList<Object>();
				
				for(Map<String, Object> map : list){
					//替换时间格式
					if(!ToolsString.isEmpty(map.get("vst_chart_addtime")+"")){
						map.put("vst_chart_addtime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, Long.valueOf(map.get("vst_chart_addtime").toString())));
					}
					if(!ToolsString.isEmpty(map.get("vst_chart_uptime")+"")){
						map.put("vst_chart_uptime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, Long.valueOf(map.get("vst_chart_uptime").toString())));
					}
					
					dataList.add(map);
				}
				result.set_dataList(dataList);
			}
		} catch (Exception e) {
			logger.error("查询自动化页面(统计图)列表失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		// 参数带上
		result.set_queryParam(cutPage.get_queryParam());
		return result;
	}
	
	/**
	 * 新增自动化页面(统计图)
	 */
	@Override
	public void addAutoPageChart(Map<String, Object> param, UserSession user)
			throws SystemException {
		try {
			VstAutoPageChart bean = new VstAutoPageChart();
			bean = (VstAutoPageChart) BeanUtils.mapToBean(param, bean.getClass());
			// 设置主键
			bean.setVst_chart_id(ToolsRandom.getRandom(8));
			// 设置状态
			bean.setVst_chart_state(VstConstants.STATE_AVALIABLE);
			// 设置添加时间
			bean.setVst_chart_addtime(System.currentTimeMillis());
			// 设置创建人
			bean.setVst_chart_creator(user.getLoginInfo().getLoginName());
			// 新增
			int n = _vstAutoPageChartDao.insert(bean);
			
			if(n > 0){
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_ADD, 
						LogUtils.logFormat(VstConstants.OPERATE_TYPE_ADD, null, bean)));
			}
			
		} catch (Exception e) {
			logger.error("新增自动化页面(统计图)失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
	}
	
	/**
	 * 根据ID，获取自动化页面(统计图)信息
	 */
	@Override
	public Map<String, Object> getAutoPageChartById(String id) throws SystemException {
		Map<String, Object> result = null;
		try {
			result = _vstAutoPageChartDao.queryById(id);
		} catch (Exception e) {
			logger.error("根据id获取自动化页面(统计图)信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改自动化页面(统计图)
	 */
	@Override
	public int updateAutoPageChart(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException {
		// 返回结果
		int result = 0;
		try {
			VstAutoPageChart bean = new VstAutoPageChart();
			bean = (VstAutoPageChart) BeanUtils.mapToBean(param, bean.getClass());
			// 设置更新时间
			bean.setVst_chart_uptime(System.currentTimeMillis());
			// 设置修改人
			bean.setVst_chart_updator(user.getLoginInfo().getLoginName());
			// 更新操作
			result = _vstAutoPageChartDao.update(bean);
			
			if(result == 1){
				VstAutoPageChart oldBean = null;
				if(oldParam != null){
					oldBean = new VstAutoPageChart();
					oldBean = (VstAutoPageChart) BeanUtils.mapToBean(oldParam, oldBean.getClass());
				}
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), 
										VstConstants.OPERATE_TYPE_UPDATE, 
										LogUtils.logFormat(VstConstants.OPERATE_TYPE_UPDATE, oldBean, bean)));
			}
		} catch (Exception e) {
			logger.error("更新自动化页面(统计图)信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 批量删除自动化页面(统计图)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int deleteAutoPageChart(String ids, UserSession user) {
		int result = 0;
		try {
			Map<String, Object> param = new HashMap<String, Object>(1);
			param.put("list_ids", Arrays.asList(ids.split(",")));
			result = _vstAutoPageChartDao.delete(param);
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("批量删除自动化页面(统计图), id=").append(ids);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_DEL, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("批量删除自动化页面(统计图)失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改自动化页面(统计图)状态
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int modifyAutoPageChartState(String ids, int state, UserSession user) throws SystemException {
		int result = 0;
		try {
			Map<String, Object> params = new HashMap<String, Object>(1);
			params.put("list_ids", Arrays.asList(ids.split(",")));
			params.put("vst_chart_state", state);
			params.put("vst_chart_uptime", System.currentTimeMillis());
			params.put("vst_chart_updator", user.getLoginInfo().getLoginName());
			result = _vstAutoPageChartDao.modifyState(params);
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append(state == 1 ? "启用自动化页面(统计图)，自动化页面(统计图)id=" : "禁用自动化页面(统计图)，自动化页面(统计图)id=").append(ids)
				.append("，操作人=").append(user.getLoginInfo().getLoginName());
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_UPDATE, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("修改自动化页面(统计图)状态失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改自动化页面(统计图)排序
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public void modifyAutoPageChartIndexs(String ids, String indexs, UserSession user) {
		int result = 0;
		try {
			List<VstAutoPageChart> list = new ArrayList<VstAutoPageChart>();			
			String[] id = ids.split(",");
			String[] index = indexs.split(",");
			long now = System.currentTimeMillis();
			VstAutoPageChart bean = null;
			for(int i = 0; i < id.length; i++){
				bean = new VstAutoPageChart();
				bean.setVst_chart_id(id[i]);
				bean.setVst_chart_index(ToolsNumber.parseInt(index[i]));
				bean.setVst_chart_uptime(now);
				bean.setVst_chart_updator(user.getLoginInfo().getLoginName());
				list.add(bean);
			}
			result = _vstAutoPageChartDao.batchUpdate(list);
			
			if(result > 0){
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_UPDATE, "批量修改自动化页面(统计图)排序，记录id=" + ids + ",排序值=" + indexs));
			}
		} catch (Exception e) {
			logger.error("批量修改自动化页面(统计图)排序失败: " + SystemException.getExceptionInfo(e));
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
			List<Map<String, Object>> dataList = _vstAutoPageChartDao.queryForList(params);
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
