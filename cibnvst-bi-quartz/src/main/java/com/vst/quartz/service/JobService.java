package com.vst.quartz.service;

import com.alibaba.fastjson.JSONObject;
import com.vst.quartz.utils.scheduler.SchedulerUtil;
import com.vst.quartz.dao.quartz.QuartzMapper;
import com.vst.quartz.enumerate.QuartzEnum;
import com.vst.quartz.tasks.PushChannelLevel;
import com.vst.quartz.tasks.PushTask;
import com.vst.quartz.tasks.SchedulerTask;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * 开启定时任务
 * @author kai
 */
@Service
public class JobService {

    private Log logger= LogFactory.getLog(JobService.class);

    /**
     * 定时触发时间: 每一个小时执行一次
     */
    private final static String CRON_EXPRESSION = "0 0 0/1 * * ?";

    @Autowired
    QuartzMapper quartzMapper;

    @PostConstruct
    public void init(){
        logger.info("start:"+System.currentTimeMillis());
        try {
            Scheduler scheduler=new StdSchedulerFactory().getScheduler();
            //清理积分流水Job
            JobDetail clearDBPaymentJob = JobBuilder.newJob(PushTask.class).withIdentity("jobUser", "jobUserGroup").build();
            //触发器
            CronTriggerImpl clearDBPaymentTrigger = new CronTriggerImpl();
            clearDBPaymentTrigger.setName("triggerUser");
            clearDBPaymentTrigger.setCronExpression(CRON_EXPRESSION);

            //渠道统计
            JobDetail pushChannelLevelJob = JobBuilder.newJob(PushChannelLevel.class).withIdentity("jobLevel", "jobLevelGroup").build();
            //触发器
            CronTriggerImpl pushChannelLevelTrigger = new CronTriggerImpl();
            pushChannelLevelTrigger.setName("triggerLevel");
            pushChannelLevelTrigger.setCronExpression("0 0 5 * * ? *");



            scheduler.scheduleJob(clearDBPaymentJob,clearDBPaymentTrigger);
            scheduler.scheduleJob(pushChannelLevelJob,pushChannelLevelTrigger);
            if (!scheduler.isShutdown()){
                scheduler.start();
            }


//            //遍历配置表，项目启动时，根据配置数据启动或者禁用数据
            List<Map<String,Object>> configs = quartzMapper.queryQuartzConfig(null);
            //判断是否存在数据
            if (configs != null && configs.size() > 0){
                logger.info("配置个数"+configs.size());

                for (Map<String,Object> map: configs){

                    JSONObject object = new JSONObject(map);
                    logger.info("获取数据:"+object);
                    //获取任务状态
                    int status =object.getInteger("vst_qc_state");
                    //获取任务名
                    String jobName = object.getString("vst_qc_name");
                    //获取触发器名
                    String triggerName = object.getString("vst_qc_trigger_name");
                    //获取启动时间
                    String cronTime = object.getString("vst_qc_job_cron");

                    //如果状态为1，表示启动
                    if (status == QuartzEnum.VST_STATUS.getTowStatus()){

                        logger.info("开始启动任务，任务名："+jobName);

                        //添加任务
                        SchedulerUtil.addJob(scheduler,jobName,triggerName, SchedulerTask.class,cronTime);


                    }
                    //如果状态为2表示禁用
                    else if (status == QuartzEnum.VST_STATUS.getTreeStatus()){
                        logger.info("该任务是暂停任务，任务名："+jobName);

                        //首先添加任务，然后禁用任务
                        SchedulerUtil.addJob(scheduler,jobName,triggerName,SchedulerTask.class,cronTime);
                        //禁用任务
                        SchedulerUtil.pauseJob(scheduler,jobName);
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
