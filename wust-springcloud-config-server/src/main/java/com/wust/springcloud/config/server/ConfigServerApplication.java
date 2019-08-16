package com.wust.springcloud.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author ：wust
 * @date ：Created in 2019/8/16 13:44
 * @description：
 * @version:
 */
@EnableEurekaClient
@EnableConfigServer
@SpringBootApplication(scanBasePackages = {"com.wust.springcloud.config.server", "com.wust.springcloud.common"})
public class ConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
