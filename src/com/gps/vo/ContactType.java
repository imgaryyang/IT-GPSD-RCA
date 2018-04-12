package com.gps.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the CONTACT_TYPE database table.
 * 
 */
@Entity
@Table(name="CONTACT_TYPE")
@NamedQuery(name="ContactType.findAll", query="SELECT c FROM ContactType c")
public class ContactType implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer ctId;
	private String title;
	private List<ContractContact> contractContacts;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CT_ID")
	public Integer getCtId() {
		return this.ctId;
	}

	public void setCtId(Integer ctId) {
		this.ctId = ctId;
	}


	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	//bi-directional many-to-one association to ContractContact
	@OneToMany(mappedBy="contactType")
	public List<ContractContact> getContractContacts() {
		return this.contractContacts;
	}

	public void setContractContacts(List<ContractContact> contractContacts) {
		this.contractContacts = contractContacts;
	}

	public ContractContact addContractContact(ContractContact contractContact) {
		getContractContacts().add(contractContact);
		contractContact.setContactType(this);

		return contractContact;
	}

	public ContractContact removeContractContact(ContractContact contractContact) {
		getContractContacts().remove(contractContact);
		contractContact.setContactType(null);

		return contractContact;
	}

}