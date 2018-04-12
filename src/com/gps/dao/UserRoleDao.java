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
import com.gps.vo.UserRole;

import java.util.List;


public interface UserRoleDao {

	public void addUserRole(UserRole userRole) throws GPSException;

    public void updateUserRole(UserRole userRole) throws GPSException;

    public Integer getMaxUserRoleId() throws GPSException;

    public List<UserRole> getUserRolesByUser(Integer userId) throws GPSException;

    public UserRole getUserRoleById(Integer userRoleId) throws GPSException;

    public void deleteUserRole(UserRole userRole) throws GPSException;

    public List<UserRole> getUserRolesByContractIdAndRole(Integer contractId, String role);

    public List<UserRole> getUserRolesByContractIdAndUserId(Integer contractId, Integer userId);

    public UserRole getUserRolesByContractIdAndUserIdAndRole(Integer contractId, Integer userId, String role);

    public List<UserRole> getUserRolesByRoleName(String role);

    public UserRole findGlobalRole(Integer userId, String role);

    /**
     * 
     * @param userRole
     * @throws GPSException
     * @author Muhammad Attique
     */
	void softDeleteUserRole(UserRole userRole) throws GPSException;
}
