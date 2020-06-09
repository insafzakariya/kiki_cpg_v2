package org.kiki_cpg_v2.dto;

public class SubscriptionPaymentDto {

	private Integer subscriptionPaymentId;
	private Integer viewerId;
	private Integer packageId;
	private String tokenHash;
	private Integer status;
	private boolean alreadySubscribed = false;
	private String mobile;
	private String subscriptionType;
	private boolean trialVerify;
	private String ideabizDays;

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

	public boolean isAlreadySubscribed() {
		return alreadySubscribed;
	}

	public void setAlreadySubscribed(boolean alreadySubscribed) {
		this.alreadySubscribed = alreadySubscribed;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public boolean isTrialVerify() {
		return trialVerify;
	}

	public void setTrialVerify(boolean trialVerify) {
		this.trialVerify = trialVerify;
	}

	public String getIdeabizDays() {
		return ideabizDays;
	}

	public void setIdeabizDays(String ideabizDays) {
		this.ideabizDays = ideabizDays;
	}

}