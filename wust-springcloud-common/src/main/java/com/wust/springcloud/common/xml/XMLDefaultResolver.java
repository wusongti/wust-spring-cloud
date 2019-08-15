package com.wust.springcloud.common.xml;

import com.wust.springcloud.common.exception.BusinessException;
import org.springframework.core.io.ClassPathResource;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;


public abstract class XMLDefaultResolver implements XMLAbstractResolver{
    @Override
    public void validateXML(String xsdPath, String xmlPath) throws BusinessException {
        try{
            SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

            Schema schema = schemaFactory.newSchema(new ClassPathResource(xsdPath).getURL());
            Validator validator = schema.newValidator();

            Source source = new StreamSource(new ClassPathResource(xmlPath).getInputStream());
            validator.validate(source);
        } catch (Exception e) {
            throw new BusinessException("["+xmlPath+"]验证失败。"+e.toString());
        }
    }
}
