package com.vst.defend.service.task;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;

/**
 * 
 * @author lhp
 * @date 2018-4-13 上午10:48:41
 * @version
 */
@Service
public interface VstExportTaskService {
	/**
	 * 查询导出任务列表
	 * @param cutPage
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	CutPage getExportTaskList(CutPage cutPage, UserSession user) throws SystemException;
	
	/**
	 * 新增导出任务
	 * @param param
	 * @param user
	 * @throws SystemException
	 */
	void addExportTask(Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 根据ID，获取导出任务信息
	 * @param id
	 * @return
	 * @throws SystemException
	 */
	Map<String, Object> getExportTaskById(String id) throws SystemException;
	
	/**
	 * 修改导出任务
	 * @param oldParam
	 * @param param
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int updateExportTask(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException;
	
	/**
	 * 删除导出任务
	 * @param ids
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int deleteExportTask(String ids, UserSession user) throws SystemException;
	
	/**
	 * 修改导出任务状态
	 * @param ids
	 * @param state
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int modifyExportTaskState(String ids, int state, UserSession user) throws SystemException;
	
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
	 * 未读消息数量是否发生变化
	 * @param receiverId
	 * @return
	 */
	boolean isUnreadMessageNumberChanged(String receiverId);
	
	/**
	 * 写缓存(用于通知接收人未读消息发生变更)
	 * @param receiverId
	 */
	void writeCache(String receiverId);
}
