package com.wust.springcloud.admin.server.interceptors;


import com.wust.springcloud.admin.server.core.service.AuthorizeService;
import com.wust.springcloud.common.interceptors.ContextHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Created by WST on 2019/5/27.
 */
public class ContextHandlerInterceptor extends ContextHandlerAdapter implements HandlerInterceptor {
    @Autowired
    private AuthorizeService authorizeService;


    @Override
    protected boolean hasToken(String token) {
        return authorizeService.hasToken(token);
    }

    @Override
    protected String getUserContextDtoByToken(String token) {
        return authorizeService.getUserContextDtoByToken(token);
    }
}
