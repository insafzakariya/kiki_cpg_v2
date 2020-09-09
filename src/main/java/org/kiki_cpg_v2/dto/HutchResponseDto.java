/**
 * @DaSep 2, 2020 @HutchResponseDto.java
 */
package org.kiki_cpg_v2.dto;

/**
 * @author Anjana Thrishakya
 */
public class HutchResponseDto {
	private String resultCode;
	private String description;
	private String transactionId;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
}
