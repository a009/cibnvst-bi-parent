package com.vst.defend.service.outer.impl;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.pojo.OuterVstUserAddChannel;
import com.vst.common.pojo.VstSysOperateLog;
import com.vst.common.tools.date.ToolsDate;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.BeanUtils;
import com.vst.defend.communal.util.CutPage;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.communal.util.VstTools;
import com.vst.defend.dao2.user.VstUserAddChannelDao;
import com.vst.defend.dao3.inner.InnerUserAddChannelDao;
import com.vst.defend.service.outer.InnerUserAddChannelService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author lhp
 * @date 2018-1-19 下午03:48:41
 * @version
 */
@Service
public class InnerUserAddChannelServiceImpl implements InnerUserAddChannelService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(InnerUserAddChannelServiceImpl.class);
	
	/**
	 * 新增渠道用户(对内)Dao接口
	 */
	@Resource
	private InnerUserAddChannelDao _innerUserAddChannelDao;
	
	/**
	 * 新增用户(渠道)Dao接口
	 */
	@Resource
	private VstUserAddChannelDao _vstUserAddChannelDao;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询分页数据
	 */
	@Override
	public ReportBean getCutPageData(CutPage cutPage) throws SystemException {
		ReportBean bean = new ReportBean();
		try{
			Map<String, Object> params = cutPage.get_castQueryParam();
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				params.put("startDay", VstTools.parseInt(params.get("startDay")+""));
				params.put("endDay", VstTools.parseInt(params.get("endDay")+""));
				// 参数特殊化处理
				if(!ToolsString.isEmpty(params.get("vst_uac_channel")+"")){
					VstTools.paramFormat("vst_uac_channel", params.get("vst_uac_channel")+"", params);
				}
			}
			
			int count = _innerUserAddChannelDao.queryCount(params);
			
			if(count != 0){
				int start = (cutPage.get_currPage()-1) * cutPage.get_singleCount();
				
				params.put("offset", start);
				params.put("limit", cutPage.get_singleCount());
				
				List<Map<String, Object>> list = _innerUserAddChannelDao.queryForList(params);
				
				for(Map<String, Object> map : list){
					// 转换日期格式
					map.put("vst_uac_addtime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, ToolsNumber.parseLong(map.get("vst_uac_addtime")+"")));
					map.put("vst_uac_uptime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, ToolsNumber.parseLong(map.get("vst_uac_uptime")+"")));
				}
				
				bean.setTotalSize(count); //设置总行数
				bean.setSingleSize(cutPage.get_singleCount());//设置单页显示行数
				bean.setMapData(list);
			}
		}catch(Exception e){
			logger.error("查询新增渠道用户分页数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
	
	/**
	 * 导出数据
	 */
	@Override
	public ReportBean getExportData(Map<String, Object> params, UserSession user) throws SystemException {
		ReportBean bean = new ReportBean();
		try{
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				params.put("startDay", VstTools.parseInt(params.get("startDay")+""));
				params.put("endDay", VstTools.parseInt(params.get("endDay")+""));
				// 参数特殊化处理
				if(!ToolsString.isEmpty(params.get("vst_uac_channel")+"")){
					VstTools.paramFormat("vst_uac_channel", params.get("vst_uac_channel")+"", params);
				}
			}
			
			List<Map<String, Object>> data = _innerUserAddChannelDao.queryExport(params);
			if(data != null && data.size() > 0){
				
				Map<String, Object> temp = new HashMap<String, Object>(3);
				// 获取包名
				temp.put("vst_table_name", "vst_sys");
				temp.put("vst_column_name", "pkgName");
				temp.put("vst_state", VstConstants.STATE_AVALIABLE);
				Map<String, String> pkgMap = _baseService.getDictionaryForMap(temp);
				
				for(Map<String, Object> map : data){
					//更改日期格式
					String week = ToolsDate.getWeekByDate(map.get("日期")+"", ToolsDate.yyyy_MM_dd4);
					if("周日".equals(week) || "周六".equals(week)){
						map.put("日期", map.get("日期") + " " +week);
					}
					if(pkgMap.containsKey(map.get("包名")+"")){
						map.put("包名", pkgMap.get(map.get("包名")+""));
					}
				}
			}
			
			bean.setMapData(data);
			bean.setTitle("vst_user_add_channel_data");
			
			// 写操作日志
			StringBuilder sb = new StringBuilder();
			sb.append("导出数据，查询条件：").append(params);
			_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_EXPORT, sb.toString()));
		}catch(Exception e){
			logger.error("导出新增渠道用户数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
	
	/**
	 * 查询报表数据(按日期统计)
	 */
	@Override
	public ReportBean getReportByDate(Map<String, Object> params) throws SystemException {
		ReportBean bean = new ReportBean();
		try{
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				params.put("startDay", VstTools.parseInt(params.get("startDay")+""));
				params.put("endDay", VstTools.parseInt(params.get("endDay")+""));
				// 参数特殊化处理
				if(!ToolsString.isEmpty(params.get("vst_uac_channel")+"")){
					VstTools.paramFormat("vst_uac_channel", params.get("vst_uac_channel")+"", params);
				}
			}
			
			List<Map<String, Object>> data = _innerUserAddChannelDao.queryReportByDate(params);
			bean.setMapData(data);
		}catch(Exception e){
			logger.error("查询新增渠道用户(按日期统计)失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}

	/**
	 * 审核数据
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int auditData(String ids, int state, UserSession user) throws SystemException {
		int result = 0;
		try{
			Map<String, Object> params = new HashMap<String, Object>(4);
			params.put("list_ids", Arrays.asList(ids.split(",")));
			params.put("vst_uac_state", state);
			params.put("vst_uac_uptime", System.currentTimeMillis());
			params.put("vst_uac_updator", user.getLoginInfo().getLoginName());
			result = _innerUserAddChannelDao.modifyState(params);
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("审核新增渠道用户数据，ids=").append(ids);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_UPDATE, sb.toString()));
			}
		}catch(Exception e){
			logger.error("审核新增渠道用户数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 批量审核数据
	 */
	@Override
	public int batchAuditData(Map<String, Object> params, UserSession user) throws SystemException {
		int result = 0;
		try {
			if(params != null){
				params.put("startDay", VstTools.parseInt(params.get("startDay")+""));
				params.put("endDay", VstTools.parseInt(params.get("endDay")+""));
				// 参数特殊化处理
				if(!ToolsString.isEmpty(params.get("vst_uac_channel")+"")){
					VstTools.paramFormat("vst_uac_channel", params.get("vst_uac_channel")+"", params);
				}
				params.put("vst_uac_uptime", System.currentTimeMillis());
				params.put("vst_uac_updator", user.getLoginInfo().getLoginName());
				
				result = _innerUserAddChannelDao.auditData(params);
				if(result > 0){
					// 写操作日志
					StringBuilder sb = new StringBuilder();
					sb.append("批量审核数据, 条件=").append(params);
					_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_UPDATE, sb.toString()));
				}
			}
		} catch (Exception e) {
			logger.error("批量审核数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 批量修改调整系数
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int modifyModulus(String ids, String modulus, UserSession user) {
		int result = 0;
		try {
			Map<String, Object> params = new HashMap<String, Object>(4);
			params.put("list_ids", Arrays.asList(ids.split(",")));
			params.put("vst_uac_modulus", modulus);
			params.put("vst_uac_uptime", System.currentTimeMillis());
			params.put("vst_uac_updator", user.getLoginInfo().getLoginName());
			result = _innerUserAddChannelDao.modifyState(params);
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("批量修改调整系数，记录id=").append(ids).append(",系数值=").append(modulus);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_UPDATE, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("批量修改调整系数失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}

	/**
	 * 导入数据
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int importData(String date, UserSession user) throws SystemException {
		int result = 0;
		try {
			if(!ToolsString.isEmpty(date)){
				int day = VstTools.parseInt(date);
				
				// 删除outer表数据
				Map<String, Object> params = new HashMap<String, Object>(2);
				params.put("startDay", day);
				params.put("endDay", day);
				_innerUserAddChannelDao.delete(params);
				
				// 将原表数据导入
				List<Map<String, Object>> srcList = _vstUserAddChannelDao.queryForList(params);
				if(srcList != null && srcList.size() > 0){
					// 获取渠道的调整系数
					String channel_rate = _baseService.getOptionByKey("channel_rate");
					JSONObject channelRate = JSONObject.parseObject(channel_rate);
					
					List<OuterVstUserAddChannel> beanList = new ArrayList<OuterVstUserAddChannel>();
					for(Map<String, Object> map : srcList){
						OuterVstUserAddChannel bean = new OuterVstUserAddChannel();
						bean = (OuterVstUserAddChannel) BeanUtils.mapToBean(map, bean.getClass());
						// 设置调整系数
						String channel = bean.getVst_uac_channel();
						if(channelRate.containsKey(channel)){
							bean.setVst_uac_modulus(channelRate.getString(channel));
						}else{
							bean.setVst_uac_modulus(channelRate.getString("0"));
						}
						// 设置状态
						bean.setVst_uac_state(1);
						// 设置同步时间
						bean.setVst_uac_synctime(System.currentTimeMillis());
						
						beanList.add(bean);
						//_innerUserAddChannelDao.insert(bean);
						//result ++;
					}
					result = _innerUserAddChannelDao.batchInsert(beanList);
					
					if(result > 0){
						// 写操作日志
						StringBuilder sb = new StringBuilder();
						sb.append("导入数据，日期=").append(date);
						_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_ADD, sb.toString()));
					}
				}
			}
		} catch (Exception e) {
			logger.error("导入数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}

	/**
	 * 删除数据
	 */
	@Override
	public int deleteData(Map<String, Object> params, UserSession user) throws SystemException {
		int result = 0;
		try {
			if(params != null){
				params.put("startDay", VstTools.parseInt(params.get("startDay")+""));
				params.put("endDay", VstTools.parseInt(params.get("endDay")+""));
				// 参数特殊化处理
				if(!ToolsString.isEmpty(params.get("vst_uac_channel")+"")){
					VstTools.paramFormat("vst_uac_channel", params.get("vst_uac_channel")+"", params);
				}
				
				result = _innerUserAddChannelDao.delete(params);
				if(result > 0){
					// 写操作日志
					StringBuilder sb = new StringBuilder();
					sb.append("删除新增渠道用户, 条件=").append(params);
					_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_DEL, sb.toString()));
				}
			}
		} catch (Exception e) {
			logger.error("删除新增渠道用户失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}

	/**
	 * 修改数据
	 */
	@Override
	public int updateData(Map<String, Object> params, UserSession user) throws SystemException {
		int result = 0;
		try {
			if(params != null){
				OuterVstUserAddChannel bean = new OuterVstUserAddChannel();
				bean.setVst_uac_id(ToolsString.checkEmpty(params.get("vst_uac_id")));
				bean.setVst_uac_amount(ToolsNumber.parseLong(params.get("vst_uac_amount")+""));
				bean.setVst_uac_modulus(ToolsString.checkEmpty(params.get("vst_uac_modulus")));
				bean.setVst_uac_uptime(System.currentTimeMillis());
				bean.setVst_uac_updator(user.getLoginInfo().getLoginName());

				result = _innerUserAddChannelDao.update(bean);
				if(result > 0){
					// 写操作日志
					StringBuilder sb = new StringBuilder();
					sb.append("修改新增渠道用户, 参数=").append(params);
					_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_UPDATE, sb.toString()));
				}
			}
		} catch (Exception e) {
			logger.error("修改新增渠道用户失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}

	/**
	 * 查询报表数据(按维度统计)
	 */
	@Override
	public ReportBean getReportByDim(Map<String, Object> params) throws SystemException {
		ReportBean bean = new ReportBean();
		try{
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				params.put("startDay", VstTools.parseInt(params.get("startDay")+""));
				params.put("endDay", VstTools.parseInt(params.get("endDay")+""));
				// 参数特殊化处理
				if(!ToolsString.isEmpty(params.get("vst_uac_channel")+"")){
					VstTools.paramFormat("vst_uac_channel", params.get("vst_uac_channel")+"", params);
				}
			}

			List<Map<String, Object>> data = _innerUserAddChannelDao.queryReportByDim(params);
			bean.setMapData(data);
		}catch(Exception e){
			logger.error("查询新增渠道用户(按维度统计)失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
}
