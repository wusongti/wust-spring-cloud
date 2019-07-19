package com.wust.springcloud.autotask.server.core.job;

import com.wust.springcloud.common.annotations.JobAnnotation;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ：wust
 * @date ：Created in 2019/7/19 15:54
 * @description：
 * @version:
 */
@Component
@JobAnnotation(jobName="SyncDataJob",jobDescription="自动同步数据工作任务",jobGroupName="system",cronExpression="0 * * * * ? *")
public class SyncDataJob implements BaseJob{
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("SyncDataJob执行了。。。");

        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setExchange("dev.organization.exchange");
        rabbitTemplate.setRoutingKey("dev.organization.routing.key");
        rabbitTemplate.convertAndSend("你好");
    }
}
