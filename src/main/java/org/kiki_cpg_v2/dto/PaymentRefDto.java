/**
 * @DaJul 29, 2020 @PaymentRefDto.java
 */
package org.kiki_cpg_v2.dto;

/**
 * @author Anjana Thrishakya
 */
public class PaymentRefDto {

	private String transactionUUID;
	private String referanceNo;
	private Double amount;
	private Integer days;
	private String status;
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTransactionUUID() {
		return transactionUUID;
	}

	public void setTransactionUUID(String transactionUUID) {
		this.transactionUUID = transactionUUID;
	}

	public String getReferanceNo() {
		return referanceNo;
	}

	public void setReferanceNo(String referanceNo) {
		this.referanceNo = referanceNo;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

}
