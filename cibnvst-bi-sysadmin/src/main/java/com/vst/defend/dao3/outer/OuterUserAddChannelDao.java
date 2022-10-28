package com.vst.defend.dao3.outer;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.vst.common.pojo.OuterVstUserAddChannel;
import com.vst.defend.communal.dao.BaseDao;

/**
 * 
 * @author lhp
 * @date 2018-1-18 下午02:12:02
 * @version
 */
@Repository
public interface OuterUserAddChannelDao extends BaseDao<OuterVstUserAddChannel> {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExport(Map<String,Object> params);
	
	/**
	 * 根据日期统计
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryReportByDate(Map<String,Object> params);
	
	/**
	 * 根据维度统计
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryReportByDim(Map<String,Object> params);
}
