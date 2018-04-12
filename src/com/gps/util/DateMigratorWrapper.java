package com.gps.util;

import com.gps.vo.GpsUser;
import com.gps.vo.SessionAcl;
import com.gps.vo.helper.UserVo;

import java.io.Serializable;
import java.util.List;

public class DateMigratorWrapper implements Serializable{
	private static final long serialVersionUID = 1L;
	private GpsUser gpsUser;
	private UserVo userVo;
	private String sessionId;
    //UserRolesHelper rolesHelper;
	private Long siteId;
	private String siteCode;
	private boolean hasError;
	private boolean passwordVerified;
	
	private List<SessionAcl> listSessionAcl;
	
	public Integer getMaxLevel(){
		Integer max = -1;
		if(listSessionAcl != null){
			for(SessionAcl acl : listSessionAcl){
				if(max < acl.getActiveAccessLevel()){
					max = acl.getActiveAccessLevel();
				}
			}
		}
		return max;
	}
	
	public boolean isHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
	
	/*public UserRolesHelper getRolesHelper() {
		return rolesHelper;
	}
	public void setRolesHelper(UserRolesHelper rolesHelper) {
		this.rolesHelper = rolesHelper;
	}*/
	
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public String getSiteCode() {
		return siteCode;
	}
	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	

	/**
	 * @return the userVo
	 */
	public UserVo getUserVo() {
		return userVo;
	}

	/**
	 * @param userVo the userVo to set
	 */
	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}

	/**
	 * @return the listSessionAcl
	 */
	public List<SessionAcl> getListSessionACL() {
		return listSessionAcl;
	}

	/**
	 * @param listSessionAcl the listSessionAcl to set
	 */
	public void setListSessionACL(List<SessionAcl> listSessionACL) {
		this.listSessionAcl = listSessionACL;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the passwordVerified
	 */
	public boolean isPasswordVerified() {
		return passwordVerified;
	}

	/**
	 * @param passwordVerified the passwordVerified to set
	 */
	public void setPasswordVerified(boolean passwordVerified) {
		this.passwordVerified = passwordVerified;
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

	
	
}
