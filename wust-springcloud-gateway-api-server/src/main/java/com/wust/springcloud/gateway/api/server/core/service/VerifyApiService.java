package com.wust.springcloud.gateway.api.server.core.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author ：wust
 * @date ：Created in 2019/7/18 14:15
 * @description：
 * @version:
 */
@FeignClient(value = "api-sso-server")
public interface VerifyApiService {
    @GetMapping(value = "/VerifyApiRpc/hasSign/{sign}")
    boolean hasSign(@PathVariable String sign);
}
