package org.kiki_cpg_v2.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "payment_policies")
public class PaymentPolicyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PaymentPolicyID")
	private Integer id;

	@Column(name = "Status")
	private int status;

	@Column(name = "ModifiedBy")
	private Integer modifiedBy;

	@Column(name = "ModifiedOn")
	private Date modifiedOn;

	@Column(name = "PaymentAmount")
	private float paymentAmount;

	@Column(name = "PaymentType")
	private String paymentType;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PaymentMethodID")
	private PaymentMethodEntity paymentMethodEntity;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PolicyID")
	private PolicyEntity policyEntity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public float getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(float paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public PaymentMethodEntity getPaymentMethodEntity() {
		return paymentMethodEntity;
	}

	public void setPaymentMethodEntity(PaymentMethodEntity paymentMethodEntity) {
		this.paymentMethodEntity = paymentMethodEntity;
	}

	public PolicyEntity getPolicyEntity() {
		return policyEntity;
	}

	public void setPolicyEntity(PolicyEntity policyEntity) {
		this.policyEntity = policyEntity;
	}

}
