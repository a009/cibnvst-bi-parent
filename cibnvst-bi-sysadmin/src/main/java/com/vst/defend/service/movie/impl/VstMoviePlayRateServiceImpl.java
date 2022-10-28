package com.vst.defend.service.movie.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

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
import com.vst.defend.dao2.movie.VstMoviePlayRateDao;
import com.vst.defend.service.movie.VstMoviePlayRateService;

/**
 * 
 * @author lhp
 * @date 2018-7-12 下午03:27:43
 * @version
 */
@Service
public class VstMoviePlayRateServiceImpl implements VstMoviePlayRateService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstMoviePlayRateServiceImpl.class);
	
	/**
	 * 影片播放成功率Dao接口
	 */
	@Resource
	private VstMoviePlayRateDao _vstMoviePlayRateDao;
	
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
			
			List<Map<String, Object>> data = _vstMoviePlayRateDao.queryExport(params);
			if(data != null && data.size() > 0){
				
				Map<String, Object> temp = new HashMap<String, Object>(4);
				// 获取包名
				temp.put("vst_pkg", params.get("pkgName"));
				temp.put("vst_table_name", "vst_sys");
				temp.put("vst_column_name", "pkgName");
				temp.put("vst_state", VstConstants.STATE_AVALIABLE);
				Map<String, String> pkgMap = _baseService.getDictionaryForMap(temp);
				// 获取专区类型
				temp.put("vst_table_name", "vst_movie");
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
			bean.setTitle("vst_movie_play_rate_data");
			
			// 写操作日志
			StringBuilder sb = new StringBuilder();
			sb.append("导出数据，查询条件：").append(params);
			_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_EXPORT, sb.toString()));
		}catch(Exception e){
			logger.error("导出影片播放成功率数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}

	/**
	 * 按分类统计
	 */
	@Override
	public JSONObject getReportByCid(Map<String, Object> params) throws SystemException {
		JSONObject result = new JSONObject();
		try{
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				params.put("startDay", VstTools.parseInt(params.get("startDay")+""));
				params.put("endDay", VstTools.parseInt(params.get("endDay")+""));
			}
			
			List<Map<String, Object>> list = _vstMoviePlayRateDao.queryReportByCid(params);
			if(list != null && list.size() > 0){
				// 获Top20的分类
				List<String> classifys = new ArrayList<String>(20);
				for(Map<String, Object> map : list){
					String vst_mpr_cid = map.get("vst_mpr_cid")+"";
					if(!ToolsString.isEmpty(vst_mpr_cid) && !classifys.contains(vst_mpr_cid)){
						classifys.add(vst_mpr_cid);
					}
					if(classifys.size() >= 20){
						break;
					}
				}
				result.put("classifys", classifys);
				
				// 获取各天的数据
				Map<String, List<String>> dateMap = new LinkedHashMap<String, List<String>>();
				
				int startDay = ToolsNumber.parseInt(params.get("startDay")+"");
				int endDay = ToolsNumber.parseInt(params.get("endDay")+"");
				
				for(int day : VstTools.getDateSection(startDay, endDay)){
					if(!dateMap.containsKey(day+"")){
						List<String> amountList = new ArrayList<String>(classifys.size());
						for(int i=0; i<classifys.size(); i++){
							amountList.add("0");
						}
						for(int i=0; i<classifys.size(); i++){
							for(Map<String, Object> map : list){
								int vst_mpr_date = ToolsNumber.parseInt(map.get("vst_mpr_date")+"");
								String vst_mpr_cid = map.get("vst_mpr_cid")+"";
								if(classifys.get(i).equals(vst_mpr_cid) && vst_mpr_date == day){
									amountList.set(i, map.get("vst_mpr_rate")+"");
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
			logger.error("按分类统计失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
}
