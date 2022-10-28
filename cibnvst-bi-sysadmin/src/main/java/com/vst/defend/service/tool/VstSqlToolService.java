package com.vst.defend.service.tool;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.vst.defend.communal.exception.SystemException;

/**
 * 
 * @author lhp
 * @date 2018-4-10 下午06:19:54
 * @version
 */
@Service
public interface VstSqlToolService {
	/**
	 * 根据sql语句查询
	 * @param sql
	 * @param currPage
	 * @param singleCount
	 * @return
	 * @throws SystemException
	 */
	JSONObject queryBySql(String sql, int currPage, int singleCount) throws SystemException;
}
