package com.vst.defend.dao.config;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.vst.common.pojo.VstSqlFilter;
import com.vst.defend.communal.dao.BaseDao;

/**
 * 
 * @author lhp
 * @date 2017-11-30 下午05:21:34
 * @version
 */
@Repository
@Transactional
public interface VstSqlFilterDao extends BaseDao<VstSqlFilter> {

}
