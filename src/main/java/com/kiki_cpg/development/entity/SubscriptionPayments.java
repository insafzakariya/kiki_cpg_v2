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
	private Integer SubscriptionPaymentID;
	
	@Column(name = "ViewerID")
	private int ViewerID;
	
	@Column(name = "PackageID")
	private int PackageID;
	
	@Column(name = "TokenHash")
	public String TokenHash;
	
	@Column(name = "Status")
	private int Status;
	
	@Column(name = "CreatedDate")
	private Date CreatedDate;
	
	@Column(name = "ExpireDate")
	private Date ExpireDate;

	public Integer getSubscriptionPaymentID() {
		return SubscriptionPaymentID;
	}

	public void setSubscriptionPaymentID(Integer subscriptionPaymentID) {
		SubscriptionPaymentID = subscriptionPaymentID;
	}

	public int getViewerID() {
		return ViewerID;
	}

	public void setViewerID(int viewerID) {
		ViewerID = viewerID;
	}

	public int getPackageID() {
		return PackageID;
	}

	public void setPackageID(int packageID) {
		PackageID = packageID;
	}

	public String getTokenHash() {
		return TokenHash;
	}

	public void setTokenHash(String tokenHash) {
		TokenHash = tokenHash;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public Date getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(Date createdDate) {
		CreatedDate = createdDate;
	}

	public Date getExpireDate() {
		return ExpireDate;
	}

	public void setExpireDate(Date expireDate) {
		ExpireDate = expireDate;
	}
	
	

}
