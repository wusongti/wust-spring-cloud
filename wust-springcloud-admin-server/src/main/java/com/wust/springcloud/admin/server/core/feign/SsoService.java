package com.wust.springcloud.admin.server.core.feign;


import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by WST on 2019/3/15.
 */
@FeignClient(value = "sso-server")
public interface SsoService {
}
