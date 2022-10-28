package com.vst.defend.controller.report;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.cache.CacheSysConfig;
import com.vst.defend.communal.controller.BaseController;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.PinyinUtils;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.service.report.VstAutoReportService;

/**
 * 自动化报表
 * @author lhp
 * @date 2017-9-11
 * @version
 */
@Scope("prototype")
@Controller
@RequestMapping("/report")
public class VstAutoReportController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = VstAutoReportController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 自动化报表Service接口
	 */
	@Resource
	private VstAutoReportService _vstAutoReportService;
	
	@ResponseBody
	@RequestMapping("/json")
	public String getJson(){
		JSONObject json = new JSONObject();
		try {
			// 初始化
			this.initializeAction(_className);
			
			// 允许跨域
			response.setHeader("Access-Control-Allow-Origin", "*");
			
			String code = ToolsString.checkEmpty(request.getParameter("code"));
			
			if(ToolsString.isEmpty(code)){
				json.put("code", 201);
				json.put("msg", "调用失败，code参数不能为空");
			}else{
				Map<String, Object> object = new HashMap<String, Object>();
				object.put("code", code);
				
				boolean parmStatus = true;
				String errorMsg = null;
				
				// 获取需要接收的查询条件
				List<Map<String, Object>> conditions = getConditionToCache(code);
				if(conditions != null && conditions.size() > 0){
					for(Map<String, Object> map : conditions){
						String vst_condition_arg = map.get("vst_condition_arg")+"";
						String parm = ToolsString.checkEmpty(request.getParameter(vst_condition_arg));
						int vst_condition_need = ToolsNumber.parseInt(map.get("vst_condition_need")+"");
						
						if(vst_condition_need == 1 && ToolsString.isEmpty(parm)){
							parmStatus = false;
							errorMsg = vst_condition_arg + "参数不能为空";
							break;
						}else{
							// 参数&符还原
							parm = parm.replace("<@_@>", "&");
							object.put(vst_condition_arg, parm);
						}
					}
				}
				
				if(parmStatus){
					// 分页信息
					int currPage = ToolsNumber.parseInt(request.getParameter("currPage"));
					int singleCount = ToolsNumber.parseInt(request.getParameter("singleCount"));
					if(currPage == -1){
						currPage = 1;
					}
					if(singleCount == -1){
						singleCount = 10;
					}
					object.put("currPage", currPage);
					object.put("singleCount", singleCount);
					
					// 排序信息
					object.put("orderBy", ToolsString.checkEmpty(request.getParameter("orderBy")));
					object.put("order", ToolsString.checkEmpty(request.getParameter("order")));
					
					JSONObject result = _vstAutoReportService.getJsonByCode(object);
					
					if(result == null){
						json.put("code", 202);
						json.put("msg", "没有结果");
					}else{
						JSONArray data = result.getJSONArray("data");
						if(data == null || data.isEmpty()){
							json.put("code", 202);
							json.put("msg", "没有结果");
						}else{
							json.put("data", result.get("data"));
							json.put("info", result.get("info"));
							json.put("code", 100);
							json.put("msg", "调用成功");
						}
					}
				}else{
					json.put("code", 201);
					json.put("msg", errorMsg);
				}
			}
			
			// 解决跨域问题
			String callback = ToolsString.checkEmpty(request.getParameter("callback"));
			if(!ToolsString.isEmpty(callback)){
				printJson(callback + "(" + json.toJSONString() + ")");
			}else{
				printJson(json.toJSONString());
			}
		} catch (Exception e) {
			json.put("code", 301);
			json.put("msg", "服务器异常");
			printJson(json.toJSONString());
			logger.error("getJson error. ERROR:" + SystemException.getExceptionInfo(e));
		}
		return null;
	}
	
	/**
	 * 去缓存中，拿到需要的查询参数
	 * @param code
	 * @return
	 */
	private List<Map<String, Object>> getConditionToCache(String code){
		List<Map<String, Object>> conditions = null;
		try{
			// 获取缓存对象
			CacheSysConfig cache = CacheSysConfig.getInstance();
			
			Map<String, Object> params = new HashMap<String, Object>(2);
			params.put("vst_condition_state", VstConstants.STATE_AVALIABLE);
			params.put("vst_code", code);
			
			conditions = cache.getDataMap("autoCondition", params);
		}catch(Exception e){
			e.printStackTrace();
		}
		return conditions;
	}
	
	/**
	 * 获取图表页面
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/chart")
	public String getChartPage(){
		JSONObject json = new JSONObject();
		try {
			// 初始化
			this.initializeAction(_className);
			
			// 允许跨域
			response.setHeader("Access-Control-Allow-Origin", "*");
			
			String code = ToolsString.checkEmpty(request.getParameter("code"));
			
			if(ToolsString.isEmpty(code)){
				json.put("code", 201);
				json.put("msg", "调用失败，code参数不能为空");
			}else{
				Map<String, Object> params = new HashMap<String, Object>(1);
				params.put("code", code);
				json = _vstAutoReportService.getChartJson(params);
				json.put("code", 100);
				json.put("msg", "调用成功");
			}
		} catch (Exception e) {
			json.put("code", 301);
			json.put("msg", "服务器异常");
			logger.error("getChartPage error. ERROR:" + SystemException.getExceptionInfo(e));
		}
		printJson(json.toJSONString());
		return null;
	}
	
	/**
	 * 获取表格页面
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/table")
	public String getTablePage(){
		JSONObject json = new JSONObject();
		try {
			// 初始化
			this.initializeAction(_className);
			
			// 允许跨域
			response.setHeader("Access-Control-Allow-Origin", "*");
			
			String code = ToolsString.checkEmpty(request.getParameter("code"));
			
			if(ToolsString.isEmpty(code)){
				json.put("code", 201);
				json.put("msg", "调用失败，code参数不能为空");
			}else{
				json.put("code", 100);
				json.put("msg", "调用成功");
			}
		} catch (Exception e) {
			json.put("code", 301);
			json.put("msg", "服务器异常");
			printJson(json.toJSONString());
			logger.error("getTablePage error. ERROR:" + SystemException.getExceptionInfo(e));
		}
		return null;
	}
	
	/**
	 * 获取地区的编码
	 * @return
	 */
	@RequestMapping("/ajaxGetProvinceCode.action")
	public String ajaxGetProvinceCode(){
		JSONObject json = new JSONObject();
		try {
			String data = URLDecoder.decode(request.getParameter("data"), "UTF-8");
			JSONArray dataArray = JSONObject.parseArray(data);
			
			if(dataArray != null && dataArray.size() > 0){
				for(int i=0; i<dataArray.size(); i++){
					JSONObject dataJson = dataArray.getJSONObject(i);
					dataJson.put("code", PinyinUtils.chineseToPinYinF(dataJson.getString("value")));
					dataJson.put("enname", PinyinUtils.chineseToPinYinF(dataJson.getString("value")));
				}
			}
			json.put("code", 100);
			json.put("data", dataArray);
			printJson(json.toJSONString());
		} catch (Exception e) {
			json.put("code", 301);
			json.put("msg", "服务器异常");
			printJson(json.toJSONString());
			logger.error("ajaxGetProvinceCode error. ERROR:" + SystemException.getExceptionInfo(e));
		}
		return null;
	}
}
