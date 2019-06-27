package com.wust.springcloud.common.entity.qrtz.simproptriggers;

/**
 * table name:  qrtz_simprop_triggers
 * author name: wust
 * create time: 2019-06-13 09:55:09
 */ 
public class QrtzSimpropTriggers{

	private String schedName;
	private String triggerName;
	private String triggerGroup;
	private String strProp1;
	private String strProp2;
	private String strProp3;
	private Integer intProp1;
	private Integer intProp2;
	private Long longProp1;
	private Long longProp2;
	private Double decProp1;
	private Double decProp2;
	private String boolProp1;
	private String boolProp2;

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
	public void setStrProp1(String strProp1){
		this.strProp1=strProp1;
	}
	public String getStrProp1(){
		return strProp1;
	}
	public void setStrProp2(String strProp2){
		this.strProp2=strProp2;
	}
	public String getStrProp2(){
		return strProp2;
	}
	public void setStrProp3(String strProp3){
		this.strProp3=strProp3;
	}
	public String getStrProp3(){
		return strProp3;
	}
	public void setIntProp1(Integer intProp1){
		this.intProp1=intProp1;
	}
	public Integer getIntProp1(){
		return intProp1;
	}
	public void setIntProp2(Integer intProp2){
		this.intProp2=intProp2;
	}
	public Integer getIntProp2(){
		return intProp2;
	}
	public void setLongProp1(Long longProp1){
		this.longProp1=longProp1;
	}
	public Long getLongProp1(){
		return longProp1;
	}
	public void setLongProp2(Long longProp2){
		this.longProp2=longProp2;
	}
	public Long getLongProp2(){
		return longProp2;
	}
	public void setDecProp1(Double decProp1){
		this.decProp1=decProp1;
	}
	public Double getDecProp1(){
		return decProp1;
	}
	public void setDecProp2(Double decProp2){
		this.decProp2=decProp2;
	}
	public Double getDecProp2(){
		return decProp2;
	}
	public void setBoolProp1(String boolProp1){
		this.boolProp1=boolProp1;
	}
	public String getBoolProp1(){
		return boolProp1;
	}
	public void setBoolProp2(String boolProp2){
		this.boolProp2=boolProp2;
	}
	public String getBoolProp2(){
		return boolProp2;
	}
}

