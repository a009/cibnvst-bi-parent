package com.vst.defend.service.sys;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;

/**
 * 
 * @author lhp
 * @date 2017-4-7 下午12:12:21
 * @description
 * @version
 */
@Service
public interface VstSysOperateLogService {
	/**
	 * 获取操作日志列表
	 * @param cutPage
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	CutPage getSysOperLogList(CutPage cutPage, UserSession user) throws SystemException;
	
	/**
	 * 根据时间参数删除操作日志
	 * @param param
	 * @param user
	 * @return
	 */
	int deleteSysOperLogs(Map<String, Object> param, UserSession user);
}
