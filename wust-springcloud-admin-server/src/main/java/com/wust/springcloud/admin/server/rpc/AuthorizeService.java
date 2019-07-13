package com.wust.springcloud.admin.server.rpc;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by WST on 2019/3/15.
 */
@FeignClient(value = "api-sso-server")
public interface AuthorizeService {
    @GetMapping(value = "/AuthenticationApi/hasToken/{token}")
    boolean hasToken(@PathVariable("token")String token);

    @GetMapping(value = "/AuthenticationApi/getUserContextDtoByToken/{token}")
    String getUserContextDtoByToken(@PathVariable("token")String token);
}
