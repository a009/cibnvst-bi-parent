package com.vst.quartz.dao.clean;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author kai
 * 数据备份访问层
 * TODO: 2018/4/23
 */
@Repository
public interface BackUpMapper {

    /**
     * 查询需要备份的数据
     * @param sql 传来的sql语句
     * @return 返回值
     */
    List<Map<String,Object>> queryList(String sql);

    /**
     * 将数据保存到备份表
     * @param sql 查询的sql语句
     * @return 返回值int
     */
    int batchTablesBackUp(String sql);


}
