package com.vst.quartz.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.pojo.*;
import com.vst.common.tools.date.ToolsDate;
import com.vst.common.tools.encrypt.ToolsEncrypt;
import com.vst.common.tools.http.ToolsHttp;
import com.vst.common.tools.number.ToolsRandom;
import com.vst.quartz.constant.Constants;
import com.vst.quartz.config.dynamic.DataSourceHolder;
import com.vst.quartz.config.dynamic.DynamicDataSource;
import com.vst.quartz.dao.push.*;
import com.vst.quartz.daoconfig.config.VstOptionsMapper;
import com.vst.quartz.service.PushChannelService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kai
 * 额外业务处理层
 * TODO: 2018/4/23
 */
@Service
public class PushChannelServiceImpl implements PushChannelService {

    private Log logger= LogFactory.getLog(PushChannelServiceImpl.class);

    private OuterVstUserAddChannelMapper outerVstUserAddChannelMapper;

    private OuterVstUserLevelChannelMapper outerVstUserLevelChannelMapper;

    private VstUserAddChannelMapper vstUserAddChannelMapper;

    private VstUserLevelChannelMapper vstUserLevelChannelMapper;

    private VstOptionsMapper optionsMapper;

    private VstChannelLevelMapper vstChannelLevelMapper;

    /**
     * kai
     * TODO: 2018/3/26 10:38:43
     * 采用构造注入不采用field注入和setter注入
     * 虽然构造注入显得代码很臃肿，但是为什么要用，相关解释可以查看spring文档,
     *     数据 对内 ——> 对外   optionsMapper数据访问层的数据用于数据同步是获取相应的调增系数
     *     渠道质量统计 汇集了七张表的数据，六张表数据来源于 报表库， 一张表的数据来源于4.0系统，通过接口获取
     *      @param outerVstUserAddChannelMapper 对外新增渠道用户统计 数据访问层
     *      @param outerVstUserLevelChannelMapper 对外季质量用户统计 数据访问层
     *      @param vstUserAddChannelMapper 对内新增渠道用户统计 数据访问层
     *      @param vstUserLevelChannelMapper 对内季质量用户统计 数据访问层
     *      @param optionsMapper    参数配置 数据访问层
     *      @param vstChannelLevelMapper 渠道质量统计 数据访问层
     */
    @Autowired
    public PushChannelServiceImpl(OuterVstUserAddChannelMapper outerVstUserAddChannelMapper,OuterVstUserLevelChannelMapper outerVstUserLevelChannelMapper
                  ,VstUserAddChannelMapper vstUserAddChannelMapper,VstUserLevelChannelMapper vstUserLevelChannelMapper
                  ,VstOptionsMapper optionsMapper,VstChannelLevelMapper vstChannelLevelMapper) {

        this.outerVstUserAddChannelMapper = outerVstUserAddChannelMapper;
        this.outerVstUserLevelChannelMapper = outerVstUserLevelChannelMapper;
        this.vstUserAddChannelMapper = vstUserAddChannelMapper;
        this.vstUserLevelChannelMapper = vstUserLevelChannelMapper;
        this.optionsMapper = optionsMapper;
        this.vstChannelLevelMapper = vstChannelLevelMapper;
    }

    /**
     * 新增渠道用户统计-对外 同步
     * @param startDate 开始统计日期
     * @param endDate 结束统计日期
     * @return JSONObject
     */
    @Override
    public JSONObject pushChannelVstUserAdd(Integer startDate,Integer endDate) {
        DataSourceHolder.setDataSourceKey(DynamicDataSource.DATA_SOURCE_BI);
        JSONObject result = new JSONObject();
        logger.info(">>>>>>>>>开始同步新增渠道用户统计>>>>>>>>>");
        if (startDate < 0 ||  endDate<0){
            result.put("code","110");
            result.put("msg","日期格式不对，不能小于0");
        }
        try {

            //查询同步表的全部数据
            List<OuterVstUserAddChannel> outers=outerVstUserAddChannelMapper.queryOuterAddChannel();

            //保存需要同步的数据
            List<VstUserAddChannel> lists= new ArrayList<>();
            int maxOuter=0;
            int maxUser=0;
            //封装查询条件
            Map<String,Object> map =new HashMap<>();
            //如果outers小于0，表示同步所有数据，大于0则反
            if (outers.size() > 0){
                if (startDate == 0 && endDate == 0) {
                    //查询原表数据最大统计日期数
                    maxUser = vstUserAddChannelMapper.queryMaxAdd();
                    //获取同步表最大同步日期
                    maxOuter = outerVstUserAddChannelMapper.queryMaxAdd();
                }else {
                    maxOuter = startDate;
                    maxUser = endDate;
                }

                //如果日期相等，则只更新当天的数据
               if (maxUser > 0 && maxOuter>0){
                    if (maxUser>=maxOuter){
                        map.put("maxOuter",maxOuter);
                        if (startDate > 0){
                            map.put("maxUser",maxUser);
                        }
                        map.put("date",startDate);
                        lists=vstUserAddChannelMapper.queryVstUserDateChannel(map);
                    }

               }

            }else {
                //查询全部原表数据
                lists=vstUserAddChannelMapper.queryAddAll();
            }

            //封装数据
            outers=new ArrayList<OuterVstUserAddChannel>();
            //保存数据之前先根据maxUser找出这个时间的同步数据，然后删除
            int del = outerVstUserAddChannelMapper.deleteVstUserDateAddChannel(map);

            if (del > 0){
                logger.info("时间段为:"+maxOuter+"的"+del+"条数据清理完成");
            }


            DataSourceHolder.setDataSourceKey(DynamicDataSource.DATA_SOURCE_CONFIG);
            //查询配置表
            VstOptions options= optionsMapper.queryOptionsKey("channel_rate");

            DataSourceHolder.setDataSourceKey(DynamicDataSource.DATA_SOURCE_BI);
            int sum = 0;
            //遍历原表所有数据
            for (VstUserAddChannel vstAdd:lists) {
                //定义OuterVstUserAddChannel同步表对象，用于保存VstUserAddChannel中的数据
                OuterVstUserAddChannel addChannel=new OuterVstUserAddChannel();
                //主键
                addChannel.setVst_uac_id(vstAdd.getVst_uac_id());
                //包名
                addChannel.setVst_uac_pkg(vstAdd.getVst_uac_pkg());
                //新增时间，13位毫秒数
                addChannel.setVst_uac_addtime(vstAdd.getVst_uac_addtime());
                //新增用户数
                addChannel.setVst_uac_amount(vstAdd.getVst_uac_amount());
                //渠道
                addChannel.setVst_uac_channel(vstAdd.getVst_uac_channel());
                //创建人
                addChannel.setVst_uac_creator(vstAdd.getVst_uac_creator());
                //统计日期
                addChannel.setVst_uac_date(vstAdd.getVst_uac_date());
                //天环比
                addChannel.setVst_uac_dod(vstAdd.getVst_uac_dod());
                //月环比
                addChannel.setVst_uac_mom(vstAdd.getVst_uac_mom());
                //备注
                addChannel.setVst_uac_summary(vstAdd.getVst_uac_summary());
                //修改人
                addChannel.setVst_uac_updator(vstAdd.getVst_uac_updator());
                //修改时间，13位毫秒数
                addChannel.setVst_uac_uptime(vstAdd.getVst_uac_uptime());
                //周环比
                addChannel.setVst_uac_wow(vstAdd.getVst_uac_wow());
                //获取调整系数 默认0.35
                String channel="0.35";
                if (options != null) {
                    JSONObject object=JSONObject.parseObject(options.getVst_option_value());

                    //如果object中没有91vst这个属性，则表示调用默认为0的属性
                    //根据vstAdd.getVst_uac_channel()渠道获取调整系数
                    if (object.getString(vstAdd.getVst_uac_channel()) != null){
                        channel=object.getString(vstAdd.getVst_uac_channel());
                    }else {
                       channel=object.getString("0");
                    }
                }

                //调整系数，默认0.35
                addChannel.setVst_uac_modulus(channel);
                //审核状态，1：未审核，2：已审核
                addChannel.setVst_uac_state(1);
                //同步时间
                addChannel.setVst_uac_synctime(System.currentTimeMillis());

                outers.add(addChannel);

                // 如果存在两千数据，则执行批量添加
                if (outers.size()>=1999){
                    int count=outerVstUserAddChannelMapper.batchVstUserAddChannel(outers);
                    if (count>0){
                        logger.info(">>>>>>>>>>>>新增渠道用户统计数据同步:"+outers.size()+"条成功");
                        sum = sum + outers.size();
                    }else {
                        logger.info(">>>>>>>>>>>>新增渠道用户统计数据同步:"+outers.size()+"条失败");
                    }
                    outers.clear();
                }
            }

            //判断outers中是否还有数据没有保存
            if (outers.size()>0){
                int count=outerVstUserAddChannelMapper.batchVstUserAddChannel(outers);
                if (count>0){
                    logger.info(">>>>>>>>>>>>新增渠道用户统计数据同步:"+outers.size()+"条成功");
                    sum = sum + outers.size();

                }else {
                    logger.info(">>>>>>>>>>>>新增渠道用户统计数据同步:"+outers.size()+"条失败");
                }
                outers.clear();
            }
            result.put("code",200);
            result.put("msg","数据同步成功，本次同步:"+sum+"条");
        }catch (Exception e){
            logger.info(">>>>>异常报错。。。。。");
            result.put("code",110);
            result.put("msg",">>>>>异常报错。。。。");
            e.printStackTrace();
        }


        logger.info(">>>>>>>>>同步新增渠道用户统计结束>>>>>>>>>");

        return result;
    }

    /**
     * LiuKai
     * 2018-1-22 14:59：48
     * @param startDate 开始统计日期
     * @param endDate 结束统计日期
     * 季质量用户统计-对外 同步
     */
    @Override
    public JSONObject pushChannelVstUserLevel(Integer startDate,Integer endDate) {
        DataSourceHolder.setDataSourceKey(DynamicDataSource.DATA_SOURCE_BI);
        logger.info(">>>>>>>>>开始同步季质量用户统计>>>>>>>>>");
        JSONObject result = new JSONObject();
        if (startDate < 0 || endDate < 0 ){
            result.put("code","110");
            result.put("msg","日期格式不对，不能小于0");
        }
        try {

            //查询同步表的全部数据
            List<OuterVstUserLevelChannel> outers=outerVstUserLevelChannelMapper.queryOuterLevelChannel();

            //保存需要同步的数据
            List<VstUserLevelChannel> lists= new ArrayList<VstUserLevelChannel>();
            int maxOuter=0;
            int maxUser=0;
            //封装查询数据
            Map<String, Object> map = new HashMap<>(5);
            //如果outers小于0，表示同步所有数据，大于0则反
            if (outers.size() > 0){
                if (startDate == 0 && endDate==0) {
                    //查询原表数据最大统计日期数
                    maxUser = vstUserLevelChannelMapper.queryMaxLevel();
                    //获取同步表的最大日期
                    maxOuter = outerVstUserLevelChannelMapper.queryMaxLevel();
                }else {
                    maxOuter = startDate;
                    maxUser = endDate;
                }

                //如果日期相等，则只更新当天的数据
                if (maxUser > 0 && maxOuter>0){
                    if (maxUser >= maxOuter) {
                        map.put("maxOuter", maxOuter);
                        if (startDate > 0){
                            map.put("maxUser", endDate);
                        }
                        map.put("date", startDate);
                        lists = vstUserLevelChannelMapper.queryVstUserDateChannel(map);
                    }
                }

            }else {
                //查询全部原表数据
                lists=vstUserLevelChannelMapper.queryLevelAll();
            }

            //封装数据
            outers=new ArrayList<>();
            //保存数据之前先根据maxOuter找出这个时间的同步数据，然后删除
            int del = outerVstUserLevelChannelMapper.deleteVstUserDateLevelChannel(map);

            if (del > 0){
                logger.info("时间段为:"+maxUser+"的"+del+"条数据清理完成");
            }

            DataSourceHolder.setDataSourceKey(DynamicDataSource.DATA_SOURCE_CONFIG);

            //查询配置表
            VstOptions options= optionsMapper.queryOptionsKey("channel_rate");


            DataSourceHolder.setDataSourceKey(DynamicDataSource.DATA_SOURCE_BI);

            int sum = 0;

            //遍历原表所有数据
            for (VstUserLevelChannel vstLevel:lists) {
                //定义OuterVstUserAddChannel同步表对象，用于保存VstUserAddChannel中的数据
                OuterVstUserLevelChannel levelChannel=new OuterVstUserLevelChannel();
                //主键
                levelChannel.setVst_ulc_id(vstLevel.getVst_ulc_id());
                //包名
                levelChannel.setVst_ulc_pkg(vstLevel.getVst_ulc_pkg());
                //新增时间，13位毫秒数
                levelChannel.setVst_ulc_addtime(vstLevel.getVst_ulc_addtime());
                //季新增用户数
                levelChannel.setVst_ulc_add_amount(vstLevel.getVst_ulc_add_amount());
                //季质量用户数
                levelChannel.setVst_ulc_season_amount(vstLevel.getVst_ulc_season_amount());
                //渠道
                levelChannel.setVst_ulc_channel(vstLevel.getVst_ulc_channel());
                //创建人
                levelChannel.setVst_ulc_creator(vstLevel.getVst_ulc_creator());
                //统计日期
                levelChannel.setVst_ulc_date(vstLevel.getVst_ulc_date());
                //天环比
                levelChannel.setVst_ulc_season_dod(vstLevel.getVst_ulc_season_dod());
                //季质量用户周环比
                levelChannel.setVst_ulc_season_wow(vstLevel.getVst_ulc_season_wow());
                //季质量用户占比
                levelChannel.setVst_ulc_season_radio(vstLevel.getVst_ulc_season_radio());
                //备注
                levelChannel.setVst_ulc_summary(vstLevel.getVst_ulc_summary());
                //修改人
                levelChannel.setVst_ulc_updator(vstLevel.getVst_ulc_updator());
                //修改时间，13位毫秒数
                levelChannel.setVst_ulc_uptime(vstLevel.getVst_ulc_uptime());
                //获取调整系数 默认0.35
                String channel="0.35";
                if (options != null) {
                    JSONObject object=JSONObject.parseObject(options.getVst_option_value());

                    //如果object中没有这个渠道属性，则表示调用默认为0的属性
                    //根据vstAdd.getVst_uac_channel()渠道获取调整系数
                    if (object.getString(vstLevel.getVst_ulc_channel()) != null){
                        channel=object.getString(vstLevel.getVst_ulc_channel());
                    }else {
                        channel=object.getString("0");
                    }
                }

                //调整系数，默认0.35
                levelChannel.setVst_ulc_modulus(channel);
                //审核状态，1：未审核，2：已审核
                levelChannel.setVst_ulc_state(1);
                //同步时间
                levelChannel.setVst_ulc_synctime(System.currentTimeMillis());

                outers.add(levelChannel);

                //如果存在两千数据，则执行批量添加
                if (outers.size()>=1999){
                    int count=outerVstUserLevelChannelMapper.batchVstUserLevelChannel(outers);
                    if (count>0){
                        logger.info(">>>>>>>>>>>>新增渠道用户统计数据同步:"+outers.size()+"条成功");
                        sum = sum + outers.size();

                    }else {
                        logger.info(">>>>>>>>>>>>新增渠道用户统计数据同步:"+outers.size()+"条失败");
                    }
                    outers.clear();
                }
            }

            //判断outers中是否还有数据没有保存
            if (outers.size()>0){
                int count=outerVstUserLevelChannelMapper.batchVstUserLevelChannel(outers);
                if (count>0){
                    logger.info(">>>>>>>>>>>>新增渠道用户统计数据同步:"+outers.size()+"条成功");
                    sum = sum + outers.size();
                }else {
                    logger.info(">>>>>>>>>>>>新增渠道用户统计数据同步:"+outers.size()+"条失败");
                }
                outers.clear();
            }

            result.put("code",200);
            result.put("msg","数据同步成功，本次同步:"+sum+"条");
        }catch (Exception e) {
            logger.info(">>>>>异常报错。。。。。");
            result.put("code",110);
            result.put("msg","异常报错。。。。");
            e.printStackTrace();
        }


        logger.info(">>>>>>>>>同步季质量用户统计结束>>>>>>>>>");

        return result;
    }


    /**
     * LiuKai
     * 2018-1-25 12:08:59
     * 修改新增渠道用户统计-对外 审核状态
     */
    @Override
    public void updateChannelVstUserAdd(){
        DataSourceHolder.setDataSourceKey(DynamicDataSource.DATA_SOURCE_BI);
        //查询同步时间大于六小时并且为审核的数据
        List<OuterVstUserAddChannel> outers=outerVstUserAddChannelMapper.queryVstUserStateList();
        //定义总页数
        int page=0;
        int pageSize=2000;
        if (outers.size()>0){
            page = (pageSize>=outers.size()?1:(outers.size() % pageSize == 0)?outers.size()/pageSize:outers.size()/pageSize+1);


            for (int i = 1; i<=page; i++){

                List<OuterVstUserAddChannel> channels;

                if (i == page){
                    channels=outers.subList((i-1)*pageSize,outers.size());
                }else {
                    channels=outers.subList((i-1)*pageSize,i*pageSize);
                }

                //修改值
                for (OuterVstUserAddChannel addChannel:channels) {
                    addChannel.setVst_uac_uptime(System.currentTimeMillis());
                    addChannel.setVst_uac_updator("sync_audit");
                }
                int count = outerVstUserAddChannelMapper.updateStateAdd(channels);
                if (count > 0){
                    logger.info(">>>>>>>>成功修改："+channels.size()+"条记录");
                }

            }
        }else {
            logger.info(">>>>>>>>>>>暂无修改>>>>>>>>>>>ADD");
        }
    }

    /**
     * LiuKai
     * 2018-1-25 12:08:59
     * 修改季质量用户统计-对外 审核状态
     */
    @Override
    public void updateChannelVstUserLevel() {
        DataSourceHolder.setDataSourceKey(DynamicDataSource.DATA_SOURCE_BI);
        //查询同步时间大于六小时并且为审核的数据
        List<OuterVstUserLevelChannel> outers=outerVstUserLevelChannelMapper.queryVstUserStateList();
        //定义总页数
        int page=0;
        int pageSize=2000;
        if (outers.size()>0){
            page = (pageSize>=outers.size()?1:(outers.size() % pageSize == 0)?outers.size()/pageSize:outers.size()/pageSize+1);


            for (int i = 1; i<=page; i++){

                List<OuterVstUserLevelChannel> channels=new ArrayList<OuterVstUserLevelChannel>();

                if (i == page){
                    channels=outers.subList((i-1)*pageSize,outers.size());
                }else {
                    channels=outers.subList((i-1)*pageSize,i*pageSize);
                }

                //修改值
                for (OuterVstUserLevelChannel levelChannel:channels) {
                    levelChannel.setVst_ulc_uptime(System.currentTimeMillis());
                }


                int count = outerVstUserLevelChannelMapper.updateStateLevel(channels);
                if (count > 0){
                    logger.info(">>>>>>>>成功修改："+channels.size()+"条记录");
                }

            }
        }else {
            logger.info(">>>>>>>>>>>暂无修改>>>>>>>>>>>LEVEL");
        }
    }

    /**
     *  同步渠道质量统计
     * @param startDate 开始统计日期
     * @param endDate 结束统计日期
     * @return JSONObject
     */
    @Override
    public JSONObject pushVstChannelLevel(Integer startDate,Integer endDate) {

        DataSourceHolder.setDataSourceKey(DynamicDataSource.DATA_SOURCE_BI);
        JSONObject result = new JSONObject();
        int sum = 0 ;
        try {
            //查询

            //查询累计渠道用户统计
            //获取原表数据
            Map<String, Object> map = new HashMap<>(16);


            map.put("columns","vst_cl_id");
            map.put("table", "vst_channel_level");
            map.put("wheres","1=1 limit 0,2");
            //这个查询步骤主要是为了判定数据库中是否包含数据，以防止查询最大日期的时候返回的是个空值
            List<Map<String,Object>> levels = vstChannelLevelMapper.queryChannelLevel(map);
            List<Map<String ,Object>> sums = new ArrayList<Map<String, Object>>();
            int levelMax=0;
            int sumMax = 0;
            //如果大于0表示有数据，否则直接同步全部数据
            if (levels.size()>0){
                if (startDate == 0 && endDate == 0) {
                    map.clear();
                    map.put("columns", "max(vst_cl_date)");
                    map.put("table", "vst_channel_level");
                    //获取原表数据最大的日期
                    levelMax = vstChannelLevelMapper.queryMaxColumn(map);

                    //查询vst_user_sum_channel表，获取vst_channel_level表的vst_cl_user_sum字段数据
                    //查询最大日期
                    map.clear();
                    map.put("columns", "max(vst_usc_date)");
                    map.put("table", "vst_user_sum_channel");
                    sumMax = vstChannelLevelMapper.queryMaxColumn(map);
                }else {
                    levelMax = startDate;
                    sumMax = endDate;
                }

                if (sumMax >= levelMax){
                    map.clear();
                    map.put("columns","vst_usc_amount,vst_usc_pkg,vst_usc_channel,vst_usc_date");
                    map.put("table","vst_user_sum_channel");
                    if (startDate == 0){
                        map.put("wheres","(vst_usc_pkg = 'net.myvst.v2' or vst_usc_pkg = 'com.vst.itv52.v1') " +
                                "and vst_usc_date >= "+levelMax);
                    }else {
                        map.put("wheres","(vst_usc_pkg = 'net.myvst.v2' or vst_usc_pkg = 'com.vst.itv52.v1') " +
                                "and vst_usc_date >= "+levelMax+" and vst_usc_date<="+sumMax);
                    }


                    //根据条件查询全部数据
                    sums = vstChannelLevelMapper.queryChannelLevel(map);

                }else if (levelMax>sumMax && sumMax <= 0){
                    map.clear();
                    map.put("columns", "max(vst_usc_date)");
                    map.put("table", "vst_user_sum_channel");
                    sumMax = vstChannelLevelMapper.queryMaxColumn(map);
                    map.clear();
                    map.put("columns","vst_usc_amount,vst_usc_pkg,vst_usc_channel,vst_usc_date");
                    map.put("table","vst_user_sum_channel");
                    map.put("wheres","(vst_usc_pkg = 'net.myvst.v2' or vst_usc_pkg = 'com.vst.itv52.v1') " +
                            "and vst_usc_date >= "+levelMax+" and vst_usc_date<="+sumMax);
                    //根据条件查询全部数据
                    sums = vstChannelLevelMapper.queryChannelLevel(map);
                }
                else {
                    logger.info(">>>>>>>貌似vst_channel_level数据有点不正常了。。。。。。。");
                }


            }else {
                sumMax=Integer.parseInt(ToolsDate.getCurrDate("yyyyMMdd"));
                map.clear();
                map.put("columns","vst_usc_amount,vst_usc_pkg,vst_usc_channel,vst_usc_date");
                map.put("table","vst_user_sum_channel");
                map.put("wheres","vst_usc_pkg = 'net.myvst.v2' or vst_usc_pkg = 'com.vst.itv52.v1'");
                //查询全部vst_user_sum_channel数据
                sums = vstChannelLevelMapper.queryChannelLevel(map);

            }


            if (sums.size()>0){

                //查询第二张vst_user_active_channel活跃渠道用户统计表
                map.clear();
                map.put("columns","vst_uac_amount,vst_uac_pkg,vst_uac_channel,vst_uac_date");
                map.put("table","vst_user_active_channel");
                if (startDate == 0) {
                    map.put("wheres", "(vst_uac_pkg = 'net.myvst.v2' or vst_uac_pkg='com.vst.itv52.v1') " +
                            "and vst_uac_date >= " + levelMax);
                }else {
                    map.put("wheres", "(vst_uac_pkg = 'net.myvst.v2' or vst_uac_pkg='com.vst.itv52.v1') " +
                            "and vst_uac_date >= " + levelMax+" and vst_uac_date<="+sumMax);
                }

                List<Map<String,Object>> actives = vstChannelLevelMapper.queryChannelLevel(map);

                //用于保存数据
                Map<String,Map<String,Object>> activeMap = new HashMap<String, Map<String,Object>>();

                //遍历活跃渠道用户统计数据
                if (actives.size()>0){
                    for (Map<String,Object> resultMap : actives){

                        //获取时间
                        int date = Integer.parseInt(resultMap.get("vst_uac_date") == null?"0":resultMap.get("vst_uac_date").toString());
                        String pkg = resultMap.get("vst_uac_pkg") == null?"":resultMap.get("vst_uac_pkg").toString();
                        String channel = resultMap.get("vst_uac_channel") == null?"":resultMap.get("vst_uac_channel").toString();
                        String key = ToolsEncrypt.getMD5Encrypt(pkg+channel+date);
                        activeMap.put(key,resultMap);
                    }
                }


                //查询第三张vst_user_add_channel新增渠道用户统计表数据
                map.clear();
                map.put("columns","vst_uac_amount,vst_uac_pkg,vst_uac_channel,vst_uac_date");
                map.put("table","vst_user_add_channel");
                if (startDate == 0) {
                    map.put("wheres", "(vst_uac_pkg = 'net.myvst.v2' or vst_uac_pkg='com.vst.itv52.v1') " +
                            "and vst_uac_date >= " + levelMax);
                }else {
                    map.put("wheres", "(vst_uac_pkg = 'net.myvst.v2' or vst_uac_pkg='com.vst.itv52.v1') " +
                            "and vst_uac_date >= " + levelMax +" and vst_uac_date<="+sumMax);
                }
                List<Map<String,Object>>  adds = vstChannelLevelMapper.queryChannelLevel(map);
                //用于保存数据
                Map<String,Map<String,Object>> addMap = new HashMap<String, Map<String,Object>>();
                if (adds.size() > 0){
                    for (Map<String,Object> resultMap: adds) {
                        //获取时间
                        int date = Integer.parseInt(resultMap.get("vst_uac_date") == null?"0":resultMap.get("vst_uac_date").toString());
                        String pkg = resultMap.get("vst_uac_pkg") == null?"":resultMap.get("vst_uac_pkg").toString();
                        String channel = resultMap.get("vst_uac_channel") == null?"":resultMap.get("vst_uac_channel").toString();
                        String key = ToolsEncrypt.getMD5Encrypt(pkg+channel+date);
                        addMap.put(key,resultMap);
                    }
                }


                //查询第四张表vst_user_level_channel 季质量用户统计数据
                map.clear();
                map.put("columns","vst_ulc_add_amount,vst_ulc_season_amount,vst_ulc_season_radio,vst_ulc_pkg,vst_ulc_channel,vst_ulc_date");
                map.put("table","vst_user_level_channel");
                if (startDate == 0) {
                    map.put("wheres", "(vst_ulc_pkg = 'net.myvst.v2' or vst_ulc_pkg='com.vst.itv52.v1') " +
                            "and vst_ulc_date >= " + levelMax);
                }else {
                    map.put("wheres", "(vst_ulc_pkg = 'net.myvst.v2' or vst_ulc_pkg='com.vst.itv52.v1') " +
                            "and vst_ulc_date >= " + levelMax + " and vst_ulc_date<="+sumMax);
                }

                List<Map<String,Object>> userLevels= vstChannelLevelMapper.queryChannelLevel(map);
                //用于保存数据
                Map<String,Map<String,Object>> userLevelMap = new HashMap<String, Map<String,Object>>();
                if (userLevels.size() > 0){
                    for (Map<String,Object> resultMap: userLevels){
                        //获取日期
                        int date = Integer.parseInt(resultMap.get("vst_ulc_date")==null?"0":resultMap.get("vst_ulc_date").toString());
                        String pkg = resultMap.get("vst_ulc_pkg") == null?"":resultMap.get("vst_ulc_pkg").toString();
                        String channel = resultMap.get("vst_ulc_channel") == null?"":resultMap.get("vst_ulc_channel").toString();
                        String key = ToolsEncrypt.getMD5Encrypt(pkg+channel+date);
                        userLevelMap.put(key,resultMap);

                    }
                }

                //查询第五张表vst_user_retain_channel留存用户统计-渠道维度
                map.clear();
                map.put("columns","vst_urc_amount,vst_urc_week_amount,vst_urc_month_amount,vst_urc_season_amount" +
                        ",vst_urc_pkg,vst_urc_channel,vst_urc_date");
                map.put("table","vst_user_retain_channel");
                if (startDate == 0) {
                    map.put("wheres", "(vst_urc_pkg = 'net.myvst.v2' or vst_urc_pkg='com.vst.itv52.v1') " +
                            "and vst_urc_date >= " + levelMax);
                }else {
                    map.put("wheres", "(vst_urc_pkg = 'net.myvst.v2' or vst_urc_pkg='com.vst.itv52.v1') " +
                            "and vst_urc_date >= " + levelMax + " and vst_urc_date <= "+sumMax);
                }
                List<Map<String,Object>> retains = vstChannelLevelMapper.queryChannelLevel(map);
                //用于保存数据
                Map<String, Map<String,Object>> retainMap = new HashMap<String,Map<String,Object>>();

                if (retains.size() > 0){
                    for (Map<String,Object> resultMap: retains) {

                        //获取统计时间
                        int date = Integer.parseInt(resultMap.get("vst_urc_date") == null?"0":resultMap.get("vst_urc_date").toString());
                        String pkg = resultMap.get("vst_urc_pkg")==null?"":resultMap.get("vst_urc_pkg").toString();
                        String channel = resultMap.get("vst_urc_channel")==null?"":resultMap.get("vst_urc_channel").toString();
                        String key = ToolsEncrypt.getMD5Encrypt(pkg+channel+date);

                        retainMap.put(key,resultMap);


                    }
                }

                //查询第六张表vst_movie_total_play_channel渠道汇总播放统计
                map.clear();
                map.put("columns","vst_mtpc_vv,vst_mtpc_uv,vst_mtpc_avg,vst_mtpc_playtime" +
                        ",vst_mtpc_playtime_avg,vst_mtpc_pkg,vst_mtpc_channel,vst_mtpc_date");
                map.put("table","vst_movie_total_play_channel");
                if (startDate == 0) {
                    map.put("wheres", "(vst_mtpc_pkg = 'net.myvst.v2' or vst_mtpc_pkg='com.vst.itv52.v1') " +
                            "and vst_mtpc_date >= " + levelMax);
                }else {
                    map.put("wheres", "(vst_mtpc_pkg = 'net.myvst.v2' or vst_mtpc_pkg='com.vst.itv52.v1') " +
                            "and vst_mtpc_date >= " + levelMax + " and vst_mtpc_date<="+sumMax);
                }
                List<Map<String,Object>> plays = vstChannelLevelMapper.queryChannelLevel(map);
                //用于保存数据
                Map<String, Map<String,Object>> playMap = new HashMap<String,Map<String,Object>>();

                if (plays.size()  > 0){
                    for (Map<String, Object> resultMap: plays) {

                        //统计日期
                        int date = Integer.parseInt(resultMap.get("vst_mtpc_date")==null?"0":resultMap.get("vst_mtpc_date").toString());
                        String pkg = resultMap.get("vst_mtpc_pkg") == null?"":resultMap.get("vst_mtpc_pkg").toString();
                        String channel = resultMap.get("vst_mtpc_channel")==null?"":resultMap.get("vst_mtpc_channel").toString();
                        String key = ToolsEncrypt.getMD5Encrypt(pkg+channel+date);

                        playMap.put(key,resultMap);
                    }
                }
                map.clear();

                //第七张表，通过接口方式获取
                String url = Constants.ORDERS;
                String orderUrl = url.replace("{startDate}",levelMax+"").replace("{endDate}",sumMax+"");
                JSONObject orderObject = ToolsHttp.tryGetJson(orderUrl,3,2000,false);

                if (orderObject == null){
                    logger.info("数据没有找到，可能超时了。。。。");
                    result.put("code","119");
                    result.put("msg","数据没有找到，可能超时了。。。。");
                    return result;
                }

                //获取data
                JSONObject data =orderObject.getJSONObject("data");



                //数据保存之前先将已经同步的数据清理下。然后在保存。这样的效率比较修改高 （先删除再保存，效率高于修改）

                map.put("levelMax",levelMax);
                if (startDate > 0) {
                    map.put("sumMax", sumMax);
                }
                map.put("date",startDate);
                int delete = vstChannelLevelMapper.deleteChannelLevel(map);
                if (delete > 0){
                    logger.info("删除数据："+delete+"条");
                }

                //封装数据
                List<VstChannelLevel> list = new ArrayList<VstChannelLevel>();

                for (Map<String,Object> sumsMap: sums) {
                    VstChannelLevel level =new VstChannelLevel();
                    //首先获取统计时间
                    int date = Integer.parseInt(sumsMap.get("vst_usc_date").toString());
                    //获取渠道
                    String channel = sumsMap.get("vst_usc_channel")==null?"":sumsMap.get("vst_usc_channel").toString();
                    //获取包
                    String pkg = sumsMap.get("vst_usc_pkg")==null?"":sumsMap.get("vst_usc_pkg").toString();
                    //获取累计用户数
                    Long sumAmount = Long.parseLong(sumsMap.get("vst_usc_amount")==null?"0":sumsMap.get("vst_usc_amount").toString());




                    //从各个表中获取数据，组成获取数据的key
                    String key = ToolsEncrypt.getMD5Encrypt(pkg+channel+date);


                    //获取第二张表中的数据-活跃渠道用户统计表
                    //活跃用户数
                    Long activeAmount = 0L;
                    Map<String,Object> activeMaps = activeMap.get(key);
                    if (activeMaps != null && activeMaps.size() > 0) {
                        JSONObject activeObject = new JSONObject(activeMaps);
                        if (activeObject != null) {
                            activeAmount = activeObject.getLong("vst_uac_amount");
                        }
                    }

                    //获取第三张表中的数据-新增渠道用户统计表
                    //新增用户数
                    Long addAmount = 0L;
                    Map<String,Object> addMaps = addMap.get(key);
                    if (addMaps != null && addMaps.size() > 0) {
                        JSONObject addObject = new JSONObject(addMaps);
                        if (addObject != null) {
                            addAmount = addObject.getLong("vst_uac_amount");
                        }
                    }

                    //获取第四张表的数据-季质量用户统计数据
                    //获取季新增用户数
                    Long seaseAddAmount = 0L;
                    //获取季质量用户数
                    Long seasonAmount = 0L;
                    //获取季质量用户占比
                    String seasonRadio = "0.00%";
                    Map<String,Object> userLevelMaps = userLevelMap.get(key);
                    if (userLevelMaps != null && userLevelMaps.size() >0 ) {
                        JSONObject levelObject = new JSONObject(userLevelMaps);
                        if (levelObject != null) {
                            seaseAddAmount = levelObject.getLong("vst_ulc_add_amount");
                            seasonAmount = levelObject.getLong("vst_ulc_season_amount");
                            seasonRadio = levelObject.getString("vst_ulc_season_radio");
                        }
                    }

                    //获取第五表的数据-留存用户统计-渠道维度
                    //获取日次留存用户
                    Long retainAmount = 0L;
                    //获取次周留存用户
                    Long retainWeekAmount = 0L;
                    //获取次月留存用户
                    Long retainMonthAmount = 0L;
                    //获取季留存用户数
                    Long retainSeasonAmount = 0L;
                    Map<String,Object> retainMaps = retainMap.get(key);
                    if (retainMaps != null && retainMaps.size() > 0) {
                        JSONObject retainObject = new JSONObject(retainMaps);
                        if (retainObject != null) {
                            retainAmount = retainObject.getLong("vst_urc_amount");
                            retainWeekAmount = retainObject.getLong("vst_urc_week_amount");
                            retainMonthAmount = retainObject.getLong("vst_urc_month_amount");
                            retainSeasonAmount = retainObject.getLong("vst_urc_season_amount");
                        }
                    }


                    //获取第六章表数据-渠道汇总播放统计
                    //播放量
                    Long mtpcVv = 0L;
                    //播放用户数
                    Long mtpcUv = 0L;
                    //人均播放量
                    String mtpcAvg = "0.0";
                    //播放总时长，单位秒
                    Long mtpcPlayTime = 0L;
                    //人均播放时长
                    Long mtpcPlayTimeAvg = 0L;
                    //获取数据
                    Map<String,Object> playMaps = playMap.get(key);
                    if (playMaps != null && playMaps.size() >0) {
                        JSONObject playObject = new JSONObject(playMaps);
                        if (playObject != null) {
                            mtpcVv = playObject.getLong("vst_mtpc_vv");
                            mtpcUv = playObject.getLong("vst_mtpc_uv");
                            mtpcAvg = playObject.getString("vst_mtpc_avg");
                            mtpcPlayTime = playObject.getLong("vst_mtpc_playtime");
                            mtpcPlayTimeAvg = playObject.getLong("vst_mtpc_playtime_avg");
                        }
                    }

                    //封装数据到实体了

                    //主键
                    level.setVst_cl_id(ToolsRandom.getRandom(10));
                    //统计日期
                    level.setVst_cl_date(date);
                    //渠道
                    level.setVst_cl_channel(channel);
                    //包名
                    level.setVst_cl_pkg(pkg);

                    //累计用户
                    level.setVst_cl_user_sum(sumAmount);
                    //活跃用户
                    level.setVst_cl_user_active(activeAmount);
                    //新增用户
                    level.setVst_cl_user_add(addAmount);
                    //季新增用户
                    level.setVst_cl_user_add_season(seaseAddAmount);
                    //季质量用户
                    level.setVst_cl_user_level_season(seasonAmount);
                    //质量率
                    level.setVst_cl_user_radio(seasonRadio);
                    //次日留存用户
                    level.setVst_cl_user_retain_day(retainAmount);
                    //周留存用户
                    level.setVst_cl_user_retain_week(retainWeekAmount);
                    //月留存用户
                    level.setVst_cl_user_retain_month(retainMonthAmount);
                    //季度留存用户
                    level.setVst_cl_user_retain_season(retainSeasonAmount);
                    //播放量
                    level.setVst_cl_vv(mtpcVv);
                    //播放用户
                    level.setVst_cl_uv(mtpcUv);
                    //人均播放量
                    level.setVst_cl_avg(mtpcAvg);
                    //播时长，单位秒
                    level.setVst_cl_playtime(mtpcPlayTime);
                    //人均播时长，单位秒
                    level.setVst_cl_playtime_avg(mtpcPlayTimeAvg);


                    if (("net.myvst.v2").equals(pkg)){
                        //获取订单数。
                        //首先根据统计时间获取各渠道的数据
                        JSONObject timeObject= data.getJSONObject(date+"");
                        if (timeObject != null){
                            //获取渠道数据
                            JSONObject object = timeObject.getJSONObject(channel);
                            if (object != null){
                                //获取订单数
                                Long orders = object.getLong("nums");
                                level.setVst_cl_orders(orders);

                                //获取订单总金额
                                Long price = object.getLong("prices");
                                level.setVst_cl_orders_amount(price);

                                //获取订单客单价
                                String pctPrice = object.getString("pctPrice");
                                level.setVst_cl_orders_price(pctPrice);

                                //获取订单均价
                                String avgPrice = object.getString("avgPrice");
                                level.setVst_cl_orders_price_avg(avgPrice);

                            }else {
                                level.setVst_cl_orders(0L);
                                level.setVst_cl_orders_amount(0L);
                                level.setVst_cl_orders_price("0");
                                level.setVst_cl_orders_price_avg("0");
                            }

                        }else {
                            level.setVst_cl_orders(0L);
                            level.setVst_cl_orders_amount(0L);
                            level.setVst_cl_orders_price("0");
                            level.setVst_cl_orders_price_avg("0");
                        }

                    }else if ("com.vst.itv52.v1".equals(pkg)){
                        level.setVst_cl_orders(0L);
                        level.setVst_cl_orders_amount(0L);
                        level.setVst_cl_orders_price("0");
                        level.setVst_cl_orders_price_avg("0");
                    }else {
                        logger.info("包："+pkg+"无需同步");
                        continue;
                    }




                    //新增时间，13位毫秒数
                    level.setVst_cl_addtime(System.currentTimeMillis());
                    level.setVst_cl_creator("KAI ADD");
                    level.setVst_cl_uptime(0L);
                    level.setVst_cl_updator("");

                    list.add(level);

                    //如果数据有2000条，则保存到数据库中
                    if (list.size() >= 2000){
                        int count =  vstChannelLevelMapper.batchVstChannelLevel(list);
                        if (count > 0){
                            logger.info("数据保存成功。此次保存数据:"+list.size()+"条");
                            sum = sum + list.size();

                        }else {
                            logger.info("数据保存失败。此次保存数据失败:"+list.size()+"条");
                        }
                        list.clear();
                    }




                }

                //如果list中还有数据，则将这些数据保存到数据库
                if (list.size()>0){
                    int count =  vstChannelLevelMapper.batchVstChannelLevel(list);
                    if (count > 0){
                        logger.info("数据保存成功。此次保存数据:"+list.size()+"条");
                        sum = sum + list.size();
                    }else {
                        logger.info("数据保存失败。此次保存数据失败:"+list.size()+"条");
                    }
                    list.clear();
                }

                result.put("code",200);
                result.put("msg","数据同步成功: 本次同步数据:"+sum+"条");

            }else {
                logger.info(">>>>>>>貌似vst_user_sum_channel数据为空");

                result.put("code",201);
                result.put("msg","未找到同步数据");
            }



        }catch (Exception e){
            logger.info("系统异常错误。。。。。");
            e.printStackTrace();
        }
        return  result;

    }


}
