package com.wust.springcloud.gateway.api.server.filter;


import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.common.util.MyStringUtils;
import com.wust.springcloud.gateway.api.server.core.service.AuthorizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 外部系统接入网关
 * Created by WST on 2019/4/18.
 */
@Component
public class AccessFilter implements GlobalFilter {
    @Autowired
    private AuthorizeService authorizeService;


    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, GatewayFilterChain gatewayFilterChain) {
        ServerHttpRequest request = serverWebExchange.getRequest();

        ServerHttpResponse response = serverWebExchange.getResponse();

        HttpHeaders headers = response.getHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT");
        headers.add("Access-Control-Max-Age", "3600");
        headers.add("Access-Control-Allow-Headers", "x-requested-with,token,origin,content-type,accept");
        headers.add("Access-Control-Allow-Credentials","true");

        String reqUrl = request.getPath().toString();

        String token = MyStringUtils.null2String(request.getHeaders().get(ApplicationEnum.X_AUTH_TOKEN.getStringValue()).get(0));
        if(MyStringUtils.isBlank(token)){
            token = request.getQueryParams().getFirst("authenticate");
        }

        if (reqUrl.contains("/login4api")) { // 登录请求默认通过
            response.setStatusCode(HttpStatus.OK);
            return gatewayFilterChain.filter(serverWebExchange);
        }else { // 非登录请求，需要校验令牌和访问资源的权限
            if(MyStringUtils.isNoneBlank(MyStringUtils.null2String(token))){
                if(authorizeService.hasToken(token)){
                    response.setStatusCode(HttpStatus.OK);
                    return gatewayFilterChain.filter(serverWebExchange);
                }else{
                    response.setStatusCode(HttpStatus.NO_CONTENT);
                    return response.setComplete();
                }
            }else{
                response.setStatusCode(HttpStatus.NO_CONTENT);
                return response.setComplete();
            }
        }
    }
}
