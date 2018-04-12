/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: SessionAclDao.java
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

package com.gps.dao;

import com.gps.exceptions.GPSException;
import com.gps.vo.SessionAcl;

import java.util.List;


/**
 * This class provides interface for SessionAclDao.
 *  
 * @authorWaqar Malik
 */
public interface SessionAclDao {

	/**
	 * 
	 * @param sessionAcl
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public void addSessionAcl(SessionAcl sessionAcl) throws GPSException;
	
	/**
	 * 
	 * @param sessionAcls
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public void addSessionAcl(List<SessionAcl> sessionAcls) throws GPSException;
	
	/**
	 * 
	 * @param sessionAcls
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public void batchInsertSessionAcl(List<SessionAcl> sessionAcls) throws GPSException;
	
	/**
	 * 
	 * @param sessionAcl
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public void updateSessionAcl(SessionAcl sessionAcl) throws GPSException;
	
	/**
	 * 
	 * @param sessionId
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public void removeSessionAcl(SessionAcl sessionAcl) throws GPSException;
	
	/**
	 * 
	 * @param sessionId
	 * @throws GPSException
	 * @authorWaqar Malik
	 * @return 
	 */
	public Integer removeSessionAclBySession(String sessionId) throws GPSException;
	
	/**
	 * 
	 * @param sessionAcls
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public void removeSessionAcl(List<SessionAcl> sessionAcls) throws GPSException;
	
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
