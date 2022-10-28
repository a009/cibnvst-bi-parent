package com.vst.quartz.utils.file.util;

import com.vst.quartz.utils.file.CloseableUtil;
import com.vst.quartz.utils.mail.Email;
import com.vst.quartz.utils.mail.EmailModule;
import com.vst.quartz.utils.mail.EmailTools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.zip.GZIPOutputStream;

/**
 * @author kai 
 * 将文件压缩成gzip格式
 * TODO: 2018/4/23
 */
public class GzipTools {

    private static Log logger = LogFactory.getLog(GzipTools.class);

    /**
     * kai
     * 单文件压缩成jar
     * @param srcFilePath 源文件路径
     * @param destFilePath 压缩后目标文件路径
     * @param jobName 任务名
     * @param format 文件后缀，用于判断只压缩哪种格式文件。例如只压缩 JSON:"json",例如只压缩sql:"sql"等
     */
    public static void toGzip(String srcFilePath,String destFilePath,String jobName,String... format){

        File srcFile = new File(srcFilePath);

        if (!srcFile.exists()){
            logger.info("提供的文件路径不存在");
            return;
        }

        File destFile = new File(destFilePath+File.separator+srcFile.getName()+".Gzip");

        FileOutputStream out = null;
        GZIPOutputStream outputStream = null;

        String str="";

        try {
            out = new FileOutputStream(destFile);
            outputStream = new GZIPOutputStream(out);
            toGzipType(srcFile,outputStream,str,jobName,format);

        }catch (IOException e){
            //发送邮件提醒
            Email email = new Email();
            email.setTitle("压缩文件夹为Gzip创建时异常：任务名："+jobName);
            //获取当前方法
            String method = Thread.currentThread() .getStackTrace()[1].getMethodName();
            email.setContent(EmailModule.createIpModule("文件压缩","LiuKai",method,e));
            EmailTools.sendEmail(email);
            e.printStackTrace();
        }finally {
            CloseableUtil.close(outputStream,out);
        }

    }

    /**
     * kai
     * 区分文件类型，判断是文件夹还是文件
     * TODO: 2018/4/20 16:30:39
     * @param src 源文件
     * @param gos 压缩
     * @param str 保存的文件路径
     * @param jobName 任务名
     * @param format 文件后缀，用于判断只压缩哪种格式文件。例如只压缩 JSON:"json",例如只压缩sql:"sql"等
     */
    private static void toGzipType(File src, GZIPOutputStream gos, String str, String jobName,String... format) {
        if (!src.exists()){
            logger.info("文件不存在");
            return;
        }

        //判断是文件夹还是文件

        if (src.isFile()){
            //文件，调用toZipFile方法
            toIsFormat(src,gos,jobName,format);
        }else if (src.isDirectory()){
            //文件夹，调用toZipFolder方法
            toGzipFolder(src,gos,str,jobName,format);
        }else {
            //文件类型不符合
            logger.info("文件类型不符合");
        }
    }

    /**
     * kai
     * 过滤掉不需要压缩的文件格式
     * @param file 源文件
     * @param gos zip格式文件压缩
     * @param jobName 文件路径
     * @param format 文件后缀，用于判断只压缩哪种格式文件。例如只压缩 JSON:"json",例如只压缩sql:"sql"等
     */
    public static void  toIsFormat(File file, GZIPOutputStream gos,String jobName,String... format){
        //判断压缩的文件类型，只压缩format这种类型的文件
        boolean flag = false;
        for (String f: format) {
            if (file.getName().contains(f)){
                flag =true;
                break;
            }
        }
        if (!flag){
            return ;
        }

        toGzipFile(file,gos,jobName);

    }

    /**
     * kai
     * 压缩文件
     * TODO: 2018/4/20 14：54：40
     * @param file file 源文件
     * @param gos zos zip格式文件压缩
     * @param jobName 任务名
     */
    private static void toGzipFile(File file, GZIPOutputStream gos,String jobName){



        if (!file.exists()){
            logger.info("文件不存在:"+file.getPath());
            return;
        }

        BufferedInputStream inputStream=null;

        try {
            //使用输入流读取文件
            inputStream = new BufferedInputStream(new FileInputStream(file));

            int count;

            byte[] bytes = new byte[1024];

            while ((count = inputStream.read(bytes)) != -1){
                gos.write(bytes,0,count);
            }

            gos.finish();

            gos.flush();


        }catch (IOException e){
            //发送邮件提醒
            Email email = new Email();
            email.setTitle("压缩文件为Gzip时出现异常：任务名："+jobName);
            //获取当前方法
            String method = Thread.currentThread() .getStackTrace()[1].getMethodName();
            email.setContent(EmailModule.createIpModule("文件压缩","LiuKai",method,e));
            EmailTools.sendEmail(email);
            e.printStackTrace();
        }finally {
            //关闭流
            CloseableUtil.close(gos,inputStream);
        }

    }

    /**
     * kai
     * 压缩文件夹
     * TODO: 2018/4/20 15：17：44
     * @param file 源文件
     * @param gos zip压缩格式
     * @param str 保存的文件路径
     * @param jobName 任务名
     * @param format 文件后缀，用于判断只压缩哪种格式文件。例如只压缩 JSON:"json",例如只压缩sql:"sql"等
     */
    private static void  toGzipFolder(File file,GZIPOutputStream gos,String str,String jobName,String... format){
        if (!file.exists()){
            logger.info("文件夹不存在"+file.getPath());
            return;
        }

        //获取目录中指定规则的内容
        File[] files = file.listFiles();
        if (files == null){
            logger.info("目录中无内容");
            return;
        }
        if (files.length == 0){
            logger.info("文件中暂无数据压缩");
            return;
        }

        for (File f: files) {
            toGzipType(f,gos,str+file.getName()+File.separator,jobName,format);
        }

    }

}
