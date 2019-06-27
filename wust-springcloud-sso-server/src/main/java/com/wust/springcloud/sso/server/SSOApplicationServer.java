package com.wust.springcloud.sso.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by WST on 2019/3/29.
 */
@EnableEurekaClient
@MapperScan("com.wust.springcloud.sso.server.dao")
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = {"com.wust.springcloud.sso.server", "com.wust.springcloud.common"})
public class SSOApplicationServer {
    public static void main(String[] args) {
        SpringApplication.run(SSOApplicationServer.class, args);
    }
}
