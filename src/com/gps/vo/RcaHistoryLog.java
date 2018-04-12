package com.gps.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


@Entity
@Table(name="RCA_HISTORY_LOG")
@NamedQuery(name="RcaHistoryLog.findAll", query="SELECT a FROM RcaHistoryLog a")
public class RcaHistoryLog implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer rcaHistoryId;
	private String comments;
	private String role;
	private Timestamp submittedOn;
    private GpsUser submittedBy;
   	private String formAction;
    private Rca rca;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RCA_HISTORY_ID")
	public Integer getRcaHistoryId() {
		return this.rcaHistoryId;
	}

	public void setRcaHistoryId(Integer rcaHistoryId) {
		this.rcaHistoryId = rcaHistoryId;
	}

    @Column(name="COMMENTS")
	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}


	@Column(name="ROLE")
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


	//bi-directional many-to-one association to Rca
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="RCA_ID")
	public Rca getRca() {
		return this.rca;
	}

	public void setRca(Rca rca) {
		this.rca = rca;
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
