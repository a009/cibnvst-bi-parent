package com.vst.defend.service.config;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;

/**
 * 
 * @author lhp
 * @date 2018-4-16 下午03:26:44
 * @version
 */
@Service
public interface VstTableDescService {
	/**
	 * 查询表算法描述列表
	 * @param cutPage
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	CutPage getTableDescList(CutPage cutPage, UserSession user) throws SystemException;
	
	/**
	 * 新增表算法描述
	 * @param param
	 * @param user
	 * @throws SystemException
	 */
	void addTableDesc(Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 根据ID，获取表算法描述信息
	 * @param id
	 * @return
	 * @throws SystemException
	 */
	Map<String, Object> getTableDescById(String id) throws SystemException;
	
	/**
	 * 修改表算法描述
	 * @param oldParam
	 * @param param
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int updateTableDesc(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 删除表算法描述
	 * @param ids
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int deleteTableDesc(String ids, UserSession user) throws SystemException;
	
	/**
	 * 修改表算法描述状态
	 * @param ids
	 * @param state
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int modifyTableDescState(String ids, int state, UserSession user) throws SystemException;
	
	/**
	 * 修改表算法描述排序
	 * @param ids
	 * @param indexs
	 * @param user
	 * @return
	 */
	void modifyTableDescIndexs(String ids, String indexs, UserSession user) throws SystemException;
	
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
	 * 批量添加表算法描述
	 * @param params
	 * @param user
	 * @throws SystemException
	 */
	int batchAddTableDesc(Map<String, Object> params, UserSession user) throws SystemException;
	
	/**
	 * 复制新增表算法描述
	 * @param param
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int copyAddTableDesc(Map<String, Object> param, UserSession user) throws SystemException;
}
