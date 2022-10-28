package com.vst.defend.dao.config;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.vst.common.pojo.VstFeaturesConfig;
import com.vst.defend.communal.dao.BaseDao;

/**
 * 
 * @author lhp
 * @date 2018-12-13 下午05:26:51
 * @version
 */
@Repository
@Transactional
public interface VstFeaturesConfigDao extends BaseDao<VstFeaturesConfig> {

}
