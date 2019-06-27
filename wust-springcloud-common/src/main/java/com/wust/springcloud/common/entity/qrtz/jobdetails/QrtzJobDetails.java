package com.wust.springcloud.common.entity.qrtz.jobdetails;

/**
 * table name:  qrtz_job_details
 * author name: wust
 * create time: 2019-06-13 09:55:09
 */ 
public class QrtzJobDetails implements java.io.Serializable{
	private static final long serialVersionUID = 39813355043696970L;

	private String schedName;
	private String jobName;
	private String jobGroup;
	private String description;
	private String jobClassName;
	private String isDurable;
	private String isNonconcurrent;
	private String isUpdateData;
	private String requestsRecovery;
	private byte[] jobData;

	public void setSchedName(String schedName){
		this.schedName=schedName;
	}
	public String getSchedName(){
		return schedName;
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
	public void setJobClassName(String jobClassName){
		this.jobClassName=jobClassName;
	}
	public String getJobClassName(){
		return jobClassName;
	}
	public void setIsDurable(String isDurable){
		this.isDurable=isDurable;
	}
	public String getIsDurable(){
		return isDurable;
	}
	public void setIsNonconcurrent(String isNonconcurrent){
		this.isNonconcurrent=isNonconcurrent;
	}
	public String getIsNonconcurrent(){
		return isNonconcurrent;
	}
	public void setIsUpdateData(String isUpdateData){
		this.isUpdateData=isUpdateData;
	}
	public String getIsUpdateData(){
		return isUpdateData;
	}
	public void setRequestsRecovery(String requestsRecovery){
		this.requestsRecovery=requestsRecovery;
	}
	public String getRequestsRecovery(){
		return requestsRecovery;
	}
	public void setJobData(byte[] jobData){
		this.jobData=jobData;
	}
	public byte[] getJobData(){
		return jobData;
	}
}

