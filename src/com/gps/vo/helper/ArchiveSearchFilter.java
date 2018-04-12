package com.gps.vo.helper;


public class ArchiveSearchFilter {
	
	private String form;
	private String state;
	private String submitter;
	private String approver;
	private Pagination pagination;
	private String pagingAction;
	private Integer month;
	private Integer year;
	
	
	public ArchiveSearchFilter(){
		pagination = new Pagination();
		pagination.setPageNumber(1);
		setPagingAction(Constant.NEW_SEARCH);
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


	/**
	 * @return the form
	 */
	public String getForm() {
		return form;
	}


	/**
	 * @param form the form to set
	 */
	public void setForm(String form) {
		this.form = form;
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
	 * @return the submitter
	 */
	public String getSubmitter() {
		return submitter;
	}


	/**
	 * @param submitter the submitter to set
	 */
	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}


	/**
	 * @return the approver
	 */
	public String getApprover() {
		return approver;
	}


	/**
	 * @param approver the approver to set
	 */
	public void setApprover(String approver) {
		this.approver = approver;
	}

	
}
