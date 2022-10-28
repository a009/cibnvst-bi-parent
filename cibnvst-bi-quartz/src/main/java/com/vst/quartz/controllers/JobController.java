package com.vst.quartz.controllers;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.pojo.VstQuartzConfig;
import com.vst.quartz.constant.ApiCode;
import com.vst.quartz.utils.scheduler.SchedulerUtil;
import com.vst.quartz.enumerate.QuartzEnum;
import com.vst.quartz.service.quartz.QuartzService;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author kai 
 * 任务启动关闭按钮控制层
 * TODO: 2018/4/23
 */
@Controller
@RequestMapping("/job")
public class JobController {

    private QuartzService quartzService;

    /**
     * kai
     * 使用构造注入而不是用filed注入和setter注入的原因，请查看Spring官网
     * @param quartzService 任务配置业务层
     */
    @Autowired
    public JobController(QuartzService quartzService) {
        this.quartzService = quartzService;
    }



    /**
     * @author LiuKai
     * 更新触发时间
     * @param quartzId 任务配置id
     * @param time 需要修改的时间 cron格式
     * @return 返回值
     */
    @RequestMapping("/modify")
    @ResponseBody
    public JSONObject modifyJobTime(@RequestParam(value = "quartzId") String quartzId, String time){
        JSONObject object=new JSONObject();

        VstQuartzConfig config =quartzService.queryByIdQuartz(quartzId);
        if (config == null){
            object.put("code", ApiCode.API_CODE_119);
            object.put("msg","任务配置没有找到");
            return object;
        }

        //获取触发器名
        String triggerName = config.getVst_qc_trigger_name();

        try {
            //修改任务时间
            SchedulerUtil.modifyJobTime(new StdSchedulerFactory().getScheduler(),triggerName,time);

            //获取配置状态
            int status = config.getVst_qc_state();
            //判断状态时启用还是禁用 1.启动 2,暂停
            if (status == QuartzEnum.VST_STATUS.getTreeStatus()){
                SchedulerUtil.pauseJob(new StdSchedulerFactory().getScheduler(),config.getVst_qc_name());
            }

            //修改任务中的配置时间
            config.setVst_qc_job_cron(time);

            JSONObject jsonObject = quartzService.updateQuartzConfig(config);
            String code = "code";
            if (jsonObject.getIntValue(code) == ApiCode.API_CODE_200){
                object.put("code",ApiCode.API_CODE_200);
                object.put("msg","任务时间修改成功");
            }else {
                object.put("code",ApiCode.API_CODE_202);
                object.put("msg","任务时间修改失败");
            }
        }catch (SchedulerException e){
            object.put("code",ApiCode.API_CODE_202);
            object.put("msg","修改任务时间时出现异常。。。");
            e.printStackTrace();
        }
        return object;
    }


    /**
     * 恢复任务
     * @param quartzId 任务配置id
     * @return 返回值 json
     */
    @RequestMapping("/resume")
    @ResponseBody
    public JSONObject resumeJob(@RequestParam(value = "quartzId") String quartzId){
        JSONObject object=new JSONObject();
        VstQuartzConfig config =quartzService.queryByIdQuartz(quartzId);
        if (config == null){
            object.put("code", ApiCode.API_CODE_119);
            object.put("msg","任务配置没有找到");
            return object;
        }

        //获取配置名称
        String jobName = config.getVst_qc_name();

        try {
            //恢复任务
            SchedulerUtil.resumeJob(new StdSchedulerFactory().getScheduler(),jobName);

            //修改任务配置表中的任务，修改成启动
            config.setVst_qc_state(1);


            JSONObject jsonObject = quartzService.updateQuartzConfig(config);
            String code = "code";
            if (jsonObject.getIntValue(code) == ApiCode.API_CODE_200){
                object.put("code",ApiCode.API_CODE_200);
                object.put("msg","任务恢复成功");
            }else {
                object.put("code",ApiCode.API_CODE_202);
                object.put("msg","任务恢复失败");
            }
        }catch (SchedulerException e){
            object.put("code",ApiCode.API_CODE_202);
            object.put("msg","任务恢复失败是异常错误");
            e.printStackTrace();
        }
        return object;
    }

    /**
     * @author LiuKai
     * 删除任务
     * @param quartzId 任务配置id
     * @return 返回值json
     */
    @RequestMapping("/delete")
    @ResponseBody
    public JSONObject deleteJob(@RequestParam(value = "quartzId") String quartzId){
        JSONObject object=new JSONObject();
        VstQuartzConfig config =quartzService.queryByIdQuartz(quartzId);
        if (config == null){
            object.put("code", ApiCode.API_CODE_119);
            object.put("msg","任务配置没有找到");
            return object;
        }

        //获取配置名称
        String jobName = config.getVst_qc_name();


        try {
            //删除任务
            SchedulerUtil.deleteJob(new StdSchedulerFactory().getScheduler(),jobName);

            //删除任务之后，需要删除任务配置信息


            object.put("code",ApiCode.API_CODE_200);
            object.put("msg","任务删除成功");
        }catch (SchedulerException e){
            object.put("code",ApiCode.API_CODE_202);
            object.put("msg","任务删除失败");
            e.printStackTrace();
        }
        return object;
    }

    /**
     * @author LiuKai
     * 暂停任务
     * @param quartzId 任务名
     * @return 返回值JsonObject
     */
    @RequestMapping("/pause")
    @ResponseBody
    public JSONObject pauseJob(@RequestParam(value = "quartzId") String quartzId){
        JSONObject object=new JSONObject();

        VstQuartzConfig config =quartzService.queryByIdQuartz(quartzId);


        if (config == null){
            object.put("code", ApiCode.API_CODE_119);
            object.put("msg","任务配置没有找到");
            return object;
        }

        //获取配置名称
        String jobName = config.getVst_qc_name();

        try {
            //暂停任务
            SchedulerUtil.pauseJob(new StdSchedulerFactory().getScheduler(),jobName);


            //修改任务配置表中任务状态
            config.setVst_qc_state(2);

            JSONObject jsonObject = quartzService.updateQuartzConfig(config);
            String code = "code";
            if (jsonObject.getIntValue(code) == ApiCode.API_CODE_200){
                object.put("code",ApiCode.API_CODE_200);
                object.put("msg","任务暂停成功");
            }else {
                object.put("code",ApiCode.API_CODE_202);
                object.put("msg","任务暂停失败");
            }

        }catch (SchedulerException e){
            object.put("code",ApiCode.API_CODE_110);
            object.put("msg","系统异常错误");
            e.printStackTrace();
        }
        return object;
    }

}
