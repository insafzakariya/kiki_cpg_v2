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
	private String mobile;
	private boolean isMobitel;
	private String mCashPaymentURL;
	private boolean trialVerify;
	private String ideabizSubscription;
	private String mobitelSubscription;
	private String viewerSubscription;
	private boolean alreadySubscribed = false;
	private Double ideabizAmount;
	private String ideabizDays;
	private boolean mobitelSubscribe = false;
	private boolean mobitelConnection = false;
	
	
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public boolean isMobitel() {
		return isMobitel;
	}

	public void setMobitel(boolean isMobitel) {
		this.isMobitel = isMobitel;
	}

	public String getmCashPaymentURL() {
		return mCashPaymentURL;
	}

	public void setmCashPaymentURL(String mCashPaymentURL) {
		this.mCashPaymentURL = mCashPaymentURL;
	}

	public boolean isTrialVerify() {
		return trialVerify;
	}

	public void setTrialVerify(boolean trialVerify) {
		this.trialVerify = trialVerify;
	}

	public String getIdeabizSubscription() {
		return ideabizSubscription;
	}

	public void setIdeabizSubscription(String ideabizSubscription) {
		this.ideabizSubscription = ideabizSubscription;
	}

	public String getMobitelSubscription() {
		return mobitelSubscription;
	}

	public void setMobitelSubscription(String mobitelSubscription) {
		this.mobitelSubscription = mobitelSubscription;
	}

	public String getViewerSubscription() {
		return viewerSubscription;
	}

	public void setViewerSubscription(String viewerSubscription) {
		this.viewerSubscription = viewerSubscription;
	}

	public boolean isAlreadySubscribed() {
		return alreadySubscribed;
	}

	public void setAlreadySubscribed(boolean alreadySubscribed) {
		this.alreadySubscribed = alreadySubscribed;
	}

	public Double getIdeabizAmount() {
		return ideabizAmount;
	}

	public void setIdeabizAmount(Double ideabizAmount) {
		this.ideabizAmount = ideabizAmount;
	}

	public String getIdeabizDays() {
		return ideabizDays;
	}

	public void setIdeabizDays(String ideabizDays) {
		this.ideabizDays = ideabizDays;
	}

	public boolean isMobitelSubscribe() {
		return mobitelSubscribe;
	}

	public void setMobitelSubscribe(boolean mobitelSubscribe) {
		this.mobitelSubscribe = mobitelSubscribe;
	}

	public boolean isMobitelConnection() {
		return mobitelConnection;
	}

	public void setMobitelConnection(boolean mobitelConnection) {
		this.mobitelConnection = mobitelConnection;
	}

	@Override
	public String toString() {
		return "SubscriptionPaymentDto [subscriptionPaymentId=" + subscriptionPaymentId + ", viewerId=" + viewerId
				+ ", packageId=" + packageId + ", tokenHash=" + tokenHash + ", status=" + status + ", createdDate="
				+ createdDate + ", expireDate=" + expireDate + ", mobile=" + mobile + ", isMobitel=" + isMobitel
				+ ", mCashPaymentURL=" + mCashPaymentURL + ", trialVerify=" + trialVerify + ", ideabizSubscription="
				+ ideabizSubscription + ", mobitelSubscription=" + mobitelSubscription + ", viewerSubscription="
				+ viewerSubscription + ", alreadySubscribed=" + alreadySubscribed + ", ideabizAmount=" + ideabizAmount
				+ ", ideabizDays=" + ideabizDays + ", mobitelSubscribe=" + mobitelSubscribe + ", mobitelConnection="
				+ mobitelConnection + ", paymentMethods=" + paymentMethods + "]";
	}

}
