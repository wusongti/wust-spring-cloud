package com.wust.springcloud.easyexcel.xmlobject.common;
/**
 * Created by wust on 2017/5/8.
 */

import com.wust.springcloud.easyexcel.ConfigDefinitionBean;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 *
 * Function:
 * Reason:
 * Date:2017/5/8
 * @author wusongti@163.com
 */
public class XMLExcel4Import extends ConfigDefinitionBean {

    private static final long serialVersionUID = -6700074651889442998L;
    private String attributeId;

    private List<XMLSheet4Import> xmlSheet4ImportList;

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public List<XMLSheet4Import> getXmlSheet4ImportList() {
        return xmlSheet4ImportList;
    }

    public void setXmlSheet4ImportList(List<XMLSheet4Import> xmlSheet4ImportList) {
        this.xmlSheet4ImportList = xmlSheet4ImportList;
    }

    public boolean isEmpty(){
        if(attributeId == null
                && CollectionUtils.isEmpty(xmlSheet4ImportList)){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "XMLExcel4Import{" +
                "attributeId='" + attributeId + '\'' +
                ", xmlSheet4ImportList=" + xmlSheet4ImportList +
                '}';
    }
}
