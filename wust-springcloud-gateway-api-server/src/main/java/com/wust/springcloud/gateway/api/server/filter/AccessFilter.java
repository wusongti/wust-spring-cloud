package com.wust.springcloud.gateway.api.server.filter;


import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.common.util.MyStringUtils;
import com.wust.springcloud.common.util.SignUtil;
import com.wust.springcloud.gateway.api.server.core.service.SsoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.Map;

/**
 * 外部系统接入网关
 * Created by WST on 2019/4/18.
 */
@Component
public class AccessFilter implements GlobalFilter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SsoService ssoService;


    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, GatewayFilterChain gatewayFilterChain) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        ServerHttpResponse response = serverWebExchange.getResponse();
        String jsonStr = MyStringUtils.null2String(request.getHeaders().get(ApplicationEnum.API_SIGN.getStringValue()).get(0));
        if(MyStringUtils.isNotBlank(jsonStr)){
            Map map = JSONObject.parseObject(jsonStr, Map.class);
            if(verifyParameter(map)){
                boolean flag = SignUtil.verify(map); // 验证签名是否合法，防止篡改请求参数
                if(flag){
                    if(ssoService.hasSign(map.get("sign").toString())){ // 防止重复的请求，如有则拦截掉
                        logger.error("重复的请求，已经被网关拦截");
                        response.setStatusCode(HttpStatus.UNAUTHORIZED);
                        return response.setComplete();
                    }else{
                        response.setStatusCode(HttpStatus.OK);
                        return gatewayFilterChain.filter(serverWebExchange);
                    }
                }else{
                    logger.error("非法的请求，接口签名校验不通过");
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return response.setComplete();
                }
            }else{
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
        }else{ // 没有签名参数，不允许访问api接口
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
    }

    /**
     * 验证承参数是否合法
     * @param map
     * @return
     */
    private boolean verifyParameter(Map map){
        if(map == null || map.size() == 0){
            return false;
        }else{
            if(!map.containsKey("companyId")){
                logger.error("缺失[companyId]参数");
                return false;
            }

            if(MyStringUtils.isBlank(MyStringUtils.null2String(map.get("companyId")))){
                logger.error("缺失[companyId]参数值");
                return false;
            }

            if(!map.containsKey("appId")){
                logger.error("缺失[appId]参数");
                return false;
            }

            if(MyStringUtils.isBlank(MyStringUtils.null2String(map.get("appId")))){
                logger.error("缺失[appId]参数值");
                return false;
            }

            if(!map.containsKey("sign")){
                logger.error("缺失[sign]参数");
                return false;
            }

            if(MyStringUtils.isBlank(MyStringUtils.null2String(map.get("sign")))){
                logger.error("缺失[sign]参数值");
                return false;
            }

            if(!map.containsKey("nonce")){
                logger.error("缺失[nonce]参数");
                return false;
            }
            if(MyStringUtils.isBlank(MyStringUtils.null2String(map.get("nonce")))){
                logger.error("缺失[nonce]参数值");
                return false;
            }


            if(!map.containsKey("timestamp")){
                logger.error("缺失[timestamp]参数");
                return false;
            }

            if(MyStringUtils.isBlank(MyStringUtils.null2String(map.get("timestamp")))){
                logger.error("缺失[timestamp]参数值");
                return false;
            }
        }
        return true;
    }
}
