package com.vst.defend.dao3.inner;

import com.vst.common.pojo.VstUserSecondaryActiveChannelDiff;
import com.vst.defend.communal.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author wangjie
 * @date 2020/5/8 16:51
 */
@Repository
public interface InnerUserSecondaryActiveChannelDiffDao extends BaseDao<VstUserSecondaryActiveChannelDiff> {
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
}
