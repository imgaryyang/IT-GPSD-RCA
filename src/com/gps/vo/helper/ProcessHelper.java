package com.gps.vo.helper;

import java.io.Serializable;


public class ProcessHelper implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4380355076527817286L;
	// old SLAs
	private Integer contractProcessId;
	private Integer contractId;
	private Integer processId;
	private String name;
	private String rag;
	
	
	/**
	 * @return the contractProcessId
	 */
	public Integer getContractProcessId() {
		return contractProcessId;
	}

	/**
	 * @param contractProcessId the contractProcessId to set
	 */
	public void setContractProcessId(Integer contractProcessId) {
		this.contractProcessId = contractProcessId;
	}

	/**
	 * @return the contractId
	 */
	public Integer getContractId() {
		return contractId;
	}

	/**
	 * @param contractId the contractId to set
	 */
	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	/**
	 * @return the processId
	 */
	public Integer getProcessId() {
		return processId;
	}

	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}



	public String getRag() {
		return rag;
	}


	public void setRag(String rag) {
		this.rag = rag;
	}


	
	

}
