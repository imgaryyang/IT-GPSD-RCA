package com.gps.vo;

import javax.persistence.*;
import java.io.Serializable;

/*
 * The persistent class for the RCA_EVENT_DEATILS database table.
*/


@Entity
@Table(name="RCA_PREVENTION_DETAILS")
@NamedQuery(name="RcaPreventionDetail.findAll", query="SELECT r FROM RcaPreventionDetail r")
public class RcaPreventionDetail implements Serializable {

	private Integer rcaId;
	private String futurePrevention;
	private String newPoliciesImplemented;
	private String comments;

	private Rca rca;


	public RcaPreventionDetail() {
	}


	@Id
	@Column(name="RCA_ID")
	public Integer getRcaId() {
		return this.rcaId;
	}

	public void setRcaId(Integer rcaId) {
		this.rcaId = rcaId;
	}


	@Column(name = "FUTURE_PREVENTION")
	public String getFuturePrevention() {
		return futurePrevention;
	}

	public void setFuturePrevention(String futurePrevention) {
		this.futurePrevention = futurePrevention;
	}

	@Column(name = "NEW_POLICIES_IMPLEMENTED")
	public String getNewPoliciesImplemented() {
		return newPoliciesImplemented;
	}

	public void setNewPoliciesImplemented(String newPoliciesImplemented) {
		this.newPoliciesImplemented = newPoliciesImplemented;
	}

	@Column(name = "COMMENTS")
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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


}
