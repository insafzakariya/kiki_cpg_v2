package org.kiki_cpg_v2.dto;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionPaymentDto {

	private Integer subscriptionPaymentId;
	private Integer viewerId;
	private Integer packageId;
	private String tokenHash;
	private Integer status;
	private boolean alreadySubscribed = false;
	private String mobile;
	private List<String> subscriptionTypeList = new ArrayList<String>();
	private String subscriptionType;
	private boolean trialVerify;
	private String ideabizDays;
	private String lastSubscribeDay;
	private String language;

	

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

	public List<String> getSubscriptionTypeList() {
		return subscriptionTypeList;
	}

	public void setSubscriptionTypeList(List<String> subscriptionTypeList) {
		this.subscriptionTypeList = subscriptionTypeList;
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

	public String getLastSubscribeDay() {
		return lastSubscribeDay;
	}

	public void setLastSubscribeDay(String lastSubscribeDay) {
		this.lastSubscribeDay = lastSubscribeDay;
	}
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public String toString() {
		return "SubscriptionPaymentDto [subscriptionPaymentId=" + subscriptionPaymentId + ", viewerId=" + viewerId
				+ ", packageId=" + packageId + ", tokenHash=" + tokenHash + ", status=" + status
				+ ", alreadySubscribed=" + alreadySubscribed + ", mobile=" + mobile + ", subscriptionTypeList="
				+ subscriptionTypeList + ", subscriptionType=" + subscriptionType + ", trialVerify=" + trialVerify
				+ ", ideabizDays=" + ideabizDays + ", lastSubscribeDay=" + lastSubscribeDay + ", language=" + language
				+ "]";
	}

}
