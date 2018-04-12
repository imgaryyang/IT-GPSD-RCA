package com.gps.util;

import com.gps.vo.GpsUser;
import com.gps.vo.UserRole;

import java.io.Serializable;
import java.util.List;

public class UserSession implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6188129014472267536L;
	private GpsUser gpsUser;
	private Integer accessLevel;
    private String accessLevelTypes;
    private List<UserRole> roles;
    private String isAdmin;
    private List<String> userRoles;
	
	@Override
	public String toString(){
		StringBuilder buff = new StringBuilder();
		if(gpsUser != null){
			buff.append("Id=").append(gpsUser.getEmail());
			buff.append(", Acess Level=").append(CommonUtil.getAccessName(accessLevel));
		}
		return buff.toString();
		
	}
	


	/**
	 * @return the accessLevel
	 */
	public Integer getAccessLevel() {
		return accessLevel;
	}

	/**
	 * @param accessLevel the accessLevel to set
	 */
	public void setAccessLevel(Integer accessLevel) {
		this.accessLevel = accessLevel;
	}



	/**
	 * @return the gpsUser
	 */
	public GpsUser getGpsUser() {
		return gpsUser;
	}



	/**
	 * @param gpsUser the gpsUser to set
	 */
	public void setGpsUser(GpsUser gpsUser) {
		this.gpsUser = gpsUser;
	}

    public String getAccessLevelTypes() {
        return accessLevelTypes;
    }

    public void setAccessLevelTypes(String accessLevelTypes) {
        this.accessLevelTypes = accessLevelTypes;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public List<String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<String> userRoles) {
        this.userRoles = userRoles;
    }
}
