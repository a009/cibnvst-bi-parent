package com.vst.defend.service.home.impl;

import com.vst.common.pojo.VstSysOperateLog;
import com.vst.common.tools.date.ToolsDate;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.communal.util.VstTools;
import com.vst.defend.dao2.home.VstHomeClassifyClickDao;
import com.vst.defend.service.home.VstHomeClassifyClickService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangjie
 * @date 2020/1/17 11:32
 */
@Service
public class VstHomeClassifyClickServiceImpl implements VstHomeClassifyClickService {
    /**
     * 写日志
     */
    private static final Log logger = LogFactory.getLog(VstHomeClassifyClickServiceImpl.class);

    /**
     * 首页分类点击Dao接口
     */
    @Resource
    private VstHomeClassifyClickDao _vstHomeClassifyClickDao;

    /**
     * 基本操作接口
     */
    @Resource
    private BaseService _baseService;

    /**
     * 导出数据
     */
    @Override
    public ReportBean getExportData(Map<String, Object> params, UserSession user) throws SystemException {
        ReportBean bean = new ReportBean();
        try{
            if(params == null){
                params = new HashMap<String, Object>();
            }else{
                params.put("startDay", VstTools.parseInt(params.get("startDay")+""));
                params.put("endDay", VstTools.parseInt(params.get("endDay")+""));
                // 参数特殊化处理
                if(!ToolsString.isEmpty(params.get("vst_hcc_type")+"")){
                    VstTools.paramFormat("vst_hcc_type", params.get("vst_hcc_type")+"", params);
                }
            }

            List<Map<String, Object>> data = _vstHomeClassifyClickDao.queryExport(params);
            if(data != null && data.size() > 0){
                Map<String, Object> temp = new HashMap<String, Object>(4);
                // 获取包名
                temp.put("vst_pkg", params.get("pkgName"));
                temp.put("vst_table_name", "vst_sys");
                temp.put("vst_column_name", "pkgName");
                temp.put("vst_state", VstConstants.STATE_AVALIABLE);
                Map<String, String> pkgMap = _baseService.getDictionaryForMap(temp);
                // 获取分类
                temp.put("vst_table_name", "vst_movie");
                temp.put("vst_column_name", "classify");
                Map<String, String> classifyMap = _baseService.getDictionaryForMap(temp);

                // 导出字段内容
                String export_column = ToolsString.checkEmpty(params.get("export_column"));
                List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();

                for(Map<String, Object> map : data){
                    //更改日期格式
                    String week = ToolsDate.getWeekByDate(map.get("日期")+"", ToolsDate.yyyy_MM_dd4);
                    if("周日".equals(week) || "周六".equals(week)){
                        map.put("日期", map.get("日期") + " " +week);
                    }
                    if(pkgMap.containsKey(map.get("包名")+"")){
                        map.put("包名", pkgMap.get(map.get("包名")+""));
                    }
                    if(classifyMap.containsKey(map.get("影片分类")+"")){
                        map.put("影片分类", classifyMap.get(map.get("影片分类")+""));
                    }
                    // 只保留关键字段
                    Map<String, Object> tmp = new LinkedHashMap<String, Object>();
                    for(String key : map.keySet()){
                        if(export_column.indexOf(key) >= 0){
                            tmp.put(key, map.get(key));
                        }
                    }
                    dataList.add(tmp);
                }
                bean.setMapData(dataList);
            }
            bean.setTitle("vst_home_classify_click_data");

            // 写操作日志
            StringBuilder sb = new StringBuilder();
            sb.append("导出数据，查询条件：").append(params);
            _baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_EXPORT, sb.toString()));
        }catch(Exception e){
            logger.error("导出首页分类点击数据失败: " + SystemException.getExceptionInfo(e));
            throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
        }
        return bean;
    }
}
