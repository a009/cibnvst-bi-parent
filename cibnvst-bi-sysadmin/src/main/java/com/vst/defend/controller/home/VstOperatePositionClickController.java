package com.vst.defend.controller.home;

import java.io.File;
import java.net.URLDecoder;
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

import com.alibaba.fastjson.JSONObject;
import com.vst.common.tools.number.ToolsRandom;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.controller.BaseController;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.ErrorInfo;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.CutPage;
import com.vst.defend.communal.util.ExportUtil;
import com.vst.defend.communal.util.PropertiesReader;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.service.home.VstOperatePositionClickService;
import com.vst.defend.service.task.VstExportTaskService;

/**
 * 运营页位置点击
 * @author lhp
 * @date 2018-4-12 下午05:37:55
 * @version
 */
@Scope("session")
@Controller
@RequestMapping("/operatePositionClick")
public class VstOperatePositionClickController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = VstOperatePositionClickController.class.getName();
	
	/**
	 * 表名
	 */
	private static final String _tableName = "vst_operate_position_click";
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 运营页位置点击Service接口
	 */
	@Resource
	private VstOperatePositionClickService _vstOperatePositionClickService;
	
	/**
	 * 导出任务Service接口
	 */
	@Resource
	private VstExportTaskService _vstExportTaskService;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询运营页位置点击统计
	 * @return
	 */
	@RequestMapping("/findOperatePositionClick.action")
	public String findOperatePositionClick(){
		try {
			// 初始化
			this.initializeAction(_className);
			
			Object obj = session.getAttribute("cutPage_operatePositionClick");
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
			params.put("vst_table_name", "vst_operate_position_click");
			params.put("vst_column_name", "vst_opc_type");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("types", _baseService.getDictionaryForMap(params));
			
			// 获取表字段
			Map<String, String> columns = new LinkedHashMap<String, String>();
			columns.put("vst_opc_date", "日期");
			columns.put("vst_opc_pkg", "包名");
			columns.put("vst_opc_type", "类型");
			columns.put("vst_opc_pos", "位置编号");
			columns.put("vst_opc_vv", "点击量");
			columns.put("vst_opc_vv_dod", "点击量天环比");
			columns.put("vst_opc_vv_wow", "点击量周环比");
			columns.put("vst_opc_uv", "独立用户");
			columns.put("vst_opc_uv_dod", "独立用户天环比");
			columns.put("vst_opc_uv_wow", "独立用户周环比");
			columns.put("vst_opc_avg", "人均点击量");
			request.setAttribute("columns", columns);
			// 获取表描述
			request.setAttribute("tableDesc", _baseService.getTableDesc(_tableName));
		} catch (Exception e) {
			logger.error("findOperatePositionClick error." + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_operatePositionClick");
		}
		
		return "home/operatePositionClick_list";
	}
	
//	/**
//	 * 导出数据
//	 * @return
//	 */
//	@RequestMapping("/exportData.action")
//	public String exportData(){
//		try{
//			this.initializeAction(_className);
//			
//			Map<String, Object> queryParam = new HashMap<String, Object>();
//			queryParam.put("startDay", ToolsString.checkEmpty(request.getParameter("startDay")));
//			queryParam.put("endDay", ToolsString.checkEmpty(request.getParameter("endDay")));
//			queryParam.put("pkgName", ToolsString.checkEmpty(request.getParameter("pkgName")));
//			queryParam.put("vst_opc_type", ToolsString.checkEmpty(request.getParameter("vst_opc_type")));
//			queryParam.put("vst_opc_pos", ToolsString.checkEmpty(request.getParameter("vst_opc_pos")));
//			queryParam.put("export_column", ToolsString.checkEmpty(request.getParameter("export_column")));
//			// 编码转码
//			VstTools.decodeToUTF_8(queryParam);
//			// 获取模块ID
//			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
//			ReportBean bean = _vstOperatePositionClickService.getExportData(queryParam, getUserSession(moduleId));
//			List<Map<String, Object>> data = bean.getMapData();
//			File file = ExportUtil.ExportExcel(data, PropertiesReader.getProperty("export_dir"), bean.getTitle().trim());
//			if(file.exists()){
//				VstTools.fileDownLoad(response, file);
//				file.delete();
//			}
//		}catch(Exception e){
//			logger.error("exportData error." + SystemException.getExceptionInfo(e));
//			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");// 转向失败页面
//		}
//		return null;
//	}
	
	/**
	 * 导出数据
	 * @return
	 */
	@RequestMapping("/exportData.action")
	public String exportData(){
		JSONObject json = new JSONObject();
		try{
			this.initializeAction(_className);
			
			final String startDay = ToolsString.checkEmpty(request.getParameter("startDay"));
			final String endDay = ToolsString.checkEmpty(request.getParameter("endDay"));
			final String pkgName = ToolsString.checkEmpty(request.getParameter("pkgName"));
			final String vst_opc_type = ToolsString.checkEmpty(request.getParameter("vst_opc_type"));
			final String vst_opc_pos = ToolsString.checkEmpty(request.getParameter("vst_opc_pos"));
			final String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			final String export_column = URLDecoder.decode(ToolsString.checkEmpty(request.getParameter("export_column")), "UTF-8");
			
			// 启动导出线程
			{
				Runnable runnable = new Runnable() {
					
					@Override
					public void run() {
						UserSession user = getUserSession(moduleId);
						String vst_task_id = ToolsRandom.getRandom(10);
						try{
							// 导出条件
							Map<String, Object> queryParam = new HashMap<String, Object>();
							queryParam.put("startDay", startDay);
							queryParam.put("endDay", endDay);
							queryParam.put("pkgName", pkgName);
							queryParam.put("vst_opc_type", vst_opc_type);
							queryParam.put("vst_opc_pos", vst_opc_pos);
							// 1、插入导出任务表
							Map<String, Object> taskMap = new HashMap<String, Object>();
							taskMap.put("vst_task_id", vst_task_id);
							taskMap.put("vst_module_id", moduleId);
							taskMap.put("vst_task_condition", queryParam.toString());
							taskMap.put("vst_task_table", _tableName);
							taskMap.put("vst_task_columns", export_column);
							taskMap.put("vst_task_state", 0);
							_vstExportTaskService.addExportTask(taskMap, user);
							// 2、生成文件
							queryParam.put("export_column", export_column);
							ReportBean bean = _vstOperatePositionClickService.getExportData(queryParam, getUserSession(moduleId));
							List<Map<String, Object>> data = bean.getMapData();
							String fileName = ToolsRandom.getRandom(20);
							File file = ExportUtil.ExportExcel(data, PropertiesReader.getProperty("export_dir"), fileName);
							if(file.exists()){
								// 3、更新数据库(成功)
								Map<String, Object> editMap = new HashMap<String, Object>();
								editMap.put("vst_task_id", vst_task_id);
								editMap.put("vst_task_file_name", fileName+".xlsx");
								editMap.put("vst_task_file_path", PropertiesReader.getProperty("export_dir")+"/"+fileName+".xlsx");
								editMap.put("vst_task_file_size", file.length());
								editMap.put("vst_task_state", 1);
								_vstExportTaskService.updateExportTask(null, editMap, user);
							}
						}catch(Exception e){
							// 3、更新数据库(失败)
							Map<String, Object> editMap = new HashMap<String, Object>();
							editMap.put("vst_task_id", vst_task_id);
							editMap.put("vst_task_state", 2);
							_vstExportTaskService.updateExportTask(null, editMap, user);
						}finally{
							_vstExportTaskService.writeCache(user.getVstSysUser().getVst_sys_id());
						}
					}
				};
				
				Thread exportThread = new Thread(runnable);
				exportThread.start();
			}
			json.put("code", 100);
		}catch(Exception e){
			json.put("code", 301);
			json.put("msg", "服务器异常");
			logger.error("exportData error." + SystemException.getExceptionInfo(e));
		}
		printJson(json.toJSONString());
		return null;
	}
}