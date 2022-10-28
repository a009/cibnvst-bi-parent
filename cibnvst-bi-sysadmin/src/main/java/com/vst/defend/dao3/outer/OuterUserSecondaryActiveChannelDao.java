package com.vst.defend.dao3.outer;

import com.vst.common.pojo.OuterVstUserSecondaryActiveChannel;
import com.vst.defend.communal.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author wangjie
 * @date 2019/11/28 12:28
 */
@Repository
public interface OuterUserSecondaryActiveChannelDao extends BaseDao<OuterVstUserSecondaryActiveChannel> {
    /**
     * 导出数据
     * @param params
     * @return
     */
    List<Map<String,Object>> queryExport(Map<String,Object> params);

    /**
     * 根据日期统计
     * @param params
     * @return
     */
    List<Map<String,Object>> queryReportByDate(Map<String,Object> params);

    /**
     * 根据维度统计
     * @param params
     * @return
     */
    List<Map<String,Object>> queryReportByDim(Map<String,Object> params);
}
