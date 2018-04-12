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

import com.gps.vo.GpsUser;
import com.gps.vo.RcaActionHistoryLog;
import com.gps.vo.RcaActionSupportingFile;

import java.io.Serializable;
import java.util.List;

/**
 * This class is a helper vo for UI.
 */

public class ActionHelper implements Serializable {

    private String monthName;
    private Integer year;
    private GpsUser actionCreatedBy;
    private GpsUser actionUpdatedBy;
    private String rcaCoordinator;
    private String assignedDate;
    private String targetDate;
    private List<RcaActionSupportingFile> supportingFiles;
    private String fileDescription;
    private List<RcaActionHistoryLog> rcaActionHistoryLogs;
    private String resolutionDescription;
    private Boolean targetDateUpdated=false;
    private String userRoles;


    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public GpsUser getActionCreatedBy() {
        return actionCreatedBy;
    }

    public void setActionCreatedBy(GpsUser actionCreatedBy) {
        this.actionCreatedBy = actionCreatedBy;
    }

    public GpsUser getActionUpdatedBy() {
        return actionUpdatedBy;
    }

    public void setActionUpdatedBy(GpsUser actionUpdatedBy) {
        this.actionUpdatedBy = actionUpdatedBy;
    }

    public String getRcaCoordinator() {
        return rcaCoordinator;
    }

    public void setRcaCoordinator(String rcaCoordinator) {
        this.rcaCoordinator = rcaCoordinator;
    }

    public String getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(String assignedDate) {
        this.assignedDate = assignedDate;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    public List<RcaActionSupportingFile> getSupportingFiles() {
        return supportingFiles;
    }

    public void setSupportingFiles(List<RcaActionSupportingFile> supportingFiles) {
        this.supportingFiles = supportingFiles;
    }

    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
    }

    public List<RcaActionHistoryLog> getRcaActionHistoryLogs() {
        return rcaActionHistoryLogs;
    }

    public void setRcaActionHistoryLogs(List<RcaActionHistoryLog> rcaActionHistoryLogs) {
        this.rcaActionHistoryLogs = rcaActionHistoryLogs;
    }

    public String getResolutionDescription() {
        return resolutionDescription;
    }

    public void setResolutionDescription(String resolutionDescription) {
        this.resolutionDescription = resolutionDescription;
    }

    public Boolean getTargetDateUpdated() {
        return targetDateUpdated;
    }

    public void setTargetDateUpdated(Boolean targetDateUpdated) {
        this.targetDateUpdated = targetDateUpdated;
    }

    public String getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(String userRoles) {
        this.userRoles = userRoles;
    }
}
