package com.vst.defend.dao2.movie;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author lhp
 * @date 2018-2-24 下午06:04:41
 * @version
 */
@Repository
public interface VstMovieTotalPlayChannelDao {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExport(Map<String,Object> params);
}
