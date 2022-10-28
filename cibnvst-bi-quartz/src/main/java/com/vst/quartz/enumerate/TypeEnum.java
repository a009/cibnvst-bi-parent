package com.vst.quartz.enumerate;

/**
 * @author kai 
 * 基本数据类型枚举类
 * TODO: 2018/4/23
 */
public enum  TypeEnum {

    /**
     * 基本数据类型
     */
    VST_TYPE("varchar","char","int","bigint");


    private String typeString;

    private String typeChar;

    private String typeInteger;

    private String typeLong;

    public String getTypeString() {
        return typeString;
    }

    public void setTypeString(String typeString) {
        this.typeString = typeString;
    }

    public String getTypeChar() {
        return typeChar;
    }

    public void setTypeChar(String typeChar) {
        this.typeChar = typeChar;
    }

    public String getTypeInteger() {
        return typeInteger;
    }

    public void setTypeInteger(String typeInteger) {
        this.typeInteger = typeInteger;
    }

    public String getTypeLong() {
        return typeLong;
    }

    public void setTypeLong(String typeLong) {
        this.typeLong = typeLong;
    }

    TypeEnum(String typeString,String typeChar,String typeInteger,String typeLong){
        this.typeString = typeString;
        this.typeChar = typeChar;
        this.typeInteger = typeInteger;
        this.typeLong = typeLong;
    }
}
