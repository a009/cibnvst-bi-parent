package com.vst.quartz.constant;

import com.vst.common.tools.date.ToolsDate;

/**
 * @author kai
 * 用于标示成功或者失败
 * TODO: 2018/3/21 14:56 40
 */
public class ApiCode {

    /**
     * 数据查询或者保存成功
     */
    public final static Integer API_CODE_200=200;

    /**
     * 传入的参数为空
     */
    public final static Integer API_CODE_119=119;

    /**
     * 查询的数据为空
     */
    public final static Integer API_CODE_202=202;

    /**
     * 异常报错
     */
    public final static Integer API_CODE_110=110;


    public static String API_CODE_TIME= ToolsDate.getCurrDate("yyyy-MM-dd HH:mm:ss");
}
