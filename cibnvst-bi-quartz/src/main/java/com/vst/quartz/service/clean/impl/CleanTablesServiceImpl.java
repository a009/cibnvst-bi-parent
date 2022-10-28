package com.vst.quartz.service.clean.impl;

import com.vst.common.tools.string.ToolsString;
import com.vst.quartz.dao.clean.CleanTablesMapper;
import com.vst.quartz.service.clean.CleanTablesService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kai
 * 清理的业务逻辑层
 * TODO: 2018/4/23
 */
@Service
public class CleanTablesServiceImpl implements CleanTablesService {

    private Log logger= LogFactory.getLog(CleanTablesServiceImpl.class);

    private CleanTablesMapper cleanTablesMapper;

    @Autowired
    public CleanTablesServiceImpl(CleanTablesMapper cleanTablesMapper) {
        this.cleanTablesMapper = cleanTablesMapper;
    }

    /**
     * @author LiuKai
     * 清除数据
     * @param tableName 表明
     * @param condition 清除的条件
     * @return 返回值
     */
    @Override
    public int clearTables(String tableName, String condition) {
        if (ToolsString.isEmpty(tableName)){
            logger.info("表名为空");
            return 0;
        }
        String sql;//用于接收拼接的sql
        //如果condition为空，则说明是操作全部，没有条件
        if (ToolsString.isEmpty(condition)){
            sql="delete from "+tableName;
        }else {
            sql="delete from "+tableName+" where "+condition;
        }
        //执行清除操作
        return cleanTablesMapper.cleanTables(sql);
    }

    /**
     * @author LiuKai
     * 根据传入的sql的修改信息 禁用情况
     * @param tableName 表名
     * @param condition 表执行的条件
     * @return 返回值int
     */
    @Override
    public int updateTables(String tableName, String condition) {
        return 0;
    }

}
