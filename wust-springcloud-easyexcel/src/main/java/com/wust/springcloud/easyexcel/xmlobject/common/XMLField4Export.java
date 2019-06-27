package com.wust.springcloud.easyexcel.xmlobject.common;

/**
 * 字段元素
 */
public class XMLField4Export{
    // column属性
    private String attributeColumn;

    // property属性
    private String attributeLabel;

    // attributeType属性
    private String attributeType;

    // attributeFormat属性
    private String attributeFormat;

    // rootCode属性
    private String attributeRootCode;

    // parentCode属性
    private String attributeParentCode;


    public String getAttributeColumn() {
        return attributeColumn;
    }

    public void setAttributeColumn(String attributeColumn) {
        this.attributeColumn = attributeColumn;
    }

    public String getAttributeLabel() {
        return attributeLabel;
    }

    public void setAttributeLabel(String attributeLabel) {
        this.attributeLabel = attributeLabel;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    public String getAttributeFormat() {
        return attributeFormat;
    }

    public void setAttributeFormat(String attributeFormat) {
        this.attributeFormat = attributeFormat;
    }

    public String getAttributeRootCode() {
        return attributeRootCode;
    }

    public void setAttributeRootCode(String attributeRootCode) {
        this.attributeRootCode = attributeRootCode;
    }

    public String getAttributeParentCode() {
        return attributeParentCode;
    }

    public void setAttributeParentCode(String attributeParentCode) {
        this.attributeParentCode = attributeParentCode;
    }

    @Override
    public String toString() {
        return "XMLField4Export{" +
                "attributeColumn='" + attributeColumn + '\'' +
                ", attributeLabel='" + attributeLabel + '\'' +
                ", attributeType='" + attributeType + '\'' +
                ", attributeFormat='" + attributeFormat + '\'' +
                ", attributeRootCode='" + attributeRootCode + '\'' +
                ", attributeParentCode='" + attributeParentCode + '\'' +
                '}';
    }
}