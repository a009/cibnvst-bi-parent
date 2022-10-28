package com.vst.defend.service.outer.impl;

import com.vst.common.pojo.VstSysOperateLog;
import com.vst.common.tools.date.ToolsDate;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.CutPage;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.communal.util.VstTools;
import com.vst.defend.dao3.outer.OuterUserAddChannelDao;
import com.vst.defend.service.outer.OuterUserAddChannelService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author lhp
 * @date 2018-1-16 下午06:24:03
 * @version
 */
@Service
public class OuterUserAddChannelServiceImpl implements OuterUserAddChannelService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(OuterUserAddChannelServiceImpl.class);
	
	/**
	 * 新增渠道用户Dao接口
	 */
	@Resource
	private OuterUserAddChannelDao _outerUserAddChannelDao;
	
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
				// 渠道
				String vst_uac_channel = params.get("vst_uac_channel")+"";
				if(ToolsString.isEmpty(vst_uac_channel) || "all".equals(vst_uac_channel)){
					params.remove("vst_uac_channel");
				}else{
					params.remove("vst_uac_channel");
					params.put("list_channels", Arrays.asList(vst_uac_channel.split(",")));
				}
				// 当前时间
				params.put("now", System.currentTimeMillis());
			}
			
			int count = _outerUserAddChannelDao.queryCount(params);
			
			if(count != 0){
				int start = (cutPage.get_currPage()-1) * cutPage.get_singleCount();
				
				params.put("offset", start);
				params.put("limit", cutPage.get_singleCount());
				
				List<Map<String, Object>> list = _outerUserAddChannelDao.queryForList(params);
				
				for(Map<String, Object> map : list){
					// 转换日期格式
					map.put("vst_uac_addtime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, map.get("vst_uac_addtime")+""));
					map.put("vst_uac_uptime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, map.get("vst_uac_uptime")+""));
					// 计算新增用户
					double vst_uac_modulus = 1;
					if(!ToolsString.isEmpty(map.get("vst_uac_modulus")+"")){
						vst_uac_modulus = ToolsNumber.parseDouble(map.get("vst_uac_modulus")+"");
					}
					long vst_uac_amount = ToolsNumber.parseLong(map.get("vst_uac_amount")+"");
					if(vst_uac_amount != 0 && Math.round(vst_uac_amount * vst_uac_modulus) == 0){
						map.put("vst_uac_amount", 1);
					}else{
						map.put("vst_uac_amount", Math.round(vst_uac_amount * vst_uac_modulus));
					}
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
				// 渠道
				String vst_uac_channel = params.get("vst_uac_channel")+"";
				if(ToolsString.isEmpty(vst_uac_channel) || "all".equals(vst_uac_channel)){
					params.remove("vst_uac_channel");
				}else{
					params.remove("vst_uac_channel");
					params.put("list_channels", Arrays.asList(vst_uac_channel.split(",")));
				}
				// 当前时间
				params.put("now", System.currentTimeMillis());
			}
			
			List<Map<String, Object>> data = _outerUserAddChannelDao.queryExport(params);
			if(data != null && data.size() > 0){
				
				Map<String, Object> temp = new HashMap<String, Object>(3);
				// 获取包名
				temp.put("vst_table_name", "vst_sys");
				temp.put("vst_column_name", "pkgName");
				temp.put("vst_state", VstConstants.STATE_AVALIABLE);
				Map<String, String> pkgMap = _baseService.getDictionaryForMap(temp);
				
				for(Map<String, Object> map : data){
					// 更改日期格式
					String week = ToolsDate.getWeekByDate(map.get("日期")+"", ToolsDate.yyyy_MM_dd4);
					if("周日".equals(week) || "周六".equals(week)){
						map.put("日期", map.get("日期") + " " +week);
					}
					if(pkgMap.containsKey(map.get("包名")+"")){
						map.put("包名", pkgMap.get(map.get("包名")+""));
					}
					// 计算新增用户
					double vst_uac_modulus = 1;
					if(!ToolsString.isEmpty(map.get("调整系数")+"")){
						vst_uac_modulus = ToolsNumber.parseDouble(map.get("调整系数")+"");
					}
					long vst_uac_amount = ToolsNumber.parseLong(map.get("新增用户数")+"");
					if(vst_uac_amount != 0 && Math.round(vst_uac_amount * vst_uac_modulus) == 0){
						map.put("新增用户数", 1);
					}else{
						map.put("新增用户数", Math.round(vst_uac_amount * vst_uac_modulus));
					}
					map.remove("调整系数");
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
				// 渠道
				String vst_uac_channel = params.get("vst_uac_channel")+"";
				if(ToolsString.isEmpty(vst_uac_channel) || "all".equals(vst_uac_channel)){
					params.remove("vst_uac_channel");
				}else{
					params.remove("vst_uac_channel");
					params.put("list_channels", Arrays.asList(vst_uac_channel.split(",")));
				}
				// 当前时间
				params.put("now", System.currentTimeMillis());
			}
			
			List<Map<String, Object>> data = _outerUserAddChannelDao.queryReportByDate(params);
			if(data != null && data.size() > 0){
				for(Map<String, Object> map : data){
					// 计算新增用户
					if(ToolsNumber.parseLong(map.get("old_uac_amount")+"") != 0 && ToolsNumber.parseDouble(map.get("vst_uac_amount")+"") == 0){
						map.put("vst_uac_amount", 1);
					}
				}
			}
			
			bean.setMapData(data);
		}catch(Exception e){
			logger.error("查询新增渠道用户(按日期统计)失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
	
	/**
	 * 查询报表数据(按渠道统计)
	 */
	@Override
	public ReportBean getReportByChannel(Map<String, Object> params) throws SystemException {
		ReportBean bean = new ReportBean();
		try{
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				params.put("startDay", VstTools.parseInt(params.get("startDay")+""));
				params.put("endDay", VstTools.parseInt(params.get("endDay")+""));
				// 渠道
				String vst_uac_channel = params.get("vst_uac_channel")+"";
				if(ToolsString.isEmpty(vst_uac_channel) || "all".equals(vst_uac_channel)){
					params.remove("vst_uac_channel");
				}else{
					params.remove("vst_uac_channel");
					params.put("list_channels", Arrays.asList(vst_uac_channel.split(",")));
				}
				// 当前时间
				params.put("now", System.currentTimeMillis());
			}
			
			List<Map<String, Object>> data = _outerUserAddChannelDao.queryReportByDim(params);
			bean.setMapData(data);
		}catch(Exception e){
			logger.error("查询新增渠道用户(按渠道统计)失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
}
