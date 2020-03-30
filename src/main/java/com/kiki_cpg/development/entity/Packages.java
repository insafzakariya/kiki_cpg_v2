package com.kiki_cpg.development.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "packages")
public class Packages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PackageID")
    private Integer packageId;

    @Column(name = "Name")
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "Status")
    private int status;

    @Column(name = "ActivityStartDate")
    private Date activityStartDate;

    @Column(name = "ActivityEndDate")
    private Date activityEndDate;

    @Column(name = "ModifiedOn")
    private Date modifiedOn;

    @Column(name = "ModifiedBy")
    private int modifiedBy;

    @Column(name = "availableDays")
    private int availableDays;

    @OneToMany(mappedBy = "packages", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PackagePolicies> packagePolicies = new ArrayList<>();

    @OneToMany(mappedBy = "package_viewertypes", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PackageViewertypes> package_viewertypes = new ArrayList<>();
    
    public Packages() {
    }

    public List<PackagePolicies> getPackagePolicies() {
        return packagePolicies;
    }
    

    public List<PackageViewertypes> getPackage_viewertypes() {
		return package_viewertypes;
	}

	public void setPackage_viewertypes(List<PackageViewertypes> package_viewertypes) {
		this.package_viewertypes = package_viewertypes;
	}

	public void setPackagePolicies(List<PackagePolicies> packagePolicies) {
        this.packagePolicies = packagePolicies;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getActivityStartDate() {
        return activityStartDate;
    }

    public void setActivityStartDate(Date activityStartDate) {
        this.activityStartDate = activityStartDate;
    }

    public Date getActivityEndDate() {
        return activityEndDate;
    }

    public void setActivityEndDate(Date activityEndDate) {
        this.activityEndDate = activityEndDate;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public int getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(int modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public int getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(int availableDays) {
        this.availableDays = availableDays;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
