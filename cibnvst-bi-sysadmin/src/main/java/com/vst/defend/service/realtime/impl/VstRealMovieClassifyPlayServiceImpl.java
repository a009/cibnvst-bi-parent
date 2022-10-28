package com.vst.defend.service.realtime.impl;

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
import com.vst.defend.dao2.realtime.VstRealMovieClassifyPlayDao;
import com.vst.defend.service.realtime.VstRealMovieClassifyPlayService;
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
 * @date 2018-3-16 上午10:30:28
 * @version
 */
@Service
public class VstRealMovieClassifyPlayServiceImpl implements VstRealMovieClassifyPlayService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstRealMovieClassifyPlayServiceImpl.class);
	
	/**
	 * 实时分类播放Dao接口
	 */
	@Resource
	private VstRealMovieClassifyPlayDao _vstRealMovieClassifyPlayDao;
	
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
			}
			
			List<Map<String, Object>> data = _vstRealMovieClassifyPlayDao.queryExport(params);
			if(data != null && data.size() > 0){
				
				Map<String, Object> temp = new HashMap<String, Object>(4);
				// 获取包名
				temp.put("vst_pkg", params.get("pkgName"));
				temp.put("vst_table_name", "vst_sys");
				temp.put("vst_column_name", "pkgName");
				temp.put("vst_state", VstConstants.STATE_AVALIABLE);
				Map<String, String> pkgMap = _baseService.getDictionaryForMap(temp);
				// 获取专区类型
				temp.put("vst_table_name", "vst_movie_classify_play");
				temp.put("vst_column_name", "specialType");
				Map<String, String> specialTypeMap = _baseService.getDictionaryForMap(temp);
				// 获取分类
				temp.put("vst_table_name", "vst_movie");
				temp.put("vst_column_name", "classify");
				Map<String, String> classifyMap = _baseService.getDictionaryForMap(temp);
				
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
					if(specialTypeMap.containsKey(map.get("专区类型")+"")){
						map.put("专区类型", specialTypeMap.get(map.get("专区类型")+""));
					}
					if(classifyMap.containsKey(map.get("影片分类")+"")){
						map.put("影片分类", classifyMap.get(map.get("影片分类")+""));
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
			bean.setTitle("vst_real_movie_classify_play_data");
			
			// 写操作日志
			StringBuilder sb = new StringBuilder();
			sb.append("导出数据，查询条件：").append(params);
			_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_EXPORT, sb.toString()));
		}catch(Exception e){
			logger.error("导出实时分类播放数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}

	/**
	 * 实时统计
	 */
	@Override
	public JSONObject getReportLine(Map<String, Object> params) throws SystemException {
		JSONObject result = new JSONObject();
		try{
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				params.put("startDay", VstTools.parseInt(params.get("startDay")+""));
				params.put("endDay", VstTools.parseInt(params.get("endDay")+""));
			}
			System.out.println("1、执行SQL前的时间："+ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, System.currentTimeMillis()));
			List<Map<String, Object>> list = _vstRealMovieClassifyPlayDao.queryReportLine(params);
			System.out.println("2、执行SQL后的时间："+ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, System.currentTimeMillis()));
			if(list != null && list.size() > 0){
				// 按时间分组
				Map<String, Map<String, Object>> dateMap = new LinkedHashMap<String, Map<String,Object>>();
				for(Map<String, Object> map : list){
					String date = map.get("vst_rmcp_date")+"";
					String hour = map.get("vst_rmcp_hour")+"";
					String minute = map.get("vst_rmcp_minute")+"";
					String second = map.get("vst_rmcp_seconds")+"";
					
					String key = date + "-" + hour + "-" + minute + "-" + second;
					
					if(dateMap.containsKey(key)){
						Map<String, Object> value = dateMap.get(key);
						long vst_rmcp_vv = ToolsNumber.parseLong(value.get("vst_rmcp_vv")+"") + ToolsNumber.parseLong(map.get("vst_rmcp_vv")+"");
						long vst_rmcp_uv = ToolsNumber.parseLong(value.get("vst_rmcp_uv")+"") + ToolsNumber.parseLong(map.get("vst_rmcp_uv")+"");
						value.put("vst_rmcp_vv", vst_rmcp_vv);
						value.put("vst_rmcp_uv", vst_rmcp_uv);
					}else{
						Map<String, Object> value = new HashMap<String, Object>(2);
						value.put("vst_rmcp_vv", map.get("vst_rmcp_vv"));
						value.put("vst_rmcp_uv", map.get("vst_rmcp_uv"));
						dateMap.put(key, map);
					}
				}
				// 生成结果数据
				List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
				for(String key : dateMap.keySet()){
					Map<String, Object> map = new HashMap<String, Object>();
					String[] arr = key.split("-");
					map.put("vst_rmcp_date", arr[0]);
					map.put("vst_rmcp_hour", arr[1]);
					map.put("vst_rmcp_minute", arr[2]);
					map.put("vst_rmcp_seconds", arr[3]);
					map.putAll(dateMap.get(key));
					dataList.add(map);
				}
				result.put("data", dataList);
				System.out.println("3、逻辑执行后的时间："+ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, System.currentTimeMillis()));
			}
		}catch(Exception e){
			logger.error("查询实时统计数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
}
