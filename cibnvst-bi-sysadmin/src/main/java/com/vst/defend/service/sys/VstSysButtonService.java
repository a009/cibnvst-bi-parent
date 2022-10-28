package com.vst.defend.service.sys;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;

/**
 * 
 * @author lhp
 * @date 2017-3-27 下午05:32:09
 * @description
 * @version
 */
@Service
public interface VstSysButtonService {
	/**
	 * 查询按钮列表
	 * @param cutPage
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	CutPage getSysButtonList(CutPage cutPage, UserSession user) throws SystemException;
	
	/**
	 * 校验按钮名称是否存在
	 * @param buttonName
	 * @return true：存在，false：不存在
	 * @throws SystemException
	 */
	boolean checkButtonNameExist(String buttonName) throws SystemException;
	
	/**
	 * 新增按钮
	 * @param param
	 * @param user
	 * @throws SystemException
	 */
	void addSysButton(Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 根据ID，获取按钮信息
	 * @param id
	 * @return
	 * @throws SystemException
	 */
	Map<String, Object> getSysButtonById(String id) throws SystemException;
	
	/**
	 * 修改按钮
	 * @param oldParam
	 * @param param
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int updateSysButton(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 删除按钮
	 * @param ids
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int deleteSysButton(String ids, UserSession user) throws SystemException;
	
	/**
	 * 修改按钮状态
	 * @param ids
	 * @param state
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int modifyButtonState(String ids, int state, UserSession user) throws SystemException;
	
	/**
	 * 修改按钮排序
	 * @param ids
	 * @param indexs
	 * @param user
	 * @return
	 */
	void modifyButtonIndexs(String ids, String indexs, UserSession user) throws SystemException;
	
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
