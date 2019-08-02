package com.wust.springcloud.common.entity.sys.datasource;

import com.wust.springcloud.common.entity.BaseEntity;

import javax.persistence.Id;


/**
 * table name:  sys_data_source
 * author name: wust
 * create time: 2019-06-17 15:42:21
 */ 
public class SysDataSource extends BaseEntity{
	private static final long serialVersionUID = 5502135893804008206L;

	private String companyId;
	private String jdbcUrl;
	private String jdbcUsername;
	private String jdbcPassword;
	private String jdbcDriver;
	private String description;

	public void setCompanyId(String companyId){
		this.companyId=companyId;
	}
	public String getCompanyId(){
		return companyId;
	}
	public void setJdbcUrl(String jdbcUrl){
		this.jdbcUrl=jdbcUrl;
	}
	public String getJdbcUrl(){
		return jdbcUrl;
	}
	public void setJdbcUsername(String jdbcUsername){
		this.jdbcUsername=jdbcUsername;
	}
	public String getJdbcUsername(){
		return jdbcUsername;
	}
	public void setJdbcPassword(String jdbcPassword){
		this.jdbcPassword=jdbcPassword;
	}
	public String getJdbcPassword(){
		return jdbcPassword;
	}
	public void setJdbcDriver(String jdbcDriver){
		this.jdbcDriver=jdbcDriver;
	}
	public String getJdbcDriver(){
		return jdbcDriver;
	}
	public void setDescription(String description){
		this.description=description;
	}
	public String getDescription(){
		return description;
	}
}

