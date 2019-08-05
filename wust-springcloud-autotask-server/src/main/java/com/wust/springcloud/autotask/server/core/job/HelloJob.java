package com.wust.springcloud.autotask.server.core.job;

import com.wust.springcloud.autotask.server.core.service.JobService;
import com.wust.springcloud.common.annotations.JobAnnotation;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.entity.qrtz.jobandtrigger.QrtzJobAndTriggerList;
import com.wust.springcloud.common.entity.qrtz.jobandtrigger.QrtzJobAndTriggerSearch;
import com.wust.springcloud.common.util.cache.SpringRedisTools;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WST on 2019/6/13.
 */

@Component
@JobAnnotation(jobName="HelloJob",jobDescription="测试工作任务",jobGroupName="system",cronExpression="0 * * * * ? *")
public class HelloJob implements BaseJob{
    @Autowired
    private JobService jobServiceImpl;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        DefaultBusinessContext.getContext().setDataSourceId("wust-quartz");
        QrtzJobAndTriggerSearch search = new QrtzJobAndTriggerSearch();
        List<QrtzJobAndTriggerList> list = jobServiceImpl.listPage(search);
        System.out.println("找到数据");
        System.out.println(list);
    }
}
