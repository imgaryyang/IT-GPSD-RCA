package com.gps.vo.helper;

import java.io.Serializable;
import java.util.List;

public class MonthlyForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651229625501781751L;
	
	private String month;
	private String year;
	private List<String> contractIdList;
	private String formAction;
	private String isFileDelete;
	
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public List<String> getContractIdList() {
		return contractIdList;
	}
	public void setContractIdList(List<String> contractIdList) {
		this.contractIdList = contractIdList;
	}
	public void setFormAction(String formAction) {
		this.formAction = formAction;
	}
	public String getFormAction() {
		return formAction;
	}
	/**
	 * @param isFileDelete the isFileDelete to set
	 */
	public void setIsFileDelete(String isFileDelete) {
		this.isFileDelete = isFileDelete;
	}
	/**
	 * @return the isFileDelete
	 */
	public String getIsFileDelete() {
		return isFileDelete;
	}
	
	

}
