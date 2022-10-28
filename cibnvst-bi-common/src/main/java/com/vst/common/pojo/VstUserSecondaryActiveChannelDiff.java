package com.vst.common.pojo;

/**
 * 用户二次活跃渠道差异化统计
 * @author wangjie
 * @date 2020/5/8 14:50
 */
public class VstUserSecondaryActiveChannelDiff {
    /**
     * 主键
     */
    private String vst_usacd_id;

    /**
     * 统计日期
     */
    private Integer vst_usacd_date;

    /**
     * 包名
     */
    private String vst_usacd_pkg;

    /**
     * 渠道
     */
    private String vst_usacd_channel;

    /**
     * 当日新增用户数
     */
    private Long vst_usacd_uv;

    /**
     * 二次活跃用户数
     */
    private Long vst_usacd_amount;

    /**
     * 友盟新增用户数
     */
    private Long vst_usacd_umeng_uv;

    /**
     * 调整系数，默认-1，表示不按公式计算。
     * 新二活 = 友盟新增 * vst二活 / vst新增 * 系数
     */
    private String vst_usacd_modulus;

    /**
     * 天环比
     */
    private String vst_usacd_dod;

    /**
     * 周环比
     */
    private String vst_usacd_wow;

    /**
     * 月环比
     */
    private String vst_usacd_mom;

    /**
     * 新增时间，13位毫秒数
     */
    private Long vst_usacd_addtime;

    /**
     * 创建人
     */
    private String vst_usacd_creator;

    /**
     * 修改时间，13位毫秒数
     */
    private Long vst_usacd_uptime;

    /**
     * 修改人
     */
    private String vst_usacd_updator;

    /**
     * 备注
     */
    private String vst_usacd_summary;

    public String getVst_usacd_id() {
        return vst_usacd_id;
    }

    public void setVst_usacd_id(String vst_usacd_id) {
        this.vst_usacd_id = vst_usacd_id;
    }

    public Integer getVst_usacd_date() {
        return vst_usacd_date;
    }

    public void setVst_usacd_date(Integer vst_usacd_date) {
        this.vst_usacd_date = vst_usacd_date;
    }

    public String getVst_usacd_pkg() {
        return vst_usacd_pkg;
    }

    public void setVst_usacd_pkg(String vst_usacd_pkg) {
        this.vst_usacd_pkg = vst_usacd_pkg;
    }

    public String getVst_usacd_channel() {
        return vst_usacd_channel;
    }

    public void setVst_usacd_channel(String vst_usacd_channel) {
        this.vst_usacd_channel = vst_usacd_channel;
    }

    public Long getVst_usacd_uv() {
        return vst_usacd_uv;
    }

    public void setVst_usacd_uv(Long vst_usacd_uv) {
        this.vst_usacd_uv = vst_usacd_uv;
    }

    public Long getVst_usacd_amount() {
        return vst_usacd_amount;
    }

    public void setVst_usacd_amount(Long vst_usacd_amount) {
        this.vst_usacd_amount = vst_usacd_amount;
    }

    public Long getVst_usacd_umeng_uv() {
        return vst_usacd_umeng_uv;
    }

    public void setVst_usacd_umeng_uv(Long vst_usacd_umeng_uv) {
        this.vst_usacd_umeng_uv = vst_usacd_umeng_uv;
    }

    public String getVst_usacd_modulus() {
        return vst_usacd_modulus;
    }

    public void setVst_usacd_modulus(String vst_usacd_modulus) {
        this.vst_usacd_modulus = vst_usacd_modulus;
    }

    public String getVst_usacd_dod() {
        return vst_usacd_dod;
    }

    public void setVst_usacd_dod(String vst_usacd_dod) {
        this.vst_usacd_dod = vst_usacd_dod;
    }

    public String getVst_usacd_wow() {
        return vst_usacd_wow;
    }

    public void setVst_usacd_wow(String vst_usacd_wow) {
        this.vst_usacd_wow = vst_usacd_wow;
    }

    public String getVst_usacd_mom() {
        return vst_usacd_mom;
    }

    public void setVst_usacd_mom(String vst_usacd_mom) {
        this.vst_usacd_mom = vst_usacd_mom;
    }

    public Long getVst_usacd_addtime() {
        return vst_usacd_addtime;
    }

    public void setVst_usacd_addtime(Long vst_usacd_addtime) {
        this.vst_usacd_addtime = vst_usacd_addtime;
    }

    public String getVst_usacd_creator() {
        return vst_usacd_creator;
    }

    public void setVst_usacd_creator(String vst_usacd_creator) {
        this.vst_usacd_creator = vst_usacd_creator;
    }

    public Long getVst_usacd_uptime() {
        return vst_usacd_uptime;
    }

    public void setVst_usacd_uptime(Long vst_usacd_uptime) {
        this.vst_usacd_uptime = vst_usacd_uptime;
    }

    public String getVst_usacd_updator() {
        return vst_usacd_updator;
    }

    public void setVst_usacd_updator(String vst_usacd_updator) {
        this.vst_usacd_updator = vst_usacd_updator;
    }

    public String getVst_usacd_summary() {
        return vst_usacd_summary;
    }

    public void setVst_usacd_summary(String vst_usacd_summary) {
        this.vst_usacd_summary = vst_usacd_summary;
    }

    @Override
    public String toString() {
        return "VstUserSecondaryActiveChannelDiff{" +
                "vst_usacd_id='" + vst_usacd_id + '\'' +
                ", vst_usacd_date=" + vst_usacd_date +
                ", vst_usacd_pkg='" + vst_usacd_pkg + '\'' +
                ", vst_usacd_channel='" + vst_usacd_channel + '\'' +
                ", vst_usacd_uv=" + vst_usacd_uv +
                ", vst_usacd_amount=" + vst_usacd_amount +
                ", vst_usacd_umeng_uv=" + vst_usacd_umeng_uv +
                ", vst_usacd_modulus='" + vst_usacd_modulus + '\'' +
                ", vst_usacd_dod='" + vst_usacd_dod + '\'' +
                ", vst_usacd_wow='" + vst_usacd_wow + '\'' +
                ", vst_usacd_mom='" + vst_usacd_mom + '\'' +
                ", vst_usacd_addtime=" + vst_usacd_addtime +
                ", vst_usacd_creator='" + vst_usacd_creator + '\'' +
                ", vst_usacd_uptime=" + vst_usacd_uptime +
                ", vst_usacd_updator='" + vst_usacd_updator + '\'' +
                ", vst_usacd_summary='" + vst_usacd_summary + '\'' +
                '}';
    }
}
