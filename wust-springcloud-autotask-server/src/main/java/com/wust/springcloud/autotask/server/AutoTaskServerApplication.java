package com.wust.springcloud.autotask.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableFeignClients
@EnableEurekaClient
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = {"com.wust.springcloud.autotask.server", "com.wust.springcloud.common"})
public class AutoTaskServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoTaskServerApplication.class, args);
    }

}
