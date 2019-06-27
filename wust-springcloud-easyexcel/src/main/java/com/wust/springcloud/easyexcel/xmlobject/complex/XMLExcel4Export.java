package com.wust.springcloud.easyexcel.xmlobject.complex;/**
 * Created by wust on 2018/1/15.
 */

import com.wust.springcloud.easyexcel.ConfigDefinitionBean;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 *
 * Function:
 * Reason:
 * Date:2018/1/15
 * @author wusongti@163.com
 */
public class XMLExcel4Export extends ConfigDefinitionBean {
    private String attributeId;
    private List<XMLSheet4Export> xmlSheet4ExportList;

    public boolean isEmpty(){
        if(attributeId == null
                && CollectionUtils.isEmpty(xmlSheet4ExportList)){
            return true;
        }
        return false;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public List<XMLSheet4Export> getXmlSheet4ExportList() {
        return xmlSheet4ExportList;
    }

    public void setXmlSheet4ExportList(List<XMLSheet4Export> xmlSheet4ExportList) {
        this.xmlSheet4ExportList = xmlSheet4ExportList;
    }
}
