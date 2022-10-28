package com.vst.defend.controller.rank;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
import com.vst.defend.service.rank.MoviePlayRankService;

/**
 * 影片播放排行
 * @author lhp
 * @date 2018-2-1 下午03:09:35
 * @version
 */
@Scope("prototype")
@Controller
@RequestMapping("/moviePlayRank")
public class MoviePlayRankController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = MoviePlayRankController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 影片播放排行Service接口
	 */
	@Resource
	private MoviePlayRankService _moviePlayRankService;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询影片播放排行统计
	 * @return
	 */
	@RequestMapping("/findMoviePlayRank.action")
	public String findMoviePlayRank(){
		try {
			// 初始化
			this.initializeAction(_className);
			
			Object obj = session.getAttribute("cutPage_moviePlayRank");
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
		} catch (Exception e) {
			logger.error("findMoviePlayRank error." + SystemException.getExceptionInfo(e));
			// 转向失败页面
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
					.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
		} finally {
			// 记得移除session中的分页信息
			session.removeAttribute("cutPage_moviePlayRank");
		}
		
		return "rank/moviePlayRank_list";
	}
	
	/**
	 * 查询分页数据
	 * @return
	 */
	@RequestMapping("/ajaxGetCutPage.action")
	public String ajaxGetCutPage(){
		try{
			this.initializeAction(_className);
			
			CutPage cutPage = new CutPage();
			// 查询条件
			Map<String, String> queryParam = new HashMap<String, String>();
			queryParam.put("startDay", ToolsString.checkEmpty(request.getParameter("startDay")));
			queryParam.put("endDay", ToolsString.checkEmpty(request.getParameter("endDay")));
			queryParam.put("pkgName", ToolsString.checkEmpty(request.getParameter("pkgName")));
			queryParam.put("vst_mp_special_type", ToolsString.checkEmpty(request.getParameter("vst_mp_special_type")));
			queryParam.put("vst_mp_cid", ToolsString.checkEmpty(request.getParameter("vst_mp_cid")));
			// 排序信息
			String orderBy = ToolsString.checkEmpty(request.getParameter("orderBy"));
			String order = ToolsString.checkEmpty(request.getParameter("order"));
			queryParam.put("orderBy", orderBy);
			queryParam.put("order", order);
			cutPage.set_queryParam(queryParam);
			// 分页信息
			int currPage = ToolsNumber.parseInt(request.getParameter("currPage"));
			int singleCount = ToolsNumber.parseInt(request.getParameter("singleCount"));
			if(currPage != -1){
				cutPage.set_currPage(currPage);
			}
			if(singleCount != -1){
				cutPage.set_singleCount(singleCount);
			}
			
			ReportBean bean = _moviePlayRankService.getCutPageData(cutPage);
			List<Map<String, Object>> dataList = bean.getMapData();
			JSONObject json = new JSONObject();
			if(dataList != null && !dataList.isEmpty()){
				json.put("result", "success");
				json.put("data", dataList);
				json.put("singleSize", bean.getSingleSize());
				json.put("totalSize", bean.getTotalSize());
				json.put("currPage", currPage);
			}else{
				json.put("result", "empty");
			}
			printJson(json.toString());
		}catch(Exception e){
			logger.error("ajaxGetCutPage error." + SystemException.getExceptionInfo(e));
			return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");// 转向失败页面
		}
		return null;
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
			
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			ReportBean bean = _moviePlayRankService.getExportData(queryParam, getUserSession(moduleId));
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
}
