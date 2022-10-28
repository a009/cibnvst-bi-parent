package com.vst.defend.dao.config;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.vst.common.pojo.VstTableDesc;
import com.vst.defend.communal.dao.BaseDao;

/**
 * 
 * @author lhp
 * @date 2018-4-16 下午03:18:20
 * @version
 */
@Repository
@Transactional
public interface VstTableDescDao extends BaseDao<VstTableDesc> {

}
