package com.vst.common.pojo;


/**
 * @author kai
 * 渠道质量统计
 */
public class VstChannelLevel {

    /**
     * 主键 10位随机字符串
     */
    private String vst_cl_id;

    /**
     * 统计日期
     */
    private Integer vst_cl_date;

    /**
     * 包名
     */
    private String vst_cl_pkg;

    /**
     * 渠道
     */
    private String vst_cl_channel;

    /**
     * 累计用户
     */
    private Long vst_cl_user_sum;

    /**
     * 活跃用户
     */
    private Long vst_cl_user_active;

    /**
     * 新增用户
     */
    private Long vst_cl_user_add;

    /**
     * 季新增用户
     */
    private Long vst_cl_user_add_season;

    /**
     * 季质量用户
     */
    private Long vst_cl_user_level_season;

    /**
     * 质量率
     */
    private String vst_cl_user_radio;

    /**
     * 次日留存用户
     */
    private Long vst_cl_user_retain_day;

    /**
     * 周留存用户
     */
    private Long vst_cl_user_retain_week;

    /**
     * 月留存用户
     */
    private Long vst_cl_user_retain_month;

    /**
     * 季度留存用户
     */
    private Long vst_cl_user_retain_season;

    /**
     * 播放量
     */
    private Long vst_cl_vv;

    /**
     * 播放用户
     */
    private Long vst_cl_uv;

    /**
     * 人均播放量
     */
    private String vst_cl_avg;

    /**
     * 播放时长
     */
    private Long vst_cl_playtime;

    /**
     * 人均播放时长，单位秒
     */
    private Long vst_cl_playtime_avg;

    /**
     * 会员订单笔数
     */
    private Long vst_cl_orders;

    /**
     * 订单总金额
     */
    private Long vst_cl_orders_amount;

    /**
     * 客单价
     */
    private String vst_cl_orders_price;

    /**
     * 均价
     */
    private String vst_cl_orders_price_avg;

    /**
     * 新增时间，13为毫秒数
     */
    private Long vst_cl_addtime;

    /**
     * 创建人
     */
    private String vst_cl_creator;

    /**
     * 修改时间，13为毫秒数
     */
    private Long vst_cl_uptime;

    /**
     * 修改人
     */
    private String vst_cl_updator;

    /**
     * 备注
     */
    private String vst_cl_summary;


    public String getVst_cl_id() {
        return vst_cl_id;
    }

    public void setVst_cl_id(String vst_cl_id) {
        this.vst_cl_id = vst_cl_id;
    }

    public Integer getVst_cl_date() {
        return vst_cl_date;
    }

    public void setVst_cl_date(Integer vst_cl_date) {
        this.vst_cl_date = vst_cl_date;
    }

    public String getVst_cl_pkg() {
        return vst_cl_pkg;
    }

    public void setVst_cl_pkg(String vst_cl_pkg) {
        this.vst_cl_pkg = vst_cl_pkg;
    }

    public String getVst_cl_channel() {
        return vst_cl_channel;
    }

    public void setVst_cl_channel(String vst_cl_channel) {
        this.vst_cl_channel = vst_cl_channel;
    }

    public Long getVst_cl_user_sum() {
        return vst_cl_user_sum;
    }

    public void setVst_cl_user_sum(Long vst_cl_user_sum) {
        this.vst_cl_user_sum = vst_cl_user_sum;
    }

    public Long getVst_cl_user_active() {
        return vst_cl_user_active;
    }

    public void setVst_cl_user_active(Long vst_cl_user_active) {
        this.vst_cl_user_active = vst_cl_user_active;
    }

    public Long getVst_cl_user_add() {
        return vst_cl_user_add;
    }

    public void setVst_cl_user_add(Long vst_cl_user_add) {
        this.vst_cl_user_add = vst_cl_user_add;
    }

    public Long getVst_cl_user_add_season() {
        return vst_cl_user_add_season;
    }

    public void setVst_cl_user_add_season(Long vst_cl_user_add_season) {
        this.vst_cl_user_add_season = vst_cl_user_add_season;
    }

    public Long getVst_cl_user_level_season() {
        return vst_cl_user_level_season;
    }

    public void setVst_cl_user_level_season(Long vst_cl_user_level_season) {
        this.vst_cl_user_level_season = vst_cl_user_level_season;
    }

    public String getVst_cl_user_radio() {
        return vst_cl_user_radio;
    }

    public void setVst_cl_user_radio(String vst_cl_user_radio) {
        this.vst_cl_user_radio = vst_cl_user_radio;
    }

    public Long getVst_cl_user_retain_day() {
        return vst_cl_user_retain_day;
    }

    public void setVst_cl_user_retain_day(Long vst_cl_user_retain_day) {
        this.vst_cl_user_retain_day = vst_cl_user_retain_day;
    }

    public Long getVst_cl_user_retain_week() {
        return vst_cl_user_retain_week;
    }

    public void setVst_cl_user_retain_week(Long vst_cl_user_retain_week) {
        this.vst_cl_user_retain_week = vst_cl_user_retain_week;
    }

    public Long getVst_cl_user_retain_month() {
        return vst_cl_user_retain_month;
    }

    public void setVst_cl_user_retain_month(Long vst_cl_user_retain_month) {
        this.vst_cl_user_retain_month = vst_cl_user_retain_month;
    }

    public Long getVst_cl_user_retain_season() {
        return vst_cl_user_retain_season;
    }

    public void setVst_cl_user_retain_season(Long vst_cl_user_retain_season) {
        this.vst_cl_user_retain_season = vst_cl_user_retain_season;
    }

    public Long getVst_cl_vv() {
        return vst_cl_vv;
    }

    public void setVst_cl_vv(Long vst_cl_vv) {
        this.vst_cl_vv = vst_cl_vv;
    }

    public Long getVst_cl_uv() {
        return vst_cl_uv;
    }

    public void setVst_cl_uv(Long vst_cl_uv) {
        this.vst_cl_uv = vst_cl_uv;
    }

    public String getVst_cl_avg() {
        return vst_cl_avg;
    }

    public void setVst_cl_avg(String vst_cl_avg) {
        this.vst_cl_avg = vst_cl_avg;
    }

    public Long getVst_cl_playtime() {
        return vst_cl_playtime;
    }

    public void setVst_cl_playtime(Long vst_cl_playtime) {
        this.vst_cl_playtime = vst_cl_playtime;
    }

    public Long getVst_cl_playtime_avg() {
        return vst_cl_playtime_avg;
    }

    public void setVst_cl_playtime_avg(Long vst_cl_playtime_avg) {
        this.vst_cl_playtime_avg = vst_cl_playtime_avg;
    }

    public Long getVst_cl_orders() {
        return vst_cl_orders;
    }

    public void setVst_cl_orders(Long vst_cl_orders) {
        this.vst_cl_orders = vst_cl_orders;
    }

    public Long getVst_cl_orders_amount() {
        return vst_cl_orders_amount;
    }

    public void setVst_cl_orders_amount(Long vst_cl_orders_amount) {
        this.vst_cl_orders_amount = vst_cl_orders_amount;
    }

    public String getVst_cl_orders_price() {
        return vst_cl_orders_price;
    }

    public void setVst_cl_orders_price(String vst_cl_orders_price) {
        this.vst_cl_orders_price = vst_cl_orders_price;
    }

    public String getVst_cl_orders_price_avg() {
        return vst_cl_orders_price_avg;
    }

    public void setVst_cl_orders_price_avg(String vst_cl_orders_price_avg) {
        this.vst_cl_orders_price_avg = vst_cl_orders_price_avg;
    }

    public Long getVst_cl_addtime() {
        return vst_cl_addtime;
    }

    public void setVst_cl_addtime(Long vst_cl_addtime) {
        this.vst_cl_addtime = vst_cl_addtime;
    }

    public String getVst_cl_creator() {
        return vst_cl_creator;
    }

    public void setVst_cl_creator(String vst_cl_creator) {
        this.vst_cl_creator = vst_cl_creator;
    }

    public Long getVst_cl_uptime() {
        return vst_cl_uptime;
    }

    public void setVst_cl_uptime(Long vst_cl_uptime) {
        this.vst_cl_uptime = vst_cl_uptime;
    }

    public String getVst_cl_updator() {
        return vst_cl_updator;
    }

    public void setVst_cl_updator(String vst_cl_updator) {
        this.vst_cl_updator = vst_cl_updator;
    }

    public String getVst_cl_summary() {
        return vst_cl_summary;
    }

    public void setVst_cl_summary(String vst_cl_summary) {
        this.vst_cl_summary = vst_cl_summary;
    }

    @Override
    public String toString() {
        return "VstChannelLevel{" +
                "vst_cl_id='" + vst_cl_id + '\'' +
                ", vst_cl_date=" + vst_cl_date +
                ", vst_cl_pkg='" + vst_cl_pkg + '\'' +
                ", vst_cl_channel='" + vst_cl_channel + '\'' +
                ", vst_cl_user_sum=" + vst_cl_user_sum +
                ", vst_cl_user_active=" + vst_cl_user_active +
                ", vst_cl_user_add=" + vst_cl_user_add +
                ", vst_cl_user_add_season=" + vst_cl_user_add_season +
                ", vst_cl_user_level_season=" + vst_cl_user_level_season +
                ", vst_cl_user_radio='" + vst_cl_user_radio + '\'' +
                ", vst_cl_user_retain_day=" + vst_cl_user_retain_day +
                ", vst_cl_user_retain_week=" + vst_cl_user_retain_week +
                ", vst_cl_user_retain_month=" + vst_cl_user_retain_month +
                ", vst_cl_user_retain_season=" + vst_cl_user_retain_season +
                ", vst_cl_vv=" + vst_cl_vv +
                ", vst_cl_uv=" + vst_cl_uv +
                ", vst_cl_avg='" + vst_cl_avg + '\'' +
                ", vst_cl_playtime=" + vst_cl_playtime +
                ", vst_cl_playtime_avg=" + vst_cl_playtime_avg +
                ", vst_cl_orders=" + vst_cl_orders +
                ", vst_cl_orders_amount=" + vst_cl_orders_amount +
                ", vst_cl_orders_price='" + vst_cl_orders_price + '\'' +
                ", vst_cl_orders_price_avg=" + vst_cl_orders_price_avg +
                ", vst_cl_addtime=" + vst_cl_addtime +
                ", vst_cl_creator='" + vst_cl_creator + '\'' +
                ", vst_cl_uptime=" + vst_cl_uptime +
                ", vst_cl_updator='" + vst_cl_updator + '\'' +
                ", vst_cl_summary='" + vst_cl_summary + '\'' +
                '}';
    }
}
