package com.gps.vo.helper;

import com.gps.vo.Contract;
import com.gps.vo.RcaCoordinator;

import java.io.Serializable;
import java.util.List;

/**
 * This class is a helper vo for UI.

 */

public class InitiateRcaForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651229625501781751L;
	

	private String rcaContract;
	private String rcaCoordinator;
    private List<Contract> contracts;
    private List<RcaCoordinator> rcaCoordinatorList;
    private String rcaNumber;
    private String rcaMonth;
    private String rcaYear;

    public List<RcaCoordinator> getRcaCoordinatorList() {
        return rcaCoordinatorList;
    }

    public void setRcaCoordinatorList(List<RcaCoordinator> rcaCoordinatorList) {
        this.rcaCoordinatorList = rcaCoordinatorList;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public String getRcaCoordinator() {
        return rcaCoordinator;
    }

    public void setRcaCoordinator(String rcaCoordinator) {
        this.rcaCoordinator = rcaCoordinator;
    }

    public String getRcaContract() {
        return rcaContract;
    }

    public void setRcaContract(String rcaContract) {
        this.rcaContract = rcaContract;
    }

    public String getRcaNumber() {
        return rcaNumber;
    }

    public void setRcaNumber(String rcaNumber) {
        this.rcaNumber = rcaNumber;
    }

    public String getRcaMonth() {
        return rcaMonth;
    }

    public void setRcaMonth(String rcaMonth) {
        this.rcaMonth = rcaMonth;
    }

    public String getRcaYear() {
        return rcaYear;
    }

    public void setRcaYear(String rcaYear) {
        this.rcaYear = rcaYear;
    }
}
