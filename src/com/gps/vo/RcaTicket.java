package com.gps.vo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="RCA_TICKETS")
@NamedQuery(name="RcaTicket.findAll", query="SELECT r FROM RcaTicket r")

public class RcaTicket implements Serializable {
	
	private static final long serialVersionUID = -3224282453658251291L;
	private Integer rcaTicketId;
	private String associatedTool;
	private short severity;
	private String ticketNum;
	private String ticketType;
	private String secondaryTicketType;
	private String otherAssociatedTool;
	private Rca rca;

	public RcaTicket() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RCA_TICKET_ID")
	public Integer getRcaTicketId() {
		return this.rcaTicketId;
	}

	public void setRcaTicketId(Integer rcaTicketId) {
		this.rcaTicketId = rcaTicketId;
	}


	@Column(name="ASSOCIATED_TOOL")
	public String getAssociatedTool() {
		return this.associatedTool;
	}

	public void setAssociatedTool(String associatedTool) {
		this.associatedTool = associatedTool;
	}


	public short getSeverity() {
		return this.severity;
	}

	public void setSeverity(short severity) {
		this.severity = severity;
	}


	@Column(name="TICKET_NUM")
	public String getTicketNum() {
		return this.ticketNum;
	}

	public void setTicketNum(String ticketNum) {
		this.ticketNum = ticketNum;
	}


	@Column(name="TICKET_TYPE")
	public String getTicketType() {
		return this.ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}


	//bi-directional many-to-one association to Rca
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="RCA_ID")
	public Rca getRca() {
		return this.rca;
	}

	public void setRca(Rca rca) {
		this.rca = rca;
	}

	@Column(name = "TYPE")
	public String getSecondaryTicketType() {
		return secondaryTicketType;
	}

	public void setSecondaryTicketType(String secondaryTicketType) {
		this.secondaryTicketType = secondaryTicketType;
	}

	@Column(name = "OTHER_ASSOCIATION_TOOL")
	public String getOtherAssociatedTool() {
		return otherAssociatedTool;
	}

	public void setOtherAssociatedTool(String otherAssociatedTool) {
		this.otherAssociatedTool = otherAssociatedTool;
	}

}
