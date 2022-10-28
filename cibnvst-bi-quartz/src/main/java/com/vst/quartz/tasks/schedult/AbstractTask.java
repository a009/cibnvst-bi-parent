package com.vst.quartz.tasks.schedult;

import com.vst.quartz.utils.bean.SpringContextUtil;
import com.vst.quartz.service.clean.BackUpService;
import com.vst.quartz.service.clean.CleanTablesService;
import com.vst.quartz.service.PushChannelService;
import com.vst.quartz.service.quartz.QuartzService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author kai 
 * 任务启动类
 * TODO: 2018/4/23
 */
public abstract class AbstractTask implements Job {

    protected CleanTablesService cleanTablesService;

    protected PushChannelService pushChannelService;

    protected BackUpService backUpService;

    protected QuartzService quartzService;

    {
        cleanTablesService=SpringContextUtil.getBean(CleanTablesService.class);
        backUpService=SpringContextUtil.getBean(BackUpService.class);
        pushChannelService=SpringContextUtil.getBean(PushChannelService.class);
        quartzService = SpringContextUtil.getBean(QuartzService.class);
    }


    /**
     * kai
     * 任务启动类
     * TODO: 2018/4/24
     * @param jobExecutionContext 任务对象
     * @throws JobExecutionException 异常抛出
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        executeTask(jobExecutionContext);
    }


    /**
     * kai
     * 抽象方法定义，用于任务启动时调用
     * TODO: 2018/4/24
     * @param jobExecutionContext 任务对象
     *
     */
    protected abstract void executeTask(JobExecutionContext jobExecutionContext);
}
