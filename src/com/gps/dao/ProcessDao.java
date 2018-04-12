/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: ProcessDao.java
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

package com.gps.dao;

import com.gps.exceptions.GPSException;

import java.util.List;


/**
 * This class provides interface for ContractProcess entity persistence.
 *  
 * @authorWaqar Malik
 */
public interface ProcessDao {


	/**
	 * This method gets Process from db for provided Id.
	 * 
	 * @param contractProcessId
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public com.gps.vo.Process getProcess(Integer processId) throws GPSException;
	
	/**
	 * this method returns all the Processes if any
	 * 
	 * @param contractId
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public List<com.gps.vo.Process> getProcessList() throws GPSException;
	
}
