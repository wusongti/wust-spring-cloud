package com.wust.springcloud.autotask.server.core.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by WST on 2019/6/13.
 */
public interface BaseJob extends Job{
    void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException;
}
