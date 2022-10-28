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
import com.vst.defend.service.api.MovieApiService;

/**
 * 影片统计API
 * @author lhp
 * @date 2017-12-11 下午04:03:18
 * @version
 */
@Controller
@RequestMapping("/movieAPI")
public class MovieApiController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = MovieApiController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 影片统计Service接口
	 */
	@Resource
	private MovieApiService _movieApiService;
	
	/**
	 * 查询热播榜
	 * @return
	 */
	@RequestMapping("/getMoviePlay.action")
	public String getMoviePlay(){
		JSONObject json = new JSONObject();
		try{
			this.initializeAction(_className);
			
			// 获取页面参数
			int date = ToolsNumber.parseInt(request.getParameter("date"));	//日期，格式yyyyMMdd
			int _currPage = ToolsNumber.parseInt(request.getParameter("_currPage"));	//当前页
			int _singleCount = ToolsNumber.parseInt(request.getParameter("_singleCount"));	//每页显示数据
			String cid = ToolsString.checkEmpty(request.getParameter("cid"));	//分类ID
			
			if(_currPage <= 0){
				_currPage = 1;
			}
			if(_singleCount <= 0){
				_singleCount = 10;
			}
			
			if(date == -1){
				json.put("date", date);
				json.put("msg", "参数错误");
				json.put("code", 201);
				json.put("data", Collections.EMPTY_LIST);
			}else{
				Map<String, Object> params = new HashMap<String, Object>(4);
				params.put("date", date);
				params.put("_currPage", _currPage);
				params.put("_singleCount", _singleCount);
				params.put("cid", cid);
				// 获取数据
				ReportBean bean = _movieApiService.getMoviePlayData(params);
				List<JSONObject> data = bean.getData();
				if(data!=null && !data.isEmpty()){
					json.put("date", date);
					json.put("msg", "调用成功");
					json.put("code", 100);
					json.put("data", data);
				}else{
					json.put("date", date);
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
			logger.error("getMoviePlay error." + SystemException.getExceptionInfo(e));
		}
		printJson(json.toJSONString());
		return null;
	}
	
	/**
	 * 查询热搜榜
	 * @return
	 */
	@RequestMapping("/getMovieSearch.action")
	public String getMovieSearch(){
		JSONObject json = new JSONObject();
		try{
			this.initializeAction(_className);
			
			// 获取页面参数
			int date = ToolsNumber.parseInt(request.getParameter("date"));	//日期，格式yyyyMMdd
			int _currPage = ToolsNumber.parseInt(request.getParameter("_currPage"));	//当前页
			int _singleCount = ToolsNumber.parseInt(request.getParameter("_singleCount"));	//每页显示数据
			String cid = ToolsString.checkEmpty(request.getParameter("cid"));	//分类ID
			
			if(_currPage <= 0){
				_currPage = 1;
			}
			if(_singleCount <= 0){
				_singleCount = 10;
			}
			
			if(date == -1){
				json.put("date", date);
				json.put("msg", "参数错误");
				json.put("code", 201);
				json.put("data", Collections.EMPTY_LIST);
			}else{
				Map<String, Object> params = new HashMap<String, Object>(4);
				params.put("date", date);
				params.put("_currPage", _currPage);
				params.put("_singleCount", _singleCount);
				params.put("cid", cid);
				// 获取数据
				ReportBean bean = _movieApiService.getMovieSearchData(params);
				List<JSONObject> data = bean.getData();
				if(data!=null && !data.isEmpty()){
					json.put("date", date);
					json.put("msg", "调用成功");
					json.put("code", 100);
					json.put("data", data);
				}else{
					json.put("date", date);
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
			logger.error("getMovieSearch error." + SystemException.getExceptionInfo(e));
		}
		printJson(json.toJSONString());
		return null;
	}
	
	/**
	 * 查询影片播放汇总排行
	 * @return
	 */
	@RequestMapping("/getMoviePlayTop.action")
	public String getMoviePlayTop(){
		JSONObject json = new JSONObject();
		try{
			this.initializeAction(_className);
			
			// 获取页面参数
			int startDay = ToolsNumber.parseInt(request.getParameter("startDay"));	//起始日期，格式yyyyMMdd
			int endDay = ToolsNumber.parseInt(request.getParameter("endDay"));	//结束日期，格式yyyyMMdd
			int _currPage = ToolsNumber.parseInt(request.getParameter("_currPage"));	//当前页
			int _singleCount = ToolsNumber.parseInt(request.getParameter("_singleCount"));	//每页显示数据
			String pkgName = ToolsString.checkEmpty(request.getParameter("pkgName"));	//包名
			
			if(_currPage <= 0){
				_currPage = 1;
			}
			if(_singleCount <= 0){
				_singleCount = 10;
			}
			if(ToolsString.isEmpty(pkgName)){
				pkgName = "net.myvst.v2";
			}
			
			if(startDay == -1 || endDay == -1){
				json.put("msg", "没有起始日期和结束日期");
				json.put("code", 201);
				json.put("data", Collections.EMPTY_LIST);
			}else{
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("startDay", startDay);
				params.put("endDay", endDay);
				params.put("_currPage", _currPage);
				params.put("_singleCount", _singleCount);
				params.put("pkgName", pkgName);
				params.put("vst_mp_special_type", ToolsString.checkEmpty(request.getParameter("vst_mp_special_type")));
				params.put("vst_mp_cid", ToolsString.checkEmpty(request.getParameter("vst_mp_cid")));
				
				// 获取数据
				ReportBean bean = _movieApiService.findMoviePlayTop(params);
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
			logger.error("getMoviePlayTop error." + SystemException.getExceptionInfo(e));
		}
		printJson(json.toJSONString());
		return null;
	}
	
	/**
	 * 查询影片点击汇总排行
	 * @return
	 */
	@RequestMapping("/getMovieClickTop.action")
	public String getMovieClickTop(){
		JSONObject json = new JSONObject();
		try{
			this.initializeAction(_className);
			
			// 获取页面参数
			int startDay = ToolsNumber.parseInt(request.getParameter("startDay"));	//起始日期，格式yyyyMMdd
			int endDay = ToolsNumber.parseInt(request.getParameter("endDay"));	//结束日期，格式yyyyMMdd
			int _currPage = ToolsNumber.parseInt(request.getParameter("_currPage"));	//当前页
			int _singleCount = ToolsNumber.parseInt(request.getParameter("_singleCount"));	//每页显示数据
			String pkgName = ToolsString.checkEmpty(request.getParameter("pkgName"));	//包名
			
			if(_currPage <= 0){
				_currPage = 1;
			}
			if(_singleCount <= 0){
				_singleCount = 10;
			}
			if(ToolsString.isEmpty(pkgName)){
				pkgName = "net.myvst.v2";
			}
			
			if(startDay == -1 || endDay == -1){
				json.put("msg", "没有起始日期和结束日期");
				json.put("code", 201);
				json.put("data", Collections.EMPTY_LIST);
			}else{
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("startDay", startDay);
				params.put("endDay", endDay);
				params.put("_currPage", _currPage);
				params.put("_singleCount", _singleCount);
				params.put("pkgName", pkgName);
				params.put("vst_mc_special_type", ToolsString.checkEmpty(request.getParameter("vst_mc_special_type")));
				params.put("vst_mc_cid", ToolsString.checkEmpty(request.getParameter("vst_mc_cid")));
				
				// 获取数据
				ReportBean bean = _movieApiService.findMovieClickTop(params);
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
			logger.error("getMovieClickTop error." + SystemException.getExceptionInfo(e));
		}
		printJson(json.toJSONString());
		return null;
	}
	
	/**
	 * 查询区块播放量
	 * @return
	 */
	@RequestMapping("/getMovieBlock.action")
	public String getMovieBlock(){
		JSONObject json = new JSONObject();
		try{
			this.initializeAction(_className);
			
			// 允许跨域
			response.setHeader("Access-Control-Allow-Origin", "*");
			
			// 获取页面参数
			int date = ToolsNumber.parseInt(request.getParameter("date"));	//日期，格式yyyyMMdd
			int type = ToolsNumber.parseInt(request.getParameter("type"));	//类型
			
			if(date == -1){
				json.put("date", date);
				json.put("msg", "参数错误");
				json.put("code", 201);
				json.put("data", Collections.EMPTY_LIST);
			}else{
				Map<String, Object> params = new HashMap<String, Object>(3);
				params.put("date", date);
				params.put("type", type);
				params.put("pkgName", "net.myvst.v2");
				
				JSONObject data = _movieApiService.getMovieBlockData(params);
				if(data != null && !data.isEmpty()){
					json.put("msg", "调用成功");
					json.put("code", 100);
					json.put("data", data);
				}else{
					json.put("msg", "没有结果");
					json.put("code", 202);
					json.put("data", Collections.EMPTY_LIST);
				}
			}
			
			// 解决跨域问题
			String callback = ToolsString.checkEmpty(request.getParameter("callback"));
			if(!ToolsString.isEmpty(callback)){
				printJson(callback + "(" + json.toJSONString() + ")");
			}else{
				printJson(json.toJSONString());
			}
		}catch(Exception e){
			json.put("code", 301);
			json.put("msg", "服务器异常");
			printJson(json.toJSONString());
			logger.error("getMovieBlock error." + SystemException.getExceptionInfo(e));
		}
		return null;
	}
}
