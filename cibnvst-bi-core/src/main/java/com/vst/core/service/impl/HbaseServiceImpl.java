package com.vst.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;

import com.vst.common.tools.string.ToolsString;
import com.vst.core.communal.sql.Save;
import com.vst.core.communal.sql.SqlFactory;
import com.vst.core.communal.util.PropertiesReader;
import com.vst.core.dao.HbaseDao;
import com.vst.core.dao.impl.HbaseDaoImpl;
import com.vst.core.service.DBMgrService;

/**
 * @author weiwei(joslyn)
 * @date 2017-11-1 下午05:52:34
 * @decription
 * @version
 */
public class HbaseServiceImpl extends DBMgrService {
	
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(HbaseServiceImpl.class);
	
	/**
	 * 单例类
	 */
	private static final HbaseServiceImpl _cache = new HbaseServiceImpl();
	
	/**
	 * hbase数据层管理类
	 */
	protected HbaseDao _hbaseDao;
	
	/**
	 * 构造器
	 */
	protected HbaseServiceImpl() {
		_hbaseDao = new HbaseDaoImpl();
	}

	/**
	 * 获取单例对象
	 * @return
	 */
	public static HbaseServiceImpl getInstance(){
		return _cache;
	}

	@Override
	public int save(SqlFactory sqlFactory, int date, List<Map<String, Object>> list) throws Exception {
		if(list != null && !list.isEmpty()){
			String[] rowKeyColumn = sqlFactory.getSqlRowKeyColumn();
			Save primary = sqlFactory.getSaveSqlInfo().getPrimary();
			String defaultFamily = PropertiesReader.getInstance().getProperty("hbase_default_family");
			if(rowKeyColumn != null && rowKeyColumn.length > 0 && primary != null){
				Map<String, Map<String, Map<String, Object>>> paramMap = new HashMap<String, Map<String, Map<String, Object>>>();
				for(Map<String, Object> map : list){
					// 拼接rowKey主键
					String rowKey = "";
					for(String rowName : rowKeyColumn){
						if(rowName.equals("uuid")){
							rowKey += ToolsString.checkEmpty(map.get(rowName)).replace("-", "") + "[@!@]";
						}else if(rowName.equals("{time}")){
							rowKey += System.currentTimeMillis() + "[@!@]";
						}else if(rowName.equals("{date}")){
							rowKey += date + "[@!@]";
						}else{
							rowKey += map.get(rowName) + "[@!@]";
						}
					}
					rowKey = rowKey.contains("[@!@]") ? rowKey.substring(0, rowKey.lastIndexOf("[@!@]")) : rowKey;
					Map<String, Map<String, Object>> columnMap = new HashMap<String, Map<String, Object>>(1);
					columnMap.put(defaultFamily, map);
					paramMap.put(rowKey, columnMap);
				}
				// 保存数据到hbase
				boolean flag = _hbaseDao.saveRows(sqlFactory.getSqlTableName(), paramMap);
				if(flag){
					return paramMap.size();
				}
				return -1;
			}else if(primary != null){
				Map<String, Map<String, Map<String, Object>>> paramMap = new HashMap<String, Map<String, Map<String, Object>>>();
				for(Map<String, Object> map : list){
					Map<String, Map<String, Object>> columnMap = new HashMap<String, Map<String,Object>>(1);
					columnMap.put(defaultFamily, map);
					paramMap.put(primary.getName(), columnMap);
				}
				// 保存数据到hbase
				boolean flag = _hbaseDao.saveRows(sqlFactory.getSqlTableName(), paramMap);
				if(flag){
					return paramMap.size();
				}
				return -1;
			}
		}
		return 0;
	}

	@Override
	public boolean delete(SqlFactory sqlFactory, String date) {
		boolean flag = false;
		if(!ToolsString.isEmpty(date)){
			int affectRows = _hbaseDao.deleteRowsByRowKeyLike(sqlFactory.getSqlTableName(), "[@!@]" + date);
			logger.info("===>>> delete from table[" + sqlFactory.getSqlTableName() + "][" + date + "]  affectRows(" + affectRows + ")");
			flag = true;
		}
		return flag;
	}

	@Override
	public List<JSONObject> queryFromDB(SqlFactory sqlFactory, String date) {
		return _hbaseDao.queryRowsByRowKeyLike(sqlFactory.getSqlTableName(), "[@!@]" + date);
	}
}
