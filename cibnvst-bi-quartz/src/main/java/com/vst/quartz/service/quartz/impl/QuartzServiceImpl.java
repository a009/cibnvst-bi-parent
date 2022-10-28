package com.vst.quartz.service.quartz.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.vst.common.pojo.VstQuartzConfig;
import com.vst.common.tools.number.ToolsRandom;
import com.vst.common.tools.string.ToolsString;
import com.vst.quartz.constant.ApiCode;
import com.vst.quartz.constant.ApiMagic;
import com.vst.quartz.dao.quartz.QuartzMapper;
import com.vst.quartz.enumerate.QuartzEnum;
import com.vst.quartz.service.quartz.QuartzService;
import com.vst.quartz.tasks.SchedulerTask;
import com.vst.quartz.utils.page.PageBean;
import com.vst.quartz.utils.scheduler.SchedulerUtil;
import com.vst.quartz.utils.table.TableTools;
import com.vst.quartz.utils.ts.VfcTools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kai
 * 任务配置业务逻辑层
 * TODO: 2018/3/16 10：41：53
 */
@Service
public class QuartzServiceImpl implements QuartzService {

    private Log logger = LogFactory.getLog(QuartzServiceImpl.class);

    private QuartzMapper quartzMapper;

    @Autowired
    public QuartzServiceImpl(QuartzMapper quartzMapper) {
        this.quartzMapper = quartzMapper;
    }

    /**
     * @author LiuKai
     * 查询全部的配置信息
     * @return 返回值 List
     */
    @Override
    public JSONObject queryQuartzConfig(String jobName, PageBean bean) {
        JSONObject object =new JSONObject();
        PageHelper.startPage(bean.getCurrentPage(),bean.getTotalCount());
        if (ToolsString.isEmpty(jobName)){
            jobName=null;
        }
        Map<String,Object> map = new HashMap<>(10);
        map.put("jobName",jobName);

        Page<Map<String, Object>> mapPage = (Page<Map<String, Object>>)quartzMapper.queryQuartzConfig(map);
        List<Map<String,Object>> maps =mapPage.getResult();

        if (maps.size()>0){
            object.put("code",0);
            object.put("data",maps);
            object.put("count",mapPage.getTotal());
            object.put("msg","");
            object.put("time", ApiCode.API_CODE_TIME);
        }else {
            object.put("code",0);
            object.put("msg","暂无数据");
        }

        return object;
    }

    /**
     * @author LiuKai
     * @param jobName 任务名
     * @return 返回值 任务对象
     */
    @Override
    public VstQuartzConfig queryByName(String jobName) {
        return quartzMapper.queryByName(jobName);
    }

    /**
     * kai
     * 根据主键查询任务配置
     * TODO: 2018/3/29 10：55：50
     * @param quartzId 任务配置id
     * @return 返回值VstQuartzConfig
     */
    @Override
    public VstQuartzConfig queryByIdQuartz(String quartzId) {
        return quartzMapper.queryByIdQuartz(quartzId);
    }

    /**
     * @author LiuKai
     * 保存配置表信息
     * @param config 配置表对象
     * @return 返回值 Json
     */
    @Override
    public JSONObject batchQuartzConfig(VstQuartzConfig config) {
        JSONObject object=new JSONObject();
        try {
            //判断参数是否为空
            object= VfcTools.getIsFlag(config);
            //如果code为119表示有参数为空，直接返回
            if (object.getIntValue(ApiMagic.API_MAGIC_CODE) == ApiCode.API_CODE_119){
                return object;
            }
            object=new JSONObject();

            //验证任务名是否存在
            VstQuartzConfig vstQuartzConfig = quartzMapper.queryByName(config.getVst_qc_name());
            if (vstQuartzConfig != null){
                object.put("code",ApiCode.API_CODE_202);
                object.put("msg","任务名已经存在");
                object.put("date",ApiCode.API_CODE_TIME);
                return object;
            }

            //验证数据源中是否存在table
            JSONObject result = vfcSource(config.getVst_qc_source_name(),config.getVst_qc_table());

            //获取验证通过的数据
            String sourceName = result.getString("add");

            if (ToolsString.isEmpty(sourceName)){
                object.put("code",ApiCode.API_CODE_202);
                object.put("msg",config.getVst_qc_source_name()+"数据源中不存在表:"+config.getVst_qc_table());
                object.put("date",ApiCode.API_CODE_TIME);
                return object;
            }

            config.setVst_qc_source_name(sourceName);

            //封装配置基本信息
            config.setVst_qc_addtime(System.currentTimeMillis());
            config.setVst_qc_id(ToolsRandom.getRandom(10));
            config.setVst_qc_uptime(config.getVst_qc_addtime());
            config.setVst_qc_creator("system");
            config.setVst_qc_updator("system");
            config.setVst_qc_entity_name("");

            //getVst_qc_is_compression如果是否压缩为空
            if (ToolsString.isEmpty(config.getVst_qc_is_compression())){
                config.setVst_qc_is_compression(2);
            }

            //保存配置
            int count=quartzMapper.batchQuartzConfig(config);
            if (count>0){
                //添加任务，并且触发任务
                SchedulerUtil.addJob(new StdSchedulerFactory().getScheduler(),config.getVst_qc_name(),config.getVst_qc_trigger_name()
                        ,SchedulerTask.class,config.getVst_qc_job_cron());
                //如果是暂停，则暂停任务
                if (config.getVst_qc_state().equals(QuartzEnum.VST_STATUS.getTreeStatus())){
                    SchedulerUtil.pauseJob(new StdSchedulerFactory().getScheduler(),config.getVst_qc_name());
                }
                logger.info("配置保存成功");
                object.put("code",ApiCode.API_CODE_200);
                object.put("msg","配置保存成功,表不存在的数据源:"+result.getString("remove")+"已经剔除");
                object.put("date",ApiCode.API_CODE_TIME);

            }else {
                logger.info("配置保存失败");
                object.put("code",ApiCode.API_CODE_202);
                object.put("msg","配置保存失败");
                object.put("date",ApiCode.API_CODE_TIME);
            }
        }catch (Exception e){
            logger.info("新增时异常报错");
            object.put("code",ApiCode.API_CODE_110);
            object.put("msg","新增时异常报错");
            object.put("date",ApiCode.API_CODE_TIME);
            e.printStackTrace();
        }
        return object;
    }

    /**
     * @author LiuKai
     * 编辑配置信息
     * @param config 配置表对象
     * @return 返回值 Json
     */
    @Override
    public JSONObject updateQuartzConfig(VstQuartzConfig config) {
        JSONObject object=new JSONObject();
        try {
            //判断字段是否为空
            object=VfcTools.getIsFlag(config);
            if (object.getInteger(ApiMagic.API_MAGIC_CODE).equals(ApiCode.API_CODE_119)){
                return object;
            }

            //验证数据源中是否存在table
            JSONObject result = vfcSource(config.getVst_qc_source_name(),config.getVst_qc_table());

            //获取验证通过的数据
            String sourceName = result.getString("add");

            if (ToolsString.isEmpty(sourceName)){
                object.put("code",ApiCode.API_CODE_202);
                object.put("msg",config.getVst_qc_source_name()+"数据源中不存在表:"+config.getVst_qc_table());
                object.put("date",ApiCode.API_CODE_TIME);
                return object;
            }

            config.setVst_qc_source_name(sourceName);


            //修改配置
            int count=quartzMapper.updateQuartzConfig(config);
            if (count>0){
                object.put("code",ApiCode.API_CODE_200);
                object.put("msg","配置修改成功,表不存在的源数据源:"+result.getString("remove")+"已经剔除");
                object.put("date",ApiCode.API_CODE_TIME);
            }else {
                object.put("code",ApiCode.API_CODE_202);
                object.put("msg","配置修改失败");
                object.put("date",ApiCode.API_CODE_TIME);
            }

        }catch (Exception e){
            object.put("code",ApiCode.API_CODE_110);
            object.put("msg","修改时出现异常报错");
            object.put("date",ApiCode.API_CODE_TIME);
            e.printStackTrace();
        }

        return object;
    }


    /**
     * kai
     * 验证数据源中是否包含需要备份的表
     * @param sourceName 数据源   A,B格式
     * @param table 表
     * @return 返回值json
     */
    private JSONObject vfcSource(String sourceName,String table){
        JSONObject object = new JSONObject();
        //验证所选数据源中是否包含需要备份的表
        String[] source;
        if (sourceName.contains(ApiMagic.API_MAGIC_COMMA)){
            source = sourceName.split(ApiMagic.API_MAGIC_COMMA);
        }else {
            source = new String[]{sourceName};
        }

        StringBuilder add = new StringBuilder();
        StringBuilder remove = new StringBuilder();
        for (String s: source){
            String sql = TableTools.selectTable(s,table);

            List<Map<String,Object>> maps = quartzMapper.queryTable(sql);
            s = s+",";
            if (maps == null ||maps.size()<=0){
                remove.append(s);
            }else {
                add.append(s);
            }
        }
        String result;
        if (!ToolsString.isEmpty(add)){
            result = add.toString().substring(0,add.toString().lastIndexOf(","));
        }else {
            result = "";
        }
        object.put("add",result);
        object.put("remove",remove);
        return object;
    }




    /**
     * kai
     * 删除配置信息
     * @param quartzId 配置表id
     * @return 返回值Json
     */
    @Override
    public JSONObject deleteQuartzConfig(String quartzId) {
        JSONObject object = new JSONObject();
        try {
            //根据quartzId查询配置信息
            VstQuartzConfig config = quartzMapper.queryByIdQuartz(quartzId);

            if (config == null){
                object.put("code", ApiCode.API_CODE_119);
                object.put("msg", "未找到相关配置");
                object.put("date",ApiCode.API_CODE_TIME);
                return object;
            }

            String jobName = config.getVst_qc_name();


            //首先删除调度任务
            SchedulerUtil.deleteJob(new StdSchedulerFactory().getScheduler(),jobName);

        }catch (Exception e){
            object.put("code",ApiCode.API_CODE_110);
            object.put("msg","调度任务删除时出现异常");
            object.put("date",ApiCode.API_CODE_TIME);
            e.printStackTrace();
            return object;
        }finally {
            int count = quartzMapper.deleteQuartzConfig(quartzId);

            if (count > 0){
                object.put("code",ApiCode.API_CODE_200);
                object.put("msg","配置删除成功");
                object.put("date",ApiCode.API_CODE_TIME);
            }else {
                object.put("code",ApiCode.API_CODE_202);
                object.put("msg","配置删除失败");
                object.put("date",ApiCode.API_CODE_TIME);
            }
        }



        return object;
    }

    /**
     * kai
     * 模糊查询数据库表
     * TODO: 2018/4/19 16:56:23
     * @param db 数据源
     * @param table 表
     * @return 返回值Json
     */
    @Override
    public JSONObject queryTable(String db, String table) {
        JSONObject object = new JSONObject();
        if (ToolsString.isEmpty(db) || ToolsString.isEmpty(table)){
            object.put("code",ApiCode.API_CODE_119);
            object.put("msg","数据源或者查询条件为空");
            object.put("date",ApiCode.API_CODE_TIME);
            return object;
        }

        //获取拼接的sql
        String result = TableTools.selectLikeTable(db,table);

        if (ToolsString.isEmpty(result)){
            object.put("code",ApiCode.API_CODE_119);
            object.put("msg","拼接的sql为空");
            object.put("date",ApiCode.API_CODE_TIME);
            return object;
        }

        return publicTable(result);
    }

    /**
     * kai
     * 查询数据库表结构
     * TODO: 2018/4/19 16:56:51
     * @param db  数据源
     * @param table 表名
     * @return 返回值
     */
    @Override
    public JSONObject queryTableFiled(String db, String table) {
        JSONObject object = new JSONObject();
        if (ToolsString.isEmpty(db) || ToolsString.isEmpty(table)){
            object.put("code",ApiCode.API_CODE_119);
            object.put("msg","数据源或者查询条件为空");
            object.put("date",ApiCode.API_CODE_TIME);
            return object;
        }
        //获取查询表字段的sql
        String result = TableTools.getTable(db,table);

        if (ToolsString.isEmpty(result)){
            object.put("code",ApiCode.API_CODE_119);
            object.put("msg","拼接的sql为空");
            object.put("date",ApiCode.API_CODE_TIME);
            return object;
        }

        return publicTable(result);
    }


    /**
     * kai
     * 根据sql查询数据
     *  TODO: 2018/4/19 17:37:59
     * @return 返回值Json
     */
    private JSONObject publicTable(String result){
        JSONObject object = new JSONObject();
        List<Map<String,Object>> maps = quartzMapper.queryTable(result);
        if (maps.size()>0){
            object.put("code",ApiCode.API_CODE_200);
            object.put("msg","数据查询成功");
            object.put("data",maps);
            object.put("date",ApiCode.API_CODE_TIME);
        }else {
            object.put("code",ApiCode.API_CODE_202);
            object.put("msg","暂无数据");
            object.put("data",maps);
            object.put("date",ApiCode.API_CODE_TIME);
        }
        return object;
    }

}
