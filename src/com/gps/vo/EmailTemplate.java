package com.gps.vo;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the EMAIL_TEMPLATE database table.
 * 
 */
@Entity
@Table(name="EMAIL_TEMPLATE")
@NamedQuery(name="EmailTemplate.findAll", query="SELECT e FROM EmailTemplate e")
public class EmailTemplate implements Serializable {
	private static final long serialVersionUID = 1L;
	private int emailTemplateId;
	private String bccEmail;
	private String body;
	private String ccEmail;
	private String emailTemplateName;
	private String formType;
	private String fromAlias;
	private short fromStatus;
	private String subject;
	private String toEmail;
	private short toStatus;

	public EmailTemplate() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="EMAIL_TEMPLATE_ID")
	public int getEmailTemplateId() {
		return this.emailTemplateId;
	}

	public void setEmailTemplateId(int emailTemplateId) {
		this.emailTemplateId = emailTemplateId;
	}


	@Column(name="BCC_EMAIL")
	public String getBccEmail() {
		return this.bccEmail;
	}

	public void setBccEmail(String bccEmail) {
		this.bccEmail = bccEmail;
	}


	@Column(name="\"BODY\"")
	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}


	@Column(name="CC_EMAIL")
	public String getCcEmail() {
		return this.ccEmail;
	}

	public void setCcEmail(String ccEmail) {
		this.ccEmail = ccEmail;
	}


	@Column(name="EMAIL_TEMPLATE_NAME")
	public String getEmailTemplateName() {
		return this.emailTemplateName;
	}

	public void setEmailTemplateName(String emailTemplateName) {
		this.emailTemplateName = emailTemplateName;
	}


	@Column(name="FORM_TYPE")
	public String getFormType() {
		return this.formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}


	@Column(name="FROM_ALIAS")
	public String getFromAlias() {
		return this.fromAlias;
	}

	public void setFromAlias(String fromAlias) {
		this.fromAlias = fromAlias;
	}


	@Column(name="FROM_STATUS")
	public short getFromStatus() {
		return this.fromStatus;
	}

	public void setFromStatus(short fromStatus) {
		this.fromStatus = fromStatus;
	}


	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}


	@Column(name="TO_EMAIL")
	public String getToEmail() {
		return this.toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}


	@Column(name="TO_STATUS")
	public short getToStatus() {
		return this.toStatus;
	}

	public void setToStatus(short toStatus) {
		this.toStatus = toStatus;
	}

}