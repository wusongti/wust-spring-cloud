package com.wust.springcloud.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by WST on 2019/2/18.
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaApplicationServer {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplicationServer.class, args);
    }
}
