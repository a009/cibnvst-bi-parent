package com.vst.defend.dao2.user;

import com.vst.defend.communal.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author wangjie
 * @date 2019/9/4 11:18
 */
@Repository
public interface VstUserSecondaryActiveChannelDao extends BaseDao<VstUserSecondaryActiveDao> {
    /**
     * 导出数据
     * @param params
     * @return
     */
    List<Map<String,Object>> queryExport(Map<String,Object> params);

    /**
     * 按渠道统计
     * @param params
     * @return
     */
    List<Map<String,Object>> queryReportByChannel(Map<String,Object> params);

    /**
     * 根据日期统计
     * @param params
     * @return
     */
    List<Map<String,Object>> queryReportByDate(Map<String,Object> params);
}
