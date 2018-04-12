package com.gps.vo;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the RCA_EDITORS database table.
 * 
 */
@Entity
@Table(name="RCA_EDITORS")
@NamedQuery(name="RcaEditor.findAll", query="SELECT r FROM RcaEditor r")
public class RcaEditor implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2835126049705483094L;
	private Integer rcaEditorId;
    private GpsUser gpsUser;
    private Rca rca;


	public RcaEditor() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RCA_EDITOR_ID")
	public Integer getRcaEditorId() {
		return this.rcaEditorId;
	}

	public void setRcaEditorId(Integer rcaEditorId) {
		this.rcaEditorId = rcaEditorId;
	}

    /**
     * bi-directional many-to-one association to BpdUser
     * @return the gpsUser
     */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="RCA_EDITOR_USER_ID")
    public GpsUser getGpsUser() {
        return gpsUser;
    }

    public void setGpsUser(GpsUser gpsUser) {
        this.gpsUser = gpsUser;
    }

    //bi-directional many-to-one association to Rca
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="RCA_ID")
    public Rca getRca() {
        return rca;
    }

    public void setRca(Rca rca) {
        this.rca = rca;
    }
}