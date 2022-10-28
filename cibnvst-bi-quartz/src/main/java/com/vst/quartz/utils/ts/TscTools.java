package com.vst.quartz.utils.ts;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.pojo.VstQuartzConfig;
import com.vst.common.tools.string.ToolsString;
import com.vst.quartz.constant.ApiCode;
import com.vst.quartz.constant.ApiMagic;
import com.vst.quartz.service.clean.BackUpService;
import com.vst.quartz.utils.bean.SpringContextUtil;
import com.vst.quartz.utils.mail.Email;
import com.vst.quartz.utils.mail.EmailModule;
import com.vst.quartz.utils.mail.EmailTools;
import com.vst.quartz.utils.page.PageBean;
import com.vst.quartz.utils.table.TableTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author kai
 * 用于处理事务失效的
 * 防止同一个类中一个没有事务的方法，调用一个有事务的方法事，事务失效
 * 如果你想写在同一个类中，那最好不要使用spring声明式事务，使用编程式事务
 * TODO: 2018/4/2 12：13：13
 */
public class TscTools {

    private BackUpService backUpService;

    public TscTools() {
        BackUpService service = SpringContextUtil.getBean(BackUpService.class);
        if (service == null){
            //发送邮件提醒
            Email email = new Email();
            email.setTitle("温馨提示。。。。。");
            //获取当前方法
            String method = Thread.currentThread() .getStackTrace()[1].getMethodName();
            String content="获取bean注入失败。。。。";
            email.setContent(EmailModule.creteTipsModule("bean注入失败","kai",method,content));
            EmailTools.sendEmail(email);

            return;
        }

        this.backUpService = service;
    }


    /**
     * kai
     * 这个方法主要用于service层在back*方法和数据操作方法中间搭建桥梁
     * 尽量一些操作可以不再savaData方法中写的，尽量在这个；里面编写
     * TODO: 2018/4/4 10:50:30
     * @param maps 备份的数据
     * @param filed 备份的字段
     * @param map 根据字段获取字段的类型
     * @param flag 用户判断备份之后是否需要执行原表清理操作
     * @param bean 分页属性
     * @param config 任务配置对象
     * @param strings 保存需要备份成sql文件的sql
     * @return 返回值 JsonObject
     */
    public JSONObject betweenSave(List<Map<String,Object>> maps, String filed, Map<String,String> map
            , Boolean flag, PageBean bean, VstQuartzConfig config, String db, List<String> strings){

        JSONObject object = new JSONObject();

        String backSource = config.getVst_qc_target_source_name();

        if (ToolsString.isEmpty(backSource)){
            object.put("code", ApiCode.API_CODE_119);
            object.put("msg","你选择了备份操作，却没有填写目标数据源");

            //发送邮件提醒
            Email email = new Email();
            email.setTitle("温馨提示。。。。。任务名:"+config.getVst_qc_name());
            //获取当前方法
            String method = Thread.currentThread() .getStackTrace()[1].getMethodName();
            String content="你选择了备份操作，却没有填写目标数据源。";
            email.setContent(EmailModule.creteTipsModule("数据源配置问题","kai",method,content));
            EmailTools.sendEmail(email);
            return object;
        }

        String[] dbSite;
        if (backSource.contains(ApiMagic.API_MAGIC_COMMA)){
            dbSite=backSource.split(ApiMagic.API_MAGIC_COMMA);
        }else {
            dbSite = new String[]{backSource};
        }
        //获取数据库中保存目标表名的字段
        JSONObject result = JSONObject.parseObject(config.getVst_qc_tarbet_table_name());
        //获取需要备份对应的表名
        List<String> list = getList(result,dbSite);


        //获取需要备份的成sql文件的sql
        String stringSql = TableTools.saveData(config.getVst_qc_table(),maps,filed,map);
        if (!ToolsString.isEmpty(stringSql)){
            strings.add(stringSql.replace("replace","INSERT"));
        }


        //保存数据到备份表
        for (String tableName: list) {
            //获取拼接的备份的sql
            String resultSql=TableTools.saveData(tableName,maps,filed,map);
            if (ToolsString.isEmpty(resultSql)){
                continue;
            }
            object = backUpService.saveBatch(tableName, maps, filed, map, flag, bean, config, dbSite, db,result,resultSql);
        }
        return object;

    }

    /**
     * kai
     * 查询全部需要备份的表名
     * TODO: 2018/4/19 18:22:10
     * @param object 保存的同步表名称
     * @param dbSite 同步的目标数据源
     * @return 返回值List
     */
    public List<String> getList(JSONObject object,String[] dbSite){
        List<String> list = new ArrayList<>();

        for (String db: dbSite) {
            String dbName = object.getString(db);
            if (ToolsString.isEmpty(dbName)){
                continue;
            }
            if (dbName.contains(ApiMagic.API_MAGIC_VERTICAL)) {
                String[] name = dbName.split(ApiMagic.API_MAGIC_ESCAPING_VERTICAL);
                for (String n: name){
                    if (!list.contains(n)){
                        list.add(n);
                    }
                }
            }else {
                if (!list.contains(dbName)){
                    list.add(dbName);
                }

            }

        }
        return list;
    }




}
