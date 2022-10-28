package com.vst.quartz.daoconfig.config;

import com.vst.common.pojo.VstOptions;
import org.springframework.stereotype.Repository;

/**
 * @author kai 
 * 配置数据访问层
 * TODO: 2018/4/23
 */
@Repository
public interface VstOptionsMapper {


    /**
     * LiuKai
     * 根据key查询相关的配置
     * @param key 返回值
     * @return
     */
    VstOptions queryOptionsKey(String key);
}
