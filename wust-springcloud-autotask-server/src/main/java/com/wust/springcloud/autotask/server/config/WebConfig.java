package com.wust.springcloud.autotask.server.config;


import com.wust.springcloud.autotask.server.interceptors.RealmHandlerInterceptor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import java.util.List;

/**
 * Created by WST on 2019/6/12.
 */
@SpringBootConfiguration
public class WebConfig extends WebMvcConfigurationSupport {
    @Bean
    public RealmHandlerInterceptor interceptor() {
        return new RealmHandlerInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    }
}
