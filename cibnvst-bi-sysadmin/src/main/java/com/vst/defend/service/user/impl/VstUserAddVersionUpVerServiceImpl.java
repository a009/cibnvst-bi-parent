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
import com.vst.defend.dao2.user.VstUserAddVersionUpVerDao;
import com.vst.defend.service.user.VstUserAddVersionUpVerService;
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
 * @date 2018-1-3 下午08:16:31
 * @version
 */
@Service
public class VstUserAddVersionUpVerServiceImpl implements VstUserAddVersionUpVerService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstUserAddVersionUpVerServiceImpl.class);
	
	/**
	 * 新增用户(显示版本、升级版本)Dao接口
	 */
	@Resource
	private VstUserAddVersionUpVerDao _vstUserAddVersionUpVerDao;
	
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
				if(!ToolsString.isEmpty(params.get("vst_uavu_version")+"")){
					VstTools.paramFormat("vst_uavu_version", params.get("vst_uavu_version")+"", params);
				}
				if(!ToolsString.isEmpty(params.get("vst_uavu_upVer")+"")){
					VstTools.paramFormat("vst_uavu_upVer", params.get("vst_uavu_upVer")+"", params);
				}
			}
			
			List<Map<String, Object>> data = _vstUserAddVersionUpVerDao.queryExport(params);
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
			bean.setTitle("vst_user_add_version_upVer_data");
			
			// 写操作日志
			StringBuilder sb = new StringBuilder();
			sb.append("导出数据，查询条件：").append(params);
			_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_EXPORT, sb.toString()));
		}catch(Exception e){
			logger.error("导出新增用户(显示版本、升级版本)数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
	
	/**
	 * 按升级版本统计
	 */
	@Override
	public JSONObject getReportByUpVer(Map<String, Object> params) throws SystemException {
		JSONObject result = new JSONObject();
		try {
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				params.put("startDay", VstTools.parseInt(params.get("startDay")+""));
				params.put("endDay", VstTools.parseInt(params.get("endDay")+""));
			}
			
			List<Map<String, Object>> list = _vstUserAddVersionUpVerDao.queryReportByUpVer(params);
			
			if(list != null && list.size() > 0){
				// 获取Top10的升级版本
				List<String> upVers = new ArrayList<String>(10);
				for(Map<String, Object> map : list){
					String vst_uavu_upVer = map.get("vst_uavu_upVer")+"";
					if(!upVers.contains(vst_uavu_upVer)){
						upVers.add(vst_uavu_upVer);
					}
					if(upVers.size() >= 10){
						break;
					}
				}
				result.put("upVers", upVers);
				
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
						for(int i=0; i<upVers.size(); i++){
							for(Map<String, Object> map : list){
								int vst_uavu_date = ToolsNumber.parseInt(map.get("vst_uavu_date")+"");
								String vst_uavu_upVer = map.get("vst_uavu_upVer")+"";
								if(upVers.get(i).equals(vst_uavu_upVer) && vst_uavu_date == day){
									amountList.set(i, ToolsNumber.parseLong(map.get("vst_uavu_amount")+""));
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
		} catch (Exception e) {
			logger.error("按升级版本统计失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
}
