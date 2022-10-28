package com.vst.quartz.utils.file.util;

import com.vst.common.tools.date.ToolsDate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;

/**
 * @author kai
 * 用于创建文件夹
 * TODO: 2018/4/20 14：47：55
 */
public class CreateFolder {

    private static Log logger = LogFactory.getLog(CreateFolder.class);

    /**
     * kai
     * 创建文件夹
     * @param path 路径
     * @return 返回值boolean
     */
    private static boolean createFolder(String path){
        File file=new File(path);
        //判断文件夹(目录)是否存在，不存在则创建
        if (!file.exists() && !file.isDirectory()){
            return file.mkdir();
        }else {
            return true;
        }

    }


    /**
     * kai
     * 创建文件
     * @param path 路径
     * @return 返回值boolean
     */
    private static boolean createFile(String path){
        //创建文件
        File file=new File(path);
        boolean flag = false;
        try {
            if (!file.exists()){
                flag =  file.createNewFile();
            }else {
                flag = true;
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return flag;

    }

    /**
     * kai
     * 创建保存的文件
     * TODO: 2018/4/20 11:09:10
     * @param url 路径
     * @param tableName 文件名
     * @param format 文件后缀
     */
    public static String getFile(String url,String tableName,String format) {

        //保存地址为保存目录加表名加当前时间
        //拼接文件夹(表名)
        String folderUrl = url + tableName + "/";

        //第二级文件夹(表名+时间(YYYY-MM-dd))
        String fileTwoUrl = folderUrl + "/" + tableName + ToolsDate.getCurrDate() + "/";

        //第三级是文件（表名+时间(yyyy-MM-dd HH:mm:ss)）
        String fileUrl = fileTwoUrl + tableName + ToolsDate.getCurrDate("yyyyMMddHHmmss") + "."+format;

        boolean flag = createFolder(folderUrl);

        if (flag) {
            logger.info(folderUrl + "创建成功");
            //创建二级目录
            flag = createFolder(fileTwoUrl);
            if (flag) {
                logger.info(fileTwoUrl + "创建成功");
                //第三级创建文件
                flag = createFile(fileUrl);
                if (flag) {
                    logger.info(fileUrl + "创建成功");
                } else {
                    logger.info(fileUrl + "创建成功失败");
                }
            } else {
                logger.info(fileTwoUrl + "创建成功失败");
            }
        } else {
            logger.info(folderUrl + "创建成功失败");
        }

        return fileUrl;

    }

}
