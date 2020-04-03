package com.kiki_cpg.development.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "subscription_payments")
public class SubscriptionPayments {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SubscriptionPaymentID")
	private Integer subscriptionPaymentID;

	@Column(name = "ViewerID")
	private Integer viewerID;

	@Column(name = "PackageID")
	private Integer packageID;

	@Column(name = "TokenHash")
	public String tokenHash;

	@Column(name = "Status")
	private int status;

	@Column(name = "CreatedDate")
	private Date createdDate;

	@Column(name = "ExpireDate")
	private Date expireDate;

	public Integer getSubscriptionPaymentID() {
		return subscriptionPaymentID;
	}

	public void setSubscriptionPaymentID(Integer subscriptionPaymentID) {
		this.subscriptionPaymentID = subscriptionPaymentID;
	}

	public Integer getViewerID() {
		return viewerID;
	}

	public void setViewerID(Integer viewerID) {
		this.viewerID = viewerID;
	}

	public Integer getPackageID() {
		return packageID;
	}

	public void setPackageID(Integer packageID) {
		this.packageID = packageID;
	}

	public String getTokenHash() {
		return tokenHash;
	}

	public void setTokenHash(String tokenHash) {
		this.tokenHash = tokenHash;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

}