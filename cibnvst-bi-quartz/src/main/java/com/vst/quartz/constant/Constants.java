package com.vst.quartz.constant;

/**
 * @author kai
 * 常量定义层
 * TODO: 2018/4/28
 */
public class Constants {

    /**
     * 任务组名
     */
    public final static String JOB_GROUP_NAME="job_group";

    /**
     * 触发组名
     */
    public final static String TRIGGER_GROUP_NAME="trigger_group";

    /**
     * 备份表后面需要加入的名称
     */
    public final static String BACK_UP="_backUp";

    /**
     * 限制每次保存时数据量，每次2000条
     */
    public final static Integer COUNT=2000;



    /**
     * 备份文件保存目录
     */

//    public final static String URL="/vst/cibnvst-bi-quartz/file/";

    public final static String URL="/Users/kai/Desktop/mac/";

    public final static String  ORDERS = "http://admin.cibnvst.com/cibnvst-sysadmin/vipOrder/ajaxGetOrderCount.action?startDate={startDate}&endDate={endDate}";

}
