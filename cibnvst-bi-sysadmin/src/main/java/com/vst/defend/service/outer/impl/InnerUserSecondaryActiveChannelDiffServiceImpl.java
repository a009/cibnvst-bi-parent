package com.vst.defend.service.outer.impl;

import com.vst.common.pojo.VstSysOperateLog;
import com.vst.common.pojo.VstUserSecondaryActiveChannelDiff;
import com.vst.common.tools.date.ToolsDate;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.CutPage;
import com.vst.defend.communal.util.VstConstants;
import com.vst.defend.communal.util.VstTools;
import com.vst.defend.dao3.inner.InnerUserSecondaryActiveChannelDiffDao;
import com.vst.defend.service.outer.InnerUserSecondaryActiveChannelDiffService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangjie
 * @date 2020/5/8 17:13
 */
@Service
public class InnerUserSecondaryActiveChannelDiffServiceImpl implements InnerUserSecondaryActiveChannelDiffService {
    /**
     * 写日志
     */
    private static final Log logger = LogFactory.getLog(InnerUserSecondaryActiveChannelDiffServiceImpl.class);

    /**
     * 二次活跃渠道用户(对内)Dao接口
     */
    @Resource
    private InnerUserSecondaryActiveChannelDiffDao _innerUserSecondaryActiveChannelDiffDao;

    /**
     * 基本操作接口
     */
    @Resource
    private BaseService _baseService;

    /**
     * 查询分页数据
     */
    @Override
    public ReportBean getCutPageData(CutPage cutPage) throws SystemException {
        ReportBean bean = new ReportBean();
        try{
            Map<String, Object> params = cutPage.get_castQueryParam();
            if(params == null){
                params = new HashMap<String, Object>();
            }else{
                params.put("startDay", VstTools.parseInt(params.get("startDay")+""));
                params.put("endDay", VstTools.parseInt(params.get("endDay")+""));
                // 参数特殊化处理
                if(!ToolsString.isEmpty(params.get("vst_usacd_channel")+"")){
                    VstTools.paramFormat("vst_usacd_channel", params.get("vst_usacd_channel")+"", params);
                }
            }

            int count = _innerUserSecondaryActiveChannelDiffDao.queryCount(params);

            if(count != 0){
                int start = (cutPage.get_currPage()-1) * cutPage.get_singleCount();

                params.put("offset", start);
                params.put("limit", cutPage.get_singleCount());

                List<Map<String, Object>> list = _innerUserSecondaryActiveChannelDiffDao.queryForList(params);

                for(Map<String, Object> map : list){
                    // 转换日期格式
                    map.put("vst_usacd_addtime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, ToolsNumber.parseLong(map.get("vst_usacd_addtime")+"")));
                    map.put("vst_usacd_uptime", ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss, ToolsNumber.parseLong(map.get("vst_usacd_uptime")+"")));
                }

                bean.setTotalSize(count); //设置总行数
                bean.setSingleSize(cutPage.get_singleCount());//设置单页显示行数
                bean.setMapData(list);
            }
        }catch(Exception e){
            logger.error("查询二次活跃渠道用户分页数据失败: " + SystemException.getExceptionInfo(e));
            throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
        }
        return bean;
    }

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
                if(!ToolsString.isEmpty(params.get("vst_usacd_channel")+"")){
                    VstTools.paramFormat("vst_usacd_channel", params.get("vst_usacd_channel")+"", params);
                }
            }

            List<Map<String, Object>> data = _innerUserSecondaryActiveChannelDiffDao.queryExport(params);
            if(data != null && data.size() > 0){

                Map<String, Object> temp = new HashMap<String, Object>(3);
                // 获取包名
                temp.put("vst_table_name", "vst_sys");
                temp.put("vst_column_name", "pkgName");
                temp.put("vst_state", VstConstants.STATE_AVALIABLE);
                Map<String, String> pkgMap = _baseService.getDictionaryForMap(temp);

                for(Map<String, Object> map : data){
                    // 更改日期格式
                    String week = ToolsDate.getWeekByDate(map.get("日期")+"", ToolsDate.yyyy_MM_dd4);
                    if("周日".equals(week) || "周六".equals(week)){
                        map.put("日期", map.get("日期") + " " +week);
                    }
                    if(pkgMap.containsKey(map.get("包名")+"")){
                        map.put("包名", pkgMap.get(map.get("包名")+""));
                    }
                    // 计算新二次活跃用户
                    String vst_usacd_modulus = ToolsString.checkEmpty(map.get("调整系数"));
                    if("-1".equals(vst_usacd_modulus)) {
                        map.put("新二次活跃用户", map.get("二次活跃用户"));
                    } else {
                        double modulus = ToolsNumber.parseDouble(vst_usacd_modulus);
                        long vst_usacd_uv = ToolsNumber.parseLong(map.get("当日新增用户")+"");
                        long vst_usacd_amount = ToolsNumber.parseLong(map.get("二次活跃用户")+"");
                        long vst_usacd_umeng_uv = ToolsNumber.parseLong(map.get("友盟新增用户")+"");
                        map.put("新二次活跃用户", Math.round(vst_usacd_umeng_uv * vst_usacd_amount / vst_usacd_uv * modulus));
                    }
                }
            }

            bean.setMapData(data);
            bean.setTitle("vst_user_secondary_active_channel_data");

            // 写操作日志
            StringBuilder sb = new StringBuilder();
            sb.append("导出数据，查询条件：").append(params);
            _baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_EXPORT, sb.toString()));
        }catch(Exception e){
            logger.error("导出二次活跃渠道用户数据失败: " + SystemException.getExceptionInfo(e));
            throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
        }
        return bean;
    }

    /**
     * 查询报表数据(按日期统计)
     */
    @Override
    public ReportBean getReportByDate(Map<String, Object> params) throws SystemException {
        ReportBean bean = new ReportBean();
        try{
            if(params == null){
                params = new HashMap<String, Object>();
            }else{
                params.put("startDay", VstTools.parseInt(params.get("startDay")+""));
                params.put("endDay", VstTools.parseInt(params.get("endDay")+""));
                // 参数特殊化处理
                if(!ToolsString.isEmpty(params.get("vst_usacd_channel")+"")){
                    VstTools.paramFormat("vst_usacd_channel", params.get("vst_usacd_channel")+"", params);
                }
            }

            List<Map<String, Object>> data = _innerUserSecondaryActiveChannelDiffDao.queryReportByDate(params);
            bean.setMapData(data);
        }catch(Exception e){
            logger.error("查询二次活跃渠道用户(按日期统计)失败: " + SystemException.getExceptionInfo(e));
            throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
        }
        return bean;
    }

    /**
     * 批量修改调整系数
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SystemException.class)
    public int modifyModulus(String ids, String modulus, UserSession user) {
        int result = 0;
        try {
            Map<String, Object> params = new HashMap<String, Object>(4);
            params.put("list_ids", Arrays.asList(ids.split(",")));
            params.put("vst_usacd_modulus", modulus);
            params.put("vst_usacd_uptime", System.currentTimeMillis());
            params.put("vst_usacd_updator", user.getLoginInfo().getLoginName());
            result = _innerUserSecondaryActiveChannelDiffDao.modifyState(params);

            if(result > 0){
                // 写操作日志
                StringBuilder sb = new StringBuilder();
                sb.append("批量修改调整系数，记录id=").append(ids).append(",系数值=").append(modulus);
                _baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_UPDATE, sb.toString()));
            }
        } catch (Exception e) {
            logger.error("批量修改调整系数失败: " + SystemException.getExceptionInfo(e));
            throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
        }
        return result;
    }

    /**
     * 删除数据
     */
    @Override
    public int deleteData(Map<String, Object> params, UserSession user) throws SystemException {
        int result = 0;
        try {
            if(params != null){
                params.put("startDay", VstTools.parseInt(params.get("startDay")+""));
                params.put("endDay", VstTools.parseInt(params.get("endDay")+""));
                // 参数特殊化处理
                if(!ToolsString.isEmpty(params.get("vst_usacd_channel")+"")){
                    VstTools.paramFormat("vst_usacd_channel", params.get("vst_usacd_channel")+"", params);
                }

                result = _innerUserSecondaryActiveChannelDiffDao.delete(params);
                if(result > 0){
                    // 写操作日志
                    StringBuilder sb = new StringBuilder();
                    sb.append("删除二次活跃渠道用户, 条件=").append(params);
                    _baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_DEL, sb.toString()));
                }
            }
        } catch (Exception e) {
            logger.error("删除二次活跃渠道用户失败: " + SystemException.getExceptionInfo(e));
            throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
        }
        return result;
    }

    /**
     * 修改数据
     */
    @Override
    public int updateData(Map<String, Object> params, UserSession user) throws SystemException {
        int result = 0;
        try {
            if(params != null){
                VstUserSecondaryActiveChannelDiff bean = new VstUserSecondaryActiveChannelDiff();
                bean.setVst_usacd_id(ToolsString.checkEmpty(params.get("vst_usacd_id")));
                bean.setVst_usacd_amount(ToolsNumber.parseLong(params.get("vst_usacd_amount")+""));
                bean.setVst_usacd_modulus(ToolsString.checkEmpty(params.get("vst_usacd_modulus")));
                bean.setVst_usacd_uptime(System.currentTimeMillis());
                bean.setVst_usacd_updator(user.getLoginInfo().getLoginName());

                result = _innerUserSecondaryActiveChannelDiffDao.update(bean);
                if(result > 0){
                    // 写操作日志
                    StringBuilder sb = new StringBuilder();
                    sb.append("修改二次活跃渠道用户, 参数=").append(params);
                    _baseService.saveLog(user, new VstSysOperateLog(user.getCurrModuleId(), VstConstants.OPERATE_TYPE_UPDATE, sb.toString()));
                }
            }
        } catch (Exception e) {
            logger.error("修改二次活跃渠道用户失败: " + SystemException.getExceptionInfo(e));
            throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
        }
        return result;
    }
}
