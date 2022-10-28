package com.vst.defend.service.sys.impl;

import com.vst.common.pojo.VstDictionary;
import com.vst.common.pojo.VstSysOperateLog;
import com.vst.common.tools.date.ToolsDate;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.BeanUtils;
import com.vst.defend.communal.util.CutPage;
import com.vst.defend.communal.util.LogUtils;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.dao.sys.VstDictionaryDao;
import com.vst.defend.service.sys.VstDictionaryService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author lhp
 * @date 2017-6-14 下午12:11:21
 * @description
 * @version
 */
@Service
public class VstDictionaryServiceImpl implements VstDictionaryService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstDictionaryServiceImpl.class);
	
	/**
	 * 字典Dao接口
	 */
	@Resource
	private VstDictionaryDao _vstDictionaryDao;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询字典列表
	 */
	@Override
	public CutPage getDictionaryList(CutPage cutPage, UserSession user) throws SystemException {
		// 返回结果
		CutPage result = new CutPage();
		try {
			Map<String, Object> params = cutPage.get_castQueryParam();
			if(params == null){
				params = new HashMap<String, Object>();
			}
			
			int count = _vstDictionaryDao.queryCount(params);
			
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
				
				List<Map<String, Object>> list = _vstDictionaryDao.queryForList(params);
				List<Object> dataList = new ArrayList<Object>();
				
				for(Map<String, Object> map : list){
					map.put("vst_addtime", ToolsDate.formatDate("yyyy-MM-dd HH:mm:ss", Long.valueOf(map.get("vst_addtime").toString())));
					map.put("vst_uptime", ToolsDate.formatDate("yyyy-MM-dd HH:mm:ss", Long.valueOf(map.get("vst_uptime").toString())));
					dataList.add(map);
				}
				result.set_dataList(dataList);
			}
		} catch (Exception e) {
			logger.error("查询字典列表失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		// 参数带上
		result.set_queryParam(cutPage.get_queryParam());
		return result;
	}
	
	/**
	 * 新增字典
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public void addDictionary(Map<String, Object> param, UserSession user)
			throws SystemException {
		try {
			VstDictionary bean = new VstDictionary();
			bean = (VstDictionary) BeanUtils.mapToBean(param, bean.getClass());
			// 设置状态
			bean.setVst_state(VstConstants.STATE_AVALIABLE);
			// 设置添加时间
			bean.setVst_addtime(System.currentTimeMillis());
			// 设置更新时间
			bean.setVst_uptime(System.currentTimeMillis());
			// 设置操作人
			bean.setVst_operator(user.getLoginInfo().getLoginName());
			
			// 新增
			int n = _vstDictionaryDao.insert(bean);
			if(n > 0){
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_ADD, 
						LogUtils.logFormat(VstConstants.OPERATE_TYPE_ADD, null, bean)));
			}
		} catch (Exception e) {
			logger.error("新增字典失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
	}
	
	/**
	 * 根据ID，获取字典信息
	 */
	@Override
	public Map<String, Object> getDictionaryById(String id) throws SystemException {
		Map<String, Object> result = null;
		try {
			result = _vstDictionaryDao.queryById(id);
		} catch (Exception e) {
			logger.error("根据字典id获取字典信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改字典
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int updateDictionary(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException {
		// 返回结果
		int result = 0;
		try {
			VstDictionary bean = new VstDictionary();
			bean = (VstDictionary) BeanUtils.mapToBean(param, bean.getClass());
			// 设置更新时间
			bean.setVst_uptime(System.currentTimeMillis());
			// 设置操作人
			bean.setVst_operator(user.getLoginInfo().getLoginName());
			// 更新操作
			result = _vstDictionaryDao.update(bean);
			
			if(result == 1){
				VstDictionary oldBean = null;
				if(oldParam != null){
					oldBean = new VstDictionary();
					oldBean = (VstDictionary) BeanUtils.mapToBean(oldParam, oldBean.getClass());
				}
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), 
										VstConstants.OPERATE_TYPE_UPDATE, 
										LogUtils.logFormat(VstConstants.OPERATE_TYPE_UPDATE, oldBean, bean)));
			}
		} catch (Exception e) {
			logger.error("更新字典信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 批量删除字典
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int deleteDictionary(String ids, UserSession user) {
		int result = 0;
		try {
			Map<String, Object> param = new HashMap<String, Object>(1);
			param.put("list_ids", Arrays.asList(ids.split(",")));
			result = _vstDictionaryDao.delete(param);
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("批量删除字典, id=").append(ids);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_DEL, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("批量删除字典失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改字典状态
	 */
	public int modifyDictionaryState(String ids, int state, UserSession user) throws SystemException {
		int result = 0;
		try {
			Map<String, Object> params = new HashMap<String, Object>(1);
			params.put("list_ids", Arrays.asList(ids.split(",")));
			params.put("vst_state", state);
			params.put("vst_uptime", System.currentTimeMillis());
			params.put("vst_operator", user.getLoginInfo().getLoginName());
			result = _vstDictionaryDao.modifyState(params);
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append(state == 1 ? "启用字典，字典id=" : "禁用字典，字典id=").append(ids)
				.append("，操作人=").append(user.getLoginInfo().getLoginName());
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_UPDATE, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("修改字典状态失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改字典排序
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public void modifyDictionaryIndexs(String ids, String indexs, UserSession user) {
		int result = 0;
		try {
			List<VstDictionary> list = new ArrayList<VstDictionary>();			
			String[] id = ids.split(",");
			String[] index = indexs.split(",");
			long now = System.currentTimeMillis();
			VstDictionary bean = null;
			for(int i = 0; i < id.length; i++){
				bean = new VstDictionary();
				bean.setVst_id(Integer.parseInt(id[i]));
				bean.setVst_index(Integer.parseInt(index[i]));
				bean.setVst_uptime(now);
				bean.setVst_operator(user.getLoginInfo().getLoginName());
				list.add(bean);
			}
			result = _vstDictionaryDao.batchUpdate(list);
			
			if(result > 0){
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_UPDATE, "批量修改字典排序，记录id=" + ids + ",排序值=" + indexs));
			}
		} catch (Exception e) {
			logger.error("批量字典排序失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
	}
	
	/**
	 * 批量添加字典
	 */
	@Override
	public int batchAddDictionary(Map<String, Object> param, UserSession user)
			throws SystemException {
		int result = 0;
		try {
			// 字典地址，支持批量添加字典，一行一个，格式：表名|字段名|属性键|属性值|排序(选)|包名(选)
			String contents = ToolsString.checkEmpty(param.get("contents"));
			
			if(!ToolsString.isEmpty(contents)){
				List<VstDictionary> beanList = new ArrayList<VstDictionary>();
				Long now = System.currentTimeMillis();
				String[] values = contents.split("\r\n");
				
				for(String value : values){
					String[] temp = value.split("[|]");
					if(temp.length < 4) continue;
					
					VstDictionary bean = new VstDictionary();
					bean.setVst_table_name(temp[0]);
					bean.setVst_column_name(temp[1]);
					bean.setVst_property_key(temp[2]);
					bean.setVst_property_value(temp[3]);
					if(temp.length >= 5){
						bean.setVst_index(ToolsNumber.parseInt(temp[4]));
					}else{
						bean.setVst_index(0);
					}
					if(temp.length >= 6){
						bean.setVst_pkg(temp[5]);
					}else{
						bean.setVst_pkg("0");
					}
					bean.setVst_state(VstConstants.STATE_AVALIABLE);
					bean.setVst_addtime(now);
					bean.setVst_uptime(now);
					bean.setVst_operator(user.getLoginInfo().getLoginName());
					
					beanList.add(bean);
				}
				// 批量添加字典
				result = _vstDictionaryDao.batchInsert(beanList);
				if(result > 0){
					// 写操作日志
					_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_ADD, 
							LogUtils.logFormat(VstConstants.OPERATE_TYPE_ADD, null, "批量添加字典成功！")));
				}
			}
		} catch (Exception e) {
			logger.error("批量添加字典失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 获取键值对
	 */
	@Override
	public Map<String, Object> queryForMap(Map<String, Object> params, String key, String value){
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		try {
			List<Map<String, Object>> dataList = _vstDictionaryDao.queryForList(params);
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
	 * 修改字典属性键
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public void modifyDictionaryKeys(String ids, String keys, UserSession user) {
		int result = 0;
		try {
			List<VstDictionary> list = new ArrayList<VstDictionary>();			
			String[] id = ids.split(",");
			String[] key = keys.split(",");
			long now = System.currentTimeMillis();
			VstDictionary bean = null;
			for(int i = 0; i < id.length; i++){
				bean = new VstDictionary();
				bean.setVst_id(Integer.parseInt(id[i]));
				bean.setVst_property_key(key[i]);
				bean.setVst_uptime(now);
				bean.setVst_operator(user.getLoginInfo().getLoginName());
				list.add(bean);
			}
			result = _vstDictionaryDao.batchUpdate(list);
			
			if(result > 0){
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_UPDATE, "批量修改字典属性键，记录id=" + ids + ",keys=" + keys));
			}
		} catch (Exception e) {
			logger.error("批量字典属性键失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
	}
	
	/**
	 * 修改字典属性值
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public void modifyDictionaryValues(String ids, String values, UserSession user) {
		int result = 0;
		try {
			List<VstDictionary> list = new ArrayList<VstDictionary>();			
			String[] id = ids.split(",");
			String[] value = values.split(",");
			long now = System.currentTimeMillis();
			VstDictionary bean = null;
			for(int i = 0; i < id.length; i++){
				bean = new VstDictionary();
				bean.setVst_id(Integer.parseInt(id[i]));
				bean.setVst_property_value(value[i]);
				bean.setVst_uptime(now);
				bean.setVst_operator(user.getLoginInfo().getLoginName());
				list.add(bean);
			}
			result = _vstDictionaryDao.batchUpdate(list);
			
			if(result > 0){
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_UPDATE, "批量修改字典属性值，记录id=" + ids + ",values=" + values));
			}
		} catch (Exception e) {
			logger.error("批量字典属性值失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
	}
	
	/**
	 * 复制字典数据
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int copyDictionary(String ids, String dataType, String dataValue, UserSession user){
		int result = 0;
		try {
			Long now = System.currentTimeMillis();
			Map<String, Object> params = new HashMap<String, Object>(1);
			params.put("list_ids", Arrays.asList(ids.split(",")));
			List<Map<String, Object>> data = _vstDictionaryDao.queryForList(params);
			List<VstDictionary> list = new ArrayList<VstDictionary>();
			
			for(Map<String, Object> map : data){
				VstDictionary bean = new VstDictionary();
				bean = (VstDictionary) BeanUtils.mapToBean(map, bean.getClass());
				
				// 设置新增时间
				bean.setVst_addtime(now);
				// 设置操作人
				bean.setVst_operator(user.getLoginInfo().getLoginName());
				
				if("1".equals(dataType)){
					bean.setVst_table_name(dataValue);
				}else if("2".equals(dataType)){
					bean.setVst_column_name(dataValue);
				}else if("3".equals(dataType)){
					bean.setVst_pkg(dataValue);
				}
				
				list.add(bean);
			}
			
			// 批量添加
			result = _vstDictionaryDao.batchInsert(list);
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("复制字典数据, id=").append(ids);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_ADD, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("复制字典数据失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 替换信息
	 */
	@Override
	public int replaceDictionary(String ids, int replace_type, String replace_before, String replace_after, UserSession user){
		int result = 0;
		try{
			Map<String, Object> params = new HashMap<String, Object>();
			if(!ToolsString.isEmpty(ids)){
				params.put("list_ids", Arrays.asList(ids.split(",")));
			}
			
			Long now = System.currentTimeMillis();
			List<VstDictionary> listBean = new ArrayList<VstDictionary>();
			
			List<Map<String, Object>> listMap = _vstDictionaryDao.queryForList(params);
			
			for(Map<String, Object> map : listMap){
				VstDictionary bean = new VstDictionary();
				bean.setVst_id(ToolsNumber.parseInt(map.get("vst_id")+""));
				bean.setVst_uptime(now);
				bean.setVst_operator(user.getLoginInfo().getLoginName());
				
				if(replace_type == 1){	//1表名
					String vst_table_name = (map.get("vst_table_name")+"").replace(replace_before, replace_after);
					bean.setVst_table_name(vst_table_name);
				}else if(replace_type == 2){	//2字段名
					String vst_column_name = (map.get("vst_column_name")+"").replace(replace_before, replace_after);
					bean.setVst_column_name(vst_column_name);
				}else if(replace_type == 3){	//3包名
					String vst_pkg = (map.get("vst_pkg")+"").replace(replace_before, replace_after);
					bean.setVst_pkg(vst_pkg);
				}
				
				listBean.add(bean);
			}
			
			// 批量修改
			result = _vstDictionaryDao.batchUpdate(listBean);//批量修改集数
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder("替换信息，");
				sb.append("ids=").append(ids);
				sb.append("，replace_type=").append(replace_type);
				sb.append("，replace_before=").append(replace_before);
				sb.append("，replace_after=").append(replace_after);
				sb.append("，操作人=").append(user.getLoginInfo().getLoginName());
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_UPDATE, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("替换信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
}
