package com.wust.springcloud.admin.server.core.mq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author ：wust
 * @date ：Created in 2019/7/19 15:21
 * @description：
 * @version:
 */
@Component
@RabbitListener(queues = "${queue.organization.name}",containerFactory = "singleListenerContainer")
public class OrganizationChangedReceiver {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver : " + hello);
    }

}
