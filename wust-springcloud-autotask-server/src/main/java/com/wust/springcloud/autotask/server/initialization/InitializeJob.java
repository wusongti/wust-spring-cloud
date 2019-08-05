package com.wust.springcloud.autotask.server.initialization;

import com.wust.springcloud.autotask.server.core.job.BaseJob;
import com.wust.springcloud.common.annotations.JobAnnotation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.Set;

/**
 * @author ：wust
 * @date ：Created in 2019/8/5 10:06
 * @description：
 * @version:
 */
@Component
public class InitializeJob {
    static Logger logger = LogManager.getLogger(InitializeJob.class);

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    /**
     * 先根据注解获取到bean，再根据bean获取其注解信息
     * @param applicationReadyEvent
     */
    public void init(ApplicationReadyEvent applicationReadyEvent){
        Map<String, Object> beansWithAnnotationMap = applicationReadyEvent.getApplicationContext().getBeansWithAnnotation(JobAnnotation.class);
        Set<Map.Entry<String, Object>> entrySet =  beansWithAnnotationMap.entrySet();
        for (Map.Entry<String, Object> stringObjectEntry : entrySet) {
            JobAnnotation jobAnnotation = AopUtils.getTargetClass(stringObjectEntry.getValue()).getAnnotation(JobAnnotation.class);
            if(jobAnnotation != null){
                String jobName = jobAnnotation.jobName();
                String jobGroupName = jobAnnotation.jobGroupName();
                String jobClassName =  AopUtils.getTargetClass(stringObjectEntry.getValue()).getName();
                String cronExpression = jobAnnotation.cronExpression();

                Scheduler scheduler = schedulerFactoryBean.getScheduler();

                try {

                    // 判断job是否已经存在
                    JobKey jobKey = new JobKey(jobName,jobGroupName);
                    boolean isExists = scheduler.checkExists(jobKey);

                    // 判断触发器是否已经存在
                    TriggerKey triggerKey = new TriggerKey(jobName,jobGroupName);
                    boolean isTriggerExists = scheduler.checkExists(triggerKey);

                    // 存在则删除
                    if(isExists || isTriggerExists){
                        scheduler.deleteJob(jobKey);
                    }

                    //构建job信息
                    JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass()).withIdentity(jobName, jobGroupName).build();

                    //表达式调度构建器(即任务执行的时间)
                    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

                    //按新的cronExpression表达式构建一个新的trigger
                    CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName).withSchedule(scheduleBuilder).build();

                    scheduler.scheduleJob(jobDetail, trigger);

                    if(!scheduler.isShutdown()){
                        scheduler.start();
                    }
                } catch (SchedulerException e) {
                    logger.error("创建作业失败：" + e);
                } catch (Exception e) {
                    logger.error("创建作业失败：" + e);
                }
            }
        }
    }

    private BaseJob getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (BaseJob)class1.newInstance();
    }
}
