package com.vst.defend.dao.config;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.vst.common.pojo.VstSysTopicProp;
import com.vst.defend.communal.dao.BaseDao;

/**
 * 
 * @author lhp
 * @date 2017-11-17 下午06:07:47
 * @version
 */
@Repository
@Transactional
public interface VstSysTopicPropDao extends BaseDao<VstSysTopicProp> {

}
