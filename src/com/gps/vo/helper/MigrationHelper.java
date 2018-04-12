package com.gps.vo.helper;

import java.sql.Timestamp;

public class MigrationHelper  {

	private String startTimeString;
	private String endTimeString;
	private String startHourString;
	private String endHourString;
	private String startMinString;
	private String endMinString;
	private String LhinNumber;
	
	public String getLhinNumber() {
		return LhinNumber;
	}
	public void setLhinNumber(String lhinNumber) {
		LhinNumber = lhinNumber;
	}
	public String getStartHourString() {
		return startHourString;
	}
	public void setStartHourString(String startHourString) {
		this.startHourString = startHourString;
	}
	public String getEndHourString() {
		return endHourString;
	}
	public void setEndHourString(String endHourString) {
		this.endHourString = endHourString;
	}
	public String getStartMinString() {
		return startMinString;
	}
	public void setStartMinString(String startMinString) {
		this.startMinString = startMinString;
	}
	public String getEndMinString() {
		return endMinString;
	}
	public void setEndMinString(String endMinString) {
		this.endMinString = endMinString;
	}
	private Timestamp startTime;
	private Timestamp endTime;
	public String getStartTimeString() {
		return startTimeString;
	}
	public void setStartTimeString(String startTimeString) {
		this.startTimeString = startTimeString;
	}
	public String getEndTimeString() {
		return endTimeString;
	}
	public void setEndTimeString(String endTimeString) {
		this.endTimeString = endTimeString;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

}
