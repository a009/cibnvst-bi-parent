package com.vst.defend.dao2.home;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author lhp
 * @date 2018-2-28 上午11:25:21
 * @version
 */
@Repository
public interface VstHomeEntryClickDao {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExport(Map<String,Object> params);
}
