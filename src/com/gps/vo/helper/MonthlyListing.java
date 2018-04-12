/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: MonthlyListing.java
 *    CREATOR: Aizaz
 *    DEPT: GBS PAK
 *    DATE: 04/04/2013
 *
 *-PURPOSE-----------------------------------------------------------------
 * 
 *--------------------------------------------------------------------------
 *
 *
 *-CHANGE LOG--------------------------------------------------------------
 * 04/04/2013 Aizaz Initial coding.         
 *==========================================================================
 * </pre> */
package com.gps.vo.helper;

/**
 * @author Aizaz
 *
 */
public class MonthlyListing {
	
	private Integer monthlyId;
	private Integer contractId;
	private String contractTitle;
	private String country;
	private String omStatus;
	private String csStatus;
	private String fpStatus;
	private String bcStatus;
	private String createdOn;
	private String state;
	private String overallStatus;

	public void setCountry(String country) {
		this.country = country;
	}
	public String getCountry() {
		return country;
	}
	public void setOmStatus(String omStatus) {
		this.omStatus = omStatus;
	}
	public String getOmStatus() {
		return omStatus;
	}
	public void setCsStatus(String csStatus) {
		this.csStatus = csStatus;
	}
	public String getCsStatus() {
		return csStatus;
	}
	public void setFpStatus(String fpStatus) {
		this.fpStatus = fpStatus;
	}
	public String getFpStatus() {
		return fpStatus;
	}
	public void setBcStatus(String bcStatus) {
		this.bcStatus = bcStatus;
	}
	public String getBcStatus() {
		return bcStatus;
	}
	public void setOverallStatus(String overallStatus) {
		this.overallStatus = overallStatus;
	}
	public String getOverallStatus() {
		return overallStatus;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public Integer getContractId() {
		return contractId;
	}

	public void setMonthlyId(Integer monthlyId) {
		this.monthlyId = monthlyId;
	}

	public Integer getMonthlyId() {
		return monthlyId;
	}
	/**
	 * @return the contractTitle
	 */
	public String getContractTitle() {
		return contractTitle;
	}
	/**
	 * @param contractTitle the contractTitle to set
	 */
	public void setContractTitle(String contractTitle) {
		this.contractTitle = contractTitle;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	/**
	 * @return the createdOn
	 */
	public String getCreatedOn() {
		return createdOn;
	}
}
