package com.vst.quartz.service.clean;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.pojo.VstQuartzConfig;

import java.util.List;

/**
 * @author kai
 * 清除的业务逻辑层
 * TODO: 2018/4/23
 */
public interface CleanTablesService {

    /**
     * kai
     * 清除数据
     * @param tableName 表明
     * @param condition 清除的条件
     * @return 返回值
     */
    int clearTables(String tableName,String condition);

    /**
     * kai
     * 根据tableName和condition查询数据
     * @param tableName 表名
     * @param condition 表执行的条件
     * @return 返回值int
     */
    int updateTables(String tableName, String condition);


}
