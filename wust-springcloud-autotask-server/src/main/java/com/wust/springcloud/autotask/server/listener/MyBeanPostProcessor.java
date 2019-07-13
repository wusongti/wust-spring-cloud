package com.wust.springcloud.autotask.server.listener;


import com.wust.springcloud.autotask.server.core.job.BaseJob;
import com.wust.springcloud.common.annotations.JobAnnotation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

/**
 * spring管理的bean初始化完成后执行该类的方法
 * Created by WST on 2019/6/14.
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    static Logger logger = LogManager.getLogger(MyBeanPostProcessor.class);

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        processJobAnnotation(bean,beanName);
        return bean;
    }

    private void processJobAnnotation(Object bean, String beanName){
        JobAnnotation jobAnnotation = bean.getClass().getAnnotation(JobAnnotation.class);
        if (null != jobAnnotation) {
            String jobName = jobAnnotation.jobName();
            String jobGroupName = jobAnnotation.jobGroupName();
            String jobClassName = bean.getClass().getName();
            String cronExpression = jobAnnotation.cronExpression();
            Scheduler scheduler = schedulerFactoryBean.getScheduler();

            try {

                // 判断job是否已经存在
                JobKey jobKey = new JobKey(jobName,jobGroupName);
                boolean isExists = scheduler.checkExists(jobKey);

                // 判断触发器是否已经存在
                TriggerKey triggerKey = new TriggerKey(jobName,jobGroupName);
                boolean isTriggerExists = scheduler.checkExists(triggerKey);

                if(!isExists && !isTriggerExists){
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
                }
            } catch (SchedulerException e) {
                logger.error("创建作业失败：" + e);
            } catch (Exception e) {
                logger.error("创建作业失败：" + e);
            }
        }
    }

    private  BaseJob getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (BaseJob)class1.newInstance();
    }
}
