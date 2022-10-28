package com.vst.quartz.enumerate;


/**
 * @author kai
 * 表示状态， 使用各种表
 */
public enum  QuartzEnum {

    //任务状态
    VST_STATUS(0,1,2),

    VST_TYPE(1,2,3),

    //任务执行前需要执行的事
    VST_JOB_BEFORE(0,1,2),

    //任务执行后需要执行的事
    VST_JOB_AFTER(0,1,2),

    //操作类型
    VST_JOB_TYPE(1,2,3),
    //任务类型
    VST_QC_TYPE(1,2,3),

    //备份类型 1:按字段备份，2：全局备份，3：按清除条件备份
    VST_BACKUP_TYPE(1,2,3),

    //备份选项 1:只备份表，2:只备份文件，3:两者都备份
    VST_BACKUP(1,2,3),

    //文件备份类型 1:json文件,2:excel文件,3:sql文件
    VST_FILE_TYPE(1,2,3),

    //是否压缩：1:压缩,2:不压缩
    VST_IS_COMPRESSION(1,2,0);




    /**
     * 第一个状态
     */
    private Integer oneStatus;

    /**
     * 第二个状态
     */
    private Integer towStatus;

    /**
     * 第三个状态
     */
    private Integer treeStatus;


    public Integer getOneStatus() {
        return oneStatus;
    }
    public Integer getTowStatus() {
        return towStatus;
    }

    public Integer getTreeStatus() {
        return treeStatus;
    }


    QuartzEnum(Integer oneStatus, Integer towStatus, Integer treeStatus) {
        this.oneStatus = oneStatus;
        this.towStatus = towStatus;
        this.treeStatus = treeStatus;
    }
}
