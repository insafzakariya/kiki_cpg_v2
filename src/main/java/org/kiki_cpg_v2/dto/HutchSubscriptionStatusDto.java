/**
 * @author Anjana Thrishakya
 * Oct 28, 2020
 * 8:02:48 AM
 */
package org.kiki_cpg_v2.dto;

/**
 *
 */
public class HutchSubscriptionStatusDto {
	private String productCode;
	private String msisdn;
	private String responseCode;
	private String status;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
