package com.vst.defend.dao2.movie;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author wangjie
 * @date 2020/1/15 17:28
 */
@Repository
public interface VstMovieExposureDao {
    /**
     * 导出数据
     * @param params
     * @return
     */
    List<Map<String,Object>> queryExport(Map<String,Object> params);
}
