package org.kiki_cpg_v2.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "invoice_details")
public class InvoiceDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "invoice_id")
	private Integer invoiceId;

	@Column(name = "amount")
	private Double amount;

	@Column(name = "createdDate")
	private Date createdDate;

	@Column(name = "ValiedDate")
	private Date valiedDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getValiedDate() {
		return valiedDate;
	}

	public void setValiedDate(Date valiedDate) {
		this.valiedDate = valiedDate;
	}

}
