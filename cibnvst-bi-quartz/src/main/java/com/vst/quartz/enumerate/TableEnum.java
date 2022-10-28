package com.vst.quartz.enumerate;

/**
 * @author kai
 */
public enum TableEnum {
    /**
     * 是任务属于什么类型
     */
    VST_JOB_TYPE("clean","push","pull"),

    /**
     * 备份类型 1:按字段备份，2：全局备份，3：按清除条件备份
     */
    VST_BACKUP_TYPE("1","2","3");

    /**
     * 清除
     */
    private String oneName;
    /**
     * 推送
     */
    private String twoName;

    /**
     * 拉取
     */
    private String treeName;

    public String getOneName() {
        return oneName;
    }

    public void setOneName(String oneName) {
        this.oneName = oneName;
    }

    public String getTwoName() {
        return twoName;
    }

    public void setTwoName(String twoName) {
        this.twoName = twoName;
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    TableEnum(String oneName, String twoName, String treeName) {
        this.oneName = oneName;
        this.twoName = twoName;
        this.treeName = treeName;
    }
}
