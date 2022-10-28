package com.vst.quartz.dao.quartz;

import com.vst.common.pojo.VstQuartzConfig;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author kai 
 * 任务配置访问层
 * TODO: 2018/4/23
 */
@Repository
public interface QuartzMapper {

    /**
     * kai
     * 查询全部的配置信息
     * @param map 查询的条件
     * @return 返回值 List
     */
    List<Map<String,Object>> queryQuartzConfig(Map<String,Object> map);


    /**
     * kai
     * @param jobName 任务名
     * @return 返回值 任务对象
     */
    VstQuartzConfig queryByName(String jobName);

    /**
     * kai
     * 添加配置信息
     * @param config 配置表
     * @return 返回值 int
     */
    int batchQuartzConfig(VstQuartzConfig config);

    /**
     * kai
     * 编辑配置信息
     * @param config 配置表对象
     * @return 返回值 int
     */
    int updateQuartzConfig(VstQuartzConfig config);

    /**
     * kai
     * 根据任务id删除任务配置
     * TODO: 2018/3/29 12:27:10
     * @param quartzId 任务配置id
     * @return 返回值int
     */
    int deleteQuartzConfig(String quartzId);

    /**
     * kai
     * 根据quartzId查询
     * @param quartzId 任务配置id
     * @return 返回值VstQuartzConfig
     */
    VstQuartzConfig queryByIdQuartz(String quartzId);

    /**
     * kai
     * 公共查询方法
     * @param sql sql语句
     * @return 返回值List
     */
    List<Map<String,Object>> queryTable(String sql);
}
