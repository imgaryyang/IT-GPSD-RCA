package com.gps.service;

import com.gps.exceptions.GPSException;
import com.gps.vo.GpsUser;
import com.gps.vo.UserRole;
import com.gps.vo.helper.UserRoleForm;

import java.util.List;

/**
 * This class provides interface for UserRole Service.
 *  
 * @author Waqar Malik
 */
public interface UserRoleService {

	UserRole loadUserRole(Integer urId) throws GPSException;

	/**
	 * Modified on 20-Oct-16 - Added loggedInUser by @author Muhammad Attique
	 * @param urId
	 * @param loggedInUser
	 * @return
	 * @throws GPSException
	 *  
	 */
	Boolean deleteUserRole(Integer urId, Integer loggedInUser) throws GPSException;

	boolean processUserRole(UserRoleForm userRoleForm, String command) throws GPSException;

	public boolean processUserRole(Integer urId, String email, Integer contractId, String role) throws GPSException;

 	List<UserRole> getUserRolesByUserId(Integer userId) throws GPSException;

    List<UserRole> getUserRolesByContractId(Integer contractId) throws GPSException;

    List<GpsUser> getAllUsersHavingRole() throws GPSException;

	List <UserRole> getUserRolesByContractIdAndRole(Integer contractId, String role);

    public List<UserRole> getUserRolesByContractIdAndUserId(Integer contractId, Integer userId);

    public UserRole getUserRolesByContractIdAndUserIdAndRole(Integer contractId, Integer userId, String role);

    public void saveUserRole(UserRole userRole);

    public UserRole getGlobalRole(String role);

    List<UserRole> getGlobalCoordinators();

    List<UserRole> getDpeListByContractIdAndRole(Integer contractId, String dpeRole);

    List<UserRole> getEditorListByContractIdAndRole(Integer contractId, String editorRole);

}
