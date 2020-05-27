package org.kiki_cpg_v2.dto;

public class ScratchCardPaymentDto {

	private String code;
	private Integer viewerId;
	private String mobileNo;
	private Integer subscriptionPaymentId;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getViewerId() {
		return viewerId;
	}

	public void setViewerId(Integer viewerId) {
		this.viewerId = viewerId;
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

}
