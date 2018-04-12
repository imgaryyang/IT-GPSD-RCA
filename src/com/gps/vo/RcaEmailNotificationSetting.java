package com.gps.vo;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the RCA_EMAIL_NOTIFICATION_SETTINGS database table.
 * 
 */
@Entity
@Table(name="RCA_EMAIL_NOTIFICATION_SETTINGS")
@NamedQuery(name="RcaEmailNotificationSetting.findAll", query="SELECT r FROM RcaEmailNotificationSetting r")
public class RcaEmailNotificationSetting implements Serializable {

	private static final long serialVersionUID = 5285210519845824827L;

	private Integer rcaEmailNotificationSettingId;
    private Contract contract;
    private Integer rcaCompletionTargetBusinessDays;
    private String rcaRoutingMatrixEnabled;
    private String rcaWorkflowNotificationEnabled;
    private String newRcaNotificationToCoordinator;
    private String rcaAssignmentNotificationToOwner;
    private String rcaNotAcceptedReminderForOwner;
    private Integer rcaNotAcceptedReminderForOwnerDays;
    private String rcaNotSubmittedReminderForOwner;
    private Integer rcaNotSubmittedReminderForOwnerDays;
    private String rcaAwaitingApprovalNotification;
    private String rcaApprovedNotification;
    private String rcaRejectedNotification;
    private String rcaNotApprovedReminder;
    private Integer rcaNotApprovedReminderDaysAfterCreation;
    private Integer rcaNotApprovedReminderDuration;
    private String NewActionItemNotification;
    private String actionNotClosedReminder;
    private Integer actionNotClosedReminderDaysBeforeTarget;
    private Integer actionNotClosedReminderDuration;
    private String rcaNotClosedReminder;
    private Integer rcaNotClosedReminderDaysAfterCreation;
    private Integer rcaNotClosedReminderDuration;
    private String rcaClosedNotification;
    private String rcaCancelledNotification;
    private String actionItemCancelledNotification;
    private String actionItemClosedNotification;





    public RcaEmailNotificationSetting() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RCA_EMAIL_NOTIFICATION_SETTINGS_ID")
	public Integer getRcaEmailNotificationSettingId() {
		return this.rcaEmailNotificationSettingId;
	}

	public void setRcaEmailNotificationSettingId(Integer rcaEmailNotificationSettingId) {
		this.rcaEmailNotificationSettingId = rcaEmailNotificationSettingId;
	}

	//bi-directional many-to-one association to Contract
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CONTRACT_ID", nullable=false)
	public Contract getContract() {
		return this.contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

    @Column(name="RCA_COMPLETION_TARGET_BUSINESS_DAYS")
    public Integer getRcaCompletionTargetBusinessDays() {
        return rcaCompletionTargetBusinessDays;
    }

    public void setRcaCompletionTargetBusinessDays(Integer rcaCompletionTargetBusinessDays) {
        this.rcaCompletionTargetBusinessDays = rcaCompletionTargetBusinessDays;
    }

    @Column(name="RCA_ROUTING_MATRIX_ENABLED")
    public String getRcaRoutingMatrixEnabled() {
        return rcaRoutingMatrixEnabled;
    }

    public void setRcaRoutingMatrixEnabled(String rcaRoutingMatrixEnabled) {
        this.rcaRoutingMatrixEnabled = rcaRoutingMatrixEnabled;
    }

    @Column(name="RCA_WORKFLOW_NOTIFICATION_ENABLED")
    public String getRcaWorkflowNotificationEnabled() {
        return rcaWorkflowNotificationEnabled;
    }

    public void setRcaWorkflowNotificationEnabled(String rcaWorkflowNotificationEnabled) {
        this.rcaWorkflowNotificationEnabled = rcaWorkflowNotificationEnabled;
    }

    @Column(name="NEW_RCA_NOTIFICATION_TO_COORDINATOR")
    public String getNewRcaNotificationToCoordinator() {
        return newRcaNotificationToCoordinator;
    }

    public void setNewRcaNotificationToCoordinator(String newRcaNotificationToCoordinator) {
        this.newRcaNotificationToCoordinator = newRcaNotificationToCoordinator;
    }

    @Column(name="RCA_ASSIGNMENT_NOTIFICATION_TO_OWNER")
    public String getRcaAssignmentNotificationToOwner() {
        return rcaAssignmentNotificationToOwner;
    }

    public void setRcaAssignmentNotificationToOwner(String rcaAssignmentNotificationToOwner) {
        this.rcaAssignmentNotificationToOwner = rcaAssignmentNotificationToOwner;
    }

    @Column(name="RCA_NOT_ACCEPTED_REMINDER_FOR_OWNER")
    public String getRcaNotAcceptedReminderForOwner() {
        return rcaNotAcceptedReminderForOwner;
    }

    public void setRcaNotAcceptedReminderForOwner(String rcaNotAcceptedReminderForOwner) {
        this.rcaNotAcceptedReminderForOwner = rcaNotAcceptedReminderForOwner;
    }

    @Column(name="RCA_NOT_ACCEPTED_REMINDER_FOR_OWNER_DAYS")
    public Integer getRcaNotAcceptedReminderForOwnerDays() {
        return rcaNotAcceptedReminderForOwnerDays;
    }

    public void setRcaNotAcceptedReminderForOwnerDays(Integer rcaNotAcceptedReminderForOwnerDays) {
        this.rcaNotAcceptedReminderForOwnerDays = rcaNotAcceptedReminderForOwnerDays;
    }

    @Column(name="RCA_NOT_SUBMITTED_REMINDER_FOR_OWNER")
    public String getRcaNotSubmittedReminderForOwner() {
        return rcaNotSubmittedReminderForOwner;
    }

    public void setRcaNotSubmittedReminderForOwner(String rcaNotSubmittedReminderForOwner) {
        this.rcaNotSubmittedReminderForOwner = rcaNotSubmittedReminderForOwner;
    }

    @Column(name="RCA_NOT_SUBMITTED_REMINDER_FOR_OWNER_DAYS")
    public Integer getRcaNotSubmittedReminderForOwnerDays() {
        return rcaNotSubmittedReminderForOwnerDays;
    }

    public void setRcaNotSubmittedReminderForOwnerDays(Integer rcaNotSubmittedReminderForOwnerDays) {
        this.rcaNotSubmittedReminderForOwnerDays = rcaNotSubmittedReminderForOwnerDays;
    }

    @Column(name="RCA_AWAITING_APPROVAL_NOTIFICATION")
    public String getRcaAwaitingApprovalNotification() {
        return rcaAwaitingApprovalNotification;
    }

    public void setRcaAwaitingApprovalNotification(String rcaAwaitingApprovalNotification) {
        this.rcaAwaitingApprovalNotification = rcaAwaitingApprovalNotification;
    }

    @Column(name="RCA_APPROVED_NOTIFICATION")
    public String getRcaApprovedNotification() {
        return rcaApprovedNotification;
    }

    public void setRcaApprovedNotification(String rcaApprovedNotification) {
        this.rcaApprovedNotification = rcaApprovedNotification;
    }

    @Column(name="RCA_REJECTED_NOTIFICATION")
    public String getRcaRejectedNotification() {
        return rcaRejectedNotification;
    }

    public void setRcaRejectedNotification(String rcaRejectedNotification) {
        this.rcaRejectedNotification = rcaRejectedNotification;
    }

    @Column(name="RCA_NOT_APPROVED_REMINDER")
    public String getRcaNotApprovedReminder() {
        return rcaNotApprovedReminder;
    }

    public void setRcaNotApprovedReminder(String rcaNotApprovedReminder) {
        this.rcaNotApprovedReminder = rcaNotApprovedReminder;
    }

    @Column(name="RCA_NOT_APPROVED_REMINDER_DAYS_AFTER_CREATION")
    public Integer getRcaNotApprovedReminderDaysAfterCreation() {
        return rcaNotApprovedReminderDaysAfterCreation;
    }

    public void setRcaNotApprovedReminderDaysAfterCreation(Integer rcaNotApprovedReminderDaysAfterCreation) {
        this.rcaNotApprovedReminderDaysAfterCreation = rcaNotApprovedReminderDaysAfterCreation;
    }

    @Column(name="RCA_NOT_APPROVED_REMINDER_DURATION")
    public Integer getRcaNotApprovedReminderDuration() {
        return rcaNotApprovedReminderDuration;
    }

    public void setRcaNotApprovedReminderDuration(Integer rcaNotApprovedReminderDuration) {
        this.rcaNotApprovedReminderDuration = rcaNotApprovedReminderDuration;
    }

    @Column(name="NEW_ACTION_ITEM_NOTIFICATION")
    public String getNewActionItemNotification() {
        return NewActionItemNotification;
    }

    public void setNewActionItemNotification(String newActionItemNotification) {
        NewActionItemNotification = newActionItemNotification;
    }

    @Column(name="ACTION_NOT_CLOSED_REMINDER")
    public String getActionNotClosedReminder() {
        return actionNotClosedReminder;
    }

    public void setActionNotClosedReminder(String actionNotClosedReminder) {
        this.actionNotClosedReminder = actionNotClosedReminder;
    }

    @Column(name="ACTION_NOT_CLOSED_REMINDER_DAYS_BEFORE_TARGET")
    public Integer getActionNotClosedReminderDaysBeforeTarget() {
        return actionNotClosedReminderDaysBeforeTarget;
    }

    public void setActionNotClosedReminderDaysBeforeTarget(Integer actionNotClosedReminderDaysBeforeTarget) {
        this.actionNotClosedReminderDaysBeforeTarget = actionNotClosedReminderDaysBeforeTarget;
    }

    @Column(name="ACTION_NOT_CLOSED_REMINDER_DURATION")
    public Integer getActionNotClosedReminderDuration() {
        return actionNotClosedReminderDuration;
    }

    public void setActionNotClosedReminderDuration(Integer actionNotClosedReminderDuration) {
        this.actionNotClosedReminderDuration = actionNotClosedReminderDuration;
    }

    @Column(name="RCA_NOT_CLOSED_REMINDER")
    public String getRcaNotClosedReminder() {
        return rcaNotClosedReminder;
    }

    public void setRcaNotClosedReminder(String rcaNotClosedReminder) {
        this.rcaNotClosedReminder = rcaNotClosedReminder;
    }

    @Column(name="RCA_NOT_CLOSED_REMINDER_DAYS_AFTER_CREATION")
    public Integer getRcaNotClosedReminderDaysAfterCreation() {
        return rcaNotClosedReminderDaysAfterCreation;
    }

    public void setRcaNotClosedReminderDaysAfterCreation(Integer rcaNotClosedReminderDaysAfterCreation) {
        this.rcaNotClosedReminderDaysAfterCreation = rcaNotClosedReminderDaysAfterCreation;
    }

    @Column(name="RCA_NOT_CLOSED_REMINDER_DURATION")
    public Integer getRcaNotClosedReminderDuration() {
        return rcaNotClosedReminderDuration;
    }

    public void setRcaNotClosedReminderDuration(Integer rcaNotClosedReminderDuration) {
        this.rcaNotClosedReminderDuration = rcaNotClosedReminderDuration;
    }

    @Column(name="RCA_CLOSED_NOTIFICATION")
    public String getRcaClosedNotification() {
        return rcaClosedNotification;
    }

    public void setRcaClosedNotification(String rcaClosedNotification) {
        this.rcaClosedNotification = rcaClosedNotification;
    }

    @Column(name="RCA_CANCELLED_NOTIFICATION")
    public String getRcaCancelledNotification() {
        return rcaCancelledNotification;
    }

    public void setRcaCancelledNotification(String rcaCancelledNotification) {
        this.rcaCancelledNotification = rcaCancelledNotification;
    }

    @Column(name="ACTION_ITEM_CANCELLED_NOTIFICATION")
    public String getActionItemCancelledNotification() {
        return actionItemCancelledNotification;
    }

    public void setActionItemCancelledNotification(String actionItemCancelledNotification) {
        this.actionItemCancelledNotification = actionItemCancelledNotification;
    }

    @Column(name="ACTION_ITEM_CLOSED_NOTIFICATION")
    public String getActionItemClosedNotification() {
        return actionItemClosedNotification;
    }

    public void setActionItemClosedNotification(String actionItemClosedNotification) {
        this.actionItemClosedNotification = actionItemClosedNotification;
    }
}