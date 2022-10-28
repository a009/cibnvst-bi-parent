package com.vst.quartz.tasks;

import com.vst.quartz.tasks.schedult.AbstractTask;
import org.quartz.JobExecutionContext;

/**
 * @author kai
 * 额外任务执行层
 */
public class PushChannelLevel extends AbstractTask {

    @Override
    protected void executeTask(JobExecutionContext jobExecutionContext) {
        pushChannelService.pushVstChannelLevel(0,0);
    }
}
