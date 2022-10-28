package com.vst.quartz.dao.push;

import com.vst.common.pojo.OuterVstUserLevelChannel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author kai
 * 同步的目标库
 * @// TODO: 2018/1/22
 */
@Repository
public interface OuterVstUserLevelChannelMapper {

    /**
     * 保存数据
     * @param list OuterVstUserLevelChannel对象
     * @return 返回值int
     */
    int batchVstUserLevelChannel(List<OuterVstUserLevelChannel> list);

    /**
     * 删除数据
     * @return 返回值int
     */
    int deleteVstUserLevelChannel();

    /**
     * 查询全部数据
     * @return 返回值 对象集合
     */
    List<OuterVstUserLevelChannel> queryOuterLevelChannel();


    /**
     * LiuKai
     * 查询最大的统计日期
     * @return 返回值 int
     */
    int queryMaxLevel();

    /**
     * kai
     * 根据传入的统计日期删除数据
     * @param map 保存的统计日期
     * @return 返回值int
     */
    int deleteVstUserDateLevelChannel(Map<String,Object> map);

    /**
     * LiuKai
     * 查询超过六小时未通过审核的数据
     * @return 返回值 int集合
     */
    List<OuterVstUserLevelChannel> queryVstUserStateList();


    /**
     * LiuKai
     * 修改为审核并且时间同步时间超过六个小时的数据，将为审核修改成一审核
     * @param list 字符串集合
     * @return 返回值int
     */
    int updateStateLevel(List<OuterVstUserLevelChannel> list);
}
