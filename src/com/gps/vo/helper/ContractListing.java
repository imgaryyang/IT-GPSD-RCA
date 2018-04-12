/**
 * 
 */
package com.gps.vo.helper;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Aizaz
 *
 */
public class ContractListing implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1751606120955335037L;
	
	private Integer contractId;
	private String title;
	private String email;
	private Timestamp updatedOn;
	private String countryName;
	private String sectorName;
	private String state;
	private String geo;
	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}
	public Integer getContractId() {
		return contractId;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}
	public String getSectorName() {
		return sectorName;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getState() {
		return state;
	}
	public void setGeo(String geo) {
		this.geo = geo;
	}
	public String getGeo() {
		return geo;
	}
	/**
	 * @param updatedOn the updatedOn to set
	 */
	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}
	/**
	 * @return the updatedOn
	 */
	public Timestamp getUpdatedOn() {
		return updatedOn;
	}
}
