package com.kiki_cpg.development.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "invoice_password")
public class InvoicePassword {
	
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
}
