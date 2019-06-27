package com.wust.springcloud.easyexcel.xmlobject.common;

import com.wust.springcloud.common.util.MyStringUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * SQL元素
 */
public class XMLSQL4Export{
    // attributeId属性
    private String attributeId;

    // SQL文本
    private String sqlText;

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public boolean isEmpty(){
        if(StringUtils.isBlank(MyStringUtils.null2String(attributeId))
                && StringUtils.isBlank(MyStringUtils.null2String(sqlText))){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "XMLSQL4Export{" +
                "attributeId='" + attributeId + '\'' +
                ", sqlText='" + sqlText + '\'' +
                '}';
    }
}