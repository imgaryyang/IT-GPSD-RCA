package com.gps.vo;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the SERVICE_DESCRIPTION database table.
 * 
 */
@Entity
@Table(name="CAUSE_DESCRIPTION")
@NamedQuery(name="CauseDescription.findAll", query="SELECT cd FROM CauseDescription cd")
public class CauseDescription implements Serializable {
	/**
	 * 
	 */
	private Integer causeDescriptionId;
	private String causeDescriptionName;
	private String causeDescriptionType;
	private CauseDescription parent;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CAUSE_DESCRIPTION_ID")
	public Integer getCauseDescriptionId() { 	return this.causeDescriptionId;}
	public void setCauseDescriptionId(Integer causeDescriptionId) {
		this.causeDescriptionId = causeDescriptionId;
	}


	@Column(name="CAUSE_DESCRIPTION_NAME")
	public String getCauseDescriptionName() {	return causeDescriptionName;}
	public void setCauseDescriptionName(String causeDescriptionName) {this.causeDescriptionName = causeDescriptionName;}

	@Column(name="CAUSE_DESCRIPTION_TYPE")
	public String getCauseDescriptionType() {	return causeDescriptionType; 	}
	public void setCauseDescriptionType(String causeDescriptionType) { this.causeDescriptionType = causeDescriptionType; }

	//bi-directional many-to-one association to ServiceDescription
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CAUSE_DESCRIPTION_PARENT_ID") public CauseDescription getParent() {	return parent; 	}
	public void setParent(CauseDescription parent) { 	this.parent = parent; 	}
}