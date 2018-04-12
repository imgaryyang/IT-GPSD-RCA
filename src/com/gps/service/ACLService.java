/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: ACLService.java
 *    CREATOR:Waqar Malik
 *    DEPT: GBS PAK
 *    DATE: 19/07/2013
 *
 *-PURPOSE-----------------------------------------------------------------
 * 
 *--------------------------------------------------------------------------
 *
 *
 *-CHANGE LOG--------------------------------------------------------------
 * 19/07/2013Waqar Malik Initial coding.
 *==========================================================================
 * </pre> */

package com.gps.service;

import com.gps.exceptions.GPSException;
import com.gps.vo.AccessControlList;
import com.gps.vo.GpsUser;
import com.gps.vo.helper.ACLForm;

import java.util.List;

/**
 * This class provides interface for ACL Service.
 *  
 * @authorWaqar Malik
 */
public interface ACLService {
	
	/**
	 * 
	 * @param aclId
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	AccessControlList loadAcl(Integer aclId) throws GPSException;
	
	/**
	 * 
	 * @param aclId
	 * @return
	 * @throws GPSException
	 */
	Boolean deleteAcl(Integer aclId) throws GPSException;

	/**
	 * 
	 * @param aCLForm
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	boolean processACL(ACLForm aCLForm, String command) throws GPSException;
	
	/**
	 * 
	 * @param aclId
	 * @param email
	 * @param contractId
	 * @param formType
	 * @param activeLevel
	 * @param approvedLevel
	 * @return
	 * @throws GPSException
	 */
	public boolean processSingleACL(Integer aclId, String email, Integer contractId, String formType, String activeLevel, String awaitingLevel, String approvedLevel) throws GPSException;
	
	/**
	 * 
	 * @param aclId
	 * @param email
	 * @param formType
	 * @param activeLevel
	 * @param approvedLevel
	 * @return
	 * @throws GPSException
	 */
	public boolean processNonContractACL(Integer aclId, String email, String formType, String customer, String activeLevel, String awaitingLevel, String approvedLevel) throws GPSException;
	
	/**
	 * 
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	List<GpsUser> getSingleContractAcls() throws GPSException;
	
	/**
	 * 
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	List<AccessControlList> getNonContractAcls() throws GPSException;
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	List<AccessControlList> getAclsByUserId(Integer userId) throws GPSException;
	
	
	
	
}
