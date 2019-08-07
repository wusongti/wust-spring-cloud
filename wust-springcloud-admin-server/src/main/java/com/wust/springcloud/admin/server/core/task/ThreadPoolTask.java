package com.wust.springcloud.admin.server.core.task;


import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by WST on 2019/5/21.
 * 线程池
 */
@Component
public class ThreadPoolTask {

    @Async
    public void test() {

    }
}
