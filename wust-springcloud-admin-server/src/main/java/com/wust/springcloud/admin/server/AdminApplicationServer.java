package com.wust.springcloud.admin.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableFeignClients
@EnableEurekaClient
@EnableTransactionManagement
@MapperScan("com.wust.springcloud.admin.server.dao")
@SpringBootApplication(scanBasePackages = {"com.wust.springcloud.admin.server", "com.wust.springcloud.common"})
public class AdminApplicationServer {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplicationServer.class, args);
    }

}
