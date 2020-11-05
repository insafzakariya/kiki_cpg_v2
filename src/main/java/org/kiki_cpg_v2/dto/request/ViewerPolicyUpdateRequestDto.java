package org.kiki_cpg_v2.dto.request;

public class ViewerPolicyUpdateRequestDto {

	@Override
	public String toString() {
		return "ViewerPolicyUpdateRequestDto [viewerId=" + viewerId + ", packageId=" + packageId + "]";
	}

	private Integer viewerId;
	private Integer packageId;

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

}
