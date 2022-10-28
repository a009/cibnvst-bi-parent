package com.vst.defend.dao.config;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.vst.common.pojo.VstSql;
import com.vst.defend.communal.dao.BaseDao;

/**
 * 
 * @author lhp
 * @date 2017-11-20 下午12:18:26
 * @version
 */
@Repository
@Transactional
public interface VstSqlDao extends BaseDao<VstSql> {

}
