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

import com.gps.vo.Contract;
import com.gps.vo.UserRole;

import java.io.Serializable;
import java.util.List;

/**
 * This class is a helper vo for UI.

 */

public class UserRoleForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651229625501781751L;
	
	private UserRole userRole;
	private String email;
	private String command;
    private List<Contract> contracts;


    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }
}
