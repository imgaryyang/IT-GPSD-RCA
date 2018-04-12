package com.gps.vo;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the RCA_COORDINATOR database table.
 * 
 */
@Entity
@Table(name="RCA_COORDINATOR")
@NamedQuery(name="RcaCoordinator.findAll", query="SELECT r FROM RcaCoordinator r")
public class RcaCoordinator implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3052065767557813623L;
	private Integer rcaCoordinatorId;
	private Contract contract;
	private Coordinator coordinator;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RCA_COORDINATOR_ID")
	public Integer getRcaCoordinatorId() {
		return this.rcaCoordinatorId;
	}

	public void setRcaCoordinatorId(Integer rcaCoordinatorId) {
		this.rcaCoordinatorId = rcaCoordinatorId;
	}

	//bi-directional many-to-one association to Contract
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CONTRACT_ID")
	public Contract getContract() {
		return this.contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}


	//bi-directional many-to-one association to Coordinator
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="COORDINATOR_ID")
	public Coordinator getCoordinator() {
		return this.coordinator;
	}

	public void setCoordinator(Coordinator coordinator) {
		this.coordinator = coordinator;
	}

}