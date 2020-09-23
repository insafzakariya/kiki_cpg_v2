/**
 * @DaJul 29, 2020 @HNBBeginDto.java
 */
package org.kiki_cpg_v2.dto.request;

/**
 * @author Anjana Thrishakya
 */
public class TransactionBeginDto {

	/**
	 * 
	 */
	public TransactionBeginDto() {
	}

	/**
	 * @param mobileNo
	 * @param viewerId
	 * @param planId
	 */
	public TransactionBeginDto(String mobileNo, Integer viewerId, Integer planId) {
		super();
		this.mobileNo = mobileNo;
		this.viewerId = viewerId;
		this.planId = planId;
	}

	private String mobileNo;
	private Integer viewerId;
	private Integer planId;

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
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

	@Override
	public String toString() {
		return "HNBBeginDto [mobileNo=" + mobileNo + ", viewerId=" + viewerId + ", planId=" + planId + "]";
	}

}
