package com.wust.springcloud.sso.server.interceptors;

import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.enums.ApplicationEnum;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：wust
 * @date ：Created in 2019/7/24 14:07
 * @description：拦截器，设置上下文，数据源只走平台，以为该sso服务只做登录登出操作，获取资源去缓存获取。所以不需要设置动态数据源id
 * @version:
 */
public class ContextHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        setDefaultBusinessContext(request);
        return true;
    }

    private static void setDefaultBusinessContext(HttpServletRequest httpServletRequest){
        DefaultBusinessContext.getContext().setLocale(httpServletRequest.getLocale());
        DefaultBusinessContext.getContext().setDataSourceId(ApplicationEnum.DEFAULT.name());
    }
}
