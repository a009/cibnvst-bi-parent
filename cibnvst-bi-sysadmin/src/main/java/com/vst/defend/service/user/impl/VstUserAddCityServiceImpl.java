package com.vst.defend.service.user.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vst.common.pojo.VstSysOperateLog;
import com.vst.common.tools.date.ToolsDate;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.AddressUtils;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.communal.util.VstTools;
import com.vst.defend.dao2.user.VstUserAddCityDao;
import com.vst.defend.service.user.VstUserAddCityService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author lhp
 * @date 2018-1-3 下午05:39:25
 * @version
 */
@Service
public class VstUserAddCityServiceImpl implements VstUserAddCityService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstUserAddCityServiceImpl.class);
	
	/**
	 * 新增用户(城市)Dao接口
	 */
	@Resource
	private VstUserAddCityDao _vstUserAddCityDao;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
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
				if(!ToolsString.isEmpty(params.get("vst_uaci_city")+"")){
					VstTools.paramFormat("vst_uaci_city", params.get("vst_uaci_city")+"", params);
				}
			}
			
			List<Map<String, Object>> data = _vstUserAddCityDao.queryExport(params);
			if(data != null && data.size() > 0){
				
				Map<String, Object> temp = new HashMap<String, Object>(3);
				// 获取包名
				temp.put("vst_table_name", "vst_sys");
				temp.put("vst_column_name", "pkgName");
				temp.put("vst_state", VstConstants.STATE_AVALIABLE);
				Map<String, String> pkgMap = _baseService.getDictionaryForMap(temp);
				
				// 导出字段内容
				String export_column = ToolsString.checkEmpty(params.get("export_column"));
				List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
				
				for(Map<String, Object> map : data){
					//更改日期格式
					String week = ToolsDate.getWeekByDate(map.get("日期")+"", ToolsDate.yyyy_MM_dd4);
					if("周日".equals(week) || "周六".equals(week)){
						map.put("日期", map.get("日期") + " " +week);
					}
					if(pkgMap.containsKey(map.get("包名")+"")){
						map.put("包名", pkgMap.get(map.get("包名")+""));
					}
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
			bean.setTitle("vst_user_add_city_data");
			
			// 写操作日志
			StringBuilder sb = new StringBuilder();
			sb.append("导出数据，查询条件：").append(params);
			_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_EXPORT, sb.toString()));
		}catch(Exception e){
			logger.error("导出新增用户(城市)数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
	
	/**
	 * 按城市统计
	 */
	@Override
	public ReportBean getReportByCity(Map<String, Object> params) throws SystemException {
		ReportBean bean = new ReportBean();
		try {
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				params.put("startDay", VstTools.parseInt(params.get("startDay")+""));
				params.put("endDay", VstTools.parseInt(params.get("endDay")+""));
				// 参数特殊化处理
				if(!ToolsString.isEmpty(params.get("vst_uaci_city")+"")){
					VstTools.paramFormat("vst_uaci_city", params.get("vst_uaci_city")+"", params);
				}
			}
			
			List<Map<String, Object>> list = _vstUserAddCityDao.queryReportByDim(params);
			if(list != null && list.size() > 0){
				List<JSONObject> mapData = new ArrayList<JSONObject>();
				
				Map<String, JSONArray> cityMap = AddressUtils.getCityMap();
				
				for(Map<String, Object> map : list){
					String city = map.get("vst_uaci_city")+"";
					if(cityMap.containsKey(city)){
						JSONArray value = new JSONArray();
						value.addAll(cityMap.get(city));
						value.add(map.get("vst_uaci_amount"));
						
						JSONObject json = new JSONObject();
						json.put("name", city);
						json.put("value", value);
						mapData.add(json);
					}
				}
				
				bean.setData(mapData);
			}
		} catch (Exception e) {
			logger.error("按城市统计失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
}
