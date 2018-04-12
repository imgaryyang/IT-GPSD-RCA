package com.gps.vo;

import com.gps.vo.helper.ContractHelper;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the CONTRACT database table.
 * 
 */
@Entity
@NamedQuery(name="Contract.findAll", query="SELECT c FROM Contract c")
public class Contract implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3213552707198942925L;
	//@Transient Variables 
	private ContractHelper contractHelper;
	private String contractStartDate;
	private String contractEndDate;
	//@Transient End 
	private Integer contractId;
	private String applicationAlias;
	private String applicationDesc;
    private String applicationName;
	private String btmtId;
	private Integer compRcaDays;
	private Integer createdBy;
	private Timestamp createdOn;
	private Integer currentPhaseId;
	private String deliveryLocation;
	private Timestamp endDate;
	private String routingEnabled;
	private Timestamp startDate;
	private String state;
	private String title;
	private GpsUser gpsUser;
	private Timestamp updatedOn;
	private String workflowEnabled;
	private String reportingExcludeFlag;
	private String reportingIncludeFlag;
	private String autoHealthUpdate;
	private ContractContact contractContact;
	private Customer customer;
	private Integer rcaCoordinatorId;
	private List<ContractContact> contractContacts;
	private List<Rca> rcas;
	private Set<RcaCoordinator> rcaCoordinators;
    private String isDeleted;
    private Set<AccessControlList> accessControlLists;
    private Set<UserRole> userRoles;
	private String isActive;
    private Set<SessionAcl> sessionAcls;
   // private RcaEmailNotificationSetting rcaEmailNotificationSetting;



    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CONTRACT_ID")
	public Integer getContractId() {
		return this.contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}



	@Column(name="APPLICATION_ALIAS")
	public String getApplicationAlias() {
		return this.applicationAlias;
	}

	public void setApplicationAlias(String applicationAlias) {
		this.applicationAlias = applicationAlias;
	}


	@Column(name="APPLICATION_DESC")
	public String getApplicationDesc() {
		return this.applicationDesc;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}


    @Column(name="APPLICATION_NAME")
    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

	@Column(name="BTMT_ID")
	public String getBtmtId() {
		return this.btmtId;
	}

	public void setBtmtId(String btmtId) {
		this.btmtId = btmtId;
	}


	@Column(name="COMP_RCA_DAYS")
	public Integer getCompRcaDays() {
		return this.compRcaDays;
	}

	public void setCompRcaDays(Integer compRcaDays) {
		this.compRcaDays = compRcaDays;
	}


	@Column(name="CREATED_BY")
	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}


	@Column(name="CREATED_ON")
	public Timestamp getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}


	@Column(name="CURRENT_PHASE_ID")
	public int getCurrentPhaseId() {
		return this.currentPhaseId;
	}

	public void setCurrentPhaseId(int currentPhaseId) {
		this.currentPhaseId = currentPhaseId;
	}


	@Column(name="DELIVERY_LOCATION")
	public String getDeliveryLocation() {
		return this.deliveryLocation;
	}

	public void setDeliveryLocation(String deliveryLocation) {
		this.deliveryLocation = deliveryLocation;
	}


	@Column(name="END_DATE")
	public Timestamp getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}


	@Column(name="ROUTING_ENABLED")
	public String getRoutingEnabled() {
		return this.routingEnabled;
	}

	public void setRoutingEnabled(String routingEnabled) {
		this.routingEnabled = routingEnabled;
	}


	@Column(name="START_DATE")
	public Timestamp getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}


	@Column(name="\"STATE\"")
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}


	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	/**
	 * bi-directional many-to-one association to BpdUser
	 * @return the gpsUser
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="UPDATED_BY")
	public GpsUser getGpsUser() {
		return gpsUser;
	}


	/**
	 * @param gpsUser the gpsUser to set
	 */
	public void setGpsUser(GpsUser gpsUser) {
		this.gpsUser = gpsUser;
	}


	@Column(name="UPDATED_ON")
	public Timestamp getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}


	@Column(name="WORKFLOW_ENABLED")
	public String getWorkflowEnabled() {
		return this.workflowEnabled;
	}

	public void setWorkflowEnabled(String workflowEnabled) {
		this.workflowEnabled = workflowEnabled;
	}

	@Column(name="REPORTING_EXCLUDE_FLAG", length=1)
	public String getReportingExcludeFlag() {
		return this.reportingExcludeFlag;
	}

	public void setReportingExcludeFlag(String reportingExcludeFlag) {
		this.reportingExcludeFlag = reportingExcludeFlag;
	}


	@Column(name="REPORTING_INCLUDE_FLAG", length=1)
	public String getReportingIncludeFlag() {
		return this.reportingIncludeFlag;
	}

	public void setReportingIncludeFlag(String reportingIncludeFlag) {
		this.reportingIncludeFlag = reportingIncludeFlag;
	}
	
	@Column(name="AUTO_HEALTH_UPDATE", nullable=false, length=1)	
	public String getAutoHealthUpdate() {
		return this.autoHealthUpdate;
	}

	public void setAutoHealthUpdate(String autoHealthUpdate) {
		this.autoHealthUpdate = autoHealthUpdate;
	}

    @Column(name="IS_DELETED", length=1)
    public String getIsDeleted() { return isDeleted;  }

    public void setIsDeleted(String isDeleted) { this.isDeleted = isDeleted; }


	@Column(name="IS_ACTIVE", length=1)
	public String getIsActive() { return isActive;  }

	public void setIsActive(String isActive) { this.isActive = isActive; }


	//bi-directional many-to-one association to ContractContact
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CONTRACT_CONTACT_ID")
	public ContractContact getContractContact() {
		return this.contractContact;
	}

	public void setContractContact(ContractContact contractContact) {
		this.contractContact = contractContact;
	}


	//bi-directional many-to-one association to Customer
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="INAC")
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	@Column(name="RCA_COORDINATOR_ID")
	public Integer getRcaCoordinatorId() {
		return this.rcaCoordinatorId;
	}

	public void setRcaCoordinatorId(Integer rcaCoordinatorId) {
		this.rcaCoordinatorId = rcaCoordinatorId;
	}

	//bi-directional many-to-one association to ContractContact
	@OneToMany(mappedBy="contract")
	public List<ContractContact> getContractContacts() {
		return this.contractContacts;
	}

	public void setContractContacts(List<ContractContact> contractContacts) {
		this.contractContacts = contractContacts;
	}

	public ContractContact addContractContact(ContractContact contractContact) {
		getContractContacts().add(contractContact);
		contractContact.setContract(this);

		return contractContact;
	}

	public ContractContact removeContractContact(ContractContact contractContact) {
		getContractContacts().remove(contractContact);
		contractContact.setContract(null);

		return contractContact;
	}


    //bi-directional many-to-one association to AccessControlList
    @OneToMany(mappedBy="contract")
    public Set<AccessControlList> getAccessControlLists() {
        return this.accessControlLists;
    }

    public void setAccessControlLists(Set<AccessControlList> accessControlLists) {
        this.accessControlLists = accessControlLists;
    }

    //bi-directional many-to-one association to UserRole
    @OneToMany(mappedBy="contract")
    public Set<UserRole> getUserRoles() {
        return this.userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }


	//bi-directional many-to-one association to Rca
	@OneToMany(mappedBy="contract")
	public List<Rca> getRcas() {
		return this.rcas;
	}

	public void setRcas(List<Rca> rcas) {
		this.rcas = rcas;
	}


	//bi-directional many-to-one association to RcaCoordinator
	@OneToMany(mappedBy="contract")
   	public Set<RcaCoordinator> getRcaCoordinators() {
		return this.rcaCoordinators;
	}

	public void setRcaCoordinators(Set<RcaCoordinator> rcaCoordinators) {
		this.rcaCoordinators = rcaCoordinators;
	}

    //bi-directional many-to-one association to SessionAcl
    @OneToMany(mappedBy="contract")
    public Set<SessionAcl> getSessionAcls() {
        return this.sessionAcls;
    }

    public void setSessionAcls(Set<SessionAcl> sessionAcls) {
        this.sessionAcls = sessionAcls;
    }
/*
    @OneToOne(mappedBy="contract")
    public RcaEmailNotificationSetting getRcaEmailNotificationSetting() {
        return rcaEmailNotificationSetting;
    }

    public void setRcaEmailNotificationSetting(RcaEmailNotificationSetting rcaEmailNotificationSetting) {
        this.rcaEmailNotificationSetting = rcaEmailNotificationSetting;
    }

*/
    @Transient
	public String getContractStartDate() {
		return contractStartDate;
	}

	public void setContractStartDate(String contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	
	@Transient 
	public ContractHelper getContractHelper() {
		return contractHelper;
	}

	public void setContractHelper(ContractHelper contractHelper) {
		this.contractHelper = contractHelper;
	}


	/**
	 * @return the contractEndDate
	 */
	@Transient 
	public String getContractEndDate() {
		return contractEndDate;
	}


	/**
	 * @param contractEndDate the contractEndDate to set
	 */
	public void setContractEndDate(String contractEndDate) {
		this.contractEndDate = contractEndDate;
	}


}