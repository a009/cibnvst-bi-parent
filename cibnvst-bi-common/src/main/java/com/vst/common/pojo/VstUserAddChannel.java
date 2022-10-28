package com.vst.common.pojo;

/**
 * 新增渠道用户统计
 * @author kai
 * @// TODO: 2018/1/22
 *
 */
public class VstUserAddChannel {

    /**
     * 主键
     */
    private String vst_uac_id;

    /**
     * 统计日期
     */
    private Integer vst_uac_date;

    /**
     * 包名
     */
    private String vst_uac_pkg;

    /**
     * 渠道
     */
    private String vst_uac_channel;

    /**
     * 新增用户数
     */
    private Long vst_uac_amount;

    /**
     * 天环比
     */
    private String vst_uac_dod;

    /**
     * 周环比
     */
    private String vst_uac_wow;

    /**
     * 月环比
     */
    private String vst_uac_mom;

    /**
     * 新增时间，13位毫秒数
     */
    private Long vst_uac_addtime;

    /**
     * 创建人
     */
    private String vst_uac_creator;

    /**
     * 修改时间，13位毫秒数
     */
    private Long vst_uac_uptime;

    /**
     * 修改人
     */
    private String vst_uac_updator;

    /**
     * 备注
     */
    private String vst_uac_summary;


    public String getVst_uac_id() {
        return vst_uac_id;
    }

    public void setVst_uac_id(String vst_uac_id) {
        this.vst_uac_id = vst_uac_id;
    }

    public Integer getVst_uac_date() {
        return vst_uac_date;
    }

    public void setVst_uac_date(Integer vst_uac_date) {
        this.vst_uac_date = vst_uac_date;
    }

    public String getVst_uac_pkg() {
        return vst_uac_pkg;
    }

    public void setVst_uac_pkg(String vst_uac_pkg) {
        this.vst_uac_pkg = vst_uac_pkg;
    }

    public String getVst_uac_channel() {
        return vst_uac_channel;
    }

    public void setVst_uac_channel(String vst_uac_channel) {
        this.vst_uac_channel = vst_uac_channel;
    }

    public Long getVst_uac_amount() {
        return vst_uac_amount;
    }

    public void setVst_uac_amount(Long vst_uac_amount) {
        this.vst_uac_amount = vst_uac_amount;
    }

    public String getVst_uac_dod() {
        return vst_uac_dod;
    }

    public void setVst_uac_dod(String vst_uac_dod) {
        this.vst_uac_dod = vst_uac_dod;
    }

    public String getVst_uac_wow() {
        return vst_uac_wow;
    }

    public void setVst_uac_wow(String vst_uac_wow) {
        this.vst_uac_wow = vst_uac_wow;
    }

    public String getVst_uac_mom() {
        return vst_uac_mom;
    }

    public void setVst_uac_mom(String vst_uac_mom) {
        this.vst_uac_mom = vst_uac_mom;
    }

    public Long getVst_uac_addtime() {
        return vst_uac_addtime;
    }

    public void setVst_uac_addtime(Long vst_uac_addtime) {
        this.vst_uac_addtime = vst_uac_addtime;
    }

    public String getVst_uac_creator() {
        return vst_uac_creator;
    }

    public void setVst_uac_creator(String vst_uac_creator) {
        this.vst_uac_creator = vst_uac_creator;
    }

    public Long getVst_uac_uptime() {
        return vst_uac_uptime;
    }

    public void setVst_uac_uptime(Long vst_uac_uptime) {
        this.vst_uac_uptime = vst_uac_uptime;
    }

    public String getVst_uac_updator() {
        return vst_uac_updator;
    }

    public void setVst_uac_updator(String vst_uac_updator) {
        this.vst_uac_updator = vst_uac_updator;
    }

    public String getVst_uac_summary() {
        return vst_uac_summary;
    }

    public void setVst_uac_summary(String vst_uac_summary) {
        this.vst_uac_summary = vst_uac_summary;
    }

    @Override
    public String toString() {
        return "VstUserAddChannel{" +
                "vst_uac_id='" + vst_uac_id + '\'' +
                ", vst_uac_date=" + vst_uac_date +
                ", vst_uac_pkg='" + vst_uac_pkg + '\'' +
                ", vst_uac_channel='" + vst_uac_channel + '\'' +
                ", vst_uac_amount=" + vst_uac_amount +
                ", vst_uac_dod='" + vst_uac_dod + '\'' +
                ", vst_uac_wow='" + vst_uac_wow + '\'' +
                ", vst_uac_mom='" + vst_uac_mom + '\'' +
                ", vst_uac_addtime=" + vst_uac_addtime +
                ", vst_uac_creator='" + vst_uac_creator + '\'' +
                ", vst_uac_uptime=" + vst_uac_uptime +
                ", vst_uac_updator='" + vst_uac_updator + '\'' +
                ", vst_uac_summary='" + vst_uac_summary + '\'' +
                '}';
    }
}
