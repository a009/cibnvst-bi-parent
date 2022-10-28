package com.vst.defend.dao2.user;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author lhp
 * @date 2018-1-5 上午10:42:07
 * @version
 */
@Repository
public interface VstUserLevelChannelDao {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExport(Map<String,Object> params);
	
	/**
	 * 查询列表数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryForList(Map<String,Object> params);
}
