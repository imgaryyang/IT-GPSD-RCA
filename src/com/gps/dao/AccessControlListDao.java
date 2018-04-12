/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: AccessControlListDao.java
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
import com.gps.vo.AccessControlList;

import java.util.List;


/**
 * This class provides interface for AccessControlListDao.
 * 
 * @author Waqar Malik
 */
public interface AccessControlListDao {
	
	

	/**
	 * 
	 * @param acl
	 * @throws GPSException
	 * @author Waqar Malik
	 */
	public void addAccessControlList(AccessControlList acl) throws GPSException;
	
	/**
	 * 
	 * @param acl
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public void updateAccessControlList(AccessControlList acl) throws GPSException;
	
	/**
	 * 
	 * @param aclId
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	AccessControlList getACLById(Integer aclId) throws GPSException;
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public List<AccessControlList> getACLByUser(Integer userId) throws GPSException;
	
	/**
	 * 
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public List<AccessControlList> listSingleContractAcls() throws GPSException;
	
	/**
	 * 
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public List<AccessControlList> listNonContractAcls() throws GPSException;
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	List<AccessControlList> getAclsByUserId(Integer userId) throws GPSException;
	
	
	/**
	 * 
	 * @param aclId
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public void deleteAccessControlList(Integer aclId) throws GPSException;

	/**
	 * 
	 * @param acl
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	void deleteAccessControlList(AccessControlList acl) throws GPSException;
	
	
}
