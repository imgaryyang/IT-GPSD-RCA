/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: ContractRequestDao.java
 *    CREATOR:Waqar Malik
 *    DEPT: GBS PAK
 *    DATE: 25/09/2013
 *
 *-PURPOSE-----------------------------------------------------------------
 * 
 *--------------------------------------------------------------------------
 *
 *
 *-CHANGE LOG--------------------------------------------------------------
 * 25/09/2013Waqar Malik Initial coding.
 *==========================================================================
 * </pre> */
package com.gps.dao;

import com.gps.exceptions.GPSException;
import com.gps.vo.ContractRequest;

import java.util.List;

/**
 * This class provides interface for ContractRequestDao.
 *  
 * @authorWaqar Malik
 */
public interface ContractRequestDao {
	
	/**
	 * 
	 * @param requestId
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	void dropContractRequest(ContractRequest contractRequest) throws GPSException;
	
	/**
	 * 
	 * @param contractRequest
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	 void addContractRequest(ContractRequest contractRequest) throws GPSException;
	 
	 /**
	  * 
	  * @param contractRequest
	  * @throws GPSException
	  * @authorWaqar Malik
	  */
	 void updateContractRequest(ContractRequest contractRequest) throws GPSException;
	 
	 /**
	  * 
	  * @param requestId
	  * @return
	  * @throws GPSException
	  * @authorWaqar Malik
	  */
	 ContractRequest getContractRequestById(Long requestId) throws GPSException;
	 
	 /**
	  * 
	  * @param requestedBy
	  * @return
	  * @throws GPSException
	  * @authorWaqar Malik
	  */
	 List<ContractRequest> listAwaitingRequests(String requestedBy) throws GPSException;
	 
	 /**
	  * 
	  * @return
	  * @throws GPSException
	  * @authorWaqar Malik
	  */
	List<ContractRequest> listRequiringAction() throws GPSException;
	
	/**
	 * 
	 * @param sQuery
	 * @param queryParameters
	 * @return
	 * @authorWaqar Malik
	 */
	List<ContractRequest> getContractRequestsByParam(String sQuery, List<Object> queryParameters);
	
	/**
	 * 
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	List<String> listSubmitters() throws GPSException;
	
	/**
	 * 
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	List<String> listApprovers() throws GPSException;

}
