/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: SLADao.java
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

package com.gps.dao;

import com.gps.exceptions.GPSException;
import com.gps.vo.ContractProcess;

import java.util.List;


/**
 * This class provides interface for ContractProcess entity persistence.
 *  
 * @authorWaqar Malik
 */
public interface ContractProcessDao {

	/**
	 * this method adds single ContractProcess entity in db.
	 * 
	 * @param contractProcess
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public void addContractProcess(ContractProcess contractProcess) throws GPSException;
	
	/**
	 * 
	 * @param contractProcess
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public boolean removeContractProcess(ContractProcess contractProcess) throws GPSException;
	
	/**
	 * this method adds list of ContractProcess entities in db.
	 * 
	 * @param contractProcessList
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public void addContractProcess(List<ContractProcess> contractProcessList) throws GPSException;
	
	/**
	 * 
	 * @param contractProcessId
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public Long getSlaCount(Integer contractProcessId) throws GPSException;
	
	/**
	 * 
	 * @param contractProcessId
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public Long getSloCount(Integer contractProcessId) throws GPSException;
	/**
	 * This method gets ContractProcess from db for provided Id.
	 * 
	 * @param contractProcessId
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public ContractProcess getContractProcess(Integer contractProcessId) throws GPSException;
	
	/**
	 * This method gets ContractProcess from db for provided contractId & processId.
	 * 
	 * @param contractId
	 * @param processId
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public ContractProcess getContractProcess(Integer contractId, Integer processId) throws GPSException;
	
	/**
	 * this method returns all the ContractProcess if any, for given contract
	 * 
	 * @param contractId
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public List<ContractProcess> getContractProcessList(Integer contractId) throws GPSException;
	
}
