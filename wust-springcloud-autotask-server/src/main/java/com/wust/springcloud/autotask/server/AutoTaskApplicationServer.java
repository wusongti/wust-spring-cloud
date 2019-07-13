package com.wust.springcloud.autotask.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableFeignClients
@EnableEurekaClient
@EnableTransactionManagement
@MapperScan("com.wust.springcloud.autotask.server.core.dao")
@SpringBootApplication(scanBasePackages = {"com.wust.springcloud.autotask.server", "com.wust.springcloud.common"})
public class AutoTaskApplicationServer {

    public static void main(String[] args) {
        SpringApplication.run(AutoTaskApplicationServer.class, args);
    }

}
