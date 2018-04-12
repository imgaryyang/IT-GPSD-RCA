package com.gps.vo.helper;

import com.gps.vo.Process;
import com.gps.vo.RcaCoordinator;
import com.gps.vo.RcaEmailNotificationSetting;

import java.util.ArrayList;
import java.util.List;

public class ContractHelper {
	
	private String formAction;
	private String customerPreference = null;
	private String inac = null;
	private String customerName = null;
    private String accessLevel;
	private boolean selected = false;
	private List<String> imtCountries = new ArrayList<String>();
	private List<String> deliveryCenters = new ArrayList<String>();
	private List<Process> processList = new ArrayList<Process>();
	private RcaEmailNotificationSetting emailNotificationSetting = null;
	
	private String contractPhaseId = null;
	private String isUnderGoingTransition = null;
	private String contractTransitionPhaseStartDate = null;
	private String contractTransitionPhaseEndDate= null;
	private String phaseStartDate= null;
	private String phaseEndDate= null;
	private String contractTransformationStartDate= null;
	private String contractTransformationEndDate= null;
	
	private String contractSteadyStateEffectiveDate= null;
	private String phaseOutEffectiveDate= null;
	private String closureReason= null;
	private String closureReasonDesc= null;
	private List<String> contractScope = new ArrayList<String>();
	private String contractIndicator = null;
	private List<String> contractProcesses = new ArrayList<String>();
    private List<RcaCoordinator> rcaCoordinators = new ArrayList<RcaCoordinator>();

    private String gtlName = null;
	private String gtlEmail = null;
	private String gtlPhone = null;
	private String gdlName = null;
	private String gdlEmail = null;
	private String gdlPhone = null;
	private String peName = null;
	private String peEmail = null;
	private String pePhone = null;
	private String sdpeName = null;
	private String sdpeEmail = null;
	private String sdpePhone = null;
	private String dpmeName = null;
	private String dpmeEmail = null;
	private String dpmePhone = null;
	private String tmName = null;
	private String tmEmail = null;
	private String tmPhone = null;
	private String faName = null;
	private String faEmail = null;
	private String faPhone = null;
	private String bomName = null;
	private String bomEmail = null;
	private String bomPhone = null;

    private String aoName = null;
    private String aoEmail = null;
    private String aoPhone = null;
    private String afpName = null;
    private String afpEmail = null;
    private String afpPhone = null;
    private String bpoName = null;
    private String bpoEmail = null;
    private String bpoPhone = null;
    private String sdmName = null;
    private String sdmEmail = null;
    private String sdmPhone = null;
    private String dpeName = null;
    private String dpeEmail = null;
    private String dpePhone = null;
    private String pcName = null;
    private String pcEmail = null;
    private String pcPhone = null;

    private String priRcacName = null;
  	private String priRcacEmail = null;
    private String priRcacPhone = null;


    private String contractHealthUpdateFrequency = null;
	private List<String> bcrTypes = new ArrayList<String>();
	private List<String> cssTypes = new ArrayList<String>();


    public String getPriRcacName() {
        return priRcacName;
    }

    public void setPriRcacName(String priRcacName) {
        this.priRcacName = priRcacName;
    }

    public String getPriRcacEmail() {
        return priRcacEmail;
    }

    public void setPriRcacEmail(String priRcacEmail) {
        this.priRcacEmail = priRcacEmail;
    }

    public String getPriRcacPhone() {
        return priRcacPhone;
    }

    public void setPriRcacPhone(String priRcacPhone) {
        this.priRcacPhone = priRcacPhone;
    }

	
    public String getAfpName() {
        return afpName;
    }
    public void setAfpName(String afpName) {
        this.afpName = afpName;
    }
    public String getAfpEmail() {
        return afpEmail;
    }
    public void setAfpEmail(String afpEmail) {
        this.afpEmail = afpEmail;
    }
    public String getAfpPhone() {
        return afpPhone;
    }
    public void setAfpPhone(String afpPhone) {
        this.afpPhone = afpPhone;
    }
    public String getBpoName() {
        return bpoName;
    }
    public void setBpoName(String bpoName) {
        this.bpoName = bpoName;
    }
    public String getBpoEmail() {
        return bpoEmail;
    }
    public void setBpoEmail(String bpoEmail) {
        this.bpoEmail = bpoEmail;
    }
    public String getBpoPhone() {
        return bpoPhone;
    }
    public void setBpoPhone(String bpoPhone) {
        this.bpoPhone = bpoPhone;
    }
    public String getSdmName() {
        return sdmName;
    }
    public void setSdmName(String sdmName) {
        this.sdmName = sdmName;
    }
    public String getSdmEmail() {
        return sdmEmail;
    }
    public void setSdmEmail(String sdmEmail) {
        this.sdmEmail = sdmEmail;
    }
    public String getSdmPhone() {
        return sdmPhone;
    }
    public void setSdmPhone(String sdmPhone) {
        this.sdmPhone = sdmPhone;
    }
    public String getDpeName() {
        return dpeName;
    }
    public void setDpeName(String dpeName) {
        this.dpeName = dpeName;
    }
    public String getDpeEmail() {
        return dpeEmail;
    }
    public void setDpeEmail(String dpeEmail) {
        this.dpeEmail = dpeEmail;
    }
    public String getDpePhone() {
        return dpePhone;
    }
    public void setDpePhone(String dpePhone) {
        this.dpePhone = dpePhone;
    }
    public String getPcName() {
        return pcName;
    }
    public void setPcName(String pcName) {
        this.pcName = pcName;
    }
    public String getPcEmail() {
        return pcEmail;
    }
    public void setPcEmail(String pcEmail) {
        this.pcEmail = pcEmail;
    }
    public String getPcPhone() {
        return pcPhone;
    }
    public void setPcPhone(String pcPhone) {
        this.pcPhone = pcPhone;
    }

	public String getAoName() {
		return aoName;
	}
	public void setAoName(String aoName) {
		this.aoName = aoName;
	}
	public String getAoEmail() {
		return aoEmail;
	}
	public void setAoEmail(String aoEmail) {
		this.aoEmail = aoEmail;
	}
	public String getAoPhone() {
		return aoPhone;
	}
	public void setAoPhone(String aoPhone) {
		this.aoPhone = aoPhone;
	}
	public String getCustomerPreference() {
		return customerPreference;
	}
	public void setCustomerPreference(String customerPreference) {
		this.customerPreference = customerPreference;
	}
	public String getInac() {
		return inac;
	}
	public void setInac(String inac) {
		this.inac = inac;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean isSelected) {
		this.selected = isSelected;
	}
	public List<String> getImtCountries() {
		
		return imtCountries;
	}
	public void setImtCountries(List<String> imtCountriesList) {
		this.imtCountries = imtCountriesList;
	}
	public List<String> getDeliveryCenters() {
		return deliveryCenters;
	}
	public void setDeliveryCenters(List<String> deliveryCenters) {
		this.deliveryCenters = deliveryCenters;
	}
	public String getContractPhaseId() {
		return contractPhaseId;
	}
	public void setContractPhaseId(String contractPhase) {
		this.contractPhaseId = contractPhase;
	}
	public String getIsUnderGoingTransition() {
		return isUnderGoingTransition;
	}
	public void setIsUnderGoingTransition(String isUnderGoingTransition) {
		this.isUnderGoingTransition = isUnderGoingTransition;
	}
	public String getContractTransitionPhaseStartDate() {
		return contractTransitionPhaseStartDate;
	}
	public void setContractTransitionPhaseStartDate(
			String contractTransitionPhaseStartDate) {
		this.contractTransitionPhaseStartDate = contractTransitionPhaseStartDate;
	}
	public String getContractTransitionPhaseEndDate() {
		return contractTransitionPhaseEndDate;
	}
	public void setContractTransitionPhaseEndDate(
			String contractTransitionPhaseEndDate) {
		this.contractTransitionPhaseEndDate = contractTransitionPhaseEndDate;
	}
	public String getPhaseStartDate() {
		return phaseStartDate;
	}
	public void setPhaseStartDate(String contractStartDate) {
		this.phaseStartDate = contractStartDate;
	}
	public String getContractTransformationStartDate() {
		return contractTransformationStartDate;
	}
	public void setContractTransformationStartDate(
			String contractTransformationStartDate) {
		this.contractTransformationStartDate = contractTransformationStartDate;
	}
	public String getContractTransformationEndDate() {
		return contractTransformationEndDate;
	}
	public void setContractTransformationEndDate(
			String contractTransformationEndDate) {
		this.contractTransformationEndDate = contractTransformationEndDate;
	}
	public String getPhaseEndDate() {
		return phaseEndDate;
	}
	public void setPhaseEndDate(String contractEndDate) {
		this.phaseEndDate = contractEndDate;
	}
	public String getContractSteadyStateEffectiveDate() {
		return contractSteadyStateEffectiveDate;
	}
	public void setContractSteadyStateEffectiveDate(
			String contractSteadyStateEffectiveDate) {
		this.contractSteadyStateEffectiveDate = contractSteadyStateEffectiveDate;
	}
	public String getPhaseOutEffectiveDate() {
		return phaseOutEffectiveDate;
	}
	public void setPhaseOutEffectiveDate(String phaseOutEffectiveDate) {
		this.phaseOutEffectiveDate = phaseOutEffectiveDate;
	}
	public String getClosureReason() {
		return closureReason;
	}
	public void setClosureReason(String closureReason) {
		this.closureReason = closureReason;
	}
	public String getClosureReasonDesc() {
		return closureReasonDesc;
	}
	public void setClosureReasonDesc(String closureReasonDesc) {
		this.closureReasonDesc = closureReasonDesc;
	}
	public List<String> getContractScope() {
		return contractScope;
	}
	public void setContractScope(List<String> contractScope) {
		this.contractScope = contractScope;
	}
	public String getContractIndicator() {
		return contractIndicator;
	}
	public void setContractIndicator(String contractIndicator) {
		this.contractIndicator = contractIndicator;
	}
	public List<String> getContractProcesses() {
		return contractProcesses;
	}
	public void setContractProcesses(List<String> contractProcesses) {
		this.contractProcesses = contractProcesses;
	}
	public String getGtlName() {
		return gtlName;
	}
	public void setGtlName(String gtlName) {
		this.gtlName = gtlName;
	}
	public String getGtlEmail() {
		return gtlEmail;
	}
	public void setGtlEmail(String gtlEmail) {
		this.gtlEmail = gtlEmail;
	}
	public String getGtlPhone() {
		return gtlPhone;
	}
	public void setGtlPhone(String gtlPhone) {
		this.gtlPhone = gtlPhone;
	}
	public String getGdlName() {
		return gdlName;
	}
	public void setGdlName(String gdlName) {
		this.gdlName = gdlName;
	}
	public String getGdlEmail() {
		return gdlEmail;
	}
	public void setGdlEmail(String gdlEmail) {
		this.gdlEmail = gdlEmail;
	}
	public String getGdlPhone() {
		return gdlPhone;
	}
	public void setGdlPhone(String gdlPhone) {
		this.gdlPhone = gdlPhone;
	}
	public String getPeName() {
		return peName;
	}
	public void setPeName(String peName) {
		this.peName = peName;
	}
	public String getPeEmail() {
		return peEmail;
	}
	public void setPeEmail(String peEmail) {
		this.peEmail = peEmail;
	}
	public String getPePhone() {
		return pePhone;
	}
	public void setPePhone(String pePhone) {
		this.pePhone = pePhone;
	}
	public String getSdpeName() {
		return sdpeName;
	}
	public void setSdpeName(String sdpeName) {
		this.sdpeName = sdpeName;
	}
	public String getSdpeEmail() {
		return sdpeEmail;
	}
	public void setSdpeEmail(String sdpeEmail) {
		this.sdpeEmail = sdpeEmail;
	}
	public String getSdpePhone() {
		return sdpePhone;
	}
	public void setSdpePhone(String sdpePhone) {
		this.sdpePhone = sdpePhone;
	}
	public String getDpmeName() {
		return dpmeName;
	}
	public void setDpmeName(String dpmeName) {
		this.dpmeName = dpmeName;
	}
	public String getDpmeEmail() {
		return dpmeEmail;
	}
	public void setDpmeEmail(String dpmeEmail) {
		this.dpmeEmail = dpmeEmail;
	}
	public String getDpmePhone() {
		return dpmePhone;
	}
	public void setDpmePhone(String dpmePhone) {
		this.dpmePhone = dpmePhone;
	}
	public String getTmName() {
		return tmName;
	}
	public void setTmName(String tmName) {
		this.tmName = tmName;
	}
	public String getTmEmail() {
		return tmEmail;
	}
	public void setTmEmail(String tmEmail) {
		this.tmEmail = tmEmail;
	}
	public String getTmPhone() {
		return tmPhone;
	}
	public void setTmPhone(String tmPhone) {
		this.tmPhone = tmPhone;
	}
	public String getFaName() {
		return faName;
	}
	public void setFaName(String faName) {
		this.faName = faName;
	}
	public String getFaEmail() {
		return faEmail;
	}
	public void setFaEmail(String faEmail) {
		this.faEmail = faEmail;
	}
	public String getFaPhone() {
		return faPhone;
	}
	public void setFaPhone(String faPhone) {
		this.faPhone = faPhone;
	}
	public String getBomName() {
		return bomName;
	}
	public void setBomName(String bomName) {
		this.bomName = bomName;
	}
	public String getBomEmail() {
		return bomEmail;
	}
	public void setBomEmail(String bomEmail) {
		this.bomEmail = bomEmail;
	}
	public String getBomPhone() {
		return bomPhone;
	}
	public void setBomPhone(String bomPhone) {
		this.bomPhone = bomPhone;
	}
	public String getContractHealthUpdateFrequency() {
		return contractHealthUpdateFrequency;
	}
	public void setContractHealthUpdateFrequency(
			String contractHealthUpdateFrequency) {
		this.contractHealthUpdateFrequency = contractHealthUpdateFrequency;
	}
	public List<String> getBcrTypes() {
		return bcrTypes;
	}
	public void setBcrTypes(List<String> bcrTypes) {
		this.bcrTypes = bcrTypes;
	}
	public List<String> getCssTypes() {
		return cssTypes;
	}
	public void setCssTypes(List<String> cssTypes) {
		this.cssTypes = cssTypes;
	}
	


	/**
	 * @return the formAction
	 */
	public String getFormAction() {
		return formAction;
	}

	/**
	 * @param formAction the formAction to set
	 */
	public void setFormAction(String formAction) {
		this.formAction = formAction;
	}

	/**
	 * @return the processList
	 */
	public List<com.gps.vo.Process> getProcessList() {
		return processList;
	}

	/**
	 * @param processList the processList to set
	 */
	public void setProcessList(List<com.gps.vo.Process> processList) {
		this.processList = processList;
	}


    public List<RcaCoordinator> getRcaCoordinators() {
        return rcaCoordinators;
    }

    public void setRcaCoordinators(List<RcaCoordinator> rcaCoordinators) {
        this.rcaCoordinators = rcaCoordinators;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public RcaEmailNotificationSetting getEmailNotificationSetting() {
        return emailNotificationSetting;
    }

    public void setEmailNotificationSetting(RcaEmailNotificationSetting emailNotificationSetting) {
        this.emailNotificationSetting = emailNotificationSetting;
    }
}
