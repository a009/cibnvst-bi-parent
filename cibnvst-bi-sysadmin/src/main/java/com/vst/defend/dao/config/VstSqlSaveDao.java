package com.vst.defend.dao.config;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.vst.common.pojo.VstSqlSave;
import com.vst.defend.communal.dao.BaseDao;

/**
 * 
 * @author lhp
 * @date 2017-12-4 下午05:05:50
 * @version
 */
@Repository
@Transactional
public interface VstSqlSaveDao extends BaseDao<VstSqlSave> {

}
