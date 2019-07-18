package com.wust.springcloud.autotask.server.core.rpc;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by WST on 2019/6/12.
 */
@FeignClient(value = "api-sso-server")
public interface AuthorizeService {
    @GetMapping(value = "/AuthenticationRpc/hasToken/{token}")
    boolean hasToken(@PathVariable("token") String token);

    @GetMapping(value = "/AuthenticationRpc/getUserContextDtoByToken/{token}")
    String getUserContextDtoByToken(@PathVariable("token") String token);
}