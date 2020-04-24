package com.kiki_cpg.development.dto;

public class NavigationDto {

	private Integer viewerId;
	private Integer subscriptionPaymentId;
	private String navigationUrl;

	public Integer getViewerId() {
		return viewerId;
	}

	public void setViewerId(Integer viewerId) {
		this.viewerId = viewerId;
	}

	public Integer getSubscriptionPaymentId() {
		return subscriptionPaymentId;
	}

	public void setSubscriptionPaymentId(Integer subscriptionPaymentId) {
		this.subscriptionPaymentId = subscriptionPaymentId;
	}

	public String getNavigationUrl() {
		return navigationUrl;
	}

	public void setNavigationUrl(String navigationUrl) {
		this.navigationUrl = navigationUrl;
	}

	@Override
	public String toString() {
		return "NavigationDto [viewerId=" + viewerId + ", subscriptionPaymentId=" + subscriptionPaymentId
				+ ", navigationUrl=" + navigationUrl + "]";
	}

}
