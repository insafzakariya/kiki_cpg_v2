package com.kiki_cpg.development.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "viewer_balance")
public class ViewerBalance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "viewerId")
	private Integer viewerId;

	@Column(name = "mobileNumber")
	private String mobileNumber;

	@Column(name = "balance")
	private Double balance;
	
	@Column(name = "accoun_type")
	private String accoun_type;

	@Column(name = "balance_date")
	private Date balance_date;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getViewerId() {
		return viewerId;
	}

	public void setViewerId(Integer viewerId) {
		this.viewerId = viewerId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Date getBalance_date() {
		return balance_date;
	}

	public void setBalance_date(Date balance_date) {
		this.balance_date = balance_date;
	}

	public String getAccoun_type() {
		return accoun_type;
	}

	public void setAccoun_type(String accoun_type) {
		this.accoun_type = accoun_type;
	}

	
	

}
