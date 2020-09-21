package org.kiki_cpg_v2.dto;

import java.util.Date;

public class InvoiceDetailsForNotificationEmailDto {

	private Integer invoiceId;
	private Double amount;
	private Date createDate;
	
	public Integer getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Override
	public String toString() {
		return "InvoiceDetailsForNotificationEmailDto [invoiceId=" + invoiceId + ", amount=" + amount + ", createDate="
				+ createDate + "]";
	}
	
}
