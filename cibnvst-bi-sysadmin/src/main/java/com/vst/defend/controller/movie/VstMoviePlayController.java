package com.vst.defend.controller.movie;

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
import com.vst.defend.service.movie.VstMoviePlayService;

/**
 * 影片播放
 * @author lhp
 * @date 2017-11-24 下午05:17:17
 * @version
 */
@Scope("session")
@Controller
@RequestMapping("/moviePlay")
public class VstMoviePlayController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = VstMoviePlayController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 影片播放Service接口
	 */
	@Resource
	private VstMoviePlayService _vstMoviePlayService;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询影片播放统计
	 * @return
	 */
	@RequestMapping("/findMoviePlay.action")
	public String findMoviePlay(){
		try {
			// 初始化
			this.initializeAction(_className);
			
			Object obj = session.getAttribute("cutPage_moviePlay");
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
			
			// 获取专区类型
			Map<String, Object> params = new HashMap<String, Object>(4);
			params.put("vst_pkg", recordPkg);
			params.put("vst_table_name", "vst_movie");
			params.put("vst_column_name", "specialType");
			params.put("vst_state", VstConstants.STATE_AVALIABLE);
			request.setAttribute("specialTypes", _baseService.getDictionaryForMap(params));
			// 获取分类
			params.put("vst_table_name", "vst_movie");
			params.put("vst_column_name", "classify");
			request.setAttribute("classifys", _baseService.getDictionaryForMap(params));
			
			// 获取表字段
			Map<String, String> columns = new LinkedHashMap<String, String>();
			columns.put("vst_mp_date", "日期");
			columns.put("vst_mp_pkg", "包名");
			columns.put("vst_mp_special_type", "专区类型");
			columns.put("vst_mp_cid", "影片分类");
			columns.put("vst_mp_uuid", "影片ID");
			columns.put("vst_mp_name", "影片名称");
			columns.put("vst_mp_vv", "播放量");
			columns.put("vst_mp_vv_dod", "播放量天环比");
			columns.put("vst_mp_vv_wow", "播放量周环比");
			columns.put("vst_mp_uv", "独立用户");
			columns.put("vst_mp_uv_dod", "独立用户天环比");
			columns.put("vst_mp_uv_wow", "独立用户周环比");
			columns.put("vst_mp_avg", "人均播放量");
			columns.put("vst_mp_playtime", "播放时长(H)");
			columns.put("vst_mp_playtime_avg", "人均播放时长(m)");
			request.setAttribute("columns", columns);
			// 获取表描述
			request.setAttribute("tableDesc", _baseService.getTableDesc("vst_movie_play"));
		} catch (Exception e) {
			logger.error("findMoviePlay error." + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_moviePlay");
		}
		
		return "movie/moviePlay_list";
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
			queryParam.put("vst_mp_special_type", ToolsString.checkEmpty(request.getParameter("vst_mp_special_type")));
			queryParam.put("vst_mp_cid", ToolsString.checkEmpty(request.getParameter("vst_mp_cid")));
			queryParam.put("vst_mp_uuid", ToolsString.checkEmpty(request.getParameter("vst_mp_uuid")));
			queryParam.put("vst_mp_name", ToolsString.checkEmpty(request.getParameter("vst_mp_name")));
			queryParam.put("export_column", ToolsString.checkEmpty(request.getParameter("export_column")));
			// 编码转码
			VstTools.decodeToUTF_8(queryParam);
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			ReportBean bean = _vstMoviePlayService.getExportData(queryParam, getUserSession(moduleId));
			List<Map<String, Object>> data = bean.getMapData();
			File file = ExportUtil.ExportExcel(data, PropertiesReader.getProperty("export_dir"), bean.getTitle());
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
	 * 导出总量
	 * @return
	 */
	@RequestMapping("/exportSumData.action")
	public String exportSumData(){
		try{
			this.initializeAction(_className);
			
			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("startDay", ToolsString.checkEmpty(request.getParameter("startDay")));
			queryParam.put("endDay", ToolsString.checkEmpty(request.getParameter("endDay")));
			queryParam.put("pkgName", ToolsString.checkEmpty(request.getParameter("pkgName")));
			queryParam.put("vst_mp_special_type", ToolsString.checkEmpty(request.getParameter("vst_mp_special_type")));
			queryParam.put("vst_mp_cid", ToolsString.checkEmpty(request.getParameter("vst_mp_cid")));
			queryParam.put("vst_mp_uuid", ToolsString.checkEmpty(request.getParameter("vst_mp_uuid")));
			queryParam.put("vst_mp_name", ToolsString.checkEmpty(request.getParameter("vst_mp_name")));
			
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			ReportBean bean = _vstMoviePlayService.getExportSumData(queryParam, getUserSession(moduleId));
			List<Map<String, Object>> data = bean.getMapData();
			File file = ExportUtil.ExportExcel(data, PropertiesReader.getProperty("export_dir"), bean.getTitle());
			if(file.exists()){
				VstTools.fileDownLoad(response, file);
				file.delete();
			}
		}catch(Exception e){
			logger.error("exportSumData error." + SystemException.getExceptionInfo(e));
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");// 转向失败页面
		}
		return null;
	}
}
