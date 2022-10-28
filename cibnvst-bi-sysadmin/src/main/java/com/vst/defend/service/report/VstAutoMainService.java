package com.vst.defend.service.report;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
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
public interface VstAutoMainService {
	/**
	 * 查询自动化(主表)列表
	 * @param cutPage
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	CutPage getAutoMainList(CutPage cutPage, UserSession user) throws SystemException;
	
	/**
	 * 新增自动化(主表)
	 * @param param
	 * @param user
	 * @throws SystemException
	 */
	void addAutoMain(Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 根据ID，获取自动化(主表)信息
	 * @param id
	 * @return
	 * @throws SystemException
	 */
	Map<String, Object> getAutoMainById(String id) throws SystemException;
	
	/**
	 * 修改自动化(主表)
	 * @param oldParam
	 * @param param
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int updateAutoMain(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 删除自动化(主表)
	 * @param ids
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int deleteAutoMain(String ids, UserSession user) throws SystemException;
	
	/**
	 * 修改自动化(主表)状态
	 * @param ids
	 * @param state
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int modifyAutoMainState(String ids, int state, UserSession user) throws SystemException;
	
	/**
	 * 修改自动化(主表)排序
	 * @param ids
	 * @param indexs
	 * @param user
	 * @return
	 */
	void modifyAutoMainIndexs(String ids, String indexs, UserSession user) throws SystemException;
	
	/**
	 * 获取键值对
	 * @param params
	 * @param key
	 * @param value
	 * @return
	 * @throws SystemException
	 */
	Map<String, Object> queryForMap(Map<String, Object> params, String key, String value) throws SystemException;
	
	/**
	 * 查看树形结构
	 * @param vst_code 代码编号
	 * @return
	 */
	JSONObject getTreeByCode(String vst_code) throws SystemException;
	
	/**
	 * 根据代码编号彻底删除
	 * @param vst_code
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int realDeleteByCode(String vst_code, UserSession user) throws SystemException;
	
	/**
	 * 刷新自动化缓存
	 * @param user
	 * @return
	 */
	boolean flushAutoCache(UserSession user) throws SystemException;
}
