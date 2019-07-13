package com.wust.springcloud.autotask.server.core.entity;

/**
 * Created by WST on 2019/6/14.
 */
public class JobUpdate implements java.io.Serializable{
    private static final long serialVersionUID = -1832165799184197617L;

    private String jobName;
    private String jobClassName;
    private String jobGroupName;
    private String cronExpression;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    public String getJobGroupName() {
        return jobGroupName;
    }

    public void setJobGroupName(String jobGroupName) {
        this.jobGroupName = jobGroupName;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
}
