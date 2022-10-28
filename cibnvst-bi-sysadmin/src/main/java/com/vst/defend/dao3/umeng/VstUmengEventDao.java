package com.vst.defend.dao3.umeng;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.vst.common.pojo.VstUmengEvent;
import com.vst.defend.communal.dao.BaseDao;

/**
 * 
 * @author lhp
 * @date 2018-3-15 下午05:04:28
 * @version
 */
@Repository
@Transactional
public interface VstUmengEventDao extends BaseDao<VstUmengEvent> {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExport(Map<String,Object> params);
}
