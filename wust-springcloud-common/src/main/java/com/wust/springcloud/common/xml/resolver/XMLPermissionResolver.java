package com.wust.springcloud.common.xml.resolver;


import com.wust.springcloud.common.entity.sys.menu.SysMenu;
import com.wust.springcloud.common.entity.sys.resource.SysResource;
import com.wust.springcloud.common.exception.BusinessException;
import com.wust.springcloud.common.util.MyStringUtils;
import com.wust.springcloud.common.xml.XMLDefaultResolver;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;


public class XMLPermissionResolver extends XMLDefaultResolver {
    static Logger logger = LogManager.getLogger(XMLPermissionResolver.class);

    /**
     * 元素名称
     */

    private static final String ELEMENT_SUBSYSTEM = "subsystem";
    private static final String ELEMENT_PATH = "path";
    private static final String ELEMENT_MODULEGROUP = "modulegroup";
    private static final String ELEMENT_MODULE = "module";
    private static final String ELEMENT_OPERATION = "operation";

    /**
     * 属性名称
     */
    private static final String ELEMENT_ATTRIBUTE_CODE = "code";
    private static final String ELEMENT_ATTRIBUTE_NAME = "name";
    private static final String ELEMENT_ATTRIBUTE_DESC = "desc";
    private static final String ELEMENT_ATTRIBUTE_PERMISSION = "permission";
    private static final String ELEMENT_ATTRIBUTE_URL = "url";
    private static final String ELEMENT_ATTRIBUTE_ORDER = "order";
    private static final String ELEMENT_ATTRIBUTE_IMG = "img";
    private static final String ELEMENT_ATTRIBUTE_PATH = "path";
    private static final String ELEMENT_ATTRIBUTE_PAGE = "page";
    private static final String ELEMENT_ATTRIBUTE_VISIBLE = "visible";



    /**
     * 解析到的数据集合
     */
    private final Map<String, Object> permissionsMap = new HashMap<>();
    private final Map<String, Object> parseMenuMap = new HashMap<>();
    private final Map<String, Object> parseResourceMap = new HashMap<>();
    private final List<SysMenu> parseMenuList = new ArrayList<SysMenu>();
    private final List<SysResource> parseResourceList = new ArrayList<SysResource>();


    @Override
    public Map<String, List> getResult() {
        this.parseXML();
        Map<String, List> map = new HashMap<>(2);
        map.put("parseMenuList",parseMenuList);
        map.put("parseResourceList",parseResourceList);
        return map;
    }

    public void parseXML() throws BusinessException {
        String mainXMLPath = "permissions" + File.separator + "xml" + File.separator + "main.xml";
        String mainXSDPath = "permissions" + File.separator + "xsd" + File.separator + "main.xsd";
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        validateXML(mainXSDPath, mainXMLPath);

        try{
            DocumentBuilder db = dbf.newDocumentBuilder();
            org.w3c.dom.Document doc = db.parse(ResourceUtils.getFile("classpath:" + mainXMLPath));
            org.w3c.dom.Element element = doc.getDocumentElement();
            doParseXML(element, db);
        }catch (Exception e){
            throw new BusinessException(e);
        }
    }


    private void doParseXML(org.w3c.dom.Element element, DocumentBuilder db) throws Exception {
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            short nodeType = node.getNodeType();
            if (nodeType == Node.ELEMENT_NODE) {
                if (ELEMENT_SUBSYSTEM.equals(node.getNodeName())) {   // 解析subsystem节点
                    String code = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ATTRIBUTE_CODE);
                    if (!parseMenuMap.containsKey(code)) {  // 忽略已经解析过的节点
                        if (!"*".equals(code) && StringUtils.isNotBlank(code)) {    // id属性值为星号或为空则可以忽略
                            String name = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ATTRIBUTE_NAME);
                            String desc = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ATTRIBUTE_DESC);
                            String permission = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ATTRIBUTE_PERMISSION);
                            String url = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ATTRIBUTE_URL);
                            String orderString = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ATTRIBUTE_ORDER);
                            String order = StringUtils.isBlank(MyStringUtils.null2String(orderString)) ? "-1" : orderString;


                            SysMenu menuEntity = new SysMenu();
                            menuEntity.setCode(code);
                            menuEntity.setName(name);
                            menuEntity.setDescription(desc);
                            menuEntity.setPermission(permission);
                            menuEntity.setUrl(url);
                            menuEntity.setLevel(0);
                            menuEntity.setSort(Integer.parseInt(order));
                            menuEntity.setPcode(null);
                            menuEntity.setType("subSystem");
                            menuEntity.setCreateTime(new Date());

                            parseMenuList.add(menuEntity);
                            parseMenuMap.put(menuEntity.getCode(), menuEntity);
                        } else {
                            logger.info("属性code值为[" + code + "]，的节点忽略");
                        }
                    } else {
                        logger.info("已经解析过此节点：" + parseMenuMap.get(code));
                    }
                } else if (ELEMENT_MODULEGROUP.equals(node.getNodeName())) {   // 解析modulegroup节点
                    String code = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ATTRIBUTE_CODE);
                    if (!parseMenuMap.containsKey(code)) {  // 忽略已经解析过的节点
                        if (!"*".equals(code) && StringUtils.isNotBlank(code)) {    // id属性值为星号或为空则可以忽略
                            String name = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ATTRIBUTE_NAME);
                            String desc = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ATTRIBUTE_DESC);
                            String permission = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ATTRIBUTE_PERMISSION);
                            String url = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ATTRIBUTE_URL);
                            String pcode = ((org.w3c.dom.Element) node.getParentNode()).getAttribute(ELEMENT_ATTRIBUTE_CODE);
                            String orderString = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ATTRIBUTE_ORDER);
                            String order = StringUtils.isBlank(MyStringUtils.null2String(orderString)) ? "-1" : orderString;
                            String img = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ATTRIBUTE_IMG);
                            String visible = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ATTRIBUTE_VISIBLE);


                            SysMenu menuEntity = new SysMenu();
                            menuEntity.setCode(code);
                            menuEntity.setName(name);
                            menuEntity.setDescription(desc);
                            menuEntity.setPermission(permission);
                            menuEntity.setUrl(url);
                            SysMenu sysMenuParent = parseMenuMap.get(pcode) == null ? null : (SysMenu) parseMenuMap.get(pcode);
                            menuEntity.setLevel(sysMenuParent == null ? 0 : (sysMenuParent.getLevel() + 1));
                            menuEntity.setSort(Integer.parseInt(order));
                            menuEntity.setImg(img);
                            menuEntity.setPcode(pcode);
                            menuEntity.setType("menuGroup");
                            menuEntity.setVisible(visible);
                            menuEntity.setCreateTime(new Date());

                            parseMenuList.add(menuEntity);
                            parseMenuMap.put(menuEntity.getCode(), menuEntity);
                        } else {
                            logger.info("属性id值为[" + code + "]，的节点忽略");
                        }
                    } else {
                        logger.info("已经解析过此节点：" + parseMenuMap.get(code));
                    }
                } else if (ELEMENT_MODULE.equals(node.getNodeName())) {
                    String code = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ATTRIBUTE_CODE);
                    if (!parseMenuMap.containsKey(code)) {  // 忽略已经解析过的节点
                        if (!"*".equals(code) && StringUtils.isNotBlank(code)) {    // id属性值为星号或为空则可以忽略
                            String name = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ATTRIBUTE_NAME);
                            String desc = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ATTRIBUTE_DESC);
                            String permission = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ATTRIBUTE_PERMISSION);
                            String url = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ATTRIBUTE_URL);
                            String pcode = ((org.w3c.dom.Element) node.getParentNode()).getAttribute(ELEMENT_ATTRIBUTE_CODE);
                            String orderString = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ATTRIBUTE_ORDER);
                            String order = StringUtils.isBlank(MyStringUtils.null2String(orderString)) ? "-1" : orderString;
                            String img = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ATTRIBUTE_IMG);
                            String page = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ATTRIBUTE_PAGE);
                            String parentPage = ((org.w3c.dom.Element) node.getParentNode()).getAttribute(ELEMENT_ATTRIBUTE_PAGE);
                            String visible = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ATTRIBUTE_VISIBLE);


                            // 设置菜单权限标识，如果权限标识没有填写，则用page名称作为permission
                            String permissionNew = "";
                            if (StringUtils.isBlank(MyStringUtils.null2String(permission))) {
                                if (StringUtils.isBlank(MyStringUtils.null2String(parentPage))) {
                                    permissionNew = page;
                                } else {
                                    permissionNew = parentPage + "_" + page;
                                }
                            } else {
                                permissionNew = permission;
                            }

                            // 判断权限标识是否重复了
                            if (!"anon".equalsIgnoreCase(permissionNew) && StringUtils.isNotBlank(permissionNew)) {
                                if (permissionsMap.containsKey(permissionNew)) {
                                    throw new Exception("模块[" + desc + "]，权限标识[" + permissionNew + "]和已有的权限标识重复，请换一个。");
                                } else {
                                    permissionsMap.put(permissionNew, null);
                                }
                            }


                            SysMenu tSysMenuParent = parseMenuMap.get(pcode) == null ? null : (SysMenu) parseMenuMap.get(pcode);
                            Integer level = isLeaf(node) ? -1 : tSysMenuParent.getLevel() + 1;
                            SysMenu menuEntity = new SysMenu();
                            menuEntity.setCode(code);
                            menuEntity.setName(name);
                            menuEntity.setDescription(desc);
                            menuEntity.setUrl(url);
                            menuEntity.setLevel(level);
                            menuEntity.setSort(Integer.parseInt(order));
                            menuEntity.setImg(img);
                            menuEntity.setPcode(pcode);
                            menuEntity.setPermission(permissionNew);
                            menuEntity.setType("menu");
                            menuEntity.setVisible(visible);
                            menuEntity.setCreateTime(new Date());

                            parseMenuList.add(menuEntity);
                            parseMenuMap.put(menuEntity.getCode(), menuEntity);
                        } else {
                            logger.info("属性code值为[" + code + "]，的节点忽略");
                        }
                    } else {
                        logger.info("已经解析过此节点：" + parseMenuMap.get(code));
                    }
                } else if (ELEMENT_OPERATION.equals(node.getNodeName())) {
                    String code = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ATTRIBUTE_CODE);
                    if (!parseResourceMap.containsKey(code)) {  // 忽略已经解析过的节点
                        if (!"*".equals(code) && StringUtils.isNotBlank(code)) {    // id属性值为星号或为空则可以忽略
                            String name = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ATTRIBUTE_NAME);
                            String desc = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ATTRIBUTE_DESC);
                            String permission = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ATTRIBUTE_PERMISSION);
                            String url = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ATTRIBUTE_URL);
                            String menuCode = ((org.w3c.dom.Element) node.getParentNode()).getAttribute(ELEMENT_ATTRIBUTE_CODE);
                            String parentPermission = ((org.w3c.dom.Element) node.getParentNode()).getAttribute(ELEMENT_ATTRIBUTE_PERMISSION);
                            String parentPage = ((org.w3c.dom.Element) node.getParentNode()).getAttribute(ELEMENT_ATTRIBUTE_PAGE);


                            /*
                             * 权限标识=父菜单权限标识.操作按钮权限标识，
                             * 如果父菜单权限标识不填写，则使用page作为前缀，如page="user"，则权限标识为user.add
                             */
                            String permissionNew = "";
                            if ("anon".equalsIgnoreCase(parentPermission) || "anon".equalsIgnoreCase(permission)) {
                                // 父级菜单是白名单，则自己默认也是白名单
                                permissionNew = "anon";
                            } else {
                                // 父级菜单没有写权限标识，则使用page作为permission
                                if (StringUtils.isBlank(MyStringUtils.null2String(parentPermission))) {
                                    permissionNew = parentPage;
                                } else {
                                    permissionNew = parentPermission;
                                }

                                // 如果operation节点没有设置权限标识，则根据name属性值生成：Search --> search, Ab Cd-->abcd
                                if (StringUtils.isBlank(MyStringUtils.null2String(permission))) {
                                    String oPermissionNew = name.replace(" ", "");
                                    oPermissionNew = oPermissionNew.replace(oPermissionNew.substring(0, 1), oPermissionNew.substring(0, 1).toLowerCase());
                                    permissionNew = permissionNew + "." + oPermissionNew;
                                } else {
                                    permissionNew = permissionNew + "." + permission;
                                }
                            }

                            SysResource resourceEntity = new SysResource();
                            resourceEntity.setCode(code);
                            resourceEntity.setName(name);
                            resourceEntity.setDescription(desc);
                            resourceEntity.setUrl(url);
                            resourceEntity.setMenuCode(menuCode);
                            resourceEntity.setPermission(permissionNew);
                            resourceEntity.setCreateTime(new Date());

                            parseResourceList.add(resourceEntity);
                            parseResourceMap.put(resourceEntity.getCode(), resourceEntity);
                        } else {
                            logger.info("属性code值为[" + code + "]，的节点忽略");
                        }
                    } else {
                        logger.info("已经解析过此节点：" + parseResourceMap.get(code));
                    }
                } else if (ELEMENT_PATH.equals(node.getNodeName())) {
                    String path = ((org.w3c.dom.Element) node).getAttribute(ELEMENT_ATTRIBUTE_PATH);
                    if (!StringUtils.isBlank(path) && !"*".equals(path)) {// 星号表示可以忽略的节点
                        String XMLPath = path;
                        String XSDPath = "permissions" + File.separator + "xsd" + File.separator + "module.xsd";
                        super.validateXML(XSDPath, XMLPath);
                        org.w3c.dom.Document doc = db.parse(ResourceUtils.getFile("classpath:" + XMLPath));
                        org.w3c.dom.Element subElement = doc.getDocumentElement();
                        doParseXML(subElement, db);
                    } else {
                        logger.info("属性path值为[" + path + "]，的节点忽略");
                    }
                } else {
                    logger.info("未知节点：" + node.getNodeName());
                }
                doParseXML((org.w3c.dom.Element) node, db);
            }
        }
    }


    private boolean isLeaf(Node node){
        boolean result = false;
        NodeList nodeChildNodes = node.getChildNodes();
        if(nodeChildNodes != null){
            for (int i = 0; i < nodeChildNodes.getLength(); i++) {
                Node child = nodeChildNodes.item(i);
                short nodeType = child.getNodeType();
                if (nodeType == Node.ELEMENT_NODE) {
                    if (ELEMENT_OPERATION.equals(child.getNodeName())) {
                        result = true;
                        break;
                    }
                }
            }
        }else {
            result = true;
        }
        return result;
    }
}
