package com.gps.vo.helper;

import java.io.Serializable;
import java.util.List;

public class WeeklyForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651229625501781751L;
	
	private String formDate;
	private List<String> contractIdList;
	
	public List<String> getContractIdList() {
		return contractIdList;
	}
	public void setContractIdList(List<String> contractIdList) {
		this.contractIdList = contractIdList;
	}
	public void setFormDate(String formDate) {
		this.formDate = formDate;
	}
	public String getFormDate() {
		return formDate;
	}
	
	

}
