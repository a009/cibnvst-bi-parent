package com.vst.defend.dao.sys;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.vst.common.pojo.VstSysRole;
import com.vst.defend.communal.dao.BaseDao;

/**
 * 
 * @author lhp
 * @date 2017-4-7 下午02:24:53
 * @description
 * @version
 */
@Repository
@Transactional
public interface VstSysRoleDao extends BaseDao<VstSysRole> {
	
}
