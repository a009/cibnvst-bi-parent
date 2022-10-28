package com.vst.quartz.utils.mail;


import com.vst.common.pojo.VstQuartzConfig;
import com.vst.quartz.utils.ip.IpTools;

import java.util.List;
import java.util.Map;

/**
 * @author kai 
 * 邮件内容发送的模版
 * TODO: 2018/3/23
 */
public class EmailModule {

    public static String module(String title,String name,String method,Exception e){
        StringBuilder builder = new StringBuilder();
        builder.append("异常模块:"+title+">>>>>>>><br/>");
        builder.append("负责人:"+name+";<br/>");
        builder.append("出现异常的方法名:"+method+"<br/>");
        builder.append("异常信息:<br/>");
        builder.append(e);
        return builder.toString();
    }


    /**
     * kai
     * 邮件发送的异常信息模版(发送异常在哪个方法名，和ip)
     * @param title 属于哪个功能。例如条件备份。全量备份。字段备份等
     * @param name 负责人
     * @param method 方法名
     * @param e 异常信息
     * @return 返回值string
     */
    public static String createIpModule(String title,String name,String method,Exception e){
        String content = module(title, name, method, e);
        StringBuilder builder = new StringBuilder(content);
        builder.append("ip地址:"+ IpTools.getAddress()+";<br/>");
        return builder.toString();
    }


    /**
     * kai
     * 邮件发送的异常信息模版，   不包含ip
     * TODO: 2018/3/23 11:00:30
     * @param title 属于哪个功能。例如条件备份。全量备份。字段备份等
     * @param name 负责人
     * @param method 方法名
     * @param e 异常信息
     * @return 返回值string
     */
    public static String createNoIpModule(String title,String name,String method,Exception e){
        return module(title,name,method,e);
    }

    /**
     * kai
     * 提示模块，主要作用于任务配置是没有配置完整信息
     * @param title 提示的问题
     * @param name 负责人
     * @param method 检测出问题的方法
     * @param content 内容
     * @return 返回值String
     */
    public static String creteTipsModule(String title,String name,String method,String content){
        StringBuilder builder = new StringBuilder();
        builder.append("提示模块:"+title+">>>>>>>><br/>");
        builder.append("负责人:"+name+";<br/>");
        builder.append("检测出问题的方法:"+method+"<br/>");
        builder.append("原因:<br/>"+content+"");
        return builder.toString();
    }

    /**
     * kai
     * 提示模版。任务暂停提示模版，邮件发送后，会暂停任务
     * TODO: 2018/4/3 12：06：39
     */

    public static void cretePauseJobModule(String title, String name, String method, String content, VstQuartzConfig config){

        //编辑邮件发送的基本细腻下
        Email email = new Email();

        email.setTitle("警告。。。。任务名:"+config.getVst_qc_name());

        StringBuilder builder = new StringBuilder();
        builder.append("提示模块:"+title+">>>>>>>><br/>");
        builder.append("负责人:"+name+";<br/>");
        builder.append("检测出问题的方法:"+method+"<br/>");
        builder.append("内容:<br/>"+content+"");

        email.setContent(builder.toString());

        //发送邮件
        EmailTools.sendEmail(email);
    }



    /**
     * kai
     * 邮件发送内容为html模版
     * TODO: 2018/3/23 11:08:11
     * @param title 标题
     * @param maps 需要显示的数据
     * @return 返回值string
     */
    public static String createHtmlModule(String title, List<Map<String, Object>> maps){
        return null;
    }



}
