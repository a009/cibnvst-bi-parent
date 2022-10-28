package com.vst.defend.service.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.dao2.movie.VstMovieBlockDao;
import com.vst.defend.dao2.movie.VstMovieClickDao;
import com.vst.defend.dao2.movie.VstMoviePlayDao;
import com.vst.defend.dao2.movie.VstMovieSearchDao;
import com.vst.defend.service.api.MovieApiService;

/**
 * 
 * @author lhp
 * @date 2017-12-11 下午04:06:00
 * @version
 */
@Service
public class MovieApiServiceImpl implements MovieApiService {
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(MovieApiServiceImpl.class);
	
	/**
	 * 影片播放Dao接口
	 */
	@Resource
	private VstMoviePlayDao _vstMoviePlayDao;
	
	/**
	 * 影片点击Dao接口
	 */
	@Resource
	private VstMovieClickDao _vstMovieClickDao;
	
	/**
	 * 影片搜索Dao接口
	 */
	@Resource
	private VstMovieSearchDao _vstMovieSearchDao;
	
	/**
	 * 区块播放Dao接口
	 */
	@Resource
	private VstMovieBlockDao _vstMovieBlockDao;
	
	/**
	 * 查询影片播放数据
	 */
	@Override
	public ReportBean getMoviePlayData(Map<String, Object> params) throws SystemException {
		ReportBean result = new ReportBean();
		try{
			if(params == null){
				params = new HashMap<String, Object>();
			}
			// 查询条件
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("pkgName", "net.myvst.v2");
			condition.put("date", params.get("date"));
			if(!ToolsString.isEmpty(params.get("cid"))){
				condition.put("vst_mp_cid", params.get("cid"));
			}
			
			List<JSONObject> dataList = new ArrayList<JSONObject>();
			
			int count = _vstMoviePlayDao.queryCount(condition);
			result.setTotalSize(count);
			if(count != 0){
				int _currPage = ToolsNumber.parseInt(params.get("_currPage")+"");
				int _singleCount = ToolsNumber.parseInt(params.get("_singleCount")+"");
				//分页
				int start = (_currPage - 1) * _singleCount;
				condition.put("offset", start);
				condition.put("limit", _singleCount);
				
				List<Map<String, Object>> data = _vstMoviePlayDao.queryList(condition);
				for(Map<String, Object> map : data){
					JSONObject json = new JSONObject();
					json.put("cid", map.get("vst_mp_cid"));
					json.put("uuid", map.get("vst_mp_uuid"));
					json.put("filmName", map.get("vst_mp_name"));
					json.put("totalPlayCount", map.get("vst_mp_vv"));
					json.put("totalUserCount", map.get("vst_mp_uv"));
					
					dataList.add(json);
				}
			}
			result.setData(dataList);
		}catch(Exception e){
			logger.error("查询影片播放数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 查询影片搜索数据
	 */
	@Override
	public ReportBean getMovieSearchData(Map<String, Object> params) throws SystemException {
		ReportBean result = new ReportBean();
		try{
			if(params == null){
				params = new HashMap<String, Object>();
			}
			// 查询条件
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("pkgName", "net.myvst.v2");
			condition.put("date", params.get("date"));
			if(!ToolsString.isEmpty(params.get("cid"))){
				condition.put("vst_mos_cid", params.get("cid"));
			}
			
			List<JSONObject> dataList = new ArrayList<JSONObject>();
			
			int count = _vstMovieSearchDao.queryCount(condition);
			result.setTotalSize(count);
			if(count != 0){
				int _currPage = ToolsNumber.parseInt(params.get("_currPage")+"");
				int _singleCount = ToolsNumber.parseInt(params.get("_singleCount")+"");
				//分页
				int start = (_currPage - 1) * _singleCount;
				condition.put("offset", start);
				condition.put("limit", _singleCount);
				
				List<Map<String, Object>> data = _vstMovieSearchDao.queryList(condition);
				for(Map<String, Object> map : data){
					JSONObject json = new JSONObject();
					json.put("cid", map.get("vst_mos_cid"));
					json.put("uuid", map.get("vst_mos_uuid"));
					json.put("filmName", map.get("vst_mos_name"));
					json.put("totalPlayCount", map.get("vst_mos_amount"));
					json.put("totalUserCount", map.get("vst_mos_uv"));
					
					dataList.add(json);
				}
			}
			result.setData(dataList);
		}catch(Exception e){
			logger.error("查询影片搜索数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}

	/**
	 * 查询影片播放汇总排行
	 */
	@Override
	public ReportBean findMoviePlayTop(Map<String, Object> params) throws SystemException {
		ReportBean result = new ReportBean();
		try{
			if(params == null){
				params = new HashMap<String, Object>();
			}
			
			int count = _vstMoviePlayDao.queryTopCount(params);
			result.setTotalSize(count);
			if(count != 0){
				int _currPage = ToolsNumber.parseInt(params.get("_currPage")+"");
				int _singleCount = ToolsNumber.parseInt(params.get("_singleCount")+"");
				//分页
				int start = (_currPage - 1) * _singleCount;
				params.put("offset", start);
				params.put("limit", _singleCount);
				
				List<Map<String, Object>> data = _vstMoviePlayDao.queryTopList(params);
				result.setMapData(data);
			}
		}catch(Exception e){
			logger.error("查询影片播放汇总排行失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}

	/**
	 * 查询影片点击汇总排行
	 */
	@Override
	public ReportBean findMovieClickTop(Map<String, Object> params) throws SystemException {
		ReportBean result = new ReportBean();
		try{
			if(params == null){
				params = new HashMap<String, Object>();
			}
			
			int count = _vstMovieClickDao.queryTopCount(params);
			result.setTotalSize(count);
			if(count != 0){
				int _currPage = ToolsNumber.parseInt(params.get("_currPage")+"");
				int _singleCount = ToolsNumber.parseInt(params.get("_singleCount")+"");
				//分页
				int start = (_currPage - 1) * _singleCount;
				params.put("offset", start);
				params.put("limit", _singleCount);
				
				List<Map<String, Object>> data = _vstMovieClickDao.queryTopList(params);
				result.setMapData(data);
			}
		}catch(Exception e){
			logger.error("查询影片点击汇总排行失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}

	/**
	 * 查询区块播放数据
	 */
	@Override
	public JSONObject getMovieBlockData(Map<String, Object> params) throws SystemException {
		JSONObject result = new JSONObject();
		try{
			if(params == null){
				params = new HashMap<String, Object>();
			}
			
			List<Map<String, Object>> data = _vstMovieBlockDao.queryListByCount(params);
			if(data != null && data.size() > 0){
				// 格式：区块ID->(位置->数据)
				Map<String, Map<String, Map<String, Object>>> blockData = new HashMap<String, Map<String, Map<String,Object>>>();
				
				for(Map<String, Object> map : data){
					String block = ToolsString.checkEmpty(map.get("vst_mb_block"));
					String index = ToolsString.checkEmpty(map.get("vst_mb_index"));
					
					Map<String, Object> temp = new HashMap<String, Object>();
					temp.put("vv", map.get("vst_mb_vv"));
					temp.put("vvDod", map.get("vst_mb_vv_dod"));
					temp.put("vvWow", map.get("vst_mb_vv_wow"));
					temp.put("uv", map.get("vst_mb_uv"));
					temp.put("uvDod", map.get("vst_mb_uv_dod"));
					temp.put("uvWow", map.get("vst_mb_uv_wow"));
					temp.put("avg", map.get("vst_mb_avg"));
					temp.put("playtime", map.get("vst_mb_playtime"));
					temp.put("avgPlaytime", map.get("vst_mb_playtime_avg"));
					
					if(blockData.containsKey(block)){
						Map<String, Map<String, Object>> indexData = blockData.get(block);
						if(!indexData.containsKey(index)){
							indexData.put(index, temp);
						}
					}else{
						Map<String, Map<String, Object>> indexData = new HashMap<String, Map<String,Object>>();
						indexData.put(index, temp);
						blockData.put(block, indexData);
					}
				}
				// 封装数据
				for(String key : blockData.keySet()){
					result.put(key, blockData.get(key));
				}
			}
		}catch(Exception e){
			logger.error("查询区块播放数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
}
