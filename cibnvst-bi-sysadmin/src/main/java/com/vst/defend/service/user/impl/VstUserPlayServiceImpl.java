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
import com.vst.defend.dao2.user.VstUserPlayDao;
import com.vst.defend.service.user.VstUserPlayService;
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
 * @date 2018-2-28 上午10:05:51
 * @version
 */
@Service
public class VstUserPlayServiceImpl implements VstUserPlayService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstUserPlayServiceImpl.class);
	
	/**
	 * 播放时长分布Dao接口
	 */
	@Resource
	private VstUserPlayDao _vstUserPlayDao;
	
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
			}
			
			List<Map<String, Object>> data = _vstUserPlayDao.queryExport(params);
			if(data != null && data.size() > 0){
				
				Map<String, Object> temp = new HashMap<String, Object>(4);
				// 获取包名
				temp.put("vst_pkg", params.get("pkgName"));
				temp.put("vst_table_name", "vst_sys");
				temp.put("vst_column_name", "pkgName");
				temp.put("vst_state", VstConstants.STATE_AVALIABLE);
				Map<String, String> pkgMap = _baseService.getDictionaryForMap(temp);
				// 获取类型
				temp.put("vst_table_name", "vst_user_play");
				temp.put("vst_column_name", "vst_up_type");
				Map<String, String> typeMap = _baseService.getDictionaryForMap(temp);
				
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
					if(typeMap.containsKey(map.get("类型")+"")){
						map.put("类型", typeMap.get(map.get("类型")+""));
					}
					map.put("播放时长(H)", VstTools.toHour(ToolsNumber.parseLong(map.get("播放时长(H)")+"")*1000));
					map.put("人均播放时长(m)", VstTools.toMinute(ToolsNumber.parseLong(map.get("人均播放时长(m)")+"")*1000));
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
			bean.setTitle("vst_user_play_data");
			
			// 写操作日志
			StringBuilder sb = new StringBuilder();
			sb.append("导出数据，查询条件：").append(params);
			_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_EXPORT, sb.toString()));
		}catch(Exception e){
			logger.error("导出播放时长分布数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
	
	/**
	 * 按类型统计
	 */
	@Override
	public JSONObject getReportByType(Map<String, Object> params) throws SystemException {
		JSONObject result = new JSONObject();
		try {
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				params.put("startDay", VstTools.parseInt(params.get("startDay")+""));
				params.put("endDay", VstTools.parseInt(params.get("endDay")+""));
			}
			
			List<Map<String, Object>> list = _vstUserPlayDao.queryReportByType(params);
			if(list != null && list.size() > 0){
				// 定义类型顺序
				int[] types = {8, 7, 6, 5, 4, 3, 2, 1, 0};
				result.put("types", types);
				
				// 获取各天的数据
				Map<String, List<Integer>> dateMap = new LinkedHashMap<String, List<Integer>>();
				
				int startDay = ToolsNumber.parseInt(params.get("startDay")+"");
				int endDay = ToolsNumber.parseInt(params.get("endDay")+"");
				
				for(int day : VstTools.getDateSection(startDay, endDay)){
					if(!dateMap.containsKey(day+"")){
						List<Integer> amountList = new ArrayList<Integer>(9);
						for(int i=0; i<9; i++){
							amountList.add(0);
						}
						for(int i=0; i<types.length; i++){
							for(Map<String, Object> map : list){
								int vst_up_date = ToolsNumber.parseInt(map.get("vst_up_date")+"");
								int vst_up_type = ToolsNumber.parseInt(map.get("vst_up_type")+"");
								if(vst_up_date == day && vst_up_type == types[i]){
									amountList.set(i, ToolsNumber.parseInt(map.get("vst_up_uv")+""));
									break;
								}
							}
						}
						// 筛选数据，如果当天没有数据，则不显示
						boolean isPut = false;
						for(int amount : amountList){
							if(amount != 0){
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
		} catch (Exception e) {
			logger.error("按类型统计失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
}
