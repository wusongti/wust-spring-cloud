package com.wust.springcloud.common.interceptors;

import com.wust.springcloud.common.interceptors.context.StrategyContext;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by WST on 2019/6/17.
 */
public class ContextHandlerDefaultInterceptor implements HandlerInterceptor {


    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
        StrategyContext.getInstance().setDefaultBusinessContext(httpServletRequest);
        return true;
    }
}
