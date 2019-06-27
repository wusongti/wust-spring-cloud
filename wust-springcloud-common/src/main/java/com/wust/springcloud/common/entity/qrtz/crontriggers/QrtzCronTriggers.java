package com.wust.springcloud.common.entity.qrtz.crontriggers;

/**
 * table name:  qrtz_cron_triggers
 * author name: wust
 * create time: 2019-06-13 09:55:09
 */ 
public class QrtzCronTriggers implements java.io.Serializable{

	private static final long serialVersionUID = 2806390556227814191L;
	private String schedName;
	private String triggerName;
	private String triggerGroup;
	private String cronExpression;
	private String timeZoneId;

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
	public void setCronExpression(String cronExpression){
		this.cronExpression=cronExpression;
	}
	public String getCronExpression(){
		return cronExpression;
	}
	public void setTimeZoneId(String timeZoneId){
		this.timeZoneId=timeZoneId;
	}
	public String getTimeZoneId(){
		return timeZoneId;
	}
}

