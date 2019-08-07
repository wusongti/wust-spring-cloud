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
 * @description：导入角色队列配置
 * @version:
 */
@SpringBootConfiguration
public class ImportExcelQueueConfig {
    @Autowired
    private Environment env;

    @Bean
    public Queue queue() {
        return new Queue(env.getProperty("queue.importexcel.name"));
    }

    @Bean
    DirectExchange exchange(){
        return new DirectExchange(env.getProperty("exchange.importexcel.name"),true,true);
    }

    @Bean
    Binding binding(){
        return  BindingBuilder.bind(queue()).to(exchange()).with(env.getProperty("routing.importexcel.key.name"));
    }
}
