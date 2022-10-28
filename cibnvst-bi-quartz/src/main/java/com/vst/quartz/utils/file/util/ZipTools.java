package com.vst.quartz.utils.file.util;

import com.vst.quartz.utils.file.CloseableUtil;
import com.vst.quartz.utils.mail.Email;
import com.vst.quartz.utils.mail.EmailModule;
import com.vst.quartz.utils.mail.EmailTools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author kai
 * 压缩文件或者文件夹
 * TODO: 2018/4/20 14:53:35
 */
public class ZipTools {

    private static Log logger = LogFactory.getLog(ZipTools.class);


    /**
     * kai
     * 单文件压缩
     * TODO: 2018/4/20 16:12:39
     * @param srcFilePath 源文件路径
     * @param destFilePath 压缩后目标文件路径
     * @param jobName 任务名
     * @param format 文件后缀，用于判断只压缩哪种格式文件。例如只压缩 JSON:"json",例如只压缩sql:"sql"等
     */
    public static void  toZip(String srcFilePath, String destFilePath,String jobName,String... format){
        File srcFile= new File(srcFilePath);

        if (!srcFile.exists()){
            logger.info("提供的文件路径不存在");
            return;
        }

        File destFile = new File(destFilePath+File.separator+srcFile.getName()+".zip");

        FileOutputStream stream = null;
        ZipOutputStream outputStream = null;
        String str="";
        try {
            stream = new FileOutputStream(destFile);
            outputStream = new ZipOutputStream(stream);

            toZipType(srcFile,outputStream,str,jobName,format);
        }catch (IOException e){
            //发送邮件提醒
            Email email = new Email();
            email.setTitle("压缩文件夹为Zip创建时异常：任务名："+jobName);
            //获取当前方法
            String method = Thread.currentThread() .getStackTrace()[1].getMethodName();
            email.setContent(EmailModule.createIpModule("文件夹压缩","LiuKai",method,e));
            EmailTools.sendEmail(email);
            e.printStackTrace();
        }finally {
            CloseableUtil.close(outputStream,stream);
        }
    }

    /**
     * kai
     * 批量文件压缩
     * TODO: 2018/4/20 19:00:43
     * @param srcFilePath 源文件集合
     * @param destFilePath 压缩后的目标路径
     * @param format 文件后缀，用于判断只压缩哪种格式文件。例如只压缩 JSON:"json",例如只压缩sql:"sql"等
     */
    public static void toZip(Collection<String> srcFilePath, String destFilePath,String jobName,String... format){
        if (srcFilePath.size()<=0){
            logger.info("提供的文件路径为空");
            return;
        }

        for (String path: srcFilePath) {
           toZip(path,destFilePath,jobName,format);
        }
    }


    /**
     * kai
     * 区分文件类型，判断是文件夹还是文件
     * TODO: 2018/4/20 16:30:39
     * @param src 源文件
     * @param zos 压缩
     * @param str 保存的文件路径
     * @param jobName 任务名
     * @param format 文件后缀，用于判断只压缩哪种格式文件。例如只压缩 JSON:"json",例如只压缩sql:"sql"等
     */
    private static void toZipType(File src, ZipOutputStream zos,String str,String jobName,String... format) {
        if (!src.exists()){
            logger.info("文件不存在");
            return;
        }

        //判断是文件夹还是文件

        if (src.isFile()){
            //文件，调用toZipFile方法
            toIsFormat(src,zos,str,jobName,format);
        }else if (src.isDirectory()){
            //文件夹，调用toZipFolder方法
            toZipFolder(src,zos,str,jobName,format);
        }else {
            //文件类型不符合
            logger.info("文件类型不符合");
        }
    }

    /**
     * kai
     * 过滤掉不需要压缩的文件格式
     * @param file 源文件
     * @param zos zip格式文件压缩
     * @param str 保存的文件路径
     * @param format 文件后缀，用于判断只压缩哪种格式文件。例如只压缩 JSON:"json",例如只压缩sql:"sql"等
     */
    public static void  toIsFormat(File file, ZipOutputStream zos,String str,String jobName,String... format){
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

        toZipFile(file,zos,str,jobName);

    }

    /**
     * kai
     * 压缩文件
     * TODO: 2018/4/20 14：54：40
     * @param file file 源文件
     * @param zos zos zip格式文件压缩
     * @param str str 保存的文件路径
     * @param jobName 任务名
     */
    private static void toZipFile(File file, ZipOutputStream zos,String str,String jobName){



        if (!file.exists()){
            logger.info("文件不存在:"+file.getPath());
            return;
        }

        BufferedInputStream inputStream=null;

        try {
            //使用输入流读取文件
             inputStream = new BufferedInputStream(new FileInputStream(file));

            //创建压缩文件中的条目
            ZipEntry entry = new ZipEntry(str+file.getName());

            //将创建好的条目加入的压缩文件中
            zos.putNextEntry(entry);

            int count;

            byte[] bytes = new byte[1024];

            while ((count = inputStream.read(bytes)) != -1){
                zos.write(bytes,0,count);
            }

            zos.closeEntry();


        }catch (IOException e){
            //发送邮件提醒
            Email email = new Email();
            email.setTitle("压缩文件为Zip时出现异常：任务名："+jobName);
            //获取当前方法
            String method = Thread.currentThread() .getStackTrace()[1].getMethodName();
            email.setContent(EmailModule.createIpModule("文件压缩","LiuKai",method,e));
            EmailTools.sendEmail(email);
            e.printStackTrace();
        }finally {
            //关闭流
            CloseableUtil.close(inputStream);
        }

    }

    /**
     * kai
     * 压缩文件夹
     * TODO: 2018/4/20 15：17：44
     * @param file 源文件
     * @param zos zip压缩格式
     * @param str 保存的文件路径
     * @param jobName 任务名
     * @param format 文件后缀，用于判断只压缩哪种格式文件。例如只压缩 JSON:"json",例如只压缩sql:"sql"等
     */
    private static void  toZipFolder(File file,ZipOutputStream zos,String str,String jobName,String... format){
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
            try {

                //创建压缩文件中的条目
                ZipEntry entry = new ZipEntry(file.getName()+File.separator);

                zos.putNextEntry(entry);


            }catch (IOException e){
                //发送邮件提醒
                Email email = new Email();
                email.setTitle("压缩文件夹为Zip时出现异常：任务名："+jobName);
                //获取当前方法
                String method = Thread.currentThread() .getStackTrace()[1].getMethodName();
                email.setContent(EmailModule.createIpModule("文件夹压缩","LiuKai",method,e));
                EmailTools.sendEmail(email);
                e.printStackTrace();
            }
        }

        for (File f: files) {
            toZipType(f,zos,str+file.getName()+File.separator,jobName,format);
        }

    }
}
