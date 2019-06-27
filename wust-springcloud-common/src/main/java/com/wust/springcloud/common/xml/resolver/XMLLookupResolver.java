package com.wust.springcloud.common.xml.resolver;


import com.wust.springcloud.common.entity.sys.lookup.SysLookup;
import com.wust.springcloud.common.exception.BusinessException;
import com.wust.springcloud.common.util.MyStringUtils;
import com.wust.springcloud.common.xml.XMLDefaultResolver;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Function:
 * Reason:
 * Date:2018/6/27
 * @author wusongti@163.com
 */
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
    private final List<SysLookup> sysLookups = new ArrayList<SysLookup>();


    @Override
    public void parseXML() throws BusinessException {
        String mainXMLPath = "dictionary" + File.separator + "lookup" + File.separator + "sys_lookup.xml";
        String mainXSDPath = "dictionary" + File.separator + "lookup" + File.separator + "sys_lookup.xsd";
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        //validateXML(mainXSDPath, mainXMLPath);
        try{
            DocumentBuilder db = dbf.newDocumentBuilder();
            org.w3c.dom.Document doc = db.parse(ResourceUtils.getFile("classpath:" + mainXMLPath));
            org.w3c.dom.Element element = doc.getDocumentElement();
            doParseXML(element);
        }catch (Exception e){
            throw new BusinessException(e);
        }
    }

    @Override
    public Map<String, List> getResult() {
        this.parseXML();
        Map<String, List> map = new HashMap<>(1);
        map.put("sysLookups",sysLookups);
        return map;
    }


    private void doParseXML(org.w3c.dom.Element element) throws Exception {
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            short nodeType = node.getNodeType();
            if (nodeType == Node.ELEMENT_NODE) {
                if (ELEMENT_RECORD.equalsIgnoreCase(node.getNodeName())) {
                    String id = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ID);
                    String code = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_CODE);
                    String parentCode = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_PARENT_CODE);
                    String rootCode = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ROOT_CODE);
                    String value = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_VALUE);
                    String name = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_NAME);
                    String status = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_STATUS);
                    String visible = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_VISIBLE);
                    String description = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_DESCRIPTION);
                    String lan = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_LAN);
                    String sort = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_SORT);


                    String uniqueKeyId = "ID" + id;
                    if (uniqueKeyIdChecking.containsKey(uniqueKeyId)) {
                        throw new BusinessException("解析sys_lookup.xml失败，ID元素值不允许重复[" + id + "]");
                    }
                    uniqueKeyIdChecking.put(uniqueKeyId, null);

                    String uniqueKeyCode = "CODE" + code;
                    if (uniqueKeyCodeChecking.containsKey(uniqueKeyCode)) {
                        throw new BusinessException("解析sys_lookup.xml失败，CODE元素值不允许重复[" + code + "]");
                    }
                    uniqueKeyCodeChecking.put(uniqueKeyCode, null);


                    SysLookup lookup = new SysLookup();
                    lookup.setId(id);
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
                    this.sysLookups.add(lookup);
                }
                doParseXML((org.w3c.dom.Element) node);
            }
        }
    }
}
