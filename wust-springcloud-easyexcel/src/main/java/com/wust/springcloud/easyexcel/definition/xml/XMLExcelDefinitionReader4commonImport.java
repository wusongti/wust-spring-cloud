package com.wust.springcloud.easyexcel.definition.xml;/**
 * Created by wust on 2017/8/7.
 */

import com.wust.springcloud.easyexcel.ConfigDefinitionBean;
import com.wust.springcloud.easyexcel.EasyExcelEnum;
import com.wust.springcloud.easyexcel.exception.EasyExcelException;
import com.wust.springcloud.easyexcel.xmlobject.common.XMLExcel4Import;
import com.wust.springcloud.easyexcel.xmlobject.common.XMLField4Import;
import com.wust.springcloud.easyexcel.xmlobject.common.XMLList4Import;
import com.wust.springcloud.easyexcel.xmlobject.common.XMLSheet4Import;
import org.apache.commons.lang3.StringUtils;
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
 * Function:基于XML：普通导入配置注册
 * Reason:
 * Date:2017/8/7
 * @author wusongti@163.com
 */
public class XMLExcelDefinitionReader4commonImport extends XMLExcelDefinitionReader {

    public XMLExcelDefinitionReader4commonImport(String xsdPath, String xmlPath) {
        super(xsdPath, xmlPath);
    }

    @Override
    public ConfigDefinitionBean registerConfigDefinitions() {
        ConfigDefinitionBean configDefinitionBean = ConfigDefinitionBeanFactory4common.getInstance().getImportRegisterConfigDefinitions(this.xmlPath,this);
        return configDefinitionBean;
    }


    public void doRegisterConfigDefinitions(InputStream inputStream,XMLExcel4Import xmlExcel4Import) throws EasyExcelException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputStream);
            inputStream.close();

            Element root = doc.getDocumentElement();
            doParseXML4Import(root,xmlExcel4Import);
        } catch (ParserConfigurationException e) {
            throw new EasyExcelException(e);
        } catch (SAXException e) {
            throw new EasyExcelException(e);
        } catch (IOException e) {
            throw new EasyExcelException(e);
        }
    }



    private void doParseXML4Import(Element element,XMLExcel4Import xmlExcel4Import) {
        if(EasyExcelEnum.excel.name().equalsIgnoreCase(element.getNodeName())) {// excel
            String attributeId = element.getAttribute(EasyExcelEnum.id.name());

            xmlExcel4Import.setXmlSheet4ImportList(new ArrayList<XMLSheet4Import>());

            xmlExcel4Import.setAttributeId(attributeId);
        }

        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            short nodeType = node.getNodeType();
            if (nodeType == Node.ELEMENT_NODE) {
                if(EasyExcelEnum.sheet.name().equalsIgnoreCase(node.getNodeName())){// sheet节点

                    XMLSheet4Import xmlSheet = new XMLSheet4Import();

                    String attributeIndex = ((Element)node).getAttribute(EasyExcelEnum.index.name());

                    xmlSheet.setAttributeIndex(StringUtils.isBlank(attributeIndex) ? 0:Integer.parseInt(attributeIndex));

                    xmlSheet.setXmlList4Import(new XMLList4Import());

                    xmlExcel4Import.getXmlSheet4ImportList().add(xmlSheet);

                }else if(EasyExcelEnum.list.name().equalsIgnoreCase(node.getNodeName())) {// list节点

                    String attributeIndex = ((Element) node).getAttribute(EasyExcelEnum.index.name());

                    String attributeStartRow = ((Element) node).getAttribute(EasyExcelEnum.startRow.name());

                    String attributeClass = ((Element) node).getAttribute("class");

                    XMLList4Import xmlList = new XMLList4Import();

                    xmlList.setAttributeIndex(StringUtils.isBlank(attributeIndex) ? 0 : Integer.parseInt(attributeIndex));

                    xmlList.setElementFields(new ArrayList<XMLField4Import>());

                    xmlList.setAttributeStartRow(Integer.parseInt(attributeStartRow));

                    xmlList.setAttributeClass(attributeClass);

                    List<XMLSheet4Import> xmlSheet4ImportList = xmlExcel4Import.getXmlSheet4ImportList();
                    xmlSheet4ImportList.get(xmlSheet4ImportList.size() - 1).setXmlList4Import(xmlList);

                }else if(EasyExcelEnum.field.name().equalsIgnoreCase(node.getNodeName())){// field节点

                    String attributeIndex = ((Element)node).getAttribute(EasyExcelEnum.index.name());

                    String attributeType = ((Element)node).getAttribute(EasyExcelEnum.type.name());

                    String property = ((Element)node).getAttribute(EasyExcelEnum.property.name());

                    String pattern = ((Element)node).getAttribute(EasyExcelEnum.pattern.name());

                    String format = ((Element)node).getAttribute(EasyExcelEnum.format.name());

                    String required = ((Element)node).getAttribute(EasyExcelEnum.required.name());

                    String rootCode = ((Element)node).getAttribute(EasyExcelEnum.rootCode.name());

                    String regex = ((Element)node).getAttribute(EasyExcelEnum.regex.name());

                    String regexErrMsg = ((Element)node).getAttribute(EasyExcelEnum.regexErrMsg.name());

                    String scale = ((Element)node).getAttribute(EasyExcelEnum.scale.name());

                    String roundingMode = ((Element)node).getAttribute(EasyExcelEnum.roundingMode.name());

                    XMLField4Import xmlField = new XMLField4Import();

                    xmlField.setAttributeProperty(property);

                    xmlField.setAttributeIndex(StringUtils.isBlank(attributeIndex) ? 0:Integer.parseInt(attributeIndex));

                    xmlField.setAttributeType(attributeType);

                    xmlField.setAttributePattern(pattern);

                    xmlField.setAttributeFormat(format);

                    xmlField.setAttributeRequired(required);

                    xmlField.setAttributeRootCode(rootCode);

                    xmlField.setAttributeRegex(regex);

                    xmlField.setAttributeRegexErrMsg(regexErrMsg);

                    if(StringUtils.isNotBlank(scale)){
                        xmlField.setAttributeScale(Integer.parseInt(scale));
                    }

                    if(StringUtils.isNotBlank(roundingMode)){
                        xmlField.setAttributeRoundingMode(Integer.parseInt(roundingMode));
                    }


                    List<XMLSheet4Import> xmlSheet4ImportList = xmlExcel4Import.getXmlSheet4ImportList();

                    xmlSheet4ImportList.get(xmlSheet4ImportList.size()-1).getXmlList4Import().getElementFields().add(xmlField);
                }

                doParseXML4Import((Element) node,xmlExcel4Import);
            }
        }
    }
}
