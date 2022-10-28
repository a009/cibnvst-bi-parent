package com.vst.quartz.utils.ip;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author kai
 * TODO: 2018/3/23
 * 服务器ip操作
 */
public class IpTools {

    private static InetAddress address;

    /**
     * kai
     * 获取IP地址
     * TODO: 2018/3/23 10：28：16
     * @return 返回值string
     */
    public static String getAddress(){
        String ip = "";
        try {
            address = InetAddress.getLocalHost();

            //获取ip
            ip = address.getHostAddress();

        }catch (UnknownHostException e){
            e.fillInStackTrace();
            ip = "获取失败";
        }

        return ip;

    }
}
