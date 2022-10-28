package com.vst.defend.service.index.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.PropertiesReader;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.communal.util.VstTools;
import com.vst.defend.dao2.movie.VstMovieClassifyPlayDao;
import com.vst.defend.dao2.movie.VstMoviePlayDao;
import com.vst.defend.dao2.movie.VstMovieSiteDao;
import com.vst.defend.service.index.IndexService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author lhp
 * @date 2017-12-5 下午05:41:16
 * @version
 */
@Service
public class IndexServiceImpl implements IndexService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(IndexServiceImpl.class);
	
	/**
	 * 分类播放Dao接口
	 */
	@Resource
	private VstMovieClassifyPlayDao _vstMovieClassifyPlayDao;
	
	/**
	 * 平台播放Dao接口
	 */
	@Resource
	private VstMovieSiteDao _vstMovieSiteDao;
	
	/**
	 * 影片播放Dao接口
	 */
	@Resource
	private VstMoviePlayDao _vstMoviePlayDao;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 获取首页数据
	 */
	@Override
	public JSONObject getIndexData(Map<String, Object> params) throws SystemException {
		JSONObject result = new JSONObject();
		try{
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				//data-日期，pkgName-包名
				params.put("date", VstTools.parseInt(params.get("date")+""));
			}
			
			// 是否缓存
			String isCache = params.get("isCache")+"";
			
			// 获取分类
			Map<String, Object> temp = new HashMap<String, Object>(3);
			temp.put("vst_pkg", params.get("pkgName"));
			temp.put("vst_table_name", "vst_movie");
			temp.put("vst_column_name", "classify");
			temp.put("vst_state", VstConstants.STATE_AVALIABLE);
			Map<String, String> classifyMap = _baseService.getDictionaryForMap(temp);
			
			//-- 影片分类占比
			// 按播放量排序
			Map<String, Object> classifyParam = new HashMap<String, Object>();
			classifyParam.putAll(params);
			classifyParam.put("vst_mcp_special_type", -2);
			classifyParam.put("orderBy", "vst_mcp_vv");
			classifyParam.put("order", "DESC");
			List<Map<String, Object>> classifyData1 = null;
			if("false".equals(isCache)){
				classifyData1 = _vstMovieClassifyPlayDao.queryForList(classifyParam);
			}else{
				classifyData1 = _vstMovieClassifyPlayDao.queryHomeList(classifyParam);
			}
			if(classifyData1 != null && classifyData1.size() > 0){
				JSONArray vvArray = new JSONArray();
				for(Map<String, Object> map : classifyData1){
					JSONObject tmp = new JSONObject();
					if(classifyMap.containsKey(map.get("vst_mcp_cid")+"")){
						tmp.put("vst_mcp_cid", classifyMap.get(map.get("vst_mcp_cid")+""));
					}else{
						tmp.put("vst_mcp_cid", map.get("vst_mcp_cid"));
					}
					tmp.put("vst_mcp_vv", map.get("vst_mcp_vv"));
					vvArray.add(tmp);
				}
				result.put("classifyVV", vvArray);
			}
			
			// 按观看人数排序
			classifyParam.put("orderBy", "vst_mcp_uv");
			classifyParam.put("order", "DESC");
			List<Map<String, Object>> classifyData2 = null;
			if("false".equals(isCache)){
				classifyData2 = _vstMovieClassifyPlayDao.queryForList(classifyParam);
			}else{
				classifyData2 = _vstMovieClassifyPlayDao.queryHomeList(classifyParam);
			}
			if(classifyData2 != null && classifyData2.size() > 0){
				JSONArray uvArray = new JSONArray();
				for(Map<String, Object> map : classifyData2){
					JSONObject tmp = new JSONObject();
					if(classifyMap.containsKey(map.get("vst_mcp_cid")+"")){
						tmp.put("vst_mcp_cid", classifyMap.get(map.get("vst_mcp_cid")+""));
					}else{
						tmp.put("vst_mcp_cid", map.get("vst_mcp_cid"));
					}
					tmp.put("vst_mcp_uv", map.get("vst_mcp_uv"));
					uvArray.add(tmp);
				}
				result.put("classifyUV", uvArray);
			}
			
			
			
			//-- 平台数据
			Map<String, Object> siteParam = new HashMap<String, Object>();
			siteParam.putAll(params);
			siteParam.put("limit", 10);
			siteParam.put("offset", 0);
			// 按播放量排序
			siteParam.put("orderBy", "vst_ms_vv");
			siteParam.put("order", "DESC");
			List<Map<String, Object>> siteData1 = null;
			if("false".equals(isCache)){
				siteData1 = _vstMovieSiteDao.queryForList(siteParam);
			}else{
				siteData1 = _vstMovieSiteDao.queryHomeList(siteParam);
			}
			if(siteData1 != null && siteData1.size() > 0){
				JSONArray vvArray = new JSONArray(10);
				vvArray.addAll(siteData1);
				result.put("siteVV", vvArray);
			}
			
			// 按观看人数排序
			siteParam.put("orderBy", "vst_ms_uv");
			siteParam.put("order", "DESC");
			List<Map<String, Object>> siteData2 = null;
			if("false".equals(isCache)){
				siteData2 = _vstMovieSiteDao.queryForList(siteParam);
			}else{
				siteData2 = _vstMovieSiteDao.queryHomeList(siteParam);
			}
			if(siteData2 != null && siteData2.size() > 0){
				JSONArray uvArray = new JSONArray(10);
				uvArray.addAll(siteData2);
				result.put("siteUV", uvArray);
			}
			
			
			
			//-- 影片数据
			//1电影、2电视剧、3动漫、4综艺、5纪录片、8少儿、514新闻、515自媒体、525体育、526教育、529游戏
			//int[] cids = {1,2,3,4,5,8,514,515,525,526,529};
			
			// 按播放量排序
			Map<String, Object> movieParam = new HashMap<String, Object>();
			movieParam.putAll(params);
			movieParam.put("orderBy", "vst_mp_vv");
			movieParam.put("order", "DESC");
			movieParam.put("limit", 10);
			movieParam.put("offset", 0);
			JSONObject vvJSON = new JSONObject();
			
			//for(int cid : cids){
			for(String cid : classifyMap.keySet()){
				movieParam.put("vst_mp_cid", cid);
				List<Map<String, Object>> vvArray = null;
				if("false".equals(isCache)){
					vvArray = _vstMoviePlayDao.queryForList(movieParam);
				}else{
					vvArray = _vstMoviePlayDao.queryHomeList(movieParam);
				}
				vvJSON.put("vv_"+cid, vvArray);
			}
			result.put("movieVV", vvJSON);
			
			// 按观看人数排序
			movieParam.put("orderBy", "vst_mp_uv");
			movieParam.put("order", "DESC");
			JSONObject uvJSON = new JSONObject();
			//for(int cid : cids){
			for(String cid : classifyMap.keySet()){
				movieParam.put("vst_mp_cid", cid);
				List<Map<String, Object>> uvArray = null;
				if("false".equals(isCache)){
					uvArray = _vstMoviePlayDao.queryForList(movieParam);
				}else{
					uvArray = _vstMoviePlayDao.queryHomeList(movieParam);
				}
				uvJSON.put("uv_"+cid, uvArray);
			}
			result.put("movieUV", uvJSON);
		}catch(Exception e){
			logger.error("获取首页数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 导出首页数据文件
	 */
	public File exportIndexFile(Map<String, Object> params) throws SystemException {
		try{
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				//data-日期，pkgName-包名
				params.put("date", VstTools.parseInt(params.get("date")+""));
			}
			
			Map<String, Object> temp = new HashMap<String, Object>(3);
			temp.put("date", params.get("date"));
			temp.put("pkgName", params.get("pkgName"));
			temp.put("isCache", true);
			
			JSONObject data = getIndexData(temp);
			if(data != null && !data.isEmpty()){
				
				File file = new File(PropertiesReader.getProperty("export_dir") + "/indexData.xlsx");
				file.createNewFile();
				
				XSSFWorkbook wb = new XSSFWorkbook();
				XSSFCellStyle style = wb.createCellStyle();
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				
				String export_column = params.get("export_column")+"";
				String[] exportArr = export_column.split(",");
				
				// 获取分类
				Map<String, Object> tmpParam = new HashMap<String, Object>(4);
				tmpParam.put("vst_pkg", params.get("pkgName"));
				tmpParam.put("vst_table_name", "vst_movie");
				tmpParam.put("vst_column_name", "classify");
				tmpParam.put("vst_state", VstConstants.STATE_AVALIABLE);
				Map<String, String> classifyMap = _baseService.getDictionaryForMap(tmpParam);
				
				int sheetNum = 0;
				
				for(String export : exportArr){
					if("classify".equals(export)){
						JSONArray classifyVV = data.getJSONArray("classifyVV");
						JSONArray classifyUV = data.getJSONArray("classifyUV");
						setSheetClassifyData(wb, style, sheetNum++, classifyVV, classifyUV);
					}else if("site".equals(export)){
						JSONArray siteVV = data.getJSONArray("siteVV");
						JSONArray siteUV = data.getJSONArray("siteUV");
						setSheetSiteData(wb, style, sheetNum++, siteVV, siteUV);
					}else if(export.startsWith("class")){
						int classNum = ToolsNumber.parseInt(export.substring(5));
						JSONObject movieVV = data.getJSONObject("movieVV");
						JSONObject movieUV = data.getJSONObject("movieUV");
						JSONArray classVV = movieVV.getJSONArray("vv_"+classNum);
						JSONArray classUV = movieUV.getJSONArray("uv_"+classNum);
						setSheetMovieData(wb, style, sheetNum++, classifyMap.get(classNum+""), classVV, classUV);
					}
				}
				
				FileOutputStream fileOut = new FileOutputStream(file);
				wb.write(fileOut);
				fileOut.close();
				
				return file;
			}
		}catch(Exception e){
			logger.error("导出首页数据文件失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return null;
	}
	
	
	private void setSheetClassifyData(XSSFWorkbook wb, XSSFCellStyle style, int sheetNum, JSONArray vvData, JSONArray uvData){
		XSSFSheet sheet = wb.createSheet();
		wb.setSheetName(sheetNum, "分类播放");
		XSSFRow row;
		XSSFCell cell;
		int rowNum = 0;
		
		row = sheet.createRow(rowNum++);
		cell = row.createCell(0);
		cell.setCellValue("《播放量》");
		cell = row.createCell(1);
		cell.setCellValue("");
		
		// 播放量
		if(vvData != null){
			row = sheet.createRow(rowNum++);
			cell = row.createCell(0);
			cell.setCellValue("分类名称");
		    cell.setCellStyle(style);
		    sheet.setColumnWidth(0, 24 * 256);
		    cell = row.createCell(1);
			cell.setCellValue("播放量");
		    cell.setCellStyle(style);
		    sheet.setColumnWidth(1, 24 * 256);
			for(int i=0; i<vvData.size(); i++){
				JSONObject classify = vvData.getJSONObject(i);
				row = sheet.createRow(rowNum++);
				cell = row.createCell(0);
				cell.setCellValue(classify.getString("vst_mcp_cid"));
				cell = row.createCell(1);
				cell.setCellValue(classify.getLongValue("vst_mcp_vv"));
			}
		}
		
		rowNum += 2;
		row = sheet.createRow(rowNum++);
		cell = row.createCell(0);
		cell.setCellValue("《观看人数》");
		cell = row.createCell(1);
		cell.setCellValue("");
		
		// 观看人数
		if(uvData != null){
			row = sheet.createRow(rowNum++);
			cell = row.createCell(0);
			cell.setCellValue("分类名称");
		    cell.setCellStyle(style);
		    sheet.setColumnWidth(0, 24 * 256);
		    cell = row.createCell(1);
			cell.setCellValue("观看人数");
		    cell.setCellStyle(style);
		    sheet.setColumnWidth(1, 24 * 256);
			for(int i=0; i<uvData.size(); i++){
				JSONObject classify = uvData.getJSONObject(i);
				row = sheet.createRow(rowNum++);
				cell = row.createCell(0);
				cell.setCellValue(classify.getString("vst_mcp_cid"));
				cell = row.createCell(1);
				cell.setCellValue(classify.getLongValue("vst_mcp_uv"));
			}
		}
	}
	
	private void setSheetSiteData(XSSFWorkbook wb, XSSFCellStyle style, int sheetNum, JSONArray vvData, JSONArray uvData){
		XSSFSheet sheet = wb.createSheet();
		wb.setSheetName(sheetNum, "平台播放");
		XSSFRow row;
		XSSFCell cell;
		int rowNum = 0;
		
		row = sheet.createRow(rowNum++);
		cell = row.createCell(0);
		cell.setCellValue("《播放量》");
		cell = row.createCell(1);
		cell.setCellValue("");
		
		// 播放量
		if(vvData != null){
			row = sheet.createRow(rowNum++);
			cell = row.createCell(0);
			cell.setCellValue("平台名称");
		    cell.setCellStyle(style);
		    sheet.setColumnWidth(0, 24 * 256);
		    cell = row.createCell(1);
			cell.setCellValue("播放量");
		    cell.setCellStyle(style);
		    sheet.setColumnWidth(1, 24 * 256);
		    cell = row.createCell(2);
			cell.setCellValue("观看人数");
		    cell.setCellStyle(style);
		    sheet.setColumnWidth(2, 24 * 256);
		    cell = row.createCell(3);
			cell.setCellValue("人均播放量");
		    cell.setCellStyle(style);
		    sheet.setColumnWidth(3, 24 * 256);
		    cell = row.createCell(4);
			cell.setCellValue("人均时长(m)");
		    cell.setCellStyle(style);
		    sheet.setColumnWidth(4, 24 * 256);
			for(int i=0; i<vvData.size(); i++){
				JSONObject site = vvData.getJSONObject(i);
				row = sheet.createRow(rowNum++);
				cell = row.createCell(0);
				cell.setCellValue(site.getString("vst_ms_site"));
				cell = row.createCell(1);
				cell.setCellValue(site.getLongValue("vst_ms_vv"));
				cell = row.createCell(2);
				cell.setCellValue(site.getLongValue("vst_ms_uv"));
				cell = row.createCell(3);
				cell.setCellValue(site.getString("vst_ms_avg"));
				cell = row.createCell(4);
				cell.setCellValue(VstTools.toMinute(site.getLongValue("vst_ms_playtime_avg")*1000));
			}
		}
		
		rowNum += 2;
		row = sheet.createRow(rowNum++);
		cell = row.createCell(0);
		cell.setCellValue("《观看人数》");
		cell = row.createCell(1);
		cell.setCellValue("");
		
		// 观看人数
		if(uvData != null){
			row = sheet.createRow(rowNum++);
			cell = row.createCell(0);
			cell.setCellValue("平台名称");
		    cell.setCellStyle(style);
		    sheet.setColumnWidth(0, 24 * 256);
		    cell = row.createCell(1);
			cell.setCellValue("播放量");
		    cell.setCellStyle(style);
		    sheet.setColumnWidth(1, 24 * 256);
		    cell = row.createCell(2);
			cell.setCellValue("观看人数");
		    cell.setCellStyle(style);
		    sheet.setColumnWidth(2, 24 * 256);
		    cell = row.createCell(3);
			cell.setCellValue("人均播放量");
		    cell.setCellStyle(style);
		    sheet.setColumnWidth(3, 24 * 256);
		    cell = row.createCell(4);
			cell.setCellValue("人均时长(m)");
		    cell.setCellStyle(style);
		    sheet.setColumnWidth(4, 24 * 256);
			for(int i=0; i<uvData.size(); i++){
				JSONObject site = uvData.getJSONObject(i);
				row = sheet.createRow(rowNum++);
				cell = row.createCell(0);
				cell.setCellValue(site.getString("vst_ms_site"));
				cell = row.createCell(1);
				cell.setCellValue(site.getLongValue("vst_ms_vv"));
				cell = row.createCell(2);
				cell.setCellValue(site.getLongValue("vst_ms_uv"));
				cell = row.createCell(3);
				cell.setCellValue(site.getString("vst_ms_avg"));
				cell = row.createCell(4);
				cell.setCellValue(VstTools.toMinute(site.getLongValue("vst_ms_playtime_avg")*1000));
			}
		}
	}
	
	private void setSheetMovieData(XSSFWorkbook wb, XSSFCellStyle style, int sheetNum, String sheetName, JSONArray vvData, JSONArray uvData){
		XSSFSheet sheet = wb.createSheet();
		wb.setSheetName(sheetNum, sheetName);
		XSSFRow row;
		XSSFCell cell;
		int rowNum = 0;
		
		row = sheet.createRow(rowNum++);
		cell = row.createCell(0);
		cell.setCellValue("《播放量》");
		cell = row.createCell(1);
		cell.setCellValue("");
		
		// 播放量
		if(vvData != null){
			row = sheet.createRow(rowNum++);
			cell = row.createCell(0);
			cell.setCellValue("影片名称");
		    cell.setCellStyle(style);
		    sheet.setColumnWidth(0, 24 * 256);
		    cell = row.createCell(1);
			cell.setCellValue("播放量");
		    cell.setCellStyle(style);
		    sheet.setColumnWidth(1, 24 * 256);
		    cell = row.createCell(2);
			cell.setCellValue("观看人数");
		    cell.setCellStyle(style);
		    sheet.setColumnWidth(2, 24 * 256);
		    cell = row.createCell(3);
			cell.setCellValue("人均播放量");
		    cell.setCellStyle(style);
		    sheet.setColumnWidth(3, 24 * 256);
		    cell = row.createCell(4);
			cell.setCellValue("人均时长(m)");
		    cell.setCellStyle(style);
		    sheet.setColumnWidth(4, 24 * 256);
			for(int i=0; i<vvData.size(); i++){
				JSONObject site = vvData.getJSONObject(i);
				row = sheet.createRow(rowNum++);
				cell = row.createCell(0);
				cell.setCellValue(site.getString("vst_mp_name"));
				cell = row.createCell(1);
				cell.setCellValue(site.getLongValue("vst_mp_vv"));
				cell = row.createCell(2);
				cell.setCellValue(site.getLongValue("vst_mp_uv"));
				cell = row.createCell(3);
				cell.setCellValue(site.getString("vst_mp_avg"));
				cell = row.createCell(4);
				cell.setCellValue(VstTools.toMinute(site.getLongValue("vst_mp_playtime_avg")*1000));
			}
		}
		
		rowNum += 2;
		row = sheet.createRow(rowNum++);
		cell = row.createCell(0);
		cell.setCellValue("《观看人数》");
		cell = row.createCell(1);
		cell.setCellValue("");
		
		// 观看人数
		if(uvData != null){
			row = sheet.createRow(rowNum++);
			cell = row.createCell(0);
			cell.setCellValue("影片名称");
		    cell.setCellStyle(style);
		    sheet.setColumnWidth(0, 24 * 256);
		    cell = row.createCell(1);
			cell.setCellValue("播放量");
		    cell.setCellStyle(style);
		    sheet.setColumnWidth(1, 24 * 256);
		    cell = row.createCell(2);
			cell.setCellValue("观看人数");
		    cell.setCellStyle(style);
		    sheet.setColumnWidth(2, 24 * 256);
		    cell = row.createCell(3);
			cell.setCellValue("人均播放量");
		    cell.setCellStyle(style);
		    sheet.setColumnWidth(3, 24 * 256);
		    cell = row.createCell(4);
			cell.setCellValue("人均时长(m)");
		    cell.setCellStyle(style);
		    sheet.setColumnWidth(4, 24 * 256);
			for(int i=0; i<uvData.size(); i++){
				JSONObject site = uvData.getJSONObject(i);
				row = sheet.createRow(rowNum++);
				cell = row.createCell(0);
				cell.setCellValue(site.getString("vst_mp_name"));
				cell = row.createCell(1);
				cell.setCellValue(site.getLongValue("vst_mp_vv"));
				cell = row.createCell(2);
				cell.setCellValue(site.getLongValue("vst_mp_uv"));
				cell = row.createCell(3);
				cell.setCellValue(site.getString("vst_mp_avg"));
				cell = row.createCell(4);
				cell.setCellValue(VstTools.toMinute(site.getLongValue("vst_mp_playtime_avg")*1000));
			}
		}
	}
	

	/**
	 * 获取邮件内容
	 */
	@Override
	public String getEmailContent(Map<String, Object> params, String path) throws SystemException {
		String result = null;
		try{
			JSONObject json = getIndexData(params);
			
			String row0 = "background: #c9daf8;height: 32px;text-indent: 1em;word-break: break-all;";
			String row1 = "background: #f3f3f3;height: 32px;text-indent: 1em;word-break: break-all;";
			String row2 = "background: #fff;height: 32px;text-indent: 1em;word-break: break-all;";
			String line04 = "border-bottom: 1px solid #CCC;width: 100%;margin: auto;border-collapse:collapse";
			String td = "font: 12px/ 25px Arial, Helvetica, sans-serif, '宋体';";
			
			StringBuffer table = new StringBuffer("<html><head><body><h1 style='text-align:center;font-size:24px;font-weight: bold;'><font color='#8DB6CD'>微视听日报</font></h1>");
			table.append("<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='text' style='height:32px;width:130px;font-size:16px;border-radius:5px;' readonly='readonly' value='"+params.get("date")+"'/><br /><br />");
			
//			// 应用指标
//			table.append("<br /><span style='font-size: 20px; font-weight: bold; margin-top: 15px; margin-right: 15px; margin-bottom: 15px; margin-left: 15px;'>应用指标</span><hr />");
//			table.append("<table border='1px' bordercolor='#c9daf8' cellspacing='0px' style='").append(line04).append("'><tr style='").append(row0).append("'><td style=\"").append(td).append("\" width='25%'>指标</td><td style=\"").append(td).append("\" width='25%'>总量</td><td style=\"").append(td).append("\" width='25%'>天环比</td><td style=\"").append(td).append("\" width='25%'>周环比</td></tr>")
//				//累计用户
//				.append("<tr style='").append(row1).append("'><td style=\"").append(td).append("\">累计用户:</td><td style=\"").append(td).append("\">").append(formateNum(json.getString("sumNum"))).append("</td><td style=\"").append(td).append("\"><span style='color:").append(sumCompareDayColor).append("'>").append(json.getString("sumCompareDay")).append("</span></td><td style=\"").append(td).append("\"><span style='color:").append(sumCompareWeekColor).append("'>").append(json.getString("sumCompareWeek")).append("</span></td></tr>")
//				//新增用户
//				.append("<tr style='").append(row2).append("'><td style=\"").append(td).append("\">新增用户:</td><td style=\"").append(td).append("\">").append(formateNum(json.getString("newNum"))).append("</td><td style=\"").append(td).append("\"><span style='color:").append(newCompareDayColor).append("'>").append(json.getString("newCompareDay")).append("</span></td><td style=\"").append(td).append("\"><span style='color:").append(newCompareWeekColor).append("'>").append(json.getString("newCompareWeek")).append("</span></td></tr>")
//				//活跃用户
//				.append("<tr style='").append(row1).append("'><td style=\"").append(td).append("\">日活跃用户:</td><td style=\"").append(td).append("\">").append(formateNum(json.getString("activeDay1"))).append("</td><td style=\"").append(td).append("\"><span style='color:").append(activecDay1Color).append("'>").append(json.getString("activecDay1")).append("</span></td><td style=\"").append(td).append("\"><span style='color:").append(activecWeek1Color+"'>").append(json.getString("activecWeek1")).append("</span></td></tr>")
//				.append("<tr style='").append(row2).append("'><td style=\"").append(td).append("\">周活跃用户:</td><td style=\"").append(td).append("\">").append(formateNum(json.getString("activeDay7"))).append("</td><td style=\"").append(td).append("\"><span style='color:").append(activecDay7Color).append("'>").append(json.getString("activecDay7")).append("</span></td><td style=\"").append(td).append("\"><span style='color:").append(activecWeek7Color).append("'>").append(json.getString("activecWeek7")).append("</span></td></tr>")
//				.append("<tr style='").append(row1).append("'><td style=\"").append(td).append("\">月活跃用户:</td><td style=\"").append(td).append("\">").append(formateNum(json.getString("activeDay30"))).append("</td><td style=\"").append(td).append("\"><span style='color:").append(activecDay30Color).append("'>").append(json.getString("activecDay30")).append("</span></td><td style=\"").append(td).append("\"><span style='color:").append(activecWeek30Color).append("'>").append(json.getString("activecWeek30")).append("</span></td></tr>")
//				.append("<tr style='").append(row1).append("'><td style=\"").append(td).append("\">季活跃用户:</td><td style=\"").append(td).append("\">").append(formateNum(json.getString("activeDay91"))).append("</td><td style=\"").append(td).append("\"><span style='color:").append(activecDay91Color).append("'>").append(json.getString("activecDay91")).append("</span></td><td style=\"").append(td).append("\"><span style='color:").append(activecWeek91Color).append("'>").append(json.getString("activecWeek91")).append("</span></td></tr>")
//				//留存用户
//				.append("<tr style='").append(row2).append("'><td style=\"").append(td).append("\">次日留存用户:</td><td style=\"").append(td).append("\">").append(formateNum(json.getString("retainDay1"))).append("</td><td style=\"").append(td).append("\"><span style='color:").append(retainCompareDay1Color).append("'>").append(json.getString("retainCompareDay1")).append("</span></td><td style=\"").append(td).append("\"><span style='color:").append(retainCompareWeek1Color+"'>").append(json.getString("retainCompareWeek1")).append("</span></td></tr>")
//				.append("<tr style='").append(row1).append("'><td style=\"").append(td).append("\">次日留存率:</td><td style=\"").append(td).append("\">").append(json.getString("retainDay1Rate")).append("</td><td style=\"").append(td).append("\">--</td><td style=\"").append(td).append("\">--</td></tr>")
//				.append("<tr style='").append(row2).append("'><td style=\"").append(td).append("\">周留存用户:</td><td style=\"").append(td).append("\">").append(formateNum(json.getString("retainDay7"))).append("</td><td style=\"").append(td).append("\"><span style='color:").append(retainCompareDay7Color).append("'>").append(json.getString("retainCompareDay7")).append("</span></td><td style=\"").append(td).append("\"><span style='color:").append(retainCompareWeek7Color+"'>").append(json.getString("retainCompareWeek7")).append("</span></td></tr>")
//				.append("<tr style='").append(row1).append("'><td style=\"").append(td).append("\">周留存用户率:</td><td style=\"").append(td).append("\">").append(json.getString("retainDay7Rate")).append("</td><td style=\"").append(td).append("\">--</td><td style=\"").append(td).append("\">--</td></tr>")
//				.append("<tr style='").append(row2).append("'><td style=\"").append(td).append("\">月留存用户:</td><td style=\"").append(td).append("\">").append(formateNum(json.getString("retainDay30"))).append("</td><td style=\"").append(td).append("\"><span style='color:").append(retainCompareDay30Color).append("'>").append(json.getString("retainCompareDay30")).append("</span></td><td style=\"").append(td).append("\"><span style='color:").append(retainCompareWeek30Color+"'>").append(json.getString("retainCompareWeek30")).append("</span></td></tr>")
//				.append("<tr style='").append(row1).append("'><td style=\"").append(td).append("\">月留存用户率:</td><td style=\"").append(td).append("\">").append(json.getString("retainDay30Rate")).append("</td><td style=\"").append(td).append("\">--</td><td style=\"").append(td).append("\">--</td></tr>")
//				//流失用户
//				.append("<tr style='").append(row2).append("'><td style=\"").append(td).append("\">30天流失用户:</td><td style=\"").append(td).append("\">").append(formateNum(json.getString("churnDay30"))).append("</td><td style=\"").append(td).append("\"><span style='color:").append(churncDay30Color).append("'>").append(json.getString("churncDay30")).append("</span></td><td style=\"").append(td).append("\"><span style='color:"+churncWeek30Color).append("'>").append(json.getString("churncWeek30")).append("</span></td></tr>")
//				.append("<tr style='").append(row1).append("'><td style=\"").append(td).append("\">121天流失用户:</td><td style=\"").append(td).append("\">").append(formateNum(json.getString("churnDay121"))).append("</td><td style=\"").append(td).append("\"><span style='color:").append(churncDay121Color).append("'>").append(json.getString("churncDay121")).append("</span></td><td style=\"").append(td).append("\"><span style='color:"+churncWeek121Color).append("'>").append(json.getString("churncWeek121")).append("</span></td></tr>")
//				.append("<tr style='").append(row2).append("'><td style=\"").append(td).append("\">365天流失用户:</td><td style=\"").append(td).append("\">").append(formateNum(json.getString("churnDay365"))).append("</td><td style=\"").append(td).append("\"><span style='color:").append(churncDay365Color).append("'>").append(json.getString("churncDay365")).append("</span></td><td style=\"").append(td).append("\"><span style='color:").append(churncWeek365Color).append("'>").append(json.getString("churncWeek365")).append("</span></td></tr>")
//				//播放量
//				.append("<tr style='").append(row1).append("'><td style=\"").append(td).append("\">视频播放总量:</td><td style=\"").append(td).append("\">").append(formateNum(json.getString("moviePlayTotal"))).append("</td><td style=\"").append(td).append("\"><span style='color:").append(moviePlayCDayColor+"'>").append(json.getString("moviePlayCDay")).append("</span></td><td style=\"").append(td).append("\"><span style='color:").append(moviePlayCWeekColor).append("'>").append(json.getString("moviePlayCWeek")).append("</span></td></tr>")
//				.append("<tr style='").append(row2).append("'><td style=\"").append(td).append("\">日播放时长(小时):</td><td style=\"").append(td).append("\">").append(json.getString("moviePlayDuration")).append("</td><td style=\"").append(td).append("\"><span style='color:").append(moviePlayDCDayColor).append("'>").append(json.getString("moviePlayDCDay")).append("</span></td><td style=\"").append(td).append("\"><span style='color:").append(moviePlayDCWeekColor).append("'>").append(json.getString("moviePlayDCWeek")).append("</span></td></tr>")
//				.append("<tr style='").append(row1).append("'><td style=\"").append(td).append("\">日播放人数:</td><td style=\"").append(td).append("\">").append(formateNum(json.getString("moviePlayUTotal"))).append("</td><td style=\"").append(td).append("\"><span style='color:").append(moviePlayUCDayColor).append("'>").append(json.getString("moviePlayUCDay")).append("</span></td><td style=\"").append(td).append("\"><span style='color:").append(moviePlayUCWeekColor).append("'>").append(json.getString("moviePlayUCWeek")).append("</span></td></tr>")
//				.append("<tr style='").append(row2).append("'><td style=\"").append(td).append("\">日人均播放量:</td><td style=\"").append(td).append("\">").append(json.getString("moviePlayUAvg")).append("</td><td style=\"").append(td).append("\">--</td><td style=\"").append(td).append("\">--</td></tr>")
//				.append("<tr style='").append(row1).append("'><td style=\"").append(td).append("\">人均播放时长(分钟):</td><td style=\"").append(td).append("\">").append(json.getString("moviePlayUAvgDuration")).append("</td><td style=\"").append(td).append("\">--</td><td style=\"").append(td).append("\">--</td></tr>")
//				.append("</table>");
			
			// 分类播放占比
			table.append("<br /><span style='font-size: 20px; font-weight: bold; margin-top: 15px; margin-right: 15px; margin-bottom: 15px; margin-left: 15px;'>分类播放占比</span><hr />");
			table.append("<table border='1px' bordercolor='#c9daf8' cellspacing='0px' style='").append(line04).append("'><tr style='").append(row0).append("'>")
				 .append("<td style=\"").append(td).append("\" width='11%'>分类</td>")
				 .append("<td style=\"").append(td).append("\" width='11%'>播放量</td>")
				 .append("<td style=\"").append(td).append("\" width='11%'>播放量占比</td>")
				 .append("<td style=\"").append(td).append("\" width='11%'>播放人数</td>")
				 .append("<td style=\"").append(td).append("\" width='11%'>播放人数占比</td>")
				 .append("<td style=\"").append(td).append("\" width='12%'>播放时长(小时)</td>")
				 .append("<td style=\"").append(td).append("\" width='11%'>播放时长占比</td>")
				 .append("<td style=\"").append(td).append("\" width='11%'>人均播放量</td>")
				 .append("<td style=\"").append(td).append("\" width='11%'>人均时长(分钟)</td></tr>");
			JSONObject classifyData = getClassifyPlay(params);
			JSONArray classifyArr = classifyData.getJSONArray("data");
			for(int i=0; i<classifyArr.size(); i++){
				JSONObject bean = classifyArr.getJSONObject(i);
				int row = i%2+1;
				if(row == 1){
					table.append("<tr style='").append(row1).append("'>");
				}else{
					table.append("<tr style='").append(row2).append("'>");
				}
				table.append("<td style=\"").append(td).append("\">").append(bean.getString("cidName")).append("</td>")
					 .append("<td style=\"").append(td).append("\">").append(formateNum(bean.getString("vst_mcp_vv"))).append("</td>")
					 .append("<td style=\"").append(td).append("\">").append(bean.getString("vvRatio")).append("</td>")
					 .append("<td style=\"").append(td).append("\">").append(formateNum(bean.getString("vst_mcp_uv"))).append("</td>")
					 .append("<td style=\"").append(td).append("\">").append(bean.getString("uvRatio")).append("</td>")
					 .append("<td style=\"").append(td).append("\">").append(formateNum(VstTools.toHour(bean.getLong("vst_mcp_playtime")*1000)+"")).append("</td>")
					 .append("<td style=\"").append(td).append("\">").append(bean.getString("ptRatio")).append("</td>")
					 .append("<td style=\"").append(td).append("\">").append(bean.getString("vst_mcp_avg")).append("</td>")
					 .append("<td style=\"").append(td).append("\">").append(VstTools.toMinute(bean.getLong("vst_mcp_playtime_avg")*1000)).append("</td></tr>");
			}
			JSONObject classifySum = classifyData.getJSONObject("sumData");
			table.append("<tr style='").append(row2).append("'>")
				 .append("<td style=\"").append(td).append("\">").append("总计").append("</td>")
				 .append("<td style=\"").append(td).append("\">").append(formateNum(classifySum.getString("vvSum"))).append("</td>")
				 .append("<td style=\"").append(td).append("\">").append("--").append("</td>")
				 .append("<td style=\"").append(td).append("\">").append(formateNum(classifySum.getString("uvSum"))).append("</td>")
				 .append("<td style=\"").append(td).append("\">").append("--").append("</td>")
				 .append("<td style=\"").append(td).append("\">").append(formateNum(VstTools.toHour(classifySum.getLong("ptSum")*1000)+"")).append("</td>")
				 .append("<td style=\"").append(td).append("\">").append("--").append("</td>")
				 .append("<td style=\"").append(td).append("\">").append("--").append("</td>")
				 .append("<td style=\"").append(td).append("\">").append("--").append("</td></tr>");
			table.append("</table>");
			
			
//			// 分类播放饼状图
//			JSONArray classifyVV = json.getJSONArray("classifyVV");
//			JSONArray classifyUV = json.getJSONArray("classifyUV");
//			List<String> imagePath = new ArrayList<String>();
//			String imageName1 = "classifyVV.png";
//			String imageName2 = "classifyUV.png";
//			ChartUtils.getImageFile(classifyVV, "vst_mcp_cid", "vst_mcp_vv", path + "/" + imageName1, 750, 380);
//			ChartUtils.getImageFile(classifyUV, "vst_mcp_cid", "vst_mcp_uv", path + "/" + imageName2, 750, 380);
//			imagePath.add(path + "/" + imageName1);
//			imagePath.add(path + "/" + imageName2);
//			table.append("<table style='width: 100%;margin: auto;border-collapse:collapse'><tr>")
//				 .append("<td width='50%'><br /><span style='font-size: 20px; font-weight: bold; margin-top: 15px; margin-right: 15px; margin-bottom: 15px; margin-left: 15px;'>分类播放量占比</span><hr /><img src='cid:").append(imageName1).append("' style='height: auto; width: auto; width:100%;'></td>")
//				 .append("<td width='50%'><br /><span style='font-size: 20px; font-weight: bold; margin-top: 15px; margin-right: 15px; margin-bottom: 15px; margin-left: 15px;'>分类播放人数占比</span><hr /><img src='cid:").append(imageName2).append("' style='height: auto; width: auto; width:100%;'></td>")
//				 .append("</tr></table>");
			
			// 平台播放量排行榜
			table.append("<table>");
			table.append("<tr style='height: 32px;text-indent: 1em;word-break: break-all;' width='100%'><td style=\"").append(td).append("\" width='50%'><br /><span style='font-size: 20px; font-weight: bold; margin-top: 15px; margin-right: 15px; margin-bottom: 15px; margin-left: 15px;'>平台播放量排行榜Top10</span><hr />");
			table.append("<table style='").append(line04).append("'><tr style='").append(row0).append("'>")
				 .append("<td style=\"").append(td).append("\" width='10%'>#</td>")
				 .append("<td style=\"").append(td).append("\" width='30%'>平台名称</td>")
				 .append("<td style=\"").append(td).append("\" width='15%'>播放量</td>")
				 .append("<td style=\"").append(td).append("\" width='15%'>观看人数</td>")
				 .append("<td style=\"").append(td).append("\" width='15%'>人均播放量</td>")
				 .append("<td style=\"").append(td).append("\" width='15%'>人均时长(分钟)</td></tr>");
			JSONArray siteVV = json.getJSONArray("siteVV");
			for(int i=0; i<siteVV.size(); i++){
				JSONObject bean = siteVV.getJSONObject(i);
				int row = i%2+1;
				int num = i + 1;
				String playColor = "green";
				String playText = "↑";
				if(bean.getString("vst_ms_vv_dod").contains("-")){
					playColor="red";
					playText="↓";
				}
				String userColor = "green";
				String userText = "↑";
				if(bean.getString("vst_ms_uv_dod").contains("-")){
					userColor="red";
					userText="↓";
				}
				if(row == 1){
					table.append("<tr style='").append(row1).append("'>");
				}else{
					table.append("<tr style='").append(row2).append("'>");
				}
				table.append("<td style=\"").append(td).append("\">").append(num).append("</td>")
					 .append("<td style=\"").append(td).append("\">").append(bean.getString("vst_ms_site")).append("</td>")
					 .append("<td style=\"").append(td).append("\">").append(formateNum(bean.getString("vst_ms_vv"))).append("&nbsp;&nbsp;<span style='font-weight:bold;font-size:16px;font-weight:1200;color:").append(playColor).append("'><strong>").append(playText).append("</strong></span></td>")
					 .append("<td style=\"").append(td).append("\">").append(formateNum(bean.getString("vst_ms_uv"))).append("&nbsp;&nbsp;<span style='font-weight:bold;font-size:16px;font-weight:1200;color:").append(userColor).append("'><strong>").append(userText).append("</strong></span></td>")
					 .append("<td style=\"").append(td).append("\">").append(bean.getString("vst_ms_avg")).append("</td>")
					 .append("<td style=\"").append(td).append("\">").append(VstTools.toMinute(bean.getLong("vst_ms_playtime_avg")*1000)).append("</td></tr>");
			}
			table.append("</table></td><td width='15px'></td>");
			
			// 平台观看人数排行榜
			table.append("<td style=\"").append(td).append("\" width='50%'><br /><span style='font-size: 20px; font-weight: bold; margin-top: 15px; margin-right: 15px; margin-bottom: 15px; margin-left: 15px;'>平台观看人数排行榜Top10</span><hr />");
			table.append("<table style='").append(line04).append("'><tr style='").append(row0).append("'>")
			 	 .append("<td style=\"").append(td).append("\" width='10%'>#</td>")
			 	 .append("<td style=\"").append(td).append("\" width='30%'>平台名称</td>")
			 	 .append("<td style=\"").append(td).append("\" width='15%'>播放量</td>")
			 	 .append("<td style=\"").append(td).append("\" width='15%'>观看人数</td>")
			 	 .append("<td style=\"").append(td).append("\" width='15%'>人均播放量</td>")
			 	 .append("<td style=\"").append(td).append("\" width='15%'>人均时长(分钟)</td></tr>");
			JSONArray siteUV = json.getJSONArray("siteUV");
			for(int i=0; i<siteUV.size(); i++){
				JSONObject bean = siteUV.getJSONObject(i);
				int row = i%2+1;
				int num = i + 1;
				String playColor = "green";
				String playText = "↑";
				if(bean.getString("vst_ms_vv_dod").contains("-")){
					playColor="red";
					playText="↓";
				}
				String userColor = "green";
				String userText = "↑";
				if(bean.getString("vst_ms_uv_dod").contains("-")){
					userColor="red";
					userText="↓";
				}
				if(row == 1){
					table.append("<tr style='").append(row1).append("'>");
				}else{
					table.append("<tr style='").append(row2).append("'>");
				}
				table.append("<td style=\"").append(td).append("\">").append(num).append("</td>")
					 .append("<td style=\"").append(td).append("\">").append(bean.getString("vst_ms_site")).append("</td>")
					 .append("<td style=\"").append(td).append("\">").append(formateNum(bean.getString("vst_ms_vv"))).append("&nbsp;&nbsp;<span style='font-weight:bold;font-size:16px;font-weight:1200;color:").append(playColor).append("'><strong>").append(playText).append("</strong></span></td>")
					 .append("<td style=\"").append(td).append("\">").append(formateNum(bean.getString("vst_ms_uv"))).append("&nbsp;&nbsp;<span style='font-weight:bold;font-size:16px;font-weight:1200;color:").append(userColor).append("'><strong>").append(userText).append("</strong></span></td>")
					 .append("<td style=\"").append(td).append("\">").append(bean.getString("vst_ms_avg")).append("</td>")
					 .append("<td style=\"").append(td).append("\">").append(VstTools.toMinute(bean.getLong("vst_ms_playtime_avg")*1000)).append("</td></tr>");
			}
			table.append("</table></td></tr>");
			
			// 影片分类排行榜
			//1电影、2电视剧、3动漫、4综艺、5纪录片、8少儿、514新闻、515自媒体、525体育、526教育、529游戏
			JSONObject movieVV = json.getJSONObject("movieVV");
			JSONObject movieUV = json.getJSONObject("movieUV");
			
			int[] cids = {1,2,3,4,5,8,514,515,525,526,529};
			for(int cid : cids){
				// 播放量排行榜
				table.append("<tr style='height: 32px;text-indent: 1em;word-break: break-all;' width='100%'><td style=\"").append(td).append("\" width='50%'><br /><span style='font-size: 20px; font-weight: bold; margin-top: 15px; margin-right: 15px; margin-bottom: 15px; margin-left: 15px;'>"+getTypeName(cid+"")+"播放量排行榜Top10</span><hr />");
				table.append("<table style='").append(line04).append("'><tr style='").append(row0).append("'>")
					 .append("<td style=\"").append(td).append("\" width='10%'>#</td>")
					 .append("<td style=\"").append(td).append("\" width='30%'>影片名称</td>")
					 .append("<td style=\"").append(td).append("\" width='15%'>播放量</td>")
					 .append("<td style=\"").append(td).append("\" width='15%'>观看人数</td>")
					 .append("<td style=\"").append(td).append("\" width='15%'>人均播放量</td>")
					 .append("<td style=\"").append(td).append("\" width='15%'>人均时长(分钟)</td></tr>");
				JSONArray vvArray = movieVV.getJSONArray("vv_"+cid);
				for(int i=0; i<vvArray.size(); i++){
					JSONObject bean = vvArray.getJSONObject(i);
					int row = i%2+1;
					int num = i + 1;
					String playColor = "green";
					String playText = "↑";
					if(bean.getString("vst_mp_vv_dod").contains("-")){
						playColor="red";
						playText="↓";
					}
					String userColor = "green";
					String userText = "↑";
					if(bean.getString("vst_mp_uv_dod").contains("-")){
						userColor="red";
						userText="↓";
					}
					if(row == 1){
						table.append("<tr style='").append(row1).append("'>");
					}else{
						table.append("<tr style='").append(row2).append("'>");
					}
					table.append("<td style=\"").append(td).append("\">").append(num).append("</td>")
					 	 .append("<td style=\"").append(td).append("\" title=\"").append(bean.getString("vst_mp_name")).append("\">").append(formatString(bean.getString("vst_mp_name"))).append("</td>")
					 	 .append("<td style=\"").append(td).append("\">").append(formateNum(bean.getString("vst_mp_vv"))).append("&nbsp;&nbsp;<span style='font-weight:bold;font-size:16px;font-weight:1200;color:").append(playColor).append("'><strong>").append(playText).append("</strong></span></td>")
					 	 .append("<td style=\"").append(td).append("\">").append(formateNum(bean.getString("vst_mp_uv"))).append("&nbsp;&nbsp;<span style='font-weight:bold;font-size:16px;font-weight:1200;color:").append(userColor).append("'><strong>").append(userText).append("</strong></span></td>")
					 	 .append("<td style=\"").append(td).append("\">").append(bean.getString("vst_mp_avg")).append("</td>")
					 	 .append("<td style=\"").append(td).append("\">").append(VstTools.toMinute(bean.getLong("vst_mp_playtime_avg")*1000)).append("</td></tr>");
				}
				table.append("</table></td><td width='15px'></td>");
				
				// 观看人数排行榜
				table.append("<td style=\"").append(td).append("\" width='50%'><br /><span style='font-size: 20px; font-weight: bold; margin-top: 15px; margin-right: 15px; margin-bottom: 15px; margin-left: 15px;'>"+getTypeName(cid+"")+"观看人数排行榜Top10</span><hr />");
				table.append("<table style='").append(line04).append("'><tr style='").append(row0).append("'>")
					 .append("<td style=\"").append(td).append("\" width='10%'>#</td>")
					 .append("<td style=\"").append(td).append("\" width='30%'>影片名称</td>")
					 .append("<td style=\"").append(td).append("\" width='15%'>播放量</td>")
					 .append("<td style=\"").append(td).append("\" width='15%'>观看人数</td>")
					 .append("<td style=\"").append(td).append("\" width='15%'>人均播放量</td>")
					 .append("<td style=\"").append(td).append("\" width='15%'>人均时长(分钟)</td></tr>");
				JSONArray uvArray = movieUV.getJSONArray("uv_"+cid);
				for(int i=0; i<uvArray.size(); i++){
					JSONObject bean = uvArray.getJSONObject(i);
					int row = i%2+1;
					int num = i + 1;
					String playColor = "green";
					String playText = "↑";
					if(bean.getString("vst_mp_vv_dod").contains("-")){
						playColor="red";
						playText="↓";
					}
					String userColor = "green";
					String userText = "↑";
					if(bean.getString("vst_mp_uv_dod").contains("-")){
						userColor="red";
						userText="↓";
					}
					if(row == 1){
						table.append("<tr style='").append(row1).append("'>");
					}else{
						table.append("<tr style='").append(row2).append("'>");
					}
					table.append("<td style=\"").append(td).append("\">").append(num).append("</td>")
					 	 .append("<td style=\"").append(td).append("\" title=\"").append(bean.getString("vst_mp_name")).append("\">").append(formatString(bean.getString("vst_mp_name"))).append("</td>")
					 	 .append("<td style=\"").append(td).append("\">").append(formateNum(bean.getString("vst_mp_vv"))).append("&nbsp;&nbsp;<span style='font-weight:bold;font-size:16px;font-weight:1200;color:").append(playColor).append("'><strong>").append(playText).append("</strong></span></td>")
					 	 .append("<td style=\"").append(td).append("\">").append(formateNum(bean.getString("vst_mp_uv"))).append("&nbsp;&nbsp;<span style='font-weight:bold;font-size:16px;font-weight:1200;color:").append(userColor).append("'><strong>").append(userText).append("</strong></span></td>")
					 	 .append("<td style=\"").append(td).append("\">").append(bean.getString("vst_mp_avg")).append("</td>")
					 	 .append("<td style=\"").append(td).append("\">").append(VstTools.toMinute(bean.getLong("vst_mp_playtime_avg")*1000)).append("</td></tr>");
				}
				table.append("</table></td></tr>");
			}
			
			table.append("</table></body></head></html>");
			
			result = table.toString();
		}catch(Exception e){
			logger.error("获取邮件内容失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 获取邮件内容(对外)
	 */
	@Override
	public String getEmailByOuter(Map<String, Object> params, double ratio) throws SystemException {
		String result = null;
		try{
			// 是否缓存
			String isCache = params.get("isCache")+"";
			
			// 1电影、2电视剧、3动漫、4综艺、5纪录片、8少儿
			int[] cids = {1,2,3,4,5,8};
			
			// 按播放量排序
			Map<String, Object> movieParam = new HashMap<String, Object>();
			movieParam.putAll(params);
			movieParam.put("orderBy", "vst_mp_vv");
			movieParam.put("order", "DESC");
			movieParam.put("limit", 10);
			movieParam.put("offset", 0);
			JSONObject movieVV = new JSONObject();
			
			for(int cid : cids){
				movieParam.put("vst_mp_cid", cid);
				List<Map<String, Object>> vvArray = null;
				if("false".equals(isCache)){
					vvArray = _vstMoviePlayDao.queryForList(movieParam);
				}else{
					vvArray = _vstMoviePlayDao.queryHomeList(movieParam);
				}
				movieVV.put("vv_"+cid, vvArray);
			}
			
			// 按观看人数排序
			movieParam.put("orderBy", "vst_mp_uv");
			movieParam.put("order", "DESC");
			JSONObject movieUV = new JSONObject();
			for(int cid : cids){
				movieParam.put("vst_mp_cid", cid);
				List<Map<String, Object>> uvArray = null;
				if("false".equals(isCache)){
					uvArray = _vstMoviePlayDao.queryForList(movieParam);
				}else{
					uvArray = _vstMoviePlayDao.queryHomeList(movieParam);
				}
				movieUV.put("uv_"+cid, uvArray);
			}
			
			String row0 = "background: #c9daf8;height: 32px;text-indent: 1em;word-break: break-all;";
			String row1 = "background: #f3f3f3;height: 32px;text-indent: 1em;word-break: break-all;";
			String row2 = "background: #fff;height: 32px;text-indent: 1em;word-break: break-all;";
			String line04 = "border-bottom: 1px solid #CCC;width: 100%;margin: auto;border-collapse:collapse";
			String td = "font: 12px/ 25px Arial, Helvetica, sans-serif, '宋体';";
			
			StringBuffer table = new StringBuffer("<html><head><body><h1 style='text-align:center;font-size:24px;font-weight: bold;'><font color='#8DB6CD'>微视听日报</font></h1>");
			table.append("<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='text' style='height:32px;width:130px;font-size:16px;border-radius:5px;' readonly='readonly' value='"+params.get("date")+"'/><br /><br />");
			
			// 影片分类排行榜
			table.append("<table>");
			for(int cid : cids){
				// 播放量排行榜
				table.append("<tr style='height: 32px;text-indent: 1em;word-break: break-all;' width='100%'><td style=\"").append(td).append("\" width='50%'><br /><span style='font-size: 20px; font-weight: bold; margin-top: 15px; margin-right: 15px; margin-bottom: 15px; margin-left: 15px;'>"+getTypeName(cid+"")+"播放量排行榜Top10</span><hr />");
				table.append("<table style='").append(line04).append("'><tr style='").append(row0).append("'>")
					 .append("<td style=\"").append(td).append("\" width='10%'>#</td>")
					 .append("<td style=\"").append(td).append("\" width='30%'>影片名称</td>")
					 .append("<td style=\"").append(td).append("\" width='15%'>播放量</td>")
					 .append("<td style=\"").append(td).append("\" width='15%'>观看人数</td>")
					 .append("<td style=\"").append(td).append("\" width='15%'>人均播放量</td>")
					 .append("<td style=\"").append(td).append("\" width='15%'>人均时长(分钟)</td></tr>");
				JSONArray vvArray = movieVV.getJSONArray("vv_"+cid);
				for(int i=0; i<vvArray.size(); i++){
					JSONObject bean = vvArray.getJSONObject(i);
					int row = i%2+1;
					int num = i + 1;
					String playColor = "green";
					String playText = "↑";
					if(bean.getString("vst_mp_vv_dod").contains("-")){
						playColor="red";
						playText="↓";
					}
					String userColor = "green";
					String userText = "↑";
					if(bean.getString("vst_mp_uv_dod").contains("-")){
						userColor="red";
						userText="↓";
					}
					if(row == 1){
						table.append("<tr style='").append(row1).append("'>");
					}else{
						table.append("<tr style='").append(row2).append("'>");
					}
					long vst_mp_vv = Math.round(bean.getLongValue("vst_mp_vv") * ratio);
					long vst_mp_uv = Math.round(bean.getLongValue("vst_mp_uv") * ratio);
					table.append("<td style=\"").append(td).append("\">").append(num).append("</td>")
					 	 .append("<td style=\"").append(td).append("\" title=\"").append(bean.getString("vst_mp_name")).append("\">").append(formatString(bean.getString("vst_mp_name"))).append("</td>")
					 	 .append("<td style=\"").append(td).append("\">").append(formateNum(vst_mp_vv+"")).append("&nbsp;&nbsp;<span style='font-weight:bold;font-size:16px;font-weight:1200;color:").append(playColor).append("'><strong>").append(playText).append("</strong></span></td>")
					 	 .append("<td style=\"").append(td).append("\">").append(formateNum(vst_mp_uv+"")).append("&nbsp;&nbsp;<span style='font-weight:bold;font-size:16px;font-weight:1200;color:").append(userColor).append("'><strong>").append(userText).append("</strong></span></td>")
					 	 .append("<td style=\"").append(td).append("\">").append(bean.getString("vst_mp_avg")).append("</td>")
					 	 .append("<td style=\"").append(td).append("\">").append(VstTools.toMinute(bean.getLong("vst_mp_playtime_avg")*1000)).append("</td></tr>");
				}
				table.append("</table></td><td width='15px'></td>");
				
				// 观看人数排行榜
				table.append("<td style=\"").append(td).append("\" width='50%'><br /><span style='font-size: 20px; font-weight: bold; margin-top: 15px; margin-right: 15px; margin-bottom: 15px; margin-left: 15px;'>"+getTypeName(cid+"")+"观看人数排行榜Top10</span><hr />");
				table.append("<table style='").append(line04).append("'><tr style='").append(row0).append("'>")
					 .append("<td style=\"").append(td).append("\" width='10%'>#</td>")
					 .append("<td style=\"").append(td).append("\" width='30%'>影片名称</td>")
					 .append("<td style=\"").append(td).append("\" width='15%'>播放量</td>")
					 .append("<td style=\"").append(td).append("\" width='15%'>观看人数</td>")
					 .append("<td style=\"").append(td).append("\" width='15%'>人均播放量</td>")
					 .append("<td style=\"").append(td).append("\" width='15%'>人均时长(分钟)</td></tr>");
				JSONArray uvArray = movieUV.getJSONArray("uv_"+cid);
				for(int i=0; i<uvArray.size(); i++){
					JSONObject bean = uvArray.getJSONObject(i);
					int row = i%2+1;
					int num = i + 1;
					String playColor = "green";
					String playText = "↑";
					if(bean.getString("vst_mp_vv_dod").contains("-")){
						playColor="red";
						playText="↓";
					}
					String userColor = "green";
					String userText = "↑";
					if(bean.getString("vst_mp_uv_dod").contains("-")){
						userColor="red";
						userText="↓";
					}
					if(row == 1){
						table.append("<tr style='").append(row1).append("'>");
					}else{
						table.append("<tr style='").append(row2).append("'>");
					}
					long vst_mp_vv = Math.round(bean.getLongValue("vst_mp_vv") * ratio);
					long vst_mp_uv = Math.round(bean.getLongValue("vst_mp_uv") * ratio);
					table.append("<td style=\"").append(td).append("\">").append(num).append("</td>")
					 	 .append("<td style=\"").append(td).append("\" title=\"").append(bean.getString("vst_mp_name")).append("\">").append(formatString(bean.getString("vst_mp_name"))).append("</td>")
					 	 .append("<td style=\"").append(td).append("\">").append(formateNum(vst_mp_vv+"")).append("&nbsp;&nbsp;<span style='font-weight:bold;font-size:16px;font-weight:1200;color:").append(playColor).append("'><strong>").append(playText).append("</strong></span></td>")
					 	 .append("<td style=\"").append(td).append("\">").append(formateNum(vst_mp_uv+"")).append("&nbsp;&nbsp;<span style='font-weight:bold;font-size:16px;font-weight:1200;color:").append(userColor).append("'><strong>").append(userText).append("</strong></span></td>")
					 	 .append("<td style=\"").append(td).append("\">").append(bean.getString("vst_mp_avg")).append("</td>")
					 	 .append("<td style=\"").append(td).append("\">").append(VstTools.toMinute(bean.getLong("vst_mp_playtime_avg")*1000)).append("</td></tr>");
				}
				table.append("</table></td></tr>");
			}
			
			table.append("</table></body></head></html>");
			
			result = table.toString();
		}catch(Exception e){
			logger.error("获取邮件内容失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 获取应用指标数据
	 */
	@Override
	public JSONObject getApplicationIndex(Map<String, Object> params) throws SystemException {
		JSONObject result = new JSONObject();
		try{
			
		}catch(Exception e){
			logger.error("获取应用指标数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 获取分类播放数据
	 */
	@Override
	public JSONObject getClassifyPlay(Map<String, Object> params) throws SystemException {
		JSONObject result = new JSONObject();
		try{
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				params.put("date", VstTools.parseInt(params.get("date")+""));
			}
			params.put("vst_mcp_special_type", -2);
			params.put("limit", 10);
			
			List<Map<String, Object>> data = _vstMovieClassifyPlayDao.queryForList(params);
			if(data != null && data.size() > 0){
				int count = 0;
				long vvSum = 0;
				long uvSum = 0;
				long ptSum = 0;
				// 计算总量
				for(Map<String, Object> map : data){
					count ++;
					vvSum += ToolsNumber.parseLong(map.get("vst_mcp_vv")+"");
					uvSum += ToolsNumber.parseLong(map.get("vst_mcp_uv")+"");
					ptSum += ToolsNumber.parseLong(map.get("vst_mcp_playtime")+"");
				}
				// 分类数据
				JSONArray classifyArray = new JSONArray(10);
				for(Map<String, Object> map : data){
					JSONObject bean = new JSONObject(map);
					// 分类名称
					bean.put("cidName", getTypeName(bean.getString("vst_mcp_cid")));
					// 各占比
					double vvRatio = Math.round(bean.getLongValue("vst_mcp_vv")*10000.0/vvSum)/100.0;
					bean.put("vvRatio", vvRatio+"%");
					double uvRatio = Math.round(bean.getLongValue("vst_mcp_uv")*10000.0/uvSum)/100.0;
					bean.put("uvRatio", uvRatio+"%");
					double ptRatio = Math.round(bean.getLongValue("vst_mcp_playtime")*10000.0/ptSum)/100.0;
					bean.put("ptRatio", ptRatio+"%");
					classifyArray.add(bean);
				}
				result.put("data", classifyArray);
				// 汇总数据
				JSONObject sumData = new JSONObject(3);
				sumData.put("vvSum", vvSum);
				sumData.put("uvSum", uvSum);
				sumData.put("ptSum", ptSum);
				result.put("sumData", sumData);
			}
		}catch(Exception e){
			logger.error("获取分类播放数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 格式化数字为千分位显示
	 * @param 要格式化的数字
	 * @return
	 */
	private String formateNum(String text) {
		DecimalFormat df = null;
		if (text.indexOf(".") > 0) {
			if (text.length() - text.indexOf(".") - 1 == 0) {
				df = new DecimalFormat("###,##0.");
			} else if (text.length() - text.indexOf(".") - 1 == 1) {
				df = new DecimalFormat("###,##0.0");
			} else {
				df = new DecimalFormat("###,##0.00");
			}
		} else {
			df = new DecimalFormat("###,##0");
		}
		double number = 0.0;
		try {
			number = Double.parseDouble(text);
		} catch (Exception e) {
			number = 0.0;
		}
		return df.format(number);
	}
	
	/**
	 * 格式化字符串
	 * @param str
	 * @return
	 */
	private String formatString(String str){
		String result = ToolsString.checkEmpty(str);
		if(str != null){
			if(str.length() > 16){
				result = str.subSequence(0, 16) + "...";
			}
		}
		return result;
	}
	
	/**
	 * 获取分类名称
	 * @param cid
	 * @return
	 */
	private String getTypeName(String cid){
		String result = cid;
		Map<String, Object> params = new HashMap<String, Object>(3);
		params.put("vst_table_name", "vst_movie");
		params.put("vst_column_name", "classify");
		params.put("vst_state", VstConstants.STATE_AVALIABLE);
		Map<String, String> classifyMap = _baseService.getDictionaryForMap(params);
		if(classifyMap.containsKey(cid)){
			result = classifyMap.get(cid);
		}
		return result;
	}

}
