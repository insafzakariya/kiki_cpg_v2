package org.kiki_cpg_v2.entity.custom;

public class ViewerSubscriptionCustomEntity {

	private Integer viewerId;
	private Integer viewerSubscriptionId;
	private String mobile;
	private Integer days;
	private Integer subscriprion;
	private String expireDate;

	public Integer getViewerId() {
		return viewerId;
	}

	public void setViewerId(Integer viewerId) {
		this.viewerId = viewerId;
	}

	public Integer getViewerSubscriptionId() {
		return viewerSubscriptionId;
	}

	public void setViewerSubscriptionId(Integer viewerSubscriptionId) {
		this.viewerSubscriptionId = viewerSubscriptionId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Integer getSubscriprion() {
		return subscriprion;
	}

	public void setSubscriprion(Integer subscriprion) {
		this.subscriprion = subscriprion;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

}
