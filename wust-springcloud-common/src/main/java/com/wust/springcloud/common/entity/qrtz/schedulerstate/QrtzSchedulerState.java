package com.wust.springcloud.common.entity.qrtz.schedulerstate;

/**
 * table name:  qrtz_scheduler_state
 * author name: wust
 * create time: 2019-06-13 09:55:09
 */ 
public class QrtzSchedulerState{

	private String schedName;
	private String instanceName;
	private Long lastCheckinTime;
	private Long checkinInterval;

	public void setSchedName(String schedName){
		this.schedName=schedName;
	}
	public String getSchedName(){
		return schedName;
	}
	public void setInstanceName(String instanceName){
		this.instanceName=instanceName;
	}
	public String getInstanceName(){
		return instanceName;
	}
	public void setLastCheckinTime(Long lastCheckinTime){
		this.lastCheckinTime=lastCheckinTime;
	}
	public Long getLastCheckinTime(){
		return lastCheckinTime;
	}
	public void setCheckinInterval(Long checkinInterval){
		this.checkinInterval=checkinInterval;
	}
	public Long getCheckinInterval(){
		return checkinInterval;
	}
}

