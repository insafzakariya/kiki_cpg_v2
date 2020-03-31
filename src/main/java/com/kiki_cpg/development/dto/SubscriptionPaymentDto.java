package com.kiki_cpg.development.dto;

import java.util.Date;
import java.util.List;

public class SubscriptionPaymentDto {

	private Integer subscriptionPaymentId;
	private Integer viewerId;
	private Integer packageId;
	private String tokenHash;
	private Integer status;
	private Date createdDate;
	private Date expireDate;
	
	List<PaymentPolicyDto> paymentMethods;

	public Integer getSubscriptionPaymentId() {
		return subscriptionPaymentId;
	}

	public void setSubscriptionPaymentId(Integer subscriptionPaymentId) {
		this.subscriptionPaymentId = subscriptionPaymentId;
	}

	public Integer getViewerId() {
		return viewerId;
	}

	public void setViewerId(Integer viewerId) {
		this.viewerId = viewerId;
	}

	public Integer getPackageId() {
		return packageId;
	}

	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}

	public String getTokenHash() {
		return tokenHash;
	}

	public void setTokenHash(String tokenHash) {
		this.tokenHash = tokenHash;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public List<PaymentPolicyDto> getPaymentMethods() {
		return paymentMethods;
	}

	public void setPaymentMethods(List<PaymentPolicyDto> paymentMethods) {
		this.paymentMethods = paymentMethods;
	}

}
