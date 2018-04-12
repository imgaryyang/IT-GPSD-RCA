/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: SupportService.java
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

package com.gps.service;

import com.gps.exceptions.GPSException;
import com.gps.vo.ContractRequest;
import com.gps.vo.helper.ArchiveSearchFilter;

import java.util.List;

/**
 * This class provides interface for support service.
 *  
 * @authorWaqar Malik
 */
public interface SupportService {
	
	/**
	 * 
	 * @param requestId
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	void cancelContractRequest(Long requestId) throws GPSException;

	/**
	 * 
	 * @param requestId
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	void markArchive(Long requestId) throws GPSException;
	
	/**
	 * 
	 * @param contractRequest
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	void processContractRequest(ContractRequest contractRequest) throws GPSException;

	/**
	 * 
	 * @param contractRequest
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
//	void approveContractRequest(ContractRequest contractRequest) throws GPSException;
	
	/**
	 * 
	 * @param requestId
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	ContractRequest getContractRequest(Long requestId) throws GPSException;
	
	/**
	 * 
	 * @param requestId
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
	 * @param searchFilter
	 * @param userEmail
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	List<ContractRequest> listArchive(ArchiveSearchFilter searchFilter, String userEmail) throws GPSException;
	
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
