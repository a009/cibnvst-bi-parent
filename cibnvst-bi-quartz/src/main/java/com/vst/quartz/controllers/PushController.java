package com.vst.quartz.controllers;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.tools.date.ToolsDate;
import com.vst.quartz.constant.ApiCode;
import com.vst.quartz.constant.ApiMagic;
import com.vst.quartz.enumerate.QuartzEnum;
import com.vst.quartz.service.PushChannelService;
import com.vst.quartz.utils.thread.channel.ChannelLevelThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kai
 */
@RestController
@RequestMapping("/push")
public class PushController {

    @Autowired
    PushChannelService pushChannelService;

    /**
     * LiuKai
     * 同步新增渠道用户统计数据到outer
     * TODO: 2018-3-19 10:58:10
     * @param startDate 同步的开始时间
     * @param endDate 同步的结束时间
     * @return 返回值Json
     */
    @RequestMapping("user/add/channel")
    public JSONObject pushAddUser(@RequestParam("startDate") Integer startDate,
                                  @RequestParam("endDate") Integer endDate){
        JSONObject object = new JSONObject();

        if (startDate == 0 || endDate == 0){
            object.put(ApiMagic.API_MAGIC_CODE,ApiCode.API_CODE_202);
            object.put(ApiMagic.API_MAGIC_MSG,"新增渠道统计的startDate开始时间和结束时间必须都大于0");
            object.put(ApiMagic.API_MAGIC_DATE,ToolsDate.getCurrDate("yyyy-MM-dd HH:mm:ss"));
            return object;
        }

        if (startDate > endDate){
            object.put(ApiMagic.API_MAGIC_CODE,ApiCode.API_CODE_202);
            object.put(ApiMagic.API_MAGIC_MSG,"开始时间不能大于结束时间");
            object.put(ApiMagic.API_MAGIC_DATE,ToolsDate.getCurrDate("yyyy-MM-dd HH:mm:ss"));
            return object;
        }
        new Thread(new ChannelLevelThread(pushChannelService,QuartzEnum.VST_TYPE.getOneStatus(),startDate,endDate)).start();

        object.put(ApiMagic.API_MAGIC_CODE,ApiCode.API_CODE_200);
        object.put(ApiMagic.API_MAGIC_MSG,"同步新增渠道用户统计数据到outer正在进行,请稍等。。。。。");
        object.put(ApiMagic.API_MAGIC_DATE,ToolsDate.getCurrDate("yyyy-MM-dd HH:mm:ss"));
        return object;
    }

    /**
     * LiuKai
     * 同步季质量用户统计数据到outer
     * TODO: 2018-3-19 10:58:10
     * @param startDate 同步的开始时间
     * @param endDate 同步的结束时间
     * @return 返回值Json
     */
    @RequestMapping("user/level/channel")
    public JSONObject pushLevelUser(@RequestParam("startDate") Integer startDate,
                                    @RequestParam("endDate") Integer endDate){
        JSONObject object = new JSONObject();

        if (startDate == 0 || endDate == 0){
            object.put(ApiMagic.API_MAGIC_CODE,ApiCode.API_CODE_202);
            object.put(ApiMagic.API_MAGIC_MSG,"新增季质量用户统计startDate开始时间和结束时间必须都大于0");
            object.put(ApiMagic.API_MAGIC_DATE,ToolsDate.getCurrDate("yyyy-MM-dd HH:mm:ss"));
            return object;
        }

        if (startDate > endDate){
            object.put(ApiMagic.API_MAGIC_CODE,ApiCode.API_CODE_202);
            object.put(ApiMagic.API_MAGIC_MSG,"开始时间不能大于结束时间");
            object.put(ApiMagic.API_MAGIC_DATE,ToolsDate.getCurrDate("yyyy-MM-dd HH:mm:ss"));
            return object;
        }

        new Thread(new ChannelLevelThread(pushChannelService,QuartzEnum.VST_TYPE.getTowStatus(),startDate,endDate)).start();

        object.put(ApiMagic.API_MAGIC_CODE,ApiCode.API_CODE_200);
        object.put(ApiMagic.API_MAGIC_MSG,"同步季质量用户统计数据到outer正在进行,请稍等。。。。。");
        object.put(ApiMagic.API_MAGIC_DATE,ToolsDate.getCurrDate("yyyy-MM-dd HH:mm:ss"));
        return object;
    }



    /**
     * LiuKai
     * 同步七张表数据到VstChannelLevel表
     * TODO: 2018-3-19 10:58:10
     * @param startDate 同步的开始时间
     * @param endDate 同步的结束时间
     * @return 返回值Json
     */
    @RequestMapping("channel/level")
    public JSONObject pushChannelLevel(@RequestParam("startDate" ) Integer startDate,
                                       @RequestParam("endDate") Integer endDate){
        JSONObject object = new JSONObject();

        if (startDate == 0 || endDate == 0){
            object.put(ApiMagic.API_MAGIC_CODE,ApiCode.API_CODE_202);
            object.put(ApiMagic.API_MAGIC_MSG,"startDate开始时间和结束时间必须都大于0");
            object.put(ApiMagic.API_MAGIC_DATE,ToolsDate.getCurrDate("yyyy-MM-dd HH:mm:ss"));
            return object;
        }

        if (startDate > endDate){
            object.put(ApiMagic.API_MAGIC_CODE,ApiCode.API_CODE_202);
            object.put(ApiMagic.API_MAGIC_MSG,"开始时间不能大于结束时间");
            object.put(ApiMagic.API_MAGIC_DATE,ToolsDate.getCurrDate("yyyy-MM-dd HH:mm:ss"));
            return object;
        }

        new Thread(new ChannelLevelThread(pushChannelService,QuartzEnum.VST_TYPE.getTreeStatus(),startDate,endDate)).start();

        object.put(ApiMagic.API_MAGIC_CODE,ApiCode.API_CODE_200);
        object.put(ApiMagic.API_MAGIC_MSG,"同步七张表数据到VstChannelLevel表正在进行,请稍等。。。。。");
        object.put(ApiMagic.API_MAGIC_DATE,ToolsDate.getCurrDate("yyyy-MM-dd HH:mm:ss"));
        return object;
    }




}
