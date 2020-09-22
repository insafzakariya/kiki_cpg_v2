package org.kiki_cpg_v2.dto.request;

public class ViewerEmailUpdateDto {
	
	private String emailAddress;
	private Integer viewerId;
	private Integer invoiceId;
	private Integer paymentMethodId;
	
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Integer getViewerId() {
		return viewerId;
	}

	public void setViewerId(Integer viewerId) {
		this.viewerId = viewerId;
	}

	public Integer getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Integer getPaymentMethodId() {
		return paymentMethodId;
	}

	public void setPaymentMethodId(Integer paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}

	@Override
	public String toString() {
		return "ViewerEmailUpdateDto [emailAddress=" + emailAddress + ", viewerId=" + viewerId + ", invoiceId="
				+ invoiceId + ", paymentMethodId=" + paymentMethodId + "]";
	}

	
}
