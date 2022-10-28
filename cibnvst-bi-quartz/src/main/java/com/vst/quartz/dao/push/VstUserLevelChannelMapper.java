package com.vst.quartz.dao.push;

import com.vst.common.pojo.VstUserAddChannel;
import com.vst.common.pojo.VstUserLevelChannel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author kai 
 * 原数据季质量用户统计
 * @// TODO: 2018/1/22
 */
@Repository
public interface VstUserLevelChannelMapper {

    /**
     * LiuKai
     * 查询今天的数据
     * @return 返回值 对象集合
     */
    List<VstUserLevelChannel> queryVstUserLevelChannel();


    /**
     * LiuKai
     * 查询全部数据
     * @return 返回值 对象集合
     */
    List<VstUserLevelChannel> queryLevelAll();

    /**
     * LiuKai
     * 根据传入的统计日期查询
     * @param map 统计日期
     * @return 返回值 对象集合
     */
    List<VstUserLevelChannel> queryVstUserDateChannel(Map<String,Object> map);


    /**
     * LiuKai
     * 查询最大的统计日期
     * @return 返回值 int
     */
    int queryMaxLevel();


}
