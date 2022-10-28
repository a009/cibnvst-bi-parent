package com.vst.defend.service.report.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.cache.CacheSysConfig;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.dao.report.VstAutoPageChartDao;
import com.vst.defend.dao2.sql.VstSQLDao;
import com.vst.defend.service.report.VstAutoReportService;

/**
 * 
 * @author lhp
 * @date 2017-9-12 下午02:37:26
 * @version
 */
@Service
public class VstAutoReportServiceImpl implements VstAutoReportService {
	
	/**
	 * SQL执行Dao接口
	 */
	@Resource
	private VstSQLDao _vstSQLDao;
	
	/**
	 * 自动化页面(统计图)Dao接口
	 */
	@Resource
	private VstAutoPageChartDao _vstAutoPageChartDao;
	
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstAutoReportServiceImpl.class);
	
	@Override
	public JSONObject getJsonByCode(Map<String, Object> object) throws SystemException {
		JSONObject result = new JSONObject();
		try {
			String code = ToolsString.checkEmpty(object.get("code"));
			if(!ToolsString.isEmpty(code)){
				// 获取缓存对象
				CacheSysConfig cache = CacheSysConfig.getInstance();
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("vst_main_state", VstConstants.STATE_AVALIABLE);
				params.put("vst_code", code);
				
				// 获取头部查询
//				List<Map<String, Object>> headers = _vstAutoMainDao.queryForList(params);
				List<Map<String, Object>> headers = cache.getDataMap("autoMain", params);
				
				
				if(headers != null && headers.size() > 0){
					Map<String, Object> header = headers.get(0);
					
					StringBuilder sql = new StringBuilder();
					sql.append(header.get("vst_main_script"));
					sql.append(" WHERE 1=1");
					
					// 获取查询条件
					params.put("vst_condition_state", VstConstants.STATE_AVALIABLE);
//					List<Map<String, Object>> conditions = _vstAutoConditionDao.queryForList(params);
					List<Map<String, Object>> conditions = cache.getDataMap("autoCondition", params);
					StringBuilder sqlCondtion = new StringBuilder();
					for(Map<String, Object> map : conditions){
						String vst_condition_arg = map.get("vst_condition_arg")+"";
						if(!ToolsString.isEmpty(object.get(vst_condition_arg)+"")){
							String vst_condition_script = map.get("vst_condition_script")+"";
							String temp = vst_condition_script.replace("?", object.get(vst_condition_arg)+"");
							sqlCondtion.append(" AND ").append(temp);
						}
					}
					sql.append(sqlCondtion);
					
					// 获取sql续加
					params.put("vst_overlay_state", VstConstants.STATE_AVALIABLE);
//					List<Map<String, Object>> overlays = _vstAutoOverlayDao.queryForList(params);
					List<Map<String, Object>> overlays = cache.getDataMap("autoOverlay", params);
					for(Map<String, Object> map : overlays){
						String vst_overlay_script = map.get("vst_overlay_script")+"";
						if(vst_overlay_script.indexOf("ORDER BY") != -1){
							// 用户可以自己指定排序规则
							String orderBy = object.get("orderBy")+"";
							String order = object.get("order")+"";
							if(!ToolsString.isEmpty(orderBy) && !ToolsString.isEmpty(order)){
								sql.append(" ORDER BY ").append(orderBy).append(" ").append(order);
								continue;
							}
						}
						sql.append(" ").append(vst_overlay_script);
					}
					
					String vst_main_isPaging = header.get("vst_main_isPaging")+"";
					if("1".equals(vst_main_isPaging)){
						// 获取总条数
						StringBuilder sql2 = new StringBuilder();
						if(ToolsString.isEmpty(header.get("vst_main_countScript")+"")){
							sql2.append("SELECT count(1) FROM ( ");
							sql2.append(sql);
							sql2.append(" ) a");
						}else{
							sql2.append(header.get("vst_main_countScript"));
							sql2.append(" WHERE 1=1");
							sql2.append(sqlCondtion);
						}
						// 分页
						int currPage = ToolsNumber.parseInt(object.get("currPage")+"");
						int singleCount = ToolsNumber.parseInt(object.get("singleCount")+"");
						sql.append(" LIMIT ").append(singleCount);
						sql.append(" OFFSET ").append((currPage-1) * singleCount);
						
						System.out.println("sql分页语句：" + sql2.toString());
						
						int totalCount = _vstSQLDao.queryBySqlCount(sql2.toString());
						JSONObject info = new JSONObject();
						info.put("currPage", currPage);	//当前页
						info.put("singleCount", singleCount);	//单页条数
						info.put("totalCount", totalCount);	//总条数
						result.put("info", info);
						if(totalCount > 0){
							List<Map<String, Object>> data = _vstSQLDao.queryBySqlList(sql.toString());
							result.put("data", data);
						}else{
							result.put("data", Collections.EMPTY_LIST);
						}
					}else{
						List<Map<String, Object>> data = _vstSQLDao.queryBySqlList(sql.toString());
						result.put("data", data);
					}
					System.out.println("sql列表语句：" + sql.toString());
				}
			}
		} catch (Exception e) {
			logger.error("根据code获取json失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}

	/**
	 * 自动化-统计图
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getChartJson(Map<String, Object> object) throws SystemException {
		JSONObject result = new JSONObject();
		try {
			String code = ToolsString.checkEmpty(object.get("code"));
			
			if(!ToolsString.isEmpty(code)){
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("vst_chart_state", VstConstants.STATE_AVALIABLE);
				params.put("vst_code", code);
				
				List<Map<String, Object>> charts = _vstAutoPageChartDao.queryForList(params);
				
				if(charts != null && charts.size() > 0){
					JSONArray chartArray = new JSONArray();
					for(Map<String, Object> map : charts){
						JSONObject tmpJson = new JSONObject();
						tmpJson.put("type", map.get("vst_chart_type"));
						
						String vst_chart_json = map.get("vst_chart_json")+"";
						String vst_chart_api = map.get("vst_chart_api")+"";
						
//						//System.out.println("API -- " + vst_chart_api);
//						//根据API地址，拿到json数据
//						JSONObject jsonData = ToolsHttp.getJson(vst_chart_api);
//						System.out.println(jsonData);
//						//将json数据，装入hcharts里
						
						tmpJson.put("api", vst_chart_api);
//						tmpJson.put("json", JSONObject.parse(vst_chart_json));
						tmpJson.put("json", vst_chart_json);
						chartArray.add(tmpJson);
					}
					result.put("data", chartArray);
				}
			}
		} catch (Exception e) {
			logger.error("获取(自动化-统计图)失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
}
