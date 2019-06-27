package com.wust.springcloud.common.entity.qrtz.calendars;

/**
 * table name:  qrtz_calendars
 * author name: wust
 * create time: 2019-06-13 09:55:09
 */ 
public class QrtzCalendars{

	private String schedName;
	private String calendarName;
	private byte[] calendar;

	public void setSchedName(String schedName){
		this.schedName=schedName;
	}
	public String getSchedName(){
		return schedName;
	}
	public void setCalendarName(String calendarName){
		this.calendarName=calendarName;
	}
	public String getCalendarName(){
		return calendarName;
	}
	public void setCalendar(byte[] calendar){
		this.calendar=calendar;
	}
	public byte[] getCalendar(){
		return calendar;
	}
}

