package com.wust.springcloud.common.adapter.context;


import javax.servlet.http.HttpServletRequest;

public interface IStrategy {
    void setDefaultBusinessContext(HttpServletRequest request);
}
