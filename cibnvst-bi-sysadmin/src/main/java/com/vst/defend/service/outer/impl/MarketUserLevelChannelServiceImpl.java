package com.vst.defend.service.outer.impl;

import com.vst.common.pojo.VstSysOperateLog;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.communal.util.VstTools;
import com.vst.defend.dao2.user.VstUserLevelChannelDao;
import com.vst.defend.service.outer.MarketUserLevelChannelService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author lhp
 * @date 2018-3-7 下午12:21:06
 * @version
 */
@Service
public class MarketUserLevelChannelServiceImpl implements MarketUserLevelChannelService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(MarketUserLevelChannelServiceImpl.class);
	
	/**
	 * 季质量用户(渠道)Dao接口
	 */
	@Resource
	private VstUserLevelChannelDao _vstUserLevelChannelDao;
	
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
				if(!ToolsString.isEmpty(params.get("vst_ulc_channel")+"")){
					VstTools.paramFormat("vst_ulc_channel", params.get("vst_ulc_channel")+"", params);
				}
			}
			
			List<Map<String, Object>> data = _vstUserLevelChannelDao.queryExport(params);
			if(data != null && data.size() > 0){
				// 获取包名
				Map<String, Object> temp = new HashMap<String, Object>(3);
				temp.put("vst_table_name", "vst_sys");
				temp.put("vst_column_name", "pkgName");
				temp.put("vst_state", VstConstants.STATE_AVALIABLE);
				Map<String, String> pkgMap = _baseService.getDictionaryForMap(temp);
				
				// 调整系数
				double vst_coefficient = 1;
				if(!ToolsString.isEmpty(params.get("vst_coefficient")+"")){
					vst_coefficient = ToolsNumber.parseDouble(params.get("vst_coefficient")+"");
				}
				
				// 获取所有渠道
				Set<String> channelSet = new LinkedHashSet<String>();
				// date_channel -> obj
				Map<String, Map<String, Object>> mapData = new HashMap<String, Map<String,Object>>();
				
				for(Map<String, Object> map : data){
					channelSet.add(map.get("渠道")+"");
					mapData.put(map.get("日期")+"_"+map.get("渠道"), map);
				}
				
				int startDay = ToolsNumber.parseInt(params.get("startDay")+"");
				int endDay = ToolsNumber.parseInt(params.get("endDay")+"");
				List<Integer> days = VstTools.getDateSection(startDay, endDay);
				
				List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
				
				for(int i=days.size()-1; i>=0; i--){
					int day = days.get(i);
					for(String channel : channelSet){
						String key = day+"_"+channel;
						if(mapData.containsKey(key)){
							Map<String, Object> tmp = mapData.get(key);
							if(pkgMap.containsKey(tmp.get("包名")+"")){
								tmp.put("包名", pkgMap.get(tmp.get("包名")+""));
							}
							long vst_ulc_add_amount = ToolsNumber.parseLong(tmp.get("季新增用户数")+""); 
							tmp.put("季新增用户数", Math.round(vst_ulc_add_amount * vst_coefficient));
							long vst_ulc_season_amount = ToolsNumber.parseLong(tmp.get("季质量用户数")+""); 
							tmp.put("季质量用户数", Math.round(vst_ulc_season_amount * vst_coefficient));
							dataList.add(tmp);
						}else{
							Map<String, Object> tmp = new LinkedHashMap<String, Object>();
							tmp.put("日期", day);
							tmp.put("包名", "");
							tmp.put("渠道", channel);
							tmp.put("季新增用户数", 0);
							tmp.put("季质量用户数", 0);
							tmp.put("天环比", "");
							tmp.put("周环比", "");
							tmp.put("占比", "");
							dataList.add(tmp);
						}
					}
				}
				bean.setMapData(dataList);
			}
			
			bean.setTitle("market_user_level_channel_data");
			
			// 写操作日志
			StringBuilder sb = new StringBuilder();
			sb.append("导出数据，查询条件：").append(params);
			_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_EXPORT, sb.toString()));
		}catch(Exception e){
			logger.error("导出季质量渠道用户(市场数据)数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
}