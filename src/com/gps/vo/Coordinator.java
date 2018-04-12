package com.gps.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the "COORDINATOR" database table.
 * 
 */
@Entity
@Table(name="\"COORDINATOR\"")
@NamedQuery(name="Coordinator.findAll", query="SELECT c FROM Coordinator c")
public class Coordinator implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7727925957863696564L;
	
	private Integer coordinatorId;
	private String coordinatorName;
	private String intranetId;
	private String phone;
	private List<RcaCoordinator> rcaCoordinators;

    //Transient
    private String notesId;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="COORDINATOR_ID")
	public Integer getCoordinatorId() {
		return this.coordinatorId;
	}

	public void setCoordinatorId(Integer coordinatorId) {
		this.coordinatorId = coordinatorId;
	}


	@Column(name="COORDINATOR_NAME")
	public String getCoordinatorName() {
		return this.coordinatorName;
	}

	public void setCoordinatorName(String coordinatorName) {
		this.coordinatorName = coordinatorName;
	}


	@Column(name="INTRANET_ID")
	public String getIntranetId() {
		return this.intranetId;
	}

	public void setIntranetId(String intranetId) {
		this.intranetId = intranetId;
	}


	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


	//bi-directional many-to-one association to RcaCoordinator
	@OneToMany(mappedBy="coordinator")
	public List<RcaCoordinator> getRcaCoordinators() {
		return this.rcaCoordinators;
	}

	public void setRcaCoordinators(List<RcaCoordinator> rcaCoordinators) {
		this.rcaCoordinators = rcaCoordinators;
	}

    @Transient
    public String getNotesId() {
        return notesId;
    }

    public void setNotesId(String notesId) {
        this.notesId = notesId;
    }
}