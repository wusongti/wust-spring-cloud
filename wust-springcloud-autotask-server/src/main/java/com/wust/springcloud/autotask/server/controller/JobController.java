package com.wust.springcloud.autotask.server.controller;

import com.wust.springcloud.autotask.server.entity.JobAdd;
import com.wust.springcloud.autotask.server.entity.JobUpdate;
import com.wust.springcloud.autotask.server.job.BaseJob;
import com.wust.springcloud.autotask.server.service.JobService;
import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.BaseDto;
import com.wust.springcloud.common.dto.MessageMap;
import com.wust.springcloud.common.entity.qrtz.jobandtrigger.QrtzJobAndTriggerList;
import com.wust.springcloud.common.entity.qrtz.jobandtrigger.QrtzJobAndTriggerSearch;
import com.wust.springcloud.common.entity.sys.company.SysCompanyList;
import com.wust.springcloud.common.entity.sys.company.SysCompanySearch;
import com.wust.springcloud.common.entity.sys.company.SysCompanyUpdate;
import com.wust.springcloud.common.entity.sys.organization.SysOrganizationList;
import com.wust.springcloud.common.entity.sys.organization.SysOrganizationSearch;
import com.wust.springcloud.common.enums.OperationLogEnum;
import com.wust.springcloud.common.util.MyStringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Created by WST on 2019/6/13.
 */
@RequestMapping("/JobController")
@RestController
public class JobController {
    @Autowired
    private JobService jobServiceImpl;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;


    @RequestMapping(value = "/listPage",method = RequestMethod.POST)
    public @ResponseBody
    BaseDto listPage(@RequestBody QrtzJobAndTriggerSearch search){
        BaseDto baseDto = new BaseDto();
        MessageMap mm = new MessageMap();
        List<QrtzJobAndTriggerList> qrtzJobAndTriggerLists =  jobServiceImpl.listPage(search);
        baseDto.setPage(search.getPageDto());
        baseDto.setLstDto(qrtzJobAndTriggerLists);
        baseDto.setMessageMap(mm);
        return baseDto;
    }


    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public @ResponseBody
    MessageMap create(@RequestBody JobAdd entity){
        MessageMap mm = new MessageMap();

       Scheduler scheduler = schedulerFactoryBean.getScheduler();

        try {

            // 判断job是否已经存在
            JobKey jobKey = new JobKey(entity.getJobName(),entity.getJobGroupName());
            boolean isExists = scheduler.checkExists(jobKey);

            // 判断触发器是否已经存在
            TriggerKey triggerKey = new TriggerKey(entity.getJobName(),entity.getJobGroupName());
            boolean isTriggerExists = scheduler.checkExists(triggerKey);

            if(isExists && isTriggerExists){
                mm.setFlag(MessageMap.INFOR_WARNING);
                mm.setMessage("您要创建的作业已经存在，作业名称["+entity.getJobName()+"]");
                return mm;
            }

            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob(getClass(entity.getJobClassName()).getClass()).withIdentity(entity.getJobName(), entity.getJobGroupName()).build();

            //表达式调度构建器(即任务执行的时间)
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(entity.getCronExpression());

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(entity.getJobName(), entity.getJobGroupName()).withSchedule(scheduleBuilder).build();

            scheduler.scheduleJob(jobDetail, trigger);

            if(!scheduler.isShutdown()){
                scheduler.start();
            }
        } catch (SchedulerException e) {
           mm.setFlag(MessageMap.INFOR_WARNING);
           mm.setMessage("创建作业任务失败");
           return mm;
        } catch (Exception e) {
            mm.setFlag(MessageMap.INFOR_WARNING);
            mm.setMessage("创建作业任务失败");
            return mm;
        }finally {
        }
        return mm;
    }

    /**
     * 暂停
     * @param entity
     * @return
     */
    @RequestMapping(value = "/pause",method = RequestMethod.POST)
    public @ResponseBody
    MessageMap pause(@RequestBody JobUpdate entity){
        MessageMap mm = new MessageMap();
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            JobKey jobKey = new JobKey(entity.getJobName(), entity.getJobGroupName());
            boolean isExists = scheduler.checkExists(jobKey);
            if (isExists) {
                scheduler.pauseJob(jobKey);
            }
        }catch (SchedulerException e){
            mm.setFlag(MessageMap.INFOR_WARNING);
            mm.setMessage("暂停作业失败");
            return mm;
        } catch (Exception e) {
            mm.setFlag(MessageMap.INFOR_WARNING);
            mm.setMessage("暂停作业失败");
            return mm;
        }
        return mm;
    }

    /**
     * 恢复
     * @param entity
     * @return
     */
    @RequestMapping(value = "/resume",method = RequestMethod.POST)
    public @ResponseBody
    MessageMap resume(@RequestBody JobUpdate entity){
        MessageMap mm = new MessageMap();
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            JobKey jobKey = new JobKey(entity.getJobName(), entity.getJobGroupName());
            boolean isExists = scheduler.checkExists(jobKey);
            if (isExists) {
                scheduler.resumeJob(jobKey);
            }
        }catch (SchedulerException e){
            mm.setFlag(MessageMap.INFOR_WARNING);
            mm.setMessage("恢复作业失败");
            return mm;
        } catch (Exception e) {
            mm.setFlag(MessageMap.INFOR_WARNING);
            mm.setMessage("恢复作业失败");
            return mm;
        }
        return mm;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public @ResponseBody
    MessageMap update(@RequestBody JobUpdate entity){
        MessageMap mm = new MessageMap();
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        try {
            JobKey jobKey = new JobKey(entity.getJobName(), entity.getJobGroupName());
            boolean isExists = scheduler.checkExists(jobKey);
            if (isExists) {
                scheduler.deleteJob(jobKey);
            }

            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob(getClass(entity.getJobClassName()).getClass()).withIdentity(entity.getJobName(), entity.getJobGroupName()).build();

            //表达式调度构建器(即任务执行的时间)
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(entity.getCronExpression());

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(entity.getJobName(), entity.getJobGroupName()).withSchedule(scheduleBuilder).build();

            scheduler.scheduleJob(jobDetail, trigger);

            if(!scheduler.isShutdown()){
                scheduler.start();
            }
        }catch (SchedulerException e){
            mm.setFlag(MessageMap.INFOR_WARNING);
            mm.setMessage("修改作业失败");
            return mm;
        } catch (Exception e) {
            mm.setFlag(MessageMap.INFOR_WARNING);
            mm.setMessage("修改作业失败");
            return mm;
        }
        return mm;
    }


    @RequestMapping(value = "/delete/{jobName}/{jobGroupName}",method = RequestMethod.DELETE)
    public @ResponseBody
    MessageMap delete(@PathVariable String jobName,@PathVariable String jobGroupName){
        MessageMap mm = new MessageMap();
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        try {
            JobKey jobKey = new JobKey(jobName, jobGroupName);
            boolean isExists = scheduler.checkExists(jobKey);

            if (isExists) {
                scheduler.deleteJob(jobKey);
            }
        }catch (SchedulerException e){
            mm.setFlag(MessageMap.INFOR_WARNING);
            mm.setMessage("删除作业失败");
            return mm;
        }
        return mm;
    }


    private static BaseJob getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (BaseJob)class1.newInstance();
    }
}
