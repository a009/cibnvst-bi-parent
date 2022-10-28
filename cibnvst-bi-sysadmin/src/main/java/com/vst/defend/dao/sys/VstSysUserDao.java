package com.vst.defend.dao.sys;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.vst.common.pojo.VstSysUser;
import com.vst.defend.communal.dao.BaseDao;

/**
 * 
 * @author lhp
 * @date 2017-4-6 下午03:10:27
 * @description
 * @version
 */
@Repository
@Transactional
public interface VstSysUserDao extends BaseDao<VstSysUser> {
	
}
