package com.gps.vo;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the SERVICE_DESCRIPTION database table.
 * 
 */
@Entity
@Table(name="SERVICE_DESCRIPTION")
@NamedQuery(name="ServiceDescription.findAll", query="SELECT sd FROM ServiceDescription sd")
public class ServiceDescription implements Serializable {
	/**
	 * 
	 */
	private Integer serviceDescriptionId;
	private String serviceDescriptionName;
	private String serviceDescriptionType;
	private ServiceDescription parent;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SERVICE_DESCRIPTION_ID")
	public Integer getServiceDescriptionId() { 	return this.serviceDescriptionId;}
	public void setServiceDescriptionId(Integer serviceDescriptionId) {
		this.serviceDescriptionId = serviceDescriptionId;
	}


	@Column(name="SERVICE_DESCRIPTION_NAME")
	public String getServiceDescriptionName() {	return serviceDescriptionName;}
	public void setServiceDescriptionName(String serviceDescriptionName) {this.serviceDescriptionName = serviceDescriptionName;}

	@Column(name="SERVICE_DESCRIPTION_TYPE")
	public String getServiceDescriptionType() {	return serviceDescriptionType; 	}
	public void setServiceDescriptionType(String serviceDescriptionType) { this.serviceDescriptionType = serviceDescriptionType; }

	//bi-directional many-to-one association to ServiceDescription
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="SERVICE_DESCRIPTION_PARENT_ID") public ServiceDescription getParent() {	return parent; 	}
	public void setParent(ServiceDescription parent) { 	this.parent = parent; 	}
}