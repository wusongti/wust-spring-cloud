package com.wust.springcloud.easyexcel.xmlobject.common;

import java.math.BigDecimal;

/**
 * 字段元素
 */
public class XMLField4Import {
    // index属性
    private Integer attributeIndex;

    // 值类型
    private String attributeType;

    // attributeProperty属性
    private String attributeProperty;

    // 键值对，例如：{1：成功；2：失败}
    private String attributeFormat;

    // attributeRequired属性
    private String attributeRequired;

    // 日期格式
    private String attributePattern;

    // rootCode属性
    private String attributeRootCode;

    // 正则表达式
    private String attributeRegex;

    // 不符合正则表达式的时的错误消息
    private String attributeRegexErrMsg;

    // 当type=double时，保留小数位
    private Integer attributeScale = 2;

    // 当type=double时，小数位舍取方式
    private Integer attributeRoundingMode = BigDecimal.ROUND_HALF_UP;


    public Integer getAttributeIndex() {
        return attributeIndex;
    }

    public void setAttributeIndex(Integer attributeIndex) {
        this.attributeIndex = attributeIndex;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    public String getAttributeProperty() {
        return attributeProperty;
    }

    public void setAttributeProperty(String attributeProperty) {
        this.attributeProperty = attributeProperty;
    }

    public String getAttributeFormat() {
        return attributeFormat;
    }

    public void setAttributeFormat(String attributeFormat) {
        this.attributeFormat = attributeFormat;
    }

    public String getAttributeRequired() {
        return attributeRequired;
    }

    public void setAttributeRequired(String attributeRequired) {
        this.attributeRequired = attributeRequired;
    }

    public String getAttributePattern() {
        return attributePattern;
    }

    public void setAttributePattern(String attributePattern) {
        this.attributePattern = attributePattern;
    }

    public String getAttributeRootCode() {
        return attributeRootCode;
    }

    public void setAttributeRootCode(String attributeRootCode) {
        this.attributeRootCode = attributeRootCode;
    }

    public String getAttributeRegex() {
        return attributeRegex;
    }

    public void setAttributeRegex(String attributeRegex) {
        this.attributeRegex = attributeRegex;
    }

    public String getAttributeRegexErrMsg() {
        return attributeRegexErrMsg;
    }

    public void setAttributeRegexErrMsg(String attributeRegexErrMsg) {
        this.attributeRegexErrMsg = attributeRegexErrMsg;
    }

    public Integer getAttributeScale() {
        return attributeScale;
    }

    public void setAttributeScale(Integer attributeScale) {
        this.attributeScale = attributeScale;
    }

    public Integer getAttributeRoundingMode() {
        return attributeRoundingMode;
    }

    public void setAttributeRoundingMode(Integer attributeRoundingMode) {
        this.attributeRoundingMode = attributeRoundingMode;
    }

    @Override
    public String toString() {
        return "XMLField4Import{" +
                "attributeIndex=" + attributeIndex +
                ", attributeType='" + attributeType + '\'' +
                ", attributeProperty='" + attributeProperty + '\'' +
                ", attributeFormat='" + attributeFormat + '\'' +
                ", attributeRequired='" + attributeRequired + '\'' +
                ", attributePattern='" + attributePattern + '\'' +
                ", attributeRootCode='" + attributeRootCode + '\'' +
                ", attributeRegex='" + attributeRegex + '\'' +
                ", attributeRegexErrMsg='" + attributeRegexErrMsg + '\'' +
                ", attributeScale=" + attributeScale +
                ", attributeRoundingMode=" + attributeRoundingMode +
                '}';
    }
}