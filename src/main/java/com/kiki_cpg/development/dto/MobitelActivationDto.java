package com.kiki_cpg.development.dto;

public class MobitelActivationDto {

	private Integer viewerId;
	private String activationStatus;
	private String mobileNo;

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

	@Override
	public String toString() {
		return "MobitelActivationDto [viewerId=" + viewerId + ", activationStatus=" + activationStatus + ", mobileNo="
				+ mobileNo + "]";
	}

}
