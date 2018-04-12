package com.gps.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the USER_ROLES database table.
 * 
 */
@Entity
@Table(name="USER_ROLES")
@NamedQuery(name="UserRole.findAll", query="SELECT u FROM UserRole u")
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer urId;
    private GpsUser gpsUser;
    private Contract contract;
	private String role;
	private String isEnabled;
	private Timestamp disabledOn;
	private Integer disabledBy;
	
	public UserRole() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="UR_ID")
	public Integer getUrId() {
		return this.urId;
	}

	public void setUrId(Integer urId) {
		this.urId = urId;
	}


    /**
     * bi-directional many-to-one association to BpdUser
     * @return the gpsUser
     */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="UR_USER_ID")
    public GpsUser getGpsUser() {
        return gpsUser;
    }

    /**
     * @param user the gpsUser to set
     */
    public void setGpsUser(GpsUser user) {
        this.gpsUser = user;
    }


    //bi-directional many-to-one association to Contract
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="UR_CONTRACT_ID")
    public Contract getContract() {
        return this.contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }


	@Column(name="ROLE")
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Column(name="IS_ENABLED", length=1)
	public String getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Column(name="DISABLED_ON")
	public Timestamp getDisabledOn() {
		return disabledOn;
	}


	public void setDisabledOn(Timestamp disabledOn) {
		this.disabledOn = disabledOn;
	}

	@Column(name="DISABLED_BY")
	public Integer getDisabledBy() {
		return disabledBy;
	}


	public void setDisabledBy(Integer disabledBy) {
		this.disabledBy = disabledBy;
	}
	
	
	
}