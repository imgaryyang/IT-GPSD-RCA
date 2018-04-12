package com.gps.vo;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the ACESS_LEVEL database table.
 * 
 */
@Entity
@Table(name="ACESS_LEVEL")
@NamedQuery(name="AcessLevel.findAll", query="SELECT a FROM AcessLevel a")
public class AcessLevel implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer acessLevelId;
	private String isEnabled;
	private String name;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ACESS_LEVEL_ID")
	public Integer getAcessLevelId() {
		return this.acessLevelId;
	}

	public void setAcessLevelId(Integer acessLevelId) {
		this.acessLevelId = acessLevelId;
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

}