package com.vst.quartz.service;


import com.alibaba.fastjson.JSONObject;

/**
 *  PushChannelService
 * @author kai 
 * Description 数据同步service
 * TODO: 2018/1/22
 */
public interface PushChannelService {

    /**
     * kai
     * 2018-1-22 14:59:08
     * 新增渠道用户统计-对外 同步
     * @param startDate 开始统计日期
     * @param endDate 结束统计日期
     * @return 返回值json
     */
    JSONObject pushChannelVstUserAdd(Integer startDate,Integer endDate);


    /**
     * kai
     * 2018-1-22 14:59：48
     * 季质量用户统计-对外 同步
     * @param startDate 开始统计日期
     * @param endDate 结束统计日期
     * @return 返回值json
     */
    JSONObject pushChannelVstUserLevel(Integer startDate,Integer endDate);

    /**
     * kai
     * 2018-1-25 12:08:59
     * 修改新增渠道用户统计-对外 审核状态
     */
    void updateChannelVstUserAdd();

    /**
     * kai
     * 2018-1-25 12:08:59
     * 修改季质量用户统计-对外 审核状态
     */
    void updateChannelVstUserLevel();

    /**
     * kai
     * 2018-1-25 12:08:59
     * 同步
     * @param startDate 开始统计日期
     * @param endDate 结束统计日期
     * @return Json
     */
    JSONObject pushVstChannelLevel(Integer startDate,Integer endDate);




}


