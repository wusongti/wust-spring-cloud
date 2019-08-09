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
 * @date ：Created in 2019/8/9 10:09
 * @description： 更新用户组织关系表
 * @version:
 */
@SpringBootConfiguration
public class UpdateUserOrganizationQueueConfig {
    @Autowired
    private Environment env;

    @Bean
    public Queue updateUserOrganizationQueue() {
        return new Queue(env.getProperty("queue.updateUserOrganization.name"));
    }

    @Bean
    DirectExchange updateUserOrganizationQueueDirectExchange(){
        return new DirectExchange(env.getProperty("exchange.updateUserOrganization.name"),true,true);
    }

    @Bean
    Binding updateUserOrganizationBinding(){
        return  BindingBuilder.bind(updateUserOrganizationQueue()).to(updateUserOrganizationQueueDirectExchange()).with(env.getProperty("routing.updateUserOrganization.key.name"));
    }
}
