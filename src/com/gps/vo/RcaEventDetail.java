package com.gps.vo;

import javax.persistence.*;
import java.io.Serializable;

/*
 * The persistent class for the RCA_EVENT_DEATILS database table.
*/


@Entity
@Table(name="RCA_EVENT_DETAILS")
@NamedQuery(name="RcaEventDetail.findAll", query="SELECT r FROM RcaEventDetail r")
public class RcaEventDetail implements Serializable {
	
	private static final long serialVersionUID = 4973474968432821117L;
	private int rcaId;
	private String chronology;
	private String executiveSummary;
	private String howServiceRestored;
	private String isDelegateAccepted;
	private String isOwnerAccepted;
	private String issueDescription;
	private String rcaCoordinatorId;
	private String rcaDelegate;
	private String rcaOwner;
	private String repeatIssue;
	private String repeatRisk;
	private String supportTeamEffectiveness;
	private String why1;
	private String why2;
	private String why3;
	private String why4;
	private String whyProblem;
	private String impactedTower;
	private String executiveAlert;
	private String keyIssues;
	private String repeatRcaOrTicketNum;
	private Rca rca;


	public RcaEventDetail() {
	}


	@Id
	@Column(name="RCA_ID")
	public int getRcaId() {
		return this.rcaId;
	}

	public void setRcaId(int rcaId) {
		this.rcaId = rcaId;
	}

	@Lob
	public String getChronology() {
		return this.chronology;
	}

	public void setChronology(String chronology) {
		this.chronology = chronology;
	}


	@Column(name="EXECUTIVE_SUMMARY")
	public String getExecutiveSummary() {
		return this.executiveSummary;
	}

	public void setExecutiveSummary(String executiveSummary) {
		this.executiveSummary = executiveSummary;
	}


	@Column(name="HOW_SERVICE_RESTORED")
	public String getHowServiceRestored() {
		return this.howServiceRestored;
	}

	public void setHowServiceRestored(String howServiceRestored) {
		this.howServiceRestored = howServiceRestored;
	}


	@Column(name="IS_DELEGATE_ACCEPTED")
	public String getIsDelegateAccepted() {
		return this.isDelegateAccepted;
	}

	public void setIsDelegateAccepted(String isDelegateAccepted) {
		this.isDelegateAccepted = isDelegateAccepted;
	}


	@Column(name="IS_OWNER_ACCEPTED")
	public String getIsOwnerAccepted() {
		return this.isOwnerAccepted;
	}

	public void setIsOwnerAccepted(String isOwnerAccepted) {
		this.isOwnerAccepted = isOwnerAccepted;
	}


	@Column(name="ISSUE_DESCRIPTION")
	public String getIssueDescription() {
		return this.issueDescription;
	}

	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}


	@Column(name="RCA_COORDINATOR_ID")
	public String getRcaCoordinatorId() {
		return this.rcaCoordinatorId;
	}

	public void setRcaCoordinatorId(String rcaCoordinatorId) {
		this.rcaCoordinatorId = rcaCoordinatorId;
	}


	@Column(name="RCA_DELEGATE")
	public String getRcaDelegate() {
		return this.rcaDelegate;
	}

	public void setRcaDelegate(String rcaDelegate) {
		this.rcaDelegate = rcaDelegate;
	}


	@Column(name="RCA_OWNER")
	public String getRcaOwner() {
		return this.rcaOwner;
	}

	public void setRcaOwner(String rcaOwner) {
		this.rcaOwner = rcaOwner;
	}


	@Column(name="REPEAT_ISSUE")
	public String getRepeatIssue() {
		return this.repeatIssue;
	}

	public void setRepeatIssue(String repeatIssue) {
		this.repeatIssue = repeatIssue;
	}


	@Column(name="REPEAT_RISK")
	public String getRepeatRisk() {
		return this.repeatRisk;
	}

	public void setRepeatRisk(String repeatRisk) {
		this.repeatRisk = repeatRisk;
	}


	@Column(name="SUPPORT_TEAM_EFFECTIVENESS")
	public String getSupportTeamEffectiveness() {
		return this.supportTeamEffectiveness;
	}

	public void setSupportTeamEffectiveness(String supportTeamEffectiveness) {
		this.supportTeamEffectiveness = supportTeamEffectiveness;
	}





	@Column(name="WHY_1")
	public String getWhy1() {
		return this.why1;
	}

	public void setWhy1(String why1) {
		this.why1 = why1;
	}


	@Column(name="WHY_2")
	public String getWhy2() {
		return this.why2;
	}

	public void setWhy2(String why2) {
		this.why2 = why2;
	}


	@Column(name="WHY_3")
	public String getWhy3() {
		return this.why3;
	}

	public void setWhy3(String why3) {
		this.why3 = why3;
	}


	@Column(name="WHY_4")
	public String getWhy4() {
		return this.why4;
	}

	public void setWhy4(String why4) {
		this.why4 = why4;
	}


	@Column(name="WHY_PROBLEM")
	public String getWhyProblem() {
		return this.whyProblem;
	}

	public void setWhyProblem(String whyProblem) {
		this.whyProblem = whyProblem;
	}

	@Column(name = "IMPACTED_TOWER")
	public String getImpactedTower() {
		return impactedTower;
	}

	public void setImpactedTower(String impactedTower) {
		this.impactedTower = impactedTower;
	}

	@Column(name = "EXECUTIVE_ALERT")
	public String getExecutiveAlert() {
		return executiveAlert;
	}

	public void setExecutiveAlert(String executiveAlert) {
		this.executiveAlert = executiveAlert;
	}

	@Column(name = "KEY_ISSUES")
	public String getKeyIssues() {
		return keyIssues;
	}

	public void setKeyIssues(String keyIssues) {
		this.keyIssues = keyIssues;
	}


	//bi-directional one-to-one association to Rca
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="RCA_ID")
	public Rca getRca() {
		return this.rca;
	}

	public void setRca(Rca rca) {
		this.rca = rca;
	}

	@Column(name="REPEAT_RCA_OR_TICKET_NUMBER")
	public String getRepeatRcaOrTicketNum() {
		return repeatRcaOrTicketNum;
	}

	public void setRepeatRcaOrTicketNum(String repeatRcaOrTicketNum) {
		this.repeatRcaOrTicketNum = repeatRcaOrTicketNum;
	}
}
