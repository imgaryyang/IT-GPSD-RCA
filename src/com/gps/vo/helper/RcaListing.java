
package com.gps.vo.helper;

import java.io.Serializable;

public class RcaListing implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2751640914803063625L;

    private Integer rcaId;
    private String rcaOrActionNumber;
    private String rcaOwner;
    private String rcaDelegate;
    private String rcaCoordinator;
    private String rcaDueDate;
    private String rcaCreateDate;
    private String rcaStage;
    private String rcaDpe;
    private String rcaAccountTitle;
    private String numberAndType;
    private String listingType;
    private String primaryTicket;
    private String rcaTitle;
    private String month;
    private String year;


    public String toString(){
    	StringBuilder builder = new StringBuilder();
    	builder.append("rcaId="+rcaId);
    	builder.append(", rcaOwner="+rcaOwner);
    	if(rcaOrActionNumber != null && !rcaOrActionNumber.isEmpty())
    		builder.append(", rcaOrActionNumber="+rcaOrActionNumber);
    	if(rcaDelegate != null && !rcaDelegate.isEmpty())
    		builder.append(", rcaDelegate="+rcaDelegate);
    	if(rcaAccountTitle != null && !rcaAccountTitle.isEmpty())
    		builder.append(", rcaAccountTitle="+rcaAccountTitle);
    	if(rcaStage != null && !rcaStage.isEmpty())
    		builder.append(", rcaStage="+rcaStage);
    	if(rcaDueDate != null && !rcaDueDate.isEmpty())
    		builder.append(", rcaDueDate="+rcaDueDate);
    	
    	return builder.toString();
    }

    public String getRcaStage() {
        return rcaStage;
    }

    public void setRcaStage(String rcaStage) {
        this.rcaStage = rcaStage;
    }

    public String getRcaDpe() {
        return rcaDpe;
    }

    public void setRcaDpe(String rcaDpe) {
        this.rcaDpe = rcaDpe;
    }

    public Integer getRcaId() {
        return rcaId;
    }

    public void setRcaId(Integer rcaId) {
        this.rcaId = rcaId;
    }

    public String getRcaOrActionNumber() {
        return rcaOrActionNumber;
    }

    public void setRcaOrActionNumber(String rcaOrActionNumber) {
        this.rcaOrActionNumber = rcaOrActionNumber;
    }

    public String getRcaOwner() {
        return rcaOwner;
    }

    public void setRcaOwner(String rcaOwner) {
        this.rcaOwner = rcaOwner;
    }

    public String getRcaDelegate() {
        return rcaDelegate;
    }

    public void setRcaDelegate(String rcaDelegate) {
        this.rcaDelegate = rcaDelegate;
    }

    public String getRcaCoordinator() {
        return rcaCoordinator;
    }

    public void setRcaCoordinator(String rcaCoordinator) {
        this.rcaCoordinator = rcaCoordinator;
    }

    public String getRcaCreateDate() {
        return rcaCreateDate;
    }

    public void setRcaCreateDate(String rcaCreateDate) {
        this.rcaCreateDate = rcaCreateDate;
    }

    public String getRcaDueDate() {
        return rcaDueDate;
    }

    public void setRcaDueDate(String rcaDueDate) {
        this.rcaDueDate = rcaDueDate;
    }

    public String getRcaAccountTitle() {
        return rcaAccountTitle;
    }

    public void setRcaAccountTitle(String rcaAccountTitle) {
        this.rcaAccountTitle = rcaAccountTitle;
    }

    public String getNumberAndType() {
        return numberAndType;
    }

    public void setNumberAndType(String numberAndType) {
        this.numberAndType = numberAndType;
    }

    public String getListingType() {
        return listingType;
    }

    public void setListingType(String listingType) {
        this.listingType = listingType;
    }

    public String getPrimaryTicket() {
        return primaryTicket;
    }

    public void setPrimaryTicket(String primaryTicket) {
        this.primaryTicket = primaryTicket;
    }

    public String getRcaTitle() {
        return rcaTitle;
    }

    public void setRcaTitle(String rcaTitle) {
        this.rcaTitle = rcaTitle;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
