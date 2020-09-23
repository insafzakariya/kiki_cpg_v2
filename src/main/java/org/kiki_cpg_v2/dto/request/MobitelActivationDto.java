package org.kiki_cpg_v2.dto.request;

public class MobitelActivationDto {

	private Integer viewerId;
	private String activationStatus;
	private String mobileNo;
	private Integer subscriptionPaymentId;
	private Integer days;
	private boolean trial;
	private Integer planId;

	public Integer getViewerId() {
		return viewerId;
	}

	public void setViewerId(Integer viewerId) {
		this.viewerId = viewerId;
	}

	public String getActivationStatus() {
		return activationStatus;
	}

	public void setActivationStatus(String activationStatus) {
		this.activationStatus = activationStatus;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Integer getSubscriptionPaymentId() {
		return subscriptionPaymentId;
	}

	public void setSubscriptionPaymentId(Integer subscriptionPaymentId) {
		this.subscriptionPaymentId = subscriptionPaymentId;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public boolean isTrial() {
		return trial;
	}

	public void setTrial(boolean trial) {
		this.trial = trial;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	@Override
	public String toString() {
		return "MobitelActivationDto [viewerId=" + viewerId + ", activationStatus=" + activationStatus + ", mobileNo="
				+ mobileNo + ", subscriptionPaymentId=" + subscriptionPaymentId + ", days=" + days + ", trial=" + trial
				+ ", planId=" + planId + "]";
	}

}
