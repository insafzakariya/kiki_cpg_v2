package org.kiki_cpg_v2.dto;

public class UnsubscribeDto {

	private Integer subscriptionPaymentId;
	private Integer viewerid;
	private Integer type;
	private String mobile;

	public Integer getSubscriptionPaymentId() {
		return subscriptionPaymentId;
	}

	public void setSubscriptionPaymentId(Integer subscriptionPaymentId) {
		this.subscriptionPaymentId = subscriptionPaymentId;
	}

	public Integer getViewerid() {
		return viewerid;
	}

	public void setViewerid(Integer viewerid) {
		this.viewerid = viewerid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public String toString() {
		return "UnsubscribeDto [subscriptionPaymentId=" + subscriptionPaymentId + ", viewerid=" + viewerid + ", type="
				+ type + ", mobile=" + mobile + "]";
	}

}
