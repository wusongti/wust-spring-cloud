package com.wust.springcloud.sso.server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by WST on 2019/3/29.
 */
@EnableEurekaClient
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = {"com.wust.springcloud.sso.server", "com.wust.springcloud.common"})
public class SsoServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SsoServerApplication.class, args);
    }
}
