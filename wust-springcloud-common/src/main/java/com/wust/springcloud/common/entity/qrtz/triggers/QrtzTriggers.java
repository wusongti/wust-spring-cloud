package com.wust.springcloud.common.entity.qrtz.triggers;

/**
 * table name:  qrtz_triggers
 * author name: wust
 * create time: 2019-06-13 09:55:09
 */ 
public class QrtzTriggers implements java.io.Serializable{

	private static final long serialVersionUID = 8841291229603918397L;

	private String schedName;
	private String triggerName;
	private String triggerGroup;
	private String jobName;
	private String jobGroup;
	private String description;
	private Long nextFireTime;
	private Long prevFireTime;
	private Integer priority;
	private String triggerState;
	private String triggerType;
	private Long startTime;
	private Long endTime;
	private String calendarName;
	private Short misfireInstr;
	private byte[] jobData;

	public void setSchedName(String schedName){
		this.schedName=schedName;
	}
	public String getSchedName(){
		return schedName;
	}
	public void setTriggerName(String triggerName){
		this.triggerName=triggerName;
	}
	public String getTriggerName(){
		return triggerName;
	}
	public void setTriggerGroup(String triggerGroup){
		this.triggerGroup=triggerGroup;
	}
	public String getTriggerGroup(){
		return triggerGroup;
	}
	public void setJobName(String jobName){
		this.jobName=jobName;
	}
	public String getJobName(){
		return jobName;
	}
	public void setJobGroup(String jobGroup){
		this.jobGroup=jobGroup;
	}
	public String getJobGroup(){
		return jobGroup;
	}
	public void setDescription(String description){
		this.description=description;
	}
	public String getDescription(){
		return description;
	}
	public void setNextFireTime(Long nextFireTime){
		this.nextFireTime=nextFireTime;
	}
	public Long getNextFireTime(){
		return nextFireTime;
	}
	public void setPrevFireTime(Long prevFireTime){
		this.prevFireTime=prevFireTime;
	}
	public Long getPrevFireTime(){
		return prevFireTime;
	}
	public void setPriority(Integer priority){
		this.priority=priority;
	}
	public Integer getPriority(){
		return priority;
	}
	public void setTriggerState(String triggerState){
		this.triggerState=triggerState;
	}
	public String getTriggerState(){
		return triggerState;
	}
	public void setTriggerType(String triggerType){
		this.triggerType=triggerType;
	}
	public String getTriggerType(){
		return triggerType;
	}
	public void setStartTime(Long startTime){
		this.startTime=startTime;
	}
	public Long getStartTime(){
		return startTime;
	}
	public void setEndTime(Long endTime){
		this.endTime=endTime;
	}
	public Long getEndTime(){
		return endTime;
	}
	public void setCalendarName(String calendarName){
		this.calendarName=calendarName;
	}
	public String getCalendarName(){
		return calendarName;
	}
	public void setMisfireInstr(Short misfireInstr){
		this.misfireInstr=misfireInstr;
	}
	public Short getMisfireInstr(){
		return misfireInstr;
	}
	public void setJobData(byte[] jobData){
		this.jobData=jobData;
	}
	public byte[] getJobData(){
		return jobData;
	}
}

