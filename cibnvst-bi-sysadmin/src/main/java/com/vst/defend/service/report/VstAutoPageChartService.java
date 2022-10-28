package com.vst.defend.service.report;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;

/**
 * 
 * @author lhp
 * @date 2017-9-12 下午04:03:57
 * @version
 */
@Service
public interface VstAutoPageChartService {
	/**
	 * 查询自动化页面(统计图)列表
	 * @param cutPage
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	CutPage getAutoPageChartList(CutPage cutPage, UserSession user) throws SystemException;
	
	/**
	 * 新增自动化页面(统计图)
	 * @param param
	 * @param user
	 * @throws SystemException
	 */
	void addAutoPageChart(Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 根据ID，获取自动化页面(统计图)信息
	 * @param id
	 * @return
	 * @throws SystemException
	 */
	Map<String, Object> getAutoPageChartById(String id) throws SystemException;
	
	/**
	 * 修改自动化页面(统计图)
	 * @param oldParam
	 * @param param
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int updateAutoPageChart(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 删除自动化页面(统计图)
	 * @param ids
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int deleteAutoPageChart(String ids, UserSession user) throws SystemException;
	
	/**
	 * 修改自动化页面(统计图)状态
	 * @param ids
	 * @param state
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int modifyAutoPageChartState(String ids, int state, UserSession user) throws SystemException;
	
	/**
	 * 修改自动化页面(统计图)排序
	 * @param ids
	 * @param indexs
	 * @param user
	 * @return
	 */
	void modifyAutoPageChartIndexs(String ids, String indexs, UserSession user) throws SystemException;
	
	/**
	 * 获取键值对
	 * @param params
	 * @param key
	 * @param value
	 * @return
	 * @throws SystemException
	 */
	Map<String, Object> queryForMap(Map<String, Object> params, String key, String value) throws SystemException;
}
