package com.vst.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.vst.core.communal.sql.Save;

/**
 * @author joslyn
 * @date 2017年11月22日 下午12:05:41
 * @description
 * @version 1.0
 */
public interface MySQLDao extends Serializable{

	/**
	 * 批量保存数据
	 * @param sqls
	 */
	int save(List<String> sqls);

	/**
	 * 批量保存数据
	 * @param sql
	 * @param columns
	 * @param dataList
	 * @return
	 */
	int save(String sql, List<Save> columns, List<Map<String, Object>> dataList) throws Exception;

	/**
	 * 删除记录
	 * @param sql
	 * @return
	 */
	int delete(String sql);

	/**
	 * 查询数据
	 * @param sql
	 * @return
	 */
	List<JSONObject> query(String sql);
}
