
package com.gps.vo.helper;

import java.io.Serializable;

/**
 * This class is a helper vo for UI.

 */

public class RcaFormDateHelper implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -7790131369905737098L;
	private String rcaClosedDate;
    private String rcaDueDate;
    private String rcaCreatedDate;
    private String incidentStartDateTime;
    private String incidentEndDateTime;
    private String serviceRestoredDateTime;




    public String getRcaClosedDate() {
        return rcaClosedDate;
    }

    public void setRcaClosedDate(String rcaClosedDate) {
        this.rcaClosedDate = rcaClosedDate;
    }

    public String getRcaDueDate() {
        return rcaDueDate;
    }

    public void setRcaDueDate(String rcaDueDate) {
        this.rcaDueDate = rcaDueDate;
    }

    public String getRcaCreatedDate() {
        return rcaCreatedDate;
    }

    public void setRcaCreatedDate(String rcaCreatedDate) {
        this.rcaCreatedDate = rcaCreatedDate;
    }

    public String getIncidentStartDateTime() {
        return incidentStartDateTime;
    }

    public void setIncidentStartDateTime(String incidentStartDateTime) {
        this.incidentStartDateTime = incidentStartDateTime;
    }

    public String getIncidentEndDateTime() {
        return incidentEndDateTime;
    }

    public void setIncidentEndDateTime(String incidentEndDateTime) {
        this.incidentEndDateTime = incidentEndDateTime;
    }

    public String getServiceRestoredDateTime() {
        return serviceRestoredDateTime;
    }

    public void setServiceRestoredDateTime(String serviceRestoredDateTime) {
        this.serviceRestoredDateTime = serviceRestoredDateTime;
    }


}
