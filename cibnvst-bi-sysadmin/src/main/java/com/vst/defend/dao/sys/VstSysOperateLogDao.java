package com.vst.defend.dao.sys;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.vst.common.pojo.VstSysOperateLog;
import com.vst.defend.communal.dao.BaseDao;

/**
 * 
 * @author lhp
 * @date 2017-4-7 下午12:14:42
 * @description
 * @version
 */
@Repository
@Transactional
public interface VstSysOperateLogDao extends BaseDao<VstSysOperateLog> {
	
}
