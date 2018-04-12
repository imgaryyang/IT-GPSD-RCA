package com.gps.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the SESSION_ACL database table.
 */
@Entity
@Table(name = "SESSION_ACL")
@NamedQuery(name = "SessionAcl.findAll", query = "SELECT s FROM SessionAcl s")
public class SessionAcl implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3880551072554116755L;
    private Integer sessionAclId;
    private int activeAccessLevel;
    private int approvedAccessLevel;
    private Contract contract;
    private String formType;
    private String sessionId;
    private GpsUser gpsUser;
    private String role;
    private Timestamp signInTime;

    public SessionAcl() {
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SESSION_ACL_ID")
    public Integer getSessionAclId() {
        return this.sessionAclId;
    }

    public void setSessionAclId(Integer sessionAclId) {
        this.sessionAclId = sessionAclId;
    }


    @Column(name = "ACTIVE_ACCESS_LEVEL")
    public int getActiveAccessLevel() {
        return this.activeAccessLevel;
    }

    public void setActiveAccessLevel(int activeAccessLevel) {
        this.activeAccessLevel = activeAccessLevel;
    }


    @Column(name = "APPROVED_ACCESS_LEVEL")
    public int getApprovedAccessLevel() {
        return this.approvedAccessLevel;
    }

    public void setApprovedAccessLevel(int approvedAccessLevel) {
        this.approvedAccessLevel = approvedAccessLevel;
    }


    //bi-directional many-to-one association to Contract
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTRACT_ID")
    public Contract getContract() {
        return this.contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }


    @Column(name = "FORM_TYPE")
    public String getFormType() {
        return this.formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }


    @Column(name = "SESSION_ID")
    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    //bi-directional many-to-one association to Contract
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    public GpsUser getGpsUser() {
        return gpsUser;
    }

    public void setGpsUser(GpsUser gpsUser) {
        this.gpsUser = gpsUser;
    }


    @Column(name = "SIGN_IN_TIME")
    public Timestamp getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(Timestamp signInTime) {
        this.signInTime = signInTime;
    }

    @Column(name = "ROLE")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}