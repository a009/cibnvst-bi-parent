package com.vst.defend.service.api;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.exception.SystemException;

/**
 * 
 * @author lhp
 * @date 2017-12-11 下午04:05:21
 * @version
 */
@Service
public interface MovieApiService {

	/**
	 * 查询影片播放数据
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	ReportBean getMoviePlayData(Map<String, Object> params) throws SystemException;
	
	/**
	 * 查询影片搜索数据
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	ReportBean getMovieSearchData(Map<String, Object> params) throws SystemException;
	
	/**
	 * 查询影片播放汇总排行
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	ReportBean findMoviePlayTop(Map<String, Object> params) throws SystemException;
	
	/**
	 * 查询影片点击汇总排行
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	ReportBean findMovieClickTop(Map<String, Object> params) throws SystemException;
	
	/**
	 * 查询区块播放数据
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	JSONObject getMovieBlockData(Map<String, Object> params) throws SystemException;
}
