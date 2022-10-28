package com.vst.quartz.utils.file;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author kai 
 * 流关闭工具类
 * TODO: 2018/4/3 18：36：40
 */
public class CloseableUtil {

    public static void close(Closeable closeable){
        try {
            if(closeable!=null){
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void close(Closeable... closeables){
        try {
            if (closeables.length > 0){
                for (Closeable c: closeables) {
                    c.close();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    private static void toZipType(){

    }

}
