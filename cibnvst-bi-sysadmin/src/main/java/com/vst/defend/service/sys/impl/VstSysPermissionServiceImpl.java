package com.vst.defend.service.sys.impl;

import com.vst.common.pojo.VstSysOperateLog;
import com.vst.common.pojo.VstSysPermission;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.BeanUtils;
import com.vst.defend.communal.util.CutPage;
import com.vst.defend.communal.util.LogUtils;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.dao.sys.VstSysPermissionDao;
import com.vst.defend.service.sys.VstSysPermissionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author lhp
 * @date 2017-4-13 下午06:29:53
 * @description
 * @version
 */
@Service
@SuppressWarnings("unchecked")
public class VstSysPermissionServiceImpl implements VstSysPermissionService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstSysPermissionServiceImpl.class);
	
	/**
	 * 权限Dao接口
	 */
	@Resource
	private VstSysPermissionDao _vstSysPermissionDao;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询所有的权限列表
	 */
	@Override
	public CutPage getSysPermissionList(CutPage cutPage, UserSession user)
			throws SystemException {
		// 返回结果
		CutPage result = new CutPage();
		try {
			Map<String, Object> params = cutPage.get_castQueryParam();
			if(params == null){
				params = new HashMap<String, Object>();
			}
			
			int count = _vstSysPermissionDao.queryCount(params);
			
			if(count != 0){
				// 设置单页显示数量
				result.set_singleCount(cutPage.get_singleCount());
				// 设置总数
				result.set_totalResults(count);
				// 设置当前页
				result.set_currPage(cutPage.get_currPage());
				
				int start = (cutPage.get_currPage()-1) * cutPage.get_singleCount();
				
				params.put("offset", start);
				params.put("limit", cutPage.get_singleCount());
				
				List list = _vstSysPermissionDao.queryForList(params);
				result.set_dataList(list);
			}
		} catch (Exception e) {
			logger.error("查询权限列表失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		// 参数带上
		result.set_queryParam(cutPage.get_queryParam());
		return result;
	}

	/**
	 * 校验该组合是否存在
	 */
	@Override
	public boolean checkNameExist(String name) throws SystemException {
		try {
			if(ToolsString.isEmpty(name) || name.split("_").length < 3){
				return true;
			}
			
			Map<String, Object> param = new HashMap<String, Object>(3);
			String[] values = name.split("_");
			param.put("vst_role_id", values[0]);
			param.put("vst_module_id", values[1]);
			param.put("vst_button_id", values[2]);
			int count = _vstSysPermissionDao.queryCount(param);
			
			return count > 0;
		} catch (Exception e) {
			logger.error("ajax验证权限组合是否存在失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
	}

	/**
	 * 添加权限映射
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public void addSysPermission(Map<String, Object> params, UserSession user) throws SystemException {
		try {
			String bIDs = params.get("vst_button_id")+"";//多个按钮
			String[] bID = bIDs.split(",");
			
			for (int i = 0; i < bID.length; i++) {
				if(ToolsString.isEmpty(bID[i])){
					params.put("vst_button_id", "0");
				}else{
					params.put("vst_button_id", bID[i]);
				}
				// 判断权限是否存在
				if(_vstSysPermissionDao.queryCount(params) == 0){
					VstSysPermission bean = new VstSysPermission();
					bean = (VstSysPermission) BeanUtils.mapToBean(params, bean.getClass());
					// 新增
					_vstSysPermissionDao.insert(bean);
					
					// 写操作日志
					_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_ADD, 
							LogUtils.logFormat(VstConstants.OPERATE_TYPE_ADD, null, bean)));
				}
			}
		} catch (Exception e) {
			logger.error("新增权限映射失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
	}

	/**
	 * 批量删除权限映射
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int batchDelSysPermissions(String groupIDs, UserSession user)
			throws SystemException {
		int result = 0;
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			
			String[] all = groupIDs.split(",");
			for(String value : all){
				String[] values = value.split("_");
				param.put("vst_role_id", values[0]);
				param.put("vst_module_id", values[1]);
				if(values.length >= 3){
					param.put("vst_button_id", values[2]);
				}else{
					param.put("vst_button_id", "");
				}
				
				_vstSysPermissionDao.delete(param);
				result++;
			}
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("批量删除权限映射，组合id=").append(groupIDs)
				  .append("，操作人=").append(user.getLoginInfo().getLoginName());
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_DEL, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("批量删除权限映射失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
}
