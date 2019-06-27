package com.wust.springcloud.common.dto;

import java.util.Map;

/**
 * Created by WST on 2019/5/20.
 */
public class ExcelDto implements java.io.Serializable{

    private static final long serialVersionUID = -3603379527831538941L;

    private String batchNo;
    private String xmlName;
    private String moduleName;
    private String excelName;
    private String excelSuffix;
    private Map parameters;

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getXmlName() {
        return xmlName;
    }

    public void setXmlName(String xmlName) {
        this.xmlName = xmlName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getExcelName() {
        return excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }

    public String getExcelSuffix() {
        return excelSuffix;
    }

    public void setExcelSuffix(String excelSuffix) {
        this.excelSuffix = excelSuffix;
    }

    public Map getParameters() {
        return parameters;
    }

    public void setParameters(Map parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return super.toString() + "\nExcelDto{" +
                "batchNo='" + batchNo + '\'' +
                ", xmlName='" + xmlName + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", excelName='" + excelName + '\'' +
                ", excelSuffix='" + excelSuffix + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
