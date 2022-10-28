package com.vst.defend.dao3.inner;

import com.vst.common.pojo.OuterVstUserSecondaryActiveChannel;
import com.vst.defend.communal.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author wangjie
 * @date 2019/11/28 12:25
 */
@Repository
public interface InnerUserSecondaryActiveChannelDao extends BaseDao<OuterVstUserSecondaryActiveChannel> {
    /**
     * 导出数据
     * @param params
     * @return
     */
    List<Map<String,Object>> queryExport(Map<String,Object> params);

    /**
     * 审核数据
     * @param params
     * @return
     */
    int auditData(Map<String,Object> params);

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
