/**
 * @author Harindi Supunsara
 * Sep 11, 2020
 * 8:17:39 AM
 */
package org.kiki_cpg_v2.dto;

/**
 *
 */
public class PaymentResponseDto {
	
	private Integer viewerId;
	private String referance;
	private String type;
	private String status;
	private String message;

	public Integer getViewerId() {
		return viewerId;
	}

	public void setViewerId(Integer viewerId) {
		this.viewerId = viewerId;
	}

	public String getReferance() {
		return referance;
	}

	public void setReferance(String referance) {
		this.referance = referance;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
