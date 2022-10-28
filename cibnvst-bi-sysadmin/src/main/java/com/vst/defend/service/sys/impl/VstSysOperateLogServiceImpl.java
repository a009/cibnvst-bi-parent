package com.vst.defend.service.sys.impl;

import com.vst.common.pojo.VstSysOperateLog;
import com.vst.common.tools.date.ToolsDate;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.CutPage;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.dao.sys.VstSysOperateLogDao;
import com.vst.defend.service.sys.VstSysOperateLogService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author lhp
 * @date 2017-4-7 下午12:30:26
 * @description
 * @version
 */
 @Service
public class VstSysOperateLogServiceImpl implements VstSysOperateLogService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstSysOperateLogServiceImpl.class);
	
	/**
	 * 操作日志接口
	 */
	@Resource
	private VstSysOperateLogDao _vstSysOperateLogDao;
	
	/**
	 * 基本操作接口
	 */
	@Resource
	private BaseService _baseService;
	
	/**
	 * 获取操作日志列表
	 * @param cutPage
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	@Override
	public CutPage getSysOperLogList(CutPage cutPage, UserSession user) throws SystemException {
		// 返回结果
		CutPage result = new CutPage();
		try {
			// 获取页面查询参数
			Map<String, Object> params = cutPage.get_castQueryParam();
			if(params == null){
				params = new HashMap<String, Object>();
			}else{
				// 格式化时间
				if(!ToolsString.isEmpty(ToolsString.checkEmpty(params.get("operate_starttime")))){
					params.put("operate_starttime", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm_ss, params.get("operate_starttime")+""));
				}
				if(!ToolsString.isEmpty(ToolsString.checkEmpty(params.get("operate_endtime")))){
					params.put("operate_endtime", ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm_ss, params.get("operate_endtime")+""));
				}
			}
			
			int count = _vstSysOperateLogDao.queryCount(params);
			
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
				
				List<Map<String, Object>> list = _vstSysOperateLogDao.queryForList(params);
				List<Object> dataList = new ArrayList<Object>();
				
				for(Map<String, Object> map : list){
					if(!ToolsString.isEmpty(map.get("vst_log_addtime")+"")){
						map.put("vst_log_addtime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, map.get("vst_log_addtime")+""));
					}
					dataList.add(map);
				}
				result.set_dataList(dataList);
			}
		} catch (Exception e) {
			logger.error("查询操作日志列表失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		// 参数带上
		result.set_queryParam(cutPage.get_queryParam());
		return result;
	}
	
	/**
	 * 根据时间参数删除操作日志
	 * @param param
	 * @param user
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
	public int deleteSysOperLogs(Map<String, Object> param, UserSession user) {
		// 返回结果
		int result = 0;
		try {
			//转换时间参数
			Long nowTime = System.currentTimeMillis();
			String startT = ToolsString.checkEmpty(param.get("operate_starttime")+"");
			String endT = ToolsString.checkEmpty(param.get("operate_endtime")+"");
			Long starttime;
			Long endtime;
			StringBuilder sb = new StringBuilder();
			
			if("".equals(startT) && "".equals(endT)){
				//一个月之前时间
				endtime = nowTime;
				starttime = nowTime - 31*24*60*60*1000L;
				param.put("operate_starttime", starttime);
				param.put("operate_endtime", endtime);
				sb.append("删除一个月之前的操作日志，开始时间=").append(starttime).append("，结束时间=").append(endtime)
					.append("，操作人=").append(user.getLoginInfo().getLoginName());
			}else if(!"".equals(startT) && "".equals(endT)){
				//只有开始时间
				endtime = nowTime;
				starttime = ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm_ss, startT);
				param.put("operate_starttime", starttime);
				param.put("operate_endtime", endtime);
				sb.append("删除开始时间之后的操作日志，开始时间=").append(starttime).append("，结束时间=").append(endtime)
					.append("，操作人=").append(user.getLoginInfo().getLoginName());
			}else if("".equals(startT) && !"".equals(endT)){
				//只有结束时间
				endtime = ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm_ss, endT);
				param.remove("operate_starttime");
				param.put("operate_endtime", endtime);
				sb.append("删除结束时间之前的操作日志，结束时间=").append(endtime)
					.append("，操作人=").append(user.getLoginInfo().getLoginName());
			}else if(!"".equals(startT) && !"".equals(endT)){
				//时间段内
				endtime = ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm_ss, endT);
				starttime = ToolsDate.toUnixTimestamp(ToolsDate.yyyy_MM_dd_HH_mm_ss, startT);
				param.put("operate_starttime", starttime);
				param.put("operate_endtime", endtime);
				sb.append("删除指定时间内的操作日志，开始时间=").append(starttime).append("，结束时间=").append(endtime)
					.append("，操作人=").append(user.getLoginInfo().getLoginName());
			}
			// 删除数据
			result = _vstSysOperateLogDao.delete(param);

			if (result > 0) {
				// 写操作日志
				_baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_DEL, sb.toString()));
			}
		} catch (Exception e) {
			logger.error("删除操作日志失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return result;
	}
}
