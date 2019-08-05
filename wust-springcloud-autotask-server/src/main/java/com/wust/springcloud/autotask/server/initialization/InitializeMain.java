package com.wust.springcloud.autotask.server.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author ：wust
 * @date ：Created in 2019/8/2 17:48
 * @description：
 * @version:
 */
@Component
public class InitializeMain implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
   private InitializeJob initializeJob;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        initializeJob.init(applicationReadyEvent);
    }
}
