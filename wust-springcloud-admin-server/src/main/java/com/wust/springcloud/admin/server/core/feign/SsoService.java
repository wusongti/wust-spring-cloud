package com.wust.springcloud.admin.server.core.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by WST on 2019/3/15.
 */
@FeignClient(value = "api-sso-server")
public interface SsoService {
}
