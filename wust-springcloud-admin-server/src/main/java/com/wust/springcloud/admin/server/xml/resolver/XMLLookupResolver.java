package com.wust.springcloud.admin.server.xml.resolver;


import com.wust.springcloud.common.entity.sys.lookup.SysLookup;
import com.wust.springcloud.common.exception.BusinessException;
import com.wust.springcloud.common.util.MyStringUtils;
import com.wust.springcloud.common.xml.XMLDefaultResolver;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

/**
 *
 * Function:
 * Reason:
 * Date:2018/6/27
 * @author wusongti@163.com
 */
@Component
public class XMLLookupResolver extends XMLDefaultResolver {

    /**
     * 元素名称
     */
    private static final String ELEMENT_RECORD = "record";
    private static final String ELEMENT_ID = "id";
    private static final String ELEMENT_CODE = "code";
    private static final String ELEMENT_PARENT_CODE = "parent_code";
    private static final String ELEMENT_ROOT_CODE = "root_code";
    private static final String ELEMENT_VALUE = "value";
    private static final String ELEMENT_NAME = "name";
    private static final String ELEMENT_STATUS = "status";
    private static final String ELEMENT_VISIBLE = "visible";
    private static final String ELEMENT_DESCRIPTION = "description";
    private static final String ELEMENT_LAN = "lan";
    private static final String ELEMENT_SORT = "sort";


    private final Map<String, String> uniqueKeyIdChecking = new HashMap<>();
    private final Map<String, String> uniqueKeyCodeChecking = new HashMap<>();
    private final Map<String,List> map = new HashMap<>(2);
    private List<SysLookup> sysLookups;


    @Override
    public void parseXML() throws BusinessException {
        String[] mainXMLPaths = {"dictionary" + File.separator + "lookup" + File.separator + "sys_lookup.xml","dictionary" + File.separator + "lookup" + File.separator + "sys_lookup_en_US.xml"};
        String mainXSDPath = "dictionary" + File.separator + "lookup" + File.separator + "sys_lookup.xsd";
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        //validateXML(mainXSDPath, mainXMLPath);
        try{
            DocumentBuilder db = dbf.newDocumentBuilder();
            for (String mainXMLPath : mainXMLPaths) {
                String lan = mainXMLPath.substring(mainXMLPath.length() - 9,mainXMLPath.indexOf("."));
                if(mainXMLPath.contains("sys_lookup.xml")){
                    lan = "zh_CN";
                }
                sysLookups = new ArrayList<>();
                map.put(lan,sysLookups);
                org.w3c.dom.Document doc = db.parse(new ClassPathResource(mainXMLPath).getInputStream());
                org.w3c.dom.Element element = doc.getDocumentElement();
                doParseXML(element,lan);
            }
        }catch (Exception e){
            throw new BusinessException(e);
        }
    }

    @Override
    public Map<String, List> getResult() {
        this.parseXML();
        return map;
    }


    private void doParseXML(org.w3c.dom.Element element, String lan) throws Exception {
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            short nodeType = node.getNodeType();
            if (nodeType == Node.ELEMENT_NODE) {
                if (ELEMENT_RECORD.equalsIgnoreCase(node.getNodeName())) {
                    String code = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_CODE);
                    String parentCode = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_PARENT_CODE);
                    String rootCode = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ROOT_CODE);
                    String value = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_VALUE);
                    String name = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_NAME);
                    String status = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_STATUS);
                    String visible = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_VISIBLE);
                    String description = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_DESCRIPTION);
                    String sort = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_SORT);

                    String uniqueKeyCode = "CODE" + code + lan;
                    if (uniqueKeyCodeChecking.containsKey(uniqueKeyCode)) {
                        throw new BusinessException("解析sys_lookup.xml失败，CODE元素值不允许重复[" + code + "]");
                    }
                    uniqueKeyCodeChecking.put(uniqueKeyCode, null);


                    SysLookup lookup = new SysLookup();
                    lookup.setCode(code);
                    lookup.setParentCode(MyStringUtils.isBlank(MyStringUtils.null2String(rootCode)) ? "0000" : parentCode);
                    lookup.setRootCode(MyStringUtils.isBlank(MyStringUtils.null2String(rootCode)) ? "0000" : rootCode);
                    lookup.setValue(value);
                    lookup.setName(name);
                    lookup.setStatus(MyStringUtils.isBlank(MyStringUtils.null2String(status)) ? "100201" : status);
                    lookup.setSort(MyStringUtils.isBlank(sort) ? 0 : Integer.parseInt(sort));
                    lookup.setVisible(MyStringUtils.isBlank(visible) ? "100701" : ("Y".equalsIgnoreCase(visible) ? "100701" : "100702"));
                    lookup.setDescription(description);
                    lookup.setLan(MyStringUtils.isBlank(MyStringUtils.null2String(lan)) ? "zh_CN" : lan);
                    lookup.setCreateTime(new Date());
                    this.sysLookups.add(lookup);
                }
                doParseXML((org.w3c.dom.Element) node,lan);
            }
        }
    }
}
