package com.gps.vo;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the GROUP_GPSUSER database table.
 * 
 */
@Entity
@Table(name="GROUP_GPSUSER")
@NamedQuery(name="GroupGpsuser.findAll", query="SELECT g FROM GroupGpsuser g")
public class GroupGpsuser implements Serializable {
	private static final long serialVersionUID = 1L;
	private int gbuId;
	private int userId;

	public GroupGpsuser() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="GBU_ID")
	public int getGbuId() {
		return this.gbuId;
	}

	public void setGbuId(int gbuId) {
		this.gbuId = gbuId;
	}




	@Column(name="USER_ID")
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}