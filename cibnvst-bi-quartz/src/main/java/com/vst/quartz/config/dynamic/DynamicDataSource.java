package com.vst.quartz.config.dynamic;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author kai
 * 数据源切换执行类
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 定义目标数据源
     */
    public final static String DATA_SOURCE_CONFIG = "vst_config";



    /**
     * 原数据源
     * DATA_SOURCE_BI BI库数据源
     * DATA_SOURCE_CONFIG 配置库数据源
     */
    public final static String DATA_SOURCE_BI = "vst_bi";

    /**
     * 将目标数据源保存在数组中，统一调配
     */
    public final static String[] DATA_SOURCE_ALL = new String[]{DATA_SOURCE_CONFIG};

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceHolder.getDataSourceKey();
    }
}
