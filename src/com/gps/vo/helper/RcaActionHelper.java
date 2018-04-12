package com.gps.vo.helper;

import com.gps.vo.GpsUser;


/**
 * Created by Waqar Malik on 17-04-2015.
 */
public class RcaActionHelper {

    private GpsUser actionCreatedBy;
    private GpsUser actionUpdatedBy;
    private String resolutionDescription;

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

    public String getResolutionDescription() {
        return resolutionDescription;
    }

    public void setResolutionDescription(String resolutionDescription) {
        this.resolutionDescription = resolutionDescription;
    }
}
