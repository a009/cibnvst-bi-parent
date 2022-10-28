package com.vst.defend.service.tool.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.dao2.sql.VstSQLDao;
import com.vst.defend.service.tool.VstSqlToolService;

/**
 * 
 * @author lhp
 * @date 2018-4-10 下午06:21:02
 * @version
 */
@Service
public class VstSqlToolServiceImpl implements VstSqlToolService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstSqlToolServiceImpl.class);
	
	/**
	 * SQL执行Dao接口
	 */
	@Resource
	private VstSQLDao _vstSQLDao;
	
	/**
	 * 根据sql语句查询
	 */
	@Override
	public JSONObject queryBySql(String sql, int currPage, int singleCount) throws SystemException {
		JSONObject result = new JSONObject();
		try {
			// 查询条件
			Map<String, Object> param = new HashMap<String, Object>(3);
			param.put("sql", sql);
			int start = (currPage-1)*singleCount;
			param.put("offset", start);
			param.put("limit", singleCount);
			
			// 查询时间
			long startTime = System.currentTimeMillis();
			List<Map<String, Object>> list = _vstSQLDao.querySqlList(param);
			long endTime = System.currentTimeMillis();
			
			if(list != null && list.size() > 0){
				result.put("data", list);
				result.put("time", endTime-startTime);
			}
		} catch (Exception e) {
			logger.error("根据sql语句查询失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
}
