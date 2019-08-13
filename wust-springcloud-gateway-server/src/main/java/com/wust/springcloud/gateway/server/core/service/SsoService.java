package com.wust.springcloud.gateway.server.core.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by WST on 2019/4/18.
 */
@FeignClient(value = "api-sso-server")
public interface SsoService {
    @GetMapping(value = "/AuthenticationRpc/v1/hasToken/{token}")
    boolean hasToken(@PathVariable("token") String token);
}
