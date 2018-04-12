package com.gps.vo.helper;


import java.sql.Timestamp;

public class SearchFilter {

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
	private String formType;
	private Pagination pagination;
	private String pagingAction;
	private Integer month;
	private Integer year;
	private String rcaOrActionNumber;
	private String rcaCoordinator;
	private String rcaOwner;
	private String rcaDelegate;
	private String primaryTicket;
	private Integer startOutageDay;
	private Integer endOutageDay;
	private Integer startOutageMonth;
	private Integer endOutageMonth;
	private Integer startOutageYear;
	private Integer endOutageYear;
	private Timestamp startDate;
	private Timestamp endDate;
	private String rcaNumbers;
	private String actionNumbers;
	private String selectedRcaNumbers;
	private String selectedActionNumbers;



	public SearchFilter(){
		pagination = new Pagination();
		pagination.setPageNumber(1);
		setPagingAction(Constant.NEW_SEARCH);
		setFormType("Profile");
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
	public String getRcaOrActionNumber() {
		return rcaOrActionNumber;
	}
	public void setRcaOrActionNumber(String rcaOrActionNumber) {
		this.rcaOrActionNumber = rcaOrActionNumber;
	}
	/**
	 * @return the pagination
	 */
	public Pagination getPagination() {
		return pagination;
	}

	/**
	 * @param pagination the pagination to set
	 */
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	/**
	 * @return the pagingAction
	 */
	public String getPagingAction() {
		return pagingAction;
	}

	/**
	 * @param pagingAction the pagingAction to set
	 */
	public void setPagingAction(String pagingAction) {
		this.pagingAction = pagingAction;
	}



	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getYear() {
		return year;
	}

	/**
	 * @return the month
	 */
	public Integer getMonth() {
		return month;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(Integer month) {
		this.month = month;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public String getRcaCoordinator() {
		return rcaCoordinator;
	}

	public void setRcaCoordinator(String rcaCoordinator) {
		this.rcaCoordinator = rcaCoordinator;
	}


	public String getRcaOwner() {
		return rcaOwner;
	}

	public void setRcaOwner(String rcaOwner) {
		this.rcaOwner = rcaOwner;
	}

	public String getRcaDelegate() {
		return rcaDelegate;
	}

	public void setRcaDelegate(String rcaDelegate) {
		this.rcaDelegate = rcaDelegate;
	}


	public String getPrimaryTicket() {
		return primaryTicket;
	}

	public void setPrimaryTicket(String primaryTicket) {
		this.primaryTicket = primaryTicket;
	}

	public Integer getStartOutageDay() {
		return startOutageDay;
	}

	public void setStartOutageDay(Integer startOutageDay) {
		this.startOutageDay = startOutageDay;
	}

	public Integer getEndOutageDay() {
		return endOutageDay;
	}

	public void setEndOutageDay(Integer endOutageDay) {
		this.endOutageDay = endOutageDay;
	}

	public Integer getStartOutageMonth() {
		return startOutageMonth;
	}

	public void setStartOutageMonth(Integer startOutageMonth) {
		this.startOutageMonth = startOutageMonth;
	}

	public Integer getEndOutageMonth() {
		return endOutageMonth;
	}

	public void setEndOutageMonth(Integer endOutageMonth) {
		this.endOutageMonth = endOutageMonth;
	}

	public Integer getStartOutageYear() {
		return startOutageYear;
	}

	public void setStartOutageYear(Integer startOutageYear) {
		this.startOutageYear = startOutageYear;
	}

	public Integer getEndOutageYear() {
		return endOutageYear;
	}

	public void setEndOutageYear(Integer endOutageYear) {
		this.endOutageYear = endOutageYear;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public String getRcaNumbers() {
		return rcaNumbers;
	}

	public void setRcaNumbers(String rcaNumbers) {
		this.rcaNumbers = rcaNumbers;
	}

	public String getActionNumbers() {
		return actionNumbers;
	}

	public void setActionNumbers(String actionNumbers) {
		this.actionNumbers = actionNumbers;
	}

	public String getSelectedRcaNumbers() {
		return selectedRcaNumbers;
	}

	public void setSelectedRcaNumbers(String selectedRcaNumbers) {
		this.selectedRcaNumbers = selectedRcaNumbers;
	}

	public String getSelectedActionNumbers() {
		return selectedActionNumbers;
	}

	public void setSelectedActionNumbers(String selectedActionNumbers) {
		this.selectedActionNumbers = selectedActionNumbers;
	}
}
