package com.vst.quartz.tasks.schedult;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.pojo.VstQuartzConfig;
import com.vst.quartz.constant.ApiCode;
import com.vst.quartz.constant.ApiMagic;
import com.vst.quartz.utils.scheduler.SchedulerUtil;
import com.vst.quartz.config.dynamic.DataSourceHolder;
import com.vst.quartz.config.dynamic.DynamicDataSource;
import com.vst.quartz.enumerate.QuartzEnum;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author kai 
 * 处理数据源层
 * TODO: 2018/3/15 10：48：29
 */
public abstract class AbstractTableMulti extends AbstractTask {

    @Override
    protected void executeTask(JobExecutionContext jobExecutionContext) {
        switchDBSite(jobExecutionContext);
    }


    /**
     * 当存在多数据源的时候需要进行数据源切换
     * @param context 任务JobExecutionContext对象
     */
    private void switchDBSite(JobExecutionContext context){

        String[] dbSite;

        //获取jobName任务名
        String jobName=context.getJobDetail().getKey().getName();
        //切换成默认bi数据源查询任务配置
        DataSourceHolder.setDataSourceKey(DynamicDataSource.DATA_SOURCE_BI);
        //查询任务配置
        VstQuartzConfig config=quartzService.queryByName(jobName);
        if (config == null){
            System.out.println("配置不存在");
            return;
        }
        //获取数据的源数据源
        String source = config.getVst_qc_source_name();
        //判断是否有多条数据源
        if (source.contains(ApiMagic.API_MAGIC_COMMA)){
            dbSite = source.split(ApiMagic.API_MAGIC_COMMA);
        }else {
            dbSite = new String[]{source};
        }

        //遍历数据源
        for (String db: dbSite) {
            noTableMulti(config,db);
        }


        //获取任务类型，判断是临时任务还是定时任务，如果是临时任务，则暂停
        int type = config.getVst_qc_type();
        if (type == QuartzEnum.VST_QC_TYPE.getOneStatus()){
            try {
                //暂停任务
                SchedulerUtil.pauseJob(new StdSchedulerFactory().getScheduler(),config.getVst_qc_name());

                //修改数据库状态
                config.setVst_qc_state(2);

                DataSourceHolder.setDataSourceKey(DynamicDataSource.DATA_SOURCE_BI);
                JSONObject jsonObject = quartzService.updateQuartzConfig(config);
                if (jsonObject.getIntValue(ApiMagic.API_MAGIC_CODE) == ApiCode.API_CODE_200){
                    System.out.println("暂停成功");
                }else {
                    System.out.println("失败");
                }


            }catch (SchedulerException e){
                e.printStackTrace();
            }

        }
    }

    /**
     * kai
     * 抽象方法
     * TODO: 2018/4/24
     * @param config 任务配置对象
     * @param dbSite 数据源
     */
    protected abstract void noTableMulti(VstQuartzConfig config, String dbSite);
}
