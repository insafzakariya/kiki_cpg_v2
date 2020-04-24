package com.kiki_cpg.development.dto;

public class DialogOtpConfirmDto {

	private String pin;
	private String serverRef;
	private Integer subscriptionPaymentId;
	private Integer day;

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
}
