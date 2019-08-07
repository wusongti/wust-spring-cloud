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
 * @description：导出excel队列配置
 * @version:
 */
@SpringBootConfiguration
public class ExportExcelQueueConfig {
    @Autowired
    private Environment env;

    @Bean
    public Queue exportExcelQueue() {
        return new Queue(env.getProperty("queue.exportexcel.name"));
    }

    @Bean
    DirectExchange exportExcelQueueDirectExchange(){
        return new DirectExchange(env.getProperty("exchange.exportexcel.name"),true,true);
    }

    @Bean
    Binding exportExcelBinding(){
        return  BindingBuilder.bind(exportExcelQueue()).to(exportExcelQueueDirectExchange()).with(env.getProperty("routing.exportexcel.key.name"));
    }
}
