package com.vst.defend.controller.channel;

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

import com.alibaba.fastjson.JSONObject;
import com.vst.common.tools.http.ToolsHttp;
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
import com.vst.defend.communal.util.VstTools;
import com.vst.defend.service.channel.VstChannelLevelService;

/**
 * 渠道质量
 * @author lhp
 * @date 2018-3-9 下午04:07:05
 * @version
 */
@Scope("session")
@Controller
@RequestMapping("/channelLevel")
public class VstChannelLevelController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = VstChannelLevelController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 渠道质量Service接口
	 */
	@Resource
	private VstChannelLevelService _vstChannelLevelService;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询渠道质量统计
	 * @return
	 */
	@RequestMapping("/findChannelLevel.action")
	public String findChannelLevel(){
		try {
			// 初始化
			this.initializeAction(_className);
			
			Object obj = session.getAttribute("cutPage_channelLevel");
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
			
			// 获取表字段
			Map<String, String> columns = new LinkedHashMap<String, String>();
			columns.put("vst_cl_date", "日期");
			columns.put("vst_cl_pkg", "包名");
			columns.put("vst_cl_channel", "渠道");
			columns.put("vst_cl_orders", "订单数");
			columns.put("vst_cl_orders_amount", "订单金额(分)");
			columns.put("vst_cl_orders_price", "客单价(分)");
			columns.put("vst_cl_orders_price_avg", "均价(分)");
			columns.put("vst_cl_user_sum", "累计用户");
			columns.put("vst_cl_user_active", "活跃用户");
			columns.put("vst_cl_user_add", "新增用户");
			columns.put("vst_cl_user_add_season", "季新增用户");
			columns.put("vst_cl_user_level_season", "季质量用户");
			columns.put("vst_cl_user_radio", "质量率");
			columns.put("vst_cl_user_retain_day", "次日留存用户");
			columns.put("vst_cl_user_retain_week", "周留存用户");
			columns.put("vst_cl_user_retain_month", "月留存用户");
			columns.put("vst_cl_user_retain_season", "季留存用户");
			columns.put("vst_cl_vv", "播放量");
			columns.put("vst_cl_uv", "独立用户");
			columns.put("vst_cl_avg", "人均播放量");
			columns.put("vst_cl_playtime", "播放时长(H)");
			columns.put("vst_cl_playtime_avg", "人均播放时长(m)");
			request.setAttribute("columns", columns);
			// 获取表描述
			request.setAttribute("tableDesc", _baseService.getTableDesc("vst_channel_level"));
		} catch (Exception e) {
			logger.error("findChannelLevel error." + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_channelLevel");
		}
		
		return "channel/channelLevel_list";
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
			queryParam.put("vst_cl_channel", ToolsString.checkEmpty(request.getParameter("vst_cl_channel")));
			queryParam.put("vst_cl_channel_not_null", ToolsString.checkEmpty(request.getParameter("vst_cl_channel_not_null")));
			queryParam.put("export_column", ToolsString.checkEmpty(request.getParameter("export_column")));
			// 编码转码
			VstTools.decodeToUTF_8(queryParam);
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			ReportBean bean = _vstChannelLevelService.getExportData(queryParam, getUserSession(moduleId));
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
	 * 同步数据
	 * @return
	 */
	@RequestMapping("/ajaxSyncData.action")
	public String ajaxSyncData(){
		JSONObject json = new JSONObject();
		try {
			// 初始化
			this.initializeAction(_className);
			int startDate = VstTools.parseInt(request.getParameter("startDate"));
			int endDate = VstTools.parseInt(request.getParameter("endDate"));
			String url = "http://bi.cibnvst.com:8081/cibnvst-bi-quartz/push/channel/level?startDate={startDate}&endDate={endDate}"
					.replace("{startDate}", startDate+"")
					.replace("{endDate}", endDate+"");
			json = ToolsHttp.getJson(url);
		} catch (Exception e) {
			json.put("code", 300);
			logger.error("ajaxSyncData error. ERROR:" + SystemException.getExceptionInfo(e));
		}
		printJson(json.toJSONString());
		return null;
	}
}
