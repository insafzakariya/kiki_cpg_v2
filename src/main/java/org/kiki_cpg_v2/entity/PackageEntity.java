package org.kiki_cpg_v2.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "packages")
public class PackageEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PackageID")
	private Integer id;

	@Column(name = "Name")
	private String name;

	@Column(name = "Description")
	private String description;

	@Column(name = "Status")
	private Integer status;

	@Column(name = "ActivityStartDate")
	private Date activityStartDate;

	@Column(name = "ActivityEndDate")
	private Date activityEndDate;

	@Column(name = "ModifiedOn")
	private Date modifiedOn;

	@Column(name = "ModifiedBy")
	private int modifiedBy;

	@Column(name = "availableDays")
	private Integer availableDays;

	@OneToMany(mappedBy = "packageEntity", targetEntity = PackagePolicyEntity.class)
	private List<PackagePolicyEntity> packagePoliciesEntities = new ArrayList<>();

	@OneToMany(mappedBy = "packageEntity", targetEntity = PackageViewertypesEntity.class)
	private List<PackageViewertypesEntity> packageViewertypesEntities = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
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

	public Integer getAvailableDays() {
		return availableDays;
	}

	public void setAvailableDays(Integer availableDays) {
		this.availableDays = availableDays;
	}

	public List<PackagePolicyEntity> getPackagePoliciesEntities() {
		return packagePoliciesEntities;
	}

	public void setPackagePoliciesEntities(List<PackagePolicyEntity> packagePoliciesEntities) {
		this.packagePoliciesEntities = packagePoliciesEntities;
	}

	public List<PackageViewertypesEntity> getPackageViewertypesEntities() {
		return packageViewertypesEntities;
	}

	public void setPackageViewertypesEntities(List<PackageViewertypesEntity> packageViewertypesEntities) {
		this.packageViewertypesEntities = packageViewertypesEntities;
	}

}
