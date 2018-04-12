package com.gps.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the CUSTOMER database table.
 * 
 */
@Entity
@NamedQuery(name="Customer.findAll", query="SELECT c FROM Customer c")
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;
	private int inac;
	private String isEnabled;
	private String name;
	private List<Contract> contracts;

	public Customer() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getInac() {
		return this.inac;
	}

	public void setInac(int inac) {
		this.inac = inac;
	}


	@Column(name="IS_ENABLED")
	public String getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}


	@Column(name="\"NAME\"")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	//bi-directional many-to-one association to Contract
	@OneToMany(mappedBy="customer")
	public List<Contract> getContracts() {
		return this.contracts;
	}

	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}

	public Contract addContract(Contract contract) {
		getContracts().add(contract);
		contract.setCustomer(this);

		return contract;
	}

	public Contract removeContract(Contract contract) {
		getContracts().remove(contract);
		contract.setCustomer(null);

		return contract;
	}

}