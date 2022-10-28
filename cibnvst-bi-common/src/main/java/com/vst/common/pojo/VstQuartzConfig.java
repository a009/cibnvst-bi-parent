package com.vst.common.pojo;

/**
 * @author kai
 * 任务配置表
 * TODO: 2018/1/9
 */
public class VstQuartzConfig {

    /**
     * 配置表主键
     */
    private String vst_qc_id;

    /**
     * 任务类型 1:临时任务 2:定时任务
     */
    private Integer vst_qc_type;

    /**
     * 操作类型 1表示清理,2表示推送,3拉取
     */
    private Integer vst_qc_job_type;

    /**
     * 配置任务执行时间
     */
    private String vst_qc_job_cron;

    /**
     * 清理之后的处理方案 (1表示删除原表数据,0表示不处理)
     */
    private Integer vst_qc_deal_after;

    /**
     * 清理之前的处理方案 (1表示禁用原表数据，2表示备份，3表示不处理)
     */
    private Integer vst_qc_deal_before;

    /**
     * 表名
     */
    private String vst_qc_table;

    /**
     * 任务名称 唯一
     */
    private String vst_qc_name;

    /**
     * 触发器名称
     */
    private String vst_qc_trigger_name;

    /**
     * 处理条件 （字段名='值' and 字段名1=''）
     */
    private String vst_qc_condition;

    /**
     * 备份选项: 1.只备份表 2,只备份文件,3两者都备份
     */
    private Integer vst_qc_backup;

    /**
     * 备份类型,1:根据表字段备份,2:全表备份,3:根据清除条件备份
     */
    private Integer vst_qc_backup_type;

    /**
     * 备份需要的条件 类型为1 请填写表字段，逗号隔开，其余两个类型不需要填写
     */
    private String vst_qc_backup_condition;

    /**
     * 表对应的实体类名称
     */
    private String vst_qc_entity_name;

    /**
     * 备份数据原表数据源
     */
    private String vst_qc_source_name;

    /**
     * 备份数据目标表数据源(多张用逗号隔开)
     */
    private String vst_qc_target_source_name;

    /**
     * 目标备份库的数据表
     */
    private String vst_qc_tarbet_table_name;

    /**
     * 备份文件的格式，1:json格式，2:excel格式文件，3:sql文件格式
     */
    private Integer vst_qc_file_format;

    /**
     * 文件保存地址
     */
    private String vst_qc_file_address;

    /**
     * 是否压缩
     */
    private Integer vst_qc_is_compression;

    /**
     * 压缩格式,1:zip格式,2:jar格式,3:gzip格式(不支持目录压缩）,4:tar格式
     */
    private Integer vst_qc_compression_format;

    /**
     * 压缩目标路径
     */
    private String vst_qc_compression_address;

    /**
     * 任务状态 1.启动 2,暂停
     */
    private Integer vst_qc_state;

    /**
     * 新增时间
     */
    private Long vst_qc_addtime;

    /**
     * 新增人
     */
    private String vst_qc_creator;

    /**
     * 修改时间
     */
    private Long vst_qc_uptime;

    /**
     * 操作人
     */
    private String vst_qc_updator;

    /**
     * 任务描述
     */
    private String vst_qc_summary;



    public String getVst_qc_id() {
        return vst_qc_id;
    }

    public void setVst_qc_id(String vst_qc_id) {
        this.vst_qc_id = vst_qc_id;
    }

    public Integer getVst_qc_type() {
        return vst_qc_type;
    }

    public void setVst_qc_type(Integer vst_qc_type) {
        this.vst_qc_type = vst_qc_type;
    }

    public Integer getVst_qc_job_type() {
        return vst_qc_job_type;
    }

    public void setVst_qc_job_type(Integer vst_qc_job_type) {
        this.vst_qc_job_type = vst_qc_job_type;
    }

    public String getVst_qc_job_cron() {
        return vst_qc_job_cron;
    }

    public void setVst_qc_job_cron(String vst_qc_job_cron) {
        this.vst_qc_job_cron = vst_qc_job_cron;
    }

    public Integer getVst_qc_deal_after() {
        return vst_qc_deal_after;
    }

    public void setVst_qc_deal_after(Integer vst_qc_deal_after) {
        this.vst_qc_deal_after = vst_qc_deal_after;
    }

    public Integer getVst_qc_deal_before() {
        return vst_qc_deal_before;
    }

    public void setVst_qc_deal_before(Integer vst_qc_deal_before) {
        this.vst_qc_deal_before = vst_qc_deal_before;
    }

    public String getVst_qc_table() {
        return vst_qc_table;
    }

    public void setVst_qc_table(String vst_qc_table) {
        this.vst_qc_table = vst_qc_table;
    }

    public String getVst_qc_name() {
        return vst_qc_name;
    }

    public void setVst_qc_name(String vst_qc_name) {
        this.vst_qc_name = vst_qc_name;
    }

    public String getVst_qc_trigger_name() {
        return vst_qc_trigger_name;
    }

    public void setVst_qc_trigger_name(String vst_qc_trigger_name) {
        this.vst_qc_trigger_name = vst_qc_trigger_name;
    }

    public String getVst_qc_condition() {
        return vst_qc_condition;
    }

    public void setVst_qc_condition(String vst_qc_condition) {
        this.vst_qc_condition = vst_qc_condition;
    }

    public Integer getVst_qc_backup() {
        return vst_qc_backup;
    }

    public void setVst_qc_backup(Integer vst_qc_backup) {
        this.vst_qc_backup = vst_qc_backup;
    }

    public Integer getVst_qc_backup_type() {
        return vst_qc_backup_type;
    }

    public void setVst_qc_backup_type(Integer vst_qc_backup_type) {
        this.vst_qc_backup_type = vst_qc_backup_type;
    }

    public String getVst_qc_backup_condition() {
        return vst_qc_backup_condition;
    }

    public void setVst_qc_backup_condition(String vst_qc_backup_condition) {
        this.vst_qc_backup_condition = vst_qc_backup_condition;
    }

    public String getVst_qc_entity_name() {
        return vst_qc_entity_name;
    }

    public void setVst_qc_entity_name(String vst_qc_entity_name) {
        this.vst_qc_entity_name = vst_qc_entity_name;
    }

    public String getVst_qc_source_name() {
        return vst_qc_source_name;
    }

    public void setVst_qc_source_name(String vst_qc_source_name) {
        this.vst_qc_source_name = vst_qc_source_name;
    }

    public String getVst_qc_target_source_name() {
        return vst_qc_target_source_name;
    }

    public void setVst_qc_target_source_name(String vst_qc_target_source_name) {
        this.vst_qc_target_source_name = vst_qc_target_source_name;
    }

    public String getVst_qc_tarbet_table_name() {
        return vst_qc_tarbet_table_name;
    }

    public void setVst_qc_tarbet_table_name(String vst_qc_tarbet_table_name) {
        this.vst_qc_tarbet_table_name = vst_qc_tarbet_table_name;
    }

    public Integer getVst_qc_file_format() {
        return vst_qc_file_format;
    }

    public void setVst_qc_file_format(Integer vst_qc_file_format) {
        this.vst_qc_file_format = vst_qc_file_format;
    }

    public String getVst_qc_file_address() {
        return vst_qc_file_address;
    }

    public void setVst_qc_file_address(String vst_qc_file_address) {
        this.vst_qc_file_address = vst_qc_file_address;
    }

    public Integer getVst_qc_is_compression() {
        return vst_qc_is_compression;
    }

    public void setVst_qc_is_compression(Integer vst_qc_is_compression) {
        this.vst_qc_is_compression = vst_qc_is_compression;
    }

    public Integer getVst_qc_compression_format() {
        return vst_qc_compression_format;
    }

    public void setVst_qc_compression_format(Integer vst_qc_compression_format) {
        this.vst_qc_compression_format = vst_qc_compression_format;
    }


    public String getVst_qc_compression_address() {
        return vst_qc_compression_address;
    }

    public void setVst_qc_compression_address(String vst_qc_compression_address) {
        this.vst_qc_compression_address = vst_qc_compression_address;
    }

    public Integer getVst_qc_state() {
        return vst_qc_state;
    }

    public void setVst_qc_state(Integer vst_qc_state) {
        this.vst_qc_state = vst_qc_state;
    }

    public Long getVst_qc_addtime() {
        return vst_qc_addtime;
    }

    public void setVst_qc_addtime(Long vst_qc_addtime) {
        this.vst_qc_addtime = vst_qc_addtime;
    }

    public String getVst_qc_creator() {
        return vst_qc_creator;
    }

    public void setVst_qc_creator(String vst_qc_creator) {
        this.vst_qc_creator = vst_qc_creator;
    }

    public Long getVst_qc_uptime() {
        return vst_qc_uptime;
    }

    public void setVst_qc_uptime(Long vst_qc_uptime) {
        this.vst_qc_uptime = vst_qc_uptime;
    }

    public String getVst_qc_updator() {
        return vst_qc_updator;
    }

    public void setVst_qc_updator(String vst_qc_updator) {
        this.vst_qc_updator = vst_qc_updator;
    }

    public String getVst_qc_summary() {
        return vst_qc_summary;
    }

    public void setVst_qc_summary(String vst_qc_summary) {
        this.vst_qc_summary = vst_qc_summary;
    }

    @Override
    public String toString() {
        return "VstQuartzConfig{" +
                "vst_qc_id='" + vst_qc_id + '\'' +
                ", vst_qc_type=" + vst_qc_type +
                ", vst_qc_job_type=" + vst_qc_job_type +
                ", vst_qc_job_cron='" + vst_qc_job_cron + '\'' +
                ", vst_qc_deal_after=" + vst_qc_deal_after +
                ", vst_qc_deal_before=" + vst_qc_deal_before +
                ", vst_qc_table='" + vst_qc_table + '\'' +
                ", vst_qc_name='" + vst_qc_name + '\'' +
                ", vst_qc_trigger_name='" + vst_qc_trigger_name + '\'' +
                ", vst_qc_condition='" + vst_qc_condition + '\'' +
                ", vst_qc_backup=" + vst_qc_backup +
                ", vst_qc_backup_type=" + vst_qc_backup_type +
                ", vst_qc_backup_condition='" + vst_qc_backup_condition + '\'' +
                ", vst_qc_entity_name='" + vst_qc_entity_name + '\'' +
                ", vst_qc_source_name='" + vst_qc_source_name + '\'' +
                ", vst_qc_target_source_name='" + vst_qc_target_source_name + '\'' +
                ", vst_qc_tarbet_table_name='" + vst_qc_tarbet_table_name + '\'' +
                ", vst_qc_file_format=" + vst_qc_file_format +
                ", vst_qc_file_address='" + vst_qc_file_address + '\'' +
                ", vst_qc_is_compression=" + vst_qc_is_compression +
                ", vst_qc_compression_format=" + vst_qc_compression_format +
                ", vst_qc_compression_address=" + vst_qc_compression_address +
                ", vst_qc_state=" + vst_qc_state +
                ", vst_qc_addtime=" + vst_qc_addtime +
                ", vst_qc_creator='" + vst_qc_creator + '\'' +
                ", vst_qc_uptime=" + vst_qc_uptime +
                ", vst_qc_updator='" + vst_qc_updator + '\'' +
                ", vst_qc_summary='" + vst_qc_summary + '\'' +
                '}';
    }
}
