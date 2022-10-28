package com.vst.common.pojo;

/**
 * 季质量用户统计
 * @author kai
 * @// TODO: 2018/1/22
 */
public class VstUserLevelChannel {
    /**
     * 主键，10位随机字符串
	 */
    private String vst_ulc_id;

    /**
     * 统计日期
     */
    private Integer vst_ulc_date;

    /**
     * 包名
     */
    private String vst_ulc_pkg;

    /**
     * 渠道
     */
    private String vst_ulc_channel;

    /**
     * 季新增用户数
     */
    private Long vst_ulc_add_amount;

    /**
     * 季质量用户数
     */
    private Long vst_ulc_season_amount;

    /**
     * 季质量用户天环比
     */
    private String vst_ulc_season_dod;

    /**
     * 季质量用户周环比
     */
    private String vst_ulc_season_wow;

    /**
     * 季质量用户占比
     */
    private String vst_ulc_season_radio;

    /**
     * 新增时间，13位毫秒数
     */
    private Long vst_ulc_addtime;

    /**
     * 创建人
     */
    private String vst_ulc_creator;

    /**
     * 修改时间，13位毫秒数
     */
    private Long vst_ulc_uptime;

    /**
     * 修改人
     */
    private String vst_ulc_updator;

    /**
     * 备注
     */
    private String vst_ulc_summary;

    public String getVst_ulc_id() {
        return vst_ulc_id;
    }

    public void setVst_ulc_id(String vst_ulc_id) {
        this.vst_ulc_id = vst_ulc_id;
    }

    public Integer getVst_ulc_date() {
        return vst_ulc_date;
    }

    public void setVst_ulc_date(Integer vst_ulc_date) {
        this.vst_ulc_date = vst_ulc_date;
    }

    public String getVst_ulc_pkg() {
        return vst_ulc_pkg;
    }

    public void setVst_ulc_pkg(String vst_ulc_pkg) {
        this.vst_ulc_pkg = vst_ulc_pkg;
    }

    public String getVst_ulc_channel() {
        return vst_ulc_channel;
    }

    public void setVst_ulc_channel(String vst_ulc_channel) {
        this.vst_ulc_channel = vst_ulc_channel;
    }

    public Long getVst_ulc_add_amount() {
        return vst_ulc_add_amount;
    }

    public void setVst_ulc_add_amount(Long vst_ulc_add_amount) {
        this.vst_ulc_add_amount = vst_ulc_add_amount;
    }

    public Long getVst_ulc_season_amount() {
        return vst_ulc_season_amount;
    }

    public void setVst_ulc_season_amount(Long vst_ulc_season_amount) {
        this.vst_ulc_season_amount = vst_ulc_season_amount;
    }

    public String getVst_ulc_season_dod() {
        return vst_ulc_season_dod;
    }

    public void setVst_ulc_season_dod(String vst_ulc_season_dod) {
        this.vst_ulc_season_dod = vst_ulc_season_dod;
    }

    public String getVst_ulc_season_wow() {
        return vst_ulc_season_wow;
    }

    public void setVst_ulc_season_wow(String vst_ulc_season_wow) {
        this.vst_ulc_season_wow = vst_ulc_season_wow;
    }

    public String getVst_ulc_season_radio() {
        return vst_ulc_season_radio;
    }

    public void setVst_ulc_season_radio(String vst_ulc_season_radio) {
        this.vst_ulc_season_radio = vst_ulc_season_radio;
    }

    public Long getVst_ulc_addtime() {
        return vst_ulc_addtime;
    }

    public void setVst_ulc_addtime(Long vst_ulc_addtime) {
        this.vst_ulc_addtime = vst_ulc_addtime;
    }

    public String getVst_ulc_creator() {
        return vst_ulc_creator;
    }

    public void setVst_ulc_creator(String vst_ulc_creator) {
        this.vst_ulc_creator = vst_ulc_creator;
    }

    public Long getVst_ulc_uptime() {
        return vst_ulc_uptime;
    }

    public void setVst_ulc_uptime(Long vst_ulc_uptime) {
        this.vst_ulc_uptime = vst_ulc_uptime;
    }

    public String getVst_ulc_updator() {
        return vst_ulc_updator;
    }

    public void setVst_ulc_updator(String vst_ulc_updator) {
        this.vst_ulc_updator = vst_ulc_updator;
    }

    public String getVst_ulc_summary() {
        return vst_ulc_summary;
    }

    public void setVst_ulc_summary(String vst_ulc_summary) {
        this.vst_ulc_summary = vst_ulc_summary;
    }

    @Override
    public String toString() {
        return "VstUserLevelChannel{" +
                "vst_ulc_id='" + vst_ulc_id + '\'' +
                ", vst_ulc_date=" + vst_ulc_date +
                ", vst_ulc_pkg='" + vst_ulc_pkg + '\'' +
                ", vst_ulc_channel='" + vst_ulc_channel + '\'' +
                ", vst_ulc_add_amount=" + vst_ulc_add_amount +
                ", vst_ulc_season_amount=" + vst_ulc_season_amount +
                ", vst_ulc_season_dod='" + vst_ulc_season_dod + '\'' +
                ", vst_ulc_season_wow='" + vst_ulc_season_wow + '\'' +
                ", vst_ulc_season_radio='" + vst_ulc_season_radio + '\'' +
                ", vst_ulc_addtime=" + vst_ulc_addtime +
                ", vst_ulc_creator='" + vst_ulc_creator + '\'' +
                ", vst_ulc_uptime=" + vst_ulc_uptime +
                ", vst_ulc_updator='" + vst_ulc_updator + '\'' +
                ", vst_ulc_summary='" + vst_ulc_summary + '\'' +
                '}';
    }
}
