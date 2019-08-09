package com.wust.springcloud.admin.server.core.mq.producer;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author ：wust
 * @date ：Created in 2019/8/9 10:12
 * @description：更新用户组织关系生产者
 * @version:
 */
@Component
public class UpdateUserOrganizationProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment env;


    public void send(JSONObject jsonObject){
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setExchange(env.getProperty("exchange.updateUserOrganization.name"));
        rabbitTemplate.setRoutingKey(env.getProperty("routing.updateUserOrganization.key.name"));
        rabbitTemplate.convertAndSend(jsonObject);
    }
}
