package com.vst.defend.dao2.movie;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author lhp
 * @date 2018-10-18 下午06:10:36
 * @version
 */
@Repository
public interface VstMovieBlockDao {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExport(Map<String,Object> params);
	
	/**
	 * 统计区块播放数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryListByCount(Map<String,Object> params);
}
