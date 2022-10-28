package com.vst.common.pojo;

/**
 * 用户二次活跃渠道统计
 * @author kai
 * 2019-11-28 下午11:35:56
 *
 */
public class VstUserSecondaryActiveChannel {
    /**
     * 主键
     */
    private String vst_usac_id;

    /**
     * 统计日期
     */
    private Integer vst_usac_date;

    /**
     * 包名
     */
    private String vst_usac_pkg;

    /**
     * 渠道
     */
    private String vst_usac_channel;

    /**
     * 二次用户活跃渠道数量
     */
    private Long vst_usac_amount;

    /**
     * 天环比
     */
    private String vst_usac_dod;

    /**
     * 周环比
     */
    private String vst_usac_wow;

    /**
     * 月环比
     */
    private String vst_usac_mom;

    /**
     * 新增时间，13位毫秒数
     */
    private Long vst_usac_addtime;

    /**
     * 创建人
     */
    private String vst_usac_creator;

    /**
     * 修改时间，13位毫秒数
     */
    private Long vst_usac_uptime;

    /**
     * 修改人
     */
    private String vst_usac_updator;

    /**
     * 备注
     */
    private String vst_usac_summary;


    public String getVst_usac_id() {
        return vst_usac_id;
    }

    public void setVst_usac_id(String vst_usac_id) {
        this.vst_usac_id = vst_usac_id;
    }

    public Integer getVst_usac_date() {
        return vst_usac_date;
    }

    public void setVst_usac_date(Integer vst_usac_date) {
        this.vst_usac_date = vst_usac_date;
    }

    public String getVst_usac_pkg() {
        return vst_usac_pkg;
    }

    public void setVst_usac_pkg(String vst_usac_pkg) {
        this.vst_usac_pkg = vst_usac_pkg;
    }

    public String getVst_usac_channel() {
        return vst_usac_channel;
    }

    public void setVst_usac_channel(String vst_usac_channel) {
        this.vst_usac_channel = vst_usac_channel;
    }

    public Long getVst_usac_amount() {
        return vst_usac_amount;
    }

    public void setVst_usac_amount(Long vst_usac_amount) {
        this.vst_usac_amount = vst_usac_amount;
    }

    public String getVst_usac_dod() {
        return vst_usac_dod;
    }

    public void setVst_usac_dod(String vst_usac_dod) {
        this.vst_usac_dod = vst_usac_dod;
    }

    public String getVst_usac_wow() {
        return vst_usac_wow;
    }

    public void setVst_usac_wow(String vst_usac_wow) {
        this.vst_usac_wow = vst_usac_wow;
    }

    public String getVst_usac_mom() {
        return vst_usac_mom;
    }

    public void setVst_usac_mom(String vst_usac_mom) {
        this.vst_usac_mom = vst_usac_mom;
    }

    public Long getVst_usac_addtime() {
        return vst_usac_addtime;
    }

    public void setVst_usac_addtime(Long vst_usac_addtime) {
        this.vst_usac_addtime = vst_usac_addtime;
    }

    public String getVst_usac_creator() {
        return vst_usac_creator;
    }

    public void setVst_usac_creator(String vst_usac_creator) {
        this.vst_usac_creator = vst_usac_creator;
    }

    public Long getVst_usac_uptime() {
        return vst_usac_uptime;
    }

    public void setVst_usac_uptime(Long vst_usac_uptime) {
        this.vst_usac_uptime = vst_usac_uptime;
    }

    public String getVst_usac_updator() {
        return vst_usac_updator;
    }

    public void setVst_usac_updator(String vst_usac_updator) {
        this.vst_usac_updator = vst_usac_updator;
    }

    public String getVst_usac_summary() {
        return vst_usac_summary;
    }

    public void setVst_usac_summary(String vst_usac_summary) {
        this.vst_usac_summary = vst_usac_summary;
    }

    @Override
    public String toString() {
        return "VstUserAddChannel{" +
                "vst_usac_id='" + vst_usac_id + '\'' +
                ", vst_usac_date=" + vst_usac_date +
                ", vst_usac_pkg='" + vst_usac_pkg + '\'' +
                ", vst_usac_channel='" + vst_usac_channel + '\'' +
                ", vst_usac_amount=" + vst_usac_amount +
                ", vst_usac_dod='" + vst_usac_dod + '\'' +
                ", vst_usac_wow='" + vst_usac_wow + '\'' +
                ", vst_usac_mom='" + vst_usac_mom + '\'' +
                ", vst_usac_addtime=" + vst_usac_addtime +
                ", vst_usac_creator='" + vst_usac_creator + '\'' +
                ", vst_usac_uptime=" + vst_usac_uptime +
                ", vst_usac_updator='" + vst_usac_updator + '\'' +
                ", vst_usac_summary='" + vst_usac_summary + '\'' +
                '}';
    }
}
