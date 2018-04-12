/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: GpsUserDao.java
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

package com.gps.dao;

import com.gps.exceptions.GPSException;
import com.gps.vo.GpsUser;

import java.util.List;


/**
 * This class provides interface for GpsUserDao.
 *  

 */
public interface GpsUserDao {

	/**
	 * 
	 * @param GpsUser
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public void addGpsUser(GpsUser GpsUser) throws GPSException;
	
	/**
	 * 
	 * @param aclId
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public void deleteGpsUser(Integer userId) throws GPSException;

	/**
	 * 
	 * @param GpsUser
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	void deleteGpsUser(GpsUser GpsUser) throws GPSException;

	/**
	 * This method is used to get GpsUser by email
	 * 
	 * @param email
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	GpsUser getGpsUserByEmail(String email) throws GPSException;
	
	/**
	 * 
	 * @param email
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	 GpsUser getGpsUserByEmailNoFetch(String email) throws GPSException;
	
	/**
	 * 
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	List<GpsUser> listEmails() throws GPSException;
	
	/**
	 * 
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	List<GpsUser> listSingleContractAcls() throws GPSException;

    List<GpsUser> listUsersHavingRoles() throws GPSException;

	GpsUser getUserById(Integer userId)throws GPSException;
	
}
