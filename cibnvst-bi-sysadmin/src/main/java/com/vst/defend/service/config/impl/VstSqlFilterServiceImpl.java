package com.vst.defend.service.config.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vst.common.pojo.VstSqlFilter;
import com.vst.common.pojo.VstSysOperateLog;
import com.vst.common.tools.date.ToolsDate;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.number.ToolsRandom;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.BeanUtils;
import com.vst.defend.communal.util.CutPage;
import com.vst.defend.communal.util.LogUtils;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.communal.util.VstTools;
import com.vst.defend.dao.config.VstSqlFilterDao;
import com.vst.defend.service.config.VstSqlFilterService;

/**
 * 
 * @author lhp
 * @date 2017-11-30 下午05:35:06
 * @version
 */
@Service
public class VstSqlFilterServiceImpl implements VstSqlFilterService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstSqlFilterServiceImpl.class);
	
	/**
	 * sql筛选配置Dao接口
	 */
	@Resource
	private VstSqlFilterDao _vstSqlFilterDao;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询sql筛选配置列表
	 */
	@Override
	public CutPage getSqlFilterList(CutPage cutPage, UserSession user) throws SystemException {
		// 返回结果
		CutPage result = new CutPage();
		try {
			Map<String, Object> params = cutPage.get_castQueryParam();
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				// 参数特殊化处理
				if(!ToolsString.isEmpty(params.get("vst_filter_key")+"")){
					VstTools.paramFormat("vst_filter_key", params.get("vst_filter_key")+"", params);
				}
				if(!ToolsString.isEmpty(params.get("vst_filter_value")+"")){
					VstTools.paramFormat("vst_filter_value", params.get("vst_filter_value")+"", params);
				}
				if(!ToolsString.isEmpty(params.get("vst_filter_action_key")+"")){
					VstTools.paramFormat("vst_filter_action_key", params.get("vst_filter_action_key")+"", params);
				}
				if(!ToolsString.isEmpty(params.get("vst_filter_action_value")+"")){
					VstTools.paramFormat("vst_filter_action_value", params.get("vst_filter_action_value")+"", params);
				}
			}
			
			int count = _vstSqlFilterDao.queryCount(params);
			
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
				
				List<Map<String, Object>> list = _vstSqlFilterDao.queryForList(params);
				List<Object> dataList = new ArrayList<Object>();
				
				// 获取包名
				Map<String, Object> tmpMap = new HashMap<String, Object>(3);
				tmpMap.put("vst_table_name", "vst_sys");
				tmpMap.put("vst_column_name", "pkgName");
				tmpMap.put("vst_state", VstConstants.STATE_AVALIABLE);
				Map<String, String> pkgMap = _baseService.getDictionaryForMap(tmpMap);
				
				for(Map<String, Object> map : list){
					// 替换包名
					if(!ToolsString.isEmpty(map.get("vst_filter_pkg")+"")){
						map.put("vst_filter_pkg_name", getPkgName(map.get("vst_filter_pkg")+"", pkgMap));
					}
					if(!ToolsString.isEmpty(map.get("vst_filter_pkg_block")+"")){
						map.put("vst_filter_pkg_block_name", getPkgName(map.get("vst_filter_pkg_block")+"", pkgMap));
					}
					
					if(!ToolsString.isEmpty(map.get("vst_filter_addtime")+"")){
						map.put("vst_filter_addtime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, ToolsNumber.parseLong(map.get("vst_filter_addtime")+"")));
					}
					if(!ToolsString.isEmpty(map.get("vst_filter_uptime")+"")){
						map.put("vst_filter_uptime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, ToolsNumber.parseLong(map.get("vst_filter_uptime")+"")));
					}
					dataList.add(map);
				}
				result.set_dataList(dataList);
			}
		} catch (Exception e) {
			logger.error("查询sql筛选配置列表失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		// 参数带上
		result.set_queryParam(cutPage.get_queryParam());
		return result;
	}
	
	/**
	 * 新增sql筛选配置
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public void addSqlFilter(Map<String, Object> param, UserSession user)
			throws SystemException {
		try {
			VstSqlFilter bean = new VstSqlFilter();
			bean = (VstSqlFilter) BeanUtils.mapToBean(param, bean.getClass());
			// 设置id
			bean.setVst_filter_id(ToolsRandom.getRandom(6));
			// 设置状态
			bean.setVst_filter_state(VstConstants.STATE_AVALIABLE);
			// 设置新增时间
			bean.setVst_filter_addtime(System.currentTimeMillis());
			// 设置新增人
			bean.setVst_filter_creator(user.getLoginInfo().getLoginName());
			
			// 新增
			int n = _vstSqlFilterDao.insert(bean);
			if(n > 0){
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(),
										VstConstants.OPERATE_TYPE_ADD, 
										LogUtils.logFormat(VstConstants.OPERATE_TYPE_ADD, null, bean)));
			}
		} catch (Exception e) {
			logger.error("新增sql筛选配置失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
	}
	
	/**
	 * 根据ID，获取sql筛选配置信息
	 */
	@Override
	public Map<String, Object> getSqlFilterById(String id) throws SystemException {
		Map<String, Object> result = null;
		try {
			result = _vstSqlFilterDao.queryById(id);
		} catch (Exception e) {
			logger.error("根据id获取sql筛选配置信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改sql筛选配置
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int updateSqlFilter(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException {
		// 返回结果
		int result = 0;
		try {
			VstSqlFilter bean = new VstSqlFilter();
			bean = (VstSqlFilter) BeanUtils.mapToBean(param, bean.getClass());
			// 设置修改时间
			bean.setVst_filter_uptime(System.currentTimeMillis());
			// 设置修改人
			bean.setVst_filter_updator(user.getLoginInfo().getLoginName());
			// 更新操作
			result = _vstSqlFilterDao.update(bean);
			
			if(result == 1){
				VstSqlFilter oldBean = null;
				if(oldParam != null){
					oldBean = new VstSqlFilter();
					oldBean = (VstSqlFilter) BeanUtils.mapToBean(oldParam, oldBean.getClass());
				}
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), 
										VstConstants.OPERATE_TYPE_UPDATE, 
										LogUtils.logFormat(VstConstants.OPERATE_TYPE_UPDATE, oldBean, bean)));
			}
		} catch (Exception e) {
			logger.error("更新sql筛选配置信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 批量删除sql筛选配置
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int deleteSqlFilter(String ids, UserSession user) {
		int result = 0;
		try {
			Map<String, Object> param = new HashMap<String, Object>(1);
			param.put("list_ids", Arrays.asList(ids.split(",")));
			result = _vstSqlFilterDao.delete(param);
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("批量删除sql筛选配置, id=").append(ids);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_DEL, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("批量删除sql筛选配置失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改sql筛选配置状态
	 */
	public int modifySqlFilterState(String ids, int state, UserSession user) throws SystemException {
		int result = 0;
		try {
			Map<String, Object> params = new HashMap<String, Object>(1);
			params.put("list_ids", Arrays.asList(ids.split(",")));
			params.put("vst_filter_state", state);
			params.put("vst_filter_uptime", System.currentTimeMillis());
			params.put("vst_filter_updator", user.getLoginInfo().getLoginName());
			result = _vstSqlFilterDao.modifyState(params);
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append(state == 1 ? "启用sql筛选配置, id=" : "禁用sql筛选配置, id=").append(ids);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_UPDATE, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("修改sql筛选配置状态失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改sql筛选配置排序
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public void modifySqlFilterIndexs(String ids, String indexs, UserSession user) {
		int result = 0;
		try {
			List<VstSqlFilter> list = new ArrayList<VstSqlFilter>();			
			String[] id = ids.split(",");
			String[] index = indexs.split(",");
			long now = System.currentTimeMillis();
			VstSqlFilter bean = null;
			for(int i = 0; i < id.length; i++){
				bean = new VstSqlFilter();
				bean.setVst_filter_id(id[i]);
				bean.setVst_filter_index(Integer.parseInt(index[i]));
				bean.setVst_filter_uptime(now);
				bean.setVst_filter_updator(user.getLoginInfo().getLoginName());
				list.add(bean);
			}
			result = _vstSqlFilterDao.batchUpdate(list);
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("批量修改sql筛选配置排序, id=").append(ids).append(", 排序值=").append(indexs);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(),
										VstConstants.OPERATE_TYPE_UPDATE,
										sb.toString()));
			}
		} catch (Exception e) {
			logger.error("批量sql筛选配置排序失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
	}
	
	/**
	 * 获取键值对
	 */
	@Override
	public Map<String, Object> queryForMap(Map<String, Object> params, String key, String value){
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		try {
			List<Map<String, Object>> dataList = _vstSqlFilterDao.queryForList(params);
			if(value == null){	//value为空时，值代表整个对象
				for(Map<String, Object> map : dataList){
					if(!ToolsString.isEmpty(map.get(key)+"")){
						result.put(map.get(key)+"", map);
					}
				}
			}else{
				for(Map<String, Object> map : dataList){
					if(!ToolsString.isEmpty(map.get(key)+"")){
						result.put(map.get(key)+"", map.get(value));
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取键值对失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 批量添加sql筛选配置
	 */
	@Override
	public int batchAddSqlFilter(Map<String, Object> param, UserSession user) throws SystemException {
		int result = 0;
		try {
			// 地址数据，支持批量添加，一行一个，格式：任务ID*[!]筛选Key*[!]筛选Value*[!]排序[!]匹配类型[!]匹配上处理类型[!]未匹配处理类型[!]操作类型[!]动作Key[!]动作Value[!]适用包名[!]屏蔽包名[!]备注
			String contents = ToolsString.checkEmpty(param.get("contents"));
			
			if(!ToolsString.isEmpty(contents)){
				List<VstSqlFilter> beanList = new ArrayList<VstSqlFilter>();
				Long now = System.currentTimeMillis();
				String[] values = contents.split("\r\n");
				
				for(String value : values){
					String[] temp = value.split("\\[!\\]");
					if(temp.length < 3) continue;
					
					VstSqlFilter bean = new VstSqlFilter();
					bean.setVst_filter_id(ToolsRandom.getRandom(6));
					bean.setVst_sql_id(temp[0]);
					bean.setVst_filter_key(temp[1]);
					bean.setVst_filter_value(temp[2]);
					if(temp.length >= 4){
						bean.setVst_filter_index(ToolsNumber.parseInt(temp[3]));
					}else{
						bean.setVst_filter_index(0);
					}
					if(temp.length >= 5){
						bean.setVst_filter_match_type(ToolsNumber.parseInt(temp[4]));
					}else{
						bean.setVst_filter_match_type(1);
					}
					if(temp.length >= 6){
						bean.setVst_filter_yes_type(ToolsNumber.parseInt(temp[5]));
					}else{
						bean.setVst_filter_yes_type(1);
					}
					if(temp.length >= 7){
						bean.setVst_filter_no_type(ToolsNumber.parseInt(temp[6]));
					}else{
						bean.setVst_filter_no_type(1);
					}
					if(temp.length >= 8){
						bean.setVst_filter_action_type(ToolsNumber.parseInt(temp[7]));
					}else{
						bean.setVst_filter_action_type(0);
					}
					if(temp.length >= 9){
						bean.setVst_filter_action_key(temp[8]);
					}else{
						bean.setVst_filter_action_key("");
					}
					if(temp.length >= 10){
						bean.setVst_filter_action_value(temp[9]);
					}else{
						bean.setVst_filter_action_value("");
					}
					if(temp.length >= 11){
						bean.setVst_filter_pkg(temp[10]);
					}else{
						bean.setVst_filter_pkg("0");
					}
					if(temp.length >= 12){
						bean.setVst_filter_pkg_block(temp[11]);
					}else{
						bean.setVst_filter_pkg_block("0");
					}
					if(temp.length >= 13){
						bean.setVst_filter_summary(temp[12]);
					}else{
						bean.setVst_filter_summary("");
					}
					bean.setVst_filter_state(VstConstants.STATE_AVALIABLE);
					bean.setVst_filter_addtime(now);
					bean.setVst_filter_creator(user.getLoginInfo().getLoginName());
					
					beanList.add(bean);
				}
				// 批量添加字典
				result = _vstSqlFilterDao.batchInsert(beanList);
				if(result > 0){
					// 写操作日志
					StringBuilder sb = new StringBuilder();
					sb.append("批量添加sql筛选配置, 内容=").append(contents);
					_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(),
											VstConstants.OPERATE_TYPE_ADD,
											sb.toString()));
				}
			}
		} catch (Exception e) {
			logger.error("批量添加sql筛选配置失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 复制新增sql筛选配置
	 */
	@Override
	public int copyAddSqlFilter(Map<String, Object> param, UserSession user) throws SystemException {
		int result = 0;
		try {
			if(ToolsString.isEmpty(param.get("vst_filter_state")+"")){
				param.put("vst_filter_state", VstConstants.STATE_AVALIABLE);
			}
			
			VstSqlFilter bean = new VstSqlFilter();
			bean = (VstSqlFilter) BeanUtils.mapToBean(param, bean.getClass());
			// 设置id
			bean.setVst_filter_id(ToolsRandom.getRandom(6));
			// 设置新增时间
			bean.setVst_filter_addtime(System.currentTimeMillis());
			// 设置新增人
			bean.setVst_filter_creator(user.getLoginInfo().getLoginName());
			
			// 新增
			result = _vstSqlFilterDao.insert(bean);
			if(result > 0){
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(),
										VstConstants.OPERATE_TYPE_ADD, 
										LogUtils.logFormat(VstConstants.OPERATE_TYPE_ADD, null, bean)));
			}
		} catch (Exception e) {
			logger.error("复制新增sql筛选配置失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 从Map中获取包名称
	 * @param pkg
	 * @param pkgMap
	 * @return
	 */
	private String getPkgName(String pkg, Map<String, String> pkgMap){
		String result = "";
		if(!ToolsString.isEmpty(pkg)){
			String[] pkgArr = pkg.split(",");
			for(String str : pkgArr){
				result += pkgMap.get(str) + ",";
			}
			if(result.length() > 0){
				result = result.substring(0, result.length()-1);
			}
		}
		return result;
	}
}
