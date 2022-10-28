package com.vst.defend.dao3.bullet;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.vst.common.pojo.VstBulletData;
import com.vst.defend.communal.dao.BaseDao;

/**
 * 
 * @author lhp
 * @date 2018-6-12 下午04:08:03
 * @version
 */
@Repository
public interface VstBulletDataDao extends BaseDao<VstBulletData> {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExport(Map<String,Object> params);
}
