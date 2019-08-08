package com.wust.springcloud.common.interceptors.context;


import javax.servlet.http.HttpServletRequest;

public interface IStrategy {
    void setDefaultBusinessContext(HttpServletRequest request);
}
