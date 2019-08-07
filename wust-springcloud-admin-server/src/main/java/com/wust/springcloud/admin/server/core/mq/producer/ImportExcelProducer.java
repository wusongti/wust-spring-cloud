package com.wust.springcloud.admin.server.core.mq.producer;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author ：wust
 * @date ：Created in 2019/8/7 11:18
 * @description：
 * @version:
 */
@Component
public class ImportExcelProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment env;


    public void send(JSONObject jsonObject){
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setExchange(env.getProperty("exchange.importexcel.name"));
        rabbitTemplate.setRoutingKey(env.getProperty("routing.importexcel.key.name"));
        rabbitTemplate.convertAndSend(jsonObject);
    }
}
