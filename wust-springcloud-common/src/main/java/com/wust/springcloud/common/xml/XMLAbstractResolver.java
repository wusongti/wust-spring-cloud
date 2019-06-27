
package com.wust.springcloud.common.xml;


import com.wust.springcloud.common.exception.BusinessException;

import java.util.List;
import java.util.Map;


public interface XMLAbstractResolver {

    /**
     * 验证XML
     * @param xsdPath
     * @param xmlPath
     * @throws BusinessException
     */
    void validateXML(String xsdPath, String xmlPath) throws BusinessException;

    /**
     * 解析XML
     * @throws BusinessException
     */
    void parseXML() throws BusinessException;

    /**
     * 获取解析结果
     * @return
     */
    Map<String,List> getResult();
}

