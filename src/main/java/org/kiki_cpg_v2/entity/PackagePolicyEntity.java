package org.kiki_cpg_v2.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "package_policies")
public class PackagePolicyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RowID")
	private Integer id;

	@Column(name = "ModifiedOn")
	private Date modifiedOn;

	@Column(name = "ModifiedBy")
	private Integer modifiedBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PolicyID")
	private PolicyEntity policyEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PackageID")
	private PackageEntity packageEntity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public PolicyEntity getPolicyEntity() {
		return policyEntity;
	}

	public void setPolicyEntity(PolicyEntity policyEntity) {
		this.policyEntity = policyEntity;
	}

	public PackageEntity getPackageEntity() {
		return packageEntity;
	}

	public void setPackageEntity(PackageEntity packageEntity) {
		this.packageEntity = packageEntity;
	}

}
