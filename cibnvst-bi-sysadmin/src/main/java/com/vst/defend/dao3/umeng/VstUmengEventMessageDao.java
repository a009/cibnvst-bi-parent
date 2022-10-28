package com.vst.defend.dao3.umeng;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.vst.common.pojo.VstUmengEventMessage;
import com.vst.defend.communal.dao.BaseDao;

/**
 * 
 * @author lhp
 * @date 2018-3-15 上午11:50:56
 * @version
 */
@Repository
@Transactional
public interface VstUmengEventMessageDao extends BaseDao<VstUmengEventMessage> {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExport(Map<String,Object> params);
	
	/**
	 * 查询汇总数据
	 * @param params
	 * @return 返回list集合
	 */
	List<Map<String,Object>> queryListSum(Map<String,Object> params);
	
	/**
	 * 查询汇总记录数
	 * @param params
	 * @return 返回int
	 */
	int queryCountSum(Map<String,Object> params);
	
	/**
	 * 导出汇总数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExportSum(Map<String,Object> params);
}
