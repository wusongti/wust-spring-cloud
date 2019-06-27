package com.wust.springcloud.admin.server.interceptors;


import com.wust.springcloud.admin.server.rpc.AuthorizeService;
import com.wust.springcloud.common.interceptors.RealmHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Created by WST on 2019/5/27.
 */
public class RealmHandlerInterceptor extends RealmHandlerAdapter implements HandlerInterceptor {
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
