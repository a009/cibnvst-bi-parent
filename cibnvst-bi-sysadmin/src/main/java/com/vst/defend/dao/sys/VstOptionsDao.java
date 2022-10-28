package com.vst.defend.dao.sys;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.vst.common.pojo.VstOptions;
import com.vst.defend.communal.dao.BaseDao;

/**
 * 
 * @author lhp
 * @date 2017-5-9 上午10:58:24
 * @description
 * @version
 */
@Repository
@Transactional
public interface VstOptionsDao extends BaseDao<VstOptions> {

}
