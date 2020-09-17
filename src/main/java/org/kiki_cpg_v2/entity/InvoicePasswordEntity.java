package org.kiki_cpg_v2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "invoice_password")
public class InvoicePasswordEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "invoice_id")
	private String invoiceId;

	@Column(name = "plain_verification_password")
	private String plainVerificationPassword;

	@Column(name = "ammount")
	private String ammount;

	@Column(name = "mcash_reference_id")
	private String mcashReferenceId;

	@Column(name = "customer_mobile")
	private String customerMobile;

	@Column(name = "status_code")
	private String statusCode;

	@Column(name = "sha256_checksum")
	private String sha256Checksum;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getPlainVerificationPassword() {
		return plainVerificationPassword;
	}

	public void setPlainVerificationPassword(String plainVerificationPassword) {
		this.plainVerificationPassword = plainVerificationPassword;
	}

	public String getAmmount() {
		return ammount;
	}

	public void setAmmount(String ammount) {
		this.ammount = ammount;
	}

	public String getMcashReferenceId() {
		return mcashReferenceId;
	}

	public void setMcashReferenceId(String mcashReferenceId) {
		this.mcashReferenceId = mcashReferenceId;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getSha256Checksum() {
		return sha256Checksum;
	}

	public void setSha256Checksum(String sha256Checksum) {
		this.sha256Checksum = sha256Checksum;
	}
}
