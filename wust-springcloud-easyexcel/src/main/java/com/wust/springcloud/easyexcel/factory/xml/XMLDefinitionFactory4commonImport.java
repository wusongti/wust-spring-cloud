package com.wust.springcloud.easyexcel.factory.xml;/**
 * Created by wust on 2017/8/4.
 */

import com.wust.springcloud.easyexcel.definition.ExcelDefinitionReader;
import com.wust.springcloud.easyexcel.definition.xml.XMLExcelDefinitionReader4commonImport;

/**
 *
 * Function:导入：基于XML的抽象工厂默认实现
 * Reason:负责生产导入定义配置对象
 * Date:2017/8/4
 * @author wusongti@163.com
 */
public class XMLDefinitionFactory4commonImport extends XMLDefinitionFactory {
    protected static final String IMPORT_XSD = "classpath:import.xsd";
    private String fullXmlName;
    public XMLDefinitionFactory4commonImport(String fullXmlName) {
        this.fullXmlName = fullXmlName;
    }

    @Override
    public ExcelDefinitionReader createExcelDefinitionReader() {
        ExcelDefinitionReader definitionReader = new XMLExcelDefinitionReader4commonImport(IMPORT_XSD,fullXmlName);
        return definitionReader;
    }
}