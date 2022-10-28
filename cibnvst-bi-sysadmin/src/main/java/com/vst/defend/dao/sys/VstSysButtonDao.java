package com.vst.defend.dao.sys;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.vst.common.pojo.VstSysButton;
import com.vst.defend.communal.dao.BaseDao;
import com.vst.defend.communal.exception.SystemException;

/**
 * 
 * @author lhp
 * @date 2017-3-27 下午05:04:08
 * @description
 * @version
 */
@Repository
@Transactional
public interface VstSysButtonDao extends BaseDao<VstSysButton> {
	/**
	 * 根据角色id获取相应的按钮列表
	 * @param roleIds
	 * @return
	 * @throws SystemException
	 */
	List<Map<String, Object>> getButtonsByRoleId(List<String> roleIds) throws SystemException;
}
