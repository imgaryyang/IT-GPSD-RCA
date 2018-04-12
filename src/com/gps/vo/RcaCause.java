package com.gps.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "RCA_CAUSE")
@NamedQuery(name = "RcaCause.findAll", query = "SELECT r FROM RcaCause r")
public class RcaCause implements Serializable {
    private static final long serialVersionUID = 1L;
    private int rcaCauseId;
    private String serviceProvider;
    private String otherServiceProvider;
    private String outageCategory;
    private String outageSubCategory;
    private String outageSubCategory2;
    private String locationOfBusinessImpact;
    private String otherLocOfBusinessImpact;
    private String rootOrContributingCause;
    private String causeCategory;
    private String causeSubCategory;
    private String locOfSystem;
    private String causeSummary;
    private String causeSelectionGuidance;
    private String otherOutageSubCat2;
    private String isPrimary;
    private Rca rca;

    //Transient//
    
    private List<ServiceDescription> outageSubCategories;
    private List<ServiceDescription> outageSubCategories2;
    private List<CauseDescription> causeCategories;
    private List<CauseDescription> causeSubCategories;


    public RcaCause() {
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RCA_CAUSE_ID")
    public int getRcaCauseId() {
        return this.rcaCauseId;
    }

    public void setRcaCauseId(int rcaCauseId) {
        this.rcaCauseId = rcaCauseId;
    }


    @Column(name = "IS_PRIMARY")
    public String getIsPrimary() {
        return this.isPrimary;
    }

    public void setIsPrimary(String isPrimary) {
        this.isPrimary = isPrimary;
    }
    
//    @Column(name = "OTHER_OUTAGE_SUB_CAT_2", nullable=true)
    @Transient
    public String getOtherOutageSubCat2() {
		return otherOutageSubCat2;
	}


	public void setOtherOutageSubCat2(String otherOutageSubCat2) {
		this.otherOutageSubCat2 = otherOutageSubCat2;
	}

    //bi-directional many-to-one association to Rca
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RCA_ID")
    public Rca getRca() {
        return this.rca;
    }

    public void setRca(Rca rca) {
        this.rca = rca;
    }

    @Column(name = "CAUSE_SUMMARY")
    public String getCauseSummary() {
        return causeSummary;
    }

    public void setCauseSummary(String causeSummary) {
        this.causeSummary = causeSummary;
    }


    @Column(name = "LOC_OF_BUSINESS_IMPACT")
    public String getLocationOfBusinessImpact() {
        return locationOfBusinessImpact;
    }

    public void setLocationOfBusinessImpact(String locationOfBusinessImpact) {
        this.locationOfBusinessImpact = locationOfBusinessImpact;
    }

    @Column(name = "OTHER_LOC_OF_BUSINESS_IMPACT")
    public String getOtherLocOfBusinessImpact() {
        return otherLocOfBusinessImpact;
    }

    public void setOtherLocOfBusinessImpact(String otherLocOfBusinessImpact) {
        this.otherLocOfBusinessImpact = otherLocOfBusinessImpact;
    }

    @Column(name = "SERVICE_PROVIDER")
    public String getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Column(name = "OTHER_SERVICE_PROVIDER")
    public String getOtherServiceProvider() {
        return otherServiceProvider;
    }

    public void setOtherServiceProvider(String otherServiceProvider) {
        this.otherServiceProvider = otherServiceProvider;
    }

    @Column(name = "OUTAGE_CATEGORY")
    public String getOutageCategory() {
        return outageCategory;
    }

    public void setOutageCategory(String outageCategory) {
        this.outageCategory = outageCategory;
    }

    @Column(name = "OUTAGE_SUB_CATEGORY")
    public String getOutageSubCategory() {
        return outageSubCategory;
    }

    public void setOutageSubCategory(String outageSubCategory) {
        this.outageSubCategory = outageSubCategory;
    }

    @Column(name = "OUTAGE_SUB_CATEGORY_2")
    public String getOutageSubCategory2() {
        return outageSubCategory2;
    }

    public void setOutageSubCategory2(String outageSubCategory2) {
        this.outageSubCategory2 = outageSubCategory2;
    }

    @Column(name = "ROOT_CAUSE")
    public String getRootOrContributingCause() {
        return rootOrContributingCause;
    }

    public void setRootOrContributingCause(String rootOrContributingCause) {
        this.rootOrContributingCause = rootOrContributingCause;
    }

    @Column(name = "CAUSE_CATEGORY")
    public String getCauseCategory() {
        return causeCategory;
    }

    public void setCauseCategory(String causeCategory) {
        this.causeCategory = causeCategory;
    }

    @Column(name = "CAUSE_SUB_CATEGORY")
    public String getCauseSubCategory() {
        return causeSubCategory;
    }

    public void setCauseSubCategory(String causeSubCategory) {
        this.causeSubCategory = causeSubCategory;
    }

    @Column(name = "LOC_OF_SYSTEM")
    public String getLocOfSystem() {
        return locOfSystem;
    }

    public void setLocOfSystem(String locOfSystem) {
        this.locOfSystem = locOfSystem;
    }

    @Column(name = "CAUSE_SELECTION_GUIDANCE")
    public String getCauseSelectionGuidance() {
        return causeSelectionGuidance;
    }

    public void setCauseSelectionGuidance(String causeSelectionGuidance) {
        this.causeSelectionGuidance = causeSelectionGuidance;
    }

    @Transient
    public List<ServiceDescription> getOutageSubCategories2() {
        return outageSubCategories2;
    }

    public void setOutageSubCategories2(List<ServiceDescription> outageSubCategories2) {
        this.outageSubCategories2 = outageSubCategories2;
    }

    @Transient
    public List<ServiceDescription> getOutageSubCategories() {
        return outageSubCategories;
    }

    public void setOutageSubCategories(List<ServiceDescription> outageSubCategories) {
        this.outageSubCategories = outageSubCategories;
    }

    @Transient
    public List<CauseDescription> getCauseCategories() {
        return causeCategories;
    }

    public void setCauseCategories(List<CauseDescription> causeCategories) {
        this.causeCategories = causeCategories;
    }

    @Transient
    public List<CauseDescription> getCauseSubCategories() {
        return causeSubCategories;
    }

    public void setCauseSubCategories(List<CauseDescription> causeSubCategories) {
        this.causeSubCategories = causeSubCategories;
    }


	

}
