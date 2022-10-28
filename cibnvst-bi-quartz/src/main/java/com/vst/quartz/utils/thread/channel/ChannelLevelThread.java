package com.vst.quartz.utils.thread.channel;

import com.vst.quartz.service.PushChannelService;

/**
 * @author kai 
 * 程序一步调用
 * TODO: 2018/7/20
 */
public class ChannelLevelThread implements Runnable {

    private PushChannelService pushChannelService;

    /**
     * 类型
     * 类型1表示同步新增渠道用户统计数据到outer
     * 类型2表示同步季质量用户统计数据到outer
     * 类型3表示同步七张表数据到VstChannelLevel表
     */
    private int type;

    /**
     * 开始时间
     */
    private Integer startDate;

    /**
     * 结束时间
     */
    private Integer endDate;


    public ChannelLevelThread(PushChannelService pushChannelService, int type,Integer startDate,Integer endDate) {
        this.pushChannelService = pushChannelService;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * 方法启动
     */
    @Override
    public void run() {
        switch (type){
            case 1:
                pushChannelService.pushChannelVstUserAdd(startDate,endDate);
                break;
            case 2:
                pushChannelService.pushChannelVstUserLevel(startDate,endDate);
                break;
            case 3:
                pushChannelService.pushVstChannelLevel(startDate,endDate);
                break;
                default:
                    break;
        }
    }
}
