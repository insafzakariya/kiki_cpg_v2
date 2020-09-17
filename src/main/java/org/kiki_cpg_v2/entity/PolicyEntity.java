package org.kiki_cpg_v2.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "policies")
public class PolicyEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PolicyID")
	private Integer id;

	@Column(name = "Name")
	private String name;

	@Column(name = "PolicyType")
	private Integer policyType;

	@Column(name = "Status")
	private Integer status;

	@Column(name = "ModifiedOn")
	private Date modifiedOn;

	@Column(name = "ModifiedBy")
	private Integer modifiedBy;

	@Column(name = "Description")
	private String description;

	@Column(name = "ValidFrom")
	private Date validFrom;

	@Column(name = "ValidTo")
	private Date validTo;

	@OneToMany(mappedBy = "policyEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<PackagePolicyEntity> packagePolicyEntities = new ArrayList<>();

	@OneToMany(mappedBy = "policyEntity", targetEntity = PaymentPolicyEntity.class)
	private List<PaymentPolicyEntity> paymentPolicyEntities = new ArrayList<>();

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

	public Integer getPolicyType() {
		return policyType;
	}

	public void setPolicyType(Integer policyType) {
		this.policyType = policyType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	public List<PackagePolicyEntity> getPackagePolicyEntities() {
		return packagePolicyEntities;
	}

	public void setPackagePolicyEntities(List<PackagePolicyEntity> packagePolicyEntities) {
		this.packagePolicyEntities = packagePolicyEntities;
	}

	public List<PaymentPolicyEntity> getPaymentPolicyEntities() {
		return paymentPolicyEntities;
	}

	public void setPaymentPolicyEntities(List<PaymentPolicyEntity> paymentPolicyEntities) {
		this.paymentPolicyEntities = paymentPolicyEntities;
	}

}
