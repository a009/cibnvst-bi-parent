package com.vst.defend.dao.sys;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.vst.common.pojo.VstSysModule;
import com.vst.defend.communal.dao.BaseDao;

/**
 * 
 * @author lhp
 * @date 2017-4-6 下午02:47:46
 * @description
 * @version
 */
@Repository
@Transactional
public interface VstSysModuleDao extends BaseDao<VstSysModule> {
	
	/**
	 * 根据角色id获取相应的模块列表
	 * @param params
	 * @return 返回list集合
	 */
	List<VstSysModule> getModulesByRoleId(Map<String,Object> params);
}
