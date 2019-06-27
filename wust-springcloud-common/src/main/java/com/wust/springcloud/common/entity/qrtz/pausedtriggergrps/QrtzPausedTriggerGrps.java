package com.wust.springcloud.common.entity.qrtz.pausedtriggergrps;

/**
 * table name:  qrtz_paused_trigger_grps
 * author name: wust
 * create time: 2019-06-13 09:55:09
 */ 
public class QrtzPausedTriggerGrps{

	private String schedName;
	private String triggerGroup;

	public void setSchedName(String schedName){
		this.schedName=schedName;
	}
	public String getSchedName(){
		return schedName;
	}
	public void setTriggerGroup(String triggerGroup){
		this.triggerGroup=triggerGroup;
	}
	public String getTriggerGroup(){
		return triggerGroup;
	}
}

