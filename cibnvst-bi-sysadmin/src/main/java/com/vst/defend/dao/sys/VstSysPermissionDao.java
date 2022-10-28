package com.vst.defend.dao.sys;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.vst.common.pojo.VstSysPermission;
import com.vst.defend.communal.dao.BaseDao;

/**
 * 
 * @author lhp
 * @date 2017-4-13 下午05:53:23
 * @description
 * @version
 */
@Repository
@Transactional
public interface VstSysPermissionDao extends BaseDao<VstSysPermission> {
	
	/**
	 * 查询权限
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> queryPermissions(Map<String,Object> params);
}
