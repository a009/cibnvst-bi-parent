package com.vst.quartz.tasks;

import com.vst.common.pojo.VstQuartzConfig;
import com.vst.quartz.utils.page.PageBean;
import com.vst.quartz.utils.scheduler.SchedulerUtil;
import com.vst.quartz.config.dynamic.DataSourceHolder;
import com.vst.quartz.enumerate.QuartzEnum;
import com.vst.quartz.tasks.schedult.AbstractTablePageTask;
import com.vst.quartz.utils.mail.EmailModule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author kai 
 * 任务启动类
 * TODO: 2018/4/3 17：25：57
 */
public class SchedulerTask extends AbstractTablePageTask {

    private Log logger= LogFactory.getLog(SchedulerTask.class);


    /**
     * kai
     * 任务执行时会调用的方法
     * @param config 任务配置对象
     * @param dbSite 源数据源
     * @param bean 分页对象
     */
     @Override
     protected void exePageTask(VstQuartzConfig config, String dbSite, PageBean bean) {
         executeService(config,dbSite,bean);
     }


    /**
     * kai
     * TODO: 2018/3/15 11：00：14
     * 任务业务处理层
     * @param config 任务配置对象
     * @param db 源数据源
     * @param bean 分页对象
     */
    private void executeService(VstQuartzConfig config,String db,PageBean bean){

         if (config != null){

             logger.info("输出任务名："+config.getVst_qc_name()+">>>>>>>>>>>>>>>>>");

             //如果是1，则执行清除操作,2执行推送，3执行拉取
             int jobType = config.getVst_qc_job_type();
             if (jobType == QuartzEnum.VST_JOB_TYPE.getOneStatus()){

                 //切换数据源
                 DataSourceHolder.setDataSourceKey(db);

                 //*********************************第一阶段**********************************//

                 //flag主要用于标识第一阶段是否执行禁用，或者备份操作，如果执行了，则在本类不需要执行清除，在禁用和备份中清除，以达到
                 //事物一致性
                 boolean flag = false;
                 //判断执行任务第一阶段需要干什么
                 //判断是否是禁用 1,禁用，2，备份
                 int before = config.getVst_qc_deal_before();
                 if (before == QuartzEnum.VST_JOB_BEFORE.getTowStatus()){
                     flag =true;
                     bean.setCurrentPage(bean.getCurrentPage()+1);

                 }else if (before == QuartzEnum.VST_JOB_BEFORE.getTreeStatus()){
                     flag = true;
                     //如果备份的话，判断是那种备份类型
                     //如果类型为1表示按字段来备份 2:全局备份，3按照清除条件备份
                     int backType = config.getVst_qc_backup_type();
                     if (backType == QuartzEnum.VST_BACKUP_TYPE.getOneStatus()){
                         backUpService.backUpField(config,bean,db);
                     }else if (backType == QuartzEnum.VST_BACKUP_TYPE.getTowStatus()){
                         backUpService.backUpAll(config,bean,db);
                     }else if (backType == QuartzEnum.VST_BACKUP_TYPE.getTreeStatus()){
                         backUpService.backUpCondition(config,bean,db);
                     }else {
                         logger.info("暂无找到备份信息");

                         try {
                             //暂停任务
                             SchedulerUtil.pauseJob(new StdSchedulerFactory().getScheduler(),config.getVst_qc_name());
                         }catch (Exception e){
                             e.printStackTrace();
                         }

                         //发送邮件提醒
                         //获取当前方法
                         String method = Thread.currentThread() .getStackTrace()[1].getMethodName();

                         String content = "第一阶段选择了备份，却没有找到备份类型,任务："+config.getVst_qc_name()+"备份类型编号:"+
                                 config.getVst_qc_backup_type();
                         //发送邮件，该模版中引用了发送邮件的方法，因此这歌地方不需要写
                         EmailModule.cretePauseJobModule("警告。。。未找到备份类型","LiuKai",method,content,config);

                         return;
                     }
                 }else {
                     bean.setCurrentPage(bean.getCurrentPage()+1);
                 }



                 //*********************************第二阶段**********************************//
                 //判断任务执行第二阶段需要干什么
                 //判断是否删除原数据 如果flag为false则表示在之前没有进行过备份和禁用
                 if (!flag) {
                     int after = config.getVst_qc_deal_after();
                     //判断第二阶段是否执行清理操作。0，不处理，1，清理
                     if (after == QuartzEnum.VST_JOB_AFTER.getTowStatus()) {
                         DataSourceHolder.setDataSourceKey(db);
                         int count = cleanTablesService.clearTables(config.getVst_qc_table(), config.getVst_qc_condition());
                         if (count > 0) {
                             logger.info("清除数据:" + count + "条成功。。。");
                         }else {
                             logger.info("清除数据:" + count + "条。。。");
                         }
                     }
                 }

             }
             //如果是push，则执行推送操作
             else if (jobType == QuartzEnum.VST_JOB_TYPE.getTowStatus()){
                 logger.info("推送操作暂无开发");
                 bean.setCurrentPage(bean.getCurrentPage()+1);
             }
             //如果是pull，则执行拉取操作
             else if (jobType == QuartzEnum.VST_JOB_TYPE.getTreeStatus()){
                 logger.info("拉取操作暂无开发");
                 bean.setCurrentPage(bean.getCurrentPage()+1);
             }
             else {
                 //发送邮件提醒
                 //获取当前方法
                 String method = Thread.currentThread() .getStackTrace()[1].getMethodName();

                 String content = "你当前任务未找到任务类型,任务名："+config.getVst_qc_name()
                         +"任务类型编号:"+config.getVst_qc_type();
                 //发送邮件，该模版中引用了发送邮件的方法，因此这个地方不需要写
                 EmailModule.cretePauseJobModule("警告。。。未找到任务类型","LiuKai",method,content,config);
                 bean.setCurrentPage(bean.getCurrentPage()+1);
             }
         }
     }

}
