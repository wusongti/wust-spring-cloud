package com.wust.springcloud.common.context;

/**
 * Created by WST on 2019/5/6.
 */
public class BaseBusinessContext implements java.io.Serializable{

    protected String companyId;
    protected String lan;
    protected String dataSourceId;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }
}
