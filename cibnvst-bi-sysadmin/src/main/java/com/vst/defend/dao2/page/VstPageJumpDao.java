package com.vst.defend.dao2.page;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author lhp
 * @date 2018-5-16 上午10:20:47
 * @version
 */
@Repository
public interface VstPageJumpDao {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExport(Map<String,Object> params);
	
	/**
	 * 按当前页面导出
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExportByCurrPage(Map<String,Object> params);
	
	/**
	 * 按跳转页面统计
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryReportByJumpPage(Map<String,Object> params);
}
