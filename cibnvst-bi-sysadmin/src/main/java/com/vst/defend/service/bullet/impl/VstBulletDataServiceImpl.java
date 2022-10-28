package com.vst.defend.service.bullet.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vst.common.pojo.VstBulletData;
import com.vst.common.pojo.VstSysOperateLog;
import com.vst.common.tools.date.ToolsDate;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.number.ToolsRandom;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.communal.util.VstTools;
import com.vst.defend.dao3.bullet.VstBulletDataDao;
import com.vst.defend.service.bullet.VstBulletDataService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author lhp
 * @date 2018-6-12 下午04:28:49
 * @version
 */
@Service
public class VstBulletDataServiceImpl implements VstBulletDataService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstBulletDataServiceImpl.class);
	
	/**
	 * 弹窗统计Dao接口
	 */
	@Resource
	private VstBulletDataDao _vstBulletDataDao;
	
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
			
			List<Map<String, Object>> data = _vstBulletDataDao.queryExport(params);
			if(data != null && data.size() > 0){
				
				Map<String, Object> temp = new HashMap<String, Object>(4);
				// 获取包名
				temp.put("vst_pkg", params.get("pkgName"));
				temp.put("vst_table_name", "vst_sys");
				temp.put("vst_column_name", "pkgName");
				temp.put("vst_state", VstConstants.STATE_AVALIABLE);
				Map<String, String> pkgMap = _baseService.getDictionaryForMap(temp);
				// 获取类型
				temp.put("vst_table_name", "vst_bullet_data");
				temp.put("vst_column_name", "vst_bd_type");
				Map<String, String> typeMap = _baseService.getDictionaryForMap(temp);
				
				// 导出字段内容
				String export_column = ToolsString.checkEmpty(params.get("export_column"));
				List<String> columns = new ArrayList<String>();
				for(String col : export_column.split(",")){
					columns.add(col.substring(col.indexOf("'")+1, col.lastIndexOf("'")));
				}
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
					// 只保留关键字段
					Map<String, Object> tmp = new LinkedHashMap<String, Object>();
					for(String col : columns){
						if(map.containsKey(col)){
							tmp.put(col, map.get(col));
						}else{
							tmp.put(col, "");
						}
					}
					dataList.add(tmp);
				}
				bean.setMapData(dataList);
			}
			bean.setTitle("vst_bullet_data");
			
			// 写操作日志
			StringBuilder sb = new StringBuilder();
			sb.append("导出数据，查询条件：").append(params);
			_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_EXPORT, sb.toString()));
		}catch(Exception e){
			logger.error("导出弹窗统计数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}

	/**
	 * 导入数据
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int importData(int type, int way, JSONArray jsonArr, UserSession user) throws SystemException {
		int result = 0;
		try{
			if(jsonArr != null && jsonArr.size() > 0){
				List<VstBulletData> list = new ArrayList<VstBulletData>();
				long now = System.currentTimeMillis();
				// 日期集合
				Set<Integer> dateSet = new HashSet<Integer>();
				
				for(int i=0; i<jsonArr.size(); i++){
					JSONObject json = jsonArr.getJSONObject(i);
					
					String pkg = json.getString("pkg");
					JSONArray dataList = json.getJSONArray("dataList");
					if(dataList != null && dataList.size() > 0){
						for(int j=0; j<dataList.size(); j++){
							JSONObject jsonBean = dataList.getJSONObject(j);
							
							int date = ToolsNumber.parseInt(jsonBean.get("date")+"");
							dateSet.add(date);
							
							VstBulletData bean = new VstBulletData();
							bean.setVst_bd_id(ToolsRandom.getRandom(10));
							bean.setVst_bd_date(date);
							bean.setVst_bd_pkg(pkg);
							bean.setVst_bd_type(type);
							bean.setVst_bd_total(jsonBean.getInteger("total"));
							
							JSONObject dataSun = jsonBean.getJSONObject("data");
							if(dataSun != null){
								if(dataSun.containsKey("retain_uv_1")){
									bean.setVst_bd_one(ToolsNumber.parseInt(dataSun.get("retain_uv_1")+""));
								}
								if(dataSun.containsKey("retain_ratio_1")){
									bean.setVst_bd_one_ratio(dataSun.get("retain_ratio_1")+"");
								}
								if(dataSun.containsKey("retain_uv_2")){
									bean.setVst_bd_two(ToolsNumber.parseInt(dataSun.get("retain_uv_2")+""));
								}
								if(dataSun.containsKey("retain_ratio_2")){
									bean.setVst_bd_two_ratio(dataSun.get("retain_ratio_2")+"");
								}
								if(dataSun.containsKey("retain_uv_3")){
									bean.setVst_bd_three(ToolsNumber.parseInt(dataSun.get("retain_uv_3")+""));
								}
								if(dataSun.containsKey("retain_ratio_3")){
									bean.setVst_bd_three_ratio(dataSun.get("retain_ratio_3")+"");
								}
								if(dataSun.containsKey("retain_uv_4")){
									bean.setVst_bd_four(ToolsNumber.parseInt(dataSun.get("retain_uv_4")+""));
								}
								if(dataSun.containsKey("retain_ratio_4")){
									bean.setVst_bd_four_ratio(dataSun.get("retain_ratio_4")+"");
								}
								if(dataSun.containsKey("retain_uv_5")){
									bean.setVst_bd_five(ToolsNumber.parseInt(dataSun.get("retain_uv_5")+""));
								}
								if(dataSun.containsKey("retain_ratio_5")){
									bean.setVst_bd_five_ratio(dataSun.get("retain_ratio_5")+"");
								}
								if(dataSun.containsKey("retain_uv_6")){
									bean.setVst_bd_six(ToolsNumber.parseInt(dataSun.get("retain_uv_6")+""));
								}
								if(dataSun.containsKey("retain_ratio_6")){
									bean.setVst_bd_six_ratio(dataSun.get("retain_ratio_6")+"");
								}
								if(dataSun.containsKey("retain_uv_7")){
									bean.setVst_bd_seven(ToolsNumber.parseInt(dataSun.get("retain_uv_7")+""));
								}
								if(dataSun.containsKey("retain_ratio_7")){
									bean.setVst_bd_seven_ratio(dataSun.get("retain_ratio_7")+"");
								}
								if(dataSun.containsKey("accumulate_uv_1")){
									bean.setVst_bd_con_one(ToolsNumber.parseInt(dataSun.get("accumulate_uv_1")+""));
								}
								if(dataSun.containsKey("accumulate_ratio_1")){
									bean.setVst_bd_con_one_ratio(dataSun.get("accumulate_ratio_1")+"");
								}
								if(dataSun.containsKey("accumulate_uv_2")){
									bean.setVst_bd_con_two(ToolsNumber.parseInt(dataSun.get("accumulate_uv_2")+""));
								}
								if(dataSun.containsKey("accumulate_ratio_2")){
									bean.setVst_bd_con_two_ratio(dataSun.get("accumulate_ratio_2")+"");
								}
								if(dataSun.containsKey("accumulate_uv_3")){
									bean.setVst_bd_con_three(ToolsNumber.parseInt(dataSun.get("accumulate_uv_3")+""));
								}
								if(dataSun.containsKey("accumulate_ratio_3")){
									bean.setVst_bd_con_three_ratio(dataSun.get("accumulate_ratio_3")+"");
								}
								if(dataSun.containsKey("accumulate_uv_4")){
									bean.setVst_bd_con_four(ToolsNumber.parseInt(dataSun.get("accumulate_uv_4")+""));
								}
								if(dataSun.containsKey("accumulate_ratio_4")){
									bean.setVst_bd_con_four_ratio(dataSun.get("accumulate_ratio_4")+"");
								}
								if(dataSun.containsKey("accumulate_uv_5")){
									bean.setVst_bd_con_five(ToolsNumber.parseInt(dataSun.get("accumulate_uv_5")+""));
								}
								if(dataSun.containsKey("accumulate_ratio_5")){
									bean.setVst_bd_con_five_ratio(dataSun.get("accumulate_ratio_5")+"");
								}
								if(dataSun.containsKey("accumulate_uv_6")){
									bean.setVst_bd_con_six(ToolsNumber.parseInt(dataSun.get("accumulate_uv_6")+""));
								}
								if(dataSun.containsKey("accumulate_ratio_6")){
									bean.setVst_bd_con_six_ratio(dataSun.get("accumulate_ratio_6")+"");
								}
								if(dataSun.containsKey("accumulate_uv_7")){
									bean.setVst_bd_con_seven(ToolsNumber.parseInt(dataSun.get("accumulate_uv_7")+""));
								}
								if(dataSun.containsKey("accumulate_ratio_7")){
									bean.setVst_bd_con_seven_ratio(dataSun.get("accumulate_ratio_7")+"");
								}
							}
							bean.setVst_bd_addtime(now);
							bean.setVst_bd_creator(user.getLoginInfo().getLoginName());
							list.add(bean);
						}
					}
				}
				if(way == 2){	// 先删除
					Map<String, Object> tmpMap = new HashMap<String, Object>(2);
					tmpMap.put("vst_bd_type", type);
					tmpMap.put("list_date", dateSet);
					_vstBulletDataDao.delete(tmpMap);
				}
				// 批量添加
				result = _vstBulletDataDao.batchInsert(list);
				if(result > 0){
					// 写操作日志
					StringBuilder sb = new StringBuilder();
					sb.append("导入数据，type=").append(type);
					sb.append("，way=").append(way);
					sb.append("，json=").append(jsonArr);
					_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_ADD, sb.toString()));
				}
			}
		}catch(Exception e){
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
				int deleteType = ToolsNumber.parseInt(params.get("deleteType")+"");
				if(deleteType == 1){
					Map<String, Object> tmpMap = new HashMap<String, Object>();
					String recordIds = params.get("recordIds")+"";
					tmpMap.put("list_ids", Arrays.asList(recordIds.split(",")));
					
					result = _vstBulletDataDao.delete(tmpMap);
				}else if(deleteType == 2){
					Map<String, Object> tmpMap = new HashMap<String, Object>();
					tmpMap.put("startDay", VstTools.parseInt(params.get("startDay")+""));
					tmpMap.put("endDay", VstTools.parseInt(params.get("endDay")+""));
					tmpMap.put("pkgName", params.get("pkgName"));
					tmpMap.put("vst_bd_type", params.get("vst_bd_type"));
					
					result = _vstBulletDataDao.delete(tmpMap);
				}else{
					return 0;
				}
				
				if(result > 0){
					// 写操作日志
					StringBuilder sb = new StringBuilder();
					sb.append("删除弹窗统计数据, 条件=").append(params);
					_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_DEL, sb.toString()));
				}
			}
		} catch (Exception e) {
			logger.error("删除弹窗统计数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
}
