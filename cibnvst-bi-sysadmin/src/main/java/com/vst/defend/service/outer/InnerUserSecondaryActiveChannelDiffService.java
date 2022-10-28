package com.vst.defend.service.outer;

import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author wangjie
 * @date 2020/5/8 17:12
 */
@Service
public interface InnerUserSecondaryActiveChannelDiffService {
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
     * 批量修改调整系数
     * @param ids
     * @param modulus
     * @param user
     * @return
     * @throws SystemException
     */
    int modifyModulus(String ids, String modulus, UserSession user) throws SystemException;

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
}
