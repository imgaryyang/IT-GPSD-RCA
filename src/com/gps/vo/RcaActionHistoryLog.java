package com.gps.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;



@Entity
@Table(name="ACTION_HISTORY_LOG")
@NamedQuery(name="RcaActionHistoryLog.findAll", query="SELECT a FROM RcaActionHistoryLog a")
public class RcaActionHistoryLog implements Serializable {
	
	private static final long serialVersionUID = -8488369625171397486L;
	private Integer rcaActionHistoryId;
	private String comments;
	private String role;
    private String formAction;
	private Timestamp submittedOn;
	private RcaAction rcaAction;
    private GpsUser submittedBy;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ACTION_HISTORY_ID")
	public Integer getRcaActionHistoryId() {
		return this.rcaActionHistoryId;
	}

	public void setRcaActionHistoryId(Integer rcaActionHistoryId) {
		this.rcaActionHistoryId = rcaActionHistoryId;
	}

    @Column(name="COMMENTS")
	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}


	@Column(name="\"ROLE\"")
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}


	@Column(name="SUBMITTED_ON")
	public Timestamp getSubmittedOn() {
		return this.submittedOn;
	}

	public void setSubmittedOn(Timestamp submittedOn) {
		this.submittedOn = submittedOn;
	}


	//bi-directional many-to-one association to RcaAction
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="RCA_ACTION_ID")
	public RcaAction getRcaAction() {
		return this.rcaAction;
	}

	public void setRcaAction(RcaAction rcaAction) {
		this.rcaAction = rcaAction;
	}

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SUBMITTED_BY")
    public GpsUser getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(GpsUser submittedBy) {
        this.submittedBy = submittedBy;
    }

    @Column(name="FORM_ACTION")
    public String getFormAction() {
        return formAction;
    }

    public void setFormAction(String formAction) {
        this.formAction = formAction;
    }
}
