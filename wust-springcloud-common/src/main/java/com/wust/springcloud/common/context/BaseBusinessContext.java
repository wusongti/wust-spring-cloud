package com.wust.springcloud.common.context;

import java.util.Locale;

/**
 * Created by WST on 2019/5/6.
 */
public class BaseBusinessContext implements java.io.Serializable{

    protected String companyId;
    protected Locale locale;
    protected String dataSourceId;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Locale getLocale() {
        // TODO 默认获取本地locale
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }
}
