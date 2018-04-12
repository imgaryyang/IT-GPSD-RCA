package com.gps.vo;

import com.gps.vo.helper.ProcessHelper;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the CONTRACT_PROCESS database table.
 * 
 */
@Entity
@Table(name = "CONTRACT_PROCESS")
@Cacheable(false)
public class ContractProcess implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// Start @Transient Variables
	private ProcessHelper processHelper;
	//End @Transient Variables
	
	private Integer contractProcessId;
	private Contract contract;
	private Process process;

	public ContractProcess() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CONTRACT_PROCESS_ID", unique = true, nullable = false)
	public Integer getContractProcessId() {
		return this.contractProcessId;
	}

	public void setContractProcessId(Integer contractProcessId) {
		this.contractProcessId = contractProcessId;
	}

	// bi-directional many-to-one association to Contract
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTRACT_ID")
	public Contract getContract() {
		return this.contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	// bi-directional many-to-one association to Process
	//@ManyToOne(fetch = FetchType.EAGER)
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name = "PROCESS_ID")
	public Process getProcess() {
		return this.process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	/**
	 * @return the processHelper
	 */
	@Transient 
	public ProcessHelper getProcessHelper() {
		return processHelper;
	}

	/**
	 * @param processHelper the processHelper to set
	 */
	@Transient 
	public void setProcessHelper(ProcessHelper processHelper) {
		this.processHelper = processHelper;
	}
}