package com.wust.springcloud.common.xml.factory;


import com.wust.springcloud.common.xml.XMLAbstractResolver;
import com.wust.springcloud.common.xml.XMLDefinitionFactory;
import com.wust.springcloud.common.xml.resolver.XMLLookupResolver;

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
