package com.wust.springcloud.admin.server.core.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * @author ：wust
 * @date ：Created in 2019/7/19 15:41
 * @description：
 * @version:
 */
@SpringBootConfiguration
public class QueueConfig {
    @Autowired
    private Environment env;

    @Bean
    public Queue organizationQueue() {
        return new Queue(env.getProperty("queue.organization.name"),true);
    }

    @Bean
    DirectExchange organizationExchange(){
        return new DirectExchange(env.getProperty("exchange.organization.name"),true,false);
    }

    @Bean
    Binding organizationBinding(){
        return  BindingBuilder.bind(organizationQueue()).to(organizationExchange()).with(env.getProperty("routing.organization.key.name"));
    }
}
