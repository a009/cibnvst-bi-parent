package com.vst.defend.service.movie.impl;

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
import com.vst.defend.dao2.movie.VstMovieClassifyPlayDao;
import com.vst.defend.dao2.movie.VstMovieTotalPlayDao;
import com.vst.defend.service.movie.VstMovieClassifyPlayService;
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
 * @date 2017-12-1 下午03:14:04
 * @version
 */
@Service
public class VstMovieClassifyPlayServiceImpl implements VstMovieClassifyPlayService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstMovieClassifyPlayServiceImpl.class);
	
	/**
	 * 影片分类播放Dao接口
	 */
	@Resource
	private VstMovieClassifyPlayDao _vstMovieClassifyPlayDao;
	
	/**
	 * 汇总播放Dao接口
	 */
	@Resource
	private VstMovieTotalPlayDao _vstMovieTotalPlayDao;
	
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
			
			List<Map<String, Object>> data = _vstMovieClassifyPlayDao.queryExport(params);
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
			bean.setTitle("vst_movie_classify_play_data");
			
			// 写操作日志
			StringBuilder sb = new StringBuilder();
			sb.append("查询条件：").append(params);
			_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_EXPORT, sb.toString()));
		}catch(Exception e){
			logger.error("导出影片分类播放数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
	
	/**
	 * 导出总量
	 */
	@Override
	public ReportBean getExportSumData(Map<String, Object> params, UserSession user) throws SystemException {
		ReportBean bean = new ReportBean();
		try{
			int startDay = -1;
			int endDay = -1;
			int dataType = ToolsNumber.parseInt(params.get("dataType")+"");
			
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				startDay = VstTools.parseInt(params.get("startDay")+"");
				endDay = VstTools.parseInt(params.get("endDay")+"");
				params.put("startDay", VstTools.parseInt(params.get("startDay")+""));
				params.put("endDay", VstTools.parseInt(params.get("endDay")+""));
			}
			
			if(startDay != -1 && endDay != -1 && startDay <= endDay){
				
				int[] cidArr = {2,8,4,1,3,517,9,514,5,124,525,529,515,526};
				Map<Integer, String> cidMap = new HashMap<Integer, String>();
				cidMap.put(2, "电视剧");
				cidMap.put(8, "少儿");
				cidMap.put(4, "综艺");
				cidMap.put(1, "电影");
				cidMap.put(3, "动漫");
				cidMap.put(517, "电视直播");
				cidMap.put(9, "事件专题");
				cidMap.put(514, "新闻");
				cidMap.put(5, "纪录片");
				cidMap.put(124, "公开课");
				cidMap.put(525, "体育");
				cidMap.put(529, "游戏");
				cidMap.put(515, "自媒体");
				cidMap.put(526, "教育");
				
				if(dataType >= 1 && dataType <= 3){
					List<Map<String, Object>> data = _vstMovieClassifyPlayDao.queryExportSum(params);
					if(data != null && data.size() > 0){
						
						List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
						
						for(int day : VstTools.getDateSection(startDay, endDay)){
							Map<String, Object> line = new LinkedHashMap<String, Object>();
							line.put("日期", day);
							
							for(int cid : cidArr){
								boolean flag = false;	//判断是否有匹配数据
								for(Map<String, Object> map : data){
									int tempDay = ToolsNumber.parseInt(map.get("vst_mcp_date")+"");
									int tempCid = ToolsNumber.parseInt(map.get("vst_mcp_cid")+"");
									int tempSpec = ToolsNumber.parseInt(map.get("vst_mcp_special_type")+"");
									if(tempDay == day && tempCid == cid && tempSpec == -2){
										if(dataType == 1){
											long vst_mcp_vv = ToolsNumber.parseLong(map.get("vst_mcp_vv")+"");
											line.put(cidMap.get(cid), vst_mcp_vv);
										}else if(dataType == 2){
											long vst_mcp_uv = ToolsNumber.parseLong(map.get("vst_mcp_uv")+"");
											line.put(cidMap.get(cid), vst_mcp_uv);
										}else if(dataType == 3){
											long vst_mcp_playtime = ToolsNumber.parseLong(map.get("vst_mcp_playtime")+"");
											line.put(cidMap.get(cid), VstTools.toHour(vst_mcp_playtime*1000));
										}
										flag = true;
										break;
									}
								}
								if(!flag){
									line.put(cidMap.get(cid), 0);
								}
							}
							
							dataList.add(line);
						}
						// 获取汇总数据
						List<Map<String, Object>> totalData = _vstMovieTotalPlayDao.queryExportSum(params);
						for(Map<String, Object> map : dataList){
							String date = map.get("日期")+"";
							for(Map<String, Object> totalMap : totalData){
								if(date.equals(totalMap.get("vst_mtp_date")+"")){
									if(dataType == 1){
										long vst_mtp_vv = ToolsNumber.parseLong(totalMap.get("vst_mtp_vv")+"");
										map.put("全部", vst_mtp_vv);
									}else if(dataType == 2){
										long vst_mtp_uv = ToolsNumber.parseLong(totalMap.get("vst_mtp_uv")+"");
										map.put("全部", vst_mtp_uv);
									}else if(dataType == 3){
										long vst_mtp_playtime = ToolsNumber.parseLong(totalMap.get("vst_mtp_playtime")+"");
										map.put("全部", VstTools.toHour(vst_mtp_playtime*1000));
									}
									break;
								}
							}
						}
						
						bean.setMapData(dataList);
						bean.setTitle("vst_movie_classify_play_sum"+dataType);
					}
				}else if(dataType == 4){
					params.put("pkgName", "net.myvst.v2");
					params.put("vst_mcp_special_type", -2);
					List<Map<String, Object>> data = _vstMovieClassifyPlayDao.queryForList(params);
					
					if(data != null && data.size() > 0){
						List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
						
						for(int day : VstTools.getDateSection(startDay, endDay)){
							Map<String, Object> line = new LinkedHashMap<String, Object>();
							line.put("日期", day);
							
							for(int cid : cidArr){
								boolean flag = false;	//判断是否有匹配数据
								for(Map<String, Object> map : data){
									int tempDay = ToolsNumber.parseInt(map.get("vst_mcp_date")+"");
									int tempCid = ToolsNumber.parseInt(map.get("vst_mcp_cid")+"");
									if(tempDay == day && tempCid == cid){
										line.put(cidMap.get(cid), map.get("vst_mcp_avg"));
										flag = true;
										break;
									}
								}
								if(!flag){
									line.put(cidMap.get(cid), 0);
								}
							}
							dataList.add(line);
						}
						// 获取汇总数据
						List<Map<String, Object>> totalData = _vstMovieTotalPlayDao.queryForList(params);
						for(Map<String, Object> map : dataList){
							String date = map.get("日期")+"";
							for(Map<String, Object> totalMap : totalData){
								if(date.equals(totalMap.get("vst_mtp_date")+"")){
									map.put("全部", totalMap.get("vst_mtp_avg"));
									break;
								}
							}
						}
						
						bean.setMapData(dataList);
						bean.setTitle("vst_movie_classify_play_sum"+dataType);
					}
				}
			}
				
			// 写操作日志
			StringBuilder sb = new StringBuilder();
			sb.append("导出影片分类总量，查询条件：").append(params);
			_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_EXPORT, sb.toString()));
		}catch(Exception e){
			logger.error("导出影片分类播放总量失败: " + SystemException.getExceptionInfo(e));
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
			
			List<Map<String, Object>> list = _vstMovieClassifyPlayDao.queryReportByCid(params);
			if(list != null && list.size() > 0){
				// 获Top10的分类
				List<String> classifys = new ArrayList<String>(10);
				for(Map<String, Object> map : list){
					String vst_mcp_cid = map.get("vst_mcp_cid")+"";
					if(!ToolsString.isEmpty(vst_mcp_cid) && !classifys.contains(vst_mcp_cid)){
						classifys.add(vst_mcp_cid);
					}
					if(classifys.size() >= 10){
						break;
					}
				}
				result.put("classifys", classifys);
				
				// 获取各天的数据
				Map<String, List<Long>> dateMap = new LinkedHashMap<String, List<Long>>();
				
				int startDay = ToolsNumber.parseInt(params.get("startDay")+"");
				int endDay = ToolsNumber.parseInt(params.get("endDay")+"");
				
				for(int day : VstTools.getDateSection(startDay, endDay)){
					if(!dateMap.containsKey(day+"")){
						List<Long> amountList = new ArrayList<Long>(10);
						for(int i=0; i<10; i++){
							amountList.add(0L);
						}
						for(int i=0; i<classifys.size(); i++){
							for(Map<String, Object> map : list){
								int vst_mcp_date = ToolsNumber.parseInt(map.get("vst_mcp_date")+"");
								String vst_mcp_cid = map.get("vst_mcp_cid")+"";
								if(classifys.get(i).equals(vst_mcp_cid) && vst_mcp_date == day){
									amountList.set(i, ToolsNumber.parseLong(map.get("vst_mcp_vv")+""));
									break;
								}
							}
						}
						// 筛选数据，如果当天没有数据，则不显示
						boolean isPut = false;
						for(long amount : amountList){
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
		}catch(Exception e){
			logger.error("按分类统计失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
}
