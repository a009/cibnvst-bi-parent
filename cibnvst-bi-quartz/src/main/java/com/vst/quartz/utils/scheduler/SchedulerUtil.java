package com.vst.quartz.utils.scheduler;

import com.vst.quartz.constant.Constants;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;

/**
 * @author kai
 * 定时任务工具类
 * TODO: 2018/4/23 18：56：52
 */
public class SchedulerUtil {


	/**
	 * @author LiuKai
	 * time 2017-1-5 11:34:22
	 * Description: 添加一个定时任务
	 * @param scheduler 调度器
	 * @param jobName 任务名
	 * @param triggerName 触发器名
	 * @param jobClass 任务(你需要调用的类)
	 * @param time 时间设置
	 */
	@SuppressWarnings("unchecked")
	public static void addJob(Scheduler scheduler,String jobName,String triggerName
	                        ,Class jobClass,String time){
		try {
			//任务执行类
			JobKey jobKey=new JobKey(jobName, Constants.JOB_GROUP_NAME);

			JobDetail jobDetail= JobBuilder.newJob(jobClass).withIdentity(jobKey).build();

			//定义触发器
			CronTriggerImpl cronTrigger=new CronTriggerImpl();
			//给触发器创建触发器名和触发器组
			cronTrigger.setName(triggerName);
			cronTrigger.setGroup(Constants.TRIGGER_GROUP_NAME);
			//设置触发器时间
			cronTrigger.setCronExpression(time);

			//将他们添加到调度器中
			scheduler.scheduleJob(jobDetail,cronTrigger);

			//启动调度器
			if (!scheduler.isShutdown()){
				scheduler.start();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @author LiuKai
	 * time 2017-1-5 15：26：32
	 * descrition 修改一个定时任务的时间
	 * @param scheduler 调度器
	 * @param triggerName 触发器名
	 * @param time 时间
	 */
	public static void modifyJobTime(Scheduler scheduler, String triggerName, String time){
		try {
			TriggerKey triggerKey=new TriggerKey(triggerName, Constants.TRIGGER_GROUP_NAME);
			CronTrigger cronTrigger=(CronTrigger)scheduler.getTrigger(triggerKey);
			if (cronTrigger == null){
				return;
			}
			//获取调度器中定义的时间
			String oldTime=cronTrigger.getCronExpression();

			//判断调度器中的时间与更新的时间是否相等

			if (!oldTime.equalsIgnoreCase(time)){
				//创建表达式构建器
				CronScheduleBuilder scheduleBuilder=CronScheduleBuilder.cronSchedule(time);

				//根据新的时间构建调度器
				cronTrigger=cronTrigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

				//重新设置
				scheduler.rescheduleJob(triggerKey,cronTrigger);

			}

		}catch (SchedulerException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 执行任务
	 * @param scheduler 调度器
	 * @param jobName 任务名
	 * @throws SchedulerException
	 */
	public static void triggerJob(Scheduler scheduler, String jobName) throws SchedulerException{
		JobKey jobKey = JobKey.jobKey(jobName, Constants.JOB_GROUP_NAME);
		scheduler.triggerJob(jobKey);
	}
	
	/**
	 * 恢复任务
	 * @param scheduler 调度器
	 * @param jobName 任务名
	 * @throws SchedulerException
	 */
	public static void resumeJob(Scheduler scheduler, String jobName) throws SchedulerException{
		JobKey jobKey = JobKey.jobKey(jobName, Constants.JOB_GROUP_NAME);
		scheduler.resumeJob(jobKey);
	}
	
	/**
	 * 删除任务
	 * @param scheduler 调度器
	 * @param jobName 任务名
	 * @throws SchedulerException
	 */
	public static void deleteJob(Scheduler scheduler, String jobName) throws SchedulerException{
		JobKey jobKey = JobKey.jobKey(jobName, Constants.JOB_GROUP_NAME);
		scheduler.deleteJob(jobKey);

	}
	
	/**
	 * 暂停任务
	 * @param scheduler 调度器
	 * @param jobName 任务名
	 * @throws SchedulerException
	 */
	public static void pauseJob(Scheduler scheduler, String jobName) throws SchedulerException{
		JobKey jobKey = JobKey.jobKey(jobName, Constants.JOB_GROUP_NAME);
		scheduler.pauseJob(jobKey);
	}
}
