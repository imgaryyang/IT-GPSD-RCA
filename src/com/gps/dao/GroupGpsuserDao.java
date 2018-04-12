/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: GroupGpsuserDao.java
 *    CREATOR:Waqar Malik
 *    DEPT: GBS PAK
 *    DATE: 16/09/2013
 *
 *-PURPOSE-----------------------------------------------------------------
 * 
 *--------------------------------------------------------------------------
 *
 *
 *-CHANGE LOG--------------------------------------------------------------
 * 16/09/2013Waqar Malik Initial coding.
 *==========================================================================
 * </pre> */

package com.gps.dao;

import com.gps.exceptions.GPSException;
import com.gps.vo.GroupGpsuser;

import java.util.List;


/**
 * This class provides interface for GroupGpsUserDao.
 *  
 * @authorWaqar Malik
 */
public interface GroupGpsuserDao {

	/**
	 * 
	 * @param GroupGpsuser
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public void addGroupGpsuser(GroupGpsuser GroupGpsuser) throws GPSException;
	
	/**
	 * 
	 * @param listGroupGpsuser
	 * @throws GPSException
	 */
	public void addGroupGpsuser(List<GroupGpsuser> listGroupGpsuser) throws GPSException;

}
