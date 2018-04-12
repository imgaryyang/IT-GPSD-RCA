package com.gps.vo;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the PROCESS database table.
 * 
 */
@Entity
@NamedQuery(name="Process.findAll", query="SELECT p FROM Process p")
public class Process implements Serializable {
	private static final long serialVersionUID = 1L;
	private int processId;
	private String isEnabled;
	private String name;
	private String shortName;

	public Process() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PROCESS_ID")
	public int getProcessId() {
		return this.processId;
	}

	public void setProcessId(int processId) {
		this.processId = processId;
	}


	@Column(name="IS_ENABLED")
	public String getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}


	@Column(name="\"NAME\"")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Column(name="SHORT_NAME")
	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

}