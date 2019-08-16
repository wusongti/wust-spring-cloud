package com.wust.springcloud.common.context;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by WST on 2019/5/6.
 */
public class BaseBusinessContext implements java.io.Serializable{

    protected Long companyId;
    protected Locale locale;
    protected String dataSourceId;
    private HttpServletRequest request;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Locale getLocale() {
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

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
