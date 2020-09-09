/**
 * @DaSep 7, 2020 @HutchSubscribeDto.java
 */
package org.kiki_cpg_v2.dto.request;

/**
 * @author Anjana Thrishakya
 */
public class HutchSubscribeDto {

	private String msisdn;
	private String transactionId;
	private String offerCode;

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getOfferCode() {
		return offerCode;
	}

	public void setOfferCode(String offerCode) {
		this.offerCode = offerCode;
	}

	@Override
	public String toString() {
		return "HutchSubscribeDto [msisdn=" + msisdn + ", transactionId=" + transactionId + ", offerCode=" + offerCode
				+ "]";
	}

}
