/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: ProcessService.java
 *    CREATOR:Waqar Malik
 *    DEPT: GBS PAK
 *    DATE: 17/04/2013
 *
 *-PURPOSE-----------------------------------------------------------------
 * 
 *--------------------------------------------------------------------------
 *
 *
 *-CHANGE LOG--------------------------------------------------------------
 * 17/04/2013Waqar Malik Initial coding.
 *==========================================================================
 * </pre> */

package com.gps.service;

import com.gps.exceptions.GPSException;

import java.util.List;

/**
 * This class provides interface for Process Service.
 *  
 * @authorWaqar Malik
 */
public interface ProcessService {
	/**
	 * This method returns ContractProcess for provided contractId & processId.
	 * 
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public com.gps.vo.Process getProcess(Integer processId) throws GPSException;

	
	/**
	 * this method returns list of ContractProcess any given contractId.
	 * 
	 * @param contractId
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public List<com.gps.vo.Process> getProcessList(Integer contractId) throws GPSException;
}
