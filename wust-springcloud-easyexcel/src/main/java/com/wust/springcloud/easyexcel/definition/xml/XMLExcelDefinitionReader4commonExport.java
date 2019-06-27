package com.wust.springcloud.easyexcel.definition.xml;/**
 * Created by wust on 2017/8/7.
 */

import com.wust.springcloud.easyexcel.ConfigDefinitionBean;
import com.wust.springcloud.easyexcel.EasyExcelEnum;
import com.wust.springcloud.easyexcel.exception.EasyExcelException;
import com.wust.springcloud.easyexcel.xmlobject.common.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Function:基于XML：普通导出配置注册
 * Reason:
 * Date:2017/8/7
 * @author wusongti@163.com
 */
public class XMLExcelDefinitionReader4commonExport extends XMLExcelDefinitionReader {

    public XMLExcelDefinitionReader4commonExport(String xsdPath, String xmlPath) {
        super(xsdPath, xmlPath);
    }

    @Override
    public ConfigDefinitionBean registerConfigDefinitions() {
        ConfigDefinitionBean configDefinitionBean = ConfigDefinitionBeanFactory4common.getInstance().getExportRegisterConfigDefinitions(super.xmlPath,this);
        return configDefinitionBean;
    }

    public void doRegisterConfigDefinitions(InputStream inputStream,XMLExcel4Export xmlExcel4Export) throws EasyExcelException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputStream);
            inputStream.close();

            Element root = doc.getDocumentElement();
            doParseXML4Export(root,xmlExcel4Export);
        } catch (ParserConfigurationException e) {
            throw new EasyExcelException(e);
        } catch (SAXException e) {
            throw new EasyExcelException(e);
        } catch (IOException e) {
            throw new EasyExcelException(e);
        }
    }


    private void doParseXML4Export(Element element,XMLExcel4Export xmlExcel4Export) {
        if(EasyExcelEnum.excel.name().equalsIgnoreCase(element.getNodeName())) {// excel
            String attributeId = element.getAttribute(EasyExcelEnum.id.name());

            xmlExcel4Export.setXmlSheet4ExportList(new ArrayList<XMLSheet4Export>());

            xmlExcel4Export.setAttributeId(attributeId);
        }

        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            short nodeType = node.getNodeType();
            if (nodeType == Node.ELEMENT_NODE) {
                if(EasyExcelEnum.sheet.name().equalsIgnoreCase(node.getNodeName())){// sheet节点
                    Element e = (Element)node;

                    String attributeValue = "";
                    if(e.hasAttribute(EasyExcelEnum.label.name())){
                        attributeValue = e.getAttribute(EasyExcelEnum.label.name());
                    }

                    XMLSheet4Export xmlSheet = new XMLSheet4Export();

                    xmlSheet.setAttributeLabel(attributeValue);

                    xmlSheet.setXmlList4Export(new XMLList4Export());

                    xmlExcel4Export.getXmlSheet4ExportList().add(xmlSheet);
                }else if(EasyExcelEnum.list.name().equalsIgnoreCase(node.getNodeName())){// list节点
                    String attributeValue = "";

                    Element e = (Element)node;
                    if(e.hasAttribute(EasyExcelEnum.label.name())){
                        attributeValue = e.getAttribute(EasyExcelEnum.label.name());
                    }

                    XMLList4Export xmlList = new XMLList4Export();

                    xmlList.setAttributeLabel(attributeValue);

                    xmlList.setElementSql(new XMLSQL4Export());

                    xmlList.setElementFields(new ArrayList<XMLField4Export>());

                    List<XMLSheet4Export> xmlSheet4ExportList = xmlExcel4Export.getXmlSheet4ExportList();
                    xmlSheet4ExportList.get(xmlSheet4ExportList.size() - 1).setXmlList4Export(xmlList);
                }else if(EasyExcelEnum.sql.name().equalsIgnoreCase(node.getNodeName())){// sql节点
                    Element e = (Element)node;

                    String attributeId = "";
                    if(e.hasAttribute(EasyExcelEnum.id.name())){
                        attributeId = e.getAttribute(EasyExcelEnum.id.name());
                    }

                    String sqlText = e.getTextContent();

                    XMLSQL4Export xmlsql4Export = new XMLSQL4Export();
                    xmlsql4Export.setAttributeId(attributeId);
                    xmlsql4Export.setSqlText(sqlText);

                    List<XMLSheet4Export> xmlSheet4ExportList = xmlExcel4Export.getXmlSheet4ExportList();
                    xmlSheet4ExportList.get(xmlSheet4ExportList.size() - 1).getXmlList4Export().setElementSql(xmlsql4Export);

                }else if(EasyExcelEnum.field.name().equalsIgnoreCase(node.getNodeName())){// field节点

                    Element e = (Element)node;

                    String attributeLabel = "";
                    String attributeColumn = "";
                    String attributeType = "";
                    String attributeFormat = "";
                    String attributeRootCode = "";
                    String attributeParentCode = "";

                    if(e.hasAttribute(EasyExcelEnum.label.name())){
                        attributeLabel = e.getAttribute(EasyExcelEnum.label.name());
                    }
                    if(e.hasAttribute(EasyExcelEnum.column.name())){
                        attributeColumn = e.getAttribute(EasyExcelEnum.column.name());
                    }
                    if(e.hasAttribute(EasyExcelEnum.type.name())){
                        attributeType = e.getAttribute(EasyExcelEnum.type.name());
                    }
                    if(e.hasAttribute(EasyExcelEnum.format.name())){
                        attributeFormat = e.getAttribute(EasyExcelEnum.format.name());
                    }
                    if(e.hasAttribute(EasyExcelEnum.rootCode.name())){
                        attributeRootCode = e.getAttribute(EasyExcelEnum.rootCode.name());
                    }

                    XMLField4Export xmlField = new XMLField4Export();

                    xmlField.setAttributeLabel(attributeLabel);

                    xmlField.setAttributeColumn(attributeColumn);

                    xmlField.setAttributeType(attributeType);

                    xmlField.setAttributeFormat(attributeFormat);

                    xmlField.setAttributeRootCode(attributeRootCode);

                    xmlField.setAttributeParentCode(attributeParentCode);

                    List<XMLSheet4Export> xmlSheet4ExportList = xmlExcel4Export.getXmlSheet4ExportList();
                    xmlSheet4ExportList.get(xmlSheet4ExportList.size() - 1).getXmlList4Export().getElementFields().add(xmlField);
                }

                doParseXML4Export((Element) node,xmlExcel4Export);
            }
        }
    }
}
