package com.gps.vo;

import com.gps.vo.helper.RcaActionHelper;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;



@Entity
@Table(name="RCA_ACTION")
@NamedQuery(name="RcaAction.findAll", query="SELECT r FROM RcaAction r")
public class RcaAction implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1669704376570182538L;
	private Integer rcaActionId;
	private String actionDesc;
	private String actionItemNote;
	private String actionStatus;
	private String additionalInfo;
	private Date assignedOn;
	private String assignedTo;
	private Date closeDate;
	private Integer completePercent;
	private Integer createdBy;
	private String followupActivity;
	private String rcaNote;
	private Date targetDate;
	private Integer updatedBy;
	private Timestamp updatedOn;
    private String targetDateModificationReason;
	private List<RcaActionHistoryLog> rcaActionHistoryLogs;
	private Rca rca;
    private String actionNumber;
    private List<RcaActionSupportingFile> rcaActionSupportingFiles;
    private Timestamp createdOn;

    /** Transient **/
    private String actionTargetDate;
    private String actionClosedDate;
    private RcaActionHelper rcaActionHelper;
    private String isNew = "N";
    private String completePercentTxt;


    public RcaAction() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RCA_ACTION_ID")
	public Integer getRcaActionId() {
		return this.rcaActionId;
	}

	public void setRcaActionId(Integer rcaActionId) {
		this.rcaActionId = rcaActionId;
	}


	@Column(name="RCA_ACTION_NUMBER")
	public String getActionNumber() {
		return this.actionNumber;
	}

	public void setActionNumber(String actionNumber) {
		this.actionNumber = actionNumber;
	}


    @Column(name="ACTION_DESC")
    public String getActionDesc() {
        return this.actionDesc;
    }

    public void setActionDesc(String actionDesc) {
        this.actionDesc = actionDesc;
    }


    @Column(name="ACTION_ITEM_NOTE")
	public String getActionItemNote() {
		return this.actionItemNote;
	}

	public void setActionItemNote(String actionItemNote) {
		this.actionItemNote = actionItemNote;
	}


	@Column(name="ACTION_STATUS")
	public String getActionStatus() {
		return this.actionStatus;
	}

	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}


	@Column(name="ADDITIONAL_INFO")
	public String getAdditionalInfo() {
		return this.additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="ASSIGNED_ON")
	public Date getAssignedOn() {
		return this.assignedOn;
	}

	public void setAssignedOn(Date assignedOn) {
		this.assignedOn = assignedOn;
	}


	@Column(name="ASSIGNED_TO")
	public String getAssignedTo() {
		return this.assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="CLOSE_DATE")
	public Date getCloseDate() {
		return this.closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}


	@Column(name="COMPLETE_PERCENT")
	public Integer getCompletePercent() {
		return this.completePercent;
	}

	public void setCompletePercent(Integer completePercent) {
		this.completePercent = completePercent;
	}


	@Column(name="CREATED_BY")
	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}


	@Column(name="FOLLOWUP_ACTIVITY")
	public String getFollowupActivity() {
		return this.followupActivity;
	}

	public void setFollowupActivity(String followupActivity) {
		this.followupActivity = followupActivity;
	}


	@Column(name="RCA_NOTE")
	public String getRcaNote() {
		return this.rcaNote;
	}

	public void setRcaNote(String rcaNote) {
		this.rcaNote = rcaNote;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="TARGET_DATE")
	public Date getTargetDate() {
		return this.targetDate;
	}

	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}


	@Column(name="UPDATED_BY")
	public Integer getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}


	@Column(name="CREATED_ON")
	public Timestamp getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Timestamp updatedOn) {
		this.createdOn = createdOn;
	}


    @Column(name="UPDATED_ON")
    public Timestamp getUpdatedOn() {
        return this.updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Column(name="TARGET_DATE_MODIFICATION_REASON")
    public String getTargetDateModificationReason() {
        return targetDateModificationReason;
    }

    public void setTargetDateModificationReason(String targetDateModificationReason) {
        this.targetDateModificationReason = targetDateModificationReason;
    }


	//bi-directional many-to-one association to RcaActionHistoryLog
	@OneToMany(mappedBy="rcaAction")
	public List<RcaActionHistoryLog> getRcaActionHistoryLogs() {
		return this.rcaActionHistoryLogs;
	}

	public void setRcaActionHistoryLogs(List<RcaActionHistoryLog> rcaActionHistoryLogs) {
		this.rcaActionHistoryLogs = rcaActionHistoryLogs;
	}


    //bi-directional many-to-one association to RcaActionSupportingFile
    @OneToMany(mappedBy="rcaAction")
    public List<RcaActionSupportingFile> getRcaActionSupportingFiles() {
        return rcaActionSupportingFiles;
    }

    public void setRcaActionSupportingFiles(List<RcaActionSupportingFile> rcaActionSupportingFiles) {
        this.rcaActionSupportingFiles = rcaActionSupportingFiles;
    }

	public RcaActionHistoryLog addActionHistoryLog(RcaActionHistoryLog rcaActionHistoryLog) {
		getRcaActionHistoryLogs().add(rcaActionHistoryLog);
		rcaActionHistoryLog.setRcaAction(this);

		return rcaActionHistoryLog;
	}

	public RcaActionHistoryLog removeActionHistoryLog(RcaActionHistoryLog rcaActionHistoryLog) {
		getRcaActionHistoryLogs().remove(rcaActionHistoryLog);
		rcaActionHistoryLog.setRcaAction(null);

		return rcaActionHistoryLog;
	}


	//bi-directional many-to-one association to Rca
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="RCA_ID")
	public Rca getRca() {
		return this.rca;
	}

	public void setRca(Rca rca) {
		this.rca = rca;
	}

    @Transient
    public String getActionTargetDate() {
        return actionTargetDate;
    }

    public void setActionTargetDate(String actionTargetDate) {
        this.actionTargetDate = actionTargetDate;
    }

    @Transient
    public String getActionClosedDate() {
        return actionClosedDate;
    }

    public void setActionClosedDate(String actionClosedDate) {
        this.actionClosedDate = actionClosedDate;
    }


    @Transient
    public RcaActionHelper getRcaActionHelper() {
        return rcaActionHelper;
    }

    public void setRcaActionHelper(RcaActionHelper rcaActionHelper) {
        this.rcaActionHelper = rcaActionHelper;
    }

	@Transient
	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	@Transient
	public String getCompletePercentTxt() {
		return completePercentTxt;
	}


	public void setCompletePercentTxt(String completePercentTxt) {
		this.completePercentTxt = completePercentTxt;
	}
}
