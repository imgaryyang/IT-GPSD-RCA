/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: SessionACLService.java
 *    CREATOR:Waqar Malik
 *    DEPT: GBS PAK
 *    DATE: 27/08/2013
 *
 *-PURPOSE-----------------------------------------------------------------
 * 
 *--------------------------------------------------------------------------
 *
 *
 *-CHANGE LOG--------------------------------------------------------------
 * 27/08/2013Waqar Malik Initial coding.
 *==========================================================================
 * </pre> */

package com.gps.service;

import com.gps.exceptions.GPSException;
import com.gps.vo.GpsUser;
import com.gps.vo.SessionAcl;

import java.util.List;

/**
 * This class provides interface for monthly forms service.
 *  
 * @authorWaqar Malik
 */
public interface SessionACLService {
	/**
	 * This method persists list of sessionAcls to db.
	 * 
	 * @param sessionAcls
	 * @throws GPSException
	 */
	public void storeSessionAcl(List<SessionAcl> sessionAcls, String sessionId, GpsUser user) throws GPSException;
	
	/**
	 * 
	 * @param sessionAcls
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public void dropSessionAcl(List<SessionAcl> sessionAcls) throws GPSException;
	
	/**
	 * 
	 * @param sessionId
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public void dropSessionAcl(String sessionId) throws GPSException;

	/**
	 * 
	 * @param sessionId
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public List<SessionAcl> getSessionAcl(String sessionId) throws GPSException;
	
	/**
	 * 
	 * @param sessionId
	 * @param contractId
	 * @param formType
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public SessionAcl getSessionAcl(String sessionId, Integer contractId, String formType) throws GPSException;

}
