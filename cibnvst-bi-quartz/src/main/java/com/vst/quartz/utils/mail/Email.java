package com.vst.quartz.utils.mail;

/**
 * @author kai
 * 邮件基本信息配置类
 * TODO: 2018/3/22 15:00:35
 */
public class Email {

    /**
     * 发送人的smtp协议邮箱服务器地址
     */
    private String myEmailAddress = "smtp.exmail.qq.com";

    /**
     * 发件人的邮箱账号
     */
    private String myEmailAccount = "kai.liu@91vst.com";

    /**
     * 发件人的邮箱授权码，不是邮箱登陆密码
     */
    private String myEmailPassword = "Liukai110";

    /**
     * 收件人的邮箱地址
     */
    private String reciptEmail = "1251763859@qq.com";

    /**
     * 抄送人
     */
    private String copyEmail = "18664366169@163.com";

    /**
     * 使用java发邮箱的协议配置
     */
    private String smtp = "smtp";

    /**
     * SMTP邮件发送的端口
     */
    private String port = "456";

    /**
     * 是否需要请求认证 true或者false
     */
    private String sync = "true";


    /**
     * 邮件标题
     */
    private String title;

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 图片地址
     */
    private String imageUrl;



    public String getMyEmailAddress() {
        return myEmailAddress;
    }

    public void setMyEmailAddress(String myEmailAddress) {
        this.myEmailAddress = myEmailAddress;
    }

    public String getMyEmailAccount() {
        return myEmailAccount;
    }

    public void setMyEmailAccount(String myEmailAccount) {
        this.myEmailAccount = myEmailAccount;
    }

    public String getMyEmailPassword() {
        return myEmailPassword;
    }

    public void setMyEmailPassword(String myEmailPassword) {
        this.myEmailPassword = myEmailPassword;
    }

    public String getReciptEmail() {
        return reciptEmail;
    }

    public void setReciptEmail(String reciptEmail) {
        this.reciptEmail = reciptEmail;
    }

    public String getCopyEmail() {
        return copyEmail;
    }

    public void setCopyEmail(String copyEmail) {
        this.copyEmail = copyEmail;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {

        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Email{" +
                "myEmailAddress='" + myEmailAddress + '\'' +
                ", myEmailAccount='" + myEmailAccount + '\'' +
                ", myEmailPassword='" + myEmailPassword + '\'' +
                ", reciptEmail='" + reciptEmail + '\'' +
                ", copyEmail='" + copyEmail + '\'' +
                ", smtp='" + smtp + '\'' +
                ", port='" + port + '\'' +
                ", sync='" + sync + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
