package com.vst.defend.service.umeng.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vst.common.pojo.VstSysOperateLog;
import com.vst.common.pojo.VstUmengEventMessage;
import com.vst.common.tools.date.ToolsDate;
import com.vst.common.tools.http.ToolsHttp;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.number.ToolsRandom;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.CutPage;
import com.vst.defend.communal.util.JsonUtils;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.communal.util.VstTools;
import com.vst.defend.dao3.umeng.VstUmengEventMessageDao;
import com.vst.defend.service.umeng.VstUmengEventMessageService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author lhp
 * @date 2018-3-15 下午12:13:16
 * @version
 */
@Service
public class VstUmengEventMessageServiceImpl implements VstUmengEventMessageService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstUmengEventMessageServiceImpl.class);
	
	/**
	 * 事件消息Dao接口
	 */
	@Resource
	private VstUmengEventMessageDao _vstUmengEventMessageDao;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 事件消息抓取接口
	 */
	private static String EVENT_MESSAGE_URL = "https://mobile.umeng.com/ht/api/v3/app/event/analysis/property/string/detail?relatedId=53eb1622fd98c52bb000343e&dataSourceId=53eb1622fd98c52bb000343e";
	
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
				if(!ToolsString.isEmpty(params.get("vst_uem_param_value")+"")){
					VstTools.paramFormat("vst_uem_param_value", params.get("vst_uem_param_value")+"", params);
				}
			}
			
			int count = _vstUmengEventMessageDao.queryCount(params);
			
			if(count != 0){
				int start = (cutPage.get_currPage()-1) * cutPage.get_singleCount();
				
				params.put("offset", start);
				params.put("limit", cutPage.get_singleCount());
				
				List<Map<String, Object>> list = _vstUmengEventMessageDao.queryForList(params);
				
				for(Map<String, Object> map : list){
					// 转换日期格式
					map.put("vst_uem_addtime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, ToolsNumber.parseLong(map.get("vst_uem_addtime")+"")));
					map.put("vst_uem_uptime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, ToolsNumber.parseLong(map.get("vst_uem_uptime")+"")));
				}
				
				bean.setTotalSize(count); //设置总行数
				bean.setSingleSize(cutPage.get_singleCount());//设置单页显示行数
				bean.setMapData(list);
			}
		}catch(Exception e){
			logger.error("查询事件消息分页数据失败: " + SystemException.getExceptionInfo(e));
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
				if(!ToolsString.isEmpty(params.get("vst_uem_param_value")+"")){
					VstTools.paramFormat("vst_uem_param_value", params.get("vst_uem_param_value")+"", params);
				}
			}
			
			List<Map<String, Object>> data = _vstUmengEventMessageDao.queryExport(params);
			if(data != null && data.size() > 0){
				// 导出字段内容
				String export_column = ToolsString.checkEmpty(params.get("export_column"));
				List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
				
				for(Map<String, Object> map : data){
					// 只保留关键字段
					Map<String, Object> tmp = new LinkedHashMap<String, Object>();
					for(String key : map.keySet()){
						if(export_column.indexOf(key) >= 0){
							tmp.put(key, map.get(key));
						}
					}
					dataList.add(tmp);
				}
				bean.setMapData(dataList);
			}
			bean.setTitle("vst_umeng_event_message_data");
			
			// 写操作日志
			StringBuilder sb = new StringBuilder();
			sb.append("导出数据，查询条件：").append(params);
			_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_EXPORT, sb.toString()));
		}catch(Exception e){
			logger.error("导出事件消息数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}

	/**
	 * 抓取事件消息
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int graspEventMessageData(Map<String, Object> params) throws SystemException {
		int result = 0;
		try {
			String startDay = ToolsString.checkEmpty(params.get("startDay"));
			String endDay = ToolsString.checkEmpty(params.get("endDay"));
			String eventName = ToolsString.checkEmpty(params.get("eventName"));
			String cookie = ToolsString.checkEmpty(params.get("cookie"));
			// 如果没有填cookie，就读取配置表
			if(ToolsString.isEmpty(cookie)){
				cookie = _baseService.getOptionByKey("umeng_cookie");
			}
			int startIndex = cookie.indexOf("XSRF-TOKEN=") + 11;
			int endIndex = cookie.indexOf(";", startIndex);
			String xsrfToken = cookie.substring(startIndex, endIndex);
			// 抓取条数
			int graspNum = 0;
			String paramType = "";
			if(eventName.equals("live_xiaowei_play_count")) {
				graspNum = 500;
				paramType = "channelName";
			} else if(eventName.equals("live_xiaowei_category_play_count")) {
				graspNum = 100;
				paramType = "live_xiaowei_category_play_count";
			}

			Map<String, String> header = new HashMap<>();
			header.put("Content-Type", "application/json;charset=utf-8");
			header.put("X-XSRF-TOKEN", xsrfToken);
			header.put("Cookie", cookie);

			JSONObject body = new JSONObject();
			body.put("relatedId", "53eb1622fd98c52bb000343e");
			body.put("version", JsonUtils._EMPTY_JSONARRAY);
			body.put("channel", JsonUtils._EMPTY_JSONARRAY);
			body.put("timeUnit", "day");
			body.put("eventName", eventName);
			body.put("propertyName", eventName);
			body.put("view", "propertyStringLaunchView");
			body.put("type", "propertyValue");
			body.put("dataSourceId", "53eb1622fd98c52bb000343e");

			// 遍历多天数据
			int startDate = VstTools.parseInt(startDay);
			int endDate = VstTools.parseInt(endDay);
			for(int day : VstTools.getDateSection(startDate, endDate)) {
				StringBuffer dayStr = new StringBuffer(day+"");
				dayStr.insert(4, "-");
				dayStr.insert(7, "-");
				body.put("fromDate", dayStr);
				body.put("toDate", dayStr);
				String jsonStr = ToolsHttp.httpPost(EVENT_MESSAGE_URL, header, body.toJSONString());
				JSONObject json = JSONObject.parseObject(jsonStr);
				if(json != null && json.containsKey("data")) {
					JSONObject data = json.getJSONObject("data");
					JSONArray items = data.getJSONArray("items");
					if(items != null) {
						List<VstUmengEventMessage> dataList = new ArrayList<VstUmengEventMessage>();
						Long now = System.currentTimeMillis();
						int end = (graspNum < items.size()) ? graspNum : items.size();
						for(int i = 0; i < end; i++) {
							JSONObject item = items.getJSONObject(i);
							VstUmengEventMessage bean = new VstUmengEventMessage();
							bean.setVst_uem_id(ToolsRandom.getRandom(10));
							bean.setVst_uem_date(day);
							bean.setVst_uem_event_name(eventName);
							bean.setVst_uem_param_type(paramType);
							if(item.getString("name").length() > 100) {
								continue;
							}
							bean.setVst_uem_param_value(item.getString("name"));
							bean.setVst_uem_message_num(item.getLong("value"));
							bean.setVst_uem_radio(item.get("rate")+"%");
							bean.setVst_uem_addtime(now);
							bean.setVst_uem_creator("wj grasp");
							dataList.add(bean);
						}
						if(!dataList.isEmpty()) {
							int count = _vstUmengEventMessageDao.batchInsert(dataList);
							System.out.println("插入事件消息数据：" + count);
							result += count;
						}
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 删除事件消息
	 */
	@Override
	public int deleteEventMessage(Map<String, Object> params, UserSession user) throws SystemException {
		int result = 0;
		try {
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				params.put("startDay", VstTools.parseInt(params.get("startDay")+""));
				params.put("endDay", VstTools.parseInt(params.get("endDay")+""));
				// 参数特殊化处理
				if(!ToolsString.isEmpty(params.get("vst_uem_param_value")+"")){
					VstTools.paramFormat("vst_uem_param_value", params.get("vst_uem_param_value")+"", params);
				}
			}
			
			List<Map<String, Object>> data = _vstUmengEventMessageDao.queryForList(params);
			if(data != null && data.size() > 0){
				//需要删除的ID
				List<String> ids = new ArrayList<String>();
				for(Map<String, Object> map : data){
					ids.add(map.get("vst_uem_id")+"");
				}
				
				Map<String, Object> param = new HashMap<String, Object>(1);
				param.put("list_ids", ids);
				result = _vstUmengEventMessageDao.delete(param);
			}
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("删除事件消息, 条件：").append(params);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_DEL, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("删除事件消息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 查询汇总数据
	 */
	@Override
	public ReportBean getCutPageDataSum(CutPage cutPage) throws SystemException {
		ReportBean bean = new ReportBean();
		try{
			Map<String, Object> params = cutPage.get_castQueryParam();
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				params.put("startDay", VstTools.parseInt(params.get("startDay")+""));
				params.put("endDay", VstTools.parseInt(params.get("endDay")+""));
				// 参数特殊化处理
				if(!ToolsString.isEmpty(params.get("vst_uem_param_value")+"")){
					VstTools.paramFormat("vst_uem_param_value", params.get("vst_uem_param_value")+"", params);
				}
			}
			
			int count = _vstUmengEventMessageDao.queryCountSum(params);
			
			if(count != 0){
				int start = (cutPage.get_currPage()-1) * cutPage.get_singleCount();
				
				params.put("offset", start);
				params.put("limit", cutPage.get_singleCount());
				
				List<Map<String, Object>> list = _vstUmengEventMessageDao.queryListSum(params);
				
				bean.setTotalSize(count); //设置总行数
				bean.setSingleSize(cutPage.get_singleCount());//设置单页显示行数
				bean.setMapData(list);
			}
		}catch(Exception e){
			logger.error("查询事件消息汇总数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}

	/**
	 * 导出汇总数据
	 */
	@Override
	public ReportBean getExportDataSum(Map<String, Object> params, UserSession user) throws SystemException {
		ReportBean bean = new ReportBean();
		try{
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				params.put("startDay", VstTools.parseInt(params.get("startDay")+""));
				params.put("endDay", VstTools.parseInt(params.get("endDay")+""));
				// 参数特殊化处理
				if(!ToolsString.isEmpty(params.get("vst_uem_param_value")+"")){
					VstTools.paramFormat("vst_uem_param_value", params.get("vst_uem_param_value")+"", params);
				}
			}
			
			List<Map<String, Object>> data = _vstUmengEventMessageDao.queryExportSum(params);
			bean.setMapData(data);
			bean.setTitle("vst_umeng_event_message_sum");
			
			// 写操作日志
			StringBuilder sb = new StringBuilder();
			sb.append("导出汇总数据，查询条件：").append(params);
			_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_EXPORT, sb.toString()));
		}catch(Exception e){
			logger.error("导出事件消息汇总数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
}
