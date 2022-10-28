package com.vst.defend.dao2.user;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author wangjie
 * @date 2019/9/4 10:04
 */
@Repository
public interface VstUserSecondaryActiveDao {
    /**
     * 导出数据
     * @param params
     * @return
     */
    List<Map<String,Object>> queryExport(Map<String,Object> params);
}
