/**
 * @author Harindi Supunsara
 * Sep 11, 2020
 * 8:13:56 AM
 */
package org.kiki_cpg_v2.dto.request;

/**
 *
 */
public class PaymentRequestDto {

	private Integer viewerId;
	private Double amount;
	private String token;
	private String type;
	private String referance;

	public Integer getViewerId() {
		return viewerId;
	}

	public void setViewerId(Integer viewerId) {
		this.viewerId = viewerId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReferance() {
		return referance;
	}

	public void setReferance(String referance) {
		this.referance = referance;
	}

	@Override
	public String toString() {
		return "PaymentRequestDto [viewerId=" + viewerId + ", amount=" + amount + ", token=" + token + ", type=" + type
				+ ", referance=" + referance + "]";
	}

}
