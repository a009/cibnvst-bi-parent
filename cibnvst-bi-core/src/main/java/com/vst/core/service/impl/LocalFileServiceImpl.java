package com.vst.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.vst.common.tools.file.ToolsFile;
import com.vst.core.communal.sql.SqlFactory;
import com.vst.core.service.DBMgrService;

/**
 * @author joslyn
 * @date 2017年11月20日 下午8:16:40
 * @description
 * @version 1.0
 */
public class LocalFileServiceImpl extends DBMgrService{
	
	/**
	 * 单例类
	 */
	private static final LocalFileServiceImpl _cache = new LocalFileServiceImpl();
	
	/**
	 * 构造器
	 */
	private LocalFileServiceImpl() {
	}

	/**
	 * 获取单例对象
	 * @return
	 */
	public static LocalFileServiceImpl getInstance(){
		return _cache;
	}

	@Override
	public int save(SqlFactory sqlFactory, int date,
			List<Map<String, Object>> list) throws Exception {
		if(list != null && !list.isEmpty()){
			List<String> data = new ArrayList<String>();
			for(int i = 0, l = list.size(); i < l; i++){
				Map<String, Object> map = list.get(i);
				com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject(map);
				data.add(json.toString());
				if((i + 1) % 5000 == 0 || i == l - 1){
					ToolsFile.writeLinesToFile(sqlFactory.getSqlTableName(), data);
					data.clear();
				}
			}
			return list.size();
		}
		return 0;
	}

	@Override
	public boolean delete(SqlFactory sqlFactory, String date) {
		return true;
	}

	@Override
	public List<JSONObject> queryFromDB(SqlFactory sqlFactory, String date) {
		return null;
	}

}
