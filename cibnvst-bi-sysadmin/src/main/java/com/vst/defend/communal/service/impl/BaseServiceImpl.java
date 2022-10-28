package com.vst.defend.communal.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.vst.common.pojo.VstSysButton;
import com.vst.common.pojo.VstSysLoginLog;
import com.vst.common.pojo.VstSysModule;
import com.vst.common.pojo.VstSysOperateLog;
import com.vst.common.tools.number.ToolsRandom;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.bean.SysModules;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.BeanUtils;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.dao.config.VstTableDescDao;
import com.vst.defend.dao.sys.VstDictionaryDao;
import com.vst.defend.dao.sys.VstOptionsDao;
import com.vst.defend.dao.sys.VstSysButtonDao;
import com.vst.defend.dao.sys.VstSysLoginLogDao;
import com.vst.defend.dao.sys.VstSysModuleDao;
import com.vst.defend.dao.sys.VstSysOperateLogDao;
import com.vst.defend.dao.sys.VstSysRoleDao;
import com.vst.defend.service.sys.VstSysRoleService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author weiwei
 * @date 2014-11-24 上午11:19:51
 * @description
 * @version
 */
@Service
@SuppressWarnings("unused")
public class BaseServiceImpl implements BaseService {

	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(BaseServiceImpl.class);
	
	/**
	 * 模块管理接口
	 */
	@Resource
	private VstSysModuleDao _vstSysModuleDao;
	
	/**
	 * 按钮管理接口
	 */
	@Resource
	private VstSysButtonDao _vstSysButtonDao;
	
	/**
	 * 系统角色Dao接口
	 */
	@Resource
	private VstSysRoleDao _vstSysRoleDao;
	
	/**
	 * 系统角色Service接口
	 */
	@Resource
	private VstSysRoleService _vstSysRoleService;

	/**
	 * 操作日志接口
	 */
	@Resource
	private VstSysOperateLogDao _vstSysOperateLogDao;
	
	/**
	 * 登录日志接口
	 */
	@Resource
	private VstSysLoginLogDao _vstSysLoginLogDao;
	
	/**
	 * 字典Dao接口
	 */
	@Resource
	private VstDictionaryDao _vstDictionaryDao;
	
	/**
	 * 控制面板Dao接口
	 */
	@Resource
	private VstOptionsDao _vstOptionsDao;
	
	/**
	 * 表描述Dao接口
	 */
	@Resource
	private VstTableDescDao _vstTableDescDao;
	
	/**
	 * 根据角色id获取用户模块
	 * @param roleIds
	 * @return
	 * @throws SystemException
	 */
	@Override
	public Map<VstSysModule, List<VstSysModule>> getSysModules(	List<String> roleIds) throws SystemException {
		Map<VstSysModule, List<VstSysModule>> result = new LinkedHashMap<VstSysModule, List<VstSysModule>>();
		
		try {
			Map<String, Object> param = new HashMap<String, Object>(1);
			param.put("roleIds", roleIds);
			
			List<VstSysModule> list = _vstSysModuleDao.getModulesByRoleId(param);
			
			// 一级菜单
			Map<String, VstSysModule> firstMap = new HashMap<String, VstSysModule>();
			// 二级菜单
			List<VstSysModule> secondList = new ArrayList<VstSysModule>();
			for(VstSysModule bean : list){
				if(ToolsString.isEmpty(bean.getVst_module_parent()) || "0".equals(bean.getVst_module_parent().trim())){
					firstMap.put(bean.getVst_module_id(), bean);
				}else{
					secondList.add(bean);
				}
			}
			
			// 遍历二级菜单
			List<VstSysModule> tempList = null;
			for(VstSysModule bean : secondList){
				tempList = result.get(firstMap.get(bean.getVst_module_parent()));
				
				if(tempList == null){
					tempList = new ArrayList<VstSysModule>();
					result.put(firstMap.get(bean.getVst_module_parent()), tempList);
				}
				
				tempList.add(bean);
			}
			
		} catch (Exception e) {
			logger.error("获取用户模块列表失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		
		return result;
	}
	
	/**
	 * 根据角色id获取用户所拥有的按钮
	 * @param roleIds
	 * @return
	 * @throws SystemException
	 */
	@Override
	public Map<String, List<VstSysButton>> getSysButtons(List<String> roleIds) throws SystemException {
		// 返回结果
		Map<String, List<VstSysButton>> result = new HashMap<String, List<VstSysButton>>();
		try {
			List<Map<String, Object>> list = _vstSysButtonDao.getButtonsByRoleId(roleIds);
			
			List<VstSysButton> buttons = null;
			VstSysButton bean = null;
			for(Map<String, Object> map : list){
				String moduleId = map.get("vst_module_id").toString();
				
				// 根据模块id，获取该页面的按钮集合
				buttons = result.get(moduleId);
				if(buttons == null){
					buttons = new ArrayList<VstSysButton>();
					result.put(moduleId, buttons);
				}
				
				// 移除vst_module_id属性
				map.remove("vst_module_id");
				bean = new VstSysButton();
				bean = (VstSysButton) BeanUtils.mapToBean(map, bean.getClass());
				buttons.add(bean);
			}	
		} catch (Exception e) {
			logger.error("获取用户按钮列表失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}

	/**
	 * 获取当前用户所对应的所有角色
	 * @param roleIds
	 * @return
	 * @throws SystemException
	 */
	@Override
	public Map<String, String> getAllSysRolesName(List<String> roleIds)
			throws SystemException {
		// 返回结果
		Map<String, String> result = null;
		
		try {
			Map<String, Object> param = new HashMap<String, Object>(2);
			//当我禁用角色后，在修改禁用的角色会报错，找不到！因为我查询的都是正常状态的，所以为了解决问题，我改为查询全部角色
			//param.put("vst_role_state", VstConstants.STATE_AVALIABLE);
			param.put("list_roleIds", roleIds);
//			Map<String, Object> map = _vstSysRoleDao.queryForMap(param, "vst_role_id", "vst_role_name");
			Map<String, Object> map = _vstSysRoleService.queryForMap(param, "vst_role_id", "vst_role_name");
			
			result = new HashMap<String, String>(map.size());
			for(String roleId : map.keySet()){
				result.put(roleId, map.get(roleId).toString());
			}
		} catch (Exception e) {
			logger.error("获取VST系统所有的角色失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		
		return result;
	}
	
	/**
	 * 获取所有VST管理员的角色
	 * @return
	 * @throws SystemException
	 */
	@Override
	public Map<String, String> getAllSysRolesName()
			throws SystemException {
		// 返回结果
		Map<String, String> result = null;
		
		try {
			Map<String, Object> param = new HashMap<String, Object>(1);
			//当我查询用户列表、权限列表、下拉框时，被禁用的角色看不到，因为我查询的都是正常状态的，所以为了解决问题，我改为查询全部角色
			//param.put("vst_role_state", VstConstants.STATE_AVALIABLE);
//			Map<String, Object> map = _vstSysRoleDao.queryForMap(param, "vst_role_id", "vst_role_name");
			Map<String, Object> map = _vstSysRoleService.queryForMap(param, "vst_role_id", "vst_role_name");
			
			result = new LinkedHashMap<String, String>(map.size());
			for(String roleId : map.keySet()){
				result.put(roleId, map.get(roleId).toString());
			}
		} catch (Exception e) {
			logger.error("获取VST系统所有的角色失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		
		return result;
	}
	
	/**
	 * 获取所有的系统模块名称
	 * @return
	 * @throws SystemException
	 */
	@Override
	public Map<String, String> getAllSysModulesName() throws SystemException {
		// 返回结果
		Map<String, String> result = null;
		
		try {
			Map<String, Object> param = new HashMap<String, Object>(1);
			param.put("vst_module_state", VstConstants.STATE_AVALIABLE);
			List<Map<String, Object>> list = _vstSysModuleDao.queryForList(param);
			
			result = new LinkedHashMap<String, String>(list.size());
			for(Map<String, Object> map : list){
				result.put(map.get("vst_module_id") + "", map.get("vst_module_name") + "");
			}
		} catch (Exception e) {
			logger.error("获取VST系统所有的模块失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		
		return result;
	}

	/**
	 * 获取所有的系统按钮名称
	 * @return
	 * @throws SystemException
	 */
	@Override
	public Map<String, String> getAllSysButtonsName() throws SystemException {
		// 返回结果
		Map<String, String> result = null;
		
		try {
			Map<String, Object> param = new HashMap<String, Object>(1);
			param.put("vst_button_state", VstConstants.STATE_AVALIABLE);
			List<Map<String, Object>> list = _vstSysButtonDao.queryForList(param);
			
			result = new LinkedHashMap<String, String>(list.size());
			for(Map<String, Object> map : list){
				result.put(map.get("vst_button_id") + "", map.get("vst_button_name") + "");
			}
		} catch (Exception e) {
			logger.error("获取VST系统所有的按钮失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		
		return result;
	}

	/**
	 * 写操作日志
	 * @param user
	 * @param log
	 * @throws SystemException
	 */
	@Override
	public void saveLog(UserSession user, VstSysOperateLog log)
			throws SystemException {
		try {
			Assert.notNull(log.getVst_log_type(), "操作类型为空！");
			Assert.notNull(log.getVst_log_content(), "操作内容为空！");
			Assert.notNull(user.getVstSysUser().getVst_sys_id(), "用户id为空！");
			
			// 模块名称
			String moduleName = null;
			
			// 如果操作模块不为空
			if(!ToolsString.isEmpty(log.getVst_module_id())){
				// 从session里面获取模块名称
				Map<VstSysModule, List<VstSysModule>> modules = user.getModules();
				
				lable : for(VstSysModule parent : modules.keySet()){
					if(parent.getVst_module_id().equals(log.getVst_module_id())){
						moduleName = parent.getVst_module_name();
						break;
					}else{
						for(VstSysModule child : modules.get(parent)){
							if(child.getVst_module_id().equals(log.getVst_module_id())){
								moduleName = child.getVst_module_name();
								break lable;
							}
						}
					}
				}
				
				if("F44K6WXQ5U6M".equals(log.getVst_module_id())){
					moduleName = "用户管理";
				}
				
				Assert.notNull(moduleName, "模块名称为空！");
				log.setVst_module_name(moduleName);
			}
			
			// 登录iP
			log.setVst_log_ip(user.getLoginInfo().getLoginIp());
			// 登录用户id
			log.setVst_sys_id(user.getVstSysUser().getVst_sys_id());
			// 登录用户帐号
			log.setVst_sys_username(user.getVstSysUser().getVst_sys_username());
			// 日志id
			log.setVst_log_id(ToolsRandom.getRandom(12));
			// 新增时间
			log.setVst_log_addtime(System.currentTimeMillis());
			
			// 保存操作日志
			_vstSysOperateLogDao.insert(log);
		} catch (Exception e) {
			logger.error("写操作日志失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
	}

	/**
	 * 写登录日志
	 * @param user
	 * @param log
	 * @throws SystemException
	 */
	@Override
	public void saveLoginLog(UserSession user, VstSysLoginLog log)
			throws SystemException {
		try {
			Assert.notNull(user.getVstSysUser().getVst_sys_id(), "用户id为空！");
			Assert.notNull(user.getVstSysUser().getVst_sys_username(), "登录名为空！");
			
			// 登录iP
			log.setVst_log_ip(user.getLoginInfo().getLoginIp());
			// 登录用户id
			log.setVst_sys_id(user.getVstSysUser().getVst_sys_id());
			// 登录用户帐号
			log.setVst_sys_username(user.getVstSysUser().getVst_sys_username());
			// 日志id
			log.setVst_log_id(ToolsRandom.getRandom(12));
			// 登录时间
			log.setVst_log_login_time(System.currentTimeMillis());
			
			_vstSysLoginLogDao.insert(log);
		} catch (Exception e) {
			logger.error("写登录日志失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
	}

	/**
	 * 根据用户id获取该用户的最大权限模块
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	@Override
	public List<SysModules> getParentSysModules(UserSession user)
			throws SystemException {
		// 返回结果
		List<SysModules> result = null;
		try {
			// 获取当前用户所拥有的角色id
			Map<String, String> roles = user.getRoles();
			List<String> roleIds = new ArrayList<String>();
			for(String roleId : roles.keySet()){
				roleIds.add(roleId);
			}
			
			// 查询模块列表(已经去重复)
			Map<String, Object> param = new HashMap<String, Object>(1);
			param.put("roleIds", roleIds);
			List<VstSysModule> modules = _vstSysModuleDao.queryForListAsBean(param);
			
			if(!modules.isEmpty()){
				// 一级菜单
				Map<String, VstSysModule> firstMap = new HashMap<String, VstSysModule>();
				// 二级菜单
				List<VstSysModule> secondList = new ArrayList<VstSysModule>();
				for(VstSysModule bean : modules){
					if(ToolsString.isEmpty(bean.getVst_module_parent()) || "0".equals(bean.getVst_module_parent().trim())){
						firstMap.put(bean.getVst_module_id(), bean);
					}else{
						secondList.add(bean);
					}
				}
				
				// 遍历二级菜单
				Map<VstSysModule, List<VstSysModule>> filterMap = new LinkedHashMap<VstSysModule, List<VstSysModule>>();
				List<VstSysModule> tempList = null;
				for(VstSysModule bean : secondList){
					tempList = filterMap.get(firstMap.get(bean.getVst_module_parent()));
					
					if(tempList == null){
						tempList = new ArrayList<VstSysModule>();
						filterMap.put(firstMap.get(bean.getVst_module_parent()), tempList);
					}
					
					tempList.add(bean);
				}
				
				result = new ArrayList<SysModules>();
				List<SysModules> list = null;
				// 封装数据
				for(VstSysModule parent : filterMap.keySet()){
					// 添加父模块
					SysModules pModule = new SysModules();
					pModule.setModuleId(parent.getVst_module_id());
					pModule.setModuleName(parent.getVst_module_name());
					result.add(pModule);
					
					list = new ArrayList<SysModules>();
					// 添加子模块
					for(VstSysModule child : filterMap.get(parent)){
						// 设置单个模块的模块id和名称以及按钮列表
						SysModules module = new SysModules();
						module.setModuleId(child.getVst_module_id());
						module.setModuleName(child.getVst_module_name());
						module.setButtonList(user.getButtons().get(child.getVst_module_id()));
						list.add(module);
					}
					
					// 把子模块关联到父模块下
					pModule.setModuleList(list);
				}
			}
		} catch (Exception e) {
			logger.error("获取该用户最大权限列表失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 获取字典键值对
	 */
	public Map<String, String> getDictionaryForMap(Map<String, Object> params) throws SystemException {
		Map<String, String> result = new LinkedHashMap<String, String>();
		try {
			Map<String, Object> tmpMap = new HashMap<String, Object>();
			if(params != null){
				tmpMap.putAll(params);
				if(!ToolsString.isEmpty(tmpMap.get("vst_pkg")+"")){
					// 如果该包名下没有数据，就取全部包的数据
					int count = _vstDictionaryDao.queryCount(tmpMap);
					if(count == 0){
						tmpMap.put("vst_pkg", "0");
					}
				}
			}
			List<Map<String, Object>> dataList = _vstDictionaryDao.queryForList(tmpMap);
			for(Map<String, Object> map : dataList){
				if(!ToolsString.isEmpty(map.get("vst_property_key")+"")){
					result.put(map.get("vst_property_key")+"", map.get("vst_property_value")+"");
				}
			}
		} catch (Exception e) {
			logger.error("获取字典键值对失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}

	/**
	 * 获取控制面板的配置内容
	 */
	@Override
	public String getOptionByKey(String key) throws SystemException {
		String result = "";
		try {
			Map<String, Object> bean = _vstOptionsDao.queryById(key);
			if(bean != null){
				result = ToolsString.checkEmpty(bean.get("vst_option_value"));
			}
		} catch (Exception e) {
			logger.error("获取控制面板的配置内容失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}

	/**
	 * 获取表的描述信息
	 */
	@Override
	public JSONArray getTableDesc(String tableName) throws SystemException {
		JSONArray result = new JSONArray();
		try {
			Map<String, Object> params = new HashMap<String, Object>(2);
			params.put("vst_td_table", tableName);
			params.put("vst_td_state", VstConstants.STATE_AVALIABLE);
			
			List<Map<String, Object>> list = _vstTableDescDao.queryForList(params);
			if(list != null && list.size() > 0){
				for(Map<String, Object> map : list){
					Map<String, Object> tmp = new HashMap<String, Object>(2);
					tmp.put("title", map.get("vst_td_title"));
					tmp.put("desc", map.get("vst_td_desc"));
					result.add(tmp);
				}
			}
		} catch (Exception e) {
			logger.error("获取表的描述信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
}
