package com.vst.quartz.service.quartz;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.pojo.VstQuartzConfig;
import com.vst.quartz.utils.page.PageBean;

/**
 * @author kai
 * 任务配置业务逻辑层
 * TODO: 2018/4/23
 */
public interface QuartzService {
    /**
     * LiuKai
     * 查询全部的配置信息
     * @param bean 分页公共类
     * @param jobName 任务名
     * @return 返回值 List
     */
    JSONObject queryQuartzConfig(String jobName, PageBean bean);


    /**
     * kai
     * @param jobName 任务名
     * @return 返回值 任务对象
     */
    VstQuartzConfig queryByName(String jobName);


    /**
     * kai
     * 根据主键查询任务配置
     * TODO: 2018/3/29 10：49：45
     * @param quartzId 任务配置id
     * @return 返回值VstQuartzConfig对象
     */
    VstQuartzConfig queryByIdQuartz(String quartzId);



    /**
     * kai
     * 添加配置表信息
     * @param config 配置表
     * @return 返回值 int
     */
    JSONObject batchQuartzConfig(VstQuartzConfig config);

    /**
     * kai
     * 编辑配置信息
     * @param config 配置表对象
     * @return 返回值 int
     */
    JSONObject updateQuartzConfig(VstQuartzConfig config);

    /**
     * @author kai
     * 删除配置信息
     * @param quartzId 配置表id
     * @return 返回值Json
     */
    JSONObject deleteQuartzConfig(String quartzId);

    /**
     * kai
     * 查询数据表结构
     * TODO: 2018/4/19 16:51:20
     * @param db 数据源
     * @param table 表名
     * @return 返回值Json
     */
    JSONObject queryTable(String db,String table);

    /**
     * kai
     * 查询表字段
     * TODO: 2018/4/19 16:53:20
     * @param db 数据源
     * @param table 表名
     * @return 返回值Json
     */
    JSONObject queryTableFiled(String db,String table);
}
