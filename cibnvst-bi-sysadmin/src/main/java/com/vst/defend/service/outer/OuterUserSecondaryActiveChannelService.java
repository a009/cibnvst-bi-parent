package com.vst.defend.service.outer;

import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author wangjie
 * @date 2019/9/5 11:06
 */
@Service
public interface OuterUserSecondaryActiveChannelService {
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
     * 查询报表数据(按渠道统计)
     * @param params
     * @return
     * @throws SystemException
     */
    ReportBean getReportByChannel(Map<String, Object> params) throws SystemException;
}
