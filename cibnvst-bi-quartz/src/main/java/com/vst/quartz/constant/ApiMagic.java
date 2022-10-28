package com.vst.quartz.constant;

/**
 * @author kai
 * 定义一些特殊符号常量，防止魔法值出现
 * 例如:data.contains(",") contains里面直接写 "," 就是魔法值的写法
 * TODO: 2018/4/24
 */
public class ApiMagic {

    /**
     * 特殊符号点
     */
    public static final String API_MAGIC_POINT = ".";

    /**
     * 逗号
     */
    public static final String API_MAGIC_COMMA = ",";

    /**
     * 斜杠
     */
    public static final String API_MAGIC_SLASH = "/";

    /**
     * 竖杠
     */
    public static final String API_MAGIC_VERTICAL = "|";

    /**
     * 竖（转义） 为什么加一个转义，因为split是使用正则匹配，所以需要转义
     */
    public static final String API_MAGIC_ESCAPING_VERTICAL = "\\|";


    /**
     * code 返回值状态
     */
    public static final String API_MAGIC_CODE = "code";

    /**
     * msg 返回值细腻下
     */
    public static final String API_MAGIC_MSG = "msg";


    /**
     * date 返回值时间
     */
    public static final String API_MAGIC_DATE = "date";


    /**
     * bytes
     */
    public static final Integer API_MAGIC_1024 = 1024;





}
