/**
 * @DaAug 11, 2020 @HNBVerifyDto.java
 */
package org.kiki_cpg_v2.dto.request;

/**
 * @author Anjana Thrishakya
 */
public class HNBVerifyDto {

	private Integer viewerId;
	private String mobile;
	private String transaction_uuid;
	private String referance_no;
	private Double card_amount;

	
	public Integer getViewerId() {
		return viewerId;
	}

	public void setViewerId(Integer viewerId) {
		this.viewerId = viewerId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTransaction_uuid() {
		return transaction_uuid;
	}

	public void setTransaction_uuid(String transaction_uuid) {
		this.transaction_uuid = transaction_uuid;
	}

	public String getReferance_no() {
		return referance_no;
	}

	public void setReferance_no(String referance_no) {
		this.referance_no = referance_no;
	}

	public Double getCard_amount() {
		return card_amount;
	}

	public void setCard_amount(Double card_amount) {
		this.card_amount = card_amount;
	}
	
	@Override
	public String toString() {
		return "HNBVerifyDto [viewerId=" + viewerId + ", mobile=" + mobile + ", transaction_uuid=" + transaction_uuid
				+ ", referance_no=" + referance_no + ", card_amount=" + card_amount + "]";
	}

}
