package com.wust.springcloud.common.entity.qrtz.blobtriggers;

/**
 * table name:  qrtz_blob_triggers
 * author name: wust
 * create time: 2019-06-13 09:55:09
 */ 
public class QrtzBlobTriggers{

	private String schedName;
	private String triggerName;
	private String triggerGroup;
	private byte[] blobData;

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
	public void setBlobData(byte[] blobData){
		this.blobData=blobData;
	}
	public byte[] getBlobData(){
		return blobData;
	}
}

