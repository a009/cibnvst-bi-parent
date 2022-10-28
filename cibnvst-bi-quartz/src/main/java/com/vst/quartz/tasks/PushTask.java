package com.vst.quartz.tasks;

import com.vst.quartz.tasks.schedult.AbstractTask;
import org.quartz.JobExecutionContext;

/**
 * @author kai 
 * 任务启动时需要执行的类
 * TODO: 2018/1/23
 */
public class PushTask extends AbstractTask {
    @Override
    protected void executeTask(JobExecutionContext jobExecutionContext) {
        //同步新增渠道用户统计
        pushChannelService.pushChannelVstUserAdd(0,0);
        //同步季质量用户统计
        pushChannelService.pushChannelVstUserLevel(0,0);


        //修改六个小时以前为审核的数据
        pushChannelService.updateChannelVstUserAdd();

        pushChannelService.updateChannelVstUserLevel();
    }
}
