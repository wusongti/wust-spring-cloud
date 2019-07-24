package com.wust.springcloud.sso.server.config;


import com.wust.springcloud.sso.server.interceptors.ContextHandlerInterceptor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


/**
 * Created by WST on 2019/2/18.
 */
@SpringBootConfiguration
public class WebConfig extends WebMvcConfigurationSupport {
    @Bean
    public ContextHandlerInterceptor interceptor() {
        return new ContextHandlerInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor());
    }


}
