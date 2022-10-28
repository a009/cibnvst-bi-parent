package com.vst.quartz.dao.clean;

import org.springframework.stereotype.Repository;

/**
 * @author kai
 * 清理的数据层
 * TODO: 2018/4/23
 */
@Repository
public interface CleanTablesMapper {
    /**
     * kai
     * 根据传入的sql语句执行清除功能
     * @param sql 需要执行的sql语句
     * @return 返回值 int
     */
    int cleanTables(String sql);

    /**
     * @author LiuKai
     * 根据传入的sql的修改信息 禁用情况
     * @param sql 需要执行的sql
     * @return 返回值int
     */
    int updateTables(String sql);


}
