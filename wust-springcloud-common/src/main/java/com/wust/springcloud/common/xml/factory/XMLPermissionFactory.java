package com.wust.springcloud.common.xml.factory;


import com.wust.springcloud.common.xml.XMLAbstractResolver;
import com.wust.springcloud.common.xml.XMLDefinitionFactory;
import com.wust.springcloud.common.xml.resolver.XMLPermissionResolver;

public class XMLPermissionFactory implements XMLDefinitionFactory {

    @Override
    public XMLAbstractResolver createXMLResolver() {
        XMLAbstractResolver xmlAbstractResolver = new XMLPermissionResolver();
        return xmlAbstractResolver;
    }
}
