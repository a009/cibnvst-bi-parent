package com.vst.quartz.dao.push;

import com.vst.common.pojo.VstChannelLevel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author kai
 * 同步渠道质量统计
 */
@Repository
public interface VstChannelLevelMapper {

    /**
     * kai
     * 查询数据
     * @param map 查询条件
     * @return 返回值list map
     */
    List<Map<String,Object>> queryChannelLevel(Map<String,Object> map);


    /**
     * kai
     * 查询最大数量
     * @param map 查询条件
     * @return 返回值int
     */
    int queryMaxColumn(Map<String,Object> map);

    /**
     * kai
     * 删除数据
     * @param map 删除条件
     * @return 返回值int
     */
    int deleteChannelLevel(Map<String,Object> map);


    /**
     * kai
     * 查询数据
     * @param channelLevels 保存的VstChannelLevel集合数据
     * @return 返回值int
     */
    int batchVstChannelLevel(List<VstChannelLevel> channelLevels);


}
