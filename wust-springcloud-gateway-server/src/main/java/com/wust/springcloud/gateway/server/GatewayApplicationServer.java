package com.wust.springcloud.gateway.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@EnableEurekaClient
@EnableCaching
@EnableDiscoveryClient
@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = {"com.wust.springcloud.gateway.server", "com.wust.springcloud.common"})
public class GatewayApplicationServer {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplicationServer.class, args);
    }


}
