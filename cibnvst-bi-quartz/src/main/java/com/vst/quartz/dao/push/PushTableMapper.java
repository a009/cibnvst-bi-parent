package com.vst.quartz.dao.push;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.List;

/**
 * @author kai 
 * 额外业务数据访问层
 * TODO: 2018/4/23
 */
@Repository
public interface PushTableMapper {


    /**
     * LiuKai
     * TODO: 2018/3/12 11：30：22
     * @param sql 查询的sql
     * @return 返回值 LisMap
     */
    List<Map<String,Object>> queryTable(String sql);

    /**
     * LiuKai
     * TODO: 2018/3/12 11:31:45
     * @param sql 添加的sql
     * @return 返回值
     */
    int batchTable(String sql);


    
    
    
}
