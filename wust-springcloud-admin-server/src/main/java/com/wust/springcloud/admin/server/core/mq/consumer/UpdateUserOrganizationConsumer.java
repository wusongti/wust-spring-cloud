package com.wust.springcloud.admin.server.core.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.admin.server.core.service.SysUserOrganizationService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author ：wust
 * @date ：Created in 2019/8/9 10:13
 * @description：
 * @version:
 */
@Component
@RabbitListener(queues = "${queue.updateUserOrganization.name}",containerFactory = "singleListenerContainer")
public class UpdateUserOrganizationConsumer {

    @Autowired
    private SysUserOrganizationService sysUserOrganizationServiceImpl;

    @RabbitHandler
    public void process(JSONObject jsonObject) {
        sysUserOrganizationServiceImpl.init();
    }
}
