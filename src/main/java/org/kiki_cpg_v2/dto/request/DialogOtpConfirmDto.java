package org.kiki_cpg_v2.dto.request;

public class DialogOtpConfirmDto {

	private String pin;
	private String serverRef;
	private Integer subscriptionPaymentId;
	private Integer day;
	private Integer viewerId;
	private Integer planId;
	private boolean trial;

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getServerRef() {
		return serverRef;
	}

	public void setServerRef(String serverRef) {
		this.serverRef = serverRef;
	}

	public Integer getSubscriptionPaymentId() {
		return subscriptionPaymentId;
	}

	public void setSubscriptionPaymentId(Integer subscriptionPaymentId) {
		this.subscriptionPaymentId = subscriptionPaymentId;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getViewerId() {
		return viewerId;
	}

	public void setViewerId(Integer viewerId) {
		this.viewerId = viewerId;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public boolean isTrial() {
		return trial;
	}

	public void setTrial(boolean trial) {
		this.trial = trial;
	}

	@Override
	public String toString() {
		return "DialogOtpConfirmDto [pin=" + pin + ", serverRef=" + serverRef + ", subscriptionPaymentId="
				+ subscriptionPaymentId + ", day=" + day + ", viewerId=" + viewerId + ", planId=" + planId + ", trial="
				+ trial + "]";
	}
}
