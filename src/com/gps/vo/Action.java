package com.gps.vo;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the "ACTION" database table.
 * 
 */
@Entity
@Table(name="\"ACTION\"")
@NamedQuery(name="Action.findAll", query="SELECT a FROM Action a")
public class Action implements Serializable {
	private static final long serialVersionUID = 1L;
	private int actionId;
	private String bccEmail;
	private String ccEmail;
	private int contractId;
	private int emailTemplateId;
	private String formType;
	private String isHighPriority;
	private String qualifier;
	private String stateChangeCode;
	private String toEmail;

	public Action() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ACTION_ID")
	public int getActionId() {
		return this.actionId;
	}

	public void setActionId(int actionId) {
		this.actionId = actionId;
	}


	@Column(name="BCC_EMAIL")
	public String getBccEmail() {
		return this.bccEmail;
	}

	public void setBccEmail(String bccEmail) {
		this.bccEmail = bccEmail;
	}


	@Column(name="CC_EMAIL")
	public String getCcEmail() {
		return this.ccEmail;
	}

	public void setCcEmail(String ccEmail) {
		this.ccEmail = ccEmail;
	}


	@Column(name="CONTRACT_ID")
	public int getContractId() {
		return this.contractId;
	}

	public void setContractId(int contractId) {
		this.contractId = contractId;
	}


	@Column(name="EMAIL_TEMPLATE_ID")
	public int getEmailTemplateId() {
		return this.emailTemplateId;
	}

	public void setEmailTemplateId(int emailTemplateId) {
		this.emailTemplateId = emailTemplateId;
	}


	@Column(name="FORM_TYPE")
	public String getFormType() {
		return this.formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}


	@Column(name="IS_HIGH_PRIORITY")
	public String getIsHighPriority() {
		return this.isHighPriority;
	}

	public void setIsHighPriority(String isHighPriority) {
		this.isHighPriority = isHighPriority;
	}


	public String getQualifier() {
		return this.qualifier;
	}

	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}


	@Column(name="STATE_CHANGE_CODE")
	public String getStateChangeCode() {
		return this.stateChangeCode;
	}

	public void setStateChangeCode(String stateChangeCode) {
		this.stateChangeCode = stateChangeCode;
	}


	@Column(name="TO_EMAIL")
	public String getToEmail() {
		return this.toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

}