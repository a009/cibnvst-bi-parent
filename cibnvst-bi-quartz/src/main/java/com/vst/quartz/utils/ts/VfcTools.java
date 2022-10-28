package com.vst.quartz.utils.ts;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.pojo.VstQuartzConfig;
import com.vst.common.tools.string.ToolsString;
import com.vst.quartz.constant.ApiCode;
import com.vst.quartz.enumerate.QuartzEnum;

/**
 * @author kai
 * 该工具类用于处理service层的一些参数验证
 * TODO: 2018/4/27
 */
public class VfcTools {


    /**
     * LiuKai
     * 判断配置对象里面的参数是否为空
     * @param config 任务配置对象
     * @return 返回值Json
     */
    public static JSONObject getIsFlag(VstQuartzConfig config){
        JSONObject object=new JSONObject();
        //判断信息是否输入完整
        if (ToolsString.isEmpty(config.getVst_qc_name())){
            object.put("code", ApiCode.API_CODE_119);
            object.put("msg","请输入任务名称");
            return object;
        }
        if (ToolsString.isEmpty(config.getVst_qc_trigger_name())){
            object.put("code",ApiCode.API_CODE_119);
            object.put("msg","请输入触发器名称");
            return object;
        }
        if (ToolsString.isEmpty(config.getVst_qc_source_name())){
            object.put("code",ApiCode.API_CODE_119);
            object.put("msg","请输入源数据源");
            return object;
        }
        if (ToolsString.isEmpty(config.getVst_qc_table())){
            object.put("code",ApiCode.API_CODE_119);
            object.put("msg","请输入数据表名称");
            return object;
        }
        if (ToolsString.isEmpty(config.getVst_qc_job_type())){
            object.put("code",ApiCode.API_CODE_119);
            object.put("msg","请输入任务类型");
            return object;
        }
        //如果选择了备份则需要填写实体类名
        int before = config.getVst_qc_deal_before();
        if (before == QuartzEnum.VST_STATUS.getTreeStatus()) {
            //如果选择了备份表 1.只备份表 2,只备份文件,3两者都备份'
            if (config.getVst_qc_backup().equals(QuartzEnum.VST_BACKUP.getOneStatus())) {
                if (ToolsString.isEmpty(config.getVst_qc_target_source_name())) {
                    object.put("code", ApiCode.API_CODE_119);
                    object.put("msg", "请输入目标数据源");
                    return object;
                }
            } else if (config.getVst_qc_backup().equals(QuartzEnum.VST_BACKUP.getTowStatus())) {
                if (ToolsString.isEmpty(config.getVst_qc_file_format())) {
                    object.put("code", ApiCode.API_CODE_119);
                    object.put("msg", "请选择文件备份的格式");
                    return object;
                }
            } else if (config.getVst_qc_backup().equals(QuartzEnum.VST_BACKUP.getTreeStatus())) {
                if (ToolsString.isEmpty(config.getVst_qc_target_source_name())
                        || ToolsString.isEmpty(config.getVst_qc_file_format())) {
                    object.put("code", ApiCode.API_CODE_119);
                    object.put("msg", "请选择目标数据源和文件格式");
                    return object;
                }

            }


            if (config.getVst_qc_backup_type().equals(QuartzEnum.VST_BACKUP_TYPE.getOneStatus())){
                if (ToolsString.isEmpty(config.getVst_qc_backup_condition())){
                    object.put("code",ApiCode.API_CODE_119);
                    object.put("msg","请输入字段备份时所需要的字段");
                    return object;
                }
            }

        }
        object.put("code",ApiCode.API_CODE_200);
        object.put("msg","验证通过");
        return object;


    }

}
