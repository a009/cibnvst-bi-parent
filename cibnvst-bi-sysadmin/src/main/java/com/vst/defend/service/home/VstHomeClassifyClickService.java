package com.vst.defend.service.home;

import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author wangjie
 * @date 2020/1/17 11:03
 */
@Service
public interface VstHomeClassifyClickService {
    /**
     * 导出数据
     * @param params
     * @return
     */
    ReportBean getExportData(Map<String, Object> params, UserSession user) throws SystemException;
}
