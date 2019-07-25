package com.wust.springcloud.autotask.server.core.job;

import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.common.annotations.JobAnnotation;
import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.common.enums.RedisKeyEnum;
import com.wust.springcloud.common.util.cache.SpringRedisTools;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by WST on 2019/6/13.
 */

@Component
@JobAnnotation(jobName="HelloJob",jobDescription="测试工作任务",jobGroupName="system",cronExpression="0 * * * * ? *")
public class HelloJob implements BaseJob{
    @Autowired
    private SpringRedisTools springRedisTools;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Object dataSourceObj = springRedisTools.getByKey(RedisKeyEnum.REDIS_TABLE_KEY_DATA_SOURCE.name());
        if(dataSourceObj != null) {
            JSONObject jsonObject = (JSONObject) dataSourceObj;
            Set<String> keys = jsonObject.keySet();
            keys.add(ApplicationEnum.DEFAULT.name());
            for (String companyId : keys) {
                System.out.println("执行了。。。" + companyId);
            }
        }

    }
}
