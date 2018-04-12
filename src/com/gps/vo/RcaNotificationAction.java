package com.gps.vo;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the RCA_NOTIFICATION_ACTION database table.
 * 
 */
@Entity
@Table(name="RCA_NOTIFICATION_ACTION")
@NamedQuery(name="RcaNotificationAction.findAll", query="SELECT r FROM RcaNotificationAction r")
public class RcaNotificationAction implements Serializable {
	private static final long serialVersionUID = 1L;
	private int rcaNotificationId;
	private int daysAfterAssignment;
	private int daysAfterCreation;
	private int duration;
	private String isEnabled;
	private String notificationName;

	public RcaNotificationAction() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RCA_NOTIFICATION_ID")
	public int getRcaNotificationId() {
		return this.rcaNotificationId;
	}

	public void setRcaNotificationId(int rcaNotificationId) {
		this.rcaNotificationId = rcaNotificationId;
	}


	@Column(name="DAYS_AFTER_ASSIGNMENT")
	public int getDaysAfterAssignment() {
		return this.daysAfterAssignment;
	}

	public void setDaysAfterAssignment(int daysAfterAssignment) {
		this.daysAfterAssignment = daysAfterAssignment;
	}


	@Column(name="DAYS_AFTER_CREATION")
	public int getDaysAfterCreation() {
		return this.daysAfterCreation;
	}

	public void setDaysAfterCreation(int daysAfterCreation) {
		this.daysAfterCreation = daysAfterCreation;
	}


	public int getDuration() {
		return this.duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}


	@Column(name="IS_ENABLED")
	public String getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}


	@Column(name="NOTIFICATION_NAME")
	public String getNotificationName() {
		return this.notificationName;
	}

	public void setNotificationName(String notificationName) {
		this.notificationName = notificationName;
	}

}