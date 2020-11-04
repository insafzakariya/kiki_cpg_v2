/**
 * @author Anjana Thrishakya
 * Nov 2, 2020
 * 4:48:17 PM
 */
package org.kiki_cpg_v2.dto;

/**
 *
 */
public class ApplePayDto {

	private Integer viewerId;
	private Integer planId;
	private String receiptString;

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

	public String getReceiptString() {
		return receiptString;
	}

	public void setReceiptString(String receiptString) {
		this.receiptString = receiptString;
	}

	@Override
	public String toString() {
		return "ApplePayDto [viewerId=" + viewerId + ", planId=" + planId + ", receiptString=" + receiptString + "]";
	}

}
