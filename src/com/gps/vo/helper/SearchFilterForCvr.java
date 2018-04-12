package com.gps.vo.helper;

import java.sql.Date;
import java.util.List;


public class SearchFilterForCvr {
	private String contract = null;
	private String customer = null;
	private String stage = null;
	private String access = null;
	private String status;
	private String lob = null;
	private String tower = null;
	private String sector = null;
	private String geo = null;
	private String iot = null;
	private String imt = null;
	private String country = null;
	private Integer page;
	private Integer month;
	private Integer year;
	private Date week;
	private String pagingAction;
	private List<Date> currYearList;
	
	public String toCsv(){
		StringBuilder builder = new StringBuilder("Filter: ");
		if(contract != null && !contract.isEmpty()){
			builder.append("contract=").append(contract);
		}
		if(customer != null && !customer.isEmpty()){
			builder.append(", customer=").append(customer);
		}
		if(stage != null && !stage.isEmpty()){
			builder.append(", stage="+stage);
		}
		if(tower != null && !tower.isEmpty()){
			builder.append(", tower="+tower);
		}
		if(lob != null && !lob.isEmpty()){
			builder.append(", lob="+lob);
		}
		if(sector != null && !sector.isEmpty()){
			builder.append(", sector="+sector);
		}
		return builder.toString();
	}
	
	
	public static Integer PAGE_SIZE = 25;
	
	private String formDate;


	public SearchFilterForCvr(){
		setPage(1);
	}
	
	public String getContract() {
		return contract;
	}
	public void setContract(String contract) {
		this.contract = contract;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
	public String getLob() {
		return lob;
	}
	public void setLob(String lob) {
		this.lob = lob;
	}
	public String getTower() {
		return tower;
	}
	public void setTower(String tower) {
		this.tower = tower;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public String getGeo() {
		return geo;
	}
	public void setGeo(String geo) {
		this.geo = geo;
	}
	public String getIot() {
		return iot;
	}
	public void setIot(String iot) {
		this.iot = iot;
	}
	public String getImt() {
		return imt;
	}
	public void setImt(String imt) {
		this.imt = imt;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
	
	public String getFormDate() {
		return formDate;
	}

	public void setFormDate(String formDate) {
		this.formDate = formDate;
	}
	
	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Date getWeek() {
		return week;
	}

	public void setWeek(Date week) {
		this.week = week;
	}

	public List<Date> getCurrYearList() {
		return currYearList;
	}

	public void setCurrYearList(List<Date> currYearList) {
		this.currYearList = currYearList;
	}

	public String getPagingAction() {
		return pagingAction;
	}

	public void setPagingAction(String pagingAction) {
		this.pagingAction = pagingAction;
	}
}