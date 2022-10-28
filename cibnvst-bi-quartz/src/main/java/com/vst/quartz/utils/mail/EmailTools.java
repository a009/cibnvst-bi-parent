package com.vst.quartz.utils.mail;

import com.vst.common.tools.string.ToolsString;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * @author kai
 * 邮件发送工具类
 * TODO: 2018/3/22
 */
public class EmailTools {

    /**
     *  创建邮件对象
     */
    private static MimeMessage message;

    /**
     * 用于保存邮件内容
     */
    private static MimeMultipart multipart;


//
//    //发送人的smtp协议邮箱服务器地址
//    private final static String myEmailAddress = "smtp.exmail.qq.com";
//    //发件人的邮箱账号
//    private final static String myEmailAccount = "kai.liu@91vst.com";
//    //发件人的邮箱授权码，不一定是邮箱登陆密码 如果网易邮箱需要的是授权码。腾讯邮箱就是登陆密码
//    private final static String myEmailPassword = "Liukai110";
//
//    //收件人的邮箱地址
//    private final static String reciptEmail = "1251763859@qq.com";

    /**
     * kai
     * 发送邮箱
     * TODO: 2018/3/22 11:02:00
     */
    public static void sendEmail(Email email){
        //创建一封邮件用户接受服务器参数配置
        Properties pros = new Properties();
        //*****开始properties参数配置*****//
        //使用java发邮箱的协议配置
        pros.setProperty("mail.transport.protocol", email.getSmtp());
        //发件人邮箱服务器地址
        pros.setProperty("mail.smtp.host", email.getMyEmailAddress());
        //是否需要请求认证
        pros.setProperty("mail.smtp,auth", email.getSync());
        pros.setProperty("mail.smtp.socketFactory.port", email.getPort());
        //*****properties参数配置结束*****//

        //根据参数配置，设置会话对象
        Session session = Session.getInstance(pros);
        //开启debug模式可以查看返回的日志
        session.setDebug(true);

        //写入邮件信息
        createMimeMessage(session,email);

        try {
            //根据对象获取邮箱传输对象
            Transport transport  = session.getTransport();
            //建立连接
            transport.connect(email.getMyEmailAccount(),email.getMyEmailPassword());

            //发送邮箱地址
            transport.sendMessage(message,message.getAllRecipients());
            //关闭连接
            transport.close();

        }catch ( MessagingException e){
            e.printStackTrace();
        }

    }





    /**
     * kai
     * 创建邮件
     * TODO: 2018/3/22 11：20：10
     * @param session 和服务器交互的会话
     * @param email 邮件发送的基本信息
     * @return 返回值 MimeMessage对象
     */
    private static MimeMessage createMimeMessage(Session session,Email email){
        // 1. 创建一封邮件
        message = new MimeMessage(session);
        multipart = new MimeMultipart();
        try {
            // 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
            message.setFrom(new InternetAddress(email.getMyEmailAccount(), email.getMyEmailAccount(), "UTF-8"));

            // 3. To: 收件人（可以增加多个收件人(TO)、抄送(CC)、密送(BCC)）
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email.getReciptEmail()));

            //邮件主题
            message.setSubject(email.getTitle(),"UTF-8");

            //判断抄送人是否为空
            if (!ToolsString.isEmpty(email.getCopyEmail())){
                Address[] addresses = InternetAddress.parse(email.getCopyEmail());
                message.setRecipients(MimeMessage.RecipientType.CC,addresses);
            }
            String imageId="";
            //如果图片不为空则添加图片
            if (!ToolsString.isEmpty(email.getImageUrl())){
                //附带图片的邮件
                imageId = createImageMimeMessage(email.getImageUrl());
            }

            //如果正文不是空的，则添加正文
            if (!ToolsString.isEmpty(email.getContent())){
                //+"<br/><img src='cid:"+imageId+"'/>"
               createTextMimeMessage(email.getContent());
            }

            multipart.setSubType("related");

            // 6. 设置发件时间
            message.setSentDate(new Date());


            message.setContent(multipart);


            // 7. 保存设置
            message.saveChanges();
        }catch (UnsupportedEncodingException | MessagingException e ){
            e.printStackTrace();
        }
        return message;
    }

    /**
     * LiuKai
     * 封装正文内容
     *  TODO: 2018/3/22 18:48:08
     * @param content 正文内容
     */
    private static void createTextMimeMessage(String content){
        MimeBodyPart part = new MimeBodyPart();
        try {
            part.setContent(content,"text/html;charset=Utf-8");
            multipart.addBodyPart(part);
        }catch (MessagingException e){
            e.printStackTrace();
        }


    }



    /**
     * kai
     * 创建一封html格式的邮件
     * TODO: 2018/3/22 12:26:15
     * @param session 和服务器交互的会话
     * @return 返回MimeMessage对象
     */
    private static MimeMessage createHtmlMimeMessage(Session session,String content){
        MimeMessage message = new MimeMessage(session);

        //构建一个的MiniMultipart容器类
        Multipart multipart = new MimeMultipart();

        //创建一个包含html的MimeBodyPart类
        BodyPart part = new MimeBodyPart();

        try {
            //设置html的内容
            part.setContent(content,"text/html; charset=UTF-8");

            multipart.addBodyPart(part);

            //将Multipart的内容设置称邮件形式

            message.setContent(multipart);

            //设置邮件发送的时间
            message.setSentDate(new Date());

            //保存邮件
            message.saveChanges();
        }catch (MessagingException e){
            e.printStackTrace();
        }

        return message;
    }


    /**
     * kai
     * 创建一封带图片的邮件
     * TODO: 2018/3/22 11：56：48
     * @param imageUrl 图片地址
     * @return 返回MimeMessage对象
     */
    private static String createImageMimeMessage(String imageUrl){

        //创建图片节点
        MimeBodyPart bodyPart = new MimeBodyPart();
        String imageId="";
        try {
            //使用DataHandler读取本地文件
            FileDataSource dataSource = new FileDataSource(imageUrl);
            DataHandler handler = new DataHandler(dataSource);
            //将图片添加到节点
            bodyPart.setDataHandler(handler);
            //设置图片的唯一标示
            bodyPart.setContentID(""+dataSource.getName());
            //将节点加入到容器中
            multipart.addBodyPart(bodyPart);

            imageId=dataSource.getName();
        }catch (MessagingException e){
            e.printStackTrace();
        }
        return imageId;
    }






}
