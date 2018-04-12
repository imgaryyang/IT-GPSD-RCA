package com.gps.vo.helper;

import com.gps.vo.GpsUser;
import com.gps.vo.RcaCause;

import java.util.Date;
import java.util.List;

/**
 * Created by Waqar Malik on 17-04-2015.
 */
public class RcaHelper {
    private GpsUser rcaDpe;
    private List<RcaCause> contributingCauses;
    private String rcaCoordinatorManager;
    private Date approvedDate;
    private String secondaryProblemIncidentRecord;
    private String secondaryAssociatedTool;
   
    public GpsUser getRcaDpe() {
        return rcaDpe;
    }

    public void setRcaDpe(GpsUser rcaDpe) {
        this.rcaDpe = rcaDpe;
    }

    public List<RcaCause> getContributingCauses() {
        return contributingCauses;
    }

    public void setContributingCauses(List<RcaCause> contributingCauses) {
        this.contributingCauses = contributingCauses;
    }

    public String getRcaCoordinatorManager() {
        return rcaCoordinatorManager;
    }

    public void setRcaCoordinatorManager(String rcaCoordinatorManager) {
        this.rcaCoordinatorManager = rcaCoordinatorManager;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getSecondaryProblemIncidentRecord() {
        return secondaryProblemIncidentRecord;
    }

    public void setSecondaryProblemIncidentRecord(String secondaryProblemIncidentRecord) {
        this.secondaryProblemIncidentRecord = secondaryProblemIncidentRecord;
    }

    public String getSecondaryAssociatedTool() {
        return secondaryAssociatedTool;
    }

    public void setSecondaryAssociatedTool(String secondaryAssociatedTool) {
        this.secondaryAssociatedTool = secondaryAssociatedTool;
    }
}
