package com.wust.springcloud.common.entity.qrtz.simpletriggers;

/**
 * table name:  qrtz_simple_triggers
 * author name: wust
 * create time: 2019-06-13 09:55:09
 */ 
public class QrtzSimpleTriggers{

	private String schedName;
	private String triggerName;
	private String triggerGroup;
	private Long repeatCount;
	private Long repeatInterval;
	private Long timesTriggered;

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
	public void setRepeatCount(Long repeatCount){
		this.repeatCount=repeatCount;
	}
	public Long getRepeatCount(){
		return repeatCount;
	}
	public void setRepeatInterval(Long repeatInterval){
		this.repeatInterval=repeatInterval;
	}
	public Long getRepeatInterval(){
		return repeatInterval;
	}
	public void setTimesTriggered(Long timesTriggered){
		this.timesTriggered=timesTriggered;
	}
	public Long getTimesTriggered(){
		return timesTriggered;
	}
}

