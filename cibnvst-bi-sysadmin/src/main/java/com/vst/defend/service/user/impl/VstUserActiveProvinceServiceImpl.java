package com.vst.defend.service.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.pojo.VstSysOperateLog;
import com.vst.common.tools.date.ToolsDate;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.communal.util.VstTools;
import com.vst.defend.dao2.user.VstUserActiveProvinceDao;
import com.vst.defend.service.user.VstUserActiveProvinceService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

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
 * @date 2018-1-4 下午07:48:48
 * @version
 */
@Service
public class VstUserActiveProvinceServiceImpl implements VstUserActiveProvinceService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstUserActiveProvinceServiceImpl.class);
	
	/**
	 * 活跃用户(省份)Dao接口
	 */
	@Resource
	private VstUserActiveProvinceDao _vstUserActiveProvinceDao;
	
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
				String vst_uap_province = params.get("vst_uap_province")+"";
				if(!ToolsString.isEmpty(params.get("vst_uap_province")+"")){
					VstTools.paramFormat("vst_uap_province", params.get("vst_uap_province")+"", params);
					// 针对多省份查询
					String[] provinces = vst_uap_province.split(",");
					if(provinces.length > 1){
						params.put("list_provinces", Arrays.asList(provinces));
						params.remove("vst_uap_province");
					}
				}
			}
			
			List<Map<String, Object>> data = _vstUserActiveProvinceDao.queryExport(params);
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
			bean.setTitle("vst_user_active_province_data");
			
			// 写操作日志
			StringBuilder sb = new StringBuilder();
			sb.append("导出数据，查询条件：").append(params);
			_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_EXPORT, sb.toString()));
		}catch(Exception e){
			logger.error("导出活跃用户(省份)数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
	
	/**
	 * 按省份统计
	 */
	@Override
	public JSONObject getReportByProvince(Map<String, Object> params) throws SystemException {
		JSONObject result = new JSONObject();
		try{
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				params.put("startDay", VstTools.parseInt(params.get("startDay")+""));
				params.put("endDay", VstTools.parseInt(params.get("endDay")+""));
				// 针对多省份查询
				String vst_uap_province = params.get("vst_uap_province")+"";
				if(!ToolsString.isEmpty(vst_uap_province)){
					String[] provinces = vst_uap_province.split(",");
					if(provinces.length > 1){
						params.put("list_provinces", Arrays.asList(provinces));
						params.remove("vst_uap_province");
					}
				}
			}
			
			List<Map<String, Object>> list = _vstUserActiveProvinceDao.queryReportByProvince(params);
			if(list != null && list.size() > 0){
				// 获取Top10的省份
				List<String> provinces = new ArrayList<String>(10);
				for(Map<String, Object> map : list){
					String vst_uap_province = map.get("vst_uap_province")+"";
					if(!ToolsString.isEmpty(vst_uap_province) && !provinces.contains(vst_uap_province)){
						provinces.add(vst_uap_province);
					}
					if(provinces.size() >= 10){
						break;
					}
				}
				result.put("provinces", provinces);
				
				// 获取各天的数据
				Map<String, List<String>> dateMap = new LinkedHashMap<String, List<String>>();
				
				int startDay = ToolsNumber.parseInt(params.get("startDay")+"");
				int endDay = ToolsNumber.parseInt(params.get("endDay")+"");
				
				for(int day : VstTools.getDateSection(startDay, endDay)){
					if(!dateMap.containsKey(day+"")){
						List<String> amountList = new ArrayList<String>(provinces.size());
						for(int i=0; i<provinces.size(); i++){
							amountList.add("0");
						}
						for(int i=0; i<provinces.size(); i++){
							for(Map<String, Object> map : list){
								int vst_uap_date = ToolsNumber.parseInt(map.get("vst_uap_date")+"");
								String vst_uap_province = map.get("vst_uap_province")+"";
								if(provinces.get(i).equals(vst_uap_province) && vst_uap_date == day){
									amountList.set(i, map.get("vst_uap_amount")+"");
									break;
								}
							}
						}
						// 筛选数据，如果当天没有数据，则不显示
						boolean isPut = false;
						for(String amount : amountList){
							if(!"0".equals(amount)){
								isPut = true;
								break;
							}
						}
						if(isPut){
							dateMap.put(day+"", amountList);
						}
					}
				}
				result.put("dateMap", dateMap);
			}
		}catch(Exception e){
			logger.error("按省份统计失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
}
