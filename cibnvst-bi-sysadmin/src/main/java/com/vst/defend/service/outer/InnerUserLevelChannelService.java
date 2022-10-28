package com.vst.defend.service.outer;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;

/**
 * 
 * @author lhp
 * @date 2018-1-24 上午10:51:41
 * @version
 */
@Service
public interface InnerUserLevelChannelService {
	/**
	 * 查询分页数据
	 * @param cutPage
	 * @return
	 * @throws SystemException
	 */
	ReportBean getCutPageData(CutPage cutPage) throws SystemException;
	
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	ReportBean getExportData(Map<String, Object> params, UserSession user) throws SystemException;
	
	/**
	 * 查询报表数据(按日期统计)
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	ReportBean getReportByDate(Map<String, Object> params) throws SystemException;
	
	/**
	 * 审核数据
	 * @param ids 数据ID
	 * @param state 审核状态
	 * @param user 操作用户
	 * @return
	 * @throws SystemException
	 */
	int auditData(String ids, int state, UserSession user) throws SystemException;
	
	/**
	 * 批量审核数据
	 * @param params
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int batchAuditData(Map<String, Object> params, UserSession user) throws SystemException;
	
	/**
	 * 批量修改调整系数
	 * @param ids
	 * @param modulus
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int modifyModulus(String ids, String modulus, UserSession user) throws SystemException;
	
	/**
	 * 导入数据
	 * @param date 日期,yyyy-MM-dd
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int importData(String date, UserSession user) throws SystemException;
	
	/**
	 * 删除数据
	 * @param params
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int deleteData(Map<String, Object> params, UserSession user) throws SystemException;

	/**
	 * 修改数据
	 * @param params
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int updateData(Map<String, Object> params, UserSession user) throws SystemException;

	/**
	 * 查询报表数据(按维度统计)
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	ReportBean getReportByDim(Map<String, Object> params) throws SystemException;
}
