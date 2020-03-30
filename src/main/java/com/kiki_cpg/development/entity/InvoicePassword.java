package com.kiki_cpg.development.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity
@Table(name = "invoice_password")
public class InvoicePassword {
	
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "invoice_id")
	private String invoiceId;
	
	@Column(name = "plain_verification_password")
	private String plainVerificationPassword;
	
	@Column(name = "id")
	private String ammount;
	
	@Column(name = "id")
	private String mcashReferenceId;
	
	@Column(name = "id")
	private String customerMobile;
	
	@Column(name = "id")
	private String statusCode;
	
	@Column(name = "id")
	private String sha256Checksum;
}
