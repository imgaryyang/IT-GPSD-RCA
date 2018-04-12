package com.gps.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


/**
 * The persistent class for the GPS_USER database table.
 * 
 */
@Entity
@Table(name="GPS_USER")
@NamedQuery(name="GpsUser.findAll", query="SELECT g FROM GpsUser g")
public class GpsUser implements Serializable {
	private static final long serialVersionUID = 1L;
	private int userId;
	private String email;
    private String notesId;
	private Set<AccessControlList> accessControlLists;
    private Set<UserRole> userRoles;
	
	public GpsUser() {
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USER_ID")
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

    @Column(name="EMAIL")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	//bi-directional many-to-one association to AccessControlList
		@OneToMany(mappedBy="gpsUser", fetch=FetchType.LAZY)
		public Set<AccessControlList> getAccessControlLists() {
			return this.accessControlLists;
		}

		public void setAccessControlLists(Set<AccessControlList> accessControlLists) {
			this.accessControlLists = accessControlLists;
		}


    //bi-directional many-to-one association to AccessControlList
    @OneToMany(mappedBy="gpsUser", fetch=FetchType.LAZY)
    public Set<UserRole> getUserRoles() {
        return this.userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    @Column(name="NOTES_ID")
    public String getNotesId() {
        return notesId;
    }

    public void setNotesId(String notesId) {
        this.notesId = notesId;
    }
}