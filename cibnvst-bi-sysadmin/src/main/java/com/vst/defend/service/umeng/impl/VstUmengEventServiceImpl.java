package com.vst.defend.service.umeng.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vst.common.pojo.VstSysOperateLog;
import com.vst.common.pojo.VstUmengEvent;
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
import com.vst.defend.dao3.umeng.VstUmengEventDao;
import com.vst.defend.service.umeng.VstUmengEventService;
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
 * @date 2018-3-15 下午05:12:36
 * @version
 */
@Service
public class VstUmengEventServiceImpl implements VstUmengEventService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstUmengEventServiceImpl.class);
	
	/**
	 * 事件Dao接口
	 */
	@Resource
	private VstUmengEventDao _vstUmengEventDao;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 事件抓取接口
	 */
	private static String EVENT_URL = "https://mobile.umeng.com/ht/api/v3/app/event/analysis/trend?relatedId=53eb1622fd98c52bb000343e&dataSourceId=53eb1622fd98c52bb000343e";
	
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
			}
			
			int count = _vstUmengEventDao.queryCount(params);
			
			if(count != 0){
				int start = (cutPage.get_currPage()-1) * cutPage.get_singleCount();
				
				params.put("offset", start);
				params.put("limit", cutPage.get_singleCount());
				
				List<Map<String, Object>> list = _vstUmengEventDao.queryForList(params);
				
				for(Map<String, Object> map : list){
					// 转换日期格式
					map.put("vst_ue_addtime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, ToolsNumber.parseLong(map.get("vst_ue_addtime")+"")));
					map.put("vst_ue_uptime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, ToolsNumber.parseLong(map.get("vst_ue_uptime")+"")));
				}
				
				bean.setTotalSize(count); //设置总行数
				bean.setSingleSize(cutPage.get_singleCount());//设置单页显示行数
				bean.setMapData(list);
			}
		}catch(Exception e){
			logger.error("查询事件分页数据失败: " + SystemException.getExceptionInfo(e));
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
			}
			
			List<Map<String, Object>> data = _vstUmengEventDao.queryExport(params);
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
			bean.setTitle("vst_umeng_event_data");
			
			// 写操作日志
			StringBuilder sb = new StringBuilder();
			sb.append("导出数据，查询条件：").append(params);
			_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_EXPORT, sb.toString()));
		}catch(Exception e){
			logger.error("导出事件数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}

	/**
	 * 抓取事件
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int graspEventData(Map<String, Object> params) throws SystemException {
		int result = 0;
		try {
			String startDay = ToolsString.checkEmpty(params.get("startDay"));
			String endDay = ToolsString.checkEmpty(params.get("endDay"));
			String eventName = ToolsString.checkEmpty(params.get("eventName"));
			String cookie = ToolsString.checkEmpty(params.get("cookie"));
			// 如果没有填cookie，就读取配置表
			if(ToolsString.isEmpty(cookie)) {
				cookie = _baseService.getOptionByKey("umeng_cookie");
			}
			int startIndex = cookie.indexOf("XSRF-TOKEN=") + 11;
			int endIndex = cookie.indexOf(";", startIndex);
			String xsrfToken = cookie.substring(startIndex, endIndex);

			Map<String, String> header = new HashMap<>();
			header.put("Content-Type", "application/json;charset=utf-8");
			header.put("X-XSRF-TOKEN", xsrfToken);
			header.put("Cookie", cookie);

			JSONObject body = new JSONObject();
			body.put("relatedId", "53eb1622fd98c52bb000343e");
			body.put("fromDate", startDay);
			body.put("toDate", endDay);
			body.put("version", JsonUtils._EMPTY_JSONARRAY);
			body.put("channel", JsonUtils._EMPTY_JSONARRAY);
			body.put("timeUnit", "day");
			body.put("eventName", eventName);
			body.put("view", "deviceSView");
			body.put("dataSourceId", "53eb1622fd98c52bb000343e");

			String jsonStr = ToolsHttp.httpPost(EVENT_URL, header, body.toJSONString());
			JSONObject json = JSONObject.parseObject(jsonStr);
			if(json != null && json.containsKey("data")) {
				JSONObject data = json.getJSONObject("data");
				// 日期Array
				JSONArray dates = data.getJSONArray("dates");
				// 数据Array
				JSONArray datas = data.getJSONArray("items").getJSONObject(0).getJSONArray("data");
				if(dates != null && datas != null) {
					List<VstUmengEvent> dataList = new ArrayList<VstUmengEvent>();
					Long now = System.currentTimeMillis();
					for(int i = 0; i < dates.size(); i++) {
						VstUmengEvent bean = new VstUmengEvent();
						bean.setVst_ue_id(ToolsRandom.getRandom(10));
						bean.setVst_ue_date(VstTools.parseInt(dates.getString(i)));
						bean.setVst_ue_event_name(eventName);
						bean.setVst_ue_uv(datas.getIntValue(i));
						bean.setVst_ue_addtime(now);
						bean.setVst_ue_creator("wj grasp");
						dataList.add(bean);
					}
					if(!dataList.isEmpty()) {
						result = _vstUmengEventDao.batchInsert(dataList);
						System.out.println("插入事件数据：" + result);
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 删除事件
	 */
	@Override
	public int deleteEvent(Map<String, Object> params, UserSession user) throws SystemException {
		int result = 0;
		try {
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				params.put("startDay", VstTools.parseInt(params.get("startDay")+""));
				params.put("endDay", VstTools.parseInt(params.get("endDay")+""));
			}
			
			List<Map<String, Object>> data = _vstUmengEventDao.queryForList(params);
			if(data != null && data.size() > 0){
				//需要删除的ID
				List<String> ids = new ArrayList<String>();
				for(Map<String, Object> map : data){
					ids.add(map.get("vst_ue_id")+"");
				}
				
				Map<String, Object> param = new HashMap<String, Object>(1);
				param.put("list_ids", ids);
				result = _vstUmengEventDao.delete(param);
			}
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("删除事件, 条件：").append(params);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_DEL, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("删除事件失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
}
