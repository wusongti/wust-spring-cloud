package com.wust.springcloud.common.entity.sys.datasource;

/**
 * Created by WST on 2019/6/17.
 */
public class SysDataSourceList extends SysDataSource{
    private static final long serialVersionUID = 7095378836627433753L;

    private String companyName;

    private String initStatus;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getInitStatus() {
        return initStatus;
    }

    public void setInitStatus(String initStatus) {
        this.initStatus = initStatus;
    }
}
