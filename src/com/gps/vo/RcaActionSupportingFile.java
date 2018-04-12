package com.gps.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the WEEKLY_FILE database table.
 * 
 */
@Entity
@Table(name="RCA_ACTION_SUPPORTING_FILES")
public class RcaActionSupportingFile implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer fileId;
	private byte[] contents;
	private String mime;
	private String name;
	private Timestamp uploadedOn;
	private long size;
	private String type;
    private String description;
	private GpsUser uploadedBy;
    private RcaAction rcaAction;
	private String isDeleted="N";

    // Transient
    private String uploadedTime;


	public RcaActionSupportingFile() {
	}


	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="FILE_ID", unique=true, nullable=false)
	public Integer getFileId() {
		return this.fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}


	@Lob
	@Basic(fetch= FetchType.LAZY)
	public byte[] getContents() {
		return this.contents;
	}

	public void setContents(byte[] contents) {
		this.contents = contents;
	}


	@Column(length=40)
	public String getMime() {
		return this.mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}


	@Column(length=200)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Column(name="UPLOADED_ON")
	public Timestamp getUploadedOn() {
		return this.uploadedOn;
	}

	public void setUploadedOn(Timestamp uploadedOn) {
		this.uploadedOn = uploadedOn;
	}


	@Column(name="\"SIZE\"")
	public long getSize() {
		return this.size;
	}

	public void setSize(long l) {
		this.size = l;
	}

    @Column(name="DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


	@Column(name="\"TYPE\"", length=10)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Transient
	public String getIsDeleted() {
		return isDeleted;
	}


	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

    //bi-directional many-to-one association to rca
    @ManyToOne(cascade= CascadeType.DETACH)
    @JoinColumn(name="UPLOADED_BY")
    public GpsUser getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(GpsUser uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    //bi-directional many-to-one association to RcaAction
    @ManyToOne(cascade= CascadeType.DETACH)
    @JoinColumn(name="RCA_ACTION_ID")
    public RcaAction getRcaAction() {
        return rcaAction;
    }

    public void setRcaAction(RcaAction rcaAction) {
        this.rcaAction = rcaAction;
    }

    @Transient
    public String getUploadedTime() {
        return uploadedTime;
    }

    public void setUploadedTime(String uploadedTime) {
        this.uploadedTime = uploadedTime;
    }


}