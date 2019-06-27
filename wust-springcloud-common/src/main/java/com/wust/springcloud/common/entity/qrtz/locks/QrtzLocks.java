package com.wust.springcloud.common.entity.qrtz.locks;

/**
 * table name:  qrtz_locks
 * author name: wust
 * create time: 2019-06-13 09:55:09
 */ 
public class QrtzLocks{

	private String schedName;
	private String lockName;

	public void setSchedName(String schedName){
		this.schedName=schedName;
	}
	public String getSchedName(){
		return schedName;
	}
	public void setLockName(String lockName){
		this.lockName=lockName;
	}
	public String getLockName(){
		return lockName;
	}
}

