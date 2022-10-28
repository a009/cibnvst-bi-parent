package com.vst.defend.service.tool;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.exception.SystemException;

/**
 * 
 * @author lhp
 * @date 2019-1-8 下午03:27:49
 * @version
 */
@Service
public interface VstJarToolService {
	/**
	 * 执行SQL
	 * @param sparkExecParam
	 * @return
	 * @throws SystemException
	 */
	List<String> executeSql(String sparkExecParam) throws SystemException;
	
//	/**
//	 * 执行SQL
//	 * @param saveType
//	 * @param sql
//	 * @return
//	 */
//	JSONObject executeSql(String saveType, String sql) throws SystemException;
}
