package com.kiki_cpg.development.dto;

import java.util.Date;

public class PaymentPolicyDto {

	private Integer paymentPolicyId;
	private PaymentMethodDto paymentMethod;
	private PolicyDto policy;
	private int status;
	private Integer modifiedBy;
	private Date modifiedOn;
	private float paymentAmount;
	private String paymentType;

	public Integer getPaymentPolicyId() {
		return paymentPolicyId;
	}

	public void setPaymentPolicyId(Integer paymentPolicyId) {
		this.paymentPolicyId = paymentPolicyId;
	}

	public PaymentMethodDto getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethodDto paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public PolicyDto getPolicy() {
		return policy;
	}

	public void setPolicy(PolicyDto policy) {
		this.policy = policy;
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

}
