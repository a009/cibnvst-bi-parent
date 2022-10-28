package com.vst.defend.dao3.shopping;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.vst.common.pojo.VstShoppingLiveOrder;
import com.vst.defend.communal.dao.BaseDao;

/**
 * 
 * @author lhp
 * @date 2017-11-28 下午04:48:49
 * @version
 */
@Repository
@Transactional
public interface VstShoppingLiveOrderDao extends BaseDao<VstShoppingLiveOrder> {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExport(Map<String,Object> params);

	/**
	 * 根据日期统计
	 */
	List<Map<String,Object>> queryReportByDate(Map<String,Object> params);
	
	/**
	 * 查询分页数据(根据商品ID、名称)
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryListByGood(Map<String,Object> params);
	
	/**
	 * 查询记录数(根据商品ID、名称)
	 * @param params
	 * @return
	 */
	int queryCountByGood(Map<String,Object> params);
	
	/**
	 * 导出数据(根据商品ID、名称)
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExportByGood(Map<String,Object> params);
	
	/**
	 * 根据商品分类统计
	 */
	List<Map<String,Object>> queryReportByCategory(Map<String,Object> params);
	
	/**
	 * 根据商品来源统计
	 */
	List<Map<String,Object>> queryReportBySource(Map<String,Object> params);
	
	/**
	 * 查询分页数据(根据省份)
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryListByProvince(Map<String,Object> params);

	/**
	 * 查询记录数(根据省份)
	 * @param params
	 * @return
	 */
	int queryCountByProvince(Map<String,Object> params);
	
	/**
	 * 导出数据(根据省份)
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExportByProvince(Map<String,Object> params);
	
	/**
	 * 查询分页数据(根据地址)
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryListByAddress(Map<String,Object> params);
	
	/**
	 * 查询记录数(根据地址)
	 * @param params
	 * @return
	 */
	int queryCountByAddress(Map<String,Object> params);
	
	/**
	 * 导出数据(根据地址)
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExportByAddress(Map<String,Object> params);
}
