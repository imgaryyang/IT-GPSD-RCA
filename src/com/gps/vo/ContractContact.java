package com.gps.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the CONTRACT_CONTACT database table.
 * 
 */
@Entity
@Table(name="CONTRACT_CONTACT")
@NamedQuery(name="ContractContact.findAll", query="SELECT c FROM ContractContact c")
public class ContractContact implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer contractContactId;
	private String contactIntranetId;
	private String contactName;
	private String contractPhone;
	private List<Contract> contracts;
	private ContactType contactType;
	private Contract contract;

	public ContractContact() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CONTRACT_CONTACT_ID")
	public Integer getContractContactId() {
		return this.contractContactId;
	}

	public void setContractContactId(Integer contractContactId) {
		this.contractContactId = contractContactId;
	}


	@Column(name="CONTACT_INTRANETID")
	public String getContactIntranetId() {
		return this.contactIntranetId;
	}

	public void setContactIntranetId(String contactIntranetId) {
		this.contactIntranetId = contactIntranetId;
	}


	@Column(name="CONTACT_NAME")
	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}


	@Column(name="CONTRACT_PHONE")
	public String getContractPhone() {
		return this.contractPhone;
	}

	public void setContractPhone(String contractPhone) {
		this.contractPhone = contractPhone;
	}


	//bi-directional many-to-one association to Contract
	@OneToMany(mappedBy="contractContact")
	public List<Contract> getContracts() {
		return this.contracts;
	}

	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}

	public Contract addContract(Contract contract) {
		getContracts().add(contract);
		contract.setContractContact(this);

		return contract;
	}

	public Contract removeContract(Contract contract) {
		getContracts().remove(contract);
		contract.setContractContact(null);

		return contract;
	}


	//bi-directional many-to-one association to ContactType
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CT_ID")
	public ContactType getContactType() {
		return this.contactType;
	}

	public void setContactType(ContactType contactType) {
		this.contactType = contactType;
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

}