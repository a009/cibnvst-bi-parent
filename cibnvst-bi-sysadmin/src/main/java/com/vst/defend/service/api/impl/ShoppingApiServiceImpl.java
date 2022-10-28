package com.vst.defend.service.api.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.vst.common.tools.date.ToolsDate;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.dao3.shopping.VstShoppingLiveOrderDao;
import com.vst.defend.dao3.shopping.VstShoppingPlayOrderDao;
import com.vst.defend.service.api.ShoppingApiService;

/**
 * 
 * @author lhp
 * @date 2018-1-30 上午11:42:50
 * @version
 */
@Service
public class ShoppingApiServiceImpl implements ShoppingApiService {
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(ShoppingApiServiceImpl.class);
	
	/**
	 * 点播订单Dao接口
	 */
	@Resource
	private VstShoppingPlayOrderDao _vstShoppingPlayOrderDao;
	
	/**
	 * 直播订单Dao接口
	 */
	@Resource
	private VstShoppingLiveOrderDao _vstShoppingLiveOrderDao;

	/**
	 * 查询点播订单数据
	 */
	@Override
	public ReportBean findShoppingPlayData(Map<String, Object> params) throws SystemException {
		ReportBean result = new ReportBean();
		try{
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				if(!ToolsString.isEmpty(params.get("startDate")+"")){
					params.put("startDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd4, params.get("startDate")+""));
				}
				if(!ToolsString.isEmpty(params.get("endDate")+"")){
					params.put("endDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd4, params.get("endDate")+"")+86399999);
				}
				if(ToolsString.isEmpty(params.get("vst_shopping_play_order_supplier_type")+"")){
					params.put("vst_shopping_play_order_supplier_type", "");
				}
			}
			
			int count = _vstShoppingPlayOrderDao.queryCount(params);
			result.setTotalSize(count);
			if(count != 0){
				int _currPage = ToolsNumber.parseInt(params.get("_currPage")+"");
				int _singleCount = ToolsNumber.parseInt(params.get("_singleCount")+"");
				//分页
				int start = (_currPage - 1) * _singleCount;
				params.put("offset", start);
				params.put("limit", _singleCount);
				
				List<Map<String, Object>> data = _vstShoppingPlayOrderDao.queryForList(params);
				for(Map<String, Object> map : data){
					// 转换日期格式
					map.put("vst_shopping_play_order_date", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm, map.get("vst_shopping_play_order_date")+""));
					map.put("vst_shopping_play_order_addtime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm, map.get("vst_shopping_play_order_addtime")+""));
					map.put("vst_shopping_play_order_uptime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm, map.get("vst_shopping_play_order_uptime")+""));
				}
				result.setMapData(data);
			}
		}catch(Exception e){
			logger.error("查询点播订单数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}

	/**
	 * 查询直播订单数据
	 */
	@Override
	public ReportBean findShoppingLiveData(Map<String, Object> params) throws SystemException {
		ReportBean result = new ReportBean();
		try{
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				if(!ToolsString.isEmpty(params.get("startDate")+"")){
					params.put("startDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd4, params.get("startDate")+""));
				}
				if(!ToolsString.isEmpty(params.get("endDate")+"")){
					params.put("endDate", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd4, params.get("endDate")+"")+86399999);
				}
			}
			
			int count = _vstShoppingLiveOrderDao.queryCount(params);
			result.setTotalSize(count);
			if(count != 0){
				int _currPage = ToolsNumber.parseInt(params.get("_currPage")+"");
				int _singleCount = ToolsNumber.parseInt(params.get("_singleCount")+"");
				//分页
				int start = (_currPage - 1) * _singleCount;
				params.put("offset", start);
				params.put("limit", _singleCount);
				
				List<Map<String, Object>> data = _vstShoppingLiveOrderDao.queryForList(params);
				for(Map<String, Object> map : data){
					// 转换日期格式
					map.put("vst_shopping_live_order_date", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm, map.get("vst_shopping_live_order_date")+""));
					map.put("vst_shopping_live_order_addtime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm, map.get("vst_shopping_live_order_addtime")+""));
					map.put("vst_shopping_live_order_uptime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm, map.get("vst_shopping_live_order_uptime")+""));
				}
				result.setMapData(data);
			}
		}catch(Exception e){
			logger.error("查询直播订单数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
}
