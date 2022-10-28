package com.vst.defend.service.outer;

import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author wangjie
 * @date 2019/8/5 14:44
 */
@Service
public interface OuterChannelLevelService {
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
}
