package com.gps.vo;

import com.gps.vo.helper.RcaHelper;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the RCA database table.
 */
@Entity
@NamedQuery(name = "Rca.findAll", query = "SELECT r FROM Rca r")
public class Rca implements Serializable {
    private static final long serialVersionUID = 1L;

    //@Transient Variables
    private GpsUser rcaCreatedBy;
    private String incidentDuration;
    private String serviceRestoredDuration;
    private RcaCause primaryRcaCause;
    
    private RcaCoordinator primaryRcaCoordinator;
    private RcaHelper rcaHelper;
    //End @Transient Variables


    private Integer rcaId;
    private Integer actionItemClosed;
    private Integer actionItemOpen;
    private Date closeDate;
    private Timestamp createDate;
    private Integer createdBy;
    private String customerImpacted;
    private Date dueDate;
    private String impactDetails;
    private Timestamp incidentEntTime;
    private Timestamp incidentStartTime;
    private String isDelegateAccepted;
    private String isOwnerAccepted;
    private Integer month;
    private String problemIndidentRecord;
    private String rcaCoordinatorId;
    private String rcaDelegate;
    private Integer rcaDpeId;
    private String rcaNumber;
    private String rcaOwner;
    private String rcaReason;
    private String rcaOtherLocation;
    private String rcaReasonOutage;
    private String rcaStage;
    private Integer severity;
    private String sourceNotification;
    private String title;
    private Integer updatedBy;
    private Timestamp updatedOn;
    private Integer year;
    private String approvalsOrReviews;
    private String notesAndEscalations;
    private String rcaAssociatedWith;
    private String listOfSlaSloImpacted;
    private String primaryTicketExists;
    private String primaryTicketType;
    private String primaryTicketAssociation;
    private String noPrimaryTicketExplanation;
    private String businessImpactInMins;
    private String systemImpacted;
    private String serviceImpacted;
    private String totalGpsPractitionersImpacted;
    private String customerImpactedList;
    private String customerOther;
    private String locationOfFailure;
    private String customerAgreedRca;
    private String rcaOpeningReason;
    private String failedChangeCriteria;
    private String failedChangeImpactLevel;
    private String failedChangeAssignee;
    private String failedChangeAssigneeGroup;
    private String otherReasonDetail;
    private String hasCoordinatorApproved;
    private String dueDateModificationReason;
    private String priOtherAssociatedTool;
    private String managedBy;
    private Contract contract;
    private List<RcaAction> rcaActions;
    private List<RcaCause> rcaCauses;
    private RcaEventDetail rcaEventDetail;
    private RcaPreventionDetail rcaPreventionDetail;
    private List<RcaTicket> rcaTickets;
    private List<RcaHistoryLog> rcaHistoryLogs;
    private List<RcaSupportingFile> rcaSupportingFiles;
    private List<RcaEditor> rcaEditors;



    public Rca() {
    	
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RCA_ID")
    public Integer getRcaId() {
        return this.rcaId;
    }

    public void setRcaId(Integer rcaId) {
        this.rcaId = rcaId;
    }


    @Column(name = "ACTION_ITEM_CLOSED")
    public Integer getActionItemClosed() {
        return this.actionItemClosed;
    }

    public void setActionItemClosed(Integer actionItemClosed) {
        this.actionItemClosed = actionItemClosed;
    }


    @Column(name = "ACTION_ITEM_OPEN")
    public Integer getActionItemOpen() {
        return this.actionItemOpen;
    }

    public void setActionItemOpen(Integer actionItemOpen) {
        this.actionItemOpen = actionItemOpen;
    }


    @Temporal(TemporalType.DATE)
    @Column(name = "CLOSE_DATE")
    public Date getCloseDate() {
        return this.closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }


    @Column(name = "CREATE_DATE")
    public Timestamp getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }


    @Column(name = "CREATED_BY")
    public Integer getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }


    @Column(name = "CUSTOMER_IMPACTED")
    public String getCustomerImpacted() {
        return this.customerImpacted;
    }

    public void setCustomerImpacted(String customerImpacted) {
        this.customerImpacted = customerImpacted;
    }


    @Temporal(TemporalType.DATE)
    @Column(name = "DUE_DATE")
    public Date getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }


    @Column(name = "IMPACT_DETAILS")
    public String getImpactDetails() {
        return this.impactDetails;
    }

    public void setImpactDetails(String impactDetails) {
        this.impactDetails = impactDetails;
    }


    @Column(name = "INCIDENT_ENT_TIME")
    public Timestamp getIncidentEntTime() {
        return this.incidentEntTime;
    }

    public void setIncidentEntTime(Timestamp incidentEntTime) {
        this.incidentEntTime = incidentEntTime;
    }


    @Column(name = "INCIDENT_START_TIME")
    public Timestamp getIncidentStartTime() {
        return this.incidentStartTime;
    }

    public void setIncidentStartTime(Timestamp incidentStartTime) {
        this.incidentStartTime = incidentStartTime;
    }


    @Column(name = "IS_DELEGATE_ACCEPTED")
    public String getIsDelegateAccepted() {
        return this.isDelegateAccepted;
    }

    public void setIsDelegateAccepted(String isDelegateAccepted) {
        this.isDelegateAccepted = isDelegateAccepted;
    }


    @Column(name = "IS_OWNER_ACCEPTED")
    public String getIsOwnerAccepted() {
        return this.isOwnerAccepted;
    }

    public void setIsOwnerAccepted(String isOwnerAccepted) {
        this.isOwnerAccepted = isOwnerAccepted;
    }


    @Column(name = "\"MONTH\"")
    public Integer getMonth() {
        return this.month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    @Column(name = "PROBLEM_INDIDENT_RECORD")
    public String getProblemIndidentRecord() {
        return this.problemIndidentRecord;
    }

    public void setProblemIndidentRecord(String problemIndidentRecord) {
        this.problemIndidentRecord = problemIndidentRecord;
    }


    @Column(name = "RCA_COORDINATOR_ID")
    public String getRcaCoordinatorId() {
        return this.rcaCoordinatorId;
    }

    public void setRcaCoordinatorId(String rcaCoordinatorId) {
        this.rcaCoordinatorId = rcaCoordinatorId;
    }


    @Column(name = "RCA_DELEGATE")
    public String getRcaDelegate() {
        return this.rcaDelegate;
    }

    public void setRcaDelegate(String rcaDelegate) {
        this.rcaDelegate = rcaDelegate;
    }


    @Column(name = "RCA_DPE_ID")
    public Integer getRcaDpeId() {
        return this.rcaDpeId;
    }

    public void setRcaDpeId(Integer rcaDpeId) {
        this.rcaDpeId = rcaDpeId;
    }


    @Column(name = "RCA_NUMBER")
    public String getRcaNumber() {
        return this.rcaNumber;
    }

    public void setRcaNumber(String rcaNumber) {
        this.rcaNumber = rcaNumber;
    }


    @Column(name = "RCA_OWNER")
    public String getRcaOwner() {
        return this.rcaOwner;
    }

    public void setRcaOwner(String rcaOwner) {
        this.rcaOwner = rcaOwner;
    }


    @Column(name = "RCA_REASON")
    public String getRcaReason() {
        return this.rcaReason;
    }

    public void setRcaReason(String rcaReason) {
        this.rcaReason = rcaReason;
    }

    @Column(name = "RCA_OTHER_LOCATION")
    public String getRcaOtherLocation() {
		return rcaOtherLocation;
	}

	public void setRcaOtherLocation(String rcaOtherLocation) {
		this.rcaOtherLocation = rcaOtherLocation;
	}

	@Column(name = "RCA_REASON_OUTAGE")
	public String getRcaReasonOutage() {
		return rcaReasonOutage;
	}

	public void setRcaReasonOutage(String rcaReasonOutage) {
		this.rcaReasonOutage = rcaReasonOutage;
	}

    @Column(name = "RCA_STAGE")
    public String getRcaStage() {
        return this.rcaStage;
    }

    public void setRcaStage(String rcaStage) {
        this.rcaStage = rcaStage;
    }

    @Column(name = "SEVERITY")
    public Integer getSeverity() {
        return this.severity;
    }

    public void setSeverity(Integer severity) {
        this.severity = severity;
    }


    @Column(name = "SOURCE_NOTIFICATION")
    public String getSourceNotification() {
        return this.sourceNotification;
    }

    public void setSourceNotification(String sourceNotification) {
        this.sourceNotification = sourceNotification;
    }


    @Column(name = "TITLE")
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Column(name = "UPDATED_BY")
    public Integer getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }


    @Column(name = "UPDATED_ON")
    public Timestamp getUpdatedOn() {
        return this.updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }


    @Column(name = "\"YEAR\"")
    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }


    //bi-directional many-to-one association to Contract
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTRACT_ID")
    public Contract getContract() {
        return this.contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }


    //bi-directional many-to-one association to RcaAction
    @OneToMany(mappedBy = "rca")
    public List<RcaAction> getRcaActions() {
        return this.rcaActions;
    }

    public void setRcaActions(List<RcaAction> rcaActions) {
        this.rcaActions = rcaActions;
    }

    public RcaAction addRcaAction(RcaAction rcaAction) {
        getRcaActions().add(rcaAction);
        rcaAction.setRca(this);

        return rcaAction;
    }

    public RcaAction removeRcaAction(RcaAction rcaAction) {
        getRcaActions().remove(rcaAction);
        rcaAction.setRca(null);

        return rcaAction;
    }

    @Transient
    public GpsUser getRcaCreatedBy() {
        return rcaCreatedBy;
    }

    public void setRcaCreatedBy(GpsUser rcaCreatedBy) {
        this.rcaCreatedBy = rcaCreatedBy;
    }


    @Transient
    public String getServiceRestoredDuration() {
        return serviceRestoredDuration;
    }

    public void setServiceRestoredDuration(String serviceRestoredDuration) {
        this.serviceRestoredDuration = serviceRestoredDuration;
    }

    @Transient
    public String getIncidentDuration() {
        return incidentDuration;
    }

    public void setIncidentDuration(String incidentDuration) {
        this.incidentDuration = incidentDuration;
    }


    //bi-directional many-to-one association to RcaCause
    @OneToMany(mappedBy = "rca")
    public List<RcaCause> getRcaCauses() {
        return this.rcaCauses;
    }

    public void setRcaCauses(List<RcaCause> rcaCauses) {
        this.rcaCauses = rcaCauses;
    }


    //bi-directional one-to-one association to RcaEventDetail
    @OneToOne(mappedBy = "rca", fetch = FetchType.LAZY)
    public RcaEventDetail getRcaEventDetail() {
        return this.rcaEventDetail;
    }

    public void setRcaEventDetail(RcaEventDetail rcaEventDetail) {
        this.rcaEventDetail = rcaEventDetail;
    }

    //bi-directional one-to-one association to RcaEventDetail
    @OneToOne(mappedBy = "rca", fetch = FetchType.LAZY)
    public RcaPreventionDetail getRcaPreventionDetail() {
        return rcaPreventionDetail;
    }

    public void setRcaPreventionDetail(RcaPreventionDetail rcaPreventionDetail) {
        this.rcaPreventionDetail = rcaPreventionDetail;
    }

    //bi-directional many-to-one association to RcaTicket
    @OneToMany(mappedBy = "rca")
    public List<RcaTicket> getRcaTickets() {
        return this.rcaTickets;
    }

    public void setRcaTickets(List<RcaTicket> rcaTickets) {
        this.rcaTickets = rcaTickets;
    }

    public RcaTicket addRcaTicket(RcaTicket rcaTicket) {
        getRcaTickets().add(rcaTicket);
        rcaTicket.setRca(this);

        return rcaTicket;
    }

    public RcaTicket removeRcaTicket(RcaTicket rcaTicket) {
        getRcaTickets().remove(rcaTicket);
        rcaTicket.setRca(null);

        return rcaTicket;
    }

    @Transient
    public RcaCause getPrimaryRcaCause() {
        return primaryRcaCause;
    }

    public void setPrimaryRcaCause(RcaCause primaryRcaCause) {
        this.primaryRcaCause = primaryRcaCause;
    }


    @Column(name = "APPROVALS_OR_REVIEWS")
    public String getApprovalsOrReviews() {
        return approvalsOrReviews;
    }

    public void setApprovalsOrReviews(String approvalsOrReviews) {
        this.approvalsOrReviews = approvalsOrReviews;
    }

    @Column(name = "NOTES_AND_ESCALATIONS")
    public String getNotesAndEscalations() {
        return notesAndEscalations;
    }

    public void setNotesAndEscalations(String notesAndEscalations) {
        this.notesAndEscalations = notesAndEscalations;
    }

    //bi-directional many-to-one association to RcaHistoryLog
    @OneToMany(mappedBy = "rca")
    public List<RcaHistoryLog> getRcaHistoryLogs() {
        return rcaHistoryLogs;
    }

    public void setRcaHistoryLogs(List<RcaHistoryLog> rcaHistoryLogs) {
        this.rcaHistoryLogs = rcaHistoryLogs;
    }

    @Column(name = "RCA_ASSOCIATED_WITH")
    public String getRcaAssociatedWith() {
        return rcaAssociatedWith;
    }

    public void setRcaAssociatedWith(String rcaAssociatedWith) {
        this.rcaAssociatedWith = rcaAssociatedWith;
    }

    @Column(name = "LIST_OF_SLA_SLO_IMPACTED")
    public String getListOfSlaSloImpacted() {
        return listOfSlaSloImpacted;
    }

    public void setListOfSlaSloImpacted(String listOfSlaSloImpacted) {
        this.listOfSlaSloImpacted = listOfSlaSloImpacted;
    }

    @Column(name = "PRIMARY_TICKET_EXISTS")
    public String getPrimaryTicketExists() {
        return primaryTicketExists;
    }

    public void setPrimaryTicketExists(String primaryTicketExists) {
        this.primaryTicketExists = primaryTicketExists;
    }

    @Column(name = "PRIMARY_TICKET_TYPE")
    public String getPrimaryTicketType() {
        return primaryTicketType;
    }

    public void setPrimaryTicketType(String primaryTicketType) {
        this.primaryTicketType = primaryTicketType;
    }

    @Column(name = "PRIMARY_TICKET_ASSOCIATION")
    public String getPrimaryTicketAssociation() {
        return primaryTicketAssociation;
    }

    public void setPrimaryTicketAssociation(String primaryTicketAssociation) {
        this.primaryTicketAssociation = primaryTicketAssociation;
    }

    @Column(name = "NO_PRIMARY_TICKET_EXPLAINATION")
    public String getNoPrimaryTicketExplanation() {
        return noPrimaryTicketExplanation;
    }

    public void setNoPrimaryTicketExplanation(String noPrimaryTicketExplanation) {
        this.noPrimaryTicketExplanation = noPrimaryTicketExplanation;
    }

    @Column(name = "BUSINESS_IMPACT_IN_MINS")
    public String getBusinessImpactInMins() {
        return businessImpactInMins;
    }

    public void setBusinessImpactInMins(String businessImpactInMins) {
        this.businessImpactInMins = businessImpactInMins;
    }

    @Column(name = "SYSTEM_IMPACTED")
    public String getSystemImpacted() {
        return systemImpacted;
    }

    public void setSystemImpacted(String systemImpacted) {
        this.systemImpacted = systemImpacted;
    }

    @Column(name = "TOTAL_GPS_PRACT_IMPACTED")
    public String getTotalGpsPractitionersImpacted() {
        return totalGpsPractitionersImpacted;
    }

    public void setTotalGpsPractitionersImpacted(String totalGpsPractitionersImpacted) {
        this.totalGpsPractitionersImpacted = totalGpsPractitionersImpacted;
    }

    @Column(name = "CUSTOMER_IMPACTED_LIST")
    public String getCustomerImpactedList() {
        return customerImpactedList;
    }

    public void setCustomerImpactedList(String customerImpactedList) {
        this.customerImpactedList = customerImpactedList;
    }

    @Column(name = "LOC_OF_FAILURE")
    public String getLocationOfFailure() {
        return locationOfFailure;
    }

    public void setLocationOfFailure(String locationOfFailure) {
        this.locationOfFailure = locationOfFailure;
    }

    @Column(name = "SERVICE_IMPACTED")
    public String getServiceImpacted() {
        return serviceImpacted;
    }

    public void setServiceImpacted(String serviceImpacted) {
        this.serviceImpacted = serviceImpacted;
    }


    @Column(name = "CUSTOMER_AGREED_RCA")
    public String getCustomerAgreedRca() {
        return customerAgreedRca;
    }

    public void setCustomerAgreedRca(String customerAgreedRca) {
        this.customerAgreedRca = customerAgreedRca;
    }


    @Column(name="FAILED_CHANGE_CRITERIA")
    public String getFailedChangeCriteria() {
        return failedChangeCriteria;
    }

    public void setFailedChangeCriteria(String failedChangeCriteria) {
        this.failedChangeCriteria = failedChangeCriteria;
    }

    @Column(name="FAILED_CHANGE_IMPACT_LEVEL")
    public String getFailedChangeImpactLevel() {
        return failedChangeImpactLevel;
    }

    public void setFailedChangeImpactLevel(String failedChangeImpactLevel) {
        this.failedChangeImpactLevel = failedChangeImpactLevel;
    }

    @Column(name="FAILED_CHANGE_ASSIGNEE")
    public String getFailedChangeAssignee() {
        return failedChangeAssignee;
    }

    public void setFailedChangeAssignee(String failedChangeAssignee) {
        this.failedChangeAssignee = failedChangeAssignee;
    }

    @Column(name="FAILED_CHANGE_ASSIGNEE_GROUP")
    public String getFailedChangeAssigneeGroup() {
        return failedChangeAssigneeGroup;
    }

    public void setFailedChangeAssigneeGroup(String failedChangeAssigneeGroup) {
        this.failedChangeAssigneeGroup = failedChangeAssigneeGroup;
    }

    @Column(name="OTHER_REASON_DETAILS")
    public String getOtherReasonDetail() {
        return otherReasonDetail;
    }

    public void setOtherReasonDetail(String otherReasonDetail) {
        this.otherReasonDetail = otherReasonDetail;
    }

    @Column(name="RCA_OPENING_REASON")
    public String getRcaOpeningReason() {
        return rcaOpeningReason;
    }

    public void setRcaOpeningReason(String rcaOpeningReason) {
        this.rcaOpeningReason = rcaOpeningReason;
    }

    @Column(name = "HAS_COORDINATOR_APPROVED")
    public String getHasCoordinatorApproved() {
        return hasCoordinatorApproved;
    }

    public void setHasCoordinatorApproved(String hasCoordinatorApproved) {
        this.hasCoordinatorApproved = hasCoordinatorApproved;
    }


    //bi-directional many-to-one association to RcaSupportingFile
    @OneToMany(mappedBy = "rca")
    public List<RcaSupportingFile> getRcaSupportingFiles() {
        return rcaSupportingFiles;
    }

    public void setRcaSupportingFiles(List<RcaSupportingFile> rcaSupportingFiles) {
        this.rcaSupportingFiles = rcaSupportingFiles;
    }

    //bi-directional many-to-one association to RcaSupportingFile
    @OneToMany(mappedBy = "rca")
    public List<RcaEditor> getRcaEditors() {
        return rcaEditors;
    }

    public void setRcaEditors(List<RcaEditor> rcaEditors) {
        this.rcaEditors = rcaEditors;
    }

    @Transient
    public RcaCoordinator getPrimaryRcaCoordinator() {
        return primaryRcaCoordinator;
    }

    public void setPrimaryRcaCoordinator(RcaCoordinator primaryRcaCoordinator) {
        this.primaryRcaCoordinator = primaryRcaCoordinator;
    }

    @Transient
    public RcaHelper getRcaHelper() {
        return rcaHelper;
    }

    public void setRcaHelper(RcaHelper rcaHelper) {
        this.rcaHelper = rcaHelper;
    }

    @Column(name = "DUE_DATE_MODIFICATION_REASON")
    public String getDueDateModificationReason() {
        return dueDateModificationReason;
    }

    public void setDueDateModificationReason(String dueDateModificationReason) {
        this.dueDateModificationReason = dueDateModificationReason;
    }

    @Column(name = "PRIMARY_TICKET_OTHER_ASSOCIATION")
    public String getPriOtherAssociatedTool() {
        return priOtherAssociatedTool;
    }

    public void setPriOtherAssociatedTool(String priOtherAssociatedTool) {
        this.priOtherAssociatedTool = priOtherAssociatedTool;
    }

    @Column(name = "CUSTOMER_OTHER")
	public String getCustomerOther() {
		return customerOther;
	}


	public void setCustomerOther(String customerOther) {
		this.customerOther = customerOther;
	}


	 @Column(name = "MANAGED_BY")
	public String getManagedBy() {
		return managedBy;
	}


	public void setManagedBy(String managedBy) {
		this.managedBy = managedBy;
	}

    
}