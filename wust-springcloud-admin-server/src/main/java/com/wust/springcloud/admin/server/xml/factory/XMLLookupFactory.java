package com.wust.springcloud.admin.server.xml.factory;


import com.wust.springcloud.admin.server.xml.resolver.XMLLookupResolver;
import com.wust.springcloud.common.xml.XMLAbstractResolver;
import com.wust.springcloud.common.xml.XMLDefinitionFactory;

/**
 *
 * Function:
 * Reason:
 * Date:2018/6/27
 * @author wusongti@163.com
 */
public class XMLLookupFactory implements XMLDefinitionFactory {

    @Override
    public XMLAbstractResolver createXMLResolver() {
        XMLAbstractResolver xmlAbstractResolver = new XMLLookupResolver();
        return xmlAbstractResolver;
    }
}
