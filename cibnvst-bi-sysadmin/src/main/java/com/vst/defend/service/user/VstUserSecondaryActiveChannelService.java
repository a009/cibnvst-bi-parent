package com.vst.defend.service.user;

import com.alibaba.fastjson.JSONObject;
import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author wangjie
 * @date 2019/9/4 11:23
 */
@Service
public interface VstUserSecondaryActiveChannelService {
    /**
     * 导出数据
     * @param params
     * @return
     */
    ReportBean getExportData(Map<String, Object> params, UserSession user) throws SystemException;

    /**
     * 按渠道统计
     * @param params
     * @return
     * @throws SystemException
     */
    JSONObject getReportByChannel(Map<String, Object> params) throws SystemException;
}
