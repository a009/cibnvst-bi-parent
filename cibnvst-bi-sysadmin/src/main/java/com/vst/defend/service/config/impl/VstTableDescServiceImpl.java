package com.vst.defend.service.config.impl;

import com.vst.common.pojo.VstSysOperateLog;
import com.vst.common.pojo.VstTableDesc;
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
import com.vst.defend.dao.config.VstTableDescDao;
import com.vst.defend.service.config.VstTableDescService;
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
 * @date 2018-4-16 下午03:27:56
 * @version
 */
@Service
public class VstTableDescServiceImpl implements VstTableDescService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstTableDescServiceImpl.class);
	
	/**
	 * 表算法描述Dao接口
	 */
	@Resource
	private VstTableDescDao _vstTableDescDao;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 查询表算法描述列表
	 */
	@Override
	public CutPage getTableDescList(CutPage cutPage, UserSession user) throws SystemException {
		// 返回结果
		CutPage result = new CutPage();
		try {
			Map<String, Object> params = cutPage.get_castQueryParam();
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				// 参数特殊化处理
				if(!ToolsString.isEmpty(params.get("vst_td_title")+"")){
					VstTools.paramFormat("vst_td_title", params.get("vst_td_title")+"", params);
				}
				if(!ToolsString.isEmpty(params.get("vst_td_desc")+"")){
					VstTools.paramFormat("vst_td_desc", params.get("vst_td_desc")+"", params);
				}
			}
			
			int count = _vstTableDescDao.queryCount(params);
			
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
				
				List<Map<String, Object>> list = _vstTableDescDao.queryForList(params);
				List<Object> dataList = new ArrayList<Object>();
				
				for(Map<String, Object> map : list){
					if(!ToolsString.isEmpty(map.get("vst_td_addtime")+"")){
						map.put("vst_td_addtime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, ToolsNumber.parseLong(map.get("vst_td_addtime")+"")));
					}
					if(!ToolsString.isEmpty(map.get("vst_td_uptime")+"")){
						map.put("vst_td_uptime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, ToolsNumber.parseLong(map.get("vst_td_uptime")+"")));
					}
					dataList.add(map);
				}
				result.set_dataList(dataList);
			}
		} catch (Exception e) {
			logger.error("查询表算法描述列表失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		// 参数带上
		result.set_queryParam(cutPage.get_queryParam());
		return result;
	}
	
	/**
	 * 新增表算法描述
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public void addTableDesc(Map<String, Object> param, UserSession user)
			throws SystemException {
		try {
			VstTableDesc bean = new VstTableDesc();
			bean = (VstTableDesc) BeanUtils.mapToBean(param, bean.getClass());
			// 设置id
			bean.setVst_td_id(ToolsRandom.getRandom(6));
			// 设置状态
			bean.setVst_td_state(VstConstants.STATE_AVALIABLE);
			// 设置新增时间
			bean.setVst_td_addtime(System.currentTimeMillis());
			// 设置新增人
			bean.setVst_td_creator(user.getLoginInfo().getLoginName());
			
			// 新增
			int n = _vstTableDescDao.insert(bean);
			if(n > 0){
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(),
										VstConstants.OPERATE_TYPE_ADD, 
										LogUtils.logFormat(VstConstants.OPERATE_TYPE_ADD, null, bean)));
			}
		} catch (Exception e) {
			logger.error("新增表算法描述失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
	}
	
	/**
	 * 根据ID，获取表算法描述信息
	 */
	@Override
	public Map<String, Object> getTableDescById(String id) throws SystemException {
		Map<String, Object> result = null;
		try {
			result = _vstTableDescDao.queryById(id);
		} catch (Exception e) {
			logger.error("根据id获取表算法描述信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改表算法描述
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int updateTableDesc(Map<String, Object> oldParam, Map<String, Object> param, UserSession user) throws SystemException {
		// 返回结果
		int result = 0;
		try {
			VstTableDesc bean = new VstTableDesc();
			bean = (VstTableDesc) BeanUtils.mapToBean(param, bean.getClass());
			// 设置修改时间
			bean.setVst_td_uptime(System.currentTimeMillis());
			// 设置修改人
			bean.setVst_td_updator(user.getLoginInfo().getLoginName());
			// 更新操作
			result = _vstTableDescDao.update(bean);
			
			if(result == 1){
				VstTableDesc oldBean = null;
				if(oldParam != null){
					oldBean = new VstTableDesc();
					oldBean = (VstTableDesc) BeanUtils.mapToBean(oldParam, oldBean.getClass());
				}
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), 
										VstConstants.OPERATE_TYPE_UPDATE, 
										LogUtils.logFormat(VstConstants.OPERATE_TYPE_UPDATE, oldBean, bean)));
			}
		} catch (Exception e) {
			logger.error("更新表算法描述信息失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 批量删除表算法描述
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int deleteTableDesc(String ids, UserSession user) {
		int result = 0;
		try {
			Map<String, Object> param = new HashMap<String, Object>(1);
			param.put("list_ids", Arrays.asList(ids.split(",")));
			result = _vstTableDescDao.delete(param);
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("批量删除表算法描述, id=").append(ids);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(),
										VstConstants.OPERATE_TYPE_DEL,
										sb.toString()));
			}
		} catch (Exception e) {
			logger.error("批量删除表算法描述失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改表算法描述状态
	 */
	public int modifyTableDescState(String ids, int state, UserSession user) throws SystemException {
		int result = 0;
		try {
			Map<String, Object> params = new HashMap<String, Object>(1);
			params.put("list_ids", Arrays.asList(ids.split(",")));
			params.put("vst_td_state", state);
			params.put("vst_td_uptime", System.currentTimeMillis());
			params.put("vst_td_updator", user.getLoginInfo().getLoginName());
			result = _vstTableDescDao.modifyState(params);
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append(state == 1 ? "启用表算法描述, id=" : "禁用表算法描述, id=").append(ids);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_UPDATE, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("修改表算法描述状态失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 修改表算法描述排序
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public void modifyTableDescIndexs(String ids, String indexs, UserSession user) {
		int result = 0;
		try {
			List<VstTableDesc> list = new ArrayList<VstTableDesc>();			
			String[] id = ids.split(",");
			String[] index = indexs.split(",");
			long now = System.currentTimeMillis();
			VstTableDesc bean = null;
			for(int i = 0; i < id.length; i++){
				bean = new VstTableDesc();
				bean.setVst_td_id(id[i]);
				bean.setVst_td_index(Integer.parseInt(index[i]));
				bean.setVst_td_uptime(now);
				bean.setVst_td_updator(user.getLoginInfo().getLoginName());
				list.add(bean);
			}
			result = _vstTableDescDao.batchUpdate(list);
			
			if(result > 0){
				// 写操作日志
				StringBuilder sb = new StringBuilder();
				sb.append("批量修改表算法描述排序, id=").append(ids).append(", 排序值=").append(indexs);
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(),
										VstConstants.OPERATE_TYPE_UPDATE,
										sb.toString()));
			}
		} catch (Exception e) {
			logger.error("批量表算法描述排序失败: " + SystemException.getExceptionInfo(e));
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
			List<Map<String, Object>> dataList = _vstTableDescDao.queryForList(params);
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
	 * 批量添加表算法描述
	 */
	@Override
	public int batchAddTableDesc(Map<String, Object> params, UserSession user) throws SystemException {
		int result = 0;
		try {
			// 地址，支持批量添加，一行一个，格式：表名|属性|内容|排序|备注(选)
			String contents = ToolsString.checkEmpty(params.get("contents"));
			
			if(!ToolsString.isEmpty(contents)){
				List<VstTableDesc> beanList = new ArrayList<VstTableDesc>();
				Long now = System.currentTimeMillis();
				String[] values = contents.split("\r\n");
				
				for(String value : values){
					String[] temp = value.split("[|]");
					if(temp.length < 4) continue;
					
					VstTableDesc bean = new VstTableDesc();
					bean.setVst_td_id(ToolsRandom.getRandom(6));
					bean.setVst_td_table(temp[0]);
					bean.setVst_td_title(temp[1]);
					bean.setVst_td_desc(temp[2]);
					bean.setVst_td_index(ToolsNumber.parseInt(temp[3]));
					if(temp.length >= 5){
						bean.setVst_td_summary(temp[4]);
					}else{
						bean.setVst_td_summary("");
					}
					bean.setVst_td_state(VstConstants.STATE_AVALIABLE);
					bean.setVst_td_addtime(now);
					bean.setVst_td_creator(user.getLoginInfo().getLoginName());
					
					beanList.add(bean);
				}
				// 批量添加
				result = _vstTableDescDao.batchInsert(beanList);
				if(result > 0){
					// 写操作日志
					StringBuilder sb = new StringBuilder();
					sb.append("批量添加表算法描述, 内容=").append(contents);
					_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(),
											VstConstants.OPERATE_TYPE_ADD,
											sb.toString()));
				}
			}
		} catch (Exception e) {
			logger.error("批量添加表算法描述失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
	
	/**
	 * 复制新增表算法描述
	 */
	@Override
	public int copyAddTableDesc(Map<String, Object> param, UserSession user) throws SystemException {
		int result = 0;
		try {
			if(ToolsString.isEmpty(param.get("vst_td_state")+"")){
				param.put("vst_td_state", VstConstants.STATE_AVALIABLE);
			}
			
			VstTableDesc bean = new VstTableDesc();
			bean = (VstTableDesc) BeanUtils.mapToBean(param, bean.getClass());
			// 设置id
			bean.setVst_td_id(ToolsRandom.getRandom(6));
			// 设置新增时间
			bean.setVst_td_addtime(System.currentTimeMillis());
			// 设置新增人
			bean.setVst_td_creator(user.getLoginInfo().getLoginName());
			
			// 新增
			result = _vstTableDescDao.insert(bean);
			if(result > 0){
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(),
										VstConstants.OPERATE_TYPE_ADD, 
										LogUtils.logFormat(VstConstants.OPERATE_TYPE_ADD, null, bean)));
			}
		} catch (Exception e) {
			logger.error("复制新增表算法描述失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
}
