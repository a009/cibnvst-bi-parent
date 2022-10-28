package com.vst.defend.controller.api;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.controller.BaseController;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.service.api.ShoppingApiService;

/**
 * 全球购统计API
 * @author lhp
 * @date 2018-1-30 上午11:39:15
 * @version
 */
@Controller
@RequestMapping("/shoppingAPI")
public class ShoppingApiController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = ShoppingApiController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 全球购统计Service接口
	 */
	@Resource
	private ShoppingApiService _shoppingApiService;
	
	/**
	 * 查询点播订单
	 * @return
	 */
	@RequestMapping("/getShoppingPlay.action")
	public String getShoppingPlay(){
		JSONObject json = new JSONObject();
		try{
			this.initializeAction(_className);
			
			// 获取页面参数
			int startDate = ToolsNumber.parseInt(request.getParameter("startDate"));	//起始日期，格式yyyyMMdd
			int endDate = ToolsNumber.parseInt(request.getParameter("endDate"));	//结束日期，格式yyyyMMdd
			int _currPage = ToolsNumber.parseInt(request.getParameter("_currPage"));	//当前页
			int _singleCount = ToolsNumber.parseInt(request.getParameter("_singleCount"));	//每页显示数据
			
			if(_currPage <= 0){
				_currPage = 1;
			}
			if(_singleCount <= 0){
				_singleCount = 10;
			}
			
			if(startDate == -1 || endDate == -1){
				json.put("msg", "没有起始日期和结束日期");
				json.put("code", 201);
				json.put("data", Collections.EMPTY_LIST);
			}else{
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("startDate", startDate);
				params.put("endDate", endDate);
				params.put("_currPage", _currPage);
				params.put("_singleCount", _singleCount);
				params.put("vst_shopping_play_order_channel", ToolsString.checkEmpty(request.getParameter("channel")));
				
				// 获取数据
				ReportBean bean = _shoppingApiService.findShoppingPlayData(params);
				List<Map<String, Object>> data = bean.getMapData();
				if(data!=null && !data.isEmpty()){
					json.put("msg", "调用成功");
					json.put("code", 100);
					json.put("data", data);
				}else{
					json.put("msg", "没有结果");
					json.put("code", 202);
					json.put("data", Collections.EMPTY_LIST);
				}
				// 分页信息
				JSONObject info = new JSONObject();
				info.put("_currPage", _currPage);
				info.put("_singleCount", _singleCount);
				info.put("_totalSize", bean.getTotalSize());
				json.put("info", info);
			}
		}catch(Exception e){
			json.put("code", 301);
			json.put("msg", "服务器异常");
			logger.error("getShoppingPlay error." + SystemException.getExceptionInfo(e));
		}
		printJson(json.toJSONString());
		return null;
	}
	
	/**
	 * 查询直播订单
	 * @return
	 */
	@RequestMapping("/getShoppingLive.action")
	public String getShoppingLive(){
		JSONObject json = new JSONObject();
		try{
			this.initializeAction(_className);
			
			// 获取页面参数
			int startDate = ToolsNumber.parseInt(request.getParameter("startDate"));	//起始日期，格式yyyyMMdd
			int endDate = ToolsNumber.parseInt(request.getParameter("endDate"));	//结束日期，格式yyyyMMdd
			int _currPage = ToolsNumber.parseInt(request.getParameter("_currPage"));	//当前页
			int _singleCount = ToolsNumber.parseInt(request.getParameter("_singleCount"));	//每页显示数据
			
			if(_currPage <= 0){
				_currPage = 1;
			}
			if(_singleCount <= 0){
				_singleCount = 10;
			}
			
			if(startDate == -1 || endDate == -1){
				json.put("msg", "没有起始日期和结束日期");
				json.put("code", 201);
				json.put("data", Collections.EMPTY_LIST);
			}else{
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("startDate", startDate);
				params.put("endDate", endDate);
				params.put("_currPage", _currPage);
				params.put("_singleCount", _singleCount);
				params.put("vst_shopping_live_order_channel", ToolsString.checkEmpty(request.getParameter("channel")));
				
				// 获取数据
				ReportBean bean = _shoppingApiService.findShoppingLiveData(params);
				List<Map<String, Object>> data = bean.getMapData();
				if(data!=null && !data.isEmpty()){
					json.put("msg", "调用成功");
					json.put("code", 100);
					json.put("data", data);
				}else{
					json.put("msg", "没有结果");
					json.put("code", 202);
					json.put("data", Collections.EMPTY_LIST);
				}
				// 分页信息
				JSONObject info = new JSONObject();
				info.put("_currPage", _currPage);
				info.put("_singleCount", _singleCount);
				info.put("_totalSize", bean.getTotalSize());
				json.put("info", info);
			}
		}catch(Exception e){
			json.put("code", 301);
			json.put("msg", "服务器异常");
			logger.error("getShoppingLive error." + SystemException.getExceptionInfo(e));
		}
		printJson(json.toJSONString());
		return null;
	}
}
