/**
 * @DaSep 2, 2020 @HutchResponseDto.java
 */
package org.kiki_cpg_v2.dto;

/**
 * @author Anjana Thrishakya
 */
public class HutchResponseDto {
	private String transactionId;
	private String prmTransactionId;
	private String responseCode;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getPrmTransactionId() {
		return prmTransactionId;
	}

	public void setPrmTransactionId(String prmTransactionId) {
		this.prmTransactionId = prmTransactionId;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	@Override
	public String toString() {
		return "HutchResponseDto [transactionId=" + transactionId + ", prmTransactionId=" + prmTransactionId
				+ ", responseCode=" + responseCode + "]";
	}

}
