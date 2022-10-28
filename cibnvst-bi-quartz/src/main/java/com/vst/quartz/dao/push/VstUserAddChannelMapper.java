package com.vst.quartz.dao.push;

import com.vst.common.pojo.VstUserAddChannel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author kai
 * 原数据新增渠道用户统计dao
 * @// TODO: 2018/1/22
 */
@Repository
public interface VstUserAddChannelMapper {

    /**
     * LiuKai
     * 查询今天的数据
     * @return 返回值 对象集合
     */
    List<VstUserAddChannel> queryVstUserAddChannel();

    /**
     * LiuKai
     * 查询今天的数据
     * @return 返回值 对象集合
     */
    List<VstUserAddChannel> queryAddAll();

    /**
     * LiuKai
     * 根据传入的统计日期查询
     * @param map 统计日期和传入的查询日期
     * @return 返回值 对象集合
     */
    List<VstUserAddChannel> queryVstUserDateChannel(Map<String,Object> map);

    /**
     * LiuKai
     * 查询最大的统计日期
     * @return 返回值 int
     */
    int queryMaxAdd();
}
