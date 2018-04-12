/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: ContractProcessService.java
 *    CREATOR:Waqar Malik
 *    DEPT: GBS PAK
 *    DATE: 06/04/2013
 *
 *-PURPOSE-----------------------------------------------------------------
 * 
 *--------------------------------------------------------------------------
 *
 *
 *-CHANGE LOG--------------------------------------------------------------
 * 06/04/2013Waqar Malik Initial coding.
 *==========================================================================
 * </pre> */

package com.gps.service;

import com.gps.exceptions.GPSException;
import com.gps.vo.ContractProcess;

/**
 * This class provides interface for ContractProcess Service.
 *  
 * @authorWaqar Malik
 */
public interface ContractProcessService {
	
	/**
	 * This method saves contractProcess.
	 * 
	 * @param contractProcess
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public boolean addContractProcess(ContractProcess contractProcess) throws GPSException;
	
	/**
	 * 
	 * @param contractProcessId
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public Boolean isAnyDependents(Integer contractProcessId) throws GPSException;
	
	/**
	 * This method returns ContractProcess for provided contractId & processId.
	 * 
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public ContractProcess getContractProcess(Integer contractId, Integer processId) throws GPSException;
	
	/**
	 * This method returns ContractProcess for provided contractProcessId.
	 * 
	 * @param contractProcessId
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public ContractProcess getContractProcess(Integer contractProcessId) throws GPSException;
	
}
