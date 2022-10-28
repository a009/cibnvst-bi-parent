package com.vst.defend.dao2.channel;

import java.util.List;
import java.util.Map;

import com.vst.common.pojo.VstChannelLevel;
import com.vst.defend.communal.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author lhp
 * @date 2018-3-9 下午03:55:02
 * @version
 */
@Repository
public interface VstChannelLevelDao extends BaseDao<VstChannelLevel> {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryExport(Map<String,Object> params);
}
