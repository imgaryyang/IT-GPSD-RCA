package com.gps.service;

import com.gps.util.DateMigratorWrapper;
import com.gps.vo.AccessControlList;
import com.gps.vo.SessionAcl;
import com.gps.vo.UserRole;
import com.gps.vo.helper.UserVo;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

public interface ClientService {
	DateMigratorWrapper loginUser(DateMigratorWrapper dateMigratorWrapper);
	
	/**
	 * 
	 * @param intranetId
	 * @param password
	 * @return
	 */
	Boolean verifyPassword(String intranetId, String password);

	/**
	 * This method is used to get List<SessionAcl> for which user has access.
	 * 
	 * @param acls
	 * @return
	 */
	List<SessionAcl> getContractsByAcls(Set<AccessControlList> acls);

	List<SessionAcl> getContractsByUserRoles(Set<UserRole> userRoles);

	/**
	 * 
	 * @param intranetId
	 * @return
	 */
	String getUserRole(String intranetId);

	/**
	 * 
	 * @param userVo
	 * @param session
	 */
	void genOTPassword(UserVo userVo, HttpSession session);
}
