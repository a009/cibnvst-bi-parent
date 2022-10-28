package com.vst.defend.service.rank.impl;

import com.vst.common.pojo.VstSysOperateLog;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.CutPage;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.communal.util.VstTools;
import com.vst.defend.dao2.movie.VstMovieSearchDao;
import com.vst.defend.service.rank.MovieSearchRankService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author lhp
 * @date 2018-2-1 下午03:42:12
 * @version
 */
@Service
public class MovieSearchRankServiceImpl implements MovieSearchRankService {
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(MovieSearchRankServiceImpl.class);
	
	/**
	 * 影片搜索Dao接口
	 */
	@Resource
	private VstMovieSearchDao _vstMovieSearchDao;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询分页数据
	 */
	@Override
	public ReportBean getCutPageData(CutPage cutPage) throws SystemException {
		ReportBean bean = new ReportBean();
		try{
			Map<String, Object> params = cutPage.get_castQueryParam();
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				params.put("startDay", VstTools.parseInt(params.get("startDay")+""));
				params.put("endDay", VstTools.parseInt(params.get("endDay")+""));
			}
			
			int count = _vstMovieSearchDao.queryTopCount(params);
			
			if(count != 0){
				// 获取最大显示条数
				int rank_max_count = ToolsNumber.parseInt(_baseService.getOptionByKey("rank_max_count"), 100);
				if(count > rank_max_count){
					count = rank_max_count;
				}
				
				int start = (cutPage.get_currPage()-1) * cutPage.get_singleCount();
				
				params.put("offset", start);
				params.put("limit", cutPage.get_singleCount());
				
				List<Map<String, Object>> list = _vstMovieSearchDao.queryTopList(params);
				
				bean.setTotalSize(count); //设置总行数
				bean.setSingleSize(cutPage.get_singleCount());//设置单页显示行数
				bean.setMapData(list);
			}
		}catch(Exception e){
			logger.error("查询影片搜索排行分页数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}

	/**
	 * 导出数据
	 */
	@Override
	public ReportBean getExportData(Map<String, Object> params, UserSession user) throws SystemException {
		ReportBean bean = new ReportBean();
		try{
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				params.put("startDay", VstTools.parseInt(params.get("startDay")+""));
				params.put("endDay", VstTools.parseInt(params.get("endDay")+""));
			}
			// 获取最大显示条数
			int rank_max_count = ToolsNumber.parseInt(_baseService.getOptionByKey("rank_max_count"), 100);
			params.put("limit", rank_max_count);
			
			List<Map<String, Object>> data = _vstMovieSearchDao.queryTopExport(params);
			if(data != null && data.size() > 0){
				
				Map<String, Object> temp = new HashMap<String, Object>(4);
				// 获取包名
				temp.put("vst_pkg", params.get("pkgName"));
				temp.put("vst_table_name", "vst_sys");
				temp.put("vst_column_name", "pkgName");
				temp.put("vst_state", VstConstants.STATE_AVALIABLE);
				Map<String, String> pkgMap = _baseService.getDictionaryForMap(temp);
				// 获取专区类型
				temp.put("vst_table_name", "vst_movie");
				temp.put("vst_column_name", "specialType");
				Map<String, String> specialTypeMap = _baseService.getDictionaryForMap(temp);
				// 获取分类
				temp.put("vst_table_name", "vst_movie");
				temp.put("vst_column_name", "classify");
				Map<String, String> classifyMap = _baseService.getDictionaryForMap(temp);
				
				for(Map<String, Object> map : data){
					if(pkgMap.containsKey(map.get("包名")+"")){
						map.put("包名", pkgMap.get(map.get("包名")+""));
					}
					if(specialTypeMap.containsKey(map.get("专区类型")+"")){
						map.put("专区类型", specialTypeMap.get(map.get("专区类型")+""));
					}
					if(classifyMap.containsKey(map.get("影片分类")+"")){
						map.put("影片分类", classifyMap.get(map.get("影片分类")+""));
					}
				}
			}
			
			bean.setMapData(data);
			bean.setTitle("vst_movie_search_rank_data");
			
			// 写操作日志
			StringBuilder sb = new StringBuilder();
			sb.append("导出数据，查询条件：").append(params);
			_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_EXPORT, sb.toString()));
		}catch(Exception e){
			logger.error("导出影片搜索排行数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bean;
	}
}
