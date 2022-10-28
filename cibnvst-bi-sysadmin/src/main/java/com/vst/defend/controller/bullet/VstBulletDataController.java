package com.vst.defend.controller.bullet;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.controller.BaseController;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.ErrorInfo;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.CutPage;
import com.vst.defend.communal.util.ExportUtil;
import com.vst.defend.communal.util.PropertiesReader;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.communal.util.VstTools;
import com.vst.defend.service.bullet.VstBulletDataService;

/**
 * 弹窗统计
 * @author lhp
 * @date 2018-6-12 下午04:35:08
 * @version
 */
@Scope("session")
@Controller
@RequestMapping("/bulletData")
public class VstBulletDataController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = VstBulletDataController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 弹窗统计Service接口
	 */
	@Resource
	private VstBulletDataService _vstBulletDataService;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询弹窗统计
	 * @return
	 */
	@RequestMapping("/findBulletData.action")
	public String findBulletData(){
		try {
			// 初始化
			this.initializeAction(_className);
			
			Object obj = session.getAttribute("cutPage_bulletData");
			if(obj != null){
				cutPage = (CutPage) obj;
			}else{
				cutPage = new CutPage();
			}
			
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			// 获取包名
			String recordPkg = ToolsString.checkEmpty(request.getParameter("recordPkg"));
			if(ToolsString.isEmpty(recordPkg)){
				if(session.getAttribute("pkgName") != null){
					recordPkg = session.getAttribute("pkgName")+"";
				}else{
					recordPkg = "net.myvst.v2";
				}
			}
			
			// 获取页面按钮
			cutPage.set_buttonList(getPageButtons(moduleId));
			// 转发查询到的数据到页面
			request.setAttribute("cutPage", cutPage);
			// 转发当前模块ID
			request.setAttribute("moduleId", moduleId);
			// 转发包名
			request.setAttribute("recordPkg", recordPkg);
			
			// 获取类型
			Map<String, Object> params = new HashMap<String, Object>(4);
			params.put("vst_pkg", recordPkg);
			params.put("vst_table_name", "vst_bullet_data");
			params.put("vst_column_name", "vst_bd_type");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("types", _baseService.getDictionaryForMap(params));
			
			// 获取表字段
			Map<String, String> columns = new LinkedHashMap<String, String>();
			columns.put("vst_bd_date", "日期");
			columns.put("vst_bd_pkg", "包名");
			columns.put("vst_bd_type", "类型");
			columns.put("vst_bd_total", "点击用户数");
			columns.put("vst_bd_one", "1日用户数");
			columns.put("vst_bd_two", "2日用户数");
			columns.put("vst_bd_three", "3日用户数");
			columns.put("vst_bd_four", "4日用户数");
			columns.put("vst_bd_five", "5日用户数");
			columns.put("vst_bd_six", "6日用户数");
			columns.put("vst_bd_seven", "7日用户数");
			columns.put("vst_bd_one_ratio", "1日留存率");
			columns.put("vst_bd_two_ratio", "2日留存率");
			columns.put("vst_bd_three_ratio", "3日留存率");
			columns.put("vst_bd_four_ratio", "4日留存率");
			columns.put("vst_bd_five_ratio", "5日留存率");
			columns.put("vst_bd_six_ratio", "6日留存率");
			columns.put("vst_bd_seven_ratio", "7日留存率");
			columns.put("vst_bd_con_one", "1日连续用户数");
			columns.put("vst_bd_con_two", "2日连续用户数");
			columns.put("vst_bd_con_three", "3日连续用户数");
			columns.put("vst_bd_con_four", "4日连续用户数");
			columns.put("vst_bd_con_five", "5日连续用户数");
			columns.put("vst_bd_con_six", "6日连续用户数");
			columns.put("vst_bd_con_seven", "7日连续用户数");
			columns.put("vst_bd_con_one_ratio", "1日连续留存率");
			columns.put("vst_bd_con_two_ratio", "2日连续留存率");
			columns.put("vst_bd_con_three_ratio", "3日连续留存率");
			columns.put("vst_bd_con_four_ratio", "4日连续留存率");
			columns.put("vst_bd_con_five_ratio", "5日连续留存率");
			columns.put("vst_bd_con_six_ratio", "6日连续留存率");
			columns.put("vst_bd_con_seven_ratio", "7日连续留存率");
			request.setAttribute("columns", columns);
			// 获取表描述
			request.setAttribute("tableDesc", _baseService.getTableDesc("vst_bullet_data"));
		} catch (Exception e) {
			logger.error("findBulletData error." + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_bulletData");
		}
		
		return "bullet/bulletData_list";
	}
	
	/**
	 * 导出数据
	 * @return
	 */
	@RequestMapping("/exportData.action")
	public String exportData(){
		try{
			this.initializeAction(_className);
			
			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("startDay", ToolsString.checkEmpty(request.getParameter("startDay")));
			queryParam.put("endDay", ToolsString.checkEmpty(request.getParameter("endDay")));
			queryParam.put("pkgName", ToolsString.checkEmpty(request.getParameter("pkgName")));
			queryParam.put("vst_bd_type", ToolsString.checkEmpty(request.getParameter("vst_bd_type")));
			queryParam.put("export_column", ToolsString.checkEmpty(request.getParameter("export_column")));
			// 编码转码
			VstTools.decodeToUTF_8(queryParam);
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			ReportBean bean = _vstBulletDataService.getExportData(queryParam, getUserSession(moduleId));
			List<Map<String, Object>> data = bean.getMapData();
			File file = ExportUtil.ExportExcel(data, PropertiesReader.getProperty("export_dir"), bean.getTitle().trim());
			if(file.exists()){
				VstTools.fileDownLoad(response, file);
				file.delete();
			}
		}catch(Exception e){
			logger.error("exportData error." + SystemException.getExceptionInfo(e));
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");// 转向失败页面
		}
		return null;
	}
	
	/**
	 * 导入数据
	 * @return
	 */
	@RequestMapping("/ajaxImportData.action")
	@ResponseBody
	public JSONObject ajaxImportData(){
		JSONObject json = new JSONObject();
		try {
			// 初始化
			this.initializeAction(_className);
			int import_type = ToolsNumber.parseInt(request.getParameter("import_type"));
			int import_way = ToolsNumber.parseInt(request.getParameter("import_way"));
			String import_json = ToolsString.checkEmpty(request.getParameter("import_json"));
			
			JSONArray jsonArr = JSONArray.parseArray(import_json);
			if(jsonArr == null || jsonArr.isEmpty()){
				json.put("code", 201);
				json.put("msg", "json格式错误！");
				return json;
			}
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _vstBulletDataService.importData(import_type, import_way, jsonArr, getUserSession(moduleId));
			if(result > 0){
				json.put("code", 100);
				json.put("result", result);
			}else{
				json.put("code", 200);
				json.put("msg", "未插入数据！");
			}
		} catch (Exception e) {
			json.put("code", 301);
			json.put("msg", "服务器异常！");
			logger.error("ajaxImportData error. ERROR:" + SystemException.getExceptionInfo(e));
		}
		return json;
	}
	
	/**
	 * 删除数据
	 * @return
	 */
	@RequestMapping("/ajaxDeleteData.action")
	public String ajaxDeleteData(){
		JSONObject json = new JSONObject();
		try {
			// 初始化Action类
			this.initializeAction(_className);
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("recordIds", ToolsString.checkEmpty(request.getParameter("recordIds")));
			params.put("startDay", ToolsString.checkEmpty(request.getParameter("startDay")));
			params.put("endDay", ToolsString.checkEmpty(request.getParameter("endDay")));
			params.put("pkgName", ToolsString.checkEmpty(request.getParameter("pkgName")));
			params.put("vst_bd_type", ToolsString.checkEmpty(request.getParameter("vst_bd_type")));
			params.put("deleteType", ToolsString.checkEmpty(request.getParameter("deleteType")));
			
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			int result = _vstBulletDataService.deleteData(params, getUserSession(moduleId));
			if(result > 0){
				json.put("code", 100);
				json.put("result", result);
			}else{
				json.put("code", 200);
			}
		} catch (Exception e) {
			json.put("code", 300);
			logger.error("ajaxDeleteData error. ERROR:" + SystemException.getExceptionInfo(e));
		}
		printJson(json.toJSONString());
		return null;
	}
}
