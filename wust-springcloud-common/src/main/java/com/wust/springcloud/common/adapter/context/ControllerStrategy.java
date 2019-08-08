package com.wust.springcloud.common.adapter.context;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：wust
 * @date ：Created in 2019/8/8 10:48
 * @description：前端请求走这里设置上下文
 * @version:
 */
public class ControllerStrategy implements IStrategy{
    @Override
    public void setDefaultBusinessContext(HttpServletRequest request) {

    }
}
