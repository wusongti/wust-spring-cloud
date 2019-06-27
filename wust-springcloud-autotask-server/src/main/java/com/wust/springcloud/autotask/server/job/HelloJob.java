package com.wust.springcloud.autotask.server.job;

import com.wust.springcloud.common.annotations.JobAnnotation;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * Created by WST on 2019/6/13.
 */

@Component
@JobAnnotation(jobName="HelloJob",jobDescription="测试工作任务",jobGroupName="system",cronExpression="0 * * * * ? *")
public class HelloJob implements BaseJob{
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("执行了。。。");
    }
}
