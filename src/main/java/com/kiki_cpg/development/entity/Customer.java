package com.kiki_cpg.development.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import com.kiki_cpg.development.enums.DealerSubscriptionType;

@Entity
@Table(name = "customer")
public class Customer {

	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "viwer_id")
	private Integer viwer_id;

	@Column(name = "mobile_no")
	private String mobile_no;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private DealerSubscriptionType status;

	@Column(name = "dealer_id")
	private BigInteger dealer_id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getViwer_id() {
		return viwer_id;
	}

	public void setViwer_id(Integer viwer_id) {
		this.viwer_id = viwer_id;
	}

	public String getMobile_no() {
		return mobile_no;
	}

	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}

	public DealerSubscriptionType getStatus() {
		return status;
	}

	public void setStatus(DealerSubscriptionType status) {
		this.status = status;
	}

	public BigInteger getDealer_id() {
		return dealer_id;
	}

	public void setDealer_id(BigInteger dealer_id) {
		this.dealer_id = dealer_id;
	}
	
	

}
