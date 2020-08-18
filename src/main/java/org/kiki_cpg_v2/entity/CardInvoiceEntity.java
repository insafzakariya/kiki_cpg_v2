/**
 * @DaJun 23, 2020 @CardInvoiceEntity.java
 */
package org.kiki_cpg_v2.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Anjana Thrishakya
 */
@Entity
@Table(name = "card_invoice")
public class CardInvoiceEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "subscribed_days")
	private Integer subscribedDays;

	@Column(name = "amount")
	private Double amount;

	@Column(name = "transaction_no")
	private String transactionNo;

	@Column(name = "viewer_id")
	private Integer viewerId;

	@Column(name = "auth_response")
	private String authResponse;
	
	@Column(name = "transaction_type")
	private String transactionType;
	
	@Column(name = "reference_number")
	private String referanceNo;
	
	@Column(name = "decision")
	private String decision;

	@Column(name = "success")
	private Integer success;

	@Column(name = "createdDate")
	private Date createdDate;

	@Column(name = "status")
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSubscribedDays() {
		return subscribedDays;
	}

	public void setSubscribedDays(Integer subscribedDays) {
		this.subscribedDays = subscribedDays;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public Integer getViewerId() {
		return viewerId;
	}

	public void setViewerId(Integer viewerId) {
		this.viewerId = viewerId;
	}

	public String getAuthResponse() {
		return authResponse;
	}

	public void setAuthResponse(String authResponse) {
		this.authResponse = authResponse;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getReferanceNo() {
		return referanceNo;
	}

	public void setReferanceNo(String referanceNo) {
		this.referanceNo = referanceNo;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
