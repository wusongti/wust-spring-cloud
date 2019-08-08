package com.wust.springcloud.common.adapter;

import com.wust.springcloud.common.adapter.context.StrategyContext;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by WST on 2019/6/17.
 */
public abstract class ContextHandlerAdapter implements HandlerInterceptor {


    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
        StrategyContext.getInstance().setDefaultBusinessContext(httpServletRequest);
        return true;
    }
}
