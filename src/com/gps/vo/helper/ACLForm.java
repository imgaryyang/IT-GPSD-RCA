/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: ACLForm.java
 *    CREATOR:Waqar Malik
 *    DEPT: GBS PAK
 *    DATE: 17/07/2013
 *
 *-PURPOSE-----------------------------------------------------------------
 * 
 *--------------------------------------------------------------------------
 *
 *
 *-CHANGE LOG--------------------------------------------------------------
 * 17/07/2013Waqar Malik Initial coding.
 *==========================================================================
 * </pre> */
package com.gps.vo.helper;

import com.gps.vo.AccessControlList;
import com.gps.vo.Contract;

import java.io.Serializable;
import java.util.List;

/**
 * This class is a helper vo for UI.
 *  
 * @authorWaqar Malik
 */

public class ACLForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651229625501781751L;
	
	private AccessControlList acl;
	private AccessControlList group;
	private List<Contract> contracts;

	private String email;
	private String groupName;
	private String command;
	
	/**
	 * @return the acl
	 */
	public AccessControlList getAcl() {
		return acl;
	}
	/**
	 * @param acl the acl to set
	 */
	public void setAcl(AccessControlList acl) {
		this.acl = acl;
	}
	/**
	 * @return the contracts
	 */
	public List<Contract> getContracts() {
		return contracts;
	}
	/**
	 * @param contracts the contracts to set
	 */
	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}
	/**
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}
	/**
	 * @param command the command to set
	 */
	public void setCommand(String command) {
		this.command = command;
	}
	
	/**
	 * @return the group
	 */
	public AccessControlList getGroup() {
		return group;
	}
	/**
	 * @param group the group to set
	 */
	public void setGroup(AccessControlList group) {
		this.group = group;
	}
	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
