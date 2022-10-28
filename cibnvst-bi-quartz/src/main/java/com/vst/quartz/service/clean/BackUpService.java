package com.vst.quartz.service.clean;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.pojo.VstQuartzConfig;
import com.vst.quartz.utils.page.PageBean;

import java.util.List;
import java.util.Map;

/**
 * @author kai
 * 备份操作逻辑层
 * TODO: 2018/1/11
 */
public interface BackUpService {
    /**
     * LiuKai
     * TODO: 2018/1/11 11：45：58
     * 字段备份
     * @param config 任务对象
     * @param pageBean 分页实体对象
     * @param db 源数据源
     * @return 返回值json
     */
    JSONObject backUpField(VstQuartzConfig config, PageBean pageBean,String db);

    /**
     * kai
     * TODO: 2018-1-11 15：42：40
     * 全量备份
     * @param config 任务对象
     * @param pageBean 分页实体对象
     * @param db 源数据源
     * @return 返回值 JSONObject
     */
    JSONObject backUpAll(VstQuartzConfig config, PageBean pageBean,String db);

    /**
     * kai
     * 条件备份
     * TODO: 2018-1-11 15：45：40
     * @param config 任务对象
     * @param pageBean 分页实体对象
     * @param db 源数据源
     * @return 返回值Json
     */
    JSONObject backUpCondition(VstQuartzConfig config, PageBean pageBean,String db);

    /**
     * kai
     * 条件备份
     * TODO: 2018/1/11 16:20:20
     * @param backUpTableName 备份表名称
     * @param maps 备份的数据(用于备份到文件)
     * @param filed 备份的字段
     * @param map 根据字段获取字段的类型
     * @param flag 用户判断备份之后是否需要执行原表清理操作
     * @param config 任务配置对象
     * @param daSite 目标数据源
     * @param db 源数据源
     * @param bean 分页公共类
     * @param result 数据库目标表名
     * @param resultSql 备份到备份表的sql
     * @return 返回值 JsonObject
     */
    JSONObject saveBatch(String backUpTableName, List<Map<String,Object>> maps, String filed, Map<String,String> map
            , Boolean flag, PageBean bean, VstQuartzConfig config, String[] daSite,String db,JSONObject result
            ,String resultSql);


}
