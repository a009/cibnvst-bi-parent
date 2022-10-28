package com.vst.defend.dao.report;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.vst.common.pojo.VstAutoMain;
import com.vst.defend.communal.dao.BaseDao;

/**
 * 
 * @author lhp
 * @date 2017-9-12 下午03:17:08
 * @version
 */
@Repository
@Transactional
public interface VstAutoMainDao extends BaseDao<VstAutoMain> {

}
