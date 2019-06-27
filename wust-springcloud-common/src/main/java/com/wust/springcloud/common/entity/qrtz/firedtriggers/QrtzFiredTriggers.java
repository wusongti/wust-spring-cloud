package com.wust.springcloud.common.entity.qrtz.firedtriggers;

/**
 * table name:  qrtz_fired_triggers
 * author name: wust
 * create time: 2019-06-13 09:55:09
 */ 
public class QrtzFiredTriggers{

	private String schedName;
	private String entryId;
	private String triggerName;
	private String triggerGroup;
	private String instanceName;
	private Long firedTime;
	private Long schedTime;
	private Integer priority;
	private String state;
	private String jobName;
	private String jobGroup;
	private String isNonconcurrent;
	private String requestsRecovery;

	public void setSchedName(String schedName){
		this.schedName=schedName;
	}
	public String getSchedName(){
		return schedName;
	}
	public void setEntryId(String entryId){
		this.entryId=entryId;
	}
	public String getEntryId(){
		return entryId;
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
	public void setInstanceName(String instanceName){
		this.instanceName=instanceName;
	}
	public String getInstanceName(){
		return instanceName;
	}
	public void setFiredTime(Long firedTime){
		this.firedTime=firedTime;
	}
	public Long getFiredTime(){
		return firedTime;
	}
	public void setSchedTime(Long schedTime){
		this.schedTime=schedTime;
	}
	public Long getSchedTime(){
		return schedTime;
	}
	public void setPriority(Integer priority){
		this.priority=priority;
	}
	public Integer getPriority(){
		return priority;
	}
	public void setState(String state){
		this.state=state;
	}
	public String getState(){
		return state;
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
	public void setIsNonconcurrent(String isNonconcurrent){
		this.isNonconcurrent=isNonconcurrent;
	}
	public String getIsNonconcurrent(){
		return isNonconcurrent;
	}
	public void setRequestsRecovery(String requestsRecovery){
		this.requestsRecovery=requestsRecovery;
	}
	public String getRequestsRecovery(){
		return requestsRecovery;
	}
}

