package com.vst.defend.dao2.home;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author wangjie
 * @date 2020/1/17 10:53
 */
@Repository
public interface VstHomeClassifyClickDao {
    /**
     * 导出数据
     * @param params
     * @return
     */
    List<Map<String,Object>> queryExport(Map<String,Object> params);
}
