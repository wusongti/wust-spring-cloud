package com.wust.springcloud.gateway.server.filter;




import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.common.util.MyStringUtils;
import com.wust.springcloud.gateway.server.rpc.AuthorizeService;
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
 * 任何请求spring cloud家族的任何子系统，首先需要通过这一层的过滤器。
 * 此过滤器需要做如下方面的工作：
 * 1.如果是登录操作，则调用sso服务登录，sso服务登录成功后返回token给前端；
 * 2.如果是非登录操作，则从请求中获取token参数，根据token调用sso服务判断是否是有效的请求（判定有效请求有很多工作要做，比如检验是否是合法登录用户，是否有权限访问系统资源等）
 * Created by WST on 2019/3/13.
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
        String token = "";
        if(request.getHeaders().containsKey(ApplicationEnum.X_AUTH_TOKEN.getStringValue())){
            token = MyStringUtils.null2String(request.getHeaders().get(ApplicationEnum.X_AUTH_TOKEN.getStringValue()).get(0));
        }

        if(MyStringUtils.isBlank(token)){
            if(request.getQueryParams().containsKey(ApplicationEnum.X_AUTH_TOKEN.getStringValue())){
                token = request.getQueryParams().getFirst(ApplicationEnum.X_AUTH_TOKEN.getStringValue());
            }
        }

        if (reqUrl.contains("/login")) { // 登录请求默认通过
            response.setStatusCode(HttpStatus.OK);
            return gatewayFilterChain.filter(serverWebExchange);
        }else { // 非登录请求，需要校验令牌和访问资源的权限
            if(MyStringUtils.isNoneBlank(MyStringUtils.null2String(token))){
                if(authorizeService.hasToken(token)){
                    // TODO 需要判断该用户是否有权限访问资源
                    if(true){ // 授权成功，通过访问
                        response.setStatusCode(HttpStatus.OK);
                        return gatewayFilterChain.filter(serverWebExchange);
                    }else{ // 授权失败，拒绝访问
                        response.setStatusCode(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
                        return response.setComplete();
                    }
                }else{
                    System.err.print("请登录=" + token);
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
