package com.wust.springcloud.admin.server.xml.factory;


import com.wust.springcloud.admin.server.xml.resolver.XMLPermissionResolver;
import com.wust.springcloud.common.xml.XMLAbstractResolver;
import com.wust.springcloud.common.xml.XMLDefinitionFactory;

public class XMLPermissionFactory implements XMLDefinitionFactory {

    @Override
    public XMLAbstractResolver createXMLResolver() {
        XMLAbstractResolver xmlAbstractResolver = new XMLPermissionResolver();
        return xmlAbstractResolver;
    }
}
