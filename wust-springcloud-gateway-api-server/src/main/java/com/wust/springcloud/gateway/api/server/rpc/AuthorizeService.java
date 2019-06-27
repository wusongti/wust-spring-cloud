package com.wust.springcloud.gateway.api.server.rpc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by WST on 2019/4/18.
 */
@FeignClient(value = "api-sso-server")
public interface AuthorizeService {
    @GetMapping(value = "/AuthenticationController/hasToken/{token}")
    boolean hasToken(@PathVariable("token") String token);
}
